package com.automation;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AutomateECommerceWebsite {
	
	String chromeDriverPath, fireFoxDriverPath;
	WebDriver chromeDriver, fireFoxDriver;
	
	@BeforeTest
	public void beforeTest() {
		chromeDriverPath = "C:\\Users\\Chandan\\OneDrive\\Desktop\\phase5classes/chromedriver.exe";
		fireFoxDriverPath = "C:\\Users\\Chandan\\OneDrive\\Desktop\\firefox/geckodriver.exe";
	}
	
	
	@Test(groups = "ChromeBrowser")
	public void openChromeBrowser() { 
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		chromeDriver = new ChromeDriver();
		
		System.out.println("Driver initialized...");
		
		chromeDriver.get("https://www.flipkart.com/mobile-phones-store");
		
		Long loadtime = (Long) ((JavascriptExecutor) chromeDriver).executeScript("return performance.timing.loadEventEnd - performance.timing.navigationStart;");
		System.out.println("Load time of the website on chrome is :- " + loadtime);
		
		
		
	    		long start = System.currentTimeMillis();
				chromeDriver.get("https://www.flipkart.com/mobile-phones-store");
				long finish = System.currentTimeMillis();
				long totalTime = finish - start; 
				System.out.println("Total Time for page load - " + totalTime + " miliseconds "); 
		
		chromeDriver.manage().window().fullscreen();
	}
	
	
	
	@Test(groups = "FireFoxBrowser")
	public void openFireFoxBrowser() { 
		System.setProperty("webdriver.gecko.driver", fireFoxDriverPath);
		fireFoxDriver = new FirefoxDriver();
		
		System.out.println("Firefox driver initialized...");
		
		fireFoxDriver.get("https://www.flipkart.com/mobile-phones-store");
		
		Long loadtime = (Long) ((JavascriptExecutor) fireFoxDriver).executeScript("return performance.timing.loadEventEnd - performance.timing.navigationStart;");
		System.out.println("Load time of the website on firefox is :- " + loadtime);
		
		fireFoxDriver.manage().window().fullscreen();
	}
	
	
	@Test(groups = "ChromeBrowser", dependsOnMethods = "openChromeBrowser")
	public void testOnChrome() {

		// Searching for the desired product
		chromeDriver.findElement(By.name("q")).sendKeys("iPhone 13");

		chromeDriver.findElement(By.className("L0Z3Pu")).click();
		
		
		// Checking for scrolling is present or not and scroll the page if present
		JavascriptExecutor js = (JavascriptExecutor) chromeDriver;
		
		String execScript = "return document.documentElement.scrollHeight>document.documentElement.clientHeight;";
		Boolean isScrollPresent = (Boolean) (js.executeScript(execScript));
		if (isScrollPresent == true) {
			System.out.println("Scrollbar is present.");
			js.executeScript("scrollBy(0, 3000)");
		} 
		else if (isScrollPresent == false){
			System.out.println("Scrollbar is not present.");
		}
		
		
	
		// Checking for image loaded or not
		WebElement productImages = fireFoxDriver.findElement(By.xpath("//img[@class='_396cs4 _3exPp9']"));
		Boolean isLoaded = (Boolean) ((JavascriptExecutor) chromeDriver).executeScript("return arguments[0].complete " + "&& typeof arguments[0].naturalWidth != \"undefined\" " + "&& arguments[0].naturalWidth > 0", productImages);

		System.out.println(isLoaded ? "Product image is loaded now on Chrome Browser." : "Product image is not loaded yet now on Chrome Browser..");

		
		
		
		
		// Scrolling to the end of the page
		try {
			Object lastHeight = ((JavascriptExecutor) chromeDriver).executeScript("return document.body.scrollHeight");
			
			while (true) {
				((JavascriptExecutor) chromeDriver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
				Thread.sleep(5000);

				Object newHeight = ((JavascriptExecutor) chromeDriver).executeScript("return document.body.scrollHeight");
				
				if (lastHeight.equals(newHeight)) {
					break;
				}
				lastHeight = newHeight;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	  }
	
	
	
	@Test(groups = "FireFoxBrowser", dependsOnMethods = "openFireFoxBrowser")
	public void testOnFireFox() {
		// Searching for the desired product
		fireFoxDriver.findElement(By.name("q")).sendKeys("iPhone 13");
		fireFoxDriver.findElement(By.className("L0Z3Pu")).click();
				
				
		// Checking for scrolling is present or not and scroll the page if present
		JavascriptExecutor js = (JavascriptExecutor) fireFoxDriver;
				
		String execScript = "return document.documentElement.scrollHeight>document.documentElement.clientHeight;";
		Boolean isScrollPresent = (Boolean) (js.executeScript(execScript));
		if (isScrollPresent == true) {
			System.out.println("Scrollbar is present.");
			js.executeScript("scrollBy(0, 3000)");
		} 
		else if (isScrollPresent == false){
			System.out.println("Scrollbar is not present.");
		}
					
				
		// Checking for image loaded or not
		WebElement productImages = fireFoxDriver.findElement(By.xpath("//img[@class='_396cs4 _3exPp9']"));
		Boolean isLoaded = (Boolean) ((JavascriptExecutor) fireFoxDriver).executeScript("return arguments[0].complete " + "&& typeof arguments[0].naturalWidth != \"undefined\" " + "&& arguments[0].naturalWidth > 0", productImages);

		System.out.println(isLoaded ? "Product image is loaded now on FireFox Browser." : "Product image is not loaded yet now on FireFox Browser..");		
		
		// Scrolling to the end of the page
		try {
			Object lastHeight = ((JavascriptExecutor) fireFoxDriver).executeScript("return document.body.scrollHeight");
			
			while (true) {
				((JavascriptExecutor) fireFoxDriver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
				Thread.sleep(5000);

				Object newHeight = ((JavascriptExecutor) fireFoxDriver).executeScript("return document.body.scrollHeight");
				
				if (lastHeight.equals(newHeight)) {
					break;
				}
				lastHeight = newHeight;
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	  }
	
	
	@AfterTest
  	public void afterTest() {
		chromeDriver.close();
		fireFoxDriver.close();
		System.out.println("Test completed and drivers are closed.");
  	}
}
