package com.yg.mobileTestcase;

import java.net.MalformedURLException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.mobile_automation.solution.Mobile_automation_common_utils;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.TouchAction;

public class MedukoTestCase extends Mobile_automation_common_utils {
	@BeforeTest
	public static void setDesiredCapability() throws MalformedURLException {
		System.out.println("======1");
		setDesiredCapability("/home/terralogic/Downloads/","app-release.apk","6.0","3300838e1b61b237","Android","com.medkumo.healthsolutions","com.medkumo.healthsolutions.MainActivity");
	}  
	@Test
	public void logIn() throws InterruptedException {
		System.out.println("hello world");
		mobileDriver.findElement(By.id("com.medkumo.healthsolutions:id/mobile_number_text_input_layout")).sendKeys("944038893612");
		//Thread.sleep(1000);
		mobileDriver.findElement(By.id("com.medkumo.healthsolutions:id/password_text_input_layout")).sendKeys("vijaya*123");
	//	Thread.sleep(1000);
		mobileDriver.navigate().back();
		mobileDriver.findElement(By.id("com.medkumo.healthsolutions:id/takemein")).click();
		Thread.sleep(2000);
	  //  Thread.sleep(1000);
	   // driver.findElement(By)
	}
 //  @Test
//   public void clickMenuButton() {
//	   WebElement ImageIcon=mobileDriver.findElement(By.className("//android.widget.ImageButton[@index='2']"));
//       ImageIcon.click();
       //(By.xpath("//android.widget.TextView[@index='1']"));
	  // mobileDriver.findElement(By.xpath("//android.widget.ImageButton[@bounds='[0,96][48,160]']"))
	 // .click();
	//   driver.findElement(By.xpath(("//android.widget.Button[@content-desc='Open navigation drawer']"))).click();
	 //  TouchAction location=new TouchAction((MobileDriver) driver_touch);
	  // location.tap(x, y).perform();
	   //mobileDriver.findElementByName("Open navigation drawer").click();
	   //WebElement el=(WebElement) mobileDriver.findElementByAndroidUIAutomator(new UiSelector().text("" + something + ""));
	   
   //}
	@Test
	public void checkNotificationBtn() {
	    mobileDriver.findElement(By.id("com.medkumo.healthsolutions:id/bookAppointment")).click();   
	}
   
}
