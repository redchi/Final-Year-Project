package core.tradingsystem.tradingbot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import core.tradingsystem.currency.Candle;
import core.tradingsystem.currency.CandleDataHandler;
import core.tradingsystem.currency.CurrencyPair;
import core.tradingsystem.strategy.Strategy;

/**
 * The Class TradingBot.
 */
public class TradingBot {
	
	private final String username; //username linked to bot
	private final String ID; // bot id
	private final CurrencyPair currency; // currency bot is trading on
	private final long timeCreated;	// time bot was creates in GMT
	private final Strategy strategy;// strategy bot is using
	private final Broker broker;	// bots broker
	private final String name; // bots name
	private int stoploss;// stoploss for any trade
	private int maxTrades; // maximum amount of trades bot can perform
	private int tradingCount; // current trades count
	/*
	 * -1 = server error
	 * 0 = no interrupt
	 * 1 = paused by user
	 * 2 = max trade count reached
	 * 3 = simulated data ended
	 * 4 = live market is closed
	 * 
	 */
	private int interuptType; //see above ^^
	private boolean usesLiveData; // states whether bot use live or simulated data
	private int simulatedDataPointer; // data pointer for simulated data
	private List<Candle> prevCandles;// candles used in previous trade, used by candlestick chart in bot view

	/**
	 * Instantiates a new trading bot.
	 *
	 * @param username the username
	 * @param iD the i D
	 * @param name the name
	 * @param currency the currency
	 * @param strategy the strategy
	 * @param usesLiveData the uses live data
	 * @param stoploss the stoploss
	 * @param maxTrades the max trades
	 */
	public TradingBot(String username,String iD,String name, CurrencyPair currency, 
			Strategy strategy, boolean usesLiveData,int stoploss,int maxTrades) {
		this.username = username;
		DateTime gmt = new DateTime(DateTimeZone.forID("GMT"));
		timeCreated = gmt.getMillis();
		ID = iD;
		this.name = name;
		this.currency = currency;
		this.strategy = strategy;
		this.usesLiveData = usesLiveData;
		this.simulatedDataPointer = 0;
		this.stoploss = stoploss;
		this.maxTrades = maxTrades;
		prevCandles = Collections.synchronizedList(new ArrayList<Candle>()); // syned cuz it get might be read and moded at the same time
		broker = new Broker();
		interuptType = 0;
	}

	/**
	 * update - Run one cycle of trading. 
	 * checks for interrupts, if so then tells trading bot manager
	 * otherwise gets current position from broker, applies strategy and gives order to broker
	 *
	 * @param dataHandler - used to get forex data
	 * @return true, if bot signals an interrupt, false otherwise
	 */
	public boolean update(CandleDataHandler dataHandler) {
		checkForInterupts();
		if(interuptType != 0) { 
			return true;
		}
		synchronized (broker) {
			Position pos = broker.getCurrentPosition();
			Order order = strategy.getResponce(pos, dataHandler, usesLiveData, simulatedDataPointer,currency,stoploss);
			if(order != null) {
				broker.processOrder(order);
				if(order.getOrderType() == PositionType.CLOSE) {
					tradingCount = tradingCount + 1;
				}
			}
		}
		
		// for candle stick chart 
		int candleAmt = 50;
		synchronized (prevCandles) {
			prevCandles.clear();
			prevCandles.addAll(dataHandler.getCandleData(currency, candleAmt, usesLiveData, simulatedDataPointer));
		}
		
		if(usesLiveData == false) {
			simulatedDataPointer = simulatedDataPointer + 1;
		}
		
		return false;
	}
	
	/**
	 * Check for interupts.
	 */
	private void checkForInterupts() {
		if(tradingCount>=maxTrades) {
			interuptType = 2;
		}
		else if(simulatedDataPointer >= 372000) {
			interuptType = 3;
		}
		if(usesLiveData == true) {
			DateTime gmt = new DateTime(DateTimeZone.forID("GMT"));
			int day = gmt.getDayOfWeek();
			int mins = gmt.getMinuteOfDay();
			if(day == 6) { // day is sat - market closed
				interuptType = 4;
			}
			else if( day == 5 && mins>=1195) { // day is friday and time is above 19:55
				interuptType = 4;
			}
			else if(day == 7 && mins<=1205) { // day is sunday and time is below 20:05
				interuptType = 4;
			}
		}
	}
	
	/**
	 * Gets the current state of trading bot and converts it into json format.
	 * used by "botview.jsp" in ajax requests
	 * @return the current state of trading bot in json
	 * @throws Exception - cannot convert objects to json
	 */
	public String getCurrentStateInfoJson() throws Exception {
		HashMap<String, String> brokerStateInfoMap;
		List<Order> historicalOrders;
		synchronized (broker) {
			brokerStateInfoMap = broker.getStateInfo();
			historicalOrders = broker.getHistoricalOrders();
		}
		
		ObjectMapper mapper = new ObjectMapper();	
		HashMap<String, String> botInfo = new HashMap<String, String>();
		botInfo.put("currency", currency.getTypeName());
		botInfo.put("name", name);
		botInfo.put("interuptType", interuptType+"");
		
		ObjectNode rootNode = mapper.createObjectNode();	
		rootNode.set("botInfo", mapper.valueToTree(botInfo));
		rootNode.set("brokerInfo", mapper.valueToTree(brokerStateInfoMap));	
		rootNode.set("historicalOrder", mapper.valueToTree(historicalOrders));	
		synchronized (prevCandles) {		
			rootNode.set("candleData", mapper.valueToTree(prevCandles));
		}
		
		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
	}
	

	/**
	 * Reset trading count.
	 */
	public void resetTradingCount() {
		tradingCount = 0;
		interuptType = 0;		
		
	}
	
	/**
	 * Gets the linked username.
	 *
	 * @return the linked username
	 */
	public String getLinkedUsername() {
		return username;
	}
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getID() {
		return ID;
	}

	/**
	 * Gets the currency.
	 *
	 * @return the currency
	 */
	public CurrencyPair getCurrency() {
		return currency;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the time created.
	 *
	 * @return the time created
	 */
	public long getTimeCreated() {
		return timeCreated;
	}
	
	/**
	 * Gets the strategy name.
	 *
	 * @return the strategy name
	 */
	public String getStrategyName() {
		return this.strategy.getName();
	}
	
	/**
	 * Gets the interupt type.
	 *
	 * @return the interupt type
	 */
	public int getInteruptType() {
		return interuptType;
	}
	
	
	/**
	 * Sets the interupt type.
	 *
	 * @param type the new interupt type
	 */
	public void setInteruptType(int type) {
		this.interuptType = type;
	}
	
	/**
	 * Gets the strategy config info.
	 *
	 * @return the strategy config info
	 */
	public ArrayList<String> getStrategyConfigInfo(){
		return strategy.getStrategyConfigInfo();
	}
	
	
}
