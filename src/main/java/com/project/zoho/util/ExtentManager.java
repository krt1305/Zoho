package com.project.zoho.util;

import java.io.File;
import java.util.Date;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;


public class ExtentManager {
	private static ExtentReports extent;

	public static ExtentReports getInstance() 
	{
		if (extent == null) 
		{
			Date d=new Date();
			String fileName=d.toString().replace(":", "_").replace(" ", "_")+".html";
			extent=new ExtentReports("/Users/rabia/Desktop/reports/"+fileName, true, DisplayOrder.NEWEST_FIRST);
		//	extent = new ExtentReports(Constants.REPORT_PATH+fileName, true, DisplayOrder.NEWEST_FIRST);
			extent.loadConfig(new File(System.getProperty("user.dir")+"//ReportsConfig.xml"));
			extent.addSystemInfo("Selenium Version", "2.53.0").addSystemInfo(
					"Environment", "QA");
		}
		return extent;
		
	
	}
}
