package core.FunctionalTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By.ById;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import core.model.DataBaseConnect;
import core.model.Mailer;
import core.model.PasswordHasher;
import core.model.PasswordTokenHandler;
import core.model.User;
import core.tradingsystem.currency.CandleDataHandler;
import core.tradingsystem.tradingbot.TradingBotManager;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PasswordResetFunctionalTests {

	
	@LocalServerPort
	private int port;
	
	@MockBean
	private DataBaseConnect databaseCon;
	
	@MockBean
	private TradingBotManager botManager;
	
	@MockBean
	private CandleDataHandler dataHandler;
	
	@SpyBean
	private PasswordTokenHandler tokenHandler;
	 
	@MockBean
	private Mailer mailer;
	
	public PasswordResetFunctionalTests() {
		System.setProperty("webdriver.chrome.driver", "./src/test/java/seleniumDrivers/chromedriver.exe");
	}
	
	@PostConstruct 
    public void initMock(){
		Mockito.when(databaseCon.checkEmailExists("asim1289@gmail.com")).thenReturn(true);
		Mockito.when(databaseCon.getUserByEmail("asim1289@gmail.com")).thenReturn(
				new User("asim1289",
						"asim1289@gmail.com",
						PasswordHasher.get_SHA_512_SecurePassword("Likliklik6"))
				);
		Mockito.when(mailer.sendPasswordResetEmail(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
	} 
	
	@Test
	//TEST ID = 4
	public void shouldResetPassword() {
		WebDriver driver = new ChromeDriver();
		driver.get("http://localhost:"+port+"/login");
		driver.findElement(By.id("forgotPassBtn")).click();
		assertEquals("Forgot password",driver.getTitle());
		driver.findElement(ById.id("email")).sendKeys("asim1289@gmail.com");
		driver.findElement(By.id("submit")).click();
		String html = driver.getPageSource();
		assertEquals(true, html.contains("enter password reset code"));
		
		ArgumentCaptor<String> arg = ArgumentCaptor.forClass(String.class);
		Mockito.verify(mailer).sendPasswordResetEmail(arg.capture(), Mockito.eq("asim1289@gmail.com"));
		String resetcode = arg.getValue();
		assertEquals(16, resetcode.length());

		driver.findElement(ById.id("code")).sendKeys(resetcode);
		driver.findElement(By.id("submit")).click();
		html = driver.getPageSource();
		assertEquals(true, html.contains("enter a new password"));
		
		String newPassword = "Likliklik7";
		String newHashedPassword = PasswordHasher.get_SHA_512_SecurePassword(newPassword);
		driver.findElement(By.id("password")).sendKeys(newPassword);
		driver.findElement(By.id("confirmPassword")).sendKeys(newPassword);
		driver.findElement(By.id("submit")).click();
		Mockito.verify(databaseCon).updateUserPassword("asim1289", newHashedPassword);
		assertEquals("login",driver.getTitle());
		driver.close();
	}
	
	@Test
	//TEST ID = 5
	public void shouldNotAcceptEmail() {
		WebDriver driver = new ChromeDriver();
		// invalid email check
		driver.get("http://localhost:"+port+"/ForgotPassword");
		driver.findElement(ById.id("email")).sendKeys("asim1289123123@gmail.com");
		driver.findElement(By.id("submit")).click();
		String html = driver.getPageSource();
		assertEquals(true, html.contains("Something went wrong"));
		driver.close();
	}
	
	@Test
	//TEST ID = 6
	public void shouldNotAcceptResetCode() {
		WebDriver driver = new ChromeDriver();
		driver.get("http://localhost:"+port+"/ForgotPassword");
		driver.findElement(ById.id("email")).sendKeys("asim1289@gmail.com");
		driver.findElement(By.id("submit")).click();
		String html = driver.getPageSource();
		assertEquals(true, html.contains("enter password reset code"));
		
		ArgumentCaptor<String> arg = ArgumentCaptor.forClass(String.class);
		Mockito.verify(mailer).sendPasswordResetEmail(arg.capture(), Mockito.eq("asim1289@gmail.com"));
		String resetcode = arg.getValue();
		String invalidResetCode = resetcode.substring(4);
		
		driver.findElement(ById.id("code")).sendKeys(invalidResetCode);
		driver.findElement(By.id("submit")).click();
		html = driver.getPageSource();
		assertEquals(true, html.contains("Something went wrong"));
		driver.close();
	}
	
	@Test
	//TEST ID = 7
	public void shouldNotAcceptNewPassword() {
		WebDriver driver = new ChromeDriver();
		driver.get("http://localhost:"+port+"/ForgotPassword");
		driver.findElement(ById.id("email")).sendKeys("asim1289@gmail.com");
		driver.findElement(By.id("submit")).click();
		String html = driver.getPageSource();
		ArgumentCaptor<String> arg = ArgumentCaptor.forClass(String.class);
		Mockito.verify(mailer).sendPasswordResetEmail(arg.capture(), Mockito.eq("asim1289@gmail.com"));
		String resetcode = arg.getValue();
		driver.findElement(ById.id("code")).sendKeys(resetcode);
		driver.findElement(By.id("submit")).click();
		html = driver.getPageSource();
		assertEquals(true, html.contains("enter a new password"));
		driver.findElement(By.id("password")).sendKeys("123");
		driver.findElement(By.id("confirmPassword")).sendKeys("345");
		driver.findElement(By.id("submit")).click();
		html = driver.getPageSource();
		assertEquals(true, html.contains("Something went wrong"));
		driver.close();
	}
	
	
}
