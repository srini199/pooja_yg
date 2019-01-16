//package com.api_automation.solution;
//import java.io.IOException;
//
//import org.testng.annotations.BeforeTest;
//import org.testng.annotations.Test;
//
//import io.restassured.RestAssured;
//import io.restassured.response.Response;
//
//
//public class EPAS_API extends Api_automation_common_utils
//{ 
//			
//			@BeforeTest
//			public void loadproperties()
//			{
//				loadProperties();
//			}
//			String sessionId;
//			String TestResult;
//			
//			int sheetno = 0;
//			int rowno = 0;
//			
//			//Login api which gives a session id which will be sent to remaining all api's
//			@Test(priority=1)
//			public void epassTestScript() throws IOException
//			{ 
//				String Path = propertyMap.get("filePath");
//				 String apiName =  propertyMap.get("api_1");
//				 
//				String[] requestBody = readXlsx(Path, apiName, sheetno,rowno);
//			 
//					
//				if(requestBody[0].equals(apiName))
//				{
//					RestAssured.baseURI =requestBody[2];
//					Response res=RestAssured.given().
//							header("Content-Type","application/json").
//								body(requestBody[3]).
//								when().
//								post("/login_verification.php").
//								then().
//								assertThat().statusCode(200).
//							extract().response();
//					String resString = res.asString();
//					System.out.println(resString);
//					sessionId = res.jsonPath().getString("sessionid");
//					System.out.println(sessionId);	
//					System.out.println(resString);
//					String status = CompareStatusCode(resString);
//					onTestSuccess(requestBody[0],requestBody[1],requestBody[3],resString,status, propertyMap.get("hostname"),propertyMap.get("user") ,propertyMap.get("password"));
//				}
//			}
//			
//			//login authentication api which will give information of the employee
//			@Test(priority=2)
//			public void authentication() throws IOException
//			{
//
//				String[] RequestBody = readXlsx(propertyMap.get("filePath"),propertyMap.get("api_2"),sheetno,rowno);
//				RequestBody[3] = "{\n" + "\"sessionid\":" +"\""+sessionId+"\""+"\n"+RequestBody[3];
//				System.out.println(RequestBody[3]);
//				String[] response = apiCall1(RequestBody[1],RequestBody[2],RequestBody[3]);
//				TestResult =  CompareStatusCode(response[1]);
//				onTestSuccess(RequestBody[0],RequestBody[1],RequestBody[3],response[0],TestResult, propertyMap.get("hostname"),propertyMap.get("user") ,propertyMap.get("password"));
//				
//				
//			}
//			
//			//API used to add admin 
//			@Test(priority=3)
//			public void AdminDetailsADDAPI() throws IOException
//			{
//				String[] RequestBody = readXlsx(propertyMap.get("filePath"),propertyMap.get("api_3"),sheetno,rowno);
//				RequestBody[3] = "{\n" + "\"sessionid\":" +"\""+sessionId+"\","+"\n"+RequestBody[3];
//				System.out.println(RequestBody[3]);
//				String[] response = apiCall1(RequestBody[1],RequestBody[2],RequestBody[3]);
//				TestResult =  CompareStatusCode(response[1]);
//				onTestSuccess(RequestBody[0],RequestBody[1],RequestBody[3],response[0],TestResult, propertyMap.get("hostname"),propertyMap.get("user") ,propertyMap.get("password"));
//				
//			}
//			//API used to edit the admin details
//			@Test(priority=4)
//			public void  AdminDetailsEditAPI() throws IOException
//			{
//				String[] RequestBody = readXlsx(propertyMap.get("filePath"),propertyMap.get("api_4"),sheetno,rowno);
//				RequestBody[3] = "{\n" + "\"sessionid\":" +"\""+sessionId+"\","+"\n"+RequestBody[3];
//				String[] response = apiCall1(RequestBody[1],RequestBody[2],RequestBody[3]);
//				TestResult =  CompareStatusCode(response[1]);
//				onTestSuccess(RequestBody[0],RequestBody[1],RequestBody[3],response[0],TestResult, propertyMap.get("hostname"),propertyMap.get("user") ,propertyMap.get("password"));		
//			}
//			//API used to get the list of the admins
//			@Test(priority=5)
//			public void AdminDetailsGetList() throws IOException
//			{
//
//				String[] RequestBody = readXlsx(propertyMap.get("filePath"),propertyMap.get("api_5"),sheetno,rowno);
//				RequestBody[3] = "{\n" + "\"sessionid\":" +"\""+sessionId+"\","+"\n"+RequestBody[3];
//				String[] response = apiCall1(RequestBody[1],RequestBody[2],RequestBody[3]);
//				TestResult =  CompareStatusCode(response[1]);
//				onTestSuccess(RequestBody[0],RequestBody[1],RequestBody[3],response[0],TestResult, propertyMap.get("hostname"),propertyMap.get("user") ,propertyMap.get("password"));
//				
//			}
//			//API used to remove the admin
//			@Test(priority=6)
//			public void RemoveAdminAPI() throws IOException
//			{
//				 
//				String[] RequestBody = readXlsx(propertyMap.get("filePath"),propertyMap.get("api_6"),sheetno,rowno);
//				RequestBody[3] = "{\n" + "\"sessionid\":" +"\""+sessionId+"\","+"\n"+RequestBody[3];
//				String[] response = apiCall1(RequestBody[1],RequestBody[2],RequestBody[3]);
//				TestResult =  CompareStatusCode(response[1]);
//				onTestSuccess(RequestBody[0],RequestBody[1],RequestBody[3],response[0],TestResult, propertyMap.get("hostname"),propertyMap.get("user") ,propertyMap.get("password"));
//				
//			}
//			
//			
//			@Test(priority=7)
//			public void LogoutAPI() throws IOException
//			{
//				String[] RequestBody = readXlsx(propertyMap.get("filePath"),propertyMap.get("api_7"),sheetno,rowno);
//				RequestBody[3] = "{\n" + "\"sessionid\":" +"\""+sessionId+"\""+"\n"+RequestBody[3];
//				String[] response = apiCall1(RequestBody[1],RequestBody[2],RequestBody[3]);
//				TestResult =  CompareStatusCode(response[1]);                                                     
//				onTestSuccess(RequestBody[0],RequestBody[1],RequestBody[3],response[0],TestResult, propertyMap.get("hostname"),propertyMap.get("user") ,propertyMap.get("password"));
//				
//			}
//}
//
//
//
