package com.cg.Application.WFM;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.text.html.HTML;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import com.cg.Logutils.HtmlResult;
import com.cg.SeleniumTestSetup.TestSetUp;
import com.cg.TestCaseRunner.TestCaseRunner;
import com.cg.UIDriver.WebUIDriver;

public class BusinessComponent_WFM_02 extends TestSetUp
{
	// to store test data
	List<Map<String, String>> mapTestDataList = new ArrayList<Map<String, String>>();
	String strUsername;
	String strPassword;
	String strDescription = "To Move shift";
	
	public void TC_WFM_02(Map<String,String> Input)
	{
		
			try {
				//local variables
				boolean blnFlag=false;

				// to get test row data from dedicated excel file
				String strRowId=(String)Input.get("RowId");
				String fileName="TC_WFM_02";
				String[] ArrRowId=strRowId.split(";");// in case of multiple rows entered, they must separated by " ; " character 
				
				//variables to store data coming from excel file
				String FromEmployee="";
				String ToEmployee="";
				String FromDay="";
				String ToDay="";
				String Week="";
				String ExpectedMessageOnPopup = "";
				String StoreNumber = "";
				boolean Start = false;
				//loop to just store the data from excel file into list of maps(of test data)
				for(String RowId:ArrRowId)
				{
					RowId=RowId.trim();
					Map<String,String> MapRowId = ReusableComponents.fetchTestData(RowId,fileName);
					mapTestDataList.add(MapRowId);
				}
				
				//1 launching browser
				blnFlag=launchApplication("WFM_URL");
				if(blnFlag)
				{
					String strExpectedResult = "Browser should be launched successfully";
					HtmlResult.passed(strDescription, strExpectedResult , "Browser launched successfully");
				}
				else
				{
					String strExpectedResult = "Browser should be launched successfully";
					HtmlResult.failed(strDescription, strExpectedResult  , "failed to launch browser");
					return ;
				}
				
				//2 login to the web application
				blnFlag = ReusableComponents.loginToTheApplication(WfmLoginUserName,WfmLoginPassword);
				if(blnFlag)
				{
					String strExpectedResult = "Login should be suucessful with credentials Username-' "+WfmLoginUserName+" ',Password-' "+WfmLoginPassword+" '";
					HtmlResult.passed(strDescription, strExpectedResult  , "Login successful with credentialsUsername-' "+WfmLoginUserName+" ',Password-' "+WfmLoginPassword+" '");
				}
				else
				{
					String strExpectedResult = "Login should be suucessful with credentials Username-' "+WfmLoginUserName+" ',Password-' "+WfmLoginPassword+" '";
					HtmlResult.failed(strDescription, strExpectedResult  , "Login is not successful with credentialsUsername-' "+WfmLoginUserName+" ',Password-' "+WfmLoginPassword+" '");
					callRecovery("quit browser");return;
				}
				
				//looping over other actions
				for(Map<String,String> TestDataRow : mapTestDataList)
				{
					FromEmployee = TestDataRow.get("FromEmployee").trim();
					ToEmployee = TestDataRow.get("ToEmployee").trim();
					FromDay = TestDataRow.get("FromDay").trim();;
					ToDay = TestDataRow.get("ToDay").trim();;
					ExpectedMessageOnPopup = TestDataRow.get("Expected Message").replaceAll("\n", "").trim();
					Week = TestDataRow.get("Week").trim();;
					StoreNumber = getTestData("Strore Number").trim();
					String strExpectedResult  = "To successfully Move a shift of ' "+Week+","+FromDay+" ', from employee ' "+ToEmployee+" ' to employee ' "+ToEmployee+" ' ";
					
					
					//action not to perform at the start
					while(Start)
					{
						System.out.println("hi");
						//clicking on home button 
						blnFlag = webUIDriver.locateElementAndClick("MySchedule_homeLink");
						if(blnFlag)
						{
							Start = false;
						}
						else
						{
							HtmlResult.failed(strDescription, "Home button should be clicked successful", "Home button is not clicked succesfully "+webUIDriver.ErrorMessage);
							continue;
						}
					}
					
					Start = true;
		
					//webUIDriver.navigateRefresh();
					//3 selecting a store
					blnFlag = ReusableComponents.selectStore(StoreNumber);
					if(blnFlag)
					{
						HtmlResult.passed(strDescription, "Store selection should be successful", "Store-' "+StoreNumber+" ' selected successfully");
					}
					else
					{
						HtmlResult.failed(strDescription, "Store selection should be successful", "Cannot select store-' "+StoreNumber+" ' "+webUIDriver.ErrorMessage);
						continue;
					}
					
					//4 selection of module (schedule or clocks)
					blnFlag = ReusableComponents.selectModule("schedule");
					if(blnFlag)
					{
						HtmlResult.passed(strDescription, "Module selection should be successful", "Module-' Schedule ' selected successfully");
					}
					else
					{
						HtmlResult.failed(strDescription, "Module selection should be successful", "Module-' Schedule ' is not selected successfully "+webUIDriver.ErrorMessage);
						continue;
					}
					
					
					//5 launch main menu
					blnFlag = ReusableComponents.launchMainMenu("schedule");
					if(blnFlag)
					{
						HtmlResult.passed(strDescription, "Menu selection should be successful", "Menu-' Schedule ' selected successfully");
					}
					else
					{
						HtmlResult.failed(strDescription, "Menu selection should be successful", "Menu-' Schedule ' is not selected successfully "+webUIDriver.ErrorMessage);
						continue;
					}
					
					//6 checking ui identifier before copying
					blnFlag=webUIDriver.blnlocateElement("FixedShifts_Header");
					if(blnFlag)
					{
						blnFlag = true;
					}
					else
					{
						HtmlResult.failed(strDescription, "Fixed shifts header should be present", "Fixed shift header is not present "+webUIDriver.ErrorMessage);
						continue;
					}
					
					//7 copying
					blnFlag = ReusableComponents.copyOrMoveShift(FromEmployee, ToEmployee, FromDay, ToDay, Week, "Move Shift");
					if(blnFlag)
					{
						HtmlResult.passed(strDescription, "Move Shift should be successful", "Move Shift button has been clicked successfully");
						
					}
					else
					{
						HtmlResult.failed(strDescription, "Move Shift should be successful", "Move Shift button has not been clicked successfully "+webUIDriver.ErrorMessage);
						continue;
					}
					
					// checking if expected message on alert is "" or it has some value
					
					if(ExpectedMessageOnPopup != "" || ExpectedMessageOnPopup.length()>0)// if ExpectedMessageOnPopup has some value means tester expecting a popup window to be present
					{
						String ActualMessageOnPopup = webUIDriver.getTextOfAlert();
						
						if(ExpectedMessageOnPopup.equalsIgnoreCase(ActualMessageOnPopup))
						{
							HtmlResult.passed(strDescription, "Alert should display message-' "+ExpectedMessageOnPopup+" '"
									, "Actual message on alert-' "+ActualMessageOnPopup+" '");
						}
						else
						{
							HtmlResult.failed(strDescription, "Alert should display message-' "+ExpectedMessageOnPopup+" '"
									, "Actual message on alert-' "+ActualMessageOnPopup+" ' "+webUIDriver.ErrorMessage);
						}
						
						Thread.sleep(3000);
						//9 handling alert
						blnFlag=webUIDriver.handleAlert("ok");
						if(blnFlag)
						{
							blnFlag = verifyMoveAction(FromEmployee, ToEmployee, FromDay, ToDay);
							if(blnFlag)
							{
								HtmlResult.passed(strDescription, "Move Shift should be successful", "Move action has been verified and performed successfully");
							}
							else
							{
								HtmlResult.failed(strDescription, "Move Shift should be successful", "Move action has not been verified and performed successfully "+webUIDriver.ErrorMessage);
							}
						}
						else
						{
							HtmlResult.failed(strDescription, "Alert should be handled", "Unable to handle alert "+webUIDriver.ErrorMessage);
						}

					}
					else //if ExpectedMessageOnPopup has no value means tester is not expecting a popup window to be present
					{
						blnFlag = verifyMoveAction(FromEmployee, ToEmployee, FromDay, ToDay);
						if(blnFlag)
						{
							HtmlResult.passed(strDescription, "Move Shift should be successful", "Copy action has been verified and performed successfully");
						}
						else
						{
							HtmlResult.failed(strDescription, "Move Shift should be successful", "Copy action has not been verified and performed successfully "+webUIDriver.ErrorMessage);
						}
					}

				}
				
				// clicking on confirm button
				blnFlag = webUIDriver.waitExplicitlyByXpath("FixedShifts_confirmButton", 5);
				if(blnFlag)
				{
					blnFlag = webUIDriver.locateElementAndClick("FixedShifts_confirmButton");
				}
				else
				{
					HtmlResult.failed(strDescription, "Confirm button should get located and clicked", "'Confirm' button not clicked successfully "+webUIDriver.ErrorMessage);
					callRecovery("quit browser");return;
				}
				
				if(blnFlag)
				{
					HtmlResult.passed(strDescription, "Confirm button should get located and clicked", "Confirm button has been clicked successfully");
				}
				else
				{
					HtmlResult.failed(strDescription, "Confirm button should get located and clicked", "'Confirm' button not clicked successfully "+webUIDriver.ErrorMessage);
					callRecovery("quit browser");return;
				}
				
				//handling alert
				Thread.sleep(3000);
				webUIDriver.handleAlert("ok");
				blnFlag = webUIDriver.blnlocateElement("FixedShifts_confirmShift_saveButton");
				
				if(blnFlag)
				{
					HtmlResult.passed(strDescription, "Alert should be handled", "Alert handled successfully");
				}
				else
				{
					HtmlResult.passed(strDescription, "Alert should be handled", "Alert is not handled successfully "+webUIDriver.ErrorMessage);
					callRecovery("quit browser");return;
				}
				

				//clicking on save button
				blnFlag = webUIDriver.waitExplicitlyByXpath("FixedShifts_confirmShift_saveButton", 5);
				if(blnFlag)
				{
					blnFlag = webUIDriver.locateElementAndClick("FixedShifts_confirmShift_saveButton");
				}
				else
				{
					HtmlResult.failed(strDescription, "Save button should get located and clicked", "'Confirm' button not clicked successfully "+webUIDriver.ErrorMessage);
					callRecovery("quit browser");return;
				}
				
				if(blnFlag)
				{
					HtmlResult.passed(strDescription, "Save button should get located and clicked", "Confirm button has been clicked successfully");
				}
				else
				{
					HtmlResult.failed(strDescription, "Save button should get located and clicked", "'Confirm' button not clicked successfully "+webUIDriver.ErrorMessage);
					callRecovery("quit browser");return;
				}
						
		} 
		catch (Exception e) {
			
			HtmlResult.failed(strDescription, "Move shift should be successful", "Unable to launch WFM website "+e.getMessage()+" "+webUIDriver.ErrorMessage);
		}
	}
	
	
	
	
	public void callRecovery(String RecoveryAction)
	{
		try
		{
			RecoveryAction = RecoveryAction.trim();
			if(RecoveryAction.equalsIgnoreCase("quit browser"))
			{
				webUIDriver.quit();
			}
			else if(RecoveryAction.equals("Launch Home screen"))
			{
				webUIDriver.locateElementAndClick("MySchedule_homeLink");
			}
		}
		catch(Exception e)
		{
			HtmlResult.failed("To run recovery scenario ' "+RecoveryAction+" '", "Recovery should be successful", "Recovery failed "+e.getMessage()+" "+webUIDriver.ErrorMessage);

		}
		
		
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	public boolean verifyMoveAction(String FromEmployee, String ToEmployee ,String FromDay, String ToDay)
	{
		List<Map<String,WebElement>> TableDeatils = ReusableComponents.getScheduledShifts("FixedShifts_Shift_Inner_Table");

		String strFromEmployeeShift = "";
		String strToEmployeeShift = "";
		WebElement FromEmployeeShift =null;
		WebElement ToEmployeeShift = null;
		FromEmployee = FromEmployee.trim();
		ToEmployee = ToEmployee.trim();
		boolean FoundFromEmployee = false;
		boolean FoundToEmployee = false;

		// looping over above table to get 'from' and 'to'  webelements
		for(Map<String,WebElement> TableRow : TableDeatils)
		{
			WebElement ColumnData =(WebElement)TableRow.get("Template");
			String strColumnData = ColumnData.getText();
			strColumnData = strColumnData.trim();

			//locating fromEmployee
			if(strColumnData.equalsIgnoreCase(FromEmployee))
			{
				FoundFromEmployee=true;
				FromEmployeeShift = TableRow.get(FromDay);
			}
			else if(strColumnData.equalsIgnoreCase(ToEmployee))
			{
				FoundToEmployee=true;	
				ToEmployeeShift = TableRow.get(ToDay);
			}
			if(FoundToEmployee && FoundFromEmployee )
			{
				break;
			}
		}

		strFromEmployeeShift = FromEmployeeShift.getText().trim();
		strToEmployeeShift = ToEmployeeShift.getText().trim();
		//.cross verification of successfully copied shifts
		System.out.println("Verifying move action.....");
		
		if(strFromEmployeeShift.equals("") && !strToEmployeeShift.equals(""))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	

	public String getTestData(String Key)
	{
		String Value="";
		for(Map DataMap : mapTestDataList)
		{
			if(DataMap.containsKey(Key.trim()))
			{
				Value =(String) DataMap.get(Key);
			}
		}
		
		return Value;
	}
	
	
	
	
	
	
	
	
	
	
}
