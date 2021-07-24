package core.backTest;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import core.tradingsystem.currency.CandleDataHandler;

public class EmaCrossoverBackTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		run();
	}

	
	public static void run() {
		
		int threadsAmt = 8;
        ExecutorService pool = Executors.newFixedThreadPool(threadsAmt);
		CandleDataHandler dataHandler = new CandleDataHandler();
		dataHandler.loadDataFromCSV();

		int maxST_EMA = 160;
		int maxLT_EMA = 210;
		int buffer = 10;
		int step = 10;
		
		int emaSIncrement = maxST_EMA/step;
		int emaLIncrement = maxLT_EMA/step;
        
		for(int i = 0;i<emaLIncrement;i++) {
			int emaL = i*step;
			for(int j = 0;j<emaSIncrement;j++) {
				int emaS = j*step;	
				if(emaS>4 && emaS<emaL && buffer+emaS<emaL) {	
					Runnable task = new EmaCrossoverBackTestRunner(dataHandler,emaS,emaL,buffer);
					pool.execute(task);
				}
			}
		}
		pool.shutdown();
	}
}
