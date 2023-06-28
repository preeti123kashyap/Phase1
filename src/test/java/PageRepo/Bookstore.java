package PageRepo;

import org.testng.annotations.Test;

import GenericFile.Resources_Utility;
import taxmann.com.Phase1.AppTest;

public class Bookstore extends AppTest {

	public static String book_Selected = null;
	public static String book_Actual = null;
	public static String confirmed_OrderNo = null;
	public static String parentUrl = null;

	@Test(enabled = true, priority = 1, groups = {"BookStore"})
	public void BookStore_Search() throws Throwable {

		try {
			System.out.println('\n' + "======== BookStore Search testing Started ========");
			parentUrl = driver.getCurrentUrl();

			click(Resources_Utility.xpath("research_Btn"), driver);
			click(Resources_Utility.xpath("bookStore_Btn"), driver);
			type(Resources_Utility.xpath("book_Search"), Resources_Utility.config("book_Search"), driver);
			enterDw(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(enabled = true, priority = 2, groups = {"BookStore"})
	public static void filter_Selection() throws Throwable {

		try {
			click(Resources_Utility.xpath("bookstore_hide_Menu"), driver);
			click(Resources_Utility.xpath("sort_by_Publication"), driver);
			click(Resources_Utility.xpath("sort_by_Price"), driver);
			click(Resources_Utility.xpath("filter_Availability"), driver);
			click(Resources_Utility.xpath("filter_In_Stock"), driver);
			sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(enabled = true, priority = 3, groups = {"BookStore"})
	public static void book_Selection() throws Throwable {

		try {
			explicit_Wait(Resources_Utility.xpath("all_Books"), 10, "All Books", driver);
			random_Selection(Resources_Utility.xpath("all_Books"), driver);

			book_Selected = random_Selection_Name(Resources_Utility.xpath("all_Books"), driver);
			System.out.println("-- Book Selected from list is - " + book_Selected);

			window_Switch(1, tabs, driver);
			explicit_Wait(Resources_Utility.xpath("selected_Book_Name"), 5, "selected Book Name", driver);
			book_Actual = getText(Resources_Utility.xpath("selected_Book_Name"), driver);
			System.out.println("-- Actual Book opened in New tab is - " + book_Actual);
			try {
				if (book_Actual.contains(book_Selected)) {
					System.out.println("-- Books Names Are Matched");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(enabled = true, priority = 4, groups = {"BookStore"})
	public void notify_Me_Check() throws Throwable {
		try {
			if (element_Displayed(Resources_Utility.xpath("notify_Me"), "Notify Me", driver)) {
				driver.close();
				window_Switch(0, tabs, driver);
				book_Selection();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(enabled = true, priority = 5, groups = {"BookStore"})
	public void view_ContentCheck() throws Throwable {
		try {
			if (element_Displayed(Resources_Utility.xpath("view_content"), "View Content", driver)) {

				verify_view_content_extension(Resources_Utility.xpath("view_content"), driver);
			}
		} catch (Exception e) {
			System.out.println("-- View Content option is not available");
		}
	}

	@Test(enabled = true, priority = 6, groups = {"BookStore"})
	public void add_To_Cart() throws Throwable {

		try {
			System.out.println("======= BookStore Order Placed Started========");
			scrollbyElement(null, 300, driver);
			explicit_Wait(Resources_Utility.xpath("add_To_Cart"), 5, "Add To Cart", driver);
			click(Resources_Utility.xpath("add_To_Cart"), driver);

			explicit_Wait(Resources_Utility.xpath("add_To_Cart_Message"), 5, "Add To Cart Message Pop-up", driver);
			verifyEquals(getText(Resources_Utility.xpath("add_To_Cart_Message"), driver),
					Resources_Utility.config("add_To_Cart_MSG"), driver);
			click(Resources_Utility.xpath("pop_up_Close"), driver);

			click(Resources_Utility.xpath("open_cart"), driver);
			click(Resources_Utility.xpath("increase_quantity"), driver);
			click(Resources_Utility.xpath("checkout"), driver);

			// shipping_Address(driver);

			explicit_Wait(Resources_Utility.xpath("product_OnCheckout"), 10, "Product_OnCheckout", driver);
			scrollbyElement(null, 500, driver);
			click(Resources_Utility.xpath("check"), driver);
			verify_total_amount(Resources_Utility.xpath("sub_total"), Resources_Utility.xpath("Grand_total"), driver);

			click(Resources_Utility.xpath("Proceed"), driver);
			click(Resources_Utility.xpath("neft"), driver);
			click(Resources_Utility.xpath("pay"), driver);

			explicit_Wait(Resources_Utility.xpath("confirmation_Order_No"), 5, "Order Confirmation", driver);
			System.out.println("-- Payment Successful");

			confirmed_OrderNo = getText(Resources_Utility.xpath("confirmation_Order_No"), driver);
			confirmed_OrderNo = confirmed_OrderNo.replaceAll("[^0-9]", "");
			System.out.println("-- Order No. is - " + confirmed_OrderNo);

			System.out.println("======= BookStore Order Placed ========");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(enabled = true, priority = 7, groups = {"BookStore"})
	public void cancel_BookOrder() throws Throwable {
		close_AllTabs(parentUrl, tabs, driver);
		cancel_Order(confirmed_OrderNo, driver);
	} 
}