package com.project.zoho.testcases;

import java.io.IOException;
import java.util.Hashtable;

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

public class DummyTestA extends BaseTest
{
	
	Xls_Reader xls=new Xls_Reader("/Users/rabia/Desktop/testing.xlsx");
	String testCaseName="LoginTest";
	String suiteSheetName="TestCases";
	String sheetName;
	SoftAssert softassert;
			
	
	@BeforeMethod
	public void init() throws IOException
	{
		softassert=new SoftAssert();
		super.init();
		
	}
	
     @Test(dataProvider="getData")
     public void test(Hashtable<String,String> data) throws IOException
     {
    	 
    //	 test=rep.startTest(testCaseName);
    //	 test.log(LogStatus.INFO, "Starting test case "+testCaseName);
    	 System.out.println("In test");
    	 System.out.println("Data from hashtable is"+data.get("Username"));
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
    	 	 
    	 
     }
     
     @DataProvider
     public Object[][] getData() throws IOException
     {
    	 super.init();
    	 return DataUtil.getTestData(xls, testCaseName);
    	 
     }
     
     
     @AfterMethod
     public void quit()
     {
    	 try
    	 {
    		 softassert.assertAll();
    	 }
    	 catch(Exception e)
    	 {
    		 
    	 }
     }
}
