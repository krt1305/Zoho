package com.project.zoho.base;
import com.project.zoho.util.Xls_Reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BaseTest {

	WebDriver driver=null;
	Properties prop=null;
	Xls_Reader xls=new Xls_Reader("/Users/rabia/Desktop/testing.xlsx");
	
	public void init() throws IOException
	{
		if(prop==null)
		{
			try 
			{
				
				prop=new Properties();
				FileInputStream fs=new FileInputStream(System.getProperty("user.dir")+"/OR.properties");
				prop.load(fs);
			} 
			catch (FileNotFoundException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(prop.getProperty("url"));
	}
	
	public void openBrowser(String browser) throws IOException
	{
		
		
		if(browser.equalsIgnoreCase("firefox"))
		{
			System.setProperty("webdriver.gecko.driver", "/Users/rabia/Desktop/geckodriver");
			driver=new FirefoxDriver();
		}
		else if(browser.equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.driver.chrome", "/Users/rabia/Desktop/chromedriver.exe");
			driver=new ChromeDriver();
						
		}
		else
		{
			
		}
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();	
		closeBrowser();
		
	}
	
	
	public void closeBrowser()
	{
		System.out.println("In quit function");
		driver.quit();
	}
	
	public void takesScreenshot()
	{
		
	}
	
	
	public boolean isSuiteRUnnable(String suiteName)
	{
		int rows=xls.getRowCount("Suites");
		int cols=xls.getColumnCount("Suites");
		System.out.println("Total rows"+rows);
		System.out.println("Total cols "+cols);
		
			for(int c=0;c<=cols;c++)
			{
				for(int r=1;r<=rows;r++)
				{
					if(xls.getCellData("Suites", "Suite", r).equalsIgnoreCase(suiteName))
					{
						String runmode=xls.getCellData("Suites", "RunMode", r);
						System.out.println("Runmode is"+runmode);
						if(runmode.equalsIgnoreCase("Y"))
							return true;
						else
							return false;
						
					}
				}
				
			}
			return false;				
			
		}
		
	
	public boolean isTestCaseRunnable(String testCaseName)
	{
		int rows=xls.getRowCount("TestCases");
		int cols=xls.getColumnCount("TestCases");	
		for(int c=0;c<=cols;c++)
		{
			for(int r=1;r<=rows;r++)
			{
				if(xls.getCellData("TestCases", "TCID", r).equalsIgnoreCase(testCaseName))
				{
					String runmode=xls.getCellData("TestCases", "Runmode", r);
					System.out.println("Runmode is"+runmode);
					if(runmode.equalsIgnoreCase("Y"))
						return true;
					else
						return false;
					
				}
			}
			
		}
		return false;
		
	}
	
	
	public void readData(String caseName)
	{
		
		//find where row starts
		//find total no of rows in tat test
		//find total no of cols in that test
		
		int rowNum = 0,rowStart,dataStart;
		while(true)
		{
			rowNum=rowNum+1;
			if(xls.getCellData("Data", 0, rowNum).equalsIgnoreCase(caseName))
			{
				
				rowStart=rowNum;
				System.out.println("Row start number is "+rowStart);
				break;
				
			}
				
				
		}

		int totalCols=0,totalRows=0;
		while(xls.getCellData("Data", totalCols, rowStart+1)!="")
		{
			totalCols=totalCols+1;
		}
		System.out.println("Total no of cols"+totalCols);
		
		int colNo=0;
		dataStart=rowStart+2;
		while(xls.getCellData("Data",0,dataStart+totalRows)!="")
		{
				System.out.println("In while");
				totalRows=totalRows+1;
				//System.out.println("Row data"+xls.getCellData("Data",colNo,rowIterator));			
		}
		
		System.out.println("Total no of rows"+totalRows);
		System.out.println("Data-------------------------");
		for(int i=dataStart;i<dataStart+totalRows;i++)
		{
			for(int j=0;j<=totalCols-1;j++)
			{
				System.out.println(xls.getCellData("Data", j, i));
			}
		}
		
		
	  
		
	}
	
	public void takeScreenShot() throws IOException
	 {
		  TakesScreenshot ts=(TakesScreenshot)driver;
		  File source=ts.getScreenshotAs(OutputType.FILE);
		  String dest="/Users/rabia/Desktop/screenshot"+".png";
		  File destination=new File(dest);
		  FileUtils.copyFile(source, destination);
		 
		
	 }
		
	

}