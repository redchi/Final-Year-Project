package core.FunctionalTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
public class RegisterFunctionalTest {

	@LocalServerPort
	private int port;
	
	@MockBean
	private DataBaseConnect databaseCon;
	
	@MockBean
	private TradingBotManager botManager;
	
	@MockBean
	private CandleDataHandler dataHandler;
	
	
	public RegisterFunctionalTest() {
		System.setProperty("webdriver.chrome.driver", "./src/test/java/seleniumDrivers/chromedriver.exe");
	}
	
	@PostConstruct 
    public void initMock(){
		Mockito.when(databaseCon.checkEmailExists("asim1289@gmail.com")).thenReturn(true);
		Mockito.when(databaseCon.checkUsernameExists("asim1289")).thenReturn(true);
    } 
	
	
	@Test
	//TEST ID = 8
	public void shouldRegister() {
		WebDriver driver = new ChromeDriver();
		driver.get("http://localhost:"+port+"/register");
		driver.findElement(By.id("username")).sendKeys("test1");
		driver.findElement(By.id("email")).sendKeys("test1@gmail.com");
		driver.findElement(By.id("password")).sendKeys("LikLikLik6");
		driver.findElement(By.id("confirmPassword")).sendKeys("LikLikLik6");
		driver.findElement(By.id("submit")).click();
		ArgumentCaptor<User> arg = ArgumentCaptor.forClass(User.class);
		Mockito.verify(databaseCon).addNewUser(arg.capture());
		User user = arg.getValue();
		assertEquals("test1",user.getUsername());
		assertEquals("test1@gmail.com",user.getEmail());
		assertEquals(PasswordHasher.get_SHA_512_SecurePassword("LikLikLik6"),
				user.getPassword());
		String title = driver.getTitle();
		assertEquals("Home", title);
		driver.close();
	}
	
	
	@Test
	//TEST ID = 9
	public void shouldNotRegisterInvalidEmail() {
		WebDriver driver = new ChromeDriver();
		driver.get("http://localhost:"+port+"/register");
		driver.findElement(By.id("username")).sendKeys("asim1289");
		driver.findElement(By.id("email")).sendKeys("test1@gmail.com");
		driver.findElement(By.id("password")).sendKeys("LikLikLik6");
		driver.findElement(By.id("confirmPassword")).sendKeys("LikLikLik6");
		driver.findElement(By.id("submit")).click();
		String html = driver.getPageSource();
		assertEquals(true,html.contains("Something went wrong"));
		

		driver.close();
	}
	
	@Test
	//TEST ID = 10
	public void shouldNotRegisterInvalidUsername() {
		WebDriver driver = new ChromeDriver();
		driver.get("http://localhost:"+port+"/register");
		driver.findElement(By.id("username")).sendKeys("123123123a");
		driver.findElement(By.id("email")).sendKeys("asim1289@gmail.com");
		driver.findElement(By.id("password")).sendKeys("LikLikLik6");
		driver.findElement(By.id("confirmPassword")).sendKeys("LikLikLik6");
		driver.findElement(By.id("submit")).click();
		String html = driver.getPageSource();
		assertEquals(true,html.contains("Something went wrong"));
		driver.close();

	}
	@Test
	//TEST ID = 11
	public void shouldNotRegisterInvalidPassword() {
		WebDriver driver = new ChromeDriver();
		driver.get("http://localhost:"+port+"/register");
		driver.findElement(By.id("username")).sendKeys("asim1289");
		driver.findElement(By.id("email")).sendKeys("test1@gmail.com");
		driver.findElement(By.id("password")).sendKeys("LikL");
		driver.findElement(By.id("confirmPassword")).sendKeys("LikL");
		driver.findElement(By.id("submit")).click();
		String html = driver.getPageSource();
		assertEquals(true,html.contains("Something went wrong"));
		driver.close();

	}

}
