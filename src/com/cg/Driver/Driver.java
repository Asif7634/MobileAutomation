package com.cg.Driver;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.cg.Logutils.HtmlResult;
import com.cg.Logutils.Logging;
import com.cg.TestCaseRunner.TestCaseRunner;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Driver  
{
	public static Method MethodsName;
	public static Object ObjectName;
	public static Logging DriverLogger=TestCaseRunner.TestCaseLogger;
	
	public static boolean invokeMethod(String ComponentCode,String Parameters) throws InvocationTargetException
	{
		Map<String, ?> ModuleDrivers=com.cg.moduledrivers.ModuleDriver.ModuleDrivers;
		boolean Found=false;
		
		Set<String> KeySet=ModuleDrivers.keySet();
		
		for(String Key:KeySet)
		{
			Found=false;
			Object DriverObject=ModuleDrivers.get(Key.trim());
			Class<? extends Object> DriverClassName=DriverObject.getClass();
			Method[] DriverClassMethods=DriverClassName.getDeclaredMethods();
			int Length=DriverClassMethods.length;

			for(int Index=0;Index<Length;Index++)
			{
				Found=false;
				Method MethodName=DriverClassMethods[Index];
				
				if(MethodName.getName().toString().equals(ComponentCode))
				{
					
					try {
					
						if(Parameters.equals("")||Parameters.equals(null)||Parameters==""||Parameters==null)
						{
							DriverLogger.info("testCase", "Executing.."+MethodName.getName());
							MethodName.invoke(DriverObject);
							MethodsName=MethodName;
							ObjectName=DriverObject;
							Found=true;
							break;	
						}
						else
						{
							System.out.println("hi");
							Map ParametersMap=getParametersMap(Parameters);
							DriverLogger.info("testCase", "Executing.."+MethodName.getName()+",Parameters-"+ParametersMap.toString());
							MethodName.invoke(DriverObject, ParametersMap);
							MethodsName=MethodName;
							ObjectName=DriverObject;
							Found=true;
							break;
						}
						
					} 
					catch (Exception e) 
					{
						TestCaseRunner.blnComponentFalied=true;
						DriverLogger.error("Testcase","component name-"+MethodName.getName()+", Parameters-"+Parameters.toString()+","+e.getMessage());
						HtmlResult.failed("", "", "Not able to execute component-' "+ComponentCode+" ' "+";"+e.getCause().toString()+";"+e.getMessage());
						break;
					}
				}
				else
				{
					Found=false;
				}
			}
			if(Found)
			{
				break;	
			}
		}
	
		if(!Found)
		{
			DriverLogger.info("Testcase","Component not found in any classes ' "+ComponentCode+" '");
			HtmlResult.failed("<font color=\"red\">Method Not Found in any of the module drivers</font>", "<font color=\"red\">Method Not Found in any of the module drivers</font>", "<font color=\"red\">Error during executing-' "+ComponentCode+" ' </font>");
			return false;
		}
		else
		{
			
			return true;
		}
	
	}

	private static Map getParametersMap(String Parameters) 
	{
		Map<String,String> ParametersMap=new HashMap<String,String>();

		String[] Params=Parameters.split(",");
		
		for(String Parameter:Params)
		{
			Parameter=Parameter.trim();
			String Key="";
			String Value="";
			String[] SubParam=Parameter.split("=");

			int Length=SubParam.length;

			if(Length>=2)
			{
				if(!SubParam[0].equals(""))
				{
					Key=SubParam[0];
					Value=SubParam[1];
					ParametersMap.put(Key, Value);
				}
				else 
				{
					Key="Undefined";
				}
			}
			else if (Length==1)
			{
				if(!SubParam[0].equals(""))
				{
					Key=SubParam[0];
					Value="";
					ParametersMap.put(Key, Value);
				}
				else 
				{
					Key="Undefined";
				}
			}
		}
		
		return ParametersMap;
	}


	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
