 package PageRepo;

import java.util.ArrayList;

import org.testng.annotations.Test;

import GenericFile.Resources_Utility;
import taxmann.com.Phase1.AppTest;

//common_Functions

public class Academy_without_enrollment extends AppTest {
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
			click(Resources_Utility.xpath("academy_btn"), driver); // Click on advisory
			click(Resources_Utility.xpath("enroll_btn"), driver); // Click on enroll button
			
		} catch (Exception e) {
			e.printStackTrace();
		}}
		
	
	  @Test(enabled = true, priority = 3, groups = { "Common" }) 
	  public void Download_brochure_foundation() throws Throwable { 
		  try { 
	  //Download Brochure 
	 
	  click(Resources_Utility.xpath("academy_logo"), driver);
	  click(Resources_Utility.xpath("tax_account_btn"), driver);
	  click(Resources_Utility.xpath("academy_level1"), driver);
	  
	  
	  System.out.println('\n' +"-- Level 1-Foundation Download brochure Testing Started ---"); 
	  click(Resources_Utility.xpath("download_brochure"), driver);
		sleep(10000);
		 window_Switch(1, tabs, driver);
		  
		  parent=driver.getCurrentUrl(); 
		  verifyEquals(parent,Resources_Utility.config("Academy_BrocuherPDf"), driver); 
		  driver.close();
		  window_Switch(0, tabs, driver);
		  System.out.println('\n' +"-- Level 1-Foundation Download brochure Testing Done ---"); 
		 } catch (Exception e) {
	  e.printStackTrace(); } }
	 

	// Download Brochure
	@Test(enabled = true, priority = 2, groups= {"Common"})
	public void Download_brochure_dashboard() throws Throwable {
		try {
			System.out.println('\n' + "------ Dashboard Download Brochure Testing Started ------");
            sleep(3000);
			click(Resources_Utility.xpath("download_brochure"), driver);
			sleep(10000);
			 window_Switch(1, tabs, driver);
			  
			  parent=driver.getCurrentUrl(); 
			  verifyEquals(parent,Resources_Utility.config("Academy_BrocuherPDf"), driver); 
			  driver.close();
			  window_Switch(0, tabs, driver);
			  
			 
			/*
			 * String phone = fake_mobileNumber(driver);
			 * System.out.println("-- Mobile No is - " + phone);
			 * type(Resources_Utility.xpath("academy_mobile"), phone, driver);
			 * click(Resources_Utility.xpath("academy_send_otp"), driver);
			 * 
			 * if (element_Displayed(Resources_Utility.xpath("otp_SentMsg"),
			 * "OTP Sent Message", driver)) { message =
			 * getText(Resources_Utility.xpath("otp_SentMsg"), driver);
			 * verifyEquals(message, Resources_Utility.config("mobileOTP_Sent"), driver); }
			 * 
			 * fake_mobile_otp(driver); typeOTP(Resources_Utility.xpath("account_otp_here"),
			 * Resources_Utility.xpath("verify_OTP"), Resources_Utility.xpath(""),
			 * "OTP_Error", driver); click(Resources_Utility.xpath("ac_submit"), driver);
			 * 
			 * explicit_Wait(Resources_Utility.xpath("academy_Success_Msg"), 10,
			 * "Academy Broucher Download", driver); message =
			 * getText(Resources_Utility.xpath("academy_Success_Msg"), driver);
			 * verifyEquals(message, Resources_Utility.config("Academy_successMsg"),
			 * driver);
			 */
			System.out.println("------ Dashboard Download Brochure Testing Done ------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test(enabled = true, priority = 4, groups= {"Common"})
	public void academy_pay() throws Throwable {

		try {
			System.out.println('\n' + "======== Academy Payment ========");
			click(Resources_Utility.xpath("academy_logo"), driver);
			
			click(Resources_Utility.xpath("enroll_btn"), driver); // Click on enroll button
			sleep(3000);
			click(Resources_Utility.xpath("pay_course_fee_btn"), driver);
			sleep(1000);
			click(Resources_Utility.xpath("proceed_To_Pay"), driver); // Click on advisory
			sleep(1000);
			click(Resources_Utility.xpath("payBtn"), driver); // Click on enroll button
			sleep(3000);
			
		} catch (Exception e) {
			e.printStackTrace();
		}}
		
}