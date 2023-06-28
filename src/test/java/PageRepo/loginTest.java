package PageRepo;

import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import GenericFile.Resources_Utility;
import GenericFile.selenium_utility;
import taxmann.com.Phase1.AppTest;

public class loginTest extends AppTest {

	@Test(enabled = true, priority = 1, groups = {"Common"})
	public void url_Test() {
		
		try {

			current_URl = driver.getCurrentUrl();
			actual_url = "https://www.taxmann.com/research";

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.urlToBe(actual_url));

			selenium_utility.verifyEquals(current_URl, actual_url, driver);
			System.out.println("-- Logged in successfully!!!");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(enabled = true, priority = 2, groups = {"Common"})
	public void skip() throws Throwable {
		
		try {
			click(Resources_Utility.xpath("skip_Btn"), driver);
			sleep(1000);
			click(Resources_Utility.xpath("allow_Btn"), driver);
			sleep(1000);
			if (element_Displayed(Resources_Utility.xpath("skip"), "Again Skip Button", driver)) {
				do {
					click(Resources_Utility.xpath("skip"), driver);
				} while (element_Displayed(Resources_Utility.xpath("skip"), "Again Skip Button", driver));
			}
			System.out.println("-- Skipped Successfully!!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}