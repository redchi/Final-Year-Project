package core.FunctionalTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import core.model.DataBaseConnect;
import core.model.PasswordHasher;
import core.model.User;
import core.tradingsystem.currency.CandleDataHandler;
import core.tradingsystem.tradingbot.TradingBotManager;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginFunctionalTests {

	@LocalServerPort
	private int port;
	
	@MockBean
	private DataBaseConnect databaseCon;
	
	@MockBean
	private TradingBotManager botManager;
	
	@MockBean
	private CandleDataHandler dataHandler;
	
	public LoginFunctionalTests() {
		System.setProperty("webdriver.chrome.driver", "./src/test/java/seleniumDrivers/chromedriver.exe");

	}
	
	@PostConstruct 
    public void initMock(){
		Mockito.when(databaseCon.getUser("asim1289")).thenReturn(
				new User(
						"asim1289",
						PasswordHasher.get_SHA_512_SecurePassword("LikLikLik6"),
						"asim1289@gmail.com"
						)
				);
    }

	@Test
	// TEST ID = 1
	public void shouldLogin() {
		WebDriver driver = new ChromeDriver();
		driver.get("http://localhost:"+port+"/login");
		driver.findElement(By.id("username")).sendKeys("asim1289");
		driver.findElement(By.id("password")).sendKeys("LikLikLik6");
		driver.findElement(By.id("loginSubmit")).click();
		String title = driver.getTitle();
		assertEquals("Home", title);
		driver.close();
	}
	
	@Test
	// TEST ID = 2
	public void shouldNotLogin() {
		WebDriver driver = new ChromeDriver();
		driver.get("http://localhost:"+port+"/login");
		driver.findElement(By.id("username")).sendKeys("asim1289123123");
		driver.findElement(By.id("password")).sendKeys("LikLikLik6");
		driver.findElement(By.id("loginSubmit")).click();
		String title = driver.getTitle();
		String html = driver.getPageSource();
		assertEquals("login", title);
		assertEquals(true, html.contains("Something went wrong"));
		driver.close();
	}
	
	@Test
	//TEST ID = 3
	public void shouldLogout() {
		WebDriver driver = new ChromeDriver();
		//login
		driver.get("http://localhost:"+port+"/login");
		driver.findElement(By.id("username")).sendKeys("asim1289");
		driver.findElement(By.id("password")).sendKeys("LikLikLik6");
		driver.findElement(By.id("loginSubmit")).click();
		String title = driver.getTitle();
		assertEquals("Home", title);
		// logout
		driver.get("http://localhost:"+port+"/logout");
		String html = driver.getPageSource();
		assertEquals(true, html.contains("Login"));
		driver.close();
	}
	
}
	













