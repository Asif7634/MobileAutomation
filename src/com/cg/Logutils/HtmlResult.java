package com.cg.Logutils;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.cg.Application.POS;
import com.cg.TestCaseRunner.TestCaseRunner;



public class HtmlResult {

	static String TestCaseID;
	public static String TestStepNumber="0";
	static String TestCaseName;
	public static String ComponentName="";
	public static StringBuilder htmlStringBuilder;
	public static String Duration;
	public static Date StartDuration;
	public static Date EndDuration;
	public static String Status="";
	public static String ResultFolderName="";
	public static Logging ResultLogger;
	public static String EndTimeEndDuration;
	public static String LastModifiedPrintReport = "";
	public static String LastDuration = "0:0:0";
	static String LastEndTime = "`";
	static String LastTestCaseFileName = "";
	static String ExecutionStatus = "<b style=\"color:green;\">Passed</b>";
	public static int WarningCounter = 0;
	public static String FailedStatus = "";
	public static String ConsolidatedReportFileLink = "";
	public static String ConsolidatedResultFile= "";
	public static String TakeScreenShotsAfterPassingComponent = "YES";
	String NoOfRuns ="";
	String LastAverageTimeperIteration = "LastAverageTimeperIteration";

	public HtmlResult()
	{
		try {

			LastDuration = "0:0:0";
			LastEndTime = "`";
			LastTestCaseFileName = TestCaseRunner.TestCaseName;
			NoOfRuns = TestCaseRunner.gblNoOfRuns;
			htmlStringBuilder=new StringBuilder();
			DateFormat DateFormat=new SimpleDateFormat("yyyy/MM/dd");
			DateFormat TimeFormat2=new SimpleDateFormat("HH:mm:ss a");
			StartDuration=new Date();

			String Application = TestCaseRunner.Application;
			String Market =	TestCaseRunner.Market;
			String Date = DateFormat.format(StartDuration);
			String StartTime = TimeFormat2.format(StartDuration);

			Duration = "Report Initialize";

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			DateFormat TimeFormat = new SimpleDateFormat("hh-mm-ss a");

			String Path =System.getProperty("user.dir");
			ResultFolderName = Path+File.separator+"FrameWork" + File.separator+"Results"+File.separator+"HTML Results"+File.separator+TestCaseRunner.TestCaseName+" ExecutedOn_"+dateFormat.format(StartDuration)+ "_Time_" + TimeFormat.format(StartDuration);
			new File (ResultFolderName).mkdir();


			//define a HTML String Builder
			//append html header and title
			htmlStringBuilder.append("<html><head><title>UKIT Automation Test </title>");
			//append body
			htmlStringBuilder.append( "<meta http-equiv=\"Content-Type\" content=\"text/html; charset = ANSI\" />");
			htmlStringBuilder.append( "<link rel='stylesheet' type='text/css' href='bootstrap.min.css'>" );
			htmlStringBuilder.append( "<!doctype html>\n" );
			htmlStringBuilder.append( "<html lang='en'>\n" );
			htmlStringBuilder.append("<style> body{ width:100%;} </style>");
			htmlStringBuilder.append( "</head>\n\n" );
			htmlStringBuilder.append("<body>");
			htmlStringBuilder.append("<table width =100%><tr>"
					+ "<td align=left widht=40%> <img src='"+ Path + "\\FrameWork\\ReportingImages\\capgemini_logo.jpg' align=left width='150' height='80'></td>"
					+"<td align=center> <h1  color= red>  Automation Test Execution Report</h1>"
					+ "<h4 align=center color = blue> Automation Test Report Summary </h4>"
					+ "<td>"
					+ "<td align=right widht=40%><img src='"+ Path + "\\FrameWork\\ReportingImages\\McDonalds_Logo.jpg' align=right width='105' height='80'></td>"
					+ "</tr></table>");
			//append table
			htmlStringBuilder.append("<table border=4 bordercolor=#000000"
					+ "align=center border=3 width=100% bgcolor= #FDFBB7>");
			htmlStringBuilder.append("<tr>  </tr> <tr>  </tr> <tr>  </tr>"
					+ "<tr>  </tr> <tr>  </tr><tr>  </tr>");
			htmlStringBuilder.append("<tr>"
					+ "<td bgcolor=yellow width =12.5%><b> Application </b></td>"
					+ "<td><b>" + Application + " </b></td>"
					+ "<td bgcolor=yellow width =12.5%><b> Market </b></td>"
					+ "<td><b> " + Market + "</b></td>"
					+ "<td bgcolor=yellow width =12.5%><b> Start Time  </b></td>"
					+ "<td><b> " + StartTime  + "</b></td>"
					+ "<td bgcolor=yellow width =12.5%><b>  Date </b></td>"
					+ "<td><b> " + Date + " </b></td>"
					+ "</tr>"
					+"<tr>"
					+ "<td bgcolor=yellow><b> Execution Status </b></td>"
					+ "<td><b>  " +  ExecutionStatus + " </b></td>"
					+ "<td bgcolor=yellow><b> Test Case File Name</b></td>"
					+ "<td><b>  "+LastTestCaseFileName+"  </b></td>"
					+ "<td bgcolor=yellow><b> End time </b></td>"
					+ "<td><b> "+LastEndTime+" </b></td>"
					+ "<td bgcolor=yellow><b> Duration</b></td>"
					+ "<td><b>"+  LastDuration + "</b></td>"
					+ "</tr>"
					/*+ "<tr>"
					+ "<td bgcolor=yellow><b> Number Of Runs</b></td>"
					+ "<td><b>  " +NoOfRuns+ " </b></td>"
					+"<td bgcolor=yellow><b> Average Time per Iteration</td>"
					+"<td><b>"+LastAverageTimeperIteration+"</b></td>+"</tr>"*/);

			htmlStringBuilder.append("<tr>  <tr> <tr>  <tr> <tr>  <tr>"
					+ "<tr>  <tr> <tr>  <tr> <tr>  <tr>");
			htmlStringBuilder.append( "</table>\n\n" );
			htmlStringBuilder.append( "\n" );
			htmlStringBuilder.append( "\n" );
			htmlStringBuilder.append( "\n" );
			htmlStringBuilder.append( "\n" );
			//append table
			htmlStringBuilder.append("<table border=0 bordercolor=#FAF6F5 align=center border=0 width=100% bgcolor= #FAF6F5>");
			htmlStringBuilder.append("<tr>  <tr> <tr>  <tr> <tr>  <tr>"
					+ "<tr>  <tr> <tr>  <tr> <tr>  <tr>");
			htmlStringBuilder.append( "</table>\n\n" );

			ReportTestCaseInfo();
			new ConsolidateTestReport();
			TakeScreenShotsAfterPassingComponent = POS.getValueFromExcel("TakeScreenShotsAfterPassingComponent");

		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			HtmlResult.failed("HTML Result file Setup", "Result file (html) should be create", e.getMessage());
		}

	}

	public static void addAverageTimePerIteration()
	{
		try{

		}
		catch(Exception e)
		{
			return;
		}
	}

	public static void failed(String strDescription, String strExpectedResult,String strActualResult)
	{

		try
		{
			FailedStatus = "Failed";

			//verification variables
			TestCaseRunner.blnComponentFalied = true;

			//logging variable
			ResultLogger=TestCaseRunner.FrameWorkLogger;
			ResultLogger.error("TestCase","logging FAILED  in HTML Reports ");

			//time format to print time in reports
			DateFormat TimeFormat = new SimpleDateFormat("HH:mm:ss a");

			//time of printing pass or fail
			Date EndDuration = new Date();
			String EndTime = TimeFormat.format(EndDuration.getTime());
			EndTimeEndDuration =EndTime;

			//setting duration
			String Duration = getTimeDuration(StartDuration,EndDuration);

			System.getProperty("user.dir");
			String ResultFile = ResultFolderName + File.separator+"Results.html";
			File file = new File(ResultFile);

			// if file does exists, then delete and create a new file
			//String ScreenShotFilePath=ResultFolderName+"\\ScreenShots\\"+Duration.replaceAll(":", "_");
			//new File(ResultFolderName+"\\ScreenShots").mkdir();

			// For iOS and MacBook - Added By Prateek Gupta (Dec)
			String ScreenShotFilePath=ResultFolderName+"/ScreenShots/"+Duration.replaceAll(":", "_");
			new File(ResultFolderName+"/ScreenShots").mkdir();
			String PreviousTestStepNo=TestStepNumber;
			TestStepNumber = TestCaseRunner.TestStepNumber;
			int PrevStepNo=Integer.parseInt(PreviousTestStepNo);
			int CurrentTestStepNo=Integer.parseInt(TestStepNumber);

			if(CurrentTestStepNo>PrevStepNo)
			{
				;
			}
			else
			{
				TestStepNumber="";
			}

			String PreviousComponentName=ComponentName;
			ComponentName=TestCaseRunner.ComponentCode;

			if(PreviousComponentName.equals(ComponentName))
			{
				ComponentName="";
			}

			String TempScreenshotName = ScreenShotFilePath.replace(" ", "%20")+".png";

			htmlStringBuilder.append("<tr bgcolor= #FAF6F5 >"
					+ "<td WIDTH=3% > " + TestStepNumber + "</td>"
					+ "<td WIDTH=10% > <b>" + ComponentName + "<b></td>"
					+ "<td WIDTH=20% > " + strDescription + "</td>"
					+ "<td WIDTH=30%>"+ strExpectedResult + " </td>"
					+ "<td WIDTH=30%><a href="+ TempScreenshotName +">"+strActualResult+"</a></td>"
					+ "<td WIDTH=8% ><font color=red ><b>Fail</b></font> </td>"
					+ "</tr>"
					);


			ConsolidateTestReport.ConsolidateReport(strDescription, strExpectedResult, strActualResult,TempScreenshotName);
			OutputStream outputStream = new FileOutputStream(file.getAbsoluteFile());
			Writer writer=new OutputStreamWriter(outputStream);

			String PrintReport = htmlStringBuilder.toString().replace(LastDuration,Duration).replace(LastTestCaseFileName, TestCaseRunner.TestCaseName).replace(LastEndTime, EndTime).replace(ExecutionStatus, "<b style=\"color:red;\">Failed</b>");
			LastDuration = Duration;
			LastTestCaseFileName = TestCaseRunner.TestCaseName;
			LastEndTime = EndTime;

			StringBuilder newHtmlStringBUilder = new StringBuilder(PrintReport);
			htmlStringBuilder = newHtmlStringBUilder;
			writer.append(htmlStringBuilder);
			writer.close();	;

			if(TestStepNumber.equals(""))
			{
				TestStepNumber=Integer.toString(CurrentTestStepNo);
			}
			if(ComponentName.equals(""))
			{
				ComponentName=PreviousComponentName;
			}

			ScreenShotUtil.TakeScreenShot(ScreenShotFilePath);
		}
		catch(Exception e)
		{
			ResultLogger=TestCaseRunner.FrameWorkLogger;
			ResultLogger.error("TestCase","Error while logging FAILED in HTML Reports- ' "+e.getMessage()+" ' ");
		}

	}

	public static void passed(String strDescription, String strExpectedResult,String strActualResult)
	{

		try
		{
			//verification variables
			//TestCaseRunner.blnComponentFalied = false;
			//logging variables
			ResultLogger=TestCaseRunner.FrameWorkLogger;
			ResultLogger.info("TestCase","logging PASSED  in HTML Reports ");

			//time of printing pass or fail
			Date EndDuration = new Date();
			DateFormat TimeFormat=new SimpleDateFormat("HH:mm:ss a");
			String EndTime = TimeFormat.format(EndDuration.getTime());
			EndTimeEndDuration = EndTime;

			//setting duration
			String Duration = getTimeDuration(StartDuration,EndDuration);


			String ResultFile = ResultFolderName + File.separator+"Results.html";
			File file = new File(ResultFile);
			// if file does exists, then delete and create a new file
			//String ScreenShotFilePath=ResultFolderName+"\\ScreenShots\\"+Duration.replaceAll(":", "_");
			//new File(ResultFolderName+"\\ScreenShots").mkdir();
			

			// For iOS and MacBook - Added By Prateek Gupta (Dec)
			String ScreenShotFilePath=ResultFolderName+"/ScreenShots/"+Duration.replaceAll(":", "_");
			new File(ResultFolderName+"/ScreenShots").mkdir();


			//bordercolor=#8060E5 border=0  align=center
			String PreviousTestStepNo=TestStepNumber;
			TestStepNumber = TestCaseRunner.TestStepNumber;
			int PrevTemp=Integer.parseInt(PreviousTestStepNo);
			int CurrentTestStepNo=Integer.parseInt(TestStepNumber);

			if(CurrentTestStepNo>PrevTemp)
			{
				;
			}
			else
			{
				TestStepNumber="";
			}

			String PreviousComponentName=ComponentName;
			ComponentName=TestCaseRunner.ComponentCode;

			if(PreviousComponentName.equals(ComponentName))
			{
				ComponentName="";
			}

			String TempScreenshotName = ScreenShotFilePath.replace(" ", "%20")+".png";

			htmlStringBuilder.append("<tr bgcolor= #FAF6F5>"
					+ "<td WIDTH=3% > " + TestStepNumber + "</td>"
					+ "<td WIDTH=10% > <b>" + ComponentName + "<b></td>"
					+ "<td WIDTH=20%> " + strDescription + "</td>"
					+ "<td WIDTH=30%>"+ strExpectedResult + " </td>"
					+"<td WIDTH=30%><a href="+TempScreenshotName+">"+ strActualResult + "</a></td>"
					+ "<td WIDTH=8%> <font color=green ><b>Passed</b></font></td>"
					+ "</tr>"
					);

			OutputStream outputStream = new FileOutputStream(file.getAbsoluteFile());
			Writer writer=new OutputStreamWriter(outputStream);

			String PrintReport = htmlStringBuilder.toString().replace(LastDuration,Duration).replace(LastTestCaseFileName, TestCaseRunner.TestCaseName).replace(LastEndTime, EndTime);
			LastDuration = Duration;
			LastTestCaseFileName = TestCaseRunner.TestCaseName;
			LastEndTime = EndTime;

			StringBuilder newHtmlStringBUilder = new StringBuilder(PrintReport);
			htmlStringBuilder = newHtmlStringBUilder;
			writer.append(htmlStringBuilder);
			writer.close();

			if(TestStepNumber.equals(""))
			{
				TestStepNumber=Integer.toString(CurrentTestStepNo);
			}
			if(ComponentName.equals(""))
			{
				ComponentName=PreviousComponentName;
			}

			if(TakeScreenShotsAfterPassingComponent.equalsIgnoreCase("YES"))
			{
				ScreenShotUtil.TakeScreenShot(ScreenShotFilePath);
			}
			else if(! TakeScreenShotsAfterPassingComponent.equalsIgnoreCase("NO"))
			{
				ScreenShotUtil.TakeScreenShot(ScreenShotFilePath);
			}

			ConsolidateTestReport.writeToFile();
		}
		catch(Exception e)
		{
			ResultLogger.error("TestCase","Error while logging PASSED in HTML Reports- ' "+e.getMessage()+" ' ");
		}

		//append row
	}


	//warning
	public static void warning(String strDescription, String strExpectedResult,String strActualResult)
	{

		try
		{
			WarningCounter++;
			FailedStatus = "Warning";
			//verification variables

			//logging variables
			ResultLogger=TestCaseRunner.FrameWorkLogger;
			ResultLogger.info("TestCase","logging PASSED  in HTML Reports ");

			//time of printing pass or fail
			Date EndDuration = new Date();
			DateFormat TimeFormat=new SimpleDateFormat("HH:mm:ss a");
			String EndTime = TimeFormat.format(EndDuration.getTime());
			EndTimeEndDuration = EndTime;

			//setting duration
			String Duration = getTimeDuration(StartDuration,EndDuration);

			System.getProperty("user.dir");
			String ResultFile = ResultFolderName + File.separator+"Results.html";
			File file = new File(ResultFile);

			// if file does exists, then delete and create a new file
			//String ScreenShotFilePath=ResultFolderName+"\\ScreenShots\\"+Duration.replaceAll(":", "_");
			//new File(ResultFolderName+"\\ScreenShots").mkdir();
			

			// For iOS and MacBook - Added By Prateek Gupta (Dec)
			String ScreenShotFilePath=ResultFolderName+"/ScreenShots/"+Duration.replaceAll(":", "_");
			new File(ResultFolderName+"/ScreenShots").mkdir();


			String PreviousTestStepNo=TestStepNumber;
			TestStepNumber = TestCaseRunner.TestStepNumber;
			int PrevTemp=Integer.parseInt(PreviousTestStepNo);
			int CurrentTestStepNo=Integer.parseInt(TestStepNumber);

			if(CurrentTestStepNo>PrevTemp)
			{
				;
			}
			else
			{
				TestStepNumber="";
			}

			String PreviousComponentName=ComponentName;
			ComponentName=TestCaseRunner.ComponentCode;

			if(PreviousComponentName.equals(ComponentName))
			{
				ComponentName="";
			}

			String TempScreenshotName = ScreenShotFilePath.replace(" ", "%20")+".png";

			htmlStringBuilder.append("<tr bgcolor= #FAF6F5>"
					+ "<td WIDTH=3% > " + TestStepNumber + "</td>"
					+ "<td WIDTH=10% > <b>" + ComponentName + "</b></td>"
					+ "<td WIDTH=20%> " + strDescription + "</td>"
					+ "<td WIDTH=30%>"+ strExpectedResult + " </td>"
					+"<td WIDTH=30%><a href="+TempScreenshotName+">"+ strActualResult + "</a></td>"
					+ "<td WIDTH=8%> <font color = orange ><b>Warning !</b></font></td>"
					+ "</tr>"
					);

			ConsolidateTestReport.ConsolidateReport(strDescription, strExpectedResult, strActualResult,TempScreenshotName);
			OutputStream outputStream = new FileOutputStream(file.getAbsoluteFile());
			Writer writer=new OutputStreamWriter(outputStream);
			//String fileContent ="<tr>"+ "<td> " + strDescription + "</td>"+ "<td>"+ strExpectedResult + " </td>"+ "<td>"+ strActualResult + " </td>"+ "<td bgcolor=red>Passed</td>"+ "</tr>";

			String PrintReport = htmlStringBuilder.toString().replace(LastDuration,Duration).replace(LastTestCaseFileName, TestCaseRunner.TestCaseName).replace(LastEndTime, EndTime);
			LastDuration = Duration;
			LastTestCaseFileName = TestCaseRunner.TestCaseName;
			LastEndTime = EndTime;

			StringBuilder newHtmlStringBUilder = new StringBuilder(PrintReport);
			htmlStringBuilder = newHtmlStringBUilder;
			writer.append(htmlStringBuilder);
			writer.close();

			if(TestStepNumber.equals(""))
			{
				TestStepNumber=Integer.toString(CurrentTestStepNo);
			}
			if(ComponentName.equals(""))
			{
				ComponentName=PreviousComponentName;
			}

			ScreenShotUtil.TakeScreenShot(ScreenShotFilePath);

			ConsolidateTestReport.writeToFile();
		}

		catch(Exception e)
		{
			ResultLogger.error("TestCase","Error while logging Warning in HTML Reports- ' "+e.getMessage()+" ' ");
		}
		//append row
	}

	public static void ReportTestCaseInfo()
	{
		ConsolidatedResultFile = ResultFolderName + File.separator+"TestConsolidateReport.html";
		ConsolidatedReportFileLink = ConsolidatedResultFile.replaceAll(" ", "%20").trim();
		try
		{
			ResultLogger=TestCaseRunner.FrameWorkLogger;
			ResultLogger.info("TestCase","Executing ReportTestCaseInfo().......");
			TestCaseID = TestCaseRunner.TestCaseNumber;
			TestCaseName = TestCaseRunner.TestCaseName;
			htmlStringBuilder.append("<table border=1 bordercolor=#8060E5  border=0 width=100% bgcolor= #FAF6F5>");
			htmlStringBuilder.append("<tr bgcolor=#87BBF3>"
					+ "<td WIDTH=25% align=center><b> Batch File Number:" + TestCaseID + " </b></td>"
					+ "<td WIDTH=75% align=center><b><a href="+ConsolidatedReportFileLink+" target=\"_blank\"> " + TestCaseName + "[Consolidated Report] </a></b></td>"
					+ "</tr>");
			htmlStringBuilder.append("</table>");
		}
		catch (Exception e)
		{
			ResultLogger.error("TestCase","Error while ReportCaseInfo() in HTML Reports- ' "+e.getMessage()+" ' ");
		}
	}

	public static void CreateTableLayOut()
	{
		try
		{
			ResultLogger=TestCaseRunner.FrameWorkLogger;
			ResultLogger.info("TestCase","Executing ReportTestCaseInfo().......");
			htmlStringBuilder.append("<table border=1 bordercolor=#8060E5 align=center border=0 width=100% bgcolor= #FAF6F5 style=\"table-layout:fixed; word-wrap: break-word;\">"); //
			htmlStringBuilder.append("<tr bgcolor=#1E90FF>"
					+ "<td WIDTH=3%><font color=\"white\"><b>Sr.#</b></font></td>"
					+ "<td WIDTH=10%><font color=\"white\"><b>Component Name</b></font></td>"
					+ "<td WIDTH=20%><font color=\"white\"><b>Test Case description</b></font></td>"
					+ "<td WIDTH=30%><font color=\"white\"><b>Expected Result</b></font></td>"
					+ "<td WIDTH=30%><font color=\"white\"><b>Actual Result</b></font></td>"
					+ "<td WIDTH=8%><font color=\"white\"><b>Test Result</b></font></td>"
					+ "</tr>");
		} catch (Exception e) {

			ResultLogger.error("TestCase","Error while CreateTableLayOut() in HTML Reports- ' "+e.getMessage()+" ' ");
		}
	}

	public static void addLineSeparator(String Colour,String Message)
	{
		try {
			htmlStringBuilder.append("<tr><td width=100% colspan=6 border=0 align=center><hr color=\""+Colour.trim()+"\"><b><font color=\""+Colour.trim()+"\">"+
					Message.trim()+"</font></b></hr></td></tr>");
			writeToFile();
		} catch (Exception e) {
			ResultLogger=TestCaseRunner.FrameWorkLogger;
			ResultLogger.error("TestCase","Error while  addLineSeparator() in HTML Reports- ' "+e.getMessage()+" ' ");
		}
	}

	public static void addMessage(String Message)
	{
		try {
			htmlStringBuilder.append("<tr><td width=100% colspan=6 border=0 align=center><b>"+
					Message.trim()+"</b></td></tr>");
			writeToFile();
		} catch (Exception e) {
			ResultLogger=TestCaseRunner.FrameWorkLogger;
			ResultLogger.error("TestCase","Error while addMessage() in HTML Reports- ' "+e.getMessage()+" ' ");
		}
	}
	public static void addMessageWithScreenShot(String Message,String FontColour)
	{
		try {
			Date EndDuration = new Date();
			String Duration = getTimeDuration(StartDuration,EndDuration);

			//String ScreenShotFilePath=ResultFolderName+"\\ScreenShots\\"+Duration.replaceAll(":", "_");
			//ScreenShotUtil.TakeScreenShot(ScreenShotFilePath);

			//added by Prateek Gupta
			String ScreenShotFilePath=ResultFolderName+"\\ScreenShots\\"+Duration.replaceAll(":", "_");
			ScreenShotUtil.TakeScreenShot(ScreenShotFilePath);

			String ScreenShotFileLink = ScreenShotFilePath.concat(".png").replaceAll(" ", "%20").trim();

			htmlStringBuilder.append("<tr><td width=100% colspan=6 border=0 align=center><b><font color='"+FontColour+"'><a href="+ScreenShotFileLink+">"+
					Message.trim()+"</a></font></b></td></tr>");

			writeToFile();
		} catch (Exception e) {
			ResultLogger=TestCaseRunner.FrameWorkLogger;
			ResultLogger.error("TestCase","Error while addMessage() in HTML Reports- ' "+e.getMessage()+" ' ");
		}
	}

	public static void addTableDataToRow(String Message)
	{
		try {
			htmlStringBuilder.append("<tr>"+Message+"</tr>");
			writeToFile();
		} catch (Exception e) {
			ResultLogger=TestCaseRunner.FrameWorkLogger;
			ResultLogger.error("TestCase","Error while addMessage() in HTML Reports- ' "+e.getMessage()+" ' ");
		}
	}

	public static void addTable(String Message)
	{
		try {
			htmlStringBuilder.append("<tr><td colspan=6 align=center><table border=1 style=\"border-style: solid; border-color:#8060E5;\"><tr>"+Message+"</tr></table></td></tr>");//border-style: solid;border-color: #ff0000 #0000ff;
			writeToFile();
		} catch (Exception e) {
			ResultLogger=TestCaseRunner.FrameWorkLogger;
			ResultLogger.error("TestCase","Error while addMessage() in HTML Reports- ' "+e.getMessage()+" ' ");
		}
	}


	public static void writeToFile()// to immediately write the current 'HtmlStringBuilders' data to the current html file
	{
		try {
			System.getProperty("user.dir");
			String ResultFile = ResultFolderName + File.separator+"Results.html";
			File file = new File(ResultFile);
			OutputStream outputStream = new FileOutputStream(file.getAbsoluteFile());
			Writer writer=new OutputStreamWriter(outputStream);

			String PrintReport  = htmlStringBuilder.toString().replace("Report Initialize",Duration);

			writer.append(PrintReport);
			writer.close();
		}
		catch (Exception e)
		{
			ResultLogger=TestCaseRunner.FrameWorkLogger;
			ResultLogger.error("TestCase","Error while writeToFile() in HTML Reports- ' "+e.getMessage()+" ' ");
		}

	}


	public static String getTimeDuration(Date StartTime , Date EndTime)
	{
		try
		{

			long TimeDifferenceInMilliSeconds = EndTime.getTime() - StartTime.getTime();
			long TimeDifferenceInSec = TimeUnit.MILLISECONDS.toSeconds(TimeDifferenceInMilliSeconds);

			long Seconds = TimeDifferenceInSec % 60;
			long Minutes = TimeDifferenceInSec / 60;
			long Hours = 0;

			if(Minutes>60)
			{
				Hours = Minutes / 60;
				Minutes = Minutes % 60;
			}

			return Hours+":"+Minutes+":"+Seconds;

		}
		catch(Exception e)
		{
			return "0:0";
		}

	}

	public static void resetHtmlStringBuilder()
	{
		try
		{
			htmlStringBuilder.delete(0, htmlStringBuilder.length()-1);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void closeTable()
	{
		htmlStringBuilder.append("</table>");
	}

	public static void closeHtmlTag() throws IOException
	{
		htmlStringBuilder.append("</table></body></html>");
		
		System.getProperty("user.dir");
		String ResultFile = ResultFolderName + File.separator+"Results.html";
		File htmlFile = new File(ResultFile);
		Desktop.getDesktop().browse(htmlFile.toURI());
		
	}
}

