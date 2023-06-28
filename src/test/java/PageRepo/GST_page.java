package PageRepo;

import org.testng.annotations.Test;
import GenericFile.Resources_Utility;
import GenericFile.selenium_utility;
import taxmann.com.Phase1.AppTest;

public class GST_page extends AppTest {

	public String StoryName;
	public String StoryHeading;
	public String data = "NOTIFICATION NO. 8";

	@Test(enabled = true, priority = 1, groups = {"Research"})
	public void open_Circular_and_Notifications() throws Throwable {

		System.out.println('\n' + "====== GST TESTING STARTED ======");
		try {

			click(Resources_Utility.xpath("GST_btn"), driver); // click on GST button
			click(Resources_Utility.xpath("circular_&_Notifications"), driver); // click on circular and notifications
			System.out.println('\n' + "====== GST Cirtular and Notifications Opened ======");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(enabled = true, priority = 2, groups = {"Research"})
	public void search_GST() throws Throwable {
		try {

			System.out.println("====== GST Cirtular and Notifications Searching Test Started ======");
			explicit_Wait(Resources_Utility.xpath("see_More"), 10, "See More", driver);
			type(Resources_Utility.xpath("internal_search_box"), data, driver); // click on search box
			selenium_utility.enterDw(driver);

			// sleep(1500);
			// mousehover(Resources_Utility.xpath("year_Filter_Btn"), driver);
			// click(Resources_Utility.xpath("year_Selection"), driver); //click on Year
			// filter and select a year

			sleep(2000);
			story_Closure(Resources_Utility.xpath("story_Close_allbtns"), driver); // close all other stories
			click(Resources_Utility.xpath("second_Story_LeftPanel_GST"), driver); // click on second story
			story_Closure(Resources_Utility.xpath("story_Close_allbtns"), driver); // close all other stories

			StoryName = getText(Resources_Utility.xpath("second_Story_LeftPanel_GST"), driver);
			StoryName = StoryName.substring(0, StoryName.lastIndexOf("["));

			StoryHeading = getText(Resources_Utility.xpath("story_Heading_GST"), driver);
			StoryHeading = StoryHeading.substring(0, StoryHeading.lastIndexOf("["));

			verifyEquals(StoryName, StoryHeading, driver); // match the story name

			System.out.println("Story Name and title matched successfully");
			System.out.println("====== GST Cirtular and Notifications Searching Done ======");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(enabled = true, priority = 3, groups = {"Research"})
	public void zoom_Check() throws Throwable {

		try {
			System.out.println('\n' + "====== GST Cirtular and Notifications Zoom Testing Started ======");
			zoom_Check(Resources_Utility.xpath("foot_Note"), Resources_Utility.xpath("zoom_Btn"), // check the Zoom
																									// functionality
					Resources_Utility.xpath("zoom_In_Btn"), Resources_Utility.xpath("zoom_Out_Btn"), driver);
			System.out.println("====== GST Cirtular and Notifications Zoom Testing Done ======");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(enabled = true, priority = 4, groups = {"Research"})
	public void download_GST() throws Throwable {

		try {
			System.out.println('\n' + "====== GST Cirtular and Notifications Download Test Started ======");
			StoryHeading = StoryHeading.replaceAll("\\p{P}", "");
			StoryHeading = StoryHeading.replaceAll("\\s", "");

			click(Resources_Utility.xpath("download_Btn"), driver); // download any story as doc
			click(Resources_Utility.xpath("pdf_Download"), driver);
			String downloaded_File_Name = verify_Downloaded_File();
			String dot = ".";
			int sep = downloaded_File_Name.lastIndexOf(dot);
			downloaded_File_Name = downloaded_File_Name.substring(0, sep);
			downloaded_File_Name = downloaded_File_Name.replaceAll("\\p{P}", "");
			downloaded_File_Name = downloaded_File_Name.replaceAll("\\s", "");
			System.out.println("Downloaded File Name - " + downloaded_File_Name);

			verifyEquals(StoryHeading, downloaded_File_Name, driver); // match the downloaded story with open one
			System.out.println("file name matched successfully");
			System.out.println("====== GST Cirtular and Notifications Download Test Done ======");
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}