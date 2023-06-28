package PageRepo;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import GenericFile.Resources_Utility;
import taxmann.com.Phase1.AppTest;

public class Parallel_Payment_Check extends AppTest {

	public static String book_Selected=null;
	public static String book_Actual=null;
	public static String parent=null;
	public static String parentUrl=null;
	public static String platform;

	
	public Parallel_Payment_Check() throws Throwable {

		try {
			platform="Bookstore";
		//	platform="Virtual_Books";
		//	platform="e_Products";
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test(enabled = true, priority = 1)
	/*
	 * public void parallel_Payment() throws Throwable {
	 * 
	 * try { System.out.println('\n'+
	 * "======== Parallel Payment Testing of "+platform+" Started ========"); switch
	 * (platform) {
	 * 
	 * case "Bookstore": select_book(platform, driver); break; case "Virtual_Books":
	 * select_book(platform, driver); break; case "e_Products":
	 * select_book(platform, driver); break; }
	 * 
	 * }catch (Exception e) { e.printStackTrace(); } }
	 */
	@Test(enabled = true, priority = 1, groups = {"BookStore"})
	public static void select_Product() throws Throwable {

		System.out.println('\n'+ "======== Product Selection for Parallel Payment Testing of "+platform+" Started ========");
		parentUrl=driver.getCurrentUrl();
		click(Resources_Utility.xpath("research_Btn"), driver);
		try {
			if(platform.contains("Bookstore")) {
				click(Resources_Utility.xpath("bookStore_Btn"), driver);
				type(Resources_Utility.xpath("book_Search"), Resources_Utility.config("book_Search"), driver);
				enterDw(driver);
				click(Resources_Utility.xpath("bookstore_hide_Menu"), driver);

				click(Resources_Utility.xpath("sort_by_Price"), driver);
				click(Resources_Utility.xpath("filter_Availability"), driver);
				click(Resources_Utility.xpath("filter_In_Stock"), driver);
			}
			if(platform.contains("Virtual_Books")) {
				click(Resources_Utility.xpath("virtual_book"), driver);
				type(Resources_Utility.xpath("book_Search"), Resources_Utility.config("VB_Search"), driver);
				enterDw(driver);
				click(Resources_Utility.xpath("bookstore_hide_Menu"),driver);
				click(Resources_Utility.xpath("sort_by_Price"), driver);
			}
			if(platform.contains("e_Products")) {
				click(Resources_Utility.xpath("bookStore_Btn"), driver);
				click(Resources_Utility.xpath("e_Products"), driver);
				click(Resources_Utility.xpath("research_Offline"), driver);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		parent=driver.getWindowHandle();
		click(Resources_Utility.xpath("first_Book"), driver);
		book_Selected = getText(Resources_Utility.xpath("first_Book"), driver);
		System.out.println("1st Book Selected is "+book_Selected);

		window_Switch(1, tabs, driver);
		book_Actual=getText(Resources_Utility.xpath("selected_Book_Name"), driver);
		System.out.println("1st Book Opened in new tab is "+book_Actual);
		try {
			if(book_Actual.contains(book_Selected)) {
				System.out.println("-- Books Name Matched");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

		pay_via_Online_Payment(platform, driver);
		window_Switch(0, tabs, driver);
		explicit_Wait(Resources_Utility.xpath("click_second"), 5, "second_Book", driver);

		click(Resources_Utility.xpath("second_Book"), driver);
		book_Selected = getText(Resources_Utility.xpath("second_Book"), driver);
		System.out.println("2nd Book Selected is "+book_Selected);

		window_Switch(2, tabs, driver);		

		book_Actual=getText(Resources_Utility.xpath("selected_Book_Name"), driver);
		System.out.println("2nd Book Selected is "+book_Selected);
		try {
			if(book_Actual.contains(book_Selected)) {
				System.out.println("-- Books Names Matched");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		pay_via_Online_Payment(platform, driver);
		window_Switch(1, tabs, driver);
		explicit_Wait(Resources_Utility.xpath("pay_Submit"), 5, "pay button", driver);
		click(Resources_Utility.xpath("pay_Submit"), driver);

		try {
			if(element_Displayed(Resources_Utility.xpath("error_Message"),"Error Message", driver)) {
				System.out.println("-- "+getText(Resources_Utility.xpath("error_Message"), driver));
				System.out.println("-- Parallel payment of two different items not allowed!!");
			}
		}catch (Exception e) {
			System.out.println("-- Parallel payment done!!");
		}
		System.out.println('\n'+ "======== Parallel Payment Testing of "+platform+" Done ========");
	}

	public static void pay_via_Online_Payment(String platform, WebDriver driver) throws Throwable {

		try {
			if(platform.contains("Bookstore")) {

				click(Resources_Utility.xpath("buy_now"), driver);
			}
			if(platform.contains("Virtual_Books")) {

				click(Resources_Utility.xpath("VB_Buy_Now"), driver);
			}
			if(platform.contains("e_Products")) {

				click(Resources_Utility.xpath("buy_now"), driver);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		explicit_Wait(Resources_Utility.xpath("product_OnCheckout"), 10, "Product_OnCheckout", driver);
		scrollbyElement(null, 350, driver);
		click(Resources_Utility.xpath("Proceed"), driver);

		explicit_Wait(Resources_Utility.xpath("online_payment"), 10, "online_payment", driver);
		click(Resources_Utility.xpath("online_payment"), driver);
	}
}