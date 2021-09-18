package com.cg.UIDriver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cg.Logutils.Logging;
import com.cg.SeleniumTestSetup.TestSetUp;
import com.cg.TestCaseRunner.TestCaseRunner;
/*import com.opera.core.systems.internal.WatirUtils;*/
/*import com.thoughtworks.selenium.webdriven.commands.Highlight;
import com.thoughtworks.selenium.webdriven.commands.SetTimeout;*/


public class WebUIDriver {

	public static WebDriver driver;
	WebElement webElement;
	Logging TestUiLogger=TestCaseRunner.TestCaseLogger;// to put logging messages into the test case level logging file
	Logging FrameWorkUiLogger=TestCaseRunner.FrameWorkLogger;// to put logging messages into the framework level logging file
	public String ErrorMessage = "";

	public WebUIDriver(WebDriver webdriver)
	{
		driver=webdriver;
	}

	public boolean get(String WebAddress)			// for get()
	{
		try
		{	WebAddress=WebAddress.trim();
		driver.get(WebAddress);
		return true;
		}
		catch( WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to launch url-' "+WebAddress+" ' ,"+"' "+e.getMessage()+" '");
			return false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to launch url-' "+WebAddress+" ' ,"+"' "+e.getMessage()+" '");
			return false;
		}
	}

	public boolean close()							//for close()
	{
		try
		{
			driver.close();
			return true;
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to close driver-'"+e.getMessage()+" '");
			return false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to close driver-'"+e.getMessage()+" '");
			return false;
		}
	}

	public boolean quit()							// for quit()
	{
		try
		{
			driver.quit();
			return true;
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to quit driver-'"+e.getMessage()+" '");
			return false;
		}
	}


	public String getTitle()						//for getTittle()
	{
		try
		{
			String Title=driver.getTitle();
			if(Title.equals("") || Title.equals(null))
			{
				return null;
			}
			else if(Title.length()>0)
			{
				return Title;
			}

		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to get the web page title-'"+e.getMessage()+" '");
			return null;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to get the web page title-'"+e.getMessage()+" '");
			return null;
		}

		return null;
	}

	public String getCurrentUrl()					//for getCurrentUrl()
	{
		try
		{
			String CurrentURL=driver.getCurrentUrl();
			if(CurrentURL.equals("") || CurrentURL.equals(null))
			{
				return null;
			}
			else if(CurrentURL.length()>0)
			{
				return CurrentURL;
			}
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to get the current URL of webpage-'"+e.getMessage()+" '");
			return null;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to get the current URL of webpage-'"+e.getMessage()+" '");
			return null;
		}
		return null;
	}

	//switchTo() supporting methods
	public boolean switchToAlert()						//for switchTo()
	{
		try
		{
			driver.switchTo().alert();
			return true;
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to switch to alert -'"+e.getMessage()+" '");
			return false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to switch to alert -'"+e.getMessage()+" '");
			return false;
		}
	}

	public boolean switchToNewWindow()
	{
		try
		{
			for(String WindowHandle:driver.getWindowHandles())
			{
				driver.switchTo().window(WindowHandle);
				return true;
			}
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to switch to new window -'"+e.getMessage()+" '");
			return false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to switch to new window -'"+e.getMessage()+" '");
			return false;
		}
		return false;
	}

	public boolean switchToFrame(int FrameNumber)
	{
		try
		{
			driver.switchTo().frame(FrameNumber);
			return true;
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to switch to new frame -FRAME NO.'"+FrameNumber+" ', ' "+e.getMessage()+" '");
			return false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to switch to new frame -FRAME NO.'"+FrameNumber+" ', ' "+e.getMessage()+" '");
			return false;
		}
	}

	public boolean switchToFrame(String FrameName)
	{
		try
		{
			driver.switchTo().frame(FrameName.trim());
			return true;
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to switch to new frame -FRAME NAME.'"+FrameName+" ', ' "+e.getMessage()+" '");
			return false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to switch to new frame -FRAME NAME.'"+FrameName+" ', ' "+e.getMessage()+" '");
			return false;
		}
	}

	public boolean switchToFrame(WebElement Element)
	{
		try
		{
			driver.switchTo().frame(Element);
			return true;
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to switch to new frame '"+Element.getText()+" ', ' "+e.getMessage()+" '");
			return false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to switch to new frame '"+Element.getText()+" ', ' "+e.getMessage()+" '");
			return false;
		}
	}

	//To find an element by tagname
	public WebElement getElementByTagname(String strTagName,String str)
	{
		WebElement element=null;
		try
		{
			driver.findElement(By.tagName(strTagName.trim()));

			if(element.isDisplayed() && element.getLocation().x>0)
			{
				return element;
			}
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to find web element-' "+strTagName+" '");
			return null;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to find web element-' "+strTagName+" ','"+e.getMessage()+"'");
			return null;
		}
		return element;
	}

	//locating web elements
	public boolean findElementByName(String strObjectName)		//for findElement() through name
	{
		boolean blnFlag=false;
		String strObjectProperty;
		try
		{
			strObjectProperty=getWebelementProperty("name",strObjectName.trim());
			if(strObjectProperty=="")
			{
				blnFlag=false;
			}
			else
			{
				blnFlag=waitExplicitlyByName(strObjectProperty, TestSetUp.TimeOutInSec);
				if(blnFlag)
				{
					if(driver.findElement(By.name(strObjectProperty)).getLocation().x>0)
					{
						blnFlag=true;
					}
				}
			}
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to find web element-' "+strObjectName+" '");
			blnFlag=false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to find web element-' "+strObjectName+" ','"+e.getMessage()+"'");
			blnFlag=false;
		}
		return blnFlag;
	}

	public boolean findElementByTagName(String strObjectName)		//for findElement() through name
	{
		boolean blnFlag=false;
		String strObjectProperty;
		try
		{
			strObjectProperty=getWebelementProperty("Tag Name",strObjectName.trim());
			if(strObjectProperty=="")
			{
				blnFlag=false;
			}
			else
			{
				blnFlag=waitExplicitlyByTagName(strObjectProperty, TestSetUp.TimeOutInSec);
				if(blnFlag)
				{
					if(driver.findElement(By.tagName(strObjectProperty)).getLocation().x>0)
					{
						blnFlag=true;
					}
				}
			}
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to find web element-' "+strObjectName+" '");
			blnFlag=false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to find web element-' "+strObjectName+" ','"+e.getMessage()+"'");
			blnFlag=false;
		}
		return blnFlag;
	}

	public boolean findElementByClassName(String strObjectName)		//for findElement() through class name
	{
		boolean blnFlag=false;
		String strObjectProperty;
		try
		{
			strObjectProperty=getWebelementProperty("Class Name",strObjectName.trim());
			if(strObjectProperty=="")
			{
				blnFlag=false;
			}
			else
			{
				blnFlag=waitExplicitlyByClassName(strObjectProperty, TestSetUp.TimeOutInSec);
				if(blnFlag)
				{
					if(driver.findElement(By.className(strObjectProperty)).getLocation().x>0)
					{
						blnFlag=true;
					}
				}
			}
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to find web element-' "+strObjectName+" '");
			blnFlag=false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to find web element-' "+strObjectName+" ','"+e.getMessage()+"'");
			blnFlag=false;
		}
		return blnFlag;
	}

	public boolean findElementById(String Element)		//for findElement() through id
	{
		try
		{	Element=Element.trim();
		WebElement element = driver.findElement(By.id(Element));
		if(element.isDisplayed() && element.getLocation().x>0)
		{
			return true;
		}
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to find web element-' "+Element+" ','"+e.getMessage()+"'");
			return false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to find web element-' "+Element+" ','"+e.getMessage()+"'");
			return false;
		}
		return false;
	}

	//find element by Link text
	public boolean findElementByLinkText(String strObjectName)		//for findElement() through name
	{
		boolean blnFlag=false;
		String strObjectProperty;
		try
		{
			strObjectProperty=getWebelementProperty("Link Text",strObjectName.trim());
			if(strObjectProperty=="")
			{
				blnFlag=false;
			}
			else
			{
				blnFlag=waitExplicitlyByLinktext(strObjectProperty, TestSetUp.TimeOutInSec);
				if(blnFlag)
				{
					if(driver.findElement(By.linkText(strObjectProperty)).getLocation().x>0)
					{
						blnFlag=true;
					}
				}
			}
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to find web element-' "+strObjectName+" '");
			blnFlag=false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to find web element-' "+strObjectName+" ','"+e.getMessage()+"'");
			blnFlag=false;
		}
		return blnFlag;
	}

	//Returns list of web elements having same class name
	public List<WebElement> findElementsByClassName(String strClassName)
	{
		List<WebElement> webElementList= new ArrayList<WebElement>();
		try
		{
			webElementList = driver.findElements(By.className(strClassName));
			return webElementList;
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to find web elements having class name-' "+strClassName+" ','"+e.getMessage()+"'");
			return null;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to find web elements having class name-' "+strClassName+" ','"+e.getMessage()+"'");
			return null;
		}
	}

	//Returns list of web elements having same tag name
	public List<WebElement> getElementsByTagname(String strTagName)
	{
		List<WebElement> webElementList= new ArrayList<WebElement>();
		try
		{
			webElementList = driver.findElements(By.tagName(strTagName));
			return webElementList;
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to find web element-' "+strTagName+" ','"+e.getMessage()+"'");
			return null;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to find web element-' "+strTagName+" ','"+e.getMessage()+"'");
			return null;
		}
	}

	//Returns list of web elements having same xpath
	public List<WebElement> findElementsByxpath(String locator)
	{
		List<WebElement> webElementList= new ArrayList<WebElement>();
		String strObjectProperty;
		try
		{
			strObjectProperty=getWebelementProperty("xpath",locator);
			webElementList = driver.findElements(By.xpath(strObjectProperty));
			return webElementList;
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to find web element-' "+locator+" ','"+e.getMessage()+"'");
			return null;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to find web element-' "+locator+" ','"+e.getMessage()+"'");
			return null;
		}
	}

	//for findElement() through xpath
	public WebElement getElementByXpath(String strObjectName)
	{
		String strObjectProperty;
		WebElement element=null;
		boolean blnFlag=false;

		try
		{
			strObjectProperty=getWebelementProperty("xpath",strObjectName.trim());
			if(strObjectProperty=="")
			{
				element=null;
			}
			else
			{
				blnFlag=waitExplicitlyByXpath(strObjectProperty, TestSetUp.TimeOutInSec);
				if(blnFlag)
				{

					if(driver.findElement(By.xpath(strObjectProperty)).getLocation().x>0)
					{
						element=driver.findElement(By.xpath(strObjectProperty));
					}
				}
			}
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to find web element-' "+strObjectName+" ','"+e.getMessage()+"'");
			element=null;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to find web element-' "+strObjectName+" ','"+e.getMessage()+"'");
			element=null;
		}
		return element;
	}

	//click operations
	public boolean clickByName(String strObjectName)	//for click() through name
	{
		String strObjectProperty;
		boolean blnFlag=false;

		try
		{
			strObjectProperty=getWebelementProperty("name",strObjectName.trim());
			if(strObjectProperty=="")
			{
				blnFlag=false;
			}
			else
			{
				blnFlag=(waitExplicitlyById(strObjectProperty, TestSetUp.TimeOutInSec) && pageHasLoaded());
				if(blnFlag)
				{
					if(driver.findElement(By.name(strObjectProperty)).getLocation().x>0)
					{
						try
						{
							JavascriptExecutor js =(JavascriptExecutor) driver;
							js.executeScript("arguments[0].style.border='3px solid red'",driver.findElement(By.name(strObjectProperty)));
							js.executeScript("var elem=arguments[0]; setTimeout(function() {elem.click();},100)",driver.findElement(By.name(strObjectProperty)));
							//js.executeScript("arguments[0].click();", driver.findElement(By.name(strObjectProperty)));
						}
						catch (Exception e)
						{
							driver.findElement(By.id(strObjectProperty)).sendKeys(Keys.ENTER);
							blnFlag=true;
							e.printStackTrace();
						}
					}
					else
					{
						TestUiLogger.error("TESTCASE", "Unable to locate the element");
						blnFlag=false;
					}
				}
				else
				{
					TestUiLogger.error("TESTCASE", "Unable to wait explicitly by id or page has not loaded");
					blnFlag=false;
				}
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

	public boolean clickByJSExecutor(WebElement element)

	{
		try {
			JavascriptExecutor js =(JavascriptExecutor) driver;
			//js.executeAsyncScript("", element);
			js.executeScript("arguments[0].style.border='3px solid red'", element);

			js.executeScript("arguments[0].click();", element);
			return true;
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to click web element-' "+element+" ','"+e.getMessage()+"'");
			return false;
		}

		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to click web element-' "+element+" ','"+e.getMessage()+"'");
			return false;
		}



	}

	public boolean clickById(String strObjectName)				//for click() through id
	{
		String strObjectProperty;
		boolean blnFlag=false;
		try
		{
			strObjectProperty=getWebelementProperty("id",strObjectName.trim());
			if(strObjectProperty=="")
			{
				blnFlag=false;
			}
			else
			{
				blnFlag=(waitExplicitlyById(strObjectProperty, TestSetUp.TimeOutInSec) && pageHasLoaded());
				if(blnFlag)
				{
					WebElement element = driver.findElement(By.id(strObjectProperty));
					if(element.getLocation().x>0)
					{
						try {
							JavascriptExecutor js =(JavascriptExecutor) driver;
							//js.executeScript("arguments[0].click();", element);
							//js.executeScript("arguments[0].style.border='3px solid red'",element);
							js.executeScript("var elem=arguments[0]; setTimeout(function() {elem.click();},100)",element);
						} catch (Exception e) {
							driver.findElement(By.id(strObjectProperty)).sendKeys(Keys.ENTER);
							blnFlag=true;
							e.printStackTrace();
						}
					}
					else
					{
						TestUiLogger.error("TESTCASE", "Unable to locate the element");
						blnFlag=false;
					}
				}
				else
				{
					TestUiLogger.error("TESTCASE", "Unable to wait explicitly by id or page has not loaded");
					blnFlag=false;
				}
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


	public boolean clickByXpath(String strObjectName)	//for click() through xpath
	{
		String strObjectProperty;
		boolean blnFlag=false;
		try
		{
			strObjectProperty=getWebelementProperty("xpath",strObjectName.trim());
			if(strObjectProperty=="")
			{
				blnFlag=false;
			}
			else
			{
				blnFlag=(waitExplicitlyByXpath(strObjectProperty,TestSetUp.TimeOutInSec) && pageHasLoaded());
				if(blnFlag)
				{
					if(driver.findElement(By.xpath(strObjectProperty)).getLocation().x>0)
					{
						try {
							JavascriptExecutor js =(JavascriptExecutor) driver;
							js.executeScript("arguments[0].style.border='3px solid red'", driver.findElement(By.xpath(strObjectProperty)));
							//js.executeScript("arguments[0].click();", driver.findElement(By.xpath(strObjectProperty)));
							js.executeScript("var elem=arguments[0]; setTimeout(function() {elem.click();},100)", driver.findElement(By.xpath(strObjectProperty)));

						} catch (WebDriverException e) {
							driver.findElement(By.xpath(strObjectProperty)).sendKeys(Keys.ENTER);
							blnFlag=true;
							TestUiLogger.error("TESTCASE", "Unable to click web element-' "+strObjectName+" ','"+e.getMessage()+"'");
							e.printStackTrace();
						}
					}
					else
					{
						TestUiLogger.error("TESTCASE", "Unable to locate the element");
						blnFlag=false;
					}
				}
				else
				{
					TestUiLogger.error("TESTCASE", "Unable to wait explicitly by id or page has not loaded");
					blnFlag=false;
				}
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

	//clear operations
	public boolean clearByName(String Buttn1)  			// for clear() through name
	{
		try
		{
			Buttn1=Buttn1.trim();
			driver.findElement(By.name(Buttn1)).clear();
			return true;
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to clear by name-' "+Buttn1+" ','"+e.getMessage()+"'");
			return false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to clear by name -' "+Buttn1+" ','"+e.getMessage()+"'");
			return false;
		}
	}


	public boolean clearById(String Buttn1)  			// for clear() through id
	{
		try
		{
			Buttn1=Buttn1.trim();
			driver.findElement(By.id(Buttn1)).clear();
			return true;
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to clear by id -' "+Buttn1+" ','"+e.getMessage()+"'");
			return false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to clear by id -' "+Buttn1+" ','"+e.getMessage()+"'");
			return false;
		}
	}

	public boolean clearByXpath(String Buttn1)  			// for clear() through xpath
	{
		try
		{
			Buttn1=Buttn1.trim();
			driver.findElement(By.xpath(Buttn1)).clear();
			return true;
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to clear by xpath -' "+Buttn1+" ','"+e.getMessage()+"'");
			return false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to clear by xpath -' "+Buttn1+" ','"+e.getMessage()+"'");
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
			strObjectProperty=getWebelementProperty("id",strObjectName.trim());
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

	public boolean sendkeysName(String strObjectName,String Value)		//for sendkeys through name
	{
		String strObjectProperty;
		boolean blnFlag=false;
		try
		{
			strObjectProperty=getWebelementProperty("name",strObjectName.trim());
			if(strObjectProperty=="")
			{
				blnFlag=false;
			}
			else
			{
				blnFlag=waitExplicitlyByName(strObjectProperty,TestSetUp.TimeOutInSec);
				if(blnFlag)
				{
					if(driver.findElement(By.name(strObjectProperty)).getLocation().x>0)
					{
						driver.findElement(By.name(strObjectProperty)).sendKeys(Value.trim());
						blnFlag=true;
					}
				}
			}
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to send keys ' "+Value+" ' by name -' "+strObjectName+" ','"+e.getMessage()+"'");
			return false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to send keys ' "+Value+" ' by name -' "+strObjectName+" ','"+e.getMessage()+"'");
			return false;
		}
		return blnFlag;
	}

	public boolean sendkeysXpath(String strObjectName,String Value)		//for sendkeys through xpath
	{
		String strObjectProperty;
		boolean blnFlag=false;
		try
		{
			strObjectProperty=getWebelementProperty("xpath",strObjectName.trim());
			if(strObjectProperty=="")
			{
				blnFlag=false;
			}
			else
			{
				blnFlag=waitExplicitlyByXpath(strObjectProperty,TestSetUp.TimeOutInSec);
				if(blnFlag)
				{
					if(driver.findElement(By.xpath(strObjectProperty)).getLocation().x>0)
					{
						driver.findElement(By.xpath(strObjectProperty)).sendKeys(Value.trim());
						blnFlag=true;
					}
				}
			}
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to send keys ' "+Value+" ' by xpath -' "+strObjectName+" ','"+e.getMessage()+"'");
			blnFlag= false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to send keys ' "+Value+" ' by xpath -' "+strObjectName+" ','"+e.getMessage()+"'");
			blnFlag= false;
		}
		return blnFlag;
	}

	//Existence of web elements
	public boolean isEnabledByName(String locator)			//is enabled through name
	{

		try
		{
			locator=locator.trim();
			return driver.findElement(By.name(locator)).isEnabled();
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to check if locator ' "+locator+" '  is enabled , '"+e.getMessage()+"'");
			return false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to check if locator ' "+locator+" '  is enabled , '"+e.getMessage()+"'");
			return false;
		}
	}

	public boolean isEnabledById(String locator)			//is enabled through id
	{

		try
		{	locator=locator.trim();
		return driver.findElement(By.id(locator)).isEnabled();
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to check if locator ' "+locator+" '  is enabled , '"+e.getMessage()+"'");
			return false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to check if locator ' "+locator+" '  is enabled , '"+e.getMessage()+"'");
			return false;
		}
	}

	public boolean isEnabledByXpath(String locator)			//is enabled through xpath
	{

		try
		{	locator=locator.trim();
		return driver.findElement(By.xpath(locator)).isEnabled();
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to check if locator ' "+locator+" '  is enabled , '"+e.getMessage()+"'");
			return false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to check if locator ' "+locator+" '  is enabled , '"+e.getMessage()+"'");
			return false;
		}
	}


	public boolean isDisplayedByName(String locator)			//is Disable through name
	{

		try
		{	locator=locator.trim();
		return driver.findElement(By.name(locator)).isDisplayed();
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
	}

	public boolean isDisplayedById(String locator)			//is Disable through id
	{

		try
		{	locator=locator.trim();
		return driver.findElement(By.id(locator)).isDisplayed();
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
	}

	public boolean isDisplayedByXpath(String locator)			//is Disable through xpath
	{
		try
		{	locator=locator.trim();
		return driver.findElement(By.xpath(locator)).isDisplayed();
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
	}

	public boolean isSelectedByName(String locator)			//is Selected through name
	{
		try
		{	locator=locator.trim();
		return driver.findElement(By.name(locator)).isSelected();
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to check if locator ' "+locator+" '  is selected , '"+e.getMessage()+"'");
			return false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to check if locator ' "+locator+" '  is selected , '"+e.getMessage()+"'");
			return false;
		}
	}

	public boolean isSelectedById(String locator)			//is Selected through id
	{
		try
		{	locator=locator.trim();
		return driver.findElement(By.id(locator)).isSelected();
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to check if locator ' "+locator+" '  is selected , '"+e.getMessage()+"'");
			return false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to check if locator ' "+locator+" '  is selected , '"+e.getMessage()+"'");
			return false;
		}
	}

	public boolean isSelectedByXpath(String locator)			//is Selected through xpath
	{
		try
		{	locator=locator.trim();
		return driver.findElement(By.xpath(locator)).isSelected();
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to check if locator ' "+locator+" '  is selected , '"+e.getMessage()+"'");
			return false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to check if locator ' "+locator+" '  is selected , '"+e.getMessage()+"'");
			return false;
		}
	}



	public Dimension getSizeById(String locator)			//get size through id
	{
		WebElement Element;
		Dimension dimension = new Dimension(0, 0);

		try
		{   locator=locator.trim();
		Element=driver.findElement(By.id(locator));// locate element by xpath

		if(Element==null)
		{
			return dimension;
		}
		else
		{
			Dimension WebDimension=Element.getSize();
			if(WebDimension==null)
			{
				return dimension;
			}
			else
			{
				return WebDimension;
			}
		}

		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to get size of elements by ' "+locator+" ',' "+e.getMessage()+" '");
			return dimension;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to get size of elements by ' "+locator+" ',' "+e.getMessage()+" '");
			return dimension;
		}
	}

	public Dimension getSizeByName(String locator)			//get size through name
	{
		WebElement Element;
		Dimension dimension = new Dimension(0, 0);



		try
		{	locator=locator.trim();
		Element=driver.findElement(By.name(locator));// locate element by xpath

		if(Element==null)
		{
			return dimension;
		}
		else
		{
			Dimension WebDimension=Element.getSize();
			if(WebDimension==null)
			{
				return dimension;
			}
			else
			{
				return WebDimension;
			}
		}

		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to get size of elements by ' "+locator+" ',' "+e.getMessage()+" '");
			return dimension;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to get size of elements by ' "+locator+" ',' "+e.getMessage()+" '");
			return dimension;
		}
	}


	public Dimension getSizeByXpath(String locator)			//get size through xpath
	{
		WebElement Element;
		Dimension dimension = new Dimension(0, 0);



		try
		{ 	locator=locator.trim();
		Element=driver.findElement(By.xpath(locator));// locate element by xpath

		if(Element==null)
		{
			return dimension;
		}
		else
		{
			Dimension WebDimension=Element.getSize();
			if(WebDimension==null)
			{
				return dimension;
			}
			else
			{
				return WebDimension;
			}
		}

		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to get size of elements by ' "+locator+" ',' "+e.getMessage()+" '");
			return dimension;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to get size of elements by ' "+locator+" ',' "+e.getMessage()+" '");
			return dimension;
		}
	}


	//manage() options
	public boolean deleteAllCookies()
	{
		try
		{
			driver.manage().deleteAllCookies();
			return true;
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to delete cookies, '"+e.getMessage()+"'");
			return false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to delete cookies, '"+e.getMessage()+"'");
			return false;
		}
	}

	public boolean deleteCookie(String CookieName)
	{
		try
		{
			driver.manage().deleteCookieNamed(CookieName.trim());
			return true;
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to delete cookies ' "+CookieName+ " ' , '"+e.getMessage()+"'");
			return false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to delete cookies ' "+CookieName+ " ' , '"+e.getMessage()+"'");
			return false;
		}
	}

	public boolean maximizeWindow()
	{
		try {
			driver.manage().window().maximize();
			return true;
		} catch (WebDriverException e) {
			TestUiLogger.error("TESTCASE", "Unable to maximize window ,' "+e.getMessage()+" '");
			return false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to maximize window ,' "+e.getMessage()+" '");
			return false;
		}
	}

	public Point getPositionOfWindow()
	{
		Point P;
		try {
			P=driver.manage().window().getPosition();
			if(P==null)
			{
				throw new Exception();
			}
			else
			{
				return P;
			}

		} catch (WebDriverException e) {
			TestUiLogger.error("TESTCASE", "Unable to get position of  window ,' "+e.getMessage()+" '");
			return null;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to get position of  window ,' "+e.getMessage()+" '");
			return null;
		}
	}

	public boolean setPositionOfWindow(Point P)
	{
		try {
			driver.manage().window().setPosition(P);
			return true;
		}  catch (WebDriverException e) {
			TestUiLogger.error("TESTCASE", "Unable to set position of  window ,' "+e.getMessage()+" '");
			return false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to set position of  window ,' "+e.getMessage()+" '");
			return false;
		}
	}

	//explicit waits
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

	//explicitly wait by link text
	public boolean waitExplicitlyByLinktext(String LinkText,long TimeOutInSec)
	{
		try {
			LinkText=LinkText.trim();
			(new WebDriverWait(driver, TimeOutInSec)).until(ExpectedConditions.presenceOfElementLocated(By.linkText(LinkText)));
			return true;
		} catch (WebDriverException e) {
			TestUiLogger.error("TESTCASE", "Error while explicit wait by ' "+LinkText+" ',' "+e.getMessage()+" '");
			return false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Error while explicit wait by ' "+LinkText+" ',' "+e.getMessage()+" '");
			return false;
		}
	}

	//explicitly wait by Class Name
	public boolean waitExplicitlyByClassName(String ClassName,long TimeOutInSec)
	{
		try {
			ClassName=ClassName.trim();
			(new WebDriverWait(driver, TimeOutInSec)).until(ExpectedConditions.presenceOfElementLocated(By.className(ClassName)));
			return true;
		} catch (WebDriverException e) {
			TestUiLogger.error("TESTCASE", "Error while explicit wait by ' "+ClassName+" ',' "+e.getMessage()+" '");
			return false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Error while explicit wait by ' "+ClassName+" ',' "+e.getMessage()+" '");
			return false;
		}
	}

	//explicitly wait by TagName
	public boolean waitExplicitlyByTagName(String TagName,long TimeOutInSec)
	{
		try
		{
			TagName=TagName.trim();
			(new WebDriverWait(driver, TimeOutInSec)).until(ExpectedConditions.presenceOfElementLocated(By.tagName(TagName)));
			return true;
		} catch (WebDriverException e) {
			TestUiLogger.error("TESTCASE", "Error while explicit wait by ' "+TagName+" ',' "+e.getMessage()+" '");
			return false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Error while explicit wait by ' "+TagName+" ',' "+e.getMessage()+" '");
			return false;
		}
	}

	public boolean waitExplicitlyByName(String Name,long TimeOutInSec)
	{
		try {
			Name=Name.trim();
			(new WebDriverWait(driver, TimeOutInSec)).until(ExpectedConditions.presenceOfElementLocated(By.name(Name)));
			return true;
		} catch (WebDriverException e) {
			TestUiLogger.error("TESTCASE", "Error while explicit wait by ' "+Name+" ',' "+e.getMessage()+" '");
			return false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Error while explicit wait by ' "+Name+" ',' "+e.getMessage()+" '");
			return false;
		}
	}


	public boolean waitExplicitlyByElement(WebElement webelement,long TimeOutInSec)
	{
		try {

			(new WebDriverWait(driver, TimeOutInSec)).until(ExpectedConditions.visibilityOf(webelement));
			return true;
		} catch (WebDriverException e) {
			TestUiLogger.error("TESTCASE", "Error while explicit wait by ' "+webelement.toString()+" ',' "+e.getMessage()+" '");
			return false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Error while explicit wait by ' "+webelement.toString()+" ',' "+e.getMessage()+" '");
			return false;
		}
	}


	public boolean waitExplicitlyByXpath(String Xpath,long TimeOutInSec)
	{
		try {
			Xpath=Xpath.trim();
			(new WebDriverWait(driver, TimeOutInSec)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(Xpath)));
			return true;
		} catch (WebDriverException e) {
			TestUiLogger.error("TESTCASE", "Error explicit while wait by ' "+Xpath+" ',' "+e.getMessage()+" '");
			return false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Error explicit while wait by ' "+Xpath+" ',' "+e.getMessage()+" '");
			return false;
		}
	}

	//implicit wait
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

	public boolean navigateTo(String apURL)			//navigate to
	{
		try
		{
			apURL=apURL.trim();
			driver.navigate().to(apURL);
			return true;
		}catch (WebDriverException e) {
			TestUiLogger.error("TESTCASE", "Error while navigate to ' "+apURL+", '"+e.getMessage()+" '");
			return false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Error while navigate to ' "+apURL+", '"+e.getMessage()+" '");
			return false;
		}
	}

	public boolean navigateBack()					//navigate back
	{
		try
		{
			driver.navigate().back();
			return true;
		}catch (WebDriverException e) {
			TestUiLogger.error("TESTCASE", "Error while navigate back , '"+e.getMessage()+" '");
			return false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Error while navigate back, '"+e.getMessage()+" '");
			return false;
		}
	}

	public boolean navigateForward()				//navigate forward
	{
		try
		{
			driver.navigate().forward();
			return true;
		}catch (WebDriverException e) {
			TestUiLogger.error("TESTCASE", "Error while navigate forward , '"+e.getMessage()+" '");
			return false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Error while navigate forward , '"+e.getMessage()+" '");
			return false;
		}
	}

	public boolean navigateRefresh() 				// navigate refresh
	{
		try
		{
			driver.navigate().refresh();
			return true;
		}catch (WebDriverException e) {
			TestUiLogger.error("TESTCASE", "Error while navigating refresh , '"+e.getMessage()+" '");
			return false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Error while navigating refresh , '"+e.getMessage()+" '");
			return false;
		}
	}


	public int getNumberOfElementsById(String locator)			//no of web elements having same id
	{
		try
		{
			return driver.findElements(By.id(locator)).size();
		}
		catch (WebDriverException e) {
			TestUiLogger.error("TESTCASE", "Error while getting number of elements by "+locator+" , '"+e.getMessage()+" '");
			return 0;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Error while getting number of elements by "+locator+" , '"+e.getMessage()+" '");
			return 0;
		}
	}

	public int getNumberOfElementsByXpath(String locator)			//size through xpath
	{
		try
		{
			return driver.findElements(By.xpath(locator)).size();
		}
		catch (WebDriverException e) {
			TestUiLogger.error("TESTCASE", "Error while getting number of elements by "+locator+" , '"+e.getMessage()+" '");
			return 0;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Error while getting number of elements by "+locator+" , '"+e.getMessage()+" '");
			return 0;
		}
	}

	public int getNumberOfElementsByName(String locator)			//size through xpath
	{
		try
		{
			return driver.findElements(By.name(locator)).size();
		}
		catch (WebDriverException e) {
			TestUiLogger.error("TESTCASE", "Error while getting number of elements by "+locator+" , '"+e.getMessage()+" '");
			return 0;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Error while getting number of elements by "+locator+" , '"+e.getMessage()+" '");
			return 0;
		}
	}

	//Drop down selection by visible text
	public boolean selectFromDropDownListByVisibleText(WebElement element, String strValue)
	{
		strValue=strValue.trim();
		try
		{
			Select oSelect = new Select(element);
			oSelect.selectByVisibleText(strValue);
			return true;
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to select value from drop down list -' "+strValue+" ','"+e.getMessage()+"'");
			return false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to select value from drop down list -' "+strValue+" ','"+e.getMessage()+"'");
			return false;
		}
	}

	//Drop down selection by visible text
	public boolean selectFromDropDownListByValue(WebElement element, String strValue)
	{
		strValue=strValue.trim();
		try
		{
			Select oSelect = new Select(element);
			oSelect.selectByValue(strValue);
			return true;
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to select value from drop down list -' "+strValue+" ','"+e.getMessage()+"'");
			return false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to select value from drop down list -' "+strValue+" ','"+e.getMessage()+"'");
			return false;
		}
	}

	//Drop down selection by visible text
	public boolean selectFromDropDownListByIndex(WebElement element, int strValue)
	{
		try
		{
			Select oSelect = new Select(element);
			oSelect.selectByIndex(strValue);
			return true;
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to select value from drop down list -' "+strValue+" ','"+e.getMessage()+"'");
			return false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to select value from drop down list -' "+strValue+" ','"+e.getMessage()+"'");
			return false;
		}
	}

	//Gets the WFM webelement property from the excel and returns the same
	public String getWebelementProperty(String strProperty,String objectName)
	{
		String strObjectProperty="";
		String objName=objectName.trim();
		try
		{
			if (!objectName.isEmpty())
			{
				List<Map<String, String>> ListOfMap = TestCaseRunner.WFM_ElementPropertyMap;
				for (Map m : ListOfMap)
				{
					if (m.get("Object Name").toString().trim().equalsIgnoreCase(objName))
					{
						strObjectProperty = m.get(strProperty).toString().trim();
						break;
					}
				}
				return strObjectProperty;
			}
			else
			{
				return null;
			}
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Error while fetching data from webelement property file , '"+e.getMessage()+" '");
			return null;
		}
	}

	//**************
	public WebElement locateElement(String strObjectName)
	{
		Boolean blnFlag=false;
		WebElement element=null;

		try
		{
			element=getElementByXpath(strObjectName);
			if(element==null)
			{
				element=getElementById(strObjectName);
				if(element==null)
				{
					element=getElementByName(strObjectName);
				}
			}
		}
		catch(Exception e)
		{
			element=null;
		}
		return element;
	}

	//Locates elements with xpath first,if element not found then find using id,else using name and click.
	public boolean locateElementAndClick(String strObjectName)
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

	//Locates elements with xpath first,if element not found then find using id,else using name and sendkeys .
	public boolean locateElementAndSendKeys(String strObjectName, String strObjectvalue)
	{
		Boolean blnFlag;
		try
		{
			blnFlag=sendkeysXpath(strObjectName, strObjectvalue);
			if(!blnFlag)
			{
				blnFlag=sendkeysId(strObjectName, strObjectvalue);
				if(!blnFlag)
				{
					blnFlag=sendkeysName(strObjectName, strObjectvalue);
				}
			}
		}
		catch(Exception e)
		{
			blnFlag=false;
		}
		return blnFlag;
	}


	//Returns element having the desired text from the list of webElement provided
	public WebElement getElementContainingRequiredText(List<WebElement> webElementList,String strText)
	{
		String strElementText="";
		WebElement element=null;

		for(WebElement webElement:webElementList)
		{
			try
			{
				strElementText=webElement.getText();
				if(strElementText.equals(strText))
				{
					element=webElement;
					break;
				}
			}
			catch(WebDriverException e)
			{
				TestUiLogger.error("TESTCASE", "Unable to locate web element having text -' "+strText+" ','"+e.getMessage()+"'");
				element=null;
				continue;
			}
			catch(Exception e)
			{
				TestUiLogger.error("TESTCASE", "Unable to locate web element having text -' "+strText+" ','"+e.getMessage()+"'");
				element=null;
				continue;
			}
		}

		return element;
	}

	//send data to empty text boxes
	public boolean sendkeyslinkText(String locator,String Value)		//for sendkeys through linktext
	{
		try{
			Value=Value.trim();
			locator=locator.trim();
			driver.findElement(By.linkText(locator)).sendKeys(Value);
			return true;
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to send keys ' "+Value+" ' by link text -' "+locator+" ','"+e.getMessage()+"'");
			return false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to send keys ' "+Value+" ' by link text -' "+locator+" ','"+e.getMessage()+"'");
			return false;
		}
	}

	//send data to empty text boxes
	public boolean sendkeysClassName(String locator,String Value)		//for sendkeys through Class Name
	{
		try{
			Value=Value.trim();
			locator=locator.trim();
			driver.findElement(By.className(locator)).sendKeys(Value);
			return true;
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to send keys ' "+Value+" ' by ClassName -' "+locator+" ','"+e.getMessage()+"'");
			return false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to send keys ' "+Value+" ' by ClassName -' "+locator+" ','"+e.getMessage()+"'");
			return false;
		}
	}

	//click operations
	public WebElement getElementBylinkText(String strObjectName)				//for click() through Linktext
	{
		String strObjectProperty;
		WebElement element=null;
		boolean blnFlag=false;
		try
		{
			strObjectName=strObjectName.trim();
			strObjectProperty=getWebelementProperty("Link Text",strObjectName);
			if(strObjectProperty=="")
			{
				blnFlag=false;
			}
			else
			{
				blnFlag=waitExplicitlyByLinktext(strObjectProperty, TestSetUp.TimeOutInSec);
				if(blnFlag)
				{
					if(driver.findElement(By.linkText(strObjectProperty)).getLocation().x>0)
					{
						element=driver.findElement(By.linkText(strObjectProperty));
					}
				}
			}
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to click web element-' "+strObjectName+" ',' "+e.getMessage()+" '");
			element=null;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to click web element-' "+strObjectName+" ','"+e.getMessage()+"'");
			element=null;
		}
		return element;
	}

	//click operations
	public boolean clickByClassName(String strObjectName)				//for click() through Class name
	{
		String strObjectProperty;
		try
		{
			strObjectName=strObjectName.trim();
			strObjectProperty=getWebelementProperty("name",strObjectName);
			driver.findElement(By.className(strObjectProperty)).click();
			return true;
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to click web element-' "+strObjectName+" ',' "+e.getMessage()+" '");
			return false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to click web element-' "+strObjectName+" ','"+e.getMessage()+"'");
			return false;
		}
	}

	//Get text from an element
	public String getText(WebElement element)
	{
		String strElementText="";
		try
		{
			if(element.isDisplayed())
			{
				strElementText=element.getText();
			}
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to get text from web element-' ,' "+e.getMessage()+" '");
			strElementText="";
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to get text from web element-' ','"+e.getMessage()+"'");
			strElementText="";
		}
		return strElementText;
	}

	//reading data from table
	public String getTableCellDataByClassName(String strClassName)
	{
		String cellText="";
		WebElement table_element;
		List<WebElement> tr_collection;
		List<WebElement> td_collection;
		int numberOfRows;
		int numberOfCols;
		int row_num,col_num;
		row_num=1;

		try
		{
			table_element = driver.findElement(By.className(strClassName));
			tr_collection=table_element.findElements(By.tagName("tr"));
			numberOfRows=tr_collection.size();
			for(WebElement trElement : tr_collection)
			{
				td_collection=trElement.findElements(By.tagName("td"));
				numberOfCols=td_collection.size();
				col_num=1;
				for(WebElement tdElement : td_collection)
				{
					cellText=tdElement.getText();
					col_num++;
				}
				row_num++;
			}

		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to get text from web element-' ,' "+e.getMessage()+" '");
			cellText="";
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to get text from web element-' ','"+e.getMessage()+"'");
			cellText="";
		}

		return cellText;
	}

	public ArrayList<String> creatKeysOfWebTable(WebElement KeyRow)
	{
		ArrayList<String> Keys = new ArrayList<String>();
		try {

			List<WebElement> ColumnCollection = KeyRow.findElements(By.xpath("td"));

			for(WebElement ColumnData : ColumnCollection )
			{
				String KeyName = ColumnData.getText();
				Keys.add(KeyName);
			}

			return Keys;

		} catch (Exception e) {
			return null;
		}
	}


	//***********
	public boolean findElementByXpath(String strObjectName)
	{
		WebElement element;
		boolean blnFlag=false;
		String strObjectProperty;

		try
		{	strObjectName=strObjectName.trim();
		strObjectProperty=getWebelementProperty("xpath",strObjectName);
		element=driver.findElement(By.xpath(strObjectProperty));
		if(element.isDisplayed() && element.getLocation().x>0)
		{
			return true;
		}
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to find web element by xpath-' "+strObjectName+" ','"+e.getMessage()+"'");
			blnFlag=false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to find web element by xpath-' "+strObjectName+" ','"+e.getMessage()+"'");
			blnFlag=false;
		}
		return blnFlag;
	}

	//************
	public WebElement getElementById(String strObjectName)		//for findElement() through id which returns web element
	{
		WebElement element=null;
		boolean blnFlag=false;
		String strObjectProperty;
		try
		{
			strObjectProperty=getWebelementProperty("id",strObjectName.trim());
			if(strObjectProperty=="")
			{
				element=null;
			}
			else
			{
				blnFlag=waitExplicitlyById(strObjectProperty, TestSetUp.TimeOutInSec);
				if(blnFlag)
				{
					if(driver.findElement(By.id(strObjectProperty)).getLocation().x>0)
					{
						element=driver.findElement(By.id(strObjectProperty));
					}
				}
			}
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to find web element by id-' "+strObjectName+" ','"+e.getMessage()+"'");
			element=null;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to find web element by id-' "+strObjectName+" ','"+e.getMessage()+"'");
			element=null;
		}
		return element;
	}

	//**********
	public WebElement getElementByName(String strObjectName)		//for findElement() through name which returns web element
	{
		WebElement element = null;
		boolean blnFlag=false;
		String strObjectProperty;
		try
		{
			strObjectProperty=getWebelementProperty("name",strObjectName.trim());
			if(strObjectProperty=="")
			{
				element=null;
			}
			else
			{
				blnFlag=waitExplicitlyByName(strObjectProperty, TestSetUp.TimeOutInSec);
				if(blnFlag)
				{
					if(driver.findElement(By.name(strObjectProperty)).getLocation().x>0)
					{
						element=driver.findElement(By.name(strObjectProperty));
					}
				}
			}
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to find web element by name-' "+strObjectName+" ','"+e.getMessage()+"'");
			element=null;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to find web element by name-' "+strObjectName+" ','"+e.getMessage()+"'");
			element=null;
		}
		return element;
	}


	//get element by class name
	public WebElement getElementByClassName(String strObjectName)		//for findElement() through name which returns web element
	{
		WebElement element = null;
		boolean blnFlag=false;
		String strObjectProperty;
		try
		{
			strObjectProperty=getWebelementProperty("Class Name",strObjectName.trim());
			if(strObjectProperty=="")
			{
				element=null;
			}
			else
			{
				blnFlag=waitExplicitlyByClassName(strObjectProperty, TestSetUp.TimeOutInSec);
				if(blnFlag)
				{
					if(driver.findElement(By.className(strObjectProperty)).getLocation().x>0)
					{
						element=driver.findElement(By.className(strObjectProperty));
					}
				}
			}
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to find web element by name-' "+strObjectName+" ','"+e.getMessage()+"'");
			element=null;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to find web element by name-' "+strObjectName+" ','"+e.getMessage()+"'");
			element=null;
		}
		return element;
	}
	public List<Map<String,WebElement>> getWebTable(String TablePropertyName)
	{
		List<Map<String,WebElement>> RowWiseTableData=new ArrayList<Map<String,WebElement>>();
		try
		{
			TablePropertyName = TablePropertyName.trim();
			String TableId = getWebelementProperty("id",TablePropertyName);
			TableId="scheduleExtended";

			String TableDynamicXpath="//*[@id='"+TableId+"']/table/tbody";
			WebElement element = driver.findElement(By.xpath(TableDynamicXpath));

			String TableDynamicRowXpath=TableDynamicXpath+"/tr";
			List<WebElement> RowCollection = element.findElements(By.xpath(TableDynamicRowXpath));

			int RowNum=1,ColNum=0;

			ArrayList<String> Keys = new ArrayList<String>();

			int KeySize=0;

			if( RowCollection.size() > 1)
			{
				for(WebElement WebRow : RowCollection)
				{

					Map<String ,WebElement> TableRow = new HashMap<String,WebElement>();
					if(RowNum==1)
					{
						Keys=creatKeysOfWebTable(WebRow);
						KeySize=Keys.size();
					}
					else
					{
						List<WebElement> ColumnCollection = WebRow.findElements(By.xpath("td"));
						ColNum=0;
						while(ColNum<KeySize)
						{
							try {
								WebElement CellData = ColumnCollection.get(ColNum);
								String text=CellData.getText();
								TableRow.put(Keys.get(ColNum), CellData);
								ColNum++;
							} catch (IndexOutOfBoundsException e) {
								break;
							}
						}

						RowWiseTableData.add(TableRow);
					}
					RowNum++;
				}

				return RowWiseTableData;
			}
		}
		catch(WebDriverException e)
		{
			return null;
		}
		catch(Exception e)
		{
			return null;
		}
		return RowWiseTableData;
	}


	//Java script executer
	public boolean clickUsingJavaScriptExecuter(WebElement element)
	{
		boolean blnFlag=false;

		try
		{
			JavascriptExecutor javaExecuter = (JavascriptExecutor) driver;
			javaExecuter.executeScript("arguments[0].style.border='3px solid red'", element);
			javaExecuter.executeScript("arguments[0].click();", element);

			blnFlag=true;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Java executer failed to click element "+e.getMessage()+"'");
			blnFlag=false;
		}

		return blnFlag;
	}

	public boolean switchToNewWindow(String strWindowTitle)
	{
		boolean blnFlag=false;
		try
		{
			for(String WindowHandle:driver.getWindowHandles())
			{
				(new WebDriverWait(driver, TestSetUp.TimeOutInSec)).until(ExpectedConditions.titleIs(strWindowTitle));
				driver.switchTo().window(WindowHandle);
				blnFlag= true;
			}
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to switch to new window -'"+e.getMessage()+" '");
			blnFlag= false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to switch to new window -'"+e.getMessage()+" '");
			blnFlag= false;
		}
		return blnFlag;
	}

	//wait untill page loads
	public boolean pageHasLoaded() throws InterruptedException
	{
		boolean blnVar = false;
		JavascriptExecutor js =(JavascriptExecutor)driver;
		int WaitCounter=1;
		do{
			Thread.sleep(1000);
			WaitCounter++;
		}while(!js.executeScript("return document.readyState").equals("complete")|| WaitCounter==TestSetUp.TimeOutInSec);

		if(js.executeScript("return document.readyState").equals("complete"))
		{
			return true;
		}
		else
		{
			return false;
		}

	}

	//getting the selected data from drop down
	public boolean getSelectedOptionFromDropdown(WebElement element, String strValue)
	{
		boolean blnFlag=false;

		try
		{
			Select oSelect = new Select(element);
			WebElement option = oSelect.getFirstSelectedOption();
			String strSelectedValue=option.getText();
			if(strSelectedValue.equals(strValue))
			{
				blnFlag=true;
			}
		}
		catch(WebDriverException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to get the value from drop down list -' "+strValue+" ','"+e.getMessage()+"'");
			blnFlag=false;
		}
		catch(Exception e)
		{
			TestUiLogger.error("TESTCASE", "Unable to get the value from drop down list -' "+strValue+" ','"+e.getMessage()+"'");
			blnFlag=false;
		}

		return blnFlag;
	}

	public boolean isAlertPresent() throws TimeoutException
	{
		int counter=1;
		boolean blnFlag=false;
		WebDriverWait wait = new WebDriverWait(driver, TestSetUp.TimeOutInSec);

		while(counter<=20)
		{
			try
			{
				wait.until(ExpectedConditions.alertIsPresent());
				Alert alert = driver.switchTo().alert();
				alert.accept();
				blnFlag=true;
				break;
			}
			catch (NoAlertPresentException e)
			{
				TestUiLogger.error("TESTCASE", "Alert not present - ' "+e.getMessage()+" '");
				continue;
			}
		}
		return blnFlag;
	}

	public String getTextOnAlertBox()
	{
		String strText="";
		WebDriverWait wait = new WebDriverWait(driver, TestSetUp.TimeOutInSec);
		try
		{
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			strText=alert.getText();
			alert.accept();
			return strText;
		}
		catch (NoAlertPresentException e)
		{
			TestUiLogger.error("TESTCASE", "Unable to read the alert box content -'"+e.getMessage()+" '");
			return strText="";
		}
	}


	public boolean blnlocateElement(String strObjectName)
	{
		Boolean blnFlag=false;

		try
		{
			blnFlag=findElementByXpath(strObjectName);
			if(!blnFlag)
			{
				blnFlag=findElementById(strObjectName);
				if(!blnFlag)
				{
					blnFlag=findElementByName(strObjectName);
				}
			}
		}
		catch(WebDriverException e)
		{
			ErrorMessage = e.getMessage();TestUiLogger.error("TESTCASE", "Unable to locate element -' "+e.getMessage()+"'");
			return false;
		}
		catch(Exception e)
		{
			ErrorMessage = e.getMessage();TestUiLogger.error("TESTCASE", "Unable to locate element -' "+e.getMessage()+"'");
			return false;
		}
		return blnFlag;
	}

	//getting text from alert
	//returning text on alert
	public String getTextOfAlert()
	{
		String TextOnAlert = "";
		try
		{
			TextOnAlert = driver.switchTo().alert().getText();
			TextOnAlert = TextOnAlert.replaceAll("\n", "");
			return TextOnAlert;
		}
		catch(WebDriverException e)
		{
			ErrorMessage = e.getMessage();TestUiLogger.error("TESTCASE", "Unable to read text on alert-' "+e.getMessage()+" '");
			return "";
		}
		catch(Exception e)
		{
			ErrorMessage = e.getMessage();TestUiLogger.error("TESTCASE", "Unable to read text on alert-' "+e.getMessage()+" '");
			return "";
		}
	}

	//handling alert ok or cancel
	public boolean handleAlert(String Action)
	{
		try {
			Alert alert ;

			if(Action.equalsIgnoreCase("ok"))
			{
				Thread.sleep(2000);
				alert=driver.switchTo().alert();
				Thread.sleep(2000);
				alert.accept();
				return true;
			}
			else if(Action.equalsIgnoreCase("cancel"))
			{
				Thread.sleep(2000);
				alert=driver.switchTo().alert();
				Thread.sleep(2000);
				alert.dismiss();
				return true;
			}


		} catch(WebDriverException e)
		{
			ErrorMessage = e.getMessage();TestUiLogger.error("TESTCASE", "Unable to handle alert -' "+e.getMessage()+"'");
			return false;
		}
		catch(Exception e)
		{
			ErrorMessage = e.getMessage();TestUiLogger.error("TESTCASE", "Unable to handle alert -' "+e.getMessage()+"'");
			return false;
		}
		return false;
	}


	//clicking on web element
	public boolean clickWebElement(WebElement Element)
	{
		try
		{
			if(Element!=null)
			{
				if(Element.isEnabled())
				{
					Object Obj;
					try {
						JavascriptExecutor executor = (JavascriptExecutor)driver;
						executor.executeScript("arguments[0].style.border='3px solid red'", Element);
						Obj = executor.executeScript("arguments[0].click();", Element);
						if(Obj == null)
						{
							Element.click();
						}
					} catch (WebDriverException e) {
						Element.sendKeys(Keys.ENTER);
					}


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
		catch(WebDriverException e)
		{

			ErrorMessage = e.getMessage();
			ErrorMessage = e.getMessage();TestUiLogger.error("TESTCASE", "Unable to find web element "+Element+" ','"+e.getMessage()+"'");
			return false;
		}
		catch(Exception e)
		{
			ErrorMessage = e.getMessage();
			ErrorMessage = e.getMessage();TestUiLogger.error("TESTCASE", "Unable to find web element "+Element+" ','"+e.getMessage()+"'");
			return false;
		}



	}

	//Returns list of web elements having same tag name
	public List<WebElement> findElementsByTagname(String strTagName)
	{
		List<WebElement> webElementList= new ArrayList<WebElement>();
		try
		{
			webElementList = driver.findElements(By.tagName(strTagName));
			return webElementList;
		}
		catch(WebDriverException e)
		{
			ErrorMessage = e.getMessage();
			ErrorMessage = e.getMessage();TestUiLogger.error("TESTCASE", "Unable to find web element-' "+strTagName+" ','"+e.getMessage()+"'");
			return null;
		}
		catch(Exception e)
		{
			ErrorMessage = e.getMessage();
			ErrorMessage = e.getMessage();TestUiLogger.error("TESTCASE", "Unable to find web element-' "+strTagName+" ','"+e.getMessage()+"'");
			return null;
		}
	}

	//click on image
	public boolean clickOnImage(WebElement WebElementImage)
	{
		try
		{
			if(WebElementImage!=null)
			{
				if(WebElementImage.isEnabled())
				{
					try {
						JavascriptExecutor executor = (JavascriptExecutor)driver;
						executor.executeScript("arguments[0].style.border='3px solid red'", WebElementImage);
						executor.executeScript("arguments[0].click();", WebElementImage);
					} catch (Exception e) {
						WebElementImage.click();
					}
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
		catch(WebDriverException e)
		{
			ErrorMessage = e.getMessage();
			ErrorMessage = e.getMessage();TestUiLogger.error("TESTCASE", "Unable to find web element "+WebElementImage+" ','"+e.getMessage()+"'");
			return false;
		}
		catch(Exception e)
		{
			ErrorMessage = e.getMessage();
			ErrorMessage = e.getMessage();TestUiLogger.error("TESTCASE", "Unable to find web element "+WebElementImage+" ','"+e.getMessage()+"'");
			return false;
		}
	}


	//Returns list of web elements having same id
	public List<WebElement> findElementsById(String strId)
	{
		List<WebElement> webElementList= new ArrayList<WebElement>();
		try
		{
			webElementList = driver.findElements(By.id(strId));
			return webElementList;
		}
		catch(WebDriverException e)
		{
			ErrorMessage = e.getMessage();
			ErrorMessage = e.getMessage();TestUiLogger.error("TESTCASE", "Unable to find web elements having class name-' "+strId+" ','"+e.getMessage()+"'");
			return null;
		}
		catch(Exception e)
		{
			ErrorMessage = e.getMessage();
			ErrorMessage = e.getMessage();TestUiLogger.error("TESTCASE", "Unable to find web elements having class name-' "+strId+" ','"+e.getMessage()+"'");
			return null;
		}
	}

	//getting webelement by xpath
	public WebElement getWebElementWithXpath(String strActualXpath)
	{
		WebElement element;
		try
		{	strActualXpath=strActualXpath.trim();

		element=driver.findElement(By.xpath(strActualXpath));
		return element;
		}
		catch(WebDriverException e)
		{
			ErrorMessage = e.getMessage();
			ErrorMessage = e.getMessage();TestUiLogger.error("TESTCASE", "Unable to find web element-' "+strActualXpath+" ','"+e.getMessage()+"'");
			return null;
		}
		catch(Exception e)
		{
			ErrorMessage = e.getMessage();
			ErrorMessage = e.getMessage();TestUiLogger.error("TESTCASE", "Unable to find web element-' "+strActualXpath+" ','"+e.getMessage()+"'");
			return null;
		}
	}

	//quitting browser
	public void quitBrowser()
	{
		try{
			driver.quit();
		}
		catch(WebDriverException e)
		{

		}
	}

	//screenshot method
	public static void takeScreenShot(String FilePath)
	{

		try {
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(FilePath.trim()));
		} catch (IOException e) {

			e.printStackTrace();
		}
	}




}

