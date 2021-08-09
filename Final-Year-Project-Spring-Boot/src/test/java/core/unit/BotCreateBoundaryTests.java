package core.unit;

import static org.junit.jupiter.api.Assertions.*;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import core.controllers.BotCreateController;
import core.model.BotCreateForm;
import core.tradingsystem.currency.CandleDataHandler;
import core.tradingsystem.tradingbot.TradingBotManager;

class BotCreateBoundaryTests {

	// form submission parameter range boundary -> tests  min-1, min, nominal, max, max+1
	
	BotCreateController controller;
	HttpSession session;
	public BotCreateBoundaryTests() {
		controller = new BotCreateController(Mockito.mock(TradingBotManager.class));
		session = new MockHttpSession();
		session.setAttribute("username", "asim");
	}
	
	@Test
	public void shouldRejectStochasticAndBollinger() {
		BotCreateForm form = new BotCreateForm();
		form.setCurrencyPair("EUR_USD");
		form.setMaxNumTrades(20);
		form.setStopLoss(20);
		form.setUsesSimulatedData("on");
		form.setName("testbot1");
		session.setAttribute("BotCreateForm", form);
		
		int stoch =0;
		int bollinger = 0;
		
		stoch = 4;
		bollinger = 100;
		ModelAndView res = controller.BollingerAndStochasticFormSubmit(bollinger, stoch, session, Mockito.mock(RedirectAttributes.class));
		assertEquals("redirect:/createBot/selectStrategy/BollingerAndStochastic",res.getViewName());
		
		stoch = 201;
		bollinger = 100;
		 res = controller.BollingerAndStochasticFormSubmit(bollinger, stoch, session, Mockito.mock(RedirectAttributes.class));
		assertEquals("redirect:/createBot/selectStrategy/BollingerAndStochastic",res.getViewName());
		
		
		stoch = 100;
		bollinger = 4;
		 res = controller.BollingerAndStochasticFormSubmit(bollinger, stoch, session, Mockito.mock(RedirectAttributes.class));
		assertEquals("redirect:/createBot/selectStrategy/BollingerAndStochastic",res.getViewName());
		
		stoch = 100;
		bollinger = 201;
		res = controller.BollingerAndStochasticFormSubmit(bollinger, stoch, session, Mockito.mock(RedirectAttributes.class));
		assertEquals("redirect:/createBot/selectStrategy/BollingerAndStochastic",res.getViewName());
	}
	
	@Test
	public void shouldCreateStochasticAndBollinger() {
		BotCreateForm form = new BotCreateForm();
		form.setCurrencyPair("EUR_USD");
		form.setMaxNumTrades(20);
		form.setStopLoss(20);
		form.setUsesSimulatedData("on");
		form.setName("testbot1");
		session.setAttribute("BotCreateForm", form);
		
		int stoch =0;
		int bollinger = 0;
		
		stoch = 5;
		bollinger = 100;
		ModelAndView res = controller.BollingerAndStochasticFormSubmit(bollinger, stoch, session, Mockito.mock(RedirectAttributes.class));
		assertEquals("redirect:/allBots",res.getViewName());
		session.setAttribute("BotCreateForm", form);
		
		stoch = 200;
		bollinger = 100;
		 res = controller.BollingerAndStochasticFormSubmit(bollinger, stoch, session, Mockito.mock(RedirectAttributes.class));
		assertEquals("redirect:/allBots",res.getViewName());
		session.setAttribute("BotCreateForm", form);
		
		
		stoch = 100;
		bollinger = 5;
		 res = controller.BollingerAndStochasticFormSubmit(bollinger, stoch, session, Mockito.mock(RedirectAttributes.class));
		assertEquals("redirect:/allBots",res.getViewName());
		session.setAttribute("BotCreateForm", form);
		
		stoch = 100;
		bollinger = 200;
		res = controller.BollingerAndStochasticFormSubmit(bollinger, stoch, session, Mockito.mock(RedirectAttributes.class));
		assertEquals("redirect:/allBots",res.getViewName());
		session.setAttribute("BotCreateForm", form);
	}
	
	
	@Test
	public void shouldRedirectEmaCrossover() {
		BotCreateForm form = new BotCreateForm();
		form.setCurrencyPair("EUR_USD");
		form.setMaxNumTrades(20);
		form.setStopLoss(20);
		form.setUsesSimulatedData("on");
		form.setName("testbot1");
		session.setAttribute("BotCreateForm", form );
		
		int EmaS = 0;
		int EmaL = 0;
		int buffer = 0;
		
		EmaS = 4;
		EmaL = 100;
		buffer = 20;
		ModelAndView res =  controller.EmaCrossOverFormSubmit(EmaS, EmaL, buffer, session, Mockito.mock(RedirectAttributes.class));
		assertEquals("redirect:/createBot/selectStrategy/EmaCrossOver", res.getViewName());
	
		
		EmaS = 151;
		EmaL = 100;
		buffer = 20;
		res =  controller.EmaCrossOverFormSubmit(EmaS, EmaL, buffer, session, Mockito.mock(RedirectAttributes.class));
		assertEquals("redirect:/createBot/selectStrategy/EmaCrossOver", res.getViewName());
		
		
		EmaS = 75;
		EmaL = 4;
		buffer = 20;
		res =  controller.EmaCrossOverFormSubmit(EmaS, EmaL, buffer, session, Mockito.mock(RedirectAttributes.class));
		assertEquals("redirect:/createBot/selectStrategy/EmaCrossOver", res.getViewName());
		
		EmaS = 75;
		EmaL = 201;
		buffer = 20;
		res =  controller.EmaCrossOverFormSubmit(EmaS, EmaL, buffer, session, Mockito.mock(RedirectAttributes.class));
		assertEquals("redirect:/createBot/selectStrategy/EmaCrossOver", res.getViewName());
		
		EmaS = 75;
		EmaL = 100;
		buffer = -1;
		res =  controller.EmaCrossOverFormSubmit(EmaS, EmaL, buffer, session, Mockito.mock(RedirectAttributes.class));
		assertEquals("redirect:/createBot/selectStrategy/EmaCrossOver", res.getViewName());
		
		EmaS = 75;
		EmaL = 100;
		buffer = 41;
		res =  controller.EmaCrossOverFormSubmit(EmaS, EmaL, buffer, session, Mockito.mock(RedirectAttributes.class));
		assertEquals("redirect:/createBot/selectStrategy/EmaCrossOver", res.getViewName());
	
	}
	
	@Test
	public void testShouldCreateEmaCrossover() {
		BotCreateForm form = new BotCreateForm();
		form.setCurrencyPair("EUR_USD");
		form.setMaxNumTrades(20);
		form.setStopLoss(20);
		form.setUsesSimulatedData("on");
		form.setName("testbot1");
		session.setAttribute("BotCreateForm", form );
		
		int EmaS = 0;
		int EmaL = 0;
		int buffer = 0;
		
		EmaS = 5;
		EmaL = 100;
		buffer = 20;
		ModelAndView res =  controller.EmaCrossOverFormSubmit(EmaS, EmaL, buffer, session, Mockito.mock(RedirectAttributes.class));
		assertEquals("redirect:/allBots", res.getViewName());
		session.setAttribute("BotCreateForm", form );
		
		
		EmaS = 150;
		EmaL = 200;
		buffer = 20;
		res =  controller.EmaCrossOverFormSubmit(EmaS, EmaL, buffer, session, Mockito.mock(RedirectAttributes.class));
		assertEquals("redirect:/allBots", res.getViewName());
		session.setAttribute("BotCreateForm", form );

		
		EmaS = 5;
		EmaL = 6;
		buffer = 20;
		res =  controller.EmaCrossOverFormSubmit(EmaS, EmaL, buffer, session, Mockito.mock(RedirectAttributes.class));
		assertEquals("redirect:/allBots", res.getViewName());
		session.setAttribute("BotCreateForm", form );

		
		EmaS = 75;
		EmaL = 200;
		buffer = 20;
		res =  controller.EmaCrossOverFormSubmit(EmaS, EmaL, buffer, session, Mockito.mock(RedirectAttributes.class));
		assertEquals("redirect:/allBots", res.getViewName());
		session.setAttribute("BotCreateForm", form );

		
		EmaS = 75;
		EmaL = 100;
		buffer = 0;
		res =  controller.EmaCrossOverFormSubmit(EmaS, EmaL, buffer, session, Mockito.mock(RedirectAttributes.class));
		assertEquals("redirect:/allBots", res.getViewName());
		session.setAttribute("BotCreateForm", form );

		
		EmaS = 75;
		EmaL = 100;
		buffer = 40;
		res =  controller.EmaCrossOverFormSubmit(EmaS, EmaL, buffer, session, Mockito.mock(RedirectAttributes.class));
		assertEquals("redirect:/allBots", res.getViewName());
		session.setAttribute("BotCreateForm", form );

	}
	
	@Test
	public void shouldRejectStochasticAndEma() {
		BotCreateForm form = new BotCreateForm();
		form.setCurrencyPair("EUR_USD");
		form.setMaxNumTrades(20);
		form.setStopLoss(20);
		form.setUsesSimulatedData("on");
		form.setName("testbot1");
		session.setAttribute("BotCreateForm", form);
		
		int ema =0;
		int stoch = 0;
		
		ema = 4;
		stoch = 100;
		ModelAndView res = controller.StochasticAndEmaFormSubmit(ema, stoch, session, Mockito.mock(RedirectAttributes.class));
		assertEquals("redirect:/createBot/selectStrategy/StochasticAndEma",res.getViewName());
		
		ema = 201;
		stoch = 100;
		res = controller.StochasticAndEmaFormSubmit(ema, stoch, session, Mockito.mock(RedirectAttributes.class));
		assertEquals("redirect:/createBot/selectStrategy/StochasticAndEma",res.getViewName());
		
		ema = 100;
		stoch = 4;
		res = controller.StochasticAndEmaFormSubmit(ema, stoch, session, Mockito.mock(RedirectAttributes.class));
		assertEquals("redirect:/createBot/selectStrategy/StochasticAndEma",res.getViewName());
		
		ema = 100;
		stoch = 201;
		res = controller.StochasticAndEmaFormSubmit(ema, stoch, session, Mockito.mock(RedirectAttributes.class));
		assertEquals("redirect:/createBot/selectStrategy/StochasticAndEma",res.getViewName());
		
	}
	
	@Test
	public void shouldCreateStochasticAndEma() {
		BotCreateForm form = new BotCreateForm();
		form.setCurrencyPair("EUR_USD");
		form.setMaxNumTrades(20);
		form.setStopLoss(20);
		form.setUsesSimulatedData("on");
		form.setName("testbot1");
		session.setAttribute("BotCreateForm", form);
		
		int ema =0;
		int stoch = 0;
		
		ema = 5;
		stoch = 100;
		ModelAndView res = controller.StochasticAndEmaFormSubmit(ema, stoch, session, Mockito.mock(RedirectAttributes.class));
		assertEquals("redirect:/allBots",res.getViewName());
		session.setAttribute("BotCreateForm", form);

		ema = 200;
		stoch = 100;
		res = controller.StochasticAndEmaFormSubmit(ema, stoch, session, Mockito.mock(RedirectAttributes.class));
		assertEquals("redirect:/allBots",res.getViewName());
		session.setAttribute("BotCreateForm", form);

		ema = 100;
		stoch = 5;
		res = controller.StochasticAndEmaFormSubmit(ema, stoch, session, Mockito.mock(RedirectAttributes.class));
		assertEquals("redirect:/allBots",res.getViewName());
		session.setAttribute("BotCreateForm", form);

		ema = 100;
		stoch = 200;
		res = controller.StochasticAndEmaFormSubmit(ema, stoch, session, Mockito.mock(RedirectAttributes.class));
		assertEquals("redirect:/allBots",res.getViewName());
		session.setAttribute("BotCreateForm", form);
	}
	



}
