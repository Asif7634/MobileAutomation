package com.cg.moduledrivers;

import java.util.HashMap;
import java.util.Map;

import com.cg.Application.GMA;
import com.cg.Application.GMA4_Appium;
import com.cg.Application.GMA5_Appium;
import com.cg.Application.POS;
import com.cg.Application.Promotion_KVS;
import com.cg.Application.Promotion_NGK;
import com.cg.ApplicationLevelComponent.Appium;
import com.cg.ApplicationLevelComponent.DemoWebdriver;
import com.cg.ApplicationLevelComponent.EggPlant;
import com.cg.ApplicationLevelComponent.RemoteWebDriver;
import com.cg.GenericComponents.GenericComponents;
import com.cg.Logutils.HtmlResult;
import com.cg.TestCaseRunner.TestCaseRunner;

public class ModuleDriver extends TestCaseRunner
{
	public static Map<String,Object> ModuleDrivers= new HashMap<String,Object>();

	public Map<String, Object> getModuleDrivers()
	{
		return ModuleDrivers;
	}

	public static void setModuleDrivers(String DriverclassName,Object DriverObject)
	{
		ModuleDrivers.put(DriverclassName, DriverObject);
	}

	public static void setModuleDrivers()
	{
		try {
			ModuleDrivers.put("HTML",new HtmlResult());
			ModuleDrivers.put("Eggplant", new EggPlant());
			ModuleDrivers.put("DemoWebdriver", new DemoWebdriver());
			ModuleDrivers.put("RemoteWebDriver", new RemoteWebDriver());
			ModuleDrivers.put("GenericComponents", new GenericComponents());
			ModuleDrivers.put("Pos", new POS());
			ModuleDrivers.put("Promotion_NGK", new Promotion_NGK());
			ModuleDrivers.put("Promotion_KVS", new Promotion_KVS());
			ModuleDrivers.put("GMA", new GMA());
			ModuleDrivers.put("Appium", new Appium());
		//	ModuleDrivers.put("GMA5_Appium", new GMA5_Appium());
			ModuleDrivers.put("GMA4_Appium", new GMA4_Appium());

		} catch (Exception e) {
			FrameWorkLogger.info("FrameWork", e.getMessage());
		}
	}
}
