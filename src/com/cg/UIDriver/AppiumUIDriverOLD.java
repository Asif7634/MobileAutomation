package com.cg.UIDriver;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cg.ApplicationLevelComponent.Appium;
import com.cg.ApplicationLevelComponent.EggPlant;
import com.cg.Logutils.HtmlResult;
import com.cg.Logutils.Logging;
import com.cg.SeleniumTestSetup.TestSetUp;
import com.cg.TestCaseRunner.TestCaseRunner;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.PressesKeyCode;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.android.Connection;
import io.appium.java_client.ios.IOSMobileCommandHelper;

public class AppiumUIDriverOLD {

	//public static AndroidDriver driver;
	public static AppiumDriver driver;
	Logging TestUiLogger=TestCaseRunner.TestCaseLogger;// to put logging messages into the test case level logging file
	Logging FrameWorkUiLogger=TestCaseRunner.FrameWorkLogger;// to put logging messages into the framework level logging file
	boolean BlnSwipeFound;

	int height,width,x, starty, endy;

	Dimension CurrentMobileSize;

	// Todo

	// Launch App
	/**
	 *
	 */
	public AppiumUIDriverOLD() {

		driver = Appium.driver;
	}


	@SuppressWarnings("deprecation")
	public void ReadTable(String strNutritionValue)
	{
		//String strNutritionValue = "sugars";
		do
		{
			height = CurrentMobileSize.getHeight();
			width = CurrentMobileSize.getWidth();
			x=width/2;
			starty = (int) (height*0.40);
			endy= (int) (width*0.20);
			try
			{
				//driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
				BlnSwipeFound = driver.findElement(By.xpath("//android.widget.TextView[@text='"+strNutritionValue+"']")).isDisplayed();
				BlnSwipeFound = true;
			}
			catch (Exception e)
			{
				BlnSwipeFound = false;
				driver.swipe(x, starty, x, endy, 500);
			}
		} while (!BlnSwipeFound);

		String val = driver.findElement(By.xpath("//*[@resource-id='com.mcdonalds.app.uk.qa:id/nutrion_table']//android.widget.TextView[@text='"+strNutritionValue+"']/../following-sibling::android.widget.FrameLayout[1]/android.widget.TextView")).getText();
		System.out.println(val);
//		HtmlResult.passed("Current Nutrion value", "Current value for " + strNutritionValue, val);
	}

	//screenshot method
	public static void takeScreenShot(String FilePath)
	{
		try {
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(FilePath.trim()));
			scrFile.delete();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	//Stop Appium Server
	/*
	 * To Stop Appium application driver object
	 */


	public void wait(int timeout)
	{
		try
		{
			driver.wait(timeout);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

/*	public boolean getNetworkConnection(String TypeOfNetwork,String ExpectedChoice)
	{
		try
		{
			String strNetworkChoice= TypeOfNetwork.trim();
			String strExpectedChoice = ExpectedChoice.trim();
			if (strNetworkChoice.equalsIgnoreCase("NONE"))
			{
				driver.setConnection(Connection.NONE);
				return true;
			}
			else if (strNetworkChoice.equalsIgnoreCase("AIRPLANE"))
			{
				driver.setConnection(Connection.AIRPLANE);
				return true;
			}
			else if (strNetworkChoice.equalsIgnoreCase("WIFI"))
			{
				driver.setConnection(Connection.WIFI);
				return true;
			}
			else if  (strNetworkChoice.equalsIgnoreCase("ALL"))
			{
				driver.setConnection(Connection.ALL);
				return true;
			}
			else if  (strNetworkChoice.equalsIgnoreCase("DATA"))
			{
				driver.setConnection(Connection.DATA);
				return true;
			}
			else
			{
				System.out.println("Wrong selection");
				return false;
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			return false;

		}
	}
	*/

	public static void StopAppiumServer()
	{
		try
		{
			driver.quit();
//			HtmlResult.passed("Appium Server and App Stop", "Mobile App object should be closed", "Mobile App object successfully closed");
		}

		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// webdriver methods
	public boolean waitImplicitly(long TimeOutInSec)	//implicitly wait
	{
		try {
			driver.manage().timeouts().implicitlyWait(TimeOutInSec, TimeUnit.SECONDS);
			return true;
		} catch (WebDriverException e) {
			TestUiLogger.error("TESTCASE", "Error  while Implicit wait, ' "+e.getMessage()+" '");
			return false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Error  while Implicit wait, ' "+e.getMessage()+" '");
			return false;
		}
	}

	//isDisplayed methods
	public boolean isDisplayedByName(String locator)			//is Disable through name
	{

		try
		{
			locator=locator.trim();
			int Counter = 0;
			while(Counter<=3)
			{
				if(driver.findElement(By.name(locator)).isDisplayed())
				{
					return true;
				}
				else
				{
					Counter++;
				}
			}

			if(Counter>=3)
			{
				return false;
			}
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to check if locator ' "+locator+" '  is displayed , '"+e.getMessage()+"'");
			return false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to check if locator ' "+locator+" '  is displayed , '"+e.getMessage()+"'");
			return false;
		}
		return false;
	}

	public boolean isDisplayedById(String locator)			//is Displayed through id
	{
		try
		{
			locator=locator.trim();
			String LocalLocator = getIdentifire("id", locator).trim();
			int Counter = 0;
			while(Counter<=3)
			{
				if(driver.findElement(By.id(LocalLocator)).isDisplayed())
				{
					return true;
				}
				else
				{
					Counter++;
				}
			}

			if(Counter>=3)
			{
				return false;
			}
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to check if locator ' "+locator+" '  is displayed , '"+e.getMessage()+"'");
			return false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to check if locator ' "+locator+" '  is displayed , '"+e.getMessage()+"'");
			return false;
		}
		return false;
	}

	public boolean isDisplayedByXpath(String locator)			//is Disable through xpath
	{
		try
		{
			locator=locator.trim();
			int Counter = 0;
			while(Counter<=3)
			{
				if(driver.findElement(By.xpath(locator)).isDisplayed())
				{
					return true;
				}
				else
				{
					Counter++;
				}
			}

			if(Counter>=3)
			{
				return false;
			}
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to check if locator ' "+locator+" '  is displayed , '"+e.getMessage()+"'");
			return false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to check if locator ' "+locator+" '  is displayed , '"+e.getMessage()+"'");
			return false;
		}

		return false;
	}


	//click by xpath
	public boolean clickByXpath(String strObjectName)	//for click() through xpath
	{
		String strObjectProperty;
		boolean blnFlag=false;
		try
		{
			strObjectProperty=getIdentifire("xapth",strObjectName.trim());
			if(strObjectProperty=="")
			{
				return false;
			}
			else
			{
				driver.findElement(By.xpath(strObjectProperty)).click();
				return true;
			}
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to click web element-' "+strObjectName+" ','"+e.getMessage()+"'");
			blnFlag=false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to click web element-' "+strObjectName+" ','"+e.getMessage()+"'");
			blnFlag=false;
		}
		if(!blnFlag)
			System.out.println();
		return blnFlag;
	}

	public boolean clickOnXpath(String strObjectName)	//for click() through xpath
	{
		boolean blnFlag=false;
		try
		{
			driver.findElement(By.xpath(strObjectName)).click();
			return true;
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to click web element-' "+strObjectName+" ','"+e.getMessage()+"'");
			blnFlag=false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to click web element-' "+strObjectName+" ','"+e.getMessage()+"'");
			blnFlag=false;
		}
		if(!blnFlag)
			System.out.println();
		return blnFlag;
	}

	
	
	public String getIdentifire(String Identifire,String ObjectName)
	{
		try
		{
			ObjectName = ObjectName.trim();
			Identifire = Identifire.trim();
			if(ObjectName.length()>0)
			{
				List<Map<String,String>> AppiumObjectList = TestCaseRunner.AppiumObjectList;

				for(Map<String,String> ObjectMap : AppiumObjectList)
				{
					String LocalObjectName = ObjectMap.get("Object");
					if(LocalObjectName.equals(ObjectName))
					{
						return ObjectMap.get(Identifire);
					}
				}
			}
			else
			{
				return "";
			}
		}
		catch(Exception e)
		{
			return "";
		}
		return "";
	}

	
	
	public boolean clickButton_old(String strObjectName)
	{
		Boolean blnFlag=false;

		try
		{
			blnFlag=clickById(strObjectName);
			if(!blnFlag)
			{
				blnFlag=clickByXpath(strObjectName);
				if(!blnFlag)
				{
					blnFlag=clickByName(strObjectName);
				}
			}

		}
		catch(Exception e)
		{
			blnFlag=false;
		}
		return blnFlag;
	}

	public boolean clickById(String strObjectName)				//for click() by id
	{
		String strObjectProperty;
		boolean blnFlag=false;
		try
		{
			strObjectProperty=getIdentifire("id",strObjectName.trim());
			if(strObjectProperty=="")
			{
				return false;
			}
			else
			{
				driver.findElement(By.id(strObjectProperty)).click();
				return true;
			}
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to click web element-' "+strObjectName+" ','"+e.getMessage()+"'");
			blnFlag=false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to click web element-' "+strObjectName+" ','"+e.getMessage()+"'");
			blnFlag=false;
		}
		return blnFlag;
	}

	public boolean clickByName(String strObjectName)	//for click() through name
	{
		String strObjectProperty;
		boolean blnFlag=false;

		try
		{
			strObjectProperty=getIdentifire("name",strObjectName.trim());
			if(strObjectProperty=="")
			{
				blnFlag=false;
			}
			else
			{
				driver.findElement(By.name(strObjectProperty)).click();
				return true;
			}
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to click web element-' "+strObjectName+" ',' "+e.getMessage()+" '");
			blnFlag=false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to click web element-' "+strObjectName+" ','"+e.getMessage()+"'");
			blnFlag=false;
		}
		return blnFlag;
	}

	public Dimension getWindowSize()
	{
		try
		{
			return driver.manage().window().getSize();
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", " Unable to get screen size "+e.getMessage()+" '");
			return null;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", " Unable to get screen size "+e.getMessage()+"'");
			return null;
		}

	}

	public String getTextByXpath(String XpathExpression)
	{
		try
		{
			return driver.findElement(By.xpath(XpathExpression)).getText().toString();
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", " Unable to get text "+e.getMessage()+" '");
			return null;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", " Unable to get text "+e.getMessage()+"'");
			return null;
		}
	}

	public String getTextById(String Id)
	{
		try
		{
			return driver.findElement(By.id(Id)).getText().toString();
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", " Unable to get text "+e.getMessage()+" '");
			return null;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", " Unable to get text "+e.getMessage()+"'");
			return null;
		}
	}

	public String getText(String Identifire,String ObejectName)
	{
		try
		{
			//String LocalObject = getIdentifire(Identifire, ObejectName);
			String LocalObject=Identifire;
			if(LocalObject!=null && LocalObject!="")
			{
				if(Identifire.equalsIgnoreCase("id"))
				{
					return driver.findElement(By.id(ObejectName)).getText();
				}
				else if(Identifire.equalsIgnoreCase("Xpath"))
				{
					return driver.findElement(By.xpath(ObejectName)).getText();
				}
				else if(Identifire.equalsIgnoreCase("Name"))
				{
					return driver.findElement(By.name(ObejectName)).getText();
				}
				else if(Identifire.equalsIgnoreCase("iOSID"))
				{
					return driver.findElementByAccessibilityId(ObejectName).getText();
				}
				else if(Identifire.equalsIgnoreCase("iOSXpath"))
				{
					return driver.findElementByXPath(ObejectName).getText();				}
				else
				{
					return "";
				}
			}
			else
			{
				return "";
			}
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", " Unable to get text "+e.getMessage()+" '");
			return "";
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", " Unable to get text "+e.getMessage()+"'");
			return "";
		}
	}



	public boolean openApp()
	{
		try
		{
			driver.launchApp();
			return true;
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", " Unable to launch app "+e.getMessage()+" '");
			return false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", " Unable to launch app "+e.getMessage()+"'");
			return false;
		}
	}

	public boolean closeApp()
	{
		try
		{
			driver.closeApp();
			return true;
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", " Unable to launch app "+e.getMessage()+" '");
			return false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", " Unable to launch app "+e.getMessage()+"'");
			return false;
		}
	}

	//send data to empty text boxes
	public boolean sendkeysId(String strObjectName,String Value)		//for sendkeys through id
	{
		String strObjectProperty;
		boolean blnFlag=false;
		try
		{
			strObjectProperty=getIdentifire("id",strObjectName.trim());
			if(strObjectProperty=="")
			{
				blnFlag=false;
			}
			else
			{
				blnFlag=waitExplicitlyById(strObjectProperty,TestSetUp.TimeOutInSec);
				if(blnFlag)
				{
					if(driver.findElement(By.id(strObjectProperty)).getLocation().x>0)
					{
						driver.findElement(By.id(strObjectProperty)).sendKeys(Value.trim());
						blnFlag=true;
					}
				}
			}
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to send keys ' "+Value+" ' by id -' "+strObjectName+" ','"+e.getMessage()+"'");
			blnFlag=false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to send keys ' "+Value+" ' by id -' "+strObjectName+" ','"+e.getMessage()+"'");
			blnFlag=false;
		}
		return blnFlag;
	}
	public boolean isObjectDisplayed(String identifier, String locator)			//is Displayed through id
	{
		int Counter = 0;
		try
		{
			locator=locator.trim();
			//String LocalLocator = getIdentifire(identifier, locator).trim();
			
			while(Counter<=1)
			{
				if (identifier.equalsIgnoreCase("id"))
				{	
					Counter ++;
					if(driver.findElement(By.id(locator)).isDisplayed())
					{
						return true;
					}
				}
				else if (identifier.equalsIgnoreCase("xpath"))
				{
					Counter ++;
					if(driver.findElement(By.xpath(locator)).isDisplayed())
					{
						return true;
					}
				}
				else if (identifier.equalsIgnoreCase("name"))
				{
					Counter ++;
					if(driver.findElement(By.name(locator)).isDisplayed())
					{
						return true;
					}
				}
				
				else if (identifier.equalsIgnoreCase("iOSXpath"))
				{	
					Counter ++;
					if(driver.findElementByXPath(locator).isDisplayed())
					{
						return true;
					}
				}
				else if (identifier.equalsIgnoreCase("iOSID"))
				{
					Counter ++;
					if(driver.findElementByAccessibilityId(locator).isDisplayed())
					{
						return true;
					}
				}
				else
				{
					Counter++;
				}
			}

			if(Counter>=3)
			{
				return false;
			}
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to check if locator ' "+locator+" '  is displayed , '"+e.getMessage()+"'");
			Counter++;
			return false;
		}
		catch(Exception e)
		{
			
		TestUiLogger.error("TESTCASE", "Unable to check if locator ' "+locator+" '  is displayed , '"+e.getMessage()+"'");
		Counter ++;
			return false;
		}
		return false;
	}
	public boolean waitExplicitlyById(String ID,long TimeOutInSec)
	{
		try {
			ID=ID.trim();
			(new WebDriverWait(driver, TimeOutInSec)).until(ExpectedConditions.presenceOfElementLocated(By.id(ID)));

			return true;
		} catch (WebDriverException e) {
			TestUiLogger.error("TESTCASE", "Error while explicit wait by ' "+ID+" ',' "+e.getMessage()+" '");
			return false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Error while explicit wait by ' "+ID+" ',' "+e.getMessage()+" '");
			return false;
		}
	}
	public boolean pressMobileKey(String mobileKeyName)
	{
		try
		{

			String strMobileKeyName= mobileKeyName.trim();

			if (strMobileKeyName.equalsIgnoreCase("BACK") ||strMobileKeyName.equalsIgnoreCase("BACK Button"))
			{
				((PressesKeyCode) driver).pressKeyCode(AndroidKeyCode.BACK);
				return true;
			}
	
			else if (strMobileKeyName.equalsIgnoreCase("BACKSPACE") ||strMobileKeyName.equalsIgnoreCase("BACKSPACE Button"))
			{
				((PressesKeyCode) driver).pressKeyCode(AndroidKeyCode.BACKSPACE);
				return true;
			}
			else if (strMobileKeyName.equalsIgnoreCase("SEARCH") ||strMobileKeyName.equalsIgnoreCase("SEARCH Button"))
			{
				((PressesKeyCode) driver).pressKeyCode(AndroidKeyCode.KEYCODE_SEARCH);
				return true;
			}
			else if (strMobileKeyName.equalsIgnoreCase("ENTER") ||strMobileKeyName.equalsIgnoreCase("ENTER Button"))
			{
				((PressesKeyCode) driver).pressKeyCode(AndroidKeyCode.ENTER);
				return true;
			}
			else if (strMobileKeyName.equalsIgnoreCase("SPACE") ||strMobileKeyName.equalsIgnoreCase("SPACE Button"))
			{
				((PressesKeyCode) driver).pressKeyCode(AndroidKeyCode.SPACE);
				return true;
			}
			else if (strMobileKeyName.equalsIgnoreCase("HOME") ||strMobileKeyName.equalsIgnoreCase("HOME Button"))
			{
				((PressesKeyCode) driver).pressKeyCode(AndroidKeyCode.HOME);
				return true;
			}
			else
			{
				System.err.println("Key is not correct please enter valid key for operation");
				return false;
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			return false;
		}
	}
	

	public String getDynamicObjectProperty(String strObjectName,String strObjectDynamic)
	{
		try
		{
			boolean blnFound = false;
			boolean BlnCurrentObject = false;
			//reading data from Excel (Property Map) for Object priority
			String Str_GMA_Mobile_Locators = EggPlant.getValueFromExcel("GMA_Mobile_Locators_Android");
			String[] arr_GMA_Mobile_Locators = Str_GMA_Mobile_Locators.split(";");
			for (String strCurrentLocator : arr_GMA_Mobile_Locators)
			{
				try
				{
					strCurrentLocator = strCurrentLocator.trim();
					if (!BlnCurrentObject)
					{
						String CurrentObject= getDynamicIdentifire(strCurrentLocator, strObjectName,strObjectDynamic) ;
						if(CurrentObject!=null && CurrentObject.length()>1)
						{
							blnFound =isObjectDisplayed(strCurrentLocator,CurrentObject);
							if (blnFound)
							{
								return strCurrentLocator+"#"+ CurrentObject ;
							}
							else
							{
								//return "";
								;
							}
						}

					}
				}
				catch (Exception e)
				{
					continue;
				}

			}
			if(!blnFound)
			{
				return "false";
			}
		}

		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", " Unable to get text "+e.getMessage()+" '");
			return "";
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", " Unable to get text "+e.getMessage()+"'");
			return "";
		}
		return strObjectName;


	}
	/***************************************************************************************************************
	 * Method Name -
	 * Project - GMA Method
	 * Method Name � GMA 5 Menu Selection
	 * Method Description - This method will add a non unit activity in fixed shifts
	 * Return Type - Boolean value
	 * Parameters - Inputs are fetched from map (inputs are)
	 * Framework - UKIT Master Framework
	 * Author - Prateek Gupta
	 * Creation Date - 09/25/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>
	 * @return

	 ***************************************************************************************************************/


	public String getAppObjectwithLocator(String objectName)
	{
		try
		{
			String strObjectName= objectName;
			strObjectName =strObjectName.trim();
			String CurrentObject =  getObjectProperty(strObjectName);
			if (CurrentObject.equalsIgnoreCase("false"))
			{
				//HtmlResult.failed("Get Object Property from Lookup file", "Locaters should be found" + strObjectName ,  "Locaters is not found" + strObjectName + " and current value is " + CurrentObject);
				return "false";
			}
			else
			{
				return CurrentObject;
			}
		}

		catch (Exception e)
		{
//			HtmlResult.failed("Locate Object Locator", "Locate Object Locator deatails should be present", e.getMessage());
			return null;
		}
	}

	/////


	public boolean getAppObjectFound(String objectName)
	{
		try
		{
			boolean blnFound =false;
			String strObjectName= objectName;
			strObjectName =strObjectName.trim();
			//String strCurrentObjectName =  getObjectProperty(strObjectName);
			String strMobileObjectName  = getAppObjectwithLocator(strObjectName);
			if(!strMobileObjectName.equalsIgnoreCase("false"))
			{
			//String[] arrMobileObjectName = strMobileObjectName.split("#");
			//String objectLocatorName =arrMobileObjectName[0].toString();
			//String objectLocatorValue= arrMobileObjectName[1].toString();
			//blnFound =isObjectDisplayed(objectLocatorName,objectLocatorValue);
			//if (blnFound)
			//{
				
				blnFound = true;
				return blnFound;
			//}
			//else
			//{
				//blnFound = false;
				//return blnFound;
			//}
		}
			else{
				return false;
			}
		}
		

		catch (Exception e)
		{
//			HtmlResult.failed("Locate Object Locator", "Locate Object Locator deatails should be present", e.getMessage());
			return false;
		}
	}
	
	
	public String getObjectProperty(String strObjectName)
	{
		try
		{
			boolean blnFound = false;
			boolean BlnCurrentObject = false;
			//reading data from Excel (Property Map) for Object priority
			String Str_GMA_Mobile_Locators = EggPlant.getValueFromExcel("GMA_Mobile_Locators_Android");
			String[] arr_GMA_Mobile_Locators = Str_GMA_Mobile_Locators.split(";");
			for (String strCurrentLocator : arr_GMA_Mobile_Locators)
			{
				try
				{
					strCurrentLocator = strCurrentLocator.trim();
					if (!BlnCurrentObject)
					{
						String CurrentObject= getIdentifire(strCurrentLocator, strObjectName) ;
						if(CurrentObject!=null && CurrentObject.length()>1)
						{
							blnFound =isObjectDisplayed(strCurrentLocator,CurrentObject);
							if (blnFound)
							{
								return strCurrentLocator+"#"+ CurrentObject ;
							}
							else
							{
								//return "";
								;
							}
						}

					}
				}
				catch (Exception e)
				{
					continue;
				}

			}
			if(!blnFound)
			{
				return "false";
			}
		}

		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", " Unable to get text "+e.getMessage()+" '");
			return "";
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", " Unable to get text "+e.getMessage()+"'");
			return "";
		}
		return strObjectName;


	}
	

	public String getAppObjectDynamicLocator(String objectName,String objectDynamic){
		try
		{
			String strObjectName= objectName;
			strObjectName =strObjectName.trim();
			String strObjectDynamic= objectDynamic;
			strObjectDynamic =strObjectDynamic.trim();
			String CurrentObject =  getDynamicObjectProperty(strObjectName,strObjectDynamic);
			if (CurrentObject.equalsIgnoreCase("false"))
			{
				//HtmlResult.failed("Get Object Property from Lookup file", "Locaters should be found" + strObjectName ,  "Locaters is not found" + strObjectName + " and current value is " + CurrentObject);
				return "false";
			}
			else
			{
				return CurrentObject;
			}
		}

		catch (Exception e)
		{
//			HtmlResult.failed("Locate Object Locator", "Locate Object Locator deatails should be present", e.getMessage());
			return null;
		}
	}


	public boolean getAppObjectFoundDynamic(String objectName,String objectDynamic)
	{
		try
		{
			boolean blnFound =false;
			String strObjectName= objectName;
			strObjectName =strObjectName.trim();
			//String strCurrentObjectName =  getObjectProperty(strObjectName);
			String strMobileObjectName  = getAppObjectDynamicLocator(strObjectName,objectDynamic);
			if (!strMobileObjectName.equalsIgnoreCase("false"))
			{
				String[] arrMobileObjectName = strMobileObjectName.split("#");
				String objectLocatorName =arrMobileObjectName[0].toString();
				String objectLocatorValue= arrMobileObjectName[1].toString();
				
				// String str = new String(objectDynamicLocatorValue);
				 //String objectLocatorValue = str.replaceAll("#", objectDynamic);
			
				//blnFound =isObjectDisplayed(objectLocatorName,objectLocatorValue);
				//if (blnFound)
				//{
					//HtmlResult.failed("Get Object Property from Lookup file", "Locaters should be found" + strObjectName ,  "Locaters is not found" + strObjectName + " and current value is " + CurrentObject);
					blnFound = true;
					return blnFound;
				//}
				//else
				//{
					//blnFound = false;
					//return blnFound;
				//}
			}
			else
			{
				return false;
			}
		}

		catch (Exception e)
		{
//			HtmlResult.failed("Locate Object Locator", "Locate Object Locator deatails should be present", e.getMessage());
			return false;
		}
	}
	public boolean enterText(String ObjectName, String strTextForEnter)
	{
		boolean BlnTextEnter =false;
		try
		{
			String strMobileObjectName  = getAppObjectwithLocator(ObjectName);
			String[] arrMobileObjectName = strMobileObjectName.split("#");
			String objectLocatorName =arrMobileObjectName[0].toString();
			String objectLocatorValue= arrMobileObjectName[1].toString();

			if(objectLocatorName!=null && objectLocatorValue!="")
			{
				if(objectLocatorName.equalsIgnoreCase("id"))
				{
					driver.findElement(By.id(objectLocatorValue)).sendKeys(strTextForEnter);
					BlnTextEnter =true;
					return BlnTextEnter;

				}
				else if(objectLocatorName.equalsIgnoreCase("Xpath"))
				{
					driver.findElement(By.xpath(objectLocatorValue)).sendKeys(strTextForEnter);
					BlnTextEnter =true;
					return BlnTextEnter;
				}
				else if(objectLocatorName.equalsIgnoreCase("Name"))
				{
					driver.findElement(By.name(objectLocatorValue)).sendKeys(strTextForEnter);
					BlnTextEnter =true;
					return BlnTextEnter;
				}
				else if(objectLocatorName.equalsIgnoreCase("iOSXpath"))
				{
					driver.findElementByXPath(objectLocatorValue).sendKeys(strTextForEnter);
					BlnTextEnter =true;
					return BlnTextEnter;
				}

				else if(objectLocatorName.equalsIgnoreCase("iOSID"))
				{
					driver.findElementByAccessibilityId(objectLocatorValue).sendKeys(strTextForEnter);
					BlnTextEnter =true;
					return BlnTextEnter;
				}
				else
				{
					BlnTextEnter =false;
					return BlnTextEnter;
				}

			}
		}
		catch(Exception e)
		{
			TestUiLogger.error("Enter Text in object ", "Unable to enter text if locator is displayed , '"+e.getMessage()+"'");
			BlnTextEnter =false;
			return BlnTextEnter;
		}



		return BlnTextEnter;
	}


	////

	/////

	public boolean clickButton(String ObjectName)
	{
		boolean BlnClick =false;
		try
	{
			String[] arrObjects = ObjectName.trim().split("#");
			for (String strObjectValue : arrObjects)
			{
				String strMobileObjectName  = getAppObjectwithLocator(strObjectValue);
				if (!(strMobileObjectName.equalsIgnoreCase("false")))
				{
					String[] arrMobileObjectName = strMobileObjectName.split("#");
					String objectLocatorName =arrMobileObjectName[0].toString();
					String objectLocatorValue= arrMobileObjectName[1].toString();

					if(objectLocatorName!=null && objectLocatorValue!="")
					{
						if(objectLocatorName.equalsIgnoreCase("id"))
						{
							driver.findElement(By.id(objectLocatorValue)).click();
							BlnClick =true;
							return BlnClick;

						}
						else if(objectLocatorName.equalsIgnoreCase("Xpath"))
						{
							driver.findElement(By.xpath(objectLocatorValue)).click();
							BlnClick =true;
							return BlnClick;
						}
						else if(objectLocatorName.equalsIgnoreCase("Name"))
						{
							driver.findElement(By.name(objectLocatorValue)).click();
							BlnClick =true;
							return BlnClick;
						}
						
						else if(objectLocatorName.equalsIgnoreCase("iOSID"))
						{
							driver.findElementByAccessibilityId(objectLocatorValue).click();
							BlnClick =true;
							return BlnClick;
						}
						else if(objectLocatorName.equalsIgnoreCase("iOSXpath"))
						{
							driver.findElementByXPath(objectLocatorValue).click();
							BlnClick =true;
							return BlnClick;
						}
						else
						{
							BlnClick =false;
							return BlnClick;
						}
					}
					else
					{
						return false;
					}
				}
			}
		}
		catch(Exception e)
		{
			TestUiLogger.error("click on button object ", "Unable to click on button if locator is displayed , '"+e.getMessage()+"'");
			BlnClick =false;
			return BlnClick;
		}
			return BlnClick;
		}

	////


	///Scroll Down -
	@SuppressWarnings("deprecation")
	public boolean scrollToObjectFound(String objectName)
	{

		try{
			Dimension CurrentMobileSize = driver.manage().window().getSize();
			System.out.println(CurrentMobileSize);
			BlnSwipeFound = false;
			int counter=0;
			do
			{
				height = CurrentMobileSize.getHeight();
				width = CurrentMobileSize.getWidth();
				x=width/2;
				starty = (int) (height*0.40);
				endy= (int) (width*0.20);
				try
				{
					//driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
					BlnSwipeFound = getAppObjectFound(objectName);
					if (BlnSwipeFound)
					{
						BlnSwipeFound = true;
						return BlnSwipeFound;
					}
					else
					{
						BlnSwipeFound = false;
						driver.swipe(x, starty, x, endy, 500);
						counter = counter +1;
					}
				}
				catch (Exception e)
				{
					BlnSwipeFound = false;
					driver.swipe(x, starty, x, endy, 500);
					counter = counter +1;
				}
			} while (!BlnSwipeFound || counter<5);
		}

		catch (Exception e)
		{
			System.out.println(e.getMessage());
			return false;
		}

		return BlnSwipeFound;
	}


	////

	public boolean scrollToObjectFoundDynamic(String objectName,String objectDynamic)
	{

		try{
			Dimension CurrentMobileSize = driver.manage().window().getSize();
			System.out.println(CurrentMobileSize);
			BlnSwipeFound = false;
			int counter=0;
			do
			{
				height = CurrentMobileSize.getHeight();
				width = CurrentMobileSize.getWidth();
				x=width/2;
				starty = (int) (height*0.40);
				endy= (int) (width*0.20);
				try
				{
					//driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
					BlnSwipeFound = getAppObjectFoundDynamic(objectName,objectDynamic);
					if (BlnSwipeFound)
					{
						BlnSwipeFound = true;
						return BlnSwipeFound;
					}
					else
					{
						BlnSwipeFound = false;
						driver.swipe(x, starty, x, endy, 100);
						counter = counter +1;
					}
				}
				catch (Exception e)
				{
					BlnSwipeFound = false;
					driver.swipe(x, starty, x, endy, 100);
					counter = counter +1;
				}
			} while (!BlnSwipeFound || counter<5);
		}

		catch (Exception e)
		{
			System.out.println(e.getMessage());
			return false;
		}

		return BlnSwipeFound;
	}



	public boolean clickText(String Identifire,String ObejectName)
	{
		try
		{
			
			String LocalObject=Identifire;
			if(LocalObject!=null && LocalObject!="")
			{
				if(Identifire.equalsIgnoreCase("id"))
				{
					driver.findElement(By.id(ObejectName)).click();
					return true;

				}
				else if(Identifire.equalsIgnoreCase("Xpath"))
				{
					driver.findElement(By.xpath(ObejectName)).click();
					return true;

				}
				else if(Identifire.equalsIgnoreCase("Name"))
				{
					driver.findElement(By.name(ObejectName)).click();
					return true;

				}
				else if(Identifire.equalsIgnoreCase("DynamicProduct"))
				{
					 Point location = driver.findElement(By.xpath(ObejectName)).getLocation();
					 driver.findElement(By.xpath(ObejectName)).click();
					 
					 return true;

				}
				else if (Identifire.equalsIgnoreCase("iOSXpath"))
				{	
					driver.findElementByXPath(ObejectName).click();
					return true;
				}
				else if (Identifire.equalsIgnoreCase("iOSID"))
				{
					driver.findElementByAccessibilityId(ObejectName).click();
					return true;
				}		
				else
				{
					return false;
				}

			}
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", " Unable to get text "+e.getMessage()+" '");
			return false;

		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", " Unable to get text "+e.getMessage()+"'");
			return false;
		}
		return BlnSwipeFound;
	}

	public boolean backPress()
	{
		try
		{
			
				((PressesKeyCode) driver).pressKeyCode(4);
				return true;
			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	
	//============================================GMA4 Function ===================
	
	
	/***************************************************************************************************************
	 * Method Name -
	 * Project - GMA Method
	 * Method Name � clickMobileDynamicAppObject
	 * Method Description -
	 * Return Type - 
	 * Parameters - Inputs are fetched from map (inputs are)
	 * Framework - UKIT Master Framework
	 * Author - Mritunjay
	 * Creation Date - 11/02/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>
	 * @return

	 ***************************************************************************************************************/

	public boolean clickMobileDynamicAppObject(String strObjectName,String objectDynamic)

	{
			try
			{
				boolean FoundAndClicked =false;
				
				String[] arrObjects = strObjectName.split("#");
				String[] arrObjectDynamic = objectDynamic.split("#");
				int intObjectCounter = 0;
				
				for (intObjectCounter=0 ; intObjectCounter<arrObjects.length ; intObjectCounter++)
				{
					if (scrollToObjectFoundDynamic(arrObjects[intObjectCounter],arrObjectDynamic[intObjectCounter]))
					{
						String strMobileObjectName  = getAppObjectDynamicLocator(arrObjects[intObjectCounter],arrObjectDynamic[intObjectCounter]);

						String[] arrMobileObjectName = strMobileObjectName.split("#");

						String objectLocatorName =arrMobileObjectName[0].toString();
						String objectLocatorValue= arrMobileObjectName[1].toString();
						
						FoundAndClicked= clickText(objectLocatorName, objectLocatorValue);

						if (FoundAndClicked)
						{
							return true;
						}
						else
						{
							return false;
						}
					
					}
				}
			}
			catch (Exception e)
			{
				System.out.println(e.getMessage());
				return false;
			}
			return false;
	}
	
	/***************************************************************************************************************
	 * Method Name -
	 * Project - GMA Method
	 * Method Name � getDynamicIdentifire
	 * Method Description -
	 * Return Type - 
	 * Parameters - Inputs are fetched from map (inputs are)
	 * Framework - UKIT Master Framework
	 * Author - Mritunjay
	 * Creation Date - 11/02/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>
	 * @return

	 ***************************************************************************************************************/
	
	public String getDynamicIdentifire(String Identifire,String ObjectName,String DynamicObject)
	{
		try
		{
			ObjectName = ObjectName.trim();
			Identifire = Identifire.trim();
			int replaceCounter ;
			DynamicObject = DynamicObject.trim();
			String[] arrDynamicObject = DynamicObject.split("#");
			if(ObjectName.length()>0)
			{
				List<Map<String,String>> AppiumObjectList = TestCaseRunner.AppiumObjectList;

				for(Map<String,String> ObjectMap : AppiumObjectList)
				{
					String LocalObjectName = ObjectMap.get("Object");
					if(LocalObjectName.equals(ObjectName))
					{
						String strIdentifire = ObjectMap.get(Identifire);
						
						 String str = new String(strIdentifire);
						 for (replaceCounter=0; replaceCounter<arrDynamicObject.length; replaceCounter++ )
						 {
							 str = str.replace("#CV"+replaceCounter+"#",arrDynamicObject[replaceCounter]);
						 }
						 return str;
					}
				}
			}
			else
			{
				return "";
			}
		}
		catch(Exception e)
		{
			return "";
		}
		return "";
	}
	
//	/***************************************************************************************************************
//	 * Method Name -
//	 * Project - GMA Method
//	 * Method Name � clickMobileAppObject
//	 * Method Description -
//	 * Return Type - 
//	 * Parameters - Inputs are fetched from map (inputs are)
//	 * Framework - UKIT Master Framework
//	 * Author - Mritunjay
//	 * Creation Date - 11/02/2017
//	 * Modification History:
//	 * # <Date>     <Who>                  <Mod description>
//	 * @return
//
//	 ***************************************************************************************************************/
	public void clickMobileAppObject(String strObjectName)

	{
		try
		{
			boolean FoundAndClicked =false;
			
			String strMobileObjectName  = getAppObjectwithLocator(strObjectName);
			
			if (!strMobileObjectName.equalsIgnoreCase("false"))
			{
				String[] arrMobileObjectName = strMobileObjectName.split("#");
				String objectLocatorName =arrMobileObjectName[0].toString();
				String objectLocatorValue= arrMobileObjectName[1].toString();

				FoundAndClicked= clickText(objectLocatorName, objectLocatorValue);

				if (FoundAndClicked)
				{
					System.out.println("Object clicekd - PASS");

//					HtmlResult.passed("Tap the Mobile app object ", "Tap operation should be done successfully for object - "+ strObjectName + " for the object -" + strObjectName , "Tap operation performed successfully for object - " + strObjectName);
				}
				else
				{
					System.out.println("FAIL");
//					HtmlResult.failed("Tap the Mobile app object ", "Tap operation should be done successfully for object - "+ strObjectName + " for the object -" + strObjectName , "Tap operation is not performed for object - " + strObjectName);
				}
			}
		}

		catch (Exception e)
		{
//			HtmlResult.failed("Tap the Mobile app object ", "Tap operation should be done successfully for object - for the object -"  , "Tap operation is not performed for object - " );

		}
	}
	
	
	/***************************************************************************************************************
	 * Method Name -
	 * Project - GMA Method
	 * Method Name � TapOnMobileObject
	 * Method Description -
	 * Return Type - 
	 * Parameters - Inputs are fetched from map (inputs are)
	 * Framework - UKIT Master Framework
	 * Author - Mritunjay
	 * Creation Date - 11/02/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>
	 * @return

	 ***************************************************************************************************************/
	public boolean TapOnMobileObject(String strObjectName)
	{
		try
		{
			
			boolean objectFound = false;
			if (strObjectName!=null && strObjectName.length()>1)
			{
				objectFound = scrollToObjectFound(strObjectName);
				if (objectFound)
				{
					//clickMobileAppObject(strObjectName);
					objectFound=clickButton(strObjectName);
					return objectFound;
					//HtmlResult.passed("Locate Object Locator", "Locate Object Locator deatails should be present" + strObjectName,"Locate Object Locator deatails is present" +strObjectName);
				}
				else{
					return objectFound;
					//HtmlResult.failed("Locate Object Locator", "Locate Object Locator deatails should be present" + strObjectName,"Locate Object Locator deatails is not present" +strObjectName);
				}
			 }
			else{
				return objectFound;
			}
			}	
				catch (Exception e)
				{	
					System.out.println(e.getMessage());
					//HtmlResult.failed("Locate Object Locator", "Locate Object Locator deatails should be present", e.getMessage());
					return false;
				}		
		}
	
	/***************************************************************************************************************
	 * Method Name -
	 * Project - GMA Method
	 * Method Name � getTextForDynamicObject
	 * Method Description - This method will get the text for dynamic object i.e. object generated at run time
	 * Return Type - String value
	 * Parameters - Inputs are fetched from map (inputs are)
	 * Framework - UKIT Master Framework
	 * Author - Madhur Barsainya
	 * Creation Date - 11/08/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>
	 * @return

	 ***************************************************************************************************************/
	public String getTextForDynamicObject(String strObjectName,String objectDynamic)
	{
		try
		{
			String ActualText = "";
			
			String strMobileObjectName  = getAppObjectDynamicLocator(strObjectName,objectDynamic);
			if (!strMobileObjectName.equalsIgnoreCase("false"))
			{
				String[] arrMobileObjectName = strMobileObjectName.split("#");

				String objectLocatorName =arrMobileObjectName[0].toString();
				String objectLocatorValue= arrMobileObjectName[1].toString();
				
				ActualText = getText(objectLocatorName, objectLocatorValue);

//				HtmlResult.passed("Tap the Mobile app object ", "Tap operation should be done successfully for object - "+ strObjectName + " for the object -" + strObjectName , "Tap operation performed successfully for object - " + strObjectName);
				return ActualText;
			}
			else
			{
				return "";
			}
		}

		catch (Exception e)
		{
//			HtmlResult.failed("Tap the Mobile app object ", "Tap operation should be done successfully for object - for the object -"  , "Tap operation is not performed for object - " );
			return "";

		}


	}
	
	/***************************************************************************************************************
	 * Method Name -
	 * Project - GMA Method
	 * Method Name � getTextByXpath1
	 * Method Description -
	 * Return Type - 
	 * Parameters - Inputs are fetched from map (inputs are)
	 * Framework - UKIT Master Framework
	 * Author - Asif
	 * Creation Date - 11/02/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>
	 * @return

	 ***************************************************************************************************************/
			
			public String getTextByXpath1(String Id)
			{
				try
				{
					boolean value = driver.findElement(By.xpath(Id)).isDisplayed();
					System.out.println(driver.findElement(By.xpath(Id)).getAttribute("name"));
					return driver.findElement(By.xpath(Id)).getAttribute("name").toString();
				}
				catch(WebDriverException e)
				{
					TestUiLogger.error("TESTCASE", " Unable to get text "+e.getMessage()+" '");
					return null;
				}
				catch(Exception e)
				{
					TestUiLogger.error("TESTCASE", " Unable to get text "+e.getMessage()+"'");
					return null;
				}
			}
			
			/***************************************************************************************************************
			 * Method Name -
			 * Project - GMA Method
			 * Method Name � getTextByContent
			 * Method Description -
			 * Return Type - 
			 * Parameters - Inputs are fetched from map (inputs are)
			 * Framework - UKIT Master Framework
			 * Author - Asif
			 * Creation Date - 11/02/2017
			 * Modification History:
			 * # <Date>     <Who>                  <Mod description>
			 * @return

			 ***************************************************************************************************************/
			
			public String getTextByContent(String Id)
			{
				try
				{
					boolean value = driver.findElement(By.xpath(Id)).isDisplayed();
					System.out.println(driver.findElement(By.xpath(Id)).getAttribute("name"));
					return driver.findElement(By.xpath(Id)).getAttribute("name").toString();
				}
				catch(WebDriverException e)
				{
					TestUiLogger.error("TESTCASE", " Unable to get text "+e.getMessage()+" '");
					return null;
				}
				catch(Exception e)
				{
					TestUiLogger.error("TESTCASE", " Unable to get text "+e.getMessage()+"'");
					return null;
				}
			}
				
			/***************************************************************************************************************
			 * Method Name -
			 * Project - GMA Method
			 * Method Name � countObject
			 * Method Description -
			 * Return Type - 
			 * Parameters - Inputs are fetched from map (inputs are)
			 * Framework - UKIT Master Framework
			 * Author - Mritunjay
			 * Creation Date - 11/02/2017
			 * Modification History:
			 * # <Date>     <Who>                  <Mod description>
			 * @return

			 ***************************************************************************************************************/
		
			public int countObject(String identifier, String locator)	//is Displayed through id
			{
				try
				{
					locator=locator.trim();
					
					//int Counter = 0;
					//while(Counter<=3)
					//{
						if (identifier.equalsIgnoreCase("id"))
						{
							return(driver.findElements(By.id(locator)).size());
							
						}
						else if (identifier.equalsIgnoreCase("xpath"))
						{
							return(driver.findElements(By.xpath(locator)).size());
						}
						else if (identifier.equalsIgnoreCase("name"))
						{
							return(driver.findElements(By.name(locator)).size());
						}
						else if(identifier.equalsIgnoreCase("iOSID"))
						{
							
							return (driver.findElementsByAccessibilityId(locator).size());
						//return (driver.findElements(By.iOSID(locator)).size());
							
						}
						else if(identifier.equalsIgnoreCase("iOSXpath"))
						{
							return (driver.findElementsByXPath(locator)).size();
							
						}
						else
						{
							return 0;			
						}
				}
				catch(WebDriverException e)
				{
					TestUiLogger.error("TESTCASE", "Unable to check if locator ' "+locator+" '  is displayed , '"+e.getMessage()+"'");
					return 0;
				}
				catch(Exception e)
				{
					TestUiLogger.error("TESTCASE", "Unable to check if locator ' "+locator+" '  is displayed , '"+e.getMessage()+"'");
					return 0;
				}
				//return 0;
			}
			
			/***************************************************************************************************************
			 * Method Name -
			 * Project - GMA Method
			 * Method Name � countmobileObject
			 * Method Description -
			 * Return Type - 
			 * Parameters - Inputs are fetched from map (inputs are)
			 * Framework - UKIT Master Framework
			 * Author - Mritunjay
			 * Creation Date - 11/02/2017
			 * Modification History:
			 * # <Date>     <Who>                  <Mod description>
			 * @return

			 ***************************************************************************************************************/
		
			public int countmobileObject(String identifier, String locator)
			{
				try
				{	
					locator=locator.trim();
					int Counter = 0;
					while(Counter<=3){
						if (identifier.equalsIgnoreCase("id"))
						{
							return (driver.findElements(By.id(locator)).size());
									
						}
						else if (identifier.equalsIgnoreCase("xpath"))
						{
							return (driver.findElements(By.xpath(locator)).size());
						}
						else if (identifier.equalsIgnoreCase("name"))
						{
							return (driver.findElements(By.name(locator)).size());
						}
						else if(identifier.equalsIgnoreCase("iOSID"))
						{
							return (driver.findElementsByAccessibilityId(locator).size());
							//return (driver.findElements(By.iOSID(locator)).size());
								
							}
							else if(identifier.equalsIgnoreCase("iOSXpath"))
							{
								return (driver.findElementsByXPath(locator)).size();
								
							}
						
						else
						{
							Counter++;
						}
					}
					return 0;
				}
				catch(WebDriverException e)
				{
					TestUiLogger.error("TESTCASE", "Unable to check if locator ' "+locator+" '  is displayed , '"+e.getMessage()+"'");
					return 0;
				}
				catch(Exception e)
				{
					TestUiLogger.error("TESTCASE", "Unable to check if locator ' "+locator+" '  is displayed , '"+e.getMessage()+"'");
					return 0;
				}
				
			}
			/***************************************************************************************************************
			 * Method Name -
			 * Project - GMA Method
			 * Method Name � objectGetattribute
			 * Method Description -
			 * Return Type - 
			 * Parameters - Inputs are fetched from map (inputs are)
			 * Framework - UKIT Master Framework
			 * Author - Mritunjay
			 * Creation Date - 11/02/2017
			 * Modification History:
			 * # <Date>     <Who>                  <Mod description>
			 * @return
		**********************************************************************************************************************/
			public String objectGetattribute(String strObjectName, String strattributeName){
				
				try
				{
				String objectLocatorName;
				String objectLocatorValue;
				String strAttributeValue="false";
				
								
				String strMobileObjectName  = getAppObjectwithLocator(strObjectName);
				
				
				if (!strMobileObjectName.equalsIgnoreCase("false"))
				{

					String[] arrMobileObjectName = strMobileObjectName.split("#");

					objectLocatorName =arrMobileObjectName[0].toString();
					objectLocatorValue= arrMobileObjectName[1].toString();
					
					if(objectLocatorName.equalsIgnoreCase("id"))
					{
						strAttributeValue =driver.findElement(By.id(objectLocatorValue)).getAttribute(strattributeName);			
						return strAttributeValue;
					}
					else if(objectLocatorName.equalsIgnoreCase("Xpath"))
					{
						strAttributeValue=driver.findElement(By.xpath(objectLocatorValue)).getAttribute(strattributeName);
						
						return strAttributeValue;
					}
					else if(objectLocatorName.equalsIgnoreCase("Name"))
					{
						strAttributeValue=driver.findElement(By.name(objectLocatorValue)).getAttribute(strattributeName);
				
						return strAttributeValue;
					}
					
					else if(objectLocatorName.equalsIgnoreCase("iOSID"))
					{
						strAttributeValue=driver.findElement(By.name(objectLocatorValue)).getAttribute(strattributeName);

						return strAttributeValue;
					}
					else if(objectLocatorName.equalsIgnoreCase("iOSXpath"))
					{
						strAttributeValue=driver.findElement(By.name(objectLocatorValue)).getAttribute(strattributeName);

						return strAttributeValue;
					}

					
				} 
				
				}
				catch(WebDriverException e)
				{
					TestUiLogger.error("TESTCASE", " Unable to get text "+e.getMessage()+" '");
					return null;
				}
				catch(Exception e)
				{
					TestUiLogger.error("TESTCASE", " Unable to get text "+e.getMessage()+"'");
					return null;
				}
				return strattributeName;
				
			}	
			/***************************************************************************************************************
			 * Method Name -
			 * Project - GMA Method
			 * Method Name � verifyText
			 * Method Description -
			 * Return Type - 
			 * Parameters -
			 * Framework - UKIT Master Framework
			 * Author - Mritunjay
			 * Creation Date - 11/02/2017
			 * Modification History:
			 * # <Date>     <Who>                  <Mod description>
			 * @return
		**********************************************************************************************************************/
			public boolean verifyText(String strObjectName,String strExpectedData)

			{
				try
				{
					String objectLocatorName;
					String objectLocatorValue;
					//mobileAppSync();
					//String strObjectName =input.get("mobileObjectName");
					//String strExpectedData = input.get("expectedData");

					String strMobileObjectName  = getAppObjectwithLocator(strObjectName);
					if (strMobileObjectName!=null && strMobileObjectName.length()>1&&!strMobileObjectName.equalsIgnoreCase("false"))
					{
						String[] arrMobileObjectName = strMobileObjectName.split("#");
						objectLocatorName =arrMobileObjectName[0].toString();
						objectLocatorValue= arrMobileObjectName[1].toString();
						String CurrentObjectText = getText(objectLocatorName, objectLocatorValue);
						if (CurrentObjectText.equalsIgnoreCase(strExpectedData))
						{
							return true;
						}
						else
						{					
							return false;
						}
					}
					else
					{						
						return false;
					}
				}

				catch (Exception e)
				{	
					System.out.println(e.getMessage());
					return false;

				}

			}

			/***************************************************************************************************************
			 * Method Name -
			 * Project - GMA Method
			 * Method Name ñ getObjectText
			 * Method Description -
			 * Return Type - 
			 * Parameters -
			 * Framework - UKIT Master Framework
			 * Author - Madhur Barsainya
			 * Creation Date - 11/28/2017
			 * Modification History:
			 * # <Date>     <Who>                  <Mod description>
			 * @return
		**********************************************************************************************************************/
			public String getObjectText(String strObjectName)

			{
				try
				{
					String objectLocatorName;
					String objectLocatorValue;
					//mobileAppSync();
					//String strObjectName =input.get("mobileObjectName");
					//String strExpectedData = input.get("expectedData");

					String strMobileObjectName  = getAppObjectwithLocator(strObjectName);
					
					if(!strMobileObjectName.equalsIgnoreCase("false"))
					{
						if (strMobileObjectName!=null && strMobileObjectName.length()>1)
						{
							String[] arrMobileObjectName = strMobileObjectName.split("#");
							objectLocatorName =arrMobileObjectName[0].toString();
							objectLocatorValue= arrMobileObjectName[1].toString();
							String CurrentObjectText = getText(objectLocatorName, objectLocatorValue);
								return CurrentObjectText;
						}
					}
					else
					{
						return "";
					}
				}

				catch (Exception e)
				{	
					System.out.println(e.getMessage());
					return "";

				}
				return "";
			}
			public boolean backbuttonPress()
			{
				try
				{
					((PressesKeyCode) driver).pressKeyCode(4);
					return true;
				}
				catch(Exception e)
				{
					System.out.println(e.getMessage());
					return false;
				}
			}

	
}	

