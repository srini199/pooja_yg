package com.terralogic.framework;


import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import java.time.Duration;
import java.time.Duration;
import static io.appium.java_client.touch.offset.PointOption.point;
import static io.appium.java_client.touch.offset.PointOption.point;
public class FlipkartWorking{
	
	
	public static AndroidDriver driver;
	WebDriverWait wait;

	@BeforeTest
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
		driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
		driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
	}
	@Test
	public void hello() {
		System.out.println("====");
	}
//	public void appiumExampleTest() throws Exception {
//    	//login
//    	System.out.println("driver=" + driver+"============"); 
//    	
//        String app_package_name = "com.flipkart.android:id/";
//        //Set<String> contextNames = driver.getContextHandles();
//        //System.out.println("about to print the context list");
//        //System.out.println(contextNames);
//        
//        //1st way
//        By btnlogin = By.id(app_package_name + "btn_login");
//        System.out.println(btnlogin+"===");
//        driver.findElement(btnlogin).click();
//        
//        
//        By mobno = By.id(app_package_name + "mobileNo");
//        System.out.println(mobno);
//        driver.findElementById("com.flipkart.android:id/mobileNo").sendKeys("8770999417");
//        
//        By password=By.id(app_package_name+ "et_password");
//        System.out.println(password);
//        driver.findElement(password).sendKeys("Anjali@8"); 
//        
//        By btn_mlogin1 = By.id(app_package_name + "btn_mlogin");
//        System.out.println(btn_mlogin1+"===");
//        
//        driver.findElementById("com.flipkart.android:id/btn_mlogin").click();
    	 
        
               
        
        //By search=By.id(app_package_name+ "search_widget_textbox");
        //System.out.println(search);
        //driver.findElementByClassName("android.widget.TextView").sendKeys("mobiles");
        
        //driver.findElementById("com.flipkart.android:id/txt_title").click();
        
        //driver.findElementByClassName("android.widget.TextView").click();
        
        //logout
        
        // WebElement ImageIcon=driver.findElement(By.xpath("//android.widget.ImageButton"));
         //ImageIcon.click();
        //System.out.println("====1");
         
         
      //   driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"My Account\"));").click();
       //  System.out.println("=====2");
         
        // WebElement test=driver. findElementByAndroidUIAutomator(new UiSelector()).scrollIntoView(description(\"Logout of this app\”));").click();
        // WebElement test=driver. findElementBy.AndroidUIAutomator(“new UiSelector().description(\”equals\”)”);
         
       //  WebElement element = driver.findElementByAndroidUIAutomator(“new UiSelector().index(0)”);
       //  assertEquals(“android.widget.FrameLayout”, element.getTagName());
        
       //driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(className("//android.view.View@index['1']"));").click();
        //System.out.println("====3");
        
        
        //Working
        //WebElement ImageIcon=driver.findElement(By.xpath("//android.widget.ImageButton"));
        //ImageIcon.click();
        
        
        //working
        //By appNotification=By.id(app_package_name+"inappnotification_icon");
        //System.out.println(appNotification);
        //driver.findElement(appNotification).click();
        //.out.println("====");
        
        
        //working scrolling
        
       //driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(resourceId(\"com.flipkart.android:id/list_row1\"));").click();
        
        //Not working
        
       // TouchAction touchAction = new TouchAction(driver);
       // touchAction.press(10).moveTo(500,500).release().perform(); //error in longpress
        
        //TouchAction touchAction = new TouchAction(driver); 
        //touchAction.tap(PointOption.point(750,750)).perform();
        
        
        //new TouchAction(driver).press(PointOption.point(550, 640))
        //.waitAction()
        //.moveTo(PointOption.point(550, 60))
        //.release()
        //.perform();
       // System.out.println("======");
        
        //working
        //By cartIcon=By.id(app_package_name+"cart_icon");
        //System.out.println(cartIcon);
        //driver.findElement(cartIcon).click();
                
        
        
        //working//inside menu
        
        //WebElement img=driver.findElement(By.xpath("//android.widget.TextView[@index='1']"));
        //img.click();
        
        
        //driver.findElementByXPath("//android.widget.RelativeLayout[2]
        /*WebElement img=driver.findElementByXPath("//android.widget.ImageView[@index='0']");
        img.click();
        System.out.println("@@@@@@@@");
        
        WebElement img1=driver.findElement(By.xpath("//android.widget.ImageView[@index='1']"));
        img1.click();
        System.out.println("=====");
        
        WebElement img2=driver.findElement(By.xpath("//android.widget.ImageView[@index='2']"));
        img2.click();
        System.out.println("*******");
        
        System.out.println("====");
        //driver.findElement(By.id("//com.flipkart.android:id/banner_image[@index='2']"));*/
        
        
       
        //working
        //driver.findElementById("com.flipkart.android:id/titleViewParent").click();
        
//	}
	//	  @AfterClass
//		  public void tearDown() {
//				if (driver != null) {
//					driver.close();
//				}  
//		}
      
	}

	
