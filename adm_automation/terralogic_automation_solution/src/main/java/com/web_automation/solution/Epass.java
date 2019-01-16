package com.web_automation.solution;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;


import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.terralogic.framework.Common_resuable_methods;

public class Epass extends Web_temp

{ 
	WebDriver driver;
	@BeforeClass
	public void loadProperty()  {
		loadProperties();
		
	}
	
	@Test(priority=1)
	public void startBrowser() {
		
		System.setProperty("webdriver.chrome.driver", "/home/terralogic/Downloads/chromedriver");
		
		   driver = new ChromeDriver();
		   System.out.println(propertyMap.get("url"));
			

		driver.manage().window().maximize();
		System.out.println("launch browser");
		driver.get(propertyMap.get("url"));
		
	}
	@Test(priority=2)
	public void testScript() throws InterruptedException {
		byXPath(driver, propertyMap.get("epass_mail_ele")).sendKeys(propertyMap.get("epass_mail")+Keys.ENTER);
		byXPath(driver, propertyMap.get("epass_pwd_ele")).sendKeys(propertyMap.get("epass_pwd")+Keys.ENTER);
	}
		@Test(priority=3)
		public void tools() 
		{
			General_Wait(2000);
		byXPath(driver,propertyMap.get("pastreviews")).click();               
		General_Wait(2000);
		
		byXPath(driver,propertyMap.get("MyTeamReviews")).click();
		General_Wait(2000);
		byXPath(driver,propertyMap.get("rating")).click();
		General_Wait(2000);
		byXPath(driver,propertyMap.get("close_button")).click();
		General_Wait(2000);
		
		byXPath(driver,propertyMap.get("goal_setting")).click();
		General_Wait(2000);
	    byXPath(driver,propertyMap.get("add/edit")).click();
	   
	    
	    
	    byXPath(driver,propertyMap.get("current_review_cycle_list")).click();
		}
		
		//scroll down to add the employee
	   @Test(priority=4)
	   public void scrolldown() 
	   {
		   General_Wait(2000);
		   byXPath(driver,propertyMap.get("current_review_cycle_list")).click();
		   General_Wait(2000);
	   WebElement element = byXPath(driver,propertyMap.get("addemployee"));
	   ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	   General_Wait(2000);
	  byXPath(driver,propertyMap.get("addemployee")).click();
	   General_Wait(2000);
	   }
	   
	  //adding the details of employee to employee list
	   @Test(priority=5)
	   public void addEmployee() 
	   {
		   General_Wait(2000);
	   byXPath(driver,propertyMap.get("emp_id_ele")).sendKeys(propertyMap.get("emp_id") +Keys.ENTER); 
	   General_Wait(2000);
		byXPath(driver,propertyMap.get("emp_name_ele")).sendKeys(propertyMap.get("emp_name") +Keys.ENTER);
		 General_Wait(2000);
		byXPath(driver,propertyMap.get("emp_mail_ele")).sendKeys(propertyMap.get("emp_mail") +Keys.ENTER);
		 General_Wait(2000);
		byXPath(driver,propertyMap.get("designation_ele")).sendKeys(propertyMap.get("designation") +Keys.ENTER);
		 General_Wait(2000);
		byXPath(driver,propertyMap.get("r1_id_ele")).sendKeys(propertyMap.get("r1_id") +Keys.ENTER);
		 General_Wait(2000);
		byXPath(driver,propertyMap.get("r1_name_ele")).sendKeys(propertyMap.get("r1_name") +Keys.ENTER);
		 General_Wait(2000);
		byXPath(driver,propertyMap.get("r1_mail_ele")).sendKeys(propertyMap.get("r1_mail") +Keys.ENTER);
		 General_Wait(2000);
		
		Select select = new Select(byXPath(driver,propertyMap.get("select_role")));
		select.selectByVisibleText("Employee");
		byXPath(driver,propertyMap.get("r2_id_ele")).sendKeys(propertyMap.get("r2_id") +Keys.ENTER);
		 General_Wait(2000);
		byXPath(driver,propertyMap.get("r2_name_ele")).sendKeys(propertyMap.get("r2_name") +Keys.ENTER);
		 General_Wait(2000);
		byXPath(driver,propertyMap.get("r2_mail_ele")).sendKeys(propertyMap.get("r2_mail") +Keys.ENTER);
		 General_Wait(2000);
		
		Select select1 = new Select(byXPath(driver,propertyMap.get("select_cycle")));
		select1.selectByVisibleText("Q4");
		byXPath(driver,propertyMap.get("r3_id_ele")).sendKeys(propertyMap.get("r3_id") +Keys.ENTER);
		 General_Wait(2000);
		byXPath(driver,propertyMap.get("r3_name_ele")).sendKeys(propertyMap.get("r3_name") +Keys.ENTER);
		 General_Wait(2000);
		byXPath(driver,propertyMap.get("r3_mail_ele")).sendKeys(propertyMap.get("r3_mail") +Keys.ENTER);
		 General_Wait(5000);
		 byXPath(driver,propertyMap.get("add")).click();
		General_Wait(3000);
		byXPath(driver,propertyMap.get("ok")).click();
	   }
	   
	   
	  @Test(priority=6)
	   public void removeEmployee() {
		  
		  General_Wait(2000);
		  byXPath(driver,propertyMap.get("checkbox")).click();
		  System.out.println("checkbox for removing employee is click");
		  General_Wait(2000);
		  //scroll down to 
		   WebElement element = byXPath(driver,propertyMap.get("remove_Employee"));
		   ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		   General_Wait(2000);
		//byXPath(driver,propertyMap.get("checkbox")).click();
		byXPath(driver,propertyMap.get("remove_Employee")).click();
		byXPath(driver,propertyMap.get("click_ok_ele")).click();
		
		
		List<WebElement> checkbox=(List<WebElement>) byXPath(driver,propertyMap.get("checkbox"));
		for(int i=0;i<checkbox.size();i++)
		{
			WebElement elem = checkbox.get(i);
			String id =elem.getAttribute("id");
			
			if(id.equalsIgnoreCase("checkboxid"))
			{
				elem.click();
				break;
			}
				
			
		} 
		
	  }	
		
		
		
	   
	  
	 
	  @Test(priority=7)
	   
	   public void goalSettingStatus() 
	   {
		General_Wait(2000);
		byXPath(driver,propertyMap.get("goal_setting_status")).click();
		General_Wait(2000);
	  }
		@Test(priority=8)
		public void assign()
		{
		byXPath(driver,propertyMap.get("assign")).click();
		General_Wait(2000);
		}
	
		
		//assigning goal to the employee
		@Test(priority=9) 
		public void goalSettings()
		{
			//byXPath(driver,propertyMap.get("goal_setting_status")).click();
			General_Wait(2000);
			
		Select select3 = new Select(byXPath(driver,propertyMap.get("select_bussiness_unit")));
		select3.selectByVisibleText("select_adm");
		General_Wait(2000);
		Select select4 = new Select(byXPath(driver,propertyMap.get("select_template")));
		select4.selectByVisibleText("temp"); 
		General_Wait(2000);
		byXPath(driver,propertyMap.get("deliverables")).click();
		byXPath(driver,propertyMap.get("weightage_1")).clear();
		byXPath(driver,propertyMap.get("weightage_1")).sendKeys("20" +Keys.ENTER);
		
		byXPath(driver,propertyMap.get("quality")).click();
		byXPath(driver,propertyMap.get("weightage_2")).clear();
		byXPath(driver,propertyMap.get("weightage_2")).sendKeys("20" +Keys.ENTER);
		
		byXPath(driver,propertyMap.get("compentency_development")).click();
		byXPath(driver,propertyMap.get("weightage_3")).clear();
		byXPath(driver,propertyMap.get("weightage_3']")).sendKeys("20" +Keys.ENTER);
		
		byXPath(driver,propertyMap.get("process")).click();
		byXPath(driver,propertyMap.get("weightage_4")).clear();
		byXPath(driver,propertyMap.get("weightage_4")).sendKeys("10" +Keys.ENTER);   
		
		byXPath(driver,propertyMap.get("innovation")).click();
		byXPath(driver,propertyMap.get("weightage_5")).clear();
		byXPath(driver,propertyMap.get("weightage_5")).sendKeys("10" +Keys.ENTER);
		
		byXPath(driver,propertyMap.get("terralogic_culture")).click();
		byXPath(driver,propertyMap.get("weightage_6")).clear();
		byXPath(driver,propertyMap.get("weightage_6")).sendKeys("10" +Keys.ENTER);
		General_Wait(2000);
		byXPath(driver,propertyMap.get("leadership")).click();
		byXPath(driver,propertyMap.get("weightage_7")).clear();
		byXPath(driver,propertyMap.get("weightage_7")).sendKeys("10" +Keys.ENTER);
		General_Wait(2000);
		
		byXPath(driver,propertyMap.get("submit")).click();
		General_Wait(2000);
		byXPath(driver,propertyMap.get("yes")).click();
		General_Wait(2000);
	   }
		
		
		//edit employee
		@Test(priority=10) 
		public void editEmployee()
		{
		byXPath(driver,propertyMap.get("edit")).click();
		General_Wait(2000);
		Select select5 = new Select(byXPath(driver,propertyMap.get("select_bussiness_unit")));
		select5.selectByVisibleText("select_adm");
		General_Wait(2000);
		Select select6 = new Select(byXPath(driver,propertyMap.get("select_template")));
		select6.selectByVisibleText("temp"); 
		General_Wait(2000);
		byXPath(driver,propertyMap.get("textline")).sendKeys(propertyMap.get("text") +Keys.ENTER); 
		General_Wait(2000);
		byXPath(driver,propertyMap.get("addrow")).click();
		   WebElement elementaddrow = byXPath(driver,propertyMap.get("textline2"));
		   ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elementaddrow);
		   byXPath(driver,propertyMap.get("textline2")).sendKeys(propertyMap.get("text2") +Keys.ENTER);
		   General_Wait(2000);
			
			byXPath(driver,propertyMap.get("submit")).click();
			General_Wait(2000);
			byXPath(driver,propertyMap.get("yes")).click();
			General_Wait(2000);
		   
		}
	  
		//logout from the epass
		
		@Test(priority=9)
		public void logout() {
			byXPath(driver, propertyMap.get("logout")).click();
		}
		
		@Test
		public void closeChromeBrowser() {
			closeBrowser();
		}

}

	