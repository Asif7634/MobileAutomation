package com.cg.GenericComponents;

public class GenericComponents 
{
	public static void Wait(long Time)
	{
		try {
			Thread.sleep(Time);
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}
	}
}
