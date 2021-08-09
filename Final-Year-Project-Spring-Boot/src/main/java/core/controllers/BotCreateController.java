package core.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import core.model.BotCreateForm;
import core.tradingsystem.currency.CurrencyPair;
import core.tradingsystem.strategy.EmaCrossover;
import core.tradingsystem.strategy.StochasticAndBollinger;
import core.tradingsystem.strategy.StochasticAndEma;
import core.tradingsystem.strategy.Strategy;
import core.tradingsystem.tradingbot.TradingBot;
import core.tradingsystem.tradingbot.TradingBotManager;

/**
 * The Class BotCreateController - handles http requests relating to the creation of trading bot
 * displays the views, validates form submissions and creates trading bot
 */
@Controller
public class BotCreateController {

	/** The bot manager. */
	public TradingBotManager botManager;

	/**
	 * Instantiates a new bot create controller.
	 *
	 * @param botManager the bot manager
	 */
	@Autowired
	public BotCreateController(TradingBotManager botManager) {
		this.botManager = botManager;
	}
	
	/**
	 * displays general bot configuration form
	 *
	 * @param errorMsgArr the error msg arr
	 * @param session the session
	 * @return the model and view
	 */
	@RequestMapping("/createBot")
	public ModelAndView createBotGenralForm(@RequestParam(required = false, value = "errorMsgArr") ArrayList<String> errorMsgArr,
			HttpSession session) {
		if(isloggedIn(session)==true) {
			ModelAndView mv = new ModelAndView("BotCreate/generalConfig");
			if(errorMsgArr!=null) {
				mv.addObject("errorMsgArr", errorMsgArr);			
			}
			return mv;
		}
		return new ModelAndView("forward:/404");
	}
	
	/**
	 * called when general bot configuration form is submitted,
	 * request parameters are binded to formDetails object,
	 * this validates formDetails.
	 * if no errors are found user is redirected to selectStrategy
	 * if errors are found user is redirected back to submit form
	 * @param formDetails the form details binding to {@link BotCreateForm}
	 * @param redirectAttrs the redirect attrs
	 * @param session the session
	 * @return the model and view of 
	 */
	@RequestMapping("/createBot/submitGenralBotCreateForm")
	public ModelAndView submitGenralBotCreateForm(@ModelAttribute BotCreateForm formDetails,
			RedirectAttributes redirectAttrs,HttpSession session) {
		if(isloggedIn(session) == true) {
			List<String> errors = formDetails.checkForErrors();
			String username = (String) session.getAttribute("username");
			List<TradingBot> linkedBots = botManager.findTradingBotLinkedToUser(username);
			if(linkedBots!= null && linkedBots.size()>=5) {
				errors.add("You cannot create more than 5 trading bots");
			}
				if(errors.size() != 0) {
					redirectAttrs.addAttribute("errorMsgArr", errors);
					return new ModelAndView("redirect:/createBot");
				}
			 session.setAttribute("BotCreateForm", formDetails);
			 return new ModelAndView("redirect:/createBot/selectStrategy");
		}
		return new ModelAndView("forward:/404");
	}

	/**
	 * shows select strategy page
	 *
	 * @param session the session
	 * @return the model and view
	 */
	@RequestMapping("/createBot/selectStrategy")
	public ModelAndView selectStrategy(HttpSession session) {
		if(isloggedIn(session) == true && session.getAttribute("BotCreateForm") != null) {
			return new ModelAndView("BotCreate/strategySelect");
		}
		return new ModelAndView("forward:/404"); 
	}
	
	/**
	 * shows Ema crossover form
	 *
	 * @param errorMsgArr the error msg arr
	 * @param session the session
	 * @return the model and view
	 */
	@RequestMapping("/createBot/selectStrategy/EmaCrossOver")
	public ModelAndView EmaCrossOverForm(@RequestParam(required = false, value = "errorMsgArr") ArrayList<String> errorMsgArr,
			HttpSession session) {
		if(isloggedIn(session)==true && session.getAttribute("BotCreateForm") != null) {
			ModelAndView mv = new ModelAndView("BotCreate/EmaCrossoverConfig");
			if(errorMsgArr!=null) {
				mv.addObject("errorMsgArr", errorMsgArr);
			}
			return mv;
		}
		return new ModelAndView("forward:/404");
	}
	
	
	/**
	 * Ema cross over form submit.
	 * 
	 * validates emas, emaL and buffer
	 * if they are correct new EmaCrossover strategy is created
	 * and new trading bot is created and inserted into trading bot manager
	 *
	 * @param EmaS the ema S
	 * @param EmaL the ema L
	 * @param buffer the buffer
	 * @param session the session
	 * @param redirectAttrs the redirect attrs
	 * @return the model and view
	 */
	@RequestMapping("/createBot/EmaCrossOverFormSubmit")
	public ModelAndView EmaCrossOverFormSubmit(@RequestParam int EmaS,@RequestParam int EmaL,
			@RequestParam int buffer,HttpSession session,RedirectAttributes redirectAttrs) {
		if(isloggedIn(session) == true && session.getAttribute("BotCreateForm") != null) {
			BotCreateForm botDetails = (BotCreateForm) session.getAttribute("BotCreateForm");
			String username = (String) session.getAttribute("username");
			List<String> errors = botDetails.checkForErrors();
			if(EmaS<5 ||EmaS>150) {
				errors.add("short term Ema period has to be between 5 and 150");
			}
			if(EmaL<5 ||EmaS>200) {
				errors.add("Long term Ema period has to be between 5 and 200");
			}
			if(EmaS>=EmaL) {
				errors.add("long term Ema cannot be less than or equal to short term Ema");
			}
			if(EmaL>200) {
				errors.add("long term ema period cannot be more than 200");
			}
			if (buffer<0 || buffer>40) {
				errors.add("buffer needs to be between 0 and 40");
			}
			// will catch all errors
			if(errors.size() != 0) {
				redirectAttrs.addAttribute("errorMsgArr", errors);
				return new ModelAndView("redirect:/createBot/selectStrategy/EmaCrossOver");
			}
			boolean usesLiveData = false;
			if(botDetails.getUsesSimulatedData() == null) {
				usesLiveData = true;
			}
			
			CurrencyPair pair = CurrencyPair.valueOf(botDetails.getCurrencyPair());
			// create trading strategy
			Strategy strat = new EmaCrossover(EmaS,EmaL,buffer);
			
			String ID = UUID.randomUUID().toString().replace("-", "");
			
			TradingBot bot = new TradingBot(
					username,
					ID,
					botDetails.getName(),
					pair,
					strat,
					usesLiveData,
					botDetails.getStopLoss(),
					botDetails.getMaxNumTrades()
					);			
			botManager.AddNewTradingBot(bot, username);
			session.removeAttribute("BotCreateForm");			
			return new ModelAndView("redirect:/allBots");
		}
		return new ModelAndView("forward:/404"); 
	}
	
	/**
	 * Stochastic and ema form submit.
	 * validates parameters if they are correct 
	 * then creates new trading bot
	 *
	 * @param ema the ema
	 * @param stochastic the stochastic
	 * @param session the session
	 * @param redirectAttrs the redirect attrs
	 * @return the model and view
	 */
	@RequestMapping("/createBot/StochasticAndEmaFormSubmit")
	public ModelAndView StochasticAndEmaFormSubmit(@RequestParam int ema, @RequestParam int stochastic,
			HttpSession session,RedirectAttributes redirectAttrs) {
		if(isloggedIn(session) == true && session.getAttribute("BotCreateForm") != null) {
			BotCreateForm botDetails = (BotCreateForm) session.getAttribute("BotCreateForm");
			String username = (String) session.getAttribute("username");
			List<String> errors = botDetails.checkForErrors();
			if(ema<5 ||ema>200) {
				errors.add("Ema period has to be between 5 and 200");
			}
			if(stochastic<5 ||stochastic>200) {
				errors.add("stochastic period has to be between 5 and 200");
			}
			// will catch all errors
			if(errors.size() != 0) {
				redirectAttrs.addAttribute("errorMsgArr", errors);
				return new ModelAndView("redirect:/createBot/selectStrategy/StochasticAndEma");
			}
			boolean usesLiveData = false;
			if(botDetails.getUsesSimulatedData() == null) {
				usesLiveData = true;
			}
			
			CurrencyPair pair = CurrencyPair.valueOf(botDetails.getCurrencyPair());
			// create trading strategy
			Strategy strat = new StochasticAndEma(stochastic, ema);
			
			String ID = UUID.randomUUID().toString().replace("-", "");
			
			TradingBot bot = new TradingBot(
					username,
					ID,
					botDetails.getName(),
					pair,
					strat,
					usesLiveData,
					botDetails.getStopLoss(),
					botDetails.getMaxNumTrades()
					);			
			botManager.AddNewTradingBot(bot, username);
			session.removeAttribute("BotCreateForm");			
			return new ModelAndView("redirect:/allBots");
		}
		return new ModelAndView("forward:/404"); 
		
	}
	
	/**
	 * Show Stochastic and ema config page .
	 *
	 * @param errorMsgArr the error msg arr
	 * @param session the session
	 * @return the model and view
	 */
	@RequestMapping("/createBot/selectStrategy/StochasticAndEma")
	public ModelAndView StochasticAndEmaForm(@RequestParam(required = false, value = "errorMsgArr") ArrayList<String> errorMsgArr,
			HttpSession session) {
		if(isloggedIn(session)==true && session.getAttribute("BotCreateForm") != null) {
			ModelAndView mv = new ModelAndView("BotCreate/stochasticAndEmaConfig");
			if(errorMsgArr!=null) {
				mv.addObject("errorMsgArr", errorMsgArr);
			}
			return mv;
		}
		return new ModelAndView("forward:/404");
	}
	
	
	/**
	 * Bollinger and stochastic form submit.
	 * 
	 * validates parameters if they are correct 
	 * then creates new trading bot
	 *
	 * @param bollinger the bollinger
	 * @param stochastic the stochastic
	 * @param session the session
	 * @param redirectAttrs the redirect attrs
	 * @return the model and view
	 */
	@RequestMapping("/createBot/BollingerAndStochasticFormSubmit")
	public ModelAndView BollingerAndStochasticFormSubmit(@RequestParam int bollinger, @RequestParam int stochastic,
			HttpSession session,RedirectAttributes redirectAttrs) {
		if(isloggedIn(session) == true && session.getAttribute("BotCreateForm") != null) {
			BotCreateForm botDetails = (BotCreateForm) session.getAttribute("BotCreateForm");
			String username = (String) session.getAttribute("username");
			List<String> errors = botDetails.checkForErrors();
			if(bollinger<5 ||bollinger>200) {
				errors.add("bollinger period has to be between 5 and 200");
			}
			if(stochastic<5 ||stochastic>200) {
				errors.add("stochastic period has to be between 5 and 200");
			}
			// will catch all errors
			if(errors.size() != 0) {
				redirectAttrs.addAttribute("errorMsgArr", errors);
				return new ModelAndView("redirect:/createBot/selectStrategy/BollingerAndStochastic");
			}
			boolean usesLiveData = false;
			if(botDetails.getUsesSimulatedData() == null) {
				usesLiveData = true;
			}
			
			CurrencyPair pair = CurrencyPair.valueOf(botDetails.getCurrencyPair());
			// create trading strategy
			Strategy strat = new StochasticAndBollinger(bollinger,stochastic);		
			String ID = UUID.randomUUID().toString().replace("-", "");
			TradingBot bot = new TradingBot(
					username,
					ID,
					botDetails.getName(),
					pair,
					strat,
					usesLiveData,
					botDetails.getStopLoss(),
					botDetails.getMaxNumTrades()
					);			
			botManager.AddNewTradingBot(bot, username);
			session.removeAttribute("BotCreateForm");			
			return new ModelAndView("redirect:/allBots");
		}
		return new ModelAndView("forward:/404"); 
	}
	
	/**
	 * Show Bollinger and stochastic config Page.
	 *
	 * @param errorMsgArr the error msg arr
	 * @param session the session
	 * @return the model and view
	 */
	@RequestMapping("/createBot/selectStrategy/BollingerAndStochastic")
	public ModelAndView BollingerAndStochasticForm(@RequestParam(required = false, value = "errorMsgArr") ArrayList<String> errorMsgArr,
			HttpSession session) {
		if(isloggedIn(session)==true && session.getAttribute("BotCreateForm") != null) {
			ModelAndView mv = new ModelAndView("BotCreate/bollingerAndStochasticConfig");
			if(errorMsgArr!=null) {
				mv.addObject("errorMsgArr", errorMsgArr);
			}
			return mv;
		}
		return new ModelAndView("forward:/404");
	}
	
	
	/**
	 * Checks if is logged in.
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
	
	
	
	
}
