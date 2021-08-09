package core.unit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;

import core.tradingsystem.currency.CandleDataHandler;
import core.tradingsystem.tradingbot.TradingBot;
import core.tradingsystem.tradingbot.TradingBotManager;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TradingBotManagerTests {

	TradingBotManager botManager;
	CandleDataHandler dataHandler;
	TradingBot testBot;
	
	public TradingBotManagerTests() {
		dataHandler = Mockito.mock(CandleDataHandler.class);
		botManager = new TradingBotManager(dataHandler);
		testBot = new TradingBot("asim", "TestBotID", "testBot", null, null, false, 0, 0);
		assertEquals(null, botManager.getBot("TestBotID"));
		botManager.AddNewTradingBot(testBot,"asim");
	}
	
	
	@Test
	@Order(1)
	public void shouldFindTradingBot() {
		assertEquals(testBot, botManager.getBot("TestBotID"));
	}

	@Test
	@Order(2)
	public void shouldReturnTradingBot() {
		List<TradingBot> bots = botManager.findTradingBotLinkedToUser("asim");
		assertEquals(1,bots.size());
		assertEquals(true,bots.contains(testBot));
	}
	
	@Test
	@Order(3)
	public void shouldDeleteTradingBot() {
		assertEquals(testBot, botManager.getBot("TestBotID"));
		botManager.deleteTradingBot("TestBotID");
		assertEquals(null, botManager.getBot("TestBotID"));
	}

	
	
}
