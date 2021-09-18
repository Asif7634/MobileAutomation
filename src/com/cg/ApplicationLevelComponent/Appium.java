package com.cg.ApplicationLevelComponent;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.cg.Logutils.HtmlResult;
import com.cg.UIDriver.AppiumUIDriver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class Appium {

	public static AppiumDriver driver;
	public static IOSDriver IosDriver;
	public static String DeviceName = "";
	public static String PlatformName = "";
	public static String PlatformVersion = "";
	public static String ApplicationPath = "";
	public static String AppPackage = "";
	public static String NoReset = "";
	public static String SessionOverride = "";
	public static String OSTypeName = "";
	public static String BundleID="";
	protected static AppiumUIDriver appUIDriver ;
	//public static IOSDriver LocalIosDriver = null ;
	//public static AndroidDriver LocalAndriodDriver = null ;

	//appium launchpad

	public static void StartAppiumAndLaunchApp() throws MalformedURLException
	{

		DesiredCapabilities capabilities = new DesiredCapabilities();
		IOSDriver LocalIosDriver = null ;
		AndroidDriver LocalAndriodDriver = null ;
		
		DeviceName = EggPlant.getValueFromExcel("DeviceName");
		PlatformName = EggPlant.getValueFromExcel("PlatformName");
		PlatformVersion = EggPlant.getValueFromExcel("PlatformVersion");
		ApplicationPath = EggPlant.getValueFromExcel("ApplicationPath");
		AppPackage = EggPlant.getValueFromExcel("AppPackage");
		NoReset = EggPlant.getValueFromExcel("NoReset");
		SessionOverride = EggPlant.getValueFromExcel("SessionOverride");
		//BundleID = EggPlant.getValueFromExcel("BundleID");
		BundleID ="com.mcdonalds.gma.enterprise.uk";
		//ApplicationPath ="/Users/Downloads/McDonaldsGMA-QA-5.ipa";
		ApplicationPath="C:/Users/Asif Mustafa/Downloads/Flipkart-7.18.apk";
		/*
		 * capabilities.setCapability("noReset","true");
		 * capabilities.setCapability("sessionoverride", SessionOverride);
		 * capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
		 * capabilities.setCapability("deviceName", "RF8N11BFSSF");
		 * capabilities.setCapability("platformName", "android");
		 * capabilities.setCapability("platformVersion", "11");
		 * capabilities.setCapability("app",ApplicationPath);
		 * capabilities.setCapability("appPackage", "com.flipkart.android");
		 * capabilities.setCapability("appActivity",
		 * "com.flipkart.android.SplashActivity");
		 * capabilities.setCapability("automationName", "UiAutomator2");
		 */

		capabilities.setCapability(MobileCapabilityType.NO_RESET,"true");
		capabilities.setCapability("sessionoverride", SessionOverride);
	    capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
		 capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
		 //capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "RF8N11BFSSF");
		 capabilities.setCapability("platformName", "android");
		 capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "11");
		 capabilities.setCapability(MobileCapabilityType.APP,ApplicationPath);
		 capabilities.setCapability("appPackage", "com.flipkart.android");
		 capabilities.setCapability("appActivity", "com.flipkart.android.SplashActivity");
		 capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
	/*
	 * capabilities.setCapability("noReset","true");
	 * capabilities.setCapability("fullReset", "false");
	 * capabilities.setCapability("newCommandTimeout", 10000 *30);
	 * capabilities.setCapability("sessionoverride", SessionOverride);
	 * capabilities.setCapability("bundleId", BundleID);
	 * capabilities.setCapability("udid",
	 * "51272ab973b4c1fdb3825ffa0c4780d3e17e6be4");
	 * capabilities.setCapability("automationName", "XCUITest");
	 * capabilities.setCapability("deviceName", "iPhone 3");
	 * capabilities.setCapability("platformName", "XCUITest");
	 * capabilities.setCapability("platformVersion", "11.2.1");
	 * capabilities.setCapability("app",ApplicationPath);
	 */

		// Start android driver I used 4727 port by default it will be 4723
		try {
			
			//OSTypeName = System.getProperty("OS.name");
			OSTypeName ="Android";
			if (OSTypeName.equalsIgnoreCase("MacOS"))
			{
				LocalIosDriver = new IOSDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
				driver = LocalIosDriver;
				
			//LocalAndriodDriver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
			}
			else
			{
				 LocalAndriodDriver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
					
					 driver=LocalAndriodDriver; Thread.sleep(3000); 
					
				//driver.findElement(By.id("//XCUIElementTypeButton[@name='ok']"));
				
				
			}
			//Thread.sleep(10000);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		appUIDriver = new AppiumUIDriver();
		// Specify the implicit wait of 5 second
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		//System.out.println("done - driver");
		System.err.println("Appium Server has Started");
	}

	public static void StopAppiumServer()
	{
		try
		{
			driver.quit();
			HtmlResult.passed("Appium Server and App Stop", "Mobile App object should be closed", "Mobile App object successfully closed");
		}

		catch (Exception e)
		{
			e.printStackTrace();
		}
	}


}
