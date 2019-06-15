package Test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Test1 {

	protected WebDriver driver;
	private String MetricSite = "https://www.metric-conversions.org";

	// convert from Celsius to Celsius
	@Test
	public void converts_temperature() {
		Assert.assertTrue(openSiteByChromDriver(MetricSite));
		String res = convert_From_To("Celsius", "Fahrenheit", "100");
		Assert.assertEquals(res.trim(), "212.0000°F", "Expected the result to be : " + res);

	}

	// convert from Meters to Feet
	@Test
	public void converts_Meters() {
		Assert.assertTrue(openSiteByChromDriver(MetricSite));
		String res = convert_From_To("Meters", "Feet", "100");
		Assert.assertEquals(res.trim(), "328ft 1.007880in", "Expected the result to be: " + res);
	}

	// convert from Ounces to Grams
	@Test
	public void converts_Weight() {
		Assert.assertTrue(openSiteByChromDriver(MetricSite));
		String res = convert_From_To("Ounces", "Grams", "100");
		Assert.assertEquals(res.trim(), "2834.952g", "Expected the result to be : " + res);

	}

	// open the chrome driver and navigate to metric conversion
	public boolean openSiteByChromDriver(String site) {
		try {
			System.setProperty("webdriver.chrome.driver", "C:\\WebDev\\chromedriver.exe");
			ChromeOptions options = new ChromeOptions();

			options.addArguments("--disable-notifications");
			options.addArguments("--start-maximized");
			driver = new ChromeDriver(options);
			driver.get(site);

			TimeUnit.SECONDS.sleep(1);
		} catch (Exception e) {
			System.out.print("Failed to open the site with chrom");
			return false;
		}
		return true;
	}

	// this function convert Values
	public String convert_From_To(String from, String to, String temp) {
		String result = null;
		driver.findElement(By.cssSelector("input[id=queryFrom]")).sendKeys(from);// insert the first type
		try {
			TimeUnit.SECONDS.sleep(2);

			driver.findElement(By.cssSelector("input[id=queryTo]")).sendKeys(to);// insert the second type
			TimeUnit.SECONDS.sleep(1);
			driver.findElements(By.cssSelector("input[class=argument]")).get(0).sendKeys(temp);// insert the value that
																								// you want to convert
			driver.findElements(By.cssSelector("a[class=\"convert greenButton\"]")).get(0).click();
			result = driver.findElement(By.cssSelector("p[id=answer]")).getText().split("=")[1];// save the result

			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();

		}
		return result;

	}
}
