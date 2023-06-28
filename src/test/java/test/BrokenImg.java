package test;
 
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
 
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.By;
import java.net.URL;
 
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
 
public class BrokenImg
{
    /*  protected static ChromeDriver driver; */
    WebDriver driver = null;
    String URL = "https://the-internet.herokuapp.com/broken_images";
    public static String status = "passed";
    String username = "deepak.verma";
	String accessKey = "EMpIMZenzCwJxc160EyjwFmFPqg31BmSKbTqH82jJbajDFxKrn";
	/*
    @BeforeClass
    public void testSetUp() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("build", "[Java] Finding broken images on a webpage using Selenium");
        capabilities.setCapability("name", "[Java] Finding broken images on a webpage using Selenium");
        capabilities.setCapability("platform", "Windows 10");
        capabilities.setCapability("browserName", "Chrome");
        capabilities.setCapability("version","latest");
        capabilities.setCapability("tunnel",false);
        capabilities.setCapability("network",true);
        capabilities.setCapability("console",true);
        capabilities.setCapability("visual",true);
 
        try {       
			driver= new RemoteWebDriver(new URL("https://deepak.verma:EMpIMZenzCwJxc160EyjwFmFPqg31BmSKbTqH82jJbajDFxKrn@hub.lambdatest.com/wd/hub"), capabilities);            
		} catch (MalformedURLException e) {
			System.out.println("Invalid grid URL");
		}
        System.out.println("Started session");
    }
*/ 
	@BeforeClass
	 public void testSetUp() throws MalformedURLException {
			
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\driver\\chromedriver.exe");
        driver = new ChromeDriver();
        
	}
	
	
    @Test(description="Approach 1: Find broken images on a web page using Selenium WebDriver", enabled=true)
    protected void test_selenium_broken_images_appr_1()
    {
        Integer iBrokenImageCount = 0;
 
        driver.navigate().to(URL);
        driver.manage().window().maximize();
 
        try
        {
            iBrokenImageCount = 0;
            List<WebElement> image_list = driver.findElements(By.tagName("img"));
            /* Print the total number of images on the page */
            System.out.println("The page under test has " + image_list.size() + " images");
            for (WebElement img : image_list)
            {
            	System.out.println(img);
                if (img != null)
                {
                    HttpClient client = HttpClientBuilder.create().build();
                    HttpGet request = new HttpGet(img.getAttribute("src"));
                    HttpResponse response = client.execute(request);
                    /* For valid images, the HttpStatus will be 200 */
                    if (response.getStatusLine().getStatusCode() != 200)
                    {
                    	
                        System.out.println(img.getAttribute("outerHTML") + " is broken.");
                        iBrokenImageCount++;
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            status = "failed";
            System.out.println(e.getMessage());
        }
        status = "passed";
        System.out.println("The page " + URL + " has " + iBrokenImageCount + " broken images");
    }
 
    @Test(description="Approach 2: Find broken images on a web page using Selenium WebDriver", enabled = true)
    protected void test_selenium_broken_images_appr_2()
    {
        Integer iBrokenImageCount = 0;
 
        driver.navigate().to(URL);
        driver.manage().window().maximize();
 
        try
        {
            iBrokenImageCount = 0;
            List<WebElement> image_list = driver.findElements(By.tagName("img"));
            /* Print the total number of images on the page */
            System.out.println("The page under test has " + image_list.size() + " images");
            for (WebElement img : image_list)
            {
                if (img != null)
                {
                    if (img.getAttribute("naturalWidth").equals("0"))
                    {
                        System.out.println(img.getAttribute("outerHTML") + " is broken.");
                        iBrokenImageCount++;
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            status = "failed";
            System.out.println(e.getMessage());
        }
        status = "passed";
        System.out.println("The page " + URL + " has " + iBrokenImageCount + " broken images");
    }
 
    @AfterClass
    public void tearDown()
    {
        if (driver != null) {
            ((JavascriptExecutor) driver).executeScript("lambda-status=" + status);
            driver.quit();
        }
    }
}