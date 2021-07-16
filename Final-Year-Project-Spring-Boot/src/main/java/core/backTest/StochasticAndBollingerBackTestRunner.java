package core.backTest;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.reflect.FieldUtils;

import core.tradingsystem.currency.CandleDataHandler;
import core.tradingsystem.currency.CurrencyPair;
import core.tradingsystem.strategy.EmaCrossover;
import core.tradingsystem.strategy.StochasticAndBollinger;
import core.tradingsystem.strategy.StochasticAndEma;
import core.tradingsystem.strategy.Strategy;
import core.tradingsystem.tradingbot.Broker;
import core.tradingsystem.tradingbot.TradingBot;

public class StochasticAndBollingerBackTestRunner implements Runnable {

	private static final String outputFilePath ="src/main/resources/Data/Raw/StochasticAndBollingerBackTestResults.csv";
	private CandleDataHandler dataHandler;
	private TradingBot bot;
	private int bollinger;
	private int stoch;
	
	public StochasticAndBollingerBackTestRunner(CandleDataHandler dataHandler, int bollinger, int stochastic) {
		this.dataHandler = dataHandler;
		this.bollinger = bollinger;
		this.stoch = stochastic;
		Strategy strat = new StochasticAndBollinger(bollinger, stochastic);
		bot = new TradingBot("test",
				"test",
				"test", 
				CurrencyPair.EUR_USD,
				strat,
				false,
				0,
				472000);
	}

	public void run() {
		for(int k = 0;k<372000;k++) {
			bot.update(dataHandler);
		}
		addResult(this);
	}

	public TradingBot getBot() {
		return bot;
	}

	public int getBollinger() {
		return bollinger;
	}
	
	public int getStoch() {
		return stoch;
	}

	private static synchronized void addResult(StochasticAndBollingerBackTestRunner instance) {
		try {
			System.out.println("a bot has completed backtesting");
			TradingBot bot = instance.getBot();
			int bollinger = instance.getBollinger();
			int stoch = instance.getStoch();

			Broker br = (Broker) FieldUtils.readField(bot,"broker",true);
			double profitPips = (Double) FieldUtils.readField(br,"profitPips",true);
			double lossPips = (Double) FieldUtils.readField(br,"lossPips",true);
			int badTradesCount = (Integer) FieldUtils.readField(br,"badTradesCount",true);
			int goodTradesCount = (Integer) FieldUtils.readField(br,"goodTradesCount",true);
			float P_L = (float) (profitPips/lossPips);
			float W_L = (float)goodTradesCount/(float)badTradesCount;
			if(Float.isInfinite(P_L)) {
				P_L = (float) profitPips*1000;
			}
			if(Float.isInfinite(W_L)) {
				W_L = 999;
			}
			if(Float.isNaN(P_L)) {
				P_L = 0;
			}
			if(Float.isNaN(W_L)) {
				W_L = 0;
			}
			System.out.println("P_L = "+P_L);
			FileWriter writer = new FileWriter(new File(outputFilePath),true);
		      writer.append(bollinger+"");
		      writer.append(',');
		      writer.append(stoch+"");
		      writer.append(',');
		      writer.append(P_L+"");
		      writer.append('\n');
		      writer.flush();
		      writer.close();
			
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
	
}
