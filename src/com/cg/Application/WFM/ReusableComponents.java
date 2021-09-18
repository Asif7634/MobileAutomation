package com.cg.Application.WFM;

import com.cg.UIDriver.WebUIDriver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import com.cg.SeleniumTestSetup.TestSetUp;
import com.cg.TestCaseRunner.TestCaseRunner;

public class ReusableComponents extends TestSetUp
{
	static WebElement element;
	private static String globalTextOnAlert = "";
	
	//Login to the application
	/***************************************************************************************************************
	* Project - McDonalds UKIT Automation eggPlant Methods
	* Method Name – loginToTheApplication
	* Method Description - This method will login into the application with the credentials provided as input
	* Return Type - Boolean value
	* Parameters - user name, password
	* Framework - UKIT Master Framework
	* Author - Shruti Chavan
	* Creation Date - 08/02/2017
	* Modification History: 
	* # <Date>     <Who>                  <Mod description>
	***************************************************************************************************************/
	public static boolean loginToTheApplication(String strUsername,String strPassword)
	{
		boolean blnFlag=false;
		
			//enter user name
			blnFlag=webUIDriver.locateElementAndSendKeys("Login_userName", strUsername);
			if(blnFlag)
			{
				//enter password
				blnFlag=webUIDriver.locateElementAndSendKeys("Login_password", strPassword);
				if(blnFlag)
				{
					//click on login button
					blnFlag=webUIDriver.locateElementAndClick("Login_loginButton");
					if(blnFlag==false)
					{
						TestUiLogger.error("TESTCASE", "Unable to Click on login button");
					}
				}
				else
				{
					TestUiLogger.error("TESTCASE", "Unable to Enter password in text box");
					blnFlag=false;
				}
			}
			else
			{
				TestUiLogger.error("TESTCASE", "Unable to Enter UserName in text box");
				blnFlag=false;
			}
		return blnFlag;
	}
	
	/***************************************************************************************************************
	* Project - McDonalds UKIT Automation eggPlant Methods
	* Method Name – fetchTestData
	* Method Description - This method will fetch data from the required excel file in form of Map
	* Return Type - Map<String, String>
	* Parameters - Row id, file name
	* Framework - UKIT Master Framework
	* Author - Shruti Chavan
	* Creation Date - 08/02/2017
	* Modification History: 
	* # <Date>     <Who>                  <Mod description>
	***************************************************************************************************************/
	public static Map<String,String> fetchTestData(String RowId,String FileName )
	{
		Map<String,String> mapTestDataSet = new HashMap<String, String>();
		try {
			String DataSetRowNumber=RowId;
			System.out.println("file");
			String CurrentDir=System.getProperty("user.dir");
			Workbook WorkBook = null;
			List<Map<String, String>> TestCaseDataMap;
			String filePath=CurrentDir+"\\FrameWork\\TestData\\WFM\\"+FileName+".xlsx";

			WorkBook=TestCaseRunner.getWorkbook(filePath.trim());		// get workbook variable of testcase file name
			TestCaseDataMap=TestCaseRunner.CreatMapFromSheet(WorkBook,"TestData");	// create list of maps from Sheet "TestData" 
																					// of local workbook variable
			for(Map mapTestData:TestCaseDataMap)
			{
				String strRequiredDataSet=mapTestData.get("Data Set number").toString();
				if(strRequiredDataSet.equals(DataSetRowNumber))
				{
					mapTestDataSet=mapTestData;	
					break;
				}
			}
			
			return mapTestDataSet;
		} catch (Exception e) {
			
			//log
			return mapTestDataSet;
		}
	}
	
	/***************************************************************************************************************
	* Project - McDonalds UKIT Automation eggPlant Methods
	* Method Name – searchStore
	* Method Description - This method will search and select the required store
	* Return Type - Boolean value
	* Parameters - Store number
	* Framework - UKIT Master Framework
	* Author - Shruti Chavan
	* Creation Date - 08/02/2017
	* Modification History: 
	* # <Date>     <Who>                  <Mod description>
	***************************************************************************************************************/
	
	public static boolean searchStore(String strStoreNumber) throws InterruptedException
	{
		Boolean blnFlag=false;
		List<WebElement> webElementList= new ArrayList<WebElement>();  
		
		//Click on McDonaldsCorp
		blnFlag=webUIDriver.locateElementAndClick("MySchedule_McDonalds (CORP)");
		if(blnFlag)
		{
			//Enter restaurant number in the search box
			blnFlag=webUIDriver.locateElementAndSendKeys("SelectStore_searchRestaurant", strStoreNumber);
			if(blnFlag)
			{
				//Click on GO button
				blnFlag=webUIDriver.locateElementAndClick("SelectStore_goButton");
				if(blnFlag)
				{
					//Select the desired store number and click on it
					int counter = 1;
					do{
						webElementList=webUIDriver.getElementsByTagname("a");
						element=webUIDriver.getElementContainingRequiredText(webElementList,strStoreNumber);				    
						counter ++;
					} while (element==null && counter <= 20); 
					element.sendKeys(Keys.ENTER);
					//----------------
				}
				else
				{
					TestUiLogger.error("TESTCASE", "Unable to Click on Go Button");
					blnFlag=false;
				}
			}
			else
			{
				TestUiLogger.error("TESTCASE", "Unable to enter restaurant number in text field ");
				blnFlag=false;
			}	
		}
		else
		{
			TestUiLogger.error("TESTCASE", "Unable to click on 'MySchedule_McDonalds'");
			blnFlag=false;
		}
		return blnFlag;	
	}
	
	//To select the week for which schedule needs to be created
	/***************************************************************************************************************
	* Project - McDonalds UKIT Automation eggPlant Methods
	* Method Name – selectWeekForSchedule
	* Method Description - This method will search and select the required week
	* Return Type - Boolean value
	* Parameters - week
	* Framework - UKIT Master Framework
	* Author - Shruti Chavan
	* Creation Date - 08/02/2017
	* Modification History: 
	* # <Date>     <Who>                  <Mod description>
	***************************************************************************************************************/
    public static boolean selectWeekForSchedule(String StrWeek) throws InterruptedException
    {
       boolean blnFlag=false;
       String strNavigationTabText;
     
    
      //click on Schedule button
     
      blnFlag=webUIDriver.locateElementAndClick("ModuleSelection_scheduleButton"); 
      
      if(blnFlag)
      {
    	
 		blnFlag=webUIDriver.locateElementAndClick("PostShiftAnalysis_Close");       
    	  
 		if(blnFlag)
 		{
 			blnFlag=clickOnNavigationTab("Schedule");
 			Thread.sleep(3000);

        	if(blnFlag)
        	{
        		//week Selection
        		blnFlag=clickOnNavigationTab(StrWeek);  
        		Thread.sleep(3000);
        		if(blnFlag==false)
        		{
        			TestUiLogger.error("TESTCASE", "Unable to Click on Next Week Navigation Button");
    				blnFlag=false;
        		}
        		
        	}
        	else
        	{
        		TestUiLogger.error("TESTCASE", "Unable to Click on Schedule Navigation bar Button");
				blnFlag=false;
        	}
 		}
 		else
 		{
 			TestUiLogger.error("TESTCASE", "Unable to Click on Cross button on window PostShiftAnalysis");
			blnFlag=false;
 		}
      }
      else
      {
    	  TestUiLogger.error("TESTCASE", "Unable to Click on Schedule Image");
			blnFlag=false;
      }
		return blnFlag;    
    } 
          
    
	//get text from the navigation tabs
    public static boolean clickOnNavigationTab(String strNavigationTabText) throws InterruptedException
    {
    	boolean blnVar=false;
    	String strText="";
    	try
    	{
    	//for storing all navigation bar 
    	List<WebElement> webElementList= new ArrayList<WebElement>();  
    																					//changes are done as it was trying to search the list which was not loaded.
    	int counter=1;
    	do{
    		Thread.sleep(1000);
    		webElementList=webUIDriver.findElementsByxpath("topNavigationTabs");        	
    		counter++;
    	}while(counter<TestSetUp.TimeOutInSec && webElementList.size()==0);
    
    	blnVar=true;
    	
    	//Click on Schedule on navigation bar
    	for(WebElement element:webElementList)
        {
        	strText=element.getText();
        	if(strText.contains(strNavigationTabText))
        	{    
        		
        		boolean blnFlag = webUIDriver.waitExplicitlyByElement(element, TestSetUp.TimeOutInSec);        		
				if(blnFlag && element.getLocation().x>0)
				{
					if(webUIDriver.clickByJSExecutor(element)) {
        				return true;
        			}	
					else
					{
						element.sendKeys(Keys.ENTER);
					}
				}	
        	} 
        }
    	}
    	catch(WebDriverException e)
    	{
    		 TestUiLogger.error("TESTCASE", "Unable to find web element-' ','"+e.getMessage()+"'");
			 return false;
    	}
    	 catch(Exception e)
    	 {
	    	 TestUiLogger.error("TESTCASE", "Unable to find web element-' ','"+e.getMessage()+"'");
			 return false;
	     }
	     
    	return blnVar;
    }
    
	//To Select fixed shifts tab
	public static boolean selectFixedShifts()
	{
		
		boolean blnval= railTrackSelection("Fixed shifts");
		
		return blnval;
		
	}
	
	//To select the projections tab
	public boolean selectProjections()
	{
		boolean blnFlag=false;
		blnFlag=railTrackSelection("Projections");
			if(blnFlag)
			{
				//Fixed shifts selection done
				blnFlag=true;
			}
		return blnFlag;	
	}

	//To select the matrix tab
	public boolean selectMatrix()
	{
		boolean blnFlag=false;
		
		blnFlag=railTrackSelection("Matrix");
		if(blnFlag)
		{
			//Fixed shifts selection done
			blnFlag=true;
		}
		return blnFlag;	
	}
	
	//Select Shifts
	public static boolean selectShifts()
	{
		boolean blnFlag=false;
		blnFlag=railTrackSelection("Shifts");
		if(blnFlag)
		{
			//Fixed shifts selection done
			blnFlag=true;
		}
		return blnFlag;	
	}
	
	//To select the framework
	public boolean selectFramework()
	{
		boolean blnFlag=false;
		
		blnFlag=railTrackSelection("Framework");
		if(blnFlag)
		{
			//Fixed shifts selection done
			blnFlag=true;
		}
		return blnFlag;	
	}
	
	//to close pos shift analysis
	public static boolean PostShiftAnalysis_Close()
	{
		boolean blnFlag;
		 blnFlag=webUIDriver.locateElementAndClick("PostShiftAnalysis_Close");     
		
         if(blnFlag)
         {
        	 return blnFlag;
         }
         else
         {
        	 return blnFlag;
         }		
	}
	
	//To select the rail road text (fixed shifts,shifts,framework,projections.....)
	public static boolean railTrackSelection(String railText)
	{
		 boolean blnFlag=false;
		 String strClassName="";
		 element=null;
	     String strRailText="";
	     List<WebElement> webElementList= new ArrayList<WebElement>();  
	
	     try
	     {
		     strClassName=webUIDriver.getWebelementProperty("Class Name", "railTrack");
		     																								//changes are done as it was trying to search the list which was not loaded 
		     int counter=1;
		    	do{
		    		Thread.sleep(1000);
		    		webElementList=webUIDriver.getElementsByTagname("a");        	
		    		counter++;
		    	  }while(counter<TestSetUp.TimeOutInSec && webElementList.size()==0);
		     
		     for(WebElement railTrackElement:webElementList)
		     {
		    	 String strElementClass=railTrackElement.getAttribute("class");
		    	 if(strElementClass.equals(strClassName))
		    	 {
		    		 strRailText=railTrackElement.getText();
		    		 if(strRailText.equals(railText))
		    		 {
	    			// railTrackElement.sendKeys(Keys.ENTER);
		    			 if(webUIDriver.clickByJSExecutor(railTrackElement) && webUIDriver.waitExplicitlyByElement(railTrackElement, TimeOutInSec) && railTrackElement.getLocation().x>0)
	    				{
   				 			return true;
	    				
    				 }		    			 
		    			 
		    		 }
		    	 }
		     }
	     }
	     catch(WebDriverException e)
	     {
	    	 TestUiLogger.error("TESTCASE", "Unable to find web element-' ','"+e.getMessage()+railText+"'");
			 return false;
	     }
	     catch(Exception e)
	     {
	    	 return false;
	     }
		
	     return false;
	}
	
	//confirm fixed shifts - Clicks on the the confirm button to confirm the shifts
	public static boolean confirmFixedShifts(String strComments)
	{
		boolean blnFlag=false;
		
		blnFlag=selectFixedShifts();
		blnFlag=webUIDriver.locateElementAndClick("FixedShifts_confirmButton");
		if(blnFlag)
		{
			Alert alert = webdriver.switchTo().alert();
			alert.accept();
			
			//click on comments
			blnFlag=webUIDriver.locateElementAndClick("FixedShifts_commentsButton");
			if(blnFlag)
			{
				//Enter comments
				blnFlag=webUIDriver.locateElementAndSendKeys("FixedShifts_comments_commentTextBox", strComments);
				if(blnFlag)
				{
					//click on save button
					blnFlag=webUIDriver.locateElementAndClick("FixedShifts_SaveButton");
				}
			}
		}	
		return blnFlag;
	}	
	
	//confirm projections
	public static boolean confirmProjections()
	{
		boolean blnFlag=false;
		
		//click on confirm button
		blnFlag=ObjEggDriver.clickButton("WFMProjections_confirmButton");
		if(blnFlag)
		{
			//click on OK button
			blnFlag=ObjEggDriver.clickButton("WFMProjections_OKButton");
		}	
		return blnFlag;		
	}
	
	
	/***************************************************************************************************************
	* Project - McDonalds UKIT Automation eggPlant Methods
	* Method Name – editFixedShifts
	* Method Description - This method click on the edit button of fixed shifts
	* Return Type - Boolean value
	* Framework - UKIT Master Framework
	* Author - Shruti Chavan
	* Creation Date - 08/02/2017
	* Modification History: 
	 * # <Date>     <Who>                  <Mod description>

	***************************************************************************************************************/
	//Edit fixed shifts
	public static boolean editFixedShifts()
	{
		boolean blnFlag=false;
		//click on fixed shifts
		try 
		{
			blnFlag=selectFixedShifts();
			if(blnFlag)
			{
				//click on edit button
				if(webUIDriver.locateElementAndClick("FixedShifts_editButton"))
				{					
					webUIDriver.getTextOnAlertBox();
					blnFlag=true;
				}
				else
				{
					TestUiLogger.error("TESTCASE", "Unable to Click on Edit button");
					blnFlag=false;
				}
			}
			else
			{
				TestUiLogger.error("TESTCASE", "Unable to select fixed shift before edit");
				blnFlag=false;
			}
		} 
		catch (WebDriverException e) 
		{
			TestUiLogger.error("TESTCASE", "Unable to edit fixed shifts  ' "+e.getMessage()+" '");
			blnFlag=false;
		}
		return blnFlag;
	}
	
	/***************************************************************************************************************
	* Project - McDonalds UKIT Automation eggPlant Methods
	* Method Name – selectDateOfWeekFromShifts
	* Method Description - This method will select a day/date from the dropdown in fixed 'Shifts'.
	* Return Type - Boolean value
	* Parameters - Inputs are fetched from map (inputs are):Day is fetched from test data.E.g.Monday,Tuesday etc.
	* Framework - UKIT Master Framework
	* Author - Taranpreet Kaur
	* Creation Date - 08/02/2017
	* Modification History: 
	* # <Date>     <Who>                  <Mod description>

	***************************************************************************************************************/
	public static boolean selectDateOfWeekFromShifts(String strDay)			
		{
		boolean blnFlag=false;
		String strIndex="";
		//Fetch day from map and select corresponding index
		switch(strDay)
		{
		case "Monday":
			strIndex = "0";
        	break;
		case "Tuesday":
			strIndex = "1";
		    break;
		case "Wednesday":
			strIndex = "2";
		    break;
		case "Thursday": 
			strIndex = "3";
		    break;
		case "Friday":
			strIndex = "4";
		    break;
		case "Saturday":
			strIndex = "5";
		    break;
		case "Sunday":
			strIndex = "6";
		    break;	
		default: strIndex = "0";
			break;
		}
		
		
		//Select dropdown menu
		blnFlag=webUIDriver.findElementByXpath("Shifts_selectDateOfWeek");
		element=webUIDriver.getElementByXpath("Shifts_selectDateOfWeek");
		
		//Create object of Select
		Select dropdown= new Select(element);
		
		
		//Index to be clicked
		int strIndex1 = Integer.parseInt(strIndex);
		
		//Select value from dropdown
		if(blnFlag)
		{
				dropdown.selectByIndex(strIndex1);
				//or
				//dropdown.selectByValue(strDay);
				blnFlag=true;
			}
		
		return blnFlag;
		}
		
		
		/***************************************************************************************************************
		* Project - McDonalds UKIT Automation eggPlant Methods
		* Method Name – selectSliderTime
		* Method Description - This method will select a time from the dropdown in 'Shifts' and also verifies if all values are displayed in dropdown.It also verifies slider time and dropdown time displayed are same.
		* Return Type - Boolean value
		* Parameters - Inputs are fetched from map (inputs are)
		* Framework - UKIT Master Framework
		* Author - Taranpreet Kaur
		* Creation Date - 10/02/2017
		* Modification History: 
		* # <Date>     <Who>                  <Mod description>

		***************************************************************************************************************/

		public static boolean selectSliderTimeFromShifts(String strHour)
			{
			
			boolean blnFlag=false;
			String DropdownValue="";
			
			//get all element values of dropdown
			List<WebElement> options=webUIDriver.findElementsByxpath("Shifts_sliderTimeOptions");
			blnFlag=webUIDriver.findElementByXpath("Shifts_sliderTimeOptions");
			
			//Verify the number of elements
			int size=options.size();
			if(size==25){
				System.out.println("All values of hour are displayed in the dropdown");
				//HtmlResult.passed("All values of hour are displayed in the dropdown", "All values of hour should be displayed in the dropdown", "All values of hour are displayed in the dropdown");
				blnFlag=true;
			}
			else{
				System.out.println("All values of hour are not displayed in the dropdown");
				//HtmlResult.failed("All values of hour are displayed in the dropdown", "All values of hour should be displayed in the dropdown", "All values of hour are not displayed in the dropdown");
			}
			
			//Verify all the values in the dropdown individually
			List<String> text = new ArrayList<>();
			for(int i=0; i<options.size(); i++) {
				
			  String DropdownValues= options.get(i).getText();
			  System.out.println(DropdownValues);
			}
			
			//Select the hour
			int strIndex = Integer.parseInt(strHour);
			element=webUIDriver.getElementByXpath("Shifts_sliderTime");
			Select dropdown= new Select(element);	
			if(blnFlag)
			{
				dropdown.selectByIndex(strIndex);
				DropdownValue =options.get(strIndex).getText();
			}
			
			//Save the dropdown value of hour
			DropdownValue=DropdownValue.trim();
			
			//Find the value of hour from the slider-table 
			WebElement hour= webUIDriver.getElementByXpath("Shifts_sliderTimeFirstElement");
			String SliderValue1=hour.getText();
			WebElement minute= webUIDriver.getElementByXpath("Shifts_sliderTimeSecondElement");
			String SliderValue2=minute.getText();
			String SliderValue =SliderValue1+SliderValue2;
			SliderValue=SliderValue.trim();
			
			//Verify if slider value and dropdown value of hour are same.
			if(DropdownValue.equals(SliderValue))
				System.out.println("Correct time is selected.");
				//HtmlResult.passed("Correct time is displayed.","Correct time should be displayed.", "Correct time is selected.");
			else
				System.out.println("Correct time is not selected.");
				//HtmlResult.failed("Correct time is displayed.","Correct time should be displayed.", "Correct time is not selected.");
			
			return blnFlag;
			}
		
		/***************************************************************************************************************
		* Project - McDonalds UKIT Automation eggPlant Methods
		* Method Name – modifyTimeScale
		* Method Description - This method will select hour from the slider in 'Shifts'.
		* Return Type - Boolean value
		* Parameters - Inputs are fetched from Test Data(hours)
		* Framework - UKIT Master Framework
		* Author - Taranpreet Kaur
		* Creation Date - 13/02/2017
		* Modification History: 
		* # <Date>     <Who>                  <Mod description>

		***************************************************************************************************************/
		
		public static boolean modifyTimeScaleFromShifts(String strHour)
		{
			
			boolean blnFlag=false;
			
			//List of all matching elements
			List<WebElement> timeSliderList = new ArrayList<WebElement>();
			timeSliderList =webUIDriver.findElementsByxpath("Shifts_TimeLocator");
			
			//Find Exactly same element
			WebElement hour =webUIDriver.getElementContainingRequiredText(timeSliderList, strHour);
			
			//CLick on the slider
			String strSlider=hour.getText();
			int SliderLength=strSlider.length();
			if(SliderLength==4){
				strSlider="0"+strSlider;
			}
			hour.click();
			blnFlag=true;
			
			//Find the value of hour from the slider-table 
					WebElement Hour= webUIDriver.getElementByXpath("Shifts_sliderTimeFirstElement");
					String SliderTable1=Hour.getText();
					WebElement minute= webUIDriver.getElementByXpath("Shifts_sliderTimeSecondElement");
					String SliderTable2=minute.getText();
					String SliderTable =SliderTable1+SliderTable2;
					SliderTable=SliderTable.trim();
					
					//Verify if slider table value and slider value of hour are same.
					if(strSlider.equals(SliderTable))
						System.out.println("Correct time :"+strSlider+" is selected.");
						//HtmlResult.passed("Correct time is displayed.","Correct time should be displayed.", "Correct time is selected.");
					else
						System.out.println("Correct time :"+strSlider+" is not selected.");
			return blnFlag;
			
		}
		
		/***************************************************************************************************************
		* Project - McDonalds UKIT Automation eggPlant Methods
		* Method Name – selectOvers
		* Method Description - This method will select Overs checkbox from 'Shifts'.
		* Return Type - Boolean value
		* Parameters - Inputs are fetched from Test Data(hours)
		* Framework - UKIT Master Framework
		* Author - Taranpreet Kaur
		* Creation Date - 14/02/2017
		* Modification History: 
		* # <Date>     <Who>                  <Mod description>
		
		***************************************************************************************************************/
		public static boolean selectOvers(){
			boolean blnFlag=false;
			
			
			WebElement element =webUIDriver.getElementByXpath("Shifts_oversCheckbox");
			if(!element.isSelected())
			{
				blnFlag=webUIDriver.clickByXpath("Shifts_oversCheckbox");
				
			}
			else{
				System.out.println("Overs checkbox is already selected.");
			}
			
			return blnFlag;
		}
		
		/***************************************************************************************************************
		* Project - McDonalds UKIT Automation Selenium Methods
		* Method Name – SelectParticular_Emp
		* Method Description - This method will select particular employee.
		* Return Type - Boolean value
		* Parameters - strEmployee,strWeekDay
		* Framework - UKIT Master Framework
		* Author - Shikha Nagar
		* Creation Date - 08/02/2017
		* Modification History: 
		* # <Date>     <Who>                  <Mod description>

		***************************************************************************************************************/

		public static boolean SelectParticular_Emp(String strEmployeeName,String strDay,List<Map<String,WebElement>> tableDetails)
		{
			boolean blnFlag=false;
			WebElement element = null;
			List<WebElement> webElementList= new ArrayList<WebElement>();
			
			for(Map TableRow : tableDetails)
			{
				WebElement ColumnData =(WebElement)TableRow.get("Template");
				String strColumnData = ColumnData.getText();
				strColumnData = strColumnData.trim();
				
				//locating fromEmployee
				if(strColumnData.equalsIgnoreCase(strEmployeeName))
				{
					element = (WebElement) TableRow.get(strDay);
					webElementList=element.findElements(By.tagName("a"));
					for(WebElement e:webElementList)
					{
							element=e;
							System.out.println(e);
							break;
					}
					break;
				}	
			}
			return blnFlag;
		}
		
		
		/***************************************************************************************************************
		* Project - McDonalds UKIT Automation eggPlant Methods
		* Method Name – DeleteShift
		* Method Description - This method will delete the shift in 'Shifts' rail track and also confirm if the deleted shift is displayed on the screen.
		* Return Type - Boolean value
		* Parameters - 
		* Framework - UKIT Master Framework
		* Author - Shikha Nagar
		* Creation Date - 15/02/2017
		* Modification History: 
		* # <Date>     <Who>                  <Mod description>
		
		***************************************************************************************************************/
		public static boolean DeleteFixedShift()
		{
				boolean blnFlag=false;
				
				WebElement DropDownMenu = webUIDriver.getElementByXpath("FixedShifts_Shifts_dropdownMenu");
				if(DropDownMenu != null)
				{
					List<WebElement> DropDownOptions = DropDownMenu.findElements(By.tagName("a"));
					WebElement deleteShiftWebElement = null;
					blnFlag = false;
					
					for(WebElement DropDownOption : DropDownOptions)
					{
						WebElement ChildTag = DropDownOption.findElement(By.tagName("font"));
						String strOptionName = ChildTag.getText();
						String [] strArrOptionName = strOptionName.split("\n");
						if(strArrOptionName[0].equalsIgnoreCase("Delete Shift"))
						{
							deleteShiftWebElement = ChildTag;
							blnFlag=true;
							break;
						}
					}
				}
				return blnFlag;
		}
		
		/***************************************************************************************************************
		* Project - McDonalds UKIT Automation eggPlant Methods
		* Method Name – deselectOvers
		* Method Description - This method will deselect Overs checkbox from 'Shifts'.
		* Return Type - Boolean value
		* Parameters - 
		* Framework - UKIT Master Framework
		* Author - Taranpreet Kaur
		* Creation Date - 10/02/2017
		* Modification History: 
		* # <Date>     <Who>                  <Mod description>
		
		***************************************************************************************************************/
		public static boolean deselectOvers(){
			boolean blnFlag=false;
			
			
			WebElement element =webUIDriver.getElementByXpath("Shifts_oversCheckbox");
			if(element.isSelected())
			{
				blnFlag=webUIDriver.clickByXpath("Shifts_oversCheckbox");
				
			}
			else{
				System.out.println("Overs checkbox is already deselected.");
			}
			
			return blnFlag;
		}
		/***************************************************************************************************************
		* Project - McDonalds UKIT Automation eggPlant Methods
		* Method Name – selectShorts
		* Method Description - This method will select Shorts checkbox from 'Shifts'.
		* Return Type - Boolean value
		* Parameters - 
		* Framework - UKIT Master Framework
		* Author - Taranpreet Kaur
		* Creation Date - 14/02/2017
		* Modification History: 
		* # <Date>     <Who>                  <Mod description>
		
		***************************************************************************************************************/
		public static boolean selectShorts(){
			boolean blnFlag=false;
			
			
			WebElement element =webUIDriver.getElementByXpath("Shifts_shortsCheckbox");
			if(!element.isSelected())
			{
				blnFlag=webUIDriver.clickByXpath("Shifts_shortsCheckbox");
			}
			else{
				System.out.println("Shorts checkbox is already selected.");
			}
			return blnFlag;
		}
		
		/***************************************************************************************************************
		* Project - McDonalds UKIT Automation eggPlant Methods
		* Method Name – deselectShorts
		* Method Description - This method will deselect Shorts checkbox from 'Shifts'.
		* Return Type - Boolean value
		* Parameters - 
		* Framework - UKIT Master Framework
		* Author - Taranpreet Kaur
		* Creation Date - 14/02/2017
		* Modification History: 
		* # <Date>     <Who>                  <Mod description>
		
		***************************************************************************************************************/
		public static boolean deselectShorts(){
			boolean blnFlag=false;
			
			
			WebElement element =webUIDriver.getElementByXpath("Shifts_shortsCheckbox");
			if(element.isSelected())
			{
				blnFlag=webUIDriver.clickByXpath("Shifts_shortsCheckbox");
			}
			else{
				System.out.println("Shorts checkbox is already deselected.");
			}
			return blnFlag;
		}
		/***************************************************************************************************************
		* Project - McDonalds UKIT Automation eggPlant Methods
		* Method Name – expandGraph
		* Method Description - This method will click on topmost + and expand Graph in 'Shifts'.
		* Return Type - Boolean value
		* Parameters - 
		* Framework - UKIT Master Framework
		* Author - Taranpreet Kaur
		* Creation Date - 14/02/2017
		* Modification History: 
		* # <Date>     <Who>                  <Mod description>
		
		***************************************************************************************************************/
		public static boolean expandGraph(){
			boolean blnFlag=false;
			
			
			WebElement element =webUIDriver.getElementByXpath("Shifts_expandGraph");
			if(element.isEnabled())
			{
				blnFlag=webUIDriver.clickByXpath("Shifts_expandGraph");
			}
			else{
				System.out.println("Graph is already expanded.");
			}
			return blnFlag;
		}
		/***************************************************************************************************************
		* Project - McDonalds UKIT Automation eggPlant Methods
		* Method Name – expandDetails
		* Method Description - This method will click on bottom + and expand Graph in 'Shifts'.
		* Return Type - Boolean value
		* Parameters - 
		* Framework - UKIT Master Framework
		* Author - Taranpreet Kaur
		* Creation Date - 14/02/2017
		* Modification History: 
		* # <Date>     <Who>                  <Mod description>
		
		***************************************************************************************************************/
		public static boolean expandDetails(){
			boolean blnFlag=false;
			
			
			WebElement element =webUIDriver.getElementByXpath("Shifts_expandDetail");
			if(element.isEnabled())
			{
				blnFlag=webUIDriver.clickByXpath("Shifts_expandDetail");
			}
			else{
				System.out.println("Graph Details is already expanded.");
			}
			return blnFlag;
		}
		/***************************************************************************************************************
		* Project - McDonalds UKIT Automation eggPlant Methods
		* Method Name – saveShifts
		* Method Description - This method will save the shift in 'Shifts' rail track.
		* Return Type - Boolean value
		* Parameters - 
		* Framework - UKIT Master Framework
		* Author - Taranpreet Kaur
		* Creation Date - 15/02/2017
		* Modification History: 
		* # <Date>     <Who>                  <Mod description>
		
		***************************************************************************************************************/
		public static boolean saveShifts(){
			boolean blnFlag=false;
			
			//Check for element present
			blnFlag=webUIDriver.findElementByXpath("Shifts_saveShiftsButton");
			if(blnFlag)
			{
				//Click on Save Shifts
				 blnFlag =webUIDriver.locateElementAndClick("Shifts_saveShiftsButton");
			}
			else{
				System.out.println("Shifts could not be saved.");
			}
			
			return blnFlag;
		}
		/***************************************************************************************************************
		* Project - McDonalds UKIT Automation eggPlant Methods
		* Method Name – confirmShifts
		* Method Description - This method will confirm the shift in 'Shifts' rail track.
		* Return Type - Boolean value
		* Parameters - 
		* Framework - UKIT Master Framework
		* Author - Taranpreet Kaur
		* Creation Date - 15/02/2017
		* Modification History: 
		* # <Date>     <Who>                  <Mod description>
		
		***************************************************************************************************************/
		public static boolean confirmShifts(){
			boolean blnFlag=false;
			
			//Check for element present
			blnFlag=webUIDriver.findElementByXpath("Shifts_confirmShiftsButton");
			if(blnFlag)
			{
				//Click on Confirm Shifts
				 blnFlag =webUIDriver.locateElementAndClick("Shifts_confirmShiftsButton");
			}else{
				System.out.println("Shifts could not be confirmed.");
			}
			
			return blnFlag;
		}
		/***************************************************************************************************************
		* Project - McDonalds UKIT Automation eggPlant Methods
		* Method Name – deleteAssignedShift
		* Method Description - This method will delete the shift in 'Shifts' rail track and also confirm if the deleted shift is displayed on the screen.
		* Return Type - Boolean value
		* Parameters - 
		* Framework - UKIT Master Framework
		* Author - Taranpreet Kaur
		* Creation Date - 15/02/2017
		* Modification History: 
		* # <Date>     <Who>                  <Mod description>
		
		***************************************************************************************************************/
		public static boolean deleteAssignedShift(){
			boolean blnFlag=false;
			
			blnFlag=webUIDriver.findElementByXpath("Shifts_DeleteTable");
			if(blnFlag)
			{
				//List of all matching elements
				List<WebElement> deleteShiftList = new ArrayList<WebElement>();
				deleteShiftList = webUIDriver.findElementsByxpath("Shifts_DeleteTable");
				List<WebElement> ShiftList = new ArrayList<WebElement>();
				ShiftList = webUIDriver.findElementsByxpath("Shifts_ShiftsTable");
				
				//Get the first shift
				String delShift = ShiftList.get(0).getText();
				//Click on the first element
				//deleteShiftList.get(0).click();
				webUIDriver.clickUsingJavaScriptExecuter(deleteShiftList.get(0));
				
				//Switch to  and click on delete
		
				blnFlag =webUIDriver.switchToNewWindow();
				webUIDriver.clickByXpath("Shifts_PopUpDeleteButton");
				
				if(blnFlag){
				//Check shift is deleted or not
				WebElement element =webUIDriver.getElementContainingRequiredText(ShiftList, delShift);
				if(element==null){
					System.out.println("No records of deleted shift found.");
					//HtmlResult.passed("Records of deleted shift present in table", "No records of deleted shift should be found.", "No records of deleted shift found.");
					blnFlag=true;
				}
				else{
					System.out.println("Records of deleted shift found.");
					//HtmlResult.failed("Records of deleted shift present in table", "No records of deleted shift should be found.", "Records of deleted shift found.");
					blnFlag=false;
				}
				}
			}
			else
				{
				System.out.println("Shift could not be deleted.");
				blnFlag=false;
				}
			
			return blnFlag;
		}
		
		/***************************************************************************************************************
		* Project - McDonalds UKIT Automation eggPlant Methods
		* Method Name – clickToAddShift
		* Method Description - This method will click on the add shift button in fixed shifts
		* Return Type - boolean value
		* Framework - UKIT Master Framework
		* Author - Shruti Chavan
		* Creation Date - 08/02/2017
		* Modification History: 
		 * # <Date>     <Who>                  <Mod description>
		 * @throws InterruptedException 

		***************************************************************************************************************/	
		public static boolean clickToAddShift(String strEmployeeName,String strDay ,List<Map<String,WebElement>> tableDetails)
		{
			boolean blnFlag=false;
			List<WebElement> webElementList= new ArrayList<WebElement>();
			WebElement addShiftForEmployee = null;
		
			for(Map TableRow : tableDetails)
			{
				WebElement ColumnData =(WebElement)TableRow.get("Template");
				String strColumnData = ColumnData.getText();
				strColumnData = strColumnData.trim();
				
				//locating fromEmployee
				if(strColumnData.equalsIgnoreCase(strEmployeeName))
				{
					addShiftForEmployee = (WebElement) TableRow.get(strDay);
					webElementList=addShiftForEmployee.findElements(By.tagName("a"));
					for(WebElement e:webElementList)
					{
							element=e;
							System.out.println(e);
							break;
					}
					break;
				}	
			}
			
			if(element.isDisplayed())
			{
				JavascriptExecutor JAva = (JavascriptExecutor) webdriver;
				JAva.executeScript("arguments[0].click();", element);
				
				//click on add shift
				WebElement DropDownMenu = webUIDriver.getElementByXpath("FixedShifts_Shifts_dropdownMenu");
				
				List<WebElement> DropDownOptions = DropDownMenu.findElements(By.tagName("a"));
				WebElement CopyShiftWebElement = null;
				blnFlag = false;
				
				for(WebElement DropDownOption : DropDownOptions)
				{
					WebElement ChildTag = DropDownOption.findElement(By.tagName("font"));
					String strOptionName = ChildTag.getText();
					String [] strArrOptionName = strOptionName.split("\n");
					if(strArrOptionName[0].equalsIgnoreCase("Add Shift"))
					{
						CopyShiftWebElement = ChildTag;
						blnFlag=true;
						break;
					}
				}
				
				blnFlag=webUIDriver.clickUsingJavaScriptExecuter(CopyShiftWebElement);
			}	
			
			return blnFlag;
		}
		
		
		
		//calculateTimeInHrsAndMin
		public static String additionOfTime(String time1,String time2)
		{
			String[] arrt1=time1.split(":");
			String[] arrt2=time2.split(":");
			
			int totalHours = Integer.parseInt(arrt1[0]) + Integer.parseInt(arrt2[0]);
			int totalMinutes = Integer.parseInt(arrt1[1]) + Integer.parseInt(arrt2[1]);
			
			if (totalMinutes >= 60) {
			  totalHours ++;
			  totalMinutes = totalMinutes % 60;
			}
			
			String newtime = totalHours+":"+totalMinutes;
			return newtime;
		}
		
		//substraction of time
		public static String subtractionOfTime(String time1,String time2)
		{
			String[] arrt1=time1.split(":");
			String[] arrt2=time2.split(":");
			
			int min1=Integer.parseInt(arrt1[1]);
			int min2=Integer.parseInt(arrt2[1]);
			
			int totalHours = Integer.parseInt(arrt1[0]) - Integer.parseInt(arrt2[0]);
			if(min1<min2)
				{
					totalHours--;
					min1=min1+60;
				}
			int totalMinutes = min1 - min2;
		
			String newtime = totalHours+":"+totalMinutes;
			return newtime;
		}
		
		//Calculate duration after breaks
		public static String calDurationAfterBreak(String time,String strShiftLength)
		{
			//convert the string value into time hrs and mins for calculation purpose
			String strDurationAfterBreak=subtractionOfTime(strShiftLength,time);
			System.out.println(strDurationAfterBreak);
			
			strDurationAfterBreak=strDurationAfterBreak.replace(".", ":");
			
			String[] afterBreakTime=strDurationAfterBreak.split(":");
			if(afterBreakTime[0].length()==1)
				afterBreakTime[0]="0"+afterBreakTime[0];
			if(afterBreakTime[1].length()==1)
				afterBreakTime[1]=afterBreakTime[1]+"0";
				
			strDurationAfterBreak=afterBreakTime[0]+" Hrs "+afterBreakTime[1]+" Mins ";
			return strDurationAfterBreak;
		}

		//Select area of work
		public static boolean selectAreaOrSection(String areaOrSectionObjectName,String value)
		{
			boolean blnFlag=false;
			element=webUIDriver.locateElement(areaOrSectionObjectName);
			if(element.isDisplayed())
			{
				blnFlag=webUIDriver.selectFromDropDownListByVisibleText(element, value);
			}
			return blnFlag;
		}
		
		/***************************************************************************************************************
		* Project - McDonalds UKIT Automation eggPlant Methods
		* Method Name – clickToViewFixedShiftDetails
		* Method Description - This method will view the shift details of an employee for the particular day
		* Return Type - Boolean value
		* Framework - UKIT Master Framework
		* Author - Shruti Chavan
		* Creation Date - 08/02/2017
		* Modification History: 
		 * # <Date>     <Who>                  <Mod description>

		***************************************************************************************************************/
		public static boolean clickToViewFixedShiftDetails(List<Map<String,WebElement>> TableDetails,String strEmployeeName,String strDay)
		{
			boolean blnFlag=false;
			List<WebElement> webElementList= new ArrayList<WebElement>();
			WebElement elementViewShiftDetails = null;
			
			try
			{
				for(Map TableRow : TableDetails)
				{
					WebElement ColumnData =(WebElement)TableRow.get("Template");
					String strColumnData = ColumnData.getText();
					strColumnData = strColumnData.trim();
					
					//locating fromEmployee
					if(strColumnData.equalsIgnoreCase(strEmployeeName))
					{
						elementViewShiftDetails = (WebElement) TableRow.get(strDay);
						webElementList=elementViewShiftDetails.findElements(By.tagName("a"));
						for(WebElement e:webElementList)
						{
								element=e;
								System.out.println(e);
								break;
						}
						break;
					}	
				}
				
				if(element.isDisplayed())
				{
					JavascriptExecutor JAva = (JavascriptExecutor) webdriver;
					JAva.executeScript("arguments[0].click();", element);
					
					//click on add shift
					WebElement DropDownMenu = webUIDriver.getElementByXpath("FixedShifts_Shifts_dropdownMenu");
					
					List<WebElement> DropDownOptions = DropDownMenu.findElements(By.tagName("a"));
					WebElement CopyShiftWebElement = null;
					blnFlag = false;
					
					for(WebElement DropDownOption : DropDownOptions)
					{
						WebElement ChildTag = DropDownOption.findElement(By.tagName("font"));
						String strOptionName = ChildTag.getText();
						String [] strArrOptionName = strOptionName.split("\n");
						if(strArrOptionName[0].equalsIgnoreCase("View Details"))
						{
							CopyShiftWebElement = ChildTag;
							blnFlag=true;
							break;
						}
					}
					
					JavascriptExecutor javaExecuter = (JavascriptExecutor) webdriver;
					javaExecuter.executeScript("arguments[0].click();", CopyShiftWebElement);	
					blnFlag=true;
				}
			}
			catch(Exception e)
			{
				TestUiLogger.error("Break Law verification", "Unable to click on Add Shift button-' "+e.getMessage()+" '");
			}
			
		
			return blnFlag;
		}
		
		/***************************************************************************************************************
		* Project - McDonalds UKIT Automation eggPlant Methods
		* Method Name – clickToAddShift
		* Method Description - This method will click on the add shift button in fixed shifts
		* Return Type - boolean value
		* Framework - UKIT Master Framework
		* Author - Shruti Chavan
		* Creation Date - 08/02/2017
		* Modification History: 
		 * # <Date>     <Who>                  <Mod description>
		 * @throws InterruptedException 

		***************************************************************************************************************/	
		public static boolean clickToAddShift()
		{
			boolean blnFlag=false;
			List<WebElement> webElementList= new ArrayList<WebElement>();
			WebElement addShiftForEmployee = null;
		

			//click on add shift
			WebElement DropDownMenu = webUIDriver.getElementByXpath("FixedShifts_Shifts_dropdownMenu");
			
			List<WebElement> DropDownOptions = DropDownMenu.findElements(By.tagName("a"));
			WebElement addShiftWebElement = null;
			blnFlag = false;
			
			for(WebElement DropDownOption : DropDownOptions)
			{
				WebElement ChildTag = DropDownOption.findElement(By.tagName("font"));
				String strOptionName = ChildTag.getText();
				String [] strArrOptionName = strOptionName.split("\n");
				if(strArrOptionName[0].equalsIgnoreCase("Add Shift"))
				{
					addShiftWebElement = ChildTag;
					blnFlag=true;
					break;
				}
			}
			
			blnFlag=webUIDriver.clickUsingJavaScriptExecuter(addShiftWebElement);
	
			return blnFlag;
		}
		
		/***************************************************************************************************************
		* Project - McDonalds UKIT Automation eggPlant Methods
		* Method Name – addFixedShiftDetails
		* Method Description - This method will add the shift details provided in shift details window and return the
								text present on alert box.
		* Return Type - String value
		* Framework - UKIT Master Framework
		* Author - Shruti Chavan
		* Creation Date - 08/02/2017
		* Modification History: 
		 * # <Date>     <Who>                  <Mod description>
		 * @throws InterruptedException 

		***************************************************************************************************************/
		public static String addFixedShiftDetails(Map testDataSet,Map shiftDetails) throws InterruptedException
		{
			String strAlertBoxText="";
			int counter;
			boolean blnFlag=false;
			element=null;
			List<WebElement> webElementList= new ArrayList<WebElement>(); 
			double doubleFirstBreakStartTime = 0,doubleShiftLength = 0,doubleBreakStartWindow,doubleFirstBrkLength = 0,doubleSecondBrkLgth;
			String strStartTime="",strShiftLength="",strFirstBreakStartTime="",firstBreakLength="",strIsvalidRule="",strShiftDurationBeforeFirstBrk="",
					strDurationAfterFirstBreak="",strDurationAfterSecondBreak="",strVerificationMsg="",strSecondBreakLength="";
			
			try
			{
				//Fetch the area and section from testDataSet data Map
				String strWorkArea=(String)testDataSet.get("Work Area");   //This can be kept in add shift iteration data sheet
				String strWorkStation=(String)testDataSet.get("Station");	//This can be kept in add shift iteration data sheet
				
				//Fetching the values from the excel
				strStartTime=(String) shiftDetails.get("Shift Start Time"); //From time 8:00
				strShiftLength=(String) shiftDetails.get("Shift Length");  //Total shift duration
				strFirstBreakStartTime=(String) shiftDetails.get("Break Start Time"); //Break start time
				firstBreakLength=(String) shiftDetails.get("First Break Length"); //Duration of first break
				strSecondBreakLength=(String) shiftDetails.get("Second Break Length"); //Duration of second break
				strDurationAfterFirstBreak=(String) shiftDetails.get("Duration after first break"); //Duration after first break e.g 2:15
				strDurationAfterSecondBreak=(String) shiftDetails.get("Duration after second break"); //Duration after second break e.g 2:30
				strIsvalidRule=(String) shiftDetails.get("Is valid Rule");
				strVerificationMsg=(String) shiftDetails.get("Alert box message");
	
				//Split break time in hours and minutes (e.g 00:45 = 00 Hrs 45 Mins) to get the duration of shift hrs before break
				String[] breakTime=strFirstBreakStartTime.split(":");
				strShiftDurationBeforeFirstBrk=breakTime[0]+" Hrs "+breakTime[1]+" Mins ";
			
				//get break duration 
				String[] breakLength=firstBreakLength.split(":");
				String strFirstBreakLength=breakLength[0]+" Hrs "+breakLength[1]+" Mins ";
				
				//get duration after first break
				String[] afterBreakTime=strDurationAfterFirstBreak.split(":");
				if(afterBreakTime[0].length()==1)
					afterBreakTime[0]="0"+afterBreakTime[0];
				if(afterBreakTime[1].length()==1)
					afterBreakTime[1]=afterBreakTime[1]+"0";
					
				strDurationAfterFirstBreak=afterBreakTime[0]+" Hrs "+afterBreakTime[1]+" Mins ";
				
				//get duration after second break
				String[] afterSecondBreakTime=strDurationAfterSecondBreak.split(":");
				if(afterSecondBreakTime[0].length()==1)
					afterSecondBreakTime[0]="0"+afterSecondBreakTime[0];
				if(afterSecondBreakTime[1].length()==1)
					afterSecondBreakTime[1]=afterSecondBreakTime[1]+"0";
					
				strDurationAfterSecondBreak=afterSecondBreakTime[0]+" Hrs "+afterSecondBreakTime[1]+" Mins ";
				
				/*//get duration after first break
				String strTime=additionOfTime(strFirstBreakStartTime,firstBreakLength);
				System.out.println(strTime);
				
				//strDurationAfterBreak=calDurationAfterBreak(time, doubleShiftLength);
				strDurationAfterBreak=calDurationAfterBreak(strTime, strShiftLength);
				System.out.println(strDurationAfterBreak);
				*/
				
				//Select the shift start time (from time)
				blnFlag=selectFromDropDownList("FixedShifts_addShiftDetails_FromTime",strStartTime); 
				
				//Select the shift duration before first break
				if(blnFlag)
				{
					blnFlag=selectFromDropDownList("FixedShifts_addShiftDetails_DurationBeforeFirstBrk",strShiftDurationBeforeFirstBrk); 
				}
				
				//Select area and station before first break
				if(blnFlag)  
				{
					blnFlag=selectFromDropDownList("FixedShifts_addShiftDetails_AreaBeforeFirstBrk",strWorkArea);  //select Area	
					blnFlag=selectFromDropDownList("FixedShifts_addShiftDetails_SectionBeforeFirstBrk",strWorkStation); //select station
				}
				
				//select first break duration
				if(blnFlag)  
				{
					blnFlag=selectFromDropDownList("FixedShifts_addShiftDetails_FirstBreakDuration",strFirstBreakLength);
				}
			
				//select area-break
				if(blnFlag) 
				{
					blnFlag=selectFromDropDownList("FixedShifts_addShiftDetails_FirstBreakArea","Break");	
				}
			
				//Select shift duration after first break
				if(blnFlag)  
				{
					blnFlag=selectFromDropDownList("FixedShifts_addShiftDetails_DurationAfterFirstBrk",strDurationAfterFirstBreak);
				}
			
				//select Area & Section after first break
				if(blnFlag)  
				{
					blnFlag=selectFromDropDownList("FixedShifts_addShiftDetails_AreaAfterFirstBrk",strWorkArea);  //Select area
					blnFlag=selectFromDropDownList("FixedShifts_addShiftDetails_SectionAfterFirstBrk",strWorkStation);  //Select station
				}		
				
				//if shift length >= 10 hrs then 2 breaks are to be given
				if(doubleShiftLength>=10.00 && strSecondBreakLength!="00:00")  
				{
					//select second break duration
					if(blnFlag)  
					{
						blnFlag=selectFromDropDownList("FixedShifts_addShiftDetails_SecondBreakDuration",strFirstBreakLength);
					}
				
					//select second break area
					if(blnFlag) 
					{
						blnFlag=selectFromDropDownList("FixedShifts_addShiftDetails_SecondBreakArea","Break");	
					}
				
					//Select shift duration after first break
					if(blnFlag)  
					{
						blnFlag=selectFromDropDownList("FixedShifts_addShiftDetails_DurationAfterSecondBrk",strDurationAfterSecondBreak);
					}
				
					//select Area & Section after first break
					if(blnFlag)  
					{
						blnFlag=selectFromDropDownList("FixedShifts_addShiftDetails_AreaAfterSecondBrk",strWorkArea);  //Select area
						blnFlag=selectFromDropDownList("FixedShifts_addShiftDetails_SectionAfterSecondBrk",strWorkStation);  //Select station
					}		
				}
				
				//Click on save button
				if(blnFlag)
				{
					counter=1;
					do
					{
						element=webUIDriver.getElementByXpath("FixedShifts_addShiftDetails_SaveButton");
					}
					while(element==null && counter<=20);
					
					blnFlag=webUIDriver.clickUsingJavaScriptExecuter(element);
				}
				else
				{
					TestUiLogger.error("TESTCASE", "Unable to click on Save button of fixed shift details window");
				}
				
				//Read the text present on alert box to check if shift is added
				if(blnFlag) 
				{
					try
					{
						strAlertBoxText=webUIDriver.getTextOnAlertBox();
					}
					catch(Exception e)
					{
						TestUiLogger.error("TESTCASE", "Unable to read the alert box content -'"+e.getMessage()+" '");
					}
				}
			}
			catch(Exception e)
			{
				strAlertBoxText="";
			}
			return strAlertBoxText;
		}
		
		//selecting duration ,area and section
		public static boolean selectFromDropDownList(String strObjectName, String strValue)
		{
			boolean blnFlag=false;
			
			element=webUIDriver.locateElement(strObjectName);
			if(element.isDisplayed())
			{
				blnFlag=webUIDriver.selectFromDropDownListByVisibleText(element, strValue);
			}
			if(!blnFlag)
			{
				TestUiLogger.error("TESTCASE", "Unable to select "+strObjectName);
			}
				
			return blnFlag;
		}
		
		/***************************************************************************************************************
		* Project - McDonalds UKIT Automation eggPlant Methods
		* Method Name – DeleteShift
		* Method Description - This method will delete the shift in 'Shifts' rail track and also confirm if the deleted shift is displayed on the screen.
		* Return Type - Boolean value
		* Parameters - 
		* Framework - UKIT Master Framework
		* Author - Shikha Nagar
		* Creation Date - 15/02/2017
		* Modification History: 
		* # <Date>     <Who>                  <Mod description>
		 * @throws TimeoutException 
		
		***************************************************************************************************************/
		public static boolean clickToDeleteFixedShift() throws TimeoutException
		{
				boolean blnFlag=false;
				
				WebElement DropDownMenu = webUIDriver.getElementByXpath("FixedShifts_Shifts_dropdownMenu");
				if(DropDownMenu != null)
				{
					List<WebElement> DropDownOptions = DropDownMenu.findElements(By.tagName("a"));
					WebElement deleteShiftWebElement = null;
					blnFlag = false;
					
					for(WebElement DropDownOption : DropDownOptions)
					{
						WebElement ChildTag = DropDownOption.findElement(By.tagName("font"));
						String strOptionName = ChildTag.getText();
						String [] strArrOptionName = strOptionName.split("\n");
						if(strArrOptionName[0].equalsIgnoreCase("Delete Shift"))
						{
							deleteShiftWebElement = ChildTag;
							blnFlag=true;
							webUIDriver.clickUsingJavaScriptExecuter(deleteShiftWebElement);
							break;
						}
					}
				}
				
				Alert alert=webdriver.switchTo().alert();
				String alertText=alert.getText();
				if(alertText.contains("Are you sure you want to delete shift?"))
				{
					alert.accept();	
					String alertBoxText=webUIDriver.getTextOnAlertBox();
					if(alertBoxText.endsWith("Shift Deleted Successfully"))
						blnFlag=true;
					else
						blnFlag=false;
				}
				else
				{
					alert.dismiss();
					blnFlag=false;
				}
				return blnFlag;
		}

		/***************************************************************************************************************
		* Project - McDonalds UKIT Automation Selenium Methods
		* Method Name – SelectParticular_Emp
		* Method Description - This method will select particular day for employee to carry out various shift related operations.
		* Return Type - Boolean value
		* Parameters - strEmployee,strWeekDay,tableDetails(this contains names of employee and days)
		* Framework - UKIT Master Framework
		* Author - Shikha Nagar
		* Creation Date - 08/02/2017
		* Modification History: 
		* # <Date>     <Who>                  <Mod description>

		***************************************************************************************************************/
		public static boolean employeeAndDaySelection(String strEmployeeName,String strDay,List<Map<String,WebElement>> tableDetails)
		{
			boolean blnFlag=false;
			WebElement element = null;
			List<WebElement> webElementList= new ArrayList<WebElement>();
			
			for(Map TableRow : tableDetails)
			{
				WebElement ColumnData =(WebElement)TableRow.get("Template");
				String strColumnData = ColumnData.getText();
				strColumnData = strColumnData.trim();
				
				//locating fromEmployee
				if(strColumnData.equalsIgnoreCase(strEmployeeName))
				{
					element = (WebElement) TableRow.get(strDay);
					webElementList=element.findElements(By.tagName("a"));
					for(WebElement e:webElementList)
					{
						element=e;
						System.out.println(e);
						break;
					}
					break;
				}	
			}
			
			//Click on the particular day 
			if(element.isDisplayed())
			{
				blnFlag=webUIDriver.clickUsingJavaScriptExecuter(element);
				if(blnFlag)
					blnFlag=webUIDriver.clickUsingJavaScriptExecuter(element);
			}
			return blnFlag;
		}
		
		//Search restaurant
		public static boolean selectStore(String strStoreNumber) throws InterruptedException
		{
			Boolean blnFlag = false;
			List<WebElement> webElementList= new ArrayList<WebElement>();  
		
			//Click on McDonaldsCorp
			//blnFlag=webUIDriver.locateElementAndClick();
			WebElement element = webUIDriver.getElementByXpath("MySchedule_McDonalds (CORP)");
			blnFlag=webUIDriver.clickWebElement(element);
			//blnFlag = webUIDriver.clickByPartialLinkText("McDonalds (CORP)");
			if(blnFlag)
			{
				webUIDriver.waitImplicitly(2);
				//Enter restaurant number in the search box
				blnFlag=webUIDriver.locateElementAndSendKeys("SelectStore_searchRestaurant", strStoreNumber);
				if(blnFlag)
				{
					//Click on GO button
					blnFlag=webUIDriver.locateElementAndClick("SelectStore_goButton");
					if(blnFlag)
					{
						Thread.sleep(2000);
						//Select the desired store number and click on it
						webElementList=webUIDriver.findElementsByTagname("a");
						element=webUIDriver.getElementContainingRequiredText(webElementList,strStoreNumber);
						if(element != null)
						{
							webUIDriver.clickWebElement(element);
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
		
		//module selector
				public static boolean selectModule(String ModuleName)
				{
					boolean blnFlag = false;
					WebElement element = null;
					try
					{
						if(ModuleName.equalsIgnoreCase("schedule"))
						{
							element = webUIDriver.getElementByXpath("ModuleSelection_ScheduleImage");
						}
						else if(ModuleName.equalsIgnoreCase("clocks"))
						{
							element = webUIDriver.getElementByXpath("ModuleSelection_ClocksImage");
						}
						
						blnFlag = webUIDriver.clickOnImage(element);
						if(blnFlag)
						{
							return true;
						}
						else
						{
							return false;
						}
						
					}
					catch(Exception e)
					{return false;}
		
				}
				
				// launching main menu 
				public static boolean launchMainMenu(String MenuName)
				{
					boolean blnFlag = false;
					WebElement Element = null;
					try
					{
						MenuName = MenuName.trim();
						String MainMenuXpath = webUIDriver.getWebelementProperty("xpath", "MySchedule_mainMenu");
						webUIDriver.waitExplicitlyByXpath(MainMenuXpath, 5);
						WebElement MainMenu = webUIDriver.getElementByXpath("MySchedule_mainMenu");
						List<WebElement> MainMenuNames = MainMenu.findElements(By.tagName("td"));
						for(WebElement MenuNames : MainMenuNames)
						{
							String CurrentMenuName = MenuNames.getText();
							if(CurrentMenuName.length()>1)
							{
								CurrentMenuName = CurrentMenuName.trim();
								if(CurrentMenuName.equalsIgnoreCase(MenuName))
								{
									Element = MenuNames;
									break;
								}
							}
							
						}
						
						if(Element != null)
						{
							blnFlag = webUIDriver.clickWebElement(Element);
							if(blnFlag)
							{
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
						return false;
					}
					catch(Exception e)
					{
						return false;
					}
					return false;
				}
		
		//copyOrMOve shift
				public static boolean copyOrMoveShift(String FromEmployee,String ToEmployee,String FromDay, String ToDay, String OfWeek , String ActionName)
				{
					boolean blnFlag = false;
					boolean blnReturnValue = false;
					try{
						
						//week selection 
						String WeeksId = webUIDriver.getWebelementProperty("id", "FixedShifts_WeekTable_weeks");
						List<WebElement> WeeksList = webUIDriver.findElementsById(WeeksId);
						
						// looping over WeeksList to click on expected week(OfWeek)
						for(WebElement WebElementWeek : WeeksList )
						{
							String WeekDetails = WebElementWeek.getText();
							if(WeekDetails!=null)
							{
								WeekDetails=WeekDetails.trim();
								String ExpectedWeek=OfWeek.trim();
								if(WeekDetails.equalsIgnoreCase(ExpectedWeek))
								{
									boolean blnStatus = webUIDriver.clickWebElement(WebElementWeek);
									if(blnStatus)
									{
										blnStatus=webUIDriver.waitImplicitly(2);
										if(blnStatus)
										{
											blnFlag=true;
											break;
										}
										else
										{
											blnFlag=false;
											break;
										}
									}
									else
									{
										// OfWeek WebElement not found on webpage
										return false;
									}
									
								}
							}
						}
						
						
						// after week selection click on edit button
						if(blnFlag)
						{ 
							blnFlag = webUIDriver.waitExplicitlyByXpath("FixedShifts_WeekTable_EditButton", 6000);
							if(blnFlag)
							{System.out.println("HERE");
								blnFlag = webUIDriver.locateElementAndClick("FixedShifts_WeekTable_EditButton");
								if(blnFlag)
								{
									System.out.println("Hi");
									//handling alert-ok
									blnFlag = webUIDriver.handleAlert("ok");
									if(blnFlag)
									{
										blnFlag = webUIDriver.waitExplicitlyByXpath("FixedShifts_Header", 6000);
										
										// verifying requied webpage is present or not
										if(blnFlag)
										{
											blnFlag = webUIDriver.waitExplicitlyByXpath("FixedShifts_Header", 6000);
											//after edit button getting fixed shift table in list of map format
											blnFlag = webUIDriver.isDisplayedByXpath("FixedShifts_Shift_Inner_Table");
											
											if(blnFlag)
											{
												webUIDriver.waitExplicitlyByXpath("FixedShifts_Shift_Inner_Table", 6000);
												List<Map<String,WebElement>> TableDeatils = getScheduledShifts("FixedShifts_Shift_Inner_Table");
												
												//local variables
												WebElement FromShift = null;
												WebElement ToShift = null;
												FromEmployee = FromEmployee.trim();
												ToEmployee = ToEmployee.trim();
												boolean FoundFromEmployee = false;
												boolean FoundToEmployee = false;
												
												// looping over above table to get 'from' and 'to'  webelements
												for(Map TableRow : TableDeatils)
												{
													WebElement ColumnData =(WebElement)TableRow.get("Template");
													String strColumnData = ColumnData.getText();
													strColumnData = strColumnData.trim();
													
													//locating fromEmployee
													if(strColumnData.equalsIgnoreCase(FromEmployee))
													{
														FromShift = (WebElement) TableRow.get(FromDay);
														FoundFromEmployee=true;
														
													}
													else if(strColumnData.equalsIgnoreCase(ToEmployee))
													{
														ToShift = (WebElement)TableRow.get(ToDay);
														FoundToEmployee=true;	
													}
													
													//breaking loop after locating both employees
													if(FoundToEmployee && FoundFromEmployee)
													{
														blnFlag = true;
														break;
													}
													else
													{
														blnFlag=false;
													}
												}
												
												//copying Fromshift  and pasting it into ToShift only after successfully locating it
												if(blnFlag)
												{
													//click on shift using local function due to additional tags
													blnFlag = ReusableComponents.clickOnShift(FromShift);
													if(blnFlag)
													{
														
														WebElement DropDownMenu = webUIDriver.getElementByXpath("FixedShifts_Shifts_dropdownMenu");
														if(DropDownMenu != null)
														{
															List<WebElement> DropDownOptions = DropDownMenu.findElements(By.tagName("a"));
															WebElement ShiftWebElement = null;
															blnFlag = false;
															
															for(WebElement DropDownOption : DropDownOptions)
															{
																WebElement ChildTag = DropDownOption.findElement(By.tagName("font"));
																String strOptionName = ChildTag.getText();
																String [] strArrOptionName = strOptionName.split("\n");
																if(strArrOptionName[0].equalsIgnoreCase(ActionName.trim()))
																{
																	ShiftWebElement = ChildTag;
																	blnFlag=true;
																	break;
																}
															}
															
															if(blnFlag)
															{
																blnFlag = webUIDriver.clickWebElement(ShiftWebElement);
																if(blnFlag)
																{
																	blnFlag = webUIDriver.waitImplicitly(2);
																	if(blnFlag)
																	{
																		blnFlag = webUIDriver.switchToNewWindow();
																		if(blnFlag)
																		{
																			if(ActionName.equalsIgnoreCase("COPY SHIFT"))
																			{
																				blnFlag = performActionOnShift("copy", ToEmployee, ToDay);
																			}
																			else if(ActionName.equalsIgnoreCase("MOVE SHIFT"))
																			{
																				blnFlag = performActionOnShift("move", ToEmployee, ToDay);
																			}
																			
																			if(blnFlag)
																			{
																				return true;
																			}
																			else
																			{
																				// log error unable to copy/move
																				return false;
																			}
																		}
																		else
																		{
																			//log fail unable to switch to new window
																			return false;
																		}
																	}
																	else
																	{
																		//wait failed 
																		return false;
																	}
																}
																
																else
																{
																	//log failure CopyShiftWebElement is not present
																	return false;
																}
															}
															else
															{
																//log failure copy sift button not found
																return false;
															}
														}
														else
														{
															// dropdown webelement is not present on webpage
															return false;
														}
													}
													else
													{
														// log fail unable to click on shift assigned
														return false;
													}
												}
												else
												{
													return false;
												}

											}
											else
											{
												//table is not displayed on webpage
												return false;
											}
											
										}
										else
										{
											//not a schedule webpage 
											return false;
										}
									}
									else
									{
										// alert handling failed
										return false;
									}
								}
								else
								{
									//edit button not found
									return false;
								}
							}
							else
							{
								//edit button is not present on webpage
								return false;
							}
						}
						else
						{
							// wait fails
							return false;
						}	
					}
					catch(WebDriverException e)
					{
						return false;
					}
					catch(Exception e)
					{
						return false;
					}
				}
		
		// getting scheduled shifts
				public static List<Map<String,WebElement>> getScheduledShifts(String TablePropertyName)
				{
					List<Map<String,WebElement>> RowWiseTableData=new ArrayList<Map<String,WebElement>>();
					try
					{
						TablePropertyName = TablePropertyName.trim();
						String TableId = webUIDriver.getWebelementProperty("id",TablePropertyName);

						String TableDynamicXpath="//*[@id='"+TableId+"']/table/tbody";

						webUIDriver.waitExplicitlyByXpath(TablePropertyName, 10);
						
						WebElement element = webUIDriver.getWebElementWithXpath(TableDynamicXpath);

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
				
				
				//clicking on shifts
				public static boolean clickOnShift(WebElement ShiftElement)
				{
					try
					{
						WebElement Element = ShiftElement.findElement(By.tagName("a"));
						//Element.click();
						JavascriptExecutor executor = (JavascriptExecutor) WebUIDriver.driver;
						executor.executeScript("arguments[0].click();", Element);
						return true;
					}
					catch(WebDriverException e)
					{
						return false;
					}
					catch(Exception e)
					{
						return true;
					}
				}
		
				public static boolean performActionOnShift(String ActionName, String ToEmployee , String ToDay)
				{
					String Message = "";
					String EmployeeListXpath = "";
					boolean blnStatus = false;
					Select SelectList = null;
					
					try{
						ActionName = ActionName.trim();
						ToEmployee = ToEmployee.trim();
						ToDay = ToDay.trim();
						
						if (ActionName.equalsIgnoreCase("copy") || ActionName.equalsIgnoreCase("move")) {
							EmployeeListXpath = webUIDriver.getWebelementProperty("xpath",
									"FixedShifts_ActionToEmployee_SelectList");
							if (EmployeeListXpath != "" || EmployeeListXpath != null) {
								// selecting the employee
								WebElement SelectListWebELement = webUIDriver.getElementByXpath("FixedShifts_ActionToEmployee_SelectList");
								SelectList = new Select(SelectListWebELement);
								SelectList.selectByVisibleText(ToEmployee);
								if(SelectListWebELement != null)
								{
									blnStatus=true;
								}
								
								if (blnStatus) {
									// check boxes for each day
									boolean DayChecked = false;
									if (ToDay.equalsIgnoreCase("MON")) {
										blnStatus = webUIDriver.clickByXpath("FixedShifts_Monday_checkbox");
									} else if (ToDay.equalsIgnoreCase("TUE")) {
										blnStatus = webUIDriver.clickByXpath("FixedShifts_Tuesday_checkbox");
									} else if (ToDay.equalsIgnoreCase("WED")) {
										blnStatus = webUIDriver.clickByXpath("FixedShifts_Wednesday_checkbox");
									} else if (ToDay.equalsIgnoreCase("THU")) {
										blnStatus = webUIDriver.clickByXpath("FixedShifts_Thursday_checkbox");
									} else if (ToDay.equalsIgnoreCase("FRI")) {
										blnStatus = webUIDriver.clickByXpath("FixedShifts_Friday_checkbox");
									} else if (ToDay.equalsIgnoreCase("SAT")) {
										blnStatus = webUIDriver.clickByXpath("FixedShifts_Saturday_checkbox");
									} else if (ToDay.equalsIgnoreCase("SUN")) {
										blnStatus = webUIDriver.clickByXpath("FixedShifts_Sunday_checkbox");
									} else {
										return false;
									}

									// clicking on action specific button (copy/move)
									if (blnStatus) {
										if (ActionName.equalsIgnoreCase("copy")) {
											blnStatus = webUIDriver.clickByXpath("FixedShifts_copyButton");
										} else if (ActionName.equalsIgnoreCase("move")) {
											blnStatus = webUIDriver.clickByXpath("FixedShifts_moveButton");
										}
									}

									// final confimation of action
									if (blnStatus) {
										globalTextOnAlert = webUIDriver.getTextOfAlert();
										return true;
									} else {
										return false;
									}
								} else {
									//log error
									return false;
								}
							}

							else {
								//log error message
								return false;
							} 
						}
						else if(ActionName.equalsIgnoreCase("DELETE"))
						{
							System.out.println("Write code for delete here");
						}
					}
					catch(WebDriverException e)
					{
						//log error message
						return false;		
					}
					catch(Exception e)
					{
						//log error message
						return false;
					}
					return false;
				}
		
				
				//creating keys on table
				//function return keys of schedule web table of shifts
				public static ArrayList<String> creatKeysOfWebTable(WebElement KeyRow)
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
		
		
		
		
}
