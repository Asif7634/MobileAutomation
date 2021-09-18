package com.cg.Main;
import com.cg.TestCaseRunner.TestCaseRunner;

public class Main
{
	static Main mainObject=new Main();

	//start of execution
	public static void main(String[] args)
	{
		try{

			//Start point of execution
			//testcase runner runs each and every test cases selected
			TestCaseRunner TestRunner=new TestCaseRunner();
			TestRunner.getClass();
		}
		catch(Exception e)
		{
			System.err.println("Error-"+e.getCause().toString());
		}
	}
}
