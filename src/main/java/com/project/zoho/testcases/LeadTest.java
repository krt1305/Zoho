package com.project.zoho.testcases;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
		//testCaseName="CreateLeadTest";
		logger=extent.startTest("LeadTest");
		logger.log(LogStatus.INFO, "Starting test case "+testCaseName);
		System.out.println("Data from hashtable is"+data.get("LeadCompany"));
		if(isTestCaseRunnable(suiteSheetName,testCaseName))
		{
			if(data.get("Runmode").equals("N"))
   	    	{
   	    		System.out.println("Skiping the test "+testCaseName);
   	    		logger.log(LogStatus.SKIP, "Skipping " +testCaseName);
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
		wait(30);
		driver.findElement(By.xpath(prop.getProperty("leadsTab"))).click();
		waitforPagetoLoad();
		wait(30);
		driver.findElement(By.xpath(prop.getProperty("createNewButton"))).click();
		Thread.sleep(5000);	
		driver.findElement(By.xpath(prop.getProperty("createLeadSubMenu"))).click();
		Thread.sleep(5000);
	//	wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(prop.getProperty("LeadFirstName")))));
    	driver.findElement(By.xpath(prop.getProperty("LeadLastName"))).sendKeys(data.get("LeadLastName"));
		driver.findElement(By.xpath(prop.getProperty("LeadCompany"))).sendKeys(data.get("LeadCompany"));
		driver.findElement(By.xpath(prop.getProperty("LeadSaveButton"))).click();
		wait(10);
		driver.findElement(By.xpath(prop.getProperty("backToLeadsArrow"))).click();
		wait(5);
		//validate if the lead was saved
		int rnum=getLeadRowNum(data.get("LeadLastName"));
		if(rnum==-1)
		{
			System.out.println("NO lead found with name"+data.get("LeadLastName"));
			takeScreenShot();
			reportFailure("Lead not found in lead table "+data.get("LeadLastName"));
		}
		else
		{
			reportPass("Lead found in lead table "+data.get("LeadLastName"));
			logger.log(LogStatus.PASS, "Found lead");
		}
			
							
	}
	
	
	@Test(priority=2,dataProvider="getData")
	public void convertLeadTest(Hashtable <String,String> data) throws IOException, InterruptedException
	{
		
		if(isTestCaseRunnable(suiteSheetName,testCaseName))
		{
			if(data.get("Runmode").equals("N"))
   	    	{
   	    		System.out.println("Skiping the test "+testCaseName);
   	    		logger.log(LogStatus.SKIP, "Skipping " +testCaseName);
   	    		throw new SkipException("Skipping the test as runmode is N");
   	    	}
   	 	}
   	 	else
   	 	{
   	 	 logger.log(LogStatus.SKIP, "Skipping " +testCaseName);
   	 		throw new SkipException("Skipping test case "+testCaseName +" as run mode is No");
   	 	}
	//	openBrowser("Firefox");
	//	doLogin(prop.getProperty("username"), prop.getProperty("password"));
	//	driver.findElement(By.xpath(prop.getProperty("crmIcon"))).click();
	//	waitforPagetoLoad();
	//	wait(20);
	//	driver.findElement(By.xpath(prop.getProperty("leadsTab"))).click();
	//	wait(10);
		clickOnLead(data.get("LeadLastName"));
		wait(5);
		driver.findElement(By.xpath(prop.getProperty("convertLeadbutton"))).click();
		wait(5);
		driver.findElement(By.xpath(prop.getProperty("convertLeadsaveButton"))).click();
		
		
	}
	
	@Test(priority=3,dataProvider="getData")
	public void deleteLeadAccountTest(Hashtable <String,String> data) throws IOException, InterruptedException
	{
		testCaseName="DeleteLeadAccountTest";
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
	//	openBrowser("Firefox");
	//	doLogin(prop.getProperty("username"), prop.getProperty("password"));
	//	driver.findElement(By.xpath(prop.getProperty("crmIcon"))).click();
//		waitforPagetoLoad();
//		wait(20);
		driver.findElement(By.xpath(prop.getProperty("leadsTab"))).click();
		clickOnLead(data.get("LeadLastName"));
		driver.findElement(By.xpath(prop.getProperty("deleteMenu"))).click();
		driver.findElement(By.xpath(prop.getProperty("deleteMenuOption"))).click();
		acceptAlert();
		
		int rnum=getLeadRowNum(data.get("LeadLastName"));
		if(rnum==-1)
			System.out.println("NO lead found with name"+data.get("LeadLastName"));
		
	}
	
	
	
	@AfterMethod
	public void quit()
	{
		
		try
		{
			softassert.assertAll();
		}
		catch(Error e)
		{
			logger.log(LogStatus.FAIL, e.getMessage());
		}

	/*	if(driver!=null)
		{
			driver.quit();
		}
	*/	
		extent.endTest(logger);
		extent.flush();
	}
	
	@DataProvider
	public Object[][] getData() throws IOException
	{
		super.init();
		return DataUtil.getTestData(xls, testCaseName);
		
	}
	

	
	@DataProvider
	public Object[][] deleteLeadData() throws IOException
	{
		super.init();
		return DataUtil.getTestData(xls, "DeleteLeadAccountTest");
		
	}
	
	
}
