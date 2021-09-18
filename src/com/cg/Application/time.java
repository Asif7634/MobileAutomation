package com.cg.Application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class time {

	@SuppressWarnings("deprecation")
	public static void main(String args[]) throws ParseException{
		
		
		
		String objectDynamicLocatorValue="android.widget.TextView[@text='#CV#' and @index='0'and @resource-id='com.mcdonalds.app.uk.qa:id/save']";
		
		String str = new String(objectDynamicLocatorValue);
		String objectDynamic = "SaveButton";
		String objectLocatorValue = str.replace("#CV#", objectDynamic);
		
		
		System.out.println(objectLocatorValue);
//		SimpleDateFormat parser = new SimpleDateFormat("dd:mm:yyyy HH:mm");
//		Date date = new Date();
//
//		
//		date.setYear(2017);
//		
//
//		Calendar c = Calendar.getInstance();
//		c.setTime(date);
//		c.set(Calendar.YEAR, 2017);
//		date = c.getTime();
//
//		System.out.println(date);
//		
//
//		Date ten = parser.parse("25:10:2017 5:00");
//		Date eighteen = parser.parse("25:10:2017 11:00");
//
//		
//		System.out.println(ten);
//		System.out.println(eighteen);
//		if (date.after(ten) && date.before(eighteen)) {
//
//			System.out.println("success");
//		}
//		else{
//			System.out.println("failure");
//		}
//	}

	}

}


