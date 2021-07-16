package core.tradingsystem.strategy;

import java.util.ArrayList;

import core.tradingsystem.currency.CandleDataHandler;
import core.tradingsystem.currency.CurrencyPair;
import core.tradingsystem.tradingbot.Order;
import core.tradingsystem.tradingbot.Position;
import core.tradingsystem.tradingbot.PositionType;

// TODO: Auto-generated Javadoc
/**
 * The Class Strategy.
 */
public abstract class Strategy {

	/** The name of strategy */
	protected final String name;
	
	/**
	 * Instantiates a new strategy.
	 *
	 * @param name the name
	 */
	public Strategy(String name) {
		this.name = name;
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
	 * Gets the responce.
	 *
	 * @param currentPosition the current position
	 * @param CDH the candleDataHandler
	 * @param usesLiveData the uses live data
	 * @param datePointer the date pointer
	 * @param currency the currency
	 * @param stopLoss the stop loss
	 * @return the responce
	 */
	public abstract Order getResponce(Position currentPosition,CandleDataHandler CDH,
			boolean usesLiveData,int datePointer,CurrencyPair currency,int stopLoss);
	
	/**
	 * Gets the strategy configuration info.
	 * used by "botview.jsp" when displaying strategy param details
	 * @return the strategy config info
	 */
	public abstract  ArrayList<String> getStrategyConfigInfo();
	
	/**
	 * Stop loss reached, checks whether the stop loss has been reached
	 *
	 * @param currentPrice the current price
	 * @param openingPrice the opening price
	 * @param orderType the order type
	 * @param stoplossPips the stoploss pips
	 * @return true, if stop loss has been reached
	 */
	protected boolean stopLossReached(float currentPrice,float openingPrice,PositionType orderType,int stoplossPips) {
		float stopLoss = (float) (stoplossPips*0.0001) * -1;
		if(orderType == PositionType.SHORT && (openingPrice - currentPrice)<stopLoss) {
			return true;
		}
		if(orderType == PositionType.BUY && (currentPrice-openingPrice)<stopLoss) {
			return true;
		}
		return false;
		
	}

	
}
