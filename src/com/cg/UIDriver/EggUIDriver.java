package com.cg.UIDriver;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cg.ApplicationLevelComponent.EggPlant;
import com.cg.EggDriver.EggDriveException;
import com.cg.EggDriver.EggDriver;
import com.cg.Logutils.HtmlResult;
import com.cg.Logutils.Logging;
import com.cg.TestCaseRunner.TestCaseRunner;

public class EggUIDriver
{
	static public  String ImagefoundScale;

	public String imageActionPriority="";
	public String LastErrorMessage = "No Errors";
	public Logging logger= TestCaseRunner.TestCaseLogger;

	//contrast variables
	public String Contrast=EggPlant.getValueFromExcel("Contrast"),
			ContrastBlack=EggPlant.getValueFromExcel("ContrastBlack"),
			ContrastWhite=EggPlant.getValueFromExcel("ContrastWhite"),
			ContrastRed=EggPlant.getValueFromExcel("ContrastRed");

	//configuration variables
	static public int ImageWait=Integer.parseInt(EggPlant.getValueFromExcel("ImgWait"));
	static public String ImgWait=EggPlant.getValueFromExcel("ImgWait");
	static public String ImageFixedScale=EggPlant.getValueFromExcel("ImageFixedScale");
	static public String MinScale=EggPlant.getValueFromExcel("MinScale");
	static public String MaxScale=EggPlant.getValueFromExcel("MaxScale");
	static public String StepScaleCount=EggPlant.getValueFromExcel("StepScaleCount");

	public static String Suitepath=TestCaseRunner.DirPath+"\\FrameWork\\LookUp\\OR\\posAutomation.suite";
	public static String PerviouslyConnectedSystem = "";



	public EggDriver eggDriver=new EggDriver();

	public ArrayList<Integer> ImageLocation(String imageName) {
		try {
			int x, y;
			ArrayList<Integer> xy = new ArrayList<Integer>();

			imageName=imageName.trim();
			String ImgName=getUiImageName(imageName);
			List<Point> ImgLocation = eggDriver.ImageLocation("ImageName : \""+ ImgName +"\",scale:"+ImageFixedScale);//has collections with folder names without .png

			x = ImgLocation.get(0).x;
			y = ImgLocation.get(0).y;
			xy.add(x);
			xy.add(y);
			return xy;
		}
		catch (EggDriveException e)
		{
			LastErrorMessage = e.getMessage();
			HtmlResult.failed("To return image location", "Should be able to locate and return location of image-'"+imageName+"'", e.getMessage());
		}
		return null;
	}

	public boolean pressHomeButton()
	{
		try {
			eggDriver.pressHomeButton();
			return true;
		}
		catch (Exception e)
		{
			LastErrorMessage = e.getMessage();
			return false;
		}
	}

	public void takeScreenShot(String fileName) {
		try {
			eggDriver.CaptureScreen("Name: \"" + fileName + "\",increment:no");
		} catch (Exception e) {
			LastErrorMessage = e.getMessage();
			logger.error("TestCase", "Error while taking screenshots-"+LastErrorMessage);
		}
	}

	public boolean connect(String SystemName,String PortNum)
	{
		boolean ConnectionStatus=false;
		ConnectionStatus=eggDriver.connectDrive("127.0.0.1", 5400);
		eggDriver.overridePreviousSession=true;
		eggDriver.verboseLogging=true;
		int intPortNum=Integer.parseInt(PortNum.trim());

		if(ConnectionStatus)
		{
			String SuitePath=Suitepath;
			ConnectionStatus=eggDriver.startDriveSession(SuitePath);
			if(ConnectionStatus)
			{
				try{
					eggDriver.connect(SystemName,intPortNum);
					return true;
				}
				catch(EggDriveException e)
				{

					return false;
				}
			}
		}
		else
		{
			return false;
		}
		return false;

	}

	public boolean connect(String IpAddress,int portNum) throws InterruptedException
	{
		eggDriver.overridePreviousSession=true;
		eggDriver.verboseLogging=false;
		boolean ConnectionStatus=false;
		PerviouslyConnectedSystem = IpAddress+":"+Integer.toString(portNum);
		try{
			eggDriver.verboseLogging = true;
			eggDriver.overridePreviousSession = true;
			System.out.println("");
			ConnectionStatus=eggDriver.connectDrive("127.0.0.1", 5400);
			Thread.sleep(10000);
			if(ConnectionStatus)
			{
				Thread.sleep(2000);
				String SuitePath=Suitepath;

				ConnectionStatus=eggDriver.startDriveSession(SuitePath);
				Thread.sleep(2000);
				if(ConnectionStatus)
				{
					eggDriver.connect(IpAddress, portNum);
					return true;
				}
				else
				{
					return false;
				}
			}
			else
			{
				return false;
			}
		}
		catch(EggDriveException e)
		{
			LastErrorMessage = e.getMessage();
			logger.error("TestCase", "Error while connect-"+LastErrorMessage);
			return false;
		}
	}

	/*******************************************************************
	 *
	 */
	/*
	public boolean connect(String iPAddress, String port, String password) {

		boolean ConnectionStatus=false;
		eggDriver.overridePreviousSession=true;
		eggDriver.verboseLogging=true;
		ConnectionStatus=eggDriver.connectDrive("127.0.0.1", 5400);
		if(ConnectionStatus)
		{
			String SuitePath=Suitepath;
			ConnectionStatus=eggDriver.startDriveSession(SuitePath);
			if(ConnectionStatus)
			{
				try {
					eggDriver.connect(iPAddress, Integer.valueOf(port), null, null, password);
					return true;
				}
				catch (EggDriveException e)
				{
					return false;
				}
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}

	}*/

	public String getUiImageName(String ImageName)
	{
		if (!ImageName.isEmpty())
		{
			ImageName.trim();
			String UiImageName = "";
			List<Map<String, String>> ListOfMap = TestCaseRunner.uiMap;
			for (Map m : ListOfMap)
			{

				if (m.get("buttonName").toString().trim().equalsIgnoreCase(ImageName))
				{
					UiImageName = m.get("bitMap").toString().replace(".png", "").trim();
					break;
				}
			}
			return UiImageName;
		}
		return null;
	}

	public void wait(int Time)
	{
		try {
			eggDriver.wait(Time);
		} catch (EggDriveException e) {
			LastErrorMessage = e.getMessage();
			logger.error("TestCase", "Error while wait-"+LastErrorMessage);
		}
	}


	public List<Map<String,Object>> getImageInfo(String ImageName)
	{
		List<Map<String,Object>> ImageInfo ;
		try
		{
			String UiImageName = getUiImageName(ImageName);
			ImageInfo = eggDriver.imageInfo(UiImageName);
			return ImageInfo;
		}
		catch(EggDriveException e)
		{
			return null;
		}
		catch(Exception e)
		{
			return null;
		}
	}

	public List<Map<String,Object>> getTextInfo(String TextToSearch)
	{
		List<Map<String,Object>> TextInfo ;
		try
		{
			String UiTextToSearch = "text:\""+TextToSearch+"\"";
			TextInfo = eggDriver.textInfo(UiTextToSearch);
			return TextInfo;
		}
		catch(EggDriveException e)
		{
			return null;
		}
		catch(Exception e)
		{
			return null;
		}
	}



	public Boolean imageFound(String ImageName)
	{
		boolean FlagStatus =false;

		String ImgName=getUiImageName(ImageName);
		try {

			FlagStatus=eggDriver.imageFound(ImageWait,"ImageName : " + "\"" + ImgName + "\",scale:"+ImageFixedScale); // added by yogesh for handle return during failed case in report

			if(FlagStatus)
			{
				ImagefoundScale=ImageFixedScale;
				return FlagStatus;
			}
			else
			{
				double ScaleValue=Double.parseDouble(MinScale);
				double MaxScaleValue=Double.parseDouble(MaxScale);
				double StepScaleValue=Double.parseDouble(StepScaleCount);

				while(ScaleValue<=MaxScaleValue)
				{
					FlagStatus=eggDriver.imageFound(ImageWait,"ImageName : " + "\"" + ImgName + "\",scale:"+ScaleValue);
					if(FlagStatus)
					{
						ImagefoundScale=Double.toString(ScaleValue);
						ScaleValue=MaxScaleValue+1;
						return FlagStatus;
					}
					else
					{
						ScaleValue=ScaleValue+StepScaleValue;
						String strScaleValue = String.valueOf(ScaleValue);
						int strScaleValueLength = strScaleValue.length();
						if (strScaleValueLength > 6)
						{
							strScaleValue=strScaleValue.substring(0,5);
							ScaleValue = Double.parseDouble(strScaleValue);
						}
					}
				}
			}
		}
		catch (EggDriveException e) //UKIT
		{
			LastErrorMessage = e.getMessage();
			logger.error("TestCase", "Error while image found-"+LastErrorMessage);
			return false;
		}
		catch(NullPointerException e)
		{
			return false;
		}
		return FlagStatus;
	}

	public String readText(Rectangle r)
	{
		String Text="";
		try{
			Text=eggDriver.readText(r);
			return Text;
		}catch(EggDriveException e)
		{
			LastErrorMessage = e.getMessage();
			logger.error("TestCase", "Error while read text from "+r.toString()+"-"+LastErrorMessage);
			return "";
		}
	}

	public String readText(String Rectangle)
	{
		String Text=Rectangle;
		try{
			Text=eggDriver.readText(Text);
			return Text;
		}catch(EggDriveException e)
		{
			LastErrorMessage = e.getMessage();
			logger.error("TestCase", "Error while read text from "+Rectangle.toString()+"-"+LastErrorMessage);
			return "";
		}
	}

	public void setSearchRectangle(Rectangle r)
	{
		try
		{
			int X1 = r.x;
			int Y1 = r.y;
			int X2 = r.x+r.width;
			int Y2 = r.y+r.height;
			String Rectangle = "("+Integer.toString(X1)+","+Integer.toString(Y1)+","+Integer.toString(X2)+","+Integer.toString(Y2)+")";
			eggDriver.setSearchRectangle(Rectangle);
		}
		catch(EggDriveException e)
		{
			return;
		}
	}

	public List<Point> everyImageLocation(String imageName) {
		Map imageAttribs = getImageAttribs(imageName);
		try {
			return eggDriver.everyImageLocation((String) imageAttribs.get("bitMap"));
		} catch (EggDriveException e) {
			return null;
		}

	}

	public Map<String, Object> connectionInfo() {
		try {
			return eggDriver.connectionInfo();
		} catch (EggDriveException e) {
			return null;
		}

	}

	public String colourAtlocation(String commandName, String HotSpotPoint) throws EggDriveException {
		eggDriver.setValue("the colorFormat", "HTML");
		System.out.println(eggDriver.colouratlocation("text:\"" + commandName + "\",Hotspot:(" + HotSpotPoint + ")"));
		try {
			return eggDriver.colouratlocation("text:\"" + commandName + "\",Hotspot:(" + HotSpotPoint + ")");
		} catch (EggDriveException e) {
			return "";
		}
	}

	public String colourAtlocation(Point p) throws EggDriveException {
		eggDriver.setValue("the colorFormat", "Basic");
		int x = (int) p.getX() ;
		int y = (int) p.getY() ;
		String command;
		command = x + "," + y;

		try {
			return eggDriver.colouratlocation(command);
		} catch (EggDriveException e) {
			return "";
		}
	}


	public List<Point> everyImageLocation(String imageName, Rectangle r) {

		// Map imageAttribs = getImageAttribs(imageName);
		String imgName = "text:" + "\"" + imageName + "\",";
		String searchInRectangle = "SearchRectangle:(" + "\"" + r.x + "," + r.y + "," + r.width + "," + r.height + "\""+ ")";
		System.out.println(imgName + searchInRectangle);
		try {
			return eggDriver.everyImageLocation(imgName + searchInRectangle);
		} catch (EggDriveException e) {
			return null;
		}

	}

	//Method updated in eggUIDriver
	public Rectangle imageRectangle(String imageLeftTop, String imageRightBottom)
	{
		int x1 = -1, y1 = -1, x2 = -1, y2 = -1;

		if (imageFound(imageLeftTop)) {
			ArrayList<Integer> TopLeft = ImageLocation("LeftTopCorner"); //location top left of sales panel
			x1 = TopLeft.get(0);
			y1 = TopLeft.get(1);
		}

		if (imageFound(imageRightBottom)) {
			ArrayList<Integer> BottomRight = ImageLocation("RightBottomCorner"); //location of right bottom of sales panel
			x2 = BottomRight.get(0);
			y2 = BottomRight.get(1);
		}

		Rectangle rect = new Rectangle(x1, y1, x2, y2);
		return rect;
	}


	public List<Point> getEveryImageLocation(String imageName, Rectangle r) {

		String imgName = "text:" + "\"" + imageName + "\"";
		String searchInRectangle = "("+Integer.toString(r.x)+","+Integer.toString(r.y)+","+Integer.toString(r.width)+","+Integer.toString(r.height)+")";
		try {
			eggDriver.setValue("the searchrectangle", searchInRectangle);
			return eggDriver.everyTextLocation(imgName);
		} catch (EggDriveException e) {
			return null;
		}

	}

	public List<Point> getEveryTextLocation(String imageName) {

		String imgName = "text:" + "\"" + imageName + "\",validwords:\"*\"";
		try {
			return eggDriver.everyTextLocation(imgName);
		} catch (EggDriveException e) {
			return null;
		}
	}

	public List<Point> everyTextLocation(String imageName) {

		String imgName = "text:" + "\"" + imageName + "\"";
		try {
			return eggDriver.everyTextLocation(imgName);
		} catch (EggDriveException e) {
			return null;
		}
	}

	public List<Point> getTextLocation(String imageName) {

		String imgName = "text:" + "\"" + imageName + "\",validwords:\"*\"";
		try {
			return eggDriver.textLocation(imgName);
		} catch (EggDriveException e) {
			return null;
		}
	}


	public List<Point> getEveryTextLocation(String imageName,char Validwords) {

		String imgName = "text:" + "\"" + imageName + "\",validwords:\""+Validwords+"\"";
		try {
			return eggDriver.everyTextLocation(imgName);
		} catch (EggDriveException e) {
			return null;
		}
	}

	public List<Point> getEveryTextLocation(String imageName, int TextDifference) {

		try {
			String imgName = "text:" + "\"" + imageName + "\",validwords:\"*,TextDifference:"+Integer.toString(TextDifference)+"";
			return eggDriver.everyTextLocation(imgName);
		} catch (EggDriveException e) {
			return null;
		}
		catch (Exception e) {
			return null;
		}
	}

	public List<Point> getEveryTextLocation(String imageName ,String TextDifference) {

		String imgName = "text:" + "\"" + imageName + "\",validwords:\"*\",textDifference:\""+TextDifference+"\"";
		try {
			return eggDriver.everyTextLocation(imgName);
		} catch (EggDriveException e) {
			return null;
		}
	}
	public String readText(Rectangle r,String ValidWords)
	{
		//Abhishek[13-09-2016]
		String text = "";
		try {
			text =eggDriver.readText(r,"ValidWords:\""+ValidWords+"\"");
			return text;
		}
		catch (EggDriveException e)
		{
			LastErrorMessage = e.getMessage();
			logger.error("TestCase", "Error while read text from ' "+r.toString()+" ' and valid words- '"+ValidWords+"' '"+LastErrorMessage+"'");
			return "";
		}
	}

	public String readText(Point Location)
	{
		String text = "";
		try {
			text =eggDriver.readText(Location);
			return text;
		}
		catch (EggDriveException e)
		{
			LastErrorMessage = e.getMessage();
			logger.error("TestCase", "Error while read text from  at location-"+Location.toString()+"-"+e.getMessage()+"");
			return "";
		}
	}



	public String Readtext(Rectangle r,String ValidWords)
	{
		//Abhishek[13-09-2016]
		String text = "";
		try {
			text =eggDriver.readText(r,ValidWords);
			return text;
		}
		catch (EggDriveException e)
		{
			LastErrorMessage = e.getMessage();
			logger.error("TestCase", "Error while read text from ' "+r.toString()+" ' and valid words- '"+ValidWords+"' '"+LastErrorMessage+"'");
			return "";
		}
	}


	public boolean swipeLeft() {

		try {
			eggDriver.swipeLeft();
			return true;
		}
		catch (EggDriveException e)
		{
			LastErrorMessage = e.getMessage();
			logger.error("TestCase", "Error while swipeLeft ");
			return false;
		}

	}

	public boolean swipeLeft(Point p) {

		try {
			eggDriver.swipeLeft(p);
			return true;

		} catch (EggDriveException e) {
			LastErrorMessage = e.getMessage();
			logger.error("TestCase", "Error while swipeLeft from-' "+p.toString()+" '");
			return false;
		}
	}

	public boolean swipeLeft(String image) {

		String ImageName=getUiImageName(image.trim());

		if(ImageName.length()>0)
		{
			try {
				eggDriver.swipeLeft(ImageName);
				return true;
			}
			catch (EggDriveException e) {LastErrorMessage = e.getMessage();
			logger.error("TestCase", "Error while swipeLeft image-' "+image+" '");
			return false;}
		}
		else
		{
			return false;
		}
	}

	public boolean swipeRight() {

		try {
			eggDriver.swipeRight();
			return true;
		}
		catch (EggDriveException e)
		{
			return false;
		}
	}

	public boolean swipeRight(Point p) {

		try {
			eggDriver.swipeRight(p);
			return true;

		} catch (EggDriveException e) {
			return false;
		}
	}

	public boolean swipeRight(String image) {

		try {
			String ImageName=getUiImageName(image.trim());
			if(ImageName.length()>0)
			{
				eggDriver.swipeRight(ImageName);
				return true;
			}
			else
			{
				return false;
			}
		}
		catch (Exception e)
		{
			return false;
		}
	}


	public boolean swipeDown() {

		try {
			eggDriver.swipeDown();
			return true;
		}
		catch (EggDriveException e)
		{
			return false;
		}
	}

	public boolean swipeDown(Point p) {

		try {
			eggDriver.swipeDown(p);
			return true;
		}
		catch (EggDriveException e)
		{
			return false;
		}
	}

	public boolean swipeDown(String image) {
		try {
			String ImageName=getUiImageName(image.trim());
			if(ImageName.length()>0)
			{
				eggDriver.swipeDown(ImageName);
				return true;
			}
			else
			{
				return false;
			}
		}
		catch (Exception e)
		{
			return false;
		}
	}

	public boolean swipeUp() {

		try {
			eggDriver.swipeUp();
			return true;
		} catch (EggDriveException e) {
			return false;
		}
	}

	public boolean swipeUp(Point p) {

		try {
			eggDriver.swipeUp(p);
			return true;
		} catch (EggDriveException e) {
			return false;
		}
	}

	public boolean swipeUp(String image) {
		try {
			String ImageName= getUiImageName(image.trim());
			if(ImageName.length()>0)
			{
				eggDriver.swipeUp(ImageName);
				return true;
			}
			else
			{
				return false;
			}
		}
		catch (Exception e)
		{
			return false;
		}
	}

	public boolean touchToStart()
	{
		Point touchPoint=new Point(50,50);
		try {
			eggDriver.click(touchPoint);
			return true;
		} catch (EggDriveException e) {
			return false;
		}
	}


	public boolean typeText(String Text)
	{
		try {
			eggDriver.typeText(Text.trim());
			return true;
		} catch (EggDriveException e) {
			return false;
		}
	}

	public boolean disconnect()
	{
		try {
			setSearchRectangle(new Rectangle(remoteScreenSize()));
			eggDriver.disconnect();
			eggDriver.disconnectDrive();
			return true;
		}
		catch (EggDriveException e)
		{
			return false;
		}
		catch(Exception e)
		{
			eggDriver.disconnectDrive();
			return true;
		}
	}

	private void startEggDriveProcess() {
		try {
			stopEggDriverProcess();
			Runtime rt = Runtime.getRuntime();

			try {
				rt.exec("taskkill /f /im conhost.exe");
				Thread.sleep(5000);
				rt.exec("taskkill /f /im Eggplant.exe");
				String batFileName = "StartEggDrive.bat";
				rt.exec("cmd /c start " + batFileName);
				Thread.sleep(10000);
				rt.runFinalization();
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void stopEggDriverProcess(){
		try {
			Runtime rt = Runtime.getRuntime();

			try {
				Thread.sleep(5000);
				rt.exec("taskkill /f /im conhost.exe");
				Thread.sleep(5000);
				rt.exec("taskkill /f /im Eggplant.exe");
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getWebelementProperty(String pageTitle, String objectName)
	{
		if (!objectName.isEmpty())
		{
			String objName=objectName.trim();
			String pgTitle=pageTitle.trim();
			String objProperty="";
			List<Map<String, String>> ListOfMap = TestCaseRunner.WebElementPropertyMap;
			for (Map m : ListOfMap)
			{
				if(m.get("Page Tilte").toString().trim().equalsIgnoreCase(pgTitle))
				{
					objectName= m.get("Object Name").toString().trim();
					if (m.get("Object Name").toString().trim().equalsIgnoreCase(objName))
					{
						objProperty = m.get("Property").toString().trim();
						break;
					}
				}
			}
			return objProperty;
		}
		return null;
	}

	public static String getWebelementFormValues(String pageTitle, String objectName)
	{
		if (!objectName.isEmpty())
		{
			String objName=objectName.trim();
			String pgTitle=pageTitle.trim();
			String objProperty="";
			List<Map<String, String>> ListOfMap = TestCaseRunner.WebElementPropertyMap;
			for (Map m : ListOfMap)
			{
				if(m.get("Page Tilte").toString().trim().equalsIgnoreCase(pgTitle))
				{
					objectName= m.get("Object Name").toString().trim();
					if (m.get("Object Name").toString().trim().equalsIgnoreCase(objName))
					{
						if(m.containsKey("Value"))
						{
							objProperty = m.get("Value").toString().trim();
							break;
						}
					}
				}
			}
			return objProperty;
		}
		return null;
	}





	public boolean click(String image, String clicktype) {
		Boolean blnActionStatus = false;
		switch (clicktype.trim()) {
		case "Position":
			blnActionStatus = clickPoint(image);
			break;
		case "Image":
			blnActionStatus = clickImage(image);
			break;
		case "Text":
			blnActionStatus = clickText(image);
			break;
		default:
			blnActionStatus = click(image);
		}

		return blnActionStatus;
	}





	// Ashish
	public boolean clickPoint(String uiButtonName) {
		boolean blnReturn = false;
		Map imageAttribs = getImageAttribs(uiButtonName);
		try {
			if (imageAttribs.get("x").toString().isEmpty() == true
					|| imageAttribs.get("y").toString().isEmpty() == true) {
				blnReturn = false;
			} else {
				int Posi_X = Integer.parseInt(imageAttribs.get("x").toString());
				int Posi_Y = Integer.parseInt(imageAttribs.get("y").toString());
				Point ptCoordinates = new Point(Posi_X, Posi_Y);
				try {
					eggDriver.click(ptCoordinates);
					Thread.sleep(800);
					blnReturn = true;
				} catch (EggDriveException | InterruptedException e) {
				}
			}
		} catch (Exception e) {
		}
		return blnReturn;
	}



	public boolean clickPoint(Point p)
	{
		try
		{
			eggDriver.click(p);
			//log success
			return true;
		}
		catch(Exception e)
		{
			//log error
			return false;
		}
	}

	// -----------------------------------------------------------------------------------------------------------------------
	// Ashish
	// -----------------------------------------------------------------------------------------------------------------------
	// Yogesh
	public boolean clickImage(String uiButtonName) {
		boolean blnReturn = false;
		String strImageName =getUiImageName(uiButtonName.trim());
		//.getClass().String strImageName = imageAttribs.get("bitMap").toString().replace(".png", "").trim();

		if(true)
		{
			try
			{
				String strImageDescription = "ImageName:" + "\"" + strImageName + "\"" + ",scale:" + ImagefoundScale;
				eggDriver.clickImage(strImageDescription);
				blnReturn=true;
				return blnReturn;

			}
			catch (EggDriveException e)
			{

			}
		}

		return blnReturn;//UKIT
	}



	public Map getImageAttribs(String ButtonName)
	{
		List<Map<String, String>> UiDataList=TestCaseRunner.uiMap;
		Map<String,String> Details=new HashMap<String,String>();
		for(Map map:UiDataList)
		{
			String UiImageName=(String)map.get("buttonName");
			if(UiImageName.equalsIgnoreCase(ButtonName.trim()))
			{
				Details=map;
				break;
			}
		}
		return Details;
	}


	// -----------------------------------------------------------------------------------------------------------------------
	// Ashish
	public boolean clickText(String uiButtonName) {
		boolean blnReturn = false;
		String strImageName =uiButtonName;
		try {
			eggDriver.clickText(strImageName);
			blnReturn = true;
		} catch (EggDriveException e) {
			return false;
		}
		return blnReturn;
	}

	public boolean click(String image) {
		Boolean blnActionStatus = false;
		String arrActionPriority[] = {"Position","Image","Text"};
		for (int i = 0; i < arrActionPriority.length; i++) {
			switch (arrActionPriority[i].trim()) {
			case "Position":
				blnActionStatus = clickPoint(image);
				break;
			case "Image":
				blnActionStatus = clickImage(image);
				break;
			case "Text":
				blnActionStatus = clickText(image);
				break;
			}
			if (blnActionStatus == true) {
				break;
			}
		}
		return blnActionStatus;
	}

	/**
	 * Search for given images and return the rectangle object
	 *
	 * @param imageLeftTop
	 * @param imageRightBottom
	 * @return
	 */
	//	public Rectangle imageRectangle(String imageLeftTop, String imageRightBottom) {
	//		int x = -1, y = -1, w = -1, h = -1;
	//
	//		if (imageFound(imageLeftTop)) {
	//			ArrayList<Integer> imgLocationLT = ImageLocation(imageLeftTop);
	//			x = imgLocationLT.get(0);
	//			y = imgLocationLT.get(1);
	//		} else {
	//			//DriverLogger.handleError("Image '" + imageLeftTop + "' not found");
	//		}
	//
	//		if (imageFound(imageRightBottom)) {
	//			ArrayList<Integer> imgLocationRB = ImageLocation(imageRightBottom);
	//			w = imgLocationRB.get(0) - x;
	//			h = imgLocationRB.get(1) - y;
	//		} else {
	//			//DriverLogger.handleError("Image '" + imageRightBottom + "' not found");
	//		}
	//
	//		Rectangle rect = new Rectangle(x, y, w, h);
	//		return rect;
	//	}


	/**
	 * Provides the Dimension of Remotely connected McDMachine
	 *
	 * @return x,y Coordinates of Viewer Screen
	 *
	 */
	public Dimension remoteScreenSize() {
		try {
			return eggDriver.remoteScreenSize();
		} catch (EggDriveException e) {


			//logger.handleError("Error while retrieving remoteScreenSize", e);
		}
		return null;
	}

	public boolean clickTextInRectangle(String Text, Rectangle r) {
		boolean blnReturn = false;
		try {
			r.height = r.height + r.y;
			r.width = r.width + r.x;

			String textDescription = "Text:\"" + Text + "\",searchrectangle:(" + r.x + "," + r.height + "," + r.width
					+ "," + r.y + ")";

			eggDriver.clickImage(textDescription);
			blnReturn = true;
		} catch (EggDriveException e) {
			//	logger.trace("Failed to click Text with Text and search-type searchRectangle");
		}

		return blnReturn;
	}


	public boolean clickTextOnScreen(String Text) {

		try {
			String textDescription = "Text:\"" + Text + "\"";
			eggDriver.clickImage(textDescription);
			return true;
		} catch (EggDriveException e) {
			return false;
		}
	}



	//****************************************************

	// Sanjaya
	public Boolean imageFoundSearchRectangleText(String imageName) {
		// Map imageAttribs = getImageAttribs(imageName);
		String imgName = "Text:" + "\"" + imageName + "\",ValidWords:\"*\",ValidCharacters:\" \"";

		try {
			return eggDriver.imageFound(imgName);
		} catch (EggDriveException e) {
			return false;
		}

	}



	public Boolean imageFoundSearchRectangle(String imageName, Rectangle r) {
		String uiMapImgName =  getUiImageName(imageName);
		boolean FlagStatus=false;
		int WaitTime=Integer.parseInt(ImgWait);
		uiMapImgName = uiMapImgName.replace(".png", "");
		String searchInRectangle = "SearchRectangle:(" + "\"" + r.x + "," + r.y + "," + (r.x + r.width) + ","
				+ (r.y + r.height) + "\"" + ")";

		try {


			FlagStatus=eggDriver.imageFound("1","ImageName :"+uiMapImgName+",scale:"+ImageFixedScale,searchInRectangle);
			if(FlagStatus)
			{

				ImagefoundScale=ImageFixedScale;
				return FlagStatus;
			}
			FlagStatus=eggDriver.imageFound(WaitTime,"ImageName :"+uiMapImgName+",scale:1.0",searchInRectangle);
			if(FlagStatus)
			{

				ImagefoundScale="1.0";
				return FlagStatus;
			}
			else
			{
				double ScaleValue=Double.parseDouble(MinScale);
				double MaxScaleValue=Double.parseDouble(MaxScale);
				double StepScaleValue=Double.parseDouble(StepScaleCount);

				while(ScaleValue<=MaxScaleValue)
				{

					FlagStatus=eggDriver.imageFound(WaitTime,"ImageName :"+uiMapImgName+",scale:"+ScaleValue,searchInRectangle);
					if(FlagStatus)
					{
						ImagefoundScale=Double.toString(ScaleValue);
						ScaleValue=MaxScaleValue+1;
						return FlagStatus;
					}
					else
					{
						ScaleValue=ScaleValue+StepScaleValue;
					}

				}
			}
		}
		catch (EggDriveException e) //UKIT
		{
			logger.error("TestCase","Image not clicked with Image PNG file and given scale'" + e.getMessage() +"'");//UKIT

			return false;

		}
		return FlagStatus;
	}


	/***************************
	 *
	 *
	 * @param actionButtonName
	 * @param searchArea
	 * @return
	 */
	public boolean clickinSearchRectangle(String actionButtonName, Rectangle searchArea) {
		boolean blnFlag = false;
		String uiMapImage = getUiImageName(actionButtonName);
		uiMapImage = uiMapImage.replace(".png", "");
		try {
			eggDriver.clickImage("imageName:\"" + uiMapImage+"\",scale:" + ImagefoundScale+"," + "searchRectangle:(" + searchArea.x + ","
					+ searchArea.y + "," + searchArea.width + "," + searchArea.height + ")");
			blnFlag=true;

		} catch (EggDriveException e) {
			logger.error("TestCase","Image in specified search area not clicked : '" + e.getMessage() +"'");
		}

		return blnFlag;
	}

	public List<Point> getLocation()
	{
		try
		{
			return eggDriver.getLocation();
		}
		catch(EggDriveException e)
		{
			return null;
		}
	}


	public boolean waitImageFound(String waitTime,String imageName)


	{

		String uiMapImage = getUiImageName(imageName);
		boolean FlagStatus =false;
		int WaitTime=Integer.parseInt(ImgWait);
		if(uiMapImage==null)
		{
			FlagStatus=false;
		}
		else{
			uiMapImage = uiMapImage.replace(".png", "");
			try {

				FlagStatus=eggDriver.imageFound(WaitTime,"ImageName : " + "\"" + uiMapImage + "\",scale:"+ImageFixedScale);
				if(FlagStatus)
				{
					ImagefoundScale=ImageFixedScale;
					return FlagStatus;
				}

				else
				{
					double ScaleValue=Double.parseDouble(MinScale);
					double MaxScaleValue=Double.parseDouble(MaxScale);
					double StepScaleValue=Double.parseDouble(StepScaleCount);

					while(ScaleValue<=MaxScaleValue)
					{
						FlagStatus=eggDriver.imageFound(WaitTime,"ImageName : " + "\"" + uiMapImage + "\",scale:"+ScaleValue);
						if(FlagStatus)
						{
							ImagefoundScale=Double.toString(ScaleValue);
							ScaleValue=MaxScaleValue+1;
							return FlagStatus;
						}
						else
						{
							ScaleValue=ScaleValue+StepScaleValue;
						}

					}
				}
			}
			catch (EggDriveException e) //UKIT
			{
				logger.error("TestCase","Image not clicked with Image PNG file and given scale -' " + e.getMessage() + "'");//UKIT
			}
		}
		return FlagStatus;
	}

	//moblie functions
	public boolean launchApp(String AppName)
	{
		try
		{
			eggDriver.launchApp(AppName.trim());
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}

}
