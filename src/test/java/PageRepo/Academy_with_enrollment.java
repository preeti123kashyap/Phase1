package PageRepo;

import java.util.ArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import GenericFile.Resources_Utility;
import taxmann.com.Phase1.AppTest;

//common_Functions

public class Academy_with_enrollment extends AppTest {
	public String parent = null;

	public static ArrayList<String> tabs;
	public static ArrayList<String> tabs1;
	public String message = null;

	@Test(enabled = true, priority = 1,groups= {"Common"})
	public void academy_testing() throws Throwable {

		try {
			System.out.println('\n' + "======== Academy Page Testing Started ========");
			parent = driver.getCurrentUrl();
			sleep(3000);
			click(Resources_Utility.xpath("research_Btn"), driver);
			click(Resources_Utility.xpath("academy_btn"), driver);  //click on academy
			click(Resources_Utility.xpath("enroll_btn"), driver);  //click on enroll button

		} catch (Exception e) {
			e.printStackTrace();
		}}
        
	@Test(enabled = true, priority = 2,groups= {"Common"})
	public void academy_dashboard() throws Throwable {

		try {
			Boolean display=driver.findElement(By.xpath(Resources_Utility.xpath("profile_complete"))).isDisplayed();
              if(display)
              {   
            	  sleep(2000);
            	  click((Resources_Utility.xpath("profile_complete")), driver); 
            	  sleep(1000);
            	  if(driver.findElement(By.xpath(Resources_Utility.xpath("click_dob"))).isDisplayed())
            	  {  
            	  click((Resources_Utility.xpath("click_dob")), driver);
            	  sleep(1000);
            	  click((Resources_Utility.xpath("academy_dob")), driver);
            	  sleep(1000);
            	  click((Resources_Utility.xpath("year_click")), driver);
            	  for(int i=1; i<4;i++) 
            	  {
            		  
            		  
            	  click((Resources_Utility.xpath("datepicker_arrow")), driver);
            		  
            		    
            	  }
            	  random_Selection(Resources_Utility.xpath("year_list"),driver);
            	  sleep(1000);
            	  random_Selection(Resources_Utility.xpath("month_list"),driver);
            	  sleep(1000);
            	  random_Selection(Resources_Utility.xpath("day_list"),driver);
            	  sleep(1000);
            	  click((Resources_Utility.xpath("acad_submit_1")), driver);
            	  sleep(2000);
            	  }
            	  else
            	  {  
            	  scrollbyElement(null,400, driver);
            	  sleep(10000);
            	  click(Resources_Utility.xpath("click_address"), driver);
            	  sleep(2000);
            	  type(Resources_Utility.xpath("add_name"), Resources_Utility.config("add_name"), driver);
            	  sleep(1000);
            	  type(Resources_Utility.xpath("add_mobile"), Resources_Utility.config("add_mobile"), driver);
            	  sleep(1000);
            	  type(Resources_Utility.xpath("add_flat"), Resources_Utility.config("add_flat"), driver);
            	  sleep(1000);
            	  type(Resources_Utility.xpath("add_street"), Resources_Utility.config("add_street"), driver);
            	  sleep(1000);
            	  type(Resources_Utility.xpath("add_Locality"), Resources_Utility.config("add_Locality"), driver);
            	  sleep(1000);
            	  type(Resources_Utility.xpath("add_pin"), Resources_Utility.config("add_pin"), driver);
            	  sleep(1000);
            	  type(Resources_Utility.xpath("add_city"), Resources_Utility.config("add_city"), driver);
            	  sleep(1000);
            	  click((Resources_Utility.xpath("add_state	")), driver);
            	  sleep(1000);
            	  random_Selection(Resources_Utility.xpath("add_states"),driver);
            	  }
              }

		} catch (Exception e) {
			e.printStackTrace();
		}}

	//Upcoming class Testing
	@Test(enabled = true, priority = 3,groups= {"Common"})
	public void course_schedule_upcoming() throws Throwable {

	//	try {

			click(Resources_Utility.xpath("Course_Schedule"), driver);
			sleep(2000);
			/*click(Resources_Utility.xpath("upcoming_class"), driver); 
			sleep(2000);
			click(Resources_Utility.xpath("filter_by_module"), driver); 
			sleep(5000);

			

			random_Selection(Resources_Utility.xpath("filter_option"), driver);
			sleep(7000);

			random_Selection(Resources_Utility.xpath("attend_class"), driver);
			sleep(5000);
			window_Switch(0, tabs, driver);
			sleep(5000);
			scrollbyElement(null, 700, driver);
			click(Resources_Utility.xpath("other_detail"), driver);
			scrollbyElement(null, 500, driver);
			sleep(5000);
			System.out.println('\n' + "Upcoming class testing done!!");
          

		} catch (Exception e) {
			e.printStackTrace();
		}*/
			}

	//previous class
	/*
	 * @Test(enabled = true, priority = 3,groups= {"Common"}) public void
	 * course_schedule_previous() throws Throwable {
	 * 
	 * try {
	 * 
	 * String header1="Got it ! Feel free to add a comment";
	 * 
	 * click(Resources_Utility.xpath("previous_class"), driver); sleep(2000);
	 * click(Resources_Utility.xpath("filter_by_module"), driver); sleep(7000);
	 * random_Selection(Resources_Utility.xpath("filter_option"), driver);
	 * sleep(7000);
	 * 
	 * 
	 * 
	 * sleep(10000); random_Selection(Resources_Utility.xpath("watch_recording"),
	 * driver); sleep(5000); click(Resources_Utility.xpath("play_icon"), driver);
	 * sleep(6000); click(Resources_Utility.xpath("rec_close"), driver);
	 * sleep(5000); click(Resources_Utility.xpath("like_dislike"), driver);
	 * sleep(3000); String
	 * header2=getText(Resources_Utility.xpath("like_dislike_model_heading"),
	 * driver);
	 * 
	 * verifyEquals(header1,header2,driver);
	 * 
	 * click(Resources_Utility.xpath("like_dislike_cross"), driver); sleep(5000);
	 * scrollbyElement(null, 400, driver);
	 * click(Resources_Utility.xpath("other_detail"), driver); sleep(5000);
	 * System.out.println('\n' + "Previous class testing done!!"); } catch
	 * (Exception e) { e.printStackTrace(); }}
	 */

	//Document download testing
	@Test(enabled = true, priority = 3,groups= {"Common"})
	public void Document_download() throws Throwable {

	//	try {

			click(Resources_Utility.xpath("documents"), driver);
			sleep(2000);
			/*
			 * click(Resources_Utility.xpath("filter_by_module"), driver); sleep(3000);
			 * random_Selection(Resources_Utility.xpath("filter_option"), driver);
			 * 
			 * sleep(10000); random_Selection(Resources_Utility.xpath("document_download"),
			 * driver); sleep(10000); System.out.println("Document has been downloaded!!!");
			 * 
			 * 
			 * } catch (Exception e) { e.printStackTrace(); }
			 */}
	
	public void Assessments() throws Throwable {

		//	try {

				click(Resources_Utility.xpath("assessment"), driver);
				sleep(2000);
				/*
				 * click(Resources_Utility.xpath("filter_by_module"), driver); sleep(3000);
				 * random_Selection(Resources_Utility.xpath("filter_option"), driver);
				 * 
				 * sleep(10000); random_Selection(Resources_Utility.xpath("document_download"),
				 * driver); sleep(10000); System.out.println("Document has been downloaded!!!");
				 * 
				 * 
				 * } catch (Exception e) { e.printStackTrace(); }
				 */}
	public void Library() throws Throwable {

		//	try {

				click(Resources_Utility.xpath("library"), driver);
				sleep(2000);
				/*
				 * click(Resources_Utility.xpath("filter_by_module"), driver); sleep(3000);
				 * random_Selection(Resources_Utility.xpath("filter_option"), driver);
				 * 
				 * sleep(10000); random_Selection(Resources_Utility.xpath("document_download"),
				 * driver); sleep(10000); System.out.println("Document has been downloaded!!!");
				 * 
				 * 
				 * } catch (Exception e) { e.printStackTrace(); }
				 */}
	public void certificates() throws Throwable {

		//	try {

				click(Resources_Utility.xpath("certificates"), driver);
				sleep(2000);
				/*
				 * click(Resources_Utility.xpath("filter_by_module"), driver); sleep(3000);
				 * random_Selection(Resources_Utility.xpath("filter_option"), driver);
				 * 
				 * sleep(10000); random_Selection(Resources_Utility.xpath("document_download"),
				 * driver); sleep(10000); System.out.println("Document has been downloaded!!!");
				 * 
				 * 
				 * } catch (Exception e) { e.printStackTrace(); }
				 */}
	





}