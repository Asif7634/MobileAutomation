package com.cg.ApplicationLevelComponent;

import java.io.IOException;

public class RemoteWebDriver 
{
	public RemoteWebDriver()
	{
		//startRemoteNode();
	}

	private void startRemoteNode() {
		try {
			Runtime rt = Runtime.getRuntime();	
			try {
				System.out.println("Starting Remote Node");
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
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
