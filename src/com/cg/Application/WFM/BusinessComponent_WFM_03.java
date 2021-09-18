package com.cg.Application.WFM;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import com.cg.Logutils.HtmlResult;
import com.cg.SeleniumTestSetup.TestSetUp;

public class BusinessComponent_WFM_03 extends TestSetUp
{

	// to store test data
	List<Map<String, String>> mapTestDataList = new ArrayList<Map<String, String>>();
	String strUsername;
	String strPassword;
	String strDescription = "To Move a Shift";
	public void TC_WFM_03(Map<String,String> Input)
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
				callRecovery();return;
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
				boolean Start = false;
				
				//action not to perform at the start
				while(Start)
				{
					//clicking on home button 
					blnFlag = webUIDriver.locateElementAndClick("MySchedule_homeButtonImage");
					if(blnFlag)
					{
						Start = true;
					}
					else
					{
						HtmlResult.failed(strDescription, "Home button should be clicked successful", "Home button is not clicked succesfully "+webUIDriver.ErrorMessage);
						continue;
					}
				}
				
	
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
				blnFlag = ReusableComponents.copyOrMoveShift(FromEmployee, ToEmployee, FromDay, ToDay, Week, "Copy Shift");
				if(blnFlag)
				{
					HtmlResult.passed(strDescription, "Copy shift should be successful", "Copy shift button has been clicked successfully");
					
				}
				else
				{
					HtmlResult.failed(strDescription, "Copy shift should be successful", "Copy shift button has not been clicked successfully "+webUIDriver.ErrorMessage);
					continue;
				}
				
				// checking if expected message on alert is "" or it has some value
				
				if(ExpectedMessageOnPopup != "" || ExpectedMessageOnPopup.length()>0)// if ExpectedMessageOnPopup has some value means tester expecting a popup window to be present
				{
					String ActualMessageOnPopup = webUIDriver.getTextOfAlert();
					
					Thread.sleep(3000);
					//9 handling alert
					blnFlag=webUIDriver.handleAlert("ok");
					if(blnFlag)
					{
						blnFlag = verifyCopyAction(FromEmployee, ToEmployee, FromDay, ToDay);
						if(blnFlag)
						{
							HtmlResult.passed(strDescription, "Copy shift should be successful", "Copy action has been verified and performed successfully");
						}
						else
						{
							HtmlResult.failed(strDescription, "Copy shift should be successful", "Copy action has not been verified and performed successfully "+webUIDriver.ErrorMessage);
						}
					}
					else
					{
						HtmlResult.failed(strDescription, "Alert should be handled", "Unable to handle alert "+webUIDriver.ErrorMessage);
					}

				}
				else //if ExpectedMessageOnPopup has no value means tester is not expecting a popup window to be present
				{
					blnFlag = verifyCopyAction(FromEmployee, ToEmployee, FromDay, ToDay);
					if(blnFlag)
					{
						HtmlResult.passed(strDescription, "Copy shift should be successful", "Copy action has been verified and performed successfully");
					}
					else
					{
						HtmlResult.failed(strDescription, "Copy shift should be successful", "Copy action has not been verified and performed successfully "+webUIDriver.ErrorMessage);
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
				callRecovery();return;
			}
			
			if(blnFlag)
			{
				HtmlResult.passed(strDescription, "Confirm button should get located and clicked", "Confirm button has been clicked successfully");
			}
			else
			{
				HtmlResult.failed(strDescription, "Confirm button should get located and clicked", "'Confirm' button not clicked successfully "+webUIDriver.ErrorMessage);
				callRecovery();return;
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
				callRecovery();return;
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
				callRecovery();return;
			}
			
			if(blnFlag)
			{
				HtmlResult.passed(strDescription, "Save button should get located and clicked", "Confirm button has been clicked successfully");
			}
			else
			{
				HtmlResult.failed(strDescription, "Save button should get located and clicked", "'Confirm' button not clicked successfully "+webUIDriver.ErrorMessage);
				callRecovery();return;
			}
			
			
			
			
			
			
			
			
			
//			blnFlag = webUIDriver.waitExplicitlyByXpath("FixedShifts_confirmShift_saveButton", 5);
//			if(blnFlag)
//			{
//				HtmlResult.passed(strDescription, "Save button should be clicked successfully", "Save button clicked successfully");
//			}
//			else
//			{
//				HtmlResult.failed(strDescription, "Save button should be clicked successfully", "'Save' button not clicked successfully");
//				callRecovery();return;
//			}
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
//			//actions are starting from here
//			for(Map<String,String> TestDataRow : mapTestDataList)
//			{
//				FromEmployee = TestDataRow.get("FromEmployee").trim();
//				ToEmployee = TestDataRow.get("ToEmployee").trim();
//				FromDay = TestDataRow.get("FromDay").trim();;
//				ToDay = TestDataRow.get("ToDay").trim();;
//				ExpectedMessageOnPopup = TestDataRow.get("Expected Message").replaceAll("\n", "").trim();
//				Week = TestDataRow.get("Week").trim();;
//				StoreNumber = getTestData("Strore Number").trim();
//				String strExpectedResult  = "To successfully Move a shift of ' "+Week+","+FromDay+" ', from employee ' "+ToEmployee+" ' to employee ' "+ToEmployee+" ' ";
//				
//				
//				
//				
//				
//				//launch the application *remove after completion
//				blnFlag=launchApplication("WFM_URL");
//				
//				if(blnFlag)
//				{
//					blnFlag = ReusableComponents.loginToTheApplication(WfmLoginUserName,WfmLoginPassword);
//					if(blnFlag)
//					{
//						blnFlag = ReusableComponents.selectStore(StoreNumber);
//						if(blnFlag)
//						{
//							blnFlag = ReusableComponents.selectModule("schedule");
//							if(blnFlag)
//							{
//								blnFlag = ReusableComponents.launchMainMenu("schedule");	
//								if(blnFlag)
//								{  
//									//checking a unique identifier header on webpage
//									blnFlag=webUIDriver.locateElement("FixedShifts_Header");
//									
//									if(blnFlag)
//									{
//										blnFlag = ReusableComponents.copyOrMoveShift(FromEmployee, ToEmployee, FromDay, ToDay, Week, "Copy Shift");
//										if(blnFlag)
//										{
//											if(ExpectedMessageOnPopup != "" || ExpectedMessageOnPopup.length()>0)
//											{
//												String ActualMessageOnPopup = webUIDriver.getTextOfAlert();
//												
//												blnFlag=webUIDriver.handleAlert("ok");
//												if(blnFlag)
//												{
//													if(ExpectedMessageOnPopup.equals(ActualMessageOnPopup))
//													{
//														List<Map<String,WebElement>> TableDeatils = ReusableComponents.getScheduledShifts("FixedShifts_Shift_Inner_Table");
//
//														String strFromEmployeeShift = "";
//														String strToEmployeeShift = "";
//														WebElement FromEmployeeShift =null;
//														WebElement ToEmployeeShift = null;
//														FromEmployee = FromEmployee.trim();
//														ToEmployee = ToEmployee.trim();
//														boolean FoundFromEmployee = false;
//														boolean FoundToEmployee = false;
//
//														// looping over above table to get 'from' and 'to'  webelements
//														for(Map<String,WebElement> TableRow : TableDeatils)
//														{
//															WebElement ColumnData =(WebElement)TableRow.get("Template");
//															String strColumnData = ColumnData.getText();
//															strColumnData = strColumnData.trim();
//
//															//locating fromEmployee
//															if(strColumnData.equalsIgnoreCase(FromEmployee))
//															{
//																FoundFromEmployee=true;
//																FromEmployeeShift = TableRow.get(FromDay);
//															}
//															else if(strColumnData.equalsIgnoreCase(ToEmployee))
//															{
//																FoundToEmployee=true;	
//																ToEmployeeShift = TableRow.get(ToDay);
//															}
//															if(FoundToEmployee && FoundFromEmployee )
//															{
//																break;
//															}
//														}
//
//														strFromEmployeeShift = FromEmployeeShift.getText().trim();
//														strToEmployeeShift = ToEmployeeShift.getText().trim();
//														//.cross verification of successfully copied shifts
//														System.out.println("hi");
//														if(strFromEmployeeShift.equals(strToEmployeeShift) )
//														{
//															HtmlResult.passed(strDescription, strExpectedResult, "Copy shift successful From- ' "+FromEmployee+"//"+Week+"//"+FromDay+" ' , To ' "+ToEmployee+"//"+ToDay+" ' "+webUIDriver.ErrorMessage);
//														}
//														else
//														{
//															HtmlResult.failed(strDescription, strExpectedResult, "Cannot copy shift From- ' "+FromEmployee+"//"+Week+"//"+FromDay+" ' , To ' "+ToEmployee+"//"+ToDay+" ' "+webUIDriver.ErrorMessage);// unable to copy shift
//														}
//													}
//													else
//													{
//														HtmlResult.failed(strDescription, strExpectedResult, "Expected Alert-' "+ExpectedMessageOnPopup+" ' and Actual alert-' "+ActualMessageOnPopup+" '");
//													}
//												}
//												else
//												{
//													
//												}
//									
//											}
//											else // else no alert then just verify copy action
//											{
//												List<Map<String,WebElement>> TableDeatils = ReusableComponents.getScheduledShifts("FixedShifts_Shift_Inner_Table");
//												String strFromEmployeeShift = "";
//												String strToEmployeeShift = "";
//												WebElement FromEmployeeShift = null;
//												WebElement ToEmployeeShift = null;
//												FromEmployee = FromEmployee.trim();
//												ToEmployee = ToEmployee.trim();
//												boolean FoundFromEmployee = false;
//												boolean FoundToEmployee = false;
//												
//												// looping over above table to get 'from' and 'to'  webelements
//												for(Map<String,WebElement> TableRow : TableDeatils)
//												{
//													WebElement ColumnData =(WebElement)TableRow.get("Template");
//													String strColumnData = ColumnData.getText();
//													strColumnData = strColumnData.trim();
//													
//													//locating fromEmployee and toemployee
//													if(strColumnData.equalsIgnoreCase(FromEmployee))
//													{
//														FoundFromEmployee=true;
//														FromEmployeeShift = TableRow.get(FromDay);
//													}
//													else if(strColumnData.equalsIgnoreCase(ToEmployee))
//													{
//														FoundToEmployee=true;	
//														ToEmployeeShift =TableRow.get(ToDay);
//													}
//													if(FoundToEmployee && FoundFromEmployee )
//													{
//														break;
//													}
//												}
//												
//												strFromEmployeeShift = FromEmployeeShift.getText().trim();
//												strToEmployeeShift = ToEmployeeShift.getText().trim();
//												
//												//.cross verification of successfully copied shifts
//												if(strToEmployeeShift.trim().equals(strToEmployeeShift.trim()))
//												{
//													HtmlResult.passed(strDescription, strExpectedResult, "'Copy shift' successful From- ' "+FromEmployee+"//"+Week+"//"+FromDay+" ' , To ' "+ToEmployee+"//"+ToDay+" ' "+webUIDriver.ErrorMessage);
//												}
//												else
//												{
//													HtmlResult.failed(strDescription, strExpectedResult, "Cannot copy shift From- ' "+FromEmployee+"//"+Week+"//"+FromDay+" ' , To ' "+ToEmployee+"//"+ToDay+" ' "+webUIDriver.ErrorMessage);// unable to copy shift
//												}	
//											}
//											
//											//clicking on confirm button and then home button
//											blnFlag = webUIDriver.waitExplicitlyByXpath("FixedShifts_confirmButton", 5); 
//											if(blnFlag)
//											{
//													blnFlag = webUIDriver.locateElementAndClick("FixedShifts_confirmButton");
//													if(blnFlag)
//													{
//														blnFlag = webUIDriver.handleAlert("ok");
//														if(blnFlag)
//														{
//															//click on save button
//															blnFlag = webUIDriver.waitExplicitlyByXpath("FixedShifts_confirmShift_saveButton", 5);
//															if(blnFlag)
//															{
//																blnFlag = webUIDriver.locateElementAndClick("FixedShifts_confirmShift_saveButton");
//																if(blnFlag)
//																{
//																	blnFlag = webUIDriver.waitExplicitlyByXpath("MySchedule_homeButtonImage", 5);
//																	if(blnFlag)
//																	{
//																		blnFlag = webUIDriver.locateElementAndClick("MySchedule_homeButtonImage");
//																		if(blnFlag)
//																		{
//																			;
//																		}
//																		else
//																		{
//																			HtmlResult.failed(strDescription, strExpectedResult, "Home button clicked on webpage"+webUIDriver.ErrorMessage);
//																		}
//																	}
//																	else
//																	{
//																		HtmlResult.failed(strDescription, strExpectedResult, "Home button not found on webpage"+webUIDriver.ErrorMessage);
//																	}
//																}
//																else
//																{
//																	HtmlResult.failed(strDescription, strExpectedResult, "Save button not clicked on webpage"+webUIDriver.ErrorMessage);
//																}
//															}
//															else
//															{
//																HtmlResult.failed(strDescription, strExpectedResult, "Save button not found on webpage"+webUIDriver.ErrorMessage);
//															}
//														}
//														else
//														{
//															HtmlResult.failed(strDescription, strExpectedResult, "Unable to handle alert "+webUIDriver.ErrorMessage);
//														}
//													}
//													else
//													{
//														HtmlResult.failed(strDescription, strExpectedResult, "Confirm button not clicked on webpage"+webUIDriver.ErrorMessage);
//													}	
//											}
//											else
//											{
//												HtmlResult.failed(strDescription, strExpectedResult, "Confirm button not found on webpage"+webUIDriver.ErrorMessage);
//											}	
//										}
//										else
//										{
//											HtmlResult.failed(strDescription, strExpectedResult, "Cannot move shift From- ' "+FromEmployee+"//"+Week+"//"+FromDay+" ' , To ' "+ToEmployee+"//"+ToDay+" ' "+webUIDriver.ErrorMessage);
//										}
//									}
//									else
//									{
//										HtmlResult.failed(strDescription, strExpectedResult, "Fixed Shift header is not present on webpage "+webUIDriver.ErrorMessage);
//									}
//								}
//								else
//								{
//									HtmlResult.failed(strDescription, strExpectedResult, "Main menu ' Schedule ' not found "+webUIDriver.ErrorMessage);
//								}
//							}
//							else
//							{
//								HtmlResult.failed(strDescription, strExpectedResult, "Module ' Schedule ' not found "+webUIDriver.ErrorMessage);
//							}
//						}
//						else
//						{
//							HtmlResult.failed(strDescription, strExpectedResult, "Store ' "+StoreNumber+" ' not found "+webUIDriver.ErrorMessage);
//						}
//					}
//					else
//					{
//						HtmlResult.failed(strDescription, strExpectedResult, "Failed to login with credentials '"+WfmLoginUserName+"//"+WfmLoginPassword+" ' "+webUIDriver.ErrorMessage);
//					}
//				}
//				else
//				{
//					HtmlResult.failed(strDescription, strExpectedResult, "Unable to launch WFM website");
//				}
//			}
	
		} 
		catch (Exception e) {
			
			HtmlResult.failed(strDescription, "Copy shift should be successful", "Unable to launch WFM website "+e.getMessage()+" "+webUIDriver.ErrorMessage);
		}
	}
	
	public boolean verifyCopyAction(String FromEmployee, String ToEmployee ,String FromDay, String ToDay)
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
		System.out.println("Verifying copy action.....");
		if(strFromEmployeeShift.equals(strToEmployeeShift) )
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	public void callRecovery()
	{
		webUIDriver.quitBrowser();
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


