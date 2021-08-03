package core.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import core.tradingsystem.currency.CandleDataHandler;
import core.tradingsystem.currency.CurrencyPair;
import core.tradingsystem.strategy.EmaCrossover;
import core.tradingsystem.strategy.Strategy;
import core.tradingsystem.tradingbot.TradingBot;
import core.tradingsystem.tradingbot.TradingBotManager;


/**
 * The Class BotInfoController - handles all functionality relating to retrieving information from trading bots
 */
@Controller
public class BotInfoController  {

	
	/** The bot manager. */
	@Autowired
	TradingBotManager botManager;
	
	/** The data handler. */
	@Autowired
	CandleDataHandler dataHandler;


	/**
	 * Display the allbots page
	 *
	 * @param session the session
	 * @return the model and view
	 */
	@RequestMapping("/allBots")
	public ModelAndView displayAllbots(HttpSession session) {
		if(isloggedIn(session) == true) {
			String username = (String) session.getAttribute("username");
			List<TradingBot> allUserBots = botManager.findTradingBotLinkedToUser(username);	
			
			List<GeneralBotInfo> botInfo = new ArrayList<GeneralBotInfo>();
			if(allUserBots != null) {
				for(TradingBot bot:allUserBots) {
					DateTime dateTemp = new DateTime(bot.getTimeCreated());
					DateTimeFormatter fmt = DateTimeFormat.forPattern("d/M/Y k:m");
					String date = dateTemp.toString(fmt);
					GeneralBotInfo info = new GeneralBotInfo(bot.getName(),
							bot.getID(),date,bot.getCurrency().toString());
					botInfo.add(info);
				}
			}
			ModelAndView mv = new ModelAndView();
			mv.setViewName("BotView/allBotsTable");
			mv.addObject("botInfoArr", botInfo);
			return mv;
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName("forward:/404");
		return mv;
	}
	
	   /**
   	 * Gets state of a trading bot and return response in json
   	 *
   	 * @param botID the bot ID
   	 * @param response the response
   	 * @param session the session
   	 * @return the bot state
   	 */
   	@RequestMapping(value="/getBotStateInfo", produces = "application/json")
	    @ResponseBody
		public String getBotState(@RequestParam String botID, HttpServletResponse response,HttpSession session) {
		  if(isloggedIn(session) == true) {
			  response.addHeader("Access-Control-Allow-Origin", "*");
		      response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
		      response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
		      response.addHeader("Access-Control-Max-Age", "1728000"); 
				String username = (String) session.getAttribute("username");		    	
				TradingBot bot = botManager.getBot(botID);
				try {
					if(bot != null && bot.getLinkedUsername().equals(username)) {
						return bot.getCurrentStateInfoJson();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					return null;
				}
		  }
		  response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		  return null;
		}
	
	
	/**
	 * Show bot view page
	 *
	 * @param botID the bot ID
	 * @param session the session
	 * @return the model and view
	 */
	@RequestMapping(value = "/viewBot")
	public ModelAndView showBotView(@RequestParam String botID,HttpSession session) {
		if(isloggedIn(session)==true) {
			String username = (String) session.getAttribute("username");
			TradingBot bot = botManager.getBot(botID);
			if(bot != null && bot.getLinkedUsername().equals(username)) {
				ModelAndView mv = new ModelAndView();
				mv.addObject("bot", bot);
				mv.setViewName("BotView/botView");
				return mv;
			}
			
		}		
		return new ModelAndView("forward:/404");
	}
	   


	/**
	 * Checks if user is logged in.
	 *
	 * @param session the session
	 * @return true, if is logged in
	 */
	private boolean isloggedIn(HttpSession session) {
		if(session.getAttribute("username")==null) {
			return false;
		}
		return true;
	}
	
	public class GeneralBotInfo {
		private String name;
		private String ID;
		private String date;
		private String currency;
		
		
		public GeneralBotInfo() {
			
		}
		public GeneralBotInfo(String name,String ID, String date, String currency) {
			this.name = name;
			this.ID = ID;
			this.date = date;
			this.currency = currency;
		}
		public String getname() {
			return name;
		}
		public void setname(String name) {
			this.name = name;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public String getCurrency() {
			return currency;
		}
		public void setCurrency(String currency) {
			this.currency = currency;
		}
		public String getID() {
			return ID;
		}
		public void setID(String iD) {
			ID = iD;
		}
	}
	
	


	
	
}
