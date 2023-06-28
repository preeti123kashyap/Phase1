package PageRepo;

import org.testng.annotations.Test;
import GenericFile.Resources_Utility;
import GenericFile.selenium_utility;
import taxmann.com.Phase1.AppTest;

public class SearchStory extends AppTest {

	String[] storyName = new String[] {
			"INSTRUCTION NO.8/2021-CUSTOMS"/*
											 * , "interest reversal of ITC", "Ajay Kumar Singh Gaur",
											 * "NOTIFICATION NO. 1/2021 - CENTRAL TAX (RATE) [F. NO. 354/53/...",
											 * "127 taxmann.com 506",
											 */ };

	@Test (enabled = true, priority = 1, groups = {"Research"})
	public void searchStory() {

		try {
			System.out.println("====== SEARCH STORY TESTING STARTED ======");

			for (int i = 0; i < storyName.length; i++) {

				String head = getText(Resources_Utility.xpath("search_Box"), driver);
				System.out.println(head);

				System.out.println("-- Searching - '" + storyName[i] + "'");
				type(Resources_Utility.xpath("search_Box"), storyName[i], driver);
				selenium_utility.enterDw(driver);

				try {
					explicit_Wait(Resources_Utility.xpath("favourite_icon"), 5, "Favourtie Icon", driver);
					String searchStoryName = getText(Resources_Utility.xpath("search_Story_Name_LeftPanel"), driver);
					String searchStoryHeading = getText(Resources_Utility.xpath("search_Story_heading"), driver);

					searchStoryName = searchStoryName.trim();
					searchStoryHeading = searchStoryHeading.trim();

					verifyEquals(searchStoryName, Resources_Utility.config("search_Story_" + i), driver);
					verifyEquals(searchStoryHeading, Resources_Utility.config("search_Story_" + i), driver);

				} catch (Exception e) {
					if (element_Displayed(Resources_Utility.xpath("secondUser_Check"), "Second User", driver)) {
						System.out.println(
								"-- This story is already Opened in another window. Please wait for 5 minutes!!!!!");
					} else {
						e.printStackTrace();
					}
				}
				clear_text(Resources_Utility.xpath("search_Box"), driver);
			}
			System.out.println("====== SEARCH STORY TESTING Done ======");
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}