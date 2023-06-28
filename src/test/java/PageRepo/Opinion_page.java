package PageRepo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import GenericFile.Resources_Utility;
import taxmann.com.Phase1.AppTest;

public class Opinion_page extends AppTest {

	public String choose_by = null;
	public String url = null;
	public int stories_Count = 0;
	public int stories_Count1 = 0;
	public String date_On_Story = null;
	public String filter_Selected = null;
	public String sort = null;
	public String views_Count = null;
	public Date date = null;
	public List<WebElement> story_Count = null;
	public List<WebElement> story_Count1 = null;

	@Test(enabled = true, priority = 1, groups = {"Research"})
	public void choose_OpinionPage() throws Throwable {

		System.out.println('\n' + "============== OPINION PAGE TESTING ==============");
		sleep(1000);
		url = driver.getCurrentUrl();
		driver.get(url + "/all/archives?moduleType=articles");
	}

	@Test(enabled = true, priority = 2, groups = {"Research"})
	public void opinion_FilterSelection() throws Throwable {

		try {

			explicit_Wait(Resources_Utility.xpath("reset_Filters"), 10, "Reset Filters Option", driver);
			click(Resources_Utility.xpath("reset_Filters"), driver); // clicking on Reset filters

			// explicit_Wait(Resources_Utility.xpath("allmain_Filters"), 5, "Main Filters",
			// driver);
			wait_Tobe_Clickable(Resources_Utility.xpath("allmain_Filters"), 20, "allmain_Filters", driver);
			random_Selection(Resources_Utility.xpath("allmain_Filters"), driver); // clicking on main filters
			random_Selection(Resources_Utility.xpath("filter_List"), driver); // selecting randomly any filter

			List<WebElement> elementName = driver
					.findElements(By.xpath(Resources_Utility.xpath("selected_Filter_Tags")));

			for (int i = 1; i <= elementName.size(); i++) {
				filter_Selected = driver
						.findElement(By.xpath(
								"(//div[@class='filter-tag d-flex flex-wrap overflow-hidden']/a)" + "[" + i + "]"))
						.getAttribute("title");
				System.out.println("-- Filter Selected - " + "'" + filter_Selected + "'");
			}
			sleep(2000);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(enabled = true, priority = 3, groups = {"Research"})
	public void opinion_ByDate() throws Throwable {
		try {
			System.out.println('\n' + "============== Opinion page Testing Date-wise Started ==============");

			order_Selection("date_Btn", driver);
			story_Count = driver.findElements(By.xpath(Resources_Utility.xpath("dates_On_Opinion")));

			story_Selection(story_Count, "Dates_Check", driver);
			System.out.println("============== Opinion page Testing Date-wise Done ==============");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(enabled = true, priority = 4, groups = {"Research"})
	public void opinion_ByPopularity() throws IOException {

		try {
			System.out.println('\n' + "============== Opinion page Testing Popularity-wise Started ==============");

			opinion_FilterSelection();
			sleep(1000);
			order_Selection("popularity_Btn", driver);
			story_Count1 = driver.findElements(By.xpath(Resources_Utility.xpath("dates_On_Opinion")));
			ArrayList<Integer> sortedList = new ArrayList<>();

			List<Integer> counts_View = new ArrayList<>();
			counts_View = story_Selection(story_Count1, "Popularity_Check", driver);

			for (int s : counts_View) {
				sortedList.add(s);
			}
			try {
				if (sort.contains("Descending")) {
					Assert.assertTrue(sortedList.equals(counts_View));
					System.out.println("-- List is sorted as per " + sort + " Popularity order");
				} else {
					Assert.assertTrue(sortedList.equals(counts_View));
					System.out.println("-- List is sorted as per " + sort + " Popularity order");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("============== Opinion page Testing Popularity-wise Done ==============");
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public List<Integer> story_Selection(List<WebElement> story_Count, String check, WebDriver driver)
			throws Throwable {

		List<Integer> count = new ArrayList<>();
		try {
			sleep(1000);
			stories_Count = story_Count.size();
			if (story_Count.size() % 2 != 0) {
				stories_Count = story_Count.size() + 1;
			}
			System.out.println("-- Total stories displayed - " + story_Count.size()); // printing stories count
			System.out.println("-- Stories opening for testing - " + stories_Count / 2); // printing stories count
			for (int i = 1; i <= stories_Count / 2; i++) // opening stories and checking popularity count
			{
				sleep(2000);
				explicit_Wait("(//div[@id='archiveList']/div/div/div/div/a)" + "[" + i + "]", 10,
						"Story No " + "[" + i + "]", driver);
				driver.findElement(By.xpath("(//div[@id='archiveList']/div/div/div/div/a)" + "[" + i + "]")).click();
				explicit_Wait(Resources_Utility.xpath("story_Body"), 10, "Story Loading", driver);

				if (check.contains("Dates_Check")) {
					date_On_Story = getText(Resources_Utility.xpath("date_On_Story"), driver);
					date_On_Story = date_On_Story.substring(0, date_On_Story.indexOf("|"));

					System.out.println(i + " - Date - '" + date_On_Story + "' on Story - "
							+ getText(Resources_Utility.xpath("story_Heading_MostViewed"), driver));
				} else if (check.contains("Popularity_Check")) {
					views_Count = getText(Resources_Utility.xpath("views_Count"), driver);
					views_Count = views_Count.replaceAll("[^\\d]", "");
					int view_Record = Integer.parseInt(views_Count);

					System.out.println(i + " - '" + view_Record + "' Views on Story - "
							+ getText(Resources_Utility.xpath("story_Heading_MostViewed"), driver));
					count.add(view_Record);
				}
				driver.navigate().back();
				explicit_Wait(Resources_Utility.xpath("date_Btn"), 10, "Date Option", driver);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public void order_Selection(String button, WebDriver driver) throws Throwable {
		try {
			click(Resources_Utility.xpath("maximize_Btn"), driver);
			click(Resources_Utility.xpath(button), driver); // Clicking on Date/Popularity sorting
			if (button.contains("date_Btn")) {
				click(Resources_Utility.xpath("date_Sorting"), driver);
				sort = getTitle(Resources_Utility.xpath("date_Sorting"), driver);
			} else {
				sleep(1000);
				click(Resources_Utility.xpath("popularity_Sorting"), driver);
				sort = getTitle(Resources_Utility.xpath("popularity_Sorting"), driver);
			}
			sleep(2000);
			System.out.println("-- Selecting " + button + " As '" + sort + "' Order!!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}