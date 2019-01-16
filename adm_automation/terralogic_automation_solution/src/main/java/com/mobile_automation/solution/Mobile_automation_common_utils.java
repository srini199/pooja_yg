package com.mobile_automation.solution;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import io.appium.java_client.service.local.AppiumDriverLocalService;
//import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.ExtentTest;
//import com.aventstack.extentreports.MediaEntityBuilder;
//import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.terralogic.framework.Common_resuable_methods;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.touch.offset.PointOption;



public class Mobile_automation_common_utils extends Common_resuable_methods{





	/* reusable method
	 *  Version number :1
	 *  Date: December 11th 
	 *  Purpose  : Reusable method to start the server without using command prompt  
	 *  Parameters :  No
	 *  Owner : anjali
	 */


	public void startAppiumServer() {
		try{
			Process pr = Runtime.getRuntime().exec(System.getProperty("user.dir")+"/src/main/java/script.sh");;
			BufferedReader in = new BufferedReader(
					new InputStreamReader(pr.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null){
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 *  reusable method
	 *  Version number :1
	 *  Date: December 11th 
	 *  Purpose  : Reusable method to stop the server without using command prompt  
	 *  Parameters :  No
	 *   Owner : anjali
	 */


	public static void stopAppiumServer() {
		String[] command = {  "/usr/bin/killall", "-KILL", "node" };
		try {
			Runtime.getRuntime().exec(command);
			System.out.println("Appium server stopped.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* reusable method
	 *  Version number :1
	 *  Date: December 11th 
	 *  Purpose  : Reusable method to setUp the capabilities for mobile according to arguments  
	 *  Parameters : deviceName,platformVersion,PlatformName,appPackage
	 *  Owner : anjali
	 */


	public void setDesiredCapability(String platformVersion,String deviceName,String appPackage,String platformName,String appActivity) throws MalformedURLException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName",deviceName);
		capabilities.setCapability("platformVersion",platformVersion);
		capabilities.setCapability("platformName",platformName);
		capabilities.setCapability("appPackage", appPackage);
		capabilities.setCapability("appActivity", appActivity);
		driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
	}

	/* reusable method
	 *  Version number :1
	 *  Date: December 13th 
	 *  Purpose  : Reusable method for scrolling  
	 *  Parameters : x1 co-ordinate,y1 co-ordinate,x2 co-ordinate,y2 co-ordinate,driver
	 *  Owner : anjali
	 */


	public void page_scroll(int x1,int y1,int x2,int y2,AppiumDriver driver) {
		TouchAction ts=new TouchAction(driver);
		ts.press(PointOption.point(x1,y1)).moveTo(PointOption.point(x2,y2)).release().perform();	
	}

	/*reusable method
	 *  Version number :1
	 *  Date: December 13th 
	 *  Purpose  : Reusable method for click on any element  
	 *  Parameters : driver
	 *  Owner : anjali
	 *  work on:java-client 6.1.0 API
	 */   


	public void clickOnElement(int x1,int y1,AppiumDriver driver) {
		TouchAction touchAction = new TouchAction(driver);
		touchAction.tap(PointOption.point(x1,y1)).perform();
	}


	/* reusable method
	 *  Version number :1
	 *  Date: December 12th 
	 *  Purpose  : Reusable method for wait
	 *  This method is using Thread class and calling sleep method to wait for sometime
	 *  Parameters : x co-ordinate,y co-ordinate
	 *  owner : anjali
	 */


	public void Wait(long waitSeconds) {
		try{
			Thread.sleep(waitSeconds);
			System.out.println("Waiting.... ::"+waitSeconds+"ms");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/* reusable method
	 *  Version number :1
	 *  Date: December 12th 
	 *  Purpose  : Reusable method for read data from excel  
	 *  Parameters :filePath,sheetName
	 *  Owner : anjali
	 */


	public void ReadExcelFile(String filepath, int sheetNumber) throws Exception {

		String excelFilePath = filepath;
		FileInputStream inputStream = new FileInputStream(new File(excelFilePath));

		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheetAt(sheetNumber);

		Iterator<Row> iterator = firstSheet.iterator();

		while (iterator.hasNext()) {
			Row nextRow = iterator.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();


			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();

				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					System.out.print(cell.getStringCellValue());
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					System.out.print(cell.getBooleanCellValue());
					break;
				case Cell.CELL_TYPE_NUMERIC:
					System.out.print(cell.getNumericCellValue());
					break;
				}
				System.out.print(" - ");
			}
			System.out.println();
		}
		inputStream.close();
	}


	/* reusable method
	 *  Version number :1
	 *  Date: December 12th 
	 *  Purpose  : Reusable method for write data in excel  
	 *  Parameters :row,cell,result(what we need to print in particular row and cell)
	 *  Owner : anjali
	 */


	public void writeInExcel(String fileName, int sheetNo, int rowNum, int colNum, String message){
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

	/* reusable method
	 *  Version number :1
	 *  Date: December 12th 
	 *  Purpose  : Reusable method to create a connection with mysqldatabase.   
	 *  Parameters :No
	 *  Owner : anjali
	 */


	public void dbConnection(String query,String userName,String password,String column1,String column2) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306,userName,password");
			Statement stmt=conn.createStatement();
			boolean res=stmt.execute(query);
			if(res) {
				ResultSet rs=stmt.getResultSet();
				while(rs.next()){
					int id=rs.getInt(column1);
					String Getpassword=rs.getString(column2);
					System.out.println(id+"=="+Getpassword);
				}
			}
			else{
				int n=stmt.getUpdateCount();
				System.out.println("total record updated::"+n);

			}
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}


	/* reusable method
	 *  Version number :1
	 *  Date: December 16th 
	 *  Purpose  : Reusable method to get the device coordinates.   
	 *  Parameters :driver
	 *  Owner : anjali
	 */



	public void getDeviceCoordinate(AppiumDriver driver) {
		Dimension dimensions = driver.manage().window().getSize();
		int X = dimensions.getWidth();  
		int Y = dimensions.getHeight();
		System.out.println(X);
		System.out.println(Y);           
	}  

	/* reusable method
	 *  Version number :1
	 *  Date: December 16th 
	 *  Purpose  : Reusable method to get the specific element coordinate which we are passing in parameter   
	 *  Parameters :element,driver
	 *  Owner : anjali
	 */



	public static void getElementCoordinates(String ele,AndroidDriver driver,String id) {     
		Point p=(Point) driver.findElement((By) byID(driver, id));
		int X=p.getX();
		int Y=p.getY();
		System.out.println("X:" +X);
		System.out.println("Y:" +Y);   
	}
	
	/* reusable method
	 *  Version number :1
	 *  Date: December 16th 
	 *  Purpose  : Reusable method to send the mail with Attachment   
	 *  Parameters :sender,receiver,userName,password,filePath
	 *  Owner : anjali
	 */
	
	 public void sendMail(String sender,String receiver,String userName,String pass,String filePath) {
	        // Recipient's email ID needs to be mentioned.
	        String to = receiver;
	        // Sender's email ID needs to be mentioned
	        String from = sender;
	        final String username = userName;//change accordingly
	        final String password = pass;//change accordingly
	        // Assuming you are sending email through relay.jangosmtp.net
	        Properties props = new Properties();
	        props.put("mail.smtp.host", "smtp.gmail.com");
	        props.put("mail.smtp.socketFactory.port", "465");
	        props.put("mail.smtp.socketFactory.class",
	                "javax.net.ssl.SSLSocketFactory");
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.port", "465");
	        // Get the Session object.
	        Session session = Session.getInstance(props,
	                new javax.mail.Authenticator() {
	                    protected PasswordAuthentication getPasswordAuthentication() {
	                        return new PasswordAuthentication(username, password);
	                    }
	                });
	        try {
	            // Create a default MimeMessage object.
	            Message message = new MimeMessage(session);
	            // Set From: header field of the header.
	            message.setFrom(new InternetAddress(from));
	            // Set To: header field of the header.
	            message.setRecipients(Message.RecipientType.TO,
	                    InternetAddress.parse(to));
	            // Set Subject: header field
	            message.setSubject("Attachment");
	            // Create the message part
	            BodyPart messageBodyPart = new MimeBodyPart();
	            // Now set the actual message
	            messageBodyPart.setText("Please find the attachment below");
	            // Create a multipar message
	            Multipart multipart = new MimeMultipart();
	            // Set text message part
	            multipart.addBodyPart(messageBodyPart);
	            // Part two is attachment
	            messageBodyPart = new MimeBodyPart();
	            //String fileName = "/home/terralogic/Desktop/Testing/android/test-output/emailable-report.html";
	            //we need to give like this
	            String fileName=filePath;
	            DataSource source = new FileDataSource(fileName);
	            messageBodyPart.setDataHandler(new DataHandler(source));
	            messageBodyPart.setFileName(fileName);
	            multipart.addBodyPart(messageBodyPart);
	            // Send the complete message parts
	            message.setContent(multipart);
	            // Send message
	            Transport.send(message);
	            System.out.println("Email Sent Successfully !!");
	        } catch (MessagingException e) {
	            throw new RuntimeException(e);
	        }
	    }
	 
	 /* reusable method
		 *  Version number :1
		 *  Date: December 20th 
		 *  Purpose  : Reusable method to Take screenshot   
		 *  Parameters :driver,fileName(String)
		 *  Owner : anjali
		 */
	 
	 public void captureScreenshot(AndroidDriver driver,String fileName) throws IOException {	
			File src = driver.getScreenshotAs(OutputType.FILE);
			String filepath = System.getProperty("user.dir")+fileName;
			FileUtils.copyFile(src, new File(filepath));
			try {
				FileUtils.copyFile(src, new File(filepath));
				System.out.println("screenshot taken");
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	
	 
	/* reusable method
	 *  Version number :1
	 *  Date: December 19th 
	 *  Purpose  : Reusable method to create the LinkedHashSet,HashSet,TreeSet   
	 *  Parameters :Set object
	 *  Owner : anjali
	 */

	public static void CreateSet(Set s) {

		if(s instanceof LinkedHashSet) {
			LinkedHashSet h1=(LinkedHashSet)s;  
			System.out.println("LinkedHashSet");
		}else if(s instanceof HashSet) {
			HashSet hs=(HashSet)s;
			System.out.println("HashSet");
		}else if(s instanceof TreeSet) {
			TreeSet ts=(TreeSet)s;
			System.out.println("TreeSet");
		}
	}

	/* reusable method
	 *  Version number :1
	 *  Date: December 19th 
	 *  Purpose  : Reusable method to add element in LinkedHashSet,HashSet,TreeSet   
	 *  Parameters :Set object,noOfElementEnter(we need to send howmay element we want to add in Set Collection)
	 *  Owner : anjali
	 */

	public static void addElementSet(Set s,int noOfElementEnter) {
		Scanner sc=new Scanner(System.in);
		if(s instanceof LinkedHashSet) { 
			LinkedHashSet h1=(LinkedHashSet)s;
			for(int i=0;i<noOfElementEnter;i++) {
				System.out.println("Enter element");
				String str=sc.next();
				h1.add(str);	
			}
		}else if(s instanceof HashSet) {
			HashSet hs=(HashSet)s;
			for(int i=0;i<noOfElementEnter;i++) {
				System.out.println("Enter element");
				String str=sc.next();
				hs.add(str);
			}
		}else if(s instanceof TreeSet) {
			TreeSet ts=(TreeSet)s;
			for(int i=0;i<noOfElementEnter;i++) {
				System.out.println("Enter element");
				String str=sc.next();
				ts.add(str);
			}
		}
	}

	/* reusable method
	 *  Version number :1
	 *  Date: December 19th 
	 *  Purpose  : Reusable method to retrieve element from LinkedHashSet,HashSet,TreeSet   
	 *  Parameters :Set object
	 *  Owner : anjali
	 */

	public static void retrieve(Set s){
		if(s instanceof LinkedHashSet) {
			LinkedHashSet h1=(LinkedHashSet)s;
			Iterator itr=h1.iterator();
			while(itr.hasNext()) {
				System.out.println(itr.next());
			}  
		}
		else if(s instanceof HashSet) {
			HashSet hs=(HashSet)s;
			Iterator itr=hs.iterator();
			while(itr.hasNext()) {
				System.out.println(itr.next());
			} 
		}
		else if(s instanceof TreeSet) {
			TreeSet ts=(TreeSet)s;
			Iterator itr=ts.iterator();
			while(itr.hasNext()) {
				System.out.println(itr.next());
			}
		}
	}
	/* reusable method
	 *  Version number :1
	 *  Date: December 19th 
	 *  Purpose  : Reusable method to remove particular element from LinkedHashSet,HashSet,TreeSet   
	 *  Parameters :Set object,noOfElementRemove
	 *  Owner : anjali
	 */

	public static void removeElement(Set s,int noOfElementRemove) {
		Scanner sc=new Scanner(System.in);
		if(s instanceof LinkedHashSet) {
			LinkedHashSet h1=(LinkedHashSet)s;
			for(int i=0;i<noOfElementRemove;i++) {
				System.out.println("Enter element");
				String str=sc.next();
				h1.remove(str);	
			}
		}else if(s instanceof HashSet) {
			HashSet hs=(HashSet)s;
			for(int i=0;i<noOfElementRemove;i++) {
				System.out.println("Enter element");
				String str=sc.next();
				hs.remove(str);	
			}
		}else if(s instanceof TreeSet) {
			TreeSet ts=(TreeSet)s;
			for(int i=0;i<noOfElementRemove;i++) {
				System.out.println("Enter element");
				String str=sc.next();
				ts.remove(str);	
			}
		}
	}

	/* reusable method
	 *  Version number :1
	 *  Date: December 19th 
	 *  Purpose  : Reusable method to remove whole element from LinkedHashSet,HashSet,TreeSet   
	 *  Parameters :Set object
	 *  Owner : anjali
	 */

	public static void removeAllElement(Set s){
		if(s instanceof LinkedHashSet) {
			LinkedHashSet h1=(LinkedHashSet)s;
			h1.clear();
		}
		else if(s instanceof HashSet){
			HashSet hs=(HashSet)s;
			hs.clear();
		}
		else if(s instanceof TreeSet) {
			TreeSet ts=(TreeSet)s;
			ts.clear();
		}
	}

	/* reusable method
	 *  Version number :1
	 *  Date: December 19th 
	 *  Purpose  : Reusable method to create the LinkedList,Vector,Stack   
	 *  Parameters :List object
	 *  Owner : anjali
	 */

	public static void createList(List l) {

		if(l instanceof ArrayList)
		{
			ArrayList a1=(ArrayList)l;
			System.out.println("List");
		}
		else if(l instanceof LinkedList) {
			LinkedList l1=(LinkedList)l;
			System.out.println("LinkedList");
		}
		else if(l instanceof Vector) {
			Vector v1=(Vector)l;
			System.out.println("vector");
		}
		else {
			Stack s1=(Stack)l;
			System.out.println("stack");		 
		}
	}

	/*  reusable method
	 *  Version number :1
	 *  Date: December 19th 
	 *  Purpose  : Reusable method to add Element element in LinkedList,Vector,Stack.   
	 *  Parameters :List object,noOfElementEnter
	 *  Owner : anjali
	 */


	public static void addElement(List l,int noOfElementEnter){
		Scanner sc=new Scanner(System.in);
		if(l instanceof LinkedList) { 
			LinkedList l1=(LinkedList)l;
			for(int i=0;i<noOfElementEnter;i++) {
				System.out.println("Enter element");
				String str=sc.next();
				l1.add(str);
			}
		}else if(l instanceof Stack) {
			Stack s1=(Stack)l;
			for(int i=0;i<noOfElementEnter;i++) {
				System.out.println("Enter element");
				String str=sc.next();
				s1.add(str);
			}
		}else if(l instanceof Vector){
			Vector v1=(Vector)l;
			for(int i=0;i<noOfElementEnter;i++) {
				System.out.println("Enter element");
				String str=sc.next();
				v1.add(str);
			}
		}
		else{
			ArrayList a1=(ArrayList)l;
			for(int i=0;i<noOfElementEnter;i++) {
				System.out.println("Enter element");
				String str=sc.next();
				a1.add(str);
			}
		}
	}
	/*  reusable method
	 *  Version number :1
	 *  Date: December 19th 
	 *  Purpose  : Reusable method to get Element element from LinkedList,Vector,Stack.   
	 *  Parameters :List object
	 *  Owner : anjali
	 */

	public static void getElement(List l) {
		if(l instanceof ArrayList) {
			ArrayList a1=(ArrayList)l;
			ListIterator itr=a1.listIterator();
			while(itr.hasNext()) {
				System.out.println(itr.next());
			}
		}

		else if(l instanceof LinkedList) {
			LinkedList l1=(LinkedList)l;
			ListIterator itr=l1.listIterator();
			while(itr.hasNext()) {
				System.out.println(itr.next());
			}
		} 
		else if(l instanceof Vector){
			Vector v1=(Vector)l;
			ListIterator itr=v1.listIterator();
			while(itr.hasNext()) {
				System.out.println(itr.next());
			} 
		}
		else{
			Stack s1=(Stack)l;
			ListIterator itr=s1.listIterator();
			while(itr.hasNext()) {
				System.out.println(itr.next());
			} 
		}

	}
	/*  reusable method
	 *  Version number :1
	 *  Date: December 19th 
	 *  Purpose  : Reusable method to remove Element from LinkedList,Vector,Stack.   
	 *  Parameters :List object,noOfElementRemove
	 *  Owner : anjali
	 */

	public static void removeElement(List l,int noOfElementRemove){
		Scanner sc=new Scanner(System.in);
		if(l instanceof ArrayList) {
			ArrayList a1=(ArrayList)l;
			for(int i=0;i<noOfElementRemove;i++) {
				System.out.println("Enter element");
				String str=sc.next();
				a1.remove(str);	
			}
		}
		else if(l instanceof LinkedList) {
			LinkedList l1=(LinkedList)l;
			for(int i=0;i<noOfElementRemove;i++) {
				System.out.println("Enter element");
				String str=sc.next();
				l1.remove(str);	
			}
		}
		else if(l instanceof Vector){
			Vector v1=(Vector)l;
			for(int i=0;i<noOfElementRemove;i++) {
				System.out.println("Enter element");
				String str=sc.next();
				v1.remove(str);	
			}
		}
		else {
			Stack s1=(Stack)l;
			for(int i=0;i<noOfElementRemove;i++) {
				System.out.println("Enter element");
				String str=sc.next();
				s1.remove(str);	
			}

		}
	}

	/*  reusable method
	 *  Version number :1
	 *  Date: December 19th 
	 *  Purpose  : Reusable method to remove all Element from LinkedList,Vector,Stack.   
	 *  Parameters :List object
	 *  Owner : anjali
	 */

	public static void removeAllElementList(List l){
		if(l instanceof ArrayList) {
			ArrayList a1=(ArrayList)l;
			a1.clear();
		}
		else if(l instanceof LinkedList) {
			LinkedList l1=(LinkedList)l;
			l1.clear();
		}
		else if(l instanceof Vector){
			Vector v1=(Vector)l;
			v1.clear();
		}
		else {
			Stack s1=(Stack)l;
			s1.clear();
		}
	}

	/* reusable method
	 *  Version number :1
	 *  Date: December 19th 
	 *  Purpose  : Reusable method to create the HashMap,LinkedHashMap,TreeMap   
	 *  Parameters :Map object
	 *  Owner : anjali
	 */

	public static void createMap(Map m){
		if(m instanceof HashMap) {
			HashMap hm=(HashMap)m;
			System.out.println("HashMap");
		}else if(m instanceof LinkedHashMap) {
			LinkedHashMap lh=(LinkedHashMap)m;
			System.out.println("LinkedHashMap");
		}else if(m instanceof TreeMap) {
			TreeMap tm=(TreeMap)m;
			System.out.println("Treemap");
		}
	}

	/* reusable method
	 *  Version number :1
	 *  Date: December 19th 
	 *  Purpose  : Reusable method to add element in HashMap,LinkedHashMap,TreeMap   
	 *  Parameters :Map object,noOfElementAdded
	 *  Owner : anjali
	 */
	public static void addElement(Map m,int noOfElementAdded) {
		Scanner sc=new Scanner(System.in);
		if(m instanceof HashMap){
			HashMap hm=(HashMap)m;
			for(int i=0;i<noOfElementAdded;i++){
				System.out.println("Enter key");
				String key=sc.next();
				System.out.println("Enter value");
				String str=sc.next();
				hm.put(key,str);

			}
		}else if(m instanceof LinkedHashMap) {
			LinkedHashMap lh=(LinkedHashMap)m;
			for(int i=0;i<noOfElementAdded;i++){
				System.out.println("Enter key");
				String key=sc.next();
				System.out.println("Enter value");
				String str=sc.next();
				lh.put(key,str);

			}

		}else if(m instanceof TreeMap){
			TreeMap tm=(TreeMap)m;
			for(int i=0;i<noOfElementAdded;i++){
				System.out.println("Enter key");
				String key=sc.next();
				System.out.println("Enter value");
				String str=sc.next();
				tm.put(key,str);
			}
		}
	}

	/* reusable method
	 *  Version number :1
	 *  Date: December 19th 
	 *  Purpose  : Reusable method to remove particulat element from HashMap,LinkedHashMap,TreeMap using key   
	 *  Parameters :Map,key(int)
	 *  Owner : anjali
	 */

	public static void removeElement(Map m,int key) {
		//String str=sc.next();
		if(m instanceof HashMap){
			HashMap hm=(HashMap)m;
			hm.remove(key);
		}else if(m instanceof LinkedHashMap) {
			LinkedHashMap lh=(LinkedHashMap)m;
			lh.remove(key);
		}else if(m instanceof TreeMap){
			TreeMap tm=(TreeMap)m;
			tm.remove(key);
		}
	}

	/* reusable method
	 *  Version number :1
	 *  Date: December 19th 
	 *  Purpose  : Reusable method to get element from  HashMap,LinkedHashMap,TreeMap   
	 *  Parameters :Map object
	 *  Owner : anjali
	 */

	public static void iteration(Map m){ 
		if(m instanceof HashMap) {
			HashMap hm=(HashMap)m;
			Set set=hm.entrySet();//Converting to Set so that we can traverse  
			Iterator itr=set.iterator();  
			while(itr.hasNext()){  
				//Converting to Map.Entry so that we can get key and value separately  
				Map.Entry entry=(Map.Entry)itr.next();  
				System.out.println(entry.getKey()+" "+entry.getValue());  
			}  
		}else if(m instanceof LinkedHashMap) {
			LinkedHashMap lh=(LinkedHashMap)m;
			Set set=lh.entrySet();//Converting to Set so that we can traverse  
			Iterator itr=set.iterator();  
			while(itr.hasNext()){  
				//Converting to Map.Entry so that we can get key and value separately  
				Map.Entry entry=(Map.Entry)itr.next();  
				System.out.println(entry.getKey()+" "+entry.getValue());  
			}  
		}
		else if(m instanceof TreeMap) {
			TreeMap tm=(TreeMap)m;
			Set set=tm.entrySet();//Converting to Set so that we can traverse  
			Iterator itr=set.iterator();  
			while(itr.hasNext()){  
				//Converting to Map.Entry so that we can get key and value separately  
				Map.Entry entry=(Map.Entry)itr.next();  
				System.out.println(entry.getKey()+" "+entry.getValue());  
			}  
		}

	}

	/* reusable method
	 *  Version number :1
	 *  Date: December 19th 
	 *  Purpose  : Reusable method to removeAll element from HashMap,LinkedHashMap,TreeMap   
	 *  Parameters :Map object
	 *  Owner : anjali
	 */

	public static void removeAllElementMap(Map m){
		if(m instanceof HashMap) {
			HashMap hm=(HashMap)m;
			hm.clear();
		}
		else if(m instanceof LinkedHashMap) {
			LinkedHashMap lh=(LinkedHashMap)m;
			lh.clear();
		}
		else {
			TreeMap tm=(TreeMap)m;
			tm.clear();
		}

	}

}






