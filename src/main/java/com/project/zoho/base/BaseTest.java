package com.project.zoho.base;
import com.project.zoho.util.ExtentManager;
import com.project.zoho.util.Xls_Reader;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import junit.framework.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

public class BaseTest {

	public WebDriver driver=null;
	public static Properties prop=null;
	public SoftAssert softassert;
	public Xls_Reader xls=new Xls_Reader("/Users/rabia/Desktop/zoho.xlsx");
	public ExtentReports extent=ExtentManager.getInstance();
	public ExtentTest logger;
	public boolean gridRun=false;
	
	public void init() throws IOException
	{
		if(prop==null)
		{
			try 
			{				
				prop=new Properties();
				FileInputStream fs=new FileInputStream(System.getProperty("user.dir")+"/OR.properties");
				//	Sxystem.out.println(System.getProperty("user.dir")+"/OR.properties");
				prop.load(fs);
				System.out.println(prop.getProperty("URL"));
			} 
			catch (FileNotFoundException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("FInished inti function");
		
	}
	
	public boolean doLogin(String username,String password) throws InterruptedException
	{
		logger.log(LogStatus.INFO, "Trying to login with "+ username );
		//System.out.println("In login function");
		//System.out.println("Username is" + username);
		//System.out.println("Password is "+password);
		driver.get("http://www.zoho.com");
		driver.findElement(By.xpath(prop.getProperty("signInLink"))).click();
		wait(1);
		waitforPagetoLoad();
		driver.switchTo().frame(driver.findElement(By.id("zohoiam")));
		driver.findElement(By.xpath(prop.getProperty("emailID"))).sendKeys(username);
		driver.findElement(By.xpath(prop.getProperty("passwordField"))).sendKeys(password);
		driver.findElement(By.xpath(prop.getProperty("signInSubmit"))).click();		
		wait(20);
		driver.switchTo().defaultContent();
		if(isElementpresent(prop.getProperty("profilePic")))
		{
			return true;
		}
		else
		{
			return false;
		}

		
	}
	
	public void wait(int timetoWaitinSec)
	{
		try {
			Thread.sleep(timetoWaitinSec*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void clickOnLead(String leadName)
	{
		int row=getLeadRowNum(leadName);
		System.out.println(prop.getProperty("leadPart1_xpath")+row+prop.getProperty("leadPart2_xpath"));
		driver.findElement(By.xpath(prop.getProperty("leadPart1_xpath")+row+prop.getProperty("leadPart2_xpath"))).click();;
		
	}
	
	public void acceptAlert()
	{
		WebDriverWait wait=new WebDriverWait(driver,5);
		wait.until(ExpectedConditions.alertIsPresent());
		driver.switchTo().alert().accept();
		driver.switchTo().defaultContent();
	}
	
	public int getLeadRowNum(String leadName)
	{
		logger.log(LogStatus.INFO, "FInding the lead " +leadName);
		List <WebElement> leadNames=driver.findElements(By.xpath(prop.getProperty("leadNamesCol_xpath")));
		for(int i=0;i<=leadNames.size();i++)
		{
			
			System.out.println(leadNames.get(i).getText());
			if(leadNames.get(i).getText().equals(leadName))
			{
				logger.log(LogStatus.INFO, "Lead found in row num " +i);
				System.out.println("Lead found in column no"+ (i+1));
			}
				
				return (i+1);
		}
		logger.log(LogStatus.INFO, "Lead not found");
		return -1;
		
	}
	
	public void selectDate(String d) throws ParseException
	{
		driver.findElement(By.xpath(prop.getProperty("dealClosingDate"))).click();
		Date currentDate=new Date();
		//compare current date and date to be selected 
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		Date dateTobeSelected=sdf.parse(d);
		sdf=new SimpleDateFormat("MMMM");
		String monthToBeSelected=sdf.format(dateTobeSelected);
		sdf=new SimpleDateFormat("yyyy");
		String yearToBeSelected=sdf.format(dateTobeSelected);
		sdf=new SimpleDateFormat("d");
		String dayToBeSelected=sdf.format(dateTobeSelected);
		String monthYearToBeselected=monthToBeSelected+" "+yearToBeSelected;
		String getSelectedDate=driver.findElement(By.xpath(prop.getProperty("monthYearInfo"))).getText();
		while(true)
		{
			if(currentDate.compareTo(dateTobeSelected)==1)
			{
				driver.findElement(By.xpath(prop.getProperty("calenderFrontButton"))).click();
			}
			else if(currentDate.compareTo(dateTobeSelected)==-1)
			{
				driver.findElement(By.xpath(prop.getProperty("calenderBackButton"))).click();
			
			}
			if(monthYearToBeselected.equals(getSelectedDate))
			{
				break;
			}
		}
		driver.findElement(By.xpath("//td[text()='"+dayToBeSelected+"']")).click();
		
	}
	
	
	public void waitforPagetoLoad() throws InterruptedException
	{
		JavascriptExecutor js=(JavascriptExecutor)driver;
		String state=(String)js.executeScript("return document.readyState");
		while(!state.equals("complete"))
		{
			Thread.sleep(5000);
			state=(String)js.executeScript("return document.readyState");
			
		}
		
	}
	
	public void openBrowser(String browser) throws IOException
	{
			
		if(!gridRun)
		{
			if(browser.equalsIgnoreCase("firefox"))
			{
				System.setProperty("webdriver.gecko.driver", "/Users/rabia/Desktop/geckodriver");
				driver=new FirefoxDriver();
			}
		
			
			else if(browser.equalsIgnoreCase("chrome"))
			{
				System.setProperty("webdriver.chrome.driver", "/Users/rabia/Desktop/chromedriver");
				driver=new ChromeDriver();
							
			}
			else
			{
				
				
			}
		}
		else
		{
			DesiredCapabilities cap=null;
			if(browser.equalsIgnoreCase("firefox"))
			{
				cap=DesiredCapabilities.firefox();
				cap.setBrowserName("firefox");
				cap.setJavascriptEnabled(true);
				System.setProperty("webdriver.gecko.driver", "/Users/rabia/Desktop/geckodriver");
				driver=new FirefoxDriver();
			}
		
			
			else if(browser.equalsIgnoreCase("chrome"))
			{
				cap=DesiredCapabilities.chrome();
				cap.setBrowserName("Chrome");
				cap.setJavascriptEnabled(true);
				System.setProperty("webdriver.chrome.driver", "/Users/rabia/Desktop/chromedriver");
				driver=new ChromeDriver();
							
			}
			else
			{
				
				
			}
			URL url=new URL("http://localhost:4444/wd/hub");
			driver=new RemoteWebDriver(url,cap);
			
			
		}
		
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();	
		logger.log(LogStatus.INFO, "Browser opened successfully " + browser);
//		closeBrowser();
		
	}
	
	
	public void closeBrowser()
	{
		System.out.println("In quit function");
		driver.quit();
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
		
	
	public boolean isTestCaseRunnable(String suiteSheetName,String testCaseName)
	{
		
		int rows=xls.getRowCount(suiteSheetName);
		int cols=xls.getColumnCount(suiteSheetName);	
		for(int c=0;c<=cols;c++)
		{
			for(int r=1;r<=rows;r++)
			{
				System.out.println("ROw data is "+xls.getCellData(suiteSheetName, "TCID", r));
				if(xls.getCellData(suiteSheetName, "TCID", r).equalsIgnoreCase(testCaseName))
				{
					String runmode=xls.getCellData(suiteSheetName, "Runmode", r);
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
		 // String dest="/Users/rabia/Desktop/screenshot"+".png";
		//  File destination=new File(dest);
		  FileUtils.copyFile(source, new File(System.getProperty("user.dir")+"//screenshots"));
		 
		
	 }
	
	public boolean isElementpresent(String xpath)
	{
		System.out.println("In is element present function");
		List <WebElement> elem=driver.findElements(By.xpath(xpath));
		System.out.println("Element size in iselement present is"+elem.size());
		if(elem.size() >0)
			return true;
		else
		    return false;
		
	}
		
	
	public void reportPass(String msg)
	{
		logger.log(LogStatus.PASS, msg);
		
		
	}
	
	public void reportFailure(String msg)
	{
		logger.log(LogStatus.FAIL, msg);
		try 
		{
			takeScreenShot();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.fail(msg);
		 
	}
	

}