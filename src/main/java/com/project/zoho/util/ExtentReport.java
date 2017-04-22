package com.project.zoho.util;

import static org.testng.Assert.assertTrue;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;

public class ExtentReport {

	public static void main()
	{
		getInstance();
	}
	
	public static ExtentReports getInstance()
	{
		
		ExtentReports extent=new ExtentReports("/Users/rabia/Desktop/",true);
		extent.loadConfig(new File(System.getProperty("user.dir")+"/ReportsConfig.xml"));
		extent.addSystemInfo("Selenium", "3").addSystemInfo("Environment", "QA");
		return extent;
		
		
	}

}
