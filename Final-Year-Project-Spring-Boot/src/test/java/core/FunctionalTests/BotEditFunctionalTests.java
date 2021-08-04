package core.FunctionalTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import core.controllers.BotInfoController;
import core.model.DataBaseConnect;
import core.model.PasswordHasher;
import core.model.User;
import core.tradingsystem.currency.Candle;
import core.tradingsystem.currency.CandleDataHandler;
import core.tradingsystem.currency.CurrencyPair;
import core.tradingsystem.strategy.EmaCrossover;
import core.tradingsystem.strategy.Strategy;
import core.tradingsystem.tradingbot.TradingBot;
import core.tradingsystem.tradingbot.TradingBotManager;
import strategyTest.TestCandle;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BotEditFunctionalTests{

	@LocalServerPort
	private int port;
	
	@MockBean
	private DataBaseConnect databaseCon;
	
	@SpyBean
	private TradingBotManager botManager;
	
	@MockBean
	private CandleDataHandler dataHandler;
	
	@SpyBean
	private BotInfoController infoController;
	
	private WebDriver driver;

	private TradingBot testbot;
	
	public BotEditFunctionalTests() {
		System.setProperty("webdriver.chrome.driver", "./src/test/java/seleniumDrivers/chromedriver.exe");
	}
	
	
	@PostConstruct 
    public void ini(){	
		Mockito.when(databaseCon.getUser("asim1289")).thenReturn(
				new User(
						"asim1289",
						PasswordHasher.get_SHA_512_SecurePassword("LikLikLik6"),
						"asim1289@gmail.com"
						)
				);
		
		// making mock candle data 
	 	int amount =  40;
		ArrayList<Candle> candles = new ArrayList<Candle>();
		for (int i = 0; i<amount; i++) {
			candles.add(new TestCandle(i));
		}
		Mockito.when(dataHandler.getCandleData(Mockito.eq(CurrencyPair.EUR_USD), Mockito.anyInt(),Mockito.anyBoolean(),Mockito.anyInt())).thenReturn(candles);
		Strategy strat = new EmaCrossover(20, 40, 5);
		testbot = new TradingBot("asim1289", "123", "testbot1", CurrencyPair.EUR_USD, strat, false, 10, 10);
		botManager.AddNewTradingBot(testbot, "asim1289");
	}
	
	
	@Test
	//TEST ID = 20
	public void viewBot() throws Exception{
		driver = new ChromeDriver();
		driver.get("http://localhost:"+port+"/login");
		driver.findElement(By.id("username")).sendKeys("asim1289");
		driver.findElement(By.id("password")).sendKeys("LikLikLik6");
		driver.findElement(By.id("loginSubmit")).click();
		driver.get("http://localhost:"+port+"/allBots");
		driver.findElement(By.id(testbot.getID())).click();
		String html = driver.getPageSource();
		Thread.sleep(1500); // wait for webpage to load
		assertEquals("Trading bot view", driver.getTitle());
		assertEquals(true,html.contains(testbot.getName()));
		assertEquals(true,html.contains(testbot.getCurrency()+""));
		assertEquals(true,html.contains("Trading history"));
		assertEquals(true,html.contains("Strategy configuration"));
		// verify webpage requested info on test bot
		Mockito.verify(infoController).getBotState(Mockito.eq(testbot.getID()), Mockito.any(), Mockito.any());
		driver.close();
	}
	@Test
	//TEST ID = 21
	public void viewPauseDeleteBot() throws Exception {
		driver = new ChromeDriver();
		driver.get("http://localhost:"+port+"/login");
		driver.findElement(By.id("username")).sendKeys("asim1289");
		driver.findElement(By.id("password")).sendKeys("LikLikLik6");
		driver.findElement(By.id("loginSubmit")).click();
		driver.get("http://localhost:"+port+"/allBots");
		driver.findElement(By.id(testbot.getID())).click();
		String html = driver.getPageSource();
		Thread.sleep(1500); // wait for webpage to load
		assertEquals(0, testbot.getInteruptType());
		driver.findElement(By.id("pauseStartButton")).click();
		Thread.sleep(500);
		assertEquals(1, testbot.getInteruptType());
		driver.findElement(By.id("pauseStartButton")).click();
		Thread.sleep(500);
		assertEquals(0, testbot.getInteruptType());
		driver.findElement(By.id("deleteBotBtn")).click();
		//check controller called right method 
		Mockito.verify(botManager).deleteTradingBot(testbot.getID());
		// check trading bot is actually deleted
		assertEquals(null,botManager.getBot(testbot.getID()));
		driver.close();
	}
	
	@Test
	//TEST ID = 22
	public void testEurUsdForexData() throws Exception  {
		Strategy strat = new EmaCrossover(20, 40, 5);
		TradingBot testbotSimulatedData = new TradingBot("asim1289", "12321", "testbot2", CurrencyPair.EUR_USD, strat, false, 10, 10);
		testbotSimulatedData.update(dataHandler);
		Mockito.verify(dataHandler).getCandleData(CurrencyPair.EUR_USD, 80, false, 0);
		TradingBot testbotLiveData = new TradingBot("asim1289", "1231231221", "testbot2", CurrencyPair.EUR_USD, strat, true, 10, 10);
		testbotLiveData.update(dataHandler);
		Mockito.verify(dataHandler).getCandleData(CurrencyPair.EUR_USD, 80, true, 0);
	}


	
}
