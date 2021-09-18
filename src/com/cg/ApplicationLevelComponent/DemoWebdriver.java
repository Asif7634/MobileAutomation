package com.cg.ApplicationLevelComponent;


import com.cg.Logutils.HtmlResult;
import com.cg.TestCaseRunner.TestCaseRunner;
import com.cg.UIDriver.*;

import java.util.Map;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;


public class DemoWebdriver
{
	public static WebDriver driver;
	
	public boolean launchApplication(String pageTitle,String objectName,String Values)
	{		
		String baseURL = "";
		try
		{
			System.setProperty("webdriver.chrome.driver","D:\\UKIT_Automation\\chromedriver.exe");	
			driver = new ChromeDriver();
			baseURL=Values;
			driver.get(baseURL);
			driver.manage().window().maximize();	
			pageTitle=driver.getTitle();					
		}
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HtmlResult.passed("Open the browser window","Enter the URL-"+baseURL+"\n The page title should be -"+pageTitle,"Browser launched successfully");
		return true;
	}
	
	public void clickButton(String pageTitle,String objectName,String Values) throws InterruptedException
	{
		Thread.sleep(8000);
		String xpath=EggUIDriver.getWebelementProperty(pageTitle,objectName);
		driver.findElement(By.xpath(xpath)).click();
		HtmlResult.passed("Click Button ","Button '"+objectName+"' should get clicked " ,"Button clicked successsfully" );
		
	}
	
	//Component-To click Link text on the web page
	public void clickLinkText(String pageTitle,String objectName,String Values) throws InterruptedException
	{	
		Thread.sleep(8000);
		String values=Values.trim();
		WebElement element = driver.findElement(By.linkText(values));
		element.click();
		HtmlResult.passed("Click Link Text :-","Link '"+values+"' should get clicked :-","Link clicked successfully");		
	}
	
	//Component-Form to create a new account
	public void createYourMcDonaldsJobsAccount(String pageTitle,String objectName,String Values)
	{
		String xpath="";
		String objValue="";
		WebElement element;
		
		//Enter email id
		objectName="EmailId";
		xpath=EggUIDriver.getWebelementProperty(pageTitle,objectName);
		element=findElementByXpath(xpath);
		element.click();
		objValue=EggUIDriver.getWebelementProperty(pageTitle, objectName);
		sendKeys(element,objValue);
	}
	
	//Method to find element by xpath
	public WebElement findElementByXpath(String xpath)
	{
		WebElement element;
		return element=driver.findElement(By.xpath(xpath));
	}
	
	//Method to send keys to a text box
	public void sendKeys(WebElement element,String objValue)
	{
		element.sendKeys(objValue);
	}
	
	//Method to make selection from the drop down list
	public boolean dropDownSelection(String elementProperty,String elementToBeSelected)
	{
		boolean optionSelected=false;
		
		WebElement element=driver.findElement(By.xpath(elementProperty));
		//element.click();
		Select oSelect = new Select(element);
		
		
		oSelect.selectByVisibleText(elementToBeSelected);
		if(element.isSelected())
		{
			optionSelected=true;
		}
		
		return optionSelected;		
	}
	
	
	public boolean waittillExist(WebDriver chrome,String Xpath) throws InterruptedException
		{	
			boolean var = false;
			int i=3000;
			int loopBreaker=1;
			while(var==false || loopBreaker>=15)
			{	
				Thread.sleep(i);
				var = (chrome.findElement(By.xpath(Xpath)).isDisplayed() && chrome.findElement(By.xpath(Xpath)).isEnabled());
				System.out.println(i+"Milliseconds "+Xpath +" "+var);
				loopBreaker++;
				
			}
			
			if(!var)
			{
				System.out.println("False-"+Xpath);
			}
			
			return var;
		} 


	
	//Close the Web browser
	public void closeBrowser(String parameters)
	{
		driver.quit();
		HtmlResult.passed("Close the browser window", "Browser window must get closed", "Browser window closed successfully");
		System.out.println("browser window closed successfully");
	}
	/*public static void setup()
	{
		System.setProperty("webdriver.chrome.driver", "D://Selenium Files//chromedriver_win32//chromedriver.exe");
		WebDriver driver=new ChromeDriver();
				
		String baseURL="https://careers-v1.peopleclick.com/careerscp/client_mcdonalds/external/search.do?functionName=getSearchCriteria";
		driver.get(baseURL);		
	}*/
	
	
	//RMS portal
	public boolean launchApplication(Map URL)
	{		
		
		String baseURL =URL.get("URL").toString().trim() ;
		String pageTitle="";
		boolean isApplicationLaunched=false;
		try
		{
			String CurrentDir=System.getProperty("user.dir");
			System.setProperty("webdriver.chrome.driver", CurrentDir + "\\Selenium\\chromedriver.exe");	
			driver = new ChromeDriver();
			driver.get(baseURL);
			driver.manage().window().maximize();	
			pageTitle=driver.getTitle();	
			isApplicationLaunched=true;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			isApplicationLaunched=false;
		}
		
		if(isApplicationLaunched)
		{
			HtmlResult.passed("Launch the ORS(Online Recruitment System) application "," ORS application should be launched"+"\n Enter the URL-'"+baseURL+"' to login to the RMS portal"+"\n The page title should be -"+pageTitle,"ORS application successfully launched");
		}
		else
		{
			HtmlResult.failed("Launch the ORS(Online Recruitment System) application "," ORS application should be launched"+"\n Enter the URL-'"+baseURL+"' to login to the RMS portal"+"\n The page title should be -"+pageTitle,"Failed to launch the ORS application");
		}
		return true;
	}
		
	//Component- To login to the RMS portal
	public void loginToRMSPortal(String parameters) throws InterruptedException
	{
	
		String[] loginCredentials;
		WebElement element;
		String objValue="",LoginButton="LoginButton",xpath="",pageTitle="",OrganisationKey="",Password="",Username="";
		
		loginCredentials=parameters.split(",");
		pageTitle=loginCredentials[0];
		Username=loginCredentials[1];
		Password=loginCredentials[2];
		OrganisationKey=loginCredentials[3];
		
		//Enter the login credentials
		for(String objName:loginCredentials)
		{
			if(objName.contains("Username")||objName.contains("Password")||objName.contains("OrganisationKey"))
			{
				objValue=EggUIDriver.getWebelementFormValues(pageTitle, objName);
				sendKeys(pageTitle,objName,objValue,objName);
			}
		}
	
		//click on Log In button
		findElementByXpath(pageTitle, LoginButton,"Log In button");
	}
	
	//Component_To select application
	public void applicationSelection(String parameters) throws InterruptedException
	{
		String[] appSelectionDetails;
		String applicationName="",Locale="",pageTitle="",xpath="",objName_application="",okButton="okButton";
		boolean optionSelected=false;
		
		appSelectionDetails=parameters.split(",");
		pageTitle=appSelectionDetails[0];
		applicationName=appSelectionDetails[1];
		objName_application=appSelectionDetails[2];
		Locale=appSelectionDetails[3];
		
		Thread.sleep(5000);
		//Enter the application name
		dropDownSelection(pageTitle, objName_application, applicationName,"Application name");
		
		//Thread.sleep(3000);
		//Enter Locale
		dropDownSelection(pageTitle,Locale,"English (United States)","Locale");
		
		//click on OK Button
		findElementByXpath(pageTitle, okButton,"Ok button");
	}
	
	//Component- To log out
	public void LogOffFromRMSPortal(String parameters) throws InterruptedException
	{
		String[] logOffDetails;
		String logOffButton="",pageTitle="",xpath="";
		WebElement element;
		boolean isElementSelected=false;
		
		logOffDetails=parameters.split(",");
		pageTitle=logOffDetails[0];
		logOffButton=logOffDetails[1];
		
		Thread.sleep(15000);
		driver.findElement(By.xpath("//*[@id='startBarWrapper']/div[1]/div/div[2]/div[1]/span/span")).click();
		xpath=EggUIDriver.getWebelementProperty(pageTitle,logOffButton);
		element=driver.findElement(By.xpath(xpath));
		isElementSelected=element.isSelected();
		if(isElementSelected)
		{
			HtmlResult.passed("Logoff from Application", "Logoff button should be clicked", "Logoff button is clicked successfully");
		}
		else
		{
			HtmlResult.failed("Logoff from Application", "Logoff button should be clicked", "Logoff button is not clicked");
		}
	}
	
	public void requisitionCreation(String parameters) throws InterruptedException
	{
		//Thread.sleep(5000);
		WebElement element;
		String[] reqCreationDetails;
		String selectTemplate,pageTitle="",xpath="",restaurantNumber="",objValue="";
		String RequisitionsTab="Requisitions Tab",CreateRequisitionLink="Create Requisition",
			ReqTemplate="Requisitions Template",restNumLookupIcon="Lookup icon",
			restNumSearchBox="Restaurant_Num_searchBox",SelectRestaurantNumber="SelectRestaurantNumber",
			Restaurant_Num_popup_OkButton="Restaurant_Num_popup_OkButton";
		
			reqCreationDetails=parameters.split(",");
			pageTitle=reqCreationDetails[0].trim();
			selectTemplate=reqCreationDetails[1].trim();
			restaurantNumber=reqCreationDetails[2].trim();

		
			//Click on Requisitions Tab.
			findElementByXpath(pageTitle,RequisitionsTab,"Requisition tab");
				
			// Click on ‘Create Requisition’ from drop down
			findElementByXpath(pageTitle,CreateRequisitionLink,"Create Requisition");
		
			Thread.sleep(5000);
		
			//Choose the template	
			
			//xpath=EggUIDriver.getWebelementProperty(pageTitle,"Requisitions Template").trim();
			//dropDownSelection(pageTitle,"Requisitions Template","Crew - Franchisee");
			
			//Thread.sleep(5000);		
			waittillExist(driver,"//*[@id='ReqTemplates']");			
			driver.findElement(By.xpath("//*[@id='ReqTemplates']")).click();
				 	
			waittillExist(driver,"//*[@id='ReqTemplates']/option[4]");
			driver.findElement(By.xpath("//*[@id='ReqTemplates']/option[4]")).click();
			HtmlResult.passed("Template selection", "Select the ", "");
			
			//Select restuarant number -click on lookup icon,Enter restaurant number,Click on search button,Select restaurant
			//Thread.sleep(10000);
			findElementByXpath(pageTitle,restNumLookupIcon,"Restaurant number selection icon");
			
			//Enter the resaurant num.
			sendKeys(pageTitle,restNumSearchBox,restaurantNumber,"Restaurant number");
			
			waittillExist(driver, "//*[@id='AFL_FLD_REQ_RESTAURANT_NO_searchbtn__a']/span");
			driver.findElement(By.xpath("//*[@id='AFL_FLD_REQ_RESTAURANT_NO_searchbtn__a']/span")).click();
			//Thread.sleep(10000);
			
			findElementByXpath(pageTitle,SelectRestaurantNumber,"Restaurant number");
			
			findElementByXpath(pageTitle,Restaurant_Num_popup_OkButton,"OK button");
			
			//Franchiese info
			dropDownSelection(pageTitle,"Franchisee Position","No","Franchiese info");
			
			//Payroll provided
			dropDownSelection(pageTitle,"Payroll Provided","Yes","Payroll provided");
			
			//Operations/Field Services Manager
			dropDownSelection(pageTitle,"Operations/Field Services Manager","N. REMMER","Operations/Field Services Manager");
			
			//Operations/Field Consultant
			dropDownSelection(pageTitle,"Operations/Field Consultant","D. RUDGE","Operations/Field Consultant");
			
			//Owner/Operator
			dropDownSelection(pageTitle,"Owner/Operator","ANDREW CRITCHER","Owner/Operator");
			
			//Region
			dropDownSelection(pageTitle,"Region","Southern","Region");
			
			//Area
			dropDownSelection(pageTitle,"Area","London","Area");
			
			//Location
			dropDownSelection(pageTitle,"Location","East London","Location");
			
			//Salary Band
			dropDownSelection(pageTitle,"Salary Band","£21,500","Salary Band");
			
			//Regional Weighting
			dropDownSelection(pageTitle,"Regional Weighting","£1,500","Regional Weighting");
			
			//Benefit Packages
			dropDownSelection(pageTitle,"Benefit Packages","IP - Company benefit package","Benefit Packages");
			
			//Add recruiter
			findElementByXpath(pageTitle,"AddRecruiter","Add Recruiter");
			
			//Enter Last Name
			sendKeys(pageTitle,"LastName","%kokate%","Last Name");
			
			//click on search button
			findElementByXpath(pageTitle,"SearchLastName","Search button");
			
			//click on checkBox CheckBoxRecruiter
			findElementByXpath(pageTitle,"CheckBoxRecruiter","Checkbox");
			
			//click on checkBox okButton
			findElementByXpath(pageTitle,"RecruiterOkBtn","OK button");
			
			//---------Hiriring manager--------
			//Add Hiriring manager
			findElementByXpath(pageTitle,"AddHiringManger","Add Hiriring manager");
			
			//Enter Last Name
			sendKeys(pageTitle,"LastName","%kokate%","Last Name");
			
			//click on search button
			findElementByXpath(pageTitle,"SearchLastName","Search button");
			
			//click on checkBox CheckBoxRecruiter
			findElementByXpath(pageTitle,"CheckBoxRecruiter","Checkbox");
			
			//click on checkBox okButton
			findElementByXpath(pageTitle,"HiringMangerOkBtn","OK Button");
						
			//create requisition
			findElementByXpath(pageTitle,"CreateRequisitionBtn","create requisition button");
			
			
			//Close the Requisition was successfully created alert //*[@id='alertBar']/a
			waittillExist(driver, "//*[@id='alertBar']/a");
			driver.findElement(By.xpath("//*[@id='alertBar']/a")).click();
			
			/*Alert alert = driver.switchTo().alert();
			String textOnAlert=alert.getText();
			HtmlResult.passed("","",textOnAlert);
			alert.dismiss();*/
		//}
		//driver.switchTo().window(parent_Window);
	
	}
	
	public void findElementByXpath(String pageTitle,String objName,String stepDecrption) throws InterruptedException
	{
		String xpath="";
		WebElement element;
		
		try{
			xpath=EggUIDriver.getWebelementProperty(pageTitle,objName);
			waittillExist(driver, xpath);	
			element=driver.findElement(By.xpath(xpath));		
			if(element.isDisplayed())
			{
				element.click();
				HtmlResult.passed("Click on - "+stepDecrption, objName+" should be selected", "Element selection successful");
			}
		}
		catch(Exception e)
		{
			HtmlResult.failed("Click on - "+stepDecrption, objName+" should be selected", "Element selection failed because -"+e);
		}
	}
	
	public void sendKeys(String pageTitle,String objName,String objValue,String stepDecrption) throws InterruptedException
	{
		WebElement element;
		String xpath="";
		
		try{
			xpath=EggUIDriver.getWebelementProperty(pageTitle,objName);
			waittillExist(driver, xpath);
			element=driver.findElement(By.xpath(xpath));
			if(element.isDisplayed())
			{
				element.click();
				element.sendKeys(objValue);
				HtmlResult.passed("Enter - "+stepDecrption, "Below data should be entered:-"+"\nEnter '"+objValue+"' as "+objName, "Data entry successful");
			}
		}
		catch(Exception e)
		{
			HtmlResult.failed("Enter - "+stepDecrption, "Below data should be entered:-"+"\nEnter the '"+objValue+"' as "+objName, "Failed to enter data as - "+e);
		}
		
	
	}
	
	public void dropDownSelection(String pageTitle,String objName,String objValue,String stepDecrption) throws InterruptedException
	{
		String xpath;
		WebElement element;
		
		try
		{
			xpath=EggUIDriver.getWebelementProperty(pageTitle,objName);
			waittillExist(driver, xpath);
			boolean optionSelected=false;
			
			element=driver.findElement(By.xpath(xpath));
			//element.click();
			if(element.isDisplayed())
			{
				Select oSelect = new Select(element);	
				oSelect.selectByVisibleText(objValue);
				HtmlResult.passed("Select - "+stepDecrption, "\n Select "+objValue+" as "+objName, objValue+" selected");
			}
		}
		catch(Exception e)
		{
			HtmlResult.failed("Select - "+stepDecrption, "\n Select "+objValue+" as "+objName, objValue+" not selected because "+e);
		}
		
	}
	
	public void applyForJob(String parameters) throws InterruptedException
	{
		WebElement element;
		String[] careersPageDetails;
		String pageTitle="",xpath="";
		
		careersPageDetails=parameters.split(",");
		pageTitle=careersPageDetails[0];
		
		//Click on apl
		xpath=EggUIDriver.getWebelementProperty(pageTitle,"ApplyNowBtn");
		waittillExist(driver, xpath);
		element=findElementByXpath(xpath);
		element.click();
	}
	
}

