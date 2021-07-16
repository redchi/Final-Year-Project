package core.controllers;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import core.tradingsystem.tradingbot.TradingBot;
import core.tradingsystem.tradingbot.TradingBotManager;

@Controller
public class BotEditController {

	@Autowired
	TradingBotManager botManager;
	
	@RequestMapping("/pauseTradingBot")
	public void pauseTradingBot(@RequestParam String botID, HttpServletResponse response,HttpSession session) {
		if(isloggedIn(session) == true && botManager.checkAccessRights(botID,(String) session.getAttribute("username")) == true) {
			TradingBot bot = botManager.getBot(botID);
			if(bot.getInteruptType() == 0) { // to pause the bot, it must not already have other interrupts
				bot.setInteruptType(1);
				botManager.switchTradingBotToPausedList(botID);
			}
			else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
		}
		else {
			 response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
	
	@RequestMapping("/startTradingBot")
	public void startTradingBot(@RequestParam String botID, HttpServletResponse response,HttpSession session) {
		if(isloggedIn(session) == true && botManager.checkAccessRights(botID,(String) session.getAttribute("username")) == true) {
			TradingBot bot = botManager.getBot(botID);
			if(bot.getInteruptType() == 1) { // to start the bot, it must only have paused interrupt
				bot.setInteruptType(0);
				botManager.switchTradingBotToActiveList(botID);
	
			}
			else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
		}
		else {
			 response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}	
	
	@RequestMapping("/resetTradingCount")
	public void resetTradingCount(@RequestParam String botID, HttpServletResponse response,HttpSession session) {
		if(isloggedIn(session) == true && botManager.checkAccessRights(botID,(String) session.getAttribute("username")) == true) {
			TradingBot bot = botManager.getBot(botID);
			if(bot.getInteruptType() == 2) { // bot first needs to be interruped by max trade count
				bot.resetTradingCount();
				bot.setInteruptType(0);
				botManager.switchTradingBotToActiveList(botID);
				
			}
			else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
		}
		else {
			 response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
	
	@RequestMapping("/checkMarketOpen")
	public void checkTime(@RequestParam String botID, HttpServletResponse response,HttpSession session) {
		if(isloggedIn(session) == true && botManager.checkAccessRights(botID,(String) session.getAttribute("username")) == true) {
			TradingBot bot = botManager.getBot(botID);
			if(bot.getInteruptType() == 4) { // bot first needs to be interruped by market close interrupt
				// im checking if market is open
				DateTime gmt = new DateTime(DateTimeZone.forID("GMT"));
				int day = gmt.getDayOfWeek();
				int mins = gmt.getMinuteOfDay();
				if((day == 6 || day == 5 && mins>=1195 ||day == 7 && mins<=1205) == false) {
					bot.setInteruptType(0);
					botManager.switchTradingBotToActiveList(botID);
				}
			}
		}
	}
	

	
	@RequestMapping("/deleteTradingBot")
	public ModelAndView deleteTradingBot(@RequestParam String botID, HttpServletResponse response,HttpSession session) {
		if(isloggedIn(session) == true && botManager.checkAccessRights(botID,(String) session.getAttribute("username")) == true) {
			botManager.deleteTradingBot(botID);
			return new ModelAndView("redirect:/allBots");
		}
		return new ModelAndView("forward:/404");
	}


	private boolean isloggedIn(HttpSession session) {
		if(session.getAttribute("username")==null) {
			return false;
		}
		return true;
	}
	
}
