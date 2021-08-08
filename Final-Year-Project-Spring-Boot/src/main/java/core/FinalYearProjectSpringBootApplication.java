package core;

import java.util.Random;
import java.util.UUID;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import core.tradingsystem.currency.CandleDataHandler;
import core.tradingsystem.currency.CurrencyPair;
import core.tradingsystem.strategy.EmaCrossover;
import core.tradingsystem.strategy.Strategy;
import core.tradingsystem.tradingbot.TradingBot;
import core.tradingsystem.tradingbot.TradingBotManager;

@SpringBootApplication
public class FinalYearProjectSpringBootApplication implements InitializingBean {

	@Autowired
	TradingBotManager botManager;
	
	@Autowired
	CandleDataHandler dataHandler;
	/*
	 * main method, lauches application
	 */
	public static void main(String[] args) {
		SpringApplication.run(FinalYearProjectSpringBootApplication.class, args);
	}
	
	
	/**
	 * After properties set - initialise trading bot manager and data handler
	 *
	 * @throws Exception the exception
	 */
	public void afterPropertiesSet() throws Exception {
		botManager.mainTradingLoop();
		dataHandler.loadDataFromCSV();
		DateTime gmt = new DateTime(DateTimeZone.forID("GMT"));
		int day = gmt.getDayOfWeek();
		int mins = gmt.getMinuteOfDay();
		if((day == 1 && mins<365) || (day == 5 && mins>=1195) || day == 6 || day == 7 ) {
			System.out.println("################### IMPORTANT INFO!!!!");
			System.out.println("################### FOREX MARKET IS CLOSED LIVE DATA FUNCTIONALITY IS DISABLED");
			System.out.println("################### LAUNCH SERVER FROM 06:05 MONDAY TO 19:55 FRIDAY (GMT)");
		}
		else {
			dataHandler.loadHistoricalDataFromAPI();
			dataHandler.forexUpdateLoop();
		}
		loadTests();
	}
	
	/**
	 * 3 Random Tradingbots created for test1 account
	 */
	public void loadTests() {
		String username = "test1"; // a new ra
		for(int i = 0 ; i<3; i ++) {
			String ID = UUID.randomUUID().toString().replace("-", "");
			String name = getRandomString(7);		
			int EmaS = 5 + (int)(Math.random() * 20);
			int EmaL = EmaS + (int)(Math.random() * (EmaS + 40));
			int buffer = 3 + (int)(Math.random() * 10);
			Strategy strat = new EmaCrossover(EmaS, EmaL, buffer);
			TradingBot bot = new TradingBot(username,
					ID, 
					name, 
					CurrencyPair.EUR_USD,
					strat,
					false,
					100,
					100);
			
			botManager.AddNewTradingBot(bot, username);
		}
	}
	
	/**
	 * Gets the random string.
	 *
	 * @param lenght the lenght
	 * @return the random string
	 */
	protected String getRandomString(int lenght) {
       String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
       StringBuilder salt = new StringBuilder();
       Random rnd = new Random();
       while (salt.length() < lenght) { // length of the random string.
           int index = (int) (rnd.nextFloat() * SALTCHARS.length());
           salt.append(SALTCHARS.charAt(index));
       }
       String saltStr = salt.toString();
       return saltStr;
   }
	
}
