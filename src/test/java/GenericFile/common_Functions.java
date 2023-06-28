package GenericFile;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

public class common_Functions extends selenium_utility {

	public static int randomNumber=0;
	public static String orderNO=null;
	public static String otp_txt="";
	public static ArrayList<String> tabs;
	static Random rand=new Random();

	//Sign-up using new account Id
	public static void sign_up(String email_Id,WebDriver driver) throws Throwable {

		try {
			click(Resources_Utility.xpath("create_ActBtn"), driver);
			type(Resources_Utility.xpath("acount_name"),Resources_Utility.config("first_name"),driver);						//filling of sign up form
			type(Resources_Utility.xpath("account_Lname"),Resources_Utility.config("last_name"),driver);

			type(Resources_Utility.xpath("account_email"),email_Id,driver);
			type(Resources_Utility.xpath("account_password"),Resources_Utility.config("password"),driver);
			click(Resources_Utility.xpath("account_next"),driver);									//click on next button

			alert_check(null, "account_next", driver);
			mobile_registration(driver);
			interest_Page(driver);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Login using existing Id
	public void login(String email_Id, WebDriver driver) throws Throwable {

		try {
			
			click(Resources_Utility.xpath("login_WithEmail"), driver);
			type(Resources_Utility.xpath("email_input"), Resources_Utility.config("id"), driver); 
			type(Resources_Utility.xpath("pwd_input"),Resources_Utility.config("pwd"), driver);
			click(Resources_Utility.xpath("sign_btn"), driver);

			alert_check(email_Id, "sign_btn", driver);
			if(element_Displayed(Resources_Utility.xpath("profile_Btn"),"Profile Button", driver)) {
				System.out.println("-- Logged in Successfully!!!!!");
			}else {
				alert_check(email_Id,"sign_btn", driver);
				if(element_Displayed(Resources_Utility.xpath("account_mobile_path"), "'Mobile No not registered'", driver)) {
					mobile_registration(driver);
				}
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}	

	// Alert Messages Check
	public static void alert_check(String email_Id, String btn, WebDriver driver) throws Throwable{

		try {
			if(element_Displayed(Resources_Utility.xpath("alert_Pop_Up"), "Alert Message", driver)) {
				String alert_Msg=getText(Resources_Utility.xpath("alert_Pop_Up"), driver);
				System.out.println("-- Error - "+alert_Msg);

				switch (alert_Msg) {
				case "This account does not exist. Create a new account":
					sign_up(email_Id, driver);
					break;

				case "There is some network issue. Please try again later.":
					do { 
						click(Resources_Utility.xpath(btn), driver);
					}while(element_Displayed(Resources_Utility.xpath("alert_Pop_Up"),"'Network_Error'", driver));
					break;

				case "Invalid OTP. Please enter correct OTP.":
					do{
						sleep(1000);
						fake_mobile_otp(driver);			
					}while(element_Displayed(Resources_Utility.xpath("alert_Pop_Up"), "Invalid OTP Error", driver));
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Mobile Registration
	public static void mobile_registration(WebDriver driver) throws Throwable{

		try {
			String phone_No = fake_mobileNumber(driver);
			type(Resources_Utility.xpath("account_mobile_path"), phone_No, driver);
			click(Resources_Utility.xpath("getMobile_otp_btn"), driver);

			if(element_Displayed(Resources_Utility.xpath("alreadyUsedmobile_Msg"), "Mobile No. Already in Use", driver)) {
				click(Resources_Utility.xpath("alreadyUsedmobile_Yes"),driver);
			}
			fake_mobile_otp(driver);
			typeOTP(Resources_Utility.xpath("account_otp_path"),Resources_Utility.xpath("verify_OTP"),Resources_Utility.xpath("account_next"),"login_OTP_Error", driver);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	//fake mobile number
	public static String fake_mobileNumber( WebDriver driver) throws Throwable{
		String ph=null;
		String otp_url="https://spytm.com/";
		try {
			
			((JavascriptExecutor) driver).executeScript("window.open('"+otp_url+"','_blank');");
			sleep(2000);
			window_Switch(1, tabs, driver);
			sleep(3000);
		
			
			
			List<WebElement> elements = driver.findElements(By.xpath(Resources_Utility.xpath("country_code")));
			System.out.println("size" +elements.size());
			for(int i=0; i<elements.size();i++)
			{
				//filter_Selected = driver.findElement(By.xpath("(//div[@class='filter-tag d-flex flex-wrap overflow-hidden']/a)"+"["+i+"]")).getAttribute("title");
			//	String x="(elements"+"["+i+"])";
				System.out.println(elements.get(i).getText().substring(0,5));
				String x=elements.get(i).getText().substring(0,5);
				sleep(2000);
				if(x.equals("(+91)"))
				{	
					elements.get(i).click();
					break;
				}
				}
			
			click(Resources_Utility.xpath("read_more_btn"), driver);
			
			//	driver.findElement(By.xpath(country_code)).click();
			
			  scrollbyElement(Resources_Utility.xpath("Indian_No"), 0, driver);
			 ph= getText(Resources_Utility.xpath("Indian_No"), driver);
			 ph.trim().substring(3, 13);
			 window_Switch(0, tabs, driver);
			 
			  /* scrollbyElement(Resources_Utility.xpath("online_PhoneNo"), 0, driver);
			 * explicit_Wait(Resources_Utility.xpath("online_PhoneNo"), 10, "Indian Number",
			 * driver);
			 * 
			 * click(Resources_Utility.xpath("online_PhoneNo"), driver); ph =
			 * getText(Resources_Utility.xpath("Online_PhoneNo1"), driver); ph =
			 * ph.trim().substring(3, 13); window_Switch(0, tabs, driver);
			 */
		}catch (Exception e) {
			e.printStackTrace();
		}
		return ph;
	}

	//fake mobile number otp
	public static int fake_mobile_otp(WebDriver driver) throws Throwable{
		int otp=0;
		try {
			
			window_Switch(1, tabs, driver);
			driver.navigate().refresh();
			click(Resources_Utility.xpath("read_more_btn"), driver);
			if(element_Displayed(Resources_Utility.xpath("fake_otp"), "Taxmann OTP Message", driver)) {
				otp_txt=getText(Resources_Utility.xpath("fake_otp"),  driver);
			}else if(otp_txt.contains("")) {
				do {
					driver.navigate().refresh();
					explicit_Wait(Resources_Utility.xpath("fake_otp"), 10, "Taxmann OTP Message", driver);
					if(element_Displayed(Resources_Utility.xpath("fake_otp"), "Taxmann OTP Message", driver)) {
						otp_txt=getText(Resources_Utility.xpath("fake_otp"),  driver);
					}
				}while(otp_txt.isBlank());
			}
			otp_txt=otp_txt.replaceAll("[^0-9]", "");
			otp = Integer.parseInt(otp_txt);
			System.out.println("-- OTP is :" +otp);
			window_Switch(0, tabs, driver);	
		}catch (Exception e) {
			e.printStackTrace();
		}
		return otp;
	}

	// Type the OTP
	public static void typeOTP(String otp_Path, String verify_otp, String next, String otp_Alert, WebDriver driver) throws Throwable{
		try {
			driver.findElement(By.xpath(otp_Path)).clear();
			driver.findElement(By.xpath(otp_Path)).sendKeys(otp_txt);
			driver.findElement(By.xpath(verify_otp)).click();
			driver.findElement(By.xpath(next)).click();
			alert_check(null, null, driver);
			System.out.println("-- OTP Verified Successfully!!!");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	//Interest Page
	public static void interest_Page(WebDriver driver) throws Throwable{

		try {
			sleep(2000);
			click(Resources_Utility.xpath("select_StateBtn"), driver);
			explicit_Wait(Resources_Utility.xpath("state_list"), 5, "state_list", driver);
			random_Selection(Resources_Utility.xpath("state_list"), driver);
			String State=getText(Resources_Utility.xpath("selected_State"), driver);

			System.out.println("-- State Selcted - '"+State+"'");
			random_Selection(Resources_Utility.xpath("select_Profession"), driver);
			String profession = random_Selection_Name(Resources_Utility.xpath("select_Profession"), driver);

			System.out.println("-- Profession Selected - '"+profession+"'");
			if(profession.contains("Other")) {
				type(Resources_Utility.xpath("other_Profession"), "New Profession", driver);
			}

			random_Selection(Resources_Utility.xpath("select_AreaOfInterest"), driver);
			String areaOf_Interest = random_Selection_Name(Resources_Utility.xpath("select_AreaOfInterest"), driver);
			System.out.println("-- Area of Interest Selected - '"+areaOf_Interest+"'");

			scrollbyElement(null, 200, driver);
			click(Resources_Utility.xpath("account_next"), driver);	
			alert_check(null,"account_next", driver);

			System.out.println("-- Interest Page Filled");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Date sort check
	public static void date_check(String path, WebDriver driver) throws ParseException {
		try {
			/*
			 * //Scroll till listed element is visible JavascriptExecutor js =
			 * (JavascriptExecutor) driver;
			 * //js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
			 * js.executeScript("arguments[0].scrollIntoView();",
			 * driver.findElement(By.xpath("//span[text()='1']")));
			 */
			List<WebElement> dateList = driver.findElements(By.xpath(path));
			List<String> all_elements_Text = new ArrayList<>();
			ArrayList<String> sortedList = new ArrayList<>();

			for (int i = 0; i < dateList.size(); i++) {

				// loading text of each element in to array all_elements_text
				all_elements_Text.add(dateList.get(i).getText());

				// to print directly
				System.out.println(dateList.get(i).getText());
			}
			for (String s : all_elements_Text) {
				sortedList.add(s);
			}

			/*
			 * Collections.sort(sortedList,Collections.reverseOrder());
			 * Assert.assertEquals(all_elements_Text, sortedList);
			 */
			Collections.sort(sortedList);
			Assert.assertTrue(sortedList.equals(all_elements_Text));
			System.out.println("List is sorted as per date");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Story closing
	public static void story_Closure(String path, WebDriver driver) throws Throwable {
		try {
			List<WebElement> elements = driver.findElements(By.xpath(path));

			for (int i = 0; i < elements.size() - 1; ++i) {
				click(Resources_Utility.xpath("story_Close"), driver);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	// filter closing
	public static void filter_Closure(String path, WebDriver driver) throws Throwable {
		try {
			List<WebElement> elements = driver.findElements(By.xpath(path));

			for (int i = 0; i < elements.size() - 1; ++i) {
				click(Resources_Utility.xpath("filter_CloseBTn"), driver);
				sleep(2000);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	// zoom check
	public static void zoom_Check(String foot_Note, String zoom_Btn, String zoom_In_Btn,
			String zoom_Out_Btn, WebDriver driver)	throws Throwable {
		int pixel_Size=0;
		int zoom_In_Size=0;
		try {
			pixel_Size = get_FontSize(foot_Note, driver);
			System.out.println("-- Font Size Before zoooming " + pixel_Size);
			click(zoom_Btn, driver);
			for (int i = 0; i < 3; i++) {
				explicit_Wait( Resources_Utility.xpath("zoom_In_Btn"), 3, "Zoom Option", driver);
				//sleep(5000);
				click(zoom_Btn, driver);
				click(zoom_In_Btn, driver);
				zoom_In_Size = get_FontSize(foot_Note, driver);
				System.out.println("** Font Size Increased to - " + zoom_In_Size);
			}
			System.out.println("-- Font Size after Zoomed-In = " + zoom_In_Size);
			do {
				//sleep(2000);
				click(zoom_Btn, driver);
				click(zoom_Out_Btn, driver);
				zoom_In_Size = get_FontSize(foot_Note, driver);
				System.out.println("** Font Sizes decreased to - " + zoom_In_Size);
			} while (zoom_In_Size > pixel_Size);
			System.out.println("-- Font Size after Zoomed-Out = " + zoom_In_Size);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	//Scrolling method
	public static void scrollbyElement(String path, int pixel,WebDriver driver) throws Throwable {
		JavascriptExecutor js = (JavascriptExecutor) driver; 
		try {
			if(path==null) {
				js.executeScript("window.scrollBy(0,"+pixel+")");				//Scrolling the window by pixels vertically
				sleep(2000);	
			}else {
				WebElement element = driver.findElement( By.xpath(path)); 
				js.executeScript("arguments[0].scrollIntoView(true);", element);
				sleep(2000);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		} 
	}

	// Validate the downloaded file
	public static String verify_Downloaded_File() throws Throwable {
		File lastModifiedFile = null;
		try {
			sleep(2000);
			String downloadpath = "C:\\Users\\techn\\Downloads";
			File dir = new File(downloadpath);
			File[] files = dir.listFiles();

			if (files == null || files.length == 0) {
				return null;
			}
			lastModifiedFile = files[0];
			for (int i = 1; i < files.length; i++) {
				if (lastModifiedFile.lastModified() < files[i].lastModified()) {
					lastModifiedFile = files[i];
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return lastModifiedFile.getName();
	}

	// Window Switching upto two tabs
	public static void window_Switch(int tabNo, ArrayList<String> tabs, WebDriver driver) throws Throwable {

		try {
			sleep(1000);
			Set<String> allWindows = driver.getWindowHandles();
			tabs = new ArrayList<>(allWindows);
			driver.switchTo().window(tabs.get(tabNo));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void close_AllTabs(String parentUrl, ArrayList<String> tabs, WebDriver driver) throws Throwable {
		
		Set<String> allWindows = driver.getWindowHandles();
		tabs = new ArrayList<>(allWindows);
		System.out.println('\n'+"++++++++ Closing all child tabs (if any) ++++++++");
		try {
			if(tabs.size()>1) {
				for(int i=1; i<tabs.size(); i++) {
					driver.switchTo().window(tabs.get(i));
					driver.close();
				}
				System.out.println("-- Tabs closed --> "+(tabs.size()-1)+" tabs");
			}
			driver.switchTo().window(tabs.get(0));
			driver.get(parentUrl);
			explicit_Wait( Resources_Utility.xpath("allow_Btn"), 10, "Allow Button", driver);
			if(element_Displayed(Resources_Utility.xpath("allow_Btn"), "Allow Button", driver)) {
				click(Resources_Utility.xpath("allow_Btn"), driver);
			}
			System.out.println("-- All Child tabs are closed and Navigated to "+driver.getCurrentUrl());
			System.out.println("-------------------------------------------------------------------------------------------------------------");			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	//Random selection
	public static void random_Selection(String elementspath, WebDriver driver) throws Throwable {

		List<WebElement> elements = driver.findElements(By.xpath(elementspath));   // Counting the number of filters in rule dropdown

		if(elements.size()==1) {
			driver.findElement(By.xpath(elementspath)).click();	
		}
		else {
			Random objGenerator = new Random();
			randomNumber = objGenerator.nextInt(elements.size());
			if(randomNumber==0)
			{
				randomNumber++;
			}
			//System.out.println(randomNumber);
			driver.findElement(By.xpath(elementspath+"["+randomNumber+"]")).click();
			sleep(1000);
			System.out.println(randomNumber);
		}
	}

	public static int randomNumber_selected() throws Throwable{

		return randomNumber;
	}

	//Getting the name of randomly selected item
	public static String random_Selection_Name(String elementspath, WebDriver driver) throws Throwable {
		String name=null;
		try {
			List<WebElement> elements = driver.findElements(By.xpath(elementspath));   // Counting the number of filters in rule dropdown

			if(randomNumber==0 || randomNumber>elements.size()) {

				if(elements.size()==1) {
					name = driver.findElement(By.xpath(elementspath)).getText();
				}
				else {
					Random objGenerator = new Random();
					randomNumber = objGenerator.nextInt(elements.size());
					if(randomNumber==0)
					{
						randomNumber++;
					}
				}
				name = driver.findElement(By.xpath(elementspath+"["+randomNumber+"]")).getText();
			}else {
				name = driver.findElement(By.xpath(elementspath+"["+randomNumber+"]")).getText();
			}
			randomNumber=0;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return name;
	}

	//Double click
	public static void double_Click(String path, WebDriver driver) throws Throwable {
		Actions actions = new Actions(driver);
		WebElement elementLocator = driver.findElement(By.xpath(path));
		actions.doubleClick(elementLocator).perform();
	}
	//Price check of books details page
	public static int price_Check(String path, WebDriver driver){

		String price = driver.findElement(By.xpath(path)).getText();	//price of book
		String[] price1=price.split("U");
		price1[0]=price1[0].replaceAll("[^0-9]", "");
		int GrandTotal= Integer.parseInt(price1[0]);

		return GrandTotal;
	}

	//GST checking 
	public static double gst_Check(double price, String orderType)	{

		double gstAmount=0;

		try {
			switch(orderType) {

			case "Virtual Journal":
				gstAmount = price*0.18;
				gstAmount = Math.round(gstAmount);
				break;

			case "Virtual Book":
				gstAmount = price*0.05;
				gstAmount = Math.round(gstAmount);
				break;

			case "Webinar":
				gstAmount = price*0.18;
				gstAmount = Math.round(gstAmount);
				break;
			}	
		}catch (Exception e) {
			e.printStackTrace();
		}
		return gstAmount;

	}

	//Verify view content extension
	public static void verify_view_content_extension(String view_content, WebDriver driver ) throws Throwable {

		try {
			driver.findElement(By.xpath(view_content)).click();
			window_Switch(2, tabs, driver);

			System.out.println("-- URL of the page:" + driver.getCurrentUrl());
			String ext = FilenameUtils.getExtension(driver.getCurrentUrl());
			System.out.println("-- Extension of the file is - ."+ext);

			String act="pdf";
			String act1="PDF";
			String act2="html";
			try {	
				if(ext.equals(act))
				{
					try {
						Assert.assertEquals(act,ext,"Doesn't match");
					}catch(Throwable t){
						System.out.println(t.getMessage());
					}
				}
				else if(ext.equals(act1))
				{
					try {
						Assert.assertEquals(act1,ext,"Doesn't match");
					}catch(Throwable t)	{
						System.out.println(t.getMessage());
					}
				}
				else if(ext.equals(act2))
				{
					try {
						Assert.assertEquals(act2,ext,"Doesn't match");
					}catch(Throwable t){
						System.out.println(t.getMessage());
					}
				}
			}catch (Exception e) {
				System.out.println("-- WRONG Extension!!");
				e.printStackTrace();
			}
			System.out.println("-- Extension verified successfully!");

			driver.close();
			window_Switch(1, tabs, driver);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	//verify price at cart page
	public void verify_price(String unit_price, String sub_total, WebDriver driver) throws Throwable
	{
		try {
			//String s=getText(Resources_Utility.xpath("unit_price"),driver); 
			System.out.println('\n'+"======== Price Verification ========");
			String s=driver.findElement(By.xpath(unit_price)).getText();
			s=s.replaceAll("[^0-9]", "");
			int unitprice= Integer.parseInt(s);
			System.out.println("-- Unit price is: " +unitprice);

			WebElement count=driver.findElement(By.id("txtProdqty0")); //quantity value
			int c=Integer.parseInt(count.getAttribute("value"));
			System.out.println("-- Quantity is: "+c);
			int total_value=unitprice*c;
			System.out.println("-- Total Price is: "+total_value);

			//String s1=getText(Resources_Utility.xpath("subtotal"),driver); 
			String s1=driver.findElement(By.xpath(sub_total)).getText();
			s1=s1.replaceAll("[^0-9]", "");
			int subtotal= Integer.parseInt(s1);
			System.out.println("-- Sub Total amount is: " +subtotal);

			try {
				Assert.assertEquals(total_value, subtotal,"Doesn't match");
				System.out.println("======== Hence Prices verified! ========");
			}catch(Throwable t)
			{
				System.out.println(t.getMessage());
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	//verify address
	public void verify_address(String shipping_address,String check, String billing_address, WebDriver driver) throws Throwable
	{
		try {
			System.out.println('\n'+"======== Shipping and Billing Address Verification Started ========");

			String address1=driver.findElement(By.xpath(shipping_address)).getText(); 
			System.out.println("-- Shipping address: "+address1);

			driver.findElement(By.xpath(check)).click();
			scrollbyElement(null, 400, driver);

			String address2=driver.findElement(By.xpath(billing_address)).getText();
			System.out.println("-- Billing address: "+address2);
			verifyEquals(address1, address2, driver);

			System.out.println("======== Shipping and Billing Address Verification Done========");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	//verify payment modes
	public void verify_payment_Mode(String mode_check, String mode, String pay, String pay2, WebDriver driver) throws Throwable
	{
		try {
			switch (mode_check) {
			case "Online Payment":
				System.out.println('\n'+"-----"+mode_check+" mode Verification Started---- ");
				break;
			case "No cost Emi":
				System.out.println('\n'+"-----"+mode_check+" mode Verification Started---- ");
				break;
			case "NEFT":
				System.out.println('\n'+"-----"+mode_check+" Payment mode Verification Started---- ");
				break;
			case "CHEQUE":
				System.out.println('\n'+"-----"+mode_check+" Payment mode Verification Started---- ");
				break;
			case "Cash On Delivery":
				System.out.println('\n'+"-----"+mode_check+" Payment mode Verification Started---- ");
				break;
			}
			driver.findElement(By.xpath(mode)).click();
			sleep(1000);

			if (mode_check.contains("Cash On Delivery")) {
				boolean check = element_Displayed(Resources_Utility.xpath("place_Order"), "Place Order option", driver);
				if(check) {
					System.out.println('\n'+"-----"+mode_check+" Payment mode Verification Done---- ");
				}
			}
			else {
				String p1=getText(Resources_Utility.xpath("pay_Submit"), driver);
				p1=p1.replaceAll("[^0-9]", "");
				int pay1= Integer.parseInt(p1);
				System.out.println(mode_check+" Payment amount : " +pay1);

				String p2=driver.findElement(By.xpath(pay2)).getText(); 
				p2=p2.replaceAll("[^0-9]", "");
				int pay_2= Integer.parseInt(p2);
				System.out.println("Payment amount on Cart Summary: " +pay_2);

				try {
					Assert.assertEquals(pay1, pay_2,"Doesn't match");
					System.out.println("-- Hence "+mode_check+" Verification Done!!");
				}catch(Throwable t){
					System.out.println(t.getMessage());
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	//verify total amount
	public static void verify_total_amount(String sub_total, String total, WebDriver driver ) throws Throwable
	{      
		sleep(2000);
		String s=driver.findElement(By.xpath(sub_total)).getText(); 
		s=s.replaceAll("[^0-9]", "");
		int SubTotal= Integer.parseInt(s);
		System.out.println("Sub Total amount is: " +SubTotal);
		sleep(2000); 

		String t=driver.findElement(By.xpath(total)).getText(); 
		t=t.replaceAll("[^0-9]", "");
		int GrandTotal= Integer.parseInt(t);
		System.out.println("Grand Total amount is: " +GrandTotal);
		sleep(2000);

		if(SubTotal<=500) 
		{
			int Total=SubTotal+70;
			System.out.println("Sub-Total amount is less than or equal to 500");
			try
			{
				Assert.assertEquals(GrandTotal, Total,"Total is not equal to Grand-Total ");
			} catch(Throwable t1)
			{
				System.out.println(t1.getMessage());
			}
			System.out.println("Grand Total is equal to total after addition of shipping charges" );
			System.out.println(" Shipping charges applied successfully!");
		}
		else{
			System.out.println("Sub-Total amount is greater than 500");
			try {
				Assert.assertEquals(SubTotal,GrandTotal,"Sub-Total is not equal to Grand-Total ");
			}catch(Throwable t1)
			{
				System.out.println(t1.getMessage());
			}
			System.out.println("Sub-Total is equal to Grand-Total" );
			System.out.println(" Hence verified! No shipping charges applied ");
		}
	}          

	//Shipping address change
	public static void shipping_Address(WebDriver driver) throws Throwable {

		try {
			/*
			 * try { if (element_Displayed(Resources_Utility.xpath("edit_Address"), driver))
			 * { click(Resources_Utility.xpath("edit_Address"), driver); sleep(2000); } }
			 * catch (Exception e) { click(Resources_Utility.xpath("add_address"), driver);
			 * sleep(1000); }
			 * 
			 * //click(Resources_Utility.xpath("name"), driver); //
			 * clear_text(Resources_Utility.xpath("name"), driver);
			 * 
			 * driver.findElement(By.xpath("//*[@id=\"name\"]")).sendKeys(Keys.BACK_SPACE);
			 * sleep(2000);
			 */
			click(Resources_Utility.xpath("add_address"), driver);

			type(Resources_Utility.xpath("name"), Resources_Utility.config("name"), driver);
			sleep(1000);

			click(Resources_Utility.xpath("mobile_number_country"), driver);
			sleep(1000);
			click(Resources_Utility.xpath("mobile_number_country_value"), driver);
			sleep(1000);
			type(Resources_Utility.xpath("mobile_number"), Resources_Utility.config("mobile_number"), driver);
			sleep(1000);

			click(Resources_Utility.xpath("designation"), driver);
			sleep(1000);
			random_Selection(Resources_Utility.xpath("dropdown_Values"), driver);

			type(Resources_Utility.xpath("Company_Name"), Resources_Utility.config("Company_Name"), driver);
			sleep(1000);

			type(Resources_Utility.xpath("flat"), Resources_Utility.config("flat"), driver);
			sleep(1000);

			type(Resources_Utility.xpath("Street_Number"), Resources_Utility.config("Street_Number"), driver);
			sleep(1000);


			type(Resources_Utility.xpath("Locality"), Resources_Utility.config("Locality"), driver);
			sleep(1000);

			click(Resources_Utility.xpath("country"), driver);
			sleep(3000);

			click(Resources_Utility.xpath("country_select"), driver);
			sleep(2000);

			type(Resources_Utility.xpath("Pin_Code"), Resources_Utility.config("Pin_Code"), driver);
			sleep(1000);

			click(Resources_Utility.xpath("state"), driver);
			sleep(1000);

			click(Resources_Utility.xpath("state_select"), driver);
			sleep(1000);

			type(Resources_Utility.xpath("City_Name"), Resources_Utility.config("City_Name"), driver);
			sleep(1000);

			click(Resources_Utility.xpath("default_address"), driver);
			sleep(1000);

			click(Resources_Utility.xpath("submit"), driver);
			sleep(3000);

		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void Skip(WebDriver driver) throws Throwable {
		try {
			click(Resources_Utility.xpath("skip_Btn"), driver);
			click(Resources_Utility.xpath("allow_Btn"), driver);
			System.out.println("-- skipped successfully on Incognito Mode");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Login and  Skip
	public void login_And_Skip(WebDriver driver) throws Throwable {
		try {
			click(Resources_Utility.xpath("login_btn"), driver);
			type(Resources_Utility.xpath("email_input"), Resources_Utility.config("id"), driver);
			type(Resources_Utility.xpath("pwd_input"), Resources_Utility.config("pwd"), driver);
			click(Resources_Utility.xpath("sign_btn"), driver);

			if(element_Displayed(Resources_Utility.xpath("profile_Btn"), "Profile Button", driver)) {
				System.out.println("-- Logged in Successfully!!!!!");
			}
			else if(element_Displayed(Resources_Utility.xpath("sign_btn"), "Network Error", driver)){
				do {					
					click(Resources_Utility.xpath("sign_btn"), driver);
				}while(element_Displayed(Resources_Utility.xpath("network_Error"),"Network_Error", driver));
			}
			System.out.println('\n'+"-- Login Successfully on Incognito Mode");
			sleep(1500);

			click(Resources_Utility.xpath("skip_Btn"), driver);
			click(Resources_Utility.xpath("allow_Btn"), driver);
			System.out.println("-- skipped successfully on Incognito Mode");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Logout Process
	public void logout(WebDriver driver) throws Throwable {
		System.out.println('\n'+ "======== LogOut Started ========");

		try {
			if(element_Displayed(Resources_Utility.xpath("profile_Btn"), "Profile Button", driver)){}

		}catch (Exception e) {
			click(Resources_Utility.xpath("taxmann_Logo"), driver);
		}
		click(Resources_Utility.xpath("profile_Btn"), driver);
		click(Resources_Utility.xpath("logout_Btn"), driver);
		System.out.println("-- Logged Out successfully");
		driver.quit();
	}

	//Order Cancellation
	public static  void cancel_Order(String Confirmed_OrderNo, WebDriver driver) throws Throwable {
		String order_Status=null;

		try {
			System.out.println('\n'+"======== Order Cancellation Started =======");

			click(Resources_Utility.xpath("profile_Btn"), driver);
			click(Resources_Utility.xpath("order"), driver);

			explicit_Wait(Resources_Utility.xpath("cancel_order"), 5, "Cancel Order Option", driver);
			click(Resources_Utility.xpath("cancel_order"), driver);
			explicit_Wait(Resources_Utility.xpath("cancel_OrderNO"), 5, "Cancel_OrderNO", driver);
			orderNO=getText(Resources_Utility.xpath("cancel_OrderNO"), driver);

			verifyEquals(Confirmed_OrderNo, orderNO, driver);
			click(Resources_Utility.xpath("cancel_Dropdown"), driver);

			random_Selection(Resources_Utility.xpath("dropdown_Values"), driver);
			explicit_Wait(Resources_Utility.xpath("cancel_Reason_Selected"), 5, "Cancel_Reason Selected", driver);
			String reason=getText(Resources_Utility.xpath("cancel_Reason_Selected"), driver);

			try {
				if(reason.contains("Other")) {
					type(Resources_Utility.xpath("cancel_reason"), Resources_Utility.config("cancel_reason"), driver);
					System.out.println("Reason for cancellation is - "+reason+" - "+Resources_Utility.config("cancel_reason"));
				}else {
					System.out.println("Reason for cancellation is - "+reason);
				}
			}catch (Exception e) {
				e.printStackTrace();
			}

			click(Resources_Utility.xpath("cancel_button"), driver);
			explicit_Wait(Resources_Utility.xpath("myOrder_No"),'5', "Order Status", driver);
			System.out.println("-- Checking the Order status of - '"+getText(Resources_Utility.xpath("myOrder_No"), driver)+"'");

			order_Status=getText(Resources_Utility.xpath("order_Status"), driver);
			order_Status=order_Status.replaceAll("\\s", "");
			verifyEquals(order_Status,Resources_Utility.config("order_Status_Cancel"), driver);

			System.out.println("======= Order Cancelled After Testing Done =======");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}