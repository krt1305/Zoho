package com.project.zoho.util;

import java.util.Hashtable;

public class DataUtil 
{

	public static Object[][] getTestData(Xls_Reader xls,String caseName)
	{
	
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
		
		Object[][] data=new Object[totalRows][1];
		int dataRow=0;
		Hashtable<String,String> table=null;
		for(int i=dataStart;i<dataStart+totalRows;i++)
		{
			table=new Hashtable<String,String>();			
			for(int j=0;j<=totalCols-1;j++)
			{
				String key=xls.getCellData("Data", j,rowStart+1);
				String value=xls.getCellData("Data", j, i);
				table.put(key, value);
				//data[dataRow][j]=xls.getCellData("Data", j, i);
				//System.out.println(xls.getCellData("Data", j, i));
			}
			data[dataRow][0]=table;
			dataRow++;
		}
		return data;
		
	}
	
}
