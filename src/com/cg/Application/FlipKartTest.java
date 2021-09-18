package com.cg.Application;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cg.ApplicationLevelComponent.Appium;
import com.cg.Logutils.HtmlResult;
import com.sun.tools.javac.util.Assert;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class FlipKartTest {
	public static AndroidDriver<MobileElement> driver;
	//public static AppiumDriver<MobileElement> appiumDriver;
	public FlipKartTest() 
	{   
		try
		{String ApplicationPath="C:/Users/Asif Mustafa/Downloads/Flipkart-7.18.apk";
		DesiredCapabilities c= new DesiredCapabilities();
		c.setCapability(MobileCapabilityType.NO_RESET,"true");
		c.setCapability("sessionoverride", "true");
	    c.setCapability(CapabilityType.BROWSER_NAME, "");
		 c.setCapability("deviceName", "emulator-5554");
		 //capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "RF8N11BFSSF");
		 c.setCapability("platformName", "android");
		 c.setCapability("platformVersion", "11");
		 c.setCapability("app",ApplicationPath);
		 c.setCapability("appPackage", "com.flipkart.android");
		 c.setCapability("appActivity", "com.flipkart.android.SplashActivity");
		 c.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
		 //driver= new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"),c);
		 driver= new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"),c);
		 //Thread.sleep(10000);
		}
		catch(Exception e)
		{System.out.println("Uanble to initialize driver due to :"+e.getMessage());}
	}
	public void selectProductCategory()
	{
		try 
		{ WebDriverWait wait=new WebDriverWait(driver,10);
		
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.ImageButton[@content-desc=\"Open Drawer\"]")));
			 MobileElement menu= driver.findElement(By.xpath("//android.widget.ImageButton[@content-desc=\"Open Drawer\"]"));
			 menu.click();
			 System.out.println("Menu clicked");
			 
			
			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text=\"My Account\"]")));
			 MobileElement category=driver.findElement(By.xpath("//android.widget.TextView[@text=\"My Account\"]"));
			 category.click();
			 System.out.println("All category slected.");
			
				/*
				 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
				 * "(//android.widget.TextView[@index=1])[1]"))); MobileElement
				 * mobileCategory=appiumDriver.findElement(By.xpath(
				 * "(//android.widget.TextView[@index=1])[1]")); mobileCategory.click();
				 */
			// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text=\\\"Flipkart Axis Bank Credit Card\\\"]\"")));
			 Thread.sleep(10000);
			 MobileElement axis=driver.findElement(By.xpath("//android.widget.TextView[@text=\"Flipkart Axis Bank Credit Card\"]"));
			 TouchActions action = new TouchActions(driver);
			 action.scroll(axis, 10, 100);
			 action.perform();


			 /*Thread.sleep(10000);
			 System.out.println("Mobile category clicked.");
			 HashMap<String, Object> scrollObj= new HashMap<>();
			 scrollObj.put("direction", "down");
			 scrollObj.put("text", "Flipkart Axis Bank Credit Card");
			
			 
			 driver.executeScript("mobile:scroll", scrollObj);
			  */
			 driver.quit();
			 
			 
		}
		catch(Exception e)
		{System.out.println(e.getMessage());
		}
	}
     
	public static void main(String[] args) {
		FlipKartTest fk=new FlipKartTest();
		fk.selectProductCategory();

	}

}
