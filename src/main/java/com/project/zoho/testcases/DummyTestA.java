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

public class DummyTestA extends BaseTest
{
	
	Xls_Reader xls=new Xls_Reader("/Users/rabia/Desktop/testing.xlsx");
	String testCaseName;
	String sheetName;
	SoftAssert softassert;
	
	
     @Test(dataProvider="getData")
     public void test(Hashtable<String,String> data) throws IOException
     {
    	 softassert=new SoftAssert();
    	 System.out.println("In test");
    	if(data.get("RunMode").equals("N"))
    	{
    		System.out.println("Skiping the test "+testCaseName);
    		throw new SkipException("Skipping the test as runmode is N");
    	}
    	
    	System.out.println(isSuiteRUnnable("SuiteB"));
    	System.out.println(isTestCaseRunnable("TestA"));
    	readData("LoginTest");
    	//System.out.println(data.get("Col1"));
 	 
    	 
     }
     
     @DataProvider
     public Object[][] getData() throws IOException
     {
		 init();
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
