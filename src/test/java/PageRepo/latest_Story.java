package PageRepo;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import GenericFile.Resources_Utility;
import GenericFile.common_Functions;
import taxmann.com.Phase1.AppTest;

public class latest_Story extends AppTest {

	public static String storyName = null;
	public static String titleName = null;
	public static String pdfType = null;
	public static String parentWinHandle = null;
	public static String parentUrl = null;
	
	//@Test
	public void latestStoryTitle() throws Throwable {
		try {
			System.out.println('\n' + "======== Latest Story Title Matching Testing Started ========");
			parentUrl = driver.getCurrentUrl();
			explicit_Wait(Resources_Utility.xpath("firststory"), 5, "First Story", driver);
			storyName = getText(Resources_Utility.xpath("firststory"), driver);
			System.out.println("-- Story Heading - " + storyName);

			click(Resources_Utility.xpath("firststory"), driver);
			explicit_Wait(Resources_Utility.xpath("header_title"), 3, "Story Header", driver);
			titleName = getText(Resources_Utility.xpath("header_title"), driver);
			System.out.println("-- Story Heading on click - " + titleName);

			verifyEquals(storyName, titleName, driver);
			System.out.println("-- Story open and title matched successfully");
			System.out.println("======== Latest Story Title Matching Testing Done ========");
			sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void latestStoryPrint(WebDriver driver) throws Throwable {

		try {
			System.out.println('\n' + "======== Latest Story Print Testing Started ========");
			parentWinHandle = driver.getWindowHandle();
			if (element_Displayed(Resources_Utility.xpath("print"), "Print Option", driver)) {

				List<WebElement> printli = driver.findElements(By.xpath(Resources_Utility.xpath("print")));
				String mainpage = driver.getWindowHandle();
				sleep(2000);
				if (printli.isEmpty()) {

					click(Resources_Utility.xpath("print"), driver);
					random_Selection(Resources_Utility.xpath("printli"), driver);
					sleep(2000);

					window_Switch(1, tabs, driver);
					sleep(2000);

					switchNew(mainpage, driver);
					driver.switchTo().window(mainpage);
				} else {
					for (int i = 0; i < printli.size(); i++) {
						click(Resources_Utility.xpath("print"), driver);
						random_Selection(Resources_Utility.xpath("printli"), driver);
						sleep(2000);

						window_Switch(1, tabs, driver);
						System.out.println("-- Print click successfully");

						sleep(3000);
						switchNew(mainpage, driver);
					}
				}
			}
			System.out.println("======== Latest Story Print Testing Done ========");
			close_AllTabs(parentUrl, tabs, driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//@AfterTest
	public void navigate_To_Home() throws Throwable {
		
		close_AllTabs(current_URl, tabs, driver);
	}

}
