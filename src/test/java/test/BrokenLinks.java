package test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class BrokenLinks {
    
    private static WebDriver driver = null;
    static String homePage = "https://www.taxmann.com";
    static String oldPage = "https://old.taxmann.com";
    static String url = "";
    static HttpURLConnection huc = null;
    static int respCode = 200;

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        
        
        
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\driver\\chromedriver.exe");
        driver = new ChromeDriver();
        
        driver.manage().window().maximize();
        
        driver.get(homePage);
        
        List<WebElement> links = driver.findElements(By.tagName("a"));
        
        Iterator<WebElement> it = links.iterator();
        
        while(it.hasNext()){
            
            url = it.next().getAttribute("href");
            
            //System.out.println(url);
        
            if(url == null || url.isEmpty()||url.startsWith("javascript:void(0)")){
System.out.println("URL is either not configured for anchor tag or it is empty");
                continue;
            }
            
            if(url.startsWith(oldPage)){
                System.out.println(url +" belongs to old Taxmann.com.****");
                Urltest();
                continue;
            }
            
            Urltest();
        }
        
        driver.quit();

    }
    public static void Urltest() {
    	try {
            huc = (HttpURLConnection)(new URL(url).openConnection());
            
            huc.setRequestMethod("HEAD");
            
            huc.connect();
            
            respCode = huc.getResponseCode();
            
            if(respCode >= 400){
                System.out.println(url+" is a broken link");
            }
            else{
                System.out.println(url+" is a valid link");
            }
                
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println(url+" malformed url exception");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println(url+" io exception");
        }
    }
}