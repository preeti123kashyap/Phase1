package PageRepo;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import GenericFile.Resources_Utility;
import GenericFile.common_Functions;

public class Virtual_Journals extends common_Functions {

	public static String parent=null;
	public static String journalName=null;
	public static String myColl_VJ_Name=null;
	public static int Book_Price=0;
	public static double GrandPriceFinal=0;
	public static double GST_Price=0;
	public static double Actual_GrandTotal=0;
	public static int TotalPayPriceFinal=0;
	public int x=1;


	public void check_Trial(WebDriver driver) throws Throwable {
		try {
			System.out.println('\n'+"====== VIRTUAL JOURNAL FREE TRIAL TESTING STARTED ======");	

			parent=driver.getCurrentUrl();
			moveToVJ(driver);

			List<WebElement> elements = driver.findElements(By.xpath(Resources_Utility.xpath("VJ_List")));
			do {

				driver.findElement(By.xpath("(//div[@class='row pt-5 m-0']/div)"+"["+x+"]")).click(); 									//randomly select any book from the Journals
				window_Switch(1,tabs, driver); //Navigate to other tab

				explicit_Wait(Resources_Utility.xpath("vb_Name"), 30, "Journal Name", driver);
				journalName = getText(Resources_Utility.xpath("vb_Name"), driver);								//Getting the name of journal selected
				sleep(2000);

				System.out.println("-- The Journal selected is: "+journalName);
				String readNow_text=getText(Resources_Utility.xpath("VJ_ReadNow_Text"), driver);

				if(readNow_text.contains(" days for your Free Trial")) {    			//Check Read Now button visibility 
					click(Resources_Utility.xpath("VJ_ReadNow"), driver);														//click on Read now button
					readNowTest(driver);
					break;
				}
				else if(readNow_text.contains(" days remaining for your Free Trial"))
				{
					click(Resources_Utility.xpath("VJ_ReadNow"), driver);														//click on Read now button
					issueReadNowTest(driver);
				}else if(readNow_text.contains(" days Free Trial")) {
					System.out.println("-- Trial is already taken and expired!!!!");
					driver.close();
					window_Switch(0, tabs, driver);
				}
				x++;
			}while(x<=elements.size());
		}catch (Exception e) {
			e.printStackTrace();
		}
		close_AllTabs(parent, tabs, driver);
		System.out.println("====== VIRTUAL JOURNAL FREE TRIAL TESTING DONE ======");		
	}

	//---------------------- Checking buy now on Virtual Journals ----------------------

	public void buy_Check(WebDriver driver) throws Throwable{
		try {
			System.out.println('\n'+"====== VIRTUAL JOURNAL BUY TESTING STARTED ======");	

			parent=driver.getWindowHandle();
			moveToVJ(driver);									//randomly select any book from the Journals
			random_Selection(Resources_Utility.xpath("VJ_List"), driver);

			window_Switch(1, tabs, driver);			//Navigate to other tab
			journalName = getText(Resources_Utility.xpath("vb_Name"), driver);								//Getting the name of journal selected
			System.out.println("-- The Journal selected is: "+journalName);

			Book_Price= price_Check(Resources_Utility.xpath("price_Check"), driver);
			System.out.println("-- Price of book is: Rs " +Book_Price);
			sleep(2000);

			//------- Select Month of subscription ---------

			click(Resources_Utility.xpath("VJ_SelectMonth"), driver);										//select Month for subscription
			random_Selection(Resources_Utility.xpath("VJ_Month_List"), driver);
			System.out.println("-- Year Selected is: "+random_Selection_Name(Resources_Utility.xpath("VJ_Month_List"), driver));

			//------- Select Year of subscription ---------

			click(Resources_Utility.xpath("VJ_SelectYear"), driver);										//select Month for subscription
			click(Resources_Utility.xpath("VJ_yearSelected"), driver);										//select year for subscription

			System.out.println("-- Year Selected is: "+getText(Resources_Utility.xpath("VJ_yearSelected"), driver));
			click(Resources_Utility.xpath("Subscribe_Now"), driver);										//click on subscribe now
			sleep(2000);

			GrandPriceFinal= price_Check(Resources_Utility.xpath("priceOnCheckout"), driver);				//Getting price without GST on checkout page
			GST_Price=gst_Check(GrandPriceFinal, "Virtual Journal");										//Calculating the GST included
			GrandPriceFinal = GrandPriceFinal+GST_Price;													//Calculating final price 

			System.out.println("-- Price of book on Checkout page is: Rs " +GrandPriceFinal);				//printing final price
			Actual_GrandTotal= price_Check(Resources_Utility.xpath("GrandPriceOnCheckout"), driver);		//Getting actual final price on checkout page
			verifyEquals((int) Actual_GrandTotal, (int) GrandPriceFinal, driver);							//Comparing calculated price and actual shown price

			//shipping_Address(driver);

			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,600)");														//Scrolling the window by 200 pixels vertically
			sleep(2000);

			click(Resources_Utility.xpath("proceed_To_Pay"), driver);										// clicking on proceed to pay
			sleep(2000);

			TotalPayPriceFinal= price_Check(Resources_Utility.xpath("price_On_Payment"), driver);
			System.out.println("-- Price of book on Payment page is: Rs " +TotalPayPriceFinal);

			//click(Resources_Utility.xpath("payBtn"), driver);
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println('\n'+"====== VIRTUAL JOURNAL BUY TESTING DONE ======");	
	}

	//Move to Virtual Journal 
	public void moveToVJ(WebDriver driver) {

		try {
			click(Resources_Utility.xpath("research_Btn"), driver); 												//click on research button
			click(Resources_Utility.xpath("VB_Btn"), driver); 														//click on Virtual Books tab 
			click(Resources_Utility.xpath("VJ_Btn"), driver);														//click on Virtual Journals tab sleep(2000);
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("Window not able to move to Virtual Journal");
		} 
	}

	//test VJ read now button 
	public void readNowTest(WebDriver driver) {
		try {
			if (element_Displayed(Resources_Utility.xpath("VJ_DesignationDropdown"),"Designation Dropdown", driver))  									//check the Information popup is display or not. 
			{
				click(Resources_Utility.xpath("VJ_DesignationDropdown"), driver);
				random_Selection(Resources_Utility.xpath("VJ_Designation_List"), driver);

				click(Resources_Utility.xpath("VJ_StateDropdown"), driver);		
				random_Selection(Resources_Utility.xpath("VJ_Designation_List"), driver);

				click(Resources_Utility.xpath("read_Now_Submit"), driver);
				sleep(1000);

				String successful=getText(Resources_Utility.xpath("successfulPopup"), driver);	
				verifyEquals("Your Free Trial has been activated successfully",successful, driver);  					//checking the successfull message
				click(Resources_Utility.xpath("readNowPopup"), driver);													//click on read now 
				issueReadNowTest(driver);
			}

		} catch (Throwable e) {
			// TODO Auto-generated catch block
			System.out.println("Information Popup is not display ");
			e.printStackTrace();
		}

	}

	//check Read now in VJ issue is working or not.
	public void issueReadNowTest(WebDriver driver) throws Throwable
	{
		sleep(1000);
		myColl_VJ_Name = getText(Resources_Utility.xpath("my_Coll_VJ_Name"),driver);
		System.out.println("-- Virtual Journal name: "+myColl_VJ_Name);
		try {
			verifyEquals(journalName,myColl_VJ_Name, driver);
			scrollbyElement(null, 400, driver);
			if(element_Displayed(Resources_Utility.xpath("issue_ReadNow"), "Read Now", driver)) {
				click(Resources_Utility.xpath("issue_ReadNow"),driver);												//Clicking on journal's issue read now button
				sleep(5000);
				window_Switch(2, tabs, driver);
				driver.close();
				window_Switch(1, tabs, driver);
			} 

		} catch (IOException e) {
			e.printStackTrace();
		}

		driver.close();																							//closing the child window
		driver.switchTo().window(parent);																				//switching to main window
		sleep(1000);
	}

	public  void myCollection_Check(WebDriver driver) throws Throwable {
		try {

			System.out.println("-- This Virtual Journal Trail Already Taken"); 
			myColl_VJ_Name = getText(Resources_Utility.xpath("my_Coll_VJ_Name"), driver);							//Getting name of journal
			System.out.println("-- Virtual Journal name: "+myColl_VJ_Name);

			verifyEquals(journalName,myColl_VJ_Name, driver);  														//comparing the name of VB in trial
			scrollbyElement(null, 200, driver);

			try {
				if(element_Displayed(Resources_Utility.xpath("issue_ReadNow"), "Issue Read Now", driver)) {
					click(Resources_Utility.xpath("issue_ReadNow"),driver);		//Clicking on journal's issue read now button
					sleep(5000);

					window_Switch(2, tabs, driver);
					close_AllTabs(parent, tabs, driver);
				}
			}catch (Exception e1) {
				driver.close();																						//closing the child window
				driver.switchTo().window(parent);																	//switching to main window
				sleep(2000);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}