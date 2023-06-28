package PageRepo;

import org.testng.annotations.Test;

import GenericFile.Resources_Utility;
import taxmann.com.Phase1.AppTest;
import taxmann.com.Phase1.AppTest2;

public class Journals extends AppTest {

	public String parent=null;
	public int randomNumber;
	public String T1;
	public static String confirmed_OrderNo;
	public static String orderNO;

	@Test(enabled = true, priority = 1, groups = {"BookStore"})
	public void navigate_To_Journals() throws Throwable {
		try {

			System.out.println('\n'+ "======== BookStore Journals Testing Started ========");
			parent=driver.getCurrentUrl();

			click(Resources_Utility.xpath("research_Btn"), driver);			//Click on Research button
			click(Resources_Utility.xpath("bookStore_Btn"), driver);		//Click on Bookstore button
			explicit_Wait(Resources_Utility.xpath("journal_Btn"), 5, "Journal Option", driver);
			click(Resources_Utility.xpath("journal_Btn"), driver);			//Click on Journals button
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(enabled = true, priority = 2, groups = {"BookStore"})
	public void select_Journals() throws Throwable {
		try {
			explicit_Wait(Resources_Utility.xpath("all_Journals"), 5, "Journals", driver);
			random_Selection(Resources_Utility.xpath("all_Journals"), driver);	//Randomly select any journals
			String Expected_Journal_name = random_Selection_Name(Resources_Utility.xpath("all_Journals"), driver); //Getting the name of selected journals
			System.out.println("-- Journal Selected is - "+Expected_Journal_name);

			window_Switch(1, tabs, driver);
			String Actual_Journal_name=getText(Resources_Utility.xpath("selected_Journal_Name"), driver); //Getting the name of journal
			System.out.println("-- Journal opened in new tab is - "+Actual_Journal_name);

			boolean check = Actual_Journal_name.contains(Expected_Journal_name);	//Verifying the names
			if(check)
			{
				System.out.println("-- Journal Name matched");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(enabled = true, priority = 3, groups = {"BookStore"})
	public void view_Content() throws Throwable {

		try {
			if(element_Displayed(Resources_Utility.xpath("view_content"),"View Content", driver)) {
				verify_view_content_extension(Resources_Utility.xpath("view_content"), driver);
			}
		}catch (Exception e) {
			System.out.println("-- View Content option is not available");
		}
	}

	@Test(enabled = true, priority = 4, groups = {"BookStore"})
	public void add_To_Cart_Journals() throws Throwable {
		try {
			scrollbyElement(null, 400, driver);
			click(Resources_Utility.xpath("add_To_Cart"), driver);
			explicit_Wait(Resources_Utility.xpath("add_To_Cart_Message"), 2, "add To Cart Message", driver);
			
			verifyEquals(getText(Resources_Utility.xpath("add_To_Cart_Message"),driver),  Resources_Utility.config("add_To_Cart_MSG"), driver);
			click(Resources_Utility.xpath("pop_up_Close"), driver);
			click(Resources_Utility.xpath("open_cart"), driver);
			
			explicit_Wait(Resources_Utility.xpath("increase_quantity"),10, "increase_quantity", driver);
			click(Resources_Utility.xpath("increase_quantity"), driver);

			explicit_Wait(Resources_Utility.xpath("unit_price"), 10, "Unit Price", driver);
			verify_price(Resources_Utility.xpath("unit_price"), Resources_Utility.xpath("subtotal"), driver);
			click(Resources_Utility.xpath("checkout"), driver);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}		
	
	@Test(enabled = true, priority = 5, groups = {"BookStore"})
	public void shippingAddress_Journals() throws Throwable {
		try {
			explicit_Wait(Resources_Utility.xpath("shipping_address"), 10, "Shipping Address", driver);
			verify_address( Resources_Utility.xpath("shipping_address"), Resources_Utility.xpath("check"), Resources_Utility.xpath("billing_address"), driver);
			explicit_Wait(Resources_Utility.xpath("Proceed"), 10, "Proceed TO Pay", driver);
			click(Resources_Utility.xpath("Proceed"), driver);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	@Test(enabled = true, priority = 6, groups = {"BookStore"})
	public void payment_Journals() throws Throwable {
		try {
			explicit_Wait(Resources_Utility.xpath("online_payment"), 2, "Online Payment", driver);
			verify_payment_Mode("Online Payment", Resources_Utility.xpath("online_payment"), Resources_Utility.xpath("payable_Amount"), Resources_Utility.xpath("payAmount_OnCart"), driver);
			try {
				if(element_Displayed(Resources_Utility.xpath("No_cost_Emi"), "No Cost EMI Option",driver)) {
					verify_payment_Mode("No cost Emi", Resources_Utility.xpath("No_cost_Emi"), Resources_Utility.xpath("payable_Amount"), Resources_Utility.xpath("payAmount_OnCart"), driver);	
				}
			}catch (Exception e) {
				verify_payment_Mode("Cash On Delivery", Resources_Utility.xpath("cash_On_Delivery"), Resources_Utility.xpath("payable_Amount"), Resources_Utility.xpath("payAmount_OnCart"), driver);
			}

			verify_payment_Mode("NEFT", Resources_Utility.xpath("neft"), Resources_Utility.xpath("payable_Amount"), Resources_Utility.xpath("payAmount_OnCart"), driver);
			verify_payment_Mode("CHEQUE", Resources_Utility.xpath("cheque"), Resources_Utility.xpath("payable_Amount"), Resources_Utility.xpath("payAmount_OnCart"), driver);

			click(Resources_Utility.xpath("neft"), driver);
			click(Resources_Utility.xpath("pay"), driver);

			explicit_Wait(Resources_Utility.xpath("confirmation_Order_No"), 5, "Confirmation Order No", driver);
			System.out.println('\n'+"-- Payment Successful");

			confirmed_OrderNo=getText(Resources_Utility.xpath("confirmation_Order_No"), driver);
			confirmed_OrderNo=confirmed_OrderNo.replaceAll("[^0-9]", "");
			System.out.println("-- Order No. is - '"+confirmed_OrderNo+"'");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(enabled = true, priority = 7, groups = {"BookStore"})
	public void cancel_JournalsOrder() throws Throwable {
		
		close_AllTabs(parent, tabs, driver);
		cancel_Order(confirmed_OrderNo, driver);
		System.out.println('\n'+ "======== BookStore Journals Testing Done ========");

	}	
}