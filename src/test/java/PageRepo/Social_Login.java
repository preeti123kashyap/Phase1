package PageRepo;

import java.util.ArrayList;
import java.util.Set;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import GenericFile.Resources_Utility;
import GenericFile.selenium_utility;

public class Social_Login extends selenium_utility {
	public static ArrayList<String> tabs;
	public static String driverpath = System.getProperty("user.dir") + "\\driver\\";
	public static WebDriver driver = null;
	
	/*
	 * verify_google_login( driver ); sleep(2000); verify_facebook_login( driver );
	 * driver.close(); }
	 */
	public void verify_google_login(WebDriver driver ) throws Throwable {
		
		click(Resources_Utility.xpath("login_btn"), driver);
		sleep(2000);
		click(Resources_Utility.xpath("goggle_sign_in"), driver); 
		win(tabs,driver);
		sleep(3000);

		type(Resources_Utility.xpath("gmail_login"),Resources_Utility.config("login_id"),driver); 
		sleep(2000); 
		click(Resources_Utility.xpath("next"),driver);
		sleep(2000);
		type(Resources_Utility.xpath("gmail_pwd"),Resources_Utility.config("login_pwd"),driver); 
		sleep(2000); 
		click(Resources_Utility.xpath("next"),driver); 
		sleep(3000); 
		driver.switchTo().window(tabs.get(0)); 
		sleep(4000);
		try
		{ 
			click(Resources_Utility.xpath("skip"), driver); 
			sleep(2000);
			click(Resources_Utility.xpath("allow_btn"), driver);
			System.out.println("skipped successfully");
		} catch (Exception e) {

			e.printStackTrace();
			// TODO: handle exception 
		}
		sleep(2000);
		click(Resources_Utility.xpath("profile1"), driver);
		sleep(2000);
		click(Resources_Utility.xpath("P-profile"), driver);
		sleep(2000);
		JavascriptExecutor j = (JavascriptExecutor) driver;
		j.executeScript("window.scrollBy(0,400)", ""); 
		System.out.println("----Google login-----");
		String email=getText(Resources_Utility.xpath("email-address"),driver);
		System.out.println(email); 
		String mobile=getText(Resources_Utility.xpath("mobile-number"),driver);
		System.out.println(mobile); 
		sleep(2000);
		click(Resources_Utility.xpath("profile1"),driver); 
		sleep(2000);
		click(Resources_Utility.xpath("logout_Btn"), driver);

	}

	public void verify_facebook_login(WebDriver driver ) throws Throwable
	{
		click(Resources_Utility.xpath("login_btn"), driver);
		sleep(2000);
		click(Resources_Utility.xpath("facebook_sign_in"), driver);
		sleep(2000);
		win(tabs,driver);

		sleep(3000);

		type(Resources_Utility.xpath("facebook_login"),Resources_Utility.config("login_id"),driver);
		sleep(2000);
		type(Resources_Utility.xpath("facebook_pwd"),Resources_Utility.config("login_pwd"),driver);
		sleep(2000);
		click(Resources_Utility.xpath("login"), driver);
		sleep(3000);
		driver.switchTo().window(tabs.get(0));
		sleep(2000);

		driver.navigate().refresh();
		sleep(2000);
		click(Resources_Utility.xpath("profile1"), driver);
		sleep(1000);
		click(Resources_Utility.xpath("P-profile"), driver);
		sleep(3000);
		click(Resources_Utility.xpath("personal_info"), driver);
		sleep(2000);
		JavascriptExecutor j = (JavascriptExecutor) driver;
		j.executeScript("window.scrollBy(0,400)", "");
		System.out.println("----facebook login-----");
		String email=getText(Resources_Utility.xpath("email-address"),driver);
		System.out.println(email);
		String mobile=getText(Resources_Utility.xpath("mobile-number"),driver);
		System.out.println(mobile);
		sleep(4000);
		click(Resources_Utility.xpath("profile1"), driver);
		sleep(2000);
		click(Resources_Utility.xpath("logout_Btn"), driver);

	}
	public static void win(ArrayList<String> tabs2,WebDriver driver) throws Throwable
	{
		Set<String> allWindows=driver.getWindowHandles(); 
		tabs=new ArrayList<>(allWindows); 
		driver.switchTo().window(tabs.get(0));
		driver.switchTo().window(tabs.get(1)); 
		sleep(3000);
	}}