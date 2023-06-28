package PageRepo;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import GenericFile.Resources_Utility;
import GenericFile.selenium_utility;
import taxmann.com.Phase1.AppTest;

public class TaxCalculator extends AppTest {

	@Test
	public void tax_calculator() throws Throwable {

		System.out.println('\n' + "============== Income tax Calculator Testing Started ==============");

		int taxable_income = 16000000;
		int actual_taxable_income_tax = 5516550;

		driver.navigate().to("https://www.taxmann.com/tools/income-tax-calculator");

		click(Resources_Utility.xpath("assessment_year_click"), driver);
		click(Resources_Utility.xpath("assessment_year_value"), driver);
		click(Resources_Utility.xpath("tax_payer_click"), driver);
		click(Resources_Utility.xpath("tax_payer_value"), driver);
		click(Resources_Utility.xpath("gender_click"), driver);
		click(Resources_Utility.xpath("gender_value"), driver);
		click(Resources_Utility.xpath("residential_click"), driver);
		click(Resources_Utility.xpath("residential_value"), driver);

		type_Int(Resources_Utility.xpath("income"), taxable_income, driver);
		click(Resources_Utility.xpath("calculate_Btn"), driver);
		JavascriptExecutor j = (JavascriptExecutor) driver;
		j.executeScript("window.scrollBy(0,400)", "");

		// verify the result.
		WebElement l = driver
				.findElement(By.xpath("(//div[@class='row my-2 ng-star-inserted']//descendant::div[4])[4]"));
		int expected_tax = Integer.parseInt(l.getText());

		verifyEquals(actual_taxable_income_tax, expected_tax, driver);
		System.out.println("============== Income Tax Calculator Testing Done ==============");
	}
}