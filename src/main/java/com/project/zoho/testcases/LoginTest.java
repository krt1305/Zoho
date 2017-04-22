package com.project.zoho.testcases;

import java.io.IOException;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.project.zoho.base.BaseTest;
import com.project.zoho.util.DataUtil;
import com.project.zoho.util.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;

public class LoginTest extends BaseTest {
	
	
	Xls_Reader xls=new Xls_Reader("/Users/rabia/Desktop/zoho.xlsx");
	String testCaseName="LoginTest";
	String suiteSheetName="TestCases";
	String sheetName;
	
	@Test(dataProvider="getData")
	public void doLoginTest(Hashtable <String,String> data) throws IOException, InterruptedException
	{
		/*if(!isTestCaseRunnable(suiteSheetName,testCaseName) || data.get("Runmode").equalsIgnoreCase("N"))
		{
			throw new SkipException("Skipping the test case "+testCaseName+ " as run mode is N");
		}*/
		System.out.println("Data from hashtable is"+data.get("Browser"));
		 if(isTestCaseRunnable(suiteSheetName,testCaseName))
    	 {
    		 if(data.get("Runmode").equals("N"))
    	    	{
    	    		System.out.println("Skiping the test "+testCaseName);
    	    		throw new SkipException("Skipping the test as runmode is N");
    	    	}
    	 }
    	 else
    	 {
    		 throw new SkipException("Skipping test case "+testCaseName +" as run mode is No");
    	 }
    	 	 
		
		openBrowser(data.get("Browser"));
	//	driver.get("https://www.zoho.com");
	//	driver.get(prop.getProperty("URL"));
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);	
		boolean actualResult=doLogin(data.get("Username"),data.get("Password"));
		boolean expectedResult=false;
		System.out.println("Actual result from do login function is"+actualResult);
		System.out.println("Expected result from excel sheet"+data.get("ExpectedResult"));
		if(data.get("ExpectedResult").equalsIgnoreCase("Y"))
			expectedResult=true;
		else
			expectedResult=false;
		
		if(expectedResult!=actualResult)
		{
			System.out.println("Login test failed");
		//	test.log(LogStatus.INFO,"Login Failed");
		
		}
			
		else
		{
			System.out.println("Login test passed");
		//	test.log(LogStatus.INFO,"Login passed");

		}
		    
					
		
	}
	
	
	@BeforeMethod
	public void init()
	{
		softassert=new SoftAssert();
			
	}
	
	@AfterMethod
	public void quit()
	{
		softassert.assertAll();
		if(driver!=null)
		{
			driver.quit();
		}
	}
	
	
	@DataProvider
	public Object[][] getData() throws IOException
	{
		
		super.init();
		return DataUtil.getTestData(xls, testCaseName);
		
	}

}
