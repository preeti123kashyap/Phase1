package PageRepo;

import org.openqa.selenium.WebDriver;

import GenericFile.Resources_Utility;
import GenericFile.common_Functions;

public class Advisory extends common_Functions {
	public String confirmation_Msg=null;
	
	public void advisory_testing(WebDriver driver) throws Throwable {

		advisory_Form(driver);
	}

	public void advisory_Form(WebDriver driver) throws Throwable {
		
		try {
			System.out.println('\n'+ "======== Advisory Form Submission Testing Started ========");

			click(Resources_Utility.xpath("research_Btn"), driver); 
			click(Resources_Utility.xpath("advisory_btn"), driver);						//Click on advisory
			explicit_Wait(Resources_Utility.xpath("request_callback_btn"), 5, "request_callback_btn", driver);
			click( Resources_Utility.xpath("request_callback_btn"),  driver);

			// Request a callback form
			explicit_Wait(Resources_Utility.xpath("get_In_Touch"), 5, "get_In_Touch", driver);
			type(Resources_Utility.xpath( "ad_name"), "Test",  driver);	
			click(Resources_Utility.xpath( "ad_designation_drp_dwn"),driver);

			explicit_Wait(Resources_Utility.xpath("ad_designation_value"), 5, "ad_designation_value", driver);
			random_Selection( Resources_Utility.xpath("ad_designation_value"),  driver);
			type(Resources_Utility.xpath( "ad_organization_name"), "Test Organization",  driver);

			type(Resources_Utility.xpath( "ad_email"), "test@gmail.com",  driver);
			type(Resources_Utility.xpath( "ad_mobile_number"), "8809090989",  driver);
			click(Resources_Utility.xpath( "ad_subject_drp_dwn"),driver);

			explicit_Wait(Resources_Utility.xpath("ad_subject_value"), 5, "ad_subject_value", driver);
			random_Selection( Resources_Utility.xpath("ad_subject_value"),  driver);
			click(Resources_Utility.xpath( "ad_services_drp_dwn"),driver);

			explicit_Wait(Resources_Utility.xpath("ad_services_value"), 5, "ad_services_value", driver);
			random_Selection( Resources_Utility.xpath("ad_services_value"),  driver);
			click( Resources_Utility.xpath("ad_datepicker"),  driver);

			click( Resources_Utility.xpath("ad_next_month_icon"),  driver);
			click( Resources_Utility.xpath("date"),  driver);
			click( Resources_Utility.xpath("ad_slot"),  driver);

			explicit_Wait(Resources_Utility.xpath("ad_slot_time"), 5, "ad_slot_time", driver);
			random_Selection( Resources_Utility.xpath("ad_slot_time"),  driver);
			click( Resources_Utility.xpath("ad_submit"),  driver);

			explicit_Wait(Resources_Utility.xpath("confirmation_Msg"), 5, "confirmation_Msg", driver);
			confirmation_Msg = getText(Resources_Utility.xpath("confirmation_Msg"), driver);
			System.out.println("-- Confirmation Message checking!!!");
			
			verifyEquals(confirmation_Msg, Resources_Utility.config("Advisory_Confirmation"), driver);
			click( Resources_Utility.xpath("ad_close"),  driver);
			System.out.println("-- Advisory Form submitted successfully!!!");

			System.out.println('\n'+ "======== Advisory Form Submission Testing Done ========");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}