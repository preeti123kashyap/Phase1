package PageRepo;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.annotations.Test;

import GenericFile.Resources_Utility;
import taxmann.com.Phase1.AppTest;

public class Most_Viewed extends AppTest {

	public String path=null;
	public String url=null;
	public String openStory_Heading=null;
	public String listStory_Heading=null;
	public String story_SubHeading=null;
	public String pdf_StoryHeading=null;
	public String Fav_story_Heading=null;
	public int  textSize;
	public String path1 = "C:\\Users\\techn\\eclipse-workspace\\Automation\\automation\\rough\\image\\print.PNG";

	//Selecting story from most viewed
	@Test(enabled = true, priority = 1, groups = {"Research"})
	public void selecting_MostViewed() throws Throwable {

		try {
			System.out.println('\n'+ "============== Most-Viewed Story Selection Testing Started ==============");

			url=driver.getCurrentUrl();
			driver.get(url+"/all/archives?duration=15&sortBy=viewcount");

			explicit_Wait(Resources_Utility.xpath("reset_Filters"), 10, "Reset Filters Option", driver);
			click(Resources_Utility.xpath("reset_Filters"), driver);													//clicking on Reset filters

			sleep(2000);
			random_Selection(Resources_Utility.xpath("mostViewed_AllStories"), driver);									//randomly select any story from most viewed
			explicit_Wait(Resources_Utility.xpath("share"), 5, "story Heading MostViewed", driver);

			openStory_Heading = getText(Resources_Utility.xpath("story_Heading_MostViewed"), driver);  					//getting the heading of first story
			story_SubHeading = getText(Resources_Utility.xpath("story_SubHeading_MostViewed"), driver);  				//getting the sub-heading of first story
			System.out.println("Open Story Heading - "+openStory_Heading);
			driver.navigate().back();

			explicit_Wait(Resources_Utility.xpath("itemsPerPageSelection"), 5, "items Per Page Selection", driver);
			click(Resources_Utility.xpath("itemsPerPageSelection"), driver);
			click(Resources_Utility.xpath("50_Items"), driver);


			explicit_Wait(Resources_Utility.xpath("previous_Btn"), 5, "Previous Button", driver);
			listStory_Heading=random_Selection_Name(Resources_Utility.xpath("mostViewed_AllStories"), driver);
			System.out.println("Story Heading in list - "+listStory_Heading);

			driver.navigate().forward();
			explicit_Wait(Resources_Utility.xpath("share"), 10, "share Option", driver);
			verifyEquals(openStory_Heading, listStory_Heading, driver);
			System.out.println("Story Headings Matched!!!!");	

			System.out.println("============== Most-Viewed Story Selection Testing done ==============");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	//Checking Favorite options
	@Test(enabled = true, priority = 2, groups = {"Research"})
	public void favorite_MostViewed() throws Throwable {

		try {
			System.out.println('\n'+ "============== Most-Viewed Favourite Story Selection Testing Started ==============");

			explicit_Wait(Resources_Utility.xpath("print"), 10, "Print Option", driver);
			if(element_Displayed(Resources_Utility.xpath("fav_Unselected"), "Fav Button Unselected", driver))
			{
				click(Resources_Utility.xpath("favourite_icon"), driver);		//click on favorite icon
				explicit_Wait(Resources_Utility.xpath("pop_up_Message"), 10, "Confirmation Message Pop-up", driver);
				String  fav_Confirmation = getText(Resources_Utility.xpath("pop_up_Message"), driver);						//Getting the confirmation string 

				verifyEquals(fav_Confirmation, Resources_Utility.config("fav_Confirmation"), driver);						//verifying the confirmation text
				click(Resources_Utility.xpath("pop_up_Close"), driver);														//closing the pop-up
				System.out.println("story saved as favourite successfully"); 												//Success message
			}
		}catch (Exception e) {
			System.out.println("story already saved as favourite"); 														//Success message
		}
		explicit_Wait(Resources_Utility.xpath("research_Box"), 5, "Research Box", driver);
		click(Resources_Utility.xpath("research_Box"), driver); 															//Go to research box

		try {
			if(element_Displayed(Resources_Utility.xpath("skip_Btn"), "Skip Button", driver)) 								//skipping the pop-up
			{
				click(Resources_Utility.xpath("skip_Btn"), driver);
				System.out.println("Skipped Successfully!!!!");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

		explicit_Wait(Resources_Utility.xpath("imp_Notice_OK"), 3, "Important Notice", driver);
		click(Resources_Utility.xpath("imp_Notice_OK"), driver);															//Important notice skipping
		click(Resources_Utility.xpath("favourite_Btn"), driver);															//clicking on favorite button drop-down

		List<WebElement> myList=driver.findElements(By.xpath(Resources_Utility.xpath("fav_AllStories")));				//Getting the list of all favorite stories
		List<String> all_elements_text=new ArrayList<>();
		for(int i=0; i<myList.size(); i++){
			all_elements_text.add(myList.get(i).getText());
		}

		for(int i=0; i<myList.size(); i++)													//Matching the favorite marked story with the stroies in research box
		{
			try {	
				if(myList.get(i).getText().equals(story_SubHeading)) {
					path= "(//div[@class='shortcontent-text-1'])"+"["+i+"+"+1+"]";
					break;
				}
				else if(myList.get(i).getText().equals(openStory_Heading)) {
					path= "(//div[@class='shortcontent-text-1'])"+"["+i+"+"+1+"]";
					break;
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		scrollbyElement(path, 0, driver);		//scrolling upto the matched story
		explicit_Wait(path, 5, "Favourited Story", driver);
		driver.findElement(By.xpath(path)) .click(); 
		sleep(1000);

		if(element_Displayed(Resources_Utility.xpath("fav_StoryHeading"), Fav_story_Heading, driver)) {
			Fav_story_Heading = getText(Resources_Utility.xpath("fav_StoryHeading"), driver);
		}else {
			Fav_story_Heading = getText(Resources_Utility.xpath("fav_StoryHeading2"), driver);
		}
		System.out.println("Story Sub-heading in Favourite box -"+Fav_story_Heading);

		System.out.println("Open Story Sub-heading -"+story_SubHeading);
		verifyEquals(story_SubHeading, Fav_story_Heading, driver);														//Matching the headings of selected story

		explicit_Wait(Resources_Utility.xpath("favourite_icon"), 5, "Favorite Icon", driver);
		click(Resources_Utility.xpath("favourite_icon"), driver); 									//click on favorite icon to remove added story from favorite	
		explicit_Wait(Resources_Utility.xpath("pop_up_Message"), 10, "Confirmation Message Pop-up", driver);

		String fav_Confirmation1 = getText(Resources_Utility.xpath("pop_up_Message"),driver); 
		verifyEquals(fav_Confirmation1, Resources_Utility.config("fav_RemoveConfirmation"), driver);
		click(Resources_Utility.xpath("pop_up_Close"), driver);															//closing the pop-up

		System.out.println("story removed from Research box successfully");
		System.out.println("============== Most-Viewed Favourite Story Selection Testing Done ==============");
		driver.navigate().back();
	} 


	//Checking print option
	@Test(enabled = false, priority = 3, groups = {"Research"})
	public void print_MostViewed() throws Throwable {

		try {  
			System.out.println('\n'+ "============== Most-Viewed Print Story Testing Started ==============");

			String parent=driver.getWindowHandle();
			explicit_Wait(Resources_Utility.xpath("print"), 3, "Print Option", driver);
			click(Resources_Utility.xpath("print"), driver);

			random_Selection(Resources_Utility.xpath("printli"), driver);
			window_Switch(2, tabs, driver);
			System.out.println(driver.getWindowHandle());

			Screen screen = new Screen();
			Pattern pic = new Pattern(path1);
			screen.click(pic);

			driver.close(); 
			driver.switchTo().window(parent); 
			sleep(1000);

			System.out.println("============== Most-Viewed Print Story Testing Done ==============");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// download function
	@Test(enabled = true, priority = 4, groups = {"Research"})
	public void download_MostViewed() throws Throwable {

		try {  
			System.out.println('\n'+ "============== Most-Viewed Story Download Testing Started ==============");

			openStory_Heading = openStory_Heading.replaceAll("\\p{P}", "");
			openStory_Heading = openStory_Heading.replaceAll("\\s", "");

			story_SubHeading = story_SubHeading.replaceAll("\\p{P}", "");
			story_SubHeading = story_SubHeading.replaceAll("\\s", "");

			if(element_Displayed(Resources_Utility.xpath("download_Btn"), "Download Option on Story", driver)) {
				click(Resources_Utility.xpath("download_Btn"), driver); 												//download any story as doc
				click(Resources_Utility.xpath("pdf_Download"), driver);	
			}else {
				click(Resources_Utility.xpath("download_Pdf_Story"), driver); 											//download any story as doc
				pdf_StoryHeading=getText(Resources_Utility.xpath("pdf_StoryText"), driver);	
			}

			sleep(2000);
			String downloaded_File_Name = verify_Downloaded_File();
			System.out.println("-- Story Heading of downloaded file - "+downloaded_File_Name);

			String dot = ".";
			int sep = downloaded_File_Name.lastIndexOf(dot);
			downloaded_File_Name = downloaded_File_Name.substring(0, sep);
			downloaded_File_Name = downloaded_File_Name.replaceAll("\\p{P}", "");
			downloaded_File_Name = downloaded_File_Name.replaceAll("\\s", "");
			System.out.println("-- Downloaded File Name - "+downloaded_File_Name );

			try{
				boolean check = downloaded_File_Name.contains(openStory_Heading); 									// match the downloaded story with open one

				if (check) {
					verifyEquals(openStory_Heading, downloaded_File_Name, driver);									 // match the downloaded story with open one
					System.out.println("-- Downloaded file name matched successfully with Heading of Story!!!!!");
				}

				try {
					boolean check1 = downloaded_File_Name.contains(story_SubHeading); 								// match the downloaded story with open one

					if (check1) {	
						verifyEquals(story_SubHeading, downloaded_File_Name, driver); 								// match the downloaded story with open one
						System.out.println("-- Downloaded file name matched successfully with Sub-Headding of Story!!!!!");
					}

					try {
						boolean check2 = downloaded_File_Name.contains(pdf_StoryHeading); 								// match the downloaded story with open one

						if(!(pdf_StoryHeading.contains(""))) {
							if (check2) {	
								verifyEquals(pdf_StoryHeading, downloaded_File_Name, driver); 								// match the downloaded story with open one
								System.out.println("-- Downloaded file name matched successfully with Story text of pdf file!!");
							}
						}
					}
					catch (Exception e) {
						System.out.println("-- Story Format is plain Text!!!!");
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			catch (Exception e) {
				System.out.println("-- Downloaded Story Name doesn't match with Story Text, Heading or Sub-Heading of Story!!!!!");
			}
			System.out.println("============== Most-Viewed Story Download Testing Done ==============");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}