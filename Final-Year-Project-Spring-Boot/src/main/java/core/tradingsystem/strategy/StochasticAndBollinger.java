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
 * The Class StochasticAndBollinger.
 */
public class StochasticAndBollinger extends Strategy {

	/** The bollinger period. */
	private final int bollingerPeriod;
	
	/** The stochastic period. */
	private final int stochasticPeriod;
	
	/** The bollinger low. */
	private float bollingerLow;
	
	/** The bollinger high. */
	private float bollingerHigh;
	
	/** The Stochastic value. */
	private float StochasticValue;
	
	/** The latest price. */
	private float latestPrice;
	
	/**
	 * Instantiates a new stochastic and bollinger.
	 *
	 * @param bollingerPeriod the bollinger period
	 * @param stochasticPeriod the stochastic period
	 */
	public StochasticAndBollinger(int bollingerPeriod,int stochasticPeriod) {
		super("Stochastic and Bollinger");
		this.bollingerPeriod = bollingerPeriod;
		this.stochasticPeriod = stochasticPeriod;
	}

	/**
	 * {@link Strategy#getResponce(Position, CandleDataHandler, boolean, int, CurrencyPair, int)}
	 */
	@Override
	public Order getResponce(Position currentPosition, CandleDataHandler CDH, boolean usesLiveData, int datePointer,
			CurrencyPair currency, int stopLoss) {
		List<Candle> candlesForStochastic = CDH.getCandleData(currency, stochasticPeriod, usesLiveData, datePointer);
		Candle latestpriceMin = candlesForStochastic.get(candlesForStochastic.size()-1);
		latestPrice = latestpriceMin.getClose();

		StochasticValue = calculateSingleStochastic(candlesForStochastic);
		
		List<Candle> candlesForBollinger = CDH.getCandleData(currency, bollingerPeriod, usesLiveData, datePointer);
		float [] bollingerBands = calculateBands(candlesForBollinger, 2);
		bollingerHigh = bollingerBands[0];
		bollingerLow = bollingerBands[2];

		if(currentPosition == null) {
			if(buyOpen() == true) {
				String reason = "unkown ";
				return new Order(PositionType.BUY,currency,latestpriceMin,reason);
			}
			else if(shortOpen() == true) {
				String reason = "unkown";
				return new Order(PositionType.SHORT,currency,latestpriceMin,reason);
			}
		}
		else if(super.stopLossReached(latestPrice,(float)currentPosition.getOpeningPrice(), currentPosition.getType(), stopLoss)) {
			String reason = "Stop Loss Reached!";
			return new Order(PositionType.CLOSE,currency,latestpriceMin,reason);
		}	
		else if(currentPosition.getType() == PositionType.BUY) {// current position is buy
			if(buyClose() == true) {
				String reason = "unkown";
				return new Order(PositionType.CLOSE,currency,latestpriceMin,reason);
			}
		}
		else if(currentPosition.getType() == PositionType.SHORT) {//current position is sell
			if(shortClose() == true) {
				String reason = "unkown";
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
		if(latestPrice<=bollingerLow && StochasticValue<30) {
			return true;
		}
		return false;
	}
	
	/**
	 * Buy close.
	 *
	 * @return true, if successful
	 */
	private boolean buyClose() {
		if(latestPrice>=bollingerHigh && StochasticValue>80) {
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
		if(latestPrice>=bollingerHigh && StochasticValue>70) {
			return true;
		}
		return false;
	}
	
	/**
	 * Short close.
	 *
	 * @return true, if successful
	 */
	private boolean shortClose() {
		if(latestPrice<bollingerLow &&StochasticValue<20) {
			return true;
		}
		return false;
	}

	/**
	 * Gets the strategy config info.
	 *
	 * @return the strategy config info
	 */
	@Override
	public ArrayList<String> getStrategyConfigInfo() {
		ArrayList<String> output = new ArrayList<String>();
		output.add("Stochastic period: "+stochasticPeriod);
		output.add("bollingerPeriod period: "+bollingerPeriod);
		return output;
	}
	
    /**
     * Calculate Bollinger bands.
     * 
     *
     * @param data the data
     * @param differentialRange the standard deviation range 
     * @return the float[]
     */
    private float[] calculateBands(List<Candle> data,float differentialRange)
    {
        double sum = 0.0, standardDeviation = 0.0; 
        
        // -1 here if population
        int length = data.size();

        for(Candle price : data) {
            sum += price.getClose();
        }
        // mean or simple moving avearge
        double SMA = sum/length;

        for(Candle price: data) {
            standardDeviation += Math.pow(price.getClose() - SMA, 2);
        }
        float SD = (float) Math.sqrt(standardDeviation/length);
        float middle = (float) SMA;
        float upper =middle + ( SD*differentialRange);
        float lower = middle - ( SD*differentialRange);	
        return new float[] {upper,middle,lower};		
        
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
	
	
	
	
	
}
