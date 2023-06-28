package PageRepo;

import java.util.Arrays;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import GenericFile.Resources_Utility;
import GenericFile.common_Functions;


public class Facebooklogin extends common_Functions {

//	public String parent=null;
	public static WebDriver driver = null;
	@Test(enabled = true, priority = 1, groups = {"Common"})
	public void Facebook_login() throws Throwable {
		try {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--incognito","--start-maximized");
			options.addArguments(Arrays.asList("--incognito","--start-maximized"));
		    options.addArguments("--remote-allow-origins=*");
		    driver = new ChromeDriver(options);
			driver.get("https://www.taxmann.com/auth/login");
			driver.manage().window().maximize();

			System.out.println('\n'+ "======== Facebook login Testing Started ========");
		//	parent=driver.getCurrentUrl();

			click(Resources_Utility.xpath("facebook_click"), driver);
			sleep(2000);
			window_Switch(1, tabs, driver);
			sleep(2000);

			type(Resources_Utility.xpath("facebook_email"), Resources_Utility.config("facebook_email"), driver);
			sleep(2000);

			type(Resources_Utility.xpath("facebook_password"), Resources_Utility.config("facebook_password"), driver);
			sleep(2000);

			click(Resources_Utility.xpath("facebook_login"), driver);	
			sleep(5000);
			//window_Switch(1, tabs, driver);
		//	driver.navigate().refresh();
			click(Resources_Utility.xpath("skip1"), driver);
			sleep(5000);
			
			window_Switch(0, tabs, driver);
			driver.navigate().refresh();
			window_Switch(1, tabs, driver);
			click(Resources_Utility.xpath("No_thanks1"), driver);	
			sleep(2000);
			System.out.println(driver.getCurrentUrl());
			Assert.assertEquals(driver.getCurrentUrl(),"https://www.taxmann.com/research");
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
			Assert.fail();
		}
	}}
