package grid;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ConfigMapTests {

	@Test
	public void getConfigs()  {

		String testUrl = System.getenv("TEST_URL");
		String browser = System.getenv("BROWSER");
		String username = System.getenv("USERNAME");
		String password = System.getenv("PASSWORD");
	        System.out.println(testUrl);
		RemoteWebDriver driver;

		if ("chrome".equals(browser)) {
			ChromeOptions chrome_options = new ChromeOptions();
			chrome_options.addArguments("--no-sandbox"); 
			chrome_options.addArguments("--disable-dev-shm-usage"); 
			chrome_options.addArguments("--disable-notifications"); 
			chrome_options.addArguments("--headless");
			remoteWebdriver.set(new RemoteWebDriver(new URL("http://10.244.0.44:4444/wd/hub"), chrome_options));
			break;
		} else if ("firefox".equals(browser)) {
			driver = new FirefoxDriver();
		} else {
			throw new IllegalArgumentException("Unsupported browser");
		}

		driver.get(testUrl);
		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.className("decorativeSubmit")).click();
		System.out.println("URL "+driver.getCurrentUrl());

	}
}
