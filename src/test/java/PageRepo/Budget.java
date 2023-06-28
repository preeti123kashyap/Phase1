package PageRepo;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import GenericFile.Resources_Utility;
import taxmann.com.Phase1.AppTest;

public class Budget extends AppTest {

	public String parent=null;
	public String selected_Option=null;
	public String story_heading1=null;
	public String story_heading2=null;

	public static ArrayList<String> tabs;

	@Test(enabled = true, priority = 1,groups = {"Common"}) 
	public void budget_testing() throws Throwable {

		System.out.println('\n'+"======== Budget Page Testing Started ========");

		click(Resources_Utility.xpath("budget_cta"), driver);						//Click on Budget cta button		
		budget_StoryAndTaxRates(driver);
		budget_Highlights(driver);
		budget_BigStory(driver);
		budget_Faq(driver);
		budget_ExpertView(driver);	

		System.out.println('\n'+"======== Budget Page Testing Done ========");
	}

	@Test(enabled = true, priority = 2)
	public void budget_StoryAndTaxRates(WebDriver driver) throws Throwable {
		try {
			System.out.println('\n'+ "======== Budget Story and Tax Rates Testing Started ========");

			explicit_Wait(Resources_Utility.xpath("story_first_heading"), 10, "story_first_heading", driver);
			story_heading1=getText(Resources_Utility.xpath("story_first_heading"),  driver);   		//Click on first Story
			click(Resources_Utility.xpath("story_first_heading"), driver);

			sleep(2000);
			story_heading2=getText(Resources_Utility.xpath("story_heading2"),  driver);
			pageEmpty("story_content","Story content",  driver);

			verifyEquals(story_heading1, story_heading2,  driver);							//Story heading verification 
			driver.navigate().back();
			click(Resources_Utility.xpath("tax_rate"), driver); 							//click on Tax Rates

			scrollbyElement(null, 350, driver);
			random_Selection( Resources_Utility.xpath("tax_list"),  driver);
			selected_Option=random_Selection_Name(Resources_Utility.xpath("tax_list"), driver);
			System.out.println("-- Selected tax rate is:'"+selected_Option+"'");

			if(selected_Option.contains("Individual Male")) {
				pageEmpty("tax_rate_content1" ,"Tax rate content", driver);
			}else {
				pageEmpty("tax_rate_content" ,"Tax rate content", driver);
			}
			sleep(1000);
			click(Resources_Utility.xpath("up_ScrollBtn"), driver);sleep(1000);
			System.out.println("======== Budget Story and Tax Rates Testing Done ========");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(enabled = true, priority = 3)
	public void budget_Highlights(WebDriver driver) throws Throwable {
		try {
			System.out.println('\n'+ "======== Budget Highlight Testing Started ========");

			scrollbyElement(Resources_Utility.xpath("budget_list"), 0, driver);	
			explicit_Wait(Resources_Utility.xpath("budget_list"), 10, "Budget list", driver);
			random_Selection( Resources_Utility.xpath("budget_list"),  driver);					//random selection on budget Highlights
			sleep(1000);

			selected_Option = random_Selection_Name(Resources_Utility.xpath("budget_list"), driver);
			System.out.println("-- Selected budget Highlight is: "+selected_Option);
			window_Switch(1, tabs, driver);
			random_Selection( Resources_Utility.xpath("tab_list"),  driver);

			selected_Option = random_Selection_Name(Resources_Utility.xpath("tab_list"), driver);
			System.out.println("-- Selected budget Highlight in new tab is: "+selected_Option);
			pageEmpty("budget_highlight_content" ,"Budget highlight content", driver);   //Budget highlight content verification	

			driver.close();
			window_Switch(0, tabs, driver);
			sleep(1000);
			click(Resources_Utility.xpath("up_ScrollBtn"), driver);sleep(1000);
			System.out.println("======== Budget Highlight Testing Done ========");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(enabled = true, priority = 4)
	public void budget_BigStory(WebDriver driver) throws Throwable {
		try {
			System.out.println('\n'+ "======== Budget Big Story Testing Started ========");

			scrollbyElement(Resources_Utility.xpath("big_story_heading"), 0, driver);
			explicit_Wait(Resources_Utility.xpath("big_story_heading"), 5, "Big story heading", driver);
			random_Selection(Resources_Utility.xpath("big_story_heading"), driver);

			driver.navigate().back();
			story_heading1 = random_Selection_Name(Resources_Utility.xpath("big_story_heading"), driver);
			driver.navigate().forward();
			try {
				if(element_Displayed(Resources_Utility.xpath("big_story_heading1"), "Big Story Heading", driver)) {
					story_heading2=getText(Resources_Utility.xpath("big_story_heading1"),  driver);
					verifyEquals(story_heading1,story_heading2,driver); 									//big story heading verification
				}else {
					captureScreenshot(driver);
					System.out.println("-- Story Not Loaded Properly");
				}	
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			driver.navigate().back();
			scrollbyElement(Resources_Utility.xpath("videos_list"), 0, driver);										
			random_Selection( Resources_Utility.xpath("videos_list"),  driver);							//video random selection

			explicit_Wait(Resources_Utility.xpath("video_close_btn"), 10, "video_close_btn", driver);
			click(Resources_Utility.xpath("video_close_btn"), driver); //video close
			click(Resources_Utility.xpath("up_ScrollBtn"), driver);
			sleep(1000);
			System.out.println("-- Hence, video played Successfully!!!");
			System.out.println("======== Budget Big Story Testing Done ========");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(enabled = true, priority = 5)
	public void budget_Faq(WebDriver driver) throws Throwable {
		try {
			System.out.println('\n'+ "======== Budget Faq Testing Started ========");

			scrollbyElement(null, 2800, driver);
			explicit_Wait(Resources_Utility.xpath("plus_icon"), 5, "plus_icon", driver);
			click(Resources_Utility.xpath("plus_icon"), driver);
			sleep(2000);

			explicit_Wait(Resources_Utility.xpath("minus_icon"), 5, "minus_icon", driver);
			click(Resources_Utility.xpath("minus_icon"), driver);
			System.out.println("-- Hence, plus and minus icon working fine!!!");

			click(Resources_Utility.xpath("up_ScrollBtn"), driver);
			sleep(2000);
			scrollbyElement(null, 1500, driver);
			click(Resources_Utility.xpath("tools_view_all"), driver);

			window_Switch(1, tabs, driver);
			pageEmpty( "tools_content","Tools Content",driver);

			driver.close();
			window_Switch(0, tabs, driver);
			click(Resources_Utility.xpath("up_ScrollBtn"), driver);
			sleep(1000);
			System.out.println("======== Budget Faq Testing Done ========");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(enabled = true, priority = 6)
	public void budget_ExpertView(WebDriver driver) throws Throwable {

		try {
			System.out.println('\n'+ "======== Budget Expert View Testing Started ========");

			scrollbyElement(Resources_Utility.xpath("speak_view_all"), 0, driver);
			explicit_Wait(Resources_Utility.xpath("speak_view_all"), 5, "speak_view_all", driver);
			click(Resources_Utility.xpath("speak_view_all"), driver);

			System.out.println("-- Hence, Speaker view all link is working fine!!!");
			System.out.println("-- Speaker page Content verification---");
			pageEmpty("speaker_content","Speaker Content", driver);

			driver.navigate().back();
			sleep(2000);
			scrollbyElement(Resources_Utility.xpath("stories_view_all"), 0, driver);
			click(Resources_Utility.xpath("stories_view_all"), driver);

			System.out.println("-- Hence, Trending Stories view all link is working fine!!!");
			System.out.println("-- Trending Stories page Content verification---");
			pageEmpty("trnd_stories_content", "Trending Story Content", driver);

			System.out.println("======== Budget Expert View Testing Done ========");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}