package PageRepo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.lowagie.text.Document;

public class word_content {
	private static String driverpath = System.getProperty("user.dir") + "\\driver\\";
	public static WebDriver driver = null;
	public static String parent = null;
	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		System.setProperty("webdriver.chrome.driver", driverpath + "chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://www.taxmann.com/");
		driver.manage().window().maximize();
		driver.findElement(By.xpath("(//a[contains(text(),'Sign in')])[1]")).click();
		driver.findElement(By.xpath("//a[contains(text(),' Login with Email ')]")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@formcontrolname='email']")).sendKeys("deepak.verma@taxmanntechnologies.com");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("Deepak@123");
		driver.findElement(By.xpath("//a[contains(text(),'Sign In')]")).click();
		Thread.sleep(3000);
		driver.get("https://www.taxmann.com/preview-document?categoryName=direct-tax-laws&fileId=101010000000326886&subCategory=caselaws");
		Thread.sleep(5000);
       WebElement l= driver.findElement(By.cssSelector("#htmlPreview"));
   
       String str = l.getText();
       str.trim();
       String[] st=str.split("ORDER");
       System.out.println(st[0]);
       String filePath = System.getProperty("user.dir")+"/FormattedWord.doc";
       FileOutputStream out = new FileOutputStream(new File(filePath));
       Writer output = new OutputStreamWriter(out, "ASCII");
       output.write(st[0]);
       output.close();
       
      /* XWPFDocument document = new XWPFDocument();  
       FileOutputStream os = new FileOutputStream(filePath);   
            XWPFParagraph para = document.createParagraph();  
            //paragraph.setAlignment(ParagraphAlignment.RIGHT);  
            para.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun run = para.createRun();  
            run.setText(st[0]);  
            document.write(os); 
       */
       /*XWPFDocument document = new XWPFDocument();
       String filePath = System.getProperty("user.dir")+"/FormattedWord.txt";
       Path path = Paths.get("\"C:\\Users\\Administrator\\git\\automation1\\filePath\"");
       //FileOutputStream out = new FileOutputStream(new File(path));
       Files.writeString(path, st[0], StandardCharsets.UTF_8);
       Document docu = new Document("filepath"); 
       */
       
     //  String[] st1=st[0].split(" ");
       
      /* for(String ss : st) {
    	   ss.trim();
    	   System.out.println("+"+ss+"+");
       }
       
       System.out.println("+++++++++++++++++++++++++++++++++++++++++++++\n");
      
       for(String ss1 : st) {
    	   ss1.trim();
    	   System.out.print(ss1+" ");
       }
       }*/
	}


}