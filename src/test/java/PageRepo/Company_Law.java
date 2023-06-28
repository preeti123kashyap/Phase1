package PageRepo;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import GenericFile.Resources_Utility;
import taxmann.com.Phase1.AppTest;

public class Company_Law  extends AppTest {

	public String Rule_Selected=null;
	public String filter_Selected=null;
	public String Story_Name=null;
	
	@Test(enabled = true, priority = 1, groups = {"Research"})
	public void check_Rules() throws Throwable {

		try {
			System.out.println('\n'+ "============== Company Law Testing Started ==============");

			explicit_Wait(Resources_Utility.xpath("company_Law_Btn"), 10, "company_Law_Btn", driver);
			click(Resources_Utility.xpath("company_Law_Btn"), driver);											// Clicking on Company Law
			click(Resources_Utility.xpath("rule_Btn"), driver);													// Clicking on Rule option

			explicit_Wait(Resources_Utility.xpath("see_More"), 5, "See More", driver);
			click(Resources_Utility.xpath("category_Filter"), driver);											// Clicking on category filter option

			random_Selection(Resources_Utility.xpath("filter_List"), driver);									// Randomly select filer option from drop-down
			click(Resources_Utility.xpath("choose_Rule_Filter"), driver);										// Clicking on Rule filter
			random_Selection(Resources_Utility.xpath("filter_List"), driver);									// Randomly select filer option from drop-down

			List<WebElement> elementName = driver.findElements(By.xpath(Resources_Utility.xpath("selected_Filter_Tags")));

			for(int i=1; i<=elementName.size(); i++) {
				filter_Selected = driver.findElement(By.xpath("(//div[@class='filter-tag d-flex flex-wrap overflow-hidden']/a)"+"["+i+"]")).getAttribute("title");
				System.out.println("-- Filter Selected - "+"'"+filter_Selected+"'");
			}
			
			click(Resources_Utility.xpath("maximize_Btn"), driver);												// Clicking on maximize button	
			random_Selection(Resources_Utility.xpath("story_List"), driver);									// Randomly select stories
			//explicit_Wait(Resources_Utility.xpath("heading"), 5, "heading", driver);							// Wait for Story loading
			
			story_Closure(Resources_Utility.xpath("story_Close_allbtns"), driver);								//close previously opened stories
			Story_Name = getTitle(Resources_Utility.xpath("heading"), driver);
			System.out.println("-- Story Name - "+"'"+Story_Name+"'");
			
			System.out.println("============== Company Law Testing Done ==============");
		}catch (Exception e) {
			if(element_Displayed(Resources_Utility.xpath("secondUser_Check"), "Login Session Timer", driver)) {
				System.out.println("This Account is being used from another device at the moment!!");
			}else {
				e.printStackTrace();
			}
		}
	}
}