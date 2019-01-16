//package com.api_automation.solution;
//
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.Statement;
//import org.apache.poi.xssf.usermodel.XSSFRow;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import com.terralogic.framework.Common_resuable_methods;
//
//import io.restassured.RestAssured;
//import io.restassured.path.json.JsonPath;
//import io.restassured.response.Response;
//
//
//
//public class Api_automation_common_utils extends Common_resuable_methods
//{
//	 /*  reusable method
//    Version number :1
//    Date: December 12th 
//    Purpose  : Reusable method to read xlsx file 
//    Parameters : 
//     Owner : Uma P K
//           
//	  */ 
//	 
//
//	public String[] readXlsx(String fileName, String apiName,int Sheetno, int rowno) throws IOException
//	{
//		XSSFRow row = null;
//        File file = new File(fileName);
//        FileInputStream fis = new FileInputStream(file);
//        
//      //we create an XSSF Workbook object for our XLSX Excel File
//        @SuppressWarnings("resource")
//		XSSFWorkbook excelBook = new XSSFWorkbook(fis);
//        XSSFSheet sheet = excelBook.getSheetAt(Sheetno);
//        //get total number of rows
//        int totalRow = sheet.getLastRowNum();
//        row = sheet.getRow(rowno);
//        //get total number of columns
//        int colCount = row.getLastCellNum();
//        System.out.println("Rows:" + totalRow + "colCount: " + colCount);
//       // System.out.println("total row " + totalRow);
//        String[] request = new String[colCount];
//        for(int i=0;i<=totalRow;i++)
//        {
//        	String data = sheet.getRow(i).getCell(0).getStringCellValue();
//        	//System.out.println(data);
//        	if(data.equals(apiName))
//        	{
//        		for(int j=0;j<colCount;j++)
//        		{
//        			request[j]= sheet.getRow(i).getCell(j).getStringCellValue();
//        			System.out.println(request[j]);
//        		}
//        		break;
//        	}
//        }
//        return request;
//	}
//	
//	
//	 /*  reusable method
//    Version number :1
//    Date: December 12th 
//    Purpose  : Reusable method to compare two responses of api  
//    Parameters :  response of API 
//     Owner : Uma P K
//           
//*/ 
//
//
//	public int compare(Object arg0, Object arg1) 
//	{
//		// TODO Auto-generated method stub
//		String str=(String)arg0;
//		String str1=(String)arg1;
//		int n=str.compareTo(str1);
//		System.out.println(n);
//		return n;
//		
//	}
//	
//	
//	 /*  reusable method
//    Version number :1
//    Date: December 12th 
//    Purpose  : Reusable method to store responses in a file 
//    Parameters :  API response
//     Owner : Uma P K
//           Text file
//*/ 
//
//
//	 public  void writeUsingBufferedWriter(String data)
//	 {
//	        File file = new File("Text file");
//	        FileWriter fr = null;
//	        BufferedWriter br = null;
//	        String dataWithNewLine=data+System.getProperty("line.separator");
//	        try{
//	            fr = new FileWriter(file);
//	            br = new BufferedWriter(fr);
//	            while(data != null)
//	            {
//	                br.write(dataWithNewLine);
//	            }
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	        }finally{
//	            try {
//	                br.close();
//	                fr.close();
//	            } catch (IOException e) {
//	                e.printStackTrace();
//	            }
//	        }
//	    }
//	 
//	 /*  reusable method
//	    Version number :1
//	    Date: December 12th 
//	    Purpose  : Reusable method that perform put,post,delete and get operations  
//	    Parameters :  Type,URL and body
//	     Owner : Uma P K
//	           
//	*/ 
//
//
//	 public String[] apiCall1(String type,String url,String body)
//		{
//			 
//			if(type.equals("GET"))
//			{  
//				RestAssured.baseURI = url;
//				Response res=RestAssured.given().
//							header("Content-Type","application/json").
//						when().
//							get(url).
//						then().
//						extract().response();
//				int  code = res.getStatusCode();
//				String resString=res.asString();
//				JsonPath jsn2=new JsonPath(resString);
//				System.out.println(resString);
//				String StatusCode = Integer.toString(code);
//		        String ar[] = new String[2];
//		        ar[0]= resString;
//		        ar[1] =  StatusCode;
//				return ar;
//			}
//			
//			else if(type.equals("POST"))
//			{
//					RestAssured.baseURI = url;
//					Response res=RestAssured.given().
//							header("Content-Type","application/json").
//								body(body).
//							 	when().
//								post(url).
//								then(). 
//							extract().response();
//					String resString = res.asString();
//					System.out.println(resString);
//					int code = res.getStatusCode();
//					String StatusCode = Integer.toString(code);
//			        String ar[] = new String[2];
//			        ar[0]= resString;
//			        ar[1] =  StatusCode;
//					return ar;
//			}
//			
//			else if(type.equals("DELETE"))
//			{
//				RestAssured.baseURI = url;
//				Response res=RestAssured.given().
//							header("Content-Type","application/json").
//							body(body).
//						when().
//							delete(url).
//						then(). 
//							extract().response();
//				String resString=res.asString();
//				JsonPath jsn2=new JsonPath(resString);
//				System.out.println(resString);
//				System.out.println(jsn2);
//				String body3=jsn2.get("[1].body");
//				System.out.println(body3);
//				int code = res.getStatusCode();
//				String StatusCode = Integer.toString(code);
//		        String ar[] = new String[2];
//		        ar[0]= resString;
//		        ar[1] =  StatusCode;
//				return ar;
//			 
//			}
//			
//			else if(type.equals("PUT"))
//			{
//				RestAssured.baseURI = url;
//				Response res=RestAssured.given().
//							header("Content-Type","application/json").
//							body(body).
//						when().
//							put(url).
//						then(). 
//							extract().response();
//				String resString=res.asString();
//				JsonPath jsn2=new JsonPath(resString);
//				System.out.println(resString);
//				 int code = res.getStatusCode();
//				 String StatusCode = Integer.toString(code);
//			        String ar[] = new String[2];
//			        ar[0]= resString;
//			        ar[1] =  StatusCode;
//					return ar;
//				 
//			}
//			else
//			{
//				String ar[] = new String[1];
//		        ar[0]= "Type not matched";
//				return ar;
//			}
//		}
//		
//		 /*  reusable method
//	    Version number :1
//	    Date: December 12th 
//	    Purpose  : Reusable method to store responses in database  
//	    Parameters :  type,URL,input body,output response and status.
//	     Owner : Uma P K
//	           
//		  */ 
//		public void onTestSuccess(String type,String url,String body,String response,String status, String hostName,String user ,String password)
//		{
//			Connection con;
//			Statement st;
//			ResultSet rs;
//			try {
//				Class.forName("com.mysql.jdbc.Driver");
//				System.out.println("Driver loaded successfully");
//				con = DriverManager.getConnection(hostName, user, password);
//				System.out.println("Database Connection Successful");
//				st = con.createStatement();
//				String query = " insert into  apiResponse (Type, URL, Body, Response, Status)"
//				        + " values (?, ?, ?, ?, ?)";
//
//				      // create the mysql insert preparedstatement
//				 PreparedStatement preparedStmt = con.prepareStatement(query);
//				      preparedStmt.setString (1, type);
//				      preparedStmt.setString (2, url);
//				      preparedStmt.setString(3, body);
//				      preparedStmt.setString(4, response);
//				      preparedStmt.setString (5, status);
//				      
//
//				      // execute the preparedstatement
//				      preparedStmt.execute();
//			}catch(Exception ex) {
//			  ex.printStackTrace();
//			}
//				
//		}
//		
//		 /*  reusable method
//	    Version number :1
//	    Date: December 12th 
//	    Purpose  : Reusable method to check response code
//	    Parameters : 
//	     Owner : Uma P K
//	           
//		  */ 
//		public String CompareStatusCode(String code)
//		{
//			loadProperties();
//			String successCode;
//			System.out.println(propertyMap.get("postStatus"));
//			
//			if(code.equals(propertyMap.get("postStatus")) ||code.equals(propertyMap.get("putStatus")) )
//				successCode = "PASS";
//			else if(code.equals(propertyMap.get("internalStatus")))
//				successCode = "Internal Server Error";
//			else if(code.equals(propertyMap.get("notFoundStatus")))
//				successCode = "Server can not find requested page";
//			else if(code.equals(propertyMap.get("noContentStatus")))
//				successCode = "No Entity-Body in the Response";
//			else if(code.equals(propertyMap.get("badStatus")))
//				successCode = "Bad Request";
//			else
//				successCode = "FAIL";
//			
//			return successCode;
//		}
//				
//}
