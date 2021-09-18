package com.cg.Logutils;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.swing.text.DateFormatter;

import org.apache.log4j.Logger;

import com.cg.TestCaseRunner.TestCaseRunner;



public class ConsolidateTestReport {

	static String ABC="";
	static String TestCaseID;
	public static String TestStepNumber="0";
	static String TestCaseName;
	public static String ComponentName="";
	public static StringBuilder htmlStringBuilder_ConsolidateReport;
	public static String Duration;
	public static Date StartDuration;
	public static Date EndDuration;
	public static String Status="";
	public static String ResultFolderName="";
	public static Logging ResultLogger;
	public static String EndTimeEndDuration;
	public static int ConsolidateReporting_ExecutedTestCaseCount =0;
	public static String ConsolidatedResultFile = "";
	
	public ConsolidateTestReport()
	{		
		try {
			htmlStringBuilder_ConsolidateReport=new StringBuilder();
			DateFormat DateFormat=new SimpleDateFormat("yyyy/MM/dd");
			DateFormat TimeFormat2=new SimpleDateFormat("hh:mm:ss a");
			StartDuration=new Date();
			String Application =  TestCaseRunner.Application; 
			String Market =TestCaseRunner.Market;
			String Date = DateFormat.format(StartDuration);
			Status = "PASSED";
			String StartTime = TimeFormat2.format(StartDuration);
			

			Duration = "Report Initialize";
			
			String Path =System.getProperty("user.dir");               
			ResultFolderName = HtmlResult.ResultFolderName;

			//define a HTML String Builder
			//append html header and title
			htmlStringBuilder_ConsolidateReport.append("<html><head><title>UKIT Automation Test Set Report </title>");
			htmlStringBuilder_ConsolidateReport.append( "<link rel='stylesheet' type='text/css' ><meta http-equiv='Content-Type' content='text/html';charset='ISO-8859-8'>" );	
			htmlStringBuilder_ConsolidateReport.append( "<meta http-equiv='Content-Type' content='text/html';charset='ISO-8859-8'>" );	
			htmlStringBuilder_ConsolidateReport.append( "<!doctype html>\n" );
			htmlStringBuilder_ConsolidateReport.append( "<html lang='en'>\n" );
			htmlStringBuilder_ConsolidateReport.append( "</head>\n\n" );
			htmlStringBuilder_ConsolidateReport.append("<body>");
			htmlStringBuilder_ConsolidateReport.append("<table width =100%>"
					+ "<tr>"
					+ "<td align=left widht=40%> <img src='"+ Path + "\\FrameWork\\ReportingImages\\capgemini_logo.jpg' align=left width='150' height='80'></td>"
					+"<td align=center> <h1  color= red>  Automation Test Consolidate Report</h1>"
					+ "<h4 align=center color = blue> Automation Test Report Summary </h4>"
					+ "<td>"
					+ "<td align=right widht=40%><img src='"+ Path + "\\FrameWork\\ReportingImages\\McDonalds_Logo.jpg' align=right width='105' height='80'></td>"
					+ "</tr>"
					+ "</table>");
			//append table
			htmlStringBuilder_ConsolidateReport.append("<table border=4 bordercolor=#000000"
					+ "align=center border=3 width=100% bgcolor= #C2DFFF>");
			htmlStringBuilder_ConsolidateReport.append("<tr>  </tr> <tr>  </tr> <tr>  </tr>"
					+ "<tr>  </tr> <tr>  </tr><tr>  </tr>");     

			htmlStringBuilder_ConsolidateReport.append("<tr>"
					+ "<td bgcolor=#5CB3FF width =12.5%><b> Application </b></td>"
					+ "<td><b>" + Application + " </b></td>"
					+ "<td bgcolor=#5CB3FF width =12.5%><b> Market </b></td>"
					+ "<td><b> " + Market + "</b></td>"
					+ "<td bgcolor=#5CB3FF width =12.5%><b> Start Time  </b></td>"
					+ "<td><b> " + StartTime + "</b></td>"
					+ "<td bgcolor=#5CB3FF width =12.5%><b>  Date </b></td>"
					+ "<td><b> " + Date + " </b></td>"
					+"</tr>"
					+"<tr>"
					+"<tr>"
					+"</tr>"
					+"<tr>"
					+ "<td bgcolor=#5CB3FF><b> End time </b></td>"
					+ "<td><b> ##EndTime## </b></td>"	            		
					+ "<td bgcolor=#5CB3FF><b> Duration</b></td>"
					+ "<td><b> "+  Duration + "</b></td>"
					+ "<td bgcolor=#5CB3FF><b>Total Warnings</b></td>"
					+ "<td><b> ##WarningCounter##  </b></td>"
					+ "</tr>"				
					+"<tr>"
					+ "<td bgcolor=#5CB3FF><b> Total Test Case Count </b></td>"
					+ "<td><b> ##TotalTestCaseIterationCount##</b></td>"
					+ "<td bgcolor=#5CB3FF><b> Passed Test Case Count</b></td>"
					+ "<td><b> ##TestCasePassedCounter##   </b></td>"
					+ "<td bgcolor=#5CB3FF><b> Failed Test Case Count</b></td>"
					+ "<td><b> ##TestCaseFailureCounter##  </b></td>"
					+ "<td bgcolor=#5CB3FF><b> Executed Test Case Count</b></td>"
					+ "<td><b> ##ExecutedTestCaseCount##  </b></td>"
					+ "</tr>");
	
			htmlStringBuilder_ConsolidateReport.append("<tr>  <tr> <tr>  <tr> <tr>  <tr>"
					+ "<tr>  <tr> <tr>  <tr> <tr>  <tr>");
			htmlStringBuilder_ConsolidateReport.append( "</table>\n\n" );
			htmlStringBuilder_ConsolidateReport.append( "\n" );
			htmlStringBuilder_ConsolidateReport.append( "\n" );
			htmlStringBuilder_ConsolidateReport.append( "\n" );
			htmlStringBuilder_ConsolidateReport.append( "\n" );
			//append table
			htmlStringBuilder_ConsolidateReport.append("<table border=0 bordercolor=#FAF6F5 align=center border=0 width=100% bgcolor= #FAF6F5>");
			htmlStringBuilder_ConsolidateReport.append("<tr>  <tr> <tr>  <tr> <tr>  <tr>"
					+ "<tr>  <tr> <tr>  <tr> <tr>  <tr>");
			htmlStringBuilder_ConsolidateReport.append( "</table>\n\n" );	

			CreateTableLayOut();
			writeToFile();

		}

		catch (Exception e)
		{
			System.err.println(e.getMessage());	
			//ConsolidateTestReport.failed("HTML Result file Setup", "Result file (html) should be create", e.getMessage());
		}		

	}

	public static void ConsolidateReport(String strDescription, String strExpectedResult,String strActualResult, String TempScreenshotName)
	{
		try
		{	
			String Status = HtmlResult.FailedStatus;
			String colour = "";
			if(Status.equalsIgnoreCase("failed"))
			{
				colour = "red";
			}
			else if(Status.equalsIgnoreCase("warning"))
			{
				Status = "Warning !";
				colour = "orange";
			}
			
			//timings 
			Date CurrentDate = new Date();
			DateFormat CurrentTimeFormatter = new SimpleDateFormat("hh:mm:ss a");
			String TimeStamp = CurrentTimeFormatter.format(CurrentDate);
			
					htmlStringBuilder_ConsolidateReport.append("<tr bgcolor= #FAF6F5 >"
					+ "<td WIDTH=33% > " + TestCaseRunner.TestCaseName + "</td>"
					+ "<td WIDTH=5% > " + TestCaseRunner.TestScriptNumber + "</td>"
					+ "<td WIDTH=5% > " + TestCaseRunner.GlobalIterationNumber + "</td>"
					+ "<td WIDTH=20%>"+ strExpectedResult + " </td>"
					+ "<td WIDTH=20%><a href="+ TempScreenshotName +">"+strActualResult+"</a></td>"
					+ "<td WIDTH=7% ><font color = "+colour+"><b>"+Status+"</b></font> </td>"
					+ "<td WIDTH=10% ><b>"+TimeStamp+"</b></td>"
					+ "</tr>"
					);
					
			writeToFile();		
		}

		catch(Exception e)
		{
			ResultLogger=TestCaseRunner.FrameWorkLogger;
			ResultLogger.error("TestCase","Error while logging FAILED in HTML Reports- ' "+e.getMessage()+" ' ");
		}

	}

	public static void ConsolidateReport_TestCaseEntry(String strDescription, String strExpectedResult,String strActualResult)
	{

		try
		{	
			//htmlStringBuilder.append("<table border=1  bordercolor=#FFFEFB border=0 width=100% bgcolor= #FAF6F5>");
			htmlStringBuilder_ConsolidateReport.append("<tr bgcolor= #FAF6F5>"
					+ "<td WIDTH=7% >  TestCaseNumber</td>"
					+ "<td WIDTH=10% > Status  </td>"
					+ "<td WIDTH=10%>  ErrorMsg </td>"
					+ "<td WIDTH=20%> StartTime  </td>"
					+ "<td WIDTH=20%>   test </td>"
					//	+"<td WIDTH=20%><a href="+ScreenShotFilePath.replace(" ", "%20")+".png>"+ strActualResult + "</a></td>"
					+ "<td WIDTH=7%> <font color=green ><b>"+ Duration + "</b></font></td>" 
					+ "</tr>"
					);
		}

		catch(Exception e)
		{
			e.printStackTrace();

			ResultLogger.error("TestCase","Error while logging PASSED in HTML Reports- ' "+e.getMessage()+" ' ");
		}
		//append row
	}


	public static void ReportTestCaseInfo()
	{
		try
		{
			ResultLogger=TestCaseRunner.FrameWorkLogger;
			ResultLogger.info("TestCase","Executing ReportTestCaseInfo().......");
			TestCaseID = TestCaseRunner.TestCaseNumber;
			TestCaseName = TestCaseRunner.TestCaseName;
			htmlStringBuilder_ConsolidateReport.append("<table border=1 bordercolor=#8060E5  border=0 width=100% bgcolor= #FAF6F5>");
			htmlStringBuilder_ConsolidateReport.append("<tr bgcolor=#87BBF3>"
					+ "<td WIDTH=25% align=center><b> " + TestCaseID + " </b></td>"
					+ "<td WIDTH=75% align=center><b> " + TestCaseName + " </b></td>"
					+ "</tr>");
			htmlStringBuilder_ConsolidateReport.append("</table>");

		} catch (Exception e) {
			e.printStackTrace();
			ResultLogger.error("TestCase","Error while ReportCaseInfo() in HTML Reports- ' "+e.getMessage()+" ' ");
		}
	}

	public static void CreateTableLayOut()
	{
		try
		{
			ResultLogger=TestCaseRunner.FrameWorkLogger;
			ResultLogger.info("TestCase","Executing CreateTableLayOut().......");
			htmlStringBuilder_ConsolidateReport.append("<table border=1 bordercolor=#8060E5 align=center border=0 width=100% bgcolor= #FAF6F5>");
			htmlStringBuilder_ConsolidateReport.append("<tr bgcolor=#1E90FF>"
					+ "<td WIDTH=33% ><b>TestSet</b></td>"
					+ "<td WIDTH=5% ><b>Test Case </b></td>"
					+ "<td WIDTH=5% ><b>Iteration Num</b></td>"
					+ "<td WIDTH=20%><b>Expected Result</b></td>"
					+ "<td WIDTH=20%><b>Actual Result</b></td>"
					+ "<td WIDTH=7%><b>Status</b></td>"
					+	"<td WIDTH=10% ><b>Time</b></td>"
					+ "</tr>");
		} catch (Exception e) {

			ResultLogger.error("TestCase","Error while CreateTableLayOut() in HTML Reports- ' "+e.getMessage()+" ' ");
		}
	}

	public static void addLineSeparator(String Colour,String Message)
	{
		try {
			htmlStringBuilder_ConsolidateReport.append("<tr><td colspan=6 border=0 align=center><hr color=\""+Colour.trim()+"\"><b><font color=\""+Colour.trim()+"\">"+
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
			htmlStringBuilder_ConsolidateReport.append("<tr><td colspan=6 border=0 align=center><hr color=\"Red\"><b><font color=\"red\">"+
					Message.trim()+"</font></b></hr></td></tr>");
			writeToFile();
		} catch (Exception e) {
			ResultLogger=TestCaseRunner.FrameWorkLogger;
			ResultLogger.error("TestCase","Error while addMessage() in HTML Reports- ' "+e.getMessage()+" ' ");
		}
	}

	public static void writeToFile()// to immediately write the current 'HtmlStringBuilders' data to the current html file  
	{
		try {
			
			//ConsolidateReporting_ExecutedTestCaseCount =  Integer.parseInt(TestCaseRunner.GlobalIterationNumber);
			Date WriteToFileTime = new Date();
			SimpleDateFormat Formatter = new SimpleDateFormat("hh:mm:ss a");
			String EndTime = Formatter.format(WriteToFileTime);
			
			ConsolidateReporting_ExecutedTestCaseCount = TestCaseRunner.TestCasePassedCounter +TestCaseRunner.TestCaseFailureCounter;

			System.getProperty("user.dir");
			ConsolidatedResultFile = HtmlResult.ConsolidatedResultFile;
			
			File file = new File(ConsolidatedResultFile);
			
			OutputStream outputStream = new FileOutputStream(file.getAbsoluteFile());
			Writer writer=new OutputStreamWriter(outputStream);
			
			Date CurrentDate = new Date();
			Duration = HtmlResult.getTimeDuration(StartDuration, CurrentDate);
			
			String PrintReport  = htmlStringBuilder_ConsolidateReport.toString()
					.replace("Report Initialize",Duration)
					.replace("##TotalTestCaseIterationCount##", Integer.toString(TestCaseRunner.TotalTestCaseIteration))
					.replace("##TestCaseFailureCounter##", Integer.toString(TestCaseRunner.TestCaseFailureCounter))					
					.replace("##TestCasePassedCounter##", Integer.toString(TestCaseRunner.TestCasePassedCounter))
					.replace("##ExecutedTestCaseCount##", Integer.toString(ConsolidateReporting_ExecutedTestCaseCount))
					.replace("##WarningCounter##", Integer.toString(HtmlResult.WarningCounter))
					.replace("##EndTime##", EndTime);

			writer.append(PrintReport);
			writer.close();
		} 
		catch (Exception e) 
		{
			ResultLogger=TestCaseRunner.FrameWorkLogger;
			ResultLogger.error("TestCase","Error while writeToFile() in HTML Reports- ' "+e.getMessage()+" ' ");
		}
	}

	public static void closeTable()
	{
		htmlStringBuilder_ConsolidateReport.append("</table>").toString();
	}
	public static void closeHtmlTag()
	{
		htmlStringBuilder_ConsolidateReport.append("</table></body></html>");
	}
}

