package com.web_automation.solution;

import com.terralogic.framework.Common_resuable_methods;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Web_automation_common_utils extends Common_resuable_methods {

	/* reusable method Version number :1 
	 * Date: December 11th 
	 * Purpose : Reusable
	 * method to take scrrenshot 
	 * Parameters : webdriver, fileName(String) 
	 * Owner :Pooja Aggarwal
	 */
	public void captureScreenshot(WebDriver driver, String fileName) {
		TakesScreenshot screenshot = (TakesScreenshot) driver;
		File src = screenshot.getScreenshotAs(OutputType.FILE);
		String filepath = System.getProperty("user.dir") + "\\ScreenShot\\" + fileName + ".jpg";
		try {
			FileUtils.copyFile(src, new File(filepath));
			System.out.println("screenshot taken");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/* reusable method Version number :1 
	 * Date: December 11th 
	 * Purpose : Reusable method to double click an element 
	 * Parameters : webdriver, WebElement
	 *  Owner :Pooja Aggarwal
	 */
	public void actionDoubleclick(WebElement element, WebDriver driver) {
		Actions action = new Actions(driver);
		action.doubleClick(element).build().perform();
	}

	/*reusable method Version number :1 
	 * Date: December 11th 
	 * Purpose : Reusable method to move an 
	 * Parameters : webdriver, WebElement
	 *  Owner :Pooja Aggarwal
	 */
	public void actionMoveToElement(WebElement element, WebDriver driver) {
		Actions action = new Actions(driver);
		action.moveToElement(element).build().perform();
	}

	/*reusable method Version number :1 
	 * Date: December 11th 
	 * Purpose : Reusable method to click and hold an element 
	 * Parameters : webdriver, WebElement
	 *  Owner :Pooja Aggarwal
	 */
	public void actionClickAndHold(WebElement element, WebDriver driver) {
		Actions action = new Actions(driver);
		action.clickAndHold(element).build().perform();
	}

	/* reusable method Version number :1 
	 * Date: December 11th 
	 * Purpose : Reusable method to will return browser according to user requirement. if user want to
	 * open chrome browser this method will open chrome browser. 
	 * Parameters :browser name 
	 * Owner : Pooja Aggarwal
	 */
	public WebDriver startBrowser(String browserName) {
		WebDriver driver = null;
		if (browserName.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\browser\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browserName.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver",
					System.getProperty("user.dir")+"\\browser\\geckodriver.exe");
			System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
			driver = new FirefoxDriver();
		} else if (browserName.equalsIgnoreCase("IE")) {
			System.setProperty("webdriver.ie.driver",
					System.getProperty("user.dir")+"\\browser\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		} 
		return driver;
	}

	/* reusable method Version number :1 
	 * Date: December 11th 
	 * Purpose : Reusable method to will open headless browser 
	 * Parameters : No 
	 * Owner : Pooja Aggarwal
	 */
	public WebDriver headlessBrowser() {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
				System.getProperty("user.dir") + "\\phantomjs.exe");
		WebDriver driver = new PhantomJSDriver(capabilities);
		return driver;
	}

	/* reusable method Version number :1 
	 * Date: December 11th 
	 * Purpose : Reusable method to will open multi tabs in particular browser 
	 * Parameters : WebDriver,firstUrl, tabUrl 
	 * Owner : Pooja Aggarwal
	 */
	public void openMultitabs(WebDriver driver, String tabUrl) {
		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		driver.get(tabUrl);
	}

	/* reusable method Version number :1 
	 * Date: December 11th 
	 * Purpose : Reusable method to click on particular element from drop down list 
	 * Parameters :WebDriver, url, ParticularWordFromDropDown, elementclassName,
	 * listClassName(string) 
	 * comments: elementclassName--> drop down element on Ui, ParticularWordFromDropDown--> element user want to select from drop down
	 * 			listClassName--> when element clicked list of all drop down will come		
	 * Owner : Pooja Aggarwal
	 */
	public void dropDown(WebDriver driver, String ParticularWordFromDropDown, String elementclassName,
			String listClassName) {
		driver.findElement((By) byClass(driver, elementclassName));
		List<WebElement> menu = driver.findElements((By) byClass(driver, listClassName));
		System.out.println("size " + menu.size());
		for (int i = 0; i < menu.size(); i++) {
			WebElement ele = menu.get(i);
			String selectedopt = ele.getText();
			System.out.println("options are " + selectedopt);
			if (ele.getText().contains(ParticularWordFromDropDown)) {
				ele.click();
				break;
			}
		}
	}

	/* reusable method Version number :1 
	 * Date: December 11th 
	 * Purpose : Reusable method to login into any account 
	 * Parameters : webdriver, url, emailLocatorId, userName, pwdLocatorId, pwd, loginBtnLocatorId 
	 * Owner : Pooja Aggarwal
	 */
	public void loginAccount(WebDriver driver, String emailLocator, String userName, String pwdLocator,
			String pwd, String loginBtnLocator) {
		driver.findElement((By) byID(driver, emailLocator)).sendKeys(userName);
		driver.findElement((By) byID(driver, pwdLocator)).sendKeys(pwd);
		driver.findElement((By) byID(driver, loginBtnLocator)).click();
		System.out.println("login successfully");
	}

	/* reusable method Version number :1 
	 * Date: December 11th 
	 * Purpose : Reusable method to compare two links 
	 * Parameters : expectedLinkedList, actualLinkedList
	 * Owner : Pooja Aggarwal
	 */
	public void compareList(List<String> expectedList, List<String> actualList) {
		System.out.println("inside compare list");
		try {
			Assert.assertTrue(expectedList.containsAll(actualList));
			System.out.println("comprinng");
		} catch (Exception e) {
			e.getMessage();
		}
	}

	/* reusable method Version number :1 
	 * Date: December 11th 
	 * Purpose : Reusable method to scroll down the page vertically 
	 * Parameters : webdriver
	 * comment: 600 is y axis pixel
	 *  Owner :Pooja Aggarwal
	 */
	public void scrollDown(WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,600)", "");		
	}
	
	/* reusable method Version number :1 
	 * Date: December 11th 
	 * Purpose : Reusable method to scroll down at the end of page vertically 
	 * Parameters : webdriver
	 * comment: 600 is y axis pixel
	 *  Owner :Pooja Aggarwal
	 */
	public void scrollDownTillBottomOfPage(WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");		
	}

	/* reusable method Version number :1 
	 * Date: December 11th 
	 * Purpose : Reusable method to read xlsx from eclipse 
	 * Parameters : xlsxFileName, sheetNo, rowNum,colNum 
	 * comments: add apache-poi dependency
	 * Owner : Pooja Aggarwal 
	 */
	public void readXlsx(String fileName, int sheetNo, int rowNum, int colNum) throws IOException {
		File file = new File(fileName);
		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook excelBook = new XSSFWorkbook(fis);
		XSSFSheet sheet = excelBook.getSheetAt(sheetNo);
		int totalRow = sheet.getLastRowNum();
		System.out.println("total row " + totalRow);
		String data = sheet.getRow(rowNum).getCell(colNum).getStringCellValue();
		System.out.println("particular row and column data " + data);
	}

	/* reusable method Version number :1 
	 * Date: December 11th 
	 * Purpose : Reusable method to write in xlsx from eclipse 
	 * Parameters : xlsxFileName, sheetNo,rowNum, colNum, message 
	 * comments: add apache-poi dependency
	 * Owner : Pooja Aggarwal 
	 */
	public void writeInXlsx(String fileName, int sheetNo, int rowNum, int colNum, String message) {
		File file = new File(fileName);
		try {
			FileInputStream fis = new FileInputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheetAt(sheetNo);
			sheet.getRow(rowNum).createCell(colNum).setCellValue(message);
			FileOutputStream fos = new FileOutputStream(file);
			workbook.write(fos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* reusable method Version number :1 
	 * Date: December 11th 
	 * Purpose : Reusable method to send an email from eclipse
	 * Parameters : userName, pwd, subject,message, receiverEmailAddress 
	 * comments: add commons-emails dependency
	 * Owner : Pooja Aggarwal 
	 */
	public void sendEmailInJava(String userName, String pwd, String subject, String message,
			String receiverEmailAddress) throws EmailException {
		try {
			Email mail = new SimpleEmail();
			mail.setHostName("smtp.gmail.com");
			mail.setSmtpPort(465);
			mail.setAuthenticator(new DefaultAuthenticator(userName, pwd));
			mail.setSSL(true);
			mail.setFrom(userName);
			mail.setSubject(subject);
			mail.setMsg(message);
			mail.addTo(receiverEmailAddress);
			mail.send();
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}
	
	/* reusable method Version number :1 
	 * Date: December 11th
	 * Purpose : Reusable method to handle exceptions 
	 * Parameters : url, elmentLocatorId, driver,message, receiverEmailAddress 
	 * Owner : Pooja Aggarwal 
	 */
	@Test(expectedExceptions=NoSuchElementException.class)
	public void checkForNosuchElement(String url, String elmentLocatorId, WebDriver driver) {
		driver.get(url);		
		driver.findElement((By)byID(driver, elmentLocatorId));
	}
	
	/* reusable method Version number :1 
	 * Date: December 11th 
	 * Purpose : Reusable method to handle desktop popup in selenuim 
	 * Parameters : No
	 * comment: lets suppose fielname=notepad.exe and wanna write sample inside it. for this robot.keypress has to use 
	 * Owner : Pooja Aggarwal 
	 */
	 public void handleRobotClasses(String filename) throws AWTException, InterruptedException {
		String file = filename;
		Runtime run = Runtime.getRuntime(); 	
		try {
			run.exec(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Robot robot = new Robot();
		//let's suppose write in text file word=sample file. 
		robot.keyPress(KeyEvent.VK_S);		
		robot.keyPress(KeyEvent.VK_A);	
		robot.keyPress(KeyEvent.VK_M);		
		robot.keyPress(KeyEvent.VK_P);	
		robot.keyPress(KeyEvent.VK_L);
		robot.keyPress(KeyEvent.VK_E);
		robot.keyPress(KeyEvent.VK_SPACE);
		robot.keyPress(KeyEvent.VK_F);
		robot.keyPress(KeyEvent.VK_I);
		robot.keyPress(KeyEvent.VK_L);
		robot.keyPress(KeyEvent.VK_E);
	}
	 
	/* reusable method Version number :1 
	 * Date: December 11th 
	 * Purpose : Reusable method to upload a file from local in selenuim 
	 * Parameters : WebDriver,fileUploadLocatorId, uploadbtnLocator
	 * Owner : Pooja Aggarwal 
	 */
	public void uploadFileFromLocal(WebDriver driver, String fileUploadLocatorId,String fileName, String uploadbtnLocator ) {
		driver.findElement((By)byID(driver, fileUploadLocatorId)).sendKeys(fileName);
		driver.findElement((By)byID(driver, uploadbtnLocator)).click();
	}
	
	/* reusable method Version number :1 
	 * Date: December 11th 
	 * Purpose : Reusable method to upload a file in server 
	 * Parameters : WebDriver,fileUploadLocatorId, uploadbtnLocator
	 * Owner : Pooja Aggarwal 
	 */
	public void uploadFileInServer(DesiredCapabilities capabillities, String url, String fileUploadLocatorId,String fileName, String uploadbtnLocator ) {
		RemoteWebDriver driver = null;
		try {
			driver = new RemoteWebDriver(new URL(url), capabillities);
			driver.setFileDetector(new LocalFileDetector());
			driver.findElement((By) byID(driver, fileUploadLocatorId)).sendKeys(fileName);
			driver.findElement((By) byID(driver, uploadbtnLocator)).click();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}		      
	}
	
	/* reusable method Version number :1 
	 * Date: December 11th 
	 * Purpose : Reusable method to read tool tip text for a element 
	 * Parameters : WebDriver,url, elementXpathLocator
	 * Owner : Pooja Aggarwal 
	 */
	public void readToolTipText(WebDriver driver, String url, String elementXpathLocator) {
		driver.get(url);
		WebElement element=driver.findElement((By)byID(driver, elementXpathLocator));
		Actions action=new Actions(driver);
		action.moveToElement(element).build().perform();
		String elementToolTipText=element.getAttribute("title");		
		System.out.println("Tool tip text "+ elementToolTipText);
	}	
	
	/* reusable method Version number :1 Date: December 11th Purpose : Reusable
	 * method to write in properties file 
	 * Parameters : popertyFileNamepath
	 * comment: key and value store in property file
	 * Owner : Pooja Aggarwal 
	 */
	public static void writeInPropertyFile(File file, String key, String value) {
		Properties prop=new Properties();
		try {
			OutputStream fos=new FileOutputStream(file);
			prop.setProperty(key, value);			
			prop.store(fos, null);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	/* reusable method Version number :1 
	 * Date: December 11th 
	 * Purpose : Reusable method to update the test case name and status in mysql database 
	 * Parameters : databaseName, tableName, userName, pwd, description, executionStatus, startTime, endTime
	 * comments: 3306 is default port number of sql. localhost: if database is in user's local machine otherwise give server address instead of localhost
	 * Prerequisite: First create table in database. In this following code 4 coloumns are created. 
	 *  Owner : Pooja Aggarwal 
	 */
	public void updateResultInDB(String description, String executionStatus, String startTime, String endTime, String userName, String pwd, String tableName, String databaseName) {
		String query = "insert into"+tableName+" values(?,?,?,?)";
		Connection connection = null;
		PreparedStatement statement = null;;
		String connectionUrl = "jdbc:mysql://localhost:3306/"+databaseName+"?useSSL=false";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(connectionUrl, userName, pwd);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			statement = connection.prepareStatement(query);
			statement.setString(1, description);
			statement.setString(2, executionStatus);
			statement.setString(3, startTime);
			statement.setString(4, endTime);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
