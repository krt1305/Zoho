package com.project.zoho.testcases;

import java.io.IOException;
import java.text.ParseException;
import java.util.Hashtable;

import org.openqa.selenium.By;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.project.zoho.base.BaseTest;
import com.project.zoho.util.DataUtil;
import com.project.zoho.util.Xls_Reader;

public class PotentialTest extends BaseTest
{
	Xls_Reader xls=new Xls_Reader("/Users/rabia/Desktop/zoho.xlsx");
	String testCaseName="CreatePotentialTest";
	String suiteSheetName="TestCases";
	String sheetName;
	
	@Test(priority=1,dataProvider="getData")
	public void createDeal(Hashtable <String,String> data) throws IOException, InterruptedException, ParseException
	{
	
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
		openBrowser("Firefox");
		doLogin(prop.getProperty("username"), prop.getProperty("password"));
		driver.findElement(By.xpath(prop.getProperty("crmIcon"))).click();
		waitforPagetoLoad();
		wait(20);
		driver.findElement(By.xpath(prop.getProperty("dealTab"))).click();
		wait(20);
		driver.findElement(By.xpath(prop.getProperty("createNewButton"))).click();
		driver.findElement(By.xpath(prop.getProperty("createNewDealButton"))).click();
		driver.findElement(By.xpath(prop.getProperty("dealName"))).sendKeys(data.get("PotentialName"));
		driver.findElement(By.xpath(prop.getProperty("accountName"))).sendKeys(data.get("AccountName"));
		driver.findElement(By.xpath(prop.getProperty("savePotentialButton"))).click();
		selectDate(data.get("ClosingDate"));
	}
	
	@Test(priority=2,dependsOnMethods={"createPotentialTest"})
	public void deletePotentialTest()
	{
		
	}
	
	
	@DataProvider
	public Object[][] getData() throws IOException
	{
		super.init();
		return DataUtil.getTestData(xls, testCaseName);
		
	}

}
