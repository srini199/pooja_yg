package com.mobile_automation.solution;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

public class YgCases{

	@SuppressWarnings("rawtypes")
	public static AppiumDriver driver;

	@SuppressWarnings("rawtypes")
	@Test
	public void setup() throws MalformedURLException {

		System.out.println("======1");
		File appDir = new File("/home/terralogic/Downloads/");
		File app = new File(appDir, "YG_STAGING.apk");
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName","3300838e1b61b237");
		capabilities.setCapability("platformVersion","6.0");
		capabilities.setCapability("platformName","Android");
		capabilities.setCapability("appPackage", "com.yatragenie.activity");
		capabilities.setCapability("appActivity", "com.yatragenie.activity.MainActivity");
		System.out.println("======1");
		driver = new AppiumDriver(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
		driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
	}


//		@Test
//		public void appiumExampleTest() throws Exception {
//			System.out.println("driver=" + driver+"============"); 
//	        String app_package_name = "com.flipkart.android:id/";
//	        By btn_mlogin = By.id(app_package_name + "btn_mlogin");
//	        System.out.println(btn_mlogin+"===");
//	        driver.findElement(btn_mlogin).click();
//	        
//	        
//	       
//	        //1st way
//	        By mobileNo = By.id(app_package_name + "mobileNo");
//	        System.out.println(mobileNo);
//	        
//	        By password=By.id(app_package_name+ "et_password");
//	        System.out.println(password);
//	        
//	        By btn_mlogin1 = By.id(app_package_name + "btn_mlogin");
//	        System.out.println(btn_mlogin1+"===");
//	        
//	        //By cancel=By.id("com.google.android.gms.id/cancel");
//	        //System.out.println(cancel+"=======");
//	        
//	        //driver.findElement(cancel).click();
//	        driver.findElement(mobileNo).sendKeys("8770999417");
//	        
//	        driver.findElement(password).sendKeys("Anjali@8");
//	        driver.findElementById("com.flipkart.android:id/btn_mlogin").click();
//	         


//}
//		/*@AfterClass
//		public void tearDown() {
//			if (driver != null) {
//				driver.quit();
//			}
//		}*/
//}
}

