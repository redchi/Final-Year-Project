package strategyTests;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import core.backTest.EmaCrossoverBackTestRunner;
import core.tradingsystem.currency.Candle;
import core.tradingsystem.currency.CandleDataHandler;
import core.tradingsystem.currency.CurrencyPair;
import core.tradingsystem.strategy.EmaCrossover;
import core.tradingsystem.strategy.Strategy;
import core.tradingsystem.tradingbot.Broker;
import core.tradingsystem.tradingbot.Order;
import core.tradingsystem.tradingbot.TradingBot;


public class EmaCrossoverTest {

	ArrayList<Candle> uptrendCandles;
	
	@Before
	public void ini() {
//		uptrendCandles = new ArrayList<Candle>();
//		uptrendCandles.add(new Minute("date",10,10,10,10));
//		uptrendCandles.add(new Minute("date",12,12,12,12));
//		uptrendCandles.add(new Minute("date",15,15,15,15));
//		uptrendCandles.add(new Minute("date",19,19,19,19));
//		uptrendCandles.add(new Minute("date",21,21,21,21));
//		uptrendCandles.add(new Minute("date",26,26,26,26));
//		uptrendCandles.add(new Minute("date",32,32,32,32));
//		uptrendCandles.add(new Minute("date",39,39,39,39));
//		uptrendCandles.add(new Minute("date",44,44,44,44));
//		uptrendCandles.add(new Minute("date",46,46,46,46));
//		uptrendCandles.add(new Minute("date",51,51,51,51));
//		uptrendCandles.add(new Minute("date",55,55,55,55));
//		uptrendCandles.add(new Minute("date",63,63,63,63));
//		uptrendCandles.add(new Minute("date",75,75,75,75));
//		uptrendCandles.add(new Minute("date",80,80,80,80));
//		uptrendCandles.add(new Minute("date",89,89,89,89));
	}
	
	@Test
	public void uptrendConfirm() {
//		Strategy strat = new EmaCrossover(3,8,0);
//		CandleDataHandler MockdataHandler = Mockito.mock(CandleDataHandler.class);
//		Mockito.when(MockdataHandler.getLatestCandleData(
//				Mockito.any(CurrencyPair.class),Mockito.anyInt(),Mockito.anyBoolean(),Mockito.anyInt())).
//				thenReturn(uptrendCandles);
//		
//		Order order = strat.getResponce(
//				null, 
//				MockdataHandler, 
//				false, 
//				12, 
//				CurrencyPair.USD_GDP, 
//				40);
//		
//		Assert.assertEquals(order, OrderType.BUY);
	}
	
	
	

	
	@Test
	public void test1() {
		

	}
	
	
	public static void main(String[] args) {
		test2();
	}

	public static void test2() {
		
		int threadsAmt = 8;
        ExecutorService pool = Executors.newFixedThreadPool(threadsAmt);
		
		ArrayList<CandleDataHandler> dataHandlers = new ArrayList<CandleDataHandler>();
		for(int i = 0;i<1;i++) {
			CandleDataHandler dataHandler  = new CandleDataHandler();
			dataHandler.loadDataFromCSV();
			dataHandlers.add(dataHandler);
		}
		
		
		int maxST_EMA = 160;
		int maxLT_EMA = 210;
		int buffer = 10;
		int step = 10;
		
		int emaSIncrement = maxST_EMA/step;
		int emaLIncrement = maxLT_EMA/step;
        
		int count = 0;
		for(int i = 0;i<emaLIncrement;i++) {
			int emaL = i*step;
			for(int j = 0;j<emaSIncrement;j++) {
				int emaS = j*step;	
				if(emaS>3 && emaS<emaL && buffer+emaS<emaL) {	
					CandleDataHandler dataHandler = dataHandlers.get(0);
					Runnable task = new EmaCrossoverBackTestRunner(dataHandler,emaS,emaL,buffer);
					pool.execute(task);
					count = 0;
				}
			}
		}
		pool.shutdown();
		
	}

	
}



