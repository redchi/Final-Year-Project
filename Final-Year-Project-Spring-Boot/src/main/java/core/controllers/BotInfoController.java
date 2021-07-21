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


@Controller
public class BotInfoController implements InitializingBean {

	
	@Autowired
	TradingBotManager botManager;
	
	@Autowired
	CandleDataHandler dataHandler;
	
	public BotInfoController() {
		System.out.println("#123");
	}
	
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("#Bot info con after prop");
		botManager.mainTradingLoop();
		dataHandler.loadDataFromCSV();
	//	dataHandler.loadHistoricalDataFromAPI();
	//	dataHandler.forexUpdateLoop();
		loadTests();
	}
	 
	
	public void loadTests() {
		String username = "test1"; // for testing purposses only
		for(int i = 0 ; i<5; i ++) {
			String ID = UUID.randomUUID().toString().replace("-", "");
			String name = getRandomString(7);		
			int EmaS = 5 + (int)(Math.random() * 20);
			int EmaL = EmaS + (int)(Math.random() * (EmaS + 40));
			int buffer = 3 + (int)(Math.random() * 10);
		//	int pointer = 100 + (int)(Math.random() * 2000);
			
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
