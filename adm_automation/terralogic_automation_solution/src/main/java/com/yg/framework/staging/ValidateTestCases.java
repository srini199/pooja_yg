package com.yg.framework.staging;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import com.web_automation.solution.Web_automation_common_utils;

public class ValidateTestCases extends Web_automation_common_utils {

	public void validateUserLogin(WebDriver driver) {
		String expectedUserName = "Hello, kaku";
		try {
			(byXPath(driver, propertyMap.get("Login"))).click();
			Thread.sleep(2000);
			byID(driver, propertyMap.get("email_field")).sendKeys(propertyMap.get("userName"));
			byID(driver, propertyMap.get("password_field")).sendKeys(propertyMap.get("pwd"));
			byID(driver, propertyMap.get("click")).click();
			General_Wait(20000);
			// String actualUserName = byXPath(driver,
			// propertyMap.get("user_name_field")).getText();
			String actualUserName = driver
					.findElement(By.xpath("/html/body/yg-root/yg-header/header/div/div/div/div/div/div[2]/ul[2]/li[1]"))
					.getText();
			System.out.println("username " + actualUserName);
			Assert.assertEquals(expectedUserName, actualUserName);
			System.out.println(expectedUserName + "==" + actualUserName + " Login is validated");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void validateLogout(WebDriver driver) {
		//validateUserLogin(driver);
		String expectedText = "Login / Signup";
		byXPath(driver, propertyMap.get("img_icon")).click();
		General_Wait(2000);
		byXPath(driver, propertyMap.get("logout")).click();
		String actualText = (byXPath(driver, propertyMap.get("Login"))).getText();
		Assert.assertEquals(expectedText, actualText);
		System.out.println(expectedText + "==" + actualText + " Logout is validated");
	}

	public void validateOneWayBookingTicket(WebDriver driver) {
		// validateLogin(driver);
		String databaseName = "yg_bookings";
		String tableName = "BOOKINGS";
		String expectedStatus = "RESERVED";
		byXPath(driver, propertyMap.get("img_icon")).click();
		General_Wait(2000);
		byXPath(driver, propertyMap.get("my_booking")).click();
		General_Wait(20000);
		byXPath(driver, propertyMap.get("ticket")).click();
		General_Wait(10000);
		String ticketId = byXPath(driver, propertyMap.get("ticket_id")).getText();
		System.out.println("ticketID:" + ticketId);
//		String ticketId=driver.findElement(By.xpath("//div[@id='onwardPNR']")).getText();
		DbConnectivity db = new DbConnectivity();
		String actualStatus = db.stausOfTicket(databaseName, ticketId, tableName);
		Assert.assertEquals(actualStatus, expectedStatus);
		System.out.println(actualStatus + "==" + expectedStatus + " book status is validated");
	}

	public void validateBusPrice(String boardingCity, String destinationCity, String expectedBusFare, String journeyDate, String busUniqueId) {
		System.out.println(" receiving date in validation:"+journeyDate);
		int dayForSelectedBus=convertDateIntoDay(journeyDate);
		String databaseName = "yg_inventory";
		String tableName = "DAILY_INVENTORY";
		int inventoryId=Integer.parseInt(busUniqueId);
		DbConnectivity db = new DbConnectivity();
		String dbBusfare = db.reteriveBusprice(databaseName, tableName, boardingCity, destinationCity,
				inventoryId);
		System.out.println("dbBusfare from db:"+dbBusfare);
		//String dbExpectedBusFare=breakThePrice(dbBusfare,String.valueOf(dayForSelectedBus));
		
		Assert.assertEquals(dbBusfare, expectedBusFare+".0");
		System.out.println(dbBusfare + "==" + expectedBusFare+".0" + " so bus fare is validated");
	}

	public int convertDateIntoDay(String journeyDate) {
		int day = 0;
		String[] dateList = journeyDate.split("/");		
		try {
			Date d = new SimpleDateFormat("MMMM").parse(dateList[1]);
			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			// System.out.println("for " + dateList[1] + " " + cal.get(Calendar.MONTH));
			cal.set(Integer.parseInt(dateList[2]), cal.get(Calendar.MONTH), Integer.parseInt(dateList[0]));
			day = cal.get(cal.DAY_OF_WEEK);
			System.out.println("Day for selected bus:" + day);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return day;
	}

	public void valdiateTotalNoOfBuses(String boardingCity, String  destinationCity,LinkedList<String> idList) {
		int expectedNoOfBuses=idList.size();
		String databaseName = "yg_inventory";
		String tableName = "INVENTORY_MASTER";
		DbConnectivity db = new DbConnectivity();
		int actualCount = db.reteriveTotalNoOfBuses(databaseName, tableName, boardingCity, destinationCity, idList);
		Assert.assertEquals(actualCount, expectedNoOfBuses);
		System.out.println(actualCount + "==" + expectedNoOfBuses + " so total no of buses are validated");
	}

	public void validateUpdatedUserDetail(WebDriver driver) {
		validateUserLogin(driver);
		byXPath(driver, propertyMap.get("img_icon")).click();
		General_Wait(4000);
		WebElement ele = byXPath(driver, propertyMap.get("user_profile"));
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", ele);
		General_Wait(2000);
		byXPath(driver, propertyMap.get("edit_profile")).click();
		WebElement changeNameEle = driver.findElement(By.xpath("//input[@placeholder='User Name']"));
		String userNewName = propertyMap.get("user_new_name");
		changeNameEle.clear();
		changeNameEle.sendKeys(String.valueOf(userNewName));
//		changeNameEle.sendKeys(Keys.chord(Keys.CONTROL, "a"), String.valueOf(userNewName));
		byXPath(driver, propertyMap.get("save_detail")).click();
		General_Wait(5000);
		String updateText = byXPath(driver, propertyMap.get("success_msg")).getText();
		if (updateText.contains("successfully")) {
			byXPath(driver, propertyMap.get("cross_sign")).click();
			General_Wait(3000);
		} else {
			System.out.println("something is wrong please try again to update your detail");
		}
		byXPath(driver, propertyMap.get("homepage")).click();
		General_Wait(2000);
		String userName = driver
				.findElement(By.xpath("/html/body/yg-root/yg-header/header/div/div/div/div/div/div[2]/ul[2]/li[1]"))
				.getText();
		String[] nameArray = userName.split(",");
		String actualUserName = nameArray[1].trim();
		String databaseName = "yg_inventory";
		String tableName = "USERS";
		String user = "pooja.mittal30@gmail.com";
		DbConnectivity db = new DbConnectivity();
		String expectedUserName = db.reteriveUserName(databaseName, tableName, user);
		Assert.assertEquals(actualUserName, expectedUserName);
		System.out.println(actualUserName + "==" + expectedUserName + " validated updated user detail");
	}

	public void validateCancelTicket(WebDriver driver, Boolean fullTicketCancellation) {
		validateUserLogin(driver);
		String userName="pooja.mittal30@gmail.com";
		byXPath(driver, propertyMap.get("img_icon")).click();
		General_Wait(6000);
		byXPath(driver, propertyMap.get("my_booking")).click();
		General_Wait(22000);
		String walletAmountBeforeCancellation=reteriveWalletAmount(userName);
//		System.out.println("walletAmountBeforeBooking:"+walletAmountBeforeBooking);
//		String walletAmntBeforeCancellation = checkWalletAmount(driver);
		System.out.println("before cancelling ticket Genie wallet amnt:" + walletAmountBeforeCancellation);
		String databaseName = "yg_bookings";
		String tableName = "BOOKINGS";
		// select ticket for cancel
		byXPath(driver, propertyMap.get("ticket")).click();
		General_Wait(4000);
		// read ticket id
		String ticketId = byXPath(driver, propertyMap.get("ticket_id")).getText();
		System.out.println("ticketID:" + ticketId);
		// clicked on cancel ticket
		byXPath(driver, propertyMap.get("cancel_ticket_icon")).click();
		General_Wait(4000);
		// press yes for confirmation
		byXPath(driver, propertyMap.get("confirm_cancel")).click();
		General_Wait(4000);
		// selected cancelled all seat
		driver.findElement(By.xpath("//button[@class='button_yesno']")).click();
		// byXPath(driver, propertyMap.get("select-seat_for_cancel")).click();
		General_Wait(4000);
		List<WebElement> noOfseatAvaiableForCancellation=driver.findElements(By.xpath("//div[contains(@class,'col-sm-4')]"));
		System.out.println("noOfseatAvaiableForCancellation:"+noOfseatAvaiableForCancellation.size());
		if(noOfseatAvaiableForCancellation.size()>1 && fullTicketCancellation) {
			List<WebElement> totalSeat=driver.findElements(By.xpath("//div[@class='pannel-body text-left details1']"));
			for(int i=0; i<totalSeat.size(); i++) {
				driver.findElement(By.xpath("//div[@class='pannel-body text-left details1']")).click();
				General_Wait(4000);
			}			
		}else if(noOfseatAvaiableForCancellation.size()>1 && (!fullTicketCancellation)) {
			driver.findElement(By.xpath("//div[@class='pannel-body text-left details1']")).click();
			General_Wait(4000);
		}else if(noOfseatAvaiableForCancellation.size()==1) {
			driver.findElement(By.xpath("//div[@class='pannel-body text-left details1']")).click();
			General_Wait(4000);
		}		
		// final click on cancel
		byXPath(driver, propertyMap.get("final_cancel")).click();
		General_Wait(8000);
		driver.findElement(By.xpath("/html/body/yg-root/yg-modals/div[16]/div/div/div/div/button[2]/span")).click();
		General_Wait(4000);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,100)", "");
		driver.findElement(By.xpath("/html/body/yg-root/yg-modals/div[15]/div/div/div/div/button[2]")).click();
		General_Wait(4000);
		DbConnectivity db = new DbConnectivity();
		String otp = db.reteriveOTP(databaseName, ticketId, tableName);
		System.out.println("OTP of cancelled ticket:" + otp);
		byXPath(driver, propertyMap.get("cancel_otp")).sendKeys(otp);
		byXPath(driver, propertyMap.get("refund_type")).click();
		General_Wait(2000);
		List<WebElement> refundTypeList = driver.findElements(By.xpath("//ul[@class='dropdown-menu refundUl']"));
		// System.out.println("refundtypesize:" + refundTypeList.size());
		for (WebElement refundType : refundTypeList) {
			System.out.println("refundType:" + refundType.getText());
			if (refundType.getText().contains("Wallet")) {
				driver.findElement(By.xpath(
						"/html/body/yg-root/yg-modals/div[17]/div/div/div[2]/div/div/div/div/ul/li[2]/div[2]/ul/li[1]/a"))
						.click();
			}
		}
		driver.findElement(By.xpath(
				"//div[@class='form-group sub-btn']//button[@class='form-control btn_conform  enterotp_value' and text()='Confirm']"))
				.click();
		General_Wait(6000);
		// WebDriverWait wait = new WebDriverWait(driver,30);
		// wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/yg-root/yg-modals/div[19]/div/div/div/div/button")));
		WebElement finalOkWhileCancellingTicket = driver
				.findElement(By.xpath("/html/body/yg-root/yg-modals/div[19]/div/div/div/div/button"));
		Actions action = new Actions(driver);
		action.moveToElement(finalOkWhileCancellingTicket);
		action.click();
		action.perform();
//		System.out.println("ticket is cancelled");
		General_Wait(4000);
		String cancelledStatus = db.stausOfTicket(databaseName, ticketId, tableName);
		Assert.assertEquals("CANCELLED", cancelledStatus);
		System.out.println("Ticket has been cancelled");
		driver.navigate().refresh();
		String walletAmountAfterCancellation=reteriveWalletAmount(userName);
		System.out.println("Wallet amnt after cancelling the ticket:" + walletAmountAfterCancellation);
		validateLogout(driver);
		System.out.println("user successfully logout");
	}

	public String checkWalletAmount(WebDriver driver) {
		List<WebElement> sideList = driver.findElements(By.xpath("//p[@class='web-text']"));
//		System.out.println("list size:" + sideList.size());
		String walletAmnt = "";
		for (int i = 0; i < sideList.size(); i++) {
			String text = sideList.get(i).getText();
			if (text.contains("GENIE WALLET")) {
				walletAmnt = text;
				walletAmnt = walletAmnt.replace("?", "");
				break;
			}
		}
		walletAmnt = walletAmnt.replaceAll("GENIE WALLET", "");
		return walletAmnt;
	}

	public void validateWhetherBusIsCreatedOrNot(WebDriver driver, String actualBusServiceId) {
		System.out.println(actualBusServiceId + " this is in db");
		String databaseName = "yg_inventory";
		String tableName = "INVENTORY_MASTER";
		DbConnectivity db = new DbConnectivity();
		int expectedActiveFlag = db.reteriveBusCreationTime(databaseName, tableName, actualBusServiceId);
		System.out.println(expectedActiveFlag + " is in db");
		if (expectedActiveFlag == 1) {
			System.out.println("Active_flag is in db:" + expectedActiveFlag + " hence bus is successfully created");
		} else {
			System.out.println(
					"Active_flag is in db:" + expectedActiveFlag + " hence bus is not updated in db please check");
		}

	}

	public void reteriveInActiveBusId(WebDriver driver, String actualBusServiceId) {
		String databaseName = "yg_inventory";
		String tableName = "INVENTORY_MASTER";
		DbConnectivity db = new DbConnectivity();
		int expectedActiveFlag = db.reteriveBusCreationTime(databaseName, tableName, actualBusServiceId);
		if (expectedActiveFlag == 0) {
			System.out.println("Active_flag is in db:" + expectedActiveFlag + " bus is deleted successfully");
		} else {
			System.out.println("Active_flag is in db:" + expectedActiveFlag + " hence bus is not deleted");
		}
	}
	
	public String breakThePrice(String dbBusFare, String selectedDay) {
		String dbindividualBusFare=null;
		String[] priceList=dbBusFare.split(",");
		String[] individualPriceList = null;
		Map<String,String> map=new LinkedHashMap<>();
		for(int i=0; i<priceList.length; i++) {			
			individualPriceList=priceList[i].split(":");
			map.put(individualPriceList[0],individualPriceList[1]);	
		}
		for(String s: map.keySet()) {
			String key=s.toString();
			String value=map.get(key).toString();
			if(key.equals(selectedDay)) {
				dbindividualBusFare=value;
			}			
		}
		return dbindividualBusFare;
	}
	
	public String reteriveWalletAmount(String userName) {
		String databaseName = "yg_inventory";
		String tableName = "USERS";
		DbConnectivity db = new DbConnectivity();
		String walletAmount=db.reteriveWalletAmountFromDb(databaseName, tableName, userName);
		return walletAmount;
	}

}
