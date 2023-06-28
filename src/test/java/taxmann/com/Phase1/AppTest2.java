package taxmann.com.Phase1;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import GenericFile.*;

public class AppTest2 extends common_Functions {
	private static String driverpath = System.getProperty("user.dir") + "\\driver\\";
	public static WebDriver driver = null;
	public static String broswer;
	public String current_URl;
	public static String actual_URl = null;
	public static String message = null;
	public static String email_Id = null;
	public String actual_url;
	Random rand = new Random();

	@SuppressWarnings("deprecation")
	@BeforeSuite (groups = {"Common"})
	public void startup() {

		try {
			if (Resources_Utility.config("browser").equals("firefox")) {

				System.setProperty("webdriver.gecko.driver", "gecko.exe");
				driver = new FirefoxDriver();

			} else if (Resources_Utility.config("browser").equals("chrome")) {

				System.setProperty("webdriver.chrome.driver", driverpath + "chromedriver.exe");
				driver = new ChromeDriver();

			} else if (Resources_Utility.config("browser").equals("ie")) {

				System.setProperty("webdriver.ie.driver", driverpath + "IEDriverServer.exe");
				driver = new InternetExplorerDriver();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		driver.manage().window().maximize();

		driver.manage().timeouts().implicitlyWait(Integer.parseInt(Resources_Utility.config("implicit.wait")),
				TimeUnit.SECONDS);
		driver.get(Resources_Utility.config("live_URL"));
		actual_url = driver.getCurrentUrl();
	}

	@BeforeTest (groups = {"Common"})
	public void sign_up_Or_login() throws Throwable {
		try {
			email_Id = "TestUser" + rand.nextInt(1000) + "@gmail.com";
			click(Resources_Utility.xpath("login_btn"), driver);
			// sign_up(email_Id, driver);
			login(email_Id, driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterClass (groups = {"Common"})
	public void navigate_To_Home() throws Throwable {
		current_URl = Resources_Utility.config("live_URL");
		close_AllTabs(current_URl, tabs, driver);
	}

	@AfterSuite (groups = {"Common"})
	public void logout() throws Throwable {
		System.out.println('\n' + "============== LogOut Started ==============");

		try {
			if (element_Displayed(Resources_Utility.xpath("profile_Btn"), "profile_Btn", driver)) {
			}
		} catch (Exception e) {
			click(Resources_Utility.xpath("taxmann_Logo"), driver);
		}
		click(Resources_Utility.xpath("profile_Btn"), driver);
		click(Resources_Utility.xpath("logout_Btn"), driver);
		sleep(5000);
		System.out.println("Logout successfully");
		driver.quit();
	}

}