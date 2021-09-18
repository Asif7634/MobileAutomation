package com.cg.Application;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import com.cg.ApplicationLevelComponent.Appium;
import com.cg.ApplicationLevelComponent.EggPlant;
import com.cg.Logutils.HtmlResult;
import com.cg.TestCaseRunner.TestCaseRunner;
import com.cg.UIDriver.AppiumUIDriver;
import com.gargoylesoftware.htmlunit.javascript.host.Set;

import io.appium.java_client.android.AndroidDriver;

//
public class GMA4Appium_6DEC extends Appium{

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
	double SubTotal_OutOfStockScreen = 0.0;
	double SubTotal_OrderSummaryScreen = 0.0;

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

		catch(Exception e){
			System.out.println(e.getMessage());
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
									Thread.sleep(3000);
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
				HtmlResult.passed("Verify Object is not displayed","Object should not be displayed--"+strexpectedData,"Object is not displayed-- '"+strexpectedData+"'.");
			}
			else{
				HtmlResult.failed("Verify Object is not displayed","Object should not be displayed--"+strexpectedData,"Object is displayed");
			}			
		}
		catch(Exception e){
			System.out.println(e.getMessage());
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

		catch(Exception e){

			System.out.println(e.getMessage());
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
		catch(Exception e){
			System.out.println(e.getMessage());
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
					HtmlResult.passed("Selecting Side Item for the product.", "User shold be able to select Side Item for the product.", "'"+strSideItem+"' selected as Side Item for the product.");
				}
				else
				{
					HtmlResult.failed("Click on 'Done' after selecting Side Item for the product..", "User should be able to click on 'Done' after selecting Side Item for the product.", "Unable to click on 'Done' button after selecting Side Item for the product.");
				}
			}
			else
			{
				HtmlResult.failed("Click on '"+strMealType+" Side'.", "User should be able to click on '"+strMealType+" Side' for product.", "Unable to clikc on '"+strMealType+" Side' for the product.");
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getLocalizedMessage());
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
			currentFlagValue = appUIDriver.clickMobileDynamicAppObject("gma4_Drink_ItemPage_Btn",strMealType);
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
					HtmlResult.passed("Selecting Drink Item for the product.", "User shold be able to select Drink Item for the product.", "'"+strDrinkItem+"' selected as Drink Item for the product.");
				}
				else
				{
					HtmlResult.failed("Click on 'Done' after selecting Drink Item for the product..", "User should be able to click on 'Done' after selecting Drink Item for the product.", "Unable to click on 'Done' button after selecting Drink Item for the product.");
				}
			}
			else
			{
				HtmlResult.failed("Click on '"+strMealType+" Side'.", "User should be able to click on '"+strMealType+" Drink' for product.", "Unable to clikc on '"+strMealType+" Drink' for the product.");
			}
			
			
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
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
				HtmlResult.passed("Verify Dynamic Object.", "Object should be present.", "Object is present.");
			}
			else
			{
				HtmlResult.failed("Verify Dynamic Object.", "Object should be present.", "Object is present.");
			}
		}
		catch (Exception e)
		{

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
	public void verifyObjectOnScreen(Map<String,String> input){

		try{
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
					HtmlResult.passed("verify the object displayed ", "Object should be displayed--"+strexpectedData,"Object is displayed-- '"+strexpectedData+"'.");
				}
				else
				{
					HtmlResult.failed("verify the object displayed ", "Object should be displayed--"+strexpectedData,"Object is not displayed--"+strexpectedData);
				}
			}
		}
		catch(Exception e){
			System.out.println(e.getMessage());
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

			String strObjectName =input.get("mobileObjectName");
			String strattributeName = input.get("attributeName");
			String strexpectedData = input.get("expectedData");


			String ObjectattributeValue =appUIDriver.objectGetattribute(strObjectName, strattributeName);

			if(ObjectattributeValue.equalsIgnoreCase("false")){
				HtmlResult.passed("verify the object not checked " + strexpectedData, "Object should be not checked","Object is not checked" +strexpectedData);	
			}
			else if(ObjectattributeValue.equalsIgnoreCase("true")){
				HtmlResult.failed("verify the object checked " + strexpectedData, "Object should be checked","Object is checked" +strexpectedData);	
			}
			else {
				HtmlResult.failed("verify the object checked/Unchecked " +strexpectedData, "Object should be checked/unchecked","Object is checked/unchecked failed." +strexpectedData);	
			}

		}
		catch(Exception e){
			System.out.println(e.getMessage());
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
	 * # <Date>     <Who>                  <Mod description>
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
		} catch (Exception e) {
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
									HtmlResult.failed("Clicking on 'Confirm Delete' button on Order Summary screen.", "User should be able to click on 'Confirm Delete' button on Order Summary screen.", "Unable to click on 'Confirm Delete' button on Order Summary screen.");
								}
						}
						else
						{
							HtmlResult.failed("Clicking on 'Delete Order' button on Order Summary screen.", "User should be able to click on 'Delete Order' button on Order Summary screen.", "Unable to click on 'Delete Order' button on Order Summary screen.");
						}
					}
					else
					{
						HtmlResult.failed("Clicking on 'Edit' button on Order Summary screen.", "User should be able to click on 'Edit' button on Order Summary screen.", "Unable to click on 'Edit' button on Order Summary screen.");
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
				HtmlResult.passed("Verify Object is not displayed","Object should not be displayed"+strExpectedData,"Object is not displayed"+strExpectedData);
			}
			else{
				HtmlResult.failed("Verify Object is not displayed","Object should not be displayed"+strExpectedData,"Object is displayed"+strExpectedData);
			}


		}
		catch(Exception e){
			System.out.println(e.getMessage());
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
		catch(Exception e){
			System.out.println(e.getMessage());
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
			String strCardNumber = input.get("CardNumber");
			String strCardHolderName= input.get("CardHolderName");
			String strExpMonth = input.get("ExpMonth");
			String strExpYear = input.get("ExpYear");
			String strCVV = input.get("CVV");
			String strRememberCardDetails = input.get("RememberCardDetails");

			appUIDriver.enterText("gma_BarClays_CardNumber", strCardNumber);
			appUIDriver.enterText("gma_BarClays_CardHolderName", strCardHolderName);
			//appUIDriver.backbuttonPress("back");
			cardExpiryDateEntry(input);
			appUIDriver.enterText("gma_BarClays_CVC", strCVV);
			appUIDriver.backbuttonPress();

			if (strRememberCardDetails.equalsIgnoreCase("YES"))
			{
				String CheckBoxVaue = appUIDriver.objectGetattribute("gma_BarClays_ChkBoxCondition", "checked");
				if (CheckBoxVaue.equalsIgnoreCase("false"))
				{
					appUIDriver.clickButton("gma_BarClays_ChkBoxCondition");
				}
			}
			else
			{
				String CheckBoxVaue = appUIDriver.objectGetattribute("gma_BarClays_ChkBoxCondition", "checked");
				if (CheckBoxVaue.equalsIgnoreCase("true"))
				{
					appUIDriver.clickButton("gma_BarClays_ChkBoxCondition");
				}
			}
			appUIDriver.clickButton("gma_BarClays_Submit");
			HtmlResult.passed("Entering Card Details", "Should be able to enter card details.", 
					"Card details entered successfully with Card number as '"+strCardNumber+"', Card holder name as '"+strCardHolderName+
					"', Card expiration month and year as '"+strExpMonth+"/"+strExpYear+"' and CVV as '"+strCVV+"'.");
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
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
		} catch (Exception e) {
			System.out.println(e.getMessage());
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
		catch(Exception e){
			System.out.println(e.getMessage());
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
				currentFlagValue= appUIDriver.clickButton("gma4_hamburger_action");
				if (currentFlagValue)
				{
					currentFlagValue = appUIDriver.clickButton("gma4_Menu_StartAnOrder"); // navigating to ordering page
					if (currentFlagValue)
					{
						currentFlagValue = appUIDriver.clickMobileDynamicAppObject(strMainProjectObject, arrProduct[0]); //Selecting Product Category
						if (currentFlagValue)
						{
							currentFlagValue = appUIDriver.clickMobileDynamicAppObject(strSubProjectObject, arrProduct[1]); //Selecting Product	
							if (currentFlagValue)
							{
								currentFlagValue = appUIDriver.clickButton("gma4_Ordering_SubProd_AddBasket"); //Adding product to Basket
								if (currentFlagValue)
								{
									HtmlResult.passed("Selecting Product.", "Product '"+arrProduct[1]+"' should be added to Basket.", "Product '"+arrProduct[1]+"' is added to Basket.");
								}
								else
								{
									HtmlResult.failed("Selecting Product Category.", "Product '"+arrProduct[1]+"' should be added to Basket.", "Product '"+arrProduct[1]+"' is not present.");
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
					HtmlResult.failed("Clicking Hemburger Menu.", "User should be able to click on Hemburger Menu.", "Unable to click on Hemburger Menu.");
				}
			}
		}
		catch(Exception e){
			System.out.println(e.getMessage());
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
		}
	}

	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � gma4_ClickOnBasket
	 * Method Description - Clicking  on Basket to navigate to Order summary screen
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Madhur Barsainya
	 * Creation Date - 11/20/2017
	 * Modification History: Completed
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/

	public void gma4_ClickOnBasketIcon()
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
					HtmlResult.passed("Clicking on Basket Icon.", "Clicking on 'Basket Icon' and user should be navigated to order summary screen.", "Cliked on 'Basket Icon' and navigated to Order summary screen.");
				}
				else
				{
					//HtmlResult.failed("Clicking on Basket Icon.", "User should be navigated to order summary screen.", "Failed to navigate to Order summary screen.");
				}
			}
			else
			{
				//HtmlResult.failed("Clicking on Basket Icon.", "Basket Icon should be clicked.", "Unable to click on Basket Icon.");
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
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

	public void gma4_ClickOnCheckOut()
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
					HtmlResult.passed("Clicking on Product check out button.", "Cliking on 'Check Out' and user should be navigated Check-Out screen.", "Cliked on 'Check-Out' button and navigated Check-Out screen.");
				}
				else
				{
					HtmlResult.failed("Clicking on Product check out button.", "User should be navigated Check-Out screen.", "Failed to navigate to Check-Out screen.");
				}
			}
			else
			{
				HtmlResult.failed("Clicking on Product check out button.", "Product 'Check-Out' button should be clicked.", "Failed to click on Product 'Check-Out' button.");
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
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

	public void gma4_SelectPaymentType(Map<String, String> input)
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
						HtmlResult.failed("Clicking on 'Pay With New Card' payment option.", "User should be able to click on 'Pay With New Card' payment option.", "Unable to click on 'Pay With New Card' payment option.");
					}
				}
				else
				{
					currentFlagValue = appUIDriver.clickButton("gma4_SavedCard_SelectPayment");
					if (!currentFlagValue)
					{
						HtmlResult.failed("Clicking on 'Save Card' payment option.", "User should be able to click on 'Save Card' payment option.", "Unable to click on 'Save Card' payment option.");
					}
				}
				
				if (strOSType.equalsIgnoreCase("Android")) 
				{
					currentFlagValue = appUIDriver.clickButton("gma4_PaymentMethod_Save");
					if (!currentFlagValue)
					{
						HtmlResult.failed("Clicking on 'Save' button on Payment selection screen.", "User should be able to click on 'Save' button on Payment selection screen.", "Unable to click on 'Save' button on Payment selection screen.");
					}
				}
			}
			else
			{
				HtmlResult.failed("Clicking on 'Pay with' option.","User should be able to click on 'Pay with' option.", "Unable to click on 'Pay with' option.");
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
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
					HtmlResult.passed("Clicking on Product 'Check-In' button.", "Cliking on 'Check-In' button and user should be navigated 'Scan QR code' screen.", "Clicked on 'Check-In' button and navigated 'Scan QR code' screen.");
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
			gma4_ClickOnBasketIcon();
			gma4_ClickOnCheckOut();
			gma4_SelectPaymentType(input);
			gma4_ClickOnCheckIn();
			gma4_ScanQRCode();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
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
				}
			}
		}

		catch(Exception e){

			System.out.println(e.getMessage());
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

	public void DeleteAllCards(Map<String, String> input)
	{
		try
		{             
			String strObjectName =input.get("mobileObjectName");
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

							strObjectName =strObjectName.trim();                        
							String strMobileObjectName  = appUIDriver.getAppObjectwithLocator(strObjectName);

							if(!strMobileObjectName.equalsIgnoreCase("false")){
								String[] arrMobileObjectName = strMobileObjectName.split("#");
								String objectLocatorName =arrMobileObjectName[0].toString();
								String objectLocatorValue= arrMobileObjectName[1].toString();

								int delbuttoncount = appUIDriver.countObject(objectLocatorName,objectLocatorValue);
								for(int i = 0;i<delbuttoncount;i++){

									appUIDriver.clickButton("gma4_Save_Card_DEL");                                         
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
			//mobileAppSync();
			String strMobileObjectName  = appUIDriver.getAppObjectwithLocator(strObjectName);

			String[] arrMobileObjectName = strMobileObjectName.split("#");

			String objectLocatorName =arrMobileObjectName[0].toString();
			String objectLocatorValue= arrMobileObjectName[1].toString();

			FoundAndClicked= appUIDriver.clickText(objectLocatorName, objectLocatorValue);

			if (FoundAndClicked)
			{
				HtmlResult.passed("Tap the Mobile app object ", "Tap operation should be done successfully for object - "+ strObjectName + " for the object -" + strObjectName , "Tap operation performed successfully for object - " + strObjectName);
			}
			else
			{
				HtmlResult.failed("Tap the Mobile app object ", "Tap operation should be done successfully for object - "+ strObjectName + " for the object -" + strObjectName , "Tap operation is not performed for object - " + strObjectName);
			}
		}

		catch (Exception e)
		{
			HtmlResult.failed("Tap the Mobile app object ", "Tap operation should be done successfully for object - for the object -"  , "Tap operation is not performed for object - " );

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
		catch(Exception e){
			System.out.println(e.getMessage());
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

	public void gma4_AppBackButton(){

		try{
			appUIDriver.clickButton("gma4_common_BackButton"); 

		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � gma4_EditProduct
	 * Method Description - To edit product from order summary page 
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
					HtmlResult.failed("Clicking on Product Edit button on Order Summary Screen.", "User should be able to click on Edit button for '"+strProduct+"' on Order Summary Screen.", "Unable to click on 'Edit' button for '"+strProduct+"' product on Order Summary Screen. ");
				}
			}
			else
			{
				HtmlResult.failed("Clicking on 'Edit' button present on Order Summary Screen.", "User should be able to click on 'Edit' button present on Order Summary Screen.", "Unable to click on 'Edit' button present on Order Summary Screen. ");
			}

		}
		catch (Exception e)
		{

		}
	}

	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � gma4_EditProduct
	 * Method Description - To edit product from order summary page 
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


	public String readFinalOrderDetails(Map<String, String>input)
	{
		try{
			String CurrentProductText="";
			String objProductName = input.get("objProductName").trim();
			String strProductName  = appUIDriver.getAppObjectwithLocator(objProductName);

			if (strProductName!=null && strProductName.length()>1)
			{


				String[] arrGmaMenuName = strProductName.split("#");
				String objectLocatorName =arrGmaMenuName[0].toString();
				String objectLocatorValue= arrGmaMenuName[1].toString();
				CurrentProductText = appUIDriver.getText(objectLocatorName, objectLocatorValue);

				return CurrentProductText;
			}
			else
			{
				return "";
			}
		}
		catch(Exception e)
		{

		}
		return ("");

	}
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

		}
	}


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
			String strCurrentTotalPriceBfrBagChrg;
			String strCurrentTotalPriceAftBagChrg;
			String strCurrentQtyValue;
			String strExpectedBagQtyValue=input.get("expectedBagQtyVal").trim();
			String strBagChoiceButton=input.get("bagChoice").trim();

			if(appUIDriver.getAppObjectFound("gma4_CheckOut_Heading"))
			{
				if(appUIDriver.getAppObjectFound("gma4_Checkout_Total"))
				{
					String strMobileObjectName  = appUIDriver.getAppObjectwithLocator("gma4_Checkout_TotalPrice");
					String[] arrMobileObjectName = strMobileObjectName.split("#");
					objectLocatorName =arrMobileObjectName[0].toString();
					objectLocatorValue= arrMobileObjectName[1].toString();
					strCurrentTotalPriceBfrBagChrg =appUIDriver.getText(objectLocatorName, objectLocatorValue);
					System.out.println(strCurrentTotalPriceBfrBagChrg);

					// add card payment component(Reusable)
					if(appUIDriver.getAppObjectFound("gma4_CheckOut_PayWith"))
					{
						if (appUIDriver.clickButton("gma4_CheckOut_AddCardArrow"))
						{
							if(appUIDriver.getAppObjectFound("gma4_SelectPaymentMethod"))
							{
								if(appUIDriver.clickButton("gma4_SelectPaymentMethod_RadioButtion"))
								{
									if(appUIDriver.clickButton("gma4_SelectPaymentMethod_SaveButtion"))
									{
										HtmlResult.passed("Selecting card to proceed order payment.", "Card should be selected successfully to proceed order payment.", "Card is selected successfully to proceed order payment.");
									}
									else
									{
										System.out.println("");
									}
								}
								else{
									System.out.println("card type Radio button not selected hence Failed");
								}
							}
							else{
								System.out.println("Payment method object not found hence failed");
							}
						}
						else{
							System.out.println("Pay with text not found hence failed");
						}
					}

					if(appUIDriver.clickButton("gma4_CheckOut_CheckInAtRestaurant"))
					{
						System.out.println("Successfully clicked on CheckIn At Restaurent");
					}

					//Select Check-In option (after that I have to validate onelast thing page
					if(appUIDriver.clickButton("gma4_CheckInOption_DiningArea"))
					{
						System.out.println("Successfully clicked on dining area option");
					}

					// for select confirm order type
					if(appUIDriver.clickButton("gma4_OrderType_TakeOut"))
					{
						if(appUIDriver.getAppObjectFound("gma4_BagCharges_ConfirmationInfo"))
						{
							if(appUIDriver.clickButton("gma4_BagCharges_Quantity_Plus"))
							{
								strMobileObjectName  = appUIDriver.getAppObjectwithLocator("gma4_BagCharges_TotalPrice");
								String[] arrMobileObjectName1 = strMobileObjectName.split("#");
								objectLocatorName =arrMobileObjectName1[0].toString();
								objectLocatorValue= arrMobileObjectName1[1].toString();
								strCurrentQtyValue =appUIDriver.getText(objectLocatorName, objectLocatorValue);
								System.out.println(strCurrentQtyValue);

								/*String[] arrMobileObjectName2 = strCurrentQtyValue.split("�");

 	 												objectLocatorName =arrMobileObjectName2[0].toString();
 	 												objectLocatorValue= arrMobileObjectName2[1].toString();*/

								if(strExpectedBagQtyValue.equals(strCurrentQtyValue))
								{
									System.out.println("bag charges value is correct");

								}
								else
								{
									System.out.println("Failed");
								}
							}
							if(strBagChoiceButton.equalsIgnoreCase("No Thanks") ||strBagChoiceButton.equalsIgnoreCase("NoThanks"))
							{

								if(appUIDriver.clickButton("gma4_BagCharges_NoThanksButton"))
								{
									System.out.println("Successfully clicked to NoThanks Button");
								}
								else
								{
									System.out.println("Not clicked on No thanks Button hence failed");
								}
							}
							else if(strBagChoiceButton.equalsIgnoreCase("Continue"))
							{
								if(appUIDriver.clickButton("gma4_BagCharges_Continue"))
								{
									System.out.println("Successfully clicked to Continue Button");
								}
								else
								{
									System.out.println("Not clicked on Continue Button hence failed");
								}
							}
						}

						if(appUIDriver.getAppObjectFound("gma4_CheckOut_Heading"))
						{
							if(appUIDriver.getAppObjectFound("gma4_Checkout_Total"))
							{
								strMobileObjectName  = appUIDriver.getAppObjectwithLocator("gma4_Checkout_TotalPrice");
								String[] arrMobileObjectName2 = strMobileObjectName.split("#");
								objectLocatorName =arrMobileObjectName2[0].toString();
								objectLocatorValue= arrMobileObjectName2[1].toString();
								strCurrentTotalPriceAftBagChrg =appUIDriver.getText(objectLocatorName, objectLocatorValue);
								System.out.println(strCurrentTotalPriceAftBagChrg);
								if(strCurrentTotalPriceAftBagChrg.equals(strCurrentTotalPriceBfrBagChrg))
								{
									System.out.println("No bag charges applied hence Passed");
								}
								else
								{
									System.out.println("Bag charges applied hence Failed");
								}
							}
							else{
								System.out.println("Total product value is not present on screen hence Failed");
							}

						}
						else{
							System.out.println("Checkout Screen heading Is Missing or Incorrect Hence Failed");
						}
					}
					else{
						System.out.println("TakeOut Button not clicked or missing Hence failed");
					}
				}
				else{
					System.out.println("Total product value is not present on screen hence Failed");
				}
			}
			else{
				System.out.println("Checkout Screen heading Is Missing or Incorrect Hence Failed");
			}


		}


		catch(Exception e)
		{
			System.out.println(e.getMessage());
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
	public void recentOrderVerification(Map<String,String> input)
	{
		try{
			//Assign String Variable value for Hamburger icon object
			//String strObjectProperty ="";
			String objHamburgerMenu = input.get("gmaobjHamburgerMenu"); 
			String objMenuName  = input.get("gmaobjMenuName");
			boolean BlnFound =false;
			BlnFound = appUIDriver.clickButton(objHamburgerMenu);
			//If Script is able to click on Hamburger Menu

			if(BlnFound)
			{
				//HtmlResult.passed("Click on Hamburger menu", "Hamburger icon should be clicked","Hamburger icon clicked successfully");

				String strMobileObjectName  = appUIDriver.getAppObjectwithLocator(objMenuName);
				String[] arrMobileObjectName = strMobileObjectName.split("#");
				String objectLocatorName =arrMobileObjectName[0].toString();
				String objectLocatorValue= arrMobileObjectName[1].toString();
				BlnFound =appUIDriver.clickText(objectLocatorName, objectLocatorValue);
				if (BlnFound)
				{
					HtmlResult.passed("To verify recent order screen" , "Recent order screen should be displayed", "Recent order screen is present");

					Thread.sleep(1000);
					BlnFound = appUIDriver.scrollToObjectFound("gma4_RecentOrder_SeeAllBtn");
					if(BlnFound)
					{
						HtmlResult.passed("To verify Recent Order " , "Recent order option should be selected", "Recent order option clicked successfully");
						String strMobileObjectName1  = appUIDriver.getAppObjectwithLocator("gma4_recentOrder_ProductName");
						String[] arrMobileObjectName1 = strMobileObjectName1.split("#");
						objectLocatorName =arrMobileObjectName1[0].toString();
						objectLocatorValue= arrMobileObjectName1[1].toString();
						String strCurrentOrderProduct =appUIDriver.getText(objectLocatorName, objectLocatorValue);
						HtmlResult.passed("To verify recent order product name", "Recent ordered product name should be displayed", "Recent ordered product name getting display" + strMobileObjectName1);
						String StrCurrentProductName = "";
						String PreviousOrderProduct = StrCurrentProductName;


						if(strCurrentOrderProduct.equalsIgnoreCase(PreviousOrderProduct))
						{
							//System.out.println("item is found" + PreviousOrderProduct +  " Pass");
							HtmlResult.passed("To verify Recent ordered product name", "Recent ordered product name should be matched", "Recent ordered product found");
						}

						String strMobileObjectName2  = appUIDriver.getAppObjectwithLocator("gma4_recentOrder_ProductPrice");
						String[] arrMobileObjectName2 = strMobileObjectName2.split("#");
						objectLocatorName =arrMobileObjectName2[0].toString();
						objectLocatorValue= arrMobileObjectName2[1].toString();
						String strRecentOrderProductPrice =appUIDriver.getText(objectLocatorName, objectLocatorValue);



						String StrCurrentProductprice="";
						if (strRecentOrderProductPrice.equalsIgnoreCase(StrCurrentProductprice))
						{
							HtmlResult.passed("To verify Recent ordered product price", "Recent ordered product price should be matched", "Recent ordered product price matched");
							//System.out.println("item price is found" + strRecentOrderProductPrice +  " pass");

						}

						else
						{
							HtmlResult.failed("To verify Recent ordered product price", "Recent ordered product price should be matched", "Recent ordered product price not matched");
							//System.out.println("item price is not found" + strRecentOrderProductPrice +  " fail");
						}
						if(BlnFound)
						{
							BlnFound =appUIDriver.clickButton("gma4_RecentOrder_SeeAllBtn");
							HtmlResult.passed("To verify SEE ALL button", "After click on See All, all recent ordered should be displayed", "All recent ordered getting display");
						}
						else
						{
							HtmlResult.failed("To verify SEE ALL button", "After click on See All, all recent ordered should be displayed", "All recent ordered  not present");
							//System.out.println("item is not found See all buttons" +  " fail");
						}

					}

					else
					{
						HtmlResult.failed("To verify recent order screen" , "Recent order screen should be displayed", "Recent order screen is not present");
						//System.out.println("Unable to find recent order screen");
					}

				}
				else
				{
					HtmlResult.failed("To verify Recent Order " , "Recent order option should be selected", "Recent order option not clicked ");
					//System.out.println("Unable to Click on Recent Orders");
				}

			}
			else
			{
				HtmlResult.passed("To verify hamburger menu " , "Recent hamburger should be selected", "Hamburger menu clicked successfully");
				//System.out.println("Unable to click Hamrburger menu");

			}		

		}
		catch(Exception e)

		{
			System.out.println(e.getMessage());
		}

	}

	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � favouriteOrderVerification
	 * Method Description - Go to Favourite order screen through hamburger icon and also verify the recent order item
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Nandini Gupta
	 * Creation Date - 11/02/2017
	 * Modification History:
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/
	public void favouriteOrderVerification(Map<String,String> input)
	{
		try{
			boolean blnFound = appUIDriver.scrollToObjectFound("gma4_AddToFavOrderBtn");
			if(blnFound){
				blnFound =appUIDriver.clickButton("gma4_AddToFavOrderBtn");

				if(blnFound){
					appUIDriver.enterText("gma4_favOrderName", "My fav");
					if(blnFound){
						appUIDriver.clickButton("gma4_FavOrder_savebtn");
					}
				}
			}

			String objHamburgerMenu = input.get("gmaobjHamburgerMenu");
			String objMenuName  = input.get("gmaobjMenuName");
			boolean BlnFound =false;
			BlnFound = appUIDriver.clickButton(objHamburgerMenu);

			//If Script is able to click on Hamburger Menu
			if(BlnFound)
			{
				HtmlResult.passed("Verify Hamburger menu", "Hamburger icon should be clicked", "Hamburger menu button clicked successfully");
				String strMobileObjectName  = appUIDriver.getAppObjectwithLocator(objMenuName);
				String[] arrMobileObjectName = strMobileObjectName.split("#");
				String objectLocatorName =arrMobileObjectName[0].toString();
				String objectLocatorValue= arrMobileObjectName[1].toString();
				BlnFound =appUIDriver.clickText(objectLocatorName, objectLocatorValue);
			}
			if (BlnFound)
			{
				HtmlResult.passed("Verify Fav Order", "fav order should be clicked", "Fav order option clicked successfully");
				//System.out.println("Clicked on fav Orders");
				Thread.sleep(1000);
				BlnFound = appUIDriver.scrollToObjectFound("gma_FavouriteOrders_PriceNotify");
				if(BlnFound)
				{
					HtmlResult.passed("Verify price notify text", "Price notify text should be present", "{Price notify text is present");
					String strMobileObjectName1  = appUIDriver.getAppObjectwithLocator("gma4_FavouriteOrders_ProductName");
					String[] arrMobileObjectName1 = strMobileObjectName1.split("#");
					String objectLocatorName = arrMobileObjectName1[0].toString();
					String objectLocatorValue = arrMobileObjectName1[1].toString();
					String strCurrentproductName =appUIDriver.getText(objectLocatorName, objectLocatorValue);
					System.out.println(strCurrentproductName);
					if(strCurrentproductName.equalsIgnoreCase("StrCurrentProductName"))
					{
						HtmlResult.passed("Verify fav ordere product name", "Fav oreder product name should be present", "Fav order product name found successfully");
						//System.out.println("item is found" + strCurrentText +  " Pass");
					}
					else
					{
						HtmlResult.failed("Verify fav order product", "Fav oreder product name should be present", "Fav order product name not found ");
					}

				}
				else
				{
					HtmlResult.failed("Verify fav order screen", "Fav oreder screen should be present", "Fav order screen not found ");
					//System.out.println("Unable to find fav order screen");
				}

			}
			else
			{
				HtmlResult.failed("Verify fav order click", "Fav oreder menu should be click", "Fav order menu not clicked ");
				//System.out.println("Unable to Click on fav Orders");
			}
		}

		catch(Exception e)
		{

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

	public void favouritedOrder(Map<String,String> input)
	{
		try{
			boolean blnFound = appUIDriver.scrollToObjectFound("gma4_AddToFavOrderBtn");
			if(blnFound){
				blnFound =appUIDriver.clickButton("gma4_AddToFavOrderBtn");

				if(blnFound){
					appUIDriver.enterText("gma4_favOrderName", "My fav");
					if(blnFound){
						appUIDriver.clickButton("gma4_FavOrder_savebtn");
					}
					if(blnFound){
						appUIDriver.scrollToObjectFound("gma4_OrderSummary-ProductName");
					}
					if(blnFound){
						String strMobileObjectName  = appUIDriver.getAppObjectwithLocator("gma4_offers_order_ProductName");
						String[] arrMobileObjectName = strMobileObjectName.split("#");
						String objectLocatorName = arrMobileObjectName[0].toString();
						String objectLocatorValue = arrMobileObjectName[1].toString();
						String strOfferProductname =appUIDriver.getText(objectLocatorName, objectLocatorValue);
						System.out.println(strOfferProductname);
						StrCurrentProductName = strOfferProductname;

						String strMobileObjectName1  = appUIDriver.getAppObjectwithLocator("gma4_Offers_Order_TotalProductPrice");
						String[] arrMobileObjectName1 = strMobileObjectName1.split("#");
						String objectLocatorName1 = arrMobileObjectName1[0].toString();
						String objectLocatorValue1 = arrMobileObjectName1[1].toString();
						String strOfferProductprice =appUIDriver.getText(objectLocatorName1, objectLocatorValue1);
						System.out.println(strOfferProductprice);
						StrCurrentProductprice = strOfferProductprice;
					}
				}
			}

		}
		catch(Exception e){
			System.out.println();
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
		}
	}

	/***************************************************************************************************************
	 * Project - GMA Method
	 * Method Name � gma_VerifyProductInOrderSummaryScreen
	 * Method Description - Enter Zone code on table service 
	 * Return Type - NO value (Void)
	 * Parameters -
	 * Framework - UKIT Master Framework
	 * Author - Madhur Barsainya
	 * Creation Date - 11/22/2017
	 * Modification History: Completed
	 * # <Date>     <Who>                  <Mod description>

	 ***************************************************************************************************************/

	public void gma_VerifyProductInOrderSummaryScreen(Map<String, String> input)
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
//					arrProductPriceInOrderSmryScren.add
					strProductPrice = appUIDriver.getTextForDynamicObject("gma4_ProductPrice_OrderSummaryScreen",arrAllProducts[intProductCounter]);
					arrProductPriceInOrderSmryScren.add(strProductPrice);
					String[] strTempProductName =  arrAllProducts[intProductCounter].split(" ",2);
					hmProductPriceInOrderSmryScrn.put(strTempProductName[1], strProductPrice);
					
					if (arrAllProducts[intProductCounter].contains("#"))
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
						if (arrItemsPresent[intProductCounter].equalsIgnoreCase("NO"))
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
					if (arrItemsPresent[intProductCounter].equalsIgnoreCase("NO"))
					{
						HtmlResult.passed("Verifying Product in Basket.", "'"+arrAllProducts[intProductCounter]+"' should not be present in the Basket.", "'"+arrAllProducts[intProductCounter]+"' is not present in the Basket.");
					}
					else
					{
						HtmlResult.failed("Verifying Product in Basket.", "'"+arrAllProducts[intProductCounter]+"' should be present in the Basket.", "'"+arrAllProducts[intProductCounter]+"' is not present in the Basket.");
					}
				}
			}
			appUIDriver.backbuttonPress();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
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
					HtmlResult.passed("Clicking on 'Review Order' button on Out of Stock screen", "'Review Order' button should be clicked present on Out of Stock screen.", "Clicked on 'Review Order' button present on Out of Stock screen and navigated to Order summary screen.");
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
			SubTotal_OutOfStockScreen = 0.0;
			
			currentFlagValue = appUIDriver.getAppObjectFound("gma4_Text_OutOfStockScreen");
			if (currentFlagValue)
			{
				String strMessage = appUIDriver.getObjectText("gma4_Text_OutOfStockScreen");
				HtmlResult.passed("Verifying 'Out of Stock' screen.", "User should be navigated to 'Out of Stock' screen.", "User is navigated to 'Out of Stock' screen which display - '"+strMessage+"' message.");
				
//				intTotalOutOfStockProduct = appUIDriver.getObjectCount("gma4_Products_OutOfStockScreen");
//				for(int i = 1; i<=intTotalOutOfStockProduct; i++)
//				{
//					strProductName = appUIDriver.getTextForDynamicObject("gma4_Products_OutOfStockScreen1", Integer.toString(i));
//					if(hmProductPriceInOrderSmryScrn.containsKey(strProductName))
//				    {
//						hmProductPriceWithoutUnavailableItems.remove(strProductName);
//				    }
//				}
				for (String value : hmProductPriceWithoutUnavailableItems.values())
				{
					SubTotal_OutOfStockScreen += Float.parseFloat(((value.split("�"))[1].split("*"))[0]);
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
					HtmlResult.passed("Clicking on 'Review Order' button on 'New Order Total' screen", "'Review Order' button should be clicked present on 'New Order Total' screen.", "Clicked on 'Review Order' button present on 'New Order Total' screen and navigated to Order summary screen.");
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
					HtmlResult.passed("Clicking on 'Continue' button on 'New Order Total' screen", "'Continue' button should be clicked present on 'New Order Total' screen.", "'Continue' button is clicked present on 'New Order Total' screen and navigated to Order Summary screen.");
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

	public void gma4_GetOrderNumber(Map<String, String> input)
	{
		try
		{
			boolean currentFlagValue =false; 
			String strOrderNumber = "";
			currentFlagValue = appUIDriver.getAppObjectFound("gma4_OrderNumber_OrderConfirmationPage");
			if (currentFlagValue)
			{
				strOrderNumber = appUIDriver.getObjectText("gma4_Ordering_CheckOut");
				HtmlResult.passed("Capturing 'Order Number'.", "User should be able to capture 'Order Number'.", "Product is ordered successfully with 'Order Number' : "+strOrderNumber+".");
			}
			else
			{
				HtmlResult.failed("Capturing Order Number.", "User should be able to capture order number.", "Unable to capture order number.");
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
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
			String strProduct = input.get("Products");
			String[] arrProducts = strProduct.split("\\$");
			int intProductCounter = 0;

			currentFlagValue = appUIDriver.getAppObjectFound("gma4_Text_OutOfStockScreen");
			if (currentFlagValue)
			{
				strMessage =  appUIDriver.getObjectText("gma4_Text_OutOfStockScreen");
				HtmlResult.passed("Verifying Out of Stock screen.", "User should be on the Out of Stock screen.", "User is on the 'Out of Stock' screen which displays : "+strMessage);
				
				for (intProductCounter = 0; intProductCounter<arrProducts.length; intProductCounter++)
				{
					if (strProdWithSideAndDrinkItemFlag[intProductCounter].equalsIgnoreCase("YES"))
					{
						String[] arrProdSideAndDrinkItem = arrProducts[intProductCounter].split("#");
						currentFlagValue = appUIDriver.getAppObjectFoundDynamic(strActualObject, "");
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
						currentFlagValue = appUIDriver.getAppObjectFoundDynamic(strActualObject, "");
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
				ExpectedTotalPrice = SubTotal_OutOfStockScreen; 
			}
			else
			{
				ExpectedTotalPrice = SubTotal_OrderSummaryScreen; 
			}
			
			strActualTotalPrice = appUIDriver.getObjectText("gma4_NewPrice_NewOrderTotalScreen");
			ActualTotalPrice = Float.parseFloat(strActualTotalPrice);
			
			if(ActualTotalPrice == ExpectedTotalPrice)
			{
				HtmlResult.passed("Veriyfing Price on 'New Order Total' screen.", "Expected and Actual pirce value should be same.", "Expected New Total Price '"+ExpectedTotalPrice+"' actual New Total Price '"+ActualTotalPrice+"' are matching.");
			}
			else
			{
				HtmlResult.passed("Veriyfing Price on 'New Order Total' screen.", "Expected and Actual pirce value should be same.", "Expected New Total Price '"+ExpectedTotalPrice+"' actual New Total Price '"+ActualTotalPrice+"' are not matching.");
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
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

	public void enterTextinAppObjcect(Map<String,String> input)
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
		}
	}

}
