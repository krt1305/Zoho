package com.project.zoho.testcases;

import org.testng.annotations.Test;

public class PotentialTest 
{
	
	@Test(priority=1)
	public void createPotentialTest()
	{
		
	}
	
	@Test(priority=2,dependsOnMethods={"createPotentialTest"})
	public void deletePotentialTest()
	{
		
	}

}
