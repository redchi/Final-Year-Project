package core.backTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import core.tradingsystem.currency.CandleDataHandler;

public class StochasticAndEmaBackTest {

	
	public static void main(String[] args) throws Exception {
		run();
	}
	
	public static void run() throws Exception{
		int threadsAmt = 8; // increase this if your pc can support it 
        ExecutorService pool = Executors.newFixedThreadPool(threadsAmt);

		CandleDataHandler dataHandler  = new CandleDataHandler();
		dataHandler.loadDataFromCSV();
		
		
		int maxEma = 210;
		int maxStoch= 210;
		int step = 10;
		
		int emaIncrement = maxEma/step;
		int stochIncrement= maxStoch/step;
		
		for(int i = 0;i<emaIncrement;i++) {
			int ema = i*step;
			for(int j = 0;j<stochIncrement;j++) {
				int stoch = j*step;
				if(ema>5 && stoch>5 ) {	
					Runnable task = new StochasticAndEmaBackTestRunner(dataHandler, ema, stoch);
					pool.execute(task);
				}
			}
		}
		pool.shutdown();
		
	}
}
