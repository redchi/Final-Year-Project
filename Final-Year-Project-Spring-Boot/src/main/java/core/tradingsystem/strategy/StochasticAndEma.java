package core.tradingsystem.strategy;

import java.util.ArrayList;
import java.util.List;

import core.tradingsystem.currency.Candle;
import core.tradingsystem.currency.CandleDataHandler;
import core.tradingsystem.currency.CurrencyPair;
import core.tradingsystem.tradingbot.Order;
import core.tradingsystem.tradingbot.Position;
import core.tradingsystem.tradingbot.PositionType;

/**
 * The Class StochasticAndEma.
 */
public class StochasticAndEma extends Strategy {

	/** The stochatic period. */
	private final int stochaticPeriod;
	
	/** The ema period. */
	private final int emaPeriod;
	
	/** The latest ema value. */
	private float latestEmaValue;
	
	/** The latest stochastic value. */
	private float latestStochasticValue;
	
	/** The latest price. */
	private float latestPrice;
	
	
	/**
	 * Instantiates a new stochastic and ema.
	 *
	 * @param stochaticPeriod the stochatic period
	 * @param emaPeriod the ema period
	 */
	public StochasticAndEma(int stochaticPeriod, int emaPeriod) {
		super("stochastic and EMA");
		this.stochaticPeriod = stochaticPeriod;
		this.emaPeriod = emaPeriod;
	}
	
	
	/**
	 * {@link Strategy#getResponce(Position, CandleDataHandler, boolean, int, CurrencyPair, int)}
	 */
	@Override
	public Order getResponce(Position currentPosition,CandleDataHandler CDH,boolean usesLiveData,
			int datePointer,CurrencyPair currency,int stopLoss) {
		int dataAmt = emaPeriod*2;
		List<Candle> candlesForEma = CDH.getCandleData(currency, dataAmt, usesLiveData, datePointer);
		Candle latestpriceMin = candlesForEma.get(candlesForEma.size()-1);
		latestPrice = latestpriceMin.getClose();
		List<Float> EMAValues = calculateEMA(candlesForEma,emaPeriod);
		latestEmaValue = EMAValues.get(EMAValues.size()-1);
		List<Candle> candlesForstochastic = CDH.getCandleData(currency, stochaticPeriod, usesLiveData, datePointer);
		latestStochasticValue = calculateSingleStochastic(candlesForstochastic);
				
		if(currentPosition == null) {
			if(buyOpen() == true) {
				String reason = "price above moving average and stochatic value is under 30";
				return new Order(PositionType.BUY,currency,latestpriceMin,reason);
			}
			else if(shortOpen() == true) {
				String reason = "price below moving average and stochatic value is over 70";
				return new Order(PositionType.SHORT,currency,latestpriceMin,reason);
			}
		}
		else if(super.stopLossReached(latestPrice,(float)currentPosition.getOpeningPrice(), currentPosition.getType(), stopLoss)) {
			String reason = "Stop Loss Reached!";
			return new Order(PositionType.CLOSE,currency,latestpriceMin,reason);
		}	
		else if(currentPosition.getType() == PositionType.BUY) {// current position is buy
			if(latestPrice<latestEmaValue) {
				String reason = "price is below moving average";
				return new Order(PositionType.CLOSE,currency,latestpriceMin,reason);
			}
			else if(latestStochasticValue>80) {
				String reason = "stochastic value is over 80";
				return new Order(PositionType.CLOSE,currency,latestpriceMin,reason);
			}
		}
		else if(currentPosition.getType() == PositionType.SHORT) {//current position is sell
			if(latestPrice>latestEmaValue) {
				String reason = "price is above moving average";
				return new Order(PositionType.CLOSE,currency,latestpriceMin,reason);
			}
			else if(latestStochasticValue<20) {
				String reason = "stochastic value is under over 20";
				return new Order(PositionType.CLOSE,currency,latestpriceMin,reason);
			}

		}
		return null;
	}

	/**
	 * Buy open.
	 *
	 * @return true, if successful
	 */
	private boolean buyOpen() {
		if(latestPrice>latestEmaValue && latestStochasticValue<30) {
			return true;
		}
		return false;
	}
	
	/**
	 * Short open.
	 *
	 * @return true, if successful
	 */
	private boolean shortOpen() {
		if(latestPrice<latestEmaValue && latestStochasticValue>70) {
			return true;
		}
		return false;
	}
	

	
	
	/**
	 * Calculate EMA.
	 *
	 * @param data the data
	 * @param period the period
	 * @return the list
	 */
	private List<Float> calculateEMA(List<Candle> data,int period) {
		ArrayList<Float> EMA = new ArrayList<Float>();
		float weight = 2/(((float)period) + 1);
		float yesterdayEma = data.get(0).getClose();
		EMA.add(yesterdayEma);
		for(int i = 1;i<data.size();i++) {
			float entryEMA = (data.get(i).getClose()-yesterdayEma)*(weight)+(yesterdayEma);
			EMA.add(entryEMA);
			yesterdayEma = entryEMA;
		}
		return EMA;
		
	}
	
	/**
	 * Calculate single stochastic value
	 *
	 * @param data the data
	 * @return the float
	 */
	private Float calculateSingleStochastic(List<Candle> data){
		 float high = data.get(0).getHigh();;
		 float low = data.get(0).getLow();
		 float recent = data.get(data.size() -1).getClose();
		 for(Candle bar:data) {
			 if(bar.getHigh() > high) {
				 high = bar.getHigh();
			 }
			 if(low > bar.getLow()) {
				 low = bar.getLow();
			 }

		 }
		 float Stocatic = ((recent - low)/(high - low))*100;
		 return Stocatic;
	}
	

	/**
	 * Gets the strategy config info.
	 *
	 * @return the strategy config info
	 */
	@Override
	public ArrayList<String> getStrategyConfigInfo() {
		ArrayList<String> output = new ArrayList<String>();
		output.add("Stochastic period: "+stochaticPeriod);
		output.add("EMA period: "+emaPeriod);
		return output;
	}

	

}
