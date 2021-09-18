package com.cg.ApplicationLevelComponent;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.cg.Logutils.HtmlResult;
import com.cg.TestCaseRunner.TestCaseRunner;
import com.cg.UIDriver.EggUIDriver;

import jxl.JXLException;
import jxl.Sheet;
import jxl.Workbook;

public class EggPlant
{
	public static EggUIDriver eggUIDriver=new EggUIDriver();
	String strNgkLanguage="";


	//------------------------------------------------------------------------------------------------------------

	/***************************************************************************************************************
	 * Project - McDonalds UKIT Automation eggPlant Methods
	 * Method Name - connect
	 * Parameters - IpAddress , PortNumber ( Parameters will be use in Key (HashMap - Key)
	 * Return Type - Boolean value
	 * Framework - UKIT Master Framework
	 * Author - Prateek Gupta / Yogesh Chiplunkar
	 * Date - 03/02/2016
	 * Modified Date -
	 * Reason for Modification -
	 * @param input
	 * @return
	 ***************************************************************************************************************/

	public boolean connect(Map<String,String> input)
	{
		try {

			String SystemName = input.get("SystemName").trim();
			String IpAddressAndPortNum = getValueFromExcel(SystemName);
			String [] arrIpAddressAndPortNum = IpAddressAndPortNum.split(":");
			String IpAddress = "";
			String portNum = "";

			if(arrIpAddressAndPortNum.length>=2)
			{
				IpAddress = arrIpAddressAndPortNum[0];
				portNum = arrIpAddressAndPortNum[1];
			}
			else if(arrIpAddressAndPortNum.length == 1)
			{
				if(arrIpAddressAndPortNum[0].contains("."))
				{
					IpAddress = arrIpAddressAndPortNum[0];
					portNum = "5901";
				}
				else
				{
					IpAddress = "";
					portNum = arrIpAddressAndPortNum[0];
				}
			}


			String strExpected="Should be able to connect to-'"+SystemName+"'";
			String strStepDesc="To connect to the system";
			int intPortNum=Integer.parseInt(portNum);
			boolean ConnectionStatus=eggUIDriver.connect(IpAddress.trim(),intPortNum);
			if(ConnectionStatus)
			{
				Dimension ScreenSize = eggUIDriver.remoteScreenSize();
				Rectangle RecScreenSize=null;
				if(ScreenSize.width==1600)
				{
					RecScreenSize= new Rectangle(0,0,800,600);
				}
				else
				{
					RecScreenSize = new Rectangle(ScreenSize);
				}

				eggUIDriver.setSearchRectangle(RecScreenSize);
				HtmlResult.passed(strStepDesc, strExpected, "Connection successful-'"+SystemName+"'");
				eggUIDriver.wait(2);
				return true;
			}
			else
			{
				HtmlResult.failed(strStepDesc, strExpected,"Connection not successful-'"+ IpAddress+"'");
				return false;
			}
		} catch (Exception e) {
			return false;

		}

	}


	/***************************************************************************************************************
	 * //disconnect the SUT during runtime
	 * Project - McDonalds UKIT Automation eggPlant Methods
	 * Method Name - disconnect
	 * Parameters - no parameters
	 * Return Type - Boolean
	 * Framework - UKIT Master Framework
	 * Author - Prateek Gupta / Yogesh Chiplunkar
	 * Date - 03/02/2016
	 * Modified Date -
	 * Reason for Modification -
	 * @param input
	 * @return
	 ***************************************************************************************************************/


	public boolean disconnect()
	{
		try {
			boolean status=eggUIDriver.disconnect();
			if(status)
			{
				HtmlResult.passed("To disconnect the system", "System should be disconnected successfully","Disconnect successful");
				return true;
			}
		} catch (Exception e) {

			HtmlResult.failed("To disconnect the system", "System should be disconnected successfully","Disconnect successful");
			return false;
		}
		return false;
	}



	/***************************************************************************************************************
	 * // - Reading Value from Excel file
	 * Project - McDonalds UKIT Automation eggPlant Methods
	 * Method Name - getValueFromExcel
	 * Parameters -
	 * Method Type - Framework Method
	 * Return Type - Boolean
	 * Framework - UKIT Master Framework
	 * Author - Prateek Gupta / Yogesh Chiplunkar
	 * Date - 03/02/2016
	 * Modified Date -
	 * Reason for Modification -
	 * @param input
	 * @return
	 ***************************************************************************************************************/

	public static String getValueFromExcel(String Key)
	{
		List<Map<String,String>> ExcelMap=TestCaseRunner.ConfigurationMap;
		String Value="";
		boolean Found=false;
		for(Map map:ExcelMap)   // Reading from Excel Map on behalf of Key and Value pair
		{
			String MapKey=(String)map.get("Key");
			if(MapKey.equalsIgnoreCase(Key.trim()))
			{
				Value=(String)map.get("Value");

				if(! ( (Value==null) || (Value.equals("")) ) )
				{
					return Value;
				}
				else
				{
					Found=false;
				}
			}
		}

		if(Found==false)
		{
			//log error
			return null;
		}
		else
		{
			return Value;
		}

	}


	public void startEggplantProcess()
	{
		try {
			TestCaseRunner.startEggDriveProcess();
			HtmlResult.passed("To start Eggplant process", "Eggplant process should be start successfully", "Eggplant process starts successfully");
		} catch (Exception e) {
			HtmlResult.failed("To start Eggplant process", "Eggplant process should be start successfully", "Starting Eggplant process failed");
		}
	}

	public void stopEggplantProcess()
	{
		try {
			TestCaseRunner.stopEggDriverProcess();
			HtmlResult.passed("To stop Eggplant process", "Eggplant process should be stop successfully", "Eggplant process stop successfully");
		} catch (Exception e) {
			HtmlResult.failed("To stop Eggplant process", "Eggplant process should be stop successfully", "Stopping Eggplant process failed");
		}
	}

	//Reading excel data
	public static String getExcelProductCombination(String fileName,String PropertyName) throws FileNotFoundException, IOException, JXLException
	{
		try
		{
			String strValue = "";
			String Propertyfilepath ="";
			String ExcelfileName ="";
			String strProperty=PropertyName.trim();
			String file=fileName.trim();
			ExcelfileName = file + ".xls";
			Properties prop = new Properties();

			String filepath= System.getProperty("user.dir") + "\\FrameWork\\LookUp\\OR\\";

			if (file.equalsIgnoreCase("POSCombinationData"))
			{
				Propertyfilepath= filepath + ExcelfileName ;
				strValue = readExcelFile(Propertyfilepath, strProperty);
				return strValue;
			}
			else if (file.equalsIgnoreCase("NGKCombinationData"))
			{
				Propertyfilepath= filepath + ExcelfileName ;
				strValue = readExcelFile(Propertyfilepath, strProperty);
				return strValue;
			}
			else if (file.equalsIgnoreCase("NGKCLearChoiceData"))
			{
				Propertyfilepath= filepath + ExcelfileName ;
				strValue = readExcelFile(Propertyfilepath, strProperty);
				return strValue;
			}
			else
			{
				strProperty=null;
			}
		}
		catch (JXLException e)
		{
			System.out.println(e.getMessage());
			return null;
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			return null;
		}
		return null;
	}


	public static String readExcelFile(String FilePath,String PropertyName ) throws JXLException, IOException
	{
		FileInputStream fs = new FileInputStream(FilePath);
		String FlagValue="Y";
		String CellFlagValue ="Y";
		Workbook wb = Workbook.getWorkbook(fs);

		// TO get the access to the sheet
		Sheet sh = wb.getSheet(PropertyName);

		// To get the number of rows present in sheet
		int totalNoOfRows = sh.getRows();

		// To get the number of columns present in sheet
		int totalNoOfCols = sh.getColumns();

		String SingleSProductString ="";
		String OtherProductString ="";

		for (int row = 1; row < totalNoOfRows; row++) {
			boolean RowFlagValue = false;
			SingleSProductString ="";
			for (int col = 1; col < totalNoOfCols; col++) {
				//System.out.print(sh.getCell(col, row).getContents() );
				CellFlagValue = sh.getCell(0, row).getContents().trim();
				if (FlagValue.equalsIgnoreCase(CellFlagValue))
				{
					RowFlagValue = true;
					String SingleSProductString1 = sh.getCell(col, row).getContents();
					SingleSProductString += SingleSProductString1 + "|" ;
					//System.out.println(SingleSProductString);
				}
			}
			if (RowFlagValue)
			{
				OtherProductString += SingleSProductString +",";
				OtherProductString =  OtherProductString.replace("|,", ",");
			}
		}
		String FinalString="";

		OtherProductString=OtherProductString.substring(0, OtherProductString.length()-1);
		System.out.println("Final string : = " +  OtherProductString);
		FinalString = OtherProductString.trim();
		//System.out.println("Final string : = " +  FinalString);
		return FinalString;
	}

	//**********************************************CLICK FUNCTION WITH REPORTING BUT NOT A COMPONENT*************************//
	// Created by:yogesh
	// Purpose: Find and click the button and also add events to the report
	// Date=28/07/2016
	public boolean clickButtonAddToReport(String ButtonName)
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
				Clicked = eggUIDriver.click(Button);
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
	//************************************************************************************************************************//

	//Abhishek [09/08/2016]
	public Rectangle ScreenPart(double TLx, double TLy,double BRx, double BRy)

	{
		Rectangle r = new Rectangle();

		try {
			Dimension XY = eggUIDriver.remoteScreenSize();

			r.x = (int) (XY.getWidth()*TLx);
			r.y = (int) (XY.getHeight()*TLy);
			r.width = (int) (XY.getWidth()*BRx);
			r.height= (int) (XY.getHeight()*BRy);

			return r;

		} catch (Exception e) {
			HtmlResult.failed("ScreenPart Method", "Screen Part selection should be passed ", "Screen part selection is failed");
			e.printStackTrace();
		}
		return null;

	}

	//******************

	public void pressHomeButton()
	{
		try {
			eggUIDriver.pressHomeButton();
			HtmlResult.passed("To press home button","Home button should be pressed successfully","Home button pressed successfully");
		} catch (Exception e) {
			HtmlResult.failed("To press home button","Home button should be pressed successfully","Home button not pressed successfully-"+e);
		}
	}

	public void typeText(Map Text)
	{

		String strText=Text.get("Text").toString().trim();
		try {
			eggUIDriver.typeText(strText);
			HtmlResult.passed("To type the text ","Text-'"+Text+"' should be typed sucessfully","Text typed successfully:'"+Text+"'");
		} catch (Exception e) {
			HtmlResult.failed("To type the text ","Text-'"+Text+"' should be typed sucessfully","cannot type text:'"+Text+"'- "+e);
		}
	}

	//*********************TO SELECT PRODUCT AND THEIR SUB PRODUCTS SEPARATED BY -***********************************************//
	public boolean clickButton(String ButtonName)
	{
		String strButton=ButtonName.trim();
		boolean Found=false;
		boolean Clicked=false;
		boolean Status=false;
		String[] strButtonList=strButton.split("#");

		for (String Button:strButtonList) {
			Status=false;
			Button = Button.trim();
			Found=eggUIDriver.imageFound(Button);
			if (Found) {
				Clicked =eggUIDriver.clickImage(Button);
				if (Clicked) {
					Status = true;
				}
			}
			else
			{
				return false;
			}
		}
		return Status;
	}

	public boolean clickButtonWithReport(String ButtonNames)
	{
		String strButtonNames;
		String strStepDesc;
		strStepDesc="To click button present on screen";
		String strExpected;


		try {
			boolean Result=false;
			strButtonNames=ButtonNames.trim();
			String [] strArrButtonNames=strButtonNames.split("#"); // previous was Spiit(,)

			for(String ButtonName:strArrButtonNames)
			{
				ButtonName=ButtonName.trim();
				boolean ImageFound=false;
				boolean ImageClicked=false;
				Result=false;
				strExpected="Should be able to click button-"+ButtonName+" successfully";
				ImageFound=eggUIDriver.imageFound(ButtonName);
				if(ImageFound)
				{

					ImageClicked=eggUIDriver.clickImage(ButtonName);
					if(ImageClicked)
					{
						HtmlResult.passed(strStepDesc,strExpected,"Button-'"+ButtonName+"' found and clicked successfully");
						Result=true;
					}
					else
					{
						HtmlResult.failed(strStepDesc,strExpected,"Button-'"+ButtonName+"' not clicked");
					}
				}
				else
				{
					HtmlResult.failed(strStepDesc,strExpected,"Button-'"+ButtonName+"' not found");
					return false;
				}

			}
			return Result;
		} catch (Exception e) {

			//	HtmlResult.failed(strStepDesc,strExpected,"Button-'"+ ButtonNames +"' not found '" + e.getMessage() + "'");
			return false;
		}

	}



	public boolean connect1(String SystemName)
	{
		try {

			SystemName = SystemName.trim();
			String IpAddressAndPortNum = getValueFromExcel(SystemName);
			String [] arrIpAddressAndPortNum = IpAddressAndPortNum.split(":");
			String IpAddress = "";
			String portNum = "";

			if(arrIpAddressAndPortNum.length>=2)
			{
				IpAddress = arrIpAddressAndPortNum[0];
				portNum = arrIpAddressAndPortNum[1];
			}
			else if(arrIpAddressAndPortNum.length == 1)
			{
				if(arrIpAddressAndPortNum[0].contains("."))
				{
					IpAddress = arrIpAddressAndPortNum[0];
					portNum = "5901";
				}
				else
				{
					IpAddress = "";
					portNum = arrIpAddressAndPortNum[0];
				}
			}


			String strExpected="Should be able to connect to-'"+SystemName+"'";
			String strStepDesc="To connect to the system";
			int intPortNum=Integer.parseInt(portNum);
			boolean ConnectionStatus=eggUIDriver.connect(IpAddress.trim(),intPortNum);
			if(ConnectionStatus)
			{
				Dimension ScreenSize = eggUIDriver.remoteScreenSize();
				Rectangle RecScreenSize=null;
				if(ScreenSize.width==1600)
				{
					RecScreenSize= new Rectangle(0,0,800,600);
				}
				else
				{
					RecScreenSize = new Rectangle(ScreenSize);
				}

				eggUIDriver.setSearchRectangle(RecScreenSize);
				eggUIDriver.wait(2);
				return true;
			}
			else
			{
				HtmlResult.failed(strStepDesc, strExpected,"Connection not successful-'"+ IpAddress+"'");
				return false;
			}
		} catch (Exception e) {
			return false;

		}

	}


	//recovery section starts
	public boolean addSideItemToEmployeeMeal()
	{
		try
		{
			String OffersMenu = getValueFromExcel("OFFERSMENU");
			String EmployeeMeals = getValueFromExcel("EmployeeMealHelper");
			String SideItemName = getValueFromExcel("EmpMealsSideItemHelper");

			// launching offers menu
			boolean blnStatus  = clickButton(OffersMenu);
			if(blnStatus)
			{
				//launching med emp menu
				blnStatus = clickButton(EmployeeMeals);
			}
			else
			{
				return false;
			}

			//clicking on side item
			if(blnStatus)
			{
				blnStatus = clickButton(SideItemName);
			}
			else
			{
				return false;
			}

			//returning result
			if(blnStatus)
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




	public boolean runCleanUp()
	{
		try
		{
			boolean RecoveryStatus = launchMainScreen();
			if(RecoveryStatus)
			{
				return true;
			}
			else
			{
				RecoveryStatus = completePreviousOrder();
				if(RecoveryStatus)
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
			return false;
		}
	}

	public boolean launchMainScreen()
	{
		try
		{
			String BackButton = getValueFromExcel("BACK_BUTTON");
			String OffersMenu = getValueFromExcel("OFFERSMENU");

			boolean blnStatus = clickButton(BackButton+"#"+BackButton);
			if(blnStatus)
			{
				blnStatus = eggUIDriver.imageFound(OffersMenu);
			}
			else
			{
				return false;
			}

			if(blnStatus)
			{
				return true;
			}
			else
			{
				blnStatus = clickButton(BackButton);
			}

			if(blnStatus)
			{
				blnStatus = eggUIDriver.imageFound(OffersMenu);
			}
			else
			{
				return false;
			}

			if(blnStatus)
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



	public boolean completePreviousOrder()
	{
		try
		{
			String SalesPanelOrders = ReadSalesPanel();
			if(SalesPanelOrders.equals("")||SalesPanelOrders.contains("END OF SALE")||SalesPanelOrders.length()<=1)
			{
				return true;
			}
			else
			{
				boolean RecoverPos = clearTenderMenu();
				if(RecoverPos)
				{
					return true;
				}
				else
				{
					RecoverPos = eatinTotalExactCash();
					if(RecoverPos)
					{
						return true;
					}
					else
					{
						RecoverPos = clearGrillScreen();
						if(RecoverPos)
						{
							RecoverPos = eatinTotalExactCash();
							if(RecoverPos)
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
				}
			}
		}
		catch(Exception e)
		{
			return false;
		}
	}

	public boolean eatinTotalExactCash()
	{
		try
		{
			String paymentMethod=getValueFromExcel("EXACTCASH");
			String strOffersButton=getValueFromExcel( "OFFERSMENU");
			String strTenderTypeImageName=getValueFromExcel("EATINTOTAL");

			boolean RecoveryStatus = clickButton(strTenderTypeImageName);
			if(RecoveryStatus)
			{
				int Counter=0;
				Point SmartSidePoint = new Point(331,66);
				Point SmartSidePoint2 =new Point(392,66);
				Point SmartDrinkPoint = new Point(331,122);
				Point SmartDrinkPoint2 = new Point(392,122);
				try
				{
					do{
						eggUIDriver.clickPoint(SmartSidePoint);
						eggUIDriver.wait(2);
						eggUIDriver.clickPoint(SmartSidePoint2);
						eggUIDriver.wait(2);
						eggUIDriver.clickPoint(SmartDrinkPoint);
						eggUIDriver.wait(2);
						eggUIDriver.clickPoint(SmartDrinkPoint2);

						RecoveryStatus = clickButton(paymentMethod);
						if(RecoveryStatus)
						{
							RecoveryStatus = eggUIDriver.imageFound(strOffersButton);
							if(RecoveryStatus)
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
							Counter++;
						}
						if(Counter>5)
						{
							return false;
						}

					}while(Counter<=5);
				}
				catch(Exception e)
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
		return false;
	}

	public boolean clearGrillScreen()
	{
		try
		{
			String GrillDoneButton = getValueFromExcel("GRILLDONEBUTTON");
			boolean RecoveryStatus = eggUIDriver.imageFound(GrillDoneButton);
			if(RecoveryStatus)
			{
				RecoveryStatus = eggUIDriver.clickImage(GrillDoneButton);
				if(RecoveryStatus)
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

	public boolean clearTenderMenu()
	{
		try
		{
			String paymentMethod=getValueFromExcel("EXACTCASH");
			String strOffersButton=getValueFromExcel( "OFFERSMENU");

			boolean RecoveryStatus = clickButton(paymentMethod);
			if(RecoveryStatus)
			{
				RecoveryStatus = eggUIDriver.imageFound(strOffersButton);
				if(RecoveryStatus)
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


			SalesPanelDetails = eggUIDriver.readText(r); //read data from cordinates referenced by object r in off-contrast
			SalesPanelDetails = SalesPanelDetails.replaceAll("_|\"|\\*|\\#|\\%|\\>|\\$|\\\\N|\\#|\\#|\\#|", "").trim();
			SalesPanelDetails = SalesPanelDetails.replaceAll("\\\\r|\\{|\\}", "").replaceAll("w/", "").trim();

			SalesPanelDetails=SalesPanelDetails.replaceAll("[0-9]{1,}[.][0-9]{1,}", "");
			SalesPanelDetails=SalesPanelDetails.replaceAll("[\n]{2,}", "\n").replaceAll("\t", "");

			System.out.println(SalesPanelDetails);

			return SalesPanelDetails;
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
			return "";
		}

	}

	// recovery section ends


}