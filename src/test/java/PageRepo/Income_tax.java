package PageRepo;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import GenericFile.Resources_Utility;
import taxmann.com.Phase1.AppTest;

public class Income_tax extends AppTest {
	public static ArrayList<String> tabs;
	public static String parent = null;
	public static String filter_Selected = null;
	public static String StoryName = null;
	public static String pop_up_StoryName = null;
	public static String Filter_Selected = null;

	@Test(enabled = true, priority = 1, groups = {"Research"})
	public void income_tax() throws Throwable {

		try {
			System.out.println('\n' + "====== INCOME TAX STORY TESTING STARTED ======");

			click(Resources_Utility.xpath("incometax_btn"), driver);
			click(Resources_Utility.xpath("caselaws"), driver);
			explicit_Wait(Resources_Utility.xpath("see_More"), 5, "See More", driver);
			type(Resources_Utility.xpath("internal_search_box"), Resources_Utility.config("caseLaw_search"), driver);

			enterDw(driver);
			parent = driver.getWindowHandle();
			story_Closure(Resources_Utility.xpath("story_Close_allbtns"), driver); // close all other stories

			StoryName = getText(Resources_Utility.xpath("caselaws_leftStoryHeading"), driver);
			StoryName = StoryName.trim().substring(0, 41);
			System.out.println("-- Stroy Name - " + StoryName);

			System.out.println("====== INCOME TAX STORY TESTING DONE ======");
		} catch (Exception e) {
			if (element_Displayed(Resources_Utility.xpath("secondUser_Check"), "Login Session Timer", driver)) {
				System.out.println("This Account is being used from another device at the moment!!");
			} else {
				e.printStackTrace();
			}
		}
	}

	@Test(enabled = true, priority = 2, groups = {"Research"})
	public void doubleTab_Check() throws Throwable {

		try {
			System.out.println('\n' + "====== INCOME TAX DOUBLE CLICK TESTING STARTED ======");

			double_Click(Resources_Utility.xpath("caselaws_leftStoryHeading"), driver);
			window_Switch(1, tabs, driver);
			explicit_Wait(Resources_Utility.xpath("see_More"), 5, "See More", driver);

			pop_up_StoryName = getTitle(Resources_Utility.xpath("new_Window_StoryHeading"), driver);
			pop_up_StoryName = pop_up_StoryName.trim().substring(0, 41);
			System.out.println("-- Story Name in pop-up window - " + pop_up_StoryName);

			verifyEquals(StoryName, pop_up_StoryName, driver);
			driver.close();
			driver.switchTo().window(parent);

			System.out.println("====== INCOME TAX DOUBLE CLICK TESTING DONE ======");
		} catch (Exception e) {
			if (element_Displayed(Resources_Utility.xpath("secondUser_Check"), "Login Session Timer", driver)) {
				System.out.println("This Account is being used from another device at the moment!!");
			} else {
				e.printStackTrace();
			}
		}
	}

	@Test(enabled = true, priority = 3, groups = {"Research"})
	public void zoom_Check() throws Throwable {

		System.out.println('\n' + "====== INCOME TAX ZOOMING TEST STARTED ======");
		zoom_Check(Resources_Utility.xpath("foot_Note"), Resources_Utility.xpath("three_dots"),
				Resources_Utility.xpath("zoom_In_Btn"), Resources_Utility.xpath("zoom_Out_Btn"), driver);
		System.out.println("====== INCOME TAX ZOOMING TEST Done ======");
	}

	@Test(enabled = true, priority = 4, groups = {"Research"})
	public void filter_Check() throws Throwable {

		try {
			System.out.println('\n' + "====== INCOME TAX More Filter TEST STARTED ======");

			explicit_Wait(Resources_Utility.xpath("more_filter"), 5, "More Filter", driver);
			click(Resources_Utility.xpath("more_filter"), driver);
			System.out.println("-- Clicked on More Filter");
			explicit_Wait(Resources_Utility.xpath("in_favourFilter"), 2, "In Favour Filter", driver);

			click(Resources_Utility.xpath("in_favourFilter"), driver);
			explicit_Wait(Resources_Utility.xpath("FilterOption_Selection"), 10, "FilterOption_Selection", driver);
			random_Selection(Resources_Utility.xpath("FilterOption_Selection"), driver);

			Filter_Selected = getText(Resources_Utility.xpath("filter_Selected"), driver);
			System.out.println("-- Clicked on Favour Filter and Selected - " + Filter_Selected);
			sleep(2000);

			type(Resources_Utility.xpath("assessment_year"), Resources_Utility.config("assessment_year"), driver);
			System.out.println("-- Typed on Assessment Year Filter - " + Resources_Utility.config("assessment_year"));

			click(Resources_Utility.xpath("publications_Tab"), driver);
			click(Resources_Utility.xpath("year_Of_publications"), driver);
			sleep(2000);

			random_Selection(Resources_Utility.xpath("FilterOption_Selection"), driver);
			Filter_Selected = getText(Resources_Utility.xpath("filter_Selected"), driver);
			System.out.println("-- Clicked on Year of Publication and Selected - " + Filter_Selected);

			click(Resources_Utility.xpath("decision_Tab"), driver);
			sleep(1500);
			click(Resources_Utility.xpath("apply_filter"), driver);

			List<WebElement> elementName = driver
					.findElements(By.xpath(Resources_Utility.xpath("selected_Filter_Tags")));

			for (int i = 1; i <= elementName.size(); i++) {
				filter_Selected = driver
						.findElement(By.xpath(
								"(//div[@class='filter-tag d-flex flex-wrap overflow-hidden']/a)" + "[" + i + "]"))
						.getAttribute("title");
				System.out.println("-- Filter Selected - " + "'" + filter_Selected + "'");
			}
			sleep(5000);
			System.out.println("====== INCOME TAX More Filter TEST DONE ======");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}