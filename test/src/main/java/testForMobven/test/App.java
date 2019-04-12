package testForMobven.test;


import java.lang.invoke.MethodHandles;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import testForMobven.test.compare.TestAssertions;




public class App 
{	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
	
    public static void main( String[] args )
    {   
    	System.setProperty("webdriver.gecko.driver", "C:\\Users\\EXT02D6549\\eclipse-workspace\\test\\drivers\\geckodriver\\geckodriver.exe");
        //Selenium webdriver for firefox
    	WebDriver driver = new FirefoxDriver();
        try {
        	
        	//Relative path for gecko driver(for firefox)
        	String url = "https://www.amazon.com.tr"; 
        	String searchKey = "iPhone";
        	String xpathOfSecondPriceBasket = "//a[@id='mbc-buybutton-addtocart-2-announce']";
        	String xpathOfFirstPriceBasket = "//input[@id='add-to-cart-button']";
        	String selectedBoxPath = "";
        	
            double firstPrice = 0.0;
            double secondPrice = 0.0;
                        
            driver.get(url);
            
            //Textbox for search item in amazon
            WebElement searchTextBox = driver.findElement(By.id("twotabsearchtextbox"));
            //Step 1:Clear the amazon searchbox
            searchTextBox.clear(); 
            //Step 2:Write "iphone" in the search box
            searchTextBox.sendKeys(searchKey);
            Thread.sleep(3000);
            //Step 3:Write empty space to have suggestions
            searchTextBox.sendKeys(" ");
            Thread.sleep(3000);
            //Step 4:Click iphonex in suggestions
            WebElement searchSuggestions = driver.findElement(By.id("issDiv0"));
            searchSuggestions.click();
            //-----------------------------------
            Thread.sleep(3000);
            //Step 5:Click the item link on iphonex page  
            WebElement suggestionsList = driver.findElement(By.xpath("//span[contains(text(),'Apple iPhone X, 64 GB, Uzay Gri (Apple Türkiye Gar')]"));
            suggestionsList.click();
            
            Thread.sleep(3000);
            //Step 6:Get the first price on the page of item
            WebElement suggestionsListPrice = driver.findElement(By.id("priceblock_ourprice"));
            //Reformatting the price string to have the correct number format
            firstPrice = Double.valueOf(suggestionsListPrice.getText().replaceAll("TL", "").trim().replaceAll("\\.", "").replaceAll(",", "\\.").trim());
            //Step 7:Get the second price on the page of item
            WebElement secondItemValue = driver.findElement(By.id("mbc-price-2"));
            secondPrice = Double.valueOf(secondItemValue.getText().replaceAll("TL", "").trim().replaceAll("\\.", "").replaceAll(",", "\\.").trim());
            //Step 8:Comparing first and second price
            TestAssertions ta = new TestAssertions();
            ta.testAssert(firstPrice, secondPrice);
            
            Result result = JUnitCore.runClasses(ta.getClass()); 
            WebElement basket = null;
            //Step 9:Adding the price selected in the basket
            //First price
            if(result.getFailureCount() > 0 ) {
            	
            	selectedBoxPath = xpathOfFirstPriceBasket;
            	logger.info("first price has been selected!");
            //Second Price
            }else {
            	
            	selectedBoxPath = xpathOfSecondPriceBasket;
            	logger.info("second price has been selected!");
            }
        	basket = driver.findElement(By.xpath(selectedBoxPath));
        	logger.info(selectedBoxPath);
        	basket.click();	
        	//--------------------------------	
            Thread.sleep(3000);
            logger.info("test has been completed successfuly...");			
		} catch (InterruptedException e) 
        {
			logger.error(e.getMessage());
		}finally {
			
			driver.close();
			
		}
        
    }
}
