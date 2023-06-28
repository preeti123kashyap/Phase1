package PageRepo;

import org.openqa.selenium.WebDriver;
import GenericFile.Resources_Utility;
import GenericFile.common_Functions;

public class Practice extends common_Functions{

	public String parentUrl=null;
	public String storyHeading=null;
	public String opened_StoryHeading=null;
	public String filter_Tab=null;
	public String filterName=null;

	public void practice_Search(WebDriver driver) throws Throwable {

		try {
			System.out.println('\n'+ "======== Practice Search Testing Started ========");

			click(Resources_Utility.xpath("research_Btn"), driver);									//click on Research Button
			click(Resources_Utility.xpath("practice_Btn"), driver);									//click on Practice Button
			
			if(element_Displayed(Resources_Utility.xpath("skip_Btn"), "Skip Button", driver)) {
				click(Resources_Utility.xpath("skip_Btn"), driver);									//click on Skip if shown
			}
			
			click(Resources_Utility.xpath("all_Topics"), driver); sleep(2000);									//click on All TOpics
			click(Resources_Utility.xpath("discover_Topics"), driver); sleep(2000);								//click on Discover Topics
			click(Resources_Utility.xpath("income_Tax_Topic"), driver);sleep(2000);								//click on Income Tax Topics
			click(Resources_Utility.xpath("360_Degree_View"), driver);sleep(2000); 								//click on 360 degree view
			click(Resources_Utility.xpath("followed_Topics"), driver); sleep(2000);								//click on Followed Topics

			mousehover(Resources_Utility.xpath("sort_By_SelectionArrow"), driver);					//click on Selction Arrow
			random_Selection(Resources_Utility.xpath("sort_By_Selection"), driver);									//click on randomly select sorting
			System.out.println("-- Sorted By - "+getText(Resources_Utility.xpath("selected_Sort"), driver));		//Show the selection

			click(Resources_Utility.xpath("filter_Btn"), driver);									//click on filter button
			explicit_Wait(Resources_Utility.xpath("practice_MoreFilterBtn"), 10, "practice_MoreFilterBtn", driver);
			
			click(Resources_Utility.xpath("practice_MoreFilterBtn"), driver);								//click on More filter option
			filter_Select("decision_FiltersSelection", "moreFilters_SelectionOptions","More Filters", driver);				//Select the filter options from Decision Tab 

			click(Resources_Utility.xpath("other_FiltersTab"), driver);										//click on Other Filter Tab
			filter_Select("other_FiltersSelection", "moreFilters_SelectionOptions", "Main Filter", driver);				//Select the filter options from Other filter tab
			click(Resources_Utility.xpath("moreFilter_Header"), driver);
			
			click(Resources_Utility.xpath("apply_Filter"), driver); 										//click on Apply Filter Button
			explicit_Wait(Resources_Utility.xpath("practice_Stories"), 10, "practice_Stories", driver);		
			random_Selection(Resources_Utility.xpath("practice_MainFilterArrow"), driver);					//Randomly Select any filter
			


			explicit_Wait(Resources_Utility.xpath("practice_Stories"), 10, "practice_Stories", driver);
			random_Selection(Resources_Utility.xpath("practice_Stories"), driver);
	
			sleep(2500);
			opened_StoryHeading=getText(Resources_Utility.xpath("openedStory_Heading"), driver);
			System.out.println("Opened Story Name - "+opened_StoryHeading);

			System.out.println("======== Practice Search Testing Done ========");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void filter_Select(String filterTab, String FilterOptions, String filterFrom, WebDriver driver) throws Throwable {

		try {
			random_Selection(Resources_Utility.xpath(filterTab), driver);
			random_Selection(Resources_Utility.xpath(FilterOptions), driver);

			filterName=getText(Resources_Utility.xpath(FilterOptions), driver);
			System.out.println("-- "+filterFrom+" Selected as - "+filterName+"'");
			click(Resources_Utility.xpath(filterTab), driver);
			sleep(1500);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}