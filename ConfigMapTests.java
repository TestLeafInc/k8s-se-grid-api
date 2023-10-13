package grid;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class ConfigMapTests {

	@Test
	public void getConfigs()  {

		String testUrl = System.getenv("TEST_URL");
		String browser = System.getenv("BROWSER");
		String username = System.getenv("USERNAME");
		String password = System.getenv("PASSWORD");

		WebDriver driver;

		if ("chrome".equals(browser)) {
			driver = new ChromeDriver();
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
