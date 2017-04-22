package com.project.zoho.testcases;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Test {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Properties prop=null;
		prop=new Properties();
		FileInputStream fs=new FileInputStream("/Users/rabia/Documents/workspace/Zoho/resources/OR.properties");
		prop.load(fs);
		System.out.println(prop.getProperty("url"));
				

	}

}
