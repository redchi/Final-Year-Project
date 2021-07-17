package core.tradingsystem.strategy;

import java.util.ArrayList;
import java.util.List;

import core.tradingsystem.currency.Candle;
import core.tradingsystem.currency.CandleDataHandler;
import core.tradingsystem.currency.CurrencyPair;
import core.tradingsystem.tradingbot.Order;
import core.tradingsystem.tradingbot.Position;
import core.tradingsystem.tradingbot.PositionType;

// TODO: Auto-generated Javadoc
/**
 * The Class EmaCrossover.
 */
public class EmaCrossover extends Strategy {

	/** The short term ema period */
	private final int emaStPeriod;
	
	/** The long term ema period. */
	private final int emaLtPeriod;
	
	/** The buffer  in pips */
	private final int bufferIn;
	
	/** The pips buffer in real value */
	private final float pipsBuffer;	
	
	/** The latest ema S value. */
	private float latestEmaSValue;
	
	/** The latest ema L value. */
	private float latestEmaLValue;
	
	/** The latest closeing price. */
	private float latestPrice;
	
	
	/**
	 * Instantiates a new ema crossover strategy
	 *
	 * @param EmaS the ema S
	 * @param EmaL the ema L
	 * @param pipsBuffer the pips buffer
	 */
	public EmaCrossover(int EmaS,int EmaL,int pipsBuffer) {
		super("Ema CrossOver");
		this.bufferIn = pipsBuffer;
		this.emaStPeriod = EmaS;
		this.emaLtPeriod = EmaL;
		this.pipsBuffer = (float) (pipsBuffer*0.0001);
	}
	
	/**
	 * {@link Strategy#getResponce(Position, CandleDataHandler, boolean, int, CurrencyPair, int)}
	 */
	@Override
	public Order getResponce(Position currentPosition,CandleDataHandler CDH,
			boolean usesLiveData,int datePointer,CurrencyPair currency,int stopLoss) {
		
	
		int dataAmt = emaLtPeriod*2;
		List<Candle> candles = CDH.getCandleData(currency, dataAmt, usesLiveData, datePointer);
		Candle latestMin = candles.get(candles.size()-1);
		List<Float> EMASValues = calculateEMA(candles,emaStPeriod);
		List<Float> EMALValues = calculateEMA(candles,emaLtPeriod);
		latestEmaSValue = EMASValues.get(EMASValues.size()-1);
		latestEmaLValue = EMALValues.get(EMASValues.size()-1);
		latestPrice = candles.get(candles.size()-1).getClose();
						
		if(currentPosition == null) { // NO OPEN POSITIONS
			if(BuyOpen() == true) {
				String reason = "Short term Ema is above long term EMA";
				return new Order(PositionType.BUY,currency,latestMin,reason);
			}
			else if(ShortOpen() == true) {
				String reason = "Short term Ema is below long term EMA";
				return new Order(PositionType.SHORT,currency,latestMin,reason);
			}
		}
		// there is a open position so check if we hit stop loss first
		else if(super.stopLossReached(latestPrice,(float)currentPosition.getOpeningPrice(), 
				currentPosition.getType(), stopLoss)) {
			String reason = "Stop Loss Reached!";
			return new Order(PositionType.CLOSE,currency,latestMin,reason);
		}	
		else if(currentPosition.getType() == PositionType.BUY) { // currently in buy position
			
			if(BuyClose() == true) {
				// new order to close
				String reason = "Short term Ema is below long term EMA";
				return new Order(PositionType.CLOSE,currency,latestMin,reason);
			}
		}
		
		else if(currentPosition.getType() == PositionType.SHORT) {//currently in short position
			if(ShortClose() == true) {
				String reason = "Short term Ema is above long term EMA";
				return new Order(PositionType.CLOSE,currency,latestMin,reason);
			}
		}
		
		return null;
	}

	/**
	 * Short open.
	 *
	 * @return true, if successful
	 */
	private boolean ShortOpen() {
		if(latestEmaSValue+pipsBuffer<latestEmaLValue&&latestPrice<latestEmaSValue) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * Short close.
	 *
	 * @return true, if successful
	 */
	private boolean ShortClose(){
		if(latestEmaSValue>latestEmaLValue&&latestPrice>latestEmaSValue) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * Buy open.
	 *
	 * @return true, if successful
	 */
	private boolean BuyOpen() {
		if(latestEmaSValue-pipsBuffer>latestEmaLValue && latestPrice>latestEmaSValue) {
			return true;
		}
		return false;
		
	}
	
	/**
	 * Buy close.
	 *
	 * @return true, if successful
	 */
	private boolean BuyClose() {
		if(latestEmaSValue<latestEmaLValue&&latestPrice<latestEmaSValue) {
			return true;
		}
		return false;
	}
	

	
	/**
	 * Calculate EMA for a given set of values
	 *
	 * @param data the data
	 * @param period the period
	 * @return the list
	 */
	private List<Float>  calculateEMA(List<Candle> data,int period) {
		ArrayList<Float> EMA = new ArrayList<Float>();
		float weight = 2/(((float)period) + 1);
		float yesterdayEma = data.get(0).getClose();
		//first entry
		EMA.add(yesterdayEma);
		for(int i = 1;i<data.size();i++) {
			float entryEMA = (data.get(i).getClose()-yesterdayEma)*(weight)+(yesterdayEma);
			EMA.add(entryEMA);
			yesterdayEma = entryEMA;
		}
		return EMA;
	}


	/**
	 * Gets the strategy config info.
	 *
	 * @return the strategy config info
	 */
	@Override
	public ArrayList<String> getStrategyConfigInfo() {
		ArrayList<String> output = new ArrayList<String>();
		output.add("Short Term Ema: "+emaStPeriod);
		output.add("Long Term Ema: "+emaLtPeriod);
		output.add("buffer: "+bufferIn);
		return output;
	}


}









 