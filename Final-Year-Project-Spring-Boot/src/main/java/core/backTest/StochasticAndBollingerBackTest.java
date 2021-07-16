package core.backTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import core.tradingsystem.currency.CandleDataHandler;

public class StochasticAndBollingerBackTest {

	
	public static void main(String[] args) throws Exception {
		run();
	}
	
	public static void run() throws Exception{
		int threadsAmt = 8;
        ExecutorService pool = Executors.newFixedThreadPool(threadsAmt);

		CandleDataHandler dataHandler  = new CandleDataHandler();
		dataHandler.loadDataFromCSV();
		
		
		int maxBollinger = 210;
		int maxStoch= 210;
		int step = 10;
		
		int bollingIncrement = maxBollinger/step;
		int stochIncrement= maxStoch/step;
		
		for(int i = 0;i<bollingIncrement;i++) {
			int bollinger = i*step;
			for(int j = 0;j<stochIncrement;j++) {
				int stoch = j*step;
				if(bollinger>5 && stoch>5 ) {	
					Runnable task = new StochasticAndBollingerBackTestRunner(dataHandler, bollinger, stoch);
					pool.execute(task);
				}
			}
		}
		pool.shutdown();
		
	}
}
