package com.yg.framework.staging;

import java.util.List;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.terralogic.framework.Common_resuable_methods;

import io.github.bonigarcia.wdm.WebDriverManager;

@Listeners(YGCustomListener.class)
public class BusAdapter extends Common_resuable_methods {

	/*
	 * points: check what's the dropping and boarding point for that route check
	 * which bus bo check date for that route change booked bus ticket path
	 * 
	 */
	WebDriver driver;
	ValidateTestCases validateTest = new ValidateTestCases();
	String busServiceId;

	@BeforeClass
	public void loadProperty() {
		loadProperties();
	}

	@BeforeTest
	public void initaliztion() {
		String OsVersion = System.getProperty("os.name", "generic").toLowerCase();
		System.out.println("opertaing system " + OsVersion);
		// if (OsVersion.contains("win")) {
		// System.setProperty("webdriver.chrome.driver",
		// System.getProperty("user.dir") + "\\browser\\chromedriver.exe");
		// }else if (OsVersion.contains("mac") || OsVersion.contains("linux")) {
		// System.setProperty("webdriver.chrome.driver",
		// System.getProperty("user.dir")+"/browser/chromedriver");
		// }
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("http://65.49.80.209/#/");
		driver.manage().window().maximize();
	}

	public void loginToAdminAccount() {
		try {
			(byXPath(driver, propertyMap.get("Login"))).click();
			Thread.sleep(2000);
			byID(driver, propertyMap.get("email_field")).sendKeys(propertyMap.get("adminName"));
			byID(driver, propertyMap.get("password_field")).sendKeys(propertyMap.get("adminPwd"));
			byID(driver, propertyMap.get("click")).click();
			General_Wait(10000);
			// String actualAdminName =
			// driver.findElement(By.xpath("//span[@class='username']")).getText();
			// System.out.println("adminName:" + actualAdminName);
			// System.out.println("successfully login");
			// Assert.assertEquals(expectedUserName, actualUserName);
			// System.out.println(expectedUserName + "==" + actualUserName + "
			// Login is
			// validated");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 1)
	public void createABus() {
		loginToAdminAccount();
		List<WebElement> serviceList = driver.findElements(By.xpath("//li[@id='Services']//a[text()='Services']"));
		// System.out.println("webelement size:"+serviceList.size());
		for (WebElement serviceEle : serviceList) {
			// System.out.println(serviceEle.getText());
			if (serviceEle.getText().equals("SERVICES")) {
				serviceEle.click();
				break;
			}
		}
		General_Wait(2000);
		Select select = new Select(byID(driver, propertyMap.get("admin_service_list")));
		select.selectByVisibleText("Service Master");
		General_Wait(3000);
		byXPath(driver, propertyMap.get("add_new_service")).click();
		General_Wait(1000);
		// select service name
		byXPath(driver, propertyMap.get("service_name_field")).sendKeys(propertyMap.get("service_name"));
		// select service Id
		busServiceId = propertyMap.get("service_id");
		byXPath(driver, propertyMap.get("service_id_field")).sendKeys(busServiceId);
		General_Wait(5000);

		if (!(driver.findElement(By.xpath("//div[@class='modal fade']")).isDisplayed())) {
			// selecting service operator
			Select selectOperator = new Select(byXPath(driver, propertyMap.get("operator_name_field")));
			selectOperator.selectByVisibleText(propertyMap.get("operator_name"));
			// selecting seat layout
			Select selectSeatlayout = new Select(byXPath(driver, propertyMap.get("seat_layout_id")));
			selectSeatlayout.selectByVisibleText(propertyMap.get("seat_layout"));
			byXPath(driver, propertyMap.get("preview_seat_layout")).click();
			General_Wait(2000);
			// hide the seat layout preview
			byXPath(driver, propertyMap.get("preview_seat_layout")).click();
			General_Wait(1000);
			// select vechile class
			byXPath(driver, propertyMap.get("vechile_class_field")).sendKeys(propertyMap.get("vechile_class"));
			// select start city for bus
			byXPath(driver, propertyMap.get("start_city_field")).sendKeys(propertyMap.get("source_city"));
			byXPath(driver, propertyMap.get("end_city_field")).sendKeys(propertyMap.get("departure_city"));
			// select timing for departure
			driver.findElement(By.xpath("//div[@id='departureTimeDiv']//span//i[@class='icon-time']")).click();
			selectTimings(driver, propertyMap.get("departureTime"), propertyMap.get("departureXpath"));
			System.out.println("departureTime is selected");
			// slect timing for Arrival
			driver.findElement(By.xpath("//div[@id='arrivalTimeDiv']//span//i[@class='icon-time']")).click();
			selectTimings(driver, propertyMap.get("ArrivalTime"), propertyMap.get("arrivalXpath"));
			System.out.println("Arrival time is selected");
			General_Wait(2000);
			driver.findElement(By.xpath("//input[@id='journeyHours']")).click();
			byXPath(driver, propertyMap.get("bottle_amenity")).click();
			byXPath(driver, propertyMap.get("light_amenity")).click();
			System.out.println("Amenities are selected");

			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,300)", "");
			// select start date
			byXPath(driver, propertyMap.get("start_date")).click();
			selectCalender(driver, propertyMap.get("startDate"));
			// select end date
			byXPath(driver, propertyMap.get("end_date")).click();
			selectCalender(driver, propertyMap.get("endDate"));
			// select availability of bus date
			byXPath(driver, propertyMap.get("check_for_all_days")).click();

			int noOfdaysInWeek = 7;
			String busFare;
			for (int i = 1; i <= noOfdaysInWeek; i++) {
				if (i == 1 || i == 7) {
					busFare = propertyMap.get("weekend_amount");
					System.out.println("for " + i + " price of bus is set to be:" + busFare);
				} else {
					busFare = propertyMap.get("weekday_amount");
					System.out.println("for " + i + " price of bus is set to be:" + busFare);
				}
				driver.findElement(By.xpath("//input[@id='cost" + i + "'" + "]")).sendKeys(busFare);
			}
			jse.executeScript("window.scrollBy(0,300)", "");
			// select boarding
			byXPath(driver, propertyMap.get("new_boarding_button")).click();
			General_Wait(1000);
			byXPath(driver, propertyMap.get("new_boarding_button")).click();
			General_Wait(1000);
			byXPath(driver, propertyMap.get("new_boarding_button")).click();

			// select ist boarding point
			selectBoardingPointName(driver, propertyMap.get("boardingNo1"), propertyMap.get("bp_name1"),
					propertyMap.get("bp_date1"), propertyMap.get("bp_address1"));
			// select 2nd boarding point
			selectBoardingPointName(driver, propertyMap.get("boardingNo2"), propertyMap.get("bp_name2"),
					propertyMap.get("bp_date2"), propertyMap.get("bp_address2"));
			// select 3nd boarding point
			selectBoardingPointName(driver, propertyMap.get("boardingNo3"), propertyMap.get("bp_name3"),
					propertyMap.get("bp_date3"), propertyMap.get("bp_address3"));
			// select 4th boarding point
			selectBoardingPointName(driver, propertyMap.get("boardingNo4"), propertyMap.get("bp_name4"),
					propertyMap.get("bp_date4"), propertyMap.get("bp_address4"));

			jse.executeScript("window.scrollBy(0,300)", "");
			// select drop off point
			byXPath(driver, propertyMap.get("drop_button")).click();
			General_Wait(1000);
			byXPath(driver, propertyMap.get("drop_button")).click();
			General_Wait(1000);
			// select ist dropping point
			selectDroppingPointName(driver, propertyMap.get("droppingNo1"), propertyMap.get("dp_name1"),
					propertyMap.get("dp_date1"), propertyMap.get("dp_address1"));
			// select 2nd dropping point
			selectDroppingPointName(driver, propertyMap.get("droppingNo2"), propertyMap.get("dp_name2"),
					propertyMap.get("dp_date2"), propertyMap.get("dp_address2"));
			// select 3rd dropping point
			selectDroppingPointName(driver, propertyMap.get("droppingNo3"), propertyMap.get("dp_name3"),
					propertyMap.get("dp_date3"), propertyMap.get("dp_address3"));

			// save bus
			byXPath(driver, propertyMap.get("save_bus")).click();
			System.out.println("bus is saved");
			General_Wait(20000);
			System.out.println("busServiceId:" + busServiceId);
			validateTest.validateWhetherBusIsCreatedOrNot(driver, busServiceId);
			// General_Wait(10000);
			driver.navigate().refresh();
			try {
				byXPath(driver, propertyMap.get("admin_icon")).click();
				byXPath(driver, propertyMap.get("admin_logout")).click();
				General_Wait(5000);

			} catch (UnhandledAlertException e) {
				Alert alert = driver.switchTo().alert();
				System.out.println("alertText:" + alert.getText());
				alert.accept();
			}
			// validateTest.validateLogout(driver);
			logoutAdmin(driver);

			// driver.get(propertyMap.get("url"));
			// validateTest.validateUserLogin(driver);
			// try {
			// bookTickets.bookOneWaySingleTicket(driver);
			// } catch (InterruptedException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// deleteBusService(driver,busServiceId);
			// } else {
			// System.out.println("This id already exists please try with
			// another Id");
			// return;
		}
	}

	@Test(priority = 2)
	public void bookTicket() throws InterruptedException {
		SBookTickets bookTc = new SBookTickets();
		//bookTc.bookOneWaySingleTicket(driver);
		// bookTc.bookOneWayMultiTicket(driver);
		// bookTc.bookRoundTripSingleSeatTicket(driver);
		bookTc.bookRoundTripMultiSeatTicket(driver);

	}

	@Test(priority = 3)
	public void cancelTicket() {
		boolean fullTicketCancellation = false;
		validateTest.validateCancelTicket(driver, fullTicketCancellation);
	}

	// please enter bus service ID to inactive any bus or call create bus
	// function
	// before it
	@Test(priority = 4)
	public void inActiveBusService() {
		busServiceId = "60";
		loginToAdminAccount();
		List<WebElement> serviceList = driver.findElements(By.xpath("//li[@id='Services']//a[text()='Services']"));
		// System.out.println("webelement size:"+serviceList.size());
		for (WebElement serviceEle : serviceList) {
			// System.out.println(serviceEle.getText());
			if (serviceEle.getText().equals("SERVICES")) {
				serviceEle.click();
				break;
			}
		}
		General_Wait(2000);
		Select select = new Select(byID(driver, propertyMap.get("admin_service_list")));
		select.selectByVisibleText("Service Master");
		General_Wait(3000);
		byXPath(driver, propertyMap.get("search_field")).sendKeys(propertyMap.get("search_bus"));
		List<WebElement> busListed = driver.findElements(By.xpath("//tbody[@id='service-table-data']//tr//td[1]"));
		System.out.println("total bus present related to this id:" + busListed.size());
		General_Wait(2000);
		for (WebElement busId : busListed) {
			System.out.println("busId text:" + busId.getText());
			General_Wait(2000);
			if (busId.getText().equals(busServiceId)) {
				System.out.println("//tbody[@id='service-table-data']//tr[@id=" + "'" + busServiceId + "'"
						+ "]//button[@class='btn btn-primary btn-sm']");
				driver.findElement(By.xpath("//tbody[@id='service-table-data']//tr[@id=" + "'" + busServiceId + "'"
						+ "]//button[@class='btn btn-primary btn-sm']")).click();
				General_Wait(2000);
				break;
			}
		}
		General_Wait(3000);
		byXPath(driver, propertyMap.get("inactive_field")).click();
		byXPath(driver, propertyMap.get("save_bus")).click();
		General_Wait(3000);
		validateTest.reteriveInActiveBusId(driver, busServiceId);
		driver.navigate().refresh();
		try {
			byXPath(driver, propertyMap.get("admin_icon")).click();
			byXPath(driver, propertyMap.get("admin_logout")).click();
			General_Wait(5000);

		} catch (UnhandledAlertException e) {
			Alert alert = driver.switchTo().alert();
			System.out.println("alertText:" + alert.getText());
			alert.accept();
		}
		// validateTest.validateLogout(driver);
		logoutAdmin(driver);
	}

	@Test(priority = 5)
	public void updateUserDetail() {
		validateTest.validateUpdatedUserDetail(driver);
	}

	public void selectDroppingPointName(WebDriver driver, String dropOffNo, String dropOffName, String dropOffDate,
			String dropOffAddress) {
		driver.findElement(By.xpath("//input[@id='dropoffPointName_" + dropOffNo + "'" + "]")).sendKeys(dropOffName);
		driver.findElement(By.xpath("//div[@id='dropoffPointTimeDiv_" + dropOffNo + "'" + "]//span//i")).click();
		String timingsXath = "";
		if (Integer.parseInt(dropOffNo) > 1) {
			timingsXath = "//div[@class='bootstrap-datetimepicker-widget dropdown-menu' and @style]["
					+ (Integer.parseInt(dropOffNo) + 6) + "]";
		} else {
			timingsXath = "//div[@class='bootstrap-datetimepicker-widget dropdown-menu' and @style]["
					+ (Integer.parseInt(dropOffNo) + 3) + "]";
		}

		// System.out.println("xpath:" + timingsXath);
		selectTimings(driver, dropOffDate, timingsXath);
		WebElement phone = driver
				.findElement(By.xpath("//input[@id='dropoffPointPhoneNumber_" + dropOffNo + "'" + "]"));
		phone.click();
		phone.sendKeys(propertyMap.get("dp_phoneNo"));
		driver.findElement(By.xpath("//textarea[@id='dropoffPointAddress_" + dropOffNo + "'" + "]"))
				.sendKeys(dropOffAddress);
	}

	public void selectBoardingPointName(WebDriver driver, String boardingNo, String baordingName, String boardingDate,
			String baordingAddress) {
		driver.findElement(By.xpath("//input[@id='boardingPointName_" + boardingNo + "'" + "]")).sendKeys(baordingName);
		driver.findElement(By.xpath("//div[@id='boardingPointTimeDiv_" + boardingNo + "'" + "]//span//i")).click();
		System.out
				.println("click ele:" + By.xpath("//div[@id='boardingPointTimeDiv_" + boardingNo + "'" + "]//span//i"));
		String timingsXath = "//div[@class='bootstrap-datetimepicker-widget dropdown-menu' and @style]["
				+ (Integer.parseInt(boardingNo) + 2) + "]";
		// System.out.println("xpath:" + timingsXath);
		selectTimings(driver, boardingDate, timingsXath);
		WebElement phone = driver
				.findElement(By.xpath("//input[@id='boardingPointPhoneNumber_" + boardingNo + "'" + "]"));
		phone.click();
		phone.sendKeys(propertyMap.get("bp_phoneNo"));
		driver.findElement(By.xpath("//textarea[@id='boardingPointAddress_" + boardingNo + "'" + "]"))
				.sendKeys(baordingAddress);
	}

	public void selectCalender(WebDriver driver, String date) {
		General_Wait(2000);
		String[] dateList = date.split("/");
		String dateDate = dateList[0];
		String dateMonth = dateList[1];
		// String dateyear = dateList[2];

		driver.findElement(By.xpath("//div[@class='datepicker-days']//thead//tr//th[@class='datepicker-switch']"))
				.click();
		// General_Wait(1000);
		List<WebElement> monthList = driver
				.findElements(By.xpath("//div[@class='datepicker-months']//span[@class='month']"));
		// System.out.println("monthList:"+monthList.size());
		for (WebElement month : monthList) {
			// System.out.println(month.getText());
			if (month.getText().equals(dateMonth)) {
				month.click();
				break;
			}
		}
		List<WebElement> dayList = driver
				.findElements(By.xpath("//div[@class='datepicker-days']//tr//td[@class='day']"));
		// System.out.println("dayList:"+dayList.size());
		for (WebElement day : dayList) {
			// System.out.println(day.getText());
			if (day.getText().equals(dateDate)) {
				day.click();
				break;
			}
		}

	}

	public void selectTimings(WebDriver driver, String time, String xpath) {
		driver.findElement(By.xpath(xpath));
		String[] timeList = time.split(":");
		String desiredHourTime = timeList[0];
		String expectedMinute = timeList[1];
		String expectedSeconds = timeList[2];

		int expectedHour = 0, expectedSec = 0;
		int expectedMin = 0;
		try {
			expectedHour = Integer.parseInt(desiredHourTime);
			expectedMin = Integer.parseInt(expectedMinute);
			expectedSec = Integer.parseInt(expectedSeconds);

		} catch (NumberFormatException e) {
			System.out.println(e.getMessage());
		}

		// print hour
		List<WebElement> printHourtimeList = driver.findElements(
				By.xpath(xpath + "//table[@class='table-condensed']//tbody//tr//td//span[@class='timepicker-hour']"));

		// System.out.println("hourtimeList:" + printHourtimeList.size());
		int actualHour = 0;
		for (WebElement hourEle : printHourtimeList) {
			String hourEleText = hourEle.getText();
			try {
				actualHour = Integer.parseInt(hourEleText);
			} catch (NumberFormatException e) {
				System.out.println(e.getMessage());
			}

			if (!(hourEleText.equals(""))) {
				int hourDiff = expectedHour - actualHour;
				// System.out.println("difference in timings:" + hourDiff);
				if (hourDiff > 0) {
					// System.out.println(" hours are incrementing");
					for (int i = 0; i < hourDiff; i++) {
						// List<WebElement>
						// hourEle=driver.findElements(By.xpath(""))
						driver.findElement(By.xpath(
								xpath + "//table[@class='table-condensed']//tbody//tr//td//a[@data-action='incrementHours']"))
								.click();
					}
					break;
				} else {
					// System.out.println(" hours are decrementing");
					// General_Wait(2000);
					for (int i = 0; i < Math.abs(hourDiff); i++) {
						driver.findElement(By.xpath(
								xpath + "//table[@class='table-condensed']//tbody//tr//td//a[@data-action='decrementHours']"))
								.click();
					}
					break;
				}
			}
		}

		// print minutes
		List<WebElement> printminuteList = driver.findElements(
				By.xpath(xpath + "//table[@class='table-condensed']//tbody//tr//td//span[@class='timepicker-minute']"));
		// System.out.println("printminuteList:" + printminuteList.size());
		int actualMinute = 0;
		for (WebElement minuteEle : printminuteList) {
			String minuteEleText = minuteEle.getText();
			try {
				actualMinute = Integer.parseInt(minuteEleText);
			} catch (NumberFormatException e) {
				System.out.println(e.getMessage());
			}

			if (!(minuteEleText.equals(""))) {
				int minDiff = expectedMin - actualMinute;
				// System.out.println("difference in timings of minutes:" +
				// minDiff);
				if (minDiff > 0) {
					// System.out.println(" Minutes are incrementing");
					for (int i = 0; i < minDiff; i++) {
						driver.findElement(By.xpath(
								xpath + "//table[@class='table-condensed']//tbody//tr//td//a[@data-action='incrementMinutes']"))
								.click();
					}
					break;
				} else {
					// System.out.println(" Minutes are decrementing");
					for (int i = 0; i < Math.abs(minDiff); i++) {
						driver.findElement(By.xpath(
								xpath + "//table[@class='table-condensed']//tbody//tr//td//a[@data-action='decrementMinutes']"))
								.click();
					}
					break;
				}
			}
		}

		// print second
		List<WebElement> printSecondList = driver.findElements(
				By.xpath(xpath + "//table[@class='table-condensed']//tbody//tr//td//span[@class='timepicker-second']"));
		// System.out.println("printSecondList:" + printSecondList.size());
		int actualSecond = 0;
		for (WebElement secondEle : printSecondList) {
			String secondEleText = secondEle.getText();
			try {
				actualSecond = Integer.parseInt(secondEleText);
			} catch (NumberFormatException e) {
				System.out.println(e.getMessage());
			}

			if (!(secondEleText.equals(""))) {
				int diff = expectedSec - actualSecond;
				// System.out.println("difference in timings of seconds:" +
				// diff);
				if (diff > 0) {
					// System.out.println(" Seconds are incrementing");
					for (int i = 0; i < diff; i++) {
						driver.findElement(By.xpath(
								xpath + "//table[@class='table-condensed']//tbody//tr//td//a[@data-action='incrementSeconds']"))
								.click();
					}
					break;
				} else {
					// System.out.println(" Seconds are decrementing");
					for (int i = 0; i < Math.abs(diff); i++) {
						driver.findElement(By.xpath(
								xpath + "//table[@class='table-condensed']//tbody//tr//td//a[@data-action='decrementSeconds']"))
								.click();
					}
					break;
				}
			}
		}
	}

	public void logoutAdmin(WebDriver driver) {
		// loginToAdminAccount();
		String expectedText = "Login / Signup";
		byXPath(driver, propertyMap.get("img_icon")).click();
		General_Wait(2000);
		byXPath(driver, propertyMap.get("logout")).click();
		String actualText = (byXPath(driver, propertyMap.get("Login"))).getText();
		Assert.assertEquals(expectedText, actualText);
		System.out.println(expectedText + "==" + actualText + " Logout is validated");
	}

	@AfterTest
	public void closeBrowser() {
		driver.close();
		driver.quit();
	}

}
