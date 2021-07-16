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
import core.tradingsystem.strategy.Strategy;
import core.tradingsystem.tradingbot.Broker;
import core.tradingsystem.tradingbot.TradingBot;

public class EmaCrossoverBackTestRunner implements Runnable {

	private static final String outputFilePath ="src/main/resources/Data/Raw/EmaCrossoverBackTestResults.csv";
	private CandleDataHandler dataHandler;
	private TradingBot bot;
	private int emaS;
	private int emaL;
	private int buffer;
	
	public EmaCrossoverBackTestRunner(CandleDataHandler dataHandler, int emaS, int emaL,int buffer) {
		this.dataHandler = dataHandler;
		this.emaL = emaL;
		this.emaS = emaS;
		this.buffer = buffer;
		Strategy strat = new EmaCrossover(emaS, emaL, buffer);
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

	public int getEmaS() {
		return emaS;
	}

	public int getEmaL() {
		return emaL;
	}

	public int getBuffer() {
		return buffer;
	}

	private static synchronized void addResult(EmaCrossoverBackTestRunner instance) {
		try {
			System.out.println("a bot has completed backtesting");
			TradingBot bot = instance.getBot();
			int emaL = instance.getEmaL();
			int emaS = instance.getEmaS();
			int buffer = instance.getBuffer();
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
			System.out.println("results: emaS-"+emaS+"  emaL-"+emaL+"  buffer-"+buffer+"  P/L-"+P_L+"  W/L-"+W_L);
			FileWriter writer = new FileWriter(new File(outputFilePath),true);
		      writer.append(emaS+"");
		      writer.append(',');
		      writer.append(emaL+"");
		      writer.append(',');
		      writer.append(buffer+"");
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
