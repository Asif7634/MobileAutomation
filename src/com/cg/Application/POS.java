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
import com.cg.ApplicationLevelComponent.EggPlant;
import com.cg.Logutils.HtmlResult;
import com.cg.TestCaseRunner.TestCaseRunner;
import com.cg.UIDriver.EggUIDriver;

import jxl.JXLException;

public class POS extends EggPlant{

	static EggUIDriver eggUIDriver = EggPlant.eggUIDriver;	
	private String GlobalMenuNamme = ""; 
	private boolean performMangagerAuthentication = false;
	
	public boolean orderProduct(Map<String,String> input)
	{
		try {
			String strProductNames=input.get("productName");

			String[] strArrProductNames=strProductNames.split("#");
			boolean Clicked=false;
			boolean Found=false;

			for (String Button:strArrProductNames) 
			{
				Button = Button.trim();
				Found=eggUIDriver.imageFound(Button);
				if (Found) 
				{
					Clicked = eggUIDriver.clickImage(Button);	
					if (Clicked) 
					{
						HtmlResult.passed("To select product","Product-'"+Button+"' should get selected","Product-'"+Button+"' selected successfully");
						Found = true;
					}
					else
					{
						HtmlResult.failed("To select product","Product-'"+Button+"' should get selected","Product-'"+Button+"' selected successfully");
						return false;
					}
				}
				else
				{
					HtmlResult.failed("To select product","Product-'"+Button+"' should get selected","Product-'"+Button+"' not selected . Image not found");
					return false;
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
			return false;
		}
		return false;

	}


	/*******************************************************************************************************************
	 * */

	public void menuSelection(Map<String,String> input)
	{

		try
		{
			//for every menu to be launch offers button should be clicked first as its always present on screen and 
			//set menu to Lunch by default
			
			String StartCleanUp = getValueFromExcel("OrderCleanUpRecovery").trim();
			if(StartCleanUp.equalsIgnoreCase("YES"))
			{
				runCleanUp();
			}
			else if(!StartCleanUp.equalsIgnoreCase("No"))
			{
				System.err.println("Cannot run initial clean up as parameter(\"OrderCleanUpRecovery\") set in PropertyMap.xlsx file is-"+StartCleanUp+"\n It should be YES or NO");
			}
			
			String strMenuName=input.get("menuName");
			strMenuName = strMenuName.trim();
			String stepDescrp="To launch menu-"+strMenuName;
			String strExpected="Menu should be launched-"+strMenuName;
			String strMenuButton="";
			String strOffersButton="";
			String strHelperMenuButton="";
			strMenuName=strMenuName.trim();
			GlobalMenuNamme = strMenuName;
			//**************************************lunch menu*******************************************************
			if(strMenuName.equalsIgnoreCase("LUNCH MENU")||strMenuName.equalsIgnoreCase("LUNCH"))
			{
				boolean FoundAndClicked=false;
				System.out.println("Loading Lunch Menu"); 
				try 
				{
					strMenuButton=getValueFromExcel("LUNCHMENU"); 
					strOffersButton=getValueFromExcel("OFFERSMENU") ;
				} 
				catch (Exception e) 
				{
					HtmlResult.failed(stepDescrp,strExpected,"Unable to read properties file-"+e.getMessage());
				}		

				FoundAndClicked= clickButton(strOffersButton);
				if(FoundAndClicked)
				{
					FoundAndClicked=clickButton(strMenuButton);
					if(FoundAndClicked)
					{
						HtmlResult.passed(stepDescrp,strExpected,"Menu launched Successfully-"+strMenuButton);
					}
					else
					{
						HtmlResult.failed(stepDescrp,strExpected,"Menu button not found and not clicked-"+strMenuButton);
					}
				}
				else
				{
					HtmlResult.failed(stepDescrp,strExpected,"Menu button not found and not clicked"+strOffersButton);		
				}
			}
			//******************************************breakfast menu*****************************************************	
			else if(strMenuName.equalsIgnoreCase("BREAKFAST MENU")||strMenuName.equalsIgnoreCase("Breakfast") ||strMenuName.equalsIgnoreCase("menu Breakfast"))
			{
				boolean FoundAndClicked=false;
				System.out.println("Loading breakfast menu");
				try {
					strMenuButton=getValueFromExcel("BREAKFASTMENU");
					strOffersButton=getValueFromExcel("OFFERSMENU") ; 
					strHelperMenuButton=getValueFromExcel("LUNCHMENU");//helper is lunch menu
				}

				catch(Exception e)
				{
					HtmlResult.failed(stepDescrp,strExpected,"falied to find and launch menu"+e.getMessage());
				}

				FoundAndClicked=clickButton(strOffersButton);
				if(FoundAndClicked)
				{
					FoundAndClicked=clickButton(strHelperMenuButton);// find lunch image
					if(FoundAndClicked)
					{
						FoundAndClicked=clickButton(strHelperMenuButton);// again clicked on lunch menu to launch breakfast
						if(FoundAndClicked)
						{
							HtmlResult.passed(stepDescrp,strExpected,"Menu launched successfully-"+strMenuButton);
						}
						else
						{
							HtmlResult.failed(stepDescrp,strExpected,"Menu button not found and not clicked-"+strMenuButton);
						}
					}	
					else
					{
						HtmlResult.failed(stepDescrp,strExpected,"Menu button not found and not clicked-"+strHelperMenuButton);
					}
				}
				else
				{
					HtmlResult.failed(stepDescrp,strExpected,"offers button not present");					
				}			
			}
			//******************************************Dessert menu*****************************************************
			else if(strMenuName.equalsIgnoreCase("DESSERTS MENU")||strMenuName.equalsIgnoreCase("DESSERTS"))
			{
				boolean FoundAndClicked=false;
				System.out.println("Loading Desserts menu");
				try 
				{
					strMenuButton=getValueFromExcel("DESSERTSMENU");
				}
				catch (Exception e) 
				{
					HtmlResult.failed(stepDescrp,strExpected,"falied to find and launch menu"+e.getMessage());
				}

				FoundAndClicked=clickButton(strMenuButton);

				if(FoundAndClicked)
				{
					HtmlResult.passed(stepDescrp,strExpected,"Menu successfully launched-"+strMenuButton);
				}
				else
				{
					HtmlResult.failed(stepDescrp,strExpected,"Menu button not found and not clicked-"+strMenuButton);
				}	
			}

			//************************************LRAGE EVM MENU*******************************************************
			else if(strMenuName.equalsIgnoreCase("LRG EVM")||strMenuName.equalsIgnoreCase("LARGE EVM")
					||strMenuName.equalsIgnoreCase("LARGE EVM MEAL")||strMenuName.equalsIgnoreCase("LARGE MEAL")
					||strMenuName.equalsIgnoreCase("LRG EVM MENU")||strMenuName.equalsIgnoreCase("Lrg Lunch Meal")
					||strMenuName.equalsIgnoreCase("levm"))
			{
				System.out.println("Loding large evm menu");
				try {	
					boolean blnResult=selectMealMenuUK("LRGEVM");
					if(blnResult)
					{
						HtmlResult.passed(stepDescrp,strExpected,"Menu successfully launched-"+strMenuName);
					}
					else
					{
						HtmlResult.failed(stepDescrp,strExpected,"Menu button not found and not clicked-"+strMenuName);
					}
				}
				catch(Exception e)
				{
					HtmlResult.failed(stepDescrp,strExpected,"failed to launch menu-LRG EVM"+e);
				}
			}

			//****************************************happy meal***************************************************
			else if(strMenuName.equalsIgnoreCase("HAPPY MEAL")||
					strMenuName.equalsIgnoreCase("HAPPY MEAL MENU")||
					strMenuName.equalsIgnoreCase("Happy meals"))
			{
				System.out.println("Loding happy meal menu");
				try {	
					boolean blnResult=selectMealMenuUK("HAPPYMEAL");
					if(blnResult)
					{
						HtmlResult.passed(stepDescrp,strExpected,"Menu successfully launched-"+strMenuName);
					}
					else
					{
						HtmlResult.failed(stepDescrp,strExpected,"Menu button not found and not clicked-"+strMenuName);
					}
				}
				catch(Exception e)
				{
					HtmlResult.failed(stepDescrp,strExpected,"HtmlResult.failed to launch menu-HAPPY MEAL"+e);
				}
			}

			//*********************************************MED EVM LUNCH***********************************************
			else if(strMenuName.equalsIgnoreCase("MED EVM")||strMenuName.equalsIgnoreCase("MEDIUM EVM")
					||strMenuName.equalsIgnoreCase("MEDIUM EVM MEAL")||strMenuName.equalsIgnoreCase("MEDIUM MEAL")
					||strMenuName.equalsIgnoreCase("MED EVM MENU")||strMenuName.equalsIgnoreCase("Med Lunch Meal")
					||strMenuName.equalsIgnoreCase("mevm"))
			{
				System.out.println("Loading lunch medium evm menu");
				try {
					boolean blnResult=selectMealMenuUK("MEDEVM");
					if(blnResult)
					{
						HtmlResult.passed(stepDescrp,strExpected,"Menu successfully launched-"+strMenuName);
					}
					else
					{
						HtmlResult.failed(stepDescrp,strExpected,"Menu button not found and not clicked-"+strMenuName);
					}
				}
				catch(Exception e)
				{
					HtmlResult.failed(stepDescrp,strExpected,"HtmlResult.failed to launch menu-MED EVM"+e);
				}
			}
			//*****************************************MED EVM BREAKFAST MENU******************************************
			else if(strMenuName.equalsIgnoreCase("BREAKFAST MED EVM")||
					strMenuName.equalsIgnoreCase("BREAKFAST MEDIUM EVM")||
					strMenuName.equalsIgnoreCase("BREAKFAST MED EVM MEAL")||
					strMenuName.equalsIgnoreCase("BREAKFAST MEDIUM MEAL")||
					strMenuName.equalsIgnoreCase("BREAKFAST MED EVM MENU")||
					strMenuName.equalsIgnoreCase("Breakfast Medium Meal"))
			{
				boolean FoundAndClicked=false;
				String strMealmenu="";
				System.out.println("Loading Breakfast med evm menu");	
				try{

					strOffersButton=getValueFromExcel("OFFERSMENU") ; 																					    //lunch screen once clicked so can be use to load lunch menu
					strHelperMenuButton=getValueFromExcel("LUNCHMENU");
					strMealmenu=getValueFromExcel("MEDEVM");
				}
				catch(Exception e)
				{
					HtmlResult.failed(stepDescrp,strExpected,"HtmlResult.failed to launch menu-"+strMenuName+e);
				}				
				FoundAndClicked=clickButton(strOffersButton);
				if(FoundAndClicked)
				{
					FoundAndClicked=clickButton(strHelperMenuButton);//lunch menu button
					if(FoundAndClicked)
					{
						FoundAndClicked=clickButton(strHelperMenuButton);//again clicking on lunch menu to load breakfast menu
						if(FoundAndClicked)
						{
							FoundAndClicked=clickButton(strMealmenu);//clicking on meal button
							if(FoundAndClicked)
							{
								HtmlResult.passed(stepDescrp,strExpected,"Menu launched successfully-"+strMenuName);
							}
							else
							{
								HtmlResult.failed(stepDescrp,strExpected,"Menu button not found-"+strMealmenu);
							}
						}
						else
						{
							HtmlResult.failed(stepDescrp,strExpected,"Lunch Menu button not found and not clicked hence cannot load breakfast menu");
						}
					}
					else
					{
						HtmlResult.failed(stepDescrp,strExpected,"Lunch menu button not found and not clicked");
					}
				}
				else
				{
					HtmlResult.failed(stepDescrp,strExpected,"Offers menu button not found and not clicked");
				}
			}
			//***************************************************dips menu*********************************
			else if(strMenuName.equalsIgnoreCase("DIPS")||strMenuName.equalsIgnoreCase("DIPS MENU"))
			{
				boolean FoundAndClicked=false;
				System.out.println("Loading dips menu");
				try 
				{
					strMenuButton=getValueFromExcel("DIPSMENU");
				} 
				catch (Exception e)
				{
					HtmlResult.failed(stepDescrp,strExpected,"HtmlResult.failed to launch menu-"+strMenuButton);
				}

				FoundAndClicked=clickButton(strMenuButton);
				if(FoundAndClicked)
				{
					HtmlResult.passed(stepDescrp,strExpected,"Menu successfully launched-"+strMenuButton);
				}
				else
				{
					HtmlResult.failed(stepDescrp,strExpected,"Menu button not found and not clicked-"+strMenuButton);
				}
			}
			//******************************************Big flavour wraps**************************************//		
			else if(strMenuName.equalsIgnoreCase("Big flavour wraps")||strMenuName.equalsIgnoreCase("Big flavour wraps menu"))
			{
				boolean FoundAndClicked=false;
				try{
					strMenuButton=getValueFromExcel("BIGFLAVOURWRAPS");
				}
				catch(Exception e)
				{
					HtmlResult.failed(stepDescrp,strExpected,"Unable to read properties file-"+e.getMessage());
				}
				FoundAndClicked=clickButton(strMenuButton);
				if(FoundAndClicked)
				{
					HtmlResult.passed(stepDescrp,strExpected,"Menu successfully launched-"+strMenuButton);
				}
				else
				{
					HtmlResult.failed(stepDescrp,strExpected,"Menu button not found and not clicked-"+strMenuButton);
				}
			}
			//******************************************MED Big flavour wraps**************************************//		
			else if(strMenuName.equalsIgnoreCase("Med Big flavour wraps")||strMenuName.equalsIgnoreCase("Med Big flavour wraps menu")
					||strMenuName.equalsIgnoreCase("Med evm Big flavour wraps")||strMenuName.equalsIgnoreCase("Med evm Big flavour wraps menu")
					||strMenuName.equalsIgnoreCase("Medium evm Big flavour wraps")||strMenuName.equalsIgnoreCase("Medium big flavour wraps"))
			{
				boolean FoundAndClicked=false;
				String strMainMealMenuButton="";
				try{
					strMenuButton=getValueFromExcel("BIGFLAVOURWRAPS");
					strMainMealMenuButton=getValueFromExcel("MED_BIGFLAVOURWRAPS");
				}
				catch(Exception e)
				{
					HtmlResult.failed(stepDescrp,strExpected,"Unable to read properties file-"+e.getMessage());
				}
				FoundAndClicked=clickButton(strMenuButton);
				if(FoundAndClicked)
				{
					FoundAndClicked=clickButton(strMainMealMenuButton);
					if (FoundAndClicked) 
					{
						HtmlResult.passed(stepDescrp, strExpected, "Menu successfully launched-" + strMenuName);
					}
					else
					{
						HtmlResult.failed(stepDescrp,strExpected,"Menu button not found and not clicked-"+strMainMealMenuButton);
					}
				}
				else
				{
					HtmlResult.failed(stepDescrp,strExpected,"Menu button not found and not clicked-"+strMenuButton);
				}
			}
			//***********************************LRG big flavour wraps*******************************************
			else if(strMenuName.equalsIgnoreCase("Lrg Big flavour wraps")||strMenuName.equalsIgnoreCase("Large Big flavour wraps")
					||strMenuName.equalsIgnoreCase("Large evm Big flavour wraps")||strMenuName.equalsIgnoreCase("Large evm Big flavour wraps menu"))
			{
				boolean FoundAndClicked=false;
				String strMainMealMenuButton="";
				try{
					strMenuButton=getValueFromExcel("BIGFLAVOURWRAPS");
					strMainMealMenuButton=getValueFromExcel("LRG_BIGFLAVOURWRAPS");
				}
				catch(Exception e)
				{
					HtmlResult.failed(stepDescrp,strExpected,"Unable to read properties file-"+e.getMessage());
				}
				FoundAndClicked=clickButton(strMenuButton);
				if(FoundAndClicked)
				{
					FoundAndClicked=clickButton(strMainMealMenuButton);
					if (FoundAndClicked) 
					{
						HtmlResult.passed(stepDescrp, strExpected, "Menu successfully launched-" + strMenuName);
					}
					else
					{
						HtmlResult.failed(stepDescrp,strExpected,"Menu button not found and not clicked-"+strMainMealMenuButton);
					}
				}
				else
				{
					HtmlResult.failed(stepDescrp,strExpected,"Menu button not found and not clicked-"+strMenuButton);
				}
			}
			//****************************************************breakfast employee*********************************************//		
			else if(strMenuName.equalsIgnoreCase("BREAKFAST EMP")||
					strMenuName.equalsIgnoreCase("BREAKFAST EMPLOYEE")||
					strMenuName.equalsIgnoreCase("BREAKFAST EMPLOYEE MENU"))
			{
				boolean FoundAndClicked=false;
				try {
					strMenuButton=getValueFromExcel("BREAKFASTEMP");
					strOffersButton=getValueFromExcel("OFFERSMENU");	
				} 
				catch (Exception e) 
				{
					HtmlResult.failed(stepDescrp,strExpected,"Unable to read properties file-"+e.getMessage());
				}
				FoundAndClicked=clickButton(strOffersButton);
				if(FoundAndClicked)
				{
					FoundAndClicked=clickButton(strMenuButton);
					if(FoundAndClicked)
					{
						HtmlResult.passed(stepDescrp,strExpected,"Menu launched successfully-"+strMenuButton);
					}
					else
					{
						HtmlResult.failed(stepDescrp,strExpected,"Menu button not found and not clicked-"+strMenuButton);
					}
				}
				else
				{
					HtmlResult.failed(stepDescrp,strExpected,"offers menu button not found and not clicked");
				}
			}
			//**************************************Med Employee*********************************************			
			else if(strMenuName.equalsIgnoreCase("MED EMPLOYEE")||
					strMenuName.equalsIgnoreCase("MEDIUM EMPLOYEE")||
					strMenuName.equalsIgnoreCase("MEDIUM EMPLOYEE MEAL")||
					strMenuName.equalsIgnoreCase("MEDIUM EVM EMPLOYEE")||
					strMenuName.equalsIgnoreCase("MEDIUM EVM EMPLOYEE MENU")||
					strMenuName.equalsIgnoreCase("MED EMP")||
					strMenuName.equalsIgnoreCase("Med Emp Lunch Meal"))
			{
				boolean FoundAndClicked=false;
				String strMainMenuButton="";
				try 
				{
					strOffersButton=getValueFromExcel("OFFERSMENU");	
					strMenuButton=getValueFromExcel("MEDEMPLYOEE");
					strMainMenuButton=getValueFromExcel("EMPLOYEEMEALS");
				} 
				catch (Exception e) 
				{
					HtmlResult.failed(stepDescrp,strExpected,"Unable to read property file-"+e.getMessage());
				}

				FoundAndClicked=clickButton(strOffersButton);
				if(FoundAndClicked)
				{
					FoundAndClicked=clickButton(strMainMenuButton);
					if(FoundAndClicked)
					{
						HtmlResult.passed(stepDescrp,strExpected,"Menu launched successfully-"+strMenuButton);
					}
					else
					{
						HtmlResult.failed(stepDescrp,strExpected,"Menu button not found and not clicked-"+strMainMenuButton);
					}
				}
				else
				{
					HtmlResult.failed(stepDescrp,strExpected,"offers menu button not found and not clicked");
				}
			}
			//**********************************lrg employee********************************************************
			else if(strMenuName.equalsIgnoreCase("LRG EMPLOYEE")||
					strMenuName.equalsIgnoreCase("LARGE EMPLOYEE")||
					strMenuName.equalsIgnoreCase("LARGE EVM EMPLOYEE")||
					strMenuName.equalsIgnoreCase("LARGE EMPLOYEE MEAL")||
					strMenuName.equalsIgnoreCase("LRG EMP")||
					strMenuName.equalsIgnoreCase("Lrg Emp Lunch Meal"))
			{
				boolean FoundAndClicked=false;
				String strMainMenuButton="";

				try 
				{
					strOffersButton=getValueFromExcel("OFFERSMENU");	
					strMenuButton=getValueFromExcel("LRGEMPLOYEE");
					strMainMenuButton=getValueFromExcel("EMPLOYEEMEALS");
				} 
				catch (Exception e) 
				{
					HtmlResult.failed(stepDescrp,strExpected,"Unable to read property file-"+e.getMessage());
				}

				FoundAndClicked=clickButton(strOffersButton);
				if(FoundAndClicked)
				{
					FoundAndClicked=clickButton(strMainMenuButton);
					if(FoundAndClicked)
					{
						FoundAndClicked=clickButton(strMenuButton);
						if(FoundAndClicked)
						{
							HtmlResult.passed(stepDescrp,strExpected,"Menu launched successfully-"+strMenuName);
						}
						else
						{
							HtmlResult.failed(stepDescrp,strExpected,"Menu button not found and not clicked-"+strMenuButton);
						}
					}
					else
					{
						HtmlResult.failed(stepDescrp,strExpected,"Menu button not found and not clicked-"+strMainMenuButton);
					}
				}
				else
				{
					HtmlResult.failed(stepDescrp,strExpected,"offers menu button not found and not clicked");
				}
			}
			//********************************************MED EMP FLAVOUR WRAPS***************************
			else if(strMenuName.equalsIgnoreCase("MED EMP FLAVOUR WRAPS")||
					strMenuName.equalsIgnoreCase("Med Emp Big Flavour Wraps") ||strMenuName.equalsIgnoreCase("Med Flavour Emp"))
			{
				boolean FoundAndClicked=false;
				try 
				{
					strOffersButton=getValueFromExcel("OFFERSMENU");	
					strMenuButton=getValueFromExcel("EMP_FLAVWRAPS");
				} 
				catch (Exception e) 
				{
					HtmlResult.failed(stepDescrp,strExpected,"Unable to read property file-"+e.getMessage());
				}

				FoundAndClicked=clickButton(strOffersButton);
				if(FoundAndClicked)
				{
					FoundAndClicked=clickButton(strMenuButton);
					if(FoundAndClicked)
					{
						HtmlResult.passed(stepDescrp,strExpected,"Menu launched successfully-"+strMenuName);
					}
					else
					{
						HtmlResult.failed(stepDescrp,strExpected,"Menu button not found and not clicked-"+strMenuName);
					}
				}
				else
				{
					HtmlResult.failed(stepDescrp,strExpected,"Offers button not found and not clicked");
				}	
			}
			//***************************LRG EMP WRAPS************************************************
			else if(strMenuName.equalsIgnoreCase("LRG EMP FLAVOUR WRAPS")||
					strMenuName.equalsIgnoreCase("Lrg Emp Big Flavour Wraps")
					||strMenuName.equalsIgnoreCase("Lrg Flavour Emp"))
			{
				boolean FoundAndClicked=false;
				String strMainMenuButton="";
				try 
				{
					strOffersButton=getValueFromExcel("OFFERSMENU");	
					strMenuButton=getValueFromExcel("EMP_FLAVWRAPS");
					strMainMenuButton=getValueFromExcel( "LRGEMP_FLAVWRAPS");
				} 
				catch (Exception e) 
				{
					HtmlResult.failed(stepDescrp,strExpected,"Unable to read property file-"+e.getMessage());
				}

				FoundAndClicked=clickButton(strOffersButton);
				{
					if(FoundAndClicked)
					{
						FoundAndClicked=clickButton(strMenuButton);
						if(FoundAndClicked)
						{
							FoundAndClicked=clickButton(strMainMenuButton);
							if(FoundAndClicked)
							{
								HtmlResult.passed(stepDescrp,strExpected,"Menu launched successfully-"+strMenuName);
							}
							else
							{
								HtmlResult.failed(stepDescrp,strExpected,"Menu button not found and not clicked-"+strMainMenuButton);
							}
						}
						else
						{
							HtmlResult.failed(stepDescrp,strExpected,"Menu button not found and not clicked-"+strMenuButton);
						}
					}
					else
					{
						HtmlResult.failed(stepDescrp,strExpected,"Menu button not found and not clicked-"+strOffersButton);
					}
				}	
			}
			else
			{
				HtmlResult.failed(stepDescrp,strExpected,"Wrong value HtmlResult.passed from designer-"+strMenuName+"-is not a valid menu");
			}
		}

		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}


	//**********************************************CLICK FUNCTION WITH REPORTING BUT NOT A COMPONENT*************************//
	// Created by:yogesh 
	// Purpose: Find and click the button and also add events to the report
	// Date=28/07/2016
	public boolean OrderProductItem(Map<String,String> input)
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

	//**********************************internal method called by menuSelection**************************************// 	
	private boolean selectMealMenuUK(String selectMeal) 
	{
		String strMeal=selectMeal;
		boolean blnImageFound=false;
		boolean blnImageClick=false;

		String strMealButton =getValueFromExcel(strMeal);
		String strOffersButton=getValueFromExcel("OFFERSMENU");
		blnImageFound=eggUIDriver.imageFound(strOffersButton);

		if(blnImageFound)
		{
			clickButton(strOffersButton);
			blnImageFound=eggUIDriver.imageFound(strMealButton);
			if(blnImageFound)
			{
				blnImageClick=eggUIDriver.clickImage(strMealButton);
			}
		}
		return blnImageClick;	
	}

	//****************************************order meal ************************************************

	public void orderMeal(Map<String, String> input)
	{
		//core product details
		String strProductName=input.get("mealProductName");

		//side product details
		String strSideItemName=input.get("mealSideitemName");

		//drink product details
		String strDrinkItemName=input.get("mealDrinkitemName");

		//optional dip choice details
		String strDipChoice=input.get("dipChoice");

		//expected and step desc. strings
		String strStepDesc= "To place Meal order";

		//boolean variable to verify eggplant tool actions
		boolean Found=false;

		//when side item is not provided
		if( strSideItemName==null || strSideItemName.equals("") )
		{
			//when meal is not with dip choice
			if(strDipChoice==null || strDipChoice.equals("") )
			{
				String strExpected="Meal order should be placed successfully-' "+strProductName+","+strDrinkItemName+" '";
				Found=clickButton(strProductName);
				if(Found)
				{
					Found=clickButton(strDrinkItemName);
					if(Found)
					{
						HtmlResult.passed(strStepDesc, strExpected, "Order placed successfully-'"+strProductName+","+strDrinkItemName+"'");//log pass
					}
					else
					{
						HtmlResult.failed(strStepDesc, strExpected, "Button not found-' "+strDrinkItemName+" '");//log fail no meal drink product image found
						//call recovery() method
					}
				}
				else
				{
					HtmlResult.failed(strStepDesc, strExpected, "Button not found-' "+strProductName+" '");//log fail no meal drink product image found
					//call recovery() method
				}
			}

			//when meal is with dip choice
			else if(strDipChoice.length()>0)
			{

				String strExpected="Meal order should be placed successfully-' "+strProductName+","+strDrinkItemName+","+strDipChoice+" '.";

				Found=clickButton(strProductName);
				if(Found)
				{
					Found=clickButton(strDrinkItemName);
					if(Found)
					{
						Found=clickButton(strDipChoice);
						if(Found)
						{
							HtmlResult.passed(strStepDesc, strExpected, "Order placed successfully-'"+strProductName+","+strDrinkItemName+","+strDipChoice+"'");//log pass
						}
						else
						{
							HtmlResult.failed(strStepDesc, strExpected, "Button not found-' "+strDipChoice+" '");//log fail no meal drink product image found
							//call recovery() method
						}
					}
					else
					{
						HtmlResult.failed(strStepDesc, strExpected, "Button not found-' "+strDrinkItemName+" '");//log fail no meal drink product image found
						//call recovery() method
					}
				}
				else
				{
					HtmlResult.failed(strStepDesc, strExpected, "Button not found-' "+strProductName+" '");//log fail no meal drink product image found
					//call recovery() method
				}
			}
			//other cases are invalid
			else
			{
				String strExpected="Order should be placed successfully";
				HtmlResult.failed(strStepDesc, strExpected, "Invalid data-"+strDipChoice);//log fail no meal drink product image found
				//call recovery() method//log error
			}
		}

		//when side item provided
		else if(strSideItemName.length()>0)
		{
			String ClearChoiceButton = getValueFromExcel("CLEARCHOICE");
			String ClearChoicePopupOkbutton = getValueFromExcel("OK");
			
			// when no dip choice
			if(strDipChoice==null || strDipChoice.equals(""))
			{
				String strExpected="Order should be placed-'"+strProductName+","+strDrinkItemName+","+strSideItemName+"'";
				
				Found=clickButton(strProductName);
				if(Found)
				{
					Found=clickButton(ClearChoiceButton);
					
					if(GlobalMenuNamme.equalsIgnoreCase("Med Big flavour wraps")||
							GlobalMenuNamme.equalsIgnoreCase("Med Big flavour wraps menu")||
							GlobalMenuNamme.equalsIgnoreCase("Med evm Big flavour wraps")||
							GlobalMenuNamme.equalsIgnoreCase("Med evm Big flavour wraps menu")||
							GlobalMenuNamme.equalsIgnoreCase("Medium evm Big flavour wraps")||
							GlobalMenuNamme.equalsIgnoreCase("Medium big flavour wraps")||
							GlobalMenuNamme.equalsIgnoreCase("Medium evm big flavour wraps")||
							GlobalMenuNamme.equalsIgnoreCase("Lrg Big flavour wraps")||
							GlobalMenuNamme.equalsIgnoreCase("Lrg Big flavour wraps menu")||
							GlobalMenuNamme.equalsIgnoreCase("Lrg evm Big flavour wraps")||
							GlobalMenuNamme.equalsIgnoreCase("Lrg evm Big flavour wraps menu")||
							GlobalMenuNamme.equalsIgnoreCase("Lrg evm Big flavour wraps")||
							GlobalMenuNamme.equalsIgnoreCase("Lrg big flavour wraps")||
							GlobalMenuNamme.equalsIgnoreCase("Large evm Big flavour wraps"))
					{

						clickButton(ClearChoicePopupOkbutton);
					}
				
					if(Found)
					{
						Found=clickButton(strDrinkItemName);
						if(Found)
						{
							Found=clickButton(strSideItemName);
							if(Found)
							{
								HtmlResult.passed(strStepDesc, strExpected, "Order placed successfully-'"+strProductName+","+strDrinkItemName+","+strSideItemName+"'");//log pass
							}
							else
							{
								HtmlResult.failed(strStepDesc, strExpected, "Button not found-"+strSideItemName);//log fail no meal drink product image found
								//call recovery() method//log error
							}
						}
						else
						{
							HtmlResult.failed(strStepDesc, strExpected, "Button not found-"+strDrinkItemName);//log fail no meal drink product image found
							//call recovery() method//log error
						}
					}
					else
					{
						HtmlResult.failed(strStepDesc, strExpected, "Button not found- "+ClearChoiceButton);
					}
				}
				else
				{
					HtmlResult.failed(strStepDesc, strExpected, "Button not found-"+strProductName);//log fail no meal drink product image found
					//call recovery() method//log error
				}
				
			}

			//when meal is with dip choice
			else if(strDipChoice.length()>0)
			{
				String strExpected="Order should be placed-' "+strProductName+","+strDrinkItemName+","+strSideItemName+","+strDipChoice+" '";
				Found=clickButton(strProductName);
				if(Found)
				{
					Found = clickButton(ClearChoiceButton);
					if(Found)
					{
						if(GlobalMenuNamme.equalsIgnoreCase("Med Big flavour wraps")||
								GlobalMenuNamme.equalsIgnoreCase("Med Big flavour wraps menu")||
								GlobalMenuNamme.equalsIgnoreCase("Med evm Big flavour wraps")||
								GlobalMenuNamme.equalsIgnoreCase("Med evm Big flavour wraps menu")||
								GlobalMenuNamme.equalsIgnoreCase("Medium evm Big flavour wraps")||
								GlobalMenuNamme.equalsIgnoreCase("Medium big flavour wraps")||
								GlobalMenuNamme.equalsIgnoreCase("Medium evm big flavour wraps")||
								GlobalMenuNamme.equalsIgnoreCase("Lrg Big flavour wraps")||
								GlobalMenuNamme.equalsIgnoreCase("Lrg Big flavour wraps menu")||
								GlobalMenuNamme.equalsIgnoreCase("Lrg evm Big flavour wraps")||
								GlobalMenuNamme.equalsIgnoreCase("Lrg evm Big flavour wraps menu")||
								GlobalMenuNamme.equalsIgnoreCase("Lrg evm Big flavour wraps")||
								GlobalMenuNamme.equalsIgnoreCase("Lrg big flavour wraps")||
								GlobalMenuNamme.equalsIgnoreCase("Large evm Big flavour wraps"))
						{

							eggUIDriver.wait(5);
						}

						Found=clickButton(strDrinkItemName);
						if(Found)
						{
							Found=clickButton(strSideItemName);
							if(Found)
							{
								Found=clickButton(strDipChoice);
								if(Found)
								{
									HtmlResult.passed(strStepDesc, strExpected, "Order placed successfully-' "+strProductName+","+strDrinkItemName+","+strSideItemName+","+strDipChoice+" '.");//log pass
								}
								else
								{
									HtmlResult.failed(strStepDesc, strExpected, "Button not found-"+strDipChoice);//log fail no meal drink product image found
									//call recovery() method//log error
								}
							}
							else
							{
								HtmlResult.failed(strStepDesc, strExpected, "Button not found-"+strSideItemName);//log fail no meal side product image found
								//call recovery() method//log error
							}
						}
						else
						{
							HtmlResult.failed(strStepDesc, strExpected, "Button not found-"+strDrinkItemName);//log fail no meal drink product image found
							//call recovery() method//log error
						}
					}
					else
					{
						HtmlResult.failed(strStepDesc, strExpected, "Button not found- "+ClearChoiceButton);
					}
				}
				else
				{
					HtmlResult.failed(strStepDesc, strExpected, "Button not found- "+strProductName);//log fail no meal  product image found
					//call recovery() method//log error
				}


			}
			}

			//other cases are invalid
			else
			{
				//log error
			}
		}
		


	//********************************Verify Sales panel Data***********

	public void verifySalesPanel(Map <String,String> input) 
	{
		
		String ExpectedSalesPanelsData=input.get("expectedSalesPanelData");
		//String VerificationFlag1=input.get("verificationFlag");
		String VerificationFlag=input.get("verificationFlag");
		String strStepDesc="To verify sales panel";			
		String strExpected="";

		String SalesPanelActualData=ReadSalesPanel();
		String ActualSalesPanelData=SalesPanelActualData.replaceAll("\n", ",");
		ActualSalesPanelData=ActualSalesPanelData.replace("\t"," ");
		String[] ExpectedSalesPanelData= ExpectedSalesPanelsData.split("#");

		if (VerificationFlag.equalsIgnoreCase("YES")) 
		{
			String Actual=" Actual sales panel data is-";
			String Expected=" Expected Data is-";
			
			for(String ExpectedData:ExpectedSalesPanelData)
			{
				strExpected = "Sales Panel should contain expected data-' "+ExpectedData+" '";
				ExpectedData=ExpectedData.trim();
				if(ActualSalesPanelData.contains(ExpectedData))
				{
					String ActualSalesPanel=Actual+ActualSalesPanelData+"\n";
					String ExpectedSalesPanel =Expected+ExpectedData+"\n";
					HtmlResult.passed(strStepDesc,strExpected,"Verification flag-"+VerificationFlag+"\n"+ActualSalesPanel+ExpectedSalesPanel+"Hence Passed");
				}
				else
				{
					String ActualSalesPanel=Actual+ActualSalesPanelData+"\n";
					String ExpectedSalesPanel =Expected+ExpectedData+"\n";
					HtmlResult.failed(strStepDesc,strExpected,"Verification flag-"+VerificationFlag+"\n"+ActualSalesPanel+ExpectedSalesPanel+"Hence Failed");
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
				strExpected = "Sales Panel should contain expected data-' "+ExpectedData+" '";
				if(ActualSalesPanelData.contains(ExpectedData))
				{
					String ActualSalesPanel=Actual+ActualSalesPanelData+"\n";
					String ExpectedSalesPanel =Expected+ExpectedData+"\n";
					HtmlResult.failed(strStepDesc,strExpected,"Verification flag-"+VerificationFlag+"\n"+ActualSalesPanel+ExpectedSalesPanel+"Hence Failed");
				}
				else
				{
					String ActualSalesPanel=Actual+ActualSalesPanelData+"\n";
					String ExpectedSalesPanel =Expected+ExpectedData+"\n";
					HtmlResult.passed(strStepDesc,strExpected,"Verification flag-"+VerificationFlag+"\n"+ActualSalesPanel+ExpectedSalesPanel+"Hence Passed");
				}
			}
		}
		else
		{
			strExpected="Should be able to verify Sales Panel";
			HtmlResult.failed(strStepDesc,strExpected,"Wrong value passed from designer-"+VerificationFlag+"- should be Yes or no only");
		}
	}

	//***************************************************************

	//*****************************************************************************************************
	public void verifySalesPanel_SingleItem(Map<String,String> input)
	{
		String strExpectedMealProductName = input.get("SalesPanelName");
		String[] strArrExpectedData = strExpectedMealProductName.split("#");

		//new approach
		String strActualData = readSalesPanelLineByLine(strExpectedMealProductName);
		
		String[] strArrActualData=strActualData.split("\n");

		//reporting string variables
		String StepDesc="To verify Sales panel data";
		String strExpected="Order should be present in sales panel-' "+strExpectedMealProductName;


		//boolean variable to verify actions
		String VerificationStatus = verifyMeal(strArrExpectedData,strArrActualData);

		//matching expected with actual normally
		if(VerificationStatus.equals("passed"))
		{
			HtmlResult.passed(StepDesc, strExpected, "Order present in sales panel-' "+strActualData+"' ");
		}
		else if(VerificationStatus.equals("warning"))
		{
			HtmlResult.warning(StepDesc, strExpected, "Order present in sales panel-' "+strActualData+"' (reading with text search technique)");
		}
		else//if previous verification failed then verify with on contrast setting
		{
			//actual sales panel details with 'contrast' settings		
			strActualData=ReadSalesPanel();
			String[] strArrActualDataContrast=strActualData.split("\n");

			String VerificationFlag =verifyMeal(strArrExpectedData,strArrActualDataContrast);

			if(VerificationFlag.equals("passed"))
			{
				HtmlResult.passed(StepDesc, strExpected, "Order present in sales panel-' "+strActualData+"'");
			}
			else if(VerificationStatus.equals("warning"))
			{
				HtmlResult.warning(StepDesc, strExpected, "Order present in sales panel-' "+strActualData+"' (reading with off contrast settings)");
			}
			else 
			{
				HtmlResult.failed(StepDesc, strExpected, "Order present in sales panel-' "+strActualData+"' (reading with off contrast settings)");
			}
		}
	}
	//**************************************verify sales panel******************************************************

	public void verifySalesPanel_Meal(Map<String,String> input)
	{
		//expected sales panel name details

		String strExpectedMealProductName = input.get("strExpectedMealProductName");
		String strExpectdMealDrinkName = input.get("strExpectdMealDrinkName");
		String strExpectedMealSideItemName = input.get("strExpectedMealSideItemName");
		String strExpectedDipChoiceName=input.get("strExpectedDipChoiceName");
		String VerificationStatus = "";
		
		//reporting string variables
		String StepDesc="To verify Sales panel data";
		String strExpected="Order should be present in sales panel-' "+strExpectedMealProductName+","+strExpectdMealDrinkName+","+strExpectedMealSideItemName+" '";

		if(strExpectedMealProductName.length()>=3)
		{
			// verification with text searching technique
			ArrayList<String> ArrListActualData = new  ArrayList<String>();
			if(strExpectedDipChoiceName==null||strExpectedDipChoiceName.equals(""))
			{
				if(strExpectedMealSideItemName==null||strExpectedMealSideItemName.equals(""))
				{
					strExpected="Order should be present in sales panel-' "+strExpectedMealProductName+","+strExpectdMealDrinkName+" '";
					String[] strArrExpectedData = {strExpectedMealProductName,strExpectdMealDrinkName};
					ArrListActualData = readSalesPanel(strArrExpectedData);
					String[] strArrActualData = ArrListActualData.toArray(new String[ArrListActualData.size()]);
					VerificationStatus=verifyMeal(strArrExpectedData,strArrActualData);
				}
				else
				{
					strExpected="Order should be present in sales panel-' "+strExpectedMealProductName+","+strExpectdMealDrinkName+","+strExpectedMealSideItemName+" '";
					String[] strArrExpectedData = {strExpectedMealProductName,strExpectdMealDrinkName,strExpectedMealSideItemName};
					ArrListActualData = readSalesPanel(strArrExpectedData);
					String[] strArrActualData = ArrListActualData.toArray(new String[ArrListActualData.size()]);
					VerificationStatus=verifyMeal(strArrExpectedData,strArrActualData);
				}	
			}
			else if(strExpectedDipChoiceName.length()>0)
			{
				if(strExpectedMealSideItemName==null||strExpectedMealSideItemName.equals(""))
				{
					strExpected="Order should be present in sales panel-' "+strExpectedMealProductName+","+strExpectdMealDrinkName+","+strExpectedDipChoiceName+" '";
					String[] strArrExpectedData = {strExpectedMealProductName,strExpectdMealDrinkName,strExpectedDipChoiceName};
					ArrListActualData = readSalesPanel(strArrExpectedData);
					String[] strArrActualData = ArrListActualData.toArray(new String[ArrListActualData.size()]);
					VerificationStatus=verifyMeal(strArrExpectedData,strArrActualData);
				}
				else
				{
					strExpected="Order should be present in sales panel-' "+strExpectedMealProductName+","+strExpectdMealDrinkName+","+strExpectedMealSideItemName+","+strExpectedDipChoiceName+" '";
					String[] strArrExpectedData = {strExpectedMealProductName,strExpectdMealDrinkName,strExpectedMealSideItemName,strExpectedDipChoiceName};
					ArrListActualData = readSalesPanel(strArrExpectedData);
					String[] strArrActualData = ArrListActualData.toArray(new String[ArrListActualData.size()]);
					VerificationStatus=verifyMeal(strArrExpectedData,strArrActualData);
				}
			}

			//matching expected
			if(VerificationStatus.equals("passed"))
			{
				HtmlResult.passed(StepDesc, strExpected, "Order present in sales panel-' "+ArrListActualData.toString()+"' ");
			}
			else if(VerificationStatus.equals("warning"))
			{
				HtmlResult.warning(StepDesc, strExpected, "Order present in sales panel-' "+ArrListActualData.toString()+"' (with text search technique)");
			}
			//if previous verification failed then verify with contrast settings
			else
			{
				//actual sales panel details with 'normal contrast' settings		
				String strActualData=ReadSalesPanel();
				String[] strArrActualDataWithContrast=strActualData.split("\n");
			
				if(strExpectedDipChoiceName==null||strExpectedDipChoiceName.equals(""))
				{
					if(strExpectedMealSideItemName==null||strExpectedMealSideItemName.equals(""))
					{
						String[] strArrExpectedData = {strExpectedMealProductName,strExpectdMealDrinkName};
						VerificationStatus=verifyMeal(strArrExpectedData,strArrActualDataWithContrast);
					}
					else
					{
						String[] strArrExpectedData = {strExpectedMealProductName,strExpectdMealDrinkName,strExpectedMealSideItemName};
						VerificationStatus=verifyMeal(strArrExpectedData,strArrActualDataWithContrast);
					}	
				}
				else if(strExpectedDipChoiceName.length()>0)
				{
					if(strExpectedMealSideItemName==null||strExpectedMealSideItemName.equals(""))
					{
						String[] strArrExpectedData = {strExpectedMealProductName,strExpectdMealDrinkName,strExpectedDipChoiceName};
						VerificationStatus=verifyMeal(strArrExpectedData,strArrActualDataWithContrast);
					}
					else
					{
						String[] strArrExpectedData = {strExpectedMealProductName,strExpectdMealDrinkName,strExpectedMealSideItemName,strExpectedDipChoiceName};
						VerificationStatus=verifyMeal(strArrExpectedData,strArrActualDataWithContrast);
					}
				}
				
				if(VerificationStatus.equals("passed"))
				{
					HtmlResult.passed(StepDesc, strExpected, "Order present in sales panel-' "+strActualData+"' ");
				}
				else if(VerificationStatus.equals("warning"))
				{
					HtmlResult.warning(StepDesc, strExpected, "Order present in sales panel-' "+strActualData+"' (with off contrast settings)");
				}
				else
				{
					HtmlResult.failed(StepDesc, strExpected, "Order present in sales panel-' "+strActualData+"' (with off contrast settings)");
				}
			}
		}
		else
		{
			HtmlResult.failed(StepDesc, strExpected, "Expected Meal Product name is empty or invalid-' "+strExpectedMealProductName+"' ");
		}
	}
	
	public ArrayList<String> readSalesPanel(String[] ExpectedSalesPanelOrders)
	{
		ArrayList<String> ActualOrders = new ArrayList<String>();
		try
		{
			for(String ExpectedOrder: ExpectedSalesPanelOrders)
			{
				System.out.println("");
				String ActualOrder = readSalesPanelLineByLine(ExpectedOrder);
				String [] arrStringVerification = ActualOrder.split("\n");
				
				if(arrStringVerification.length<=1)
				{
					ActualOrder = ActualOrder.replaceAll("[0-9]{1,}[.]{1}[0-9]{1,}","");
					ActualOrder = ActualOrder.replaceAll("\n\n", "\n");
					ActualOrders.add(ActualOrder);
				}
				else if(arrStringVerification.length==2)
				{
					String Product = arrStringVerification[0];
					String ModifiedOrder = Product.
							replaceAll("[0-9]{1,}[.]{1}[0-9]{1,}","").
							replaceAll("\n\n", "\n");
					ActualOrders.add(ModifiedOrder);
				}
				else if(arrStringVerification.length>=3)
				{
					ActualOrders = new ArrayList<String>(Arrays.asList(arrStringVerification));
					break;
				}
			}
			
			return ActualOrders;
		}
		catch(Exception e)
		{
			return null;
		}
	}

	public boolean findTextOnSalesPanel(String TextToSearch)
	{

		Rectangle OriginalRectangle = null;
		Dimension ScreenDimensions = eggUIDriver.remoteScreenSize(); // get size of current SUT connection
		if(ScreenDimensions.width==1600)
		{
			OriginalRectangle = new Rectangle(0,0,800,600);
		}
		else
		{
			OriginalRectangle = new Rectangle(ScreenDimensions);
		}
		
		// after successful search ,set the search rectangle to entire screen
		String SalesPanelData = "";
		try
		{
			ArrayList<Integer> TopLeft = eggUIDriver.ImageLocation("LeftTopCorner"); //location top left of sales panel
			int x1 = TopLeft.get(0); 
			int y1 = TopLeft.get(1);
			
			ArrayList<Integer> BottomRight = eggUIDriver.ImageLocation("RightBottomCorner"); //location of right bottom of sales panel
			int x2 = BottomRight.get(0);
			int y2 = BottomRight.get(1);
			
			Rectangle SearchRectangle = new Rectangle(x1, y1, x2-x1, y2-y1); //setting search rectangle to sales panel using above cordinates
		
			eggUIDriver.setSearchRectangle(SearchRectangle);
			TextToSearch = TextToSearch.trim();
			List<Point> TextLocation = eggUIDriver.getEveryTextLocation(TextToSearch);
			
			if(TextLocation.size()!=0)
			{
				eggUIDriver.setSearchRectangle(OriginalRectangle);
				return true;
			}
			else
			{
				SalesPanelData = eggUIDriver.readText(SearchRectangle);
				eggUIDriver.setSearchRectangle(OriginalRectangle);
				if(SalesPanelData.replaceAll(" ", "").contains(TextToSearch.replaceAll(" ", "")))
				{
					return true;
				}
				else 
				{
					return false;
				}
			}	
			
			
		}
		catch(Exception e)
		{
			eggUIDriver.setSearchRectangle(OriginalRectangle);
			return false;
		}

	}

	//Updated by shruti on 4-5-2017
		public String readSalesPanelLineByLine(String TextToSearch)
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
				FontSize = 15;
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
			try
			{
				String OverallSalesPanelData = "";
				ArrayList<Integer> TopLeft = eggUIDriver.ImageLocation("LeftTopCorner"); //location top left of sales panel
				int x1 = TopLeft.get(0); 
				int y1 = TopLeft.get(1);
				
				ArrayList<Integer> BottomRight = eggUIDriver.ImageLocation("RightBottomCorner"); //location of right bottom of sales panel
				int x2 = BottomRight.get(0);
				int y2 = BottomRight.get(1);
				
				Rectangle SearchRectangle = new Rectangle(x1, y1, x2-x1, y2-y1); //setting search rectangle to sales panel using above cordinates
				
				int LengthStart = x1+StartPoint; // starting x1 point for text searching and reading by using x1 of TopLeft
				int LegthEnd = x1+SalesPanelSize; // to set end point on x direction i.e, x2 using SalesPanelSize of current SUT connection
				int Width =  LegthEnd-LengthStart; // calculating width for searching text in a rectangle
				
				String[] arrTextsToSearch = TextToSearch.split("#");
				for(String Texts : arrTextsToSearch)
				{
					eggUIDriver.setSearchRectangle(SearchRectangle);
					Texts = Texts.trim();
					List<Point> TextLocation = eggUIDriver.getEveryTextLocation(Texts);
					if(TextLocation.size()!=0)
					{
						if(TextLocation.size()==1)
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
							for(Point Location:TextLocation)
							{
								OverallSalesPanelData="";
								int TopLeftY = Location.y-ReadingError; // to make reading better
								int height = FontSize;
								Rectangle TextRectangle = new Rectangle(LengthStart, TopLeftY, Width, height); // creating rectangle over text
								String SalesPanelOrder = eggUIDriver.readText(TextRectangle) ; // read rectangle
								OverallSalesPanelData = OverallSalesPanelData.concat(SalesPanelOrder+"\n");
								OverallSalesPanelData.replaceAll("\t", "\n");
								if(OverallSalesPanelData.contains(TextToSearch))
								{
									break;
								}
							}
						}
						
					}
					else
					{
						OverallSalesPanelData = eggUIDriver.readText(SearchRectangle).replace("I.I.I", "").replace("I.\tI.\tI", "");
						break;
					}	
				}
		
				eggUIDriver.setSearchRectangle(OriginalRectangle);
				OverallSalesPanelData = OverallSalesPanelData.replaceAll("\t", "\n");
				return OverallSalesPanelData;
			
			}
			catch(Exception e)
			{
				eggUIDriver.setSearchRectangle(OriginalRectangle);
				return "";
			}
		}

	public String verifyMeal(String[]Expected,String[]Actual)
	{	
		int PassedCounter = 0;
		int WarningCounter = 0;
		List<String> ExpectedArrayList = new ArrayList<String>();
		List<String> ActualArrayList = new ArrayList<String>();
		
		//getting threshold
		double Threshold = Double.parseDouble(getValueFromExcel("SalesPanelOrderThreshold").trim());
		
		// preparing arraylist of expected data 
		for(int Index = 0;Index<Expected.length; Index++)
		{
			if(Expected[Index].contains("#"))
			{
				String[] MainNameAndSubName = Expected[Index].split("#");

				for(String ExpectedName : MainNameAndSubName)
				{
					ExpectedArrayList.add(ExpectedName.trim());
				}
			}
			else
			{
				ExpectedArrayList.add(Expected[Index].trim());
			}
		}
		
		//preparing arraylist of actual data
		for(int Index = 0;Index<Actual.length; Index++)
		{
			if(Actual[Index].contains("#"))
			{
				String[] MainNameAndSubName = Actual[Index].split("#");

				for(String ExpectedName : MainNameAndSubName)
				{
					if(ExpectedName.length()>0)
					{
						ActualArrayList.add(ExpectedName.trim());
					}
					
				}
			}
			else
			{
				if(Actual[Index].length()>0)
				{
					ActualArrayList.add(Actual[Index].trim());
				}
			}
		}
		
		try
		{
			if(ExpectedArrayList.size()>0 && ActualArrayList.size()>0 )
			{
				int LoopLimit = ActualArrayList.size();
				for(String ExpectedData : ExpectedArrayList)
				{
					
					if(ExpectedData.equals(""))
					{
						PassedCounter++;
						continue;
					}
					
					ExpectedData=ExpectedData.trim();	
					
					for(int Index=0;Index<LoopLimit;Index++)
					{
						String ActualData = ActualArrayList.get(Index).trim();
						ActualData = ActualData.replaceAll("[0-9]{1,}[.]{1}[0-9]{1,}", "").trim();
						if(ExpectedData.equals(ActualData))// if exact match is found increase passing counter by 1
						{
							PassedCounter++;
							break;
						}
						else
						{
							System.out.println("");
							double Result = getMatchingResult(ExpectedData,ActualData);
							if(Result>=Threshold)
							{
								WarningCounter++;
								break;
							}
						}
					}
				}
				
				if(PassedCounter==ExpectedArrayList.size())// passing counter equals expected length return pass as exact match found
				{
					return "passed";
				}
				else if(WarningCounter>0 && PassedCounter>0) // if warning counter is incremented then its warning return warning
				{
					return "warning";
				}
				else 
				{
					return "failed"; // else return failed
				}	
			}
		}
		catch(Exception e)
		{
			return "failed";
		}
		return "failed";
		
	}

	public double getMatchingResult(String Expected ,String Actual)
	{
		int MatchingCounter = 0;
		int LoopLimit = 0;
		double Result1 = 0.0;
		double Result2 = 0.0;
		double Result3 = 0.0;
		double Threshold = Double.parseDouble(getValueFromExcel("SalesPanelOrderThreshold").trim());
		
		try
		{
			char[] ExpectedCharArr = Expected.toCharArray();
			char[] ActualCharArr = Actual.toCharArray();
			
			if(ExpectedCharArr.length>ActualCharArr.length)
			{
				LoopLimit = ActualCharArr.length;
			}
			else if(ActualCharArr.length>ExpectedCharArr.length)
			{
				LoopLimit = ExpectedCharArr.length;
			}
			else 
			{
				LoopLimit = ExpectedCharArr.length;
			}
			
			for(int Index=0;Index < LoopLimit;Index++)
			{		
				if(ExpectedCharArr[Index]==ActualCharArr[Index])
				{
					MatchingCounter++;
				}
				else
				{
					break;
				}
			}
			
			Result1 = (double)MatchingCounter/ExpectedCharArr.length;
			
			int newExpectedCharArrLength = 0;
			
			if(Result1<Threshold)
			{
				MatchingCounter = 0;
				String strExpected = Expected.replaceAll(" ", "");
				String strActual = Actual.replaceAll(" ", "");
				char[] newExpectedCharArr = strExpected.toCharArray();
				char[] newActualCharArr = strActual.toCharArray();
			
				newExpectedCharArrLength = newExpectedCharArr.length;
				
				if(newExpectedCharArr.length>newActualCharArr.length)
				{
					LoopLimit = newActualCharArr.length;
				}
				else if(newActualCharArr.length>newExpectedCharArr.length)
				{
					LoopLimit = newExpectedCharArr.length;
				}
				else 
				{
					LoopLimit = newExpectedCharArr.length;
				}
				
				for(int Index=0;Index < LoopLimit;Index++)
				{		
					if(newExpectedCharArr[Index]==newActualCharArr[Index])
					{
						MatchingCounter++;
					}
					else
					{
						break;
					}
				}	
				
				int ReversenewExpectedCharArrSize = 0;
				Result2 = (double)MatchingCounter/newExpectedCharArrLength;
				
				if(Result2<Threshold)
				{
					MatchingCounter = 0;
					String newStrExpected = Expected.replaceAll(" ", "");
					String newStrActual = Actual.replaceAll(" ", "");
					char[] newModifiedExpectedCharArr = newStrExpected.toCharArray();
					char[] newModifiedActualCharArr = newStrActual.toCharArray();
					
					
					ArrayList<Character> ReversenewExpectedCharArr = new ArrayList<Character>() ;
					ArrayList<Character> ReversenewActualCharArr = new ArrayList<Character>() ;
					
					for(int Index = newExpectedCharArr.length-1 ; Index>=0 ; Index--)
					{
						ReversenewExpectedCharArr.add(newModifiedExpectedCharArr[Index]);
					}
					
					for(int Index = newActualCharArr.length-1 ; Index>=0 ; Index--)
					{
						ReversenewActualCharArr.add(newModifiedActualCharArr[Index]);
					}
					
					if(ReversenewExpectedCharArr.size()>ReversenewActualCharArr.size())
					{
						LoopLimit = ReversenewActualCharArr.size();
					}
					else if(ReversenewExpectedCharArr.size()<=ReversenewActualCharArr.size())
					{
						LoopLimit = ReversenewExpectedCharArr.size();
					}
					
					ReversenewExpectedCharArrSize = ReversenewExpectedCharArr.size();
					
					for(int Index=0;Index<=LoopLimit-1 ;Index++)
					{		
						char ExpectedChar= ReversenewExpectedCharArr.get(Index);
						char ActualChar = ReversenewActualCharArr.get(Index);
						if(ExpectedChar==ActualChar)
						{
							MatchingCounter++;
						}
						else
						{
							break;
						}
					}
					
					Result3 = (double)MatchingCounter/ReversenewExpectedCharArrSize;
					if(Result3>=Threshold)
					{
						return Result3;
					}
					else
					{
						return 0.0;
					}
				}
				else
				{
					return Result2;
				}
				
				}
				else
				{
					return Result1;
				}	
			}
			catch(Exception e)
			{
				return 0.0;
			}
	}
	
	
	
	
	
	
	
	
	
	

	
	
	
	//*****************************************************************************************************
	/*
	 * Function Name - ReadSalesPanelOnContrast
	 * Author - UKIT Automation Team
	 * 
	 * 
	 * 
	 */

	private String ReadSalesPanelOnContrast() 
	{

		String SalesPanelDetailsOn="";

		try {
			// Defining Rectangle to read the Sales Panel
			Rectangle OnContrast = new Rectangle();

			ArrayList<Integer> img1 = eggUIDriver.ImageLocation("LeftTopCorner");
			int x1 = img1.get(0);
			int y1 = img1.get(1);

			ArrayList<Integer> img2 = eggUIDriver.ImageLocation("RightBottomCorner");

			int x2 = img2.get(0);
			int y2 = img2.get(1);
			System.out.println(x1 + "*****" + y1 + "*****" + x2 + "*****" + y2);

			OnContrast.x = x1;
			OnContrast.y = y1;
			OnContrast.width = x2 - x1;
			OnContrast.height = y2 - y1;

			String DescriptionOn = "contrast:on,contrastColor:Black";
			SalesPanelDetailsOn=eggUIDriver.readText(OnContrast, DescriptionOn);

			SalesPanelDetailsOn = SalesPanelDetailsOn.replaceAll("_|\"|\\*|\\#|\\%|\\>|\\$|\\\\N|\\-|\\?|\\^|\\!|", "")
					.replaceAll("±", "").trim();
			SalesPanelDetailsOn=SalesPanelDetailsOn.replaceAll("[0-9]{1,}[.][0-9]{1,}", "").replace("I.I.I", "").replace("I.\tI.\tI.\t", "");
			SalesPanelDetailsOn=SalesPanelDetailsOn.replaceAll("[\n]{2,}", "\n").replaceAll("\t", "");
			SalesPanelDetailsOn = SalesPanelDetailsOn.replaceAll("[a-z]{1,}", "");
			SalesPanelDetailsOn=SalesPanelDetailsOn.replaceAll("\t", "\n");

			return SalesPanelDetailsOn;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return null;
		}

	}
	//**************************************************************************************************************		

	public String ReadSalesPanel()
	{
		String SalesPanelDetails = "";
		try {

			Rectangle r = new Rectangle();
			
			ArrayList<Integer> img1 = eggUIDriver.ImageLocation("LeftTopCorner");
			int x1 = img1.get(0);
			int y1 = img1.get(1);

			ArrayList<Integer> img2 = eggUIDriver.ImageLocation("RightBottomCorner");

			int x2 = img2.get(0);
			int y2 = img2.get(1);

			r.x = x1;
			r.y = y1;
			r.width = x2 - x1;
			r.height = y2 - y1;

			SalesPanelDetails = eggUIDriver.readText(r); //read data from cordinates referenced by object r in off-contrast in white colour
			SalesPanelDetails = SalesPanelDetails.replaceAll("_|\"|\\*|\\#|\\%|\\>|\\$|\\\\N|\\#|\\#|\\#|", "").trim();
			SalesPanelDetails = SalesPanelDetails.replaceAll("\\\\r|\\{|\\}", "").replaceAll("w/", "").trim();

			
			SalesPanelDetails=SalesPanelDetails.replaceAll("[0-9]{1,}[.][0-9]{1,}", "").replace("I.I.I", "").replace("I.\tI.\tI", "");
			SalesPanelDetails=SalesPanelDetails.replaceAll("[\n]{2,}", "\n").replaceAll("\t", "");
			
			if(!SalesPanelDetails.contains("Missing"))
			{
				SalesPanelDetails = SalesPanelDetails.replaceAll("[a-w]{1,}", "");
				SalesPanelDetails = SalesPanelDetails.replaceAll("[y-z]{1,}", "");
			}
		
			return SalesPanelDetails;
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
			return "";
		}

	}


	//********************************Verify Auto Add - Yogesh***************************************************

	public void verifyAutoAdd(Map<String,String> input)
	{
		String AutoAddFeature=input.get("autoAddFeature");
		String AutoAddProductSalesPanelName=input.get("autoAddSideItemName");
		String coreProductName=input.get("coreProductName");
		String strStepDesc="To verify Auto Add functionality";
		String strExpected="";
	
		String strMenuName=input.get("menuName");
		
		//autoadd should be failed with verified sales panel name	
		String SideItemHelperKey = "";
		boolean ClearOrder = false;
		
		//to handle wraps case
		if(strMenuName.equalsIgnoreCase("Med Big flavour wraps")||
				strMenuName.equalsIgnoreCase("Med Big flavour wraps menu")||
				strMenuName.equalsIgnoreCase("Med evm Big flavour wraps")||
				strMenuName.equalsIgnoreCase("Med evm Big flavour wraps menu")||
				strMenuName.equalsIgnoreCase("Medium evm Big flavour wraps")||
				strMenuName.equalsIgnoreCase("Medium big flavour wraps")||
				strMenuName.equalsIgnoreCase("Medium evm big flavour wraps"))
		{
			ClearOrder = true;
			SideItemHelperKey="SideItemHelperForMedWraps";
		}
		else if(strMenuName.equalsIgnoreCase("Lrg Big flavour wraps")||
				strMenuName.equalsIgnoreCase("Lrg Big flavour wraps menu")||
				strMenuName.equalsIgnoreCase("Lrg evm Big flavour wraps")||
				strMenuName.equalsIgnoreCase("Lrg evm Big flavour wraps menu")||
				strMenuName.equalsIgnoreCase("Lrg evm Big flavour wraps")||
				strMenuName.equalsIgnoreCase("Lrg big flavour wraps")||
				strMenuName.equalsIgnoreCase("Large evm Big flavour wraps"))
		{
			ClearOrder = true;
			SideItemHelperKey="SideItemHelperForLrgWraps";
		}
		
		
		if(AutoAddProductSalesPanelName!=null && AutoAddProductSalesPanelName.length()>2)
		{
			String SideItemAndPrice = readSalesPanelLineByLine(AutoAddProductSalesPanelName.trim());
			String [] arrSideItemAndPrice = SideItemAndPrice.split("\n");
			int Length = arrSideItemAndPrice.length;
			
			if(AutoAddFeature.equalsIgnoreCase("YES") )
			{
				// autoadd should be successful with provided side item name
				strExpected="Should auto add product -"+AutoAddProductSalesPanelName+" to '"+coreProductName+"'";
				if(Length==1 || Length==2)
				{
					HtmlResult.passed(strStepDesc,strExpected,"Auto Add status-"+AutoAddFeature+"\nExpected Auto Add Product-' "+AutoAddProductSalesPanelName+" ' \nActual auto added product is-"+arrSideItemAndPrice[0]+", \nSuccessfully auto added desired side product");
				}
				else if(Length>=3)
				{
					boolean AutoAdd=false;
					for(String ActualData:arrSideItemAndPrice)
					{
						if(ActualData.equals(AutoAddProductSalesPanelName))
						{
							AutoAdd=true;
						}
					}
					
					if(AutoAdd)
					{
						HtmlResult.passed(strStepDesc,strExpected,"Auto Add status-"+AutoAddFeature+"\nExpected Auto Add Product-' "+AutoAddProductSalesPanelName+" ' is autoadded");
					}
					else
					{
						HtmlResult.failed(strStepDesc,strExpected,"Auto Add status-"+AutoAddFeature+"\nExpected Auto Add Product-' "+AutoAddProductSalesPanelName+" ' \nActual sales panel data is-' "+Arrays.toString(arrSideItemAndPrice)+" '");
					}
				}
				else if(Length==0)
				{
					HtmlResult.failed(strStepDesc, strExpected, "Side item-'"+AutoAddProductSalesPanelName+"' not present in sales panel");
				}
			}
			else if(AutoAddFeature.equalsIgnoreCase("NO"))
			{
				strExpected="Should not auto add product -"+AutoAddProductSalesPanelName+" to '"+coreProductName+"'";
				if(AutoAddProductSalesPanelName!=null ||AutoAddProductSalesPanelName.length()>2)
				{
					strExpected="Should not auto add product-"+AutoAddProductSalesPanelName+" to '"+coreProductName+"'";
					if(Length==1 || Length==2)
					{
						HtmlResult.failed(strStepDesc,strExpected,"Auto Add status-"+AutoAddFeature+",product '"+AutoAddProductSalesPanelName+"' is autoadded");
					}
					else if(Length>=3)
					{
						boolean AutoAdd = false;
						for(String ActualData:arrSideItemAndPrice)
						{
							if(ActualData.equals(AutoAddProductSalesPanelName))
							{
								AutoAdd=true;
							}
						}
						
						if(AutoAdd)
						{
							HtmlResult.failed(strStepDesc,strExpected,"Auto Add status-"+AutoAddFeature+"\nProduct-' "+AutoAddProductSalesPanelName+" ' is autoadded");
						}
						else
						{
							HtmlResult.passed(strStepDesc,strExpected,"Auto Add status-"+AutoAddFeature+"\nProduct-' "+AutoAddProductSalesPanelName+" ' is not autoadded");
						}
						
					}
					else if(Length==0)
					{
						HtmlResult.passed(strStepDesc,strExpected,"Auto Add status-"+AutoAddFeature+"\nProduct-' "+AutoAddProductSalesPanelName+" ' is not autoadded");
					}

				}
			}
			else if(! AutoAddFeature.equalsIgnoreCase("NO"))
			{
				strExpected = "To verify auto add functionality";
				HtmlResult.failed(strStepDesc, strExpected, "AutoAdd feature should be YES or NO, provided parameter is- ' "+AutoAddFeature+" '");
			}
		}
		else
		{
			String SalesPanelData = ReadSalesPanel();
			String[] arrSalesPanelData = SalesPanelData.split("\n");
			strExpected="Should auto add side product to '"+coreProductName+"'";
			//when expected autoadd product name is not provided
			if(AutoAddFeature.equalsIgnoreCase("YES") )
			{
				// autoadd should be passed
				boolean AutoAdd=false;
				String SideItemName = "";
				for(String Order:arrSalesPanelData)
				{
					if(Order.contains("FRIES")||
							Order.contains("SIDE SALAD")||
							Order.contains("FRUIT BAG")||
							Order.contains("CARROT")||
							Order.contains("HASH BROWN"))
					{
						SideItemName = Order;
						AutoAdd = true;
					}
				}
				
				if(AutoAdd)
				{
					HtmlResult.passed(strStepDesc,strExpected, "Side item-'"+SideItemName+"' is autoadded");
				}
				else
				{
					HtmlResult.failed(strStepDesc,strExpected, "No any side itema are autoadded");
				}
			}
			else if(AutoAddFeature.equalsIgnoreCase("NO"))
			{
				// autoadd should be failed
				strExpected="Should not auto add side product to '"+coreProductName+"'";
				boolean AutoAdd=false;
				String SideItemName = "";
				for(String Order:arrSalesPanelData)
				{
					if(Order.contains("FRIES")||
							Order.contains("SIDE SALAD")||
							Order.contains("FRUIT BAG")||
							Order.contains("CARROT")||
							Order.contains("HASH BROWN"))
					{
						SideItemName = Order;
						AutoAdd = true;
					}
				}
				
				if(AutoAdd)
				{
					HtmlResult.failed(strStepDesc,strExpected, "Side item-'"+SideItemName+"' is autoadded");
				}
				else
				{
					HtmlResult.passed(strStepDesc,strExpected, "No any side itema are autoadded");
				}
			}
			else if(! AutoAddFeature.equalsIgnoreCase("NO"))
			{
				strExpected = "To verify auto add functionality";
				HtmlResult.failed(strStepDesc, strExpected, "AutoAdd feature should be YES or NO, provided parameter is- ' "+AutoAddFeature+" '");
			}
		}
		
		
		if(ClearOrder)
		{
			try 
			{
				String SideItemHelperValue=getValueFromExcel(SideItemHelperKey);
				boolean FoundAndClick=clickButtonAddToReport(SideItemHelperValue);
				if(FoundAndClick)
				{
					HtmlResult.passed("To clear order for wraps","Order should be placed with by selecting one item from side items for-"+strMenuName,"Order should placed successfully by adding-"+SideItemHelperValue+" to the meal");
				}
				else
				{
					HtmlResult.failed("To clear order for wraps","Order should be placed with by selecting one item from side items for-"+strMenuName,"Unable to find button-"+SideItemHelperValue);
				}
			} 
			catch (Exception e) 
			{
				HtmlResult.failed(strStepDesc,strExpected,"Unable to get value from properties file\n"+e.getMessage());
			}
		}

	}

	//********************************payment type*********************************//

	public void paymentType(Map<String,String> input)
	{
		String paymentType=input.get("paymentType").trim();
		String strStepDesc="To make a payment with option-"+paymentType;
		String strExpected="Order should get succesfully tendered using-'"+paymentType+"'";
		String paymentMethod="";
		Boolean FoundAndClicked=false;
		String strOffersButton="";
		
		if(paymentType.equalsIgnoreCase("exact cash"))
		{
			try
			{
				paymentMethod=getValueFromExcel("EXACTCASH");
				strOffersButton=getValueFromExcel( "OFFERSMENU"); 
			} 
			catch (Exception e) 
			{
				HtmlResult.failed(strStepDesc,strExpected,"Unable to read Map file-"+e.getMessage());
			}

			FoundAndClicked=clickButton(paymentMethod);
			if(FoundAndClicked)
			{
				HtmlResult.passed(strStepDesc,strExpected,"Payment type successful-"+paymentType);
				//clickButton("exactCashHelper");
			}
			else
			{
				HtmlResult.failed(strStepDesc,strExpected,"Payment type not successful may be beacuse of \nsmart reminder or button not found\n trying to resolve-"+paymentType);
				boolean Found=eggUIDriver.imageFound(strOffersButton);
				int Counter=0;
				if(Found)
				{
					Point SmartSidePoint=new Point(331,66);
					Point SmartDrinkPoint=new Point(331,122);
					try 
					{
						do {
							eggUIDriver.clickPoint(SmartSidePoint);
							eggUIDriver.wait(2);
							eggUIDriver.clickPoint(SmartDrinkPoint);
							Found = eggUIDriver.imageFound(paymentMethod);
							if (Found) 
							{
								String SalesData = ReadSalesPanelUKOffContrast();
								boolean clicked=clickButton(paymentMethod);
								System.out.println("");
								if (clicked) 
								{

									HtmlResult.failed(strStepDesc, strStepDesc,
											"Payment type successful-" + paymentType + "\n Order is completed as-"
													+ SalesData
													+ "\n As side items are not properly provided from Test Data\n");
									//clickButton("exactCashHelper");
									break;
								}
								else
								{
									HtmlResult.failed(strStepDesc,strStepDesc,"Button not clicked-"+paymentType);
								}
							}
							else 
							{
								Counter++;
							} 
							if(Counter>=5)
							{
								HtmlResult.failed(strStepDesc,strExpected,"Order can not be processed from this screen/n click to view screenshot");
							}
						} while (Counter<5);
					} 
					catch (Exception e) 
					{
						HtmlResult.failed(strStepDesc,strExpected,"Unable to add items from smart reminder");
					}
				}
				else
				{
					HtmlResult.failed(strStepDesc,strExpected,"Order can not be processed from this screen/n click to view screenshot");
				}
			}
		}
		else
		{
			HtmlResult.failed(strStepDesc,strExpected,"Wrong values passed from designer/nPayment type should be Exact cash");
		}
	}

	//************************************internal function to read entire sales panel data *****************************//

	private String ReadSalesPanelUKOffContrast() 
	{
		String SalesPanelDetailsOff="";
		Dimension ScreenResolution = eggUIDriver.remoteScreenSize();
		
		if(ScreenResolution.width==1024 && ScreenResolution.height==768 )
		{
			SalesPanelDetailsOff = ReadSalesPanelOffContrast();
		
		}
		else if(ScreenResolution.width==800 && ScreenResolution.height==600)
		{
			SalesPanelDetailsOff = ReadSalesPanelLineByLine();
		}
		else
		{
			SalesPanelDetailsOff = ReadSalesPanelOffContrast();
		}
		
		return SalesPanelDetailsOff;
	}

	public String ReadSalesPanelOffContrast()
	{
		String SalesPanelDetailsOff="";

		try {

			Rectangle OffContrast = new Rectangle();

			ArrayList<Integer> img1 = eggUIDriver.ImageLocation("LeftTopCorner ");
			int x1 = img1.get(0);
			int y1 = img1.get(1);

			ArrayList<Integer> img2 = eggUIDriver.ImageLocation("RightBottomCorner");
			int x2 = img2.get(0);
			int y2 = img2.get(1);
			System.out.println(x1 + "*****" + y1 + "*****" + x2 + "*****" + y2);

			OffContrast.x = x1;
			OffContrast.y = y1;
			OffContrast.width = x2 - x1;
			OffContrast.height = y2 - y1;
			String ContrastColor="(255,248,255)";

			String DescriptionOff = "contrast:off,contrastColor:"+ContrastColor;
		
			String SalesPanelDetails = eggUIDriver.readText(OffContrast,DescriptionOff); //read data from cordinates referenced by object r in off-contrast
			SalesPanelDetailsOff = SalesPanelDetails.replaceAll("_|\"|\\*|\\#|\\%|\\>|\\$|\\\\N|\\#|\\#|\\#|", "").trim();
			SalesPanelDetailsOff = SalesPanelDetailsOff.replaceAll("\\\\r|\\{|\\}", "").replaceAll("w/", "").trim();

			SalesPanelDetailsOff=SalesPanelDetailsOff.replaceAll("[\n]{2,}", "\n").replaceAll("\t", "");
			
			SalesPanelDetailsOff = SalesPanelDetailsOff.replaceAll("[a-z]{1,}", "");

			return SalesPanelDetails;
			
		
		} 
		catch (Exception e) 
		{
			return "";
		}
	}
	
	public String ReadSalesPanelLineByLine()
	{
		String SalesPanelDetailsOff="";

		try {
			// Defining Rectangle to read the Sales Panel
			Rectangle OnContrast = new Rectangle();
			Rectangle OffContrast = new Rectangle();


			ArrayList<Integer> img1 = eggUIDriver.ImageLocation("SalesPanel_TL_Row1");
			int x1 = img1.get(0);
			int y1 = img1.get(1);

			ArrayList<Integer> img2 = eggUIDriver.ImageLocation("SalesPanel_BR_Row1");
			int x2 = img2.get(0);
			int y2 = img2.get(1);
			System.out.println(x1 + "*****" + y1 + "*****" + x2 + "*****" + y2);

			OnContrast.x = x1;
			OnContrast.y = y1;
			OnContrast.width = x2 - x1;
			OnContrast.height = y2 - y1;

			OffContrast.x = x1;
			OffContrast.y = y1;
			OffContrast.width = x2 - x1;
			OffContrast.height = y2 - y1;
			String ContrastColor="(255,248,255)";

			String DescriptionOff = "contrast:off,contrastColor:"+ContrastColor;
		
			int breakCounter1=0; 

			String SalesPanelRowOff = eggUIDriver.readText(OffContrast, DescriptionOff);
			while (!SalesPanelRowOff.trim().equals("") || breakCounter1 == 0) 
			{
				if (SalesPanelRowOff.trim().equals(""))
					breakCounter1++;
				SalesPanelDetailsOff = SalesPanelDetailsOff.concat(SalesPanelRowOff.replace("\n\n","\n")).concat("\n");
				OffContrast.y=OffContrast.y+16;
				SalesPanelRowOff = eggUIDriver.readText(OffContrast, DescriptionOff);
			}
			SalesPanelDetailsOff = SalesPanelDetailsOff.replaceAll("_|\"|\\*|\\#|\\%|\\>|\\$|\\\\N|\\-|\\?|\\^|\\!|", "")
					.replaceAll("±", "").trim();
			SalesPanelDetailsOff=SalesPanelDetailsOff.replaceAll("\t", "\n");
			SalesPanelDetailsOff = SalesPanelDetailsOff.replaceAll("[a-z]{1,}", "");
			//for tea 
			SalesPanelDetailsOff = SalesPanelDetailsOff.replace("fBRKl","(BRK)");
			
			//for QTR cheese
			SalesPanelDetailsOff = SalesPanelDetailsOff.replace("OTR","QTR");
			
			return SalesPanelDetailsOff;
		} 
		catch (Exception e) 
		{
			return "";
		}

	}
	//tender type
		public void tenderType(Map<String,String> input) 
		{
			String strTenderType =input.get("tenderType");
			String strBagName= input.get("bagChoice").trim();
			String strDonation =input.get("donation").trim();
			String strDonateType=input.get("donationType").trim();
			
			String stepDesc="To tender the order Eat in total or take out total";
			String strExpected="Order Tendered Screen should get displayed-"+strTenderType;
			boolean FoundAndClicked=false;
			String strTenderTypeImageName="";
			String strNoBagButtonName="", str1BagButtonName="";
			String strNoDonationButtonName="";
			String str1PDonationButtonName="";
			String strRoundUpDonationButton="";
			String strVariableAmtDonationButtton="";
			
			//read property file if bag option flag is true then only check bag and no bag condition else do not
			if(strTenderType.equalsIgnoreCase("TAKE OUT TOTAL"))
			{ 
				try 
				{
					strTenderTypeImageName=getValueFromExcel("TAKEOUTTOTAL");
					strNoBagButtonName= getValueFromExcel("BagChoice_NoBag");
					str1BagButtonName=getValueFromExcel("BagChoice_1Bag");
					strNoDonationButtonName=getValueFromExcel("DonationChoice_No");
					str1PDonationButtonName=getValueFromExcel("DonationChoice_1P");
					strRoundUpDonationButton=getValueFromExcel("DonationChoice_RoundUp");
					strVariableAmtDonationButtton=getValueFromExcel("DonationChoice_VariableAmt");
					
				} 
				catch (Exception e) 
				{
					HtmlResult.failed(stepDesc,strExpected,"Unable to read properties file-"+e.getMessage());
				}
				FoundAndClicked=clickButton(strTenderTypeImageName);
				if(FoundAndClicked)
				{
					if(strBagName.length()>0)
					{
						if(strBagName.equalsIgnoreCase("YES"))
						{
							FoundAndClicked=clickButton(str1BagButtonName);
							if(FoundAndClicked)
							{
								if(performMangagerAuthentication)
								{
									if(handleManagerAuthentication())
									{
										HtmlResult.passed(stepDesc,strExpected,"Tender successful-"+strTenderType);
									}
								}
								else
								{
									HtmlResult.passed(stepDesc,strExpected,"Tender successful-"+strTenderType);
								}
							}
							else
							{
								HtmlResult.failed(stepDesc,strExpected,"Tender is not successful-"+strTenderType+"\nwith 1 bag button not found and not clicked");
							}
						}
						else if(strBagName.equalsIgnoreCase("NO"))
						{
							FoundAndClicked=clickButton(strNoBagButtonName);
							if(FoundAndClicked)
							{
								if(performMangagerAuthentication)
								{
									if(handleManagerAuthentication())
									{
										HtmlResult.passed(stepDesc,strExpected,"Tender successful-"+strTenderType);
									}
								}
								else
								{
									HtmlResult.passed(stepDesc,strExpected,"Tender successful-"+strTenderType);
								}
							}
						}
						else
						{
							HtmlResult.failed(stepDesc,strExpected,"Tender is not successful-"+strTenderType+"\nwith bag option passed from designer\n it sholud be either 1 bag or No bag");
						}
						
						//Donation for charity
						if(strDonation.length()>0)
						{	
							//No donation by customer
							if(strDonation.equalsIgnoreCase("NO"))
							{
								FoundAndClicked=clickButton(strNoDonationButtonName);
								if(FoundAndClicked)
								{
									HtmlResult.passed("Donation selection"," NO Donation selection should be successful ","NO Donation selection is successful ");
								}
								else
								{
									HtmlResult.failed("Donation selection"," NO Donation selection should be successful ","NO Donation selection is not successful ");
								}
							}
							//if customer wants to donate
							else if(strDonation.equalsIgnoreCase("YES"))
							{
								if(strDonateType.equalsIgnoreCase("1P"))	//Donate 1$
								{
								FoundAndClicked=clickButton(str1PDonationButtonName);
									if(FoundAndClicked)
									{
										HtmlResult.passed("IP Donation Selection", "1P Should be clicked", "1P Donation amount donated");
									}
									else
									{
										HtmlResult.failed("IP Donation Selection", "1P Should be clicked", "1P Donation not clicked");
									}
								}
								else if(strDonateType.equalsIgnoreCase("RoundUp")) //Donate RoundUp Amount
								{
									//HtmlResult.passed("Donation selection"," 1P Donation selection should be successful ","1P Donation selection is successful ");
									FoundAndClicked=clickButton(strRoundUpDonationButton);
									if(FoundAndClicked)
									{
										HtmlResult.passed("RoundUp Donation Selection", "RoundUp Donation should be clicked", "RoundUp amount donated");
									}
									else
									{
										HtmlResult.failed("RoundUp Donation Selection", "RoundUp Donation should be clicked", "RoundUp Donation not clicked");
									}
								}
								else if(strDonateType.equalsIgnoreCase("VariableAmt")) //Donate Variable amount
								{
									FoundAndClicked=clickButton(strVariableAmtDonationButtton);
									if(FoundAndClicked)
									{
										//enter the amount to donate
										
										//click on enter button
										
										
										HtmlResult.passed("Variable amount Selection", "VariableAmt Donation should be clicked", "Variable amount donated");
									}
									else
									{
										HtmlResult.failed("Variable amount Selection", "VariableAmt Donation should be clicked", "Variable amount Donation not clicked");
									}
								}
								else
								{
									HtmlResult.failed("Donation type selection"," Donation type selection should be mentioned correctly ","Donation type is not selected properly");
								}	
							}
						}
						else
						{
							System.out.println("No panel for Donation is there.");
						}
						
					}
					else
					{
						;
					}

				}
				else
				{
					HtmlResult.failed(stepDesc,strExpected,"Button not found and not clicked"+strTenderType);
				}

			}


			else if (strTenderType.equalsIgnoreCase("EAT IN TOTAL")) 
			{
				strTenderTypeImageName=getValueFromExcel("EATINTOTAL");

				FoundAndClicked=clickButton(strTenderTypeImageName);

				if(FoundAndClicked)
				{
					if(performMangagerAuthentication)
					{
						if(handleManagerAuthentication())
						{
							HtmlResult.passed(stepDesc,strExpected,"Tender successful-"+strTenderType);
						}
					}
					else
					{
						HtmlResult.passed(stepDesc,strExpected,"Tender successful-"+strTenderType);
					}
					
					
				}
				else
				{
					HtmlResult.failed(stepDesc,strExpected,"Button not found and not clicked"+strTenderType);
				}
			}
			else 
			{
				HtmlResult.failed(stepDesc,strExpected,"Incorrect Payment Type" + strTenderType );
			}
		}

	
	//old tender type
//	public void tenderType(Map<String,String> input) 
//	{
//		String strTenderType =input.get("tenderType");
//		String strBagName="";//input.get("bagChoice").trim();
//		String stepDesc="To tender the order Eat in total or take out total";
//		String strExpected="Order Tendered Screen should get displayed-"+strTenderType;
//		boolean FoundAndClicked=false;
//		String strTenderTypeImageName="";
//		//read property file if bag option flag is true then only check bag and no bag condition else do not
//		if(strTenderType.equalsIgnoreCase("TAKE OUT TOTAL"))
//		{ 
//			try 
//			{
//				strTenderTypeImageName=getValueFromExcel("TAKEOUTTOTAL");
//			} 
//			catch (Exception e) 
//			{
//				HtmlResult.failed(stepDesc,strExpected,"Unable to read properties file-"+e.getMessage());
//			}
//			FoundAndClicked=clickButton(strTenderTypeImageName);
//			if(FoundAndClicked)
//			{
//				if(strBagName.length()>0)
//				{
//					if(strBagName.equalsIgnoreCase("1 BAG"))
//					{
//						FoundAndClicked=clickButton("bag");
//						if(FoundAndClicked)
//						{
//							if(performMangagerAuthentication)
//							{
//								if(handleManagerAuthentication())
//								{
//									HtmlResult.passed(stepDesc,strExpected,"Tender successful-"+strTenderType);
//								}
//							}
//							else
//							{
//								HtmlResult.passed(stepDesc,strExpected,"Tender successful-"+strTenderType);
//							}
//						}
//						else
//						{
//							HtmlResult.failed(stepDesc,strExpected,"Tender is not successful-"+strTenderType+"\nwith 1 bag button not found and not clicked");
//						}
//					}
//					else if(strBagName.equalsIgnoreCase("NO BAG"))
//					{
//						FoundAndClicked=clickButton("no bag");
//						if(FoundAndClicked)
//						{
//							if(performMangagerAuthentication)
//							{
//								if(handleManagerAuthentication())
//								{
//									HtmlResult.passed(stepDesc,strExpected,"Tender successful-"+strTenderType);
//								}
//							}
//							else
//							{
//								HtmlResult.passed(stepDesc,strExpected,"Tender successful-"+strTenderType);
//							}
//						}
//					}
//					else
//					{
//						HtmlResult.failed(stepDesc,strExpected,"Tender is not successful-"+strTenderType+"\nwith bag option passed from designer\n it sholud be either 1 bag or No bag");
//					}
//				}
//				else
//				{
//					;
//				}
//
//			}
//			else
//			{
//				HtmlResult.failed(stepDesc,strExpected,"Button not found and not clicked"+strTenderType);
//			}
//
//		}
//
//
//		else if (strTenderType.equalsIgnoreCase("EAT IN TOTAL")) 
//		{
//			strTenderTypeImageName=getValueFromExcel("EATINTOTAL");
//
//			FoundAndClicked=clickButton(strTenderTypeImageName);
//
//			if(FoundAndClicked)
//			{
//				if(performMangagerAuthentication)
//				{
//					if(handleManagerAuthentication())
//					{
//						HtmlResult.passed(stepDesc,strExpected,"Tender successful-"+strTenderType);
//					}
//				}
//				else
//				{
//					HtmlResult.passed(stepDesc,strExpected,"Tender successful-"+strTenderType);
//				}
//				
//				
//			}
//			else
//			{
//				HtmlResult.failed(stepDesc,strExpected,"Button not found and not clicked"+strTenderType);
//			}
//		}
//		else 
//		{
//			HtmlResult.failed(stepDesc,strExpected,"Incorrect Payment Type" + strTenderType );
//		}
//	}




	//Verify Grill Button

	//****************************************verify grill component***************************************
	public void verifyGrillButton(Map<String,String> input)
	{
		String strGrillStatus=input.get("productGrillableStatus").toUpperCase().trim();
		//String strProductName=input.get("");
		String strStepDesc="To verify if product is grillable or not";
		String strExpected="";
		strExpected="Product should be grillable"+"";

		String GrillButton = getValueFromExcel("GRILLBUTTON");
		String DoneButton = getValueFromExcel("GRILLDONEBUTTON");
		String PlusButton = getValueFromExcel("GRILLPLUS");
		String MinusButton = getValueFromExcel("GRILLMINUS");

		boolean FoundAndClicked=false;


		if(!GrillButton.equals("Not in buttons"))
		{
			FoundAndClicked=clickButton(GrillButton);
		}
		else
		{
			HtmlResult.failed(strStepDesc,strExpected,"Add button-"+GrillButton+"-in buttons sheet");
		}

		if(strGrillStatus.equals("YES"))
		{

			if(FoundAndClicked)
			{
				boolean Plusfound=eggUIDriver.imageFound(PlusButton);
				boolean Minusfound=eggUIDriver.imageFound(MinusButton);
				if(Plusfound||Minusfound)
				{
					FoundAndClicked=clickButton(DoneButton);

					if(FoundAndClicked)
					{
						HtmlResult.passed(strStepDesc,strExpected,"Product is grillable");
					}
					else
					{
						HtmlResult.failed(strStepDesc,strExpected,"Unable to find button-"+DoneButton);
					}
				}
				else
				{
					if(!Plusfound)
					{
						HtmlResult.failed(strStepDesc,strExpected,"Plus button not found on grill screen");
					}
					if(!Minusfound)
					{
						HtmlResult.failed(strStepDesc,strExpected,"Minus button not found on grill screen");
					}

					FoundAndClicked=clickButton(DoneButton);
					if(FoundAndClicked)
					{
						HtmlResult.passed(strStepDesc,strExpected,"Done button is clicked successfully");
					}
					else
					{
						HtmlResult.failed(strStepDesc,strExpected,"Unable to find button-"+ DoneButton);
					}
				}
			}
			else
			{
				HtmlResult.failed(strStepDesc,strExpected,"Unable to find button-"+GrillButton);
			}
		}

		else if(strGrillStatus.equals("NO"))
		{
			strExpected="Product should not be grillable"+"";

			if(!FoundAndClicked)
			{
				HtmlResult.passed(strStepDesc,strExpected,"Product is not grillable");

			}
			else
			{
				clickButton(DoneButton);
				HtmlResult.failed(strStepDesc,strExpected,"Product is not grillable but grill button is present");
			}

		}
		else
		{
			strExpected="Grill shuld be verified";
			HtmlResult.failed(strStepDesc,strExpected,"Incorrect value passed from designer-"+strGrillStatus+"-,It must be either yes or no");
		}	
	}

	//*****************************************************************************************************
	public boolean launchMealUpgradeCategory(String CategoryName)
	{
		
		try
		{
			String MealUpgradeButton = getValueFromExcel("MENU_MEALUPGRADE");
			boolean Status = clickButton(MealUpgradeButton);
			if(Status)
			{
				Status = clickButton(CategoryName);
				if(Status)
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
				return false;
			}
			
		}
		catch(Exception e)
		{
			return false;
		}
	}

	//********************************meal upgrade***************************//
	public void verifyMealUpgrade(Map<String,String> input)
	{
		String strStepDesc="To Upgrade meal";
		String strExpected="";
		String strAfterUpgradeSalesPanelData=input.get("afterUpgradeName");
		try {
			getValueFromExcel("MealUpgradeHelper");
			String MealUpgardeMenu = getValueFromExcel("MEAL_UPGRADE_MENUS").trim();
			
			// dynamic array splitting by ,
			String[] MealUpgardeMenus = MealUpgardeMenu.split(",");
			String[] arrExpectedSalesPanelProdNames=strAfterUpgradeSalesPanelData.split(";");
			boolean RunOnlyOnce=true;
			
			// for signature beef products and 5 chicken selects case
			if(MealUpgardeMenus.length!=arrExpectedSalesPanelProdNames.length)
			{
				String[] NewMealUpgradeMenus = {MealUpgardeMenus[0],MealUpgardeMenus[1]};
				MealUpgardeMenus = NewMealUpgradeMenus;
				HtmlResult.warning(strStepDesc, "Meal upgrade should be successful", "Checking meal upgrade for menus-' "+Arrays.toString(MealUpgardeMenus)+" '");
			}
			
			//check meal upgrade by taking one by one upgrade category
			for(int Index=0;Index<MealUpgardeMenus.length;Index++)
			{
				boolean Status = launchMealUpgradeCategory(MealUpgardeMenus[Index].trim());
				strExpected = "Meal should be upgraded to-'"+MealUpgardeMenus[Index].trim()+"' successfully";
				String SelectMenu =""; 
				boolean PerformUpgrade = false;
				if(Status)
				{
					SelectMenu = MealUpgardeMenus[Index].trim();
					PerformUpgrade = true;
				}
				else
				{
					HtmlResult.failed(strStepDesc, "Meal upgrade should be successful", "Meal upgrade category' "+MealUpgardeMenus[Index]+" 'button not found");
					continue;
				}

				if(PerformUpgrade)
				{
					//to select meal in sales panel
					while(RunOnlyOnce)
					{
						ArrayList<Integer> img1 = eggUIDriver.ImageLocation("LeftTopCorner");
						int x1 = img1.get(0);
						int y1 = img1.get(1);
						Point ClickPoint = new Point(x1+10,y1+10);
						
						eggUIDriver.clickPoint(ClickPoint);
						RunOnlyOnce=false;
					}

					//verify SalesPanelData with expected Data
					String ExpectedDataOnSalesPanel = arrExpectedSalesPanelProdNames[Index].trim();
					String []arrExpectedDataOnSalesPanel = ExpectedDataOnSalesPanel.split("#");
					int ExpectedNumberOfDataToFind = arrExpectedDataOnSalesPanel.length;
					int FindCounter = 0;
					String WrongData = "";

					for(String ExpecetdOrder : arrExpectedDataOnSalesPanel)
					{
						boolean TextFoundOnSalesPanel = findTextOnSalesPanel(ExpecetdOrder);

						if(TextFoundOnSalesPanel)
						{
							FindCounter++;
						}
						else
						{
							WrongData = WrongData.concat(ExpecetdOrder+",");
							continue;
						}
					}

					//verify results
					if(FindCounter==ExpectedNumberOfDataToFind && WrongData.length()<4)
					{
						HtmlResult.passed(strStepDesc,strExpected,"Meal has been Successfully upgraded to-' "+Arrays.toString(arrExpectedDataOnSalesPanel)+" ' for menu-'"+SelectMenu+"'");
					}
					else if(WrongData.length()>3 && FindCounter!=ExpectedNumberOfDataToFind)
					{
						String[] ActualData = ReadSalesPanel().split("\n");
						HtmlResult.failed(strStepDesc,strExpected,"Products- '"+WrongData+"' not found in sales panel for menu-' "+SelectMenu+" ', Sales panel contains-'"+Arrays.toString(ActualData)+"'");
					}						
				}
			}
		}
		catch (Exception e) 
		{
			HtmlResult.failed(strStepDesc,strExpected,"Error while upgrading meal-"+e);
		}	
	}
	
	public boolean clearSignatureBeefOfEmployees() 
	{
		try
		{
			boolean blnFlag = new EggPlant().addSideItemToEmployeeMeal();
			if(blnFlag)
			{
				HtmlResult.passed("To Complete Order while meal Upgrade for Signature beef", "Complete Order while meal Upgrade for Signature beef should be successful", "Complete Order while meal Upgrade for Signature beef is successful");
				return true;			
			}
			else
			{
				HtmlResult.failed("To Complete Order while meal Upgrade for Signature beef", "Complete Order while meal Upgrade for Signature beef should be successful", "Complete Order while meal Upgrade for Signature beef is not successful");
				return false;
			}
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//***************************************************************************************//
	public boolean VerifyMeal(String[]Expected,String[]Actual)
	{	
		String[] ExpectedMeal=Expected;
		int ExpectedArrayLength=ExpectedMeal.length;
		String[] ActualSalesPanelData=Actual;
		int Length=ActualSalesPanelData.length;
		boolean UpgradeStatus=false;
		int Counter=0;
		for(String ExpectedData:ExpectedMeal)
		{	
			UpgradeStatus=false;
			ExpectedData = ExpectedData.trim();
			for(int Index=0;Index<Length;Index++)
			{
				if(ExpectedData.trim().equals(ActualSalesPanelData[Index].trim()))
				{
					UpgradeStatus=true;
					Counter++;
					break;
				}
			}
			if(!(Counter==ExpectedArrayLength))
			{
				UpgradeStatus=false;
			}
		}

		return UpgradeStatus;
	}

	//*****************************************************************************************************************//

	//**********************************************CLICK FUNCTION WITH REPORTING BUT NOT A COMPONENT*************************//
	// Created by:yogesh 
	// Purpose: Find and click the button and also add events to the report
	// Date=28/07/2016
	public boolean clickButtonAddToReport (String ButtonName)
	{
		String strButton=ButtonName;
		boolean Found=false;
		boolean Clicked=false;
		boolean Status=false;
		String[] strButtonList=strButton.split("#");

		for (String Button:strButtonList) {
			Button = Button.trim();
			Status=false;
			String strStepDesc="Find and click the button-"+Button;
			String strExpected="Button should be found and clicked-"+Button;
			String strActual="";
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
		
			// Clear choice
			//*****************************************************************************************************************
			public void clearChoiceForMeal(Map <String,String> input) throws FileNotFoundException, IOException, JXLException
			{
				try
				{
				Rectangle r = new Rectangle();			
				r=eggUIDriver.imageRectangle("LeftTopCorner", "RightBottomCorner");	

				String strMenuName=input.get("menuName");
				String strProductName=input.get("productName");			

				boolean blnNoSideItemPresent=false;
				boolean blnNoSideLoop=false;

				//reading content from property file

				String clearChoice="";
				String clearChoiceOKButton="";
				String dip="";
				String menuDips="";
				try{
					clearChoice = getValueFromExcel("clearChoiceButtonName").trim();
					clearChoiceOKButton = getValueFromExcel("clearChoiceOKButton").trim();							
					dip = getValueFromExcel("Dips");
					menuDips = getValueFromExcel("menuDips");
				}
				catch(Exception e)
				{
					HtmlResult.failed("To perform clear choice action", "Clear choice should be successful", "Wrong value in propertymap.xlsx file-'"+clearChoice+","+clearChoiceOKButton+","+dip+","+menuDips+","+"'");								
				}

				String[] strDrink=null;
				String[] strSide=null;
				String drinkButtonNames=" ";
				String SideItemButtonName=" ";

				//****************Medium EVM meal***************
				if(strMenuName.equalsIgnoreCase("MED EVM"))
				{
					try{
						drinkButtonNames = getExcelProductCombination("POSCombinationData","MedEVMDrinkButtonName"); //MedEVMDrinkButtonName
						SideItemButtonName = getExcelProductCombination("POSCombinationData","MedEVMSideItemButtonName");	//MedEVMSideItemButtonName				
					}
					catch (IOException e)
					{			
						e.printStackTrace();									
					}
				}
				//****************Large EVM meal***************
				else if(strMenuName.equalsIgnoreCase("LRG EVM MENU"))
				{
					try{
						drinkButtonNames =  getExcelProductCombination("POSCombinationData","LrgEVMDrinkButtonName"); //LrgEVMDrinkButtonName
						SideItemButtonName = getExcelProductCombination("POSCombinationData","LrgEVMSideItemButtonName"); //LrgEVMSideItemButtonName
					}
					catch (IOException e)
					{			
						e.printStackTrace();									
					}
				}
				//****************Medium employee meal***************
				else if(strMenuName.equalsIgnoreCase("MED EMPLOYEE"))
				{
					try{
						drinkButtonNames = getExcelProductCombination("POSCombinationData","MedEmployeeDrinkButtonName"); //MedEmployeeDrinkButtonName
						SideItemButtonName = getExcelProductCombination("POSCombinationData","MedEmployeeSideItemButtonName");	//MedEmployeeSideItemButtonName				
					}
					catch (IOException e)
					{			
						e.printStackTrace();									
					}
				}
				//****************Large employee meal***************
				else if(strMenuName.equalsIgnoreCase("LRG EMPLOYEE"))
				{
					try{
						drinkButtonNames = getExcelProductCombination("POSCombinationData","LRGEmployeeDrinkButtonName"); //LRGEmployeeDrinkButtonName
						SideItemButtonName = getExcelProductCombination("POSCombinationData","LRGEmployeeSideItemButtonName"); //LRGEmployeeSideItemButtonName 
					}
					catch (IOException e)
					{			
						e.printStackTrace();									
					}
				}
				//**************Medium Big Flavour Wraps***********************************************************************************************************
				else if(strMenuName.equalsIgnoreCase("Med evm Big flavour wraps"))
				{
					try{
						drinkButtonNames = getExcelProductCombination("POSCombinationData","MedEVMDrinkButtonName");
						SideItemButtonName = getExcelProductCombination("POSCombinationData","MedEVMSideItemButtonName");  
					}
					catch (IOException e)
					{			
						e.printStackTrace();									
					}
				}
				//************** Large evm Big flavour wraps ***********************************************************************************************************
				else if(strMenuName.equalsIgnoreCase("Large evm Big flavour wraps"))
				{
					try{
						drinkButtonNames = getExcelProductCombination("POSCombinationData","LrgEVMDrinkButtonName"); //// 
						SideItemButtonName = getExcelProductCombination("POSCombinationData","LrgEVMSideItemButtonName");
					}
					catch (IOException e)
					{			
						e.printStackTrace();									
					}
				}
				//************** BREAKFAST MED EVM ***********************************************************************************************************
				else if(strMenuName.equalsIgnoreCase("BREAKFAST MED EVM"))
				{
					try{
						drinkButtonNames = getExcelProductCombination("POSCombinationData","BrkEVMDrinkButtonName"); //BREAKFASTDrinkButtonName
						SideItemButtonName = getExcelProductCombination("POSCombinationData","BrkEVMSideItemButtonName"); // BREAKFASTSideItemButtonName
					}
					catch (IOException e)
					{			
						e.printStackTrace();									
					}
				}
				//************** Breakfast Employee ***********************************************************************************************************
				else if(strMenuName.equalsIgnoreCase("BREAKFAST EMP"))
				{
					try{
						drinkButtonNames = getExcelProductCombination("POSCombinationData","BrkEMPDrinkButtonName");
						SideItemButtonName = getExcelProductCombination("POSCombinationData","BrkEMPSideItemButtonName");
					}
					catch (IOException e)
					{			
						e.printStackTrace();									
					}
				}
				//************** Med Emp Big Flavour Wraps ***********************************************************************************************************
				else if(strMenuName.equalsIgnoreCase("Med Emp Big Flavour Wraps"))
				{
					drinkButtonNames = getExcelProductCombination("POSCombinationData","MedEmployeeDrinkButtonName");
					SideItemButtonName = getExcelProductCombination("POSCombinationData","MedEmployeeSideItemButtonName");
				}
				//************** Lrg Emp Big Flavour Wraps ***********************************************************************************************************
				else if(strMenuName.equalsIgnoreCase("Lrg Emp Big Flavour Wraps"))
				{
					try{
						drinkButtonNames = getExcelProductCombination("POSCombinationData","LRGEmployeeDrinkButtonName");
						SideItemButtonName = getExcelProductCombination("POSCombinationData","LRGEmployeeSideItemButtonName");
					}
					catch (IOException e)
					{			
						e.printStackTrace();									
					}
				}
				//************** Lrg Emp Big Flavour Wraps ***********************************************************************************************************
				else if(strMenuName.equalsIgnoreCase("HAPPY MEAL"))
				{
					try{
						drinkButtonNames = getExcelProductCombination("POSCombinationData","HappyDrinksButtonName");
						SideItemButtonName = getExcelProductCombination("POSCombinationData","HappyEVMSideItemButtonName");
					}
					catch (IOException e)
					{			
						e.printStackTrace();									
					}
				}

				String[] arrDrinks=drinkButtonNames.trim().split(",");
				strDrink=arrDrinks[0].split("\\|");//this is used at the end just for order completion purpose to click on the drink button (drinkButtonName@salesPanelName)			
				String[] arrSides=SideItemButtonName.split(",");
				strSide = arrSides[0].split("\\|"); //clicks on side item button (sideItemButtonName-SalesPanelData)

				//To check if side item is auto added
				autoAddFunctioalityVerification(strSide,strDrink,r);

				if(strProductName.toUpperCase().contains("PANCAKESSAUSAGE")||strProductName.toUpperCase().contains("BREAKFSTWRPKETCHUP")||strProductName.toUpperCase().contains("BREAKFSTWRPBROWN")) 
				{			
					breakfastItemsNotHavingDrinks(strMenuName,strDrink,drinkButtonNames,SideItemButtonName ,clearChoice,clearChoiceOKButton,r);				
					blnNoSideItemPresent=true;
				}
				else
				{
					//call a function to clear all the choices of ordered meal
					clearChoicesOfOrderedMeal(strMenuName,strProductName,strDrink,strSide,drinkButtonNames,SideItemButtonName,clearChoice,clearChoiceOKButton,r);										
					//call the iteration for drinks and sideItem
					fetchClearChoiceData(strMenuName,r,drinkButtonNames,SideItemButtonName,clearChoice,clearChoiceOKButton,blnNoSideLoop);
				}
				//to complete order add drink,side and dip(where required)			
				if(!blnNoSideItemPresent)
				{
					if(clickButton(strDrink[0]))
					{
						HtmlResult.passed("'Select Drink item to add in Meal'","Drink Item should get selected and added in Meal"," Drink " +strDrink[0]+ " Button clicked to complete the order");
					}
					else
					{
						HtmlResult.failed("'Select Drink item to add in Meal'","Drink Item should get selected and added in Meal"," Drink " +strDrink[0]+ " Failed to click button");
					}

					//select side item at the end to complete the order					
					if(clickButton(strSide[0]))
					{
						HtmlResult.passed("'Select Side item to add in Meal'","Side Item should get selected and added in Meal"," Side Item "+strSide[0] +" Button clicked to complete the order");
					}
					else
					{
						HtmlResult.failed("'Select Side item to add in Meal'","Side Item should get selected and added in Meal"," Side Item "+strSide[0] +" Failed to click button");
					}

					if(strProductName.toUpperCase().contains("NUGGETS")||strProductName.toUpperCase().contains("SELECTS"))
					{
						if(clickButton(menuDips))
						{
							HtmlResult.passed("'Select dip to add in Meal'","Dip should get selected and added in Meal","Dip button clicked to complete the order");
							if(clickButton(dip))
							{
								HtmlResult.passed("'Select dip to add in Meal'","Dip should get selected and added in Meal","Dip button clicked to complete the order");
							}
							else
							{
								HtmlResult.	failed("'Select dip to add in Meal'","Dip should get selected and added in Meal","Failed to click dip button");
							}
							//For 5 SELECTS	add one more dip		
							if(strProductName.toUpperCase().contains("5SELECTS"))
							{
								if(clickButton(dip))
								{
									HtmlResult.passed("'Select dip to add in Meal'","Dip should get selected and added in Meal","Dip button clicked to complete the order");
								}
								else
								{
									HtmlResult.failed("'Select dip to add in Meal'","Dip should get selected and added in Meal","Failed to click dip button");
								}
							}
						}					
					}
				}	
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}	
			//++++++++++++++++++++Check auto add functanility++++++++++++++++++++++++++++++++	
			public void autoAddFunctioalityVerification(String[] strSide,String[] strDrink,Rectangle r) throws FileNotFoundException, IOException
			{
				String Missing="";
				String SIDE="";
				try{
					Missing=getValueFromExcel("Missing");
					SIDE=getValueFromExcel("SIDE");
				}
				catch (Exception e)
				{			
					e.printStackTrace();									
				}

				String strSalesData = ReadSalesPanelUK(r);				
				if(strSalesData.contains(Missing) && strSalesData.contains(SIDE))
				{
					//sides are not auto added so log a defect
					HtmlResult.failed("To verify Auto Add functionality for side Item","Should be able to auto add Side item","Auto add verification failed");
					if(clickButton(strSide[0]))
					{
						HtmlResult.passed("Select side item to add in meal","Side item should get selected","Click button "+strSide[0]);
					}
					else
					{
						HtmlResult.failed("Select side item to add in meal","Side item should get selected","Click button "+strSide[0]);
					}
				}
				else
				{
					//Auto add functionality verified
					HtmlResult.passed("To verify Auto Add functionality for side Item","Should be able to auto add Side item","Auto add verification successful");
				}
			}

			//--------------For pancakes------------------------------------------------------------------
			public void breakfastItemsNotHavingDrinks(String strMenuName,String[] strDrink,String drinkButtonNames, String SideItemButtonName ,String clearChoice, String clearChoiceOKButton,Rectangle r) throws FileNotFoundException, IOException
			{
				boolean blnNoSideLoop=true;
				clickButton(clearChoice);
				fetchClearChoiceData(strMenuName,r,drinkButtonNames,SideItemButtonName,clearChoice,clearChoiceOKButton,blnNoSideLoop);			

				if(clickButton(strDrink[0]))
				{
					HtmlResult.passed("'Select Drink item to add in Meal'","Drink Item should get selected and added in Meal"," Drink " +strDrink[0]+ " Button clicked to complete the order");
				}
				else
				{
					HtmlResult.failed("'Select Drink item to add in Meal'","Drink Item should get selected and added in Meal"," Drink " +strDrink[0]+ " Unable to click Drink Button to complete the order");
				}
			}

			//-----------Internal function to clear all the choices of ordered meal------------------------------------------
			public void clearChoicesOfOrderedMeal(String strMenuName,String strProductName,String[] strDrink,String[] strSide,String drinkButtonNames, String SideItemButtonName ,String clearChoice, String clearChoiceOKButton,Rectangle r) throws FileNotFoundException, IOException
			{							
				String strOKButtonClearChoice="Click OK button on clear choice popup";
				String strClearChoiceButton="Click button - Clear Choice";
				String strButtonClickExpected="Button click should be successful - ";

				//click on clearChoice button		
				if(clickButton(clearChoice))
				{	
					HtmlResult.passed(strClearChoiceButton,strButtonClickExpected+"Clear Choice","Button clicked successfully - "+"Clear Choice");

					//for happy meal
					if(strMenuName.toUpperCase().contains("HAPPY"))
					{
						clickTextOnClearChoicePopUp("ClickTextOnly");
					}

					if(clickButton(clearChoiceOKButton))
					{
						HtmlResult.passed(strOKButtonClearChoice,strButtonClickExpected+"OK Button","Button clicked successfully - "+"OK Button");						
						if(clickButton(clearChoice))
						{
							HtmlResult.passed(strClearChoiceButton,strButtonClickExpected+"Clear Choice","Button clicked successfully - "+"Clear Choice");

							//For happy meal
							if(strMenuName.toUpperCase().contains("HAPPY"))
							{
								clickTextOnClearChoicePopUp("textAndOKButton");					
							}

							if(strProductName.toUpperCase().contains("NUGGETS")||strProductName.toUpperCase().contains("SELECTS"))
							{
								if(strMenuName.toUpperCase().contains("HAPPY"))
								{
									clickTextOnClearChoicePopUp("clearChoiceAndText");	
								}
								if(clickButton(clearChoiceOKButton))
								{
									HtmlResult.passed(strOKButtonClearChoice,strButtonClickExpected+"OK Button","Button clicked successfully - "+"OK Button");									
									if(clickButton(clearChoice))
									{
										HtmlResult.passed(strClearChoiceButton,strButtonClickExpected+"Clear Choice","Button clicked successfully - "+"Clear Choice");
									}
									else
									{
										HtmlResult.failed(strClearChoiceButton,strButtonClickExpected+"Clear Choice"," Failed to click button - "+"Clear Choice"+ " - Button not found ");
									}
								}
								else
								{
									HtmlResult.failed(strOKButtonClearChoice,strButtonClickExpected+"OK Button"," Failed to click button - "+"OK Button"+ " - Button not found ");
								}
							}				
						}
						else
						{
							HtmlResult.failed(strClearChoiceButton,strButtonClickExpected+"Clear Choice"," Failed to click button - "+"Clear Choice"+ " - Button not found ");
						}					
					}
					else
					{
						HtmlResult.failed(strClearChoiceButton,strButtonClickExpected+"Clear Choice"," Failed to click button - "+"Clear Choice"+ " - Button not found ");
					}
				}
				else
				{
					HtmlResult.failed(strClearChoiceButton,strButtonClickExpected+"Clear Choice"," Failed to click button - "+"Clear Choice"+ " - Button not found ");
				}
			}

			//Function for clicking text on happy meal pop up
			public void clickTextOnClearChoicePopUp(String conditon) throws FileNotFoundException, IOException
			{
				String clearChoice="";
				String clearChoiceOKButton="";
				String HappyclearChoicePopUp="";	

				try{
					clearChoice = getValueFromExcel("clearChoiceButtonName").trim();//prop.getProperty("clearChoiceButtonName").trim();
					clearChoiceOKButton = getValueFromExcel("clearChoiceOKButton").trim();							
					HappyclearChoicePopUp = getValueFromExcel("happyMealClearChoicePopup").trim();				
				}
				catch (Exception e)
				{			
					e.printStackTrace();								
				}	

				if(conditon.equals("ClickTextOnly"))
				{
					if(clickButton(HappyclearChoicePopUp)){}				
				}
				else if(conditon.equals("textAndOKButton"))
				{
					if(clickButton(HappyclearChoicePopUp)){}
					if(clickButton(clearChoiceOKButton)){}	
				}
				else
				{
					if(clickButton(clearChoice)){}
					if(clickButton(HappyclearChoicePopUp)){}
				}							
			}

			//fetching drink name and side item name from property file
			public void fetchClearChoiceData(String strMenuName,Rectangle r,String drinkButtonNames, String SideItemButtonName ,String clearChoice, String clearChoiceOKButton,boolean blnNoSideLoop) throws FileNotFoundException, IOException
			{				
				String[] arrDrinkButtonNames = drinkButtonNames.trim().split(",");
				String[] arrSideItemButtonNames = SideItemButtonName.trim().split(",");
				//Drink categories are Hot Drinks, Shakes, Cold Drinks, Bottled Drinks, Iced Drinks
				String drinksCategoryButtonName=" "; 
				String strCombinationDataFetched=" ";
				String drinkButtonName=" ";
				String strExpectedDrinkName=" ";
				String strExpectedSideItemName=" ";
				String flagDrnkClickUpchargeDrnkValidation;
				String sideItemButtonName=" ";
				String strButtonClickExpected="Button click should be successful - ";
				String strClearChoiceButton="Click button - Clear Choice";

				boolean blnUpChargePresent=false;
				boolean blnContinueSideIteration=false;
				boolean blnDataValidatedOnce=false;
				boolean blnClickedDrink = false;
				boolean blnInCorrectData=false;
				boolean blnBreakLoop=false; 
				boolean blnBreakSideLoop=false;

				// fetches the drink names from data.properties file
				int Length=arrDrinkButtonNames.length;
				for(int drinkIndex=0;drinkIndex<Length;drinkIndex++)    //drink1:-mevm Coke-1 MED COKE  //drink2-shakes#vanilla shake - 1 MED VAN SHAKE
				{
					//HtmlResult.passed("-------------------------------","DRINK INDEX - "+drinkIterationNumber,"-------------------------------");
					blnBreakLoop=false;
					blnInCorrectData=false;
					blnDataValidatedOnce=false;
					String strDrinkName = arrDrinkButtonNames[drinkIndex];

					//-------------------FOR DRINKS THAT APPEAR ON FLOATING SCREEN (SHAKES,HOT DRINKS,etc.)----------------	
					//the data is-"shakes#vanilla shake-1 MED VAN SHAKE"
					if(strDrinkName.contains("#")) 
					{
						String[] strDrnk = strDrinkName.trim().split("#");
						for(int drnkindex=0;drnkindex<strDrnk.length;drnkindex++)
						{
							try 
							{	
								if(!strDrnk[drnkindex].isEmpty())
								{
									drinksCategoryButtonName=strDrnk[drnkindex];									
								}
								else
								{
									blnInCorrectData=true;
									strCombinationDataFetched=strDrnk[drnkindex];
								}
								
								drnkindex++;
								//mevm vanillaShake|MED VAN SHAKE
								if(strDrnk[drnkindex].contains("|"))
								{
									String[] strDnk = strDrnk[drnkindex].trim().split("\\|");
									if(!strDnk[0].isEmpty())
									{
										drinkButtonName=strDnk[0];									
									}	
									else
									{
										blnInCorrectData=true;
										strCombinationDataFetched=strDnk[0];
									}
									
									if(!strDnk[1].isEmpty())
									{
										strExpectedDrinkName=strDnk[1];
									}									
								} 								
							}
							catch (ArrayIndexOutOfBoundsException e) 
							{									
								blnInCorrectData=true;
								strCombinationDataFetched=strDrinkName;
							}						
						}
					}
					//drinkButtonName|salesPanelName (mevm coke|MED COKE)
					else if(strDrinkName.contains("|"))
					{
						String[] strDrink = strDrinkName.split("\\|");						
						try {
							//Fetch button name of drink item
							if(!strDrink[0].isEmpty())
							{
								drinksCategoryButtonName=" ";
								drinkButtonName=strDrink[0];							
							}
							else
							{
								blnInCorrectData=true;
								strCombinationDataFetched=strDrinkName;
							}
							
							//Fetch sales panel name
							if(!strDrink[1].isEmpty())
							{
								strExpectedDrinkName=strDrink[1];
							}
						}
						catch(ArrayIndexOutOfBoundsException e)
						{
							blnInCorrectData=true;
							strCombinationDataFetched=strDrinkName;
						}						
					}
					else
					{
						blnInCorrectData=true;
					}
					if(blnInCorrectData)
					{
						HtmlResult.failed("Data fetched is incorrect ","Fetched data must not be empty or null","Actual data fetched is - ' "+strCombinationDataFetched+" '");
						blnBreakLoop=true;
					}

					//If drink item data is correct then fetch side item data else fetch new drink
					if(!blnBreakLoop)
					{
						//for loop for side items
						int length=arrSideItemButtonNames.length;
						for(int sideIndex=0;sideIndex<length;sideIndex++) 
						{
							//HtmlResult.addMessage("------FOR SIDE ITEM - "+arrSideItemButtonNames[sideIndex]+"-------");
							//HtmlResult.passed("-------------------------------","SIDE ITEM INDEX - "+sideItemIterationNumber,"-------------------------------");
							blnBreakSideLoop=false;
							String strSide = arrSideItemButtonNames[sideIndex];
							if(strSide.contains("|"))
							{
								String[] str = strSide.split("\\|");
								try {
									//Fetch side item button name
									if(!str[0].isEmpty())
									{
										sideItemButtonName=str[0];							
									}
									else
									{
										blnInCorrectData=true;
										blnBreakSideLoop=true;
										strCombinationDataFetched=strSide;
									}
									
									//Fetch sales panel name
									if(!str[1].isEmpty())
									{
										strExpectedSideItemName=str[1];
									}
								}
								catch(ArrayIndexOutOfBoundsException e)
								{
									blnInCorrectData=true;
									strCombinationDataFetched=strSide;
								}							
							}
							else
							{
								blnInCorrectData=true;
								blnBreakSideLoop=true;
							}

							//If side item data is correct carry out clear choice verification else fetch next side item
							if(!blnBreakSideLoop)
							{
								try 
								{
									HtmlResult.addMessage("FOR CHOICES - ( DRINK - "+strExpectedDrinkName+" , SIDE ITEM - "+strExpectedSideItemName+" )");
									flagDrnkClickUpchargeDrnkValidation=clearChoiceVerification(strMenuName,blnClickedDrink,blnContinueSideIteration,r,drinksCategoryButtonName,drinkButtonName,sideItemButtonName,strExpectedDrinkName,strExpectedSideItemName,blnNoSideLoop,blnDataValidatedOnce);								
									String[] arrStatus=flagDrnkClickUpchargeDrnkValidation.split("#");
									blnClickedDrink=Boolean.valueOf(arrStatus[0].trim()); //returns true if drink is clicked
									blnUpChargePresent=Boolean.valueOf(arrStatus[1].trim());
									blnDataValidatedOnce=Boolean.valueOf(arrStatus[2].trim());
								} 
								catch (Exception e) 
								{
									blnClickedDrink=false;
								}
								
								//If drink is not clicked then break the loop for side items and fetch the next drink
								if(!blnClickedDrink) 
								{
									blnBreakSideLoop=true;	
								}
							}	
							//If data is incorrect log failed 
							if(blnInCorrectData||blnBreakSideLoop)
							{
								if(blnInCorrectData)
								{
									HtmlResult.failed("Data fetched is incorrect ","Fetched data must not be empty or null","Actual data fetched is - ' "+strCombinationDataFetched+" '");
								}
								blnBreakLoop=true;
							}
							if(!blnClickedDrink) //if drink is not clicked the break the side loop
							{
								break;					
							}							
						}	
					}

					//If drink button is clicked clear it
					if(blnClickedDrink)
					{
						if(clickButton(clearChoice))
						{
							HtmlResult.passed(strClearChoiceButton,strButtonClickExpected+"Clear Choice","Clear Choice"+ " - Button clicked successfully ");																																					
							//for happy meal
							if(strMenuName.toUpperCase().contains("HAPPY"))
							{
								clickTextOnClearChoicePopUp("textAndOKButton");
							}

							//If three items present on clear choice pop up
							if(blnUpChargePresent)
							{	
								if(clickButton(clearChoiceOKButton));							
							}
							
							HtmlResult.passed("Click clear choice button"," All choices should get cleared "," All Choices cleared successfully ");			
						}
						else
						{
							HtmlResult.failed(strClearChoiceButton,strButtonClickExpected+"Clear Choice"," Failed to click button - "+"Clear Choice"+ " - Button not found ");
						}				
					}
					blnClickedDrink=false;
				}								
			}

			public String clearChoiceVerification(String strMenuName,boolean blnDrinkAlreadySelected,boolean blnContinueSideIteration,Rectangle r,String drinksCategoryButtonName,String drinkButtonName,String sideItemButtonName,String strActualDrinkProductName,String strActualSideProductName,boolean blnNoSideLoop,boolean blnDataValidatedOnce) throws FileNotFoundException, IOException 			
			{  
				boolean blnDrinkSelected=false; //if true breaks the loop to fetch next drink if in case drink name is wrongly entered
				boolean blnProductNotSalable=false; //to check presence of product not salable pop up (This boolean variable becomes true when data validation fails)
				boolean blnSideItemSelected=false;
				boolean blnUpChargePresent=false; //if true twice clear choice OK button will be clicked				
				String clearChoice=" ";
				String clearChoiceOKButton=" ";
				String choiceNotMade=" ";
				String drinksHavingUpcharge=" ";
				String Missing=" ";
				String SIDE="";

				String strButtonClickstepDescrp="Click button to add ";
				String strButtonClickExpected="Button click should be successful - ";
				String strClearChoiceButton="Click button - Clear Choice";

				try{
					clearChoice=getValueFromExcel("clearChoiceButtonName");//prop.getProperty("clearChoiceButtonName").trim();
					clearChoiceOKButton=getValueFromExcel("clearChoiceOKButton");	
					choiceNotMade=getValueFromExcel("choiceNotMade");
					drinksHavingUpcharge=getValueFromExcel("drinksHavingUpcharge");
					Missing=getValueFromExcel("Missing");
					SIDE=getValueFromExcel("SIDE");

					if(drinksHavingUpcharge.contains(drinkButtonName))
					{
						blnUpChargePresent=true;
					}

					if(!blnDrinkAlreadySelected)
					{
						//Select drink category e.g Shakes 
						if(drinksCategoryButtonName!=" ")
						{
							if(clickButton(drinksCategoryButtonName.trim()))
							{
								HtmlResult.passed(strButtonClickstepDescrp+" - "+drinksCategoryButtonName,strButtonClickExpected+" "+drinksCategoryButtonName,"Button clicked successfully - "+drinksCategoryButtonName);
								blnDrinkSelected=true; 
							}
							else
							{
								HtmlResult.failed(strButtonClickstepDescrp+" - "+drinksCategoryButtonName,strButtonClickExpected+" "+drinksCategoryButtonName," Failed to click button - "+drinksCategoryButtonName+ " - Button not found ");							
							}
						}
						//Select drink which is vanilla shake that appears on screen after clicking on drink category(shakes) 									
						if(clickButton(drinkButtonName.trim()))
						{
							blnDrinkAlreadySelected=true;
							HtmlResult.passed(strButtonClickstepDescrp+" - "+drinkButtonName,strButtonClickExpected+" "+drinkButtonName,"Button clicked successfully - "+drinkButtonName);		
							blnDrinkSelected=true;	
						}
						else
						{
							HtmlResult.failed(strButtonClickstepDescrp+" - "+drinkButtonName,strButtonClickExpected+" "+drinkButtonName," Failed to click button - "+drinkButtonName+ " - Button not found ");								
							blnDrinkSelected=false;	
							blnDrinkAlreadySelected=false;
						}
					}
					//If drink is clicked then click side item otherwise fetch next drink
					//Also if drink1(i.e icedDrinks/milkshakes/coldDrink/bottledDrink..) clicked then only click its sub category drinks
					if(blnDrinkSelected || blnDrinkAlreadySelected)
					{
						if(blnNoSideLoop==true)
						{
							//side items not auto added	i.e in case of pancakes,breakfstwrps, only drinks are added 		
						}
						else
						{
							//validate drink data
							if(!blnDataValidatedOnce)
							{
								//Data validation for drink
								//present = strSalesData.contains(strActualDrinkProductName);  //verify 1 MED VAN SHAKE					
								if(!verifyDataOnSalesPanel(strActualDrinkProductName,r))
								{blnProductNotSalable=true;}
								blnDataValidatedOnce=true;
							}

							//Click side item and validate it				
							if(clickButton(sideItemButtonName))
							{
								HtmlResult.passed(strButtonClickstepDescrp+" - "+sideItemButtonName,strButtonClickExpected+" "+sideItemButtonName,"Previous choice cleared and another Side Item Button clicked successfully - "+sideItemButtonName);																						
								//Sales panel data verification for side item					
								if(verifyDataOnSalesPanel(strActualSideProductName,r))
								{blnSideItemSelected=true;}
								else
								{								
									//failed(strstepDescrp,strExpected+strActualSideProductName+")","Actual Data on sales panel- " +strSalesData);
									//check if product not salable pop up occurred
									String strSalesPanelData=ReadSalesPanelUK(r);
									if(strSalesPanelData.contains(Missing)&&strSalesPanelData.contains(SIDE))
									{
										clickButton(sideItemButtonName);
										blnProductNotSalable=productNotSalableVerification(sideItemButtonName,drinkButtonName);
										if(blnProductNotSalable)
										{blnSideItemSelected=false;}
										else
										{blnSideItemSelected=true;}
									}
									else
									{blnSideItemSelected=true;}
								}						
							}
							else
							{
								HtmlResult.failed(strButtonClickstepDescrp+" - "+sideItemButtonName,strButtonClickExpected+" "+sideItemButtonName," Failed to click button - "+sideItemButtonName+ " - Button not found ");
							}	
						}
						//If Side Item is selected then clear it else fetch next side item
						if(blnSideItemSelected)
						{	
							if(clickButton(clearChoice))
							{										
								HtmlResult.passed(strClearChoiceButton,strButtonClickExpected+"Clear Choice"," Button - "+"Clear Choice"+ " - clicked sucessfully ");																		
								//for happy meal
								if(strMenuName.toUpperCase().contains("HAPPY"))
								{
									clickTextOnClearChoicePopUp("ClickTextOnly");
								}

								//found=clickButton(clearChoiceOKButton);
								//eggUIDriver.clickTextInRectangle("OK", ScreenPart(0.25,0.49,0.53,0.66))
								if(clickButton(clearChoiceOKButton))
								{								
									HtmlResult.passed("Clear the side item"," Side item should get cleared "," Side item cleared ");
								}
								else
								{
									HtmlResult.failed("Clear the side item"," Side item should get cleared "," Side item not cleared ");										
									if(clickButton(choiceNotMade))
									{
										if(clickButton(clearChoice))
										{										
											if(clickButton(clearChoiceOKButton)){}																											
										}
										else{}									
									}
									else
									{									
										if(clickButton(clearChoice))
										{										
											if(clickButton(clearChoiceOKButton)){}										
										}
										else{}									
									}											
								}
							}
							else
							{
								HtmlResult.failed(strClearChoiceButton,strButtonClickExpected+"Clear Choice"," Failed to click button - "+"Clear Choice"+ " - Button not found ");
							}				
						}								
					}
					String clickedDrinkStatusAndUpChargePresent=blnDrinkAlreadySelected + "#"+ blnUpChargePresent+ "#"+blnDataValidatedOnce;
					return clickedDrinkStatusAndUpChargePresent;
				}


				catch(Exception e)
				{
					e.printStackTrace();
					return null;
				}

			}

			public boolean verifyDataOnSalesPanel(String strExpectedSalesPanelData,Rectangle r)
			{
				boolean blnFlag=false;
				String[] strArrActualData;
				String strActualData="";
				String SalesPanelDetails = "";
				String strExpectedSalesData="";
				strExpectedSalesData=strExpectedSalesPanelData;
				strExpectedSalesData="1 "+strExpectedSalesData.trim();
				String[] strArrExpectedData = {strExpectedSalesData};
				
				SalesPanelDetails=readSalesPanelLineByLine(strExpectedSalesData);
				
				SalesPanelDetails = SalesPanelDetails.replaceAll("_|\"|\\*|\\#|\\%|\\>|\\$|\\\\N|\\#|\\#|\\#|", "").trim();
				SalesPanelDetails = SalesPanelDetails.replaceAll("\\\\r|\\{|\\}", "").replaceAll("w/", "").trim();
				SalesPanelDetails = SalesPanelDetails.
						replaceAll("[0-9]{1,}[.][0-9]{1,}", "").replaceAll("[-]{3,}", "").
						replaceAll("[\n]{2,}", "\n").replaceAll("[\t]{1,}", "");
				SalesPanelDetails = SalesPanelDetails.replaceAll("±", "");
				SalesPanelDetails = SalesPanelDetails.replaceAll("[a-w]", "").replaceAll("[y-z]", "");
				strActualData=SalesPanelDetails.trim();
				strArrActualData=strActualData.split("\n");
				
				//reporting string variables
				String StepDesc="To verify Sales panel data";
				String strExpected=strExpectedSalesPanelData+" should be present on sales panel-' ";

				//boolean variable to verify actions
				String VerificationStatus = verifyMeal(strArrExpectedData,strArrActualData);

				//matching expected with actual normally
				if(VerificationStatus.equals("passed"))
				{
					HtmlResult.passed(StepDesc, strExpected, "Order present on sales panel-' "+strActualData+"' ");
					blnFlag=true;
				}
				else if(VerificationStatus.equals("warning"))
				{
					HtmlResult.warning(StepDesc, strExpected, "Order present on sales panel-' "+strActualData+"' ");
					blnFlag=true;
				}
				else//if previous verification failed then verify with on contrast setting
				{
					//actual sales panel details with 'on contrast' settings
					/*   */		
					String strActualDataRead="";
					strActualDataRead=ReadSalesPanel();
					String[] strArrActualDataOnContrast=strActualDataRead.split("\n");

					String VerificationFlag =verifyMeal(strArrExpectedData,strArrActualDataOnContrast);

					if(VerificationFlag.equals("passed"))
					{
						HtmlResult.passed(StepDesc, strExpected, "Order present on sales panel-' "+strActualData+"' ");
						blnFlag=true;
					}
					else if(VerificationStatus.equals("warning"))
					{
						HtmlResult.warning(StepDesc, strExpected, "Order present on sales panel-' "+strActualData+"' ");
					}
					else 
					{
						HtmlResult.failed(StepDesc, strExpected, "Order present on sales panel-' "+strActualData+"' ");
					}
				}
				return blnFlag;
			}

			//Verification of product not salable
			public boolean productNotSalableVerification(String sideItem,String drinkItem) throws FileNotFoundException, IOException
			{
				String ProductNotSalableOKButton=" ";
				String ProductNotSalable=" ";
				boolean blnProductNotSalable=false;

				try{
					ProductNotSalableOKButton=getValueFromExcel("ProductNotSalableOKButton").trim();
					ProductNotSalable=getValueFromExcel("ProductNotSalableOKButton").trim();
				}
				catch(Exception e)
				{
					e.printStackTrace();									
				}

				//----if product not salable image is found---------------
				blnProductNotSalable=eggUIDriver.imageFound(ProductNotSalable);//"1",ProductNotSalable"
				if(blnProductNotSalable)
				{
					boolean flag=clickButton(ProductNotSalableOKButton);
					if(flag)
					{										
						HtmlResult.failed("Verification for Product not salable ","","Product "+sideItem+" is not salable");
					}
					blnProductNotSalable=true;								
				}
				else
				{
					blnProductNotSalable=false;
				}
				return blnProductNotSalable;							
			}


			private String ReadSalesPanelUK(Rectangle r) 
			{
				String SalesPanelDetails = "";
				try {			
					SalesPanelDetails = eggUIDriver.readText(r);// read data from cordinates referenced by object r				

					SalesPanelDetails = SalesPanelDetails.replaceAll("_|\"|\\*|\\#|\\%|\\>|\\$|\\\\N|\\?|\\?|\\?|", "").trim();
					//SalesPanelDetails = SalesPanelDetails.replaceAll("_|\"|\\*|\\#|\\%|\\>|\\$|\\\\N|\\?|\\?|\\(|\\)|", "").replaceAll("±", "").trim();
					//SalesPanelDetails = SalesPanelDetails.replaceAll("\\\\r|\\{|\\}", "").replaceAll("w/", "").trim();
					SalesPanelDetails=SalesPanelDetails.replaceAll("\t","\n");

				} catch (Exception e) {
					HtmlResult.failed("Reading Sales Panel data","Function should return Sales Panel Data","Unable to read Verify Sales Panel due to this -"  + e.getMessage());
				}
				return SalesPanelDetails;
			}		

		//***********************************************Clear choice code ends here****************************************************************************************//	





	//**********************************************Meal Balancing*****************************************************//
	public boolean MatchStringOnSalesPanel(String strExpected,Rectangle r)

	{	String Description = "";	
	String strActualString="";
	String strReport = "" ;	
	String[] arrActual;		
	boolean breakLoop = false;

	strActualString = eggUIDriver.readText(r);				

	//System.out.println(strActualString);

	strActualString = strActualString.replaceAll("_|\"|\\*|\\#|\\%|\\>|\\$|\\\\N|\\?|\\?|\\?|", "").trim();	
	strActualString=strActualString.replaceAll("\t","\n");

	if(strActualString.toUpperCase().contains("NUGGET")||strActualString.toUpperCase().contains("SELECT"))
	{
		arrActual = strActualString.split("\n[0-9]*|1|0|,|[0-9]\\.[0-9]*");
	}
	else
	{
		arrActual = strActualString.split("\n[0-9]*|1|0|,| [0-9]");		
	}

	if(strExpected.contains("#"))			
	{
		String[] arrExpected = strExpected.split("#");
		breakLoop=VerifyMeal(arrExpected,arrActual);			
	}
	else
	{			
		for(String ActualData:arrActual)
		{			
			strReport = ActualData + strReport;
			if(ActualData.trim().equals(strExpected.trim()))
			{										
				breakLoop=true;						
				break;
			}				
		}				
	}	

	if(breakLoop==false)
	{	
		for(int i=25;i<=200;i=i+50)
		{	
			for(int j=10;j<=350;j=j+50)
			{
				Description = "";
				Description = "contrast:on,contrastColor:"+eggUIDriver.ContrastBlack+","+"contrastTolerance:"+i+",DPI:"+j;				 
				strActualString = eggUIDriver.readText(r, Description);				
				//	System.out.println(strActualString);			

				Description = "contrast:on,contrastColor:"+eggUIDriver.ContrastRed+","+"contrastTolerance:"+i+",DPI:"+j;
				strActualString = strActualString+"\n" + eggUIDriver.readText(r, Description);
				//	System.out.println(strActualString);

				Description = "contrast:on,contrastColor:"+eggUIDriver.ContrastWhite+","+"contrastTolerance:"+i+",DPI:"+j;
				strActualString = strActualString+"\n" +eggUIDriver.readText(r, Description);		
				//System.out.println(strActualString);		

				strActualString = strActualString.replaceAll("_|\"|\\*|\\#|\\%|\\>|\\$|\\\\N|\\?|\\?|\\?|", "").trim();	
				strActualString=strActualString.replaceAll("\t","\n");				

				if(strActualString.toUpperCase().contains("NUGGET")||strActualString.toUpperCase().contains("SELECT"))
				{
					arrActual = strActualString.split("\n[0-9]*|1|0|,");
				}
				else
				{
					arrActual = strActualString.split("\n[0-9]*|1|0|,| [0-9]");		
				}

				if(strExpected.contains("#"))			
				{
					String[] arrExpected = strExpected.split("#");
					breakLoop=VerifyMeal(arrExpected,arrActual);			
				}
				else
				{			
					for(String ActualData:arrActual)
					{			
						strReport = ActualData + strReport;
						if(ActualData.trim().equals(strExpected.trim()))
						{										
							breakLoop=true;						
							break;
						}				
					}				
				}
				if(breakLoop==true)
					break;
			}
			if(breakLoop==true)
				break;
		}
	}	
	String strTestStepDesc = " To Match the String in Array -"+"'"+strExpected+"'";
	if(!breakLoop)
	{
		HtmlResult.failed(strTestStepDesc, "'"+ strExpected + "' product should be displayed on sales panel ", "Instead of "+strExpected+ " '" + strActualString + "' products are displayed on sales panel");

	}
	return breakLoop;
	}

	//**********************************************Meal Balancing*****************************************************//
	public void verifyMealBalancing(Map<String,String> input)
	{
		String strSideDrinkSalesPanelName=input.get("sideDrinkSalesPanelName");
		String strSideProductSalesPanelName=input.get("sideProductSalesPanelName");
		String strMealBalancingFlag=input.get("mealBalancingFlag");
		String strStepDesc="To verify meal balancing functionality";
		boolean VerifyForSideItems=false;
		boolean VerifyFoDrinkItems=false;

		//for products where side item is not required to check for meal balancing
		if(strSideProductSalesPanelName.length()<=2)
		{
			VerifyFoDrinkItems = true;
		}
		else
		{
			VerifyFoDrinkItems = true;
			VerifyForSideItems=true;
		}
		
		//------meal balancing for side item--------
		if(VerifyForSideItems)
		{
			String SideItemDescOnSalesPanel = readSalesPanelLineByLine(strSideProductSalesPanelName.trim());
			String[] SideItemAndPrice = SideItemDescOnSalesPanel.split("\n");
			int Length = SideItemAndPrice.length;
			String strExpectedForFries="";
			
			if(Length==2)
			{
				try {
					strExpectedForFries="Price should not be added to -' "+SideItemAndPrice[0]+" ' ";
					String SideItemName = SideItemAndPrice[0];
					double Price = Double.parseDouble(SideItemAndPrice[1]);
					
					if(Price>0.0)
					{
						HtmlResult.failed(strStepDesc, strExpectedForFries, "Price for product-" + SideItemName
								+ "\nis mentioned in sales panel as-" + Price);//if price is mentioned but more than 0.0 than failed
					}
					else if(Price<=0.0)
					{
						HtmlResult.passed(strStepDesc, strExpectedForFries, "Price for product-" + SideItemName
								+ "\nis mentioned in sales panel as-" + Price);// if price is not more than 0.0 than passed
					}
				} catch (NumberFormatException e) {
					HtmlResult.failed(strStepDesc, strExpectedForFries, "Price for product-" + SideItemAndPrice[0]
							+ "\nis mentioned in sales panel as-" + SideItemAndPrice[1]);// failed when price is not represented as a number	
				}
			}
			else if(Length==1)
			{
				strExpectedForFries="Price should not be added to -' "+SideItemAndPrice[0]+" ' ";
				HtmlResult.passed(strStepDesc, strExpectedForFries, "Price for product-" + SideItemAndPrice[0]
						+" is not mentioned in sales panel");// price is less than 0.0
			}
			else if(Length>2)
			{
				strExpectedForFries="Price should not be added to side item of -' "+Arrays.toString(SideItemAndPrice)+" ' ";
				HtmlResult.failed(strStepDesc, strExpectedForFries, "Side item'"+strSideProductSalesPanelName+"'is not  found in sales panel");// failed when price is not represented as a number	
			}
		}
		
		//------meal balancing for drink item--------
		if(strSideDrinkSalesPanelName.length()>3)
		{
			if(VerifyFoDrinkItems)
			{
				// meal balancing flag is yes-price should be added to drink
				// meal balancing flag is no-price should not be added to drink
				
				if (strMealBalancingFlag.equalsIgnoreCase("YES")) 
				{
					String strExpectedWhenYes="Meal Balancing flag is YES price should be added to ' "+strSideDrinkSalesPanelName.trim()+" ' " ;
					String DrinkItemDescOnSalesPanel = readSalesPanelLineByLine(strSideDrinkSalesPanelName.trim());
					String[] DrinkItemAndPrice = DrinkItemDescOnSalesPanel.split("\n");
					int Length = DrinkItemAndPrice.length;
					
					if(Length==2)
					{
						try {
							
							String DrinkItemName = DrinkItemAndPrice[0];
							double Price = Double.parseDouble(DrinkItemAndPrice[1]);
							
							if(Price>0.0)
							{
								HtmlResult.passed(strStepDesc, strExpectedWhenYes, "Price for product-" + DrinkItemName
										+ "\nis mentioned in sales panel as-" + Price);//if price is mentioned but more than 0.0 than failed
							}
							else if(Price<=0.0)
							{
								HtmlResult.failed(strStepDesc, strExpectedWhenYes, "Price for product-" + DrinkItemName
										+ "\nis mentioned in sales panel as-" + Price);// if price is not more than 0.0 than passed
							}
						} catch (NumberFormatException e) {
							HtmlResult.failed(strStepDesc, strExpectedWhenYes, "Price for product-" + DrinkItemAndPrice[0]
									+ "\nis mentioned in sales panel as-" +DrinkItemAndPrice[1]);// failed when price is not represented as a number	
						}
					}
					else if(Length==1 && DrinkItemAndPrice[0].equals(strSideDrinkSalesPanelName))
					{
						HtmlResult.failed(strStepDesc,strExpectedWhenYes,"Price for product-"+DrinkItemAndPrice[0]+"\n is not found in sales panel");
					}
					else
					{
						HtmlResult.failed(strStepDesc, strExpectedWhenYes, "Drink item '"+strSideDrinkSalesPanelName+"' is not  found in sales panel");// item not found	
					}
				}
				else if(strMealBalancingFlag.equalsIgnoreCase("NO"))
				{
					String strExpectedWhenNo="Meal Balancing flag is No price should not be added to ' "+strSideDrinkSalesPanelName.trim()+" ' " ;
					String DrinkItemDescOnSalesPanel = readSalesPanelLineByLine(strSideDrinkSalesPanelName.trim());
					String[] DrinkItemAndPrice = DrinkItemDescOnSalesPanel.split("\n");
					int Length = DrinkItemAndPrice.length;
					
					if(Length==2)
					{
						try {
							
							String DrinkItemName = DrinkItemAndPrice[0];
							double Price = Double.parseDouble(DrinkItemAndPrice[1]);
							
							if(Price<=0.0)
							{
								HtmlResult.passed(strStepDesc, strExpectedWhenNo, "Price for product-" + DrinkItemName
										+ "\nis mentioned in sales panel as-" + Price);//if price is mentioned but more than 0.0 than failed
							}
							else if(Price>0.0)
							{
								HtmlResult.failed(strStepDesc, strExpectedWhenNo, "Price for product-" + DrinkItemName
										+ "\nis mentioned in sales panel as-" + Price);// if price is not more than 0.0 than passed
							}
						} catch (NumberFormatException e) {
							HtmlResult.failed(strStepDesc, strExpectedWhenNo, "Price for product-" + DrinkItemAndPrice[0]
									+ "\nis mentioned in sales panel as-" +DrinkItemAndPrice[1]);// failed when price is not represented as a number	
						}
					}
					else if(Length<=1)
					{
						HtmlResult.passed(strStepDesc,strExpectedWhenNo,"Price for product-"+DrinkItemAndPrice[0]+"\n is not found in sales panel");
					}
					else
					{
						HtmlResult.failed(strStepDesc, strExpectedWhenNo, "Drink item '"+strSideDrinkSalesPanelName+"' is not  found in sales panel , OCR read ' "+DrinkItemAndPrice[0]+" '");// item not found	
					}
				}
				else
				{
					HtmlResult.failed("To verify meal balancing ", "Meal balancing should be verified for ordered meal ", "Wrong value in 'strMealBalancingFlag' parameter it should be YES or NO");
				}
			}
		}
		else
		{
			HtmlResult.failed("To verify meal balancing ", "Meal balancing should be verified for ordered meal ", "Wrong drink item name provided-' "+strSideDrinkSalesPanelName+" '");
		}
		
	}
	
	public void verifySmartReminder(Map<String,String> input) throws JXLException
	{
		Rectangle rectangle = new Rectangle();			
		rectangle=eggUIDriver.imageRectangle("LeftTopCorner", "RightBottomCorner");

		Map<String,String> Input = new HashMap<String,String>();
		boolean blnTestExecution;
		boolean blnExit=false;

		String strTestStepDesc = "";		
		String strDrink = "";
		String sideItem = "";
		String strProducts="";
		String strDrinks = "";
		String strSideProducts ="";
		String strDrinkNameOnSalesPanel="";
		String strDrinkNameAfterTotal="";
		String strSideProductNameAfterTotal="";
		String strBeforeEatInProductNamesOnSP = "";
		String strAfterEatInProductNamesOnSP = "";
		String strMealType = "";	
		String strMedEVMSide="";
		String strEVMNuggetsDips =""; 
		String strEVM3SelectDip = "";
		String strdrinkReminder =""; 
		String strDrinkAndSideReminder="";
		String strDrinkMsg=""; 
		String strSideMsg="";	
		String strMissing= "";
		String strAutoAdd="";
		String strDipMsg="";
		String TakeOutButton = "";
		String ExactCashButton = "";
		String ClearChoiceButton = "";	
		String strEvmSigBeefSideMsg = ""; 
		String strEVMSigBeefSide = "";
		String printFailResult="N";
		String strEvmSigBeefAutoAdd = "";

		//-----------This variables are deifned to hold the temp values
		String temp1 = "";			
		String temp2 ="";
		String temp3  ="";	

		String[] arrDrinks = null;
		String[] arrSideProducts = null;			

		strMealType = input.get("mealType");			

		strBeforeEatInProductNamesOnSP = input.get("beforeEatInProductNamesOnSP");
		strAfterEatInProductNamesOnSP = input.get("afterEatInProductNamesOnSP");

		try {
			ExactCashButton = getValueFromExcel( "EXACTCASH");
			TakeOutButton = getValueFromExcel("EATINTOTAL");
			ClearChoiceButton = getValueFromExcel( "CLEARCHOICE");				
			getValueFromExcel( "ClearChoiceOK");				

			strMissing= getValueFromExcel( "Missing");	

			//  item in DIP - Need to confirm
			strEVMNuggetsDips = getValueFromExcel( "EVMNuggetsDips");
			strEVM3SelectDip = getValueFromExcel( "EVM3SelectDips");
			getValueFromExcel( "EVM5SelectDips");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		


		if(strMealType.equals("MED EVM")||strMealType.equals("Med Big flavour wraps"))
		{
			try {					
				strDrinks= getExcelProductCombination("POSCombinationData", "MedEVMDrinkButtonName");
				strSideProducts= getExcelProductCombination("POSCombinationData", "MedEVMSideItemButtonName");

				strdrinkReminder = getValueFromExcel("MedEVMdrinkReminder");
				strDrinkAndSideReminder = getValueFromExcel( "MedEVMDrinkSideReminder");

				strAutoAdd = getValueFromExcel( "MedAutoAdd");						

				strDrinkMsg = getValueFromExcel( "MedEvmDrinkMsg");
				strSideMsg = getValueFromExcel( "MedEvmSideMsg");	
				strDipMsg = getValueFromExcel( "MedEvmDipMsg");

				strMedEVMSide = getValueFromExcel( "MedEVMSide");


				strEvmSigBeefSideMsg = getValueFromExcel( "MedEvmSigBeefSideMsg");
				strEVMSigBeefSide = getValueFromExcel( "MedEVMSigBeefSide");
				strEvmSigBeefAutoAdd = getValueFromExcel( "MedEvmSigBeefAutoAdd");

				temp1 = strSideMsg; 
				temp2 = strMedEVMSide;  
				temp3 = strAutoAdd; 

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}				

		if(strMealType.trim().equals("LRG EVM")||strMealType.equals("Lrg Big flavour wraps"))
		{
			try {					
				strDrinks= getExcelProductCombination("POSCombinationData", "LrgEVMDrinkButtonName");
				strSideProducts= getExcelProductCombination("POSCombinationData", "LrgEVMSideItemButtonName");

				strdrinkReminder = getValueFromExcel( "LrgEVMdrinkReminder");						
				strDrinkAndSideReminder = getValueFromExcel( "LrgEVMDrinkSideReminder");						

				strDrinkMsg = getValueFromExcel( "LrgEvmDrinkMsg");
				strSideMsg = getValueFromExcel( "LrgEvmSideMsg");

				strAutoAdd = getValueFromExcel( "LrgAutoAdd");							
				strDipMsg = getValueFromExcel( "LrgEvmDipMsg");

				strMedEVMSide = getValueFromExcel( "LrgEVMSide");

				strEvmSigBeefSideMsg = getValueFromExcel( "LrgEvmSigBeefSideMsg");
				strEVMSigBeefSide = getValueFromExcel( "LrgEVMSigBeefSide");
				strEvmSigBeefAutoAdd = getValueFromExcel( "LrgEvmSigBeefAutoAdd");					

				temp1 = strSideMsg; 
				temp2 = strMedEVMSide;  
				temp3 = strAutoAdd; 

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}

		if(strMealType.equals("HAPPY MEAL"))
		{
			try {						
				strDrinks= getExcelProductCombination("POSCombinationData", "HappyDrinksButtonName");
				strSideProducts= getExcelProductCombination("POSCombinationData", "HappyEVMSideItemButtonName");

				strdrinkReminder = getValueFromExcel( "HappydrinkReminder");						

				strDrinkMsg = getValueFromExcel( "HappyDrinkMsg");
				strSideMsg = getValueFromExcel( "HappySideMsg");						
				strAutoAdd = getValueFromExcel( "HappyAutoAdd");

				strMedEVMSide = getValueFromExcel( "HappySide");
				strDipMsg = getValueFromExcel( "HappyEvmDipMsg");

				temp1 = strSideMsg; 
				temp2 = strMedEVMSide;  
				temp3 = strAutoAdd; 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}	

		if(strMealType.equals("Med EMP")||strMealType.equals("Med Flavour Emp"))
		{
			try {					

				strDrinks= getExcelProductCombination("POSCombinationData", "MedEmployeeDrinkButtonName");
				strSideProducts= getExcelProductCombination("POSCombinationData", "MedEmployeeSideItemButtonName");

				strdrinkReminder = getValueFromExcel( "MedEMPdrinkReminder");						
				strDrinkAndSideReminder = getValueFromExcel( "MedEMPDrinkSideReminder");						

				strDrinkMsg = getValueFromExcel( "MedEMPDrinkMsg");
				strSideMsg = getValueFromExcel( "MedEMPSideMsg");

				strAutoAdd = getValueFromExcel( "MedEMPAutoAdd");							
				strDipMsg = getValueFromExcel( "MedEMPDipMsg");

				strMedEVMSide = getValueFromExcel( "MedEMPSide");
				temp1 = strSideMsg; 
				temp2 = strMedEVMSide;  
				temp3 = strAutoAdd; 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}

		if(strMealType.equals("Large EMP")||strMealType.equals("Large Flavour Emp")||strMealType.equals("LRG EMP"))
		{
			try {						
				strDrinks= getExcelProductCombination("POSCombinationData", "LRGEmployeeDrinkButtonName");
				strSideProducts= getExcelProductCombination("POSCombinationData", "LRGEmployeeSideItemButtonName");

				strdrinkReminder = getValueFromExcel( "LrgEMPdrinkReminder");						
				strDrinkAndSideReminder = getValueFromExcel( "LrgEMPDrinkSideReminder");						

				strDrinkMsg = getValueFromExcel( "LrgEMPDrinkMsg");
				strSideMsg = getValueFromExcel( "LrgEMPSideMsg");

				strAutoAdd = getValueFromExcel( "LrgEMPAutoAdd");							
				strDipMsg = getValueFromExcel( "LrgEMPDipMsg");

				strMedEVMSide = getValueFromExcel( "LrgEMPSide");
				temp1 = strSideMsg; 
				temp2 = strMedEVMSide;  
				temp3 = strAutoAdd; 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}

		if(strMealType.trim().equals("BREAKFAST MED EVM")||strMealType.equals("Brk EVM"))
		{
			try {

				strDrinks= getExcelProductCombination("POSCombinationData", "BrkEVMSideItemButtonName");
				strSideProducts= getExcelProductCombination("POSCombinationData", "BrkEVMDrinkButtonName");

				strdrinkReminder = getValueFromExcel( "BrkEVMdrinkReminder");						
				strDrinkAndSideReminder = getValueFromExcel( "BrkEVMDrinkSideReminder");						

				strDrinkMsg = getValueFromExcel( "BrkEVMSideMsg");
				strSideMsg = getValueFromExcel( "BrkEVMDrinkMsg");

				strAutoAdd = getValueFromExcel( "BrkEVMAutoAdd");							
				strDipMsg = getValueFromExcel( "BrkEVMDipMsg");

				strMedEVMSide = getValueFromExcel( "BrkEVMSide");	
				temp1 = strSideMsg; 
				temp2 = strMedEVMSide;  
				temp3 = strAutoAdd; 
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}


		if(strMealType.trim().equals("Breakfast EMP")||strMealType.equals("Brk EMP")||strMealType.equals("BREAKFAST EMP"))
		{
			try {strDrinks= getExcelProductCombination("POSCombinationData", "BrkEMPSideItemButtonName");
			strSideProducts= getExcelProductCombination("POSCombinationData", "BrkEMPDrinkButtonName");

			strdrinkReminder = getValueFromExcel( "BrkEmpdrinkReminder");						
			strDrinkAndSideReminder = getValueFromExcel( "BrkEmpDrinkSideReminder");						

			strDrinkMsg = getValueFromExcel( "BrkEmpSideMsg");
			strSideMsg = getValueFromExcel( "BrkEmpDrinkMsg");

			strAutoAdd = getValueFromExcel( "BrkEmpAutoAdd");							
			strDipMsg = getValueFromExcel( "BrkEmpDipMsg");

			strMedEVMSide = getValueFromExcel( "BrkEmpSide");
			temp1 = strSideMsg; 
			temp2 = strMedEVMSide;  
			temp3 = strAutoAdd; 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}

		arrDrinks=strDrinks.split(",");
		arrSideProducts=strSideProducts.split(",");			
		boolean blnProdbreakloop=false;
		int drinkIterationIndex=0;
		for (String LoopDrink:arrDrinks)
		{		
			drinkIterationIndex++;
			HtmlResult.passed("Drink Iteration number - "+drinkIterationIndex, "-----------------------------", "-----------------------------");
			
			if(blnProdbreakloop)
			{}
			else
			{					
				String[] arrDrinkOnSalesPanel = LoopDrink.split("\\|");
				if (arrDrinkOnSalesPanel.length==3)
				{
					strDrink=arrDrinkOnSalesPanel[0];
					strDrinkNameOnSalesPanel=arrDrinkOnSalesPanel[1];						
					strDrinkNameAfterTotal = arrDrinkOnSalesPanel[2];
				}
				else
				{
					HtmlResult.failed(strTestStepDesc, "Wrong data is entered in Array of Drinks, The length of the array should be 3 and should be in following formate - Drink button Name | Drink Name on sales Panel before Eatin | Drink on sales panel after take out","The Array length is :-" + arrDrinkOnSalesPanel.length);
					blnProdbreakloop=true;
					break;							
				}

				int sideProductIterationIndex=0;
				for (String LoopSideItem:arrSideProducts)
				{	
					sideProductIterationIndex++;
					HtmlResult.passed("Side Iteration number - "+sideProductIterationIndex, "-----------------------------", "-----------------------------");

					if(blnProdbreakloop)
					{}
					else
					{
						strProducts = input.get("productName");
						
						//----------This condition will be checked for MEVM and LEVM Beef items only---------
						if(strProducts.toUpperCase().contains("BEEF")&& strMealType.trim().toUpperCase().contains("EVM"))
						{							
							strSideMsg = strEvmSigBeefSideMsg; 
							strMedEVMSide = strEVMSigBeefSide; 
							strAutoAdd = strEvmSigBeefAutoAdd;
						}
						else
						{		
							strSideMsg=temp1; 
							strMedEVMSide=temp2;  
							strAutoAdd=temp3; 							
						}											
						if(strProducts.toUpperCase().contains("NUGGET"))
						{														
							String[] strButtonList=strProducts.split("#");
							strProducts = strButtonList[0];																
						}

						if(strProducts.toUpperCase().contains("SELECT"))
						{
							String[] strButtonList=strProducts.split("#");
							strProducts = strButtonList[0] +"#"+ strButtonList[1];
						}				
						if(strProducts.toUpperCase().contains("3SELECT"))
						{																
							String[] strButtonList=strProducts.split("#");
							strProducts = strButtonList[0];								
						}
						else if(strProducts.toUpperCase().contains("5SELECT"))
						{	
						}						

						String[] arrSideItemSalesPanel = LoopSideItem.split("\\|");							

						if (arrSideItemSalesPanel.length==3)
						{	sideItem = arrSideItemSalesPanel[0];
							strSideProductNameAfterTotal = arrSideItemSalesPanel[2];
						}
						else
						{
							HtmlResult.failed(strTestStepDesc, "Wrong data is entered in Side Product","Data Entered in side products are :-"+ arrSideItemSalesPanel.toString());
							blnProdbreakloop=true;
							break;								
						}

						strTestStepDesc = "Verfiy the combination -'"+ strProducts + "'+'" + strDrinkNameOnSalesPanel + "'+'" + arrSideItemSalesPanel[1] + "'";

						//--------This variable is defined to Create Pass/Fail report at the end of iteration----------
						blnTestExecution = true;

						Input.put("menuName", strMealType);						
						menuSelection(Input);	

						//----------------Click on Product--------------------							
						if(strProducts.toUpperCase().contains("NUGGET")||strProducts.toUpperCase().contains("SELECT"))
						{								
							String[] strSalesPanelList=strBeforeEatInProductNamesOnSP.split("#");
							if(strProducts.toUpperCase().contains("HAPPY"))
								strBeforeEatInProductNamesOnSP = strSalesPanelList[0];
							else									
								strBeforeEatInProductNamesOnSP = strSalesPanelList[0] +"#"+ strSalesPanelList[1];													
						}

						if(!clickButton(strProducts))
						{
							HtmlResult.failed("Order meal product - "+strProducts, "Meal product '"+strProducts+"' should be clicked", "Unable to select product");	
							//HtmlResult.failed(strTestStepDesc, "'"+strProducts+"'"+" should be clicked",  "'"+strProducts+"'"+" is not clicked");
							blnProdbreakloop=true;
							break;
						}	
						else
						{
							HtmlResult.passed("Order meal product - "+strProducts, "Meal product '"+strProducts+"' should be clicked", "Product selection successful");
						}
						
						if(!MatchStringOnSalesPanel(strdrinkReminder,"N",rectangle))
							blnTestExecution=false;							
						if(!MatchStringOnSalesPanel(strMissing,"N",rectangle))
							blnTestExecution=false;
						if(!MatchStringOnSalesPanel(strBeforeEatInProductNamesOnSP,"N",rectangle))
							blnTestExecution=false;

						//------------Auto Add is not Applicable to Wraps-----------
						if(strProducts.toUpperCase().contains("WRAP")||strProducts.toUpperCase().contains("WRP")||strProducts.toUpperCase().contains("PANCAKE"))
						{}
						else
						{	if(!MatchStringOnSalesPanel(strAutoAdd,"N",rectangle))
							blnTestExecution=false;
						}
						if(strProducts.toUpperCase().contains("NUGGET"))
						{
							if(!MatchStringOnSalesPanel(strEVMNuggetsDips,"N",rectangle))
								blnTestExecution=false;
						}							

						//-----------Click on Clear choice button-----------------
						if((strProducts.toUpperCase().contains("WRAP")||strProducts.toUpperCase().contains("WRP")||strProducts.toUpperCase().contains("PANCAKE"))&&(!strProducts.toUpperCase().contains("MEMP")&&!strProducts.toUpperCase().contains("LEMP")))
						{							
						}
						else
						{	if(!clickButton(ClearChoiceButton))
							{	
								HtmlResult.failed("Click on "+ClearChoiceButton, "'"+ClearChoiceButton+"'"+" should be clicked",  "'"+ClearChoiceButton+"'"+" is not clicked");
							}								
						}

						//-------------This code is written to validate to Happy meal nuggets-------------
						if(strProducts.toUpperCase().contains("HAPPY"))
						{	
							if(eggUIDriver.clickTextInRectangle("TOY", ScreenPart(0.3325,0.2883,0.66125,0.80666)))									
							{
								if(eggUIDriver.clickTextInRectangle("OK", ScreenPart(0.37,0.23,0.63,0.75)))
								{
									if(clickButton(ClearChoiceButton))
									{
										if(eggUIDriver.clickTextInRectangle("FRIES", ScreenPart(0.37,0.23,0.63,0.75)))
										{
											if(eggUIDriver.clickTextInRectangle("OK", ScreenPart(0.37,0.23,0.63,0.75)))
											{}
											else
											{
												HtmlResult.failed("Click OK button on clear choice popup", "'"+ClearChoiceButton+"OK Button'"+" should be clicked",  "'"+ClearChoiceButton+" OK Botton '"+" is not clicked");
											}
										}
										else
										{
											//HtmlResult.failed(strTestStepDesc, "'"+ClearChoiceButton+" HM SMALL FRIES'"+" should be clicked",  "'"+ClearChoiceButton+" HM SMALL FRIES'"+" is not clicked");
										}
									}
									else
									{
										HtmlResult.failed("Click on "+ClearChoiceButton, "'"+ClearChoiceButton+"'"+" should be clicked",  "'"+ClearChoiceButton+"'"+" is not clicked");
									}
								}
								else
								{
									HtmlResult.failed("Click OK button on clear choice popup", "'"+ClearChoiceButton+"OK Button'"+" should be clicked",  "'"+ClearChoiceButton+" OK Botton '"+" is not clicked");									
								}
							}
							else
							{
								//HtmlResult.failed(strTestStepDesc, "'"+ClearChoiceButton+" Toy'"+" should be clicked",  "'"+ClearChoiceButton+" Toy'"+" is not clicked");																
							}
							
							/*if(!eggUIDriver.clickTextInRectangle("TOY", ScreenPart(0.3325,0.2883,0.66125,0.80666)))									
								HtmlResult.failed(strTestStepDesc, "'"+ClearChoiceButton+" Toy'"+" should be clicked",  "'"+ClearChoiceButton+" Toy'"+" is not clicked");																
							if(!eggUIDriver.clickTextInRectangle("OK", ScreenPart(0.37,0.23,0.63,0.75)))
								HtmlResult.failed(strTestStepDesc, "'"+ClearChoiceButton+" HM SMALL FRIES OK Button'"+" should be clicked",  "'"+ClearChoiceButton+" HM SMALL FRIES OK Botton '"+" is not clicked");									
							if(!clickButton(ClearChoiceButton))
								HtmlResult.failed(strTestStepDesc, "'"+ClearChoiceButton+"'"+" should be clicked",  "'"+ClearChoiceButton+"'"+" is not clicked");
							if(!eggUIDriver.clickTextInRectangle("FRIES", ScreenPart(0.37,0.23,0.63,0.75)))
								HtmlResult.failed(strTestStepDesc, "'"+ClearChoiceButton+" HM SMALL FRIES'"+" should be clicked",  "'"+ClearChoiceButton+" HM SMALL FRIES'"+" is not clicked");
							if(!eggUIDriver.clickTextInRectangle("OK", ScreenPart(0.37,0.23,0.63,0.75)))
								HtmlResult.failed(strTestStepDesc, "'"+ClearChoiceButton+" HM SMALL FRIES OK Button'"+" should be clicked",  "'"+ClearChoiceButton+" HM SMALL FRIES OK Botton '"+" is not clicked");*/
						}						

						String strClearChoice = ReadSalesPanelUK(rectangle);
						if(strProducts.toUpperCase().contains("SELECT"))								
						{			
							if(!strClearChoice.contains(strdrinkReminder)&&strClearChoice.contains(strMedEVMSide)&&strClearChoice.contains(strEVM3SelectDip))
								HtmlResult.failed("To details on clear choice popup", "'"+strdrinkReminder+"','"+ strMedEVMSide +"','"+ strEVM3SelectDip+"'"+ " should be displayed on clear choice popup ",  strDrinkAndSideReminder + " is not displayed on salespanel instead -'"+strClearChoice+ "' is displayed");
						}							
						else if(strProducts.toUpperCase().contains("NUGGET"))
						{
							if(strProducts.toUpperCase().contains("HAPPY"))
							{
								try{strdrinkReminder = getValueFromExcel( "HappydrinkReminder");}
								catch (Exception e) {e.printStackTrace();}																	
							}
							if(!strClearChoice.contains(strdrinkReminder)&&strClearChoice.contains(strEVMNuggetsDips)&&strClearChoice.contains(strMedEVMSide))
								HtmlResult.failed("To details on clear choice popup", "'"+strdrinkReminder +"','" +strMedEVMSide+"'"+" and "+strEVMNuggetsDips+" should be displayed on clear choice popup ",  strDrinkAndSideReminder + " is not displayed on salespanel instead -'"+strClearChoice+ "' is displayed");
						}
						else							
						{	
							if(!(strProducts.toUpperCase().contains("PANCAKE"))&&!(strProducts.toUpperCase().contains("BREAK")&& (strProducts.toUpperCase().contains("WRP")||strProducts.toUpperCase().contains("WRAP"))))
							{
								if(!MatchStringOnSalesPanel(strdrinkReminder,"N",rectangle))
									blnTestExecution=false;							
								if(!MatchStringOnSalesPanel(strMedEVMSide,"N",rectangle))
									blnTestExecution=false;
								if(!MatchStringOnSalesPanel(strMissing,"N",rectangle))
									blnTestExecution=false;
								if(!MatchStringOnSalesPanel(strBeforeEatInProductNamesOnSP,"N",rectangle))
									blnTestExecution=false;
							}
						}								
						//-----------------------Click on Takeout button---------------
						if(!clickButton(TakeOutButton))
						{
							HtmlResult.failed("Click on button - "+TakeOutButton, TakeOutButton+" button should be clicked", "Unable to click button");
						}
						else
						{
							HtmlResult.passed("Click on button - "+TakeOutButton, TakeOutButton+" button should be clicked", "Button click successful");
						}
						//						
						//  Reseting the Recovery boolean variable-----
						blnExit=false;

						//------------------Click on Dips for Nuggets and Selects-------
						if((strProducts.toUpperCase().contains("NUGGET")||strProducts.toUpperCase().contains("SELECT"))&&(!strProducts.toUpperCase().contains("HAPPY")))
						{	
							//-------------------Product name on sales panel before Eatin--------------								
							strBeforeEatInProductNamesOnSP = input.get("beforeEatInProductNamesOnSP");
							if(!MatchStringOnSalesPanel(strDipMsg,"Y",rectangle))
								blnTestExecution=false;			

							strProducts =input.get("productName");
							String[] strButtonList=strProducts.split("#");																

							if(strProducts.toUpperCase().contains("NUGGET")||(strProducts.toUpperCase().contains("3SELECT")&& (strButtonList.length==2)))										
							{strProducts = strButtonList[1];}								
							else
							{strProducts = strButtonList[2];}
							//This is only for 3Selects								


							//-------For Happy Meal need to Skip this click------------------------																
							String strSelectCheck=input.get("productName");
							if(!clickButton(strProducts))
							{
								HtmlResult.failed("Order meal product - "+strProducts, "Meal product '"+strProducts+"' should be clicked", "Unable to select product");	
								
								//HtmlResult.failed(strTestStepDesc, "'"+strProducts+"'"+" should be clicked",  "'"+strProducts+"'"+" is not clicked");
								Recovery(TakeOutButton,ExactCashButton);
								blnProdbreakloop=true;
								blnExit = true;
								break;													
							}	
							else
							{
								HtmlResult.passed("Order meal product - "+strProducts, "Meal product '"+strProducts+"' should be clicked", "Unable to select product");									
							}
							
							//below line changed from else if to if. And above else was missing 
							if(strSelectCheck.toUpperCase().contains("5SELECT"))
							{
								if(!clickButton(strProducts))
								{
									HtmlResult.failed("Order meal product - "+strProducts, "Meal product '"+strProducts+"' should be clicked", "Product selection successful");	
									
									//HtmlResult.failed(strTestStepDesc, "'"+strProducts+"'"+" should be clicked",  "'"+strProducts+"'"+" is not clicked");
									Recovery(TakeOutButton,ExactCashButton);
									blnProdbreakloop=true;
									blnExit = true;
									break;	
								}
								else
								{
									HtmlResult.passed("Order meal product - "+strProducts, "Meal product '"+strProducts+"' should be clicked", "Product selection successful");	
								}
							}									
						}							

						if(blnExit==false)
						{
							if(!(strProducts.toUpperCase().contains("PANCAKE"))&&!(strProducts.toUpperCase().contains("BREAK")&& (strProducts.toUpperCase().contains("WRP")||strProducts.toUpperCase().contains("WRAP"))))
							{
								if(!MatchStringOnSalesPanel(strDrinkMsg,"Y",rectangle))	
									blnTestExecution=false;

								// Split the drink name if there is any # in it/////							
								String strButton=strDrink;						
								String[] strDrinkButtonList=strButton.split("#");							
								for (String Button:strDrinkButtonList) 
								{
									Button = Button.trim();																						
									if(eggUIDriver.imageFoundSearchRectangle(Button, ScreenPart(0.3, 0.0, 1, 0.3)))
									{
										if(eggUIDriver.clickinSearchRectangle(Button, ScreenPart(0.3, 0.0, 1, 0.3)))
										{
											HtmlResult.passed("Click Drink on smart reminder -  "+Button, Button+" Drink should get selected ", "Drink selection successful");
											blnExit = false;
										}
									}
									else
									{									
										HtmlResult.failed("Click Drink on smart reminder -  "+Button,  Button+" Drink should get selected ", "Unable to select drink");
										Recovery(TakeOutButton,ExactCashButton);
										blnTestExecution=false;
										blnExit = true;
										break;
									}
								}
							}	

							//----------Verify Drink and Product on sales panel-----------
							//String strDrinkSelection = ReadSalesPanelUK();
							if(blnExit==false)
							{
								if(!MatchStringOnSalesPanel(strSideMsg,"Y",rectangle))
									blnTestExecution=false;
								if(!MatchStringOnSalesPanel(strBeforeEatInProductNamesOnSP,"Y",rectangle))
									blnTestExecution=false;
								if(!(strProducts.toUpperCase().contains("PANCAKE"))&&!(strProducts.toUpperCase().contains("BREAK")&& (strProducts.toUpperCase().contains("WRP")||strProducts.toUpperCase().contains("WRAP"))))
								{
									if(!MatchStringOnSalesPanel(strDrinkNameOnSalesPanel,"Y",rectangle))
										blnTestExecution=false;
								}

								//------------------Side Item selection-------------------------------

							  	String[] strSideItemButtonList=sideItem.split("#");							
							 	for (String Button:strSideItemButtonList) 
								{
									Button = Button.trim();		
									if(eggUIDriver.imageFoundSearchRectangle(Button, ScreenPart(0.3, 0.0, 1, 0.3)))
									{
										if(eggUIDriver.clickinSearchRectangle(Button, ScreenPart(0.3, 0.0, 1, 0.3)))
										{
											HtmlResult.passed("Click Side product on smart reminder -  "+Button, "Side product '"+ Button+"'  should get selected ", "Side product selection successful");
											blnExit = false;
										}	
									}								
									else																							
									{									
										HtmlResult.failed("Click Side product on smart reminder -  "+Button, "Side product '"+ Button+"'  should get selected ", "Unable to select side product");
										Recovery(TakeOutButton,ExactCashButton);
										blnTestExecution=false;
										blnExit = true;
										break;
									}
								}						
								if(blnExit==false)
								{		
									if(strProducts.toUpperCase().contains("HAPPY")&&strProducts.toUpperCase().contains("NUGGET"))
									{	//strtakeout = ReadSalesPanelUK();
										if(!MatchStringOnSalesPanel(strDipMsg,"Y",rectangle))
											blnTestExecution=false;
										strProducts =input.get("productName");
										String[] strButtonList=strProducts.split("#");																	
										strProducts = strButtonList[1];
										if(!clickButton(strProducts))
										{
											HtmlResult.failed("Order meal product - "+strProducts, "Meal product '"+strProducts+"' should be clicked", "Product selection successful");			
											//HtmlResult.failed(strTestStepDesc, "'"+strProducts+"'"+" should be clicked",  "'"+strProducts+"'"+" is not clicked");
										}
										else
										{
											HtmlResult.passed("Order meal product - "+strProducts, "Meal product '"+strProducts+"' should be clicked", "Product selection successful");	
											//HtmlResult.passed(strTestStepDesc, "'"+strProducts+"'"+" should be clicked",  "'"+strProducts+"'"+" is not clicked");
										}
									}

									//-------------Verify Drink, side items and product on sales Panel----------
									//String strFriesSelection = ReadSalesPanelUK();
									if(!(strProducts.toUpperCase().contains("PANCAKE"))&&!(strProducts.toUpperCase().contains("BREAK")&& (strProducts.toUpperCase().contains("WRP")||strProducts.toUpperCase().contains("WRAP"))))
									{	
										if(!MatchStringOnSalesPanel(strDrinkNameAfterTotal,"Y",rectangle))
											blnTestExecution=false;
									}
									if(!MatchStringOnSalesPanel(strSideProductNameAfterTotal,"Y",rectangle))
										blnTestExecution=false;

									if(!MatchStringOnSalesPanel(strAfterEatInProductNamesOnSP,"Y",rectangle))
										blnTestExecution=false;

									//------------Create the Final test execution Report for a iteration-------
									
									strTestStepDesc=strAfterEatInProductNamesOnSP+" , "+strDrinkNameAfterTotal+" , "+strSideProductNameAfterTotal;
									if(blnTestExecution)
										HtmlResult.passed("Verify the combination - "+strTestStepDesc, "Combination verification must be successful for "+strTestStepDesc, "Verification was successful");
									else
										HtmlResult.failed("Verify the combination - "+strTestStepDesc, "Combination verification must be successful for "+strTestStepDesc, "Verification was unsuccessful");							

									//------------Click on Exact Cash-----------------------
									if(!clickButton(ExactCashButton))
									{
										HtmlResult.failed("Click on - "+ExactCashButton, "'"+ExactCashButton+"'"+" should be clicked",  "'"+ExactCashButton+"'"+" is not clicked");
										Recovery(TakeOutButton,ExactCashButton);							
									}
									//Below condition is for DT
									else
									{				
										/*if(eggUIDriver.clickTextInRectangle("Clear", ScreenPart(0.37,0.56,0.62,0.76)))
										{}
										clickButton("pos_uk_DT_BackArrow");*/
									}
								}		
							}
						}
					}
				}	
			}
		}				
	}

	public void verifySmartReminder_new(Map<String,String> input)
	{
		String strDescription = "To verify Smart Reminder feature";
		String strExpectedResult = "Smart Reminder should be displayed";
		String strMissing = "Missing" ;
		
		try
		{
			String MenuClearChoice = getValueFromExcel("CLEARCHOICE");
			String clearChoiceOKButton = getValueFromExcel("clearChoiceOKButton");
			String SmartReminderMessage = input.get("SmartReminderMessage").trim();
			String UiButtonName = input.get("productName").trim();
			String MenuName = input.get("menuName").trim();
			String AfterEatinSideItemName = input.get("SideItemName#SalesPanelName").trim();
			String AfterEatinDrinkItemName = input.get("DrinkName#SalesPanelName").trim();
			String [] ArrSmartReminderMessages = SmartReminderMessage.split("#");
			int LengthOfArrSmartReminderMessages = ArrSmartReminderMessages.length;
			
			if(LengthOfArrSmartReminderMessages ==1)
			{
				if(MenuName.equals("BREAKFAST MED EVM")||MenuName.equals("BREAKFAST EMP"))
				{
					boolean TextFound = findTextOnSalesPanel(strMissing);
					String TextToSearch = ArrSmartReminderMessages[0].trim();
					
					// search missing in sales panel
					if(TextFound)
					{
						HtmlResult.passed(strDescription, strExpectedResult, "Text-'"+strMissing+"' is found in sales panel");
						TextFound = findTextOnSalesPanel(TextToSearch.trim());
					}
					else
					{
						HtmlResult.failed(strDescription, strExpectedResult, "Text-'"+strMissing+"' is not found in sales panel");
					}
					
					// if missing is found then only search smart reminder texts
					if(TextFound)
					{
						HtmlResult.passed(strDescription, strExpectedResult, "Text-'"+TextToSearch+"' is found in sales panel");

					}
					else
					{
						HtmlResult.failed(strDescription, strExpectedResult, "Text-'"+TextToSearch+"' is not found in sales panel");
					}
				}
				else
				{
					HtmlResult.failed(strDescription, strExpectedResult, "Only one choice is applicable to some products for menus-'BREAKFAST MED EVM' and 'BREAKFAST EMP', Provided menu is-' "+MenuName+" '");
				}
			}
			else if(LengthOfArrSmartReminderMessages ==2 ||LengthOfArrSmartReminderMessages ==3 ||LengthOfArrSmartReminderMessages ==4 )
			{
				boolean TextFound = false;
				String strTextFound = "";
				String strTextNotFound = "";
				String MissingFound = "";
				String MissingNotFound = "";
				boolean blnMissingFound = false;
				int NoOfTimesPerformClearChoice = LengthOfArrSmartReminderMessages-1;
				
				for(String TextToSearch : ArrSmartReminderMessages)
				{
					blnMissingFound = false;
					TextFound = findTextOnSalesPanel(strMissing);
					if(TextFound)
					{
						MissingFound = MissingFound.concat("Found");
						blnMissingFound=true;
					}
					else
					{
						MissingNotFound = MissingNotFound.concat(TextToSearch+",");
						blnMissingFound = false;
					}
					
					TextFound = findTextOnSalesPanel(TextToSearch.trim());
					if(TextFound)
					{
						strTextFound = strTextFound.concat(TextToSearch+",");
					}
					else
					{
						strTextNotFound = strTextNotFound.concat(TextToSearch+",");
					}
					
					boolean ClearChoice = false;
					
					// while loop to make clear choice
					while(NoOfTimesPerformClearChoice>0)
					{
						if((UiButtonName.contains("levm") && UiButtonName.contains("wrap")) 
								|| (UiButtonName.contains("mevm") && UiButtonName.contains("wrap")))
						{
							break;
						}
						
						ClearChoice = false;
						ClearChoice = clickButton(MenuClearChoice);
						
						NoOfTimesPerformClearChoice--;
						if(UiButtonName.contains("happy"))
						{
							if(eggUIDriver.clickImage("pos_clearchoiceToy"))
							{
								;
							}
							else
							{
								eggUIDriver.clickText("FRIES");
								eggUIDriver.clickText("Brown");
							}
						}
						
						if(UiButtonName.contains("cknLegend") && UiButtonName.contains("Bac"))
						{
							if(eggUIDriver.clickImage("pos_ClearchoiceTrippleSticker"))
							{
								;
							}
							else
							{
								eggUIDriver.clickText("FRIES");
								eggUIDriver.clickText("Brown");
							}
						}
						
						if(eggUIDriver.imageFound(clearChoiceOKButton))
						{
							ClearChoice = eggUIDriver.clickImage(clearChoiceOKButton);
						}
					
						if(ClearChoice)
						{
							break;
						}
						else
						{
							HtmlResult.failed(strDescription, strExpectedResult, "clear choice button is not clickable");
						}
					}
				}
				
				if(blnMissingFound)
				{
					HtmlResult.passed(strDescription, strExpectedResult, "'Missing' is displayed in smart reminder");
				}
				else if(MissingNotFound.length()>3)
				{
					HtmlResult.failed(strDescription, strExpectedResult, "'Missing' is not displayed in smart reminder");
				}
				
				if(strTextFound.length()>3)
				{
					HtmlResult.passed(strDescription, strExpectedResult, "Text-'"+strTextFound+"' is found in sales panel");
				}
				if(strTextNotFound.length()>3)
				{
					HtmlResult.failed(strDescription, strExpectedResult, "Text-'"+strTextNotFound+"' is found in sales panel");
				}
			}
			else
			{
				strExpectedResult = "Smart Reminder should be displayed successfully";
				HtmlResult.failed(strDescription, strExpectedResult, "Size of SmartReminderMessage parameter in test data sheet is wrong, Given data is-'"+SmartReminderMessage+"'");
			}
			
			String strTenderTypeImageName=getValueFromExcel("EATINTOTAL");
			if(clickButton(strTenderTypeImageName))
			{
				Dimension ScreenSize = eggUIDriver.remoteScreenSize();
				Rectangle ScreenRect = new Rectangle(0,0,ScreenSize.height/2,ScreenSize.width);
				eggUIDriver.setSearchRectangle(ScreenRect);
		
			}
			
			
			
			
			
			
			
			
			
			
			
			
 			if(eatinTotalExactCash())
			{
				HtmlResult.passed(strDescription, strExpectedResult, "Order has been cleared ");
			}
			else
			{
				HtmlResult.failed(strDescription, strExpectedResult, "Order is not cleared successfully, Recovery Module will run at the start of next iteration");
			}
			
		}
		catch(Exception e)
		{
			HtmlResult.failed("To verify smart reminder feature", "Smart reminder should be displayed successfully", "Error while performing verifySmartReminder-'"+ e.getMessage()+"'");
		}
	}
	
	
	//---------------------------------------------------------------------------------
	
	//---------------------------------------------------------------------------------
	
	public boolean MatchStringOnSalesPanel(String strExpected,String printFailResultYorN,Rectangle r)	
	{
		String strActualString="";
		String[] arrActualString;	
		String[] strArrExpectedData = {strExpected};
		
		strActualString = readSalesPanelLineByLine(strExpected,r);
		arrActualString=strActualString.split("\n");
		
		if(arrActualString.length==1 || arrActualString.length==2)
		{
			return true;
		}
		else if(arrActualString.length>=3)
		{
			int MatchingCounter = 0;
			for(String ActualData:arrActualString)
			{	
				String[] arrActualData = {ActualData};
				String VerificationStatus = verifyMeal(strArrExpectedData,arrActualData);
				if(VerificationStatus.equals("passed")||VerificationStatus.equals("warning"))
				{
					MatchingCounter++;
				}
			}	
			
			if(MatchingCounter==strArrExpectedData.length)
			{
				return true;
			}
			else
			{
				if(printFailResultYorN.equalsIgnoreCase("Y"))
				{
					HtmlResult.failed("To verify data on salespanel "+strExpected, "Sales panel should contain "+strExpected, "Actual result is - "+strActualString);
				}
				return false;
			}
		}
		else if(arrActualString.length==0)
		{
			if(printFailResultYorN.equalsIgnoreCase("Y"))
			{
				HtmlResult.failed("To verify data on salespanel "+strExpected, "Sales panel should contain "+strExpected, "Actual result is - "+strActualString);
			}
			return false;
		}
		
		return false;	
	}
	
	//---------------------------------------------------------------------------------
	
	
	//Updated match string method
	public boolean MatchStringOnSalesPanelOld(String strExpected,String printFailResultYorN,Rectangle r)	
	{
		String strActualString="";
		String[] arrActualString;	
		String[] strArrExpectedData = {strExpected};
		
		strActualString = readSalesPanelLineByLine(strExpected,r);
		arrActualString=strActualString.split("\n");
		
		if(arrActualString.length==1 || arrActualString.length==2)
		{
			return true;
		}
		else if(arrActualString.length>=3)
		{
			int MatchingCounter = 0;
			for(String ActualData:arrActualString)
			{	
				String[] arrActualData = {ActualData};
				String VerificationStatus = verifyMeal(strArrExpectedData,arrActualData);
				if(VerificationStatus.equals("passed")||VerificationStatus.equals("warning"))
				{
					MatchingCounter++;
				}
			}	
			
			if(MatchingCounter==strArrExpectedData.length)
			{
				return true;
			}
			else
			{
				if(printFailResultYorN.equalsIgnoreCase("Y"))
				{
					HtmlResult.failed("To verify data on salespanel "+strExpected, "Sales panel should contain "+strExpected, "Actual result is - "+strActualString);
				}
				return false;
			}
		}
		else if(arrActualString.length==0)
		{
			if(printFailResultYorN.equalsIgnoreCase("Y"))
			{
				HtmlResult.failed("To verify data on salespanel "+strExpected, "Sales panel should contain "+strExpected, "Actual result is - "+strActualString);
			}
			return false;
		}
		
		return false;	
	}
	
	
	//Updated by shruti 8-5-17
	public String readSalesPanelLineByLine(String TextToSearch,Rectangle rectCoordinates)
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
		try
		{
			String OverallSalesPanelData = "";

			/*ArrayList<Integer> TopLeft = eggUIDriver.ImageLocation("LeftTopCorner"); //location top left of sales panel
			int x1 = TopLeft.get(0); 
			int y1 = TopLeft.get(1);
			
			ArrayList<Integer> BottomRight = eggUIDriver.ImageLocation("RightBottomCorner"); //location of right bottom of sales panel
			int x2 = BottomRight.get(0);
			int y2 = BottomRight.get(1);*/
			
			//Rectangle SearchRectangle = new Rectangle(x1, y1, x2, y2); //setting search rectangle to sales panel using above cordinates
			Rectangle SearchRectangle = new Rectangle(rectCoordinates);
			
			int LengthStart = rectCoordinates.x+StartPoint; // starting x1 point for text searching and reading by using x1 of TopLeft
			int LengthEnd = rectCoordinates.x+SalesPanelSize; // to set end point on x direction i.e, x2 using SalesPanelSize of current SUT connection
			int Width =  LengthEnd-LengthStart; // calculating width for searching text in a rectangle
			
			String[] arrTextsToSearch = TextToSearch.split("#");
			for(String Texts : arrTextsToSearch)
			{
				eggUIDriver.setSearchRectangle(SearchRectangle);
				Texts = Texts.trim();
				List<Point> TextLocation = eggUIDriver.getEveryTextLocation(Texts);
				if(TextLocation.size()!=0)
				{
					if(TextLocation.size()==1)
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
						for(Point Location:TextLocation)
						{
							OverallSalesPanelData="";
							int TopLeftY = Location.y-ReadingError; // to make reading better
							int height = FontSize;
							Rectangle TextRectangle = new Rectangle(LengthStart, TopLeftY, Width, height); // creating rectangle over text
							String SalesPanelOrder = eggUIDriver.readText(TextRectangle) ; // read rectangle
							OverallSalesPanelData = OverallSalesPanelData.concat(SalesPanelOrder+"\n");
							OverallSalesPanelData.replaceAll("\t", "\n");
							if(OverallSalesPanelData.contains(TextToSearch))
							{
								break;
							}
						}
					}
					
				}
				else
				{
					OverallSalesPanelData = eggUIDriver.readText(SearchRectangle).replace("I.I.I", "").replace("I.\tI.\tI", "");
					break;
				}	
			}
	
			eggUIDriver.setSearchRectangle(OriginalRectangle);
			OverallSalesPanelData = OverallSalesPanelData.replaceAll("\t", "\n");
			return OverallSalesPanelData;
		
		}
		catch(Exception e)
		{
			eggUIDriver.setSearchRectangle(OriginalRectangle);
			return "";
		}
	
	}
	
	public void Recovery(String TakeOutButton,String ExactCashButton )
	{
		clickButton(TakeOutButton);
		//boolean test;
		if(eggUIDriver.imageFoundSearchRectangle("RecoveryPoint1", ScreenPart(0.3, 0.0, 1, 0.4)))
		{		
			if(eggUIDriver.clickinSearchRectangle("RecoveryPoint1", ScreenPart(0.3, 0.0, 1, 0.4)))
			{}
		}
		if(eggUIDriver.imageFoundSearchRectangle("RecoveryPoint2", ScreenPart(0.3, 0.0, 1, 0.4)))
		{	
			if(eggUIDriver.clickinSearchRectangle("RecoveryPoint2", ScreenPart(0.3, 0.0, 1, 0.4)))
			{}		
		}
		//clickButton(TakeOutButton);
		if(eggUIDriver.imageFoundSearchRectangle("RecoveryPoint1", ScreenPart(0.3, 0.0, 1, 0.4)))
		{		
			if(eggUIDriver.clickinSearchRectangle("RecoveryPoint1", ScreenPart(0.3, 0.0, 1, 0.4)))
			{}
		}
		if(eggUIDriver.imageFoundSearchRectangle("RecoveryPoint2", ScreenPart(0.3, 0.0, 1, 0.4)))
		{	
			if(eggUIDriver.clickinSearchRectangle("RecoveryPoint2", ScreenPart(0.3, 0.0, 1, 0.4)))
			{}		
		}	if(clickButton(ExactCashButton))
		{
			if(eggUIDriver.clickTextInRectangle("Clear", ScreenPart(0.37,0.56,0.62,0.76)))
			{}
			clickButton("pos_uk_DT_BackArrow");
		}
	}

	public void softBumpBar()
	{
		System.out.println("softBumpBar()-Under construction.............");
	}

	public boolean performSoftBumpbar(String KvsName) throws IOException
	{
		boolean FoundAndClicked=false;
		String CrewMenuButton="";
		String SoftBumpBarButton="";
		String SelectKvsButton="";
		String OkButtonName="";
		String ServeButton="";
		String BackButton = "";
		String ClickServeButton = "";
		
		try
		{
			CrewMenuButton=getValueFromExcel("CREW_MENU");
			SoftBumpBarButton=getValueFromExcel( "SOFT_BUMPBAR");
			SelectKvsButton=getValueFromExcel( "SELECT_KVS");
			OkButtonName=getValueFromExcel("OK");
			ServeButton=getValueFromExcel("SERVE_BUTTON");
			BackButton = getValueFromExcel("BACK_BUTTON");
			ClickServeButton = getValueFromExcel("ClickServeButton");
			
			String []KvsNames = KvsName.split("#");
			int VerificationCounter = 0;
			boolean BumpBarFailed = false;
			int PerformServeButtonClick = Integer.parseInt(ClickServeButton);
			
			FoundAndClicked = clickButton(CrewMenuButton);
			if(FoundAndClicked)
			{
				FoundAndClicked = clickButton(SoftBumpBarButton);
			}
			else
			{
				return false;
			}
			
			if(FoundAndClicked)
			{
				for(String KvsScreenName: KvsNames)
				{
					String KvsNumber = getKvsNumber(KvsScreenName);
					BumpBarFailed = false;
		
					if(FoundAndClicked)
					{
						FoundAndClicked = clickButton(SelectKvsButton);
					}
					else
					{
						BumpBarFailed = true;
					}
					
					if(FoundAndClicked)
					{
						Thread.sleep(750);
						if(KvsNumber.length()>0)
						{
							FoundAndClicked = eggUIDriver.clickTextOnScreen(KvsNumber);
						}
					}
					else
					{
						BumpBarFailed = true;
					}
					
					if(FoundAndClicked)
					{
						FoundAndClicked = clickButton(OkButtonName);
					}
					else
					{
						BumpBarFailed = true;
					}
					
					if(FoundAndClicked)
					{
						int Counter = 1;
						while(Counter<=PerformServeButtonClick)
						{
							FoundAndClicked = clickButton(ServeButton);
							Counter++;
						}
		
						VerificationCounter++;
					}
					else
					{
						return false;
					}
				}
			}
			else
			{
				return false;
			}
			
			while(eggUIDriver.imageFound(BackButton))
			{
				FoundAndClicked = eggUIDriver.clickImage(BackButton);
			}
			
			if(BumpBarFailed)
			{
				return false;
			}
			else if(VerificationCounter==KvsNames.length)
			{
				HtmlResult.passed("To perform Soft bumpbar action on POS", "Soft bumpbar should be successful", "Soft bumpbar action is successfully performed on POS");
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
	
	public String getKvsNumber(String KvsName)
	{
		try
		{
			List<Map<String,String>> Properties = TestCaseRunner.KvsNamesList;
			
			for(Map<String,String> PropertyMap : Properties)
			{
				String Key = PropertyMap.get("KvsName").trim();
				String Value = "";
				if(Key.equals(KvsName))
				{
					Value = PropertyMap.get("KvsNumber").trim();
					return Value;
				}
			}
		}
		catch(Exception e)
		{
			return "";
		}
		return "";
	}
	
	
	


	public void softBumpbar(String KvsName) throws IOException
	{
		boolean FoundAndClicked=false;
		boolean clicked=false;
		String strStepDesc="To perform Soft BumpBar action on each KVS";
		String strExpected="Soft BumpBar should be successful";
		String CrewMenuButton="";
		String SoftBumpBarButton="";
		String SelectKvsButton="";
		String SelectKvsPopupTopLeft="";
		String SelectKvsPopupBottomRight="";
		String KVSName=KvsName;

		CrewMenuButton=getValueFromExcel("CREW_MENU");
		SoftBumpBarButton=getValueFromExcel( "SOFT_BUMPBAR");
		SelectKvsButton=getValueFromExcel( "SELECT_KVS");
		SelectKvsPopupTopLeft=getValueFromExcel("SELECTKVS_POPUP_TOPLEFT");
		SelectKvsPopupBottomRight=getValueFromExcel( "SELECTKVS_POPUP_BOTTOMRIGHT");
		getValueFromExcel("OK");


		FoundAndClicked=clickButtonAddToReport(CrewMenuButton);
		if(FoundAndClicked)
		{
			FoundAndClicked=clickButton(SoftBumpBarButton);
			if(FoundAndClicked)
			{
				FoundAndClicked=clickButtonAddToReport(SelectKvsButton);
				if(FoundAndClicked)
				{
					Rectangle SelectKvsRect=new Rectangle();
					SelectKvsRect=eggUIDriver.imageRectangle(SelectKvsPopupTopLeft, SelectKvsPopupBottomRight);

					String TotalKvs=eggUIDriver.readText(SelectKvsRect).replaceAll(">", "").replaceAll(";", "");
					String[]KVSList=TotalKvs.split("\n");
					for(String Kvs:KVSList)
					{
						if(Kvs.equalsIgnoreCase(KVSName))
						{
							clicked=eggUIDriver.clickTextInRectangle(Kvs, SelectKvsRect);
							if(clicked)
							{
								//connect to kvs-SelectKvs 
								//read screen of SelectKvs
								//verify if any order is pending or not
								/*if screen is cleared then------------------->*/

								HtmlResult.passed(strStepDesc,strExpected,"Order on KVS-"+Kvs+"-cleared successfully");
							}
						}
					}

				}	
				else
				{
					HtmlResult.failed(strStepDesc,strExpected,"Select KVS button not found and not clicked");
				}
			}
			else
			{
				HtmlResult.failed(strStepDesc,strExpected,"Soft BumpBar button not found and not clicked");
			}
		}
		else
		{
			HtmlResult.failed(strStepDesc,strExpected,"CREW menu button not found and not clicked");
		}	
	}	
	
	public void orderProductFromMenu(Map<String,String> Input)
	{
		try
		{
			String MenuAndProductName = Input.get("MenuAndProductName").trim();
			String [] ArrMenuAndProductName = MenuAndProductName.split(";");
			String StartCleanUp = getValueFromExcel("OrderCleanUpRecovery").trim();
			boolean PerformRecovery = false;
			
			String RunKvsCleanUp = getValueFromExcel("RunKvsCleanUp");
			String BumpAllKvsScreens = getValueFromExcel("BumpAllKvsScreens");
			String SideScreenNames= "";
			String MenuName = "";
			
			
			//to verify OAT1 and OAT2
			String VerifyOat1 = getValueFromExcel("VerifyOat1");
			String VerifyOat2 = getValueFromExcel("VerifyOat2");
			
			if(VerifyOat1.equalsIgnoreCase("YES") && !VerifyOat2.equals("YES"))
			{
				SideScreenNames = "OAT-1";
				PerformRecovery = true;
			}
			else if(VerifyOat2.equalsIgnoreCase("YES") && !VerifyOat1.equals("YES"))
			{
				SideScreenNames = "OAT-2";
				PerformRecovery = true;
			}
			else if(VerifyOat2.equalsIgnoreCase("YES") && VerifyOat1.equals("YES"))
			{
				SideScreenNames = "OAT-1#OAT-2";
				PerformRecovery = true;
			}
			else if(!VerifyOat2.equalsIgnoreCase("NO") && !VerifyOat1.equals("NO"))
			{
				HtmlResult.failed("To clear POS screen to make new orders", " Should be able to clear POS screen to make new orders", "Wrong value passed through parameters VerifyOat1 and VerifyOat2, it sholud either be YES or NO");
				PerformRecovery = false;
			}
			
			//cleaning up pos and KVS sides
			if(StartCleanUp.equalsIgnoreCase("YES"))
			{
				runCleanUp();
				StartCleanUp = "NO";
			}
			else if(!StartCleanUp.equalsIgnoreCase("NO"))
			{
				HtmlResult.failed("To clear POS screen to make new orders", " Should be able to clear POS screen to make new orders", "wrong value in StartCleanUp parameter from propertymap.xlsx file -"+StartCleanUp+"");//wrong value in StartCleanUp parameter from propertymap.xlsx
			}
			
			if(RunKvsCleanUp.equalsIgnoreCase("YES") && PerformRecovery)
			{
				runKvsCleanUp(SideScreenNames);
				RunKvsCleanUp = "No";
			}
			else if(RunKvsCleanUp.equalsIgnoreCase("NO")  && BumpAllKvsScreens.equalsIgnoreCase("YES"))
			{
				bumpAllKvsScreens();
			}
			else if(!RunKvsCleanUp.equalsIgnoreCase("NO"))
			{
				HtmlResult.failed("To clear KVS screens", " Should be able to clear KVS screens", "wrong value in RunKvsCleanUp parameter from propertymap.xlsx file -"+RunKvsCleanUp+"");//wrong value in RunKvsCleanUp parameter from propertymap.xlsx
			}
			
			
			for(String MenuAndProducts : ArrMenuAndProductName)
			{
				String[] MenuNameAndProducts = MenuAndProducts.split(":");
				ArrayList<String > ArrListProducts = new ArrayList<String>();
				boolean RunOnce = true;
				int ManagerLoginCounter = 0;
				
				if(MenuNameAndProducts.length==2)
				{
					MenuName = MenuNameAndProducts[0];
					String [] ProductsArray = MenuNameAndProducts[1].split("#");
					
					for(String Product :ProductsArray)
					{
						ArrListProducts.add(Product);
					}
				}
				
		
				
				boolean MenuSelection = selectMenu(MenuName.trim());
				if(MenuSelection)
				{
					String OrderedProducts = "";
					String UnOrderedProducts = "";
					for(String Product : ArrListProducts)
					{
						boolean OrderProduct = clickButton(Product);
						
						if(OrderProduct)
						{
							OrderedProducts = OrderedProducts.concat(Product+",");
							//to handle managers login
							ManagerLoginCounter++;
							while(RunOnce && ManagerLoginCounter>=10)
							{
								boolean Status=handleManagerAuthentication();
								if(Status)
								{
									RunOnce=false;
								}
								else
								{
									break;
								}
							}
						}
						else
						{
							UnOrderedProducts = UnOrderedProducts.concat(Product+",");
						}
					}
					
					if(OrderedProducts.length()>0)
					{
						HtmlResult.passed("To order product from Menu", "Should be able to order products- '"+MenuNameAndProducts[1]+"' from menu-'"+MenuName+"'", "Products -"+OrderedProducts+" ordered successfully from menu-' "+MenuName+" '");
					}
					
					if(UnOrderedProducts.length()>0)
					{
						HtmlResult.failed("To order product from Menu", "Should be able to order products- '"+MenuNameAndProducts[1]+"' from menu-'"+MenuName+"'", "Images not found for products-' "+UnOrderedProducts+" '");
					}
					
					
				}
				else
				{
					HtmlResult.failed("To order product from Menu", "Should be able to order products- '"+MenuNameAndProducts[1]+"' from menu-'"+MenuName+"'", "button not found -"+MenuName);
				}
			}
		}
		catch(Exception e)
		{
			HtmlResult.failed("To order product from Menu", "Should be able to order products", "unexpected error while ordering a product after selecting product-'"+e.getMessage()+","+eggUIDriver.LastErrorMessage+"'");//unexpected error while ordering a product after selecting product
		}
	}
	
	public boolean bumpAllKvsScreens() {
		
		try
		{
			boolean Performed = false;
			String KvsNames = getValueFromExcel("KVS_NAMES");
	
				if(performSoftBumpbar(KvsNames))
				{
					Performed = true;
				}
				
			return Performed;
		}
		catch(Exception e)
		{
			return false;
		}

	}
	
	public boolean recallOrder(Map<String,String> Input)
	{
		String strDescription = "To recall order from Kiosk";
		String strExpectedResult = "Order should get tendered successfully";
		String RecallOrderImageName = getValueFromExcel("RecallOrderImageName");
		try
		{
			if(clickButton(RecallOrderImageName))
			{
				List<Point> TextLocations = eggUIDriver.everyTextLocation("Total");
				if(TextLocations!=null)
				{
					boolean ButtonFound = false;
					for(Point TextLocation : TextLocations)
					{
						ButtonFound = false;
						if(eggUIDriver.clickPoint(TextLocation))
						{
							if(eggUIDriver.imageFound("tender exactcash"))
							{
								paymentType(Input);
								ButtonFound = true;
								break;
							}
						}
					}
					
					if(!ButtonFound)
					{
						HtmlResult.failed(strDescription, strExpectedResult, "Tender menu is not displayed on screen");
					}
				}
				else
				{
					List<Point> NewTextLocations = eggUIDriver.everyTextLocation("END OF SALE");
					if(NewTextLocations != null)
					{
						boolean ButtonFound = false;
						for(Point TextLocation : NewTextLocations)
						{
							ButtonFound = false;
							if(eggUIDriver.clickPoint(TextLocation))
							{
								if(eggUIDriver.imageFound("tender exactcash"))
								{
									paymentType(Input);
									ButtonFound = true;
									break;
								}
								
							}
						}
						
						if(!ButtonFound)
						{
							HtmlResult.failed(strDescription, strExpectedResult, "Tender menu is not displayed on screen");
						}
					}
					else
					{
						Point RandomPoint = new Point(256,325);
						boolean ButtonFound = false;
						int Counter = 0;
						while(!ButtonFound && Counter<=8)
						{
							ButtonFound = false;
							if(eggUIDriver.clickPoint(RandomPoint))
							{
								if(eggUIDriver.imageFound("tender exactcash"))
								{
									paymentType(Input);
									ButtonFound = true;
									break;
								}
							}
						}
						
						if(!ButtonFound)
						{
							HtmlResult.failed(strDescription, strExpectedResult, "Tender menu is not displayed on screen");
						}
						
					}
				}
			
			}
			else
			{
				HtmlResult.failed(strDescription, strExpectedResult, "Recall button not found on POS");
			}
		}
		catch(Exception e)
		{
			HtmlResult.failed(strDescription, strExpectedResult, "Error while executing component 'recallOrder' ,"+e.getMessage()+","+e.getCause());
			return false;
		}
		return false;
	}


	public void runKvsCleanUp(String KvsName)
	{
		try {
			performSoftBumpbar(KvsName);
		} catch (IOException e) {
			
			return;
		}
	}

	public boolean selectMenu(String MenuName)
{

	try
	{
		//for every menu to be launch offers button should be clicked first as its always present on screen and 
		//set menu to Lunch by default

		String strMenuName=MenuName;
		strMenuName = strMenuName.trim();
		String stepDescrp="To launch menu-"+strMenuName;
		String strExpected="Menu should be launched-"+strMenuName;
		String strMenuButton="";
		String strOffersButton="";
		String strHelperMenuButton="";
		strMenuName=strMenuName.trim();
		GlobalMenuNamme = strMenuName;
		//**************************************lunch menu*******************************************************
		if(strMenuName.equalsIgnoreCase("LUNCH MENU")||strMenuName.equalsIgnoreCase("LUNCH"))
		{
			boolean FoundAndClicked=false;
			System.out.println("Loading Lunch Menu"); 
			try 
			{
				strMenuButton=getValueFromExcel("LUNCHMENU"); 
				strOffersButton=getValueFromExcel("OFFERSMENU") ;
			} 
			catch (Exception e) 
			{
				return false;
			}		

			FoundAndClicked= clickButton(strOffersButton);
			if(FoundAndClicked)
			{
				FoundAndClicked=clickButton(strMenuButton);
				if(FoundAndClicked)
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
				return false;	
			}
		}
		//******************************************breakfast menu*****************************************************	
		else if(strMenuName.equalsIgnoreCase("BREAKFAST MENU")||strMenuName.equalsIgnoreCase("Breakfast") ||strMenuName.equalsIgnoreCase("menu Breakfast") )
		{
			boolean FoundAndClicked=false;
			System.out.println("Loading breakfast menu");
			try {
				strMenuButton=getValueFromExcel("BREAKFASTMENU");
				strOffersButton=getValueFromExcel("OFFERSMENU") ; 
				strHelperMenuButton=getValueFromExcel("LUNCHMENU");//helper is lunch menu
			}

			catch(Exception e)
			{
				return false;
			}

			FoundAndClicked=clickButton(strOffersButton);
			if(FoundAndClicked)
			{
				FoundAndClicked=clickButton(strHelperMenuButton);// find lunch image
				if(FoundAndClicked)
				{
					FoundAndClicked=clickButton(strHelperMenuButton);// again clicked on lunch menu to launch breakfast
					if(FoundAndClicked)
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
					return false;
				}
			}
			else
			{
				return false;				
			}			
		}
		//******************************************Dessert menu*****************************************************
		else if(strMenuName.equalsIgnoreCase("DESSERTS MENU")||strMenuName.equalsIgnoreCase("DESSERTS"))
		{
			boolean FoundAndClicked=false;
			System.out.println("Loading Desserts menu");
			try 
			{
				strMenuButton=getValueFromExcel("DESSERTSMENU");
			}
			catch (Exception e) 
			{
				return false;
			}

			FoundAndClicked=clickButton(strMenuButton);

			if(FoundAndClicked)
			{
				return true;
			}
			else
			{
				return false;
			}	
		}

		//************************************LRAGE EVM MENU*******************************************************
		else if(strMenuName.equalsIgnoreCase("LRG EVM")||strMenuName.equalsIgnoreCase("LARGE EVM")
				||strMenuName.equalsIgnoreCase("LARGE EVM MEAL")||strMenuName.equalsIgnoreCase("LARGE MEAL")
				||strMenuName.equalsIgnoreCase("LRG EVM MENU")||strMenuName.equalsIgnoreCase("Lrg Lunch Meal")
				||strMenuName.equalsIgnoreCase("levm"))
		{
			System.out.println("Loding large evm menu");
			try {	
				boolean blnResult=selectMealMenuUK("LRGEVM");
				if(blnResult)
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
				HtmlResult.failed(stepDescrp,strExpected,"failed to launch menu-LRG EVM"+e);
			}
		}

		//****************************************happy meal***************************************************
		else if(strMenuName.equalsIgnoreCase("HAPPY MEAL")||
				strMenuName.equalsIgnoreCase("HAPPY MEAL MENU")||
				strMenuName.equalsIgnoreCase("Happy meals"))
		{
			System.out.println("Loding happy meal menu");
			try {	
				boolean blnResult=selectMealMenuUK("HAPPYMEAL");
				if(blnResult)
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

		//*********************************************MED EVM LUNCH***********************************************
		else if(strMenuName.equalsIgnoreCase("MED EVM")||strMenuName.equalsIgnoreCase("MEDIUM EVM")
				||strMenuName.equalsIgnoreCase("MEDIUM EVM MEAL")||strMenuName.equalsIgnoreCase("MEDIUM MEAL")
				||strMenuName.equalsIgnoreCase("MED EVM MENU")||strMenuName.equalsIgnoreCase("Med Lunch Meal")||
				strMenuName.equalsIgnoreCase("mevm"))
		{
			System.out.println("Loading lunch medium evm menu");
			try {
				boolean blnResult=selectMealMenuUK("MEDEVM");
				if(blnResult)
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
				HtmlResult.failed(stepDescrp,strExpected,"HtmlResult.failed to launch menu-MED EVM"+e);
			}
		}
		//*****************************************MED EVM BREAKFAST MENU******************************************
		else if(strMenuName.equalsIgnoreCase("BREAKFAST MED EVM")||
				strMenuName.equalsIgnoreCase("BREAKFAST MEDIUM EVM")||
				strMenuName.equalsIgnoreCase("BREAKFAST MED EVM MEAL")||
				strMenuName.equalsIgnoreCase("BREAKFAST MEDIUM MEAL")||
				strMenuName.equalsIgnoreCase("BREAKFAST MED EVM MENU")||
				strMenuName.equalsIgnoreCase("Breakfast Medium Meal"))
		{
			boolean FoundAndClicked=false;
			String strMealmenu="";
			System.out.println("Loading Breakfast med evm menu");	
			try{

				strOffersButton=getValueFromExcel("OFFERSMENU") ; 																					    //lunch screen once clicked so can be use to load lunch menu
				strHelperMenuButton=getValueFromExcel("LUNCHMENU");
				strMealmenu=getValueFromExcel("MEDEVM");
			}
			catch(Exception e)
			{
				return false;
			}				
			FoundAndClicked=clickButton(strOffersButton);
			if(FoundAndClicked)
			{
				
				FoundAndClicked=clickButton(strHelperMenuButton);//lunch menu button
				if(FoundAndClicked)
				{
					
					FoundAndClicked=clickButton(strHelperMenuButton);//again clicking on lunch menu to load breakfast menu
					if(FoundAndClicked)
					{
						
						FoundAndClicked=clickButton(strMealmenu);//clicking on meal button
						if(FoundAndClicked)
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
		//***************************************************dips menu*********************************
		else if(strMenuName.equalsIgnoreCase("DIPS")||strMenuName.equalsIgnoreCase("DIPS MENU"))
		{
			boolean FoundAndClicked=false;
			System.out.println("Loading dips menu");
			try 
			{
				strMenuButton=getValueFromExcel("DIPSMENU");
			} 
			catch (Exception e)
			{
				return false;
			}

			FoundAndClicked=clickButton(strMenuButton);
			if(FoundAndClicked)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		//******************************************Big flavour wraps*************************************/		
		else if(strMenuName.equalsIgnoreCase("Big flavour wraps")||strMenuName.equalsIgnoreCase("Big flavour wraps menu"))
		{
			boolean FoundAndClicked=false;
			try{
				strMenuButton=getValueFromExcel("BIGFLAVOURWRAPS");
			}
			catch(Exception e)
			{
				return false;
			}
			FoundAndClicked=clickButton(strMenuButton);
			if(FoundAndClicked)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		//******************************************MED Big flavour wraps**************************************//		
		else if(strMenuName.equalsIgnoreCase("Med Big flavour wraps")||strMenuName.equalsIgnoreCase("Med Big flavour wraps menu")
				||strMenuName.equalsIgnoreCase("Med evm Big flavour wraps")||strMenuName.equalsIgnoreCase("Med evm Big flavour wraps menu")
				||strMenuName.equalsIgnoreCase("Medium evm Big flavour wraps")||strMenuName.equalsIgnoreCase("Medium big flavour wraps"))
		{
			boolean FoundAndClicked=false;
			String strMainMealMenuButton="";
			try{
				strMenuButton=getValueFromExcel("BIGFLAVOURWRAPS");
				strMainMealMenuButton=getValueFromExcel("MED_BIGFLAVOURWRAPS");
			}
			catch(Exception e)
			{
				return false;
			}
			FoundAndClicked=clickButton(strMenuButton);
			if(FoundAndClicked)
			{
				FoundAndClicked=clickButton(strMainMealMenuButton);
				if (FoundAndClicked) 
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
				return false;
			}
		}
		//***********************************LRG big flavour wraps*******************************************
		else if(strMenuName.equalsIgnoreCase("Lrg Big flavour wraps")||strMenuName.equalsIgnoreCase("Large Big flavour wraps")
				||strMenuName.equalsIgnoreCase("Large evm Big flavour wraps")||strMenuName.equalsIgnoreCase("Large evm Big flavour wraps menu"))
		{
			boolean FoundAndClicked=false;
			String strMainMealMenuButton="";
			try{
				strMenuButton=getValueFromExcel("BIGFLAVOURWRAPS");
				strMainMealMenuButton=getValueFromExcel("LRG_BIGFLAVOURWRAPS");
			}
			catch(Exception e)
			{
				HtmlResult.failed(stepDescrp,strExpected,"Unable to read properties file-"+e.getMessage());
			}
			FoundAndClicked=clickButton(strMenuButton);
			if(FoundAndClicked)
			{
				FoundAndClicked=clickButton(strMainMealMenuButton);
				if (FoundAndClicked) 
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
				return false;
			}
		}
		//****************************************************breakfast employee*********************************************//		
		else if(strMenuName.equalsIgnoreCase("BREAKFAST EMP")||
				strMenuName.equalsIgnoreCase("BREAKFAST EMPLOYEE")||
				strMenuName.equalsIgnoreCase("BREAKFAST EMPLOYEE MENU"))
		{
			boolean FoundAndClicked=false;
			try {
				strMenuButton=getValueFromExcel("BREAKFASTEMP");
				strOffersButton=getValueFromExcel("OFFERSMENU");	
			} 
			catch (Exception e) 
			{
				return false;
			}
			FoundAndClicked=clickButton(strOffersButton);
			if(FoundAndClicked)
			{
				FoundAndClicked=clickButton(strMenuButton);
				if(FoundAndClicked)
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
				return false;
			}
		}
		//**************************************Med Employee*********************************************			
		else if(strMenuName.equalsIgnoreCase("MED EMPLOYEE")||
				strMenuName.equalsIgnoreCase("MEDIUM EMPLOYEE")||
				strMenuName.equalsIgnoreCase("MEDIUM EMPLOYEE MEAL")||
				strMenuName.equalsIgnoreCase("MEDIUM EVM EMPLOYEE")||
				strMenuName.equalsIgnoreCase("MEDIUM EVM EMPLOYEE MENU")||
				strMenuName.equalsIgnoreCase("MED EMP")||
				strMenuName.equalsIgnoreCase("Med Emp Lunch Meal"))
		{
			boolean FoundAndClicked=false;
			String strMainMenuButton="";
			try 
			{
				strOffersButton=getValueFromExcel("OFFERSMENU");	
				strMenuButton=getValueFromExcel("MEDEMPLYOEE");
				strMainMenuButton=getValueFromExcel("EMPLOYEEMEALS");
			} 
			catch (Exception e) 
			{
				return false;
			}

			FoundAndClicked=clickButton(strOffersButton);
			if(FoundAndClicked)
			{
				FoundAndClicked=clickButton(strMainMenuButton);
				if(FoundAndClicked)
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
				return false;
			}
		}
		//**********************************lrg employee********************************************************
		else if(strMenuName.equalsIgnoreCase("LRG EMPLOYEE")||
				strMenuName.equalsIgnoreCase("LARGE EMPLOYEE")||
				strMenuName.equalsIgnoreCase("LARGE EVM EMPLOYEE")||
				strMenuName.equalsIgnoreCase("LARGE EMPLOYEE MEAL")||
				strMenuName.equalsIgnoreCase("LRG EMP")||
				strMenuName.equalsIgnoreCase("Lrg Emp Lunch Meal"))
		{
			boolean FoundAndClicked=false;
			String strMainMenuButton="";

			try 
			{
				strOffersButton=getValueFromExcel("OFFERSMENU");	
				strMenuButton=getValueFromExcel("LRGEMPLOYEE");
				strMainMenuButton=getValueFromExcel("EMPLOYEEMEALS");
			} 
			catch (Exception e) 
			{
				return false;
			}

			FoundAndClicked=clickButton(strOffersButton);
			if(FoundAndClicked)
			{
				FoundAndClicked=clickButton(strMainMenuButton);
				if(FoundAndClicked)
				{
					FoundAndClicked=clickButton(strMenuButton);
					if(FoundAndClicked)
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
					return false;
				}
			}
			else
			{
				return false;
			}
		}
		//********************************************MED EMP FLAVOUR WRAPS***************************
		else if(strMenuName.equalsIgnoreCase("MED EMP FLAVOUR WRAPS")||
				strMenuName.equalsIgnoreCase("Med Emp Big Flavour Wraps"))
		{
			boolean FoundAndClicked=false;
			try 
			{
				strOffersButton=getValueFromExcel("OFFERSMENU");	
				strMenuButton=getValueFromExcel("EMP_FLAVWRAPS");
			} 
			catch (Exception e) 
			{
				return false;
			}

			FoundAndClicked=clickButton(strOffersButton);
			if(FoundAndClicked)
			{
				FoundAndClicked=clickButton(strMenuButton);
				if(FoundAndClicked)
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
				return false;
			}	
		}
		//***************************LRG EMP WRAPS************************************************
		else if(strMenuName.equalsIgnoreCase("LRG EMP FLAVOUR WRAPS")||
				strMenuName.equalsIgnoreCase("Lrg Emp Big Flavour Wraps"))
		{
			boolean FoundAndClicked=false;
			String strMainMenuButton="";
			try 
			{
				strOffersButton=getValueFromExcel("OFFERSMENU");	
				strMenuButton=getValueFromExcel("EMP_FLAVWRAPS");
				strMainMenuButton=getValueFromExcel( "LRGEMP_FLAVWRAPS");
			} 
			catch (Exception e) 
			{
				return false;
			}

			FoundAndClicked=clickButton(strOffersButton);
			{
				if(FoundAndClicked)
				{
					FoundAndClicked=clickButton(strMenuButton);
					if(FoundAndClicked)
					{
						FoundAndClicked=clickButton(strMainMenuButton);
						if(FoundAndClicked)
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
						return false;						}
				}
				else
				{
					return false;					}
			}	
		}
		else
		{
			return false;			}
	}

	catch (Exception e)
	{
		return false;		}
	return false;
}

	public static String getSideScreenName()
	{
		String SideScreenName = "";
		Rectangle r = new Rectangle();
		try
		{
			
			String SideNameLeftTopCorner = getValueFromExcel("SideNameLeftTopCorner").trim();
			String SideNameRightBottomCorner = getValueFromExcel("SideNameRightBottomCorner").trim();
			
			ArrayList<Integer> img1 = eggUIDriver.ImageLocation(SideNameLeftTopCorner);
			int x1 = img1.get(0);
			int y1 = img1.get(1);

			ArrayList<Integer> img2 = eggUIDriver.ImageLocation(SideNameRightBottomCorner);

			int x2 = img2.get(0);
			int y2 = img2.get(1);

			r.x = x1;
			r.y = y1;
			r.width = x2 - x1;
			r.height = y2 - y1;

			SideScreenName = eggUIDriver.readText(r);
			SideScreenName = SideScreenName.trim().replaceAll("\n", "").trim();
			return SideScreenName;
		}
		catch(Exception e)
		{
			return "";
		}

	}
	
	public void clickButtonOnScreen(Map<String,String> Input)
	{
		String strDescription = "To click button on screen";
		String strExpectedResult = "Button should be clicked";
		String ButtonName = Input.get("ButtonName");
		try
		{
			ButtonName = ButtonName.trim();
			if(clickButton(ButtonName))
			{
				HtmlResult.passed(strDescription, strExpectedResult, "Button '"+ButtonName+"' clicked successfully");
			}
			else
			{
				HtmlResult.failed(strDescription, strExpectedResult, "Button '"+ButtonName+"' is not clicked successfully");
			}
		}
		catch(Exception e)
		{
			HtmlResult.failed(strDescription, strExpectedResult, "Button '"+ButtonName+"' is not clicked successfully");
		}
		
	}


	public boolean handleManagerAuthentication()
	{
		String ManagerAuthenticationEnterButton = getValueFromExcel("ManagerAuthenticationEnterButton");
		String ManagerAuthenticationNumber3Button = getValueFromExcel("ManagerAuthenticationNumber3Button");
		
		if(eggUIDriver.imageFound(ManagerAuthenticationEnterButton))
		{
			boolean blFlag = clickButton(ManagerAuthenticationNumber3Button);
			if(blFlag)
			{
				blFlag = clickButton(ManagerAuthenticationEnterButton);
			}
			else
			{
				eggUIDriver.wait(3);
				return false;
			}
			
			//
			if(blFlag)
			{
				blFlag = clickButton(ManagerAuthenticationNumber3Button);
			}
			else
			{
				eggUIDriver.wait(3);
				return false;
			}
			
			//
			if(blFlag)
			{
				blFlag = clickButton(ManagerAuthenticationEnterButton);
				return true;
			}
			else
			{
				eggUIDriver.wait(3);
				return false;
			}
			
		}
		else
		{
			eggUIDriver.wait(2);
		}
		
		return false;
		
	
	}












}

	
	