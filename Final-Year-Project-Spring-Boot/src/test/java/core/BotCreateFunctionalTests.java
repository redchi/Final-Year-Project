package core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import core.model.DataBaseConnect;
import core.model.PasswordHasher;
import core.model.User;
import core.tradingsystem.currency.CandleDataHandler;
import core.tradingsystem.strategy.EmaCrossover;
import core.tradingsystem.strategy.StochasticAndBollinger;
import core.tradingsystem.strategy.StochasticAndEma;
import core.tradingsystem.strategy.Strategy;
import core.tradingsystem.tradingbot.TradingBot;
import core.tradingsystem.tradingbot.TradingBotManager;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BotCreateFunctionalTests {

	
	@LocalServerPort
	private int port;
	
	@MockBean
	private DataBaseConnect databaseCon;
	
	@SpyBean
	private TradingBotManager botManager;
	
	@MockBean
	private CandleDataHandler dataHandler;
	
	private WebDriver driver;

	public BotCreateFunctionalTests() {
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
		
	} 
	
	
	@BeforeEach
	public void login() {
		driver = new ChromeDriver();
		driver.get("http://localhost:"+port+"/login");
		driver.findElement(By.id("username")).sendKeys("asim1289");
		driver.findElement(By.id("password")).sendKeys("LikLikLik6");
		driver.findElement(By.id("loginSubmit")).click();
	}
	
	@Test
	public void shouldCreateEmaCrossoverBot() throws Exception{
		String botName = "testbot1";
		driver.get("http://localhost:"+port+"/createBot");
		driver.findElement(By.id("botName")).sendKeys(botName);
		driver.findElement(By.id("currency")).sendKeys("EUR_USD");
		driver.findElement(By.id("maxTrades")).sendKeys("20");
		driver.findElement(By.id("stopLoss")).sendKeys("20");
		driver.findElement(By.id("submit")).click();;
		assertEquals("Select Strategy", driver.getTitle());
		driver.findElement(By.id("emaCrossOverClick")).click();
		assertEquals("Ema Crossover Config", driver.getTitle());
		driver.findElement(By.id("emaL")).sendKeys("50");
		driver.findElement(By.id("emaS")).sendKeys("20");
		driver.findElement(By.id("buffer")).sendKeys("5");
		driver.findElement(By.id("submit")).click();;
		ArgumentCaptor<TradingBot> arg = ArgumentCaptor.forClass(TradingBot.class);
		Mockito.verify(botManager).AddNewTradingBot(arg.capture(), Mockito.eq("asim1289"));
		TradingBot botMade = arg.getValue();
		assertEquals(botName, botMade.getName()); // correct bot made
		Strategy strategyMade = (Strategy) FieldUtils.readField(botMade,"strategy",true);
		assertEquals(true,strategyMade instanceof EmaCrossover); // correct startegy made
		assertEquals("My Bots", driver.getTitle());
		assertEquals(true,driver.getPageSource().contains(botName));
		driver.close();
	}
	
	@Test
	public void shouldNotCreateEmaCrossoverBot() {
		String botName = "testbot1.2";
		driver.get("http://localhost:"+port+"/createBot");
		driver.findElement(By.id("botName")).sendKeys(botName);
		driver.findElement(By.id("currency")).sendKeys("EUR_USD");
		driver.findElement(By.id("maxTrades")).sendKeys("20");
		driver.findElement(By.id("stopLoss")).sendKeys("20");
		driver.findElement(By.id("submit")).click();;
		assertEquals("Select Strategy", driver.getTitle());
		driver.findElement(By.id("emaCrossOverClick")).click();
		assertEquals("Ema Crossover Config", driver.getTitle());
		driver.findElement(By.id("emaL")).sendKeys("3");
		driver.findElement(By.id("emaS")).sendKeys("20");
		driver.findElement(By.id("buffer")).sendKeys("5");
		driver.findElement(By.id("submit")).click();;
		
	}
	
	@Test
	public void shouldCreateStochasticAndEmaBot() throws Exception{
		String botName = "testbot2";
		driver.get("http://localhost:"+port+"/createBot");
		driver.findElement(By.id("botName")).sendKeys(botName);
		driver.findElement(By.id("currency")).sendKeys("EUR_USD");
		driver.findElement(By.id("maxTrades")).sendKeys("20");
		driver.findElement(By.id("stopLoss")).sendKeys("20");
		driver.findElement(By.id("submit")).click();;
		assertEquals("Select Strategy", driver.getTitle());
		driver.findElement(By.id("stochasticAndEmaClick")).click();
		assertEquals("Stochastic And Ema Config", driver.getTitle());
		driver.findElement(By.id("ema")).sendKeys("50");
		driver.findElement(By.id("stochastic")).sendKeys("20");
		driver.findElement(By.id("submit")).click();;
		ArgumentCaptor<TradingBot> arg = ArgumentCaptor.forClass(TradingBot.class);
		Mockito.verify(botManager).AddNewTradingBot(arg.capture(), Mockito.eq("asim1289"));
		TradingBot botMade = arg.getValue();
		assertEquals(botName, botMade.getName()); // correct bot made
		Strategy strategyMade = (Strategy) FieldUtils.readField(botMade,"strategy",true);
		assertEquals(true,strategyMade instanceof StochasticAndEma); // correct startegy made
		assertEquals("My Bots", driver.getTitle());
		assertEquals(true,driver.getPageSource().contains(botName));
		driver.close();
	}
	
	@Test
	public void shouldCreateStochasticAndBollingerBot() throws Exception{
		String botName = "testbot3";
		driver.get("http://localhost:"+port+"/createBot");
		driver.findElement(By.id("botName")).sendKeys(botName);
		driver.findElement(By.id("currency")).sendKeys("EUR_USD");
		driver.findElement(By.id("maxTrades")).sendKeys("20");
		driver.findElement(By.id("stopLoss")).sendKeys("20");
		driver.findElement(By.id("submit")).click();;
		assertEquals("Select Strategy", driver.getTitle());
		driver.findElement(By.id("stochasticAndBollingerClick")).click();
		assertEquals("Stochastic And Bollinger Config", driver.getTitle());
		driver.findElement(By.id("bollinger")).sendKeys("50");
		driver.findElement(By.id("stochastic")).sendKeys("20");
		driver.findElement(By.id("submit")).click();;
		ArgumentCaptor<TradingBot> arg = ArgumentCaptor.forClass(TradingBot.class);
		Mockito.verify(botManager).AddNewTradingBot(arg.capture(), Mockito.eq("asim1289"));
		TradingBot botMade = arg.getValue();
		assertEquals(botName, botMade.getName()); // correct bot made
		Strategy strategyMade = (Strategy) FieldUtils.readField(botMade,"strategy",true);
		assertEquals(true,strategyMade instanceof StochasticAndBollinger); // correct startegy made
		assertEquals("My Bots", driver.getTitle());
		assertEquals(true,driver.getPageSource().contains(botName));
		driver.close();
	}
	
	
}
