package PageRepo;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import GenericFile.Resources_Utility;
import taxmann.com.Phase1.AppTest;

public class IncognitoMode_Check extends AppTest {

	private static String driverpath = System.getProperty("user.dir") + "\\driver\\";
	public String storyName = null;
	public String main_Window;

	@Test(enabled = true, priority = 1, groups = {"Research"})
	public void normalMode_CaseLawSelection() throws Throwable {
		try {
			System.out.println('\n' + "====== Normal Mode Story Reading Testing Started ======");

			main_Window = driver.getWindowHandle();
			System.out.println(main_Window);

			Case_Law_Selection(Resources_Utility.xpath("caselaw_StoryHeading"), driver);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	@Test(enabled = true, priority = 2, groups = {"Research"})
	public void browser_Selection() throws Throwable {
		try {
			String browserName = "Incognito_Chrome";
			// browserName = "Firefox_Browser";
			switch (browserName) {

			case "Incognito_Chrome":

				System.out.println('\n' + "====== Incognito Mode Story Reading Testing Started ======");
				System.out.println("-- Switching to Chrome Incognito Mode");
				ChromeOptions option = new ChromeOptions();
				DesiredCapabilities capabilities = new DesiredCapabilities();
				option.addArguments("incognito");
				capabilities.setCapability(ChromeOptions.CAPABILITY, option);
				System.setProperty("webdriver.chrome.driver", driverpath + "chromedriver.exe");
			//	driver = new ChromeDriver(capabilities);
				driver.manage().window().maximize();

				driver.manage().timeouts().implicitlyWait(Integer.parseInt(Resources_Utility.config("implicit.wait")),
						TimeUnit.SECONDS);
				driver.get(Resources_Utility.config("live_URL"));

				sign_up_Or_login();
				Skip(driver);

				Case_Law_Selection(Resources_Utility.xpath("secondUser_Check"), driver);
				logout(driver);
				
				// driver.switchTo().window(main_Window);
				// System.out.println(driver.getWindowHandle());
				System.out.println("====== Incognito Mode Story Reading Testing Done ======");
				break;

			case "Firefox_Browser":

				System.out.println('\n' + "====== Firefox Browser Story Reading Testing Started ======");
				System.setProperty("webdriver.gecko.driver", driverpath + "geckodriver.exe");
				driver = new FirefoxDriver();
				driver.manage().window().maximize();

				driver.manage().timeouts().implicitlyWait(Integer.parseInt(Resources_Utility.config("implicit.wait")),
						TimeUnit.SECONDS);
				driver.get(Resources_Utility.config("live_URL"));

				login_And_Skip(driver);
				Case_Law_Selection(Resources_Utility.xpath("secondUser_Check"), driver);

				logout(driver);
				// window_Switch(0, tabs, driver);
				System.out.println("====== Firefox Browser Story Reading Testing Done ======");
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void Case_Law_Selection(String storyPath, WebDriver driver) throws Throwable {
		try {
			click(Resources_Utility.xpath("incometax_btn"), driver);
			click(Resources_Utility.xpath("caselaws"), driver);
			sleep(2000);
			try {
				if (element_Displayed(Resources_Utility.xpath("skip_Btn"), "Skip Button", driver)) {
					click(Resources_Utility.xpath("skip_Btn"), driver);
				}
			} catch (Exception e) {
				System.out.println("-- Skip Button Not Shown");
			}
			explicit_Wait(Resources_Utility.xpath("see_More"), 5, "See More", driver);
			if (element_Displayed(storyPath, "Story Name", driver)) {
				storyName = getText(storyPath, driver);
				System.out.println("-- Story name - " + storyName);
				if (storyName.contains(Resources_Utility.config("secondUser_Text"))) {
					System.out.println("-- Story Reading is not allowed because - " + storyName);
				} else {
					System.out.println("-- Income Tax Case Laws Selected");
					System.out.println("-- Story name - " + storyName);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}