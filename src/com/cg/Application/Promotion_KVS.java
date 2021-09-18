package com.cg.Application;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cg.ApplicationLevelComponent.EggPlant;
import com.cg.Logutils.HtmlResult;
import com.cg.TestCaseRunner.TestCaseRunner;
import com.cg.UIDriver.EggUIDriver;

public class Promotion_KVS extends EggPlant
{
	EggPlant eggPlant = new EggPlant();

	public void kVSScreenSize(Map<String,String> input)
	{
		String Kvsname=input.get("kVSName");
		Dimension ScreenSize;
		if(Kvsname.equalsIgnoreCase("fc expo"))
		{
			ScreenSize=eggUIDriver.remoteScreenSize();
			int height=ScreenSize.height;
			int width=ScreenSize.width;

			if(height>=0 && width>=0)
			{
				System.out.println("heigth="+height+",width="+width);
				HtmlResult.passed("","","heigth="+height+",width="+width);
			}
			else
			{
				System.out.println("heigth="+height+",width="+width);
				HtmlResult.failed("FAILED","",height+","+width);
			}
		}
	}

	public void verifyKVSorder(Map<String,String> input)
	{
		String KvsList="";
		String PassedKvs = "";
		String FailedKvs= "";
		String strDescription="To verify KVS routing";
		String strExpectedResult="";

		try
		{
			//variables from excel
			String ExpectedProductKVSName=input.get("productKVSName").trim();
			String ProductCode = input.get("productCode").trim();
			String PreviouslyConnctedSystem = input.get("connectedSystemName").trim();

			//getting list of KVS's from globle map
			String[] KVSList=KvsList.split(",");//splitting list into array

			for(String ActualKvsName:KVSList)
			{

				boolean blnConnectionStatus = connectToKvs(ActualKvsName.trim()); //connecting to the Kvs
				if(blnConnectionStatus)
				{
					String ExpectedKvsNames = getExpectedKvsNameforProduct(ProductCode);
					strExpectedResult = "Order should be present on Kvs's "+ExpectedKvsNames;
					String[] ActualKvsData=readKvsScreen().trim().split("\n"); // reading the content of KVS
					String[] arrProductKVSName=ExpectedProductKVSName.trim().split(","); //expected name on KVS

					if(ExpectedKvsNames.contains(ActualKvsName))
					{
						boolean kvsScreenVerification = verifyKvsScreen(arrProductKVSName,ActualKvsData);
						if(kvsScreenVerification)
						{
							PassedKvs=PassedKvs.concat(ActualKvsName+",");
						}
						else
						{
							FailedKvs=FailedKvs.concat(ActualKvsName+",");
						}
					}
					else
					{
						boolean kvsScreenVerification = verifyKvsScreen(arrProductKVSName,ActualKvsData);
						if(kvsScreenVerification)
						{
							FailedKvs=FailedKvs.concat(ActualKvsName+",");
						}
						else
						{
							PassedKvs=PassedKvs.concat(ActualKvsName+",");
						}
					}
				}
				else
				{
					HtmlResult.failed("","" , "Connection failed");
				}
			}

			if(PassedKvs.length()>0)
			{
				PassedKvs=PassedKvs.substring(0,PassedKvs.length()-1);
				HtmlResult.passed(strDescription, strExpectedResult, "Passed for-"+PassedKvs);
			}
			if(FailedKvs.length()>0)
			{
				FailedKvs=FailedKvs.substring(0,FailedKvs.length()-1);
				HtmlResult.failed(strDescription, strExpectedResult, "Failed For-"+FailedKvs);
			}

			//again connecting to the POS/NGK
			eggPlant.disconnect();
			Map<String,String> Objects = new HashMap<String,String>();
			Objects.put("SystemName", PreviouslyConnctedSystem);
			boolean ConnectionToPreviouslyConnectedSystem = eggPlant.connect(Objects);


			if(ConnectionToPreviouslyConnectedSystem)
			{
				;
			}
			else
			{
				HtmlResult.failed("", "", "failed");
			}
		}
		catch (Exception e)
		{
			e.getMessage();
		}

	}

	// verify sequencing of ordered product on KVS
	public void verifySequencing()
	{
		String strDescription = "To verify sequencing of products on KVS";
		String strExpectedResult = "Should be able to verify sequencing on kvs";
		String SideScreenNames = "";

		//getting side name
		String VerifyOat1 = getValueFromExcel("VerifyOat1");
		String VerifyOat2 = getValueFromExcel("VerifyOat2");
		if(VerifyOat1.equalsIgnoreCase("YES") && VerifyOat2.equalsIgnoreCase("NO"))
		{
			SideScreenNames = "OAT-1";
		}
		else if(VerifyOat2.equalsIgnoreCase("YES") && VerifyOat1.equalsIgnoreCase("NO"))
		{
			SideScreenNames = "OAT-2";//OAT-2 is not Connecting 10.116.9.100
		}
		else if(VerifyOat1.equalsIgnoreCase("YES") && VerifyOat2.equalsIgnoreCase("YES"))
		{
			SideScreenNames = "OAT-1#OAT-2";//OAT-2 is not Connecting 10.116.9.100
		}
		else
		{
			HtmlResult.failed(strDescription,"Should be able to connect -"+SideScreenNames , "Please provide Yes or No on parameters(VerifyOat1 && VerifyOat2) on PropertyMap.xlsx file in routing sheet");
		}

		try
		{
			String [] arrKvsNames = SideScreenNames.split("#");
			String OrdersOnKvs = "";

			for(String KvsName : arrKvsNames )
			{
				boolean blnStatus = connectToKvs(KvsName);
				if(blnStatus)
				{
					OrdersOnKvs = OrdersOnKvs.concat(readKvsScreen()+"\n");
				}
			}

			String [] ArrOrdersOnKvs = OrdersOnKvs.split("\n");
			ArrayList<String> ActualOrderOnKvs = new ArrayList<String>();
			for(String StringVariable : ArrOrdersOnKvs)
			{
				String TempStringVariable = StringVariable.replace(" ", "").replaceAll("-", "").replaceAll("\\+", "").replaceAll("\\/", "").replaceAll("\\(", "");
				if(TempStringVariable.matches("[0-9]{1}[A-Z]{2,}"))
				{
					if(StringVariable.endsWith(" I"))
					{
						StringVariable = StringVariable.substring(0, StringVariable.length()-1);
					}

					ActualOrderOnKvs.add(StringVariable.trim());
				}
				else if(TempStringVariable.matches("[0-9]{1}[A-Z]{2,}[x][0-9]{1,}"))
				{
					ActualOrderOnKvs.add(StringVariable.trim());
				}
			}

			if(!(OrdersOnKvs.length()>3) || ActualOrderOnKvs.size()>0)
			{
				List<String> SequencingNumbers = new ArrayList<String>();
				List<String> ProductList = new ArrayList<String>();
				String NoDisplayCodeForOrder = "";

				for(String Order : ActualOrderOnKvs)
				{
					Order = Order.trim();
					Order = Order.replaceFirst("[1-9]", "").trim();
					String SequencingNumberOfActualOrder = getSequencingNumber(Order);

					if(SequencingNumberOfActualOrder!=null && SequencingNumberOfActualOrder.length()>1 && (!SequencingNumberOfActualOrder.equals("")))
					{
						SequencingNumbers.add(SequencingNumberOfActualOrder);
						ProductList.add(Order);
					}
					else{
						NoDisplayCodeForOrder = NoDisplayCodeForOrder.concat(Order+" , ");
					}
				}

				if(SequencingNumbers.size()>0 && ProductList.size()>0 && SequencingNumbers.size()==ProductList.size() )
				{
					int LoopLimit = SequencingNumbers.size();
					String SequencingVerifiedForProducts = "";
					String SequencingNotVerifiedForProducts = "";

					for(int Index = 0; Index<=LoopLimit-1;Index++)
					{
						try {

							if(Index+1<=LoopLimit-1)
							{
								String CurrentProductName = ProductList.get(Index);
								String NextProductName = ProductList.get(Index+1);
								int CurrentSequencingNumber = Integer.parseInt(SequencingNumbers.get(Index));
								int NextSequencingNumber = Integer.parseInt(SequencingNumbers.get(Index+1));

								if(CurrentSequencingNumber<=NextSequencingNumber)
								{
									SequencingVerifiedForProducts = SequencingVerifiedForProducts.concat(CurrentProductName+"("+CurrentSequencingNumber+") and "+NextProductName+"("+NextSequencingNumber+") , ");
								}
								else
								{
									SequencingNotVerifiedForProducts = SequencingNotVerifiedForProducts.concat(CurrentProductName+"("+CurrentSequencingNumber+") and "+NextProductName+"("+NextSequencingNumber+") , ");
								}
							}

						} catch (ArrayIndexOutOfBoundsException e) {
							break;
						}
					}

					SequencingVerifiedForProducts = SequencingVerifiedForProducts.replaceAll("\n"," ");
					SequencingNotVerifiedForProducts = SequencingNotVerifiedForProducts.replaceAll("\n", " ");

					if(SequencingVerifiedForProducts.length()>=0)
					{
						HtmlResult.passed(strDescription, strExpectedResult, "Sequencing is verified for products-' "+SequencingVerifiedForProducts+"' ");
					}

					if(SequencingNotVerifiedForProducts.length()>=0 && SequencingNotVerifiedForProducts.matches("[A-Z]{1,}"))
					{
						HtmlResult.failed(strDescription, strExpectedResult, "Sequencing is not verified for products-' "+SequencingNotVerifiedForProducts+"' ");
					}
				}

				if(NoDisplayCodeForOrder.length()>0 )
				{
					HtmlResult.warning(strDescription, strExpectedResult, "Display code not found for Products '"+NoDisplayCodeForOrder+"'" );
				}
			}
			else
			{
				HtmlResult.failed(strDescription, strExpectedResult, "No orders present on KVS side 1 and KVS side 2");
			}


		}
		catch(Exception e)
		{
			HtmlResult.failed(strDescription, strExpectedResult, "Error while verifying sequencing on KVS");
		}

	}

	public String getSequencingNumber(String ActualOrder)
	{
		String SequencingNumber = "";
		try
		{
			List<Map<String,String>> ReplicaOfKvsMap = TestCaseRunner.KvsMap;

			for(Map<String,String>ProductDetails :  ReplicaOfKvsMap)
			{
				String ProductDesc = ProductDetails.get("KVSDescription");

				if(ProductDesc.equals(ActualOrder))
				{
					SequencingNumber = ProductDetails.get("DisplayCode");
					break;
				}
			}

			return SequencingNumber;
		}
		catch(Exception e)
		{
			return "";
		}
	}
























	//components region ends














































	//method to get expected kvs name for a product code
	public String getExpectedKvsNameforProduct(String ProductCode)
	{
		String ExpectedKvsNames = "";
		try
		{
			ProductCode = ProductCode.trim();
			List<Map<String,String>> KvsMap = TestCaseRunner.KvsMap;//replica of global kvs map

			for(Map<String,String> map : KvsMap )
			{
				String Value = map.get("ProductCode");
				if(ProductCode.equals(Value))
				{
					Set<String> KeySet = map.keySet();
					for(String Key : KeySet)
					{
						String KeyValue = map.get(Key);
						if(KeyValue.equalsIgnoreCase("yes"))
						{
							ExpectedKvsNames = ExpectedKvsNames.concat(Key+",");
						}
					}
				}

			}

			if(ExpectedKvsNames.length()>0)
			{
				ExpectedKvsNames = ExpectedKvsNames.substring(0,ExpectedKvsNames.length()-1);
				return ExpectedKvsNames;
			}

			return ExpectedKvsNames;

		}
		catch(Exception e)
		{
			ExpectedKvsNames = "";
			return ExpectedKvsNames;
		}
	}


	public boolean verifyKvsScreen(String[] arrExpectedData,String[] arrActualData)
	{
		try
		{
			int VerificationCounter = 0;
			int ExpectedDataCount = arrExpectedData.length;
			int ActualDataCount = arrActualData.length;

			for(String ExpectedData : arrExpectedData)
			{
				for(int Index = 0; Index<ActualDataCount ;Index++)
				{
					if(ExpectedData.equals(arrActualData[Index]))
					{
						VerificationCounter++;
					}
				}
			}

			if(VerificationCounter == ExpectedDataCount)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(Exception e)
		{
			return false;
		}
	}

	// method to connect to specific KVS
	public boolean connectToKvs(String SystemName)
	{
		try
		{
			boolean blnFlag = eggUIDriver.disconnect();

			if(blnFlag)
			{
				SystemName = SystemName.trim();
				String IpAddressAndPortNum = getValueFromExcel(SystemName);
				String [] arrIpAddressAndPortNum = IpAddressAndPortNum.split(":");
				String IpAddress = "";
				String portNum = "";

				if(arrIpAddressAndPortNum.length>=2)
				{
					IpAddress = arrIpAddressAndPortNum[0];
					portNum = arrIpAddressAndPortNum[1];
				}
				else if(arrIpAddressAndPortNum.length == 1)
				{
					if(arrIpAddressAndPortNum[0].contains("."))
					{
						IpAddress = arrIpAddressAndPortNum[0];
						portNum = "5901";
					}
					else
					{
						IpAddress = "";
						portNum = arrIpAddressAndPortNum[0];
					}
				}

				int intPortNum=Integer.parseInt(portNum);
				boolean ConnectionStatus=eggUIDriver.connect(IpAddress.trim(),intPortNum);
				if(ConnectionStatus)
				{
					return true;
				}
				else
				{
					String PreviousSystem=EggUIDriver.PerviouslyConnectedSystem;
					String[] arrPreviousSystem = PreviousSystem.split(":");
					String strIpAddress = arrPreviousSystem[0];
					int PortNumber = Integer.parseInt(arrPreviousSystem[1]);
					eggUIDriver.connect(strIpAddress.trim(),PortNumber);
					return false;
				}
			}
			else
			{
				return false;
			}
		}
		catch(Exception e)
		{
			return false;
		}
	}

	public String readKvsScreen()
	{
		String OrderData="";
		int KvsScreenDivision = Integer.parseInt(getValueFromExcel("KvsScreenDivisions"));
		Rectangle OrderRect=new Rectangle();
		Dimension ScreenSize=new Dimension();

		int OriginX=1;
		int OriginY=1;

		ScreenSize=eggUIDriver.remoteScreenSize();

		int ScreenHeight=ScreenSize.height-24;
		int ScreenWidth=ScreenSize.width;
		ScreenHeight=ScreenHeight-(int)(ScreenHeight*0.022);

		int OrderWidth=ScreenWidth/KvsScreenDivision;
		int OrderHeight=ScreenHeight/2;


		OrderRect.x = OriginX;
		OrderRect.y = OriginY;
		OrderRect.height=OrderHeight+1;
		OrderRect.width=OrderWidth+1;


		for(int Index=0;Index<=7;Index++)
		{
			if(OrderRect.x>=ScreenWidth && OrderRect.y>=ScreenHeight)
			{
				break;
			}
			eggUIDriver.setSearchRectangle(OrderRect);
			OrderData = OrderData+"\n"+eggUIDriver.readText(OrderRect);
			OrderData = OrderData.replaceAll("\n\n", "\n");
			OrderData = OrderData.replaceAll("\t", " ");
			OrderData = OrderData.replace("|", "");
			OrderData=OrderData.replaceAll("!", "");
			OrderData=OrderData.replaceAll("[a-w]{1,}", "");
			OrderData=OrderData.replaceAll("[y-z]{1,}", "");
			OrderRect.x= OrderRect.x +OrderRect.width;
			if(OrderRect.x>=ScreenWidth)
			{
				OrderRect.x=1;
				OrderRect.y=OrderRect.y+OrderRect.height;
			}
		}
		eggUIDriver.setSearchRectangle(new Rectangle(ScreenSize));
		return OrderData;
	}

	//routing
	public void verifyRouting(Map<String,String> Input)
	{
		try
		{
			String PrductsDescription = Input.get("MenuAndProductName").trim();
			String DescNotFound = "";
			String[] MenuAndProductName = PrductsDescription.split(";");
			List<String> ProductsList = new ArrayList<String>();
			String VerifyOat1 = getValueFromExcel("VerifyOat1");
			String VerifyOat2 = getValueFromExcel("VerifyOat2");
			String PerformNegativeTesting = getValueFromExcel("Perform Negative Testing");
			String PerformPositiveTesting = getValueFromExcel("Perform Positive Testing");
			String TakeKvsScreenShots = getValueFromExcel("TakeKvsScreenShots");
			String ActualSideName = "";
			boolean PerformTest = false;


			if(VerifyOat1.equalsIgnoreCase("YES") && VerifyOat2.equalsIgnoreCase("NO"))
			{
				ActualSideName = "S1";
				PerformTest = true;
			}
			else if(VerifyOat1.equalsIgnoreCase("NO") && VerifyOat2.equalsIgnoreCase("YES"))
			{
				ActualSideName = "S2";
				PerformTest = true;
			}
			else if(VerifyOat1.equalsIgnoreCase("YES") && VerifyOat2.equalsIgnoreCase("YES"))
			{
				ActualSideName = "S1";
				PerformTest = true;
			}
			else if(! VerifyOat1.equalsIgnoreCase("NO") || !VerifyOat2.equalsIgnoreCase("NO"))
			{
				HtmlResult.failed("To verify routing of products", "Routing sholud be verified", "Wrong Value passed to parameters VerifyOat1 and VerifyOat2 in propertymap.xlsx file -"+VerifyOat1+", Plase provide either YES or NO");
				PerformTest = false;
			}

			if(PerformPositiveTesting.equalsIgnoreCase("YES") || PerformNegativeTesting.equalsIgnoreCase("NO"))
			{
				PerformTest = true;
			}
			else if(PerformPositiveTesting.equalsIgnoreCase("NO") || PerformNegativeTesting.equalsIgnoreCase("YES"))
			{
				PerformTest = true;
			}
			else
			{
				HtmlResult.failed("To verify routing of products", "Routing sholud be verified", "Wrong Value passed to parameters PerformPositiveTesting and PerformNegativeTesting in propertymap.xlsx file those are-"+PerformPositiveTesting+" and "+PerformNegativeTesting+", Plase provide only YES or NO");
				PerformTest = false;
			}

			if(PerformTest)
			{
				//creating list of Products
				for(String MenuAndProducts : MenuAndProductName)
				{
					String[] strArrMenuAndProducts = MenuAndProducts.split(":");
					if(strArrMenuAndProducts.length==2)
					{
						String[] arrProducts = strArrMenuAndProducts[1].split("#");
						for(String Products : arrProducts)
						{
							if(hasSubCategory(Products))
							{
								continue;
							}
							else
							{
								ProductsList.add(Products);
							}

						}
					}
				}

				//to perform positive testing
				if(PerformPositiveTesting!=null && PerformPositiveTesting.equalsIgnoreCase("YES"))
				{
					for(String ProductName : ProductsList)
					{
						ProductName = ProductName.trim();
						String strExpectedResult = "";
						String strDescription = "";
						List<String> ExpectedKvsNames = getExpectedKvsNames(ProductName, "yes",ActualSideName);
						String ProductDesc = getProductKvsDescFromUiName(ProductName.trim());

						if(ProductDesc!=null && ProductDesc.length()>2)
						{
							strExpectedResult = "Routing for '"+ProductDesc+"' should be successful for KVS's-"+ExpectedKvsNames.toString();
							strDescription = "To verify routing of product-"+ProductDesc;
							String OrderPresentOnKvs = "";
							String OrderNotPresentOnKVs = "";

							for (String KvsName : ExpectedKvsNames)
							{
								if(KvsName.equalsIgnoreCase("FC expo 1") || KvsName.equalsIgnoreCase("FC expo 2") )
								{
									continue;
								}

								boolean blnStatus = connectToKvs(KvsName);
								if (blnStatus)
								{
									boolean TextFound = findTextOnScreen(ProductDesc);

									if (TextFound)
									{
										OrderPresentOnKvs = OrderPresentOnKvs.concat(KvsName+" ,");
									}
									else
									{
										OrderNotPresentOnKVs = OrderNotPresentOnKVs.concat(KvsName+" ,");
									}
								}
								else {
									HtmlResult.failed(strDescription, strExpectedResult, "Unable to connect to KVS-"+KvsName);//unable to connect to KVS
								}
							}

							if(OrderPresentOnKvs.length()>0)
							{
								HtmlResult.passed(strDescription, strExpectedResult, "Routing for product '"+ProductDesc+"' is successful on KVS's-' "+OrderPresentOnKvs+"'");
							}
							if(OrderNotPresentOnKVs.length()>0)
							{
								HtmlResult.failed(strDescription, strExpectedResult, "Routing for product '"+ProductDesc+"' is not successful on KVS's-' "+OrderNotPresentOnKVs+"'");
							}
						}
						else
						{
							DescNotFound = DescNotFound.concat(ProductName+",");
						}
					}

					if(DescNotFound.length()>4)
					{
						HtmlResult.warning("To verify routing", "Routing of products sholud be successful", "KVS description is not found for uibuttons-' "+DescNotFound+" '");
					}
				}

				//for negative scenario
				if(PerformNegativeTesting!=null && PerformNegativeTesting.equalsIgnoreCase("YES"))
				{
					for(String ProductName : ProductsList)
					{
						ProductName = ProductName.trim();
						String strExpectedResult = "";
						String strDescription = "";

						//perform negative scenario from excel parameter
						List<String> NotExpectedKvsNames = getExpectedKvsNames(ProductName, "No",ActualSideName);
						String ProductDesc = getProductKvsDescFromUiName(ProductName);

						if(ProductDesc!=null && ProductDesc.length()>2)
						{
							String OrderPresentOnKvs = "";
							String OrderNotPresentOnKVs = "";
							strDescription = "To verify routing of product-"+ProductDesc;
							strExpectedResult = "Routing for '"+ProductDesc+"' should not be successful for KVS's-"+NotExpectedKvsNames.toString();

							for (String KvsName : NotExpectedKvsNames) {

								if(KvsName.equalsIgnoreCase("FC expo 1") || KvsName.equalsIgnoreCase("FC expo 1") )
								{
									continue;
								}

								boolean blnStatus = connectToKvs(KvsName);

								if (blnStatus)
								{
									boolean TextFound = findTextOnScreen(ProductDesc);

									if (TextFound)
									{
										OrderPresentOnKvs = OrderPresentOnKvs.concat(KvsName+" ,");
									}
									else
									{
										OrderNotPresentOnKVs = OrderNotPresentOnKVs.concat(KvsName+" ,");
									}

								} else {
									HtmlResult.failed(strDescription, strExpectedResult, "Unable to connect to KVS-"+KvsName);//unable to connect to KVS
								}
							}

							if(OrderNotPresentOnKVs.length()>0)
							{
								HtmlResult.passed(strDescription, strExpectedResult, "Routing for product '"+ProductDesc+"' is not successful on KVS's-' "+OrderNotPresentOnKVs+"'");
							}
							if(OrderPresentOnKvs.length()>0)
							{
								HtmlResult.failed(strDescription, strExpectedResult, "Routing for product '"+ProductDesc+"' is successful on KVS's-' "+OrderPresentOnKvs+"'");
							}
						}
						else
						{
							DescNotFound = DescNotFound.concat(ProductName+",");
						}
					}
					if(DescNotFound.length()>4)
					{
						HtmlResult.warning("To verify routing", "Routing of products sholud be successful", "KVS description is not found for uibuttons-' "+DescNotFound+" '");
					}
				}
			}

			if(TakeKvsScreenShots.equalsIgnoreCase("YES"))
			{
				String KvsList = getValueFromExcel("KVS_NAMES");
				String RowContent = "";
				String [] arrKvsList = KvsList.split("#");
				int Sections = 100/arrKvsList.length;

				for(String KvsName:arrKvsList)
				{
					Date CurrentTime = new Date();
					DateFormat TimeFormat=new SimpleDateFormat("HH:mm:ss a");
					String FinalImageName = HtmlResult.ResultFolderName+"//ScreenShots//"+TimeFormat.format(CurrentTime.getTime()).replaceAll(":", "_")+"_"+KvsName;

					if(connectToKvs(KvsName))
					{
						eggUIDriver.takeScreenShot(FinalImageName);
						String KvsImageLink = FinalImageName.replaceAll(" ", "%20")+".png";
						RowContent = RowContent.concat("<td width="+Sections+"% align=center ><a href=\""+KvsImageLink+"\">"+KvsName+"</a></td>");
					}
					else
					{
						RowContent = RowContent.concat("<td width="+Sections+"% align=center ><a><font color='red'>"+KvsName+"</font></a></td>");
					}

				}

				HtmlResult.addTable(RowContent);
			}


		}
		catch(Exception e)
		{
			HtmlResult.failed("To verify routing of products", "Routing for products should be successful", "Unable to verify routing '"+e.getMessage()+"'");
		}
	}

	public Map<String,String> getProductKvsDetailsFromUiName(String ProductName)
	{
		try
		{
			ProductName = ProductName.trim();
			List<Map<String,String>> ReplicaOfKvsMap = TestCaseRunner.KvsMap;
			for(Map<String,String> map:  ReplicaOfKvsMap)
			{
				String UiButtonName = map.get("UibuttonName");
				if(UiButtonName.equalsIgnoreCase(ProductName))
				{
					return map;
				}
			}
		}
		catch(Exception e)
		{
			return null;
		}
		return null;
	}

	public Map<String,String> getProductInfoFromDesc(String ProductDesc)
	{
		try
		{
			List<Map<String,String>> ReplicaOfKvsMap = TestCaseRunner.KvsMap;
			for(Map<String,String> map:  ReplicaOfKvsMap)
			{
				String KVSDescription = map.get("KVSDescription");
				if(KVSDescription.equalsIgnoreCase(ProductDesc))
				{
					return map;
				}
			}
		}
		catch(Exception e)
		{
			return null;
		}
		return null;
	}

	public Map<String,String> getProductInfoFromUiButtonName(String ProductDesc)
	{
		try
		{
			List<Map<String,String>> ReplicaOfKvsMap = TestCaseRunner.KvsMap;
			for(Map<String,String> map:  ReplicaOfKvsMap)
			{
				String UiName = map.get("UibuttonName");
				if(UiName.equalsIgnoreCase(ProductDesc))
				{
					return map;
				}
			}
		}
		catch(Exception e)
		{
			return null;
		}
		return null;
	}

	public String getProductKvsDescFromUiName(String ProductName)
	{
		try
		{
			List<Map<String,String>> ReplicaOfKvsMap = TestCaseRunner.KvsMap;
			for(Map<String,String> map:  ReplicaOfKvsMap)
			{
				String UiButtonName = map.get("UibuttonName");
				if(UiButtonName.equalsIgnoreCase(ProductName))
				{
					return map.get("KVSDescription");
				}
			}
		}
		catch(Exception e)
		{
			return null;
		}
		return null;
	}

	public List<String> getExpectedKvsNames(String ParamUiButtonName,String Parameter,String SideName)
	{
		List<String> ExpectedKvsNames = new ArrayList<String>();
		try
		{
			List<Map<String,String>> ReplicaOfKvsMap = TestCaseRunner.KvsMap;

			for(Map<String,String> map:  ReplicaOfKvsMap)
			{
				String UiButtonName = map.get("UibuttonName");
				if(UiButtonName.equalsIgnoreCase(ParamUiButtonName.trim()))
				{
					Set<String> KeySet = map.keySet();
					for(String Key : KeySet)
					{
						String Value = map.get(Key);

						if(Value.equalsIgnoreCase("YES")||Value.equalsIgnoreCase("NO"))
						{
							if(SideName.equalsIgnoreCase("S1"))
							{
								if(Key.contains("1") && !Key.contains("Drink"))
								{
									map.put(Key, "yes");
								}
								else if(Key.contains("2") && !Key.contains("Drink"))
								{
									map.put(Key, "no");
								}
							}
							else if(SideName.equalsIgnoreCase("S2"))
							{
								if(Key.contains("2") && !Key.contains("Drink"))
								{
									map.put(Key, "yes");
								}
								else if(Key.contains("1") && !Key.contains("Drink"))
								{
									map.put(Key, "no");
								}
							}

						}
					}


					String KvsColour = map.get("KVSColour");
					map.get("DisplayCode");
					if(KvsColour.equalsIgnoreCase("WHITE") || KvsColour.equalsIgnoreCase("YELLOW") || KvsColour.equalsIgnoreCase("RED") )
					{
						Set<String> NewKeySet = map.keySet();
						for(String NewKey : NewKeySet)
						{
							if(NewKey.contains("MFY"))
							{
								map.put(NewKey, "no");
							}
						}
					}

					for(String Key : KeySet)
					{
						String Value = map.get(Key);
						if(Value.equalsIgnoreCase(Parameter))
						{
							ExpectedKvsNames.add(Key);
						}
					}

				}
			}

			//to remove duplicates
			Set<String> HashSet = new HashSet<>();
			HashSet.addAll(ExpectedKvsNames);
			ExpectedKvsNames.clear();
			ExpectedKvsNames.addAll(HashSet);

			return ExpectedKvsNames;
		}
		catch(Exception e)
		{
			return null;
		}

	}

	//color code verification
	public void verifyColourCodes(Map<String,String> Input)
	{
		List<Map<String,String>> ProductsInformation = new ArrayList<Map<String,String>>();
		boolean colourFound = false;

		ArrayList<String> Menus = new ArrayList<String>();
		ArrayList<String> Products = new ArrayList<String>();

		try
		{
			String PrductsDescription = Input.get("MenuAndProductName").trim();
			String[] MenuAndProductName = PrductsDescription.split(";");
			String CheckColoursOnOAT1 = getValueFromExcel("CheckColoursOnOAT1").trim();
			String CheckColoursOnOAT2 = getValueFromExcel("CheckColoursOnOAT2").trim();
			String NoProductInformation = "";
			String KvsName = "";

			if(CheckColoursOnOAT1.equalsIgnoreCase("YES") || CheckColoursOnOAT2.equalsIgnoreCase("NO"))
			{
				KvsName = "OAT-1";
			}
			else if(CheckColoursOnOAT1.equalsIgnoreCase("NO") || CheckColoursOnOAT2.equalsIgnoreCase("YES"))
			{
				KvsName = "OAT-2";
			}
			else
			{
				HtmlResult.failed("To verify colour codes", "Colour codes should be verified", "Wrong value passed from Propertymap.xlsx file, Parameters CheckColoursOnOAT1 and CheckColoursOnOAT1 should be Yes Or No");// enter yes to only one OAT
			}

			for(String MenuAndProducts : MenuAndProductName)
			{
				String[] MenuNameAndProductName = MenuAndProducts.split(":");
				if(MenuNameAndProductName.length==2)
				{
					Menus.add(MenuNameAndProductName[0]);
					Products.add(MenuNameAndProductName[1]);
				}
				else
				{
					HtmlResult.failed("To verify colour codes of ordered products", "Colour codes should be verified for each ordered product", " No ':' splitter found between menu name and product name-'"+MenuNameAndProductName+"'");//wrong data from xl file
				}
			}

			for(String strProductsArray : Products)
			{
				String[] arrProducts = strProductsArray.split("#");
				for(String Product : arrProducts)
				{
					Product = Product.replaceAll("\n", "").trim();
					Map<String,String> ProductDetails  = new HashMap<String,String>();

					if(hasSubCategory(Product))
					{
						continue;
					}
					else
					{
						ProductDetails = getProductKvsDetailsFromUiName(Product.trim());
					}

					if(ProductDetails!=null)
					{
						ProductsInformation.add(ProductDetails);
					}
					else
					{
						NoProductInformation = NoProductInformation.concat(Product+",");
					}
				}
			}

			if(ProductsInformation.size()>0)
			{
				if(connectToKvs(KvsName))
				{
					for(Map<String,String> ProductDetails :ProductsInformation)
					{
						String KvsDescription = ProductDetails.get("KVSDescription");
						String ColourName = ProductDetails.get("KVSColour");
						String strStepDesc = "To verify colour codes of Product- ' "+KvsDescription+" '";
						String strExpectedResult = "Colour-'"+ColourName+"' should be present against KVS description-"+KvsDescription+"'";
						List<Point> Description = getTextLocationOnScreen(KvsDescription.trim());

						if(Description!=null && Description.size()==1)
						{
							Point Cordinates = Description.get(0);
							int Xcordinate = (int) Cordinates.getX()+10;
							int Ycordinate = (int) Cordinates.getY();

							boolean Continue = false;
							int LoopCounter = 0;

							do
							{
								LoopCounter++;
								Xcordinate = Xcordinate+7;
								Point ColourPoint = new Point(Xcordinate, Ycordinate);

								String ColourAtLocation = eggUIDriver.colourAtlocation(ColourPoint);
								boolean Status = matchKvsColour(ColourAtLocation, ColourName);
								if(Status)
								{
									Continue = true;
									colourFound = true;
								}
							}while(!Continue && LoopCounter<=30);

							if(colourFound)
							{
								colourFound = false;
								ColourName = ColourName.toUpperCase();
								HtmlResult.passed(strStepDesc, strExpectedResult, "Colour-"+ColourName+"' is present against KVS Description-' "+KvsDescription+" '");
							}
							else
							{
								ColourName = ColourName.toUpperCase();
								HtmlResult.failed(strStepDesc, strExpectedResult, "Colour-"+ColourName+"' is not present against KVS Description-' "+KvsDescription+" '");
							}
						}
						else if(Description==null)
						{
							HtmlResult.failed(strStepDesc, strExpectedResult, "Unable to find location of KVS descriptin -' "+KvsDescription+" ' on KVS screen");// order is not present on KVS
						}
						else
						{
							HtmlResult.failed(strStepDesc, strExpectedResult, "Multiple orders with same -' "+KvsDescription+" ' is present on KVS screen");// order is not present on KVS
						}
					}
				}
				else
				{
					HtmlResult.failed("To connect to KVS-' "+KvsName+" '", "Connection sholud be succesful with KVS-'"+KvsName+"'", "Unable to connect to KVS-' "+KvsName+"' '"+eggUIDriver.LastErrorMessage+"");//connection failed
				}
			}

			if(NoProductInformation.length()>0)
			{
				String ActualProducts = NoProductInformation.replaceAll(",", "");
				if(ActualProducts.length()>0)
				{
					HtmlResult.warning("To verify colour codes of ordered products", "Colour codes should be verified against each ordered product", "Product information is not availble for products-' "+NoProductInformation+" '");
				}
			}
		}
		catch(Exception e)
		{
			HtmlResult.failed("To verify colour codes of ordered products", "Colour codes should be verified for each ordered product", "Error while executing verifyColourCodes-'"+e.getMessage()+"'");//error
		}
	}



	public boolean matchKvsColour(String ColourValue,String ColourName)
	{
		String RgbValue = "";
		String strColourName = "";
		try
		{
			if(ColourValue.length()>1 && ColourName.length()>1)
			{
				RgbValue = ColourValue.trim();
				strColourName = ColourName.trim();

				String TrueRgbValue = RgbValue.replaceAll("\\(|\\)", "");
				String [] arrRgbValue = TrueRgbValue.split(",");

				int RedValue = Integer.parseInt(arrRgbValue[0]);
				int GreenValue = Integer.parseInt(arrRgbValue[1]);
				int BlueValue = Integer.parseInt(arrRgbValue[2]);

				if(strColourName.equalsIgnoreCase("BLUE"))
				{
					if(RedValue<=10 && GreenValue<=10 && (BlueValue>=200 && BlueValue<=255))
					{
						return true;
					}
				}
				else if(strColourName.equalsIgnoreCase("GREEN"))
				{
					if(RedValue<=10 && BlueValue<=10 && (GreenValue>=200 && GreenValue<=255))
					{
						return true;
					}
				}
				else if(strColourName.equalsIgnoreCase("RED"))
				{
					if(GreenValue<=10 && BlueValue<=10 && (RedValue>=200 && RedValue<=255))
					{
						return true;
					}
				}
				else if(strColourName.equalsIgnoreCase("WHITE"))
				{
					if(RedValue>=250 && BlueValue>=250 && GreenValue>=250)
					{
						return true;
					}
				}
				else if(strColourName.equalsIgnoreCase("PINK"))
				{
					if(RedValue>=200 && BlueValue>=200 && GreenValue<=150 )
					{
						return true;
					}
				}
				else if(strColourName.equalsIgnoreCase("YELLOW"))
				{
					if(RedValue>=220 && GreenValue>=220 && BlueValue<=50 )
					{
						return true;
					}
				}
				else if(strColourName.equalsIgnoreCase("ORANGE"))
				{
					if(BlueValue<=10 &&(RedValue<=255 && RedValue>=220) && (GreenValue>=80 && GreenValue<=200))
					{
						return true;
					}
				}

			}
			else
			{
				return false;
			}
		}
		catch(Exception e)
		{
			return false;
		}
		return false;
	}

	// text search on kvs
	public boolean findTextOnScreen(String TextToSearch)
	{
		Rectangle OriginalScreenSize = new Rectangle();
		String KvsDivisions = getValueFromExcel("KvsScreenDivisions").trim();
		try
		{
			int KvsSingleLineOrders = Integer.parseInt(KvsDivisions);
			int intKvsDivisions = KvsSingleLineOrders*2;

			ArrayList<Rectangle> KvsRectangles = new ArrayList<Rectangle>();
			TextToSearch = TextToSearch.trim();
			Dimension ScreenSize= eggUIDriver.remoteScreenSize();
			OriginalScreenSize = new Rectangle(ScreenSize);

			int X = 0;
			int Y = 0;
			int DivisionCounter = 0;
			boolean RepeatXCordinate = true;
			for(int Index = 0; Index<intKvsDivisions; Index++ )
			{
				DivisionCounter++;
				int TotalWidth = OriginalScreenSize.width;
				int TotalHeight = OriginalScreenSize.height;

				int DividedWidth = TotalWidth/KvsSingleLineOrders;
				int DividedHeight = TotalHeight/2;

				if(DivisionCounter==1)
				{
					X=0;
					Y=0;
				}
				else
				{
					if(DivisionCounter<=KvsSingleLineOrders)
					{
						Y=0;
						X+=DividedWidth;
					}
					else
					{
						if(RepeatXCordinate)
						{
							X=0;
							Y+=DividedHeight;
							RepeatXCordinate = false;
						}
						else
						{
							X+=DividedWidth;
						}
					}
				}

				Rectangle KvsDivision = new Rectangle(X,Y,DividedWidth,DividedHeight);
				KvsRectangles.add(KvsDivision);
			}

			boolean ReturnFalse = false;
			for(Rectangle KvsDivision:KvsRectangles)
			{
				ReturnFalse = false;
				for(int TextDiff = 1; TextDiff<=2;TextDiff++)
				{
					String strTextDiff = Integer.toString(TextDiff);
					eggUIDriver.setSearchRectangle(KvsDivision);
					List<Point>TextLocation = eggUIDriver.getEveryTextLocation(TextToSearch.trim(),strTextDiff);
					if(TextLocation.size()==1)
					{
						ReturnFalse = false;
						eggUIDriver.setSearchRectangle(OriginalScreenSize);
						return true;
					}
					else if(TextLocation.size()>1)
					{
						for(Point Location: TextLocation)
						{
							String TextAtLocation = eggUIDriver.readText(Location);
							TextAtLocation = TextAtLocation.replaceFirst("[0-9]", "").trim();
							if(TextAtLocation.equals(TextToSearch))
							{
								eggUIDriver.setSearchRectangle(OriginalScreenSize);
								ReturnFalse = false;
								return true;
							}
						}
					}
					else
					{
						ReturnFalse = true;
					}
				}
			}

			if(ReturnFalse)
			{
				return false;
			}
		}
		catch(Exception e)
		{
			eggUIDriver.setSearchRectangle(OriginalScreenSize);
			return false;
		}
		return false;

	}


	public List<Point> getTextLocationOnScreen(String TextToSearch)
	{
		Rectangle OriginalScreenSize = new Rectangle();
		try
		{
			TextToSearch = TextToSearch.trim();
			Dimension ScreenSize= eggUIDriver.remoteScreenSize();
			OriginalScreenSize = new Rectangle(ScreenSize);
			boolean ReturnFalse = false;

			for(int TextDiff = 1; TextDiff<=2;TextDiff++)
			{
				String strTextDiff = Integer.toString(TextDiff);
				// initially setting search rectangle to full screen
				eggUIDriver.setSearchRectangle(OriginalScreenSize);
				List<Point>TextLocation = eggUIDriver.getEveryTextLocation(TextToSearch.trim(),strTextDiff);

				if(TextLocation.size()>=1)
				{
					if(TextLocation.size()==1)
					{
						eggUIDriver.setSearchRectangle(OriginalScreenSize);
						return TextLocation;
					}
					else
					{

						for(Point Location: TextLocation)
						{
							ReturnFalse = false;
							String TextAtLocation = eggUIDriver.readText(Location);
							TextAtLocation = TextAtLocation.replaceFirst("[0-9]", "").trim(); // milk has been read as ilk need to look using eggplant

							if(TextToSearch.equals(TextAtLocation))
							{
								eggUIDriver.setSearchRectangle(OriginalScreenSize);
								List<Point> NewList = new ArrayList<Point>();
								NewList.add(Location);
								return NewList;
							}
							else{
								ReturnFalse = true;
							}
						}
					}
				}
				else
				{
					Rectangle UpperLeftHalf = new Rectangle(0, 0, ScreenSize.width/2, ScreenSize.height/2);
					eggUIDriver.setSearchRectangle(UpperLeftHalf);
					List<Point> UpperLeftHalfTextLocation = eggUIDriver.getEveryTextLocation(TextToSearch,strTextDiff);

					if(UpperLeftHalfTextLocation.size()>=1)
					{
						if(UpperLeftHalfTextLocation.size()==1)
						{
							eggUIDriver.setSearchRectangle(OriginalScreenSize);
							return UpperLeftHalfTextLocation;
						}
						else
						{

							for(Point Location: UpperLeftHalfTextLocation)
							{
								ReturnFalse = false;
								String TextAtLocation = eggUIDriver.readText(Location);
								TextAtLocation = TextAtLocation.replaceFirst("[0-9]", "").trim();
								if(TextAtLocation.equals(TextToSearch))
								{
									eggUIDriver.setSearchRectangle(OriginalScreenSize);
									List<Point> NewList = new ArrayList<Point>();
									NewList.add(Location);
									return NewList;
								}
								else{
									ReturnFalse = true;
								}
							}
						}
					}
					else
					{
						Rectangle LowerLeftHalf = new Rectangle(0, ScreenSize.height/2, ScreenSize.width/2, ScreenSize.height);
						eggUIDriver.setSearchRectangle(LowerLeftHalf);
						List<Point> LowerLeftHalfTextLocation = eggUIDriver.getEveryTextLocation(TextToSearch,strTextDiff);
						if(LowerLeftHalfTextLocation.size()>=1)
						{
							if(UpperLeftHalfTextLocation.size()==1)
							{
								eggUIDriver.setSearchRectangle(OriginalScreenSize);
								return UpperLeftHalfTextLocation;
							}
							else
							{

								for(Point Location: UpperLeftHalfTextLocation)
								{
									ReturnFalse = false;
									String TextAtLocation = eggUIDriver.readText(Location);
									TextAtLocation = TextAtLocation.replaceFirst("[0-9]", "").trim();
									if(TextAtLocation.equals(TextToSearch))
									{
										eggUIDriver.setSearchRectangle(OriginalScreenSize);
										List<Point> NewList = new ArrayList<Point>();
										NewList.add(Location);
										return NewList;
									}
									else{
										ReturnFalse = true;
									}
								}
							}
						}
						else
						{
							ReturnFalse = true;
						}
					}
				}

				eggUIDriver.setSearchRectangle(OriginalScreenSize);
			}

			if(ReturnFalse)
			{
				eggUIDriver.setSearchRectangle(OriginalScreenSize);
				return null;
			}

		}
		catch(Exception e)
		{
			eggUIDriver.setSearchRectangle(OriginalScreenSize);
			return null;
		}
		return null;
	}


	public boolean hasSubCategory(String Product)
	{
		try{
			String SubCategoryProducts = getValueFromExcel("SubCategoryProducts");
			String [] arrSubCategoryProducts = SubCategoryProducts.split("#");

			for(String ProductName: arrSubCategoryProducts)
			{
				ProductName = ProductName.trim();
				Product = Product.trim();
				if(Product.equals(ProductName))
				{
					return true;
				}
			}
		}
		catch(Exception e)
		{
			return false;
		}
		return false;
	}












	//color code verification

	//[Abhishek-20/10/2016]
	public void KVSColorValidation(Map<String,String> input )
	{
		String ProductCode = input.get("productCode");

		int intProductCode = Integer.parseInt(ProductCode);

		Map ProductDetails = getProductDetails(intProductCode);

		String Color = (String) ProductDetails.get("KVSColour");
		ProductDetails.get("KVSDescription");

		Dimension ScreenResolution = eggUIDriver.remoteScreenSize();
		Rectangle KVSPart = new Rectangle();
		Rectangle ColorTextArea = new Rectangle();

		int w = ScreenResolution.width;
		int h = ScreenResolution.height;

		int LeftTopX = 0;
		int LeftTopY = 0;
		int BottomRightX = 0;
		int BottomRightY = 0;

		int width=(w/4);
		int height=(h/2);

		ArrayList<Integer> points1;
		ArrayList<Integer> points2;

		String LeftTop="";
		String RightBottom="";

		switch(Color.toUpperCase().trim())
		{
		case "BLUE": LeftTop = "LeftTopBlue";
		RightBottom = "RightBottomBlue";
		break;

		case "YELLOW":LeftTop = "LeftTopYellow";
		RightBottom = "RightBottomYellow";
		break;
		case "RED":LeftTop = "LeftTopRed";
		RightBottom = "RightBottomRed";
		break;

		case "ORANGE":LeftTop = "LeftTopOrange";
		RightBottom = "RightBottomOrange";
		break;
		}

		KVSPart.x=0;
		KVSPart.y=0;
		KVSPart.width=width;
		KVSPart.height=height;
		String TempScale =EggUIDriver.ImagefoundScale;
		String TempFixedScale =EggUIDriver.ImageFixedScale;

		EggUIDriver.ImagefoundScale="1.1";
		EggUIDriver.ImageFixedScale="1.1";

		for(int YIndex=0;YIndex<2;YIndex++)
		{
			KVSPart.x=0;
			for(int XIndex=0;XIndex<4;XIndex++)
			{
				if(eggUIDriver.imageFoundSearchRectangle(LeftTop, KVSPart))
				{
					points1 =eggUIDriver.ImageLocation(LeftTop);//eggUIDriver.ImageLocation(LeftTop, KVSPart);
					if (eggUIDriver.imageFoundSearchRectangle(RightBottom, KVSPart))
					{
						points2 = eggUIDriver.ImageLocation(RightBottom);//eggUIDriver.ImageLocation(RightBottom, KVSPart);
						LeftTopX = points1.get(0);
						LeftTopY = points1.get(1);

						BottomRightX = points2.get(0);
						BottomRightY = points2.get(1);

						ColorTextArea.x = LeftTopX;
						ColorTextArea.y = LeftTopY;
						ColorTextArea.width = BottomRightX-LeftTopX;
						ColorTextArea.height = 	BottomRightY-LeftTopY;

						eggUIDriver.readText(ColorTextArea);

						if (true && false)//MatchStringOnSalesPanel(strKVSDescription, ColorTextArea)
						{
							HtmlResult.passed("KVS Color code verification ",Color+" should be displayed infront of a product -"+"'"+ProductDetails+"'",Color+" is displayed for a product -"+"'"+ProductDetails+"'");

						}
						else
						{
							HtmlResult.failed("KVS Color code verification ",Color+" should be displayed infront of a product -"+"'"+ProductDetails+"'",Color+" is not displayed for a product -"+"'"+ProductDetails+"'");
						}

					}
				}
				KVSPart.x=KVSPart.x	+ KVSPart.width;
			}
			KVSPart.y = KVSPart.y + KVSPart.height;
		}
		EggUIDriver.ImagefoundScale=TempScale;
		EggUIDriver.ImageFixedScale=TempFixedScale;

	}

	public Map getProductDetails(int ProductCode)
	{
		Map result = null;
		List<Map<String,String>> Details= TestCaseRunner.KvsMap;
		try{
			for(Map map:Details)
			{
				String MapProductCode=map.get("ProductCode").toString().trim();
				int ProdCode=Integer.parseInt(MapProductCode);
				if(ProdCode==ProductCode)
				{
					result=map;
					break;
				}
			}
		}
		catch(Exception e)
		{
			;
		}
		return result;
	}


} // AppDriver


