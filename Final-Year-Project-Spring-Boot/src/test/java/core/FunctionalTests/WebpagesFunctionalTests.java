package core.FunctionalTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import core.controllers.BotInfoController;
import core.model.DataBaseConnect;
import core.tradingsystem.currency.CandleDataHandler;
import core.tradingsystem.tradingbot.TradingBotManager;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebpagesFunctionalTests {

	
	@LocalServerPort
	private int port;
	
	@MockBean
	private DataBaseConnect databaseCon;
	
	@MockBean
	private TradingBotManager botManager;
	
	@MockBean
	private CandleDataHandler dataHandler;
	
	@MockBean
	private BotInfoController infoController;
	
	private WebDriver driver;

	
	public WebpagesFunctionalTests() {
		System.setProperty("webdriver.chrome.driver", "./src/test/java/seleniumDrivers/chromedriver.exe");
	}
	
	
	@Test
	//TEST ID = 23
	public void testwebpages() {
		driver = new ChromeDriver();
		driver.get("http://localhost:"+port+"/home");
		assertEquals("Home", driver.getTitle());
		driver.get("http://localhost:"+port+"/info");
		assertEquals("Help", driver.getTitle());
		driver.get("http://localhost:"+port+"/EmaCrossoverGuide");
		assertEquals("Ema Crossover help", driver.getTitle());
		driver.get("http://localhost:"+port+"/StochasticAndEmaGuide");
		assertEquals("Stochastic and Ema Help", driver.getTitle());
		driver.get("http://localhost:"+port+"/StochasticAndBollingerGuide");
		assertEquals("Stochastic and Bollinger Help", driver.getTitle());
		driver.close();
	}
	
}
