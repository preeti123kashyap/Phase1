package PageRepo;

import java.util.ArrayList;
import org.openqa.selenium.WebDriver;
import GenericFile.Resources_Utility;
import GenericFile.common_Functions;

public class Webinars extends common_Functions{
	public static ArrayList<String> tabs;
	public 	static String filterName;
	public static int webinar_Price;
	public static int actual_PriceDisplayed;
	public static String webinarName=null;
	public static String webinarSelection=null;
	public static String message=null;
	public static String parentUrl=null;
	public static String parentWinHandle=null;

	public void webinars(String webinarSelection, WebDriver driver) throws Throwable {
		System.out.println('\n'+"========= Webinar Testing Started =========");
		parentUrl=driver.getCurrentUrl();				
		click(Resources_Utility.xpath("research_Btn"), driver);
		click(Resources_Utility.xpath("bookStore_Btn"), driver);

		explicit_Wait(Resources_Utility.xpath("webinar"), 5, "Webinar Option", driver);
		click(Resources_Utility.xpath("webinar"), driver);
		window_Switch(1, tabs,driver);

		switch (webinarSelection) {

		case "Upcoming_Webinar":
			webinar_Selection(webinarSelection, driver);
			break;

		case "Past_Webinar":
			webinar_Selection(webinarSelection, driver);
			break;
		}
	}

	public static void webinar_Selection(String webinarSelection, WebDriver driver) throws Throwable {

		scrollbyElement(null, 500, driver);
		window_Switch(1,  tabs, driver);

		try {

			if(webinarSelection.contains("Upcoming_Webinar")){
				System.out.println('\n'+"========= "+webinarSelection+" Selected =========");
				if(element_Displayed(Resources_Utility.xpath("No_Webinar"), "No Webinar Message", driver)) {

					explicit_Wait(Resources_Utility.xpath("No_Webinar_Text"), 5, "No_Webinar_Text", driver);
					message=getText(Resources_Utility.xpath("No_Webinar_Text"), driver);
					System.out.println(message+'\n');
					click(Resources_Utility.xpath("past_Webinars"), driver);

					if(element_Displayed(Resources_Utility.xpath("No_Webinar"), "No Webinar Messsage", driver)) {
						message=getText(Resources_Utility.xpath("No_Webinar_Text"), driver);
						System.out.println(message+'\n');
						System.out.println("-- Currently there is not webinar session available at all!!!!!"+'\n');
						close_AllTabs(parentUrl, tabs, driver);
					}
				}
			}
			if(webinarSelection.contains("Past_Webinar")){
				System.out.println("========= "+webinarSelection+"  Selected ========="+'\n');

				explicit_Wait(Resources_Utility.xpath("past_Webinars"), 5, "Past_Webinars", driver);
				click(Resources_Utility.xpath("past_Webinars"), driver);
				if(element_Displayed(Resources_Utility.xpath("No_Webinar"), "No Webinar Messsage", driver)) {
					message=getText(Resources_Utility.xpath("No_Webinar_Text"), driver);
					System.out.println(message+'\n');
					explicit_Wait(Resources_Utility.xpath("upcoming_webinars"), 5, "Past_Webinars", driver);
					click(Resources_Utility.xpath("upcoming_webinars"), driver);
					if(element_Displayed(Resources_Utility.xpath("No_Webinar"), "No Webinar Messsage", driver)) {
						message=getText(Resources_Utility.xpath("No_Webinar_Text"), driver);
						System.out.println(message+'\n');
						System.out.println("-- Currently there is not webinar session available at all!!!!!"+'\n');	
						close_AllTabs(parentUrl, tabs, driver);
					}
				}
			}
		}catch (Exception e) {
			explicit_Wait(Resources_Utility.xpath("filter_Arrow"), 3, "filter_Arrow", driver);
			click(Resources_Utility.xpath("filter_Arrow"), driver);
			random_Selection(Resources_Utility.xpath("all_Filters_Webinar"), driver);
			filterName= getText(Resources_Utility.xpath("selected_Filter"), driver);
			System.out.println("========= Filter "+filterName+" Selected =========");

			parentWinHandle=driver.getWindowHandle();
			random_Selection(Resources_Utility.xpath("all_Webinars"), driver);

			window_Switch(1, tabs, driver);
			explicit_Wait(Resources_Utility.xpath("all_Webinars"), 3, "all_Webinars", driver);
			webinarName = random_Selection_Name(Resources_Utility.xpath("all_Webinars"), driver);
			System.out.println("Webinar Name is: " +webinarName);

			String webinarPrice = random_Selection_Name(Resources_Utility.xpath("all_Webinars_Price"), driver);

			if(webinarPrice.contains("Free")) {
				System.out.println("Webinar is FREE OF COST!!!!!!");
			}
			else {
				String[] price1=webinarPrice.split(":");
				price1[1]=price1[1].replaceAll("[^0-9]", "");
				webinar_Price= Integer.parseInt(price1[1]);
				System.out.println("Webinar Price is: Rs."+webinar_Price);
			}

			window_Switch(2, tabs, driver);

			try {
				if(filterName.contains("All Webinars")) {
					if(webinarPrice.contains("Free")) {
						free_webinar(driver);
					}else {
						paid_webinar(webinarPrice, driver);
					}
				}
				if(filterName.contains("Paid Webinars")) {
					paid_webinar(webinarPrice, driver);

				}
				if(filterName.contains("Free Webinars")) {
					if(webinarSelection.contains("Upcoming_Webinar")) {
						paid_webinar(webinarPrice, driver);
					}else {
						free_webinar(driver);
					}
				}	
				System.out.println("========= Webinar Testing Done =========");
				close_AllTabs(parentUrl, tabs, driver);
			}catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	public static void free_webinar(WebDriver driver) throws Throwable
	{
		scrollbyElement(null, 1000, driver);
		try {
			explicit_Wait(Resources_Utility.xpath("free_Webinar_VideoLink"), 3, "free_Webinar_VideoLink", driver);
			if(element_Displayed(Resources_Utility.xpath("free_Webinar_VideoLink"), "Free Webinar Video Link", driver))
			{
				System.out.println("Video Link is present!");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void paid_webinar(String webinarPrice, WebDriver driver) throws Throwable
	{   
		try {
			String actual_PriceonDisplay = getText(Resources_Utility.xpath("webinar_price"), driver);
			actual_PriceDisplayed=Integer.parseInt(actual_PriceonDisplay.replaceAll("[^0-9]", ""));
			System.out.println("Webinar Actual Price Dsiplayed: Rs."+actual_PriceDisplayed);
			verifyEquals(actual_PriceDisplayed, webinar_Price, driver);

			if(webinarPrice.contains("Free")) {
				registeration_form(driver);
				if(element_Displayed(Resources_Utility.xpath("free_RegConfirm"), "Free Registration Confirmation", driver)) {
					System.out.println("====== Upcoming Free Webinar Registartion Completed ======"+'\n');
				}
			}

			else {
				String gst=getText(Resources_Utility.xpath("webinar_GST"), driver);
				int gst1= Integer.parseInt(gst.replaceAll("[^0-9]", ""));
				System.out.println("GST is: " +gst1+" percent");

				int Total_price=actual_PriceDisplayed+(actual_PriceDisplayed*gst1)/100;
				System.out.println("Total price is: Rs."+Total_price);

				registeration_form(driver);
				String p2=getText(Resources_Utility.xpath("pay_now"),driver);
				int price2= Integer.parseInt(p2.replaceAll("[^0-9]", ""));
				System.out.println("Price is:" +price2);
				verifyEquals(Total_price,price2,driver);
				explicit_Wait(Resources_Utility.xpath("close"), 3, "close", driver);
				click(Resources_Utility.xpath("close"), driver);
				sleep(2000);
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void registeration_form(WebDriver driver) throws Throwable
	{
		try {
			click(Resources_Utility.xpath("Register_Now"), driver);
			type(Resources_Utility.xpath("first_name"),Resources_Utility.config("first_name"),driver);
			type(Resources_Utility.xpath("last_name"),Resources_Utility.config("last_name"),driver);
			type(Resources_Utility.xpath("phone"),Resources_Utility.config("mobile_number"),driver);
			type(Resources_Utility.xpath("email"),Resources_Utility.config("email_address"),driver);
			type(Resources_Utility.xpath("pincode"),Resources_Utility.config("Pin_Code"),driver);
			type(Resources_Utility.xpath("company_name"),Resources_Utility.config("Company_Name"),driver);
			click(Resources_Utility.xpath("Designation"),driver);
			random_Selection(Resources_Utility.xpath("Designation_value"),driver);
			click(Resources_Utility.xpath("submit_Btn"),driver);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}