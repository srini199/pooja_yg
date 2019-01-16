//package com.yg.mobileTestcase;
//
//import java.io.File;
//import java.net.MalformedURLException;
//import java.net.URL;
////import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//import org.apache.xmlbeans.impl.xb.xsdschema.ListDocument.List;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.remote.DesiredCapabilities;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.BeforeSuite;
//import org.testng.annotations.BeforeTest;
//import org.testng.annotations.Test;
//
//import com.mobile_automation.solution.Mobile_automation_common_utils;
//
//import io.appium.java_client.MobileElement;
//import io.appium.java_client.android.AndroidDriver;
//
//public class YgCases extends Mobile_automation_common_utils {
////   @BeforeSuite
////	public void initialization() throws InterruptedException {
////		startAppiumServer();
////		
////	}
//	
//	@BeforeTest
//	public static void setDesiredCapability() throws MalformedURLException {
//		System.out.println("======1");
//		setDesiredCapability("/home/terralogic/Downloads/","YG_STAGING.apk","6.0","3300838e1b61b237","Android","com.yatragenie.activity","com.yatragenie.activity.MainActivity");
//	}
////		System.out.println("======1");
////		DesiredCapabilities capabilities = new DesiredCapabilities();
////		File appDir = new File("/home/terralogic/Downloads/");
////		File app = new File(appDir, "YG_STAGING.apk");
////		
////		capabilities.setCapability("session-override",true);
////		capabilities.setCapability("deviceName","3300838e1b61b237");
////		capabilities.setCapability("platformVersion","6.0");
////		capabilities.setCapability("app",app);
////		capabilities.setCapability("platformName","Android");
////		capabilities.setCapability("appPackage", "com.yatragenie.activity");
////		capabilities.setCapability("appActivity", "com.yatragenie.activity.MainActivity");
////		System.out.println("======1");
////		AndroidDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
////		driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
////	}
//	@Test
//    public void Login() {
//		//go to menu
//		
//		By btnMenu = By.xpath("//hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.view.ViewGroup[2]/android.view.ViewGroup");
//    //      System.out.println(btnlogin+"===");
//        driver.findElement(btnMenu).click();
//		
//	  //go to Genie Wallet
//        
//     //  By btnLogin = By.xpath("//hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.view.ViewGroup[2]/android.view.ViewGroup");
//      // driver.findElement(btnLogin).click();
//    	
//       // By btnList=By.className("android.widget.TextView");
//    	//List<WebElement> els1 =driver.findElement(btnList);
//     	//els1.get(2).click();
////    	
////    	MobileElement el2 = ((MobileElement) driver).findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.EditText[1]");
////    	el2.sendKeys("anjalibhashani13@gmail.com");
////        
////    	MobileElement el3 = ((MobileElement) driver).findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.EditText[2]");
////    	el3.sendKeys("Anjali@8");
////    	
////    	MobileElement el4 = ((MobileElement) driver).findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]");
////    	el4.click();
////    }
//	
//	
//	
////	@AfterClass
//////	@Test
////	public void cleanUp() {
////		stopAppiumServer();
////		System.out.println("====1");
////		
////	}
//	}
//}