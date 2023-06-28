package PageRepo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import GenericFile.Resources_Utility;
import taxmann.com.Phase1.AppTest;

public class Virtual_Books extends AppTest{

	public static String parent=null;
	public static String myColl_bookName=null;
	public static String bookName=null;
	public static int Total_Amount=0;
	public static int priceWithout_GST=0;
	public static double GST_Price=0;
	public static double Expected_Price=0;
	public static double GrandPriceFinal=0;
	public static int TotalPayPriceFinal=0;
	public static String message=null;
	boolean readNowStatus = false;

	@Test(enabled = true, priority = 1, groups = {"Virtual Books and Journals"})
	public void check_Trial(WebDriver driver) throws Throwable{

		try {	
			System.out.println('\n'+"====== VIRTUAL BOOK FREE TRIAL TESTING STARTED ======");	
			parent = driver.getCurrentUrl();
			moveToVB(driver);

			do {
				random_Selection(Resources_Utility.xpath("category_List"), driver);								//randomly select a list from professional category
				random_Selection(Resources_Utility.xpath("book_List"), driver);									//randomly select any book from the category

				window_Switch(1, tabs, driver);																	//Navigate to other tab
				bookName = getText(Resources_Utility.xpath("vb_Name"), driver);
				System.out.println("The book selected is: "+bookName);
				try {
					if(element_Displayed(Resources_Utility.xpath("read_Now"), "read_Now", driver)){										//Check Read Now button visibility
						String msg = getText(Resources_Utility.xpath("read_Now"), driver);
						click(Resources_Utility.xpath("read_Now"), driver);														//click on Read now button
						try {
							if(msg.contains(" 15 minutes Free Trial ")){
								
								if (element_Displayed(Resources_Utility.xpath("Get_Free_Trial"),"Get_Free_Trial", driver))
								{		 
									click(Resources_Utility.xpath("designation_Dropdown"), driver);
									random_Selection(Resources_Utility.xpath("designation_Selection"), driver);

									click(Resources_Utility.xpath("VJ_StateDropdown"), driver);										//click on State dropdown button
									random_Selection(Resources_Utility.xpath("VJ_Designation_List"), driver);

									click(Resources_Utility.xpath("read_Now_Submit"), driver);
									click(Resources_Utility.xpath("close_Pop_up"), driver);											//close pop-up window
									myCollection_Check(bookName, driver);
								}
							}else if(msg.contains(" minutes remaining for your Free Trial ")) {

								System.out.println("-- This books trial is curretnly active!!!!");
								sleep(5000);
								close_AllTabs(parent, tabs, driver);
							}
						}catch (Exception e) {

							if(element_Displayed(Resources_Utility.xpath("existing_Trial"),"Existing Trial", driver)) {

								message=getText(Resources_Utility.xpath("existing_Trial"), driver);
								System.out.println("-- "+message);
								String[] bookName1=message.split("Free");

								System.out.println("-- Existing trial Virtual Book is - "+bookName1[0]);
								click(Resources_Utility.xpath("close_Pop_up"), driver);												//close the  pop-up
								myCollection_Check(bookName, driver);
							}else {
								e.printStackTrace();
							}
						}
					}
				}catch (Exception e) {

					System.out.println("-- This Virtual book's Trail Already Taken in Past"); 
					driver.close();
					window_Switch(0, tabs, driver);	
					readNowStatus=true;
				}	
			}while(readNowStatus);
			System.out.println("====== VIRTUAL BOOK FREE TRIAL TESTING DONE ======");	
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	//------------------------ Check Buy Now on Virtual Books ------------------------  //

	public void buy_Check(WebDriver driver) throws Throwable{

		try {
			System.out.println('\n'+"====== VIRTUAL BOOK BUY TESTING STARTED ======");	

			moveToVB(driver);
			random_Selection(Resources_Utility.xpath("category_List"), driver);								//randomly select a list from professional category
			random_Selection(Resources_Utility.xpath("book_List"), driver);									//randomly select any book from the category

			window_Switch(1, tabs, driver);	
			Total_Amount = price_Check(Resources_Utility.xpath("price_Check"), driver);						//Getting the price value from VB detail page
			System.out.println("-- Price of book is: Rs " +Total_Amount);

			click(Resources_Utility.xpath("buy_Now"), driver);												// clicking buy now
			sleep(3000);

			priceWithout_GST = price_Check(Resources_Utility.xpath("priceOnCheckout"), driver);				//Getting price without GST on checkout page
			System.out.println("-- Price without GST is: "+priceWithout_GST);

			GST_Price=gst_Check(priceWithout_GST, "Virtual Book");											//checking the GST inclusive on VB
			Expected_Price=priceWithout_GST+GST_Price;														//Adding GST in price without GST

			GrandPriceFinal= price_Check(Resources_Utility.xpath("GrandPriceOnCheckout"), driver);			//Getting price with GST on checkout page
			System.out.println("-- Price of book on Checkout page is: Rs " +GrandPriceFinal);	

			verifyEquals((int)Expected_Price, (int)GrandPriceFinal, driver);								//Comparing prices
			click(Resources_Utility.xpath("proceed_To_Pay"), driver);										// clicking on proceed to pay

			TotalPayPriceFinal= price_Check(Resources_Utility.xpath("price_On_Payment"), driver);			//Getting price on payment page
			System.out.println("-- Price of book on Payment page is: Rs " +TotalPayPriceFinal);

			verifyEquals(Total_Amount, TotalPayPriceFinal, driver);

		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("====== VIRTUAL BOOK BUY TESTING DONE ======");	
	}

	//Move to Virtual Journal 
	public void moveToVB(WebDriver driver) {

		try {
			click(Resources_Utility.xpath("research_Btn"), driver); 												//click on research button
			click(Resources_Utility.xpath("VB_Btn"), driver);														//click on Virtual Books tab
			click(Resources_Utility.xpath("professional_Btn"), driver);												//click on professional category
			explicit_Wait(Resources_Utility.xpath("category_List"), 10 , "Category List", driver);
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("Window not able to move to Virtual Journal");
		} 
	}

	public static void myCollection_Check(String bookName, WebDriver driver) throws Throwable{

		try {
			click(Resources_Utility.xpath("my_Collection"), driver);											//click on My Collection Tab
			click(Resources_Utility.xpath("my_Collection_VB"), driver);										//click on Virtual Books tab in My Collection
			sleep(2000);

			myColl_bookName=driver.findElement(By.xpath("//div[@class='card-body']/div/a[1]")).getAttribute("title");
			verifyEquals(bookName,myColl_bookName, driver);  											//comparing the name of VB in trial
			System.out.println("-- Book in My Collection - "+myColl_bookName);
			click(Resources_Utility.xpath("my_Coll_VB_1"),driver);

			sleep(4000);
			close_AllTabs(parent, tabs, driver);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
