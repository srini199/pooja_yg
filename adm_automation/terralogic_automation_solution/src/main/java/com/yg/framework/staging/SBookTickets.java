package com.yg.framework.staging;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.web_automation.solution.Web_automation_common_utils;

//changes: price separate method, check on book to return btn 
public class SBookTickets extends Web_automation_common_utils {

	String leavingFrom, goingTo, journeyDate, returnDate;
	// String goingTo = propertyMap.get("departure_city");
	int busRow = Integer.parseInt(propertyMap.get("select_bus"));
	String oneWayBoardingPoint = propertyMap.get("one_way_boardingPoint");
	String oneWayDroppingPoint = propertyMap.get("one_way_droppingPoint");
	String returnDroppingPoint = propertyMap.get("return_way_droppingPoint");
	Boolean confirmation = Boolean.parseBoolean(propertyMap.get("confirm"));
	String personName1 = propertyMap.get("name1");
	String personName2 = propertyMap.get("name2");
	String personAge = propertyMap.get("age");
	String person_gender = propertyMap.get("gender");
	String uid = propertyMap.get("id_proof");
	String id_no = propertyMap.get("id_detail");
	String phoneNo = propertyMap.get("person_phone");
	String emailId = propertyMap.get("email");
	String userName = propertyMap.get("userName");
	ValidateTestCases validateTests = new ValidateTestCases();
	
	public void selectRoute(WebDriver driver, String leavingFrom, String goingTo, String journeyDate)
			throws InterruptedException {
		byID(driver, propertyMap.get("source")).sendKeys(leavingFrom);
		byXPath(driver, propertyMap.get("destination")).sendKeys(goingTo);
		General_Wait(2000);
		selectJourneyDate(driver, journeyDate);
		General_Wait(2000);
	}

	public void bookOneWaySingleTicket(WebDriver driver) throws InterruptedException {
		int noOFTicket = 1;
		loginIntoYGAccount(driver);
		String walletAmountBeforeBooking = validateTests.reteriveWalletAmount(userName);
		System.out.println("walletAmountBeforeBooking:" + walletAmountBeforeBooking);
		General_Wait(2000);
		leavingFrom = propertyMap.get("source_city");
		goingTo = propertyMap.get("destination_city");
		journeyDate = propertyMap.get("startingDate");
		selectRoute(driver, leavingFrom, goingTo, journeyDate);
		WebElement login = byXPath(driver, propertyMap.get("search_icon"));
		login.click();
		General_Wait(10000);
		List<String> idList = selectBusWhileGoing(driver);
		String oneWayBusId = idList.get(0);
		String oneWayPersonId = idList.get(1);
		System.out.println("one Way busid:" + oneWayBusId + " oneWay PersonId:" + oneWayPersonId);
		Thread.sleep(5000);
		selectSingleSeat(driver, oneWayBusId);
		selectDroppingPointOnArriving(driver);
		confirmYourOneWayTicket(driver, confirmation);
		General_Wait(3000);
		forOneWayFillNameAgeGender(driver, oneWayBusId, oneWayPersonId, noOFTicket);
		oneWayFillYourID(driver, oneWayBusId, oneWayPersonId, noOFTicket);
		captureScreenshot(driver, "booked_tikcet");
		System.out.println("ticket is booked and captured screenshot. please check your screenshot folder");
		driver.get("http://65.49.80.209/#/");
		General_Wait(3000);
		String walletAmountAfterBooking = validateTests.reteriveWalletAmount(userName);
		System.out.println("walletAmountAfterBooking:" + walletAmountAfterBooking);
		General_Wait(2000);
		validateTests.validateOneWayBookingTicket(driver);
		validateTests.validateLogout(driver);
		System.out.println("user is successfully logout");
		// try {
		// if(driver.findElement(By.xpath("//img[@class='no-bus-found']")).isDisplayed())
		// {
		// System.out.println("no bus found on this route please select another
		// route");
		// return;
		// } else {
		// General_Wait(4000);
		// System.out.println("selecting bus");
		// }
		//
		// }catch(NoSuchElementException e) {

	}

	public void bookRoundTripSingleSeatTicket(WebDriver driver) throws InterruptedException {
		loginIntoYGAccount(driver);
		String walletAmountBeforeBooking = validateTests.reteriveWalletAmount(userName);
		System.out.println("walletAmountBeforeBooking:" + walletAmountBeforeBooking);
		General_Wait(2000);
		leavingFrom = propertyMap.get("source_city");
		goingTo = propertyMap.get("destination_city");
		journeyDate = propertyMap.get("startingDate");
		selectRoute(driver, leavingFrom, goingTo, journeyDate);
		General_Wait(2000);
		returnDate = propertyMap.get("coming_back");
		selectReturnDate(driver, returnDate);
		General_Wait(2000);
		WebElement login = byXPath(driver, propertyMap.get("search_icon"));
		login.click();
		General_Wait(6000);
		List<String> leavingBusIdList = selectBusWhileGoing(driver);
		String leavingBusId = leavingBusIdList.get(0);
		String leavingPersonId = leavingBusIdList.get(1);
		System.out.println("leaving busid:" + leavingBusId + " leaving personId:" + leavingPersonId);
		Thread.sleep(5000);
		// "VIJAYAWADA NEW BUS STAND"
		selectSingleSeat(driver, leavingBusId);
		selectDroppingPointOnArriving(driver);
		confirmYourOneWayTicket(driver, confirmation);
		General_Wait(3000);
		forOneWayFillNameAgeGender(driver, leavingBusId, leavingPersonId, 1);
		confirmYourReturnTicket(driver, true);
		System.out.println("please select bus for your return journey also");
		List<String> returningBusIdList = selectBusOnReturn(driver);
		String returningBusId = returningBusIdList.get(0);
		String returningPersonId = returningBusIdList.get(1);
		System.out.println("leaving busid:" + returningBusId + " leaving personId:" + returningPersonId);
		Thread.sleep(5000);
		System.out.println("selecting seat for your return journey also");
		Thread.sleep(2000);
		// "HAL"
		selectSingleSeat(driver, returningBusId);
		selectDroppingPointOnReturn(driver);
		confirmYourOneWayTicket(driver, confirmation);
		Thread.sleep(2000);
		fillNameAgeGenderWhileReturning(driver, returningBusId, returningPersonId, 1);
		Thread.sleep(2000);
		fillYourIDWhileReturning(driver, returningBusId, returningPersonId, 1);
		General_Wait(3000);
		driver.get("http://65.49.80.209/#/");
		General_Wait(3000);
		String walletAmountAfterBooking = validateTests.reteriveWalletAmount(userName);
		System.out.println("walletAmountAfterBooking:" + walletAmountAfterBooking);
		General_Wait(2000);
		validateTests.validateOneWayBookingTicket(driver);
	}

	public void bookRoundTripMultiSeatTicket(WebDriver driver) throws InterruptedException {
		loginIntoYGAccount(driver);
		String walletAmountBeforeBooking = validateTests.reteriveWalletAmount(userName);
		System.out.println("walletAmountBeforeBooking:" + walletAmountBeforeBooking);
		General_Wait(2000);
		leavingFrom = propertyMap.get("source_city");
		goingTo = propertyMap.get("destination_city");
		journeyDate = propertyMap.get("startingDate");
		selectRoute(driver, leavingFrom, goingTo, journeyDate);
		General_Wait(2000);
		returnDate = propertyMap.get("coming_back");
		selectReturnDate(driver, returnDate);
		General_Wait(2000);
		WebElement login = byXPath(driver, propertyMap.get("search_icon"));
		login.click();
		General_Wait(6000);
		List<String> leavingBusIdList = selectBusWhileGoing(driver);
		String leavingBusId = leavingBusIdList.get(0);
		String leavingPersonId = leavingBusIdList.get(1);
		System.out.println("leaving busid:" + leavingBusId + " leaving personId:" + leavingPersonId);
		Thread.sleep(5000);
		// "VIJAYAWADA NEW BUS STAND"
		int noOfSeatBookedWhileGoing = selectMultiSeat(driver, leavingBusId);
		System.out.println("noOfSeatBookedWhileGoing:" + noOfSeatBookedWhileGoing);
		selectDroppingPointOnArriving(driver);
		confirmYourOneWayTicket(driver, confirmation);
		General_Wait(3000);
		forOneWayFillNameAgeGender(driver, leavingBusId, leavingPersonId, noOfSeatBookedWhileGoing);
		confirmYourReturnTicket(driver, true);
		System.out.println("please select bus for your return journey also");
		List<String> returningBusIdList = selectBusOnReturn(driver);
		String returningBusId = returningBusIdList.get(0);
		String returningPersonId = returningBusIdList.get(1);
		System.out.println("leaving busid:" + returningBusId + " leaving personId:" + returningPersonId);
		Thread.sleep(5000);
		System.out.println("selecting seat for your return journey also");
		Thread.sleep(2000);
		// "HAL"
		int noOfSeatBookedWhilereturning = selectMultiSeat(driver, returningBusId);
		System.out.println("noOfSeatBookedWhilereturning:" + noOfSeatBookedWhilereturning);
		selectDroppingPointOnReturn(driver);
		confirmYourOneWayTicket(driver, confirmation);
		Thread.sleep(2000);
		fillNameAgeGenderWhileReturning(driver, returningBusId, returningPersonId, noOfSeatBookedWhilereturning);
		Thread.sleep(2000);
		fillYourIDWhileReturning(driver, returningBusId, returningPersonId, 1);
		General_Wait(3000);
		String walletAmountAfterBooking = validateTests.reteriveWalletAmount(userName);
		System.out.println("walletAmountAfterBooking:" + walletAmountAfterBooking);
		General_Wait(2000);
		validateTests.validateOneWayBookingTicket(driver);
		General_Wait(2000);
		driver.get("http://65.49.80.209/#/");
		General_Wait(3000);
		validateTests.validateOneWayBookingTicket(driver);

	}

	public void confirmYourReturnTicket(WebDriver driver, boolean returnConfirmation) {
		if (confirmation) {
			List<WebElement> bookreturnBtn = driver
					.findElements(By.xpath("//button[@class='btn-primary book-return-btn']"));
			System.out.println("bookreturnBtn size:" + bookreturnBtn.size());
			for (WebElement ele : bookreturnBtn) {
				String text = ele.getText();
				if (text != null) {
					ele.click();
					General_Wait(2000);
					break;
				}
			}
		} else {

		}
	}

	public void bookOneWayMultiTicket(WebDriver driver) throws InterruptedException {
		loginIntoYGAccount(driver);
		String walletAmountBeforeBooking = validateTests.reteriveWalletAmount(userName);
		System.out.println("walletAmountBeforeBooking:" + walletAmountBeforeBooking);
		General_Wait(2000);
		leavingFrom = propertyMap.get("source_city");
		goingTo = propertyMap.get("destination_city");
		journeyDate = propertyMap.get("startingDate");
		selectRoute(driver, leavingFrom, goingTo, journeyDate);
		WebElement login = byXPath(driver, propertyMap.get("search_icon"));
		login.click();
		General_Wait(6000);
		List<String> idList = selectBusWhileGoing(driver);
		String oneWayMultiTicketBusId = idList.get(0);
		String oneWayMultiTicketPersonId = idList.get(1);
		System.out.println("returnig busid:" + oneWayMultiTicketBusId + " personId:" + oneWayMultiTicketPersonId);
		Thread.sleep(5000);
		int noOfSeatBooked = selectMultiSeat(driver, oneWayMultiTicketBusId);
		selectDroppingPointOnArriving(driver);
		confirmYourOneWayTicket(driver, confirmation);
		General_Wait(3000);
		forOneWayFillNameAgeGender(driver, oneWayMultiTicketBusId, oneWayMultiTicketPersonId, noOfSeatBooked);
		oneWayFillYourID(driver, oneWayMultiTicketBusId, oneWayMultiTicketPersonId, noOfSeatBooked);
		captureScreenshot(driver, "booked_tikcet");
		System.out.println("ticket is booked and captured screenshot. please check your screenshot folder");
		General_Wait(3000);
		String walletAmountAfterBooking = validateTests.reteriveWalletAmount(userName);
		System.out.println("walletAmountAfterBooking:" + walletAmountAfterBooking);
		General_Wait(2000);
		validateTests.validateOneWayBookingTicket(driver);
		General_Wait(2000);
		driver.get("http://65.49.80.209/#/");
		validateTests.validateLogout(driver);
		System.out.println("user is successfully logout");
	}

	public void selectSingleSeat(WebDriver driver, String busId) throws InterruptedException {
		Actions action = new Actions(driver);
		List<WebElement> seatList = driver
				.findElements(By.xpath("//*[@id=" + "'" + busId + "']//table//tbody//tr//td"));

		// System.out.println("seat size " + seatList.size());
		int count = 0;
		for (int i = 1; i <= seatList.size(); i++) {
			// WebElement seatEle = seatList.get(i);
			action.moveToElement(seatList.get(i)).build().perform();
			seatList.get(i).click();
			String price = checkPrice(driver);
			System.out.println("price for seat " + i + ":" + price);
			if (price != null) {
				seatList.get(i).click();
				count++;
			} else {
				System.out.println("This seat is already booked");
			}
			if (count == 1) {
				seatList.get(i).click();
				break;
			}
			General_Wait(1000);
		}
		selectBoardingPoint(driver);

	}

	public int selectMultiSeat(WebDriver driver, String busId) throws InterruptedException {
		Actions action = new Actions(driver);
		List<WebElement> seatList = driver
				.findElements(By.xpath("//*[@id=" + "'" + busId + "']//table//tbody//tr//td"));
		System.out.println("seat size " + seatList.size());
		int count = 0;
		String previousSeatPrice = null;
		for (int i = 1; i <= seatList.size(); i++) {
			action.moveToElement(seatList.get(i)).build().perform();
			seatList.get(i).click();
			String price = checkPrice(driver);
			System.out.println("price for seat " + i + ":" + price);
			if (price != null && (!price.equals(previousSeatPrice))) {
				count++;
			} else {
				System.out.println("This seat is already booked");
			}
			if (count == 2) {
				break;
			}
			General_Wait(1000);
			previousSeatPrice = price;
		}
		// "VIJAYAWADA NEW BUS STANDs"
		selectBoardingPoint(driver);
		return count;
	}

	public void selectBoardingPoint(WebDriver driver) {
		General_Wait(2000);
		// select boarding point
		List<WebElement> boardingoptions = driver
				.findElements(By.xpath("//span[@class='boarding-drop-times' and text()='Select Boarding Point']"));
		for (int i = 0; i < boardingoptions.size(); i++) {
			String text = boardingoptions.get(i).getText();
			if (text.equals("Select Boarding Point")) {
				boardingoptions.get(i).click();
				List<WebElement> boardingoption = driver.findElements(By
						.xpath("//ul[@class='dropdown-menu boarding_ul_List dropdownScroll']//li[" + busRow + "]//a"));
				for (int j = 0; j < boardingoption.size(); j++) {
					String boardingtext = boardingoption.get(j).getText();
					if (!(boardingtext.equals(""))) {
						boardingoption.get(j).click();
						System.out.println("boarding point is selected");
					}
				}
			}
		}
		General_Wait(2000);

	}

	public void selectDroppingPointOnArriving(WebDriver driver) {
		// select dropping point
		List<WebElement> droppingPointoptions = driver
				.findElements(By.xpath("//button[@class='btn dropping-point-picker']"));
		for (int i = 0; i < droppingPointoptions.size(); i++) {
			String droppingText = droppingPointoptions.get(i).getText();
			if (droppingText.equals("Select Dropping Point")) {
				droppingPointoptions.get(i).click();
				List<WebElement> droppingoption = driver
						.findElements(By.xpath("//ul[@class='dropdown-menu droping_ul_List dropdownScrolldrop']"));
				System.out.println("droppingoption size:" + droppingoption.size());
				for (int j = 0; j < droppingoption.size(); j++) {
					String droppingTextEle = droppingoption.get(j).getText();
					// System.out.println("dropping point
					// text:"+droppingTextEle);
					if ((droppingTextEle.contains(oneWayDroppingPoint))) {
						droppingoption.get(j).click();
						System.out.println("dropping point is selected");
					}
				}
			}
		}
	}

	public void selectDroppingPointOnReturn(WebDriver driver) {
		// select dropping point
		List<WebElement> droppingPointoptions = driver
				.findElements(By.xpath("//button[@class='btn dropping-point-picker']"));
		for (int i = 0; i < droppingPointoptions.size(); i++) {
			String droppingText = droppingPointoptions.get(i).getText();
			if (droppingText.equals("Select Dropping Point")) {
				droppingPointoptions.get(i).click();
				List<WebElement> droppingoption = driver
						.findElements(By.xpath("//ul[@class='dropdown-menu droping_ul_List dropdownScrolldrop']"));
				System.out.println("droppingoption size:" + droppingoption.size());
				for (int j = 0; j < droppingoption.size(); j++) {
					String droppingTextEle = droppingoption.get(j).getText();
					// System.out.println("dropping point
					// text:"+droppingTextEle);
					if ((droppingTextEle.contains(returnDroppingPoint))) {
						droppingoption.get(j).click();
						System.out.println("dropping point is selected");
					}
				}
			}
		}
	}

	public void selectJourneyDate(WebDriver driver, String journeyDate2) throws InterruptedException {
		byID(driver, propertyMap.get("startsAt")).click();
		// General_Wait(2000);
		String[] expectedDateArray = journeyDate.split("/");
		// select year
		byXPath(driver, propertyMap.get("start_calender")).click();
		// year select
		WebElement yearEle = byXPath(driver, propertyMap.get("start_year"));
		checkDate(driver, yearEle, expectedDateArray[2]);
		// select month
		WebElement monthpop = byXPath(driver, propertyMap.get("start_month"));
		checkDate(driver, monthpop, expectedDateArray[1]);
		// select date
		WebElement datepopup = byXPath(driver, propertyMap.get("start_trip_date"));
		checkDate(driver, datepopup, expectedDateArray[0]);
	}

	public void confirmYourOneWayTicket(WebDriver driver, Boolean confirmation) {
		if (confirmation) {
			List<WebElement> continueBooking = driver
					.findElements(By.xpath("//button[@class='btn-primary seats-continue-btn']"));
			for (WebElement element : continueBooking) {
				if (element.getText().equals("Continue")) {
					element.click();
					System.out.println("you pressed continue so please fill your details");
					break;
				}
			}
		} else {
			List<WebElement> cancelledBooking = driver
					.findElements(By.xpath("//button[@class='btn-primary seats-cancel-btn']"));
			for (WebElement element : cancelledBooking) {
				if (element.getText().equals("Cancel")) {
					element.click();
					System.out.println("You have cancelled the booking");
					break;
				}
			}
		}
	}

	public void selectReturnDate(WebDriver driver, String returnDate2) throws InterruptedException {

		byID(driver, propertyMap.get("returnDate")).click();
		String[] rjDateArray = returnDate.split("/");
		// select year
		byXPath(driver, propertyMap.get("start_calender")).click();
		WebElement returnYearEle = byXPath(driver, propertyMap.get("return_year"));
		checkDate(driver, returnYearEle, rjDateArray[2]);
		// select return month
		WebElement returnMonthEle = byXPath(driver, propertyMap.get("return_month"));
		checkDate(driver, returnMonthEle, rjDateArray[1]);
		// select date
		General_Wait(2000);
		WebElement returnDateEle = byXPath(driver, propertyMap.get("return_date"));
		checkDate(driver, returnDateEle, rjDateArray[0]);
		System.out.println("return journey:" + returnDate + " is selected");
	}

	public static void checkDate(WebDriver driver, WebElement element, String expectedDateArray) {
		List<WebElement> yearList = new WebDriverWait(driver, 10)
				.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(element, By.tagName("td")));
		// print calender
		// for (WebElement year : yearList) {
		// System.out.println(year.getText());
		// }
		for (WebElement particularDateEle : yearList) {
			String selectmonth = particularDateEle.getText();
			if (selectmonth.equals(expectedDateArray)) {
				particularDateEle.click();
				// System.out.println(expectedDateArray + " is clicked");
				break;
			}
		}
	}

	public List<String> selectBusOnReturn(WebDriver driver) throws InterruptedException {
		// checkFilterFunction(driver);
		General_Wait(3000);
		WebElement totalEle = driver.findElement(By.xpath("//table[@id='returnBusTable']"));
		// WebElement totalEle = byXPath(driver, propertyMap.get("bus_select"));
		List<WebElement> totalEleList = new WebDriverWait(driver, 5)
				.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(totalEle, By.tagName("div")));
		String busUniqueId = null;
		String personId = null;
		LinkedList<String> idList = new LinkedList<String>();
		LinkedList<String> personIdList = new LinkedList<String>();
		List<String> bothIdList = new ArrayList<>();
		System.out.println("total size on returning " + totalEleList.size());
		for (int i = 1; i < totalEleList.size(); i++) {
			String id = totalEleList.get(i).getAttribute("id");
			int len = id.length();
			// System.out.println("id for" + i + ":" + id);
			if (!(id.equals(""))) {
				if (len == 10) {
					// System.out.println("id length is 10");
					busUniqueId = id.substring(0, 3);
					personId = id.substring(3, id.length());
					// System.out.println("id:" + busUniqueId + " perpersonId:"
					// + personId);
					idList.add(busUniqueId);
					personIdList.add(personId);
				} else if (len > 10) {
					// System.out.println("id length is 11");
					busUniqueId = id.substring(0, 4);
					personId = id.substring(4, id.length());
					// System.out.println("id:" + busUniqueId + " perpersonId:"
					// + personId);
					idList.add(busUniqueId);
					personIdList.add(personId);
				} else if (len == 8) {
					// System.out.println("id length is less than 10");
					busUniqueId = id.substring(0, 1);
					personId = id.substring(1, id.length());
					// System.out.println("id:" + busUniqueId + " perpersonId:"
					// + personId);
					idList.add(busUniqueId);
					personIdList.add(personId);
				} else if (len == 9) {
					// System.out.println("id length is less than 10");
					busUniqueId = id.substring(0, 2);
					personId = id.substring(2, id.length());
					// System.out.println("id:" + busUniqueId + " perpersonId:"
					// + personId);
					idList.add(busUniqueId);
					personIdList.add(personId);
				}
			}
		}
		int totalNoOfBuses = idList.size();
		System.out.println("While returing total buses present on this route:" + totalNoOfBuses);
		// validateTests.valdiateTotalNoOfBuses(driver,totalNoOfBuses);
		busUniqueId = idList.get(busRow - 1);
		personId = personIdList.get(busRow - 1);
		bothIdList.add(0, busUniqueId);
		bothIdList.add(1, personId);
		// System.out.println("id for slected bus:" + busRow + " is:" +
		// busUniqueId + "
		// personId:" + personId);
		WebElement busSelection = driver.findElement(By.xpath(
				"//button[@class='btn btn-primary seats-btn' and @title='Bus ServiceId : " + busUniqueId + "']"));
		if (busSelection.getText().equals("View Seats")) {
			System.out.println("seat is avilable on this route");
			busSelection.click();
			System.out.println("Bus is selected from " + leavingFrom + " for " + goingTo + " on " + journeyDate);
		} else {
			System.out.println("all buses are sold");
			return null;
		}
		return bothIdList;
	}

	public List<String> selectBusWhileGoing(WebDriver driver) throws InterruptedException {
		checkFilterFunction(driver);
		General_Wait(3000);
		WebElement totalEle = byXPath(driver, propertyMap.get("bus_select"));
		List<WebElement> totalEleList = new WebDriverWait(driver, 5)
				.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(totalEle, By.tagName("div")));
		String busUniqueId = null;
		String personId = null;
		String mainId = null;
		LinkedList<String> idList = new LinkedList<String>();
		LinkedList<String> personIdList = new LinkedList<String>();
		LinkedList<String> mainIdList = new LinkedList<String>();
		List<String> bothIdList = new ArrayList<>();
		// System.out.println("total size " + totalEleList.size());
		for (int i = 1; i < totalEleList.size(); i++) {
			String id = totalEleList.get(i).getAttribute("id");
			int len = id.length();
			// System.out.println("id for" + i + ":" + id);
			if (!(id.equals(""))) {
				if (len == 10) {
					// System.out.println("id length is 10");
					busUniqueId = id.substring(0, 3);
					personId = id.substring(3, id.length());
					// System.out.println("id:" + busUniqueId + " perpersonId:"
					// + personId);
					idList.add(busUniqueId);
					personIdList.add(personId);
					mainIdList.add(id);
				} else if (len > 10) {
					// System.out.println("id length is 11");
					busUniqueId = id.substring(0, 4);
					personId = id.substring(4, id.length());
					// System.out.println("id:" + busUniqueId + " perpersonId:"
					// + personId);
					idList.add(busUniqueId);
					personIdList.add(personId);
					mainIdList.add(id);
				} else if (len < 10 && len > 7) {
					// System.out.println("id length is less than 10");
					busUniqueId = id.substring(0, 2);
					personId = id.substring(2, id.length());
					// System.out.println("id:" + busUniqueId + " perpersonId:"
					// + personId);
					idList.add(busUniqueId);
					personIdList.add(personId);
					mainIdList.add(id);
				}
			}
		}
		int totalNoOfBuses = idList.size();
		System.out.println("total buses on this route is " + totalNoOfBuses);
		// validate no of buses on this route
		validateTests.valdiateTotalNoOfBuses(propertyMap.get("source_city"), propertyMap.get("destination_city"),
				idList);
		busUniqueId = idList.get(busRow - 1);
		personId = personIdList.get(busRow - 1);
		mainId = mainIdList.get(busRow - 1);
		WebElement selectedBusPriceEle = driver
				.findElement(By.xpath("//div[@id=" + "'" + mainId + "'" + "]//td[@class='wd-20 revisedprice']//p"));
		String priceOfSelectedBus = selectedBusPriceEle.getText();
		System.out.println("busFare:" + priceOfSelectedBus);
		// validate price of selected buses on this route
		System.out.println("date:" + propertyMap.get("startingDate"));
		validateTests.validateBusPrice(propertyMap.get("source_city"), propertyMap.get("destination_city"),
				priceOfSelectedBus, propertyMap.get("startingDate"), personId);
		bothIdList.add(0, busUniqueId);
		bothIdList.add(1, personId);
		// System.out.println("id for slected bus:" + busRow + " is:" +
		// busUniqueId + "
		// personId:" + personId);
		WebElement busSelection = driver.findElement(By.xpath(
				"//button[@class='btn btn-primary seats-btn' and @title='Bus ServiceId : " + busUniqueId + "']"));
		if (busSelection.getText().equals("View Seats")) {
			System.out.println("seat is avilable on this route");
			busSelection.click();
			System.out.println("Bus is selected from " + leavingFrom + " for " + goingTo + " on " + journeyDate);
		} else {
			System.out.println("all buses are sold");
			return null;
		}
		return bothIdList;
	}

	public void forOneWayFillNameAgeGender(WebDriver driver, String oneWayBusId, String oneWayPersonId,
			int noOfPerson) {
		System.out.println("noOfPerson for this booking:" + noOfPerson);
		for (int i = 1; i <= noOfPerson; i++) {
			if (i == 1) {
				WebElement person = driver.findElement(By.xpath("//input[@id='name" + oneWayBusId + "']"));
				person.clear();
				person.sendKeys(String.valueOf(personName1));
//				person.sendKeys(Keys.chord(Keys.CONTROL, "a"), String.valueOf(personName1));
				driver.findElement(By.id("age" + oneWayBusId)).sendKeys(personAge);
				// selecting gender
				WebElement genderElement = driver.findElement(
						By.xpath("//button[@class='btn gender-dropdown' and @id='gender" + oneWayBusId + "']"));
				genderElement.click();
				selectGender(driver, oneWayBusId, oneWayPersonId, i);
			} else {
				WebElement person = driver.findElement(By.xpath("//input[@id='name" + i + oneWayBusId + "']"));
				person.clear();
				person.sendKeys(String.valueOf(personName1));
//				person.sendKeys(Keys.chord(Keys.CONTROL, "a"), String.valueOf(personName2));
				driver.findElement(By.id("age" + i + oneWayBusId)).sendKeys(personAge);
				// selecting gender
				WebElement genderElement = driver.findElement(
						By.xpath("//button[@class='btn gender-dropdown' and @id='gender" + i + oneWayBusId + "']"));
				genderElement.click();
				selectGender(driver, oneWayBusId, oneWayPersonId, noOfPerson);
			}
		}
	}

	public void fillNameAgeGenderWhileReturning(WebDriver driver, String busId, String personId, int noOfPerson) {
		System.out.println("filling name and age details on return for personId:" + personId);
		for (int i = 1; i <= noOfPerson; i++) {
			if (i == 1) {
				WebElement person = driver.findElement(By.xpath("//input[@id='rname" + busId + "']"));
				person.clear();
				person.sendKeys(String.valueOf(personName1));
				// person.sendKeys(Keys.chord(Keys.CONTROL, "a"),
				// String.valueOf(personName1));
				driver.findElement(By.id("rage" + busId)).sendKeys(personAge);
				// selecting gender
				General_Wait(4000);
				WebElement genderElement = driver
						.findElement(By.xpath("//button[@class='btn gender-dropdown' and @id='rgender" + busId + "']"));
				genderElement.click();
				List<WebElement> genderDropDown = driver
						.findElements(By.xpath("//ul[@class='dropdown-menu genderUl']/li/a"));
				for (WebElement gender : genderDropDown) {
					if (!gender.getText().equals("")) {
						if (gender.getText().equals(person_gender)) {
							System.out.println("Male is selected");
							driver.findElement(By.xpath("//*[@id=\"" + personId + "\""
									+ "]/div/div[1]/div/div[2]/div[3]/div[2]/div/ul/li[1]/a")).click();
						} else {
							// *[@id="1139114"]/div/div[1]/div/div[2]/div[3]/div[2]/div/ul/li[2]/a
							System.out.println("Female is selected");
							System.out.println("female xpath:" + "//*[@id=\"" + personId + "\""
									+ "]/div/div[1]/div/div[2]/div[3]/div[2]/div/ul/li[2]/a");
							driver.findElement(By.xpath("//*[@id=\"" + personId + "\""
									+ "]/div/div[1]/div/div[2]/div[3]/div[2]/div/ul/li[2]/a")).click();
						}
					}
				}
			} else {
				WebElement person = driver.findElement(By.xpath("//input[@id='rname" + i + busId + "']"));
				person.clear();
				person.sendKeys(String.valueOf(personName1));
				// person.sendKeys(Keys.chord(Keys.CONTROL, "a"),
				// String.valueOf(personName1));
				driver.findElement(By.id("rage" + i + busId)).sendKeys(personAge);
				// selecting gender
				WebElement genderElement = driver.findElement(
						By.xpath("//button[@class='btn gender-dropdown' and @id='rgender" + i + busId + "']"));
				genderElement.click();
				List<WebElement> genderDropDown = driver
						.findElements(By.xpath("//ul[@class='dropdown-menu genderUl']/li/a"));
				for (WebElement gender : genderDropDown) {
					if (!gender.getText().equals("")) {
						if (gender.getText().equals(person_gender)) {
							System.out.println("Male is selected");
							driver.findElement(By.xpath("//*[@id=\"" + personId + "\"" + "]/div/div[1]/div/div["
									+ (i + 1) + "]/div[3]/div[2]/div/ul/li[1]/a")).click();
						} else {
							System.out.println("Female is selected");
							driver.findElement(By.xpath("//*[@id=\"" + personId + "\"" + "]/div/div[1]/div/div["
									+ (i + 1) + "]/div[3]/div[2]/div/ul/li[2]/a")).click();
						}
					}
				}
			}
		}
	}

	public void oneWayFillYourID(WebDriver driver, String busId, String personId, int noOfPerson)
			throws InterruptedException {

		// filling phone no
		WebElement phone = driver.findElement(By.xpath("//input[@id='passphn" + busId + "']"));
		if (phone.getText() != "") {
			phone.clear();
			phone.sendKeys(String.valueOf(phoneNo));
		} else {
			System.out.println("your no is already present ");
		}
		// filling email
		WebElement email = driver.findElement(By.xpath("//input[@id='passemail" + busId + "']"));
		General_Wait(2000);
		if (email.getText() != "") {
			email.clear();
			email.sendKeys(String.valueOf(emailId));
		} else {
			System.out.println("email is already present ");
		}

		// selecting identity
		driver.findElement(By.xpath(propertyMap.get("identity_proof") + busId + "']")).click();
		String userXpath = "//div[@id=" + "'" + personId + "'" + "]";
		List<WebElement> ele = driver.findElements(By.xpath(userXpath + "//div//ul[@class='dropdown-menu IDUl']"));
		for (WebElement element : ele) {
			String uidText = element.getText();
			// System.out.println("uid text:"+ element.getText());
			if (uidText != null && uidText.contains(uid)) {
				driver.findElement(By.xpath(userXpath + "//li[@class='dropdownfilter']//a[text()='" + uid + "']"))
						.click();
			}
		}
		// filling selected id detail
		driver.findElement(By.xpath("//input[@id='passcard" + busId + "']")).sendKeys(String.valueOf(id_no));
		System.out.println("UID is passed");

		// clicking on proceed btn
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,100)", "");
		// System.out.println("payment xpath " + propertyMap.get("pay_btn"));
		Thread.sleep(10000);
		byXPath(driver, userXpath + propertyMap.get("pay_btn")).click();
		System.out.println("please choose mode of payment");
		General_Wait(2000);
		byXPath(driver, propertyMap.get("get_ticket")).click();
		General_Wait(2000);
	}

	public void fillYourIDWhileReturning(WebDriver driver, String busId, String personId, int noOfPerson)
			throws InterruptedException {

		// filling phone no
		WebElement phone = driver.findElement(By.xpath("//input[@id='rphn" + busId + "']"));
		if (phone.getText() != "") {
			phone.clear();
			phone.sendKeys(String.valueOf(phoneNo));
//			phone.sendKeys(Keys.chord(Keys.CONTROL, "a"), String.valueOf(phoneNo));
		} else {
			System.out.println("your no is already present ");
		}
		// filling email
		WebElement email = driver.findElement(By.xpath("//input[@id='remail" + busId + "']"));
		email.clear();
		email.sendKeys(String.valueOf(emailId));
//		email.sendKeys(Keys.chord(Keys.CONTROL, "a"), String.valueOf(emailId));
		// selecting identity
		driver.findElement(By.xpath(propertyMap.get("identity_proof_return") + busId + "']")).click();
		List<WebElement> ele = driver
				.findElements(By.xpath("//ul[@class='dropdown-menu IDUl']//li[@class='dropdownfilter']//a"));
		for (WebElement element : ele) {
			if (element.getText().contains(uid)) {
				driver.findElement(By.xpath(
						"//ul[@class='dropdown-menu IDUl']//li[@class='dropdownfilter']//a[text()='" + uid + "']"))
						.click();
			}
		}
		// filling selected id detail
		driver.findElement(By.xpath("//input[@id='rcard" + busId + "']")).sendKeys(String.valueOf(id_no));
		System.out.println("UID is passed");

		// clicking on proceed btn
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,100)", "");
		System.out.println("payment xpath " + propertyMap.get("pay_btn"));
		Thread.sleep(10000);
		byXPath(driver, propertyMap.get("pay_btn")).click();
		System.out.println("please choose mode of payment");
		General_Wait(2000);
		byXPath(driver, propertyMap.get("get_ticket")).click();
		General_Wait(2000);
		captureScreenshot(driver, "booked_tikcet");
	}

	public static void checkFilterFunction(WebDriver driver) throws InterruptedException {
		driver.findElement(By.xpath("//*[@id=\"onwardFilter\"]/div/div[2]")).click();
		WebElement dropDownOptions = driver.findElement(By.xpath("//*[@id=\"onwardFilter\"]/div/div[2]/ul"));
		List<WebElement> sortByList = dropDownOptions.findElements(By.tagName("li"));
		Thread.sleep(2000);
		for (WebElement option : sortByList) {
			if (option.getText().equals("Fare")) {
				option.click();
				break;
			}
		}
		System.out.println(" Buses are sorted according to fare ");

	}

	public String checkPrice(WebDriver driver) {
		String busFare = null;
		List<WebElement> priceList = driver.findElements(By.xpath("//div[@class='seat-fare']"));
		for (int i = 0; i < priceList.size(); i++) {
			String pricetext = priceList.get(i).getText();
			if (pricetext.contains("Base Fare"))
				busFare = pricetext;
		}
		System.out.println("wallet amount in method:" + busFare);
		return busFare;
	}

	public void loginIntoYGAccount(WebDriver driver) {
		(byXPath(driver, propertyMap.get("Login"))).click();
		General_Wait(2000);
		byID(driver, propertyMap.get("email_field")).sendKeys(userName);
		byID(driver, propertyMap.get("password_field")).sendKeys(propertyMap.get("pwd"));
		byID(driver, propertyMap.get("click")).click();
		General_Wait(20000);
	}

	public void selectGender(WebDriver driver, String busId, String personId, int noOfPerson) {
		List<WebElement> genderDropDown = driver.findElements(By.xpath("//ul[@class='dropdown-menu genderUl']/li/a"));
		for (WebElement gender : genderDropDown) {
			if (!gender.getText().equals("")) {
				if (gender.getText().equals(person_gender)) {
					System.out.println("Male is selected");
					driver.findElement(By.xpath("//*[@id=\"" + personId + "\"" + "]/div/div[1]/div/div["
							+ (noOfPerson + 1) + "]/div[3]/div[2]/div/ul/li[1]/a")).click();
				} else {
					System.out.println("Female is selected");
					driver.findElement(By.xpath("//*[@id=\"" + personId + "\"" + "]/div/div[1]/div/div["
							+ (noOfPerson + 1) + "]/div[3]/div[2]/div/ul/li[2]/a")).click();
				}
			}
		}
	}

	// public void checkPriceForEachday() throws InterruptedException {
	// loginIntoYGAccount(driver);
	// leavingFrom = propertyMap.get("source_city");
	// goingTo = propertyMap.get("destination_city");
	//
	// SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
	// Calendar calender = Calendar.getInstance();
	// String todayDate = dateFormat.format(calender.getTime());
	// System.out.println("today's date:" + todayDate);
	// selectRoute(driver,leavingFrom,goingTo,todayDate);
	// selectJourneyDate(driver,todayDate);
	// System.out.println("week:" + Calendar.DAY_OF_WEEK);
	// System.out.println("day:"+ calender.get(Calendar.DAY_OF_WEEK));
	// for (int i = 1; i <= calender.DAY_OF_WEEK; i++) {
	// calender.add(Calendar.DAY_OF_MONTH, 1);
	// String nextDate = dateFormat.format(calender.getTime());
	// selectJourneyDate(driver,nextDate);
	// System.out.println("nextDate:" + nextDate);
	// }
	//
	// General_Wait(2000);
	// }

}
