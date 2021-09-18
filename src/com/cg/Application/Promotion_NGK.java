package com.cg.Application;


import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;

import com.cg.ApplicationLevelComponent.EggPlant;
import com.cg.Logutils.HtmlResult;
import com.cg.TestCaseRunner.TestCaseRunner;
import com.cg.UIDriver.EggUIDriver;

import jxl.JXLException;
import jxl.read.biff.BiffException;

public class Promotion_NGK extends EggPlant {

	int intWaitFor;//global wait variable
	public String ImgWait;
	public String StrNGKlanguage ="";

	/*
	 * ***********************************************************************************************************
	 * Method Number - 1
	 * Method - tapToStartNGK
	 * Parameter - Map<String,String> input ( We need to take image input from designer)
	 * Description: To Tap NGK Main Screen
	 * Author: Prateek Gupta (Capgemini UKIT Automation Team)
	 * Date Created: 08/Sep/2016
	 * Modification History:  
	 * <Date>      <Who>     <Mod description>
	 * <6/28/2017> <Yogesh>  <Code Review>
	 ***************************************************************************************************************
	 */

	public void tapToStartNGK(Map<String,String> input )// to start ngk oprations
	{
		String stepDesc="To tap on NGK's screen";
		String strExpected="NGK should be started";

		try{

			String NGKStartImageName =input.get("imageNameToStartNGK").trim();
			
			if (eggUIDriver.imageFound(NGKStartImageName))  
			{
				eggUIDriver.clickPoint(new Point(1000,1000));//clicking on any point on NGK will make it ready to select language and place to eat
				HtmlResult.passed(stepDesc,strExpected,"NGK is started successfully");
				eggUIDriver.wait(3);// wait till next screen gets loaded successfully 
			}
			else
			{
				HtmlResult.failed(stepDesc,strExpected,"NGK is not started");
				RecoveryNGK_OrderConfirmation();
				RecoveryNGK_TimeOut();
			}
		}
		catch (Exception e)
		{
			HtmlResult.failed (stepDesc,strExpected,"NGK is not started due to this issue" + e.getMessage());
		}
	}

	/*
	 ***************************************************************************************************************
	 * Method Number - 2
	 * Method - nGKLanguageSelection
	 * Component:  NGK
	 * Description:  To Select NGK Language
	 * Parameter - Map<String,String> input ( We need to take image input from designer)
	 * Author: Prateek Gupta (Capgemini UKIT Automation Team)
	 * Date Created: 08/Sep/2016
	 * Modification History:
	 * <Date> <Who> <Mod description>
	 *
	 ***************************************************************************************************************

	 */

	public void nGKLanguageSelection(Map<String,String> input)
	{
		
		try{

			boolean NameFlag = false;
			String Btn_LanguageName ="";
			String Btn_ngkOrderType ="";

			String LanguageName = input.get("languageName");
			StrNGKlanguage = LanguageName.trim();
			String OrderType = input.get("orderType");
			LanguageName = LanguageName.trim();
			OrderType =OrderType.trim();

			// Reading Values from Property files - We can set image name in property file
			String NGK_Eat_In  = getValueFromExcel("NGK_EatInTotal");
			String NGK_Take_Out  = getValueFromExcel("NGK_TakeOutTotal");

			// Reading Order type
			if (OrderType.equalsIgnoreCase("Eat in Total") || OrderType.equalsIgnoreCase("Eat in") )
			{
				Btn_ngkOrderType = NGK_Eat_In;
			}
			else if (OrderType.equalsIgnoreCase("Take out Total")|| OrderType.equalsIgnoreCase("Take Out"))
			{
				Btn_ngkOrderType = NGK_Take_Out;
			}

			// Reading Button names from property files
			String English = getValueFromExcel("NGKLanguageName_English");
			String Welsh = getValueFromExcel("NGKLanguageName_Welsh");
			String French = getValueFromExcel("NGKLanguageName_French");
			String German = getValueFromExcel("NGKLanguageName_German");
			String Spanish = getValueFromExcel("NGKLanguageName_Spanish");
			String Polish = getValueFromExcel("NGKLanguageName_Polish");

			if (LanguageName.trim().equalsIgnoreCase("English"))
			{
				Btn_LanguageName = English;
				NameFlag = true;
			}
			else if (LanguageName.equalsIgnoreCase("Welsh"))
			{
				Btn_LanguageName = Welsh;
				NameFlag = true;
			}
			else if (LanguageName.equalsIgnoreCase("French"))
			{
				Btn_LanguageName = French;
				NameFlag = true;
			}
			else if (LanguageName.equalsIgnoreCase("German"))
			{
				Btn_LanguageName = German;
				NameFlag = true;
			}

			else if (LanguageName.equalsIgnoreCase("Spanish"))
			{
				Btn_LanguageName = Spanish;
				NameFlag = true;
			}

			else if (LanguageName.equalsIgnoreCase("Polish"))
			{
				Btn_LanguageName = Polish;
				NameFlag = true;
			}

			String strStepDesc="Select NGK language and order type" ;
			String strExpected="Language - " + LanguageName +  " and order type '" +  OrderType + "' should be selected";

			// For Reporting purpose we are using flag value so we can customize our report because NGK image have different name in uiMap

			if (NameFlag) // Checking with Language name are correct or not , if correct then we will execute this statement
			{
				boolean Clicked = clickButton(Btn_LanguageName + " # " + Btn_ngkOrderType);

				if (Clicked)
				{
					HtmlResult.passed(strStepDesc,strExpected,"Language and meal type selected successfully");
					eggUIDriver.wait(3);
				}

				else
				{
					HtmlResult.failed(strStepDesc,strExpected,"Language and meal type is not selected");
					//Recall recovery for execution
					RecoveryNGK_LangugeSelection();
				}
			}
			else
			{
				HtmlResult.failed(strStepDesc,strExpected,"NGK langauge entry is worng, Please select any language from list - "
						+ "(English,Welsh,French,German,Spanish,Polish");
				//Recall recovery for execution
				RecoveryNGK_LangugeSelection();
			}
		}
		catch (Exception e)
		{
			HtmlResult.failed("NGK Language Selection","NGK langauge should be present for Selection",e.getMessage());
		}
	}

	/*
	 ***************************************************************************************************************
	 * Method Number - 3
	 * Method - MealSelectionPopup
	 * Component:  NGK
	 * Description:  ordering a meal
	 * Parameter - Map<String,String> input ( We need to take image input from designer)
	 * Author: Prateek Gupta (Capgemini UKIT Automation Team)
	 * Date Created: 08/Sep/2016
	 * Modification History:
	 * <Date> <Who> <Mod description>
	 *
	 ***************************************************************************************************************

	 */

	public void MealSelectionPopup(Map<String,String> input)
	{
		try
		{
			String StrProductName = input.get("mealProductName"); // Reading Product Name from designer
			Dimension ScreenResolution = eggUIDriver.remoteScreenSize(); // Reading Runtime screensize using eggplant method
			int Screenheight = ScreenResolution.height; // Reading Runtime NGK height
			int Screenwidth = ScreenResolution.width;   // Reading Runtime NGK width

			// define rectange to Select Meal Popup box
			Rectangle r = new Rectangle();
			r.height= Screenheight*47/100;
			r.width= Screenwidth;
			r.x = 11;
			r.y = Screenheight*53/100;

			boolean Status =  eggUIDriver.imageFound(StrProductName); // if Product is found on screen (in given rectangle area)
			if (Status)
			{
				if(!eggUIDriver.clickinSearchRectangle(StrProductName, ScreenPart(0.022222222, 0.519270833, 0.999074074, 0.990625)))
					Status =  eggUIDriver.clickinSearchRectangle(StrProductName, r);
				if(Status){
					HtmlResult.passed("Image Click in Search Rectangle", "PRoduct should be click in Search Rectangle" + StrProductName, "PASS" + StrProductName);
				}
				else
				{
					HtmlResult.failed("Image Click in Search Rectangle", "Product should be click in Search Rectangle" + StrProductName, "FAIL" + StrProductName);
				}
			}
			else
			{
				HtmlResult.failed("Image Click in Search Rectangle", "PRoduct  should be click in Search Rectangle" + StrProductName, "FAIL" + StrProductName);
			}
		}

		catch (Exception e)
		{
			HtmlResult.failed("Image Click in Search Rectangle", "Product  should be click in Search Rectangle" , e.getMessage());
		}
	}
	
	/*
	 ***************************************************************************************************************
	 * Method Number - 4
	 * Method - quantityModifierVerification
	 * Component:  NGK
	 * Description:  To Verify Product Quantity ( + , - and  remove the product from order basket)
	 * Parameter -
	 * Author: Prateek Gupta (Capgemini UKIT Automation Team)
	 * Date Created:
	 * Modification History:
	 * <Date> <Who> <Mod description>
	 *
	 ***************************************************************************************************************
	 */

	public void quantityModifierVerification(Map<String,String> input) throws FileNotFoundException, IOException, InterruptedException, BiffException
	{
		try
		{
			String OrderBasket_ProductCustomiseButton=getValueFromExcel("OrderBasket_ProductCustomiseButton");
			String Qty_Minus=getValueFromExcel("Qty_Minus"); // Need to update as new UI - Prateek
			String Qty_Plus=getValueFromExcel("Qty_Plus");// Need to update as new UI
			String Confirmation_deleteItem_YES= getValueFromExcel("Confirmation_deleteItem_YES");
			String Confirmation_deleteItem_NO=getValueFromExcel("Confirmation_deleteItem_NO");
			String OrderBasket_ChangeOrderBasketFocus=getValueFromExcel("OrderBasket_ChangeOrderBasketFocus");
			 
			int ActualQty=1;
			boolean blnImageFoundAndClicked=false;
			String strSalesPanelData="";
			String StringSpace =" ";
			String strProductName=input.get("salesPanelProductName").trim();  //fetch the product name for verification purpose

			eggUIDriver.wait(4);

			blnImageFoundAndClicked=clickButton(OrderBasket_ProductCustomiseButton);
			if(blnImageFoundAndClicked)
			{
				HtmlResult.passed("Click Button - Customise","Button click should be successful - Customise","Customise button clicked successfully");
				int intMaxQty=3;   //we can keep the maximum quantity global

				//For loop to verify the increment in quantity of ordered product on clicking "+" button
				//initial product quantity is 1
				for(int qty=1;qty<=intMaxQty;qty++)
				{
					blnImageFoundAndClicked=clickButton(Qty_Plus);
					if(blnImageFoundAndClicked)
					{
						HtmlResult.passed("Click Button - Plus(+) ","Button click should be successful - Plus(+)","Plus(+) button clicked successfully");

						ActualQty=qty+1;  //when plus button is clicked qty will increase by 1

						eggUIDriver.wait(2); // will replace with code

						blnImageFoundAndClicked=clickButton(OrderBasket_ChangeOrderBasketFocus);

						eggUIDriver.wait(2); // will replace with code

						if(blnImageFoundAndClicked)
						{
							//read the panel and check if quantity is incremented
							strSalesPanelData=ReadOrderBasketPanel(StrNGKlanguage);

							String strActualSalesPanelData= ""+ActualQty+" X"+" "+strProductName;
							if(strSalesPanelData.trim().contains(strActualSalesPanelData))
							{
								HtmlResult.passed("Product Name validaiton with Qty",strActualSalesPanelData+ " - should be presnet in order basket",strActualSalesPanelData+ " - is presnet in order basket");
							}
							else
							{
								HtmlResult.failed("Product Name validaiton with Qty",strActualSalesPanelData+ " - should be presnet in order basket",strActualSalesPanelData+ " - is not presnet in order basket");
							}

							blnImageFoundAndClicked=clickButton(OrderBasket_ProductCustomiseButton);
							if(blnImageFoundAndClicked)
							{
								HtmlResult.passed("Click Button - Customise","Button click should be successful - Customise","Customise button clicked successfully");
							}
							else
							{
								HtmlResult.failed("Click Button - Customise","Button click should be successful - Customise","Customise button is not clicked");
							}
						}
						else
						{
							HtmlResult.failed("Click Button - Plus(+) ","Button click should be successful - Plus(+)","Plus(+) button is not clicked");
						}
					}
				}

				//for checking quantity decreases on clicking upon "-" button
				for(int qty=1;qty<=intMaxQty;qty++)
				{
					String ActualQtyString="";
					blnImageFoundAndClicked=clickButton(Qty_Minus);

					if(blnImageFoundAndClicked)
					{
						HtmlResult.passed("Click Button - Minus(-) ","Button click should be successful - Minus(-)"," Minus(-) button clicked successfully");
						ActualQty=(intMaxQty+1)-qty;  //when plus button is clicked qty will increase by 1

						blnImageFoundAndClicked=clickButton(OrderBasket_ChangeOrderBasketFocus);
						if(blnImageFoundAndClicked)
						{
							//read the panel and check if quantity is incremented
							strSalesPanelData=ReadOrderBasketPanel(StrNGKlanguage);
							ActualQtyString =Integer.toString(ActualQty);

							if (ActualQty == 1)
							{
								ActualQtyString= ""; // Due to OCR issue we are not able to read 1 Qty
								StringSpace ="";
							}

							String strActualSalesPanelData=""+ ActualQtyString+StringSpace+"X"+" "+strProductName;
							if(strSalesPanelData.contains(strActualSalesPanelData))
							{
								//data validation passed
								HtmlResult.passed("Product Name validaiton with Qty",strActualSalesPanelData+ " - should be presnet in order basket",strActualSalesPanelData+ " - is presnet in order basket - " +strSalesPanelData);
							}
							else
							{
								HtmlResult.failed("Product Name validaiton with Qty",strActualSalesPanelData+ " - should be presnet in order basket",strActualSalesPanelData+ " - is not presnet in order basket -" + strSalesPanelData);
							}

							blnImageFoundAndClicked=clickButton(OrderBasket_ProductCustomiseButton);
							if(blnImageFoundAndClicked)
							{
								HtmlResult.passed("Click Button - Customise","Button click should be successful - Customise","Customise button clicked successfully");
							}
							else
							{
								HtmlResult.failed("Click Button - Customise","Button click should be successful - Customise","Customise button is not clicked");
							}
						}
						else
						{
						}
					}
					else
					{
						HtmlResult.failed("Click Button - Minus(-) ","Button click should be successful - Minus(-)"," Minus(-) button is not clicked");
					}
				}

				//click on NO and verify is order present - ideally order should be present.
				blnImageFoundAndClicked=clickButton(Qty_Minus);
				if(blnImageFoundAndClicked)
				{
					blnImageFoundAndClicked=clickButton(Confirmation_deleteItem_NO);
					if(blnImageFoundAndClicked)
					{
						HtmlResult.passed("Click Button - No","Button click should be successful - No","No button clicked successfully");

						blnImageFoundAndClicked=clickButton(OrderBasket_ChangeOrderBasketFocus);
						if(blnImageFoundAndClicked)
						{
							//read the panel and check if quantity is incremented
							strSalesPanelData=ReadOrderBasketPanel(StrNGKlanguage);
							StringSpace ="";
							String strActualSalesPanelData=StringSpace+"X"+" "+strProductName;
							if(strSalesPanelData.contains(strActualSalesPanelData))
							{
								//data validation passed
								HtmlResult.passed("Verification for presence of order in order basket ",strActualSalesPanelData+ " - should be present in order basket",strActualSalesPanelData+ " - is present in order basket - ");
							}
							else
							{
								HtmlResult.failed("Verification for presence of order in order basket",strActualSalesPanelData+ " - should be presnet in order basket","Data present in order basket is-" + strSalesPanelData);
							}

							blnImageFoundAndClicked=clickButton(OrderBasket_ProductCustomiseButton);
							if(blnImageFoundAndClicked)
							{
								HtmlResult.passed("Click Button - Customise","Button click should be successful - Customise","Customise button clicked successfully");
							}
							else
							{
								HtmlResult.failed("Click Button - Customise","Button click should be successful - Customise","Customise button is not clicked");
							}
						}
						else
						{
						}
					}
					else
					{
						HtmlResult.failed("Click Button - No ","Button click should be successful - No","No button is not clicked");
					}
				}
				else
				{
					//HtmlResult.HtmlResult.failed to click minus button
					HtmlResult.failed("Click Button - Minus(-) ","Button click should be successful - Minus(-)"," Minus(-) button is not clicked");
				}

				//Click on YES button to verify there is no order present
				blnImageFoundAndClicked=clickButton(Qty_Minus);
				if(blnImageFoundAndClicked)
				{
					HtmlResult.passed("Click Button - Minus(-) ","Button click should be successful - Minus(-)"," Minus(-) button clicked successfully");
					blnImageFoundAndClicked=clickButton(Confirmation_deleteItem_YES);
					if(blnImageFoundAndClicked)
					{
						HtmlResult.passed("Click Button - Yes","Button click should be successful - Yes","Yes button clicked successfully");
						strSalesPanelData=ReadOrderBasketPanel(StrNGKlanguage);
						if(strSalesPanelData.contains("Your order is empty")) // NEed to update with all name
						{
							HtmlResult.passed("Verification for presence of order in order basket", "Order basket should be empty ",  "Order basket is empty as expected");
						}
						else
						{
							HtmlResult.failed("Verification for presence of order in order basket", "Order basket should be empty ", "Order basket is not empty \n Data present is -"+strSalesPanelData);
						}
					}
					else
					{
						//failed to click yes button
						HtmlResult.failed("Click Button - Yes","Button click should be successful - Yes","Yes button is not clicked");
					}
				}
				else
				{
					HtmlResult.failed("Click Button - Minus(-) ","Button click should be successful - Minus(-)"," Minus(-) button is not clicked");
				}
			}
			else
			{
				//customise
				HtmlResult.failed("Click Button - Customise","Button click should be successful - Customise","Customise button is not clicked");
			}

		}

		catch (Exception e)

		{
			HtmlResult.failed("Perform Qty Modifier ","Qty Modifer functionality should be working","Qty Modifer functionality is not working");

		}
	}



	/*
	 ***************************************************************************************************************
	 * Method Number - 4
	 * Method - quantityModifierVerification
	 * Component:  NGK
	 * Description:  To Verify Product Quantity ( + , - and  remove the product from order basket)
	 * Parameter -
	 * Author: Prateek Gupta (Capgemini UKIT Automation Team)
	 * Date Created:
	 * Modification History:
	 * <Date> <Who> <Mod description>
	 *
	 ***************************************************************************************************************
	 */

	public void quantityModifierVerification_New(Map<String,String> input) throws FileNotFoundException, IOException, InterruptedException, BiffException
	{
		try
		{
			String Qty_Minus=getValueFromExcel("Qty_Minus"); // Need to update as new UI - Prateek
			String Qty_Plus=getValueFromExcel("Qty_Plus");// Need to update as new UI
			
			String MaximumQuantity =input.get("MaximumQuantity").trim(); //get quantity
			
			String ngk_ReviewOrder_Qty_Plus = getValueFromExcel("ngk_ReviewOrder_Qty_Plus");
			String ngk_ReviewOrder_Qty_Minus  = getValueFromExcel("ngk_ReviewOrder_Qty_Minus");
			String OrderConfirmation_AddToOrder = getValueFromExcel("OrderConfirmation_AddToOrder");

			String StrButton_AddToOrder = getValueFromExcel("OrderConfirmation_AddToOrder");

			int MaxQty = Integer.parseInt(MaximumQuantity);// Need to put from Excel
			boolean blnImageFoundAndClicked= false;
			eggUIDriver.wait(4);
			
			//Verify the quantity of the product before add to order
			double UnitPriceOfProductPlus = getPriceOfProduct();
			for(int qty=2;qty<=MaxQty;qty++)
			{
				blnImageFoundAndClicked=clickButton(Qty_Plus);
				double ExpectedPrice = (double)Math.round((UnitPriceOfProductPlus*qty)*100)/100;
				if(blnImageFoundAndClicked)
				{
					double PriceOfProductPlus = getPriceOfProduct();
					if(PriceOfProductPlus==ExpectedPrice || (PriceOfProductPlus-ExpectedPrice<=0.00001))
					{
						HtmlResult.passed("Verify if the product price is changed on changing the quantity",
											"For quantity '"+qty+"' , price of the product should be '"+ExpectedPrice+"'",
											"Actual product price is "+PriceOfProductPlus);
					}
					else
					{
						HtmlResult.failed("Verify if the product price is changed on changing the quantity",
											"For quantity '"+qty+"' , price of the product should be '"+ExpectedPrice+"'",
											"Actual product price is "+PriceOfProductPlus);
					}
				}
				else
				{
					HtmlResult.failed("Click Button - Plus(+) ","Button click should be successful - Plus(+)","Plus(+) button is not clicked");
				}
			}

			double PriceOfProductMinus = getPriceOfProduct(); 
			for(int qty=MaxQty;qty>=2;qty--)
			{
				double ExpectedPrice = (double)Math.round((PriceOfProductMinus-(PriceOfProductMinus/qty))*100)/100;
				blnImageFoundAndClicked=clickButton(Qty_Minus);
				if(blnImageFoundAndClicked)
				{	
					PriceOfProductMinus = getPriceOfProduct();
					if(PriceOfProductMinus==ExpectedPrice)
					{
						HtmlResult.passed("Verify if the product price is changed on changing the quantity",
								"For quantity '"+qty+"' , price of the product should be '"+ExpectedPrice+"'",
								"Actual product price is "+PriceOfProductMinus);					}
					else
					{
						HtmlResult.failed("Verify if the product price is changed on changing the quantity",
								"For quantity '"+qty+"' , price of the product should be '"+ExpectedPrice+"'",
								"Actual product price is "+PriceOfProductMinus);					}
				}
				else
				{
					HtmlResult.failed("Click Button - Minus(-) ","Button click should be successful - Minus(-)","Minus(-) button is not clicked");
				}
			}

			if (eggUIDriver.imageFound(OrderConfirmation_AddToOrder))
			{
				boolean FoundandClicked = clickButton(OrderConfirmation_AddToOrder);

				if (FoundandClicked)
				{
					HtmlResult.passed("Select 'Add to Order' Button", " 'Add to Order' button should be selected", "'Add to Order' button selected successfully");
				}
				else
				{
					HtmlResult.failed("Select 'Add to Order' Button", " 'Add to Order' button should be selected", "'Add to Order' button is not selected ");
				}
			}
			else
			{
				HtmlResult.failed("Select 'Add to Order' Button", " 'Add to Order' button should be selected", "Add to Order is not present");
			}

			String strStepDesc ="Complete the Order";
			String strExpected="Order should be complete   - ";
			String Str_orderConfirmationTypeSelection ="";

			boolean FoundAndClicked = clickButton("ngk_commonbutton_CompleteOrder");
			if (FoundAndClicked)
			{
				HtmlResult.passed(strStepDesc,strExpected,Str_orderConfirmationTypeSelection + " -  Confirmation selected successfully " );
			}
			else
			{
				HtmlResult.failed(strStepDesc,strExpected,Str_orderConfirmationTypeSelection + " -  Confirmation is not selected");
				//Recall recovery to cancel incomplete order
				RecoveryNGK_OrderConfirmation();
			}

			// Review My Eat in Order Screen
			//Click on Edit Button

			if (eggUIDriver.imageFound("ngk_ReviewOrder_EditButton"))
			{
				FoundAndClicked =  clickButton("ngk_ReviewOrder_EditButton");

				if (FoundAndClicked)  // click on Edit Button
				{
					HtmlResult.passed("Edit Button functionality ","Edit button should be clicked" , " Edit button is clicked successfully " );

					eggUIDriver.wait(intWaitFor);

					if (eggUIDriver.imageFound(StrButton_AddToOrder))
					{
						FoundAndClicked = clickButton(StrButton_AddToOrder);

						if (FoundAndClicked)
						{
							HtmlResult.passed("Select 'Add to Order' Button", " 'Add to Order' button should be selected", "'Add to Order' button selected successfully");
						}
						else
						{
							HtmlResult.failed("Select 'Add to Order' Button", " 'Add to Order' button should be selected", "'Add to Order' button is not selected ");
						}
					}
					else
					{
						HtmlResult.failed("Edit Button functionality ","Edit button should be clicked" , "Edit button is not clicked" );
					}
				}
				else
				{
					HtmlResult.failed("Edit Button functionality ","Edit button should be clicked" , "Edit button is not clicked" );
				}
			}
			else
			{
				HtmlResult.failed("Edit Button functionality ","Edit button should be clicked" , "Edit button is not clicked" );
			}

			//  Order Confirmation Screen for Modificaiton
			double UnitPriceOfProductPlusReview = getPriceOfProduct();
			for(int qty=2;qty<=MaxQty;qty++)
			{
				blnImageFoundAndClicked=clickButton(ngk_ReviewOrder_Qty_Plus);
				double ExpectedPrice = (double)Math.round((UnitPriceOfProductPlusReview*qty)*100)/100;
				if(blnImageFoundAndClicked)
				{
					double PriceOfProductPlusReview = getPriceOfProduct();
					if(PriceOfProductPlusReview==ExpectedPrice)
					{
						HtmlResult.passed("Verify if the product price is changed on changing the quantity",
								"For quantity '"+qty+"' , price of the product should be '"+ExpectedPrice+"'",
								"Actual product price is "+PriceOfProductPlusReview);					}
					else
					{
						HtmlResult.failed("Verify if the product price is changed on changing the quantity",
								"For quantity '"+qty+"' , price of the product should be '"+ExpectedPrice+"'",
								"Actual product price is "+PriceOfProductPlusReview);					}
					
				}
				else
				{
					HtmlResult.failed("Click Button - Plus(+) ","Button click should be successful - Plus(+)","Plus(+) button is not clicked");
				}
			}
	
			double PriceOfProductMinusReview = getPriceOfProduct(); 
			for(int qty=MaxQty;qty>=2;qty--)
			{
				double ExpectedPrice = (double)Math.round((PriceOfProductMinusReview-(PriceOfProductMinusReview/qty))*100)/100;
				blnImageFoundAndClicked=clickButton(ngk_ReviewOrder_Qty_Minus);
				if(blnImageFoundAndClicked)
				{
					PriceOfProductMinusReview = getPriceOfProduct();
					if(ExpectedPrice==PriceOfProductMinusReview)
					{
						HtmlResult.passed("Verify if the product price is changed on changing the quantity",
								"For quantity '"+qty+"' , price of the product should be '"+ExpectedPrice+"'",
								"Actual product price is "+PriceOfProductMinusReview);					}
					else
					{
						HtmlResult.failed("Verify if the product price is changed on changing the quantity",
								"For quantity '"+qty+"' , price of the product should be '"+ExpectedPrice+"'",
								"Actual product price is "+PriceOfProductMinusReview);					}
				}
				else
				{
					HtmlResult.failed("Click Button - Minus(-) ","Button click should be successful - Minus(-)","Minus(-) button is not clicked");
				}
			}

			//Do you want to delete/remove order
			String strDescription ="Product Removal Operation";
			String strExpectedResult="Product Removal option should be select  - ";
			if (eggUIDriver.imageFound("ngk_ReviewOrderScreen_RemoveButton"))
			{
				clickButton("ngk_ReviewOrderScreen_RemoveButton"); // Click on Remove button
				// For  Remove option - NO
				if (eggUIDriver.imageFound("ngk_ReviewOrder_RemoveConfirmation_No"))
				{
					if(clickButton("ngk_ReviewOrder_RemoveConfirmation_No")) // Click on Remove - NO button
					{
						HtmlResult.passed(strDescription, strExpectedResult + "' No Button '", "	Product Removal option selected - ' No Button '");
					}
					else
					{
						HtmlResult.failed(strDescription, strExpectedResult + "' No Button '","Product Removal option is not selected - ' No Button '" );
					}
				}
				else
				{
					HtmlResult.failed(strDescription, strExpectedResult + "' No Button '","Product Removal option is not selected - ' No Button '" );
				}

			}

			if (eggUIDriver.imageFound("ngk_ReviewOrderScreen_RemoveButton"))
			{
				clickButton("ngk_ReviewOrderScreen_RemoveButton"); // Click on Remove - Yes button

				if (eggUIDriver.imageFound("ngk_ReviewOrder_RemoveConfirmation_Yes"))
				{
					clickButton("ngk_ReviewOrder_RemoveConfirmation_Yes"); // Click on Remove - Yes button
					HtmlResult.passed(strDescription, strExpectedResult + "' Yes Button '", "	Product Removal option selected - ' Yes Button '");
				}
				else
				{
					HtmlResult.failed(strDescription, strExpectedResult + "' Yes Button '","	Product Removal option selected - ' Yes Button '");
				}
			}
		}

		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	public double getPriceOfProduct()
	{
		try{
			double Price = 0.0;
			String ScreenData = finalOrderValidation(StrNGKlanguage);
			String[] strScreenData = ScreenData.split("\n");
			
			for(int Index = 0; Index<strScreenData.length ; Index++)
			{
				String LocalScreenData = strScreenData[Index].trim();
				if(LocalScreenData.equals("�"))
				{
					String strPrice = strScreenData[Index+1];
					Price = Double.parseDouble(strPrice);
				}
				else if(LocalScreenData.contains("�"))
				{
					String strPrice = LocalScreenData.substring(LocalScreenData.indexOf("�")+1,LocalScreenData.length());
					Price = Double.parseDouble(strPrice);
				}
				
				if(Price>0.0)
				{
					break;
				}
			}
			
			return Price;
		}
		catch(Exception e)
		{
			return 0.0;
		}
	}
	
	

	/*
	public String readSalesPanelLineByLinenew(String TextToSearch)
	{

		Dimension ScreenDimensions = eggUIDriver.remoteScreenSize(); // get size of current SUT connection
		Rectangle OriginalRectangle = new Rectangle(ScreenDimensions); // after successful search ,set the search rectangle to entire screen
		int SalesPanelSize = 0;
		int ReadingError = 0;
		int StartPoint = 0;
		int FontSize = Integer.parseInt(getValueFromExcel("FontSize").trim());
		if(ScreenDimensions.height==768 && ScreenDimensions.width==1024)
		{
			SalesPanelSize = 303;
			ReadingError = 6;
			FontSize = 16;
			StartPoint = 1;
		}
		else if(ScreenDimensions.height==600 && ScreenDimensions.width==800)
		{
			SalesPanelSize = 233;
			FontSize = 16;
			ReadingError = 8;
			StartPoint = 1;
		}
		else if(ScreenDimensions.height==600 && ScreenDimensions.width==1600)
		{
			ScreenDimensions = new Dimension(800, 600);
			SalesPanelSize = 233;
			FontSize = 16;
			ReadingError = 8;
			StartPoint = 1;
		}

		else
		{
			ScreenDimensions = new Dimension(1920, 1080);
			SalesPanelSize = 233;
			FontSize = 16;
			ReadingError = 8;
			StartPoint = 1;
		}

		try
		{
			String OverallSalesPanelData = "";


				ArrayList<Integer> TopLeft = eggUIDriver.ImageLocation("LeftTopCorner"); //location top left of sales panel
				int x1 = TopLeft.get(0);
				int y1 = TopLeft.get(1);

				ArrayList<Integer> BottomRight = eggUIDriver.ImageLocation("RightBottomCorner"); //location of right bottom of sales panel
				int x2 = BottomRight.get(0);
				int y2 = BottomRight.get(1);



			//Rectangle SearchRectangle = new Rectangle(x1, y1, x2, y2); //setting search rectangle to sales panel using above cordinates
			Rectangle SearchRectangle = new Rectangle(0, 0, 1080, 1920); //setting search rectangle to sales panel using above cordinates

				int LengthStart = x1+StartPoint; // starting x1 point for text searching and reading by using x1 of TopLeft
				int LegthEnd = x1+SalesPanelSize; // to set end point on x direction i.e, x2 using SalesPanelSize of current SUT connection
				int Width =  LegthEnd-LengthStart; // calculating width for searching text in a rectangle


			TextToSearch = "�";

				String[] arrTextsToSearch = TextToSearch.split("#");
			for(String Texts : arrTextsToSearch)
			{
				//	eggUIDriver.setSearchRectangle(SearchRectangle);
				Texts = Texts.trim();
				List<Point> TextLocation = eggUIDriver.getEveryTextLocation(Texts);
				if(TextLocation.size()!=0)
				{
					Point Location = TextLocation.get(0); // get first occurrence of text
					int TopLeftY = Location.y-ReadingError; // to make reading better
					int height = FontSize;
					Rectangle TextRectangle = new Rectangle(LengthStart, TopLeftY, Width, height); // creating rectangle over text
					String SalesPanelOrder = eggUIDriver.readText(TextRectangle) ; // read rectangle
					OverallSalesPanelData = OverallSalesPanelData.concat(SalesPanelOrder+"\n");
					OverallSalesPanelData.replaceAll("\t", "\n");
				}
				else
				{
					OverallSalesPanelData = eggUIDriver.readText(SearchRectangle).replace("I.I.I", "").replace("I.\tI.\tI", "");
					break;
				}
			}

			//eggUIDriver.setSearchRectangle(OriginalRectangle);
			OverallSalesPanelData = OverallSalesPanelData.replaceAll("\t", "\n");






		}
		catch(Exception e)
		{
			eggUIDriver.setSearchRectangle(OriginalRectangle);
		}
	}
	 */

	/*
	 ***************************************************************************************************************
	 * Method Number - 6
	 * Method - To Tap NGK Main Screen
	 * Component:  NGK
	 * Description:  To Select NGK Language
	 * Parameter - NGKStartImageName ( We need to take image input from designer)
	 * Author: Prateek Gupta (Capgemini UKIT Automation Team)
	 * Date Created: 08/Sep/2016
	 * Modification History:
	 * <Date> <Who> <Mod description>
	 *
	 ***************************************************************************************************************

	 */


	public void productMenuSelection (Map<String,String> input)
	{
		try
		{
			String StrNGKMainMenu = input.get("nGKMainMenu");
			String StrNGKSubMenu = input.get("nGKSubMenu");
			String StrProductSelection = input.get("productSelection");
			String Str_NGKResponseTime = input.get("nGKResponseTimeInSec");
			String Str_otherMealOrProductComponents = input.get("otherMealOrProductComponents");
			String StrButton_AddToOrder = input.get("StrButton_AddToOrder");//added by prateek for NGk 5.16
			boolean FoundandClicked	= false;

			int NGKResponseTime = Integer.parseInt(Str_NGKResponseTime);
			if (!StrNGKMainMenu.isEmpty())
			{
				eggUIDriver.wait(NGKResponseTime);
				FoundandClicked	= clickButton(StrNGKMainMenu);
				if (FoundandClicked)
				{
					eggUIDriver.wait(NGKResponseTime);
					if (!StrNGKSubMenu.isEmpty())
					{
						FoundandClicked	= clickButton(StrNGKSubMenu);
						if (FoundandClicked)
						{
			
							eggUIDriver.wait(NGKResponseTime);
							if (!StrProductSelection.isEmpty())
							{
								FoundandClicked	= clickButton(StrProductSelection);
								if (FoundandClicked)
								{
									if (!Str_otherMealOrProductComponents.isEmpty())
									{
										String [] Str_otherMealOrProductComponentsList = Str_otherMealOrProductComponents.split("#");
										
										for (String OtherProductComponent:Str_otherMealOrProductComponentsList)
										{
										
											OtherProductComponent = OtherProductComponent.trim();
											eggUIDriver.wait(NGKResponseTime + 2);
											
											FoundandClicked	= clickButton(OtherProductComponent);
											
											if (FoundandClicked)
											{
												HtmlResult.passed("Product Component Selection ","product Componentshould be selected ","product Component selection is successfully - " +OtherProductComponent);
												
											}
											else
											{
												HtmlResult.failed("Product Component Selection ","product  Component should be selected ","product Component selection is not selected - " +OtherProductComponent);
												RecoveryNGK_OrderConfirmation();
											
											}
										}
									}

									if (StrButton_AddToOrder.length()>0)
									{
										FoundandClicked = clickButton(StrButton_AddToOrder);

										if (FoundandClicked)
										{
											HtmlResult.passed("Select 'Add to Order' Button", " 'Add to Order' button should be selected", "'Add to Order' button selected successfully");
										}
										else
										{
											HtmlResult.failed("Select 'Add to Order' Button", " 'Add to Order' button should be selected", "'Add to Order' button is not selected ");
										}
									}
								}
								else
								{
									HtmlResult.failed("Product Selection ","product should be selected ","product selection is not selected - " +StrProductSelection);
									RecoveryNGK_OrderConfirmation();
								}
							}
							else
							{
								HtmlResult.failed("Product Selection ","product should be selected ","product value is empty");
								RecoveryNGK_OrderConfirmation();
							}
						}
						else
						{
							HtmlResult.failed("Sub Menu Selection","Sub Menu should be selected","Sub Menu selection is not selected - " + StrNGKSubMenu);
							RecoveryNGK_OrderConfirmation();
						}
					}
					else
					{
						if (!StrProductSelection.isEmpty())
						{
							eggUIDriver.wait(NGKResponseTime);
							FoundandClicked	= clickButton(StrProductSelection);
							if (FoundandClicked)
							{
								eggUIDriver.wait(NGKResponseTime);
								if (!Str_otherMealOrProductComponents.isEmpty())
								{
									String [] Str_otherMealOrProductComponentsList = Str_otherMealOrProductComponents.split("#");
									for (String OtherProductComponent:Str_otherMealOrProductComponentsList)
									{
										OtherProductComponent = OtherProductComponent.trim();
										FoundandClicked	= clickButton(OtherProductComponent);
										if (FoundandClicked)
										{
											HtmlResult.passed("Product Component Selection ","product Componentshould be selected ","product Component selection is successfully - " +OtherProductComponent);
										}
										else
										{
											HtmlResult.failed("Product Component Selection ","product  Component should be selected ","product Component selection is not selected - " +OtherProductComponent);
											
										}
									}
								}
								else
								{
									HtmlResult.passed("Product Component Selection ","product Componentshould be selected ","product selection is successful - " +StrProductSelection);
								}

								if (StrButton_AddToOrder.length()>0)
								{
									FoundandClicked = clickButton(StrButton_AddToOrder);

									if (FoundandClicked)
									{
										HtmlResult.passed("Select 'Add to Order' Button", " 'Add to Order' button should be selected", "'Add to Order' button selected successfully");
									}
									else
									{
										HtmlResult.failed("Select 'Add to Order' Button", " 'Add to Order' button should be selected", "'Add to Order' button is not selected ");
								
									}

								}
							}
							else
							{
								HtmlResult.failed("Product Selection ","product should be selected ","product value is wrong or empty - " + StrProductSelection);
								RecoveryNGK_OrderConfirmation();
							
							}
						}
					}
				}
				else
				{
					HtmlResult.failed("Main Menu Selection ","Main Menu should be selected ","Main Menu selection is not selected - " +StrNGKMainMenu);
					
					RecoveryNGK_OrderConfirmation();
			
				}
			}
			else
			{
				if (!StrProductSelection.isEmpty())
				{
					eggUIDriver.wait(NGKResponseTime);
					FoundandClicked	= clickButton(StrProductSelection);
					if (FoundandClicked)
					{
						eggUIDriver.wait(NGKResponseTime);
						HtmlResult.passed("Product Selection ","product should be selected ","product selection is successfully - " +StrProductSelection);
						
						if (!Str_otherMealOrProductComponents.isEmpty())
						{
							String [] Str_otherMealOrProductComponentsList = Str_otherMealOrProductComponents.split("#");
							
							for (String OtherProductComponent:Str_otherMealOrProductComponentsList)
							{
							
								OtherProductComponent = OtherProductComponent.trim();
								eggUIDriver.wait(NGKResponseTime + 2);
								
								FoundandClicked	= clickButton(OtherProductComponent);
								
								if (FoundandClicked)
								{
									HtmlResult.passed("Product Component Selection ","product Componentshould be selected ","product Component selection is successfully - " +OtherProductComponent);
									
								}
								else
								{
									HtmlResult.failed("Product Component Selection ","product  Component should be selected ","product Component selection is not selected - " +OtherProductComponent);
									RecoveryNGK_OrderConfirmation();
								
								}
							}
						}
						
						
						if (StrButton_AddToOrder.length()>0)
						{
							FoundandClicked = clickButton(StrButton_AddToOrder);

							if (FoundandClicked)
							{
								HtmlResult.passed("Select 'Add to Order' Button", " 'Add to Order' button should be selected", "'Add to Order' button selected successfully");
							}
							else
							{
								HtmlResult.failed("Select 'Add to Order' Button", " 'Add to Order' button should be selected", "'Add to Order' button is not selected ");
					
							}

						}
						else
						{
							System.out.println(" AddToOrder  button functionality is not configure for this");
					
						}


					}
					else
					{
						HtmlResult.failed("Product Selection ","product should be selected ","product selection is not selected - " +StrProductSelection);
					RecoveryNGK_OrderConfirmation();
		
					}
				}
				else
				{
					HtmlResult.failed("Main Menu Selection ","Main Menu should be selected ","Main Menu selection is empty - " +StrNGKMainMenu);
					RecoveryNGK_OrderConfirmation();
			
				}
			}
		}
		catch (Exception e)
		{
			HtmlResult.failed("Main Menu Selection ","Main Menu should be selected - ",e.getMessage());

			RecoveryNGK_OrderConfirmation();
	
		}
	}


	/*
#  ****************************************************************
#  Function Name: menuSelection
#  Component:  Generic
#  Description:  To Select NGK Menu
#  Input Parameters:
#  Author: Capgemini UKIT Automation Team
#  Date Created: 08/Sep/2016
#  Modification History:
#  <Date> <Who> <Mod description>
#  *****************************************************************
	 */




	public boolean ngkMenuSelection(Map<String,String> input)
	{
		String StrcarouselSliderMenuName = input.get("carouselSliderMenu").trim();
		String ScrollingLocationImage = input.get("scrollingLocationImage").trim();

		String strStepDesc="Carousel slider main menu selection";
		String strExpected =StrcarouselSliderMenuName + " - Carousel slider menu should be selected";

		Dimension ScreenResolution = eggUIDriver.remoteScreenSize();
		int Screenheight = ScreenResolution.height;
		int Screenwidth = ScreenResolution.width;
		boolean Status = false;

		Rectangle r = new Rectangle();
		r.height= Screenheight*47/100;
		r.width= Screenwidth;
		r.x = 11;
		r.y = Screenheight*53/100;

		try {
			int Count = 0;
			boolean MenuFound = true;
			while(MenuFound && Count<20) {
				eggUIDriver.wait(1);
				if (clickButton(StrcarouselSliderMenuName))
				{
					MenuFound = false;
					Status = true;
				}
				else
				{
					eggUIDriver.swipeUp(ScrollingLocationImage);
					Count = Count + 1;
				}
			}
		}

		catch (Exception e)
		{
			System.out.println(e.getMessage());
			return false;
		}
		if (Status)
		{
			HtmlResult.passed(strStepDesc , strExpected, "Carousel slider menu selected successfully");
			return true;
		}
		else
		{
			HtmlResult.failed(strStepDesc, strExpected, "Carousel slider menu is not selected");
			RecoveryNGK_OrderConfirmation();
			RecoveryNGK_TimeOut();
			return false;
		}
	}

	/*

	Action: Order Completion

	Inputs: orderCompletionStatus

	Description: To select order - Complete the order or cancel the order

	 */

	public void EnterHSAPVoucherCode(Map<String,String> input)

	{
		String strDescription = "Enter HSAP Code";
		String strExpectedResult = "HSAP code should be entered in system";
		try
		{
			String StringVoucherCode = input.get("hsapVoucherCode");
			if (StringVoucherCode.length() > 0)
			{
				int VoucherLength=StringVoucherCode.length();
				for (int i = 0;i < VoucherLength; i++){
					System.out.println(StringVoucherCode.charAt(i));
					char CurrentNumberChar = StringVoucherCode.charAt(i);
					String CurrentNumber = String.valueOf(CurrentNumberChar);
					clickButtonAddToReport(CurrentNumber);
				}
			}
			else
			{
				HtmlResult.failed(strDescription,strExpectedResult,StringVoucherCode + " is invalid code for HSAP");
			}
		}
		catch (Exception e)
		{
			HtmlResult.failed(strDescription, strExpectedResult, e.getMessage());
		}
	}

	
	
	
	
	
	
	
	/*

	Action: Product Size Selection

	Inputs: productSizeSelection

	Description: To Select Meal Size (Large , Medium and al-carte or Different type size eg Small , Medium and Large)

	 */
	public void verifyFinalOrderValidation(Map<String,String> input)
	{
		String ExpectedSalesPanelsData=input.get("expectedProductsData");
		String VerificationFlag=input.get("verificationFlag");
		String StrNGKlanguage= input.get("StrNGKlanguage");
		String strStepDesc="To verify sales panel";
		String strExpected="Ngk Order confirmation window should contain expected data";
		
		eggUIDriver.clickText("View my order");
		String SalesPanelActualData=finalOrderValidation(StrNGKlanguage);
		String ActualSalesPanelData=SalesPanelActualData.replaceAll("\n\n", "\n");
		SalesPanelActualData=SalesPanelActualData.replace("\n", ",");
		ActualSalesPanelData=ActualSalesPanelData.replace("\t"," ");
		String[] ExpectedSalesPanelData= ExpectedSalesPanelsData.split("#");
		if (VerificationFlag.equalsIgnoreCase("YES"))
		{
			String Actual=" Actual sales panel data is-";
			String Expected=" Expected Data is-";
			for(String ExpectedData:ExpectedSalesPanelData)
			{
				ExpectedData=ExpectedData.trim();
				if(ActualSalesPanelData.contains(ExpectedData))
				{
					String ActualSalesPanel=Actual+ActualSalesPanelData+"\n";
					String ExpectedSalesPanel =Expected+ExpectedData+"\n";
					HtmlResult.passed(strStepDesc,strExpected,"Verification flag-"+VerificationFlag+"\nActual sales panel data-"+ActualSalesPanel+"\nExpected sales panel data-"+ExpectedSalesPanel+"Hence Passed");
				}
				else
				{
					String ActualSalesPanel=Actual+ActualSalesPanelData+"\n";
					String ExpectedSalesPanel =Expected+ExpectedData+"\n";
					HtmlResult.failed(strStepDesc,strExpected,"Verification flag-"+VerificationFlag+"\nActual sales panel data-"+ActualSalesPanel+"\nExpected sales panel data-"+ExpectedSalesPanel+"Hence Failed");
				}
			}
		}
		else if(VerificationFlag.equalsIgnoreCase("NO"))
		{
			String Actual=" Actual sales panel data is-";
			String Expected=" Expected Data is-";
			for(String ExpectedData:ExpectedSalesPanelData)
			{
				ExpectedData=ExpectedData.trim();
				if(ActualSalesPanelData.contains(ExpectedData))
				{
					String ActualSalesPanel=Actual+ActualSalesPanelData+"\n";
					String ExpectedSalesPanel =Expected+ExpectedData+"\n";
					HtmlResult.failed(strStepDesc,strExpected,"Verification flag-"+VerificationFlag+"\nActual sales panel data-"+ActualSalesPanel+"\nExpected sales panel data-"+ExpectedSalesPanel+"Hence Failed");
				}
				else
				{
					String ActualSalesPanel=Actual+ActualSalesPanelData+"\n";
					String ExpectedSalesPanel =Expected+ExpectedData+"\n";
					HtmlResult.passed(strStepDesc,strExpected,"Verification flag-"+VerificationFlag+"\nActual sales panel data-"+ActualSalesPanel+"\nExpected sales panel data-"+ExpectedSalesPanel+"Hence Passed");
				}
			}
		}
		else
		{
			strExpected="Should be able to verify Sales Panel";
			HtmlResult.failed(strStepDesc,strExpected,"Wrong value passed from designer-"+VerificationFlag+"- should be Yes or no only");
			RecoveryNGK_TimeOut();
		}
	}
	
	public void verifyOrder(Map<String,String> input)
	{
		String ExpectedSalesPanelsData=input.get("expectedProductsData");
		String VerificationFlag=input.get("verificationFlag");
		String strStepDesc="To verify sales panel";
		String strExpected="Ngk Order confirmation window should contain expected data";
		eggUIDriver.wait(2);
		
		if(eggUIDriver.clickText("View my order"))
		{

			List<Point> LocationsOfPoundSign = eggUIDriver.everyTextLocation("�");

			if(LocationsOfPoundSign!=null && LocationsOfPoundSign.size()==1)
			{
				HtmlResult.passed(strStepDesc,strExpected,"Verification flag-"+VerificationFlag+" and � is present on screen");
			}
			else if(LocationsOfPoundSign==null || LocationsOfPoundSign.size()==0)
			{
				HtmlResult.failed(strStepDesc,strExpected,"Verification flag-"+VerificationFlag+" and � is not present on screen");
			}
			else 
			{
				boolean NotFound = false;
				for(Point Location : LocationsOfPoundSign)
				{
					 NotFound = false;
					String TextAtLocation = eggUIDriver.readText(Location);
					if(TextAtLocation.equals("�") || TextAtLocation.contains("�"))
					{
						HtmlResult.passed(strStepDesc,strExpected,"Verification flag-"+VerificationFlag+" and � is present on screen");
						break;
					}
					else
					{
						NotFound = true;
					}
					
					eggUIDriver.clickPoint(new Point(10,10));
				}
				
				if(NotFound)
				{
					HtmlResult.failed(strStepDesc,strExpected,"Verification flag-"+VerificationFlag+" and � is not present on screen");
				}
			}
			
			//# will be useful in case of meal data verification
			String[] MainOrder = ExpectedSalesPanelsData.split("#");
			for(String WholeOrder : MainOrder)
			{
				String[] ExpectedOrders = WholeOrder.split(" or ");
				String TextFoundOnScreen = "";
				String TextNotFoundOnScreen = "";
				if (VerificationFlag.equalsIgnoreCase("YES"))
				{
					eggUIDriver.wait(2);
					for(String ExpectedOrder : ExpectedOrders)
					{
						String[] ModifiedExpectedOrder = ExpectedOrder.split("&");
						for(String ExpectedOrderOnScreen : ModifiedExpectedOrder)
						{
							List<Point> Locations = eggUIDriver.getEveryTextLocation(ExpectedOrderOnScreen);
							if(Locations!=null && Locations.size()==1)
							{
								TextFoundOnScreen = TextFoundOnScreen.concat(ExpectedOrder+",");
							}
							else if(Locations!=null && Locations.size()>1)
							{
								for(Point Location : Locations)
								{
									String TextAtLocation = eggUIDriver.readText(Location);
									if(TextAtLocation.equals(ExpectedOrder) || TextAtLocation.contains(ExpectedOrder))
									{
										TextFoundOnScreen = TextFoundOnScreen.concat(ExpectedOrder+",");
										break;
									}
									
								}
								if(TextFoundOnScreen.length()==0)
								{
									TextNotFoundOnScreen = TextNotFoundOnScreen.concat(ExpectedOrder+",");
								}
							}
							else
							{
								TextNotFoundOnScreen = TextNotFoundOnScreen.concat(ExpectedOrder+",");
							}
							
							eggUIDriver.clickPoint(new Point(10,10));
						}
					}
					
					if(TextNotFoundOnScreen.length()>=3)
					{
						HtmlResult.failed(strStepDesc,strExpected,"Verification flag-"+VerificationFlag+", Orders-"+TextNotFoundOnScreen+" are not present on screen");
					}
					if(TextFoundOnScreen.length()>=3)
					{
						HtmlResult.passed(strStepDesc,strExpected,"Verification flag-"+VerificationFlag+", Orders-"+TextFoundOnScreen+" are present on screen");
					}
				}
				else if(VerificationFlag.equalsIgnoreCase("NO"))
				{
					for(String ExpectedOrder : ExpectedOrders)
					{
						List<Point> Locations = eggUIDriver.getEveryTextLocation(ExpectedOrder);
						if(Locations!=null && Locations.size()==1)
						{
							TextFoundOnScreen = TextFoundOnScreen.concat(ExpectedOrder+",");
						}
						else if(Locations!=null && Locations.size()>1)
						{
							for(Point Location : Locations)
							{
								String TextAtLocation = eggUIDriver.readText(Location);
								if(TextAtLocation.equals(ExpectedOrder) || TextAtLocation.contains(ExpectedOrder))
								{
									TextFoundOnScreen = TextFoundOnScreen.concat(ExpectedOrder+",");
									break;
								}
								
								eggUIDriver.clickPoint(new Point(10,10));
							}
							if(TextFoundOnScreen.length()==0)
							{
								TextNotFoundOnScreen = TextNotFoundOnScreen.concat(ExpectedOrder+",");
							}
						}
						else
						{
							TextNotFoundOnScreen = TextNotFoundOnScreen.concat(ExpectedOrder+",");
						}
					}
					
					if(TextNotFoundOnScreen.length()>=3)
					{
						HtmlResult.passed(strStepDesc,strExpected,"Verification flag-"+VerificationFlag+", Orders-"+TextNotFoundOnScreen+" are not present on screen");
					}
					if(TextFoundOnScreen.length()>=3)
					{
						HtmlResult.failed(strStepDesc,strExpected,"Verification flag-"+VerificationFlag+", Orders-"+TextNotFoundOnScreen+" are present on screen");
					}	
				}
				else
				{
					strExpected="Should be able to verify Sales Panel";
					HtmlResult.failed(strStepDesc,strExpected,"Wrong value passed from designer-"+VerificationFlag+"- should be Yes or no only");
					RecoveryNGK_TimeOut();
				}
			}
		}
		else
		{
			HtmlResult.failed(strStepDesc,strExpected,"View my order button is not clickable");
		}
	}

	public String finalOrderValidation(String  StrNGKlanguage) {
		String OrderBasketDataDetails = null;
		try
		{
			eggUIDriver.wait(2);
			Dimension ScreenResolution = eggUIDriver.remoteScreenSize();
			int Screenheight = ScreenResolution.height;
			int Screenwidth = ScreenResolution.width;
		
			Rectangle r = new Rectangle();
			r.height= Screenheight-44; //Updated by Prateek (As per 5.16)
			r.width= Screenwidth-110;
			r.x = 58;
			r.y = 45;
			OrderBasketDataDetails = eggUIDriver.Readtext(r, "Language:\""+StrNGKlanguage+"\"");
			OrderBasketDataDetails = OrderBasketDataDetails.replaceAll("_|\"|\\*|\\#|\\%|\\>|\\$|\\\\N|\\?|\\?|\\?|", "").trim();

			OrderBasketDataDetails = OrderBasketDataDetails.replaceAll("\\\\r|\\{|\\}", "").replaceAll("w/", "").trim();//.replaceAll("\\\\|/|\\", " ").trim();
		
			OrderBasketDataDetails=OrderBasketDataDetails.replaceAll("\t","\n");
			
		}

		catch (Exception e) {
			HtmlResult.failed("Reading Sales Panel data","Function should return Sales Panel Data","Unable to read Verify Sales Panel due to this -"  + e.getMessage());
		}
		return OrderBasketDataDetails;
	}

	public String getScreenData(String  StrNGKlanguage) {
		String OrderBasketDataDetails = null;
		try
		{
			eggUIDriver.wait(2);
			Dimension ScreenResolution = eggUIDriver.remoteScreenSize();
			int Screenheight = ScreenResolution.height;
			int Screenwidth = ScreenResolution.width;
		
			Rectangle r = new Rectangle();
			r.height= Screenheight-44; //Updated by Prateek (As per 5.16)
			r.width= Screenwidth-110;
			r.x = 58;
			r.y = 45;
			OrderBasketDataDetails = eggUIDriver.Readtext(r, "Language:\""+StrNGKlanguage+"\"");
			OrderBasketDataDetails = OrderBasketDataDetails.replaceAll("_|\"|\\*|\\#|\\%|\\>|\\$|\\\\N|\\?|\\?|\\?|", "").trim();

			OrderBasketDataDetails = OrderBasketDataDetails.replaceAll("\\\\r|\\{|\\}", "").replaceAll("w/", "").trim();//.replaceAll("\\\\|/|\\", " ").trim();
		
			OrderBasketDataDetails=OrderBasketDataDetails.replaceAll("\t","\n");
			
		}

		catch (Exception e) {
			HtmlResult.failed("Reading Sales Panel data","Function should return Sales Panel Data","Unable to read Verify Sales Panel due to this -"  + e.getMessage());
		}
		return OrderBasketDataDetails;
	}


	//****************************************************************************************************
	// Product Selection for NGK HSAP Voucher
/*
	public void hSAPProductSelection(Map<String,String> input)
	{

		String strStepDesc="To verify sales panel";
		String strExpected="HSAP Product should be selected";
		boolean FoundAndClicked =false;
		try
		{
			String HSAP_Product_Name = input.get("productSelection").trim();
			String ExpectedOrderBasketPanelData =input.get("expectedOrderBasketPanelData").trim();

			if (HSAP_Product_Name.equalsIgnoreCase("NO") || HSAP_Product_Name.isEmpty())
			{
				HtmlResult.passed(strStepDesc,strExpected,"For this HSAP vocher, No need to product selection");
			}
			else
			{ 
				eggUIDriver.wait(2);
				boolean ValidateScreenForPoundSign = false;
				boolean OrderVerification  = false;
				if (!(HSAP_Product_Name.equalsIgnoreCase("NO") || HSAP_Product_Name.isEmpty()))
				{
					String[] strButtonList = HSAP_Product_Name.split("#");
					for (String Button:strButtonList) {
						Button = Button.trim();
						FoundAndClicked=clickButton(Button);
						if (FoundAndClicked) {
							HtmlResult.passed(strStepDesc,strExpected,"For this HSAP vocher, We need to select - "+ Button);
							ValidateScreenForPoundSign = true;
							OrderVerification = true;
						}
						else
						{
							HtmlResult.failed(strStepDesc,strExpected,"Unable to select product - "+ Button);
						}
					}
				}
				
				if(ValidateScreenForPoundSign)
				{
					List<Point> LocationOf� = eggUIDriver.getEveryTextLocation("�");
					if(LocationOf�==null || LocationOf�.size()<=0)
					{
						HtmlResult.failed(strStepDesc,strExpected,"� sign is not found on screen");
					}
					else if(LocationOf�.size()==1)
					{
						HtmlResult.passed(strStepDesc,strExpected,"� sign is found on screen");
					}
					else
					{
						boolean SignFound = false;
						for(Point Location: LocationOf�)
						{
							String TextAtLocation = eggUIDriver.readText(Location);
							if(TextAtLocation.contains("�"))
							{
								SignFound = true;
								HtmlResult.passed(strStepDesc,strExpected,"� sign is found on screen");
								break;
							}
							else
							{
								SignFound = false;
							}
						}
						
						if(!SignFound)
						{
							HtmlResult.failed(strStepDesc,strExpected,"� sign is not found on screen");
						}
					}
				}
				
				if(OrderVerification)
				{
					String OrdersFound = "";
					String OrdersNotFound = "";
					String[] Orders = ExpectedOrderBasketPanelData.split("or");
					for(String Order :  Orders)
					{
						String []ExpectedOrders = Order.split("&");
						for(String ExpectedOrder : ExpectedOrders)
						{
							List<Point> LocationOfOrder = eggUIDriver.getEveryTextLocation(ExpectedOrder);
							if(LocationOfOrder==null || LocationOfOrder.size()<=0)
							{
								OrdersNotFound = OrdersNotFound.concat(ExpectedOrder+",");
							}
							else if(LocationOfOrder.size()==1)
							{
								OrdersFound = OrdersFound.concat(ExpectedOrder+",");
							}
							else
							{
								boolean OrderNotFound = false;
								for(Point Location : LocationOfOrder)
								{
									String TextAtLocation = eggUIDriver.readText(Location);
									if(TextAtLocation.trim().contains(Order.trim()))
									{
										OrderNotFound = false;
										OrdersFound = OrdersFound.concat(Order+",");
										break;
									}
									else
									{
										OrderNotFound=true;
									}
								}
								
								if(OrderNotFound)
								{
									OrdersFound = OrdersFound.concat(Order+",");
								}
							}
						}
					}
					if(OrdersFound.length()>3)
					{
						HtmlResult.passed(strStepDesc, strExpected, "Order is present on screen-'"+OrdersFound+"'");
					}
					if(OrdersNotFound.length()>3)
					{
						HtmlResult.failed(strStepDesc, strExpected, "Order is not present on screen-'"+OrdersNotFound+"'");
					}
					
				}
			}
		}
		catch (Exception e)
		{
			HtmlResult.failed(strStepDesc,strExpected, e.getMessage());
		}
	}*/

	//****************************Verify Order Basket Panel*****************************************************

	/*
	 * ****************************************************************
	 *  Function - verifyOrderBasketPanel()
	 *  Use - Read Order basket data  and compare with Expected data
	 *  Date - 07/09/2016
	 *  Application - NGK
	 ***************************************************************
	 **/

	public void verifyOrderBasketPanel(Map<String,String> input)
	{
		String ExpectedSalesPanelsData=input.get("expectedOrderBasketPanelData");
		String VerificationFlag=input.get("verificationFlag");
		String  StrNGKlanguage = input.get("StrNGKlanguage");
		String strStepDesc="To verify sales panel";
		String strExpected="Ngk Order Basket Panel should contain expected data";

		String SalesPanelActualData=ReadOrderBasketPanel(StrNGKlanguage);
		String ActualSalesPanelData=SalesPanelActualData.replaceAll("\n\n", "\n");
		SalesPanelActualData=SalesPanelActualData.replace("\n", " ");
		ActualSalesPanelData=ActualSalesPanelData.replace("\t"," ");
		SalesPanelActualData=SalesPanelActualData.replace("\n", " ");
		String[] ExpectedSalesPanelData= ExpectedSalesPanelsData.split("#");

		if (VerificationFlag.equalsIgnoreCase("YES"))
		{
			String Actual=" Actual sales panel data is-";
			String Expected=" Expected Data is-";
			for(String ExpectedData:ExpectedSalesPanelData)
			{
				ExpectedData=ExpectedData.trim();
				if(SalesPanelActualData.contains(ExpectedData))
				{
					String ActualSalesPanel=Actual+ActualSalesPanelData+"\n";
					String ExpectedSalesPanel =Expected+ExpectedData+"\n";
					HtmlResult.passed(strStepDesc,strExpected,"Verification flag-"+VerificationFlag+"\nActual sales panel data-"+ActualSalesPanel+"\nExpected sales panel data-"+ExpectedSalesPanel+"Hence Passed");
				}
				else
				{
					String ActualSalesPanel=Actual+ActualSalesPanelData+"\n";
					String ExpectedSalesPanel =Expected+ExpectedData+"\n";
					HtmlResult.failed(strStepDesc,strExpected,"Verification flag-"+VerificationFlag+"\nActual sales panel data-"+ActualSalesPanel+"\nExpected sales panel data-"+ExpectedSalesPanel+"Hence Failed");
				}
			}
		}
		else if(VerificationFlag.equalsIgnoreCase("NO"))
		{
			String Actual=" Actual sales panel data is-";
			String Expected=" Expected Data is-";
			for(String ExpectedData:ExpectedSalesPanelData)
			{
				ExpectedData=ExpectedData.trim();
				if(ActualSalesPanelData.contains(ExpectedData))
				{
					String ActualSalesPanel=Actual+ActualSalesPanelData+"\n";
					String ExpectedSalesPanel =Expected+ExpectedData+"\n";
					HtmlResult.failed(strStepDesc,strExpected,"Verification flag-"+VerificationFlag+"\nActual sales panel data-"+ActualSalesPanel+"\nExpected sales panel data-"+ExpectedSalesPanel+"Hence Failed");
				}
				else
				{
					String ActualSalesPanel=Actual+ActualSalesPanelData+"\n";
					String ExpectedSalesPanel =Expected+ExpectedData+"\n";
					HtmlResult.passed(strStepDesc,strExpected,"Verification flag-"+VerificationFlag+"\nActual sales panel data-"+ActualSalesPanel+"\nExpected sales panel data-"+ExpectedSalesPanel+"Hence Passed");
				}
			}
		}
		else
		{
			strExpected="Should be able to verify Sales Panel";
			HtmlResult.failed(strStepDesc,strExpected,"Wrong value passed from designer-"+VerificationFlag+"- should be Yes or no only");
		}
	}
	
	public void addToOrder()
	{
		try
		{
			eggUIDriver.wait(2);
			if(clickButton("ngk_addToOrder"))
			{
				HtmlResult.passed("To click Add To Order button", "Add To Order button should be clicked", "Add To Order button clicked successfully");
			}
			else
			{
				HtmlResult.failed("To click Add To Order button", "Add To Order button should be clicked", "Add To Order button not clicked successfully");
			}
		}
		catch(Exception e)
		{
			HtmlResult.failed("To click Add To Order button", "Add To Order button should be clicked", "Add To Order button not clicked successfully");
		}
	}
	

	
	public void performReviewOrderUiValidation(Map<String,String> Input)
	{
		String strDescription = "To validate UI of NGK";
		String strExpectedResult = "UI validation should be successful";
		String OrderTypeTextsOnScreen = Input.get("OrderTypeTextOnScreen").trim();
		String OrderType = Input.get("OrderType").trim();
		
		try{
			
			// find text Total � on screen
			if(eggUIDriver.imageFound("ngk_totalPoundsign"))
			{
				HtmlResult.passed(strDescription, strExpectedResult, "Total and pound sign is present on screen");
			}
			else
			{
				HtmlResult.failed(strDescription, strExpectedResult, "Total and pound sign is not present on screen");
			}
			
			// find mc'donalds logo
			if(checkMcdLogo())
			{
				HtmlResult.passed(strDescription, strExpectedResult, "Mc'Donalds logo is present on screen");
			}
			else
			{
				HtmlResult.failed(strDescription, strExpectedResult, "Mc'Donalds logo is not present on screen");
			}
			
			//order type text validations
			String [] arrOrderTypeTextsOnScreen = OrderTypeTextsOnScreen.split("#");
			String TextToFindOnScreen = "Review my xxxxx order";
			
			try {
				if(OrderType.equalsIgnoreCase("eatin"))
				{
					TextToFindOnScreen = TextToFindOnScreen.replace("xxxxx", arrOrderTypeTextsOnScreen[0].trim());
				}
				else if(OrderType.equalsIgnoreCase("takeaway"))
				{
					TextToFindOnScreen = TextToFindOnScreen.replace("xxxxx", arrOrderTypeTextsOnScreen[1].trim());
				}
			} 
			catch (ArrayIndexOutOfBoundsException e) {
				TextToFindOnScreen = "";
				HtmlResult.failed(strDescription, strExpectedResult, "format should be EatInText#TakeAwayText, wrong data provided-'"+OrderTypeTextsOnScreen+"'");
			}
			
			if(TextToFindOnScreen.length()>2)
			{
				List<Point> TextLocations = eggUIDriver.everyTextLocation(TextToFindOnScreen);
				if(TextLocations!=null)
				{
					if(TextLocations.size()==1)
					{
						HtmlResult.passed(strDescription, strExpectedResult, "Expected text '"+TextToFindOnScreen+"' is present on screen");
					}
					else if(TextLocations.size()>=1)
					{
						boolean TextFound = false;
						for(Point pntTextLocation : TextLocations)
						{
							
							TextFound = false;
							String TextAtLocation = eggUIDriver.readText(pntTextLocation);
							if(TextAtLocation.contains(TextToFindOnScreen))
							{
								TextFound = true;
								HtmlResult.passed(strDescription, strExpectedResult, "Expected text '"+TextToFindOnScreen+"' is present on screen");
								break;
							}
							else
							{
								TextFound = false;
							}
						}
						
						if(!TextFound)
						{
							HtmlResult.failed(strDescription, strExpectedResult, "Expected text '"+TextToFindOnScreen+"' is present on screen");
						}
					}
				}
				else
				{
					HtmlResult.failed(strDescription, strExpectedResult, "Expected text '"+TextToFindOnScreen+"' is not present on screen");
				}
			}
		}
		catch(Exception e)
		{
			HtmlResult.failed(strDescription, strExpectedResult, "Error occured during executing performReviewOrderUiValidation() -'"+e.getMessage()+"','"+e.getCause()+"'");
		}
	}
	
	public void cancelOrder()
	{
		//23645714
				try
				{
					eggUIDriver.wait(2);
					if(clickButton("ngk_cancelOrder"))
					{
						HtmlResult.passed("To click Cancel  button", "Cancel button should be clicked", "Cancel button clicked successfully");
					}
					else
					{
						HtmlResult.failed("To click Cancel  button", "Cancel button should be clicked", "Cancel button not clicked successfully");
					}
				}
				catch(Exception e)
				{
					HtmlResult.failed("To click Cancel  button", "Cancel button should be clicked", "Cancel button not clicked successfully");
				}
	}
	
	// **************************************************************************************
	public void orderConfirmationSelection(Map<String,String> input)
	{
		String Str_orderConfirmationTypeSelection  = input.get("orderConfirmationType").trim();
		String strStepDesc="Order Confirmation ";
		String strExpected=Str_orderConfirmationTypeSelection + " - Order Confirmation should be selected successfully";

		try{
			boolean FoundAndClicked = false;
			if (Str_orderConfirmationTypeSelection.equalsIgnoreCase("Complete Order"))
			{
 				FoundAndClicked =  clickButton("ngk_commonbutton_CompleteOrder");
				if (FoundAndClicked)
				{
					HtmlResult.passed(strStepDesc,strExpected,Str_orderConfirmationTypeSelection + " -  Confirmation selected successfully " );
				}
				else
				{
					HtmlResult.failed(strStepDesc,strExpected,Str_orderConfirmationTypeSelection + " -  Confirmation is not selected");
					//Recall recovery to cancel incomplete order
					RecoveryNGK_OrderConfirmation();
				}
			}
			else if (Str_orderConfirmationTypeSelection.equalsIgnoreCase("Cancel Order"))
			{
				FoundAndClicked =  clickButton("ngk_commonbutton_CancelOrder");
				if (FoundAndClicked)
				{
					FoundAndClicked =  clickButton("ngk_commonbutton_Yes");
					if (FoundAndClicked)
					{
						HtmlResult.passed(strStepDesc,strExpected, Str_orderConfirmationTypeSelection + "Confirmation selected successfully");
					}
					else
					{
						HtmlResult.failed(strStepDesc,strExpected,Str_orderConfirmationTypeSelection + "Confirmation is not selected ");
					}
				}
				else
				{
					HtmlResult.failed(strStepDesc,strExpected,Str_orderConfirmationTypeSelection + "Confirmation is not selected ");
					RecoveryNGK_TimeOut();
				}
			}
			else
			{
				HtmlResult.failed(strStepDesc,strExpected, "Confirmation type -  " + Str_orderConfirmationTypeSelection + " is worng selected . Valid options are (Complete Order /Cancel Order) for Order Confirmation");
				RecoveryNGK_TimeOut();
			}
		}

		catch (Exception e)
		{
			HtmlResult.failed(strStepDesc,strExpected,e.getMessage());
			RecoveryNGK_TimeOut();
		}

		eggUIDriver.wait(5);
	}


	/*
	 * ****************************************************************
	 *  Function - ReadOrderBasketPanel()
	 *  Use - Read Order basket data
	 *  Date - 07/09/2016
	 *  Application - NGK
	 ***************************************************************
	 */


	public String ReadOrderBasketPanel(String StrNGKlanguage)
	{

		eggUIDriver.wait(6);// temp solution
		String SalesPanelDetails = "";
		try {
			// Defining Rectangle to read the Sales Panel
			Rectangle r = new Rectangle();

			ArrayList<Integer> img1 = eggUIDriver.ImageLocation("ngk_LeftTopCorner");
			int x1 = img1.get(0);
			int y1 = img1.get(1);

			ArrayList<Integer> img2 = eggUIDriver.ImageLocation("ngk_RightBottomCorner");

			int x2 = img2.get(0);
			int y2 = img2.get(1);
			System.out.println(x1 + "*****" + y1 + "*****" + x2 + "*****" + y2);

			r.x = x1;
			r.y = y1;
			r.width = x2 - x1;
			r.height = y2 - y1;

			//SalesPanelDetails = eggUIDriver.readText(r);// read data from cordinates referenced by object r

			SalesPanelDetails = eggUIDriver.Readtext(r, "Language:\""+StrNGKlanguage+"\"");

			//System.out.println(SalesPanelDetails);
			SalesPanelDetails = SalesPanelDetails.replaceAll("_|\"|\\*|\\#|\\%|\\>|\\$|\\\\N|\\?|\\?|\\?|", "").trim();
			//SalesPanelDetails = SalesPanelDetails.replaceAll("_|\"|\\*|\\#|\\%|\\>|\\$|\\\\N|\\-|\\?|\\(|\\)|", "").replaceAll("�", "").trim();
			//System.out.println(SalesPanelDetails);
			SalesPanelDetails = SalesPanelDetails.replaceAll("\\\\r|\\{|\\}", "").replaceAll("w/", "").trim();//.replaceAll("\\\\|/|\\", " ").trim();
			//System.out.println(SalesPanelDetails);
			//SalesPanelDetails=SalesPanelDetails.replaceAll("\n", " ").trim();
			SalesPanelDetails=SalesPanelDetails.replaceAll("\t","\n");
			SalesPanelDetails=SalesPanelDetails.replaceAll("�[0-9]{1,}.[0-9]{1,}","").replaceAll("�[ ][0-9]{1,}.[0-9]{1,}","");

		} catch (Exception e) {
			HtmlResult.failed("Reading Sales Panel data","Function should return Sales Panel Data","Unable to read Verify Sales Panel due to this -"  + e.getMessage());
		}
		return SalesPanelDetails;
	}

	public void verifyMealOrder(Map<String,String> Input)
	{
		String strDescription = "To verify ordered meal";
		String strExpectedResult = "Order verification should be successful";
		try
		{
			//local variable
			String Category = Input.get("Category").trim();
			String Language = Input.get("Language").trim();
			String ProductName = Input.get("ProductName").trim();
			String strActualSideProductName="Not as expected";
			String strActualDrinkProductName="Not as expected";
			String SideItemSheetName = "";
			String DrinkSheetName = "";
			
			//Lists of sides
			List<String> arrSideItems = new ArrayList<String>();
			List<String> arrDrinkItems = new ArrayList<String>();
			
			if(Category.equalsIgnoreCase("large"))
			{
				SideItemSheetName = "LunchLargeMealSideItems";
				DrinkSheetName = "LunchLargeMealDrinks";
				
				String sideItemButtonNames = getExcelProductCombination("NGKCLearChoiceData",SideItemSheetName);
				String[] arrLocalSideItems=sideItemButtonNames.trim().split(",");
				arrSideItems = new ArrayList<String>(Arrays.asList(arrLocalSideItems));
				
				String strDrinkButtonNames =getExcelProductCombination("NGKCLearChoiceData",DrinkSheetName);
				String[] arrLocalDrinkItems=strDrinkButtonNames.split(",");
				arrDrinkItems = new ArrayList<String>(Arrays.asList(arrLocalDrinkItems));
			}
		
			else if(Category.equalsIgnoreCase("medium"))
			{
				SideItemSheetName = "LunchMedMealSideItems";
				DrinkSheetName = "LunchMedMealDrinks";
				
				String sideItemButtonNames = getExcelProductCombination("NGKCLearChoiceData",SideItemSheetName);
				String[] arrLocalSideItems=sideItemButtonNames.trim().split(",");
				arrSideItems = new ArrayList<String>(Arrays.asList(arrLocalSideItems));
				
				String strDrinkButtonNames =getExcelProductCombination("NGKCLearChoiceData",DrinkSheetName);
				String[] arrLocalDrinkItems=strDrinkButtonNames.split(",");
				arrDrinkItems = new ArrayList<String>(Arrays.asList(arrLocalDrinkItems));
			}
			else if(Category.equalsIgnoreCase("happy meal"))
			{
				SideItemSheetName = "LunchHappyMealSideItems";
				DrinkSheetName = "LunchHappyMealDrinks";
				
				String sideItemButtonNames = getExcelProductCombination("NGKCLearChoiceData",SideItemSheetName);
				String[] arrLocalSideItems=sideItemButtonNames.trim().split(",");
				arrSideItems = new ArrayList<String>(Arrays.asList(arrLocalSideItems));
				
				String strDrinkButtonNames =getExcelProductCombination("NGKCLearChoiceData",DrinkSheetName);
				String[] arrLocalDrinkItems=strDrinkButtonNames.split(",");
				arrDrinkItems = new ArrayList<String>(Arrays.asList(arrLocalDrinkItems));
			}
			else if(Category.equalsIgnoreCase("breakfast"))
			{
				SideItemSheetName = "BreakfastMealSideItems";
				DrinkSheetName = "BreakfastMealDrinks";
				
				String sideItemButtonNames = getExcelProductCombination("NGKCLearChoiceData",SideItemSheetName);
				String[] arrLocalSideItems=sideItemButtonNames.trim().split(",");
				arrSideItems = new ArrayList<String>(Arrays.asList(arrLocalSideItems));
				
				String strDrinkButtonNames =getExcelProductCombination("NGKCLearChoiceData",DrinkSheetName);
				String[] arrLocalDrinkItems=strDrinkButtonNames.split(",");
				arrDrinkItems = new ArrayList<String>(Arrays.asList(arrLocalDrinkItems));
			}
			else if(Category.equalsIgnoreCase("Large wraps"))
			{
				SideItemSheetName = "LunchLargeMealSideItems";
				DrinkSheetName = "LunchLargeMealDrinks";
				
				String sideItemButtonNames = getExcelProductCombination("NGKCLearChoiceData",SideItemSheetName);
				String[] arrLocalSideItems=sideItemButtonNames.trim().split(",");
				arrSideItems = new ArrayList<String>(Arrays.asList(arrLocalSideItems));
				
				String strDrinkButtonNames =getExcelProductCombination("NGKCLearChoiceData",DrinkSheetName);
				String[] arrLocalDrinkItems=strDrinkButtonNames.split(",");
				arrDrinkItems = new ArrayList<String>(Arrays.asList(arrLocalDrinkItems));
			}
			else if(Category.equalsIgnoreCase("Medium wraps"))
			{
				SideItemSheetName = "LunchMedMealSideItems";
				DrinkSheetName = "LunchMedMealDrinks";
				
				String sideItemButtonNames = getExcelProductCombination("NGKCLearChoiceData",SideItemSheetName);
				String[] arrLocalSideItems=sideItemButtonNames.trim().split(",");
				arrSideItems = new ArrayList<String>(Arrays.asList(arrLocalSideItems));
				
				String strDrinkButtonNames =getExcelProductCombination("NGKCLearChoiceData",DrinkSheetName);
				String[] arrLocalDrinkItems=strDrinkButtonNames.split(",");
				arrDrinkItems = new ArrayList<String>(Arrays.asList(arrLocalDrinkItems));
			}
			else
			{
				HtmlResult.failed("To perform clear choice on NGK", "Clear choice functinality should be executed successfully", "Wrong menu name passed from Parameter-'Category', valid values are -'wraps,breakfast,happy meal,medium,large'");
			}
			
			//for loop for side items
			if(arrSideItems.size()>0 && arrDrinkItems.size()>0)
			{
				for(String SideItemDetails : arrSideItems)
				{
					String[] arrSideItemDetails = SideItemDetails.split("\\|");
					String SideItemName = arrSideItemDetails[0];
					
					for(String DrinkItemDetails : arrDrinkItems)
					{
						String[] arrDrinkItemDetails = DrinkItemDetails.split("\\|");
						String DrinkItemName = arrDrinkItemDetails[0];
						HtmlResult.addMessage("Performing meal order verification with products  - ' "+ProductName+" with "+DrinkItemName+"', and ' "+SideItemName+" '");
						
						if(placeMealOrder(DrinkItemName,SideItemName,strActualDrinkProductName,strActualSideProductName))
						{
							// text verifications
							String strSideItemName = getValueFromClearChoiceCombinationFile(SideItemName,SideItemSheetName,Language);
							String strDrinkItemName = getValueFromClearChoiceCombinationFile(DrinkItemName,DrinkSheetName,Language);
							
							//setting area to search text
							Rectangle OrderInfoPart = new Rectangle(96,475,897,207);
							eggUIDriver.setSearchRectangle(OrderInfoPart);
							
							//for side item text validation
							eggUIDriver.wait(2);
							List<Point> TextLocations = eggUIDriver.everyTextLocation(strSideItemName.trim());
							if(TextLocations!=null)
							{
								if(TextLocations.size()==1)
								{
									HtmlResult.passed("To verify side item name on screen", "Side item name '"+strSideItemName+"' should be present on screen", "Name '"+strSideItemName+"' is present on Screen");
								}
								else if(TextLocations.size()>1)
								{
									boolean TextFound = false;
									for(Point TextLocation : TextLocations)
									{
										String TextAtLocation = eggUIDriver.readText(TextLocation);
										if(TextAtLocation!=null && TextAtLocation.equals(strSideItemName))
										{
											TextFound = true;
											HtmlResult.passed("To verify side item name on screen", "Side item name '"+strSideItemName+"' should be present on screen", "Name '"+strSideItemName+"' is present on Screen");
											break;
										}
									}
									
									if(!TextFound)
									{
										HtmlResult.failed("To verify side item name on screen", "Side item name '"+strSideItemName+"' should be present on screen", "Name '"+strSideItemName+"' is not present on Screen" );	
									}
								}
								else
								{
									HtmlResult.failed("To verify side item name on screen", "Side item name '"+strSideItemName+"' should be present on screen", "Name '"+strSideItemName+"' is not present on Screen" );	
								}
							}
							else
							{
								HtmlResult.failed("To verify side item name on screen", "Side item name '"+strSideItemName+"' should be present on screen", "Name '"+strSideItemName+"' is not present on Screen" );
							}
						
							//for drink name validation
							TextLocations = eggUIDriver.everyTextLocation(strDrinkItemName.trim());
							if(TextLocations!=null)
							{
								if(TextLocations.size()==1)
								{
									HtmlResult.passed("To verify drink item name on screen", "Drink item name '"+strDrinkItemName+"' should be present on screen", "Name '"+strDrinkItemName+"' is present on Screen");
								}
								else if(TextLocations.size()>1)
								{
									boolean TextFound = false;
									for(Point TextLocation : TextLocations)
									{
										String TextAtLocation = eggUIDriver.readText(TextLocation);
										if(TextAtLocation!=null && TextAtLocation.equals(strDrinkItemName))
										{
											TextFound = true;
											HtmlResult.passed("To verify drink item name on screen", "Drink item name '"+strDrinkItemName+"' should be present on screen", "Name '"+strDrinkItemName+"' is present on Screen");
											break;
										}
									}
									
									if(!TextFound)
									{
										HtmlResult.failed("To verify drink item name on screen", "Drink item name '"+strDrinkItemName+"' should be present on screen", "Name '"+strDrinkItemName+"' is not present on Screen" );	
									}
								}
								else
								{
									HtmlResult.failed("To verify drink item name on screen", "Drink item name '"+strDrinkItemName+"' should be present on screen", "Name '"+strDrinkItemName+"' is not present on Screen" );	
								}
							}
							else
							{
								HtmlResult.failed("To verify drink item name on screen", "Drink item name '"+strDrinkItemName+"' should be present on screen", "Name '"+strDrinkItemName+"' is not present on Screen" );
							}
							
							eggUIDriver.setSearchRectangle(new Rectangle(eggUIDriver.remoteScreenSize()));
						}
						else
						{
							//checking if bakc button is present on screen or not
							if(clickButton("back_button"))
							{
								HtmlResult.failed("To cancel order", "Back button should be clicked ", "NGK not responded within execution time");
							}

							//to cancel order
							if (clickButton("ngk_commonbutton_CancelOrder"))
							{
								HtmlResult.passed("To cancel order","Order should be cancelled", "Order cancellation successful");
							}
							else
							{
								HtmlResult.failed("To cancel order","Order should be cancelled","Cancel order button not found");
								RecoveryNGK_TimeOut();
							}
						}	
					}
				}
			}
		}
		catch(Exception e)
		{
			HtmlResult.failed(strDescription, strExpectedResult, "Error while performing verifyMealOrder() -'"+e.getMessage()+",'"+e.getCause()+"'");
		}
		
	}
	

	
	
	
	/***********clear choice
	 * @throws BiffException
	 *
	 *
	 */

	public void nGKClearChoice(Map<String,String> Input)
	{
		try
		{
			//local variable
			String Category = Input.get("Category").trim();
			String Language = Input.get("Language").trim();
			String ProductName = Input.get("productName").trim();
			String strActualSideProductName="Not as expected";
			String strActualDrinkProductName="Not as expected";
			String SideItemSheetName = "";
			String DrinkSheetName = "";
			
			//Lists of sides
			List<String> arrSideItems = new ArrayList<String>();
			List<String> arrDrinkItems = new ArrayList<String>();
			
			if(Category.equalsIgnoreCase("large"))
			{
				SideItemSheetName = "LunchLargeMealSideItems";
				DrinkSheetName = "LunchLargeMealDrinks";
				
				String sideItemButtonNames = getExcelProductCombination("NGKCLearChoiceData",SideItemSheetName);
				String[] arrLocalSideItems=sideItemButtonNames.trim().split(",");
				arrSideItems = new ArrayList<String>(Arrays.asList(arrLocalSideItems));
				
				String strDrinkButtonNames =getExcelProductCombination("NGKCLearChoiceData",DrinkSheetName);
				String[] arrLocalDrinkItems=strDrinkButtonNames.split(",");
				arrDrinkItems = new ArrayList<String>(Arrays.asList(arrLocalDrinkItems));
			}
		
			else if(Category.equalsIgnoreCase("medium"))
			{
				SideItemSheetName = "LunchMedMealSideItems";
				DrinkSheetName = "LunchMedMealDrinks";
				
				String sideItemButtonNames = getExcelProductCombination("NGKCLearChoiceData",SideItemSheetName);
				String[] arrLocalSideItems=sideItemButtonNames.trim().split(",");
				arrSideItems = new ArrayList<String>(Arrays.asList(arrLocalSideItems));
				
				String strDrinkButtonNames =getExcelProductCombination("NGKCLearChoiceData",DrinkSheetName);
				String[] arrLocalDrinkItems=strDrinkButtonNames.split(",");
				arrDrinkItems = new ArrayList<String>(Arrays.asList(arrLocalDrinkItems));
			}
			else if(Category.equalsIgnoreCase("happy meal"))
			{
				SideItemSheetName = "LunchHappyMealSideItems";
				DrinkSheetName = "LunchHappyMealDrinks";
				
				String sideItemButtonNames = getExcelProductCombination("NGKCLearChoiceData",SideItemSheetName);
				String[] arrLocalSideItems=sideItemButtonNames.trim().split(",");
				arrSideItems = new ArrayList<String>(Arrays.asList(arrLocalSideItems));
				
				String strDrinkButtonNames =getExcelProductCombination("NGKCLearChoiceData",DrinkSheetName);
				String[] arrLocalDrinkItems=strDrinkButtonNames.split(",");
				arrDrinkItems = new ArrayList<String>(Arrays.asList(arrLocalDrinkItems));
			}
			else if(Category.equalsIgnoreCase("breakfast"))
			{
				SideItemSheetName = "BreakfastMealSideItems";
				DrinkSheetName = "BreakfastMealDrinks";
				
				String sideItemButtonNames = getExcelProductCombination("NGKCLearChoiceData",SideItemSheetName);
				String[] arrLocalSideItems=sideItemButtonNames.trim().split(",");
				arrSideItems = new ArrayList<String>(Arrays.asList(arrLocalSideItems));
				
				String strDrinkButtonNames =getExcelProductCombination("NGKCLearChoiceData",DrinkSheetName);
				String[] arrLocalDrinkItems=strDrinkButtonNames.split(",");
				arrDrinkItems = new ArrayList<String>(Arrays.asList(arrLocalDrinkItems));
			}
			else if(Category.equalsIgnoreCase("Large wraps"))
			{
				SideItemSheetName = "LunchLargeMealSideItems";
				DrinkSheetName = "LunchLargeMealDrinks";
				
				String sideItemButtonNames = getExcelProductCombination("NGKCLearChoiceData",SideItemSheetName);
				String[] arrLocalSideItems=sideItemButtonNames.trim().split(",");
				arrSideItems = new ArrayList<String>(Arrays.asList(arrLocalSideItems));
				
				String strDrinkButtonNames =getExcelProductCombination("NGKCLearChoiceData",DrinkSheetName);
				String[] arrLocalDrinkItems=strDrinkButtonNames.split(",");
				arrDrinkItems = new ArrayList<String>(Arrays.asList(arrLocalDrinkItems));
			}
			else if(Category.equalsIgnoreCase("Medium wraps"))
			{
				SideItemSheetName = "LunchMedMealSideItems";
				DrinkSheetName = "LunchMedMealDrinks";
				
				String sideItemButtonNames = getExcelProductCombination("NGKCLearChoiceData",SideItemSheetName);
				String[] arrLocalSideItems=sideItemButtonNames.trim().split(",");
				arrSideItems = new ArrayList<String>(Arrays.asList(arrLocalSideItems));
				
				String strDrinkButtonNames =getExcelProductCombination("NGKCLearChoiceData",DrinkSheetName);
				String[] arrLocalDrinkItems=strDrinkButtonNames.split(",");
				arrDrinkItems = new ArrayList<String>(Arrays.asList(arrLocalDrinkItems));
			}
			else
			{
				HtmlResult.failed("To perform clear choice on NGK", "Clear choice functinality should be executed successfully", "Wrong menu name passed from Parameter-'Category', valid values are -'wraps,breakfast,happy meal,medium,large'");
			}
			
			//for loop for side items
			if(arrSideItems.size()>0 && arrDrinkItems.size()>0)
			{
				for(String SideItemDetails : arrSideItems)
				{
					String[] arrSideItemDetails = SideItemDetails.split("\\|");
					String SideItemName = arrSideItemDetails[0];
					
					for(String DrinkItemDetails : arrDrinkItems)
					{
						String[] arrDrinkItemDetails = DrinkItemDetails.split("\\|");
						String DrinkItemName = arrDrinkItemDetails[0];
						HtmlResult.addMessage("Performing change choice with products  - ' "+DrinkItemName+"', and ' "+SideItemName+" '");
						
						if(ProductName.contains("Breakfast_Wraps") || ProductName.contains("PancakesAndSausage"))
						{
							SideItemName="";
						}
						
						if(clearChoiceVerification_New(DrinkItemName,SideItemName,strActualDrinkProductName,strActualSideProductName))
						{
							// text verifications
							String strSideItemName = getValueFromClearChoiceCombinationFile(SideItemName,SideItemSheetName,Language);
							String strDrinkItemName = getValueFromClearChoiceCombinationFile(DrinkItemName,DrinkSheetName,Language);
							
							//setting area to search text
							Rectangle OrderInfoPart = new Rectangle(96,475,897,207);
							eggUIDriver.setSearchRectangle(OrderInfoPart);
							
							//for side item text validation
							List<Point> TextLocations = eggUIDriver.everyTextLocation(strSideItemName.trim());
							if(TextLocations!=null)
							{
								if(TextLocations.size()==1)
								{
									HtmlResult.passed("To verify side item name on screen", "Side item name '"+strSideItemName+"' should be present on screen", "Name '"+strSideItemName+"' is not present on Screen");
								}
								else if(TextLocations.size()>1)
								{
									boolean TextFound = false;
									for(Point TextLocation : TextLocations)
									{
										String TextAtLocation = eggUIDriver.readText(TextLocation);
										if(TextAtLocation!=null && TextAtLocation.equals(strSideItemName))
										{
											TextFound = true;
											HtmlResult.passed("To verify side item name on screen", "Side item name '"+strSideItemName+"' should be present on screen", "Name '"+strSideItemName+"' is not present on Screen");
											break;
										}
									}
									
									if(!TextFound)
									{
										HtmlResult.failed("To verify side item name on screen", "Side item name '"+strSideItemName+"' should be present on screen", "Name '"+strSideItemName+"' is not present on Screen" );	
									}
								}
								else
								{
									HtmlResult.failed("To verify side item name on screen", "Side item name '"+strSideItemName+"' should be present on screen", "Name '"+strSideItemName+"' is not present on Screen" );	
								}
							}
							else
							{
								HtmlResult.failed("To verify side item name on screen", "Side item name '"+strSideItemName+"' should be present on screen", "Name '"+strSideItemName+"' is not present on Screen" );
							}
						
							//for drink name validation
							TextLocations = eggUIDriver.everyTextLocation(strDrinkItemName.trim());
							if(TextLocations!=null)
							{
								if(TextLocations.size()==1)
								{
									HtmlResult.passed("To verify drink item name on screen", "Drink item name '"+strDrinkItemName+"' should be present on screen", "Name '"+strDrinkItemName+"' is not present on Screen");
								}
								else if(TextLocations.size()>1)
								{
									boolean TextFound = false;
									for(Point TextLocation : TextLocations)
									{
										String TextAtLocation = eggUIDriver.readText(TextLocation);
										if(TextAtLocation!=null && TextAtLocation.equals(strDrinkItemName))
										{
											TextFound = true;
											HtmlResult.passed("To verify drink item name on screen", "Drink item name '"+strDrinkItemName+"' should be present on screen", "Name '"+strDrinkItemName+"' is not present on Screen");
											break;
										}
									}
									
									if(!TextFound)
									{
										HtmlResult.failed("To verify drink item name on screen", "Drink item name '"+strDrinkItemName+"' should be present on screen", "Name '"+strDrinkItemName+"' is not present on Screen" );	
									}
								}
								else
								{
									HtmlResult.failed("To verify drink item name on screen", "Drink item name '"+strDrinkItemName+"' should be present on screen", "Name '"+strDrinkItemName+"' is not present on Screen" );	
								}
							}
							else
							{
								HtmlResult.failed("To verify drink item name on screen", "Drink item name '"+strDrinkItemName+"' should be present on screen", "Name '"+strDrinkItemName+"' is not present on Screen" );
							}
							
							eggUIDriver.setSearchRectangle(new Rectangle(eggUIDriver.remoteScreenSize()));
						}
						else
						{
							//checking if bakc button is present on screen or not
							if(clickButton("back_button"))
							{
								HtmlResult.failed("To cancel order", "Back button should be clicked ", "NGK not responded within execution time");
							}
							
							//to cancel order
							if (clickButton("ngk_commonbutton_CancelOrder"))
							{
								HtmlResult.passed("To cancel order","Order should be cancelled", "Order cancellation successful");
							}
							else
							{
								HtmlResult.failed("To cancel order","Order should be cancelled","Cancel order button not found");
								RecoveryNGK_TimeOut();
							}
						}	
						
					}
				}
			}
		}
		catch (Exception e)
		{
			HtmlResult.failed("To perform clear choice", "Clear choice should be suucessful", "Error while performming clear choice-'"+e.getMessage()+"'");
			System.out.println(e.getMessage());
		}
	}
	
	public String getValueFromClearChoiceCombinationFile(String ProductName,String SheetName,String Language)
	{
		try
		{
			String FilePath = System.getProperty("user.dir")+"\\FrameWork\\LookUp\\OR\\NGKCLearChoiceData.xls";
			Workbook workBoook=TestCaseRunner.getWorkbook(FilePath.trim());
			List<Map<String,String>> CombinationData = TestCaseRunner.CreatMapFromSheet(workBoook, SheetName.trim());
			for(Map<String,String> map : CombinationData)
			{
				String ProductImageName = map.get("Product Image Name").trim();
				if(ProductImageName!=null && ProductImageName.equalsIgnoreCase(ProductName))
				{
					String Value = map.get(Language.trim());
					if(Value!=null && !(Value.equals("")))
					{
						return Value;
					}
				}
			}
		}
		catch(Exception e)
		{
			return "";
		}
		
		return "";
	}
	
	public boolean placeMealOrder(String drinkButtonName,String sideButtonName,String strActualDrinkProductName, String strActualSideProductName)
	{
		boolean SideItemValidated = false;
		boolean DrinkItemValidated = false;

		try
		{
			String OrderConfirmation_AddToOrder = getValueFromExcel("OrderConfirmation_AddToOrder");
			
			//Click on Edit Button
			boolean blnfoundAndClicked = false;
			eggUIDriver.wait(2);
			if (clickButton("ngk_ReviewOrder_EditButton") && eggUIDriver.imageFound(OrderConfirmation_AddToOrder))
			{
				HtmlResult.passed("To complete meal order", "Order should be placed successfully", " Edit button is clicked successfully " );
				eggUIDriver.wait(2);

				// Verify if page is present or not
				List<Point> LocationOfChangeChoiceButton = eggUIDriver.everyImageLocation("ngk_ClearchoiceScreen_ClearChoice");

				if(LocationOfChangeChoiceButton != null)
				{
					for(int Index = 0; Index <LocationOfChangeChoiceButton.size();Index++)
					{
						Point ChangeChoiceOccurance = LocationOfChangeChoiceButton.get(Index);
						if(Index == 0)
						{
							eggUIDriver.wait(2);
							if(eggUIDriver.clickPoint(ChangeChoiceOccurance))
							{
								String [] arrsideButtonNames = sideButtonName.split("#");
								boolean ProductFound = false;
								for(String SideItemName : arrsideButtonNames)
								{
									eggUIDriver.wait(2);
									ProductFound = false;
									if(SideItemName!="")
									{
										if(!clickButton(SideItemName))
										{
											HtmlResult.failed("To complete meal order", "Order should be placed successfully", "Side item-'"+SideItemName+"' button is not found on screen");
											
										}
										else
										{
											ProductFound = true;
											HtmlResult.passed("To complete meal order", "Order should be placed successfully", "Side item-'"+SideItemName+"' added to the meal");
										}
									}
									else
									{
										ProductFound = true;
										HtmlResult.warning("To complete meal order", "Order should be placed successfully", "No need to add side item to the meal");
									}
										 
								}

								if(ProductFound)
								{
									SideItemValidated = true;
								}
								else
								{
									return false;
								}
							}
							
							eggUIDriver.wait(2);
							LocationOfChangeChoiceButton = eggUIDriver.everyImageLocation("ngk_ClearchoiceScreen_ClearChoice");
							if(sideButtonName.contains("#"))
							{
								Index++;
							}
						}
						else
						{
							eggUIDriver.wait(2);
							if(eggUIDriver.clickPoint(ChangeChoiceOccurance))
							{
								boolean ProductFound = false;
								String [] arrDrinkItem = drinkButtonName.split("#");
								for(String DrinkItem : arrDrinkItem)
								{
									eggUIDriver.wait(2);
									ProductFound = false;
									if(DrinkItem!="")
									{
										if(clickButton(DrinkItem))
										{
											ProductFound = true;
											HtmlResult.passed("To complete meal order", "Order should be placed successfully", "Drink item-'"+DrinkItem+"' added to the meal");		
										}
										else
										{
											HtmlResult.failed("To complete meal order", "Order should be placed successfully", "Button-'"+DrinkItem+"' not found ");		
										}
									}
									else
									{
										ProductFound = true;
										HtmlResult.warning("To complete meal order", "Order should be placed successfully", "No need to add drink item to the meal");
									}
									
									
								}

								if(ProductFound)
								{
									DrinkItemValidated = true;
								}
								else
								{
									return false;
								}
							}
							else
							{
								HtmlResult.failed("To complete meal order", "Order should be placed successfully", "Change choice button is found at location-( "+ChangeChoiceOccurance.toString()+" ) but its not clickable");		
							}
						}
					}
				}
				else
				{
					HtmlResult.failed("To complete meal order", "Order should be placed successfully", "Change choice button is not available on screen");
				}


				//Click on Add to Order button to complete the Order
				if(SideItemValidated && DrinkItemValidated)
				{
					if (eggUIDriver.imageFound(OrderConfirmation_AddToOrder))
					{
						blnfoundAndClicked = clickButton(OrderConfirmation_AddToOrder);
						if (blnfoundAndClicked)
						{
							HtmlResult.passed("To complete meal order", "Order should be placed successfully", "'Add to Order' button selected successfully");
							
							if(SideItemValidated && DrinkItemValidated)
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
							HtmlResult.failed("To complete meal order", "Order should be placed successfully", "'Add to Order' button is not selected ");
							return false;
						}
					}
					else
					{
						HtmlResult.failed("To complete meal order", "Order should be placed successfully", "Edit button is not clicked" );
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
				HtmlResult.failed("To complete meal order", "Order should be placed successfully", "Edit button is not clicked" );
				return false;
			}
		
		}
		catch (Exception e)
		{
			HtmlResult.failed("To complete meal order", "Order should be placed successfully", "Error while performing change choice-'"+e.getMessage()+"'");
		}
		
		return false;
	}
	

	public boolean clearChoiceVerification_New(String drinkButtonName,String sideButtonName,String strActualDrinkProductName, String strActualSideProductName)
	{
		boolean SideItemValidated = false;
		boolean DrinkItemValidated = false;
		
		try
		{
			String OrderConfirmation_AddToOrder = getValueFromExcel("OrderConfirmation_AddToOrder");
			
			//Click on Edit Button
			boolean blnfoundAndClicked = false;

			if (clickButton("ngk_ReviewOrder_EditButton") && eggUIDriver.imageFound(OrderConfirmation_AddToOrder))
			{
				HtmlResult.passed("Edit Button functionality ","Edit button should be clicked" , " Edit button is clicked successfully " );
				eggUIDriver.wait(2);

				// Verify if page is present or not
				List<Point> LocationOfChangeChoiceButton = eggUIDriver.everyImageLocation("ngk_ClearchoiceScreen_ClearChoice");

				if(LocationOfChangeChoiceButton != null)
				{
					for(int Index = 0; Index <LocationOfChangeChoiceButton.size();Index++)
					{
						Point ChangeChoiceOccurance = LocationOfChangeChoiceButton.get(Index);
						if(Index == 0)
						{
							eggUIDriver.wait(2);
							if(eggUIDriver.clickPoint(ChangeChoiceOccurance))
							{
								String [] arrsideButtonNames = sideButtonName.split("#");
								boolean ProductFound = false;
								for(String SideItemName : arrsideButtonNames)
								{
									eggUIDriver.wait(2);
									ProductFound = false;
									if(SideItemName!="")
									{
										if(!clickButton(SideItemName))
										{
											HtmlResult.failed("To perform change choice actions", "Change choice should be successful", "Side item-'"+SideItemName+"' button is not found on screen");
										}
										else
										{
											ProductFound = true;
											HtmlResult.passed("To perform change choice actions", "Change choice should be successful", "Side item-'"+SideItemName+"' added to the meal");
										}
									}
									else
									{
										HtmlResult.warning("To perform change choice actions", "Change choice should be successful", "No need to add side item to the meal");
										SideItemValidated = true;
									}
										 
								}

								if(ProductFound)
								{
									SideItemValidated = true;
								}
								else
								{
									return false;
								}
							}
							
							eggUIDriver.wait(2);
							LocationOfChangeChoiceButton = eggUIDriver.everyImageLocation("ngk_ClearchoiceScreen_ClearChoice");
							if(sideButtonName.contains("#"))
							{
								Index++;
							}
						}
						else
						{
							eggUIDriver.wait(2);
							if(eggUIDriver.clickPoint(ChangeChoiceOccurance))
							{
								boolean ProductFound = false;
								String [] arrDrinkItem = drinkButtonName.split("#");
								for(String DrinkItem : arrDrinkItem)
								{
									eggUIDriver.wait(2);
									ProductFound = false;
									if(DrinkItem!="")
									{
										if(clickButton(DrinkItem))
										{
											ProductFound = true;
											HtmlResult.passed("To perform change choice actions", "Change choice should be successful", "Drink item-'"+DrinkItem+"' added to the meal");		
										}
										else
										{
											HtmlResult.failed("To perform change choice actions", "Change choice should be successful", "Button-'"+DrinkItem+"' not found ");		
										}
									}
									else
									{
										HtmlResult.warning("To perform change choice actions", "Change choice should be successful", "No need to add drink item to the meal");
										DrinkItemValidated = true;
									}
									
								}

								if(ProductFound)
								{
									DrinkItemValidated = true;
								}
								else
								{
									return false;
								}
							}
							else
							{
								HtmlResult.failed("To perform change choice actions", "Change choice should be successful", "Change choice button is found at location-( "+ChangeChoiceOccurance.toString()+" ) but its not clickable");		
							}
						}
					}
				}
				else
				{
					HtmlResult.failed("To perform change choice action", "Change choice should be successful", "Change choice button is not available on screen");
				}


				//Click on Add to Order button to complete the Order
				if(SideItemValidated && DrinkItemValidated)
				{
					if (eggUIDriver.imageFound(OrderConfirmation_AddToOrder))
					{
						blnfoundAndClicked = clickButton(OrderConfirmation_AddToOrder);
						if (blnfoundAndClicked)
						{
							HtmlResult.passed("Select 'Add to Order' Button", " 'Add to Order' button should be selected", "'Add to Order' button selected successfully");
							
							if(SideItemValidated && DrinkItemValidated)
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
							HtmlResult.failed("Select 'Add to Order' Button", " 'Add to Order' button should be selected", "'Add to Order' button is not selected ");
							return false;
						}
					}
					else
					{
						HtmlResult.failed("Edit Button functionality ","Edit button should be clicked" , "Edit button is not clicked" );
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
				HtmlResult.failed("Edit Button functionality ","Edit button should be clicked" , "Edit button is clicked " );
				return false;
			}
		
		}
		catch (Exception e)
		{
			HtmlResult.failed("To perform change choice action", "Change choice should be successful", "Error while performing change choice-'"+e.getMessage()+"'");
		}
		
		return false;
	}

	//	Internal function for verification of clear choice functionality
	public void clearChoiceVerification(String drinkButtonName1,String drinkButtonName2,String sideButtonName,String strActualDrinkProductName, String strActualSideProductName) throws FileNotFoundException, IOException, BiffException
	{
		//Fetching data from property file (NGK.properties)
		String OrderBasket_ProductCustomiseButton=getValueFromExcel("OrderBasket_ProductCustomiseButton");
		String ChangeChoice_CommonButton_OK=getValueFromExcel("ChangeChoice_CommonButton_OK");
		String OrderBasket_ProductChangeChoices=getValueFromExcel("OrderBasket_ProductChangeChoices");

		boolean blnfoundAndClicked=false;
		String strstepDescrp="To validate sales panel data - ";
		String strExpected="Sales panel should display - ";
		String strButtonClickstepDescrp="Click button to add - ";
		String strButtonClickExpected="Button click should be successful - ";
		String strSalesPanelData="";
		String sideItemSalesPanelName=strActualSideProductName;
		String drinkItemSalesPanelName=strActualDrinkProductName;
		boolean blnDataValidation=false;

		blnfoundAndClicked= clickButton(OrderBasket_ProductCustomiseButton);
		if(blnfoundAndClicked)
		{
			//HtmlResult.passed("Click Button - Customise ","Button click should be successful - Customise","Customise button clicked successfully");
			blnfoundAndClicked=clickButton(OrderBasket_ProductChangeChoices);
			if(blnfoundAndClicked)
			{
				//HtmlResult.passed("Click Button - Change Choices ","Button click should be successfull - Change Choices","Change Choices button clicked successfully");
				blnfoundAndClicked=clickButton(sideButtonName.trim()); //click on side1
				if(blnfoundAndClicked)
				{
					HtmlResult.passed(strButtonClickstepDescrp + sideButtonName,strButtonClickExpected+" "+sideButtonName,"Side Item Button clicked successfully - "+sideButtonName);

					blnfoundAndClicked=clickButton(drinkButtonName1.trim()); //click on drink1
					if(blnfoundAndClicked)
					{
						HtmlResult.passed(strButtonClickstepDescrp + drinkButtonName1,strButtonClickExpected+" "+drinkButtonName1,"Side Item Button clicked successfully - "+drinkButtonName1);
						blnfoundAndClicked=clickButton(drinkButtonName2.trim());
						if(blnfoundAndClicked)
						{
							HtmlResult.passed(strButtonClickstepDescrp + drinkButtonName2,strButtonClickExpected+" "+drinkButtonName2,"Side Item Button clicked successfully - "+drinkButtonName2);
							blnfoundAndClicked=clickButton(ChangeChoice_CommonButton_OK);
							if(blnfoundAndClicked)
							{
								//passed("Click Button - OK ","Button click should be successfull - OK","OK button clicked successfully");

								//call function to verify sales panel
								strSalesPanelData=ReadOrderBasketPanel(StrNGKlanguage);
								strSalesPanelData=strSalesPanelData.replaceAll("\t", " ");
								String[] SalesPanelData=strSalesPanelData.split("\n");

								for(String ActualSideData:SalesPanelData)   //data must contain the actual side item names that should appear on panel
								{
									if(ActualSideData.equals(drinkItemSalesPanelName))//sideItemSalesPanelName
									{
										HtmlResult.passed(strstepDescrp+" For drink item  ",strExpected + strActualDrinkProductName,"Order basket showing correct data as expected");
										blnDataValidation=true;
										strActualSideProductName=ActualSideData;
									}
								}

								if(!blnDataValidation)
								{
									HtmlResult.failed(strstepDescrp+" For drink item  ",strExpected + strActualDrinkProductName,"Actual data is - \n"+ strSalesPanelData);
								}

								//drinks data validation
								for(String ActualDrinkData:SalesPanelData)
								{
									if(ActualDrinkData.equals(sideItemSalesPanelName))
									{
										HtmlResult.passed(strstepDescrp+" For side item  ",strExpected + strActualSideProductName,"");
										blnDataValidation=true;
										strActualDrinkProductName=ActualDrinkData;
									}
								}
								if(!blnDataValidation)
								{
									HtmlResult.failed(strstepDescrp+" For side item  ",strExpected + strActualSideProductName,"Actual data is - \n"+SalesPanelData);
								}
							}
							else
							{
								HtmlResult.failed("Click Button - OK ","Button click should be successful - OK","Failed to click button - OK button not found");
							}
						}
						else
						{
							HtmlResult.failed(strButtonClickstepDescrp+" - "+drinkButtonName2,strButtonClickExpected+" "+drinkButtonName2," Failed to click button - "+drinkButtonName2+ " - Button not found ");
						}
					}
					else
					{
						HtmlResult.failed(strButtonClickstepDescrp+" - "+drinkButtonName1,strButtonClickExpected+" "+drinkButtonName1," Failed to click button - "+drinkButtonName1+ " - Button not found ");
					}
				}
				else
				{
					HtmlResult.failed(strButtonClickstepDescrp+" - "+sideButtonName,strButtonClickExpected+" "+sideButtonName," Failed to click button - "+sideButtonName+ " - Button not found ");
				}
			}
			else
			{
				HtmlResult.failed("Click Button - Change Choices ","Button click should be successful - Change Choices","Failed to click button - Change Choices button not found");
			}
		}
		else
		{
			HtmlResult.failed("Click Button - Customise ","Button click should be successful - Customise","Failed to click button - Customise button not found");
		}
	}

	//Order Meal
	public void orderMeal(Map<String,String> input)
	{
		String strProductName=input.get("productName");// (BigMac_MedBigMac) / (BigMac_LargeBigMac)
		String strTypeOfMeal=input.get("typeOfMeal");  //Medium big mac /Large big Mac
		String strSideItemName=input.get("mealSideitemName");
		String strDrinkItemName=input.get("mealDrinkitemName");
		String strdips=input.get("dips");
		String strNumOfDips=input.get("numOfDips");
		int numOfDips=Integer.parseInt(strNumOfDips);
		String coreProductSalesPanelName=input.get("salesPanelProductName");
		String strSalesPanelData;
		boolean blnDataValidation=false;
		boolean blnFoundAndClicked =false;
		String strActualProductName = " ";

		//for reporting
		String strStepDesc="Click button to add - ";
		String strExpected="Button click should be successful";
		String strstepDescrp="To validate sales panel data - ";
		String strExpectedSales="Sales panel should display - ";


		// Using menu selection select the type of menu
		//e.g. sandwiches, big flavour wraps .....

		blnFoundAndClicked=clickButton(strProductName);
		if(blnFoundAndClicked)
		{
			HtmlResult.passed(strStepDesc+strProductName,strExpected+strProductName,"Button clicked successfully "+strProductName);

			if(!(strTypeOfMeal==null))
			{
				blnFoundAndClicked=clickButton(strTypeOfMeal);
				if(blnFoundAndClicked)
				{
					HtmlResult.passed(strStepDesc+strTypeOfMeal,strExpected+strTypeOfMeal,"Button clicked successfully "+strTypeOfMeal);

				}
				else
				{
					HtmlResult.failed(strStepDesc+strTypeOfMeal,strExpected+strTypeOfMeal,"Failed to click button "+strTypeOfMeal);
				}
			}

			if(strProductName.toUpperCase().contains("NUGGETS")||strProductName.toUpperCase().contains("SELECTS"))
			{
				//dips need to be selected
				for(int i=1;i<=numOfDips;i++)  //check if there is no value in number of dips column
				{
					blnFoundAndClicked=clickButton(strdips);
					if(blnFoundAndClicked)
					{
						HtmlResult.passed(strStepDesc+strdips,strExpected+strdips,"Button clicked successfully "+strdips);
					}
					else
					{}
				}
			}

			blnFoundAndClicked=clickButton(strSideItemName);
			if(blnFoundAndClicked)
			{
				HtmlResult.passed(strStepDesc+strSideItemName,strExpected+strSideItemName,"Button clicked successfully "+strSideItemName);
				blnFoundAndClicked=clickButton(strDrinkItemName);
				if(blnFoundAndClicked)
				{
					HtmlResult.passed(strStepDesc+strDrinkItemName,strExpected+strDrinkItemName,"Button clicked successfully "+strDrinkItemName);

					//verify sales panel
					//call function to verify sales panel
					strSalesPanelData=ReadOrderBasketPanel(StrNGKlanguage);
					strSalesPanelData=strSalesPanelData.replaceAll("\t", " ");
					String[] salesPanelData=strSalesPanelData.split("\n");

					for(String ActualDrinkData:salesPanelData)
					{
						if(ActualDrinkData.equals(coreProductSalesPanelName))
						{
							blnDataValidation=true;
							strActualProductName=ActualDrinkData;
							HtmlResult.passed(strstepDescrp+" For core item  ",strExpectedSales + coreProductSalesPanelName,"Order basket data validation successful "+"\nActual order basket data is"+strActualProductName);
						}
					}
					if(!blnDataValidation)
					{
						HtmlResult.failed(strstepDescrp+" For core item  ",strExpectedSales + coreProductSalesPanelName,"Order basket data validation failed "+"\nActual data is - \n"+strActualProductName);
					}
				}
				else
				{
					HtmlResult.failed(strStepDesc+strDrinkItemName,strExpected+strDrinkItemName,"Failed to click button "+strDrinkItemName);
				}
			}
			else
			{
				HtmlResult.failed(strStepDesc+strSideItemName,strExpected+strSideItemName,"Failed to click button "+strSideItemName);
			}

		}
		else
		{
			HtmlResult.failed(strStepDesc+strProductName,strExpected+strProductName,"Failed to click button "+strProductName);
		}
	}


	public void completeOrder(Map<String,String> input)
	{
		try
		{
			//ProceedToCheckOut
			//cashless ok
			//transaction failed cancel
			//like to pay at counter
			String ProceedToCheckOut = input.get("ProceedToCheckOut").trim(); //yes or no
			String PayAtCounter = input.get("PayAtCounter").trim(); // yes or no
			String strDescription = "To complete order";
			String strExpectedResult = "Order should be completed";
			String ZoneNumber = input.get("ZoneNumber").trim(); 
			boolean PerformPayAtCounter = false;
			
			if(ProceedToCheckOut.equalsIgnoreCase("YES"))
			{
				if(clickButton("ngk_OrderConfirmScreen_ProceedToCheckout"))
				{
					eggUIDriver.wait(2);
					
					if(ZoneNumber.length()>1)
					{
						if(selectZone(ZoneNumber))
						{
							HtmlResult.passed(strDescription, strExpectedResult, "Zone ' "+ZoneNumber+" ' is selected successfully");
						}
						else
						{
							HtmlResult.failed(strDescription, strExpectedResult, "Zone ' "+ZoneNumber+" ' is not selected");
						}
					}
					
					if(eggUIDriver.clickPoint(new Point(400,600)))
					{
						if(eggUIDriver.clickText("Ok"))
						{
							eggUIDriver.wait(2);
							if(clickButton("ngk_commonbutton_Cancel"))
							{
								eggUIDriver.wait(2);
								PerformPayAtCounter = true;
							}
							else
							{
								String CancelImage = getValueFromExcel("ngk_cashlessTransactionfailed_cancel");
								if(clickButton(CancelImage))
								{
									PerformPayAtCounter = true;
								}
								else
								{
									HtmlResult.failed(strDescription, strExpectedResult, "Cancel button is not clickable");
								}
							}
						}
						else
						{
							String CashlessDeviceNotAvailable = getValueFromExcel("ngk_cashlessDeviceNotAvailable_ok").trim();
							if(clickButton(CashlessDeviceNotAvailable))
							{
								eggUIDriver.wait(2);
								if(clickButton("ngk_commonbutton_Cancel"))
								{
									eggUIDriver.wait(2);
									PerformPayAtCounter = true;
								}
								else
								{
									String CancelImage = getValueFromExcel("ngk_cashlessTransactionfailed_cancel");
									if(clickButton(CancelImage))
									{
										PerformPayAtCounter = true;
									}
									else
									{
										HtmlResult.failed(strDescription, strExpectedResult, "Cancel button is not clickable");
									}
								}
							}
							else
							{
								HtmlResult.failed(strDescription, strExpectedResult, "Cashless Device Not Available-OK button is not clickable");

							}
						}
					}
				}
				else
				{
					String ImgProceedToCheckOut = getValueFromExcel("ProceedToCheckOut").trim();
					if(clickButton(ImgProceedToCheckOut))
					{
						eggUIDriver.wait(2);
						if(eggUIDriver.clickPoint(new Point(400,600)))
						{
							eggUIDriver.wait(2);
							if(eggUIDriver.clickText("Ok"))
							{
								eggUIDriver.wait(2);
								if(eggUIDriver.clickImage("ngk_commonbutton_Cancel"))
								{
									PerformPayAtCounter = true;
								}
								else
								{
									String CancelImage = getValueFromExcel("ngk_cashlessTransactionfailed_cancel");
									if(clickButton(CancelImage))
									{
										PerformPayAtCounter = true;
									}
									else
									{
										HtmlResult.failed(strDescription, strExpectedResult, "Cancel button is not clickable");
									}
								}
							}
							else
							{
								String CashlessDeviceNotAvailable = getValueFromExcel("ngk_cashlessDeviceNotAvailable_ok").trim();
								if(clickButton(CashlessDeviceNotAvailable))
								{
									eggUIDriver.wait(2);
									if(eggUIDriver.clickImage("ngk_commonbutton_Cancel"))
									{
										PerformPayAtCounter = true;
									}
									else
									{
										String CancelImage = getValueFromExcel("ngk_cashlessTransactionfailed_cancel");
										eggUIDriver.wait(2);
										if(clickButton(CancelImage))
										{
											PerformPayAtCounter = true;
										}
										else
										{
											HtmlResult.failed(strDescription, strExpectedResult, "Cancel button is not clickable");
										}
									}
								}
								else
								{
									HtmlResult.failed(strDescription, strExpectedResult, "Cashless Device Not Available-OK button is not clickable");

								}
							}
						}
					}
					else
					{
						HtmlResult.failed(strDescription, strExpectedResult, "Proceed to checkout button is not clickable");
					}
				}
				
				if(PerformPayAtCounter)
				{
					if(PayAtCounter.equals("YES"))
					{
						eggUIDriver.wait(2);
						if(clickButton("ngk_commonbutton_YES"))
						{
							HtmlResult.passed(strDescription, strExpectedResult, "Order has been completed");
						}
						else
						{
							HtmlResult.failed(strDescription, strExpectedResult, "Order completion failed");
						}
					}
					else if(PayAtCounter.equals("No"))
					{
						eggUIDriver.wait(2);
						if(clickButton("ngk_commonbutton_No"))
						{
							HtmlResult.passed(strDescription, strExpectedResult, "Order has been completed");
						}
						else
						{
							HtmlResult.failed(strDescription, strExpectedResult, "Order completion failed");
						}
					}
				}
			}
			else
			{
				eggUIDriver.wait(2);
				if(eggUIDriver.clickText("Back"))
				{
					HtmlResult.passed(strDescription, strExpectedResult, "Order has been completed with BACK button");
				}
				else
				{
					HtmlResult.failed(strDescription, strExpectedResult, "Order has been completed with BACK button");
				}
			}
			
		}
		catch(Exception e)
		{
			HtmlResult.failed ("Complete the Order","Order should be completed","Order is not complete -" +  e.getMessage());
			RecoveryNGK_TimeOut();//Using NGK Timeout functionality
		}
	}

	/****************Complete NGK Order
	 *
	 *
	 *
	 *
	 *
	 */
	public void completeNGKOrder(Map<String,String> input)
	{
		// After verify final Order  (we need to complete this order)

		// To confirm your order (Press- YES Button to continue)

		try
		{
			//declaring variable for future use
			//String Str_confirmYourFinalOrder 			= input.get("confirmYourFinalOrder").trim();
			String Str_ConfirmationOrder_ProceedToCheckOut = input.get("ConfirmationOrder_ProceedToCheckOut").trim();
			//String Str_TableServiceChoice_NoThanks = input.get("TableService_NoThanksOption").trim();
			String Str_cashlessDeviceErrorConfirmation 	= input.get("cashlessDeviceErrorConfirmation").trim();
			String Str_doYouWantToContinueForCashless	= input.get("doYouWantToContinueForCashless").trim();
			String Str_doYouWantToPayAtCounter			= input.get("doYouWantToPayAtCounter").trim();

			int NGKResponseTime = 3;
			boolean FoundAndClicked=false;
			if(Str_ConfirmationOrder_ProceedToCheckOut.equalsIgnoreCase("YES"))
			{
				eggUIDriver.wait(NGKResponseTime);
				FoundAndClicked = clickButton("ngk_OrderConfirmScreen_ProceedToCheckout");
				/*if (Str_TableServiceChoice_NoThanks.equalsIgnoreCase("NoThanks"))
				{
					FoundAndClicked = clickButton("ngk_CommonButton_NoThanksCollect");*/
					{
						if(Str_cashlessDeviceErrorConfirmation.equalsIgnoreCase("OK"))
						{
							eggUIDriver.wait(NGKResponseTime);
							FoundAndClicked = clickButton("ngk_commonbutton_Popup_OK");
							if(Str_doYouWantToContinueForCashless.equalsIgnoreCase("Cancel"))
							{
								eggUIDriver.wait(NGKResponseTime);
								FoundAndClicked = clickButton("ngk_commonbutton_Cancel");
								if(FoundAndClicked)
								{
									if(Str_doYouWantToPayAtCounter.equalsIgnoreCase("No"))
									{
										eggUIDriver.wait(NGKResponseTime);
										FoundAndClicked = clickButton("ngk_commonbutton_NO");
										if (FoundAndClicked)
										{
											HtmlResult.passed("Complete NGK Order","NGK Order should be complete successfully","NGK Order is completed successfully");
										}
										else
										{
											HtmlResult.failed("Complete NGK Order","NGK Order should be complete successfully","NGK Order is not completed - Failed at 'Do You Want To Pay At Counter' screen");
											RecoveryNGK_TimeOut();//Using NGK Timeout functionality
										}
									}

									else if (Str_doYouWantToPayAtCounter.equalsIgnoreCase("Yes"))
									{
										eggUIDriver.wait(NGKResponseTime);
										FoundAndClicked = clickButton("ngk_commonbutton_Yes");
										if (FoundAndClicked)
										{
											HtmlResult.passed("Complete NGK Order","NGK Order should be complete successfully","NGK Order is completed successfully");
										}
										else
										{
											HtmlResult.failed("Complete NGK Order","NGK Order should be complete successfully","NGK Order is not completed - Failed at 'Do You Want To Pay At Counter' screen");
											RecoveryNGK_TimeOut();//Using NGK Timeout functionality
										}
									}
									else
									{
										HtmlResult.failed("Complete NGK Order","NGK Order should be complete successfully","NGK Order is not completed - Failed at 'Do You Want To Pay At Counter' Screen due to wrong entry in designer - valid option (YES, NO) only");
										RecoveryNGK_TimeOut();//Using NGK Timeout functionality
									}
								}
								else
								{
									HtmlResult.failed("Complete NGK Order","NGK Order should be complete successfully","NGK Order is not completed - Failed at 'Do You Want To Continue For Cashless' Screen");
									RecoveryNGK_TimeOut();//Using NGK Timeout functionality
								}
							}
						else
						{
							HtmlResult.failed("Complete NGK Order","NGK Order should be complete successfully","NGK Order is not completed - Failed at 'Confirm Your Final Order' Screen");
							RecoveryNGK_TimeOut();//Using NGK Timeout functionality
						}
					}
				}
			}

			else if(Str_ConfirmationOrder_ProceedToCheckOut.equalsIgnoreCase("NO"))
			{
				eggUIDriver.wait(NGKResponseTime);
				FoundAndClicked = clickButton("ngk_commonbutton_No");

				if (FoundAndClicked)
				{
					HtmlResult.passed("Complete NGK Order","NGK Order should be cancel","Successfully select 'Confirm Your Final Order - 'NO'  button'");
				}
				else
				{
					HtmlResult.failed("Complete NGK Order","NGK Order should be cancel","Unable to select 'Confirm Your Final Order - 'NO' button' ");
					RecoveryNGK_TimeOut();//Using NGK Timeout functionality
				}
			}

			else
			{
				HtmlResult.failed("Complete NGK Order","NGK Order should be complete successfully","NGK Order is not completed - Failed at 'Confirm Your Final Order' Screen");
				RecoveryNGK_TimeOut();//Using NGK Timeout functionality
			}

		}
		catch (Exception e)
		{
			HtmlResult.failed ("Complete the Order","Order should be completed","Order is not complete -" +  e.getMessage());
			RecoveryNGK_TimeOut();//Using NGK Timeout functionality
		}
	}


	/****************Complete NGK Order
	 *
	 *
	 *
	 *
	 *
	 */

	public void hSAPVoucherEntry(Map<String,String> input)
	{
		try{
		
			String Str_I_Have_A_Voucher = getValueFromExcel("I_Have_A_Voucher");
			String Str_Enter_Manually = getValueFromExcel("Enter_Manually");
			String strBarcodeImage = getValueFromExcel("strBarcodeImage");
			String VoucherEnterButton = getValueFromExcel("Voucher_EnterButton");
			String hSAPNumber = input.get("hsapVoucherCode");
			// Click 'i Have A Voucher' button
			
			boolean clicked  =clickButton(Str_I_Have_A_Voucher);
			if (clicked) //
			{
				HtmlResult.passed("Tap to 'I have a Voucher' button ","Button should be clicked","Button clicked successfully");
				
				if (eggUIDriver.imageFound(strBarcodeImage)) // only work if barcode image present
				{	
					clickButton(strBarcodeImage);	 // only present in Monopoly cycle
					eggUIDriver.wait(2);
					HtmlResult.passed("Tap to 'barcode' Button to enter HSAP Code","'barcode' Button should be clicked"," 'barcode' Button clicked successfully");
					
				}
				else
				{
					eggUIDriver.wait(2);
					HtmlResult.warning("Tap to 'barcode' Button to enter HSAP Code","'barcode' Button is not present"," 'barcode' Button  Button is not present");
				}
				// code will continue if barcode is present or not in any cycle hence next line will execute 
					eggUIDriver.wait(2);
					clicked  =clickButton(Str_Enter_Manually);
					if (clicked)
					{
						HtmlResult.passed("Tap to 'Enter Manually' Button to enter HSAP Code","'Enter Manually' Button should be clicked"," 'Enter Manually' Button clicked successfully");
						//Click Enter Manually (eg- 234734728) to split all the char from string
						for (int i = 0;i < hSAPNumber.length(); i++){
							char  StrVoucherNum=  hSAPNumber.charAt(i);
							String stringValueOfNumber = String.valueOf(StrVoucherNum);
							clicked =  clickButton(stringValueOfNumber);
						}
						if (clicked)
						{
							HtmlResult.passed("Enter HSAP Code - '" + hSAPNumber +" '","HSAP Code -  '" + hSAPNumber +  "'  should be entered by user successfully "," hsap code entered successfully");
							if (clicked)
							{
								// click 'EnterButton'  button
								eggUIDriver.wait(2);
								clicked = clickButtonAddToReport(VoucherEnterButton);
								if(clicked)
								{
									HtmlResult.passed("Tap to 'Enter Button' ","'Enter' Button should be clicked"," 'Enter ' Button clicked successfully");
								}
								else
								{
									HtmlResult.failed("Tap to 'Enter Button' ","'Enter' Button should be clicked"," 'Enter ' Button clicked successfully");
								}
							}
						}
						else
						{
							HtmlResult.failed("Enter HSAP Code - " + hSAPNumber ,"'HSAP Code - ' " + hSAPNumber +  "  should be entered by user successfully "," hsap code is not entered successfully");
						}
					}
					else
					{
						HtmlResult.failed("Tap to 'Enter Manually' Button to enter HSAP Code","'Enter Manually' Button should be clicked"," 'Enter Manually' Button is not clicked");
					}
				}
				/*else
				{
					HtmlResult.failed("Tap to 'barcode' Button to enter HSAP Code","'barcode' Button should be clicked"," 'barcode' Button is not clicked");
				}*/
				//Click 'Enter Manually' button
			
			else
			{
				HtmlResult.failed("Tap to 'I have a Voucher' button ","Button should be clicked","Button is not clicked");
			}
		}
		catch (Exception e)
		{
			HtmlResult.failed("Enter HSAP Code ","User Should be able to enter HSAP Code","HSAP code is not entered - " + e.getMessage());
		}
	}

	/*
	 ***************************************************************************************************************
	 * Method Number -
	 * Method - To Complete Order
	 * Component:  NGK Recovery
	 * Description:  To complete order during execution
	 * Parameter -
	 * Author: Prateek Gupta (Capgemini UKIT Automation Team)
	 * Date Created: 30/Sep/2016
	 * Modification History:
	 * <Date> <Who> <Mod description>
	 *
	 ***************************************************************************************************************
	 */

	public void RecoveryNGK_OrderConfirmation()
	{
		boolean FoundAndClicked	= false;
		boolean blnComplete = true;
		/*if (eggUIDriver.clickImage("ngk_commonbutton_Cancel")) // to click cancel button if exist
		{
			FoundAndClicked =objGotUK.clickButton("ngk_commonbutton_Cancel");
			blnComplete = true;
		}*/
		FoundAndClicked = clickButton("ngk_commonbutton_CancelOrder"); // To Select 'Cancel order' Button
		if (FoundAndClicked)
		{
			FoundAndClicked = clickButton("ngk_commonbutton_Yes"); // Do you want to continue
			if (FoundAndClicked)
			{
				HtmlResult.failed ("Order is completed using recovery", "Order is completed using Cancel Order Recovery","Order is completed using recovery scenario");
			}
			else
			{
				blnComplete = false;
			}
			blnComplete = true;
		}

		else
		{
			blnComplete = false;
		}
		if (!blnComplete)
		{
			FoundAndClicked = clickButton("ngk_commonbutton_No");
			if(FoundAndClicked)
			{
				HtmlResult.failed ("Order is completed using recovery", "Order is completed using Cancel Order Recovery","Order is completed using recovery scenario");
			}
		}
	}

	public void RecoveryNGK_LangugeSelection()
	{
		boolean FoundAndClicked	= false;
		if (eggUIDriver.imageFound("ngk_commonbutton_Back")) // to click cancel button if exist // need to fix- prateek
		{
			FoundAndClicked =clickButton("ngk_commonbutton_Back");
			if (FoundAndClicked)

			{
				HtmlResult.failed ("Order is completed using recovery", "Order is completed using Cancel Order Recovery","Order is completed using recovery scenario");
			}
		}

		else
		{
			HtmlResult.failed ("Order is completed using recovery", "Order should be completed using Cancel Order Recovery","Recovery scenario is not working");
		}
	}

	public void RecoveryNGK_TimeOut()
	{
		boolean FoundAndClicked	= false;
		Dimension ScreenResolution = eggUIDriver.remoteScreenSize(); // Reading Runtime screensize using eggplant method
		int Screenheight = ScreenResolution.height; // Reading Runtime NGK height
		int Screenwidth = ScreenResolution.width;   // Reading Runtime NGK width
		String Str_NGKScreenData="";
		int NGK_Default_TimeOut_Time =120;

		// define rectange to Select Meal Popup box
		Rectangle r = new Rectangle();
		r.height= Screenheight;
		r.width= Screenwidth;
		r.x = 11;
		r.y = Screenheight;

		Str_NGKScreenData = eggUIDriver.readText(r);

		if (Str_NGKScreenData.contains("Do you want to continue"))
		{
			eggUIDriver.wait(NGK_Default_TimeOut_Time);
			FoundAndClicked =clickButton("ngk_commonbutton_No");
			if (FoundAndClicked)

			{
				HtmlResult.failed ("Order is completed using recovery", "Order is completed using Cancel Order Recovery","Order is completed using Cancel Order Recovery");
			}
		}
		else
		{
			eggUIDriver.wait(NGK_Default_TimeOut_Time + 60);
			HtmlResult.failed ("Order is completed using recovery", "Order is completed using Time Out  Order Recovery","Order is completed using Time Out  recovery scenario -");
		}
	}



	//**********************************************CLICK FUNCTION WITH REPORTING BUT NOT A COMPONENT*************************//
	// Created by:yogesh
	// Purpose: Find and click the button and also add events to the report
	// Date=28/07/2016
	//************************************************************************************************************************//
	public boolean OrderNGKProductItem(Map<String,String> input)
	{
		//	String ButtonName ="";
		String strButton=input.get("buttonName");
		boolean Found=false;
		boolean Clicked=false;
		boolean Status=false;
		String[] strButtonList=strButton.split("#");
		
		for (String Button:strButtonList) {
			Button = Button.trim();
			String strStepDesc="Find and click the button-"+Button;
			String strExpected="Button should be found and clicked-"+Button;
			String strActual="";

			eggUIDriver.wait(4);// To Sync image - Temp solution
			Found=eggUIDriver.imageFound(Button);
			if (Found)
			{
				Clicked = eggUIDriver.clickImage(Button);
				if (Clicked)
				{
					Status = true;
					strActual="Button found and clicked-"+Button;
					HtmlResult.passed(strStepDesc,strExpected,strActual);
				}
				else
				{
					strActual="Button found but not clicked-"+Button;
					HtmlResult.failed(strStepDesc,strExpected,strActual);
					break;
				}
			}
			else
			{
				strActual="Button not found-"+Button;
				HtmlResult.failed(strStepDesc,strExpected,strActual);
				break;
			}
		}
		return Status;
	}
	
	public void placeBulkOrder(Map<String,String>input)
	{
		try
		{
			String InputDetails = input.get("MenuAndProductName").trim();
			String strDescription = "";
			String strExpectedResult = "To place multiple orders";
			getValueFromExcel("BumpAllKvsScreens");
			
			String[] Inputs = InputDetails.replaceAll("\n", "").split(";");
			
			for(String Input : Inputs)
			{
				String[] SliderMenuAndProductMenuChains =  Input.split(":");
				if(SliderMenuAndProductMenuChains.length==2)
				{
					// to launch slider menu
					String SliderMenu = SliderMenuAndProductMenuChains[0];
					String scrollingLocationImage= "ngk_SwipeMenuBar" ;
					Map<String,String> Output = new HashMap<String, String>();
					Output.put("carouselSliderMenu", SliderMenu);
					Output.put("scrollingLocationImage", scrollingLocationImage);
					
					if(ngkMenuSelection(Output))
					{
						String[] ProductMenuChain = SliderMenuAndProductMenuChains[1].split("#");
						
						String StrNGKMainMenu = "";
						String StrNGKSubMenu = "";
						String StrProductSelection = "";
						String Str_NGKResponseTime = "2";
						String Str_otherMealOrProductComponents = "";
						String StringAddToOrder = "";
						
						if(ProductMenuChain.length==1)
						{
							StrProductSelection = ProductMenuChain[0].trim();
						}
						else if(ProductMenuChain.length==2)
						{
							StrNGKMainMenu = ProductMenuChain[0];
							StrProductSelection = ProductMenuChain[1];
						}
						else if(ProductMenuChain.length==3)
						{
							StrNGKMainMenu = ProductMenuChain[0];
							StrNGKSubMenu = ProductMenuChain[1];
							StrProductSelection = ProductMenuChain[2];
						}
						else if(ProductMenuChain.length>3)
						{
							StrProductSelection = ProductMenuChain[0];
							
							String OtherComponents = "";
							for(int Index=1; Index<=ProductMenuChain.length-1;Index++)
							{
								try {
									OtherComponents = OtherComponents.concat(ProductMenuChain[Index]+"#");
								} catch (ArrayIndexOutOfBoundsException e) {
									break;
								}
							}
							
							Str_otherMealOrProductComponents = OtherComponents.substring(0, OtherComponents.length()-1);
	
						}
						
						Map<String,String> NewOutput = new HashMap<String, String>();
						NewOutput.put("nGKMainMenu", StrNGKMainMenu);
						NewOutput.put("nGKSubMenu", StrNGKSubMenu);
						NewOutput.put("productSelection", StrProductSelection);
						NewOutput.put("nGKResponseTimeInSec", Str_NGKResponseTime);
						NewOutput.put("otherMealOrProductComponents", Str_otherMealOrProductComponents);
						NewOutput.put("StrButton_AddToOrder", StringAddToOrder);
						
						productMenuSelection(NewOutput);
						addToOrder();
					}
				}
				else
				{
					HtmlResult.failed(strDescription, strExpectedResult, "':' is not used correctly to split slider menu and product menu chains");
				}
			}
			
			
			
			
			
			
		}
		catch(Exception e)
		{
			HtmlResult.failed("To place order","Order should be placed successfully","Error while executing ");//MenuAndProductName is wrong	
		}
	}
	
	
	public void performLanguageMenuUiValidation(Map<String,String> Input)
	{
		try
		{
			String LanguageName = Input.get("LanguageName").trim();
			String ValidateMcdLogo = Input.get("ValidateMcdLogo").trim();
			String Messages = Input.get("Messages").trim();
			String EatinAndTakeAwayTexts = Input.get("EatinAndTakeAwayTexts").trim();
			String ValidatePaymentOptions = Input.get("ValidatePaymentOptions").trim();
			
			String strDescription = "To validate UI of NGK";
			String strExpectedResult = "";
			
			//validate Mc'Donalds logo
			if(ValidateMcdLogo.equalsIgnoreCase("YES"))
			{
				strExpectedResult = "' Mc'Donalds sign ' should be displayed on screen";
				eggUIDriver.wait(2);
				if(checkMcdLogo())
				{	
					HtmlResult.passed(strDescription, strExpectedResult, "' Mc'Donalds sign ' is displayed on screen");
				}
				else
				{
					HtmlResult.failed(strDescription, strExpectedResult, "' Mc'Donalds sign ' is not displayed on screen");
				}
			}
			
			//validate allergn and payment information
			if(ValidatePaymentOptions.equals("YES"))
			{
				String AllergnAndPaymentImage = getValueFromExcel("ngk_allergenInfoAndPaymentOptions");
				eggUIDriver.wait(2);
				if(eggUIDriver.imageFound(AllergnAndPaymentImage))
				{
					HtmlResult.passed(strDescription, strExpectedResult, "Allergn information and payment options are displayed on screen");
				}
				else
				{
					HtmlResult.failed(strDescription, strExpectedResult, "Allergn information and payment options are not displayed on screen");
				}
			}
			
			//validate touch to start text
			eggUIDriver.wait(2);
			List<Point> TextLocation = eggUIDriver.getTextLocation("Touch to start your order");
			if(TextLocation.size()==1)
			{
				strExpectedResult = " 'Touch to start your order' text should be displayed on screen";
				HtmlResult.passed(strDescription, strExpectedResult, "'Touch to start your order' text is present on screen");
				eggUIDriver.clickPoint(TextLocation.get(0));
				eggUIDriver.wait(2);
			}
			else if(TextLocation.size()==0)
			{
				strExpectedResult = " 'Touch to start your order' text should be displayed on screen";
				HtmlResult.failed(strDescription, strExpectedResult, "'Touch to start your order' text is not present on screen");
				eggUIDriver.clickPoint(new Point(500,500));
				eggUIDriver.wait(2);
			}
			else
			{
				boolean TextNotFound = false;
				for(Point Location : TextLocation)
				{
					TextNotFound = false;
					eggUIDriver.wait(2);
					String TextAtLocation = eggUIDriver.readText(Location);
					if(TextAtLocation.trim()=="Touch to start your order")
					{
						strExpectedResult = " 'Touch to start your order' text should be displayed on screen";
						HtmlResult.passed(strDescription, strExpectedResult, "'Touch to start your order' text is present on screen");
						eggUIDriver.clickPoint(Location);
						break;
					}
					else
					{
						TextNotFound = true;
					}
				}
				
				if(TextNotFound)
				{
					strExpectedResult = " 'Touch to start your order' text should be displayed on screen";
					HtmlResult.failed(strDescription, strExpectedResult, "'Touch to start your order' text is not present on screen");
					eggUIDriver.clickPoint(new Point(500,500));
					eggUIDriver.wait(2);
				}
			}
			
		//validations on language screen
			
			/*once again validating mcd logo*/
			if(ValidateMcdLogo.equalsIgnoreCase("YES"))
			{
				strExpectedResult = "' Mc'Donalds sign ' should be displayed on screen";
				eggUIDriver.wait(2);
				if(checkMcdLogo())
				{	
					HtmlResult.passed(strDescription, strExpectedResult, "' Mc'Donalds sign ' is displayed on screen");
				}
				else
				{
					HtmlResult.failed(strDescription, strExpectedResult, "' Mc'Donalds sign ' is not displayed on screen");
				}
			}
			
			/*validation of back button text messages and language buttons*/
			
			/*---Language buttons---*/
			String LanguageButton = "ngk_Language_".concat(LanguageName.trim());
			strExpectedResult = "Language button '"+LanguageName+"' should be displayed on screen";
			eggUIDriver.wait(2);
			if(clickButton(LanguageButton))
			{
				HtmlResult.passed(strDescription, strExpectedResult, "Language button '"+LanguageName+"' is displayed on screen");	
			}
			else
			{
				HtmlResult.failed(strDescription, strExpectedResult, "Language button '"+LanguageName+"' is not displayed on screen");	
			}
			
			/*----Language messages----*/
			if(Messages.length()>1)//making it optional
			{
				eggUIDriver.wait(2);
				strExpectedResult = "Language messages should be present on screen";
				String[] LanguageMessages = Messages.split("#");
				if(LanguageMessages.length>=1)
				{
					for(String LanguageMessage : LanguageMessages)
					{
						LanguageMessage = LanguageMessage.trim();
						List<Point> TextLocations = eggUIDriver.getEveryTextLocation(LanguageMessage);
						if(TextLocations!=null)
						{
							HtmlResult.passed(strDescription, strExpectedResult, "Message ' "+LanguageMessage+" ' is displayed on screen");
						}
						else
						{
							HtmlResult.failed(strDescription, strExpectedResult, "Message ' "+LanguageMessage+" ' is not present on screen");
						}
					}
				}
				else
				{
					HtmlResult.failed(strDescription, strExpectedResult, "parameter 'Messages' can be used or left blank but should be present in parameter list");
				}
			}
			
			/*-----eat in and take away texts------*/
			if(EatinAndTakeAwayTexts.length()>1)//making it optional
			{
				String[] arrEatinAndTakeAwayTexts = EatinAndTakeAwayTexts.split("#");
				if(arrEatinAndTakeAwayTexts.length>=1)
				{
					for(String EatinAndTakeAwayText: arrEatinAndTakeAwayTexts)
					{
						EatinAndTakeAwayText = EatinAndTakeAwayText.trim();
						eggUIDriver.wait(2);
						List<Point> TextLocations = eggUIDriver.getEveryTextLocation(EatinAndTakeAwayText);
						if(TextLocations!=null)
						{
							HtmlResult.passed(strDescription, strExpectedResult, "Message ' "+EatinAndTakeAwayText+" ' is displayed on screen");
						}
						else
						{
							HtmlResult.failed(strDescription, strExpectedResult, "Message ' "+EatinAndTakeAwayText+" ' is not present on screen");
						}
					}
				}
				else
				{
					HtmlResult.failed(strDescription, strExpectedResult, "parameter 'EatinAndTakeAwayTexts' can be used or left blank but should be present in parameter list");
				}
			}
			
			/*--back button--*/
			String BackButtonWithLanguage = "back_button/".concat(LanguageName.trim());
			strExpectedResult = "Back button should be displayed on screen";
			eggUIDriver.wait(2);
			if(clickButton(BackButtonWithLanguage))
			{
				HtmlResult.passed(strDescription, strExpectedResult, "Back button in '"+LanguageName+"' is present on screen");
			}
			else
			{
				HtmlResult.failed(strDescription, strExpectedResult, "Back button in '"+LanguageName+"' is not present on screen");
			}
			
		}
		catch(Exception e)
		{
			HtmlResult.failed("To perform UI validation on NGK screen", "Validation should be successful", "Error occured due to-'"+e.getMessage()+"','"+e.getCause()+"'");
		}
	}
	
	public void performMenuTitleScreenUiValidation(Map<String,String> Input)
	{
		try
		{
			String strDescription="To validate UI of NGK";
			String strExpectedResult="";
			String LanguageName = Input.get("LanguageName").trim();
			String OrderTypeInCurrntLanguague = Input.get("LanguageOrderType").trim();
			
			Rectangle RectangleOverCurrentMenu = new Rectangle(37,1042,132,57);
			Rectangle RectangleOverCurrentMenuTitle = new Rectangle(184,321,684,149);

			//validating i have voucher image
			String IHaveVoucherCollectionName = getValueFromExcel("IHaveVoucherCollectionName");
			String IHaveVoucherImageName = IHaveVoucherCollectionName+"/".concat(LanguageName.trim());
			strExpectedResult = "Voucher option should be displayed in '"+LanguageName+"' language";
			eggUIDriver.wait(2);
			if(eggUIDriver.imageFound(IHaveVoucherImageName))
			{
				HtmlResult.passed(strDescription, strExpectedResult, "Voucher option is displayed in '"+LanguageName+"' language");
			}
			else
			{
				HtmlResult.passed(strDescription, strExpectedResult, "Voucher option is not displayed in '"+LanguageName+"' language on screen");
			}
			
			//validating orderTypes in current language
			eggUIDriver.wait(2);
			List<Point> TextLocations = eggUIDriver.getEveryTextLocation(OrderTypeInCurrntLanguague);
			strExpectedResult = "Order type should be displpayed in language ' "+LanguageName+" '";
			if(TextLocations!=null)
			{
				if(TextLocations.size()==0)
				{
					HtmlResult.failed(strDescription, strExpectedResult, "Order type is not displpayed in language ' "+LanguageName+" ' on screen");
				}
				else
				{
					HtmlResult.passed(strDescription, strExpectedResult, "Order type is displpayed in language ' "+LanguageName+" ' on screen");
				}
			}
			else 
			{
				HtmlResult.failed(strDescription, strExpectedResult, "Order type is not displpayed in language ' "+LanguageName+" ' on screen");
			}
			
			
			//validating disable complete order button
			String DisabledCompleteOrderButton = getValueFromExcel("ngk_disabled_completeOrder");
			String DisabledCompleteOrderImageName = DisabledCompleteOrderButton+"/".concat(LanguageName.trim());
			strExpectedResult = "Complete order button should be disabled and displayed in language ' "+LanguageName+" '";
			eggUIDriver.wait(2);
			if(eggUIDriver.imageFound(DisabledCompleteOrderImageName))
			{
				HtmlResult.passed(strDescription, strExpectedResult, "Complete order button is disabled and displayed in language ' "+LanguageName+" '");
			}
			else
			{
				HtmlResult.failed(strDescription, strExpectedResult, "Disabled complete order button is not displayed in language ' "+LanguageName+" '");		
			}
			
			//validation on menu names
			//as expected data is not from testcases sheet titles of the menus are just shown in reports
			
			eggUIDriver.wait(2);
			String CurrentMenuName = "";
			strExpectedResult = "Menu titles should be present in-' "+LanguageName+"' on screen";
			Point SwipeUpPoint = new Point(50,945); //+5 to RectangleOverCurrentMenu.x and -5 from RectangleOverCurrentMenu.y
			String FirstMenuName = eggUIDriver.readText(RectangleOverCurrentMenu);

			do{
				eggUIDriver.wait(2);
				//looping for other menu names than first menu, first menu will be validated at last
				eggUIDriver.clickPoint(SwipeUpPoint);
				CurrentMenuName = eggUIDriver.readText(RectangleOverCurrentMenu);
				if(CurrentMenuName.length()>0)
				{
					String MenuTitle = eggUIDriver.readText(RectangleOverCurrentMenuTitle);
					if(MenuTitle.length()>2)
					{
						HtmlResult.addMessageWithScreenShot("Menu title '"+MenuTitle+"' is present on screen for menu name '"+CurrentMenuName+"'","grey");
					}
					else
					{
						HtmlResult.addMessageWithScreenShot("Menu title '"+MenuTitle+"' is present on screen for menu name '"+CurrentMenuName+"'","Red");
					}
				}
				else
				{
					HtmlResult.addMessageWithScreenShot("Menu name is not present on screen","Red");
				}
				
				//validating mcd home logo
				eggUIDriver.wait(2);
				if(!checkMcdLogo())
				{
					strExpectedResult = "Mc'Donalds sign should be present on screen";
					HtmlResult.failed(strDescription, strExpectedResult, "For menu "+CurrentMenuName+" Mc'Donalds sign is not present");
				}
				

			}while(!(CurrentMenuName.trim().equals(FirstMenuName.trim())));
		}
		
		catch(Exception e)
		{
			HtmlResult.failed("To validate UI of NGK system", "Ui validations should be successful", "Error ocurred-"+e.getMessage()+" ,and "+e.getCause()+".");
		}
	}
	
	public void performQuantityScreenValidation(Map<String,String> Input)
	{
		try
		{
			String strDescription = "To validate UI of NGK";
			String strExpectedResult = "";
			String AddOrCancelOrderText = Input.get("AddOrCancelText");
			
			//validate pound sign
			strExpectedResult = "Price should contain � sign";
			Rectangle SearchRect = new Rectangle(5,302,695,167);
			eggUIDriver.setSearchRectangle(SearchRect);
			List<Point> TextLocations = eggUIDriver.everyTextLocation("�");
			if(TextLocations!=null)
			{
				if(TextLocations.size()==1)
				{
					String Price = eggUIDriver.readText(TextLocations.get(0));
					HtmlResult.passed(strDescription, strExpectedResult, "Price is mentioned as- ' "+Price+" ' ");
				}
				else if(TextLocations.size()>=1)
				{
					boolean TextFound = false;
					String Price = "";
					String FalseText = "";
					
					for(Point TextLocation : TextLocations)
					{
						Price = eggUIDriver.readText(TextLocation);
						if(Price.contains("�"))
						{
							TextFound = true;
							break;
						}
						else
						{
							FalseText = FalseText.concat(Price+",");
						}
					}
					
					if(TextFound)
					{
						HtmlResult.passed(strDescription, strExpectedResult, "Price is mentioned as- ' "+Price+" ' ");
					}
					else
					{
						HtmlResult.failed(strDescription, strExpectedResult, "Similar characters to ' � ' are found as- ' "+FalseText+" ' ");
					}
				}
				else
				{
					HtmlResult.failed(strDescription, strExpectedResult, " � character is not present on screen ");
				}
			}
			else
			{
				HtmlResult.failed(strDescription, strExpectedResult, " � character is not present on screen ");
			}
			
			//setting search rect to full screen
			eggUIDriver.setSearchRectangle(new Rectangle(eggUIDriver.remoteScreenSize()));
			
			//add to order and cancel order texts
			if(AddOrCancelOrderText.length()>0)
			{
				strExpectedResult = "Add to order and cancel order should be displayed on screen";
				String [] arrAddToOrderAndCancelOrderTexts = AddOrCancelOrderText.split("#");
				
				for(String ExpectedText : arrAddToOrderAndCancelOrderTexts)
				{
					List<Point> ExpectedTextsLocations = eggUIDriver.getEveryTextLocation(ExpectedText);
					if(ExpectedTextsLocations!=null)
					{
						if(ExpectedTextsLocations.size()==1)
						{
							HtmlResult.passed(strDescription, strExpectedResult, "Text ' "+ExpectedText+" 'is present on screen ");
						}
						else if(ExpectedTextsLocations.size()>=1)
						{
							boolean TextFound = false;
							String ActualTexts = "";
							String FalseText = "";
							
							for(Point TextLocation : ExpectedTextsLocations)
							{
								ActualTexts = eggUIDriver.readText(TextLocation);
								if(ActualTexts.contains(ExpectedText))
								{
									TextFound = true;
									break;
								}
								else
								{
									FalseText = FalseText.concat(ExpectedText+",");
								}
							}
							
							if(TextFound)
							{
								HtmlResult.passed(strDescription, strExpectedResult, "Text '"+ExpectedText+"' is present on screen ");
							}
							else
							{
								HtmlResult.failed(strDescription, strExpectedResult, "Text '"+ExpectedText+"' is not present on screen, Similar texts found on screen as- "+FalseText+"");
							}
						}
						else
						{
							HtmlResult.failed(strDescription, strExpectedResult, "Text ' "+ExpectedText+" 'is not present on screen ");	
						}
					}
					else
					{
						HtmlResult.failed(strDescription, strExpectedResult, "Text ' "+ExpectedText+" 'is not present on screen ");	
					}
				}

			}
		}
		catch(Exception e)
		{
			HtmlResult.failed("To validate UI of NGK", "NGK UI validation should be successful", " Error occure due to- "+e.getMessage()+" , "+e.getCause());
		}
	}
	
	public boolean selectZone(String ZoneNumber)
	{
		String strDescription = "To select zone to eat ";
		String strExpectedResult ="Expected Zone should get selected";
		try{
			
			ZoneNumber = ZoneNumber.replaceAll(" ", "").trim();
			String ZoneImageName = "ngk_TableService_"+ZoneNumber;
			if(clickButton(ZoneImageName))
			{
				return true;
			}
			else
			{
				return false;
			}
			
		}
		catch(Exception e){
			HtmlResult.failed(strDescription, strExpectedResult, "Error while runnig selectZone component,"+e.getMessage()+","+e.getCause());
			return false;
		}
	}
	
	public void selectZoneToEat(Map<String,String> Input)
	{
		String strDescription = "To select Zone in store";
		String strExpectedResult = "Zone should be selected";
		
		try
		{
			String ZoneNumber = Input.get("ZoneNumber").trim();
			if(selectZone(ZoneNumber))
			{
				HtmlResult.passed(strDescription, strExpectedResult, "Zone -'"+ZoneNumber+"' is selected");
			}
			else
			{
				HtmlResult.failed(strDescription, strExpectedResult, "Zone -'"+ZoneNumber+"' is not selected");
			}
		}
		catch(Exception e)
		{
			HtmlResult.failed(strDescription, strExpectedResult, "Error while performing selectZone() -'"+e.getMessage()+"','"+e.getCause()+"'");
		}
	}
	
	public void performZoneMenuUiValidation()
	{
		String strDescription = "To perform UI of NGK";
		String strExpectedResult = "NGK UI validation should be successful";
	
		try
		{
			//checking mcd logo
			if(checkMcdLogo())
			{
				HtmlResult.passed(strDescription, strExpectedResult, "Mc'Donalds logo is present on screen");
			}
			else
			{
				HtmlResult.failed(strDescription, strExpectedResult, "Mc'Donalds logo is not present on screen");
			}
			
			//checking zone menu info image
			if(eggUIDriver.imageFound("ngk_zonemenu"))
			{
				HtmlResult.passed(strDescription, strExpectedResult, "Zone information image is present on screen");
			}
			else
			{
				HtmlResult.failed(strDescription, strExpectedResult, "Zone information image is not present on screen");
			}

		}
		catch(Exception e)
		{
			HtmlResult.failed(strDescription, strExpectedResult, "Error while performing performZoneMenuUiValidation()-'"+e.getMessage()+"','"+e.getCause()+"'");
		}
	}
	
	public void performCompleteOrderUiValidation(Map<String,String> Input)
	{
		String strDescription = "To perform UI of NGK";
		String strExpectedResult = "NGK UI validation should be successful";
		String strCashLessDeviceMessage = Input.get("CashLessDeviceMessage");
		String strCardTransactionMessage = Input.get("CardTransactionMessage");
		String strPayAtCounterMessage = Input.get("PayAtCounterMessage");
		try
		{
			//checking mcd logo
			eggUIDriver.wait(3);
			if(checkMcdLogo())
			{
				HtmlResult.passed(strDescription, strExpectedResult, "Mc'Donalds logo is diplayed on screen");
			}
			else
			{
				HtmlResult.failed(strDescription, strExpectedResult, "Mc'Donalds logo is not diplayed on screen");
			}
			
			//cashless device message validation
			if(strCashLessDeviceMessage != null && strCashLessDeviceMessage.length()>1)
			{
				strCashLessDeviceMessage = strCashLessDeviceMessage.trim();
				String[] ArrCashLessDeviceMessage = strCashLessDeviceMessage.split("\n");
				for(String CashLessDeviceMessage : ArrCashLessDeviceMessage)
				{
					List<Point> TextLocations = eggUIDriver.everyTextLocation(CashLessDeviceMessage);
					if(TextLocations!=null)
					{
						if(TextLocations.size()==1)
						{
							HtmlResult.passed(strDescription, strExpectedResult, "Message-'"+CashLessDeviceMessage+"' is present on screen");
						}
						else
						{
							boolean TextFound = false;
							for(Point TextLocation : TextLocations )
							{
								TextFound = false;
								String TextAtLocation = eggUIDriver.readText(TextLocation);
								if(TextAtLocation.equals(CashLessDeviceMessage))
								{
									HtmlResult.passed(strDescription, strExpectedResult, "Message-'"+CashLessDeviceMessage+"' is present on screen");
									TextFound = true;
									break;
								}
			
							}
							
							if(!TextFound)
							{
								HtmlResult.failed(strDescription, strExpectedResult, "Message-'"+CashLessDeviceMessage+"' is not present on screen");
							}
						}
					}
					else
					{
						HtmlResult.failed(strDescription, strExpectedResult, "Message-'"+CashLessDeviceMessage+"' is not present on screen");
					}
				}
			}
			
			//clicking on OK button
			String CashlessOKButtonName = getValueFromExcel("ngk_cashlessDeviceNotAvailable_ok").trim();
			if(!clickButton(CashlessOKButtonName))
			{
				HtmlResult.failed(strDescription, strExpectedResult, "OK button is not clicked on screen");
			}
			
			//checking mcd logo
			eggUIDriver.wait(3);
			if(checkMcdLogo())
			{
				HtmlResult.passed(strDescription, strExpectedResult, "Mc'Donalds logo is diplayed on screen");
			}
			else
			{
				HtmlResult.failed(strDescription, strExpectedResult, "Mc'Donalds logo is not diplayed on screen");
			}
			
			
			// card transaction message validation
			if(strCardTransactionMessage!=null && strCardTransactionMessage.length()>1)
			{
				strCardTransactionMessage = strCardTransactionMessage.trim();
				
				String[] ArrCardTransactionMessage = strCardTransactionMessage.split("\n");
				for(String CardTransactionMessage : ArrCardTransactionMessage)
				{
					List<Point> TextLocations = eggUIDriver.everyTextLocation(CardTransactionMessage);
					if(TextLocations!=null)
					{
						if(TextLocations.size()==1)
						{
							HtmlResult.passed(strDescription, strExpectedResult, "Message-'"+CardTransactionMessage+"' is present on screen");
						}
						else
						{
							boolean TextFound = false;
							for(Point TextLocation : TextLocations )
							{
								TextFound = false;
								String TextAtLocation = eggUIDriver.readText(TextLocation);
								if(TextAtLocation.equals(CardTransactionMessage))
								{
									HtmlResult.passed(strDescription, strExpectedResult, "Message-'"+CardTransactionMessage+"' is present on screen");
									TextFound = true;
									break;
								}
			
							}
							
							if(!TextFound)
							{
								HtmlResult.failed(strDescription, strExpectedResult, "Message-'"+CardTransactionMessage+"' is not present on screen");
							}
						}
					}
					else
					{
						HtmlResult.failed(strDescription, strExpectedResult, "Message-'"+CardTransactionMessage+"' is not present on screen");
					}
				}
			}
			
			//clicking on cancel
			String CardTransactionCancelButtonName = getValueFromExcel("ngk_commonbutton_Cancel").trim();
			if(!clickButton(CardTransactionCancelButtonName))
			{
				HtmlResult.failed(strDescription, strExpectedResult, "Cancel button is not clicked on screen");
			}
			
			//checking mcd logo
			eggUIDriver.wait(3);
			if(checkMcdLogo())
			{
				HtmlResult.passed(strDescription, strExpectedResult, "Mc'Donalds logo is diplayed on screen");
			}
			else
			{
				HtmlResult.failed(strDescription, strExpectedResult, "Mc'Donalds logo is not diplayed on screen");
			}

			//payatcounter message validation
			if(strPayAtCounterMessage != null && strPayAtCounterMessage.length()>1)
			{
				strPayAtCounterMessage = strPayAtCounterMessage.trim();
				
				String[] ArrPayAtCounterMessage = strPayAtCounterMessage.split("/n");
				for(String PayAtCounterMessage : ArrPayAtCounterMessage)
				{
					List<Point> TextLocations = eggUIDriver.everyTextLocation(PayAtCounterMessage.trim());
					if(TextLocations!=null)
					{
						if(TextLocations.size()==1)
						{
							HtmlResult.passed(strDescription, strExpectedResult, "Message-'"+PayAtCounterMessage+"' is present on screen");
						}
						else
						{
							boolean TextFound = false;
							for(Point TextLocation : TextLocations )
							{
								TextFound = false;
								String TextAtLocation = eggUIDriver.readText(TextLocation);
								if(TextAtLocation.equals(PayAtCounterMessage))
								{
									HtmlResult.passed(strDescription, strExpectedResult, "Message-'"+PayAtCounterMessage+"' is present on screen");
									TextFound = true;
									break;
								}
			
							}
							
							if(!TextFound)
							{
								HtmlResult.failed(strDescription, strExpectedResult, "Message-'"+PayAtCounterMessage+"' is not present on screen");
							}
						}
					}
					else
					{
						HtmlResult.failed(strDescription, strExpectedResult, "Message-'"+PayAtCounterMessage+"' is not present on screen");
					}
				}
			}
			
			//paying at counter-yes
			String PayAtCounterYesButtonName = getValueFromExcel("ngk_payAtCounter_yes").trim();
			if(!clickButton(PayAtCounterYesButtonName))
			{
				HtmlResult.failed(strDescription, strExpectedResult, "Yes button is not clicked");
			}
			
			//checking mcd logo with no wait
			if(checkMcdLogo())
			{
				HtmlResult.passed(strDescription, strExpectedResult, "Mc'Donalds logo is diplayed on screen");
			}
			else
			{
				HtmlResult.failed(strDescription, strExpectedResult, "Mc'Donalds logo is not diplayed on screen");
			}
			
		}
		catch(Exception e)
		{
			HtmlResult.failed(strDescription, strExpectedResult, "Error while performing performZoneMenuUiValidation()-'"+e.getMessage()+"','"+e.getCause()+"'");
		}
	}
	
	public void displayOrderSummary()
	{
		String strDescription = " To read and display order summary ";
		String strExpectedResult =  " Order summary should be dispalyed on screen";
		try
		{
			Rectangle OrderSummaryRectangle = new Rectangle(71,578,1008,792); // not able to locate or identify a image in very small period of time 
			Rectangle EnjoyYourMealRectangle = new Rectangle(140,891,994,1091); // not able to locate or identify a image in very small period of time 
			
			String MealOrderSummary = eggUIDriver.readText(OrderSummaryRectangle);
			eggUIDriver.wait(4);
			String EnjoyMealText = eggUIDriver.readText(EnjoyYourMealRectangle);
			
			// order summary
			if(MealOrderSummary.length()>0)
			{
				if(MealOrderSummary.length()>8)
				{
					HtmlResult.addMessageWithScreenShot("Order summary found on screen is<br> ' "+MealOrderSummary+" ' ", "skyblue");
				}
				else
				{
					HtmlResult.addMessageWithScreenShot("Click to view order summary","skyblue");
				}
			}
			else
			{
				HtmlResult.failed(strDescription, strExpectedResult, "Ordered meal summary is not found at intended location");
			}
		 
			//Enjoy ypur meal 
			if(EnjoyMealText.length()>0)
			{
				if(EnjoyMealText.length()>5)
				{
					HtmlResult.addMessageWithScreenShot("Enjoy your meal found on screen is<br> as ' "+EnjoyMealText+" ' ", "skyblue");
				}
				else
				{
					HtmlResult.addMessageWithScreenShot("Click to view order summary","skyblue");
				}
			}
			else
			{
				HtmlResult.failed(strDescription, strExpectedResult, "Enjoy your meal message is not found at intended location");
			}
		
			//(71,578,1008,792)
			//(140,891,994,1091)
		
		}
		catch(Exception e)
		{
			HtmlResult.failed(strDescription, strExpectedResult, "Error while performing displayOrderSummary() -'"+e.getMessage()+"',"+e.getCause()+"'");
		}
	}
	
	
	public boolean checkMcdLogo()
	{
		try
		{
			String McdLogoImageName = getValueFromExcel("mcd_logo");
			if(eggUIDriver.imageFound(McdLogoImageName))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	
}
