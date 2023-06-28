package taxmann.com.Phase1;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class LambdaData {


	public RemoteWebDriver driver = null;
	String username = "deepak.verma";
	String accessKey = "EMpIMZenzCwJxc160EyjwFmFPqg31BmSKbTqH82jJbajDFxKrn";

	@BeforeTest
	public void setUp() throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platform", "Windows 10");
		capabilities.setCapability("browserName", "Chrome");
		capabilities.setCapability("version", "87.0"); // If this cap isn't specified, it will just get the any available one
		capabilities.setCapability("resolution","1024x768");
		capabilities.setCapability("build", "Taxmann.com");
		capabilities.setCapability("name", "Login&Logout");
		capabilities.setCapability("network", true); // To enable network logs
		capabilities.setCapability("visual", true); // To enable step by step screenshot
		capabilities.setCapability("video", true); // To enable video recording
		capabilities.setCapability("console", true); // To capture console logs

		try {       
			driver= new RemoteWebDriver(new URL("https://"+username+":"+accessKey+"@hub.lambdatest.com/wd/hub"), capabilities);            
		} catch (MalformedURLException e) {
			System.out.println("Invalid grid URL");
		}
	}

	@Test(enabled = true)
	public void testScript() throws Exception {
		try {
			driver.get("https://Taxmann.com");
			driver.findElement(By.xpath("//*[@id='header']/div/div[1]/div[3]/li/a")).click();
			driver.findElement(By.xpath("//*[@id='email']")).sendKeys("deepak.verma@taxmanntechnologies.com");
			driver.findElement(By.xpath("//*[@id='password']")).sendKeys("Deepak@123");
			driver.findElement(By.xpath("//*[@id='msform-in']/fieldset/div[2]/div[3]/button")).click();
			Thread.sleep(500);
			
			String c_url = driver.getCurrentUrl();
			String a_url = "";
			SoftAssert softAssert = new SoftAssert();
			softAssert.assertTrue(c_url.equalsIgnoreCase(a_url));
			driver.quit();					
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}


}
