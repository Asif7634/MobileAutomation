package com.cg.Application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import com.cg.ApplicationLevelComponent.Appium;
import com.cg.ApplicationLevelComponent.EggPlant;
import com.cg.Logutils.HtmlResult;
import com.cg.TestCaseRunner.TestCaseRunner;
import com.cg.UIDriver.AppiumUIDriver;

public class GMA5_Appium extends Appium{

	public AppiumUIDriver appUIDriver = new AppiumUIDriver();
	int height;
	int width ;
	int x;
	int starty;
	int endy;

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

	 ***************************************************************************************************************/

	public void openApp()
	{
		try {
			terminateApp();
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
			System.err.println("Error");
		}
	}


	/***************************************************************************************************************
	 * 	// Method Name -
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

	 ***************************************************************************************************************/
	public void hamburgerMenuSelection(Map<String,String> input)
	{
		String strDescription ="Hamburger menu item should be selected";
		String strExpectedResult="Hamburger menu item should selected ";
		try
		{
			String strhamburgerMenuName=input.get("menuName");
			String strActualResult= strhamburgerMenuName + "  - hamburger menu should be selected ";
			Boolean blnFound = false;
			int currentLoopCounter;
			Boolean CurrentFlagValue = false;
			try{
				do
				{
					currentLoopCounter =1;
					CurrentFlagValue= true;
					if (CurrentFlagValue)
					{
						//Will Update as per Framework design
						blnFound =  driver.findElement(By.id("android:id/button1")).isDisplayed();
						CurrentFlagValue= true;
						break;
					}
					else
					{
						currentLoopCounter = currentLoopCounter + 1;
						CurrentFlagValue= false;
					}
				} while (currentLoopCounter >=3);

				if (CurrentFlagValue)
				{
					driver.findElement(By.id("android:id/button1")).click();
					HtmlResult.passed("Popup", "Popup", "unable to click on popup box");
				}
			}
			catch(Exception e)
			{
				//	HtmlResult.passed("Popup", "Popup", "unable to click on popup box");
			}



			//Thread.sleep(10000);

			boolean CurrentFound = driver.findElement(By.id("com.mcdonalds.app.uk.qa:id/slide_handler")).isDisplayed();
			driver.findElement(By.id("com.mcdonalds.app.uk.qa:id/slide_handler")).click();
			System.out.println(CurrentFound);
			///////////////////////////
			if (strhamburgerMenuName.equalsIgnoreCase("About, Contact & Legal") || strhamburgerMenuName.equalsIgnoreCase("About")|| strhamburgerMenuName.equalsIgnoreCase("Contact")|| strhamburgerMenuName.equalsIgnoreCase("Legal"))
			{
				// Will update as per framework with object

				blnFound = driver.findElement(By.id("com.mcdonalds.app.uk.qa:id/mcd_menu_about")).isDisplayed();
				if (blnFound)
				{
					driver.findElement(By.id("com.mcdonalds.app.uk.qa:id/mcd_menu_about")).click();
					HtmlResult.passed(strDescription, strExpectedResult, strActualResult);
				}
				else
				{
					HtmlResult.failed(strDescription, strExpectedResult, strActualResult);
				}
			}

			if (strhamburgerMenuName.equalsIgnoreCase("Home") || strhamburgerMenuName.equalsIgnoreCase("Home")|| strhamburgerMenuName.equalsIgnoreCase("Contact")|| strhamburgerMenuName.equalsIgnoreCase("Legal"))
			{
				// Will update as per framework with object

				blnFound = driver.findElement(By.id("com.mcdonalds.app.uk.qa:id/home")).isDisplayed();
				if (blnFound)
				{
					driver.findElement(By.id("com.mcdonalds.app.uk.qa:id/home")).click();
					HtmlResult.passed(strDescription, strExpectedResult, strActualResult);
				}
				else
				{
					HtmlResult.failed(strDescription, strExpectedResult, strActualResult);
				}
			}

			if (strhamburgerMenuName.equalsIgnoreCase("Order") || strhamburgerMenuName.equalsIgnoreCase("Login"))
			{
				// Will update as per framework with object

				//blnFound = driver.findElement(By.id("com.mcdonalds.app.uk.qa:id/")).isDisplayed();
				String strMobileObjectName  = appUIDriver.getAppObjectwithLocator("hamburger_LoginLink");
				String[] arrMobileObjectName = strMobileObjectName.split("#");
				String objectLocatorName =arrMobileObjectName[0].toString();
				String objectLocatorValue= arrMobileObjectName[1].toString();
				blnFound = appUIDriver.isObjectDisplayed(objectLocatorName, objectLocatorValue);
				if (blnFound)
				{
					appUIDriver.clickButton("hamburger_LoginLink");
					HtmlResult.passed(strDescription, strExpectedResult, strActualResult);
				}
				else
				{
					HtmlResult.failed(strDescription, strExpectedResult, strActualResult);
				}
			}

			if (strhamburgerMenuName.equalsIgnoreCase("Our Menu") || strhamburgerMenuName.equalsIgnoreCase("Our Menu")|| strhamburgerMenuName.equalsIgnoreCase("Contact")|| strhamburgerMenuName.equalsIgnoreCase("Legal"))
			{
				// Will update as per framework with object

				String strMobileObjectName  = appUIDriver.getAppObjectwithLocator("app_HamburgerMenu_OurMenu");

				String[] arrMobileObjectName = strMobileObjectName.split("#");

				String objectLocatorName =arrMobileObjectName[0].toString();
				String objectLocatorValue= arrMobileObjectName[1].toString();

				blnFound = appUIDriver.clickText(objectLocatorName, objectLocatorValue);
				if (blnFound)
				{
					//driver.findElement(By.id("com.mcdonalds.app.uk.qa:id/mcd_menu_about")).click();
					HtmlResult.passed(strDescription, strExpectedResult, strActualResult);
				}
				else
				{
					HtmlResult.failed(strDescription, strExpectedResult, strActualResult);
				}
			}

		}
		catch (Exception e)
		{
			HtmlResult.failed(strDescription, strExpectedResult, e.getMessage());
		}


	}








	//

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
	 * 	// Method Name -
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

	 ***************************************************************************************************************/


	public boolean verifyTextAndClickAppObject1(Map<String,String> input)

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
	 * 	// Method Name -
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
				System.out.println("Object clicekd - PASS");

				HtmlResult.passed("Tap the Mobile app object ", "Tap operation should be done successfully for object - "+ strObjectName + " for the object -" + strObjectName , "Tap operation performed successfully for object - " + strObjectName);
			}
			else
			{
				System.out.println("FAIL");
				HtmlResult.failed("Tap the Mobile app object ", "Tap operation should be done successfully for object - "+ strObjectName + " for the object -" + strObjectName , "Tap operation is not performed for object - " + strObjectName);
			}
		}

		catch (Exception e)
		{
			HtmlResult.failed("Tap the Mobile app object ", "Tap operation should be done successfully for object - for the object -"  , "Tap operation is not performed for object - " );

		}


	}

	/***************************************************************************************************************
	 * 	// Method Name -
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

	 ***************************************************************************************************************/


	public void productDescriptionVerification(Map<String,String> input)
	{
		String strProductMainCateogries = input.get("productMainCateogries");
		strProductMainCateogries = strProductMainCateogries.trim();
		//strProductMainCateogries = driver.findElement(By.xpath("//android.widget.TextView[@text='"+strProduct+"'and @index='0']")).isDisplayed();
		//	List <?> ListProductMainCateogries =  driver.findElements(By.id("com.mcdonalds.app.uk.qa:id/category_item"));
		//WebElement Elem = driver.findElement(By.id("com.mcdonalds.app.uk.qa:id/category_item"));
		List<WebElement> ListOfElem = driver.findElements(By.id("com.mcdonalds.app.uk.qa:id/category_item"));

		//driver.findElement(By.id("com.mcdonalds.app.uk.qa:id/category_item")).getAttribute(arg0)

		//System.out.println(ListProductMainCateogries.size());
		//ListProductMainCateogriest
		List<WebElement> elements = null;


		for(WebElement item : ListOfElem)
		{
			System.out.println("List Item : "+ item.getText()); //
			if (item.getText().equalsIgnoreCase(strProductMainCateogries))
			{
				System.out.println("Pass");
				System.out.println(item.getText());
				break;
			}
			else
			{
				System.out.println("Failed");
			}
		}
	}



	//component to verify nutrition information
	public void verifyNutritionalInformation(Map<String,String> Input)
	{
		String strStepDescription = "To verify nutritonal information";
		String strExpectedResult = "Verification of nutrition should be successful";
		try
		{
			String strProduct = Input.get("Product");
			String strNutritionValue = Input.get("NutritionValue");
			String strSubProduct =Input.get("SubProduct");
			String ExpectedValue = Input.get("ExpectedValue");

			boolean BlnSwipeFound;
			int height, width, x,starty,endy=0;

			//wait implicitly
			appUIDriver.waitImplicitly(10);
			Dimension CurrentMobileSize = appUIDriver.getWindowSize();

			boolean CurrentFlagValue = false;

			//ok button
			if(appUIDriver.isDisplayedById("ok_button"))
			{
				if(appUIDriver.clickButton("ok_button"))
				{
					HtmlResult.passed(strStepDescription, "Ok button from alert should be clicked successfully", "Ok button clicked on screen");
					CurrentFlagValue = true;
				}
				else
				{
					HtmlResult.failed(strStepDescription, "Ok button from alert should be clicked successfully", "Ok button not found at the start of the application");
				}
			}
			else
			{
				HtmlResult.failed(strStepDescription, "Ok button from alert should be clicked successfully", "Ok button not found at the start of the application");
			}

			//wait implicitly
			appUIDriver.waitImplicitly(5);

			//clicking on slide handler
			if(CurrentFlagValue)
			{
				CurrentFlagValue = false;
				if(appUIDriver.isDisplayedById("slide_handler"))
				{
					if(appUIDriver.clickButton("slide_handler"))
					{
						HtmlResult.passed(strStepDescription, "Slide handler should be clicked successfully", "slide handler is clicked successfully");
						CurrentFlagValue = true;
					}
					else
					{
						HtmlResult.failed(strStepDescription, "Slide handler should be clicked successfully", "slide handler is not found on application");
					}
				}
				else
				{
					HtmlResult.failed(strStepDescription, "Slide handler should be clicked successfully", "slide handler is not found on application");
				}
			}

			//wait implicitly
			appUIDriver.waitImplicitly(5);

			//our menu clicking
			if(CurrentFlagValue)
			{
				CurrentFlagValue = false;
				boolean ObejctFound = false;
				do
				{
					height = CurrentMobileSize.getHeight();
					width = CurrentMobileSize.getWidth();
					x=width/2;
					starty = (int) (height*0.40);
					endy= (int) (width*0.20);
					try
					{
						ObejctFound = appUIDriver.isDisplayedById("discover");
						if(!ObejctFound)
						{
							driver.swipe(x, starty, x, endy, 500);
						}
					}
					catch(Exception e)
					{
						ObejctFound = false;
					}

				}while(!ObejctFound);

				if(ObejctFound)
				{
					if(appUIDriver.clickButton("discover"))
					{
						HtmlResult.passed(strStepDescription, "Our menu button should be clicked successfully", "Our menu clicked successfully");
						CurrentFlagValue = true;
					}
					else
					{
						HtmlResult.failed(strStepDescription, "Our menu button should be clicked successfully", "Our menu option not found on screen");
					}
				}
				else
				{
					HtmlResult.failed(strStepDescription, "Our menu button should be clicked successfully", "Our menu option not found on screen");
				}

			}


			//wait implicitly
			appUIDriver.waitImplicitly(5);

			//clicking on product
			if(CurrentFlagValue)
			{
				CurrentFlagValue = false;
				do
				{
					height = CurrentMobileSize.getHeight();
					width = CurrentMobileSize.getWidth();
					x=width/2;
					starty = (int) (height*0.40);
					endy= (int) (width*0.20);
					try
					{
						BlnSwipeFound = appUIDriver.isDisplayedByXpath("//android.widget.TextView[@text='"+strProduct+"'and @index='0']");
						if(!BlnSwipeFound)
						{
							driver.swipe(x, starty, x, endy, 500);
						}
					}
					catch (Exception e)
					{
						BlnSwipeFound = false;
					}
				} while (!BlnSwipeFound);

				if(BlnSwipeFound)
				{
					BlnSwipeFound = appUIDriver.clickOnXpath("//android.widget.TextView[@text='"+strProduct+"'and @index='0']");
					if(BlnSwipeFound)
					{
						HtmlResult.passed(strStepDescription, "'"+strProduct+"' button should be clicked successfully", "Button clicked successflly -'"+strProduct+"'");
						CurrentFlagValue = true;
					}
					else
					{
						HtmlResult.failed(strStepDescription, "'"+strProduct+"' button should be clicked successfully", "Button not clicked successflly -'"+strProduct+"'");
					}
				}
				else
				{
					HtmlResult.failed(strStepDescription, "'"+strProduct+"' button should be clicked successfully", "Button not clicked successflly -'"+strProduct+"'");
				}
			}

			appUIDriver.waitImplicitly(2);

			if(CurrentFlagValue)
			{
				CurrentFlagValue = false;
				do
				{
					height = CurrentMobileSize.getHeight();
					width = CurrentMobileSize.getWidth();
					x=width/2;
					starty = (int) (height*0.40);
					endy= (int) (width*0.20);

					try
					{
						appUIDriver.waitImplicitly(2);
						BlnSwipeFound = appUIDriver.isDisplayedByXpath("//android.widget.TextView[@text='"+strSubProduct+"'and @index='1']");
						if(!BlnSwipeFound)
						{
							driver.swipe(x, starty, x, endy, 500);
						}

					}
					catch (Exception e)
					{
						BlnSwipeFound = false;

					}
				} while (!BlnSwipeFound);


				if(BlnSwipeFound)
				{
					BlnSwipeFound  = appUIDriver.clickOnXpath("//android.widget.TextView[@text='"+strSubProduct+"'and @index='1']");
					if(BlnSwipeFound)
					{
						HtmlResult.passed(strStepDescription, "' "+strSubProduct+" ' should be clicked successfully ", "' "+strSubProduct+" ' clicked successfully on screen");
						CurrentFlagValue = true;
					}
					else
					{
						HtmlResult.failed(strStepDescription, "' "+strSubProduct+" ' should be clicked successfully ", "' "+strSubProduct+" ' not found on screen");
					}
				}
				else
				{
					HtmlResult.failed(strStepDescription, "' "+strSubProduct+" ' should be clicked successfully ", "' "+strSubProduct+" ' not found on screen");
				}

			}

			//clicking on ingredients
			if(CurrentFlagValue)
			{
				CurrentFlagValue = false;
				appUIDriver.waitImplicitly(5);
				if(appUIDriver.isDisplayedById("ingredients"))
				{
					if(appUIDriver.clickById("ingredients"))
					{
						HtmlResult.passed(strStepDescription, "'Ingredients' button should be clicked successfully", "Ingredients button clicked successfully");
						CurrentFlagValue  = true;
						appUIDriver.waitImplicitly(5);
					}
					else
					{
						HtmlResult.failed(strStepDescription, "'Ingredients' button should be clicked successfully", "Ingredients button not found on screen");
					}
				}
				else
				{
					HtmlResult.failed(strStepDescription, "'Ingredients' button should be clicked successfully", "Ingredients button not found on screen");
				}
			}

			//clicking on nutrition
			if(CurrentFlagValue)
			{
				CurrentFlagValue = false;
				appUIDriver.waitImplicitly(5);
				if(appUIDriver.isDisplayedById("nutrition"))
				{
					if(appUIDriver.clickById("nutrition"))
					{
						HtmlResult.passed(strStepDescription, "'Nutrition' button should be clicked successfully", "Nutrition button clicked successfully");
						CurrentFlagValue  = true;
						appUIDriver.waitImplicitly(5);
					}
					else
					{
						HtmlResult.failed(strStepDescription, "'Nutrition' button should be clicked successfully", "Nutrition button not found on screen");
					}
				}
				else
				{
					HtmlResult.failed(strStepDescription, "'Nutrition' button should be clicked successfully", "Nutrition button not found on screen");
				}
			}

			//comparing expected and actual values
			if(CurrentFlagValue)
			{
				CurrentFlagValue = false;
				do
				{
					height = CurrentMobileSize.getHeight();
					width = CurrentMobileSize.getWidth();
					x=width/2;
					starty = (int) (height*0.40);
					endy= (int) (width*0.20);
					try
					{
						appUIDriver.waitImplicitly(1);
						BlnSwipeFound = appUIDriver.isDisplayedByXpath("//android.widget.TextView[@text='"+strNutritionValue+"']");
						if(!BlnSwipeFound)
						{
							driver.swipe(x, starty, x, endy, 500);
						}
					}
					catch (Exception e)
					{
						BlnSwipeFound = false;

					}
				} while (!BlnSwipeFound);

				if(BlnSwipeFound)
				{
					String val = appUIDriver.getTextByXpath("//*[@resource-id='com.mcdonalds.app.uk.qa:id/nutrion_table']//android.widget.TextView[@text='"+strNutritionValue+"']/../following-sibling::android.widget.FrameLayout[1]/android.widget.TextView");
					if(val.trim().equals(ExpectedValue.trim()))
					{
						HtmlResult.passed(strStepDescription,"Nutrition '"+strNutritionValue+"' should contain value '"+ExpectedValue+"'","Nutrition "+strNutritionValue+" have "+val+" value");
						CurrentFlagValue = true;
					}
					else
					{
						HtmlResult.failed(strStepDescription,"Nutrition '"+strNutritionValue+"' should contain value '"+ExpectedValue+"'","Nutrition "+strNutritionValue+" have "+val+" value");
					}
				}
				else
				{
					HtmlResult.failed(strStepDescription, "Nutrition '"+strNutritionValue+"' should contain value '"+ExpectedValue+"'", "' "+strNutritionValue+" ' not found on table");
				}
			}

			//clicking on slide handler
			if(CurrentFlagValue)
			{
				CurrentFlagValue = false;
				if(appUIDriver.isDisplayedById("slide_handler"))
				{
					if(appUIDriver.clickButton("slide_handler"))
					{
						HtmlResult.passed(strStepDescription, "Slide handler should be clicked successfully", "slide handler is clicked successfully");
						CurrentFlagValue = true;
					}
					else
					{
						HtmlResult.failed(strStepDescription, "Slide handler should be clicked successfully", "slide handler is not found on application");
					}
				}
				else
				{
					HtmlResult.failed(strStepDescription, "Slide handler should be clicked successfully", "slide handler is not found on application");
				}
			}
		}
		catch (Exception e)
		{
			HtmlResult.failed(strStepDescription, strExpectedResult, "Error while performing BasicNavigationflow() "+e.getMessage()+","+e.getCause());
		}
	}


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

	public boolean terminateApp()
	{
		try {
			if(appUIDriver.closeApp()){
				return true;
			}
			else
			{
				return false;
			}
		} catch (Exception e) {
			System.err.println("Error");
			return false;
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

	public void GeneralOrderTaking( Map<String,String> Input)
	{
		boolean BlnSwipeFound = false;
		String strProduct = "Start Order";
		String strLogin = Input.get("Action");
		String Password = Input.get("Password");
		String strLoginName = Input.get("Email");
		String strStepDescription = "To perform General order taking";
		String strExpectedResult = "Order taking should be successful";
		String strSubProduct = Input.get("SubProductCategory");
		String strSubProductitem = Input.get("SubProductItem");

		try
		{
			appUIDriver.waitImplicitly(10);
			Dimension CurrentMobileSize = appUIDriver.getWindowSize();
			int currentcounter = 0;
			boolean CurrentFlagValue = false;


			//clicking ok button if displayed
			if(appUIDriver.isDisplayedById("ok_button"))
			{
				if(appUIDriver.clickById("ok_button"))
				{
					HtmlResult.passed(strStepDescription, strExpectedResult, "ok button clicked successfully");
					CurrentFlagValue = true;
				}
				else
				{
					HtmlResult.failed(strStepDescription, strExpectedResult, "ok button is not clicked successfully");
				}
			}
			else
			{
				CurrentFlagValue = true;
			}

			//Implicit wait
			appUIDriver.waitImplicitly(5);

			if(CurrentFlagValue)
			{
				CurrentFlagValue = false;
				if(appUIDriver.isDisplayedById("slide_handler"))
				{
					if(appUIDriver.clickButton("slide_handler"))
					{
						HtmlResult.passed(strStepDescription, "Slider handler should be clickced successfully", "slide handler is found on application");
						CurrentFlagValue = true;
					}
					else
					{
						HtmlResult.failed(strStepDescription, "Slide handler should be clicked successfully", "slide handler is not found on application");
					}
				}
				else
				{
					HtmlResult.failed(strStepDescription, "Slide handler should be clicked successfully", "slide handler is not found on application");
				}
			}

			//click on order
			if(CurrentFlagValue)
			{
				//click on order button using swipe action
				String OrderButtonXath = getIdentifire("xpath", "order");
				do
				{
					int height = CurrentMobileSize.getHeight();
					int width = CurrentMobileSize.getWidth();
					int x=width/2;
					int starty = (int) (height*0.40);
					int endy= (int) (height*0.20);

					try
					{
						BlnSwipeFound = appUIDriver.isDisplayedByXpath(OrderButtonXath);
						appUIDriver.waitImplicitly(5);
						if(!BlnSwipeFound)
						{
							driver.swipe(x, starty, x, endy,500);
						}
					}
					catch (Exception e)
					{
						BlnSwipeFound = false;
					}

				} while (!BlnSwipeFound);

				if(BlnSwipeFound)
				{
					if(appUIDriver.clickOnXpath(OrderButtonXath))
					{
						HtmlResult.passed(strStepDescription, strExpectedResult, "Order button clicked successfully");
						CurrentFlagValue = true;
					}
					else
					{
						HtmlResult.failed(strStepDescription, strExpectedResult, "Order button is not clicked successfully");
					}
				}
				else
				{
					HtmlResult.failed(strStepDescription, strExpectedResult, "Login button is not located on screen");
				}
			}


			//login
			if(CurrentFlagValue)
			{
				CurrentFlagValue = false;
				//enter email if displayed
				if(appUIDriver.isDisplayedById("email"))
				{
					//enter email
					if(appUIDriver.sendkeysId("email", strLoginName))
					{
						//locate and enter password
						if(appUIDriver.isDisplayedById("passowrd"))
						{
							if(appUIDriver.sendkeysId("passowrd", Password))
							{
								HtmlResult.passed(strStepDescription, strExpectedResult, "Email and password entered successfully");

								//click on login button using swipe action
								do
								{
									int height = CurrentMobileSize.getHeight();
									int width = CurrentMobileSize.getWidth();
									int x=width/2;
									int starty = (int) (height*0.40);
									int endy= (int) (height*0.20);

									try
									{
										BlnSwipeFound = appUIDriver.isDisplayedByXpath("//android.widget.TextView[@text='"+strLogin+"'and @index='0'and @resource-id='com.mcdonalds.app.uk.qa:id/save']");
										appUIDriver.waitImplicitly(5);
										if(!BlnSwipeFound)
										{
											driver.swipe(x, starty, x, endy,500);
										}
									}
									catch (Exception e)
									{
										BlnSwipeFound = false;
									}

								} while (!BlnSwipeFound);

								if(BlnSwipeFound)
								{
									if(appUIDriver.clickByXpath("//android.widget.TextView[@text='"+strLogin+"'and @index='0'and @resource-id='com.mcdonalds.app.uk.qa:id/save']"))
									{
										HtmlResult.passed(strStepDescription, strExpectedResult, "Login successful with credentials email -'"+strLoginName+"',password- '"+Password+"'");
										CurrentFlagValue = true;
									}
									else
									{
										HtmlResult.failed(strStepDescription, strExpectedResult, "Login button is not clicked successfully");
									}
								}
								else
								{
									HtmlResult.failed(strStepDescription, strExpectedResult, "Login button is not located on screen");
								}
							}

						}
						else
						{
							HtmlResult.failed(strStepDescription, strExpectedResult, "Password text box is not located on screen");
						}

					}
					else
					{
						HtmlResult.failed(strStepDescription, strExpectedResult, "email text box is not located on screen");
					}
				}
				else
				{
					HtmlResult.warning(strStepDescription, strExpectedResult, "User is already logged in");
					CurrentFlagValue = true;
				}

			}

			appUIDriver.waitImplicitly(5);

			//product section
			if(CurrentFlagValue)
			{
				CurrentFlagValue = false;
				do
				{
					int height = CurrentMobileSize.getHeight();
					int width = CurrentMobileSize.getWidth();
					int x=width/2;
					int starty = (int) (height*0.40);
					int endy= (int) (height*0.20);

					try
					{
						BlnSwipeFound = appUIDriver.isDisplayedByXpath("//android.widget.TextView[@text='"+strProduct+"'and @resource-id='com.mcdonalds.app.uk.qa:id/save']");
						appUIDriver.waitImplicitly(5);
						if(!BlnSwipeFound)
						{
							driver.swipe(x, starty, x, endy,500);
						}
					}
					catch (Exception e)
					{
						BlnSwipeFound = false;
					}

				} while (!BlnSwipeFound);

				if(BlnSwipeFound)
				{
					if(appUIDriver.clickOnXpath("//android.widget.TextView[@text='"+strProduct+"'and @resource-id='com.mcdonalds.app.uk.qa:id/save']"))
					{
						HtmlResult.passed(strStepDescription, strExpectedResult, "Product -"+strProduct+" selected successfully");
						CurrentFlagValue = true;
					}
					else
					{
						HtmlResult.failed(strStepDescription, strExpectedResult, "Product -"+strProduct+" not located on screen ");
					}
				}
				else
				{
					HtmlResult.failed(strStepDescription, strExpectedResult, "Product -"+strProduct+" not located on screen ");
				}

			}

			appUIDriver.waitImplicitly(10);

			if(CurrentFlagValue)
			{
				CurrentFlagValue = false;
				do
				{
					int height = CurrentMobileSize.getHeight();
					int width = CurrentMobileSize.getWidth();
					int x=width/2;
					int starty = (int) (height*0.40);
					int endy= (int) (height*0.20);

					try
					{
						BlnSwipeFound = appUIDriver.isDisplayedByXpath("//android.widget.TextView[@text='"+strSubProduct+"'and @resource-id='com.mcdonalds.app.uk.qa:id/category_name']");
						appUIDriver.waitImplicitly(5);
						if(!BlnSwipeFound)
						{
							driver.swipe(x, starty, x, endy,500);
						}
					}
					catch (Exception e)
					{
						BlnSwipeFound = false;
					}

				} while (!BlnSwipeFound);

				if(BlnSwipeFound)
				{
					if(appUIDriver.clickOnXpath("//android.widget.TextView[@text='"+strSubProduct+"'and @resource-id='com.mcdonalds.app.uk.qa:id/category_name']"))
					{
						HtmlResult.passed(strStepDescription, strExpectedResult, "Product -"+strSubProduct+" selected successfully");
						CurrentFlagValue = true;
					}
					else
					{
						HtmlResult.failed(strStepDescription, strExpectedResult, "Product -"+strSubProduct+" not located on screen ");
					}
				}
				else
				{
					HtmlResult.failed(strStepDescription, strExpectedResult, "Product -"+strSubProduct+" not located on screen ");
				}
			}

			if(CurrentFlagValue)
			{
				CurrentFlagValue = false;
				do
				{
					int height = CurrentMobileSize.getHeight();
					int width = CurrentMobileSize.getWidth();
					int x=width/2;
					int starty = (int) (height*0.40);
					int endy= (int) (height*0.20);

					try
					{
						BlnSwipeFound = appUIDriver.isDisplayedByXpath("//android.widget.TextView[@text='"+strSubProductitem+"'and @resource-id='com.mcdonalds.app.uk.qa:id/product_title']");
						appUIDriver.waitImplicitly(5);
						if(!BlnSwipeFound)
						{
							driver.swipe(x, starty, x, endy,500);
						}
					}
					catch (Exception e)
					{
						BlnSwipeFound = false;
					}

				} while (!BlnSwipeFound);

				if(BlnSwipeFound)
				{
					if(appUIDriver.clickOnXpath("//android.widget.TextView[@text='"+strSubProductitem+"'and @resource-id='com.mcdonalds.app.uk.qa:id/product_title']"))
					{
						HtmlResult.passed(strStepDescription, strExpectedResult, "Product -"+strSubProductitem+" selected successfully");
						CurrentFlagValue = true;
					}
					else
					{
						HtmlResult.failed(strStepDescription, strExpectedResult, "Product -"+strSubProductitem+" not located on screen ");
					}
				}
				else
				{
					HtmlResult.failed(strStepDescription, strExpectedResult, "Product -"+strSubProductitem+" not located on screen ");
				}

			}

			appUIDriver.waitImplicitly(5);

			if(CurrentFlagValue)
			{
				CurrentFlagValue = false;
				boolean ObjectOperated = false;

				//add to order
				if(appUIDriver.clickById("add_to_order"))
				{
					ObjectOperated = true;
					HtmlResult.passed(strStepDescription, strExpectedResult, "Add to order button clicked successfully");
				}
				else
				{
					HtmlResult.failed(strStepDescription, strExpectedResult, "Add to order button is not clicked on screen");
				}

				appUIDriver.waitImplicitly(5);
				//toolbar_right_icon
				if(ObjectOperated)
				{
					ObjectOperated = false;
					if(appUIDriver.clickById("toolbar_right_icon"))
					{
						ObjectOperated = true;
					}
				}
				else
				{
					HtmlResult.failed(strStepDescription, strExpectedResult, "toolbar is not located on screen");
				}

				appUIDriver.waitImplicitly(5);
				//proceed_to_pay
				if(ObjectOperated)
				{
					ObjectOperated = false;
					if(appUIDriver.clickById("proceed_to_pay"))
					{
						ObjectOperated = true;
						HtmlResult.passed(strStepDescription, strExpectedResult, "proceed to pay button clicked successfully");
					}
				}
				else
				{
					HtmlResult.failed(strStepDescription, strExpectedResult, "proceed to pay is not located on screen");
				}

				appUIDriver.waitImplicitly(5);
				//continue
				if(ObjectOperated)
				{
					ObjectOperated = false;
					if(appUIDriver.clickById("continue"))
					{
						ObjectOperated = true;
						HtmlResult.passed(strStepDescription, strExpectedResult, "continue button clicked successfully");
					}
				}
				else
				{
					HtmlResult.failed(strStepDescription, strExpectedResult, "continue button is not located on screen");
				}

				appUIDriver.waitImplicitly(5);
				//save
				if(ObjectOperated)
				{
					ObjectOperated = false;
					if(appUIDriver.clickById("save"))
					{
						ObjectOperated = true;
						HtmlResult.passed(strStepDescription, strExpectedResult, "save button clicked successfully");
					}
				}
				else
				{
					HtmlResult.failed(strStepDescription, strExpectedResult, "save button is not located on screen");
				}

				appUIDriver.waitImplicitly(10);
				Thread.sleep(3000);

				//card_order
				if(ObjectOperated)
				{
					ObjectOperated = false;
					if(appUIDriver.clickById("card_order"))
					{
						ObjectOperated = true;
						HtmlResult.passed(strStepDescription, strExpectedResult, "order button clicked successfully");
					}
				}
				else
				{
					HtmlResult.failed(strStepDescription, strExpectedResult, "order button is not located on screen");
				}

				appUIDriver.waitImplicitly(5);
				Thread.sleep(2000);
				//inside_layout
				if(ObjectOperated)
				{
					ObjectOperated = false;
					if(appUIDriver.clickById("inside_layout"))
					{
						ObjectOperated = true;
						HtmlResult.passed(strStepDescription, strExpectedResult, "inside button clicked successfully");
					}
				}
				else
				{
					HtmlResult.failed(strStepDescription, strExpectedResult, "inside button is not located on screen");
				}

				appUIDriver.waitImplicitly(5);
				Thread.sleep(2000);
				//eat_in
				if(ObjectOperated)
				{
					ObjectOperated = false;
					if(appUIDriver.clickById("eat_in"))
					{
						ObjectOperated = true;
						HtmlResult.passed(strStepDescription, strExpectedResult, "eat in button clicked successfully");
					}
				}
				else
				{
					HtmlResult.failed(strStepDescription, strExpectedResult, "eat in button is not located on screen");
				}

				appUIDriver.waitImplicitly(10);
				Thread.sleep(3000);
				//done
				if(ObjectOperated)
				{
					ObjectOperated = false;
					if(appUIDriver.clickById("done"))
					{
						ObjectOperated = true;
						String CurrentOrderNumber = appUIDriver.getText("id", "order_number_code").toString();
						if(CurrentOrderNumber!="")
						{
							HtmlResult.passed(strStepDescription, strExpectedResult, "Order placed successfully order number"+CurrentOrderNumber);
						}
						else
						{
							HtmlResult.failed(strStepDescription, strExpectedResult, "order number code is not located on screen");
						}
					}
				}
				else
				{
					HtmlResult.failed(strStepDescription, strExpectedResult, "done button is not located on screen");
				}
			}
		}

		catch(Exception e)
		{
			HtmlResult.failed(strStepDescription, strExpectedResult, "Error while performing GeneralOrderTaking()");
		}
	}


	public void MessageboxConfirmation(Map<String,String> Input)
	{
		int currentcounter;
		boolean CurrentFlagValue;
		String strDescription ="To verify message box confirmation";
		String strExpectedResult = "Message box confirmation verification should be successful";

		String ResourceId = Input.get("ResourceId");
		do
		{
			currentcounter =1;
			CurrentFlagValue= false;
			try
			{
				CurrentFlagValue = 	driver.findElement(By.id(ResourceId)).isDisplayed();
			}
			catch (Exception e)
			{
				currentcounter = currentcounter + 1;
				CurrentFlagValue= false;
			}
		} while (!CurrentFlagValue && currentcounter <=10);

		if (CurrentFlagValue)
		{
			driver.findElement(By.id(ResourceId)).click();
			System.out.println(ResourceId + " clicked successfully");
		}
		else
		{
			HtmlResult.failed(strDescription, strExpectedResult, "Resource Id "+ResourceId+" not clicked successfully");
		}
	}

	public void MessageboxConfirmation(String resourceid)
	{
		int currentcounter;
		boolean CurrentFlagValue;
		do
		{
			currentcounter =1;
			CurrentFlagValue= false;
			try
			{
				CurrentFlagValue = 	driver.findElement(By.id(resourceid)).isDisplayed();
			}
			catch (Exception e)
			{
				currentcounter = currentcounter + 1;
				CurrentFlagValue= false;
			}
		} while (!CurrentFlagValue && currentcounter <=10);

		if (CurrentFlagValue)
		{
			driver.findElement(By.id(resourceid)).click();
			System.out.println(resourceid + " clicked successfully");
		}
		else
		{
			System.out.println(resourceid + " is not clicked");
		}
	}

	////////////////////////////////////////////////////////


	/////////////////////////////////////////////////////

	public void ClickButtonOnScreen(Map<String,String> Input)
	{
		String strDescription = "To identify Button on screen";
		String strExpectedResult = "Button should be present on screen";
		String ButtonName = Input.get("ButtonName");
		try
		{
			if(appUIDriver.isDisplayedById(ButtonName))
			{
				HtmlResult.passed(strDescription, strExpectedResult, "Slider menu has been clicked");
			}
			else
			{
				HtmlResult.warning(strDescription, strExpectedResult, "Unable to locate slider menu on screen using appium");
				EggPlant eggPlant = new EggPlant();
				//eggPlant.connect("Andriod");

				if(eggPlant.clickButton(ButtonName))
				{
					HtmlResult.passed(strDescription, strExpectedResult, "Slider menu has been located on screen and clicked using eggplant");
				}
				else
				{
					HtmlResult.failed(strDescription, strExpectedResult, "Slider menu image is not present on screen");
				}
			}
		}
		catch(Exception e)
		{
			HtmlResult.failed(strDescription, strExpectedResult, "Error while performing IdentifyButtonOnScreen() '"+e.getMessage()+","+e.getCause());
		}
	}

	public boolean mobileAppSync()
	{
		try
		{
			boolean blnFound;
			String strObjectName = EggPlant.getValueFromExcel("Mobile_GMA_SyncObject");
			String strMobileObjectName  = appUIDriver.getAppObjectwithLocator(strObjectName);
			String[] arrMobileObjectName = strMobileObjectName.split("#");
			String objectLocatorName =arrMobileObjectName[0].toString();
			String objectLocatorValue= arrMobileObjectName[1].toString();
			int loopcounter =0;
			do{
				blnFound = appUIDriver.isObjectDisplayed(objectLocatorName, objectLocatorValue);
				if (!blnFound)
				{
					System.out.println("Object is not found hence exit from app ");
					//HtmlResult.passed("verify the current content of app ", "App content should be - "+ strObjectName + " for the object -" + strObjectName , "Actual content is - " + strObjectName);
					return true;

					//break;

				}
				else
				{
					appUIDriver.wait(2);
					loopcounter = loopcounter +1;
					if (loopcounter>10)
					{
						return false;
					}
				}
			}while (!blnFound);
		}
		catch(Exception e)
		{
			return false;
		}
		return false;
	}

	public void wait(Map<String,String> input)
	{
		try
		{
			String strWaitTimeSec =input.get("waitTimeInSec");
			int WaitTimeSec = Integer.parseInt(strWaitTimeSec); //parsing to int
			appUIDriver.wait(WaitTimeSec);
			HtmlResult.passed("Wait action perform", "wait perfomed for sec " + WaitTimeSec , "wait perfomed for sec " + WaitTimeSec);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}


	///////////////////////////////////////////////////
	public void customerLogin(Map<String, String> input)
	{
		try
		{
			String strUserId = input.get("custUid").trim();
			String strPassword = input.get("custPwd").trim();
			String strDescription ="Perform customer login";
			String strExpectedResult ="Customer should be login on application using user id - " + strUserId + " and password " + strPassword;
			String obj_logIn_UserId=EggPlant.getValueFromExcel("GMA_LOGIN_USERID");
			String obj_logIn_Password=EggPlant.getValueFromExcel("GMA_LOGIN_PASSWORD");
			String obj_login_LogInButton=EggPlant.getValueFromExcel("GMA_LOGIN_LOGINBUTTON");
			String strCurrentLocators  = appUIDriver.getAppObjectwithLocator("obj_logIn_UserId");
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
	}
	///////////////////////////////////////////////////

	public void getNetworkConnection(Map<String,String> input)
	{
		try
		{
			String strTypeOfNetwork = input.get("TypeOfNetwork");
			String strExpectedChoice = input.get("expectedChoice");
			boolean actionPerformed =true;
			//actionPerformed = appUIDriver.getNetworkConnection(strTypeOfNetwork, strExpectedChoice);
			
			if(actionPerformed)
			{
				HtmlResult.passed("Network operation performed", strExpectedChoice,"Network is switched ON");
			}
			else
			{
				HtmlResult.failed("Network operation not performed", "False","Network is switched Off");
			}

		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}

	}


	///


	public void setDeviceMockLocation(Map<String,String> input)
	{
		try
		{

			String strLatitudeValue = input.get("LatitudeValue");
			String strLongitudeValue =input.get("LongitudeValue");
			String GMA5_buildNumber =EggPlant.getValueFromExcel("GMA5_buildNumber");
			Boolean blnFound =true;
			Map<String,String> output = new HashMap<String,String>();
			output.put("menuName", "About");
			output.put("mobileObjectName","app_version");
			output.put("expectedData", GMA5_buildNumber);
			hamburgerMenuSelection(output);
			if (blnFound)
			{
				blnFound = verifyTextAndClickAppObject1(output);
				if (blnFound)
				{
					//blnFound  =  appUIDriver.scrollToObjectFound("setting_MockLocation");
					if (blnFound)
					{
						//appUIDriver.clickButton("setting_MockLocation");
						blnFound = appUIDriver.getAppObjectFound("setting_mockLoc_latitude_value");
						if(blnFound)
						{
							appUIDriver.enterText("setting_mockLoc_latitude_value", strLatitudeValue);
							//appUIDriver.clickButton("ObjNextButton");
							//appUIDriver.pressMobileKey("Enter");
							appUIDriver.enterText("setting_mockLoc_longitude_value", strLongitudeValue);
							//appUIDriver.clickButton("ObjNextButton");
							//appUIDriver.pressMobileKey("Enter");
							//Validate entry of Latitude and Longitude value
							appUIDriver.clickButton("setting_mockLoc_done");
							//appUIDriver.clickButton("backbutton");
							HtmlResult.passed("Set Mock Location for device" , "Device should be setup for mock location" , "Device should be setup for mock location");
							hamburgerMenuSelection(output);
						}
					}
					else
					{
						System.out.println("Not Found");
					}
				}
			}
		}
		catch (Exception e)
		{

			System.out.println(e.getMessage());

		}
	}


	//


	public void backbuttonPress(Map<String,String> input){

		try{
			String strbuttonPress = input.get("buttonName");
			String strexpectedData = input.get("expectedData");
			boolean buttonPressed=true;
			//buttonPressed = appUIDriver.backbuttonPress(strbuttonPress);
			if(buttonPressed){
				HtmlResult.passed("Return to main screen",strexpectedData,"Back to main screen");
			}
			else
			{
				HtmlResult.failed("Not return to main screen", "False","Back to main screen");
			}

		}

		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void scrollverifyitemOnPage(Map<String,String> input)
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


	public void scrollToTextVerifyAndClickObject(Map<String,String> input)
	{
		try{
			String strMobileObjectName =input.get("objectName");
			String strExpectedData = input.get("expectedData");
			String CurrentObjectText="";
			strMobileObjectName = strMobileObjectName.trim();
			strExpectedData = strExpectedData.trim();
			boolean BlnSwipeFound = false;
			boolean blnFound =false;
			int loopCounter=0;
			do
			{
				Thread.sleep(1000);
				Dimension CurrentMobileSize = appUIDriver.getWindowSize();
				System.out.println(CurrentMobileSize);
				// Current screen size from mobile devices -
				height = CurrentMobileSize.getHeight();
				width = CurrentMobileSize.getWidth();
				x=width/2;
				starty = (int) (height*0.40);
				endy= (int) (width*0.20);
				try
				{
					blnFound = appUIDriver.getAppObjectFound(strMobileObjectName);
					if (blnFound)
					{
						//BlnSwipeFound = driver.findElement(By.xpath("//android.widget.TextView[@text='"+strLogin+"'and @index='0' and @resource-id='com.mcdonalds.app.uk.qa:id/save']")).isDisplayed();


						String strMobileObjectLocator  = appUIDriver.getAppObjectwithLocator(strMobileObjectName);
						String[] arrMobileObjectName = strMobileObjectLocator.split("#");
						String objectLocatorName =arrMobileObjectName[0].toString();
						String objectLocatorValue= arrMobileObjectName[1].toString();

						CurrentObjectText = appUIDriver.getText(objectLocatorName, objectLocatorValue);
						BlnSwipeFound = true;
						//driver.findElement(By.xpath("//android.widget.TextView[@text='"+strLogin+"'and @index='0'and @resource-id='com.mcdonalds.app.uk.qa:id/save']")).click();
					}
					else
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
			} while (!BlnSwipeFound);
			// After Successfully loop , the value will check
			if (CurrentObjectText.equalsIgnoreCase(strExpectedData))
			{
				System.out.println("PASS");
				blnFound = appUIDriver.clickButton(strMobileObjectName);
				if(blnFound)
				{
					HtmlResult.passed("verify the current content of app ", "App content should be - "+ strExpectedData + " for the object -" + strMobileObjectName , "Actual content is - " + CurrentObjectText);
				}
				else
				{
					HtmlResult.failed("verify the current content of app ", "App content should be - "+ strExpectedData + " for the object -" + strMobileObjectName , "Actual content is not - " + CurrentObjectText);
				}
			}
			else
			{
				System.out.println("FAIL");
				HtmlResult.failed("verify the current content of app ", "App content should be - "+ strExpectedData + " for the object -" + strMobileObjectName , "Actual content is not - " + CurrentObjectText);
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}









