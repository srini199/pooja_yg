package com.terralogic.framework;


/**
 * Create a setupBrowser method and all the element locators
 * @author Raviteja
 *
 */

import java.io.File;
import java.io.FileFilter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.terralogic.framework.PropertyLoaderUtil;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Common_resuable_methods{
	protected static WebDriver driver;
	protected static PropertyLoaderUtil propertyObj;
	protected static Map<String, String> propertyMap = new HashMap<String, String>();
	protected static String OsVersion;
	protected static String browserDriver;
 
	 /*  reusable method
    Version number :1
    Date: December 11th 
    Purpose  : Reusable method to load properties file into framework.
               Need to call this method from every test suite as a pre intilization 
               setup/ before test execution method . 
    Parameters : No 
     Owner : saicharan
     comments: added path acc to operating system by Pooja 
           
*/ 



	public  void loadProperties(){
 
			propertyObj = new PropertyLoaderUtil();
			String portalPropertiesFilePath = null;
			String OsVersion = System.getProperty("os.name", "generic").toLowerCase();
			System.out.println("opertaing system "+ OsVersion);
			if (OsVersion.contains("win")) {
				portalPropertiesFilePath = System.getProperty("user.dir")
						+ "\\src\\main\\java\\com\\terralogic\\framework\\portal.properties";
				System.out.println("portalPropertiesFilePath :" + portalPropertiesFilePath);
			} else if (OsVersion.contains("mac") || OsVersion.contains("linux")) {
				portalPropertiesFilePath = System.getProperty("user.dir")
						+ "/src/main/java/com/terralogic/framework/portal.properties";
				System.out.println("portalPropertiesFilePath :" + portalPropertiesFilePath);
			}
			
			propertyObj.loadProperties(portalPropertiesFilePath);
			propertyMap = propertyObj.getPropertyMap();
//			OsVersion=System.getProperty("os.name","generic");
//			System.out.println("Execution Operating System::"+OsVersion);
			

			//may require in future
			//Deleting the existing error screenshots
			// String filePath=System.getProperty("user.dir")+"/Screenshots/";
			//
			// File file = new File(filePath);
			// File[] files = file.listFiles(new FileFilter() {
			// public boolean accept(File pathname) {
			// return (pathname.getName().startsWith("Failed") && pathname.getName().endsWith(".png"));
			// }} );
			//
			// for (File f:files)
			// {if (f.isFile() && f.exists())
			// { f.delete();
			// System.out.println(f.getName()+" successfully deleted");
			// }else{
			// System.out.println("cant delete a file due to open or error");
			// } }  

			}
			 
	 /*  reusable method
    Version number :1
    Date: December 10th 
    Purpose  : Reusable method to setup browser driver based on a parameter call  
    Parameters :  browser name 
     Owner : saicharan
           
*/ 
	public void setUpBrowserDriver(String browser) throws Exception{
		if(browser.equalsIgnoreCase("firefox")){
			System.setProperty("webdriver.firefox.profile", "default");
			driver = new FirefoxDriver();
		}
		else if(browser.equalsIgnoreCase("chrome")){	 
			if(OsVersion.contains("Windows")){
				browserDriver=System.getProperty("user.dir")+"/BrowserDrivers/chromedriver.exe";
			}else if(OsVersion.contains("nux")){
				browserDriver=System.getProperty("user.dir")+"/BrowserDrivers/chromedriver_linux64";
			}else if(OsVersion.contains("Mac")){
				browserDriver=System.getProperty("user.dir")+"/BrowserDrivers/chromedriver";
			}		
			System.out.println("Loading Chrome Driver From ::"+browserDriver);
			System.setProperty("webdriver.chrome.driver",browserDriver);
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-extensions");
			driver = new ChromeDriver(options);	        
		}
		else if(browser.equalsIgnoreCase("headless")){
			browserDriver=System.getProperty("user.dir")+"/BrowserDrivers/phantomjs";
			System.setProperty("phantomjs.binary.path",browserDriver);
			driver = new PhantomJSDriver();
		}

		else{
			throw new Exception("Browser is not correct");
		}
	}

	 /*  reusable method
    Version number :1
    Date: December 10th 
    Purpose  : Reusable method to launch browser 
               Browser name will be referenced from input test file/properties file           
    Parameters : No 
     Owner : saicharan
           
*/ 
	public void launchBrowser() throws Exception {
		String browser = propertyMap.get("Application_Browser");
		// String browserDriver=propertyMap.get("Browser_Driver_Path");
		if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.firefox.profile", "default");
			driver = new FirefoxDriver();

			driver.manage().window().maximize();
			System.out.println("Firefox Browser launched Successfully");
		}
		else if(browser.equalsIgnoreCase("chrome")){

			// WebDriverManager will remove the dependency of downloading Chrome drivers upon every version updates
			WebDriverManager.chromedriver().setup();

			System.out.println("=============== Launching NEW CHROME driver ===============");
			String filePath=System.getProperty("user.dir")+"/Screenshots/";
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("download.default_directory",  filePath);
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("prefs", prefs);

			options.addArguments("--start-maximized");
			options.addArguments("--disable-extensions");
			options.addArguments("--disable-notifications");

			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			driver = new ChromeDriver(capabilities);

			Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
			String browserName = caps.getBrowserName();
			String browserVersion = caps.getVersion();
			System.out.println(browserName+" "+browserVersion+" on "+caps.getPlatform());

			System.out.println("Chrome Browser launched Successfully");
		}
		else if(browser.equalsIgnoreCase("headless")){
			browserDriver=System.getProperty("user.dir")+"/BrowserDrivers/phantomjs";
			System.setProperty("phantomjs.binary.path",browserDriver);
			driver = new PhantomJSDriver();
		}
		else{
			throw new Exception("Browser is not correct");
		}

	}
	 /*  reusable method
    Version number :1
    Date: December 10th 
    Purpose  : Reusable method to quite browser . 
               This method should be called after exeution of every script 
    Parameters : No 
     Owner : saicharan
           
*/ 
	public void closeBrowser() {
		try{
			Thread.sleep(2000);
			driver.close();
			System.out.println("Browser closed Successfully");
			if(driver!=null) {
				driver.quit();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	 /*  reusable method
    Version number :1
    Date: December 10th 
    Purpose  : Reusable method to use general wait 
               This method is to define a general wait across the framework 
    Parameters : No 
     Owner : saicharan
           
*/ 

	public void General_Wait(long waitSeconds) {
		try{
			Thread.sleep(waitSeconds);
			System.out.println("Waiting.... ::"+waitSeconds+"ms");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	 /*  reusable method
    Version number :1
    Date: December 10th 
    Purpose  : Reusable method to perform a standard page sroll in web application 
               This method should be called after exeution of every script 
    Parameters : No 
     Owner : saicharan
           
*/ 
	public void Page_Scroll() throws Exception {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,250)", "");
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	 /*  reusable method
    Version number :1
    Date: December 10th 
    Purpose  : Defined methods to return the webelement based on parameters . 
               It will return the xpath and from hash map ( properties file ) and store 
               this in element . example usage is 
               WebElement saveRunsheet = byXPath(driver, propertyMap.get("ECOM_RS_saveRunsheet"));
			   saveRunsheet.click();            
    Parameters : XPATH,Driver name 
     Owner : saicharan
           
*/ 
	public static WebElement byXPath(WebDriver driver, String xpath){
		WebElement element = driver.findElement(By.xpath(xpath));
		return element;
	}

	public static WebElement byID(WebDriver driver, String id){
		WebElement element = driver.findElement(By.id(id));
		return element;
	}

	public WebElement byClass(WebDriver driver, String className){
		WebElement element = driver.findElement(By.className(className));
		return element;
	}

	public WebElement byLinkText(WebDriver driver, String linkText){
		WebElement element = driver.findElement(By.linkText(linkText));
		return element;
	}

	public WebElement byPartialLinkText(WebDriver driver, String partialLinkText){
		WebElement element = driver.findElement(By.partialLinkText(partialLinkText));
		return element;
	}

	public WebElement byTagName(WebDriver driver, String tagName){
		WebElement element = driver.findElement(By.tagName(tagName));
		return element;
	}
	public WebElement byName(WebDriver driver, String Name){
		WebElement element = driver.findElement(By.name(Name));
		return element;
	}
	
	/*  reusable method
    Version number :1
    Date: December 10th 
    Purpose  : Method to wait for a common page load across the frame work
    Parameters : No 
     Owner : saicharan
           
*/ 

		public void waitForPageLoaded() {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};
		try {
			Thread.sleep(1000);
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(expectation);
		} catch (Throwable error) {
			Assert.fail("Timeout waiting for Page Load Request to complete.");
		}
	}

	
}
