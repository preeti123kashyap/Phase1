package PageRepo;

import java.util.ArrayList;
import java.util.Random;

import org.testng.annotations.Test;

import GenericFile.Resources_Utility;
import taxmann.com.Phase1.AppTest;

public class footnote_count extends AppTest {
	public String parent = null;
	
	@Test(enabled = true, priority = 1,groups= {"Common"})
	public void references_count() throws Throwable {
		System.out.println('\n' + "======== Footnote Testing Started ========");
		parent = driver.getCurrentUrl();
		sleep(3000);
		click(Resources_Utility.xpath("research_Btn"),driver);
		
		click(Resources_Utility.xpath("practice_Btn"),driver);
		sleep(3000);
		driver.navigate().to("https://www.taxmann.com/practice/Income-tax/Advance-Tax/read/1110000000026076/1/0?cat=read");
		
		
	}}
