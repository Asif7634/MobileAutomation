package com.cg.SeleniumTestSetup;

import java.net.URL;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.cg.Application.WFM.ReusableComponents;
import com.cg.ApplicationLevelComponent.EggPlant;
import com.cg.Logutils.HtmlResult;
import com.cg.Logutils.Logging;
import com.cg.TestCaseRunner.TestCaseRunner;
import com.cg.UIDriver.WebUIDriver;

public class TestSetUp
{
	public static final long TimeOutInSec = 60;
	public static WebDriver webdriver;
	public static EggPlant ObjEggDriver= new EggPlant();
	protected static WebUIDriver webUIDriver; 
	protected static Logging TestUiLogger=TestCaseRunner.TestCaseLogger;
	public static String WfmLoginUserName = "";
	public static String WfmLoginPassword = "";
	
	public static boolean launchApplication(String appURL)
	{
		DesiredCapabilities capabilities=DesiredCapabilities.internetExplorer();
		String WebUrl=getGlobalData(appURL);
		try 
		{
			capabilities.setJavascriptEnabled(true);
			
			capabilities.setCapability(CapabilityType.BROWSER_NAME, "IE");

			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);

			capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);

			capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
			
			String CurrentDir=System.getProperty("user.dir");
			System.setProperty("webdriver.ie.driver", CurrentDir + "\\Selenium\\IEDriverServer.exe");	
			
			//creating instance of webdriver and storing it into webuidriver 
			webdriver = new InternetExplorerDriver();
			webUIDriver=new WebUIDriver(webdriver);
			
			//for remote excution webdriver = new RemoteWebDriver (new URL("http://localhost:5555/wd/hub"),capabilities);	
			webUIDriver.get(WebUrl);	
			webUIDriver.maximizeWindow();	
			
			WfmLoginUserName = getGlobalData("WFM_userName");
			WfmLoginPassword = getGlobalData("WFM_password");
	
			return true;
		}
		catch (Exception e)
		{
			//log 
			return false;
		}
	
	}
	
	public static boolean launchApplicationOnRemoteMachine(String appURL)
	{
		DesiredCapabilities capabilities = null;
		String WebUrl=getGlobalData(appURL);
		try 
		{
			String CurrentDir=System.getProperty("user.dir");
			System.setProperty("webdriver.ie.driver", CurrentDir + "\\Selenium\\IEDriverServer.exe");	
			
			//creating instance of webdriver and storing it into webuidriver 
			webdriver = new InternetExplorerDriver();
			// for local machine webUIDriver=new WebUIDriver(webdriver);
			
			//for remote excution 
			webdriver = new RemoteWebDriver (new URL("http://localhost:5555/wd/hub"),capabilities);	
			webUIDriver.get(WebUrl);	
			webUIDriver.maximizeWindow();	
			
			return true;
		}
		catch (Exception e)
		{
			//log 
			return false;
		}
	
	}
	
	
	
	
	
	
	public static String getGlobalData(String parameter)
	{
		String strGlobalValue="";
		{
			List<Map<String,String>> ConfigurationMap=TestCaseRunner.ConfigurationMap;
			for(Map map:ConfigurationMap)
			{
				String Key=(String)map.get("Key");
				if(Key.equals(parameter))
				{
					strGlobalValue=(String)map.get("Value"); 
					break;
				}
			}
		}
		return strGlobalValue;
	}
	
	
}
