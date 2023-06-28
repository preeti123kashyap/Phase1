package PageRepo;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import GenericFile.Resources_Utility;
import GenericFile.common_Functions;
import GenericFile.selenium_utility;

public class ActCheck extends common_Functions {

	public static String driverpath = System.getProperty("user.dir") + "\\driver\\";
	public static WebDriver driver = null;

	public static void main(String[] args) throws Throwable {

		System.setProperty("webdriver.chrome.driver", driverpath + "chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();

		driver.manage().timeouts().implicitlyWait(Integer.parseInt(Resources_Utility.config("implicit.wait")),
				TimeUnit.SECONDS);
		driver.get("https://taxmann.com");

		click(Resources_Utility.xpath("login_btn"), driver);
		type(Resources_Utility.xpath("email_input"), Resources_Utility.config("id"), driver);
		type(Resources_Utility.xpath("pwd_input"), Resources_Utility.config("pwd"), driver);
		click(Resources_Utility.xpath("sign_btn"), driver);
		sleep(5000);

		try {
			click(Resources_Utility.xpath("skip"), driver);
			click(Resources_Utility.xpath("allow_btn"), driver);
			System.out.println("skiped successfully");

		} catch (Exception e) {

			e.printStackTrace();// TODO: handle exception
		}

		sleep(2000);
		click(Resources_Utility.xpath("incometax_btn"), driver);
		click(Resources_Utility.xpath("act"), driver);
		sleep(2000);
		click(Resources_Utility.xpath("amendment_Act_Filter"), driver);
		sleep(1000);
		click(Resources_Utility.xpath("filter_Search"), driver);

		for (int i = 2017; i > 2016; i--) {
			clear_text(Resources_Utility.xpath("filter_Search"), driver);
			type_Int(Resources_Utility.xpath("filter_Search"), i, driver);
			sleep(2000);
			driver.findElement(By.xpath("//div[@class='options-list']//label[@title='" + i + "']")).click();
			sleep(2000);
			String year = driver.findElement(By.xpath("//*[@class='books recommended all-books']/div/div[2]/div/a[2]"))
					.getText();
			int y = Integer.parseInt(year);
			String record_Count = getText(Resources_Utility.xpath("record_Count"), driver);
			System.out.println("Records in this year " + record_Count);
			record_Count = record_Count.replaceAll("[^\\d]", "");
			for (int j = 1; j < 20; j++) {
				driver.findElement(By.xpath("//*[contains(@id,'resultList')]/div[1]/li[" + j + "]")).click();
				if (driver.findElement(By.xpath("//div[@class='adjustable-tabs d-flex flex-nowrap col-12 p-0']/div/div[2]/span"))
						.isDisplayed()) {
					story_Closure(Resources_Utility.xpath("story_Close_allbtns"), driver);
					sleep(4000);
				}
				try {
					Boolean test = driver
							.findElement(By.xpath("//div[contains(@class,'html-view html-view')]/ng-component/div/div"))
							.isDisplayed();

					String id = driver
							.findElement(By.xpath("//div[contains(@class,'html-view html-view')]/ng-component/div/div"))
							.getAttribute("id");

					String section_No = driver
							.findElement(By.xpath("//*[contains(@id,'resultList')]/div[1]/li[" + j + "]/div/div[1]"))
							.getText();
					System.out.println("Section No =" + section_No + " and id = " + id + "this is " + test);
				} catch (Exception e) {
					// TODO: handle exception
					selenium_utility.captureScreenshot(driver);
					// String id=driver.findElement(By.xpath("//div[contains(@class,'html-view
					// html-view')]/ng-component/div/div")).getAttribute("id");

					String section_No = driver
							.findElement(By.xpath("//*[contains(@id,'resultList')]/div[1]/li[" + j + "]/div/div[1]"))
							.getText();
					System.out.println("Section No =" + section_No + "this is false");
				}
			}
			selenium_utility.verifyEquals(i, y, driver);
			System.out.println("click on year " + y);
			sleep(1000);
		}
		click(Resources_Utility.xpath("profile_Btn"), driver);
		click(Resources_Utility.xpath("logout_Btn"), driver);
		sleep(5000);
		System.out.println("Logout successfully");
		driver.quit();
	}

}

/*
 * package PageRepo; import java.util.concurrent.TimeUnit;
 * 
 * import org.openqa.selenium.By; import org.openqa.selenium.WebDriver; import
 * org.openqa.selenium.chrome.ChromeDriver; import
 * GenericFile.Resources_Utility; import GenericFile.common_Functions; import
 * GenericFile.selenium_utility;
 * 
 * public class ActCheck extends common_Functions{
 * 
 * public static String driverpath =
 * System.getProperty("user.dir")+"\\driver\\"; public static WebDriver driver =
 * null;
 * 
 * public static void main(String[] args) throws Throwable {
 * 
 * 
 * System.setProperty("webdriver.chrome.driver",driverpath+"chromedriver.exe");
 * driver = new ChromeDriver(); driver.manage().window().maximize();
 * 
 * driver.manage().timeouts().implicitlyWait(Integer.parseInt(Resources_Utility.
 * config("implicit.wait")), TimeUnit.SECONDS);
 * driver.get("https://taxmann.com");
 * 
 * click(Resources_Utility.xpath("login_btn"), driver);
 * type(Resources_Utility.xpath("email_input"), Resources_Utility.config("id"),
 * driver); type(Resources_Utility.xpath("pwd_input"),
 * Resources_Utility.config("pwd"), driver);
 * click(Resources_Utility.xpath("sign_btn"), driver); sleep(5000);
 * 
 * try { click(Resources_Utility.xpath("skip"), driver);
 * click(Resources_Utility.xpath("allow_btn"), driver);
 * System.out.println("skiped successfully");
 * 
 * } catch (Exception e) {
 * 
 * e.printStackTrace();// TODO: handle exception }
 * 
 * sleep(2000); click(Resources_Utility.xpath("incometax_btn"),driver);
 * click(Resources_Utility.xpath("act"),driver); sleep(2000);
 * click(Resources_Utility.xpath("amendment_Act_Filter"), driver); sleep(1000);
 * click(Resources_Utility.xpath("filter_Search"), driver);
 * 
 * for(int i=2017;i>2016;i--) {
 * clear_text(Resources_Utility.xpath("filter_Search"), driver);
 * type_Int(Resources_Utility.xpath("filter_Search"), i, driver); sleep(2000);
 * driver.findElement(By.xpath("//div[@class='options-list']//label[@title='"+i+
 * "']")).click(); sleep(2000); String year = driver.findElement(By.
 * xpath("//*[@class='books recommended all-books']/div/div[2]/div/a[2]")).
 * getText(); int y = Integer.parseInt(year); String
 * record_Count=getText(Resources_Utility.xpath("record_Count"), driver);
 * System.out.println("Records in this year "+record_Count);
 * record_Count=record_Count.replaceAll("[^\\d]", ""); int record_Size =
 * Integer.parseInt(record_Count);
 * 
 * for(int j=1;j<20;j++) {
 * driver.findElement(By.xpath("//*[contains(@id,'resultList')]/div[1]/li["+j+
 * "]")).click(); if(driver.findElement(By.
 * xpath("//div[@class='adjustable-tabs d-flex flex-nowrap col-12 p-0']/div/div[2]/span"
 * )).isDisplayed()) {
 * story_Closure(Resources_Utility.xpath("story_Close_allbtns"), driver);
 * sleep(3000); } sleep(4000); try{ Boolean test = driver.findElement(By.
 * xpath("//div[contains(@class,'html-view html-view')]/ng-component/div/div")).
 * isDisplayed();
 * 
 * String id=driver.findElement(By.
 * xpath("//div[contains(@class,'html-view html-view')]/ng-component/div/div")).
 * getAttribute("id");
 * 
 * String section_No=driver.findElement(By.xpath(
 * "//*[contains(@id,'resultList')]/div[1]/li["+j+"]/div/div[1]")).getText();
 * System.out.println("Section No ="+section_No+" and id = "+id +"this is "
 * +test);
 * 
 * } catch (Exception e) { // TODO: handle exception
 * selenium_utility.captureScreenshot(driver); //String
 * id=driver.findElement(By.
 * xpath("//div[contains(@class,'html-view html-view')]/ng-component/div/div")).
 * getAttribute("id");
 * 
 * String section_No=driver.findElement(By.xpath(
 * "//*[contains(@id,'resultList')]/div[1]/li["+j+"]/div/div[1]")).getText();
 * System.out.println("Section No = "+section_No); } }
 * selenium_utility.verifyEquals(i, y, driver);
 * System.out.println("click on year "+y); sleep(1000); }
 * click(Resources_Utility.xpath("profile_Btn"), driver);
 * click(Resources_Utility.xpath("logout_Btn"), driver); sleep(5000);
 * System.out.println("Logout successfully"); driver.quit(); } }
 */