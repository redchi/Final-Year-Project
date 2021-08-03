package core;

import java.util.Random;
import java.util.UUID;

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
	 * After properties set - initialise botmanager and data handler
	 *
	 * @throws Exception the exception
	 */
	public void afterPropertiesSet() throws Exception {
		botManager.mainTradingLoop();
		dataHandler.loadDataFromCSV();
	//	dataHandler.loadHistoricalDataFromAPI();
	//	dataHandler.forexUpdateLoop();
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
