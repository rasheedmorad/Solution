package Test;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Test2 extends Test1 {

	String weather;
	StringBuilder content;



	// api test that receive the weather forecast at zip code: 20852 in USA
	@Test
	public void apiTest() {

		HttpURLConnection con = null;

		try {
			// api url for weather
			URL myurl = new URL(
					"https://samples.openweathermap.org/data/2.5/forecast/daily?zip=20852&appid=b6907d289e10d714a6e88b30761fae22");
			// open connection to the weather url
			con = (HttpURLConnection) myurl.openConnection();
			// send GET request
			con.setRequestMethod("GET");

			// read the data from the page
			try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {

				String line;
				content = new StringBuilder();

				while ((line = in.readLine()) != null) {
					content.append(line);
					// system-dependent line separator string
					content.append(System.lineSeparator());
				}
			}

			System.out.println(content.toString());
			weather=wetherTestGui("20852");
			
			
			
			String deg = content.substring(content.indexOf("day"), content.indexOf("min"));
			deg=deg.substring(deg.indexOf(":"),deg.indexOf(",")).split(":")[1];
			Assert.assertTrue(Float.parseFloat(weather.split("°")[0]) / Float.parseFloat(deg) < 0.10,"Validating that the results in range 10%");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			con.disconnect();
		}

	}

//method that give the weather from weather.com for zip code 20852
	public String  wetherTestGui(String zipCode) {
		// open weather.com
		Assert.assertTrue(openSiteByChromDriver("https://www.weather.com"));
		return getWeatherForecast(zipCode);
	}

	public String getWeatherForecast(String zipCode) {
		String temp = null, pharse = null;
		try {

			TimeUnit.SECONDS.sleep(15);
			// search 20852
			driver.findElement(By.xpath("//input[@aria-label=\"Location Search\"]")).sendKeys(zipCode);
			TimeUnit.SECONDS.sleep(4);
			// enter to the weather page of 20852
			driver.findElement(By.cssSelector("a[class=\"styles__item__3sdr8 styles__selected__SEH0e\"]")).click();
			TimeUnit.SECONDS.sleep(10);
			// get the weather
			temp = driver.findElement(By.cssSelector("div[class=today_nowcard-temp]")).getText();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return temp;

	}
}
