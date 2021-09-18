package com.cg.Application;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.bcel.verifier.statics.StringRepresentation;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;

import sun.security.util.Length;

import com.cg.ApplicationLevelComponent.Appium;
import com.cg.ApplicationLevelComponent.EggPlant;
import com.cg.Logutils.HtmlResult;
import com.cg.TestCaseRunner.TestCaseRunner;
import com.cg.UIDriver.AppiumUIDriver;
import com.gargoylesoftware.htmlunit.javascript.host.Set;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
//
public class GMA4_Appium extends Appium{

	public AppiumUIDriver appUIDriver = new AppiumUIDriver();
	int height;
	int width ;
	int x;
	int starty;
	int endy;
	String StrCurrentProductName="";
	String StrCurrentProductprice= "";
	HashMap<String,String> hmProductPriceInOrderSmryScrn = new HashMap<String,String>();  
	HashMap<String,String> hmProductPriceWithoutUnavailableItems = new HashMap<String,String>();
	HashMap<String,String> hmDrinkItemWithUpliftPrice = new HashMap<String,String>();
	float SubTotal_OutOfStockScreen = 0;
	float SubTotal_OrderBasketScreen = 0;
	float SubTotal_OderSummaryScreen=0;
	String StrLastOrderProductName="";
	String[] arrProductName_Offers = null ;
	String strProductName_Offers = null ;
	//String[] arrProductName_OrderSummaryScreen = null ;
	String FavOrderName = "";
	String strOrderNumber = "";
	String strFavRestaurantName = "";
	ArrayList<String> arrProductName_OrderSummaryScreen=new ArrayList<String>();
	
	String strCurrentTotalPriceBeforeBagChrg="";
	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � openApp
	 * Method Description - Invoke the Mobile App
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Prateek Gupta
	 * Creation Date - 09/25/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>

	// ***************************************************************************************************************/
	public void openApp()
	{
		try {
			//terminateApp();
			if(appUIDriver.openApp())
			{
				HtmlResult.passed("To open application", "Application should be launched successfully", "Application launched successfully");
			}
			else
			{
				HtmlResult.failed("To open application", "Application should be launched successfully", "Application is not launched successfully");
			}

		} catch (Exception e) {
			HtmlResult.failed("To open application", "Application should be launched successfully", "Application is not launched successfully");
		}
	}

	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � gma4_MenuSelection
	 * Method Description - 
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Mritunjay
	 * Creation Date - 09/25/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/
	public void gma4_MenuSelection(Map<String,String> input){

		try
		{
			String objectLocatorName;
			String objectLocatorValue;
			String strStepDescription = "To verify hamburger action";
			String strMenuName = input.get("mobileObjectName");
			String strExpectedData = input.get("expectedData");
			String CurrentObjectText="";
			strMenuName = strMenuName.trim();		
			boolean CurrentFlagValue = false;
			CurrentFlagValue = appUIDriver.clickButton("gma4_hamburger_action");
			if (CurrentFlagValue)
			{
				CurrentFlagValue = appUIDriver.scrollToObjectFound(strMenuName);
				if (CurrentFlagValue)
				{	
					String strGmaMenuName  = appUIDriver.getAppObjectwithLocator(strMenuName);
				
					if (strGmaMenuName!=null && strGmaMenuName.length()>1 && strGmaMenuName!= "false")
					{
						String[] arrGmaMenuName = strGmaMenuName.split("#");
						objectLocatorName =arrGmaMenuName[0].toString();
						objectLocatorValue= arrGmaMenuName[1].toString();
						CurrentObjectText = appUIDriver.getText(objectLocatorName, objectLocatorValue);
						if (CurrentObjectText.equalsIgnoreCase(strExpectedData))
						{
							if(appUIDriver.clickText(objectLocatorName, objectLocatorValue))
							{
								//HtmlResult.passed("verify the current content of app ", "App content should be - "+ strExpectedData + " for the object -" + strObjectName , "Actual content is - " + CurrentObjectText);
								HtmlResult.passed("Hamburger Sub Menu Selection", "Menu should be clicked--"+ strExpectedData,"Menu is clicked successfully - '"+ strExpectedData+ " '");
								//return true;
							}
							else
							{
								//	System.out.println("FAIL");
								HtmlResult.failed("Hamburger Sub Menu Selection", "Menu should be clicked--"+ strExpectedData,"Menu is not clicked - '"+ strExpectedData+ " '");
								//return false;
							}
						}
						else
						{
							HtmlResult.failed("Hamburger Sub Menu Selection", "Menu should be clicked--"+ strExpectedData,"Menu is not clicked - '"+ strExpectedData+ " '");
						}
					}
					else
					{
						HtmlResult.failed("Hamburger Sub Menu Selection", "Menu should be clicked--"+strExpectedData,"Object property is wrong - '"+ strExpectedData+ " '");
					}
				}
			}
			else
			{
				HtmlResult.failed(strStepDescription, "Hamburger action should be clicked successfully", "Hamburger action not found at the start of the application");
			}
		}

		catch(Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
	}


	/** Not in used in GMA 4.8

	public void gma4_SignIn(Map<String, String> input)
	{
		try
		{
			String strUserId = input.get("custUid").trim();
			String strPassword = input.get("custPwd").trim();
			String strDescription ="Perform customer login";
			String strExpectedResult ="Customer should be login on application using user id - " + strUserId + " and password " + strPassword;
			String obj_logIn_UserId=EggPlant.getValueFromExcel("GMA4_LOGIN_USERID");
			String obj_logIn_Password=EggPlant.getValueFromExcel("GMA4_LOGIN_PASSWORD");
			String obj_login_LogInButton=EggPlant.getValueFromExcel("GMA4_LOGIN_LOGINBUTTON");
			//String strCurrentLocators  = appUIDriver.getAppObjectwithLocator("obj_logIn_UserId");
			boolean blnFound =false;
			blnFound  = appUIDriver.enterText(obj_logIn_UserId, strUserId);
			if (blnFound)
			{

				blnFound  = appUIDriver.enterText(obj_logIn_Password, strPassword);
				if (blnFound)
				{
					blnFound  = appUIDriver.clickButton(obj_login_LogInButton);
					if (blnFound)
					{
						HtmlResult.passed(strDescription, strExpectedResult, "customer login successfully");
					}
					else
					{
						blnFound = appUIDriver.scrollToObjectFound(obj_login_LogInButton);

						blnFound  = appUIDriver.clickButton(obj_login_LogInButton);
						if (blnFound)
						{
							HtmlResult.passed(strDescription, strExpectedResult, "customer login successfully");
						}
						else
						{
							HtmlResult.failed(strDescription, strExpectedResult, "customer login is failed");
						}
					}
				}
				else{
					HtmlResult.failed(strDescription, strExpectedResult, "Unable to enter password");
				}
			}
			else
			{
				HtmlResult.failed(strDescription, strExpectedResult, "Unable to enter user id");
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}*/

	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � gma4_CustomerLoginThruHamBurgerMenu
	 * Method Description - Login to Application through Hamburger Menu and also verifies user already logged in or not
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Madhur Barsainya
	 * Creation Date - 11/02/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/

	public void gma4_CustLogin_Menu(Map<String, String> input)
	{
		try
		{
			String strUserId = input.get("custUid").trim();
			String strPassword = input.get("custPwd").trim();
			boolean currentFlagValue = false;
			String strObjProperty;
			String obj_logIn_UserId=EggPlant.getValueFromExcel("GMA4_LOGIN_USERID");
			String obj_logIn_Password=EggPlant.getValueFromExcel("GMA4_LOGIN_PASSWORD");
			String obj_login_LogInButton=EggPlant.getValueFromExcel("GMA4_LOGIN_LOGINBUTTON");
			String strOSType = EggPlant.getValueFromExcel("OS_TYPE");
			// If Hamburger Menu Present
			Thread.sleep(2000);
			currentFlagValue= appUIDriver.clickButton("gma4_hamburger_action");
			if(currentFlagValue)
			{
				strObjProperty = appUIDriver.getObjectProperty("gma4_Menu_SignOut");


				if(!strObjProperty.equalsIgnoreCase("false"))
				{
					//currentFlagValue = appUIDriver.getAppObjectFound("gma4_Menu_SignOut");
					//if (currentFlagValue)
					//{
					currentFlagValue =appUIDriver.clickButton("gma4_Menu_SignOut");
					if(currentFlagValue)
					{
						currentFlagValue =	appUIDriver.clickButton("gma4_Menu_PopUp_SignOut");
						if(currentFlagValue)
						{
							if (strOSType.equalsIgnoreCase("Android")) 
							{
								appUIDriver.clickButton("gma4_hamburger_action");
							}
							HtmlResult.warning("Customer Login","If customer login in app, kindly signout from app", "Customer was already login hence Signout the customer");
						}
					}
				}
				currentFlagValue = appUIDriver.clickButton("gma4_Menu_SignIn");
				if (!currentFlagValue)
				{
					HtmlResult.failed("Customer Login","Sign In button should be present in hamburger menu", "'Sign In' option is not present in Hamburger Menu");

				}
				else
				{
					currentFlagValue  = appUIDriver.enterText(obj_logIn_UserId, strUserId);
					if (currentFlagValue)
					{
						currentFlagValue  = appUIDriver.enterText(obj_logIn_Password, strPassword);
						if (currentFlagValue)
						{
							currentFlagValue = appUIDriver.scrollToObjectFound(obj_login_LogInButton);
							if (currentFlagValue)
							{
								currentFlagValue  = appUIDriver.clickButton(obj_login_LogInButton);
								if (currentFlagValue)
								{
									Thread.sleep(5000);
									//Don't delete..let it be for future.as of now not required
									/*currentFlagValue  = appUIDriver.getAppObjectFound("gma4_NoThanks_PushNotifications_Btn");
									if (currentFlagValue)
									{
										currentFlagValue  = appUIDriver.clickButton("gma4_NoThanks_PushNotifications_Btn");
									}
									else
									{
										//backbuttonPress();
									}*/
									if (strOSType.equalsIgnoreCase("Android")) 
									{
										appUIDriver.backbuttonPress();
										appUIDriver.clickButton("gma4_hamburger_action");
									}
									strObjProperty = appUIDriver.getObjectProperty("gma4_Menu_SignOut");

									if(!strObjProperty.equalsIgnoreCase("false"))
										//if (appUIDriver.getAppObjectFound("gma4_Menu_SignOut"))
									{
										appUIDriver.clickButton("gma4_hamburger_action");
										HtmlResult.passed("Login In GMA Application", "Customer should be able to login", "Customer login successfully with Username - '"+strUserId + "'");
										if (appUIDriver.getAppObjectFound("gma4_Ordering_SubProd_AddToBadge"))
										{
											DeleteAllItemsFromBasket(input);
										}
									}
									else
									{
										HtmlResult.failed("Login In to Application.", "Customer should be able to login", "Customer failed to login with Username - '"+ strUserId + "'");
									}
								}
								else
								{
									HtmlResult.failed("Login In to Application.", "Customer should be able to login", " Login button is not clicked");
								}
							}
							else
							{
								HtmlResult.failed("Login In to Application.", "Customer should be able to login", " Login button is not present on screen");
							}
						}
						else{
							HtmlResult.failed("Login In to Application.", "Customer should be able to login", " Unable to enter user id  in text field");

						}
					}
				}
			}
			else
			{
				HtmlResult.failed("Login In to Application.", "Customer should be able to login", " Unable to click on Hamburger Menu");
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
	}

	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � backbuttonPress
	 * Method Description - Mobile back button is pressed and expectedData contain return screen name.
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Mritunjay Sinha
	 * Creation Date - 11/02/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/
	public void backbuttonPress(){

		try{
			//String strbuttonPress = input.get("mobileObjectName");
			//String strexpectedData = input.get("expectedData");
			boolean buttonPressed=true;
			buttonPressed = appUIDriver.backbuttonPress();
			if(buttonPressed){
				HtmlResult.passed("Return to main screen","Previous screen should be displayed","Back to main screen");
			}
			else
			{
				HtmlResult.failed("Return to previous screen", "Previous screen should be not visible","Not Back to main screen");
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
	}

	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � verifySingleOnlyAppObject
	 * Method Description - verify the count of object in application
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author -Mritunjay
	 * Creation Date - 11/07/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/

	public void verifySingleOnlyAppObject(Map<String,String> input){

		try{
			String strObjectName =input.get("mobileObjectName");
			String strObjectName1 =input.get("mobileObjectName1");
			String strexpectedData = input.get("expectedData");
			int intObjCount,intObjCount1;
			boolean blnFound =false;				
			strObjectName =strObjectName.trim();
			strObjectName1 =strObjectName1.trim();
			String strMobileObjectName  = appUIDriver.getAppObjectwithLocator(strObjectName);

			if(!strMobileObjectName.equalsIgnoreCase("false")){
				String[] arrMobileObjectName = strMobileObjectName.split("#");
				String objectLocatorName =arrMobileObjectName[0].toString();
				String objectLocatorValue= arrMobileObjectName[1].toString();

				intObjCount= appUIDriver.countmobileObject(objectLocatorName,objectLocatorValue);
				if(intObjCount==1){
					blnFound =true;

					//boolean objectFound = appUIDriver.getAppObjectFound(strObjectName1);

					String strMobileObjectName1  = appUIDriver.getAppObjectwithLocator(strObjectName);

					if(!strMobileObjectName1.equalsIgnoreCase("false")){
						String[] arrMobileObjectName1 = strMobileObjectName.split("#");
						String objectLocatorName1 =arrMobileObjectName1[0].toString();
						String objectLocatorValue1= arrMobileObjectName1[1].toString();

						intObjCount1= appUIDriver.countmobileObject(objectLocatorName1,objectLocatorValue1);

						if(intObjCount1==1){
							HtmlResult.passed("Verify only one object is displayed","Only Object should be displayed--"+strexpectedData,"Only Object is displayed--"+strexpectedData);
						}
						else{
							HtmlResult.failed("Verify only one object is displayed","Only Object should be displayed--"+strexpectedData,"Only Object is not displayed--"+strexpectedData);
						}
					} else{HtmlResult.failed("Verify only one object is displayed","Only Object should be displayed--"+strexpectedData,"Only Object is not displayed--"+strexpectedData); }
				}
				else
				{
					HtmlResult.failed("Verify only one object is displayed","Only Object should be displayed"+strexpectedData,"Only Object is not displayed"+strexpectedData);
				}
			}
		}
		catch (Exception e)
		{
			HtmlResult.failed("Locate Object Locator", "Locate Object Locator deatails should be present", e.getMessage());

		}
	}	
	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � verifyObjectNotFound
	 * Method Description - Negative scenario for object not present on screen.
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author -Mritunjay
	 * Creation Date - 11/02/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/
	public void verifyObjectNotFound(Map<String,String> input){

		try{
			String strObjectName =input.get("mobileObjectName");
			String strexpectedData = input.get("expectedData");

			boolean objectFound = appUIDriver.getAppObjectFound(strObjectName);
			//appUIDriver.getAppObjectwithLocator(strObjectName);
			if(!objectFound){
				HtmlResult.passed("Verify Object is not displayed","Object "+strObjectName+" should not be displayed--"+strexpectedData,"Object is not displayed-- '"+strexpectedData+"'.");
			}
			else{
				HtmlResult.failed("Verify Object is not displayed","Object "+strObjectName+" should not be displayed--"+strexpectedData,"Object is displayed--"+strexpectedData);
			}			
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}

	}
	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � click_DynamicObject
	 * Method Description -Click on product/sub-product as per input from excel sheet.
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author -Mritunjay
	 * Creation Date - 11/02/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/
	public void click_DynamicObject(Map<String,String> input)
	{			
		try
		{				
			String strDynamicBreakfstName,strDynamicLunchName;
			String strObjectName =input.get("mobileObjectName");
			strDynamicBreakfstName =input.get("BreakfstPrdName").trim();
			strDynamicLunchName =input.get("LunchPrdName").trim();		
			boolean FoundAndClicked =false;	

			String[] arrObjects = strObjectName.split("#");	
			int intObjectCounter = 0;		
			SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
			Date date = new Date();				
			// UKTime1 and UKTime2 check breakfast and lunch time
			String ISTTime1 =input.get("StartTime").trim();
			String ISTTime2 = input.get("EndTime").trim();
			String shopTime = parser.format(date);
			Date UKTime1 = parser.parse(ISTTime1); //Denotes Start breakfast UK time UK 
			Date UKTime2 = parser.parse(ISTTime2); //Denotes end breakfast UK time UK
			Date userDate = parser.parse(shopTime);

			if (userDate.after(UKTime1) && userDate.before(UKTime2)) 

			{	
				String[] arrObjectDynamic = strDynamicBreakfstName.split("#");	

				if (strDynamicBreakfstName!=null && strDynamicBreakfstName.length()>1)
				{

					for (intObjectCounter=0 ; intObjectCounter<arrObjects.length ; intObjectCounter++)
					{
						if (appUIDriver.scrollToObjectFoundDynamic(arrObjects[intObjectCounter],arrObjectDynamic[intObjectCounter]))
						{
							String strMobileObjectName  = appUIDriver.getAppObjectDynamicLocator(arrObjects[intObjectCounter],arrObjectDynamic[intObjectCounter]);

							String[] arrMobileObjectName = strMobileObjectName.split("#");

							String objectLocatorName =arrMobileObjectName[0].toString();
							String objectLocatorValue= arrMobileObjectName[1].toString();

							FoundAndClicked= appUIDriver.clickText(objectLocatorName, objectLocatorValue);

							if (FoundAndClicked)
							{
								System.out.println("Object clicekd - PASS");

								HtmlResult.passed("Tap the Mobile app object ", "Tap operation should be done successfully for object - "+ arrObjects[intObjectCounter] + " for the object -" + arrObjects[intObjectCounter] , "Tap operation performed successfully for object - " + arrObjects[intObjectCounter]);
							}
							else
							{
								System.out.println("FAIL");
								HtmlResult.failed("Tap the Mobile app object ", "Tap operation should be done successfully for object - "+ arrObjects[intObjectCounter] + " for the object -" + arrObjects[intObjectCounter] , "Tap operation is not performed for object - " + arrObjects[intObjectCounter]);
							}
						} 

						else
						{
							HtmlResult.failed("Tap the Mobile app object ", "Tap operation should be done successfully for object - "+ arrObjects[intObjectCounter] + " for the object -" + arrObjects[intObjectCounter] , "Tap operation is not performed for object - " + arrObjects[intObjectCounter]);
						}
					}
				}
				else
				{
					HtmlResult.failed("Scroll to App Object.", "Should be able to find to '"+arrObjects[intObjectCounter]+"' object.", "Failed to find '"+arrObjects[intObjectCounter]+"' object.");
				}
			}


			else{
				String[] arrObjectDynamic = strDynamicLunchName.split("#");	

				if (strDynamicLunchName!=null && strDynamicLunchName.length()>1)
				{

					for (intObjectCounter=0 ; intObjectCounter<arrObjects.length ; intObjectCounter++)
					{
						if (appUIDriver.scrollToObjectFoundDynamic(arrObjects[intObjectCounter],arrObjectDynamic[intObjectCounter]))
						{
							String strMobileObjectName  = appUIDriver.getAppObjectDynamicLocator(arrObjects[intObjectCounter],arrObjectDynamic[intObjectCounter]);

							String[] arrMobileObjectName = strMobileObjectName.split("#");

							String objectLocatorName =arrMobileObjectName[0].toString();
							String objectLocatorValue= arrMobileObjectName[1].toString();

							FoundAndClicked= appUIDriver.clickText(objectLocatorName, objectLocatorValue);

							if (FoundAndClicked)
							{
								System.out.println("Object clicekd - PASS");

								HtmlResult.passed("Tap the Mobile app object ", "Tap operation should be done successfully for object - "+ arrObjects[intObjectCounter] + " for the object -" + arrObjects[intObjectCounter] , "Tap operation performed successfully for object - " + arrObjects[intObjectCounter]);
							}
							else
							{
								System.out.println("FAIL");
								HtmlResult.failed("Tap the Mobile app object ", "Tap operation should be done successfully for object - "+ arrObjects[intObjectCounter] + " for the object -" + arrObjects[intObjectCounter] , "Tap operation is not performed for object - " + arrObjects[intObjectCounter]);
							}
						} 

						else
						{
							HtmlResult.failed("Tap the Mobile app object ", "Tap operation should be done successfully for object - "+ arrObjects[intObjectCounter] + " for the object -" + arrObjects[intObjectCounter] , "Tap operation is not performed for object - " + arrObjects[intObjectCounter]);
						}
					}
				}
				else
				{
					HtmlResult.failed("Scroll to App Object.", "Should be able to find to '"+arrObjects[intObjectCounter]+"' object.", "Failed to find '"+arrObjects[intObjectCounter]+"' object.");
				}
			}


		}
		catch (Exception e)
		{
			HtmlResult.failed("Tap the Mobile app object ", "Tap operation should be done successfully for object - for the object -"  , "Tap operation is not performed for object - " );

		}
	}



	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � gma4_TapOnMobileObject
	 * Method Description - 
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author -Mritunjay
	 * Creation Date - 11/02/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/
	public void gma4_TapOnMobileObject(Map<String,String> input)
	{
		try{
			String strObjectName =input.get("mobileObjectName");
			String strexpectedData =input.get("expectedData");
			boolean tapMobileObject = false;
			tapMobileObject = appUIDriver.TapOnMobileObject(strObjectName);
			if(tapMobileObject)
			{
				HtmlResult.passed("Click on object", "Object should be clickable" + strexpectedData,"Object is clickable" +strexpectedData);
			}

			else{
				HtmlResult.failed("Click on object", "Object should be clickable" + strexpectedData,"Object is not clickable" +strexpectedData);
			}

		}

		catch(Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
	}


	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � clickButtonScreen
	 * Method Description - Used to customize product condiments
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author -Mritunjay
	 * Creation Date - 11/02/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/
	public void clickButtonScreen(Map<String,String> input){

		try{
			
			boolean blnclickbutton=false;
			String strexpectedData = input.get("expectedData");;
			String ObjectName = input.get("mobileObjectName");
			blnclickbutton=appUIDriver.clickButton(ObjectName);
			if (blnclickbutton){
				HtmlResult.passed("Perform click button operation", "Button should be clicked--"+strexpectedData, "Button clicked sucessfully--" +strexpectedData);
			}
			else
			{
				HtmlResult.failed("Perform click button operation", "Button should be clicked--"+strexpectedData, "Button is not clicked sucessfully--" +strexpectedData);
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
	}

	/***************************************************************************************************************
	 * 	// Method Name -
	 * Project - GMA Method
	 * Method Name � GMA4_textVerify
	 * Method Description - This method will verify text
	 * Return Type - Boolean value
	 * Parameters - Inputs object text
	 * Framework - UKIT Master Framework
	 * Author - Asif Mustafa
	 * Creation Date - 11/03/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/


		/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � enterTextinAppObjcect
	 * Method Description - Used to customize product condiments
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Prateek
	 * Creation Date - 11/02/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/

	public void enterTextinAppObject(Map<String,String> input)
	{
		String strObjectName = input.get("mobileObjectName");
		String strTextForEnter = input.get("strTextForEnter");

		try
		{	boolean currentFlagValue= false;
		strTextForEnter = strTextForEnter.trim();
		currentFlagValue=appUIDriver.getAppObjectFound(strObjectName );
		if(currentFlagValue){
			appUIDriver.enterText(strObjectName, strTextForEnter);
			HtmlResult.passed("Enter text in object--" + strObjectName ,"Enter text in object--" +  strTextForEnter , "Enter text successfully entered in object--" +  strTextForEnter);
		}
		else{HtmlResult.failed("Enter text in object--" + strObjectName ,"Enter text in object--" +  strTextForEnter , "Enter text unsuccessfully entered in object--" +  strTextForEnter);}	
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
	}



	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � gma4_CustomiseProduct
	 * Method Description - Used to customize product condiments
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Madhur Barsainya
	 * Creation Date - 11/02/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/

	public void gma4_CustomizeProduct(Map<String, String> input)
	{
		try
		{
			String strQuantityActualValue = "";
			int intNumOfCondimentsCounter = 0 ;
			int intQuantityActualValue = 0 ;
			int intQtyOfCondiments = 0 ;
			String[] arrCondiment;
			String strCondiments;
			String strNameOfCondiment = "";
			String strQtyOfCondiment = "";
			String strCustomizeCondimentsQtyObject = "gma4_CustomiseCondimentsQuantity";
			String strCustomizeCondimentPlusObject = "gma4_CustomiseCondiments_Plus";
			String strCustomizeCondimentMinusObject = "gma4_CustomiseCondiments_Minus";

			SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
			Date date = new Date();

			// UKTime1 and UKTime2 check breakfast and lunch time
			String ISTStartTime=EggPlant.getValueFromExcel("StartTime");
			String ISTEndTime=EggPlant.getValueFromExcel("EndTime");
			String shopTime = parser.format(date);
			Date UKTime1 = parser.parse(ISTStartTime); //Denotes Start breakfast UK time UK 
			Date UKTime2 = parser.parse(ISTEndTime); //Denotes end breakfast UK time UK
			Date userDate = parser.parse(shopTime);

			if (userDate.after(UKTime1) && userDate.before(UKTime2)) 
			{
				strCondiments =input.get("BrkfstCondiments").trim(); //eg - 1 Bagel with Jam#2 Cheese
			}
			else
			{
				strCondiments =input.get("LunchCondiments");  //eg - 1 Big Mac#2 Onion,2 Cucumber
			}

			appUIDriver.clickButton("gma4_CustomizationHatButton");

			//			String[] arrNumOfCondimemnts  = strCondiments.split("#");
			String[] arrCondiments = strCondiments.split(",");

			for (intNumOfCondimentsCounter = 0; intNumOfCondimentsCounter< arrCondiments.length; intNumOfCondimentsCounter++)
			{
				arrCondiment = arrCondiments[intNumOfCondimentsCounter].split(" ");
				strQtyOfCondiment = arrCondiment[0];
				strNameOfCondiment = arrCondiment[1];

				intQtyOfCondiments =  Integer.parseInt(strQtyOfCondiment);
				appUIDriver.scrollToObjectFoundDynamic(strCustomizeCondimentsQtyObject, strNameOfCondiment);
				//String strMobileObjectName  = appUIDriver.getAppObjectwithLocator(CondimentsCount);

				strQuantityActualValue  = appUIDriver.getTextForDynamicObject(strCustomizeCondimentsQtyObject,strNameOfCondiment);
				if (strQuantityActualValue.equalsIgnoreCase("none"))
				{
					intQuantityActualValue = 0;
				}
				else
				{
					intQuantityActualValue = Integer.parseInt(strQuantityActualValue);
				}
				while (intQuantityActualValue!=intQtyOfCondiments) 
				{
					strQuantityActualValue  = appUIDriver.getTextForDynamicObject(strCustomizeCondimentsQtyObject,strNameOfCondiment);
					if (strQuantityActualValue.equalsIgnoreCase("none"))
					{
						intQuantityActualValue = 0;
					}
					else
					{
						intQuantityActualValue = Integer.parseInt(strQuantityActualValue);
					}
					if(intQtyOfCondiments > intQuantityActualValue)
					{
						appUIDriver.clickMobileDynamicAppObject(strCustomizeCondimentPlusObject,strNameOfCondiment);
						Thread.sleep(1000);
					}
					else if (intQtyOfCondiments < intQuantityActualValue)
					{
						appUIDriver.clickMobileDynamicAppObject(strCustomizeCondimentMinusObject,strNameOfCondiment);
						Thread.sleep(1000);
					}
				}
			}
			appUIDriver.clickButton("gma4_Done_Btn");
			HtmlResult.passed("Customize Product.", "Product should be custimozed with '"+strCondiments+"' condiments.", "Product is customized with condiments - '"+strCondiments+"'.");
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
	}

	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � gma4_MakeAMeal
	 * Method Description - Make a Meal (Medium or Large) with Side item and Drink item
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Madhur Barsainya
	 * Creation Date - 11/10/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/

	public void gma4_MakeAMeal(Map<String, String> input)
	{
		try
		{
			String strMainProjectObject = "gma4_Ordering_Product_Menu";
			String strSubProjectObject = "gma4_Ordering_SubProduct_Menu";
			String strAllProducts;
			String strMealType = "";
			boolean currentFlagValue = false;

			SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
			Date date = new Date();

			// UKTime1 and UKTime2 check breakfast and lunch time
			String ISTStartTime=EggPlant.getValueFromExcel("StartTime");
			String ISTEndTime=EggPlant.getValueFromExcel("EndTime");
			String shopTime = parser.format(date);
			Date UKTime1 = parser.parse(ISTStartTime); //Denotes Start breakfast UK time UK 
			Date UKTime2 = parser.parse(ISTEndTime); //Denotes end breakfast UK time UK
			Date userDate = parser.parse(shopTime);

			if (userDate.after(UKTime1) && userDate.before(UKTime2)) 
			{
				strAllProducts =input.get("BrkfstProduct").trim(); // eg - Milkshakes#Vanilla Milkshake
				strMealType = "Breakfast";
			}
			else
			{
				strAllProducts =input.get("LunchProduct").trim(); // eg - Burger#Big Mac
				strMealType = input.get("MealType");
			}
			String[] arrAllProducts = strAllProducts.split("#");

			currentFlagValue= appUIDriver.clickButton("gma4_hamburger_action");
			if (currentFlagValue)
			{
				currentFlagValue = appUIDriver.clickButton("gma4_Menu_StartAnOrder"); // navigating to ordering page
				if (currentFlagValue)
				{
					currentFlagValue = appUIDriver.clickMobileDynamicAppObject(strMainProjectObject, arrAllProducts[0]); //Selecting Product Category
					if (currentFlagValue)
					{
						currentFlagValue = appUIDriver.clickMobileDynamicAppObject(strSubProjectObject, arrAllProducts[1]); //Selecting Product
						if (currentFlagValue)
						{
							HtmlResult.passed("Selecting Product.", "Product '"+arrAllProducts[1]+"' should be selected.", "Product '"+arrAllProducts[1]+"' is selected.");
							if(!strMealType.equalsIgnoreCase(""))
							{
								currentFlagValue = appUIDriver.clickMobileDynamicAppObject("gma4_MealType_ItemPage_Btn",strMealType);
								if (currentFlagValue)
								{
									gma4_SelectSideItem(input); //Side Item will be selected
									gma4_SelectDrinkItem(input); //Drink Item will be selected
									currentFlagValue = appUIDriver.clickButton("gma4_Ordering_SubProd_AddBasket");
									if (currentFlagValue)
									{
										HtmlResult.passed("Adding product to Basket.", "Product should be added to Basket.", "Product '"+arrAllProducts[1]+"' is added to basket.");
									}
									else
									{
										HtmlResult.failed("Clicking on 'Add to Basket' button.", "User should be able to click on 'Add to Basket' button.", "Unable to click on 'Add to Basket' button.");
									}
									
								}
								else
								{
									HtmlResult.failed("Selecting Meal Type.", "'"+strMealType+"' Meal should be clicked.", "Unable to click on '"+strMealType+"' Meal.");
								}
							}
						}
						else
						{
							HtmlResult.failed("Selecting Product.", "Product '"+arrAllProducts[1]+"' should be selected.", "Product '"+arrAllProducts[1]+"' is not present.");
						}
					}
					else
					{
						HtmlResult.failed("Selecting Product Category.", "Product Category '"+arrAllProducts[0]+"' should be selected.", "Product Category '"+arrAllProducts[0]+"' is not present.");
					}
				}
				else
				{
					HtmlResult.failed("Clicking Start an Order from Hemburger menu.", "User should be able to click on Start An Order from Hamburger Menu.", "Unable to click on Start An Order from Hamburger Menu.");
				}
			}
			else
			{
				HtmlResult.failed("Clicking on 'Ordering' button on Home Page.", "User should be able to click on 'Ordering' button on Home Page.", "Unable to click on 'Ordering' button on Home Page.");
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
	}


	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � gma4_SelectSideItem
	 * Method Description - To select Side Items while making a Medium or Large meal
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Madhur Barsainya
	 * Creation Date - 11/10/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/

	public void gma4_SelectSideItem(Map<String, String> input)
	{
		try
		{
			boolean currentFlagValue = false;
			String strMealType = "";
			String strSideItem = ""; //Side item to be selected while making a meal
			String[] arrSideItem ;
			SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
			Date date = new Date();
			
			// UKTime1 and UKTime2 check breakfast and lunch time
			String ISTStartTime=EggPlant.getValueFromExcel("StartTime");
			String ISTEndTime=EggPlant.getValueFromExcel("EndTime");
			String shopTime = parser.format(date);
			Date UKTime1 = parser.parse(ISTStartTime); //Denotes Start breakfast UK time UK 
			Date UKTime2 = parser.parse(ISTEndTime); //Denotes end breakfast UK time UK
			Date userDate = parser.parse(shopTime);
	
			if (userDate.after(UKTime1) && userDate.before(UKTime2)) 
			{
				strMealType = "Breakfast";
				strSideItem = input.get("BrkfstSideItem").trim();
			}
			else
			{
				strMealType = input.get("MealType");  //MealType either Medium or Large
				 strSideItem = input.get("LunchSideItem").trim();
			}
			
			arrSideItem = strSideItem.split("#");
			currentFlagValue = appUIDriver.clickMobileDynamicAppObject("gma4_Side_ItemPage_Btn",strMealType);
			if (currentFlagValue)
			{
				for (String ItemName : arrSideItem)
				{
					currentFlagValue = appUIDriver.clickMobileDynamicAppObject("gma4_SideItem_ItemPage_Btn",ItemName);
					if (!currentFlagValue)
					{
						HtmlResult.failed("Clicking on '"+ItemName+"' side item for the product.", "User shoule be able to click on '"+ItemName+"' side item for the product.", "Unable to click on '"+ItemName+"' side item for the product.");
					}
				}
				currentFlagValue = appUIDriver.clickButton("gma4_Done_Btn");
				if (currentFlagValue)
				{
					HtmlResult.passed("Selecting Side Item for the product.", "User shold be able to select Side Item for the product.", "'"+strSideItem+"' selected as Side Item for '"+strMealType+"' meal.");
				}
				else
				{
					HtmlResult.failed("Click on 'Done' after selecting Side Item for the product..", "User should be able to click on 'Done' after selecting Side Item for the product.", "Unable to click on 'Done' button after selecting Side Item for the product.");
				}
			}
			else
			{
				HtmlResult.failed("Click on '"+strMealType+" Side'.", "User should be able to click on '"+strMealType+" Side' for product.", "Unable to click on '"+strMealType+" Side' for the product.");
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getLocalizedMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
	}


	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � gma4_SelectDrinkItem
	 * Method Description - To select Drink Items while making a Medium or Large meal
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Madhur Barsainya
	 * Creation Date - 11/10/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/

	public void gma4_SelectDrinkItem(Map<String, String> input)
	{
		try
		{
			boolean currentFlagValue = false;
			String strMealType = "";
			String strDrinkItem = ""; //Drink item to be selected while making a meal
			String[] arrDrinkItem ;
			SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
			Date date = new Date();
			
			// UKTime1 and UKTime2 check breakfast and lunch time
			String ISTStartTime=EggPlant.getValueFromExcel("StartTime");
			String ISTEndTime=EggPlant.getValueFromExcel("EndTime");
			String shopTime = parser.format(date);
			Date UKTime1 = parser.parse(ISTStartTime); //Denotes Start breakfast UK time UK 
			Date UKTime2 = parser.parse(ISTEndTime); //Denotes end breakfast UK time UK
			Date userDate = parser.parse(shopTime);
	
			if (userDate.after(UKTime1) && userDate.before(UKTime2)) 
			{
				strMealType = "Breakfast";
				strDrinkItem = input.get("BrkfstDrinkItem").trim();
			}
			else
			{
				strMealType = input.get("MealType");  //MealType either Medium or Large
				strDrinkItem = input.get("LunchDrinkItem").trim();
				
			}
			
			arrDrinkItem = strDrinkItem.split("#"); 
			currentFlagValue = appUIDriver.clickButton("gma4_Drink_ItemPage_Btn");
			if (currentFlagValue)
			{
				for (String ItemName : arrDrinkItem)
				{
					currentFlagValue = appUIDriver.clickMobileDynamicAppObject("gma4_DrinkItem_ItemPage_Btn",ItemName);
					if (!currentFlagValue)
					{
						HtmlResult.failed("Clicking on '"+ItemName+"' side drink for the product.", "User shoule be able to click on '"+ItemName+"' drink item for the product.", "Unable to click on '"+ItemName+"' drink item for the product.");
					}
				}
				currentFlagValue = appUIDriver.clickButton("gma4_Done_Btn");
				if (currentFlagValue)
				{
					String strUpliftPrice_SubProduct = appUIDriver.getObjectText("gma4_DrinkUpLiftPrice_SubProduct");
					if (strUpliftPrice_SubProduct.equalsIgnoreCase("false"))
					{
						HtmlResult.passed("Selecting Drink Item for the product.", "User shold be able to select Drink Item for the product.", "'"+strDrinkItem+"' selected as Drink Item for '"+strMealType+"' meal having no uplift price.");
					}
					else
					{
						hmDrinkItemWithUpliftPrice.put(arrDrinkItem[arrDrinkItem.length-1], strUpliftPrice_SubProduct) ;
						HtmlResult.passed("Selecting Drink Item for the product.", "User shold be able to select Drink Item for the product.", "'"+strDrinkItem+"' selected as Drink Item for '"+strMealType+"' meal having uplift price '"+strUpliftPrice_SubProduct+"'.");
					}
				}
				else
				{
					HtmlResult.failed("Click on 'Done' after selecting Drink Item for the product..", "User should be able to click on 'Done' after selecting Drink Item for the product.", "Unable to click on 'Done' button after selecting Drink Item for the product.");
				}
			}
			else
			{
				HtmlResult.failed("Click on '"+strMealType+" Side'.", "User should be able to click on '"+strMealType+" Drink' for product.", "Unable to click on '"+strMealType+" Drink' for the product.");
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
	}

	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � gma4_VerifyObjectPresent_Dynamic
	 * Method Description - To verify dynamic object is present 
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Madhur Barsainya
	 * Creation Date - 11/10/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/

	public void VerifyDynamicObjectPresent(Map<String, String> input)
	{
		try
		{
			String strobjName = input.get("mobileObjectName");
			String strDynamicText = input.get("DynamicText");
			if (appUIDriver.scrollToObjectFoundDynamic(strobjName,strDynamicText))
			{
				HtmlResult.passed("Verify Dynamic Object.", "Object should be present - "+strobjName, "Object is present - "+strDynamicText);
			}
			else
			{
				HtmlResult.failed("Verify Dynamic Object.", "Object should be present - "+strobjName, "Object is not present - "+strobjName);
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
	}

	/***************************************************************************************************************
	 * Method Name -
	 * Project - GMA Method
	 * Method Name � verifyDynamicObjectText
	 * Method Description - This method will verify the tect of Dynamic object
	 * Return Type - void
	 * Parameters - Inputs are fetched from map (inputs are)
	 * Framework - UKIT Master Framework
	 * Author - Madhur Barsainya
	 * Creation Date - 11/10/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>
	 * @return

	 ***************************************************************************************************************/
	public void verifyDynamicObjectText(Map<String,String> input)
	{
		try
		{
			String strObjectName =input.get("mobileObjectName"); //Object Name
			String strDynamicText = input.get("DynamicText"); //Dynamic Text to be passed in object
			String strExpectedData = input.get("expectedData"); 
			String CurrentObjectText="";
			strObjectName = strObjectName.trim();
			strExpectedData = strExpectedData.trim();

			boolean BlnSwipeFound = false;
			int loopCounter=0;

			do
			{
				Thread.sleep(1000);

				String strMobileObjectName  = appUIDriver.getAppObjectDynamicLocator(strObjectName, strDynamicText);

				if (strMobileObjectName.contains("false"))
				{
					return;
				}
				else
				{
					String[] arrMobileObjectName = strMobileObjectName.split("#");
					String objectLocatorName =arrMobileObjectName[0].toString();
					String objectLocatorValue= arrMobileObjectName[1].toString();
					Dimension CurrentMobileSize = driver.manage().window().getSize();
					System.out.println(CurrentMobileSize);
					// Current screen size from mobile devices -
					height = CurrentMobileSize.getHeight();
					width = CurrentMobileSize.getWidth();
					x=width/2;
					starty = (int) (height*0.40);
					endy= (int) (width*0.20);
					try
					{
						CurrentObjectText = appUIDriver.getText(objectLocatorName, objectLocatorValue);

						BlnSwipeFound = true;
					}
					catch (Exception e)
					{
						BlnSwipeFound = false;
						driver.swipe(x, starty, x, endy, 500);

						loopCounter = loopCounter +1 ;
						if (loopCounter>10)
						{
							BlnSwipeFound = true;
						}
					}
				}
			}
			while (!BlnSwipeFound);

			// After Successfully loop , the value will check

			if (CurrentObjectText.equalsIgnoreCase(strExpectedData))
			{
				System.out.println("PASS");
				HtmlResult.passed("verify the current content of app ", "App content should be - "+ strExpectedData + " for the object -" + strObjectName , "Actual content is - " + CurrentObjectText);
			}
			else
			{
				System.out.println("FAIL");
				HtmlResult.failed("verify the current content of app ", "App content should be - "+ strExpectedData + " for the object -" + strObjectName , "Actual content is not - " + CurrentObjectText);
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
	}
	/***************************************************************************************************************
	 * 	// Method Name -
	 * Project - GMA Method
	 * Method Name � GMA4_textVerify
	 * Method Description - This method will verify text
	 * Return Type - Boolean value
	 * Parameters - Inputs object text
	 * Framework - UKIT Master Framework
	 * Author - Asif Mustafa
	 * Creation Date - 11/03/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/

	
		public boolean gma4_verifyText(Map<String,String> input)

		{
			try
			{
				String objectLocatorName;
				String objectLocatorValue;
				//mobileAppSync();
				String strObjectName =input.get("mobileObjectName");
				String strExpectedData = input.get("expectedData");

				String strMobileObjectName  = appUIDriver.getAppObjectwithLocator(strObjectName);

				if (strMobileObjectName!=null && strMobileObjectName.length()>1)
				{

					String[] arrMobileObjectName = strMobileObjectName.split("#");

					objectLocatorName =arrMobileObjectName[0].toString();
					objectLocatorValue= arrMobileObjectName[1].toString();
					//System.out.println( "attribute is="+driver.findElement(By.xpath(objectLocatorValue)).getAttribute("content-desc"));

					String CurrentObjectText = appUIDriver.getTextByXpath1(objectLocatorValue);



					if (CurrentObjectText.contains(strExpectedData))
					{
				

						HtmlResult.passed("verify the current content of app ", "App content should be - "+ strExpectedData + " for the object -" + strObjectName , "Actual content is - " + CurrentObjectText);
						return true;

					}
					else
					{
				

						HtmlResult.failed("verify the current content of app ", "App content should be - "+ strExpectedData + " for the object -" + strObjectName , "Actual content is not - " + CurrentObjectText);
						return false;
					}
				}

				else
				{
					HtmlResult.failed("verify the current content of app ", "App content should be - "+ strExpectedData + " for the object -" + strObjectName , "Actual content is not - " + strObjectName);
					return false;
				}
			}

			catch (Exception e)
			{
				System.out.println(e.getMessage());
				HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
				return false;
			}
		}


	/***************************************************************************************************************
	 * Method Name -
	 * Project - GMA Method
	 * Method Name �gma4_Contect_desc
	 * Method Description -
	 * Return Type - 
	 * Parameters - Inputs are fetched from map (inputs are)
	 * Framework - UKIT Master Framework
	 * Author - Asif
	 * Creation Date - 11/05/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>
	 * @return

	 ***************************************************************************************************************/
	public boolean gma4_Contect_desc(Map<String,String> input)

	{
		try
		{
			String objectLocatorName;
			String objectLocatorValue;

			String strObjectName =input.get("mobileObjectName");
			String strExpectedData = input.get("expectedData");

			String strMobileObjectName  = appUIDriver.getAppObjectwithLocator(strObjectName);

			if (strMobileObjectName!=null && strMobileObjectName.length()>1)
			{

				String[] arrMobileObjectName = strMobileObjectName.split("#");

				objectLocatorName =arrMobileObjectName[0].toString();
				objectLocatorValue= arrMobileObjectName[1].toString();


				String CurrentObjectText = appUIDriver.getTextByContent(objectLocatorValue);



				if (CurrentObjectText.equalsIgnoreCase(strExpectedData))
				{
					System.out.println("PASS");

					HtmlResult.passed("verify the current content of app ", "App content should be - "+ strExpectedData + " for the object -" + strObjectName , "Actual content is - " + CurrentObjectText);
					return true;

				}
				else
				{
					System.out.println("FAIL");

					HtmlResult.failed("verify the current content of app ", "App content should be - "+ strExpectedData + " for the object -" + strObjectName , "Actual content is not - " + CurrentObjectText);
					return false;
				}
			}

			else
			{
				HtmlResult.failed("verify the current content of app ", "App content should be - "+ strExpectedData + " for the object -" + strObjectName , "Actual content is not - " + strObjectName);
				return false;
			}
		}

		catch (Exception e)
		{
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
			return false;
		}
	}

	/***************************************************************************************************************
	 * Method Name -
	 * Project - GMA Method
	 * Method Name � scrollverifyContentDesc
	 * Method Description - 
	 * Return Type - 
	 * Parameters - Inputs are fetched from map (inputs are)
	 * Framework - UKIT Master Framework
	 * Author - Asif
	 * Creation Date - 09/25/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>
	 * @return

	 ***************************************************************************************************************/
	public void scrollverifyContentDesc(Map<String,String> input)
	{
		try{
			String strObjectName =input.get("mobileObjectName");
			String strExpectedData = input.get("expectedData");
			String CurrentObjectText="";
			strObjectName = strObjectName.trim();
			strExpectedData = strExpectedData.trim();

			boolean BlnSwipeFound = false;
			int loopCounter=0;

			do
			{
				Thread.sleep(1000);

				String strMobileObjectName  = appUIDriver.getAppObjectwithLocator(strObjectName);

				if (strMobileObjectName.contains("false"))
				{
					return;
				}
				else
				{
					String[] arrMobileObjectName = strMobileObjectName.split("#");
					String objectLocatorName =arrMobileObjectName[0].toString();
					String objectLocatorValue= arrMobileObjectName[1].toString();
					Dimension CurrentMobileSize = driver.manage().window().getSize();
					System.out.println(CurrentMobileSize);
					// Current screen size from mobile devices -
					height = CurrentMobileSize.getHeight();
					width = CurrentMobileSize.getWidth();
					x=width/2;
					starty = (int) (height*0.40);
					endy= (int) (width*0.20);
					try
					{
						CurrentObjectText = appUIDriver.getTextByContent(objectLocatorValue);							
						BlnSwipeFound = true;

					}
					catch (Exception e)
					{
						BlnSwipeFound = false;
						driver.swipe(x, starty, x, endy, 500);

						loopCounter = loopCounter +1 ;
						if (loopCounter>10)
						{
							BlnSwipeFound = true;
						}
					}
				}
			}
			while (!BlnSwipeFound);

			// After Successfully loop , the value will check

			if (CurrentObjectText.equalsIgnoreCase(strExpectedData))
			{

				HtmlResult.passed("verify the current content of app ", "App content should be - "+ strExpectedData + " for the object -" + strObjectName , "Actual content is - " + CurrentObjectText);
			}
			else
			{
				HtmlResult.failed("verify the current content of app ", "App content should be - "+ strExpectedData + " for the object -" + strObjectName , "Actual content is not - " + CurrentObjectText);
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
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

	/***************************************************************************************************************
	 * Method Name -
	 * Project - GMA Method
	 * Method Name �verifyObjectOnScreen
	 * Method Description -
	 * Return Type - 
	 * Parameters - Inputs are fetched from map (inputs are)
	 * Framework - UKIT Master Framework
	 * Author - Mritunjay
	 * Creation Date - 11/05/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>
	 * @return

	 ***************************************************************************************************************/
	public void verifyObjectOnScreen(Map<String,String> input)
	{
		try
		{
			String strObjectName =input.get("mobileObjectName");
			String strexpectedData = "";
			if (strObjectName!=null && strObjectName.length()>1)
			{
				if (appUIDriver.scrollToObjectFound(strObjectName))
				{
					strexpectedData = appUIDriver.getObjectText(strObjectName);
					if (strexpectedData.equalsIgnoreCase(""))
					{
						strexpectedData =input.get("expectedData");
					}
					HtmlResult.passed("verify the object displayed ", "Object should be displayed--"+strObjectName,"Object is displayed-- '"+strexpectedData+"'.");
				}
				else
				{
					HtmlResult.failed("verify the object displayed ", "Object should be displayed--"+strObjectName,"Object is not displayed--"+strexpectedData);
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
	}

	/***************************************************************************************************************
	 * Method Name -
	 * Project - GMA Method
	 * Method Name � verifyCheckUncheck
	 * Method Description -
	 * Return Type - 
	 * Parameters - Inputs are fetched from map (inputs are)
	 * Framework - UKIT Master Framework
	 * Author - Mritunjay
	 * Creation Date - 11/05/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>
	 * @return

	 ***************************************************************************************************************/
	public void verifyCheckUncheck(Map<String,String> input){
		try
		{
			String strattributeName;
			String strObjectName =input.get("mobileObjectName");						
			String strexpectedData = input.get("expectedData");			
			String strOSType = EggPlant.getValueFromExcel("OS_TYPE");
			String currentOS=strOSType;
			if (currentOS.equalsIgnoreCase("ios"))
		{	
			strattributeName = input.get("iosattributeName");
			String CheckBoxVaue =driver.findElement(By.className("XCUIElementTypeSwitch")).getAttribute(strattributeName);			
			
			if (CheckBoxVaue.equals("1"))
			{
				
				HtmlResult.passed("verify the object checked " + strexpectedData, "Object should be checked","Check box is" +strexpectedData);	
			}
		
			else
			{
				HtmlResult.failed("verify the object checked " + strexpectedData, "Object should be checked","Check box is" +strexpectedData);		
			}
		} 
			else{
				strattributeName = input.get("androidattributeName");
			String ObjectattributeValue =appUIDriver.objectGetattribute(strObjectName, strattributeName);

			if(ObjectattributeValue.equalsIgnoreCase("false")){
				HtmlResult.failed("verify the object checked " + strexpectedData, "Object should be checked","Check box is--" +strexpectedData);	
			}
			else if(ObjectattributeValue.equalsIgnoreCase("true")){
				HtmlResult.passed("verify the object checked " + strexpectedData, "Object should be checked","Check box is--" +strexpectedData);	
			}
		
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
	}



	/***************************************************************************************************************
	 * Method Name -
	 * Project - GMA Method
	 * Method Name � verifyTextAndClickAppObject
	 * Method Description -
	 * Return Type - 
	 * Parameters - Inputs are fetched from map (inputs are)
	 * Framework - UKIT Master Framework
	 * Author - Prateek
	 * Creation Date - 11/02/2017
	 * Modification History:
	 * # <Date>     <Who>      Prateek Gupta        - Change as per actual     <Mod description>
	 * @return

	 ***************************************************************************************************************/
	public boolean verifyTextAndClickAppObject(Map<String,String> input)

	{
		try
		{
			String objectLocatorName;
			String objectLocatorValue;

			String strObjectName =input.get("mobileObjectName");
			String strExpectedData = input.get("expectedData");
			//String strIosExpectedData = input.get("IosExpectedData");
			
			//String strOSType = EggPlant.getValueFromExcel("OS_TYPE");
			//String currentOS=strOSType;
			//if (currentOS.equalsIgnoreCase("ios")) { 
			//	Thread.sleep(7000);
				
				/* String strMobileObjectName  = appUIDriver.getAppObjectwithLocator("gma4_Barclay_Chkerror_Dismiss");
				if (!strMobileObjectName.equalsIgnoreCase("false"))
				{
					String CurrentObjectText1 = driver.switchTo().alert().getText();
					driver.switchTo().alert().accept();
					HtmlResult.passed("verify the current content of app ", "App content should be - "+ strIosExpectedData + " for the object -" + strObjectName , "Actual content is - " + CurrentObjectText1);
					return true;
				} 
				
				else
				{ 
				HtmlResult.failed("verify the current content of app ", "App content should be - "+ strIosExpectedData + " for the object -" + strObjectName , "Actual content is not matched");}
				return false;
				}
				
				*/
			
			
			String strMobileObjectName  = appUIDriver.getAppObjectwithLocator(strObjectName);

			if (strMobileObjectName!=null && strMobileObjectName.length()>1)
			{

				String[] arrMobileObjectName = strMobileObjectName.split("#");

				objectLocatorName =arrMobileObjectName[0].toString();
				objectLocatorValue= arrMobileObjectName[1].toString();

				String CurrentObjectText = appUIDriver.getText(objectLocatorName, objectLocatorValue);

				if (CurrentObjectText.equalsIgnoreCase(strExpectedData))
				{
					appUIDriver.clickText(objectLocatorName, objectLocatorValue);
					HtmlResult.passed("verify the current content of app ", "App content should be - "+ strExpectedData + " for the object -" + strObjectName , "Actual content is - " + CurrentObjectText);
					return true;

				}
				else
				{
					System.out.println("FAIL");
					HtmlResult.failed("verify the current content of app ", "App content should be - "+ strExpectedData + " for the object -" + strObjectName , "Actual content is not - " + CurrentObjectText);
					return false;
				}
			}

			else
			{
				HtmlResult.failed("verify the current content of app ", "App content should be - "+ strExpectedData + " for the object -" + strObjectName , "Actual content is not - " + strObjectName);
				return false;
			}
		}
		
		catch (Exception e)
		{
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
			return false;

		}

	}


	/***************************************************************************************************************
	 * Method Name -
	 * Project - GMA Method
	 * Method Name � closeApp
	 * Method Description -
	 * Return Type - 
	 * Parameters - Inputs are fetched from map (inputs are)
	 * Framework - UKIT Master Framework
	 * Author - Prateek
	 * Creation Date - 11/02/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>
	 * @return

	 ***************************************************************************************************************/
	public void closeApp()
	{
		try {
			if(appUIDriver.closeApp()){
				HtmlResult.passed("To close application", "Application should be closed successfully", "Application closed successfully");
			}
			else
			{
				HtmlResult.failed("To close application", "Application should be closed successfully", "Application is not closed successfully");
			}
		} catch (Exception e)
		{
			HtmlResult.failed("To close application", "Application should be closed successfully", "Application is not closed successfully");
			System.err.println("Error");
		}
	}

	/***************************************************************************************************************
	 * Method Name -
	 * Project - GMA Method
	 * Method Name � GMA 5 validation method
	 * Method Description - This method will add a non unit activity in fixed shifts
	 * Return Type - Boolean value
	 * Parameters - Inputs are fetched from map (inputs are)
	 * Framework - UKIT Master Framework
	 * Author - Asif
	 * Creation Date - 09/25/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>
	 * @return

	 ***************************************************************************************************************/

	public void cardExpiryDateEntry(Map<String,String> input)
	{
		try 
		{
			String strMonth = input.get("ExpMonth");
			String strYear = input.get("ExpYear");

			Thread.sleep(2000);
			appUIDriver.clickButton("gma_BarClays_expiryMonth");
			appUIDriver.clickMobileDynamicAppObject("gma_BarClays_expiryMonth_value", strMonth);
			appUIDriver.clickButton("gma_BarClays_expiryYear");
			appUIDriver.clickMobileDynamicAppObject("gma_BarClays_expiryYear_value", strYear);

		}
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
	}

	/***************************************************************************************************************
	 * Method Name -
	 * Project - GMA Method
	 * Method Name � GMA 5 validation method
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
	public void verifyAppObjectInformation(Map<String,String> input)
	{
		try
		{
			String strObjectName =input.get("mobileObjectName");
			String strExpectedData = input.get("expectedData");
			String CurrentObjectText="";
			strObjectName = strObjectName.trim();
			strExpectedData = strExpectedData.trim();

			boolean BlnSwipeFound = false;
			int loopCounter=0;

			do
			{
				Thread.sleep(1000);
				//String strScrollScreen =input.get("scrollScreen");
				String strMobileObjectName  = appUIDriver.getAppObjectwithLocator(strObjectName);

				if (strMobileObjectName.contains("false"))
				{
					System.out.println("object not found "+strMobileObjectName);
					return;
					
				}
				else
				{
					String[] arrMobileObjectName = strMobileObjectName.split("#");
					String objectLocatorName =arrMobileObjectName[0].toString();
					String objectLocatorValue= arrMobileObjectName[1].toString();
					Dimension CurrentMobileSize = driver.manage().window().getSize();
					System.out.println(CurrentMobileSize);
					// Current screen size from mobile devices -
					height = CurrentMobileSize.getHeight();
					width = CurrentMobileSize.getWidth();
					x=width/2;
					starty = (int) (height*0.40);
					endy= (int) (width*0.20);
					try
					{
						CurrentObjectText = appUIDriver.getText(objectLocatorName, objectLocatorValue);

						//BlnSwipeFound = driver.findElement(By.xpath("//android.widget.TextView[@text='"+strLogin+"'and @index='0' and @resource-id='com.mcdonalds.app.uk.qa:id/save']")).isDisplayed();
						BlnSwipeFound = true;
						//driver.findElement(By.xpath("//android.widget.TextView[@text='"+strLogin+"'and @index='0'and @resource-id='com.mcdonalds.app.uk.qa:id/save']")).click();

					}
					catch (Exception e)
					{
						BlnSwipeFound = false;
						driver.swipe(x, starty, x, endy, 500);

						loopCounter = loopCounter +1 ;
						if (loopCounter>10)
						{
							BlnSwipeFound = true;
						}
					}
				}
			}
			while (!BlnSwipeFound);

			// After Successfully loop , the value will check

			if (CurrentObjectText.equalsIgnoreCase(strExpectedData))
			{
				System.out.println("PASS");
				HtmlResult.passed("verify the current content of app ", "App content should be - "+ strExpectedData + " for the object -" + strObjectName , "Actual content is - " + CurrentObjectText);
			}
			else
			{
				System.out.println("FAIL");
				HtmlResult.failed("verify the current content of app ", "App content should be - "+ strExpectedData + " for the object -" + strObjectName , "Actual content is not - " + CurrentObjectText);
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
	}

	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � DeleteAllItemsFromBasket
	 * Method Description - This method will delete all the items from Basket
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Madhur Barsainya
	 * Creation Date - 11/02/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/

	public void DeleteAllItemsFromBasket(Map<String, String> input)
	{
		try
		{
			boolean currentFlagValue = false;
			String strObjProperty = appUIDriver.getObjectProperty("gma4_Ordering_SubProd_AddToBadge");

			//if (appUIDriver.getAppObjectFound("gma4_Ordering_SubProd_AddToBadge"))
			if(!strObjProperty.equalsIgnoreCase("false"))
			{
				currentFlagValue = appUIDriver.clickButton("gma4_Ordering_SubProd_AddToBadge");
				if (currentFlagValue)
				{
					Thread.sleep(3000);
					currentFlagValue = appUIDriver.clickButton("gma4_Edit_ChkOutScreen_Btn");
					if (currentFlagValue) 
					{
						currentFlagValue = appUIDriver.clickButton("gma4_DeleteOrder_ChkOutScreen_Btn");
						if (currentFlagValue)
						{
								currentFlagValue = appUIDriver.clickButton("gma4_Delete_PopUp_ChkOutScreen_Btn");
								if (currentFlagValue)
								{
									
									 if (!(appUIDriver.getAppObjectFound("gma4_Ordering_SubProd_AddToBadge"))) 
									 {
										 HtmlResult.passed("Deleting All items present in the Basket.", "All the items present in the Basket should be deleted.","All the items present in the Basket are deleted.");
									 }
									 else
									 { 
										 HtmlResult.failed("Deleting All items present in the Basket.","All the items present in the Basket should be deleted.", "Failed to delete items present in the Basket."); 
									 }
								}
								else
								{
									HtmlResult.failed("Clicking on 'Confirm Delete' button on Order Basket screen.", "User should be able to click on 'Confirm Delete' button on Order Basket screen.", "Unable to click on 'Confirm Delete' button on Order Basket screen.");
								}
						}
						else
						{
							HtmlResult.failed("Clicking on 'Delete Order' button on Order Basket screen.", "User should be able to click on 'Delete Order' button on Order Basket screen.", "Unable to click on 'Delete Order' button on Order Basket screen.");
						}
					}
					else
					{
						HtmlResult.failed("Clicking on 'Edit' button on Order Basket screen.", "User should be able to click on 'Edit' button on Order Basket screen.", "Unable to click on 'Edit' button on Order Basket screen.");
					}

				}
				else
				{
					HtmlResult.failed("Clicking on Basket Icon.", "User should be able to click on Basket Icon.", "Unable to click on Basket Icon.");
				}
				
			}
			else
			{
				HtmlResult.passed("Deleting All items from Basket.","All the items present in the Basket should be deleted.", "There are no items present in the Basket.");
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
	}

	/***************************************************************************************************************
	 * 	// Method Name -
	 * Project - GMA Method
	 * Method Name � scrollverifyContentDesc
	 * Method Description - This method will scroll and verify object should not be there
	 * Return Type - Boolean value
	 * Parameters - Inputs object text
	 * Framework - UKIT Master Framework
	 * Author - Asif Mustafa
	 * Creation Date - 11/03/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/	
	public void ScrollverifyObjectNotFound(Map<String,String> input){

		try{
			String strObjectName =input.get("mobileObjectName");
			String strExpectedData = input.get("ExpectedData");
			boolean objectFound = appUIDriver.scrollToObjectFound(strObjectName);
			if(!objectFound){
				HtmlResult.passed("Verify Object is not displayed","Object should not be displayed '"+strExpectedData+"'.","Object is not displayed '"+strExpectedData+"'.");
			}
			else{
				HtmlResult.failed("Verify Object is not displayed","Object should not be displayed '"+strExpectedData+"'.","Object is displayed '"+strExpectedData+"'.");
			}


		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}

	}
	/***************************************************************************************************************
	 * 	// Method Name -
	 * Project - GMA Method
	 * Method Name � scrollverifyText
	 * Method Description - This method will scroll and text
	 * Return Type - Boolean value
	 * Parameters - Inputs object text
	 * Framework - UKIT Master Framework
	 * Author - Asif Mustafa
	 * Creation Date - 11/03/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/		
	public void ScrollverifyText(Map<String,String> input){

		try{
			String strObjectName =input.get("mobileObjectName");
			String strExpectedData = input.get("ExpectedData");
			String CurrentObjectText="";
			strObjectName = strObjectName.trim();
			strExpectedData = strExpectedData.trim();
			boolean objectFound = appUIDriver.scrollToObjectFound(strObjectName);

			if(objectFound){
				Thread.sleep(1000);
				//String strScrollScreen =input.get("scrollScreen");
				String strMobileObjectName  = appUIDriver.getAppObjectwithLocator(strObjectName);
				String[] arrMobileObjectName = strMobileObjectName.split("#");
				String objectLocatorName =arrMobileObjectName[0].toString();
				String objectLocatorValue= arrMobileObjectName[1].toString();
				CurrentObjectText = appUIDriver.getText(objectLocatorName, objectLocatorValue);
				if (CurrentObjectText.equalsIgnoreCase(strExpectedData))
				{
					System.out.println("PASS");
					HtmlResult.passed("verify the current content of app ", "App content should be - "+ strExpectedData + " for the object -" + strObjectName , "Actual content is - " + CurrentObjectText);
				}
				else
				{
					System.out.println("FAIL");
					HtmlResult.failed("verify the current content of app ", "App content should be - "+ strExpectedData + " for the object -" + strObjectName , "Actual content is not - " + CurrentObjectText);
				}


			}
			else{
				HtmlResult.failed("Verify Object is not displayed","Object should not be displayed"+strExpectedData,"Object is displayed"+strExpectedData);
			}


		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}

	}

	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � gma4_EnterCardDetails_BarclaysPage
	 * Method Description - Entering Card details on baryalas page
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Madhur Barsainya
	 * Creation Date - 11/15/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/

	public void gma4_EnterCardDetails_BarclaysPage(Map<String, String> input)
	{
		try
		{
			String strOSType = EggPlant.getValueFromExcel("OS_TYPE");
			String strCardNumber = input.get("CardNumber");
			String strCardHolderName= input.get("CardHolderName");
			String strExpMonth = input.get("ExpMonth");
			String strExpYear = input.get("ExpYear");
			String strCVV = input.get("CVV");
			String strRememberCardDetails = input.get("RememberCardDetails");
			String CheckBoxVaue ;
			boolean currentFlagValue = false;
			
			String currentOS=strOSType;

			if (currentOS.equalsIgnoreCase("ios"))
			{
				Thread.sleep(15000);
				boolean blnMonth = false;
				
				String strMobileObjectName  = appUIDriver.getAppObjectwithLocator("gma4_Barclay_RemoveCard");
				if(!strMobileObjectName.equalsIgnoreCase("false"))
				{
					appUIDriver.clickButton("gma4_Barclay_RemoveCard");
					Thread.sleep(5000);
				}
				// for data entry in Card field
				@SuppressWarnings("unchecked")
				List<WebElement> cardField = driver.findElements(By.className("XCUIElementTypeOther"));
				{
					Thread.sleep(5000);
					// added thread sleep took some much time to load the page sagar 
					int i =0;
					System.out.println(cardField.size());

					for ( i=0;i<cardField.size();i++)
					{
						System.out.println(cardField.get(i).getLocation());
						Point currentLocation = cardField.get(i).getLocation();
						if ((currentLocation.x==26 && currentLocation.y==379) || (currentLocation.x==23 && currentLocation.y==306)|| (currentLocation.x==34 && currentLocation.y==346))
							
						{
							if(!blnMonth)
							{
								cardField.get(i).click();
								// cardField.get(i).sendKeys("09");
								//Scroll scroll = new ScrollAction(touchScreen, xOffset, yOffset
								
								@SuppressWarnings("unchecked")
								List<WebElement> wheels = driver.findElements(By.className("XCUIElementTypePickerWheel"));
								WebElement wheelelement = wheels.get(0);
								wheelelement.sendKeys(strExpMonth);
								driver.findElementByAccessibilityId("Done").click();
								blnMonth = true;
								Thread.sleep(8000);
							}
						}

						if ((currentLocation.x==117 && currentLocation.y==379) || (currentLocation.x==100 && currentLocation.y==306)||(currentLocation.x==111 && currentLocation.y==346))
						{
							cardField.get(i).click();
							// cardField.get(i).sendKeys("2018");
							//Scroll scroll = new ScrollAction(touchScreen, xOffset, yOffset)
							List<WebElement> wheels1 = driver.findElements(By.className("XCUIElementTypePickerWheel"));
							WebElement wheelelement1 = wheels1.get(0);
							wheelelement1.sendKeys(strExpYear);
							driver.findElementByAccessibilityId("Done").click();
							break;
						}
					}

				}

				// for data entry in text field
				List<WebElement> abc = driver.findElements(By.className("XCUIElementTypeTextField"));
				{
					int i;
					System.out.println(abc.size());

					for ( i=0;i<abc.size();i++)
					{
						if (i==0)
						{
							abc.get(i).sendKeys(strCardNumber);
						}
						else if (i==1)
						{
							abc.get(i).sendKeys(strCardHolderName);
						}
						else if (i==2)
						{
							abc.get(i).sendKeys(strCVV);
						}
					}

				}
			
				
				
				if (strRememberCardDetails.equalsIgnoreCase("YES"))
				{
					CheckBoxVaue =driver.findElement(By.className("XCUIElementTypeSwitch")).getAttribute("value");			
					//return strAttributeValue;
					//String CheckBoxVaue = appUIDriver.objectGetattribute("gma_BarClays_ChkBoxCondition", "value");
					if (CheckBoxVaue.equals("0"))
					{
						driver.findElement(By.className("XCUIElementTypeSwitch")).click();
						//appUIDriver.clickButton("gma_BarClays_ChkBoxCondition");
					}
					CheckBoxVaue = "checked";
				}
				else
				{	
					CheckBoxVaue =driver.findElement(By.className("XCUIElementTypeSwitch")).getAttribute("value");
					//String CheckBoxVaue = appUIDriver.objectGetattribute("gma_BarClays_ChkBoxCondition", "value");
					if (CheckBoxVaue.equals("1"))
					{
						driver.findElement(By.className("XCUIElementTypeSwitch")).click();
						//appUIDriver.clickButton("gma_BarClays_ChkBoxCondition");
					}
					CheckBoxVaue = "unchecked";
				}
				
				//driver.findElement(By.className("XCUIElementTypeSwitch")).click();
				//driver.findElement(By.xpath("//XCUIElementTypeButton[@name='Submit'")).click();
				//driver.findElementByXPath("//XCUIElementTypeButton[@name='Submit'").click();
				driver.findElementByAccessibilityId("Submit").click();
				Thread.sleep(8000);
				HtmlResult.passed("Entering Card Details", "Should be able to enter card details.", 
						"Card details entered successfully with Card number as '"+strCardNumber+"', Card holder name as '"+strCardHolderName+
						"', Card expiration month and year as '"+strExpMonth+"/"+strExpYear+"' and CVV as '"+strCVV+"' and 'Remember this Card' checkbox is '"+CheckBoxVaue+"'.");
			}
			else
			{
				currentFlagValue = appUIDriver.getAppObjectFound("gma_BarClays_CardNumber");
				if (currentFlagValue)
				{
					appUIDriver.enterText("gma_BarClays_CardNumber", strCardNumber);
					appUIDriver.enterText("gma_BarClays_CardHolderName", strCardHolderName);
					appUIDriver.backbuttonPress();
					cardExpiryDateEntry(input);
					appUIDriver.enterText("gma_BarClays_CVC", strCVV);
					appUIDriver.backbuttonPress();

					if (strRememberCardDetails.equalsIgnoreCase("YES"))
					{
						CheckBoxVaue = appUIDriver.objectGetattribute("gma_BarClays_ChkBoxCondition", "checked");
						if (CheckBoxVaue.equalsIgnoreCase("false"))
						{
							appUIDriver.clickButton("gma_BarClays_ChkBoxCondition");
						}
						CheckBoxVaue = "checked";
					}
					else
					{
						CheckBoxVaue = appUIDriver.objectGetattribute("gma_BarClays_ChkBoxCondition", "checked");
						if (CheckBoxVaue.equalsIgnoreCase("true"))
						{
							appUIDriver.clickButton("gma_BarClays_ChkBoxCondition");
						}
						CheckBoxVaue = "unchecked";
					}
					appUIDriver.clickButton("gma_BarClays_Submit");
					Thread.sleep(8000);
					HtmlResult.passed("Entering Card Details", "Should be able to enter card details.", 
							"Card details entered successfully with Card number as '"+strCardNumber+"', Card holder name as '"+strCardHolderName+
							"', Card expiration month and year as '"+strExpMonth+"/"+strExpYear+"', CVV as '"+strCVV+"' and 'Remember this Card' checkbox is '"+CheckBoxVaue+"'.");
				
				}
				else
				{
					HtmlResult.failed("Entering Payment Details.", "User should be able to enter payment details.", "Payment Details Page is not loaded.");
				}
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
	}


	//Madhur
	public void clickDynamicObject(Map<String,String> input)
	{
		try {
			String strObjectName = input.get("mobileObjectName");
			String strDynamicObjectName;
			strDynamicObjectName = input.get("DynamicObjectName").trim();
			boolean currentFlagValue = false;

			if (strDynamicObjectName != null && strDynamicObjectName.length() > 1) {
				currentFlagValue = appUIDriver.clickMobileDynamicAppObject(strObjectName,strDynamicObjectName);
				if (!currentFlagValue) 
				{
					//System.out.println("failed to click on '"+strObjectName+"' object.");
					HtmlResult.failed("Perform Click operation","Click operataion should be performed on--"+strObjectName,"failed to click on '"+strObjectName+"' object.");
				}
			}
		} 
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
	}


	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � gma4_VerifyDynamicObjectPresent
	 * Method Description - to verify if object is present
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Madhur Barsainya
	 * Creation Date - 11/02/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/
	public void gma4_VerifyDynamicObjectPresent(Map<String,String> input)
	{
		try
		{
			String strObjectName =input.get("mobileObjectName");
			//input.get("custUid").trim();
			String strItemName; 

			SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
			Date date = new Date();

			// UKTime1 and UKTime2 check breakfast and lunch time
			String shopTime = parser.format(date);
			String ISTStartTime=EggPlant.getValueFromExcel("StartTime");
			String ISTEndTime=EggPlant.getValueFromExcel("EndTime");
			Date UKTime1 = parser.parse(ISTStartTime); //Denotes Start breakfast UK time UK 
			Date UKTime2 = parser.parse(ISTEndTime); //Denotes end breakfast UK time UK
			Date userDate = parser.parse(shopTime);

			if (userDate.after(UKTime1) && userDate.before(UKTime2)) 
			{
				strItemName =input.get("BrkfstPrdName").trim();
			}
			else
			{
				strItemName =input.get("LunchPrdName").trim();
			}

			if (appUIDriver.scrollToObjectFoundDynamic(strObjectName,strItemName))
			{
				HtmlResult.passed("Verify Dynamic Object.", "Object should be present.", "Object is present.");
			}
			else
			{
				HtmlResult.failed("Verify Dynamic Object.", "Object should be present.", "Object is not present.");
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
	}	


	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � SelectProduct
	 * Method Description - 
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Madhur Barsainya
	 * Creation Date - 11/02/2017
	 * Modification History: Completed
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/
	public void SelectProduct(Map<String,String> input)
	{
		try{
			String strMainProjectObject = "gma4_Ordering_Product_Menu";
			String strSubProjectObject = "gma4_Ordering_SubProduct_Menu";
			String strAllProducts;
			boolean currentFlagValue = false;

			SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
			Date date = new Date();

			// UKTime1 and UKTime2 check breakfast and lunch time
			String ISTStartTime=EggPlant.getValueFromExcel("StartTime");
			String ISTEndTime=EggPlant.getValueFromExcel("EndTime");
			String shopTime = parser.format(date);
			Date UKTime1 = parser.parse(ISTStartTime); //Denotes Start breakfast UK time UK 
			Date UKTime2 = parser.parse(ISTEndTime); //Denotes end breakfast UK time UK
			Date userDate = parser.parse(shopTime);

			if (userDate.after(UKTime1) && userDate.before(UKTime2)) 
			{
				strAllProducts =input.get("BrkfstProduct").trim(); // eg - Milkshakes#Vanilla Milkshake
			}
			else
			{
				strAllProducts =input.get("LunchProduct").trim(); // eg - Burger#Big Mac
			}
			String[] arrAllProducts = strAllProducts.split("#");

			currentFlagValue= appUIDriver.clickButton("gma4_hamburger_action");
			if (currentFlagValue)
			{
				appUIDriver.clickButton("gma4_Menu_Home");
				appUIDriver.clickButton("gma4_hamburger_action");
				currentFlagValue = appUIDriver.clickButton("gma4_Menu_StartAnOrder"); // navigating to ordering page
				if (currentFlagValue)
				{
					currentFlagValue = appUIDriver.clickMobileDynamicAppObject(strMainProjectObject, arrAllProducts[0]); //Selecting Product Category
					if (currentFlagValue)
					{
						currentFlagValue = appUIDriver.clickMobileDynamicAppObject(strSubProjectObject, arrAllProducts[1]); //Selecting Product
						if (currentFlagValue)
						{
							HtmlResult.passed("Selecting Product.", "Product '"+arrAllProducts[1]+"' should be selected.", "Product '"+arrAllProducts[1]+"' is selected.");
						}
						else
						{
							HtmlResult.failed("Selecting Product Category.", "Product '"+arrAllProducts[1]+"' should be selected.", "Product '"+arrAllProducts[1]+"' is not present.");
						}
					}
					else
					{
						HtmlResult.failed("Selecting Product Category.", "Product Category '"+arrAllProducts[0]+"' should be selected.", "Product Category '"+arrAllProducts[0]+"' is not present.");
					}
				}
				else
				{
					HtmlResult.failed("Clicking Start an Order from Hemburger menu.", "User should be able to click on Start An Order from Hamburger Menu.", "Unable to click on Start An Order from Hamburger Menu.");
				}
			}
			else
			{
				HtmlResult.failed("Clicking on 'Ordering' button on Home Page.", "User should be able to click on 'Ordering' button on Home Page.", "Unable to click on 'Ordering' button on Home Page.");
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
	}


	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � SelectMultipleAlacarteItem
	 * Method Description - 
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Madhur Barsainya
	 * Creation Date - 11/02/2017
	 * Modification History: Completed
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/
	public void SelectMultipleAlacarteItem(Map<String,String> input)
	{
		try{
			String strMainProjectObject = "gma4_Ordering_Product_Menu";
			String strSubProjectObject = "gma4_Ordering_SubProduct_Menu";
			String strAllProducts;
			boolean currentFlagValue = false;

			SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
			Date date = new Date();

			// UKTime1 and UKTime2 check breakfast and lunch time
			String ISTStartTime=EggPlant.getValueFromExcel("StartTime");
			String ISTEndTime=EggPlant.getValueFromExcel("EndTime");
			String shopTime = parser.format(date);
			Date UKTime1 = parser.parse(ISTStartTime); //Denotes Start breakfast UK time UK 
			Date UKTime2 = parser.parse(ISTEndTime); //Denotes end breakfast UK time UK
			Date userDate = parser.parse(shopTime);

			if (userDate.after(UKTime1) && userDate.before(UKTime2)) 
			{
				strAllProducts =input.get("BrkfstProduct").trim();  // eg - Milkshakes#Vanilla Milkshake$Burger#Big Mac
			}
			else
			{
				strAllProducts =input.get("LunchProduct").trim();  // eg - Milkshakes#Vanilla Milkshake$Burger#Big Mac
			}

			String[] arrAllProducts = strAllProducts.split("\\$");
			for (String strCurrentProduct : arrAllProducts)
			{
				String[] arrProduct = strCurrentProduct.split("#"); 
				Thread.sleep(2000);
				currentFlagValue= appUIDriver.clickButton("gma4_hamburger_action");
				if (currentFlagValue)
				{Thread.sleep(1000);
					currentFlagValue = appUIDriver.clickButton("gma4_Menu_Home");
					if(currentFlagValue)
					{
						currentFlagValue = appUIDriver.clickButton("gma4_hamburger_action");
						if(currentFlagValue)
						{
							currentFlagValue = appUIDriver.clickButton("gma4_Menu_StartAnOrder"); // navigating to ordering page
							if (currentFlagValue)
							{Thread.sleep(3000);
								currentFlagValue = appUIDriver.clickMobileDynamicAppObject(strMainProjectObject, arrProduct[0]); //Selecting Product Category
								if (currentFlagValue)
								{Thread.sleep(1000);
									currentFlagValue = appUIDriver.clickMobileDynamicAppObject(strSubProjectObject, arrProduct[1]); //Selecting Product	
									if (currentFlagValue)
									{
										currentFlagValue = appUIDriver.clickButton("gma4_Ordering_SubProd_AddBasket"); //Adding product to Basket
										if (currentFlagValue)
										{
											HtmlResult.passed("Selecting Product.", "Category - '"+arrProduct[0] +"' & Product - '"+arrProduct[1]+"' should be added to Basket.", "Product '"+arrProduct[1]+"' is added to Basket.");
										}
										else
										{
											HtmlResult.failed("Selecting Product Category.", "Category - '"+arrProduct[0] +"' & Product - '"+arrProduct[1]+"' should be added to Basket.", "Product '"+arrProduct[1]+"' is not present.");
										}
									}
									else
									{
										HtmlResult.failed("Selecting Product Category.", "Product '"+arrProduct[1]+"' should be selected.", "Product '"+arrProduct[0]+"' is not present.");
									}
								}
								else
								{
									HtmlResult.failed("Selecting Product Category.", "Product Category '"+arrProduct[0]+"' should be selected.", "Product Category '"+arrProduct[0]+"' is not present.");
								}
							}
							else
							{
								HtmlResult.failed("Clicking Start an Order from Hemburger menu.", "User should be able to click on Start An Order from Hamburger Menu.", "Unable to click on Start An Order from Hamburger Menu.");
							}
						}
						else
						{
							HtmlResult.failed("Clicking Home from Hemburger menu.", "User should be able to click on Home from Hamburger Menu.", "Unable to click on Home from Hamburger Menu.");
						}
					}
					else
					{
						HtmlResult.failed("Clicking Home from Hemburger menu.", "User should be able to click on Home from Hamburger Menu.", "Unable to click on Home from Hamburger Menu.");
					}
				}
				else
				{
					HtmlResult.failed("Clicking Hemburger Menu.", "User should be able to click on Hemburger Menu.", "Unable to click on Hemburger Menu.");
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
	}


	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � gma4_AddToBasket
	 * Method Description - To add the product to basket
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Madhur Barsainya
	 * Creation Date - 11/20/2017
	 * Modification History: Completed
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/

	public void gma4_AddToBasket()
	{
		try
		{
			boolean currentFlagValue = false;
			currentFlagValue = appUIDriver.clickButton("gma4_Ordering_SubProd_AddBasket");
			if (currentFlagValue)
			{
				//Verifying Basket Icon is present
				currentFlagValue = appUIDriver.scrollToObjectFound("gma4_Ordering_SubProd_AddToBadge");
				if (currentFlagValue)
				{
					HtmlResult.passed("Add product to Basket.", "Product should be added to Basket.", "Product is successfully added to the Basket.");
				}
				else
				{
					HtmlResult.failed("Add product to Basket.", "Product should be added to Basket.", "Product is not added to the Basket.");
				}
			}
			else
			{
				HtmlResult.failed("Add product to Basket.", "Add to Basket should be clicked.", "Failed to click on Add to Basket.");
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
	}

	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � gma4_ClickOnBasket
	 * Method Description - Clicking  on Basket to navigate to Order Basket screen
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Madhur Barsainya
	 * Creation Date - 11/20/2017
	 * Modification History: Completed
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/

	public boolean gma4_ClickOnBasketIcon()
	{
		try
		{
			boolean currentFlagValue = false;
			currentFlagValue = appUIDriver.clickButton("gma4_Ordering_SubProd_AddToBadge");
			if (currentFlagValue)
			{
				//Verifying Order Summary screen
				currentFlagValue = appUIDriver.scrollToObjectFound("gma4_Ordering_CheckOut");
				if (currentFlagValue)
				{
					HtmlResult.passed("Clicking on Basket Icon.", "Clicking on 'Basket Icon' and user should be navigated to 'Order Basket' screen.", "Clicked on 'Basket Icon' and navigated to 'Order Basket' screen.");
					return true;
				}
				else
				{
					HtmlResult.failed("Clicking on Basket Icon.", "User should be navigated to 'Order Basket' screen.", "Failed to navigate to 'Order Basket' screen.");
					return false;
				}
			}
			else
			{
				currentFlagValue = appUIDriver.scrollToObjectFound("gma4_Ordering_CheckOut");
				if (currentFlagValue)
				{
					HtmlResult.warning("Clicking on Basket Icon.", "Clicking on 'Basket Icon' and user should be navigated to 'Order Basket' screen.", "User is already on 'Order Basket' screen.");
					return true;
				}
				else
				{
					HtmlResult.failed("Clicking on Basket Icon.", "Basket Icon should be clicked.", "Unable to click on Basket Icon.");
					return false;
				}
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
			return false;
		}
	}

	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � gma4_ClickOnCheckOut
	 * Method Description - Clicking  on Check-Out 
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Madhur Barsainya
	 * Creation Date - 11/20/2017
	 * Modification History: Completed
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/

	public boolean gma4_ClickOnCheckOut()
	{
		try
		{
			boolean currentFlagValue = false;
			currentFlagValue = appUIDriver.clickButton("gma4_Ordering_CheckOut");
			if (currentFlagValue)
			{
				//Verifying Order Check-Out screen
				currentFlagValue = appUIDriver.scrollToObjectFound("gma4_Ordering_ChoosePayment");
				if (currentFlagValue)
				{
					HtmlResult.passed("Clicking on Product check out button.", "Clicking on 'Check Out' and user should be navigated Check-Out screen.", "Clicked on 'Check-Out' button and navigated Check-Out screen.");
					return true;
				}
				else
				{
					HtmlResult.failed("Clicking on Product check out button.", "User should be navigated Check-Out screen.", "Failed to navigate to Check-Out screen.");
					return false;
				}
			}
			else
			{
				HtmlResult.failed("Clicking on Product check out button.", "Product 'Check-Out' button should be clicked.", "Failed to click on Product 'Check-Out' button.");
				return false;
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
			return false;
		}
	}


	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � gma4_SelectPaymentType
	 * Method Description - Select Payment Type 
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Madhur Barsainya
	 * Creation Date - 11/20/2017
	 * Modification History: Completed
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/

	public boolean gma4_SelectPaymentType(Map<String, String> input)
	{
		try
		{
			boolean currentFlagValue = false;
			String strOSType = EggPlant.getValueFromExcel("OS_TYPE");
			String 	strAddNewCard = input.get("AddNewCard");
			currentFlagValue = appUIDriver.clickButton("gma4_CheckOut_PayWith");
			if (currentFlagValue)
			{
				//Verifying whether to click on 'New Card' or 'Saved Card' 
				if (strAddNewCard.equalsIgnoreCase("YES"))
				{
					currentFlagValue = appUIDriver.clickButton("gma4_CheckOut_PayWithNewCard");
					if (!currentFlagValue)
					{
						HtmlResult.failed("Clicking on 'Pay With Card' payment option.", "User should be able to click on 'Pay With Card' payment option.", "Unable to click on 'Pay With Card' payment option.");
						return false;
					}
					else{
						HtmlResult.passed("Clicking on 'Pay With Card' payment option.", "User should be able to click on 'Pay With Card' payment option.", " 'Pay With Card' is clicked.");
					
					}
				}
				else
				{
					currentFlagValue = appUIDriver.clickButton("gma4_SavedCard_SelectPayment");
					if (!currentFlagValue)
					{
						HtmlResult.failed("Clicking on 'Save Card' payment option.", "User should be able to click on 'Save Card' payment option.", "Unable to click on 'Save Card' payment option.");
						return false;
					}
					else{
						HtmlResult.passed("Clicking on 'Save Card' payment option.", "User should be able to click on 'Save Card' payment option.", "'Save Card' payment option is clicked.");
						
						}
				}
				
				if (strOSType.equalsIgnoreCase("Android")) 
				{
					currentFlagValue = appUIDriver.clickButton("gma4_PaymentMethod_Save");
					if (!currentFlagValue)
					{
						HtmlResult.failed("Clicking on 'Save' button on Payment selection screen.", "User should be able to click on 'Save' button on Payment selection screen.", "Unable to click on 'Save' button on Payment selection screen.");
						return false;
					}}
			}
			
			else
			{
				HtmlResult.failed("Clicking on 'Pay with' option.","User should be able to click on 'Pay with' option.", "Unable to click on 'Pay with' option.");
				return false;
			}
				return true;
			
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
			return false;
		}
	}

	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � gma4_ClickOnCheckIn
	 * Method Description - Clicking  on Check-In button 
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Madhur Barsainya
	 * Creation Date - 11/20/2017
	 * Modification History: Completed
	 * # <Date>     <Who>                   <Mod description>

	 ***************************************************************************************************************/

	public boolean gma4_ClickOnCheckIn()
	{	
		boolean currentFlagValue = false;
		try
		{
			
			currentFlagValue = appUIDriver.clickButton("gma4_ChkIn_Btn_Restaurant");
			if (currentFlagValue)
			{
				//Verifying Order Check-In screen
				currentFlagValue = appUIDriver.scrollToObjectFound("gma4_ScreenTapBelow");
				if (currentFlagValue)
				{
					HtmlResult.passed("Clicking on Product 'Check-In' button.", "Clicking on 'Check-In' button and user should be navigated 'Scan QR code' screen.", "Clicked on 'Check-In' button and navigated 'Scan QR code' screen.");
					currentFlagValue = true;
					return currentFlagValue;
				}
				else
				{
					HtmlResult.failed("Clicking on Product 'Check-In' button.", "User should be navigated 'Scan QR code' screen.", "Failed to navigate 'Scan QR code' screen.");
					currentFlagValue = false;
					return currentFlagValue;
				}
			}
			else
			{
				HtmlResult.failed("Clicking on Product 'Check-In' button.", "Product 'Check-In' button should be clicked.", "Unable to click on Product 'Check-In' button.");
				currentFlagValue = false;
				return currentFlagValue;
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
		return currentFlagValue;
	}
	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � gma4_ScanQRCode
	 * Method Description - Scanning QR Code
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Madhur Barsainya
	 * Creation Date - 11/20/2017
	 * Modification History: Completed
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/

	public boolean gma4_ScanQRCode()
	{	
		boolean currentFlagValue = false;
		try
		{		
			currentFlagValue = appUIDriver.clickButton("gma4_ScreenTapBelow");
			if (currentFlagValue)
			{
				//					WebDriverWait wait = new WebDriverWait(driver, 10);
				//					WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.ImageView[@resource-id='com.mcdonalds.app.uk:id/qr_frame']")));
				currentFlagValue = true;
				return currentFlagValue; 
			}
			else
			{
				HtmlResult.failed("Scanning QR Code.", "User should be able to click on 'Tap to scan QR' screen.", "Unable to tap on 'Tap to scan QR' screen.");
				currentFlagValue = false;
				return currentFlagValue; 
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
		return currentFlagValue; 
	}

	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � gma4_ConfirmOrderType
	 * Method Description - Confirm Order Type
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Madhur Barsainya
	 * Creation Date - 11/20/2017
	 * Modification History: Completed
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/

	public void gma4_ConfirmOrderType(Map<String, String> input)
	{
		try
		{
			boolean currentFlagValue = false;
			String strOrderCheckInType = input.get("OrderCheckInType") ;

			currentFlagValue = appUIDriver.clickMobileDynamicAppObject("gma4_OrderCheckInType", strOrderCheckInType);
			if (currentFlagValue)
			{
				HtmlResult.passed("Selecting Order Check-In Type Option.", "Order Check-In type should be selected.", "Order Check-In type '"+strOrderCheckInType+"' is selected.");	
			}
			else
			{
				HtmlResult.failed("Selecting Order Check-In Type Option.", "Order Check-In type should be selected.", "Order Check-In type '"+strOrderCheckInType+"' is selected.");
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
	}

	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � gma4_ClickOnCheckOut
	 * Method Description - Clicking  on Check-Out 
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Madhur Barsainya
	 * Creation Date - 11/20/2017
	 * Modification History: Completed
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/

	public void gma4_CheckOutAndScanQRCode(Map<String, String> input)
	{
		try
		{
			boolean currentFlagValue = false;
			currentFlagValue = appUIDriver.getAppObjectFound("gma4_Ordering_SubProd_AddToBadge");
			if (currentFlagValue)
			{
				currentFlagValue = gma4_ClickOnBasketIcon();
			}
			 currentFlagValue = gma4_ClickOnCheckOut();
			 if (currentFlagValue)
			 {
				 currentFlagValue = gma4_SelectPaymentType(input);
				 if (currentFlagValue)
				 {
					 currentFlagValue =  gma4_ClickOnCheckIn();
					 if(currentFlagValue)
					 {
						 currentFlagValue = gma4_ScanQRCode();
					 }
				 }
			 }
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
	}
	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � gma4TapOnMobileObject
	 * Method Description - 
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author -Mritunjay
	 * Creation Date - 11/02/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/
	public void gma4TapOnMobileObject(Map<String,String> input)
	{
		try{
			boolean blnscrollObject;
			String strObjectName =input.get("mobileObjectName");

			if (strObjectName!=null && strObjectName.length()>1)
			{	
				blnscrollObject= appUIDriver.scrollToObjectFound(strObjectName);
				if (blnscrollObject)
				{
					appUIDriver.clickMobileAppObject(strObjectName);
					HtmlResult.passed("Clickin on Object.", "User should be able to click on object.", "Clicked on object - "+strObjectName);
				}
			}
		}

		catch(Exception e){

			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
	}

	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name �DeleteAllCards
	 * Method Description - This method will delete all the items from Basket
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Mritunjay
	 * Creation Date - 11/02/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/

	public void DeleteAllCards(Map<String,String> input)
	{
		try
		{   String strMobileObjectName; 
		String strObjectName = "";
			String strOSType = EggPlant.getValueFromExcel("OS_TYPE");         
		if (strOSType.equals("iOS"))
		{
			strObjectName ="gma4_SaveCard_MinusDel";
		}
		else
		{
			strObjectName ="gma4_SaveCard_DEL";
		}
			//String strIosObjectName =input.get("mobileIosObjectName");
			//String strObjectName =input.get("mobileObjectName");
			boolean blnHamAction,blnMenuAccount,blnMenuPayMethods,blnbackButton;
			blnHamAction = appUIDriver.clickButton("gma4_hamburger_action");
			if(blnHamAction){
				
				blnMenuAccount = appUIDriver.clickButton("gma4_Menu_Account");;
				
				if(blnMenuAccount){
					
					blnMenuPayMethods=appUIDriver.clickButton("gma4_Menu_Payment_methods");
					if(blnMenuPayMethods){
						
						String strObjProperty = appUIDriver.getObjectProperty("gma4_Save_Card_Edit");
						if(!strObjProperty.equalsIgnoreCase("false"))
						{
							// if(appUIDriver.getAppObjectFound("gma4_Save_Card_Edit"))

							appUIDriver.clickButton("gma4_Save_Card_Edit");
							
							if(strOSType.equalsIgnoreCase("ios")){
								
								strObjectName =strObjectName.trim();                        
								strMobileObjectName  = appUIDriver.getAppObjectwithLocator(strObjectName);

								if(!strMobileObjectName.equalsIgnoreCase("false")){
									String[] arrMobileObjectName = strMobileObjectName.split("#");
									String objectLocatorName =arrMobileObjectName[0].toString();
									String objectLocatorValue= arrMobileObjectName[1].toString();
									
									//gma4_SaveCard_MinusDel
									
									int delbuttoncount = appUIDriver.countObject(objectLocatorName,objectLocatorValue);
									for(int i = 0;i<delbuttoncount;i++){
										
										appUIDriver.clickButton("gma4_SaveCard_MinusDel");
										appUIDriver.clickButton("gma4_SaveCard_DEL");
										
									}
									HtmlResult.passed("Deleting Saved cards","Saved should be deleted","Saved Cards are deleted.");
								 }
								else{
									HtmlResult.passed("Deleting Saved cards","Saved should be deleted","No Cards Saved");
								 }								
								
							}
							
							else{
							strObjectName =strObjectName.trim();                        
							strMobileObjectName  = appUIDriver.getAppObjectwithLocator(strObjectName);

							if(!strMobileObjectName.equalsIgnoreCase("false")){
								String[] arrMobileObjectName = strMobileObjectName.split("#");
								String objectLocatorName =arrMobileObjectName[0].toString();
								String objectLocatorValue= arrMobileObjectName[1].toString();
								
								//gma4_SaveCard_MinusDel
								
								int delbuttoncount = appUIDriver.countObject(objectLocatorName,objectLocatorValue);
								for(int i = 0;i<delbuttoncount;i++){

									appUIDriver.clickButton("gma4_SaveCard_DEL");                                         
								}
								HtmlResult.passed("Deleting Saved cards","Saved should be deleted","Saved Cards are deleted.");
							 }
							else{
								HtmlResult.passed("Deleting Saved cards","Saved should be deleted","No Cards Saved");
							 }
							}
						
				}
					else
					{ 
						HtmlResult.failed("Not able to click Menu_Payment_methods", "Not able to click Menu_Payment_methods", "Not able to click Menu_Payment_methods");
					}
					
				}
					
				else{
					HtmlResult.failed("Not able to click Menu_Account", "Not able to click Menu_Account", "Not able to click gma4_Menu_Account");
					}
					
				}
					else{
					HtmlResult.failed("Not able to click hamburger_action", "gma4_hamburger_action", "hamburger_action"); 
					}
					
			}
				blnbackButton = appUIDriver.clickButton("gma4_common_BackButton"); 
				
				if(blnbackButton)
				{
				appUIDriver.clickButton("gma4_common_BackButton");
				}
				else
				{ 
					HtmlResult.failed("Not able to click back button","Back button should be clickable","Back button is not clickable");				
				}
			
		}
			
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
	}

	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name �tapMobileAppObject
	 * Method Description - 
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Mritunjay
	 * Creation Date - 11/02/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/
	public void tapMobileAppObject(Map<String,String> input)

	{
		try
		{
			boolean FoundAndClicked =false;
			String strObjectName =input.get("mobileObjectName");
			String strexpectedData = "";
			if (strObjectName!=null && strObjectName.length()>1)
			{
				if (appUIDriver.scrollToObjectFound(strObjectName))
				{
					strexpectedData = appUIDriver.getObjectText(strObjectName);
					if (strexpectedData.equalsIgnoreCase(""))
					{
						strexpectedData =input.get("expectedData");
					}}
			
			
			//mobileAppSync();
			String strMobileObjectName  = appUIDriver.getAppObjectwithLocator(strObjectName);

			String[] arrMobileObjectName = strMobileObjectName.split("#");

			String objectLocatorName =arrMobileObjectName[0].toString();
			String objectLocatorValue= arrMobileObjectName[1].toString();

			FoundAndClicked= appUIDriver.clickText(objectLocatorName, objectLocatorValue);

			if (FoundAndClicked)
			{
				HtmlResult.passed("Tap the Mobile app object ", "Tap operation should be done successfully for object - "+ strObjectName + " for the object -" + strObjectName , "Tap operation performed successfully for object - " + strexpectedData);
			}
			else
			{
				HtmlResult.failed("Tap the Mobile app object ", "Tap operation should be done successfully for object - "+ strObjectName + " for the object -" + strObjectName , "Tap operation is not performed for object - " + strexpectedData);
			}
		}}

		catch (Exception e)
		{
			HtmlResult.failed("Tap the Mobile app object ", "Tap operation should be done successfully for object - for the object -"  , "Tap operation is not performed for object - " );
			System.out.println(e.getMessage());
			
		}
	}


	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � gma4_verifySavedCard
	 * Method Description - gma4_verifySavedCard
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Mritunjay
	 * Creation Date - 11/24/2017
	 * Modification History: Completed
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/
	public boolean gma4_verifySavedCard(Map<String, String> input){

		try{
			boolean currentFlagValue = false;
			String strLast4Digit,CurrentObjectText;
			String strCardNumber = input.get("CardNumber");
			String strCardHolderName= input.get("CardHolderName");
			String strObjectName = input.get("mobileObjectName");
			String Str = new String(strCardNumber);			
			strLast4Digit=Str.substring(12);
			
			String strDynamicText = strCardHolderName+"-"+strLast4Digit;//strLast4Digit
			String strExpectedData= strDynamicText;		
			String strMobileObjectName  = appUIDriver.getAppObjectDynamicLocator(strObjectName, strDynamicText);

			if (strMobileObjectName.contains("false"))
			{
				return false;
			}
			else
			{
				String[] arrMobileObjectName = strMobileObjectName.split("#");
				String objectLocatorName =arrMobileObjectName[0].toString();
				String objectLocatorValue= arrMobileObjectName[1].toString();

				CurrentObjectText = appUIDriver.getText(objectLocatorName, objectLocatorValue);
			}

			// After Successfully loop , the value will check

			if (CurrentObjectText.equalsIgnoreCase(strExpectedData))
			{
				//System.out.println("PASS");
				HtmlResult.passed("verify the current content of app ", "App content should be - "+ strExpectedData + " for the object -" + strObjectName , "Actual content is - " + CurrentObjectText);
				return true;
			}
			else
			{
				//System.out.println("FAIL");
				HtmlResult.failed("verify the current content of app ", "App content should be - "+ strExpectedData + " for the object -" + strObjectName , "Actual content is not - " + CurrentObjectText);
				return false;
			}		
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}	
		return false;
	}

	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � gma4_AppBackButton
	 * Method Description - Click on application back button
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Mritunjay
	 * Creation Date - 11/24/2017
	 * Modification History: Completed
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/

	public void gma4_AppBackButton(Map<String, String> input){

		try{
						
			String strObjectName = input.get("mobileObjectName");
			if(!strObjectName.equalsIgnoreCase("")){
			//String strOSType = EggPlant.getValueFromExcel("OS_TYPE");
		
			//String currentOS=strOSType;			
				appUIDriver.clickButton("gma4_barclay_Cancel");
				HtmlResult.passed("Clicking on application back button","Back button should be clicked","User is navigated to previous screen");
				
			}
				else{
				appUIDriver.clickButton("gma4_common_BackButton"); 
				HtmlResult.passed("Clicking on application back button","Back button should be clicked","User is navigated to previous screen");
				}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
	}
	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � gma4_EditProduct
	 * Method Description - To edit product from order Basket page 
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Madhur Barsainya
	 * Creation Date - 11/22/2017
	 * Modification History: Completed
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/

	public void gma4_EditProduct(Map<String, String> input)
	{
		try
		{
			boolean currentObjectValue = false;
			String strProduct ;
			String strCustomizeProductFlag = input.get("CustomizeProduct");
			String strSideItemFlag = input.get("EditSideItem");
			String strDrinkItemFlag = input.get("EditDrinkItem");

			SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
			Date date = new Date();

			// UKTime1 and UKTime2 check breakfast and lunch time
			String ISTStartTime=EggPlant.getValueFromExcel("StartTime");
			String ISTEndTime=EggPlant.getValueFromExcel("EndTime");
			String shopTime = parser.format(date);
			Date UKTime1 = parser.parse(ISTStartTime); //Denotes Start breakfast UK time UK 
			Date UKTime2 = parser.parse(ISTEndTime); //Denotes end breakfast UK time UK
			Date userDate = parser.parse(shopTime);

			if (userDate.after(UKTime1) && userDate.before(UKTime2)) 
			{
				strProduct =input.get("BrkfstProduct").trim();  // eg - 1 Vanilla Milkshake
			}
			else
			{
				strProduct =input.get("LunchProduct").trim();  // eg - 1 Spicy Vegetable Deluxe
			}

			currentObjectValue = appUIDriver.clickButton("gma4_Edit_ChkOutScreen_Btn");
			if (currentObjectValue)
			{
				currentObjectValue = appUIDriver.clickMobileDynamicAppObject("gma4_EditItem_CheckOutScreen_Btn", strProduct);
				if (currentObjectValue)
				{
					if (strCustomizeProductFlag.equalsIgnoreCase("Yes"))
					{
						gma4_CustomizeProduct(input);
					}
					
					if (strSideItemFlag.equalsIgnoreCase("Yes"))
					{
						gma4_SelectSideItem(input);
					}
					
					if (strDrinkItemFlag.equalsIgnoreCase("Yes"))
					{
						gma4_SelectDrinkItem(input);
					}

					currentObjectValue = appUIDriver.clickButton("gma4_Ordering_SubProd_UpdateInBasket_Btn");
					if(currentObjectValue)
					{
						currentObjectValue =	appUIDriver.clickButton("gma4_Done_Btn");
						if(currentObjectValue)
						{
							HtmlResult.passed("Editing Product.", "User should be able to edit '"+strProduct+"' product.", "Edited '"+strProduct+"' product successfully.");
						}
						else
						{
							HtmlResult.failed("Clicking on Done Button.", "User should be able to click on Done button.", "Unable to click on Done button.");
						}
					}
					else
					{
						HtmlResult.failed("Clicking on Update Button.", "User should be able to click on Update button.", "Unable to click on Update button.");
					}
				}
				else
				{
					HtmlResult.failed("Clicking on Product Edit button on Order Basket Screen.", "User should be able to click on Edit button for '"+strProduct+"' on Order BasketScreen.", "Unable to click on 'Edit' button for '"+strProduct+"' product on Order Summary Screen. ");
				}
			}
			else
			{
				HtmlResult.failed("Clicking on 'Edit' button present on Order Basket Screen.", "User should be able to click on 'Edit' button present on Order Basket Screen.", "Unable to click on 'Edit' button present on Order Basket Screen. ");
			}

		}
		catch (Exception e)
		{
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
	}

	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � gma4_VerifyIncorrectZoneCodeErrorMessage
	 * Method Description - Entering Invalid Zone code for Table Service Check-In type
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Madhur Barsainya
	 * Creation Date - 11/22/2017
	 * Modification History: Completed
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/

	public void gma4_VerifyIncorrectZoneCodeErrorMessage(Map<String, String> input)
	{
		try
		{
			boolean currentObjectValue = false;
			String strZoneCode = input.get("ZoneCode");
			String strOSType = EggPlant.getValueFromExcel("OS_TYPE");
			currentObjectValue = appUIDriver.enterText("gma4_ZoneCode_TableService_Edit", strZoneCode);
			if (currentObjectValue)
			{
				if (strOSType.equalsIgnoreCase("iOS"))
				{
					currentObjectValue = appUIDriver.clickButton("gma4_Done_TableService_Btn");
				}
				else
				{
					appUIDriver.backbuttonPress();
					currentObjectValue = appUIDriver.clickButton("gma4_FinishAndPay_TableService_Btn");
				}
				if (currentObjectValue)
				{
					currentObjectValue = appUIDriver.getAppObjectFound("gma4_IncorrectZipCodeErrMsg_TableService");
					if(currentObjectValue)
					{
						HtmlResult.passed("Verifying Incorrect zone Code error message.", "On entering '"+strZoneCode+"' as zone code, incorrect zone code should appear.", "On entering '"+strZoneCode+"' as zone code, incorrect zone code error message apprears.");
						currentObjectValue = appUIDriver.clickButton("gma4_OK_IncorrectZipCodeErrMsg_TableService_Btn");
						if (currentObjectValue) 
						{
							appUIDriver.backbuttonPress();
							appUIDriver.backbuttonPress();
						}
						else
						{
							HtmlResult.failed("Clicking on Incorrect Zone Code pop-up 'OK' button.", "User should be able to click on Incorrect Zone Code pop-up 'OK' button.","Unable to click on Incorrect zone Code pop-up 'OK' button.");
						}
					}
					else
					{
						HtmlResult.failed("Verifying Incorrect zone Code error message.", "On entering '"+strZoneCode+"' as zone code, Incorrect Zone code should appear.", "On entering '"+strZoneCode+"' as zone code, incorrect zone code error message does not apprears.");
					}
				}
				else
				{
					HtmlResult.failed("Clicking on 'Finish & Pay' button on Table Service scren.", "User should be ablt to click on 'Finish & Pay' button.", "Unable to click on 'Finish & Pay' button.");
				}
			}
			else
			{
				HtmlResult.failed("Entering Zone Code.", "User should be able to enter zone code in Zone code field." , "Unable to enter zone code in zone code field.");
			}
		}
		catch (Exception e)
		{
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
	}
	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � readFinalOrderDetails
	 * Method Description - Read final ordered product name 
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Nandini Gupta
	 * Creation Date - 11/22/2017
	 * Modification History: Completed
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/


	
	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � verifyRestaurentLocator
	 * Method Description - Go to Checkout order screen and verify 'Bag Charges' as per selection
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Nandini Gupta
	 * Creation Date - 11/02/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/

	public void verifyRestaurentLocator(Map<String,String> input){
		try{
			String objHamburgerMenu = input.get("gmaobjHamburgerMenu"); 
			String objMenuName  = input.get("gmaobjMenuName");
			boolean BlnFound =false;
			BlnFound = appUIDriver.clickButton(objHamburgerMenu);
			//If Script is able to click on Hamburger Menu
			if(BlnFound)
			{
				String strMobileObjectName  = appUIDriver.getAppObjectwithLocator(objMenuName);
				String[] arrMobileObjectName = strMobileObjectName.split("#");
				String objectLocatorName =arrMobileObjectName[0].toString();
				String objectLocatorValue= arrMobileObjectName[1].toString();
				BlnFound =appUIDriver.clickText(objectLocatorName, objectLocatorValue);
				if (BlnFound)
				{
					appUIDriver.getAppObjectwithLocator("gma4_ConfigSel_Master");

					appUIDriver.clickButton("gma4_ConfigSel_Master_ProdEnv");
					if(BlnFound)
					{
						appUIDriver.clickButton("gma4_GetStarted");
						//							appUIDriver.enterTextinAppObject(input);
						appUIDriver.pressMobileKey("ENTER");
						appUIDriver.clickButton("gma4_Restaurant_Continue");
						appUIDriver.clickButton("gma4_Restarent_Conf_Pop-up");
						System.out.println("User will reach to home screen");

					}

				}
			}
		}

		catch(Exception e)
		{
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
	}




	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � recentOrderVerification
	 * Method Description - Go to Recent order screen through hamburger icon and also verify the recent order item
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Nandini Gupta
	 * Creation Date - 11/02/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/
	public void recentOrderPriceVerification(Map<String,String> input)
	{
		try{
			boolean currentFlagValue=false;
			String bookWithoffer=input.get("BookedWithOffer");
			float ExpectedTotalPrice ;
			float ActualTotalPrice ;
			String strActualTotalPrice ;
			
			ExpectedTotalPrice = SubTotal_OderSummaryScreen; 
			
			currentFlagValue=appUIDriver.scrollToObjectFound("gma4_recentOrder_ProductPrice");
			if(currentFlagValue)
			{
    			
			strActualTotalPrice = appUIDriver.getObjectText("gma4_recentOrder_ProductPrice");
			
			ActualTotalPrice = Float.parseFloat(((strActualTotalPrice.split("£"))[1].split("\\*"))[0]);
			
			if(ActualTotalPrice == ExpectedTotalPrice)
			{
				HtmlResult.passed("Veriyfing Price on 'Recent order' screen.", "Oder Summary Screen price should be equal to Recent Order Screen Price.", "Order Summary Screen Price '"+String.format("%.2f",ExpectedTotalPrice)+"'is equal to Recent Order Screen Price '"+String.format("%.2f",ActualTotalPrice)+"");
			}
			
			else
			{if(bookWithoffer.equals("YES")){
				HtmlResult.passed("Veriyfing Price on 'Recent order' screen.", "Oder Summary Screen price should not equal to Recent Order Screen Price.", "Order Summary Screen Price '"+String.format("%.2f",ExpectedTotalPrice)+"'is not equal to Recent Order Screen Price '"+String.format("%.2f",ActualTotalPrice)+"");
			}
			else{
				HtmlResult.failed("Veriyfing Price on 'Recent order' screen.", "Oder Summary Screen price should not equal to Recent Order Screen Price.", "Order Summary Screen Price '"+String.format("%.2f",ExpectedTotalPrice)+"'is equal to Recent Order Screen Price '"+String.format("%.2f",ActualTotalPrice)+"");
				
			}
			}
			}
					}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
	}



	
		/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � orderByOffer
	 * Method Description - Go to Recent order screen through hamburger icon and also verify the recent order item
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Nandini Gupta
	 * Creation Date - 11/02/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/
	public void orderByOffer(Map<String,String> input)
	{
		try	
		{
			Boolean BlnEnabled = false;
			Boolean	BlnFound=false; // TO CHECK THE Status of current step
			String ABC =input.get("objName");
			BlnFound = appUIDriver.scrollToObjectFound("gma4_Offers_SelectedRestaurant");
			if (BlnFound) // If object found then go to inside the if condition
			{
				HtmlResult.passed("To verify Selected Restaurant tab", "Selected Restaurant tab should be present", "Selected restaurant tab found");
				BlnFound=appUIDriver.clickButton("gma4_Offers_SelectedRestaurant");
				if(BlnFound)
				{
					HtmlResult.passed("To verify Selected Restaurant tab click", "Selected Restaurant tab should be clicked", "Selected restaurant tab clicked");
					BlnFound = appUIDriver.scrollToObjectFound("gma4_Offers_BOGOOffer");
					if(BlnFound)
					{
						HtmlResult.passed("To verify BOGO offer", "Selected BOGO offer should be present", "BOGO offer found");
						BlnFound =  appUIDriver.clickButton("gma4_Offers_BOGOOffer");
						if (BlnFound)
						{ 
							String enablbtn = appUIDriver.objectGetattribute("gma4_offers_order_ApplyToMobileOrderBtn","enabled");
							BlnEnabled = true;
							if (BlnEnabled)
							{
								HtmlResult.passed("Verify Apply Mobile Order button" , "Apply Mobile Order button should be enabled", "Apply Mobile Order button is enabled");
								BlnFound =  appUIDriver.clickButton("gma4_offers_order_ApplyToMobileOrderBtn");
								// HTML Report - Passed - Successfully clicked 
								HtmlResult.passed("Verify Apply Mobile Order button click" , "Apply Mobile Order button should be clicked", "Apply Mobile Order button clicked successfully");

							}
							else
							{
								// Button is disabled or product not selected
								HtmlResult.failed("Select Offer Product", "Mobile Order button should be enabled", "Mobile order button is not enabled");
							}
							BlnFound = appUIDriver.getAppObjectFound("gma4_OfferOrder_pageVerify");
							if(BlnFound){
								String strMobileObjectName  = appUIDriver.getAppObjectwithLocator("gma4_offers_order_ProductName");
								String[] arrMobileObjectName = strMobileObjectName.split("#");
								String objectLocatorName = arrMobileObjectName[0].toString();
								String objectLocatorValue = arrMobileObjectName[1].toString();
								String strOfferProductname =appUIDriver.getText(objectLocatorName, objectLocatorValue);
								System.out.println(strOfferProductname);
								String StrCurrentProductName = strOfferProductname;

								String strMobileObjectName1  = appUIDriver.getAppObjectwithLocator("gma4_Offers_Order_TotalProductPrice");
								String[] arrMobileObjectName1 = strMobileObjectName1.split("#");
								String objectLocatorName1 = arrMobileObjectName1[0].toString();
								String objectLocatorValue1 = arrMobileObjectName1[1].toString();
								String strOfferProductprice =appUIDriver.getText(objectLocatorName1, objectLocatorValue1);
								System.out.println(strOfferProductprice);
								String StrCurrentProductprice = strOfferProductprice;
							}			
						}
						else
						{
							HtmlResult.failed("Select Offer Product", "Mobile offer Order should be present", "Mobile offer order button is not present");
						}
					}
					else
					{
						HtmlResult.failed("Select Offer Product", "Offer should be present", "Offer is not present on screen");
					}
				}
				else
				{
					HtmlResult.failed("Select Offer Product", "Selected Restaurant is present", "Selected Reestaurant is not present");
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
	}



		/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � gma4_EnterZoneCode_TableService
	 * Method Description - Enter Zone code on table service 
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Madhur Barsainya
	 * Creation Date - 11/22/2017
	 * Modification History: Completed
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/

	public void gma4_EnterZoneCode(Map<String, String> input)
	{
		try
		{
			boolean currentObjectValue = false;
			String strZoneCode = input.get("ZoneCode");
			String strOSType = EggPlant.getValueFromExcel("OS_TYPE");
			currentObjectValue = appUIDriver.enterText("gma4_ZoneCode_TableService_Edit", strZoneCode);
			if (currentObjectValue)
			{
				if (strOSType.equalsIgnoreCase("iOS"))
				{
					currentObjectValue = appUIDriver.clickButton("gma4_Done_TableService_Btn");
				}
				else
				{
					appUIDriver.backbuttonPress();
				}
				if (currentObjectValue)
				{
					currentObjectValue = appUIDriver.clickButton("gma4_FinishAndPay_TableService_Btn");
					if (currentObjectValue)
					{
						HtmlResult.passed("Entering Zone Code for Table Service", "User should be able to enter Zone Code for Table Service.", "'"+strZoneCode+"' is entered as Zone Code for Table Service.");
					}
					else
					{
						HtmlResult.failed("Clicking on 'Fininsh & Pay' button on table service screen.", "User should be able to click on 'Finish & Pay' button on Table Service.", "Unable to click on 'Finish & Pay' button on Table screen.");
					}
				}
				else
				{
					HtmlResult.failed("Clicking on 'Done' button on table service screen for iOS.", "User should be able to click on 'Done' button on Table Service for iOS.", "Unable to click on 'Done' button on Table screen for iOS.");
				}
			}
			else
			{
				HtmlResult.failed("Entering Zone Code for Table Service.", "User should be able to enter Zone Code for Table Service.", "Unable to enter Zone Code for Table Service.");
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
	}

	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � gma_VerifyProductInOrderBasketScreen
	 * Method Description - Verifying Product in Order basket screen
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Madhur Barsainya
	 * Creation Date - 11/22/2017
	 * Modification History: Completed
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/

	public void gma_VerifyProductInOrderBasketScreen(Map<String, String> input)
	{
		try
		{
			String strProductObject = "gma4_ItemName_CheckOutScreen";
			String strProductWithCondimentObject = "gma4_ItemNameWithCustomization_CheckOutScreen";
			String strAllProducts ;
			String strActualObject ;
			String strItemPresentFlag = input.get("ItemPresent");
			boolean currentFlagValue = false;
			int intProductCounter ;
			ArrayList<String> arrProductPriceInOrderSmryScren = new ArrayList<String>();
			String strProductPrice;
			String strSubTotal_OrderBasketScreen;
			
			SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
			Date date = new Date();

			// UKTime1 and UKTime2 check breakfast and lunch time
			String ISTStartTime=EggPlant.getValueFromExcel("StartTime");
			String ISTEndTime=EggPlant.getValueFromExcel("EndTime");
			String shopTime = parser.format(date);
			Date UKTime1 = parser.parse(ISTStartTime); //Denotes Start breakfast UK time UK 
			Date UKTime2 = parser.parse(ISTEndTime); //Denotes end breakfast UK time UK
			Date userDate = parser.parse(shopTime);
			
			if (userDate.after(UKTime1) && userDate.before(UKTime2)) 
			{
				strAllProducts =input.get("BrkfstProduct").trim();  // eg - Vanilla Milkshake
			}
			else
			{
				strAllProducts =input.get("LunchProduct").trim();  // eg - Spicy Vegetable Deluxe
			}
		
			if (appUIDriver.getAppObjectFound("gma4_Ordering_SubProd_AddToBadge"))
			{
				gma4_ClickOnBasketIcon();
			}
		
			String[] arrAllProducts = strAllProducts.split("\\$");
			String[] arrItemsPresent = strItemPresentFlag.split("\\$");
			
			for (intProductCounter = 0; intProductCounter<arrAllProducts.length; intProductCounter++ )
			{
				if (arrAllProducts[intProductCounter].contains("#"))
				{
					strActualObject = strProductWithCondimentObject;
				}
				else
				{
					strActualObject = strProductObject;
				}
				
				currentFlagValue = appUIDriver.getAppObjectFoundDynamic(strActualObject, arrAllProducts[intProductCounter]);
				if (currentFlagValue) 
				{
					strProductPrice = appUIDriver.getTextForDynamicObject("gma4_ProductPrice_OrderBasketScreen",arrAllProducts[intProductCounter]);
					//Getting produc price
					
				    
					if(strProductPrice ==null||strProductPrice.equalsIgnoreCase(""))
					{
						strProductPrice = appUIDriver.getTextForDynamicObject("gma4_MealPrice_OrderBasketScreen",arrAllProducts[intProductCounter]); //Getting Meal price
					 
						
					}
					
					arrProductPriceInOrderSmryScren.add(strProductPrice);
					String[] strTempProductName =  arrAllProducts[intProductCounter].split(" ",2);
					hmProductPriceInOrderSmryScrn.put(strTempProductName[1], strProductPrice);
					
					if (arrAllProducts[intProductCounter].contains("#")) //This condition is used when the product is customized
					{
						String[] arrProductCondiment = arrAllProducts[intProductCounter].split("#");
						if (arrItemsPresent[intProductCounter].equalsIgnoreCase("NO"))
						{
							HtmlResult.failed("Verifying Product in Basket.", "'"+arrProductCondiment[0]+"' should not be present in Basket with condiments - '"+arrProductCondiment[1]+"'.", "'"+arrProductCondiment[0]+"'  is present in the basket with condiments - '"+arrProductCondiment[1]+"'.");
						}
						else
						{
							HtmlResult.passed("Verifying Product in Basket.", "'"+arrProductCondiment[0]+"' should be present in Basket with condiments - '"+arrProductCondiment[1]+"'.", "'"+arrProductCondiment[0]+"'  is present in the basket with condiments - '"+arrProductCondiment[1]+"' having price '"+strProductPrice+"'.");
						}
					}
					else
					{
						if (arrItemsPresent[intProductCounter].equalsIgnoreCase("NO")) //If product is present and expected is that it should not be present
						{
							HtmlResult.failed("Verifying Product in Basket.", "'"+arrAllProducts[intProductCounter]+"' should not be present in the basket.", "'"+arrAllProducts[intProductCounter]+"' is present in the basket.");
						}
						else
						{
							HtmlResult.passed("Verifying Product in Basket.", "'"+arrAllProducts[intProductCounter]+"' should be present in the basket.", "'"+arrAllProducts[intProductCounter]+"' is present in the basket having price '"+strProductPrice+"'.");
						}
					}
				}
				else
				{
					if (arrItemsPresent[intProductCounter].equalsIgnoreCase("NO")) //To verify if product should not be present.
					{
						HtmlResult.passed("Verifying Product in Basket.", "'"+arrAllProducts[intProductCounter]+"' should not be present in the Basket.", "'"+arrAllProducts[intProductCounter]+"' is not present in the Basket.");
					}
					else
					{
						//HtmlResult.passed("Verifying Product in Basket.", "'"+arrAllProducts[intProductCounter]+"' should be present in the Basket.", "'"+arrAllProducts[intProductCounter]+"' is present in the Basket.");
						HtmlResult.failed("Verifying Product in Basket.", "'"+arrAllProducts[intProductCounter]+"' should be present in the Basket.", "'"+arrAllProducts[intProductCounter]+"' is not present in the Basket.");
					}
				}
			}
			
			//Capturing Sub total from order basket screen.
			currentFlagValue = appUIDriver.scrollToObjectFound("gma4_SubTotal_OrderBasketScreen");
			if(currentFlagValue)
			{
				strSubTotal_OrderBasketScreen = appUIDriver.getObjectText("gma4_SubTotal_OrderBasketScreen");
				//SubTotal_OrderBasketScreen += Float.parseFloat(((strSubTotal_OrderBasketScreen.split("�"))[1].split("\\*"))[0]);
				SubTotal_OrderBasketScreen = Float.parseFloat(((strSubTotal_OrderBasketScreen.split("\\£"))[1].split("\\*"))[0]);
				
				//Update by Prateek (Wrong data was present in variable)
				HtmlResult.passed("Capturing 'Sub-Total' from 'Order Basket' screen.", "User should be able to capture 'Sub-Total' from 'Order Basket' screen.", "'Sub-Total' of products on 'Order Basket' screen is : '"+ SubTotal_OrderBasketScreen +"'.");
			}
			else
			{
				HtmlResult.failed("Capturing 'Sub-Total' from Order Basket screen.", "User should be able to capture 'Sub-Total' from Order Basket screen.", "Failed to find 'Sub-Total' object on Order Basket screen.");
			}
			
			/*currentFlagValue = appUIDriver.clickButton("gma4_Common_Close");
			if (!currentFlagValue)
			{
				HtmlResult.failed("Clicking on Close button on Order Basket Page.", "User should be able to click on Close button on Order Basket Page.", "Unable to click on Close button on Order Basket Page.");
			}*/
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
	}



	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � gma4_ClickOnReviewOrder_OutOfStockScreen
	 * Method Description - Clicking on Review Order button on Out of Stock screen
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Madhur Barsainya
	 * Creation Date - 11/28/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/

	public void gma4_ClickOnReviewOrder_OutOfStockScreen()
	{
		try
		{
			boolean currentFlagValue =false;  
			currentFlagValue = appUIDriver.getAppObjectFound("gma4_Text_OutOfStockScreen");
			if (currentFlagValue)
			{
				String strMessage = appUIDriver.getObjectText("gma4_Text_OutOfStockScreen");
				HtmlResult.passed("Verifying 'Out of Stock' screen.", "User should be navigated to 'Out of Stock' screen.", "User is navigated to 'Out of Stock' screen which display - '"+strMessage+"' message.");
				
				currentFlagValue = appUIDriver.clickButton("gma4_ReviewOrder_OutOfStockScreen_Btn");
				if (currentFlagValue)
				{
					currentFlagValue = appUIDriver.getAppObjectFound("gma4_Ordering_CheckOut");
					HtmlResult.passed("Clicking on 'Review Order' button on Out of Stock screen", "'Review Order' button should be clicked present on Out of Stock screen.", "Clicked on 'Review Order' button present on Out of Stock screen and navigated to Order Basket screen.");
				}
				else
				{
					HtmlResult.failed("Clicking on 'Review Order' button on Out of Stock screen", "'Review Order' button should be clicked present on Out of Stock screen.", "Unable to click on 'Review Order' button on Out of Stock screen.");
				}
			}
			else
			{
				HtmlResult.failed("Verifying 'Out of Stock' screen.", "'Out of Stock' message should be displayed.", "'Out of Stock' message is not displayed.");
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
	}
	
	
	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � gma4_ClickOnContinue_OutOfStockScreen
	 * Method Description - Clicking on Continue button on Out of Stock screen
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Madhur Barsainya
	 * Creation Date - 11/28/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/

	public void gma4_ClickOnContinue_OutOfStockScreen()
	{
		try
		{
			boolean currentFlagValue =false;  
			int intTotalOutOfStockProduct ;
			String strProductName ;
			hmProductPriceWithoutUnavailableItems = hmProductPriceInOrderSmryScrn;
			SubTotal_OutOfStockScreen = 0;
			
			currentFlagValue = appUIDriver.getAppObjectFound("gma4_Text_OutOfStockScreen");
			if (currentFlagValue)
			{
				String strMessage = appUIDriver.getObjectText("gma4_Text_OutOfStockScreen");
				HtmlResult.passed("Verifying 'Out of Stock' screen.", "User should be navigated to 'Out of Stock' screen.", "User is navigated to 'Out of Stock' screen which display - '"+strMessage+"' message.");
				
				intTotalOutOfStockProduct = appUIDriver.getObjectCount("gma4_Products_OutOfStockScreen");
				for(int i = 1; i<=intTotalOutOfStockProduct; i++)
				{
					strProductName = appUIDriver.getTextForDynamicObject("gma4_Products_OutOfStockScreen1", Integer.toString(i));
					if(hmProductPriceInOrderSmryScrn.containsKey(strProductName))
				    {
						hmProductPriceWithoutUnavailableItems.remove(strProductName);
				    }
				}
				for (String value : hmProductPriceWithoutUnavailableItems.values())
				{
					SubTotal_OutOfStockScreen += Float.parseFloat(((value.split("£"))[1].split("\\*"))[0]);
				}
				
				currentFlagValue = appUIDriver.clickButton("gma4_Continue_OutOfStockScreen_Btn");
				if (currentFlagValue)
				{
//					currentFlagValue = appUIDriver.getAppObjectFound("gma4_Ordering_CheckOut");
					HtmlResult.passed("Clicking on 'Continue' button on Out of Stock screen", "'Continue' button should be clicked present on Out of Stock screen.", "'Continue' button is clicked present on Out of Stock screen.");
				}
				else
				{
					HtmlResult.failed("Clicking on 'Continue' button on Out of Stock screen", "'Continue' button should be clicked present on Out of Stock screen.", "Unable to click on 'Continue' button on Out of Stock screen.");
				}
			}
			else
			{
				HtmlResult.failed("Verifying 'Out of Stock' screen.", "'Out of Stock' message should be displayed.", "'Out of Stock' message is not displayed.");
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
	}
	
	
	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � gma4_ClickOnReviewOrder_NewOrderTotalScreen
	 * Method Description - Clicking on Review Order button on Out of Stock screen
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Madhur Barsainya
	 * Creation Date - 11/28/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/

	public void gma4_ClickOnReviewOrder_NewOrderTotalScreen()
	{
		try
		{
			boolean currentFlagValue =false;  
			currentFlagValue = appUIDriver.getAppObjectFound("gma4_Text_NewOrderTotalScreen");
			if (currentFlagValue)
			{
				String strMessage = appUIDriver.getObjectText("gma4_Text_NewOrderTotalScreen");
				HtmlResult.passed("Verifying 'New Order Total' screen.", "User should be navigated to 'New Order Total' screen.", "User is navigated to 'New Order Total' screen which display - '"+strMessage+"' message.");
				
				currentFlagValue = appUIDriver.clickButton("gma4_ReviewOrder_NewOrderTotalScreen_Btn");
				if (currentFlagValue)
				{
					currentFlagValue = appUIDriver.getAppObjectFound("gma4_Ordering_CheckOut");
					HtmlResult.passed("Clicking on 'Review Order' button on 'New Order Total' screen", "'Review Order' button should be clicked present on 'New Order Total' screen.", "Clicked on 'Review Order' button present on 'New Order Total' screen and navigated to Order Basket screen.");
				}
				else
				{
					HtmlResult.failed("Clicking on 'Review Order' button on 'New Order Total' screen", "'Review Order' button should be clicked present on 'New Order Total' screen.", "Unable to click on 'Review Order' button on 'New Order Total' screen.");
				}
			}
			else
			{
				HtmlResult.failed("Verifying 'New Order Total' screen.", "'New Order Total' message should be displayed.", "'New Order Total' message is not displayed.");
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
	}
	
	
	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � gma4_ClickOnContinue_NewOrderTotalScreen
	 * Method Description - Clicking on Continue button on New Order Total screen
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Madhur Barsainya
	 * Creation Date - 11/28/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/

	public void gma4_ClickOnContinue_NewOrderTotalScreen()
	{
		try
		{
			boolean currentFlagValue =false;  
			currentFlagValue = appUIDriver.getAppObjectFound("gma4_Text_NewOrderTotalScreen");
			if (currentFlagValue)
			{
				String strMessage = appUIDriver.getObjectText("gma4_Text_NewOrderTotalScreen");
				HtmlResult.passed("Verifying 'New Order Total' screen.", "User should be navigated to 'New Order Total' screen.", "User is navigated to 'New Order Total' screen which display - '"+strMessage+"' message.");
				
				currentFlagValue = appUIDriver.clickButton("gma4_Continue_NewOrderTotalScreen_Btn");
				if (currentFlagValue)
				{
					currentFlagValue = appUIDriver.getAppObjectFound("gma4_Ordering_CheckOut");
					HtmlResult.passed("Clicking on 'Continue' button on 'New Order Total' screen", "'Continue' button should be clicked present on 'New Order Total' screen.", "'Continue' button is clicked present on 'New Order Total' screen and navigated to Order Basket screen.");
				}
				else
				{
					HtmlResult.failed("Clicking on 'Continue' button on 'New Order Total' screen", "'Continue' button should be clicked present on 'New Order Total' screen.", "Unable to click on 'Continue' button on 'New Order Total' screen.");
				}
			}
			else
			{
				HtmlResult.failed("Verifying 'New Order Total' screen.", "'New Order Total' message should be displayed.", "'New Order Total' message is not displayed.");
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
	}
	
	
	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � gma4_ClickOnContinue_CheckingInDifferentRestaurantScreen
	 * Method Description - Clicking on Continue button on Checking In Different Restaurant
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Madhur Barsainya
	 * Creation Date - 11/28/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/

	public void gma4_ClickOnContinue_CheckingInDifferentRestaurantScreen()
//	public void gma4_ClickOnContinue_DifferentQRScreen()
	{
		try
		{
			boolean currentFlagValue =false;  
			Thread.sleep(5000);
			currentFlagValue = appUIDriver.getAppObjectFound("gma4_WarningMsg_DifferentQRCode");
			if (currentFlagValue)
			{
				String strMessage = appUIDriver.getObjectText("gma4_WarningMsg_DifferentQRCode");
				HtmlResult.passed("Verifying 'Checking In Different Restaurant' screen.", "User should be navigated to 'Checking In Different Restaurant' screen.", "User is navigated to 'Checking In Different Restaurant' screen which display - '"+strMessage+"' message.");
				
				currentFlagValue = appUIDriver.clickButton("gma4_Continue_DifferentQRCode_Btn");
				if (currentFlagValue)
				{
					HtmlResult.passed("Clicking on 'Continue' button on 'Checking In Different Restaurant' screen", "'Continue' button should be clicked present on 'Checking In Different Restaurant' screen.", "'Continue' button is clicked present on 'Checking In Different Restaurant' screen.");
				}
				else
				{
					HtmlResult.failed("Clicking on 'Continue' button on 'Checking In Different Restaurant' screen", "'Continue' button should be clicked present on 'Checking In Different Restaurant' screen.", "Unable to click on 'Continue' button on 'Checking In Different Restaurant' screen.");
				}
			}
			else
			{
				HtmlResult.failed("Verifying 'Checking In Different Restaurant' screen.", "'Checking In Different Restaurant' message should be displayed.", "'Checking In Different Restaurant' message is not displayed.");
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
	}
	
	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � gma4_GetOrderNumber
	 * Method Description - Get the 'Order Number' from Order Confirmation screen
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Madhur Barsainya
	 * Creation Date - 11/28/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/

	public void gma4_GetOrderNumber()
	{
		try
		{
			boolean currentFlagValue =false; 
			String strActualTotalPrice ;
			float ActualTotalPrice;
			//ExpectedTotalPrice = SubTotal_OrderBasketScreen; 
			currentFlagValue = appUIDriver.getAppObjectFound("gma4_OrderNumber_OrderConfirmationPage");
			if (currentFlagValue)
			{
				strOrderNumber = appUIDriver.getObjectText("gma4_OrderNumber_OrderConfirmationPage");
				HtmlResult.passed("Capturing 'Order Number'.", "User should be able to capture 'Order Number'.", "Product is ordered successfully with 'Order Number' : "+strOrderNumber+".");
				
				if(appUIDriver.scrollToObjectFound("gma4_NewPrice_OrderSummaryScreen"))
				{
	    			strActualTotalPrice = appUIDriver.getObjectText("gma4_NewPrice_OrderSummaryScreen");
	    			ActualTotalPrice = Float.parseFloat(((strActualTotalPrice.split("£"))[1].split("\\*"))[0]);
	    			SubTotal_OderSummaryScreen=ActualTotalPrice;
    			}
			}
			else
			{
				HtmlResult.failed("Capturing Order Number.", "User should be able to capture order number.", "Unable to capture order number.");
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
	}
	
	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � gma4_verifyClickdifferentdefaultSavedCard
	 * Method Description - 
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Mritunjay
	 * Creation Date - 11/30/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/
	public void gma4_verifyClickdifferentdefaultSavedCard(Map<String, String> input){

		try{

			boolean currentFlagValue = false;
			String strLast4Digit,strObjectName;
			boolean blndefaultcard = gma4_verifySavedCard(input);
			if(blndefaultcard){

				clickButtonScreen(input);

				strObjectName = input.get("mobileObjectName1");

				String strCardNumber = input.get("CardNumber1");

				String strCardHolderName= input.get("CardHolderName1");
				String Str = new String(strCardNumber);			
				strLast4Digit=Str.substring(12);
				//System.out.println(strLast4Digit);
				String strDynamicText = strCardHolderName+"-"+strLast4Digit;//strLast4Digit
				String strDynamicObjectName = strDynamicText;		

				if (strDynamicObjectName != null && strDynamicObjectName.length() > 1) {
					currentFlagValue = appUIDriver.clickMobileDynamicAppObject(strObjectName,strDynamicObjectName);
					if (currentFlagValue) 
					{
						HtmlResult.passed("User select different from default/preselected Card","User should be select different from default/preselected Card","User is selected different from default/preselected Card" +strDynamicText);
					}
					else{
						HtmlResult.failed("User select different from default/preselected Card","User should be select different from default/preselected Card","User is not selected different from default/preselected Card" +strDynamicText);
					}
					currentFlagValue = appUIDriver.clickButton("gma4_PaymentMethod_Save");
					if (!currentFlagValue)
					{
						HtmlResult.failed("Clicking on 'Save' button on Payment selection screen.", "User should be able to click on 'Save' button on Payment selection screen.", "Unable to click on 'Save' button on Payment selection screen.");
					}
				}
			}
			else{
				HtmlResult.failed("User select different from default/preselected Card","User should be select different from default/preselected Card","User is not selected different from default/preselected Card");
			}

			currentFlagValue = gma4_ClickOnCheckIn();
			if(currentFlagValue){

				currentFlagValue=gma4_ScanQRCode();	

			}else{
				HtmlResult.failed("User Click on Check-In-Resutrant","User should Click on Check-In-Resutrant","User is not Click on Check-In-Resutrant");
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
	}
	
	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � gma4_
	 * Method Description - 
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Madhur Barsainya
	 * Creation Date - 12/01/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/
	public void gma4_VerifyProductInOutOfStockScreen(Map<String, String> input)
	{
		try
		{
			boolean currentFlagValue = false;
			String strMessage ;
			String strActualObject = "";
			String[] strProdWithSideAndDrinkItemFlag = input.get("ProductWithSideAndDrinkItem").split("\\$");
			String strProduct ;
			String[] arrProducts ;
			int intProductCounter = 0;
			String strProductName = "";
			String strOSType = EggPlant.getValueFromExcel("OS_TYPE");
			
			SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
			Date date = new Date();

			// UKTime1 and UKTime2 check breakfast and lunch time
			String ISTStartTime=EggPlant.getValueFromExcel("StartTime");
			String ISTEndTime=EggPlant.getValueFromExcel("EndTime");
			String shopTime = parser.format(date);
			Date UKTime1 = parser.parse(ISTStartTime); //Denotes Start breakfast UK time UK 
			Date UKTime2 = parser.parse(ISTEndTime); //Denotes end breakfast UK time UK
			Date userDate = parser.parse(shopTime);
			
			if (userDate.after(UKTime1) && userDate.before(UKTime2)) 
			{
				strProduct =input.get("BrkfstProduct").trim();  // eg - Vanilla Milkshake
			}
			else
			{
				strProduct =input.get("LunchProduct").trim();  // eg - Spicy Vegetable Deluxe
			}
			arrProducts = strProduct.split("\\$");

			currentFlagValue = appUIDriver.getAppObjectFound("gma4_Text_OutOfStockScreen");
			if (currentFlagValue)
			{
				strMessage =  appUIDriver.getObjectText("gma4_Text_OutOfStockScreen");
				HtmlResult.passed("Verifying Out of Stock screen.", "User should be on the Out of Stock screen.", "User is on the 'Out of Stock' screen which displays : "+strMessage);
				
				for (intProductCounter = 0; intProductCounter<arrProducts.length; intProductCounter++)
				{
					if (strProdWithSideAndDrinkItemFlag[intProductCounter].equalsIgnoreCase("YES"))
					{
						strActualObject = "gma4_ProdNameWithSideAndDrinkItem_OutOfStockScrn";
						String[] arrProdSideAndDrinkItem = arrProducts[intProductCounter].split("#");
						if (strOSType.equalsIgnoreCase("Android"))
						{
							currentFlagValue = appUIDriver.getAppObjectFoundDynamic(strActualObject, arrProducts[intProductCounter]);
						}
						else
						{
							currentFlagValue = false;
							int intTotalOutOfStockProduct = appUIDriver.getObjectCount(strActualObject);
							for(int i = 1; i<=intTotalOutOfStockProduct; i++)
							{
								strProductName = appUIDriver.getTextForDynamicObject("gma4_ProdNameWithSideAndDrinkItem_OutOfStockScrn1", Integer.toString(i));
								if (strProductName.contains(arrProdSideAndDrinkItem[0]) && strProductName.contains(arrProdSideAndDrinkItem[1]) && strProductName.contains(arrProdSideAndDrinkItem[2]))
								{
									currentFlagValue = true;
									break;
								}
							}
						}
						if (currentFlagValue)
						{
							HtmlResult.passed("Verifying product on 'Out of Stock' screen.", "Product '"+arrProdSideAndDrinkItem[0]+"' should be present on 'Out of Stock' screeen with Side Item '"+arrProdSideAndDrinkItem[1]+"' and Drink Item '"+arrProdSideAndDrinkItem[2]+"'.",  "Product '"+arrProdSideAndDrinkItem[0]+"' is present on 'Out of Stock' screeen with Side Item '"+arrProdSideAndDrinkItem[1]+"' and Drink Item '"+arrProdSideAndDrinkItem[2]+"'.");
						}
						else
						{
							HtmlResult.failed("Verifying product on 'Out of Stock' screen.", "Product '"+arrProdSideAndDrinkItem[0]+"' should be present on 'Out of Stock' screeen with Side Item '"+arrProdSideAndDrinkItem[1]+"' and Drink Item '"+arrProdSideAndDrinkItem[2]+"'.", "Product '"+arrProdSideAndDrinkItem[0]+"' is not present on 'Out of Stock' screeen with Side Item '"+arrProdSideAndDrinkItem[1]+"' and Drink Item '"+arrProdSideAndDrinkItem[2]+"'.");
						}
					}
					else
					{
						strActualObject = "gma4_ProdName_OutOfStockScrn";
						currentFlagValue = appUIDriver.getAppObjectFoundDynamic(strActualObject, arrProducts[intProductCounter]);
						if (currentFlagValue)
						{
							HtmlResult.passed("Verifying product on 'Out of Stock' screen.", "Product '"+arrProducts[intProductCounter]+"' should be present on 'Out of Stock' screeen.", "Product '"+arrProducts[intProductCounter]+"' is present on 'Out of Stock' screeen.");
						}
						else
						{
							HtmlResult.failed("Verifying product on 'Out of Stock' screen.", "Product '"+arrProducts[intProductCounter]+"' should be present on 'Out of Stock' screeen.", "Product '"+arrProducts[intProductCounter]+"' is not present on 'Out of Stock' screeen.");
						}
					}
				}
			}
			else
			{
				HtmlResult.failed("Verify 'Out of Stock' Screen.", "User should be on 'Out of Stock' screen.", "User is not currently on 'Out of Stock' screen.");
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
	}
	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � gma4_VerifyPrice_NewOrderTotalScreen
	 * Method Description - Verifying Price on New Order Total screen  
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Madhur Barsainya
	 * Creation Date - 11/28/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/

	public void gma4_VerifyPrice_NewOrderTotalScreen()
	{
		try
		{	
			double ExpectedTotalPrice ;
			double ActualTotalPrice ;
			String strActualTotalPrice ;
			if (SubTotal_OutOfStockScreen == 0.0)
			{
				ExpectedTotalPrice = SubTotal_OrderBasketScreen; 
			}
			else
			{
				ExpectedTotalPrice = SubTotal_OutOfStockScreen; 
			}
			
			strActualTotalPrice = appUIDriver.getObjectText("gma4_NewPrice_NewOrderTotalScreen");
			ActualTotalPrice = Float.parseFloat(((strActualTotalPrice.split("£"))[1].split("\\."))[0]+"."+((strActualTotalPrice.split("£"))[1].split("\\."))[1]);
			
			if(ActualTotalPrice == ExpectedTotalPrice)
			{
				HtmlResult.passed("Veriyfing Price on 'New Order Total' screen.", "Expected and Actual pirce value should be same.", "Expected New Total Price '"+String.format("%.2f",ExpectedTotalPrice)+"' actual New Total Price '"+String.format("%.2f",ActualTotalPrice)+"' are matching.");
			}
			else
			{
				HtmlResult.failed("Veriyfing Price on 'New Order Total' screen.", "Expected and Actual pirce value should be same.", "Expected New Total Price '"+String.format("%.2f",ExpectedTotalPrice)+"' actual New Total Price '"+String.format("%.2f",ActualTotalPrice)+"' are not matching.");
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
	}
	
	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � gma4_SelectOffer
	 * Method Description - Selecting Offer from Home Page and adding it to Basket.  
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Madhur Barsainya
	 * Creation Date - 12/05/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/

	public void gma4_SelectOffer(Map<String, String> input)
	{
		try
		{	
			String strOfferName = input.get("OfferName");
			String strSelectProductInOfferFlag = input.get("SelectProductInOffer");
			String[] arrProductName ;
			boolean currentFlagValue = false;
			
			currentFlagValue = appUIDriver.clickMobileDynamicAppObject("gma4_OfferName_HomePage", strOfferName);
			if (currentFlagValue)
			{
				HtmlResult.passed("Selecting Offer.", "Offer '"+strOfferName+"' should be selected.", "Offer '"+strOfferName+"' is selected.");
				if (strSelectProductInOfferFlag.equalsIgnoreCase("YES"))
				{
					arrProductName = input.get("ProductName").split("#");
					for(int counter=0; counter<arrProductName.length; counter++)
					{
						currentFlagValue = appUIDriver.TapOnMobileObject("gma4_SelectProduct_SelectOffer_Edit");
						if (currentFlagValue)
						{
							currentFlagValue = appUIDriver.clickMobileDynamicAppObject("gma4_ProductName_SelectOffer", arrProductName[counter]);
							if (currentFlagValue)
							{
								currentFlagValue = appUIDriver.clickButton("gma4_Done_Btn");
								if (currentFlagValue)
								{
									HtmlResult.passed("Selecting Product while ordering Offer.", "Product '"+arrProductName[counter]+"' should be selected.", "Product '"+arrProductName[counter]+"' is selected while selecting offer.");
								}
							}
							else
							{
								HtmlResult.failed("Selecting Product while ordering Offer.", "'"+arrProductName[counter]+"' product should be present.", "'"+arrProductName[counter]+"' product is not present.");
							}
						}
					}
				}
				currentFlagValue = appUIDriver.clickButton("gma4_ApplyToMobileOffer_Btn");
				if (currentFlagValue)
				{
					HtmlResult.passed("Selecting Offer.", "User should be able to add the Offer to Basket.", "Offer '"+strOfferName+"' is Added to Basket.");
				}
				else
				{
					HtmlResult.failed("Clickng on 'Add to Mobile Offer' button.", "User should be able to click on 'Apply to Mobile Offer' button after selecting Offer.", "Unable to click on 'Apply to Mobile Offer' button.");
				}
			}
			else
			{
				HtmlResult.failed("Selecting Offer from Home Page.", "User should be able to select on '"+strOfferName+"' Offer.", "Unable to select '"+strOfferName+"' offer.");
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
		}
	}
	
/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � readFinalOrderDetails
	 * Method Description - Read final ordered product name 
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Nandini
	 * Creation Date - 11/22/2017
	 * Modification History: Completed
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/
	public void readFinalOrderPrice()
	{	
		try
		{
			boolean currentFlagValue =false; 
			String strActualTotalPrice ;
			float ActualTotalPrice;
			//ExpectedTotalPrice = SubTotal_OrderBasketScreen; 
		
			currentFlagValue = appUIDriver.scrollToObjectFound("gma4_NewPrice_OrderSummaryScreen");
			if(currentFlagValue)
				{
    			strActualTotalPrice = appUIDriver.getObjectText("gma4_NewPrice_OrderSummaryScreen");
    			ActualTotalPrice = Float.parseFloat(((strActualTotalPrice.split("£"))[1].split("\\*"))[0]);
    			SubTotal_OderSummaryScreen=ActualTotalPrice;
    			HtmlResult.passed("Capturing 'Order Price'.", "User should be able to capture 'Order Price'.", "Product is ordered successfully with Price :"+ActualTotalPrice+".");
    			}
			
			
			else
			{
				HtmlResult.failed("Capturing 'Order Price'.", "User should be able to capture 'Order Price'.","Unable to Capture Prize ");
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
	

	}}
	
	
	
	///////////////////////////////////////////////////////////////////////////////////
	
	
		/***************************************************************************************************************
		 * Project - GMA Method
		 * Method Name � verifyBagChargesForOrder
		 * Method Description - Go to Checkout order screen and verify 'Bag Charges' as per selection
		 * Return Type - NO value (Void)
		 * Parameters -
		 * Framework - UKIT Master Framework
		 * Author - Nandini Gupta
		 * Creation Date - 11/02/2017
		 * Modification History:
		 * # <Date>     <Who>                  <Mod description>

		 ***************************************************************************************************************/

		public void verifyBagChargesForOrder(Map<String,String> input)
		{
			try
			{
				String objectLocatorName;
				String objectLocatorValue;
				String strCurrentTotalPriceBfrBagChrg="";
				String strCurrentTotalPriceAftBagChrg = "";
				String strCurrentQtyValue;
				String strExpectedBagQtyValue=input.get("expectedBagQtyVal").trim();
				String strBagChoiceButton=input.get("bagChoice").trim();


				// for select confirm order type
				if(appUIDriver.clickButton("gma4_OrderType_TakeOut")) // Select 'Take Out Option'
				{
					if(strBagChoiceButton.equalsIgnoreCase("Continue") ||strBagChoiceButton.equalsIgnoreCase("Continue"))
					{
						if(appUIDriver.getAppObjectFound("gma4_BagCharges_ConfirmationInfo")) //Found Object Order Confirmation
						{
							if(appUIDriver.clickButton("gma4_BagCharges_Quantity_Plus")) //Click to Qty Button
							{
								String strMobileObjectName  = appUIDriver.getAppObjectwithLocator("gma4_BagCharges_TotalPrice"); // read Bag Charges price 
								String[] arrMobileObjectName1 = strMobileObjectName.split("#");
								objectLocatorName =arrMobileObjectName1[0].toString();
								objectLocatorValue= arrMobileObjectName1[1].toString();
								strCurrentQtyValue =appUIDriver.getText(objectLocatorName, objectLocatorValue);
								System.out.println(strCurrentQtyValue);

								if(strExpectedBagQtyValue.equals(strCurrentQtyValue)) // Verify Bag Charges as per input data 
								{
									System.out.println("bag charges value is correct");
									HtmlResult.passed("Bag Charges Value", "Bag Charges value should be added in CheckOut Info", "Bag Charges added successfully in Checkout");							
									//Verify Total Value should be updated	
									if (appUIDriver.getAppObjectFound("gma4_BagCharges_ContinueButton")) // Click on Continue Button
									{
										appUIDriver.clickButton("gma4_BagCharges_ContinueButton");

										if (appUIDriver.scrollToObjectFound("gma4_Checkout_TotalPrice")) // Read Check Screen -  Order Total Price
										{
											strMobileObjectName  = appUIDriver.getAppObjectwithLocator("gma4_Checkout_TotalPrice");
											arrMobileObjectName1 = strMobileObjectName.split("#");
											objectLocatorName =arrMobileObjectName1[0].toString();
											objectLocatorValue= arrMobileObjectName1[1].toString();
											strCurrentTotalPriceAftBagChrg =appUIDriver.getText(objectLocatorName, objectLocatorValue);
											System.out.println(strCurrentTotalPriceAftBagChrg);		
											// 

											// Converting String to 2 digit 
											//****************************************

											strCurrentTotalPriceBeforeBagChrg = strCurrentTotalPriceBeforeBagChrg.replace("£","").trim();
											float intCurrentTotalPriceBeforeBagChrg = Float.parseFloat(strCurrentTotalPriceBeforeBagChrg);								
											String strBagCharges = strCurrentQtyValue.replace("£","").trim();
											float intCurrentBagCharge = Float.parseFloat(strBagCharges);

											float sum = intCurrentTotalPriceBeforeBagChrg + intCurrentBagCharge;
											float newOrderTotalValue = (float) valueRound(sum, 2);
											String strNewOrderTotalValue  = Float.toString(newOrderTotalValue).trim();
											//***************************************
											strNewOrderTotalValue = "£"+strNewOrderTotalValue;

											if(strCurrentTotalPriceAftBagChrg.equals(strNewOrderTotalValue)) //Validate as per Previous data
											{
												HtmlResult.passed("Bag Charges Value", "Bag Charges value should be added in Total Order Value", "Bag Charges is added for Total Order Value in Checkout order summary - Before Bag Value - ' " + strCurrentTotalPriceBeforeBagChrg + "and bag charges - '" + strCurrentQtyValue + "'  and After order meal total Value - '" + strCurrentTotalPriceAftBagChrg + "'");
											}
											else
											{
												HtmlResult.failed("Bag Charges Value", "Bag Charges value should be added in Total Order Value", "Bag Charges is not added for Total Order Value in Checkout order summary - Before Bag Value - ' " + strCurrentTotalPriceBeforeBagChrg + "and bag charges - '" + strCurrentQtyValue + "'  and After order meal total Value - '" + strCurrentTotalPriceAftBagChrg + "'");
											}
										}
										else
										{
											HtmlResult.failed("Bag Charges Value", "Check Screen should be present", "Unable to idenfify Total Price on Checkout screen");
										}
									}
									else
									{
										HtmlResult.failed("Bag Charges Value", "Bag Charges value should be added in CheckOut Info", "Bag Charges is not added");							
									}
								}
								else
								{
									HtmlResult.failed("Bag Charges Value", "Bag Charges value should be added in CheckOut Info", "Unable to click on + Button");							
									System.out.println("unable to click on + Button");
								}
							}
						}
					}
				}
				//No Thanks

				if(strBagChoiceButton.equalsIgnoreCase("No Thanks") ||strBagChoiceButton.equalsIgnoreCase("NoThanks"))
				{
					if(appUIDriver.getAppObjectFound("gma4_BagCharges_NoThanksButton"))
					{
						System.out.println("Successfully found NoThanks Button on page");
						if(appUIDriver.clickButton("gma4_BagCharges_Quantity_Plus"))
						{
							String strMobileObjectName1  = appUIDriver.getAppObjectwithLocator("gma4_BagCharges_TotalPrice");
							String[] arrMobileObjectName2 = strMobileObjectName1.split("#");
							objectLocatorName =arrMobileObjectName2[0].toString();
							objectLocatorValue= arrMobileObjectName2[1].toString();
							strCurrentQtyValue =appUIDriver.getText(objectLocatorName, objectLocatorValue);
							System.out.println("current Bag Charges - "+ strCurrentQtyValue);
							if(strExpectedBagQtyValue.equals(strCurrentQtyValue))
							{
								System.out.println("bag charges value is correct");
								HtmlResult.passed("Bag Charges Value", "Bag Charges value should be added in CheckOut Info", "Bag Charges added successfully in Checkout");							
								//Verify Total Value should be updated	
								if (appUIDriver.getAppObjectFound("gma4_BagCharges_NoThanksButton"))
								{
									appUIDriver.clickButton("gma4_BagCharges_NoThanksButton");
									if (appUIDriver.scrollToObjectFound("gma4_Checkout_TotalPrice"))
									{
										String strMobileObjectName  = appUIDriver.getAppObjectwithLocator("gma4_Checkout_TotalPrice");
										String[] arrMobileObjectName1 = strMobileObjectName.split("#");
										objectLocatorName =arrMobileObjectName1[0].toString();
										objectLocatorValue= arrMobileObjectName1[1].toString();
										strCurrentTotalPriceAftBagChrg =appUIDriver.getText(objectLocatorName, objectLocatorValue);
										System.out.println(strCurrentTotalPriceAftBagChrg);	

										if(strCurrentTotalPriceBeforeBagChrg.equals(strCurrentTotalPriceAftBagChrg))
										{
											HtmlResult.passed("Bag Charges Value", "Bag Charges value should not be added in Total Order Value", "Bag Charges is not added for Total Order Value in Checkout order summary - Before Bag Value - ' " + strCurrentTotalPriceBeforeBagChrg + "'  After Bag Value - '" + strCurrentTotalPriceAftBagChrg + "'");
										}
										else
										{
											HtmlResult.failed("Bag Charges Value", "Bag Charges value should not be added in Total Order Value", "Bag Charges is not added for Total Order Value in Checkout order summary - Before Bag Value - ' " + strCurrentTotalPriceBeforeBagChrg + "'  After Bag Value - '" + strCurrentTotalPriceAftBagChrg + "'");
										}
									}
									else
									{
										HtmlResult.failed("Bag Charges Value", "Check Screen should be present", "Unable to idenfify Total Price on Checkout screen");
									}								
								}
								else
								{
									System.out.println("Failed");
								}
							}
						}
						else
						{
							System.out.println("Not clicked on No thanks Button hence failed");
						}
					}
					else
					{
						HtmlResult.failed("Bag Charges Value", "Bag Charges value should be added in CheckOut Info", "No Thanks Button is not present");							
					}
				}
				else
				{
					HtmlResult.failed("Bag Charges Value", "Bag Charges value should be added in CheckOut Info", "Selction Choice is worng - Choose 'No Thanks' or 'Continue' as input data");							

				}
			}
			
			catch(Exception e)
			{
				System.out.println(e.getMessage());
				HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
			}
		}

// Prateek - New Method for round off the value
		public static double valueRound(double value, int places) {
			if (places < 0) throw new IllegalArgumentException();

			long factor = (long) Math.pow(10, places);
			value = value * factor;
			long tmp = Math.round(value);
			return (double) tmp / factor;
		}
		
		/***************************************************************************************************************
         * Project - GMA Method
         * Method Name � readCheckOutScreenOrderTotal
         * Method Description - read the value from Checkout screen
         * Return Type - NO value (Void)
         * Parameters -
         * Framework - UKIT Master Framework
         * Author - Nandini Gupta
         * Creation Date - 11/02/2017
         * Modification History:
         * # <Date>     <Who>                  <Mod description>

         ***************************************************************************************************************/

         public void readCheckOutScreenOrderTotal(Map<String,String> input)
         {
                try{
                      String objCheckoutOrderTotal =  input.get("mobileObject");

                      if (appUIDriver.getAppObjectFound("gma4_Ordering_CheckOut"))
                      {
                             gma4_ClickOnCheckOut();
                      }
                      if (appUIDriver.getAppObjectFound(objCheckoutOrderTotal)) // Click on Continue Button
                      {
                             if (appUIDriver.scrollToObjectFound(objCheckoutOrderTotal)) // Read Check Screen -  Order Total Price
                             {
                                    String strMobileObjectName  = appUIDriver.getAppObjectwithLocator(objCheckoutOrderTotal);
                                    String[] arrMobileObjectName1 = strMobileObjectName.split("#");
                                    String objectLocatorName =arrMobileObjectName1[0].toString();
                                    String objectLocatorValue= arrMobileObjectName1[1].toString();
                                    String strCurrentTotalPriceAftBagChrg =appUIDriver.getText(objectLocatorName, objectLocatorValue);
                                    strCurrentTotalPriceBeforeBagChrg = strCurrentTotalPriceAftBagChrg.trim();
                                    if (appUIDriver.getAppObjectFound("gma4_common_BackButton"))
                                    {
                                           appUIDriver.clickButton("gma4_common_BackButton");
                                    }
                             }
                             else
                             {
                                    HtmlResult.failed("Bag Charges Value", "Check Screen should be present", "Unable to idenfify Total Price on Checkout screen");
                             }
                      }
                      else
                      {
                             HtmlResult.failed("Read CheckOut - Order Total ", " Order Total should be present", "Order Total value is - '" + strCurrentTotalPriceBeforeBagChrg + "'");
                      }
                }
                catch (Exception e)
                {
                      System.out.println(e.getMessage());    
                      HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
                }
         }
         
         /***************************************************************************************************************
     	 * Project - GMA Method
     	 * Method Name � gma4_VerifyDynamicObjectPresent
     	 * Method Description - to verify if object is present
     	 * Return Type - NO value (Void)
     	 * Parameters -
     	 * Framework - UKIT Master Framework
     	 * Author - Mritunjay
     	 * Creation Date - 11/02/2017
     	 * Modification History:
     	 * # <Date>     <Who>                  <Mod description>

     	 ***************************************************************************************************************/
     	public void gma4_VerifyDynamicOfferPresent(Map<String,String> input)
     	{
     		try
     		{
     			String strOfferName =input.get("OfferName");
     			//input.get("custUid").trim();
     			

     			if (appUIDriver.scrollToObjectFoundDynamic("gma4_OfferName_HomePage", strOfferName))
     			{
     				HtmlResult.passed("Verify Dynamic Object.", "Object should be present.--"+strOfferName, "Object is present.--"+strOfferName);
     			}
     			else
     			{
     				HtmlResult.failed("Verify Dynamic Object.", "Object should be present.--"+strOfferName, "Object is not present.---"+strOfferName);
     			}
     		}
     		catch(Exception e)
     		{
     			System.out.println(e.getMessage());
     			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
     		}
     	}	

     	 /***************************************************************************************************************
     	 * Project - GMA Method
     	 * Method Name � gma4_VerifyDynamicObjectPresent
     	 * Method Description - to verify if object is present
     	 * Return Type - NO value (Void)
     	 * Parameters -
     	 * Framework - UKIT Master Framework
     	 * Author - Mritunjay
     	 * Creation Date - 11/02/2017
     	 * Modification History:
     	 * # <Date>     <Who>                  <Mod description>

     	 ***************************************************************************************************************/
     	public void gma4_VerifyDynamicOfferNotPresent(Map<String,String> input)
     	{
     		try
     		{
     			String strOfferName =input.get("OfferName");
     			//input.get("custUid").trim();
     			

     			if (!appUIDriver.scrollToObjectFoundDynamic("gma4_OfferName_HomePage", strOfferName))
     			{
     				HtmlResult.passed("Verify Dynamic Object.", "Object should Not be present.--"+strOfferName, "Object is Not present.--"+strOfferName);
     			}
     			else
     			{
     				HtmlResult.failed("Verify Dynamic Object.", "Object should Not be present.--"+strOfferName, "Object is present.---"+strOfferName);
     			}
     		}
     		catch(Exception e)
     		{
     			System.out.println(e.getMessage());
     			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
     		}
     	}	
    
     	/***************************************************************************************************************
    	 * Project - GMA Method
    	 * Method Name � VerifyDynamicObjectNotPresent
    	 * Method Description - To verify dynamic object is not present 
    	 * Return Type - NO value (Void)
    	 * Parameters -
    	 * Framework - UKIT Master Framework
    	 * Author - Madhur Barsainya
    	 * Creation Date - 11/10/2017
    	 * Modification History:
    	 * # <Date>     <Who>                  <Mod description>

    	 ***************************************************************************************************************/

    	public void VerifyDynamicObjectNotPresent(Map<String, String> input)
    	{
    		try
    		{
    			String strobjName = input.get("mobileObjectName");
    			String strDynamicText = input.get("DynamicText");
    			String strExpectedText= input.get("DynamicText");
    			if (!appUIDriver.getAppObjectFoundDynamic(strobjName,strDynamicText))
    			{
    				HtmlResult.passed("Verify Dynamic Object.", "Object should not be present.", "Object '"+strExpectedText+"' is not present.");
    			}
    			else
    			{
    				HtmlResult.failed("Verify Dynamic Object.", "Object should not be present.", "Object is present.");
    			}
    		}
    		catch (Exception e)
    		{
    			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
    		}
    	}
    	/***************************************************************************************************************
    	 * Project - GMA Method
    	 * Method Name � favouriteOrderVerification
    	 * Method Description - add order in to fav list
    	 * Return Type - NO value (Void)
    	 * Parameters -
    	 * Framework - UKIT Master Framework
    	 * Author - Nandini Gupta
    	 * Creation Date - 11/02/2017
    	 * Modification History:
    	 * # <Date>     <Who>                  <Mod description>

    	 ***************************************************************************************************************/


    public void favouriteOrderVerification(Map<String, String> input)
    	{
    		try
    		{
    			boolean BlnFound =false;
    			String bookWithoffer=input.get("BookedWithOffer");
    			BlnFound = appUIDriver.clickButton("gma4_hamburger_action");
    			if(BlnFound)
    			{
    				BlnFound =appUIDriver.clickButton("gma_Menu_FavouriteOrders");
    				if (BlnFound)
	    			{
	    				Thread.sleep(3000);
	    				BlnFound = appUIDriver.clickMobileDynamicAppObject("gma4_FavOrderName_FavOrderScreen",FavOrderName);
	    				if(BlnFound)
	    				{
	    					HtmlResult.passed("Verify Favourite Order on Favourite Order screen.", "Favourite order '"+FavOrderName+"' should be present on Favourite order screen.", "Favourite order '"+FavOrderName+"' is present on Favourite order screen.");
	    					//Verifying that order number should not be displayed.
	    					BlnFound = appUIDriver.getAppObjectFoundDynamic("gma4_OrderNumber_FavOrderScreen", strOrderNumber);
	    					if (BlnFound)
	    					{
	    						HtmlResult.failed("Verifying Order Number on Favourite Order Screen.", "On Favourite Order Screen, Order Number '"+strOrderNumber+"' should not be displayed for Favourite Order '"+FavOrderName+"'.", "On Favourite Order Screen, Order Number '"+strOrderNumber+"' is displayed for Favourite Order '"+FavOrderName+"'.");
	    					}
	    					else
	    					{
	    						HtmlResult.passed("Verifying Order Number on Favourite Order Screen.", "On Favourite Order Screen, Order Number '"+strOrderNumber+"' should not be displayed for Favourite Order '"+FavOrderName+"'.", "On Favourite Order Screen, Order Number '"+strOrderNumber+"' is not displayed for Favourite Order '"+FavOrderName+"'.");
	    					}
	    					
	    					
	    					String strCurrentproductName = appUIDriver.getObjectText("gma4_FavouriteOrders_ProductName");
	    					//Comparing Product Name
	    					if(strCurrentproductName.equalsIgnoreCase(StrCurrentProductName.trim()))
	    					{
	    						HtmlResult.passed("Verify favourite order product name", "Favourite order product name should be present", "Product name of Favourite order page-"+StrCurrentProductName);
	    					}
	    					else
	    					{
	    						HtmlResult.failed("Verify Favourite order product", "Favourite order product name should be present", "Favourite order product name not found ");
	    					}
	    					
	    					String strCurrentProductPrice = appUIDriver.getObjectText("gma4_FavOrder_totalPrice");
	    					if(strCurrentProductPrice.contains(StrCurrentProductprice))
	    					{
	    						HtmlResult.passed("Verify Favourite order product price", "Favourite order product price should be present","Favourite Order Screen product price:"+strCurrentProductPrice+ "is equal to Order Summary Screen Product price:"+StrCurrentProductprice+".");
		    					}
	    					else
	    					{
	    						if (bookWithoffer.equalsIgnoreCase("YES"))
	    						{
	    							HtmlResult.passed("Verify Favourite order product price", "Favourite order product price should be present", "Favourite Order Screen product price:"+strCurrentProductPrice+ "is not equal to Order Summary Screen Product price:"+StrCurrentProductprice+".");
		    						}
	    						else
	    						{
	    							HtmlResult.failed("Verify favourite order product price", "Favourite order product price should be present", "Product price on favourite order scree not captured");	
	    						}
	    					}
	    				}
	    				else
	    				{
	    					HtmlResult.failed("Verify Favourite Order on Favourite Order screen.", "Favourite order '"+FavOrderName+"' should be present on Favourite order screen.", "Favourite order '"+FavOrderName+"' is not present on Favourite order screen.");
	    				}
	    			}
	    		}
    			else
    			{
    				HtmlResult.failed("Verify Favourite order click", "Favourite oreder menu should be click", "Favourite order menu not clicked ");
    			}
    		}

    		catch(Exception e)
    		{
                     System.out.println(e.getMessage());
                     HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
    		}
    	}



    /***************************************************************************************************************
    	 * Project - GMA Method
    	 * Method Name � favouritedOrder
    	 * Method Description - add order in to fav list
    	 * Return Type - NO value (Void)
    	 * Parameters -
    	 * Framework - UKIT Master Framework
    	 * Author - Nandini Gupta
    	 * Creation Date - 11/02/2017
    	 * Modification History:
    	 * # <Date>     <Who>                  <Mod description>

    	 ***************************************************************************************************************/

    	public void favouritedOrder()
    	{
    		try
    		{
    			boolean blnFound = appUIDriver.scrollToObjectFound("gma4_AddToFavOrderBtn");
    			if(blnFound)
    			{
    				blnFound =appUIDriver.clickButton("gma4_AddToFavOrderBtn");
    			}
    			else
    			{
    				blnFound =appUIDriver.clickButton("gma4_odrAddedtoFav");
    			}
    				
    			if (blnFound)
    			{
					String randomStr=appUIDriver.generateRandom("ABCD1234");// 36 letter.
					FavOrderName = "MyFav_"+randomStr;
					blnFound = appUIDriver.enterText("gma4_favOrderName", FavOrderName);
					if(blnFound)
					{
							blnFound = appUIDriver.clickButton("gma4_FavOrder_savebtn");
							if(blnFound)
	    					{
								blnFound = appUIDriver.scrollToObjectFound("gma4_orderSumm_totalText");
		    					if(blnFound)
		    					{
		    						String strOfferProductname  = appUIDriver.getObjectText("gma4_offers_order_ProductName");
		    						StrCurrentProductName = strOfferProductname;
		
		    						String strOfferProductprice  = appUIDriver.getObjectText("gma4_Offers_Order_TotalProductPrice");
		    						StrCurrentProductprice = strOfferProductprice;
		    						
		    						HtmlResult.passed("Adding order to Favourites.", "User should be able to add order to Favourites.", "Added order to Favourites with name as : "+FavOrderName);
		    						HtmlResult.passed("Capturing Order details."," Capturing Product name and price on Order Summary Screen.","Product Name:"+strOfferProductname+"and Product Price:"+strOfferProductprice+".");
			    					
		    					}
	    					}
							else
							{
								HtmlResult.failed("Clicking on 'Save' button to add order to Favourites.", "User should be able to click on 'Save' button to add order to Favourites.", "Failed to click on 'Save' button to add order to Favourites.");
							}
	    			}
					else
					{
						HtmlResult.failed("Entering 'Favourite Name' for the order to save.", "User should be able to enter 'Favourite Name' to save the order as Favourite order.", "Unable to ener Favourite Name to save the order as Favourite Order." );
					}
    			}
    		}
    		catch(Exception e)
            {
    			System.out.println(e.getMessage());
    			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
    		}

    	}

    	/***************************************************************************************************************
    	 * Project - GMA Method
    	 * Method Name � VerifyProductNotPresent
    	 * Method Description - Verifying that Product should not be present.
    	 * Return Type - NO value (Void)
    	 * Parameters -
    	 * Framework - UKIT Master Framework
    	 * Author - Madhur Barsainya
    	 * Creation Date - 12/11/2017
    	 * Modification History: Completed
    	 * # <Date>     <Who>                  <Mod description>

    	 ***************************************************************************************************************/
    	public void VerifyProductNotPresent(Map<String,String> input)
    	{
    		try{
    			String strMainProjectObject = "gma4_Ordering_Product_Menu";
    			String strSubProjectObject = "gma4_Ordering_SubProduct_Menu";
    			String strAllProducts;
    			boolean currentFlagValue = false;

    			SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
    			Date date = new Date();

    			// UKTime1 and UKTime2 check breakfast and lunch time
    			String ISTStartTime=EggPlant.getValueFromExcel("StartTime");
    			String ISTEndTime=EggPlant.getValueFromExcel("EndTime");
    			String shopTime = parser.format(date);
    			Date UKTime1 = parser.parse(ISTStartTime); //Denotes Start breakfast UK time UK 
    			Date UKTime2 = parser.parse(ISTEndTime); //Denotes end breakfast UK time UK
    			Date userDate = parser.parse(shopTime);

    			if (userDate.after(UKTime1) && userDate.before(UKTime2)) 
    			{
    				strAllProducts =input.get("BrkfstProduct").trim(); // eg - Milkshakes#Vanilla Milkshake
    			}
    			else
    			{
    				strAllProducts =input.get("LunchProduct").trim(); // eg - Burger#Big Mac
    			}
    			String[] arrAllProducts = strAllProducts.split("#");

    			currentFlagValue= appUIDriver.clickButton("gma4_hamburger_action");
    			if (currentFlagValue)
    			{
    				currentFlagValue = appUIDriver.clickButton("gma4_Menu_StartAnOrder"); // navigating to ordering page
    				if (currentFlagValue)
    				{
    					currentFlagValue = appUIDriver.clickMobileDynamicAppObject(strMainProjectObject, arrAllProducts[0]); //Selecting Product Category
    					if (currentFlagValue)
    					{
    						HtmlResult.passed("Selecting Product Category.", "Product Category '"+arrAllProducts[0]+"' should be selected.", "Product Category '"+arrAllProducts[0]+"' is selected.");
    						currentFlagValue = appUIDriver.clickMobileDynamicAppObject(strSubProjectObject, arrAllProducts[1]); //Selecting Product
    						if (currentFlagValue)
    						{
    							HtmlResult.failed("Verifying Product not present.", "Product '"+arrAllProducts[1]+"' should be present.", "Product '"+arrAllProducts[1]+"' is present.");
    						}
    						else
    						{
    							HtmlResult.passed("Verifying Product not present.", "Product '"+arrAllProducts[1]+"' should be present.", "Product '"+arrAllProducts[1]+"' is not present.");
    						}
    					}
    					else
    					{
    						HtmlResult.failed("Selecting Product Category.", "Product Category '"+arrAllProducts[0]+"' should be selected.", "Product Category '"+arrAllProducts[0]+"' is not present.");
    					}
    				}
    				else
    				{
    					HtmlResult.failed("Clicking Start an Order from Hemburger menu.", "User should be able to click on Start An Order from Hamburger Menu.", "Unable to click on Start An Order from Hamburger Menu.");
    				}
    			}
    			else
    			{
    				HtmlResult.failed("Clicking on 'Ordering' button on Home Page.", "User should be able to click on 'Ordering' button on Home Page.", "Unable to click on 'Ordering' button on Home Page.");
    			}
    		}
    		catch(Exception e)
    		{
    			System.out.println(e.getMessage());
    			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
    		}
    	}
    	/***************************************************************************************************************
    	 * Project - GMA Method
    	 * Method Name � gma4_VerifyPrice_OrderSummaryScreen
    	 * Method Description - Verifying Price on Order Summary screen  
    	 * Return Type - NO value (Void)
    	 * Parameters -
    	 * Framework - UKIT Master Framework
    	 * Author - Madhur Barsainya
    	 * Creation Date - 12/11/2017
    	 * Modification History:
    	 * # <Date>     <Who>                  <Mod description>

    	 ***************************************************************************************************************/

    	public void gma4_VerifyPrice_OrderSummaryScreen()
    	{
    		try
    		{	boolean currentFlagValue = false;
    			double ExpectedTotalPrice ;
    			double ActualTotalPrice ;
    			String strActualTotalPrice ;
				ExpectedTotalPrice = SubTotal_OrderBasketScreen; 
				currentFlagValue=appUIDriver.scrollToObjectFound("gma4_NewPrice_OrderSummaryScreen");
				if(currentFlagValue)
				{
	    			strActualTotalPrice = appUIDriver.getObjectText("gma4_NewPrice_OrderSummaryScreen");
	    			ActualTotalPrice = Float.parseFloat(((strActualTotalPrice.split("£"))[1].split("\\."))[0]+"."+((strActualTotalPrice.split("£"))[1].split("\\."))[1]);
	    			
	    			if(ActualTotalPrice == ExpectedTotalPrice)
	    			{
	    				HtmlResult.passed("Veriyfing Price on 'Order Summary' screen.", "Expected and Actual pirce value should be same.", "Expected Total Price on Order Summary screen '"+String.format("%.2f",ExpectedTotalPrice)+"' actual New Total Price '"+String.format("%.2f",ActualTotalPrice)+"' are matching.");
	    			}
	    			else
	    			{
	    				HtmlResult.failed("Veriyfing Price on 'Order Summary' screen.", "Expected and Actual pirce value should be same.", "Expected New Total Price '"+String.format("%.2f",ExpectedTotalPrice)+"' actual New Total Price '"+String.format("%.2f",ActualTotalPrice)+"' are not matching.");
	    			}
				}
			}
    		catch (Exception e)
    		{
    			System.out.println(e.getMessage());
    			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
    		}
    	}
    	
    	
    	/***************************************************************************************************************
    	 * Project - GMA Method
    	 * Method Name � gma4_VerifyPrice_OrderBasketScreen
    	 * Method Description - Verifying Price on Order Basket screen  
    	 * Return Type - NO value (Void)
    	 * Parameters -
    	 * Framework - UKIT Master Framework
    	 * Author - Madhur Barsainya
    	 * Creation Date - 12/11/2017
    	 * Modification History:
    	 * # <Date>     <Who>                  <Mod description>

    	 ***************************************************************************************************************/

    	public void gma4_VerifyPrice_OrderBasketScreen()
    	{
    		try
    		{	
    			double ExpectedTotalPrice ;
    			double ActualTotalPrice ;
    			String strActualTotalPrice ;
				ExpectedTotalPrice = SubTotal_OrderBasketScreen; 
				
    			strActualTotalPrice = appUIDriver.getObjectText("gma4_SubTotal_OrderBasketScreen");
    			/*String[] parts =strActualTotalPrice .split("\\.");
    			String part1 = parts[0]; 
    			String part2 = parts[1];
    	
    			ActualTotalPrice = Float.parseFloat(part1.split("�")+"."+part2.split("*"));*/
    			ActualTotalPrice = Float.parseFloat(((strActualTotalPrice.split("£"))[1].split("\\*"))[0]);
    			
    			if(ActualTotalPrice > ExpectedTotalPrice)
    			{
    				HtmlResult.passed("Veriyfing Price on 'New Order Basket' screen.", "Expected price value should be less than Actual Price.", "Expected Total Price on Order Summary screen '"+String.format("%.2f",ExpectedTotalPrice)+"'  is less than actual New Total Price '"+String.format("%.2f",ActualTotalPrice)+"");
    			}
    			else if(ActualTotalPrice < ExpectedTotalPrice) 
    			{
    				HtmlResult.passed("Veriyfing Price on 'New Order Basket' screen.", "Expected price value should be greater than Actual Price.", "Expected Total Price on Order Summary screen '"+String.format("%.2f",ExpectedTotalPrice)+"'  is greater than actual New Total Price '"+String.format("%.2f",ActualTotalPrice)+"");
    			}
    			else
    			{
    				HtmlResult.failed("Veriyfing Price on 'New Order Basket' screen.", "Expected price value should not equal to Actual Price.", "Expected New Total Price '"+String.format("%.2f",ExpectedTotalPrice)+"'is equal to actual New Total Price '"+String.format("%.2f",ActualTotalPrice)+"");
    			}
    			}
    			
    		catch (Exception e)
    		{
    			System.out.println(e.getMessage());
    			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
    		}
    	}
    	
    	
    	/***************************************************************************************************************
    	 * Project - GMA Method
    	 * Method Name � gma4_VerifyProducts_OrderSummaryScreen
    	 * Method Description - Verifying Products on Order Summary screen  
    	 * Return Type - NO value (Void)
    	 * Parameters -
    	 * Framework - UKIT Master Framework
    	 * Author - Madhur Barsainya
    	 * Creation Date - 12/11/2017
    	 * Modification History:
    	 * # <Date>     <Who>                  <Mod description>

    	 ***************************************************************************************************************/

//    	public void gma4_VerifyProducts_OrderSummaryScreen(Map<String,String> input)
//    	{
//    		try
//    		{	
//    			boolean ProductFlag = false;
//    			String[] arrExpectedProducts;
//    			String strProductName;
//
//    			SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
//    			Date date = new Date();
//
//    			// UKTime1 and UKTime2 check breakfast and lunch time
//    			String ISTStartTime=EggPlant.getValueFromExcel("StartTime");
//    			String ISTEndTime=EggPlant.getValueFromExcel("EndTime");
//    			String shopTime = parser.format(date);
//    			Date UKTime1 = parser.parse(ISTStartTime); //Denotes Start breakfast UK time UK 
//    			Date UKTime2 = parser.parse(ISTEndTime); //Denotes end breakfast UK time UK
//    			Date userDate = parser.parse(shopTime);
//
//    			if (userDate.after(UKTime1) && userDate.before(UKTime2)) 
//    			{
//    				arrExpectedProducts =input.get("BrkfstProducts").trim().split(";"); //eg - Bagel with Jam#2 Cheese;Suasage and Egg Muffin#2 Cheese
//    			}
//    			else
//    			{
//    				arrExpectedProducts =input.get("LunchProducts").trim().split(";");  //eg - Big Mac#2 Onion,2 Cucumber
//    			}
//    			
//    			appUIDriver.scrollToObjectFound("gma4_ProductName_OrderSummaryScreen");
//    			int objectcount = appUIDriver.getObjectCount("gma4_ProductName_OrderSummaryScreen");
//    			for (int counter=2; counter<=objectcount; counter++)
//    			{
//    				strProductName = appUIDriver.getTextForDynamicObject("gma4_ProductName1_OrderSummaryScreen", Integer.toString(counter)) ;
//    				arrProductName_OrderSummaryScreen[counter-2] = strProductName;
//    			}
//    			
//    			for(int counter2=0; counter2<arrExpectedProducts.length; counter2++)
//    			{
//    				for(int counter3=0; counter3<arrProductName_OrderSummaryScreen.length; counter3++)
//        			{
//    					if(arrExpectedProducts[counter2].equalsIgnoreCase(arrProductName_OrderSummaryScreen[counter3]))
//    					{
//    						ProductFlag = true;
//    						HtmlResult.passed("Verifying Product on Order Summary Screen.", "'"+arrExpectedProducts[counter2]+"' should be present on Order Summary Screen.", "'"+arrExpectedProducts[counter2]+"' is present on Order Summary Screen.");
//    					}
//        			}
//    				if(!ProductFlag)
//        			{
//        				HtmlResult.failed("Verifying Product on Order Summary Screen.", "'"+arrExpectedProducts[counter2]+"' should be present on Order Summary Screen.", "'"+arrExpectedProducts[counter2]+"' is present on Order Summary Screen.");
//        			}
//    				ProductFlag = false;
//    			}
//    		}
//    		catch (Exception e)
//    		{
//    			System.out.println(e.getLocalizedMessage());
//    			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
//    		}
//   		}
    	
    	public void gma4_VerifyProducts_OrderSummaryScreen(Map<String,String> input)
        {
              try
              {    
                    boolean ProductFlag = false;
                    String[] arrExpectedProducts;
                    String strProductName;
   
                    SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
                    Date date = new Date();
   
                    // UKTime1 and UKTime2 check breakfast and lunch time
                    String ISTStartTime=EggPlant.getValueFromExcel("StartTime");
                    String ISTEndTime=EggPlant.getValueFromExcel("EndTime");
                    String shopTime = parser.format(date);
                    Date UKTime1 = parser.parse(ISTStartTime); //Denotes Start breakfast UK time UK
                    Date UKTime2 = parser.parse(ISTEndTime); //Denotes end breakfast UK time UK
                    Date userDate = parser.parse(shopTime);
   
                    if (userDate.after(UKTime1) && userDate.before(UKTime2))
                    {
                          arrExpectedProducts =input.get("BrkfstProducts").trim().split(";"); //eg - Bagel with Jam#2 Cheese;Suasage and Egg Muffin#2 Cheese
                    }
                    else
                    {
                         arrExpectedProducts =input.get("LunchProducts").trim().split(";");  //eg - Big Mac#2 Onion,2 Cucumber
                    }
                   
                    appUIDriver.scrollToObjectFound("gma4_ProductName_OrderSummaryScreen");
                    int objectcount = appUIDriver.getObjectCount("gma4_ProductName_OrderSummaryScreen");
                    for (int counter=2; counter<=objectcount; counter++)
                    {
                          strProductName = appUIDriver.getTextForDynamicObject("gma4_ProductName1_OrderSummaryScreen", Integer.toString(counter)) ;
                          //arrProductName_OrderSummaryScreen[counter-2] = strProductName;
                          arrProductName_OrderSummaryScreen.add(strProductName);
                    }
                   
                    for(int counter2=0; counter2<arrExpectedProducts.length; counter2++)
                    {
                                for(String strActualProd : arrProductName_OrderSummaryScreen) 
                                {
                                      if(arrExpectedProducts[counter2].contains("#"))
                                      {
                                            strActualProd = strActualProd.replaceAll(" \n", "#");
                                      }
                                      if(arrExpectedProducts[counter2].equalsIgnoreCase(strActualProd))
                                      {
                                            ProductFlag = true;
                                            HtmlResult.passed("Verifying Product on Order Summary Screen.", "'"+arrExpectedProducts[counter2]+"' should be present on Order Summary Screen.", "'"+arrExpectedProducts[counter2]+"' is present on Order Summary Screen.");
                                      }
                                } 
                          /*for(int counter3=0; counter3<arrProductName_OrderSummaryScreen.size(); counter3++)
                          {
                                if(arrExpectedProducts[counter2].equalsIgnoreCase(arrProductName_OrderSummaryScreen.get(counter3)))
                                {
                                      ProductFlag = true;
                                      HtmlResult.passed("Verifying Product on Order Summary Screen.", "'"+arrExpectedProducts[counter2]+"' should be present on Order Summary Screen.", "'"+arrExpectedProducts[counter2]+"' is present on Order Summary Screen.");
                                }
                          }*/
                          if(!ProductFlag)
                          {
                                HtmlResult.failed("Verifying Product on Order Summary Screen.", "'"+arrExpectedProducts[counter2]+"' should be present on Order Summary Screen.", "'"+arrExpectedProducts[counter2]+"' is present on Order Summary Screen.");
                          }
                          ProductFlag = false;
                    }
              }
              catch (Exception e)
              {
                    System.out.println(e.getLocalizedMessage());
                    HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
              }
              }
    	
    	/***************************************************************************************************************
    	 * Project - GMA Method
    	 * Method Name � gma_VerifyProductInOrderBasketScreen
    	 * Method Description - Verifying Product in Order basket screen
    	 * Return Type - NO value (Void)
    	 * Parameters -
    	 * Framework - UKIT Master Framework
    	 * Author - Madhur Barsainya
    	 * Creation Date - 11/22/2017
    	 * Modification History: Completed
    	 * # <Date>     <Who>                  <Mod description>

    	 ***************************************************************************************************************/

    	public void gma4_VerifyMealInOrderBasketScreen(Map<String, String> input)
    	{
    		try
    		{
    			String strProductObject = "gma4_ItemName_CheckOutScreen";
    			String strProductWithCondimentObject = "gma4_ItemNameWithCustomization_CheckOutScreen";
    			String strAllProducts ;
    			String strActualObject ;
    			String strItemPresentFlag = input.get("ItemPresent");
    			boolean currentFlagValue = false;
    			int intProductCounter ;
    			ArrayList<String> arrProductPriceInOrderSmryScren = new ArrayList<String>();
    			String strProductPrice;
    			String strSubTotal_OrderBasketScreen;
    			
    			SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
    			Date date = new Date();

    			// UKTime1 and UKTime2 check breakfast and lunch time
    			String ISTStartTime=EggPlant.getValueFromExcel("StartTime");
    			String ISTEndTime=EggPlant.getValueFromExcel("EndTime");
    			String shopTime = parser.format(date);
    			Date UKTime1 = parser.parse(ISTStartTime); //Denotes Start breakfast UK time UK 
    			Date UKTime2 = parser.parse(ISTEndTime); //Denotes end breakfast UK time UK
    			Date userDate = parser.parse(shopTime);
    			
    			if (userDate.after(UKTime1) && userDate.before(UKTime2)) 
    			{
    				strAllProducts =input.get("BrkfstProduct").trim();  // eg - Vanilla Milkshake
    			}
    			else
    			{
    				strAllProducts =input.get("LunchProduct").trim();  // eg - Spicy Vegetable Deluxe#2Egg|Shaker Side Salad#No Dressing|Vanilla Milkshake
    			}
    		
    			if (appUIDriver.getAppObjectFound("gma4_Ordering_SubProd_AddToBadge"))
    			{
    				gma4_ClickOnBasketIcon();
    			}
    		
    			String[] arrAllProducts = strAllProducts.split("\\|");
    			String[] arrItemsPresent = strItemPresentFlag.split("\\$");
    			
    			for (intProductCounter = 0; intProductCounter<arrAllProducts.length; intProductCounter++ )
    			{
    				if (arrAllProducts[intProductCounter].contains("#"))
    				{
    					strActualObject = strProductWithCondimentObject;
    				}
    				else
    				{
    					strActualObject = strProductObject;
    				}
    				
    				currentFlagValue = appUIDriver.getAppObjectFoundDynamic(strActualObject, arrAllProducts[intProductCounter]);
    				if (currentFlagValue) 
    				{
    					strProductPrice = appUIDriver.getTextForDynamicObject("gma4_ProductPrice_OrderBasketScreen",arrAllProducts[intProductCounter]); //Getting produc price
    					arrProductPriceInOrderSmryScren.add(strProductPrice);
    					String[] strTempProductName =  arrAllProducts[intProductCounter].split(" ",2);
    					hmProductPriceInOrderSmryScrn.put(strTempProductName[1], strProductPrice);
    					
    					if (arrAllProducts[intProductCounter].contains("#")) //This condition is used when the product is customized
    					{
    						String[] arrProductCondiment = arrAllProducts[intProductCounter].split("#");
    						if (arrItemsPresent[intProductCounter].equalsIgnoreCase("NO"))
    						{
    							HtmlResult.failed("Verifying Product in Basket.", "'"+arrProductCondiment[0]+"' should not be present in Basket with condiments - '"+arrProductCondiment[1]+"'.", "'"+arrProductCondiment[0]+"'  is present in the basket with condiments - '"+arrProductCondiment[1]+"'.");
    						}
    						else
    						{
    							HtmlResult.passed("Verifying Product in Basket.", "'"+arrProductCondiment[0]+"' should be present in Basket with condiments - '"+arrProductCondiment[1]+"'.", "'"+arrProductCondiment[0]+"'  is present in the basket with condiments - '"+arrProductCondiment[1]+"' having price '"+strProductPrice+"'.");
    						}
    					}
    					else
    					{
    						if (arrItemsPresent[intProductCounter].equalsIgnoreCase("NO")) //If product is present and expected is that it should not be present
    						{
    							HtmlResult.failed("Verifying Product in Basket.", "'"+arrAllProducts[intProductCounter]+"' should not be present in the basket.", "'"+arrAllProducts[intProductCounter]+"' is present in the basket.");
    						}
    						else
    						{
    							HtmlResult.passed("Verifying Product in Basket.", "'"+arrAllProducts[intProductCounter]+"' should be present in the basket.", "'"+arrAllProducts[intProductCounter]+"' is present in the basket having price '"+strProductPrice+"'.");
    						}
    					}
    				}
    				else
    				{
    					if (arrItemsPresent[intProductCounter].equalsIgnoreCase("NO")) //To verify if product should not be present.
    					{
    						HtmlResult.passed("Verifying Product in Basket.", "'"+arrAllProducts[intProductCounter]+"' should not be present in the Basket.", "'"+arrAllProducts[intProductCounter]+"' is not present in the Basket.");
    					}
    					else
    					{
    						//HtmlResult.passed("Verifying Product in Basket.", "'"+arrAllProducts[intProductCounter]+"' should be present in the Basket.", "'"+arrAllProducts[intProductCounter]+"' is present in the Basket.");
    						HtmlResult.failed("Verifying Product in Basket.", "'"+arrAllProducts[intProductCounter]+"' should be present in the Basket.", "'"+arrAllProducts[intProductCounter]+"' is not present in the Basket.");
    					}
    				}
    			}
    			
    			//Capturing Sub total from order basket screen.
    			currentFlagValue = appUIDriver.scrollToObjectFound("gma4_SubTotal_OrderBasketScreen");
    			if(currentFlagValue)
    			{
    				strSubTotal_OrderBasketScreen = appUIDriver.getObjectText("gma4_SubTotal_OrderBasketScreen");
    				//SubTotal_OrderBasketScreen += Float.parseFloat(((strSubTotal_OrderBasketScreen.split("�"))[1].split("\\*"))[0]);
    				SubTotal_OrderBasketScreen = Float.parseFloat(((strSubTotal_OrderBasketScreen.split("\\£"))[1].split("\\*"))[0]);
    				HtmlResult.passed("Capturing 'Sub-Total' from 'Order Basket' screen.", "User should be able to capture 'Sub-Total' from 'Order Basket' screen.", "'Sub-Total' of products on 'Order Basket' screen is : '"+String.format("%.2f",strSubTotal_OrderBasketScreen)+"'.");
    			}
    			else
    			{
    				HtmlResult.failed("Capturing 'Sub-Total' from Order Basket screen.", "User should be able to capture 'Sub-Total' from Order Basket screen.", "Failed to find 'Sub-Total' object on Order Basket screen.");
    			}
    			
    			/*currentFlagValue = appUIDriver.clickButton("gma4_Common_Close");
    			if (!currentFlagValue)
    			{
    				HtmlResult.failed("Clicking on Close button on Order Basket Page.", "User should be able to click on Close button on Order Basket Page.", "Unable to click on Close button on Order Basket Page.");
    			}*/
    		}
    		catch(Exception e)
    		{
    			System.out.println(e.getMessage());
    			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
    		}
    	}
    	
    	/***************************************************************************************************************
    	 * Project - GMA Method
    	 * Method Name � gma_VerifySideDrinkinFavouriteScreen
    	 * Method Description - Verifying DrinkItem in Favourite screen
    	 * Return Type - NO value (Void)
    	 * Parameters -
    	 * Framework - UKIT Master Framework
    	 * Author - Madhur Barsainya
    	 * Creation Date - 12/15/2017
    	 * Modification History: Completed
    	 * # <Date>     <Who>                  <Mod description>

    	 ***************************************************************************************************************/

    	public void gma4_VerifySideDrinkinFavouriteScreen(Map<String, String> input)
    	{
    		try
    		{
    			boolean currentFlagValue = false;
    			String strDrinkItem = input.get("DrinkItem");
    			String strDrinkValue= "";
    			String strDrinkItemObject = ""; 
    			if (hmDrinkItemWithUpliftPrice.containsKey(strDrinkItem))
    			{
    				strDrinkValue = hmDrinkItemWithUpliftPrice.get(strDrinkItem);
    			}
    			if(strDrinkValue.equalsIgnoreCase(""))
    			{
    				strDrinkItemObject = "gma4_DrinkItem_FavouriteOrderScreen";
    			}
    			else
    			{
    				strDrinkItem = strDrinkItem +"#"+ strDrinkValue;
    				strDrinkItemObject = "gma4_DrinkItemWithUpliftPrice_FavouriteOrderScreen";
    			}
    			
    			currentFlagValue = appUIDriver.getAppObjectFoundDynamic(strDrinkItemObject, strDrinkItem);
    			if (currentFlagValue)
    			{
    				if (strDrinkValue.equalsIgnoreCase(""))
    				{
    					HtmlResult.passed("Verifying Drink Item in Favourite Order screen.", "'"+input.get("DrinkItem")+"' should be present in Favourite screen with no Uplift price.", "'"+input.get("DrinkItem")+"' is present in Favourite screen with no Uplift price.");
    				}
    				else
    				{
    					HtmlResult.passed("Verifying Drink Item in Favourite Order screen.", "'"+input.get("DrinkItem")+"' should be present in Favourite screen with Uplift price '"+strDrinkValue+"'.", "'"+input.get("DrinkItem")+"' is present in Favourite screen with Uplift price '"+strDrinkValue+"'.");
    				}
    			}
    			else
    			{
    				HtmlResult.failed("Verifying Drink Item in Favourite Order screen.", "'"+strDrinkItem+"' should be present in Favourite Order screen.", "'"+strDrinkItem+"' is not present in Favourite Order screen.");
    			}
    			
    		}
    		catch (Exception e)
    		{
    			System.out.println(e.getMessage());
    			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
    		}
    	}
    	
    	
    	/***************************************************************************************************************
    	 * Project - GMA Method
    	 * Method Name � gma4_VeirfySideAndDrinkItem_OrderSummaryScreen
    	 * Method Description - To verify Side and Drink Item on Order Summary Screen 
    	 * Return Type - NO value (Void)
    	 * Parameters -
    	 * Framework - UKIT Master Framework
    	 * Author - Madhur Barsainya
    	 * Creation Date - 12/17/2017
    	 * Modification History:
    	 * # <Date>     <Who>                  <Mod description>

    	 ***************************************************************************************************************/

    	public void gma4_VeirfySideAndDrinkItem_OrderSummaryScreen(Map<String, String> input)
    	{
    		try
    		{
    			boolean currentFlagValue = false;
    			String strSideItem = "";
    			String strDrinkItem = "";
    			String strMainSideItem;
    			String strMainDrinkItem;
    			String strSideItemObj = "";
    			String strDrinkItemObj = "";
    			String strUpliftPrice = "";
    			
    			SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
    			Date date = new Date();
    			
    			// UKTime1 and UKTime2 check breakfast and lunch time
    			String ISTStartTime=EggPlant.getValueFromExcel("StartTime");
    			String ISTEndTime=EggPlant.getValueFromExcel("EndTime");
    			String shopTime = parser.format(date);
    			Date UKTime1 = parser.parse(ISTStartTime); //Denotes Start breakfast UK time UK 
    			Date UKTime2 = parser.parse(ISTEndTime); //Denotes end breakfast UK time UK
    			Date userDate = parser.parse(shopTime);
    	
    			if (userDate.after(UKTime1) && userDate.before(UKTime2)) 
    			{
    				strSideItem = input.get("BrkfstSideItem").trim();
    				strDrinkItem = (input.get("BrkfstDrinkItem").trim().split("#"))[1];
    			}
    			else
    			{
    				strSideItem = input.get("LunchSideItem").trim();
    				strDrinkItem = (input.get("LunchDrinkItem").trim().split("#"))[1];
    			}
    			
    			if (strSideItem.contains("#"))
    			{
    				String[] arrSideItem = strSideItem.split("#");
    				strMainSideItem = arrSideItem[0]+"#"+arrSideItem[arrSideItem.length-1];
    				strSideItemObj = "gma4_NestedSideItem_OrderSummaryScreen";
    			}
    			else
    			{
    				strMainSideItem = strSideItem;
    				strSideItemObj = "gma4_SideItem_OrderSummaryScreen";
    			}
    			
    			if(hmDrinkItemWithUpliftPrice.containsKey(strDrinkItem))
    			{
    				strDrinkItemObj = "gma4_DrinkItemWithUplift_OrderSummaryScreen";
    				strUpliftPrice = hmDrinkItemWithUpliftPrice.get(strDrinkItem);
    				strMainDrinkItem = strDrinkItem+"#"+strUpliftPrice;
    			}
    			else
    			{
    				strDrinkItemObj = "gma4_DrinkItem_OrderSummaryScreen";
    				strMainDrinkItem = strDrinkItem;
    			}
    			
    			currentFlagValue = appUIDriver.scrollToObjectFoundDynamic(strSideItemObj, strMainSideItem);
    			if(currentFlagValue)
    			{
    				HtmlResult.passed("Verifying Side Item on Order Summary Screen.", "Side Item '"+strMainSideItem+"' should be present on the screen.", "Side Item '"+strMainSideItem+"' is present on the screen.");
    				currentFlagValue = appUIDriver.scrollToObjectFoundDynamic(strDrinkItemObj, strMainDrinkItem);
    				if (currentFlagValue)
    				{
    					if (strMainDrinkItem == strDrinkItem)
    					{
    						HtmlResult.passed("Verifying Drink Item on Order Summary Screen.", "Drink Item '"+strDrinkItem+"' should be present on Order Summary screen.", "Drink Item '"+strDrinkItem+"' is present on Order Summary Screen.");
    					}
    					else
    					{
    						HtmlResult.passed("Verifying Drink Item on Order Summary Screen.", "Drink Item '"+strDrinkItem+"' with Uplift price '"+strUpliftPrice+"' should be present on Order Summary screen.", "Drink Item '"+strDrinkItem+"' with price '"+strUpliftPrice+"' is present on Order Summary Screen.");
    					}
    				}
    				else
    				{
    					if (strMainDrinkItem == strDrinkItem)
    					{
    						HtmlResult.failed("Verifying Drink Item on Order Summary Screen.", "Drink Item '"+strDrinkItem+"' should be present on Order Summary screen.", "Drink Item '"+strDrinkItem+"' is not present on Order Summary Screen.");
    					}
    					else
    					{
    						HtmlResult.failed("Verifying Drink Item on Order Summary Screen.", "Drink Item '"+strDrinkItem+"' with Uplift price '"+strUpliftPrice+"' should be present on Order Summary screen.", "Drink Item '"+strDrinkItem+"' with price '"+strUpliftPrice+"' is not present on Order Summary Screen.");
    					}
    				}
    			}
    			else
    			{
    				HtmlResult.failed("Verifying Side Item on Order Summary Screen.", "Side Item '"+strMainSideItem+"' should be present on the screen.", "Side Item '"+strMainSideItem+"' is not present on the screen.");
    			}
    			
    		}
    		catch (Exception e)
    		{
    			System.out.println(e.getMessage());
    			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
    		}
    	}
    	
    	/***************************************************************************************************************
    	 * Project - GMA Method
    	 * Method Name � readFinalOrderDetails
    	 * Method Description - To read order details
    	 * Return Type - NO value (Void)
    	 * Parameters -
    	 * Framework - UKIT Master Framework
    	 * Author - Asif
    	 * Creation Date - 12/14/2017
    	 * Modification History:
    	 * # <Date>     <Who>                  <Mod description>

    	 ***************************************************************************************************************/
  	
    	public void readFinalOrderDetails()
    	{
    		try{
    			String StrOrderNumber="";
    			String strProductName="";
    			String strPaymentCardName="";
    			String strProductPrice="";
    			
    			String strOrderTime="";
    			boolean blnFound=false;
    			
    			StrOrderNumber  = appUIDriver.getObjectText("gma4_OrderNumber_OrderConfirmationPage");
    			strOrderNumber =StrOrderNumber ;
    			blnFound = appUIDriver.scrollToObjectFound("gma4_orderSumm_totalText");
				if(blnFound)
				{
				 strProductName  = appUIDriver.getObjectText("gma4_OrderSummary-ProductName");
					StrCurrentProductName = strProductName;
                  if (!strProductName.equals(false))
                  {
				  strProductPrice  = appUIDriver.getObjectText("gma4_OrderedPriceDetails");
					StrCurrentProductprice = strProductPrice;
					
					  if(!strProductPrice.equals(false))
					  {
						
						  strPaymentCardName  = appUIDriver.getObjectText("gma4_OrderedCardDetails");
						
						  if(!strPaymentCardName.equals(false))
						  {
							  strOrderTime  = appUIDriver.getObjectText("gma4_OrderdedOrderTime");
							  if(!strOrderTime.equals(false))
							  {
					
					HtmlResult.passed("Capturing Order details.","Order details should be captured."," Product Name:"+strProductName+", Product Price:"+strProductPrice+", Order Number:"+strOrderNumber+", Payment Date and Time: "+strOrderTime+", and Payment Card Name:"+strPaymentCardName+".");
				              }
						  }
					  }
                  }
               }
			
			else
			{

				HtmlResult.passed("Capturing Order details.","Order details should be captured.","Unable to capture order details.");
			}
    	}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
			
		}
    }

    	/***************************************************************************************************************
    	 * Project - GMA Method
    	 * Method Name � gma4_ChangeConfigurationAndSelectRestaurant
    	 * Method Description - 
    	 * Return Type - NO value (Void)
    	 * Parameters -
    	 * Framework - UKIT Master Framework
    	 * Author - Madhur
    	 * Creation Date - 12/19/2017
    	 * Modification History:
    	 * # <Date>     <Who>                  <Mod description>

    	 ***************************************************************************************************************/
    	public void gma4_ChangeConfigurationAndSelectRestaurant(Map<String,String> input){

    		try
    		{
    			boolean currentFlagValue = false;
    			String strEnvironment = input.get("Environment");
    			String strCity = "411038";
    			currentFlagValue = appUIDriver.clickButton("gma4_hamburger_action");//Menu
    			if (currentFlagValue)
    			{
    				currentFlagValue = appUIDriver.scrollToObjectFound("gma4_Menu_ConfigurationSelection");//config selection
    				if (currentFlagValue)
    				{
    					currentFlagValue = appUIDriver.clickButton("gma4_Menu_ConfigurationSelection");//config selection
    					if (currentFlagValue)
    					{
    						currentFlagValue = appUIDriver.clickMobileDynamicAppObject("gma4_Environment_ConfigurationSelection", strEnvironment);//Envi
    						if (currentFlagValue)
    						{
    							HtmlResult.passed("Selecting Environment.", "User should be able to select Environment for the app.", "'"+strEnvironment+"' environment is selected for the application.");
    							Thread.sleep(10000);
    							currentFlagValue = appUIDriver.clickButton("gma4_GetStarted_ConfigurationSelection");//get started
    							if (currentFlagValue)
    							{
    								currentFlagValue = appUIDriver.enterText("gma4_EnterCity_FirstLoad", strCity);
    								if (currentFlagValue)
    								{
    									appUIDriver.pressMobileKey("ENTER");
    									HtmlResult.passed("Entering City/Restaurant Zip Code.", "User should be able to enter City/Restaurant Zip Code.", "'"+strCity+"' is entered as City/Restaurant Zip Code.");
    									Thread.sleep(5000);
    									String strRestaurantName = appUIDriver.getObjectText("gma4_RestaurantName_FirstLoad");
    									currentFlagValue = appUIDriver.getAppObjectFound("gma4_SaveToFavourite_FirstLoad");
										if (!currentFlagValue)
										{
											HtmlResult.passed("Verifying 'Save to Favourite' button.", "'Save to Faourite' button should not be present while selecting restaurant on first load.", "'Save to Faourite' button is present while selecting restaurant n first load.");
	    									currentFlagValue = appUIDriver.clickButton("gma4_Continue_FirstLoad");//continue
	    									if (currentFlagValue)
	    									{
	    										currentFlagValue = appUIDriver.clickButton("gma4_OK_RestaurantSaved_FirstLoad");//ok
        										if (currentFlagValue)
        										{
        											HtmlResult.passed("Selecting Restaurant.", "User should be able to select Restaurant.", "'"+strRestaurantName+"' restaurant is selected.");
        										}
        										else
        										{
        											HtmlResult.failed("Clicking on 'OK' button after selecting Lab.", "User shoule be able to click on 'OK' button.", "Unable to click on 'OK' button."); 
        										}
	    									}
	    									else
	    									{
	    										HtmlResult.failed("Cliking on 'Continue' button.", "User shoule be able to click on 'Continue' button.", "Unable to click on 'Continue' button.");
	    									}
	    								}
										else
										{
											HtmlResult.failed("Verifying 'Save to Favourite' button.", "'Save to Faourite' button should not be present while selecting restaurant on first load.", "'Save to Faourite' button is present while selecting restaurant n first load.");
										}
    								}
    								else
    								{
    									HtmlResult.failed("Entering City to search Restaurant.","User should be able to enter City to search Restaurant.", "Unable to enter '"+strCity+"' city to search restaurant.");
    								}
    							}
    							else
    							{
    								HtmlResult.failed("Clicking on 'Get Started' after selecting environment.", "User should be able to click on 'Get Started' after selecting Environment.", "Unable to click on 'Get Started' after selecting environment.");
    							}
    						}
    						else
    						{
    							HtmlResult.failed("Selecting Environment.", "User should be able to select Environment.", "Unable to select '"+strEnvironment+"' environment.");
    						}
    					}
    					else
    					{
    						HtmlResult.failed("Clicking on 'Configuration Selection' option in Hemburger Menu.", "User should be ablt to click on 'Configuration Selection' option in Hemburger Menu.", "Unable to click on 'Configuration Selectiion' option in Hemburger Menu.");	
    					}
    				}
    				else
    				{
    					HtmlResult.failed("Searching for 'Configuration Selection' option in Hemburger Menu.", "'Configuration Selection' option should be present in Hemburger Menu.", "Unable to find 'Configuration Selection' option in Hemburger Menu.");
    				}
    			}
    			else
    			{
    				HtmlResult.failed("Clicking on Hemburger Menu.", "User should be able to click on Hemburger Menu.", "Unable to click on Hemburger Menu.");
    			}
    		}
    		catch (Exception e)
    		{
    			System.out.println(e.getMessage());
    			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
    		}
    	}
    	
    	/***************************************************************************************************************
    	 * Project - GMA Method
    	 * Method Name � gma4_NoMOMessageForRestaurant
    	 * Method Description - 
    	 * Return Type - NO value (Void)
    	 * Parameters -
    	 * Framework - UKIT Master Framework
    	 * Author - Madhur
    	 * Creation Date - 12/20/2017
    	 * Modification History:
    	 * # <Date>     <Who>                  <Mod description>

    	 ***************************************************************************************************************/
    	public void gma4_NoMOMessageForRestaurant(Map<String,String> input)
    	{
    		try
    		{
    			boolean currentFlagValue = false;
    			String strRestaurantName = input.get("RestaurantName");
    			String strButtonText ;
    			String strLocation = "Finchley UK";
    			currentFlagValue = appUIDriver.clickButton("gma4_hamburger_action");
    			if (currentFlagValue)
    			{
    				currentFlagValue = appUIDriver.clickButton("gma4_Menu_RestaurantLocators");
    				if (currentFlagValue)
    				{
    					strButtonText = appUIDriver.getObjectText("gma4_MobileOrderingButton_SelectRestaurantScreen");
    					if (strButtonText.equalsIgnoreCase("ON"))
    					{
    						currentFlagValue = appUIDriver.clickButton("gma4_MobileOrderingButton_SelectRestaurantScreen");
    						if (currentFlagValue)
    						{
    							HtmlResult.passed("Turning OFF 'Show Mobile Ordering only' button on Restaurant Locator screen.", "User should be able to turn OFF 'Show Mobile Ordering only' button.", "Turned OFF 'Show Mobile Ordering only' button on Resturant Locator screen.");
    						}
    						else
    						{
    							HtmlResult.failed("Turning OFF 'Show Mobile Ordering only' button on Restaurant Locator screen.", "User should be able to click on 'Show Mobile Ordering only' button.", "Unable to click on 'Show Mobile Ordering only' button on Resturant Locator screen.");
    							return;
    						}
    					}
    					else
    					{
    						HtmlResult.passed("Turning OFF 'Show Mobile Ordering only' button turned OFF on Restaurant Locator screen.", "User should be able to turn OFF 'Show Mobile Ordering only' button.", "On Restaurant Locator screen, 'Show Mobile Ordering only' button is already turned OFF .");
    					}
    					
    					currentFlagValue = appUIDriver.enterText("gma4_SearchLocation_RestaurantLocatorScreen", strLocation);
    					if (currentFlagValue)
    					{
    						HtmlResult.passed("Entering Location to search restaurant on restaurant Locator screen.", "User should be able to emter Location on restaurant locator screen to search Restaurant.", "Entered '"+strLocation+"' location on restaurant locator screen to search Restaurant.");
    						appUIDriver.pressMobileKey("ENTER");
    						Thread.sleep(5000);
    						currentFlagValue = appUIDriver.scrollToObjectFoundDynamic("gma4_RestaurantName_RestaurantLocatorScreen", strRestaurantName);
    						if (currentFlagValue)
    						{
    							currentFlagValue = appUIDriver.scrollToObjectFoundDynamic("gma4_NoMOMessage_RestaurantLocatorScreen", strRestaurantName);
    	    					if (currentFlagValue)
    	    					{
    	    						HtmlResult.passed("Verifying 'Mobile Ordering is not supported for this location' message for '"+strRestaurantName+"' restaurant.", "'Mobile Ordering is not supported for this location' message should be displayed for '"+strRestaurantName+"' restaurant.", "'Mobile Ordering is not supported for this location' message is displayed for '"+strRestaurantName+"' restaurant.");
    	    						currentFlagValue = appUIDriver.clickButton("gma4_MobileOrderingButton_SelectRestaurantScreen");
    	    						if (!currentFlagValue)
    	    						{
    	    							HtmlResult.failed("Turning ON 'Show Mobile Ordering only' button on Restaurant Locator screen.", "User should be able to click on 'Show Mobile Ordering only' button.", "Unable to click on 'Show Mobile Ordering only' button on Resturant Locator screen.");
    	    						}
    	    					}
    	    					else
    	    					{
    	    						HtmlResult.failed("Verifying 'Mobile Ordering is not supported for this location' for '"+strRestaurantName+"' restaurant.", "'Mobile Ordering is not supported for this location' message should be displayed for '"+strRestaurantName+"' restaurant.", "'Mobile Ordering is not supported for this location' message is not displayed for '"+strRestaurantName+"' restaurant.");
    	    					}
    						}
    						else
    						{
    							HtmlResult.failed("Searching for Restaurant.", "'"+strRestaurantName+"' restaurant should be present.", "'"+strRestaurantName+"' restaurant is not present.");
    						}
    					}
    					else
    					{
    						HtmlResult.failed("Entering Location to search restaurant.", "User should be able to enter Loction to search restaurant on Restaurant Locator screen.", "Unable to enter Location om Restaurant Location screen.");
    					}
    				}
    				else
    				{
    					HtmlResult.failed("Clicking on 'Restaurant Locator' option in Hemburger Menu.", "'Restaurant Locator' option should be clicked in Hemburger Menu.", "Unable to click 'Restaurant Locator' option in Hemburger Menu.");
    				}
    			}
    			else
    			{
    				HtmlResult.failed("Clicking on Hemburger Menu.", "User should be able to click on Hemburger Menu.", "Unable to click on Hemburger Menu.");
    			}
    		}
    		catch (Exception e)
    		{
    			System.out.println(e.getMessage());
    			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
    		}
   		}
    	
    	
    	
    	/***************************************************************************************************************
    	 * Project - GMA Method
    	 * Method Name � gma4_SelectRestaurant
    	 * Method Description - Select Restaurant from Restaurant Locator
    	 * Return Type - NO value (Void)
    	 * Parameters -
    	 * Framework - UKIT Master Framework
    	 * Author - Madhur
    	 * Creation Date - 12/20/2017
    	 * Modification History:
    	 * # <Date>     <Who>                  <Mod description>

    	 ***************************************************************************************************************/
    	public void gma4_SelectRestaurant(Map<String,String> input)
    	{
    		try
    		{
    			boolean currentFlagValue = false;
    			String strRestaurantName = input.get("RestaurantName");
    			String strLocation = input.get("Location");
    			String strAddToFav = input.get("AddToFavourite");
    			currentFlagValue = appUIDriver.clickButton("gma4_hamburger_action");
    			if (currentFlagValue)
    			{
    				currentFlagValue = appUIDriver.clickButton("gma4_Menu_RestaurantLocators");
    				if (currentFlagValue)
    				{
    					currentFlagValue = appUIDriver.enterText("gma4_SearchLocation_RestaurantLocatorScreen", strLocation);
    					if (currentFlagValue)
    					{
    						HtmlResult.passed("Entering Location to search restaurant on restaurant Locator screen.", "User should be able to emter Location on restaurant locator screen to search Restaurant.", "Entered '"+strLocation+"' location on restaurant locator screen to search Restaurant.");
    						appUIDriver.pressMobileKey("ENTER");
    						Thread.sleep(5000);
							currentFlagValue = appUIDriver.clickMobileDynamicAppObject("gma4_SelectRestaurant_RestaurantLocatorScreen", strRestaurantName);
	    					if (currentFlagValue)
	    					{
	    						HtmlResult.passed("Selecting '"+strRestaurantName+"' restaurant","User should be able to select '"+strRestaurantName+"' restaurant.","'"+strRestaurantName+"' restaurant is selected.");
	    						if (strAddToFav.equalsIgnoreCase("YES"))
	    						{
	    							strFavRestaurantName = "My Fav Restaurant";
	    							currentFlagValue = appUIDriver.getAppObjectFound("gma4_RemoveFrom_RestaurantLocatorScreen");
	    							if (currentFlagValue)
	    							{
	    								currentFlagValue = appUIDriver.clickButton("gma4_RemoveFrom_RestaurantLocatorScreen");
	    								if (currentFlagValue)
	    								{
	    									HtmlResult.warning("Removing restaurant from Favourites.", "User should be able to remove restaurant from Favourites.", "'"+strRestaurantName+"' restaurant was already Favourted restaurant so removing it from Favourites.");
	    									appUIDriver.clickMobileDynamicAppObject("gma4_SelectRestaurant_RestaurantLocatorScreen", strRestaurantName);
	    								}
	    								else
	    								{
	    									HtmlResult.failed("Clicking on 'Remove From Favourites' button.", "User should be able to click on 'Remove From Favourites' button.", "Unable to click on 'Remove From Favourites' button.");
	    								}
	    							}
	    							currentFlagValue = appUIDriver.clickButton("gma4_SaveToFavourites_RestaurantLocatorScreen");
	    							if (currentFlagValue)
	    							{
	    								currentFlagValue = appUIDriver.clickButton("gma4_Name_SaveToFavourites_RestaurantLocatorScreen");
	    								if (currentFlagValue)
	    								{
	    									appUIDriver.enterText("gma4_EditName_SaveToFavourites_RestaurantLocatorScreen", strFavRestaurantName);
	    									currentFlagValue = appUIDriver.clickButton("gma4_Save_SaveToFavourites_RestaurantLocatorScreen");
	    									if (currentFlagValue)
	    									{
	    										currentFlagValue = appUIDriver.clickButton("gma4_SaveToFavourites2_RestaurantLocatorScreen");
	    										HtmlResult.passed("Adding restaurant to Favourite.", "User should be able to Save restaurant to Favourites.", "'"+strRestaurantName+"' restaurant is successfully saved to Favoutires with '"+strFavRestaurantName+"' as Favourite Restaurant Name.");
	    										appUIDriver.backbuttonPress();
	    									}
	    									else
	    									{
	    										HtmlResult.failed("Clicking on 'Save' button after editing Favourite Name.", "User should be able to click on 'Save' button after editing Favourite restaurant name.", "Unable to click on 'Save' button after editing Favourite restaurant name.");
	    										
	    									}
	    								}
	    								else
	    								{
	    									HtmlResult.failed("Clicking on 'Default Name' to Edit save to favourite restaurant name.", "User should be able to click on 'Default Name' to Edit save to favourite restaurant name.", "Unable to click on 'Default Name' to Edit save to favourite restaurant name.");
	    								}
	    							}
	    							else
	    							{
	    								HtmlResult.failed("Clicking on 'Save to Favourites' button for '"+strRestaurantName+"'restaurant.", "User should be able to click on 'Save to Favourites' button for '"+strRestaurantName+"'restaurant.", "Unable to click on 'Save to Favourites' button for '"+strRestaurantName+"'restaurant.");
	    							}
	    						}
	    						else
	    						{
	    							HtmlResult.passed("Selecting Restaurant.", "User should be able to select restaurant.", "'"+strRestaurantName+"' restaurant is selected successfully.");
	    							currentFlagValue = appUIDriver.clickButton("gma4_StartAnOrder_RestaurantLocatorScreen");
	    							if (currentFlagValue)
	    							{
	    								appUIDriver.backbuttonPress();
	    							}
	    							else
	    							{
	    								HtmlResult.failed("Clicking on 'Start an Order' button after selecting restaurant.", "User should be able to click on 'Start an Order' button after selecting restaurant.", "Unable to click on 'Start an Order' button after selecting restaurant.");
	    							}
	    						}
	    					}
	    					else
	    					{
	    						HtmlResult.failed("Selecting '"+strRestaurantName+"' restaurant","User should be able to select '"+strRestaurantName+"' restaurant.","'"+strRestaurantName+"' restaurant is not present.");
	    					}
    					}
    					else
    					{
    						HtmlResult.failed("Entering Location to search restaurant.", "User should be able to enter Loction to search restaurant on Restaurant Locator screen.", "Unable to enter Location om Restaurant Location screen.");
    					}
    				}
    				else
    				{
    					HtmlResult.failed("Clicking on 'Restaurant Locator' option in Hemburger Menu.", "'Restaurant Locator' option should be clicked in Hemburger Menu.", "Unable to click 'Restaurant Locator' option in Hemburger Menu.");
    				}
    			}
    			else
    			{
    				HtmlResult.failed("Clicking on Hemburger Menu.", "User should be able to click on Hemburger Menu.", "Unable to click on Hemburger Menu.");
    			}
    		}
    		catch(Exception e)
    		{
    			System.out.println(e.getMessage());
    			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
    		}
    	}
    	
    	
    	/***************************************************************************************************************
    	 * Project - GMA Method
    	 * Method Name � gma4_VerifyFulfillmentPanel
    	 * Method Description - Verify Fulfillment Panel on ordering screen
    	 * Return Type - NO value (Void)
    	 * Parameters -
    	 * Framework - UKIT Master Framework
    	 * Author - Madhur
    	 * Creation Date - 12/20/2017
    	 * Modification History:
    	 * # <Date>     <Who>                  <Mod description>

    	 ***************************************************************************************************************/
    	public void gma4_VerifyFulfillmentPanel(Map<String,String> input)
    	{
    		try
    		{
    			boolean currentFlagValue = false;
    			String strRestaurantName = input.get("RestaurantName");
    			String strRestNameObject = "";
    			currentFlagValue = appUIDriver.clickButton("gma4_hamburger_action");
    			if (currentFlagValue)
    			{
    				currentFlagValue = appUIDriver.clickButton("gma4_Menu_StartAnOrder"); // navigating to ordering page
    				if (currentFlagValue)
    				{
    					currentFlagValue = appUIDriver.getAppObjectFound("gma4_FulfillmentPanel_OrderingScreen"); //checking for fulfillment panel
    					if (!currentFlagValue)
    					{
    						appUIDriver.clickButton("gma4_Restaurant_OrderingScreen"); //clicking on restaurant on ordering screen
    						currentFlagValue = appUIDriver.getAppObjectFound("gma4_FulfillmentPanel_OrderingScreen");
    						if (!currentFlagValue)
    						{
    							HtmlResult.failed("Verifying Fulfillment Panel.", "On clicking on restaurant, Fullfillment panel should be displayed.","On clicking on restaurant on Ordering screen, Fulfillment Panel does not appear.");
    							return;
    						}
    					}
    					if (!(strFavRestaurantName.equalsIgnoreCase("")))
    					{
    						strRestNameObject = "gma4_FavRestaurantName_FulfillmentPanel";
    						strRestaurantName = strFavRestaurantName +"#"+ strRestaurantName;
    					}
    					else
    					{
    						strRestNameObject = "gma4_RestaurantName_FulfillmentPanel";
    					}
    					currentFlagValue = appUIDriver.getAppObjectFoundDynamic(strRestNameObject, strRestaurantName);
    					if (currentFlagValue)
    					{
    						HtmlResult.passed("Verifying Restaurant Name on Fulfillment Panel.", "'"+strRestaurantName+"' name should be displayed on Fulfillment panel.", "'"+strRestaurantName+"' name is displayed on Fulfillment Panel.");
    						currentFlagValue = appUIDriver.clickButton("gma4_Continue_FulfillmentPanel");
    						if (!currentFlagValue)
    						{
    							HtmlResult.failed("Clicking on Continue button on Fulfillment Panel.", "User should be able to click on Continue button on Fulfillment Panel.", "Unable to click on Continue button on Fulfillment Panel.");
    						}
    						appUIDriver.backbuttonPress();
    					}
    					else
    					{
    						HtmlResult.failed("Verifying Restaurant Name on Fulfillment Panel.", "'"+strRestaurantName+"' name should be displayed on Fulfillment panel.", "'"+strRestaurantName+"' name is not displayed on Fulfillment Panel.");
    					}
    				}
    				else
    				{
    					HtmlResult.failed("Clicking Start an Order from Hemburger menu.", "User should be able to click on Start An Order from Hamburger Menu.", "Unable to click on Start An Order from Hamburger Menu.");
    				}
    			}
    			else
    			{
    				HtmlResult.failed("Clicking on Hemburger Menu.", "User should be able to click on Hemburger Menu.", "Unable to click on Hemburger Menu.");
    			}
    		}
    		catch (Exception e)
    		{
    			System.out.println(e.getMessage());
    			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");
    		}
    	}
    	public void selectProductCategory()
    	{
    		try 
    		{ WebDriverWait wait=new WebDriverWait(Appium.driver,10);
    		
    			 MobileElement menu=(MobileElement) driver.findElement(By.xpath("//android.widget.ImageButton[@content-desc=\"Open Drawer\"]"));
    			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.ImageButton[@content-desc=\"Open Drawer\"]")));
    			 menu.click();
				 HtmlResult.passed("Clicking on Hamburger Menu", "On clicking Hamburger Menu ,menu options should be displayed.","Menu items displayed.");
				 
				 MobileElement category=(MobileElement) driver.findElement(By.xpath("//android.widget.TextView[@text=\"All Categories\"]"));
				 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text=\"All Categories\"]")));
				 category.click();
				 HtmlResult.passed("Clicking on All Category Buttons", "On clicking All Category Buttons ,Categories should be displayed.","Categories displayed.");
				 
				 MobileElement mobileCategory=(MobileElement) driver.findElement(By.xpath("(//android.widget.TextView[@index=1])[1]"));
				 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//android.widget.TextView[@index=1])[1]")));
				 mobileCategory.click();
				 HtmlResult.passed("Clicking on Mobiles Category", "On clicking Mobiles Category ,Mobiles should be displayed.","List of Mobiles displayed.");
				 
				 
    		}
    		catch(Exception e)
    		{System.out.println(e.getMessage());
			HtmlResult.failed("", "Failed due to Exception.", "Failed due to '"+e.getMessage()+"'.");}
    	}
}
