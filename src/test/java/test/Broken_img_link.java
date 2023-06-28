package test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import GenericFile.readWriteExcel;
import GenericFile.selenium_utility;

public class Broken_img_link {



	private static WebDriver driver = null;
	static String homePage = "https://www.taxmann.com";
	static String oldPage = "https://old.taxmann.com";
	static String srccode = "";
	static String href ="";
	static HttpURLConnection huc = null;
	static int brkUrl=0;
	static int brkImg=0;
	static String response = "";
	static int respCode = 200;
	static String excelPath= System.getProperty("user.dir")+"\\src\\test\\java\\Resources\\Broken_Check.xlsx";
	static String sheetName = "URL";
	static readWriteExcel rw = new readWriteExcel(excelPath);
	static List<WebElement> linksList = new ArrayList<WebElement>();
	static boolean invalid_URL=true;
	//static String result ="";

	public static void checkLinks(String Result) throws MalformedURLException, IOException {

		linksList = driver.findElements(By.tagName("a"));
		List <String> activeLink = new ArrayList<String>();
		List <String> inactiveLink = new ArrayList<String>();
		List <String> otherLink = new ArrayList<String>();
		List <String> oldLink = new ArrayList<String>();
		List<String> newList = new ArrayList<String>();
		rw.setCellData(Result, "Total URL Test", 1, String.valueOf(linksList.size()));
		//System.out.println("Size of all links ---> "+linksList.size());

		for(int i=0; i<linksList.size(); i++)
		{
			href = linksList.get(i).getAttribute("href");
			//System.out.println("number "+i+" "+href);


			newList.add(href);
			try{
				if(href==null ||( href.contains("javascript:void(0)") || (href.isEmpty()))) {
					inactiveLink.add(href);
				}
				else if(href.startsWith(oldPage)&& href.endsWith(".aspx")) {
					oldLink.add(href);
				}
				else if(!href.startsWith(homePage)) {
					otherLink.add(href);
					linksList.remove(i);
				}

				else if (href != null && (! href.contains("javascript:void(0)") && (!href.isEmpty())))
				{
					activeLink.add(href);
				}

			} catch (Exception e) {

				//print the url on excel
				inactiveLink.add(href);
			}
		}	
		rw.setCellData(Result, "Active URL", 1, String.valueOf(activeLink.size()));
		//System.out.println("Size of active links ---> "+activeLink.size());
		rw.setCellData(Result, "Inactive URL", 1, String.valueOf(inactiveLink.size()));
		//System.out.println("Size of Inactive links ---> "+inactiveLink.size());
		rw.setCellData(Result, "Other URL", 1, String.valueOf(otherLink.size()));
		//System.out.println("Size of other links ---> "+otherLink.size());
		rw.setCellData(Result, "Old URL", 1, String.valueOf(oldLink.size()));
		//System.out.println("Size of old links ---> "+oldLink.size());


		int totalLinkCount = verify(newList,"Link URL","Link Path","Link Status",Result);

		brkUrl = totalLinkCount ;

	}

	public static int verify(List link, String colname,String path, String status, String Result) {

		int invalid = 0;
		//List<String> xpath = new ArrayList<String>();
		List<String> responce = new ArrayList<String>();

		for(int j=0; j<link.size(); j++) {

			String activeUrl = (String) link.get(j);

			//xpath.add(generateXPATH(linksList.get(j), ""));
			
			//System.out.println(xpath);
			//System.out.println("URL->"+activeUrl);
			try {
				huc = (HttpURLConnection) (new URL(activeUrl).openConnection());

				huc.connect();
				response = huc.getResponseMessage();
				respCode = huc.getResponseCode();
				huc.disconnect();

				if(respCode >= 400){
					invalid++ ;
					//System.out.println("broken links ************"+j + activeUrl +"--->"+ response );
				}
				responce.add(response);
				//System.out.println(linksList.get(j));
				//System.out.println(j +activeUrl+"--->"+response);
			} catch (Exception e) {
				responce.add(response);
				System.out.println("broken links ************"+j + activeUrl +"--->"+ response );
				invalid++;
			}

		}
		rw.setCellData(Result, colname, link);
		//rw.setCellData(Result, path, xpath);
		rw.setCellData(Result, status,  responce);
		
		return invalid;
	}

	public static void checkImages(String Result) throws MalformedURLException, IOException {

		linksList = driver.findElements(By.tagName("img"));
		List <String> activeLinks = new ArrayList<String>(); 
		List <String> inactiveLink = new ArrayList<String>(); 


		rw.setCellData(Result, "Total Image Test", 1, String.valueOf(linksList.size()));
		//System.out.println("Size of all Images ---> "+linksList.size());


		for(int i=0; i<linksList.size(); i++)
		{
			srccode = linksList.get(i).getAttribute("src");

			//System.out.println(linksList.get(i));
			//String path =generateXPATH(linksList.get(i), "");
			//System.out.println(path);

			if(srccode==null ||( srccode.contains("javascript:void(0)") || (srccode.isEmpty()))) {
				inactiveLink.add(srccode);
			}

			else if (srccode != null && (! srccode.contains("javascript:void(0)") && (!srccode.isEmpty())))
			{

				activeLinks.add(srccode);
			}
		}

		rw.setCellData(Result, "Active Image", 1, String.valueOf(activeLinks.size()));
		//System.out.println("Size of active links and images ---> "+activeLinks.size());
		rw.setCellData(Result, "Inactive Images", 1, String.valueOf(inactiveLink.size()));
		//	System.out.println("Size of Inactive links and images ---> "+inactiveLink.size());

		brkImg = verify(activeLinks,"Images Url","Images Path","Images Status",Result);

	}

	public static void main(String[] args) throws MalformedURLException, IOException {
		// TODO Auto-generated method stub
		try {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\driver\\chromedriver.exe");

			driver = new ChromeDriver();

			driver.manage().window().maximize();

		} catch (Exception e) {
			e.printStackTrace();
		}



		//Excel read and write function

		int row = rw.getRowCount(sheetName);
		for(int i =1; i<row;++i) {

			String URL = rw.getCellData(sheetName, 0, i);

			driver.get(URL);
		try {
			Thread.sleep(1000);
			String websiteURL= driver.getCurrentUrl();
			if(websiteURL.equals("https://www.taxmann.com/bookstore"));{
				invalid_URL=false;
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
			try {
				if(invalid_URL) {
					
				selenium_utility.sleep(4000);
				//WebElement footer = driver.findElement(By.xpath("/html/body/app-root/app-bookstore/div/div/app-footer/div/footer"));
				
				selenium_utility.pagedownBTN("//*[contains(@class,'app-footer')]", driver);
				selenium_utility.sleep(2000);				
				
				}
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String result = String.valueOf(i+1+"sheet"); 
			System.out.println(URL);
			//add new sheet for result. and header added. 
			rw.addSheet(result);
			colFormat(result);

			//checkLinks(result);
			checkImages(result);
			rw.setCellData(sheetName, "Broken links Count", i, String.valueOf(brkUrl));
			rw.setCellData(sheetName, "Broken Images Count", i, String.valueOf(brkImg));
			//Adding Hyper link in URL sheet.
			rw.setCellData(sheetName, URL, i+1, URL, result);
		}

		//System.out.println("This Is the Summary Report ---------------------------");
		//System.out.println("Total Broken Link found are ------------"+brkUrl);
		//System.out.println("Total Broken Images found are ----------"+brkImg);
		driver.close();
	}

	public static void colFormat(String sheetName ) {

		rw.setCellData(sheetName, 0, 0, "Link URL");
		rw.setCellData(sheetName, 1, 0, "Link Path");
		rw.setCellData(sheetName, 2, 0, "Link Status");
		rw.setCellData(sheetName, 4, 0, "Images Url");
		rw.setCellData(sheetName, 5, 0, "Images Path");
		rw.setCellData(sheetName, 6, 0, "Images Status");
		rw.setCellData(sheetName, 8, 0, "Total URL Test");
		rw.setCellData(sheetName, 9, 0, "Active URL");
		rw.setCellData(sheetName, 10, 0, "Inactive URL");
		rw.setCellData(sheetName, 11, 0, "Other URL");
		rw.setCellData(sheetName, 12, 0, "Old URL");
		rw.setCellData(sheetName, 14, 0, "Total Image Test");
		rw.setCellData(sheetName, 15, 0, "Active Image");
		rw.setCellData(sheetName, 16, 0, "Inactive Images");

	}


	private static String generateXPATH(WebElement childElement, String current) {
		String childTag = childElement.getTagName();
		if(childTag.equals("html")) {
			return "/html[1]"+current;
		}
		WebElement parentElement = childElement.findElement(By.xpath("..")); 
		List<WebElement> childrenElements = parentElement.findElements(By.xpath("*"));
		int count = 0;
		for(int i=0;i<childrenElements.size(); i++) {
			WebElement childrenElement = childrenElements.get(i);
			String childrenElementTag = childrenElement.getTagName();
			if(childTag.equals(childrenElementTag)) {
				count++;
			}
			if(childElement.equals(childrenElement)) {
				
				return generateXPATH(parentElement, "/" + childTag + "[" + count + "]"+current);
			}
		}
		return null;
	}
}


