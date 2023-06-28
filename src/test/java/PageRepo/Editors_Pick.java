package PageRepo;

import org.testng.annotations.Test;

import GenericFile.Resources_Utility;
import taxmann.com.Phase1.AppTest;

public class Editors_Pick extends AppTest {

	public String url=null;
	public static String actual_url="https://www.facebook.com/login";
	public static String current_url=null;
	
	@Test(enabled = true, priority = 1, groups = {"Research"})
	public void check_Stories() throws Throwable {

		try {	
			System.out.println('\n'+ "============== Editor's Pic Page Testing Started ==============");

			url=driver.getCurrentUrl();
			driver.get(url+"/all/archives?tag=EP");

			explicit_Wait(Resources_Utility.xpath("reset_Filters"), 10, "Reset Filters Option", driver);
			click(Resources_Utility.xpath("reset_Filters"), driver);					//clicking on Reset filters
			
			explicit_Wait(Resources_Utility.xpath("allmain_Filters"), 5, "All Filters", driver);			
			random_Selection(Resources_Utility.xpath("allmain_Filters"), driver);
			random_Selection(Resources_Utility.xpath("filter_List"), driver);

			click(Resources_Utility.xpath("maximize_Btn"), driver);
			random_Selection(Resources_Utility.xpath("editors_Pick_AllStory"), driver);
			explicit_Wait(Resources_Utility.xpath("story_Heading_MostViewed"), 10, "Story Heading", driver);
			System.out.println("-- Story Name is - "+getText(Resources_Utility.xpath("story_Heading_MostViewed"), driver));

			click(Resources_Utility.xpath("share_Btn"), driver);
			click(Resources_Utility.xpath("facebook_Btn"), driver);
			sleep(2000);
			
			window_Switch(1, tabs, driver);
			current_url = driver.getCurrentUrl();
			current_url = current_url.substring(0, 30);
			
			verifyEquals(actual_url, current_url, driver);
			driver.close();
			window_Switch(0, tabs, driver);
					 
			System.out.println("============== Editor's Pic Page Testing Done ==============");
		} catch (Exception e) {	
			e.printStackTrace();
		}	
	}
}