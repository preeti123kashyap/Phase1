import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class footnote_count1 {
	private static String driverpath = System.getProperty("user.dir") + "\\driver\\";
	public static WebDriver driver = null;
	public static String parent = null;

	public static void main(String[] args) throws Throwable {
		// TODO Auto-generated method stub
		System.setProperty("webdriver.chrome.driver", driverpath + "chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://www.taxmann.com/");
		driver.manage().window().maximize();
		driver.findElement(By.xpath("(//a[contains(text(),'Sign in')])[1]")).click();
		driver.findElement(By.xpath("//a[contains(text(),' Login with Email ')]")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@formcontrolname='email']")).sendKeys("dipen@taxmann.com");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("Taxmann@123");
		driver.findElement(By.xpath("//a[contains(text(),'Sign In')]")).click();
		Thread.sleep(3000);
	//	driver.get("https://www.taxmann.com/practice/GST/Supply-under-GST/read/1110000000026884/1/0?cat=read");
		Thread.sleep(5000);
		
		
		FileInputStream input= new FileInputStream("C:\\Users\\Administrator\\Documents\\test1.xlsx");
		XSSFWorkbook w= new XSSFWorkbook(input);
		XSSFSheet s=w.getSheetAt(0);
		 XSSFRow row   =null;
		 XSSFCell cell = null;
		FileOutputStream fout= new FileOutputStream("C:\\Users\\Administrator\\Documents\\test1.xlsx");
		for(int i=0;i<157;i++)
		{
           String entry= s.getRow(i).getCell(0).getStringCellValue();
			
			System.out.println(entry);
		    driver.get(entry);
		    Thread.sleep(3000);
		List<WebElement> l= driver.findElements(By.xpath("//*[@id='footnote']/p[1]//following-sibling::p"));
		System.out.println(l.size());
		Thread.sleep(8000);
		for(int j=0; j<l.size(); j++)
		{
			row = s.getRow(i);
			System.out.println(l.get(j).getText());
			//cell = row.getCell(j+1);	
			if (row.getCell(j+1) == null)
			{	cell = row.createCell(j+1);
			cell.setCellValue((String)l.get(j).getText());}
			else
			s.getRow(i).createCell(j+1).setCellValue(l.get(j).getText());
			 
			
		}
		
		Thread.sleep(2000);
		
	/*	  for(int k=0;k<l.size()-1;k++) 
		  { 
		try
		  { 
		*/	
			
				/*
				 * row = s.getRow(i+1); if (row == null) row = s.createRow(i+1);
				 */

			
		//	s.getRow(i).
		//	s.getRow(i).getCell(i).se
		//    s.getRow(0).createCell(k+1).setCellValue(l.get(k).getText());
	       //System.out.println(l.get(2).getText());
		/*
		 * cell = row.getCell(k+1); if (cell == null) cell = row.createCell(k+1);
		 * cell.setCellValue((String)l.get(k).getText()); }
		 * 
		 * catch(Exception e) { e.getMessage(); }
		 * 
		 * 
		 * }
		 */
	
		
		
		/*
		 * for(WebElement e : l) { System.out.println(e.getText());
		 * 
		 * 
		 * 
		 * 
		 * }
		 */}
		w.write(fout);
		w.close();
		
		
		
	

	}}
