package GenericFile;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

//import com.relevantcodes.extentreports.LogStatus;

public class selenium_utility{

	public static String screenshotPath;
	public static String screenshotName;

	// Sleep comment
	public static void sleep(int nenoSecond) throws Throwable {

		Thread.sleep(nenoSecond);
	}

	// Explicit wait for element visibility
	public static void explicit_Wait(String path, int i, String pathName, WebDriver driver) {

		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(i));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));
		}catch (Exception e) {
			System.out.println("----- WAITED FOR "+i+" SECONDS FOR THE VISIBILITY OF '"+pathName+"', BUT NOT FOUND -----");
		}
	}

	// Explicit wait for the element to  be clickable
	public static void wait_Tobe_Clickable(String path, int seconds, String pathName, WebDriver driver) {

		try {
			WebElement elementClick =new WebDriverWait(driver, Duration.ofSeconds(seconds)).until(ExpectedConditions.elementToBeClickable(By.xpath(path)));
			Actions action =new Actions(driver);
			action.moveToElement(elementClick).click().build().perform();
		}catch (Exception e) {
			System.out.println("----- WAITED FOR "+seconds+" SECONDS FOR THE VISIBILITY OF '"+pathName+"' TO BECOME CLICKABLE, BUT NOT FOUND -----");
		}
	}


	// Page down button
	public static void pagedownBTN(String path, WebDriver driver) {

		try {
			WebElement popup = driver.findElement(By.xpath(path));
			Actions builder = new Actions(driver);
			Action scroll = builder.moveToElement(popup).sendKeys(Keys.PAGE_DOWN).build();
			scroll.perform();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Page up button
	public void pageupBTN(String path, WebDriver driver) {

		try {
			WebElement popup = driver.findElement(By.xpath(path));
			Actions builder = new Actions(driver);
			Action scroll = builder.moveToElement(popup).sendKeys(Keys.PAGE_UP).build();
			scroll.perform();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Select dropdown
	public void select(WebElement ele, String value) {
		try {
			Select set = new Select(ele);

			set.selectByValue(value);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	// verify assert with logs and screen shot. int.
	public static void verifyEquals(int actual, int expected, WebDriver driver) throws IOException {

		try {
			Assert.assertEquals(actual, expected);

		} catch (Throwable t) {

			selenium_utility.captureScreenshot(driver);
			// ReportNG
			Reporter.log("<br>" + "Verification failure : " + t.getMessage() + "<br>");
			Reporter.log("<a target=\"_blank\" href=" + selenium_utility.screenshotName + "><img src="
					+ selenium_utility.screenshotName + " height=200 width=200></img></a>");
			Reporter.log("<br>");
			Reporter.log("<br>");
			// Extent Reports
			// test.log(LogStatus.FAIL, " Verification failed with exception : " +
			// t.getMessage());
			// test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));
		}
	}

	// verify assert with logs and screen shot.
	public static void verifyEquals(String actual, String expected, WebDriver driver) throws IOException {

		try {
			Assert.assertEquals(actual, expected);	
			System.out.println("('"+actual+"') is Equal to ('"+expected+"')");
			System.out.println("-- Hence Both Verified");
		} catch (Throwable t) {

			if(actual.contains(expected)) {
				System.out.println("-- Results Partially Macthed!!!!");
				System.out.println("('"+actual+"') is Partially Equal to ('"+expected+"')");
			}
			else if(expected.contains(actual)) {
				System.out.println("-- Results Partially Macthed!!!!");
				System.out.println("('"+expected+"') is Partially Equal to ('"+actual+"')");
			}
			else {
				System.out.println("('"+actual+"') is Not Equal to ('"+expected+"')");
				selenium_utility.captureScreenshot(driver);
				// ReportNG
				Reporter.log("<br>" + "Verification failure : " + t.getMessage() + "<br>");
				Reporter.log("<a target=\"_blank\" href=" + selenium_utility.screenshotName + "><img src="
						+ selenium_utility.screenshotName + " height=200 width=200></img></a>");
				Reporter.log("<br>");
				Reporter.log("<br>");
			}
		}
	}

	// Take Screen shot.
	public static void captureScreenshot(WebDriver driver) throws IOException {

		try {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

			Date d = new Date();
			screenshotName = d.toString().replace(":", "_").replace(" ", "_") + ".jpg";

			FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "\\test-output\\" + screenshotName));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	// click method
	public static void click(String path, WebDriver driver) throws Throwable {
		try {
			driver.findElement(By.xpath(path)).click();
			sleep(500);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Check Element Visibility
	public static boolean element_Displayed(String path, String element_Name,  WebDriver driver) throws Throwable {
		boolean value=false;

		for(int i=0; i<2; i++) {
			try {
				value = driver.findElement(By.xpath(path)).isDisplayed();
			} catch (Exception e) { 
			}
		}
		if(value) {
			System.out.println("-- "+element_Name+" is displayed--"); 
		}else {
			System.out.println("-- "+element_Name+" is not displayed--"); 
		}
		return value;
	}

	// Check Element In-Visibility
	public static boolean element_NotDisplayed(String path, String element_Name,  WebDriver driver) throws Throwable {
		boolean value = false;
		try {
			value = driver.findElement(By.xpath(path)).isDisplayed();
			value=!value;
			if(value) {
				System.out.println("-- "+element_Name+" is Not displayed--");}
		} catch (Exception e) {
			System.out.println("-- "+element_Name+" may be displayed--");
		}
		return value;
	}

	// type method
	public static void type(String path, String value, WebDriver driver) {

		try {
			driver.findElement(By.xpath(path)).sendKeys(value);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	// type2 method
	public static void type_Int(String path, int i, WebDriver driver) {

		try {
			String value = Integer.toString(i);
			driver.findElement(By.xpath(path)).sendKeys(value);	
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	// clear text method
	public static void clear_text(String path, WebDriver driver) {
		try {
			driver.findElement(By.xpath(path)).clear();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Get font size
	@SuppressWarnings("resource")
	public static int get_FontSize(String path, WebDriver driver) {

		String value=null;
		int value1=0;
		try {
			value = driver.findElement(By.xpath(path)).getCssValue("font-size");
			Scanner in = new Scanner(value).useDelimiter("[^0-9]+");
			value1 = in.nextInt();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return value1;
	}

	// get text value
	public static String getText(String path, WebDriver driver) {

		String value=null;
		try {
			value = driver.findElement(By.xpath(path)).getText();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	// get title value
	public static String getTitle(String path, WebDriver driver) {

		String value=null;
		try {
			value = driver.findElement(By.xpath(path)).getAttribute("title");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	// Mouse over
	public static void mousehover(String path, WebDriver driver) {
		try {
			WebElement element = driver.findElement(By.xpath(path));
			Actions actions = new Actions(driver);
			actions.moveToElement(element).perform();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	// esc key press
	public void escdw(WebDriver driver) {
		try {
			Actions actions = new Actions(driver);
			actions.sendKeys(Keys.ESCAPE).build().perform();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	// enter key press
	public static void enterDw(WebDriver driver) throws Throwable {
		try {
			Actions actions = new Actions(driver);
			actions.sendKeys(Keys.ENTER).build().perform();
			sleep(1000);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	// is Element Present?
	public static boolean isPresent(String path, WebDriver driver) {
		boolean element_Present=false;
		try {
			element_Present = driver.findElement(By.xpath(path)).isDisplayed();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return element_Present;
	}

	// Switch windows
	public static void switchNew(String homepage, WebDriver driver) {

		//String mainpage = driver.getWindowHandle();
		try {
			ArrayList<String> newTb = new ArrayList<String>(driver.getWindowHandles());

			System.out.println("array list count" + newTb.size());
			System.out.println(newTb); 
			String child = newTb.get(0);

			String last = newTb.get(1);
			driver.switchTo().window(last);

			System.out.println(driver.getTitle());
			String currenturl = driver.getTitle();

			try {
				verifyEquals(currenturl, "Untitled Document", driver);
				System.out.println("-- Print page verified successfully");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			driver.switchTo().window(child);
			driver.close();
			driver.switchTo().window(homepage);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<String> tabs;
	// Switch window
	public  String switch_window_URl(WebDriver driver) {
		String current_URl=null;

		try {
			Set<String> allWindows = driver.getWindowHandles();
			tabs = new ArrayList<>(allWindows);
			driver.switchTo().window(tabs.get(1));
			current_URl=driver.getCurrentUrl();
			current_URl = current_URl.substring(0, current_URl.indexOf("?"));

			System.out.println("-- This is URL of Currecnt page -- " +current_URl);
			System.out.println("-- This is title of Currecnt page -- " +driver.getTitle());
			escdw(driver);
			driver.close();
			driver.switchTo().window(tabs.get(0));
		}catch (Exception e) {
			e.printStackTrace();
		}
		return current_URl;
	}

	//verify Page Empty
	public void pageEmpty(String path, String pagename, WebDriver driver) throws IOException
	{
		System.out.println("-- '"+pagename+"' Page content Verification");
		try {
			Boolean value = getText(Resources_Utility.xpath(path),driver).isEmpty();
			if(value)
			{
				System.out.println("-- No Data found on "+pagename+"!!!");
			}
			else
			{
				System.out.println("-- Data found on "+pagename+"!!!");
				captureScreenshot(driver);
			}
		}catch (NoSuchElementException e) {
			System.out.println("-- No Data found on "+pagename+"!!!");
			captureScreenshot(driver);
		}
	}
}