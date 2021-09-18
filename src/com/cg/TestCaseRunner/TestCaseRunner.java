package com.cg.TestCaseRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cg.ApplicationLevelComponent.Appium;
//import com.cg.ApplicationLevelComponent.Appium;
import com.cg.ApplicationLevelComponent.EggPlant;
import com.cg.Driver.Driver;
import com.cg.Logutils.ConsolidateTestReport;
import com.cg.Logutils.HtmlResult;
import com.cg.Logutils.Logging;
import com.cg.UIDriver.WebUIDriver;
import com.cg.moduledrivers.ModuleDriver;

public class TestCaseRunner
{
	//global string variables
	String currentPlatform = "window";
	public static String ComponentCode="";
	public static String Technology="";
	public static String Application="Not Selected";
	public static String Market="Not Selected";
	public static String strAbortTestCase="";
	public static String strFailTestCase="";
	public static String TestCaseNumber="";
	public static String TestCaseName ="Not Selected";
	public static String TestStepNumber = "0";
	public static String TestScriptNumber = "0";
	public static String FailTestCase="";
	public static String ScriptDescription ="";
	public static String GlobalIterationNumber ="";
	public static int TotalTestCaseIteration;
	public static String gblNoOfRuns="";
	String LastAverageTimeperIteration = "LastAverageTimeperIteration";
	public static String DirPath=System.getProperty("user.dir").toString();
	String TestSetPath=DirPath+"/FrameWork/TestSet_Batch/TestSet.xlsx";// path to the batch/testset file
	public static int EcexutionCounter = 0;

	// global poi,jxl variables
	static Workbook WorkBook;
	Sheet TestSetSheet;

	//global int variables
	int TestCaseCounter=0;
	int IterationCounter=-1;
	public static int TestCaseFailureCounter=0;
	public static int TestCasePassedCounter=0;

	// global collection variables
	ArrayList<String> ScriptNumbers= new ArrayList<String>();
	public static List<Map<String,String>> uiMap=new ArrayList<Map<String,String>>();
	public static List<Map<String,String>> KvsMap=new ArrayList<Map<String,String>>();
	public static List<Map<String,String>> ConfigurationMap=new ArrayList<Map<String,String>>();
	public static List<Map<String,String>> WebElementPropertyMap=new ArrayList<Map<String,String>>();
	public static List<Map<String,String>> IterationTestDataMap=new ArrayList<Map<String,String>>();
	public static List<Map<String,String>> WFM_ElementPropertyMap=new ArrayList<Map<String,String>>();
	public static List<Map<String,String>> KvsNamesList=new ArrayList<Map<String,String>>();
	public static List<Map<String,String>> AppiumObjectList=new ArrayList<Map<String,String>>();

	// global boolean variables
	public static boolean blnComponentFalied=false;
	public static boolean blnFailTestCase=false;
	public static boolean blnTestCaseFailed=false;
	public static boolean blnTestCasePassed=false;
	public static boolean blnWarning = false;

	// global logger variables
	public static Logging FrameWorkLogger;
	public static Logging TestCaseLogger;

	//constructor
	public TestCaseRunner()
	{
		//initializing loggers
		initializeLoggers();

		//Excel handling global variable workbook
		WorkBook=getWorkbook(TestSetPath);

		//loading all the maps
		loadAllMaps();

		//setting module drivers
		setModuleDrivers();

		//Reading test cases
		//method calls excel reading functions
		readTestSet();
	}


	// 2 types of loggers required framework level and testcase level
	private void initializeLoggers()
	{
		FrameWorkLogger=new Logging();
		TestCaseLogger=new Logging();
		FrameWorkLogger.info("FrameWork", "Loggers Initialized");
	}


	// setting module drivers
	private void setModuleDrivers()
	{
		FrameWorkLogger.info("FrameWork", "Setting up module drivers");
		ModuleDriver.setModuleDrivers();//initializes all types of objects required and inserted in module driver map

	}

	public void loadAllMaps() // method to load all the required global maps
	{
		FrameWorkLogger.info("Framework", "Loading Maps-----------------------");
		//uiMap=getMap("uiMap");
		//KvsMap=getMap("KvsMap");
		ConfigurationMap=getMap("ConfigurationMap");
		//WebElementPropertyMap=getMap("WebElementPropertyMap");
		//WFM_ElementPropertyMap = getMap("WFM_ElementPropertyMap");
		//KvsNamesList = getMap("KvsNamesList");
		//AppiumObjectList = getMap("AppiumObjectList");
	}

	public List<Map<String,String>> getMap(String MapName)// fetches and stores each excel map in separate global collection map variables
	{
		List<Map<String,String>> Map=new ArrayList<Map<String,String>>();
		
		String filePath;
		String currentDir = System.getProperty("user.dir");
		try {
			if(MapName.trim().equalsIgnoreCase("uimap"))
			{
				FrameWorkLogger.info("FrameWork", "loading uiMap.xls");
				if (currentPlatform.equalsIgnoreCase("window"))
				{
					filePath=currentDir+"\\FrameWork\\LookUp\\OR\\uiMap.xls";
					Map=CreatMapFromExcel(filePath);
				}
				else if (currentPlatform.equalsIgnoreCase("mac"))
				{
					filePath=currentDir+"/FrameWork/LookUp/OR/uiMap.xls";
					Map=CreatMapFromExcel(filePath);
				}

			}
			else if(MapName.trim().equalsIgnoreCase("kvsmap"))
			{

				FrameWorkLogger.info("FrameWork", "loading KVSMap.xls");
				if (currentPlatform.equalsIgnoreCase("window"))
				{
					filePath=currentDir+"\\FrameWork\\LookUp\\OR\\KVSMap.xls";
					Workbook WorkBook=getWorkbook(filePath);
					Map=CreatMapFromSheet(WorkBook,"ProductsRouting");
				}
				else if (currentPlatform.equalsIgnoreCase("mac"))
				{
					filePath=currentDir+"/FrameWork/LookUp/OR/KVSMap.xls";
					Workbook WorkBook=getWorkbook(filePath);
					Map=CreatMapFromSheet(WorkBook,"ProductsRouting");
				}
			}
			else if(MapName.trim().equalsIgnoreCase("configurationmap"))
			{
				if (currentPlatform.equalsIgnoreCase("window"))
				{
					FrameWorkLogger.info("FrameWork", "loading PropertyMap.xlsx");
					filePath=currentDir+"\\FrameWork\\LookUp\\OR\\PropertyMap.xlsx";
					Map=CreatMapFromExcel(filePath);
				}
				else if (currentPlatform.equalsIgnoreCase("mac"))
				{
					FrameWorkLogger.info("FrameWork", "loading PropertyMapCopy.xls");
					filePath=currentDir+"/FrameWork/LookUp/OR/PropertyMapcopy.xls";
					Map=CreatMapFromExcel(filePath);
				}
			}

			//selenium related maps
			else if(MapName.trim().equalsIgnoreCase("WebElementPropertyMap"))
			{
				if (currentPlatform.equalsIgnoreCase("window"))
				{
					FrameWorkLogger.info("FrameWork", "loading WFM_ElementPropertyMap.xls");
					filePath=currentDir+"\\FrameWork\\LookUp\\OR\\WebElementPropertyMap.xls";
					Map=CreatMapFromExcel(filePath);
				}
				else if (currentPlatform.equalsIgnoreCase("mac"))
				{
					FrameWorkLogger.info("FrameWork", "loading WFM_ElementPropertyMap.xls");
					filePath=currentDir+"/FrameWork/LookUp/OR/WebElementPropertyMap.xls";
					Map=CreatMapFromExcel(filePath);
				}
			}
			else if(MapName.trim().equalsIgnoreCase("KvsNamesList"))
			{
				if (currentPlatform.equalsIgnoreCase("window"))
				{
					FrameWorkLogger.info("FrameWork", "loading KVSMap.xlsx");
					filePath=currentDir+"\\FrameWork\\LookUp\\OR\\KVSMap.xlsx";
					Workbook WorkBook=getWorkbook(filePath);
					Map=CreatMapFromSheet(WorkBook,"KvsList");
				}
				else if (currentPlatform.equalsIgnoreCase("mac"))
				{
					FrameWorkLogger.info("FrameWork", "loading KVSMap.xls");
					filePath=currentDir+"/FrameWork/LookUp/OR/KVSMap.xls";
					Workbook WorkBook=getWorkbook(filePath);
					Map=CreatMapFromSheet(WorkBook,"KvsList");
				}
			}

			//WFM property map
			else if(MapName.trim().equalsIgnoreCase("WFM_ElementPropertyMap"))
			{
				if (currentPlatform.equalsIgnoreCase("window"))
				{
					FrameWorkLogger.info("FrameWork", "loading WFM_ElementPropertyMap.xlsx");
					filePath=currentDir+"\\FrameWork\\LookUp\\OR\\WFM_ObjectMap.xlsx";
					Map=CreatMapFromExcel(filePath);
				}
				else if (currentPlatform.equalsIgnoreCase("mac"))
				{
					FrameWorkLogger.info("FrameWork", "loading WFM_ElementPropertyMap.xls");
					filePath=currentDir+"/FrameWork/LookUp/OR/WFM_ObjectMap.xls";
					Map=CreatMapFromExcel(filePath);
				}

			}

			//appium property map
			else if(MapName.trim().equalsIgnoreCase("AppiumObjectList"))
			{
				if (currentPlatform.equalsIgnoreCase("window"))
				{
					FrameWorkLogger.info("FrameWork", "loading AppiumObjectMap.xlsx");
					filePath=currentDir+"\\FrameWork\\LookUp\\OR\\AppiumObjectMap.xlsx";
					Map=CreatMapFromExcel(filePath);
				}
				else if (currentPlatform.equalsIgnoreCase("mac"))
				{
					FrameWorkLogger.info("FrameWork", "loading AppiumObjectMapcopyNew.xls");
					filePath=currentDir+"/FrameWork/LookUp/OR/AppiumObjectMapcopyNew.xls";
					Map=CreatMapFromExcel(filePath);
				}
			}

			return Map;
		} catch (Exception e) {

			FrameWorkLogger.error("FrameWork", e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	//method converts excel cell data into list of maps
	public static List<Map<String,String>> CreatMapFromSheet (Workbook WorkBook,String SheetName) // fetches and stores excel map in to java collection List from Sheet variable of any provided workbook
	{

		FrameWorkLogger.info("FrameWork", "CreatMapFromSheet().....Workbook ref:-"+WorkBook.toString()+",Sheet Name:-"+SheetName);
		List<Map<String,String>> ListOfMap=new ArrayList<Map<String,String>>();
		ArrayList<String> KeyList=new ArrayList<String>();

		Sheet CurrentSheet=WorkBook.getSheet(SheetName.trim());
		KeyList=creatKeys(CurrentSheet);
		int NoOfKeys=KeyList.size();

		int LastRow=CurrentSheet.getPhysicalNumberOfRows();

		try{

			for(int CurrentRowNo=1;CurrentRowNo<LastRow;CurrentRowNo++) // loop iterates over each cell of excel sheet till end of rows
			{
				try {
					Row CurrentRow=CurrentSheet.getRow(CurrentRowNo);
					int Counter=0;
					int LastCellNo=CurrentRow.getLastCellNum();

					for(int Index=0; Index<=LastCellNo;Index++)
					{
						Cell cell=CurrentRow.getCell(Index);

						if(cell==null || cell.getCellType()==XSSFCell.CELL_TYPE_BLANK)
						{
							Counter++;
						}

					}

					if(Counter==CurrentRow.getLastCellNum())
					{
						continue;
					}

					else
					{
						int KeyCounter=0;
						Map<String,String> Map=new HashMap<String,String>();
						while(KeyCounter<NoOfKeys)
						{
							Cell cell=CurrentRow.getCell(KeyCounter);
							String strCellValue="";


							if(cell==null)
							{
								strCellValue="";
							}
							else if(cell.getCellType()==XSSFCell.CELL_TYPE_NUMERIC) //convert numerics into the string
							{
								double dblCellValue=cell.getNumericCellValue();
								strCellValue=Double.toString(dblCellValue).trim();
								int IndexOfDecimal = strCellValue.lastIndexOf(".");
								strCellValue=strCellValue.substring(0, strCellValue.lastIndexOf("."));
								String AfterDecimalNumber = strCellValue.substring(IndexOfDecimal,strCellValue.length());
								if(AfterDecimalNumber.equals(".0"))
								{
									strCellValue=strCellValue.substring(0, strCellValue.lastIndexOf("."));
								}
							}
							else if(cell.getCellType()==XSSFCell.CELL_TYPE_STRING)
							{
								strCellValue=cell.getStringCellValue().trim();
							}
							else if(cell.getCellType()==XSSFCell.CELL_TYPE_FORMULA) //convert cell data having excel formulae into the string
							{
								FormulaEvaluator evaluator = WorkBook.getCreationHelper().createFormulaEvaluator();

								try {
									if(DateUtil.isCellInternalDateFormatted(cell))
									{
										System.out.println("date");
										Date date = cell.getDateCellValue();
										DateFormat TimeFormat = new SimpleDateFormat("HH:mm");
										strCellValue = TimeFormat.format(date);
									}
									else
									{
										strCellValue=evaluator.evaluate(cell).formatAsString().toString().replaceAll("\"","" ).trim();
									}
								} catch (Exception e) {
									// TODO Auto-generated catch block
									strCellValue=evaluator.evaluate(cell).formatAsString().toString().replaceAll("\"","" ).trim();
								}
							}
							else if(cell.getCellType()==XSSFCell.CELL_TYPE_BLANK) // if cell data is blank store empty string in string variable
							{
								strCellValue="";
							}

							Map.put(KeyList.get(KeyCounter), strCellValue);
							KeyCounter++;
						}

						ListOfMap.add(Map); // add a map created to the List
					}
				} catch (Exception e) {
					FrameWorkLogger.error("FrameWork","FrameWork...CreatMapFromSheet().....Workbook ref:-"+WorkBook.toString()+",Sheet Name:-"+SheetName+","+e.getMessage());
					continue;
				}
			}
			return ListOfMap;
		}
		catch(Exception e)
		{
			FrameWorkLogger.error("FrameWork","FrameWork...CreatMapFromSheet().....Workbook ref:-"+WorkBook.toString()+",Sheet Name:-"+SheetName+","+e.getMessage());
			return null;
		}
	}


	//method converts excel cell data into list of maps
	public static List<Map<String,String>> CreatMapFromExcel(String FilePath) // method creates list of maps from path to the excel file provided
	{
		FrameWorkLogger.info("FrameWork", "CreatMapFromSheet().....Workbook ref:-"+WorkBook.toString()+",file path:-"+FilePath);

		List<Map<String,String>> ListOfMap=new ArrayList<Map<String,String>>();
		ArrayList<String> KeyList=new ArrayList<String>();
		Workbook workBoook=getWorkbook(FilePath.trim());

		int NoOfSheets=workBoook.getNumberOfSheets();

		for(int SheetNo=0;SheetNo<NoOfSheets;SheetNo++)// loop iterates over no of sheets present in excel file
		{
			Sheet CurrentSheet=workBoook.getSheetAt(SheetNo);
			KeyList=creatKeys(CurrentSheet);
			int NoOfKeys=KeyList.size();
			int LastRow=CurrentSheet.getLastRowNum();

			try{
				for(int CurrentRowNo=1;CurrentRowNo<=LastRow;CurrentRowNo++)// loop iterates over each cell of a excel sheet till end of rows
				{
					try {
						Row CurrentRow=CurrentSheet.getRow(CurrentRowNo);
						int LastColumnOfCurrnetRow=CurrentRow.getLastCellNum();
						if(LastColumnOfCurrnetRow<=NoOfKeys)
						{
							int KeyCounter=0;
							Map<String,String> Map=new HashMap<String,String>();
							while(KeyCounter<NoOfKeys)
							{
								Cell cell=CurrentRow.getCell(KeyCounter);
								String strCellValue="";
								if(cell==null)
								{
									strCellValue=" ";
								}
								else
								{
									if(cell.getCellType()==XSSFCell.CELL_TYPE_NUMERIC)
									{
										double dblCellValue=cell.getNumericCellValue();
										strCellValue=Double.toString(dblCellValue).trim();

										int IndexOfDecimal = strCellValue.lastIndexOf(".");
										String AfterDecimalNumber = strCellValue.substring(IndexOfDecimal,strCellValue.length());
										if(AfterDecimalNumber.equals(".0"))
										{
											strCellValue=strCellValue.substring(0, strCellValue.lastIndexOf("."));
										}

									}
									else if(cell.getCellType()==XSSFCell.CELL_TYPE_STRING)
									{
										strCellValue=cell.getStringCellValue().trim();
									}
									else if(cell.getCellType()==XSSFCell.CELL_TYPE_FORMULA) //convert cell data having excel formulae into the string
									{
										FormulaEvaluator evaluator = WorkBook.getCreationHelper().createFormulaEvaluator();

										try {
											if(DateUtil.isCellInternalDateFormatted(cell))
											{
												System.out.println("date");
												Date date = cell.getDateCellValue();
												DateFormat TimeFormat = new SimpleDateFormat("HH:mm");
												strCellValue = TimeFormat.format(date);
											}
											else
											{
												strCellValue=evaluator.evaluate(cell).formatAsString().toString().replaceAll("\"","" ).trim();
											}
										} catch (Exception e) {
											// TODO Auto-generated catch block
											strCellValue=evaluator.evaluate(cell).formatAsString().toString().replaceAll("\"","" ).trim();
										}
									}
									else if(cell.getCellType()==XSSFCell.CELL_TYPE_BLANK)
									{
										strCellValue="";
									}
								}

								Map.put(KeyList.get(KeyCounter), strCellValue);
								KeyCounter++;
							}
							ListOfMap.add(Map);
						}
					} catch (Exception e) {
						FrameWorkLogger.error("Framework","CreatMapFromSheet().....Workbook ref:-"+WorkBook.toString()+",file path:-"+FilePath+","+e.getMessage());
						continue;
					}
				}
			}
			catch(IllegalStateException | NullPointerException e)
			{
				FrameWorkLogger.error("Framework","CreatMapFromSheet().....Workbook ref:-"+WorkBook.toString()+",file path:-"+FilePath+","+e.getMessage());
				continue;
			}
		}
		return ListOfMap;
	}


	/* starts test case execution */
	private void readTestSet()
	{
		try {

			FrameWorkLogger.info("TestCase", "Reading test set");
			List<Map<String,String>> TestSetMapList=new ArrayList<Map<String,String>>();
			List<Map<String,String>> SelectedTestCases=new ArrayList<Map<String,String>>();
			TestSetMapList=CreatMapFromSheet(WorkBook,"BATCH"); // creating list of maps from sheet "batch" from global workbook of "TestSet.xls" file and storing it into a local collection variable

			for(Map<String,String>map:TestSetMapList) // loop over "TestSetMapList"
			{
				String SelectFlagStatus = map.get("SELECT").trim();
				if(SelectFlagStatus.equalsIgnoreCase("YES"))
				{
					SelectedTestCases.add(map);
				}
			}

			if(SelectedTestCases.size()>0)
			{
				for(Map<String,String>map:SelectedTestCases)
				{
					//start point to execution
					try {
						TestCaseName=map.get("TESTCASE NAME").toString().trim();
						String TestCasefileLoc=map.get("TESTCASE FILE NAME").toString().trim();
						Technology=map.get("Technology").toString().trim();
						TestCaseNumber = map.get("Test Case Number").toString().trim();
						Application = map.get("Application").toString().trim();
						Market = map.get("Market").toString().trim(); // Reporting purpose
						String NoOfRuns= map.get("No. Of Runs").trim(); // loads
						gblNoOfRuns = NoOfRuns;
						long intNoOfRuns = 1;
						try{
							intNoOfRuns = Integer.parseInt(NoOfRuns);
						}
						catch(NumberFormatException e)
						{
							intNoOfRuns = 1;
						}

						initializeReport();//initializing reporting events
						if(intNoOfRuns>=0)
						{
							for(int Index = 1; Index<=intNoOfRuns ; Index++)
							{
								runTestCase(TestCaseName,TestCasefileLoc,Technology); // method to run a testcase at a time

								//reinitializing failure and passed counters for new test case
								TestCaseFailureCounter=0;
								TestCasePassedCounter=0;

								// setting for new iteration
								HtmlResult.TestStepNumber="0";
							}
						}
						else// infinite iterations
						{
							while(true)
							{
								runTestCase(TestCaseName,TestCasefileLoc,Technology); // method to run a testcase at a time

								//reinitializing failure and passed counters for new test case
								TestCaseFailureCounter=0;
								TestCasePassedCounter=0;

								// setting for new iteration
								HtmlResult.TestStepNumber="0";
							}
						}

						//reinitializing failure and passed counters for new test case
						TestCaseFailureCounter=0;
						TestCasePassedCounter=0;

						// setting for new iteration
						HtmlResult.TestStepNumber="0";
					}
					catch(NumberFormatException e)
					{
						new HtmlResult();
						System.err.println("No Of Runs column for testcase '"+TestCaseName+"' is mentioned as-'"+gblNoOfRuns+"'");
						HtmlResult.failed("", "", "No Of Runs column for testcase '"+TestCaseName+"' is mentioned as-'"+gblNoOfRuns+"'");
						continue;
					}
					catch (Exception e) {

						new HtmlResult();
						System.err.println("Execution select flag is not selected for any of the Test set. It should be 'YES' to execute Test set so logging failed  ");
						HtmlResult.failed("", "", "Execution select flag is not selected for any of the Test set. It should be 'YES' to execute Test set so logging failed &nbsp");
						continue;
					}
				}
			}
			else
			{
				new HtmlResult();
				System.err.println("Execution select flag is not selected for any of the Test set. It should be 'YES' to execute Test set so logging failed  ");
				HtmlResult.failed("", "", "Execution select flag is not selected for any of the Test set. It should be 'YES' to execute Test set so logging failed &nbsp");

			}

		} catch (Exception e) {
			FrameWorkLogger.error("TestCase", "error in reading test set-"+e.getMessage());
		}
	}
	public static void startEggDriveProcess() {
		try {
			stopEggDriverProcess();
			Runtime rt = Runtime.getRuntime();

			try {
				FrameWorkLogger.info("TestCase", "Starting eggDrive Process");
				rt.exec("taskkill /f /im conhost.exe");
				Thread.sleep(5000);
				rt.exec("taskkill /f /im Eggplant.exe");
				String batFileName = "StartEggDrive.bat";
				rt.exec("cmd /c start " + batFileName);
				Thread.sleep(5000);
				rt.runFinalization();
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				FrameWorkLogger.info("TestCase", "error while Starting eggDrive Process,"+e.getMessage());

			}

		} catch (Exception e) {
			FrameWorkLogger.info("TestCase", "error while Starting eggDrive Process,"+e.getMessage());
		}
	}
	public static void stopEggDriverProcess(){
		try {
			FrameWorkLogger.info("TestCase", "Stoping eggDrive Process");
			Runtime rt = Runtime.getRuntime();

			try {
				Thread.sleep(5000);
				rt.exec("taskkill /f /im conhost.exe");
				Thread.sleep(5000);
				rt.exec("taskkill /f /im Eggplant.exe");
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				FrameWorkLogger.error("TestCase", "error while Stoping eggDrive Process,"+e.getMessage());

			}
		} catch (IOException e) {
			FrameWorkLogger.error("TestCase", "error while Stoping eggDrive Process,"+e.getMessage());

		}
	}

	private void startRemoteNode() {
		try {
			FrameWorkLogger.info("TestCase", "Starting remote node");

			Runtime rt = Runtime.getRuntime();
			try {
				rt.exec("taskkill /f /im conhost.exe");
				Thread.sleep(5000);
				rt.exec("taskkill /f /im cmd.exe");
				String batFilePath = "Node.bat";
				rt.exec("cmd /c start "+batFilePath);
				Thread.sleep(5000);
				rt.runFinalization();
				Thread.sleep(5000);
			}
			catch (InterruptedException e)
			{
				FrameWorkLogger.error("TestCase", "error while Starting remote node, "+e.getMessage());
			}

		} catch (IOException e) {
			FrameWorkLogger.error("TestCase", "error while Starting remote node, "+e.getMessage());
			e.printStackTrace();
		}
	}

	public boolean startAppiumServer() throws MalformedURLException
	{
		try
		{
			//Appium.StopAppiumServer();
			Appium.StartAppiumAndLaunchApp();
			return true;
		}
		catch (Exception e)
		{
			System.err.println("Not able to run appium server-"+e.getMessage());
			return false;
		}
	}

	public void initializeReport()
	{
		new HtmlResult();
	}
	
	public List<List<Map<String,String>>> setRunScriptFlagForTestCases(List<List<Map<String,String>>> OldTestCases)
	{
		List<List<Map<String,String>>> NewTestCases = new ArrayList<List<Map<String,String>>>();
		String RunScriptFlagValue = "";
		try
		{
			for(List<Map<String,String>> CurrentScript : OldTestCases)
			{ 
				int SizeOfList = CurrentScript.size();
				String RunScriptFlag = "";
				for(int Index = 0;Index< SizeOfList;Index++)
				{
					RunScriptFlag = CurrentScript.get(Index).get("Run Script Flag");
					if(RunScriptFlag!=null)
					{
						RunScriptFlagValue = RunScriptFlag;
						break;
					}
				}
				
				for(int Index = 0;Index< SizeOfList;Index++)
				{
					if(RunScriptFlagValue!="")
					{
						CurrentScript.get(Index).put("Run Script Flag",RunScriptFlagValue);
					}
				}
				
				NewTestCases.add(CurrentScript);
			}
		
		}
		catch(Exception e)
		{
			return null;
		}
		return NewTestCases;
	}
	
	public List<List<Map<String,String>>> setScriptDescriptionForTestCases(List<List<Map<String,String>>> OldTestCases)
	{
		List<List<Map<String,String>>> NewTestCases = new ArrayList<List<Map<String,String>>>();
		String RunScriptFlagValue = "";
		try
		{
			for(List<Map<String,String>> CurrentScript : OldTestCases)
			{ 
				int SizeOfList = CurrentScript.size();
				String ScriptDescription = "";
				for(int Index = 0;Index< SizeOfList;Index++)
				{
					ScriptDescription = CurrentScript.get(Index).get("Script Description");
					if(ScriptDescription!=null)
					{
						RunScriptFlagValue = ScriptDescription;
						break;
					}
				}
				
				for(int Index = 0;Index< SizeOfList;Index++)
				{
					if(RunScriptFlagValue!="")
					{
						CurrentScript.get(Index).put("Script Description",RunScriptFlagValue);
					}
				}
				
				NewTestCases.add(CurrentScript);
			}
		
		}
		catch(Exception e)
		{
			return null;
		}
		return NewTestCases;
	}
	
	
	public List<List<Map<String,String>>> filterTestScriptsAsRunFlag(List<List<Map<String,String>>> OldTestCase)
	{
		List<List<Map<String,String>>> NewTestCases = new ArrayList<List<Map<String,String>>>();
		try
		{
			for(List<Map<String,String>> CurrentScript : OldTestCase)
			{
				for(Map<String,String>CurrentRow : CurrentScript)
				{
					String ScriptFlag = CurrentRow.get("Run Script Flag");
					if(ScriptFlag.equalsIgnoreCase("Y"))
					{
						NewTestCases.add(CurrentScript);
						break;
					}		
				}
			}
		}
		catch(Exception e)
		{
			return null;
		}
		return NewTestCases;
	}

	public ArrayList<String> getScriptNumbers(List<List<Map<String,String>>> TestCases)
	{
		ArrayList <String> ScriptNoList = new ArrayList<String>();
		Set<String> ScriptNoSet = new HashSet<String>();
		try
		{
		
			for(List<Map<String,String>> CurrentTestScript : TestCases)
			{
				for(Map<String,String> CurrentRow : CurrentTestScript)
				{
					try {
						String CurrentScriptNumber = CurrentRow.get("Script").trim();
						if(CurrentScriptNumber!="")
						{
							ScriptNoList.add(CurrentScriptNumber);
						}
					} catch (Exception e) {
						continue;
					}
				}
				
				if(!ScriptNoList.isEmpty())
				{
					ScriptNoSet.addAll(ScriptNoList);
					ScriptNoList.clear();
					ScriptNoList.addAll(ScriptNoSet);
				}
			}
		}
		catch(Exception e)
		{
			return null;
		}
		return ScriptNoList;
	}
	
	
	
	private void runTestCase(String TestCaseName,String TestCasefileName,String Technology) throws 
IOException
	{

		String CurrentDir=System.getProperty("user.dir");
		//String filePath=CurrentDir+"\\FrameWork\\TestCases\\"+TestCasefileName;
		String filePath=CurrentDir+"/FrameWork/TestCases/"+TestCasefileName;

		Workbook WorkBook=getWorkbook(filePath.trim());// get workbook variable of testcase file name

		List<Map<String, String>> TestCaseMap=CreatMapFromSheet(WorkBook,"Script");// create list of maps from Sheet "Script" of local workbook variable

		List<List<Map<String,String>>> TestCases=new ArrayList<List<Map<String,String>>>();// list of testcases [{1},{2}......]

		IterationTestDataMap=CreatMapFromSheet(WorkBook,"Test Data"); // create list of maps from Sheet "Test Data" of local workbook variable

		//HtmlResult.ReportTestCaseInfo(); // generate test case info template
		HtmlResult.CreateTableLayOut(); // create table layouts for html reports

		// depending on technology start respective process through CMD
		if(Technology.equalsIgnoreCase("Appium")||Technology.equalsIgnoreCase("AppiumMobile")
				||Technology.equalsIgnoreCase("Eggium"))
		{
			TestCaseRunner.Technology=Technology;
			//startRemoteNode(); // from remote webdriver node should be running
			if(startAppiumServer())
			{
					System.out.println("Appium server will start soon");
			}
		

		}

		if(Technology.equalsIgnoreCase("Eggplant")||Technology.equalsIgnoreCase("EggWeb")||
				Technology.equalsIgnoreCase("EggRemote"))
		{
			TestCaseRunner.Technology=Technology;
			startEggDriveProcess(); // for eggplant tool
		}

		if(Technology.equalsIgnoreCase("RemoteWebDriver")||Technology.equalsIgnoreCase("EggRemote"))
		{
			TestCaseRunner.Technology=Technology;
			startRemoteNode(); // from remote webdriver node should be running
		}


		// making list of total no of scripts to be run ,Script No.1,2,3.....
		ArrayList<String> ScriptNoList=new ArrayList<String>();
		String PreviousScriptNo="0";
		for(Map map:TestCaseMap)
		{
			try {
				String CurrentScriptNo=(String)map.get("Script");
				if(!PreviousScriptNo.equals(CurrentScriptNo))
				{
					ScriptNoList.add(CurrentScriptNo);
					PreviousScriptNo=CurrentScriptNo;
				}
			} catch (Exception e) {
				continue;
			}
		}

		for(String TestScriptNo:ScriptNoList)
		{
			if (!(TestScriptNo==null || TestScriptNo.equals(""))) {
				ArrayList<Map<String, String>> ScriptList;
				try {
					ScriptList = new ArrayList<Map<String, String>>();
					for (Map m : TestCaseMap) {
						String CurrentScriptNo = (String) m.get("Script");

						if(CurrentScriptNo==null || CurrentScriptNo.equals("") || CurrentScriptNo=="" || CurrentScriptNo.equals(null))
						{
							;
						}
						else if (CurrentScriptNo.equals(TestScriptNo)) {
							ScriptList.add(m);// creating a list of current scripts steps
						}
					}

					TestCases.add(ScriptList);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					continue;
				}
			}

			//create separate lists of testcases for each script no an add it into a parent list
		}
		
		//setting run script flag
		List<List<Map<String,String>>>ArrangedTestCases = setRunScriptFlagForTestCases(TestCases);
		ArrangedTestCases = setScriptDescriptionForTestCases(TestCases);
		if(ArrangedTestCases!=null && ArrangedTestCases.size()>=1)
		{
			//removing testcases with RunTestScriptFlag has N value
			TestCases = filterTestScriptsAsRunFlag(ArrangedTestCases);
			ScriptNoList = getScriptNumbers(TestCases);
		}
		
		// removing null lists and maps from main test case list
		for(List<Map<String, String>> list:TestCases)
		{
			for(Map map:list)
			{
				if(map.isEmpty())
				{
					list.remove(map);
				}
			}
			if(list.isEmpty())
			{
				TestCases.remove(list);
			}
		}

		// running components from each test case based on their script number
		ScriptNoList  = sortList(ScriptNoList);
		int Size=TestCases.size();

		for(int Index=0;Index<Size;Index++)
		{
			boolean PerformRecovery = false;
			boolean RunOnce = true;
			TestCaseLogger.info("testcase", "******************************************************************Start of Test*****************************************************************");

			List<Map<String, String>> TestCaseScript=TestCases.get(Index);// script no wise test case 1st looping for script no 1
			
			do
			{

				List<Map<String,String>> TempTestCase = TestCases.get(Index);
				Map<String,String> TempTestCaseMap = TempTestCase.get(0); 

				ScriptDescription = TempTestCaseMap.get("Script Description");
			}while(!RunOnce);
			
			
			TestScriptNumber=ScriptNoList.get(Index); // test script no
			
			List<Map<String, String>> ScriptNoTypeItrData=getIterationData(TestScriptNumber);// get Iteration Data for current script no and store it into local list of maps

			List<String> IterationNumberList=new ArrayList<String>();

			if(ScriptNoTypeItrData.size()>0)
			{
				for(Map map:ScriptNoTypeItrData)
				{
					IterationNumberList.add((String)map.get("Iteration Number")); // creating a list of overall iteration numbers from above created Iteration data list of maps
				}
			}
			else
			{
				Map<String,String> WithOutItrData=new HashMap<>();
				WithOutItrData.put("Iteration Number", "1");
				ScriptNoTypeItrData.add(WithOutItrData);// if no any iteration data present for a testcase then it should run atleast once hence creating dummy and flushing it immediately after use later
				IterationNumberList.add((String)WithOutItrData.get("Iteration Number"));
			}
			
			HtmlResult.addLineSeparator("Green","Start of Script Number:"+TestScriptNumber+" - ' "+ScriptDescription+" '"); // line separator in reports for each iteration
		
			//for reporting
			blnTestCaseFailed = false;
			blnComponentFalied = false;

			for(String IterationNumber:IterationNumberList)
			{
				Date StartOfIterationTime = new Date();
				EcexutionCounter ++;
				//for reporting
				TotalTestCaseIteration = IterationNumberList.size();
				GlobalIterationNumber =IterationNumber;

				//for reporting
				HtmlResult.TestStepNumber="0";
				HtmlResult.ComponentName="";

				if(IterationNumberList.size()>=1)
				{
					if(IterationNumber!=null)
					{
						HtmlResult.addLineSeparator("Blue","Starting Iteration number:"+IterationNumber);
					}
				}

				for(Map<String,String> TestCaseRow:TestCaseScript) // loop over each test case row (1 row represents 1 map, multiple such rows in a testcase represents- 1 list of multipe maps, List of such test cases is a parent list for execution)
				{
					
					PerformRecovery = false;
					TestStepNumber = TestCaseRow.get("Step");
					TestScriptNumber=TestCaseRow.get("Script");
					String ComponentName=TestCaseRow.get("Component");
					ComponentCode=ComponentName;
					String Parameters=TestCaseRow.get("Parameters");
					String RunFlag=TestCaseRow.get("Run Component Flag");
					strAbortTestCase=TestCaseRow.get("Abort");
					
					try {

						String ModifiedParameters="";

						if(RunFlag.equalsIgnoreCase("y"))
						{
							if(Parameters.contains("GBL#") || Parameters.contains("ITR#"))
							{
								String[] ArrStrParams=Parameters.split(",");

								for(String Parameter:ArrStrParams)
								{
									if(!Parameter.isEmpty())
									{
										Parameter=Parameter.trim();
										if(Parameter.contains("GBL#"))
										{
											String FieldParameters=getGlobleParameters(Parameter);
											if(!FieldParameters.isEmpty())
											{
												ModifiedParameters=ModifiedParameters.concat(FieldParameters.concat(","));
											}
											else
											{
												// log error parameter is not present in global map
												TestCaseLogger.error("testcase", "Parameter-' "+Parameter+" ' not present in PropertyMap.xls");
											}
										}
										else if(Parameter.contains("ITR#"))
										{

											if(ScriptNoTypeItrData.size()>=1)
											{
												Map<String,String> CurrentIterationTestData=getCurrentIterationData(IterationNumber, ScriptNoTypeItrData);
												if(CurrentIterationTestData.size()>0)
												{
													String ColumnName=Parameter.substring(Parameter.lastIndexOf("#")+1).trim();

													if(ColumnName.isEmpty())
													{
														ColumnName=Parameter;
													}

													String ItrValue=CurrentIterationTestData.get(ColumnName);

													if(ItrValue!=null)
													{
														String ParameterNameAndValue=Parameter.substring(0,Parameter.lastIndexOf("=")).trim().concat("="+ItrValue+",").trim();
														ModifiedParameters=ModifiedParameters.concat(ParameterNameAndValue);
													}
													else
													{
														HtmlResult.addMessage("For component Name <font color=\"Brown\">'"+ComponentName+"'</font>, Test data for Script no. "+TestScriptNumber+", Iteration Number-'"+IterationNumber+"', Data for column name-<font color=\"Brown\">'"+ColumnName+"'</font> in 'Test Data' sheet is not present");
														TestCaseLogger.error("testcase", "No column name-'"+ColumnName+" ', found in 'Test Data Sheet' for Parameter-"+Parameter.substring(0,Parameter.lastIndexOf("=")).trim());
													}
												}
												else
												{
													// no iteration data found for iteration no in test data sheet
													TestCaseLogger.error("testcase", "For component-'"+ComponentName+"'No iteration data found for Iteration number-'"+IterationNumber+"' in test data sheet");
												}
											}
											else
											{
												;// error Test data for script no is less ;
											}
										}
										else
										{
											ModifiedParameters=ModifiedParameters.concat(Parameter.trim()+",");
										}
									}
									// if parameter is empty
									else
									{
										Parameter="";
										ModifiedParameters=Parameter;
									}
								}


								if(!(ModifiedParameters==null ||ModifiedParameters.equals(null) ||ModifiedParameters.equals("") ||ModifiedParameters==""))
								{
									ModifiedParameters=ModifiedParameters.substring(0,ModifiedParameters.lastIndexOf(","));
									Driver.invokeMethod(ComponentName,ModifiedParameters);
								}
								else
								{
									// parameters not available in test data sheet for (give all the details about componenet)
									TestCaseLogger.error("testcase", "For component-' "+ComponentName+" 'of Script no-' "+TestScriptNumber+" ' with itration number-' "+IterationNumber+" ', Parameters-' "+Parameters+" ' not present in test data sheet");
								}
							}
							else
							{
								Driver.invokeMethod(ComponentName,Parameters);
							}
						}
					}
					catch(Exception e)
					{
						TestCaseLogger.error("testcase", "For component-' "+ComponentName+" 'of Script no-' "+TestScriptNumber+" ' with itration number-' "+IterationNumber+" ', Parameters-' "+Parameters+" ' causing error-' "+e.getMessage()+" '");
						continue;
					}

					// ABORT TEST CASE DEPENDING ON ABORT FLAG
					if(blnComponentFalied==true )
					{
						blnTestCaseFailed = true;
						blnComponentFalied = false;
						if(strAbortTestCase.equalsIgnoreCase("y"))
						{
							PerformRecovery = true;
							break;
						}
					}
				}

				//for counting how many testcases failed and passed during execution
				if( blnTestCaseFailed==true )
				{
					TestCaseFailureCounter++;
					blnTestCaseFailed = false;
				}
				else
				{
					TestCasePassedCounter++;
					blnTestCasePassed = false;
				}

				if(PerformRecovery)
				{
					if(Technology.equalsIgnoreCase("eggplant"))
					{
						PerformRecovery = false;
						callEggPlantRecovery();
					}
				}

				//average time per iteration


				//consolidated Reports Updations
				ConsolidateTestReport.writeToFile();

				//timing variables
				Date EndOfIteration = new Date();
				String Duration = HtmlResult.getTimeDuration(StartOfIterationTime, EndOfIteration);
				String[] HrsMinsSecs = Duration.split(":");

				//to separate iterations in report
				if(IterationNumberList.size()>=1)
				{
					HtmlResult.addLineSeparator("Blue","End of Iteration number:"+IterationNumber+"<font color=purple>  :::   Total time of execution:-"+HrsMinsSecs[0]+" hrs :"+HrsMinsSecs[1]+" mins :"+HrsMinsSecs[2]+" sec </font>");
				}

				//update average time per iteration
				updateAverageTimePerIteration();

			}

			// to separate scripts in report
			HtmlResult.addLineSeparator("Green","End of Script Number:"+TestScriptNumber);
		}

		HtmlResult.closeHtmlTag();

		TestCaseLogger.info("testcase", "******************************************************************End of Test*****************************************************************");
	}
	
	public ArrayList<String> sortList(ArrayList<String> UnsortedList)
	{
		List<Integer> IntSortedList = new ArrayList<Integer>();
		List<String> StrSortedList = new ArrayList<String>();
		
		for(String StrNumber : UnsortedList)
		{
			IntSortedList.add(Integer.parseInt(StrNumber));
		}
		
		Collections.sort(IntSortedList);
		
		for(int IntNumber : IntSortedList)
		{
			StrSortedList.add(Integer.toString(IntNumber));
		}
		
		return (ArrayList<String>) StrSortedList;
	}
	

	public void updateAverageTimePerIteration()
	{
		HtmlResult.addAverageTimePerIteration();
	}

	public void callEggPlantRecovery()
	{
		EggPlant.eggUIDriver.disconnect();
	}

	public void callWebdriverRecovery()
	{
		WebUIDriver.driver.quit();
	}


	private static List<Map<String,String>> getIterationData(String TestCaseScriptNo)
	{
		List<Map<String,String>> ItrData= new ArrayList<Map<String,String>>();

		for(Map map: IterationTestDataMap)
		{
			try {
				String TestDataScriptNumber=(String)map.get("Script No");
				if(TestDataScriptNumber.trim().equals(TestCaseScriptNo.trim()))
				{
					if(map.get("Select Flag").toString().equalsIgnoreCase("Y"))
					{
						ItrData.add(map);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				continue;
			}
		}
		return ItrData;
	}


	private static Map<String,String> getCurrentIterationData(String IterationNumber,List<Map<String,String>> ScriptedMap)
	{
		Map<String,String> CurrnetIterationMap=new HashMap<String,String>();

		for(Map map:ScriptedMap)
		{
			try {
				if(map.get("Select Flag").toString().equalsIgnoreCase("Y"))
				{
					String LocalIterationNumber=(String)map.get("Iteration Number");
					if(LocalIterationNumber.trim().equals(IterationNumber.trim()))
					{
						CurrnetIterationMap=map;
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				continue;
			}
		}
		return CurrnetIterationMap;
	}

	private static String getGlobleParameters(String Parameters)
	{
		String Params="";

		String[] ArrParameters=Parameters.split(",");

		for(String Param:ArrParameters)
		{
			if(Param.contains("GBL#"))
			{
				String GlobalProperty=Param.substring(Param.lastIndexOf("#")+1).trim();
				String ReplacableString=Param.substring(Param.lastIndexOf("GBL"));
				String GloableValue="";
				for(Map map:ConfigurationMap)
				{
					String Key=(String)map.get("Key");
					if(Key.equals(GlobalProperty))
					{
						GloableValue=(String)map.get("Value");
						break;
					}
				}

				Params=Params.concat(Param.replace(ReplacableString, GloableValue).concat(","));
			}
		}
		Params=Params.substring(0,Params.lastIndexOf(","));
		return Params;
	}


	private static ArrayList<String> creatKeys(Sheet Sheet)
	{
		int rowStart=Sheet.getFirstRowNum();
		Row CurrentRow= Sheet.getRow(rowStart);
		int LastColumnIndexOfCurrentRow=CurrentRow.getLastCellNum();

		ArrayList<String> KeyList=new ArrayList<String>();

		for(int ColumnNo=0;ColumnNo<LastColumnIndexOfCurrentRow;ColumnNo++)
		{
			try {
				if(CurrentRow==null)
				{
					break;
				}

				Cell cell=CurrentRow.getCell(ColumnNo);
				String Key=cell.getStringCellValue().trim();
				KeyList.add(Key);
			} catch (Exception e) {
				continue;
			}
		}

		return KeyList;
	}

	public static Workbook getWorkbook(String fileNamePath)
	{
		Workbook newWorkbook = null;
		try
		{
			File file = new File(fileNamePath);
			FileInputStream inputStream = new FileInputStream(file);
			String FileName=file.getName();
			String fileExtensionName = FileName.substring(FileName.indexOf("."));

			if (fileExtensionName.equals(".xlsx"))
			{
				OPCPackage pkg = OPCPackage.open(fileNamePath);// need to study
				newWorkbook = new XSSFWorkbook(pkg);
			} else if (fileExtensionName.equals(".xls"))
			{
				newWorkbook = new HSSFWorkbook(inputStream);
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		catch (InvalidFormatException e) {
			e.printStackTrace();
		}
		return newWorkbook;
	}

}
