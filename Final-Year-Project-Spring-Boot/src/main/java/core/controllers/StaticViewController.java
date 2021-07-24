package core.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * The Class StaticViewController - handles static views requests
 *  these do not need any input parameters and do not communicate with model functionality
 */
@Controller
public class StaticViewController implements ErrorController {
	
	@RequestMapping(value={"", "/", "/home"})
	public String showHome() {
		return "home";
	}
	
	@RequestMapping(value = "/info")
	public String showhelp() {
		return "Help/Help";
	}
	
	@RequestMapping(value = "/EmaCrossoverGuide")
	public String showEmaCrossoverGuide() {
		return "Help/EmaCrossoverGuide";
	}
	
	@RequestMapping(value = "/StochasticAndEmaGuide")
	public String showStochasticAndEmaGuide() {
		return "Help/StocasticAndEmaGuide";
	}
	
	@RequestMapping(value = "/StochasticAndBollingerGuide")
	public String showstratStochasticAndBollingerGuide() {
		return "Help/StocasticAndBollingerGuide";
	}
	
	@RequestMapping(value = "/404")
	public String show404() {
		return "error404";
	}
	
	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/home";
	}
	
	@RequestMapping(value = "/error")
	public String exception() {
		return "error404";
	}
	
	
	
}
