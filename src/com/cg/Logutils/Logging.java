package com.cg.Logutils;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

public class Logging
{

	public static Logger Mylogger;
	//public static String Log4jConfigFilePath=System.getProperty("user.dir")+File.separator+"FrameWork\\LookUp\\Logger Configuration\\LogConfig.properties";
	
	public static String Log4jConfigFilePath=System.getProperty("user.dir")+File.separator+"FrameWork/LookUp/Logger Configuration/LogConfig.properties";
private static String FrameWorkLogDestination;
	private static String TestCaseLogDestination;
	static DateFormat dateFormat;
	static Date CurrentDate;
	static String FrmaeWorkFileName="";
	static String TestCaseFileName="";
	static RollingFileAppender RollingAppender; //= new RollingFileAppender(layOut,LoggerFileName,true);
	static ConsoleAppender ConsoleAppender; //=new ConsoleAppender(layOut);
	static PatternLayout layOut = new PatternLayout();
	String currentOS = "MacOS";



	public Logging()
	{
		Mylogger= Logger.getLogger(Logging.class);
		Mylogger.setLevel(Level.ALL);
		setLogFileName();
		layOut.setConversionPattern("%d{yyyy-MM-dd HH:mm:ss} %-5p :%L %C{1} - %M - %m%n");
	}

	private void setLogFileName()
	{
		if(FrmaeWorkFileName.equals(""))
		{
			try
			{
				Date date =new Date();
				DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy-HH.mm.ss");

				FrmaeWorkFileName="Executed at-"+dateFormat.format(date)+".txt";

			}
			catch(Exception e)
			{
				FrmaeWorkFileName="LoggerFailed.txt";
			}
		}

		if(TestCaseFileName.equals(""))
		{
			try
			{
				Date date =new Date();
				DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy-HH.mm.ss");

				TestCaseFileName="Executed at-"+dateFormat.format(date)+".txt";

			}
			catch(Exception e)
			{
				TestCaseFileName="LoggerFailed.txt";
			}
		}
	}



	public void debug(String LoggingType,String Message )
	{

		try
		{
			if(LoggingType.equalsIgnoreCase("FRAMEWORK"))
			{
				String LoggerFileName ="";
				if (currentOS=="Window")
				{
				 LoggerFileName=System.getProperty("user.dir")+"\\FrameWork\\Results\\LOGS\\Framework Logs\\"+FrmaeWorkFileName;
				}
				else
				{
				 LoggerFileName=System.getProperty("user.dir")+"/FrameWork/Results/LOGS/Framework Logs/"+FrmaeWorkFileName;
				}
				RollingAppender= new RollingFileAppender(layOut,LoggerFileName,true);

				Mylogger.addAppender(RollingAppender);
				RollingAppender.setAppend(true);
				Mylogger.trace(Message);
				Mylogger.removeAppender(RollingAppender);

				ConsoleAppender=new ConsoleAppender(layOut);
				Mylogger.addAppender(ConsoleAppender);
				Mylogger.trace(Message);


				Mylogger.removeAllAppenders();
			}
			else if(LoggingType.equalsIgnoreCase("TESTCASE"))
			{
				String LoggerFileName ="";				
				if (currentOS=="Window")
				{
					 LoggerFileName=System.getProperty("user.dir")+"\\FrameWork\\Results\\LOGS\\Test Case Logs\\"+TestCaseFileName;
				}
				else
				{
					 LoggerFileName=System.getProperty("user.dir")+"/FrameWork/Results/LOGS/Test Case Logs/"+TestCaseFileName;
				}
				
				RollingAppender= new RollingFileAppender(layOut,LoggerFileName,true);

				Mylogger.addAppender(RollingAppender);
				Mylogger.trace(Message);
				Mylogger.removeAppender(RollingAppender);

				ConsoleAppender=new ConsoleAppender(layOut);
				Mylogger.addAppender(ConsoleAppender);
				Mylogger.trace(Message);

				Mylogger.removeAllAppenders();
			}
			else
			{
				System.err.println("---------------INVALID LOGGING TYPE------------------");

			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
	}


	public void info(String LoggingType,String Message )
	{
		try
		{
			if(LoggingType.equalsIgnoreCase("FRAMEWORK"))
			{	
				String LoggerFileName ="";				
				if (currentOS=="Window")
				{
					 LoggerFileName=System.getProperty("user.dir")+"\\FrameWork\\Results\\LOGS\\Framework Logs\\"+FrmaeWorkFileName;
				}
				else
				{
					 LoggerFileName=System.getProperty("user.dir")+"/FrameWork/Results/LOGS/Framework Logs/"+FrmaeWorkFileName;
				}
				
				RollingAppender= new RollingFileAppender(layOut,LoggerFileName,true);
				Mylogger.addAppender(RollingAppender);
				Mylogger.trace(Message);
				Mylogger.removeAppender(RollingAppender);

				ConsoleAppender=new ConsoleAppender(layOut);
				Mylogger.addAppender(ConsoleAppender);
				Mylogger.trace(Message);

				Mylogger.removeAllAppenders();
			}
			else if(LoggingType.equalsIgnoreCase("TESTCASE"))
			{

				String LoggerFileName ="";				
				if (currentOS=="Window")
				{
				 LoggerFileName=System.getProperty("user.dir")+"\\FrameWork\\Results\\LOGS\\Test Case Logs\\"+TestCaseFileName;				RollingAppender= new RollingFileAppender(layOut,LoggerFileName,true);
				}
				else
				{
					 LoggerFileName=System.getProperty("user.dir")+"/FrameWork/Results/LOGS/Test Case Logs/"+TestCaseFileName;				RollingAppender= new RollingFileAppender(layOut,LoggerFileName,true);
				}
				
				
				Mylogger.addAppender(RollingAppender);
				Mylogger.trace(Message);
				Mylogger.removeAppender(RollingAppender);

				ConsoleAppender=new ConsoleAppender(layOut);
				Mylogger.addAppender(ConsoleAppender);
				Mylogger.trace(Message);

				Mylogger.removeAllAppenders();
			}
			else
			{
				System.err.println("---------------INVALID LOGGING TYPE------------------");

			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.err.println(e.getMessage());
		}



	}

	public void error(String LoggingType,String Message )
	{
		try
		{
			if(LoggingType.equalsIgnoreCase("FRAMEWORK"))
			{

				String LoggerFileName ="";				
				if (currentOS=="Window")
				{
					 LoggerFileName=System.getProperty("user.dir")+"\\FrameWork\\Results\\LOGS\\Test Case Logs"+FrmaeWorkFileName;
				}
				else
				{
					 LoggerFileName=System.getProperty("user.dir")+"/FrameWork/Results/LOGS/Test Case Logs"+FrmaeWorkFileName;
				}
				
				RollingAppender= new RollingFileAppender(layOut,LoggerFileName,true);
				Mylogger.addAppender(RollingAppender);
				Mylogger.trace(Message);
				Mylogger.removeAppender(RollingAppender);

				ConsoleAppender=new ConsoleAppender(layOut);
				Mylogger.addAppender(ConsoleAppender);
				Mylogger.trace(Message);


				Mylogger.removeAllAppenders();
			}
			else if(LoggingType.equalsIgnoreCase("TESTCASE"))
			{

				String LoggerFileName ="";				
				if (currentOS=="Window")
				{
					 LoggerFileName=System.getProperty("user.dir")+"\\FrameWork\\Results\\LOGS\\Test Case Logs\\"+TestCaseFileName;				RollingAppender= new RollingFileAppender(layOut,LoggerFileName,true);
				}
				else
				{
					 LoggerFileName=System.getProperty("user.dir")+"/FrameWork/Results/LOGS/Test Case Logs/"+TestCaseFileName;				RollingAppender= new RollingFileAppender(layOut,LoggerFileName,true);
				}
				
				Mylogger.addAppender(RollingAppender);
				Mylogger.trace(Message);
				Mylogger.removeAppender(RollingAppender);

				ConsoleAppender=new ConsoleAppender(layOut);
				Mylogger.addAppender(ConsoleAppender);
				Mylogger.trace(Message);

				Mylogger.removeAllAppenders();
			}
			else
			{
				System.err.println("---------------INVALID LOGGING TYPE------------------");

			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
	}











	Logger getLogger(String LoggingType)
	{

		Mylogger= Logger.getLogger(Logging.class);
		layOut.setConversionPattern("%d{yyyy-MM-dd HH:mm:ss} %-5p :%L %C{1} - %M - %m%n");

		if(LoggingType.equalsIgnoreCase("FRAMEWORK"))
		{
			if(setPath("FrameWork"))
			{
				return Mylogger;
			}
		}
		else if(LoggingType.equalsIgnoreCase("TESTCASE"))
		{
			if(setPath("TESTCASE"))
			{
				return Mylogger;
			}
		}
		else
		{
			return null;
		}
		return null;

	}

	boolean setPath(String LoggingType)
	{
		try {
			if(LoggingType.equalsIgnoreCase("FRAMEWORK"))
			{
				String LoggerFileName=System.getProperty("user.dir")+"";
				RollingAppender=new RollingFileAppender(layOut,LoggerFileName,true);
				Mylogger.addAppender(RollingAppender);
				return true;
			}
			else if(LoggingType.equalsIgnoreCase("TESTCASE"))
			{
				String LoggerFileName=System.getProperty("user.dir")+"";
				RollingAppender=new RollingFileAppender(layOut,LoggerFileName,true);
				Mylogger.addAppender(RollingAppender);
				return true;
			}
		} catch (Exception e) {

			return false;
		}
		return false;
	}
}
