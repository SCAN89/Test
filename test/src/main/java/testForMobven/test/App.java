package testForMobven.test;

import org.apache.log4j.Logger;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import testForMobven.test.compare.TestAssertions;


public class App 
{	
	final static Logger logger = Logger.getLogger(App.class);
	
    public static void main( String[] args )
    {    	
        
        try {
        	
        	System.setProperty("webdriver.gecko.driver", "C:\\Users\\EXT02D6549\\eclipse-workspace\\test\\drivers\\geckodriver\\geckodriver.exe");
        	String url = "https://www.amazon.com.tr"; 
        	String searchKey = "iPhone";
        	String xpathOfSecondPriceBasket = "//a[@id='mbc-buybutton-addtocart-2-announce']";
        	String xpathOfFirstPriceBasket = "//input[@id='add-to-cart-button']";
        	String selectedBoxPath = "";
        	
            double firstPrice = 0.0;
            double secondPrice = 0.0;
            
            WebDriver driver = new FirefoxDriver();
            driver.get(url);
            
            WebElement searchTextBox = driver.findElement(By.id("twotabsearchtextbox"));
            searchTextBox.clear(); 
            searchTextBox.sendKeys(searchKey);
            Thread.sleep(3000);
            searchTextBox.sendKeys(" ");
            Thread.sleep(3000);
            WebElement searchSuggestions = driver.findElement(By.id("issDiv0"));
            System.out.println("value :" + searchSuggestions.getText());
            searchSuggestions.click();
            Thread.sleep(3000);
            WebElement suggestionsList = driver.findElement(By.xpath("//span[contains(text(),'Apple iPhone X, 64 GB, Uzay Gri (Apple Türkiye Gar')]"));
            System.out.println("value : " + suggestionsList.getText());
            
            suggestionsList.click();
            Thread.sleep(3000);
            
            WebElement suggestionsListPrice = driver.findElement(By.id("priceblock_ourprice"));
            firstPrice = Double.valueOf(suggestionsListPrice.getText().replaceAll("TL", "").trim().replaceAll("\\.", "").replaceAll(",", "\\.").trim());
            
            WebElement secondItemValue = driver.findElement(By.id("mbc-price-2"));
            
            secondPrice = Double.valueOf(secondItemValue.getText().replaceAll("TL", "").trim().replaceAll("\\.", "").replaceAll(",", "\\.").trim());
            
            TestAssertions ta = new TestAssertions();
            ta.testAssert(firstPrice, secondPrice);
            
            Result result = JUnitCore.runClasses(ta.getClass()); 
            WebElement basket = null;
            //birinci büyük
            if(result.getFailureCount() > 0 ) {
            	
            	selectedBoxPath = xpathOfFirstPriceBasket;
            //ikinci büyük	
            }else {
            	
            	selectedBoxPath = xpathOfSecondPriceBasket;
            }
        	basket = driver.findElement(By.xpath(selectedBoxPath));
        	logger.info(selectedBoxPath);
        	basket.click();	
            	
            Thread.sleep(3000);
			driver.close();
			
		} catch (InterruptedException e) 
        {
			System.out.println("Error : "+ e.getMessage());
			logger.error(e.getMessage());
		}
    }
}
