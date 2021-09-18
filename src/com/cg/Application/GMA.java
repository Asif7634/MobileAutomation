package com.cg.Application;

import java.util.Map;

import com.cg.ApplicationLevelComponent.EggPlant;
import com.cg.Logutils.HtmlResult;

public class GMA extends EggPlant{


	// Coming soon
	public boolean launchApp(Map<String,String> Input)
	{
		String strDescription = "";
		String strExpectedResult = "";
		boolean ApplicationInvoked = false;
		try
		{
			String AppName = Input.get("AppName").trim();
			String ApplicationName = getValueFromExcel(AppName);

			if(ApplicationName!="")
			{
				if(eggUIDriver.launchApp(ApplicationName))
				{
					if(eggUIDriver.imageFound("mobile_mcdonaldslogo"))
					{
						ApplicationInvoked = true;
					}
				}
			}
			else if(AppName!="")
			{
				if(eggUIDriver.imageFound("mobile_mcdonaldslogo"))
				{
					ApplicationInvoked = true;
				}
			}


			if(ApplicationInvoked)
			{
				HtmlResult.passed(strDescription, strExpectedResult, "Application invoked successfully -'"+AppName+"'");
				return true;
			}
			else
			{
				HtmlResult.failed(strDescription, strExpectedResult, "Application not invoked successfully -'"+AppName+"'");
				return false;
			}

		}
		catch(Exception e)
		{
			HtmlResult.failed(strDescription, strExpectedResult, "Error while executing launchApp() component '"+e.getMessage()+","+e.getCause()+"' occured");
			return false;
		}
	}





}
