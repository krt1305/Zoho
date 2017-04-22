package com.project.zoho.testcases;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.project.zoho.base.BaseTest;
import com.project.zoho.util.DataUtil;
import com.project.zoho.util.Xls_Reader;

public class LeadTest extends BaseTest {
	
	Xls_Reader xls=new Xls_Reader("/Users/rabia/Desktop/zoho.xlsx");
	String testCaseName="CreateLeadTest";
	String suiteSheetName="TestCases";
	String sheetName;

	@BeforeMethod
	public void init()
	{
		softassert=new SoftAssert();
		
	}
	
	@Test(priority=1,dataProvider="getData")
	public void createLeadTest(Hashtable <String,String> data) throws IOException, InterruptedException
	{
		System.out.println("Data from hashtable is"+data.get("LeadCompany"));
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
		driver.findElement(By.xpath(prop.getProperty("leadsTab"))).click();
		driver.findElement(By.xpath(prop.getProperty("createLeadBtn"))).click();
		driver.findElement(By.xpath(prop.getProperty("LeadFirstName"))).sendKeys(data.get("LeadLastName"));
		driver.findElement(By.xpath(prop.getProperty("LeadCompany"))).sendKeys(data.get("LeadCompany"));
		driver.findElement(By.xpath(prop.getProperty("LeadSaveButton"))).click();
		//validate if the lead was saved
		int rnum=getLeadRowNum(data.get("LeadLastName"));
		if(rnum==-1)
			System.out.println("NO lead found with name"+data.get("LeadLastName"));
							
	}
	
	
	@Test(priority=2,dataProvider="getData")
	public void convertLeadTest(Hashtable <String,String> data) throws IOException, InterruptedException
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
		driver.findElement(By.xpath(prop.getProperty("leadsTab"))).click();
		clickOnLead(data.get("LeadLastName"));
		driver.findElement(By.xpath(prop.getProperty("convertLeadbutton"))).click();
		driver.findElement(By.xpath(prop.getProperty("convertLeadsaveButton"))).click();
		
		
	}
	
	@Test(priority=3,dataProvider="getData")
	public void deleteLeadAccountTest(Hashtable <String,String> data)
	{
		
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
