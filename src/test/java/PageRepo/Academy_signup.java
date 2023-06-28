package PageRepo;

import java.util.ArrayList;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import GenericFile.Resources_Utility;
import GenericFile.common_Functions;
import GenericFile.selenium_utility;
import taxmann.com.Phase1.AppTest;

public class Academy_signup extends common_Functions{
	private static String driverpath = System.getProperty("user.dir") + "\\driver\\";
	public static WebDriver driver = null;
	public static String parent = null;
	
	public String message = null;
	public String email_Id = null;
	public String email = null;
	public String mob_No = null;
	public static ArrayList<String> tabs;
	Random rand = new Random();

	@Test(enabled = true, priority = 1,groups= {"Common"})
	public void academy_sign() throws Throwable {
		try {
            
			System.out.println('\n' + "======== Academy Sign-up Testing Started ========");
			System.setProperty("webdriver.chrome.driver", driverpath + "chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			
            
			/*
			 * click(Resources_Utility.xpath("profile_Btn"), driver); //click on Profile
			 * click(Resources_Utility.xpath("logout_Btn"), driver); //click on logout
			 * message = getText(Resources_Utility.xpath("logout_Msg"), driver);
			 * 
			 * verifyEquals(message, Resources_Utility.config("logout_Msg"), driver);
			 * //verify logout messages click(Resources_Utility.xpath("pop_up_Close"),
			 * driver);
			 */
        //    driver.get("https://www.taxmann.com/auth/login");
			
			driver.get("https://dev.taxmann.com/");
			click(Resources_Utility.xpath("academy_Link"), driver); // click on academy
			click(Resources_Utility.xpath("enroll_btn"), driver); // click on let's get started
		//	click(Resources_Utility.xpath("academy_Sign_up"), driver);
		//	logout();
			type(Resources_Utility.xpath("account_name"), "Rahul", driver); // filling of sign up form
			type(Resources_Utility.xpath("account_Lname"), "Kumar", driver);

			email_Id = "TestUser" + rand.nextInt(1000) + "@gmail.com";
			type(Resources_Utility.xpath("account_email"), email_Id, driver);
			type(Resources_Utility.xpath("account_password"), "Test@12345", driver);

			click(Resources_Utility.xpath("account_next"), driver); // click on next button
			Thread.sleep(2000);
			String phoneNo = fake_mobileNumber(driver);
			System.out.println("-- Mobile Number is - " + phoneNo);

			type(Resources_Utility.xpath("account_mobile_path"), phoneNo, driver);
			click(Resources_Utility.xpath("send_OTP"), driver);

			if (element_Displayed(Resources_Utility.xpath("alreadyUsedmobile_Msg"), "Mobile No. Already in Use",
					driver)) {
				click(Resources_Utility.xpath("alreadyUsedmobile_Yes"), driver);
			}

			fake_mobile_otp(driver);

			typeOTP(Resources_Utility.xpath("account_otp_path"), Resources_Utility.xpath("verify_OTP"),
					Resources_Utility.xpath("account_next"), "login_OTP_Error", driver);
			explicit_Wait(Resources_Utility.xpath("ac1_UserProfileBtn"), 10, "Profile Picture", driver);

			click(Resources_Utility.xpath("ac1_UserProfileBtn"), driver);
			click(Resources_Utility.xpath("ac1_Myprofile"), driver);

			explicit_Wait(Resources_Utility.xpath("ac1_ProfileEmail"), 10, "Email at Profile", driver);
			email = getText(Resources_Utility.xpath("ac1_ProfileEmail"), driver);
			verifyEquals(email, email_Id, driver);

			System.out.println("======== Academy Sign-up Testing Done ========");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(enabled = true, priority = 2,groups= {"Common"})
	public void buy_Academy_Course() throws Throwable {

		try {

			click(Resources_Utility.xpath("enroll_btn"), driver);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}