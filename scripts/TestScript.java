package com.snapdeal.scripts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestScript{
	@Test
	@Parameters ("sBrowser")
	public void test(String sBrowser){
		WebDriver driver = null;
		if(sBrowser.equals("chrome")){
			System.setProperty("webdriver.chrome.driver","./driver/chromedriver.exe");
			driver = new ChromeDriver();
		}
		else if(sBrowser.equals("firefox")){
			System.setProperty("webdriver.gecko.driver","./driver/geckodriver.exe");
			driver = new FirefoxDriver();
		}
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://www.snapdeal.com/");
		WebElement SearchBox = driver.findElement(By.xpath("//input[@placeholder='Search products & brands']"));
		if(SearchBox.isDisplayed()) {
			System.out.println("Search text box is displayed.");
			SearchBox.sendKeys("Samsung android phone");	
			WebElement SearchButton = driver.findElement(By.xpath("//span[@class='searchTextSpan']"));
			SearchButton.click();
			WebElement SortByDropDown = driver.findElement(By.xpath("//i[@class='sd-icon sd-icon-expand-arrow sort-arrow']"));
			SortByDropDown.click();
			WebElement PriceLowToHigh = driver.findElement(By.xpath("//*[@id=\"content_wrapper\"]/div[7]/div[5]/div[3]/div[1]/div/div[2]/ul/li[3]"));
			PriceLowToHigh.click();
			sleep(5000);
			List<WebElement> AllProductName = driver.findElements(By.xpath("//p[@class='product-title ']"));
			System.out.println("Total Number of Products: "+AllProductName.size());
			List<WebElement> AllProductPrice = driver.findElements(By.xpath("//span[@class='lfloat product-price']")); 
			System.out.println("Total Number of Price: "+AllProductPrice.size());
			int priceArray[];
			priceArray = new int[AllProductPrice.size()];
			List <Integer> intArray = new ArrayList<Integer>();
			for(int k = 0;k<AllProductPrice.size();k++){
				System.out.println(AllProductName.get(k).getText());
				priceArray[k]=Integer.valueOf(AllProductPrice.get(k).getText().replaceAll("[^0-9]", ""));
				intArray.add(priceArray[k]);
				System.out.println(priceArray[k]);
			}
			int a = Collections.min(intArray);
			System.out.println("Least Price of Product: "+a);
			for(int l = 0;l<AllProductPrice.size();l++){
				if(a==priceArray[l]) {
					System.out.println(AllProductName.get(l).getText());
					AllProductName.get(l).click();
				}
			}
			
		    ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
		    driver.switchTo().window(tabs2.get(0));
		    driver.close();
		    driver.switchTo().window(tabs2.get(1));
		    String ProductName = driver.findElement(By.xpath("//*[@id=\"productOverview\"]/div[2]/div/div[1]/div[1]/div[1]/h1")).getText();
		    String ProductPrice = driver.findElement(By.xpath("//*[@id=\"buyPriceBox\"]/div[2]/div[1]/div[1]/div[1]/span[1]/span")).getText();
		    int Value = Integer.valueOf(ProductPrice);
		    WebElement AddToCart = driver.findElement(By.xpath("//span[text()='add to cart']"));
		    AddToCart.click();
		    String ProductNameInCart = driver.findElement(By.xpath("//*[@id=\"cartProductContainer\"]/div/div[2]/div[1]/div/div[2]/span[1]/a")).getText();
		    String ProductPriceInCartWithDigits = driver.findElement(By.xpath("//*[@id=\"cartProductContainer\"]/div/div[2]/div[2]/div/div[1]/div[2]/span")).getText();
			String ProductPriceInCart = ProductPriceInCartWithDigits.replaceAll("[^0-9]", "");
			System.out.println(ProductPriceInCart);
			int ValueInCart = Integer.valueOf(ProductPriceInCart);
			System.out.println(ValueInCart);
			if(ProductNameInCart.equals(ProductName)) {
				System.out.println("Right Product has been added to cart. \nProduct selected was: "+ProductName+" and \nProduct displaying in cart is: "+ProductNameInCart+" \nBoth are same.");
		    	if(ProductPriceInCart.equals(ProductPrice)) {
		    		System.out.println("Product Price in cart is same as it was selected.");
		    	}else {
		    		System.out.println("Product Price has some difference in the cart as it was selected.");
			    	int difference = ValueInCart - Value;
			    	System.out.println("Price Difference: "+difference);
			    	if (difference < 0) {
			    		System.out.println("Product Value in Product page is displaying more. The difference of: "+(-(difference)));
			    	}else if(difference > 0) {
			    		System.out.println("Product Value in Cart page is displaying. The difference of: "+difference);
			    	}
		    	}
		    }else {
		    	System.out.println("Wrong Product has been added to cart. Product selected was: "+ProductName+" and Product displaying in cart is: "+ProductNameInCart);
		    }
		}else {
			System.out.println("Search text box is not displayed.");
		}
		driver.close();
	}
	public void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}