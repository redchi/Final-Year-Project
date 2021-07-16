package core.tradingsystem.tradingbot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import core.tradingsystem.currency.CandleDataHandler;


// TODO: Auto-generated Javadoc
/**
 * The Class TradingBotManager- manages all trading bots on server
 */
@Component
public class TradingBotManager{
	
	/** The data handler for currency prices */
	private CandleDataHandler dataHandler;
	
	/** list of actively trading bots */
	private CopyOnWriteArrayList<TradingBot> activlyTradingBots; 
	
	/** list of paused trading bots. */
	private CopyOnWriteArrayList<TradingBot> pausedTradingBots;
	
	/** maps username to their linked trading bot. */
	private  Map<String, List<TradingBot>> usersToTradingBot;
	
	/** maps botID to tradingbot object */
	private Map<String, TradingBot> botIDtoTradingBot;
	

	
	/**
	 * Instantiates a new trading bot manager.
	 *
	 * @param dataHandler the data handler
	 */
	@Autowired
	public TradingBotManager(CandleDataHandler dataHandler) {
		usersToTradingBot = Collections.synchronizedMap(new HashMap<String, List<TradingBot>>());
		botIDtoTradingBot = Collections.synchronizedMap(new HashMap<String, TradingBot>());
		activlyTradingBots = new CopyOnWriteArrayList<TradingBot>();
		pausedTradingBots = new CopyOnWriteArrayList<TradingBot>();	
		this.dataHandler = dataHandler;
	}
	

	/**
	 * Find trading bot linked to user.
	 *
	 * @param Username the username
	 * @return the list
	 */
	public List<TradingBot> findTradingBotLinkedToUser(String Username) {
		synchronized(usersToTradingBot) {
			return usersToTradingBot.get(Username);
		}
	}

	/**
	 * Gets trading bot.
	 *
	 * @param botId the bot id
	 * @return the bot
	 */
	public TradingBot getBot(String botId) {
		synchronized (botIDtoTradingBot) {
			return botIDtoTradingBot.get(botId);
		}
	}
	
	
	/**
	 * Adds a new trading bot.
	 *
	 * @param bot- the new trading bot
	 * @param username the username linked to bot
	 */
	public void AddNewTradingBot(TradingBot bot,String username) {
		synchronized(usersToTradingBot) {
			synchronized(botIDtoTradingBot) {
				if(usersToTradingBot.get(username)==null) {
					List<TradingBot> bots = new ArrayList<TradingBot>();
					botIDtoTradingBot.put(bot.getID(), bot);
					bots.add(bot);
					usersToTradingBot.put(username, bots);
				}
				else {
					usersToTradingBot.get(username).add(bot);
					botIDtoTradingBot.put(bot.getID(), bot);
				}
				activlyTradingBots.add(bot);
			}
		}
	}

	/**
	 * checks if a username is linked to a trading bot
	 *
	 * @param botID the bot ID
	 * @param username the username
	 * @return true, if bot exists and username is linked to bot
	 */
	public boolean checkAccessRights(String botID,String username) {
			TradingBot bot = this.getBot(botID);
			if(bot != null && bot.getLinkedUsername().equals(username)) {
				return true;
			}
		return false;
	}
	
	/**
	 * Delete trading bot.
	 *
	 * @param botID the bot ID
	 */
	public synchronized void deleteTradingBot(String botID) {
		TradingBot bot = this.getBot(botID);
		String username = bot.getLinkedUsername();
		activlyTradingBots.remove(this.getBot(botID));	
		synchronized(usersToTradingBot) {
			synchronized(botIDtoTradingBot) {
				usersToTradingBot.get(username).remove(bot);
				botIDtoTradingBot.remove(botID);
			}
		}
	}
	
	
	/**
	 * Switch trading bot to active list.
	 *
	 * @param botID the bot ID
	 */
	public void switchTradingBotToActiveList(String botID) {
		TradingBot bot = this.getBot(botID);
		if(bot!=null) {
			pausedTradingBots.remove(bot);
			activlyTradingBots.add(bot);
		}	
	}
	
	/**
	 * Switch trading bot to paused list.
	 *
	 * @param botID the bot ID
	 */
	public void switchTradingBotToPausedList(String botID) {
		TradingBot bot = this.getBot(botID);
		if(bot!=null) {
			activlyTradingBots.remove(bot);
			pausedTradingBots.add(bot);
		}	
	}
	
	
	/**
	 * the Main trading loop.
	 * updates all actively trading bots 5 times per second
	 * if a bot signals an interrupt or throws an exception, it switches that bot to paused list
	 */
	@Async
	public void mainTradingLoop() {
		boolean stop = false;
		while(stop == false) {
			ArrayList<TradingBot> botRemoval = new ArrayList<TradingBot>();
			for(TradingBot bot:activlyTradingBots) {
				try { 
					boolean botUpdateInterupt = bot.update(dataHandler);
					if(botUpdateInterupt == true) {
						botRemoval.add(bot);
					}
				}
				catch (Exception e) {
					bot.setInteruptType(-1);
			// interrupt type of -1 means a server error
					botRemoval.add(bot);}
			}
			for(TradingBot bot:botRemoval) {
				switchTradingBotToPausedList(bot.getID());			}
			// sleep
			try {
				Thread.sleep(200);
			} catch (Exception e) {
			}
		}
	}
	
	

	

	
}
