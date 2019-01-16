package com.web_automation.solution;


import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.gargoylesoftware.htmlunit.javascript.host.Set;
import com.terralogic.framework.Common_resuable_methods;

public class Web_temp extends Common_resuable_methods 
{
	/*
	 * reusable method Version number :1 
	 * Date: December 12th 
	 * Purpose : Reusable method to get frames from a website . 
	 * Parameters : webDriver,url,framename,framelocator
	 * Owner : prasanthi
	 * 
	 */
	// frames
	public void testframes(WebDriver driver, String framename, String frameLocator, int index) 
	{
		driver.switchTo().frame(framename);//by using name or id
		driver.switchTo().frame(index);//by using index
		driver.findElement((By)byLinkText(driver,frameLocator)).click();
		
	}

	/*
	 * reusable method Version number :1 
	 * Date: December 12th Purpose : Reusable method to open multiple tabs inside the window switching to the chid window from parent window. 
	 * Parameters :webDriver,url2
	 * comments:url1 to open the first site and url2 to open another site   
	 * Owner : prasanthi
	 */
	 

	public void Multitabs(WebDriver driver, String url2)
	{
		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		driver.get(url2);
	}

	/*
	 * reusable method Version number :1 
	 * Date: December 12th 
	 * Purpose : Reusable method to reset the password of facebook and get the otp from mail 
	 * Parameters :webDriver,url,emaillocator,mail,passwordlocator,password,loginlocator,otp,continue,url1,gmaillocator,
	 				mail2,passwordlocator2,password2,searchlocator,typeinsearch,firstmaillocator,csslocator
      	 
	 * comments : url to open the facebook B
	 * 			  url1 to open mail. added java mail dependencies to read mail
	 * Owner : prasanthi
	 * 
	 */
	// reset the password to get otp
	
	public void reset(WebDriver driver, String url,String emailLocator, String mail, String passwordLocator,
			String password, String loginLocator, String otp, String contnue, String url1, String gmailLocator,
			String mail2, String passwordLocator2, String password2, String searchLocator, String typeinsearch,
			String firstmailLocator, String cssLocator) {
		driver.get(url);
		driver.findElement((By)byID(driver, emailLocator)).sendKeys(mail);// opening facebook by mail
		driver.findElement((By)byID(driver, passwordLocator)).sendKeys(password);// giving wrong password
		driver.findElement((By)byID(driver,loginLocator)).click();// clicking loginbutton
		driver.findElement((By)byXPath(driver,otp)).click();
		driver.findElement((By)byName(driver,contnue)).click();

		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		// opening mail
		driver.get(url1);
		driver.findElement((By)byID(driver,gmailLocator)).sendKeys(mail2 + Keys.ENTER);
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(password)));
		driver.findElement((By)byName(driver,passwordLocator2)).sendKeys(password2 + Keys.ENTER);
		// search the facebook mails in inbox
		driver.findElement((By)byName(driver,searchLocator)).sendKeys(typeinsearch + Keys.ENTER);
		driver.findElement((By)byClass(driver,firstmailLocator)).click();
		String n = "";
		List<WebElement> mail1 = driver.findElements(By.cssSelector(cssLocator));
		for (int i = 0; i < mail1.size(); i++) {
			n = n + mail1.get(i).getText();
			System.out.println(n + "====");

		}

	}

	/*
	 * reusable method Version number :1 
	 * Date: December 12th 
	 * Purpose : Reusable method to get whole list in dropdown 
	 * Parameters : webDriver, url,ddlocator 
	 * Owner : prasanthi
	 * 
	 */
	
	// to get whole list in dropdown
		public void selectlistofdropdownvalues(WebDriver driver, String ddLocator)
			{
		// dropdown box to get list of options
		WebElement dropdown = driver.findElement(By.id(ddLocator));
		Select dd = new Select(dropdown);
		List<WebElement> list = dd.getOptions();
		int total = list.size();
		System.out.println("total count is " + total);
		for (WebElement ele : list) {
			String name = ele.getText();
			System.out.println("list are--" +name);
		}

	}

	/*
	 * reusable method Version number :1
	 * Date: December 12th 
	 * Purpose : Reusable method to select the specific option in the dropdown box by using index
	             value and text . 
	 * Parameters : webDriver,ddlocator,byvalue,byindex,bytext
	 * Owner : prasanthi
	 * 
	 */
	// to select specific option in dropdown

	public void selectspecificdropdownvalues(WebDriver driver, String ddLocator,  String byvalue, int byindex, String bytext) 
	{
		
		WebElement dropdown = driver.findElement((By)byID(driver,ddLocator));
		Select dd = new Select(dropdown);
		dd.selectByValue(byvalue);//by using value locator
		dd.selectByIndex(byindex);//by using index locator
		dd.selectByVisibleText(bytext);//by using text locator

	}

	/*
	 * reusable method Version number :1 
	 * Date:December 12th 
	 * Purpose : Reusable method to open nested tabs with in the tab .
	 * Parameters : webDriver,tab1locator,tab2locator,url2,enterinsearch
	 * Owner : prasanthi
	 * 
	 */
	// nested tabs
public void nestedtabs(WebDriver driver,String tab1Locator, String url2,String tab2Locator, String enterinsearch ) 
{
	String parent = driver.getWindowHandle();
	System.out.println("parent window id is "+parent);
	driver.findElement((By)byXPath(driver,tab1Locator)).click();//open the tab inside the window
	Set allwindows = (Set) driver.getWindowHandles();
	ArrayList<String> tabs = new ArrayList<String>();
	driver.switchTo().window(tabs.get(1));//open the another tab from the previous tab
	driver.findElement((By)byName(driver,tab2Locator)).sendKeys(enterinsearch +Keys.ENTER);
	//Actions act = new Actions(driver);
	((JavascriptExecutor)driver).executeScript("window.open()");
	ArrayList<String> tabs1 = new ArrayList<String>(driver.getWindowHandles());
	driver.switchTo().window(tabs1.get(2));
	driver.get(url2);	
}
}
