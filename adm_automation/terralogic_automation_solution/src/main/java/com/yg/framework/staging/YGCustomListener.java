package com.yg.framework.staging;

import java.sql.Timestamp;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.yg.framework.staging.MongoDbconnectivity;

public class YGCustomListener implements ITestListener {
	
	String timeStamp, module;
//	String versionNo="V1";
	String status;
	@Override
	public void onTestStart(ITestResult result) {
		Date d=new Date();
		Timestamp t=new Timestamp(d.getTime());
		timeStamp=t.toString();			
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		String TestCaseId = null;
		module=result.getMethod().getMethodName();
		if(module.equals("createABus")) {
			TestCaseId="1";
		}else if(module.equals("bookTicket")) {
			TestCaseId="2";
		}else if(module.equals("cancelTicket")){
			TestCaseId="3";
		}else if(module.equals("inActiveBusService")){
			TestCaseId="4";
		}else{
			TestCaseId="5";
		}		
		status="pass";
		String build_details=System.getenv("BUILD_NUMBER");
		MongoDbconnectivity connection=new MongoDbconnectivity();
		connection.updateStatus(TestCaseId,module,build_details,status,timeStamp);
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		module=result.getMethod().getMethodName();
		String TestCaseId = null;
		module=result.getMethod().getMethodName();
		if(module.equals("createABus")) {
			TestCaseId="1";
		}else if(module.equals("bookTicket")) {
			TestCaseId="2";
		}else if(module.equals("cancelTicket")){
			TestCaseId="3";
		}else if(module.equals("inActiveBusService")){
			TestCaseId="4";
		}else{
			TestCaseId="5";
		}
		status="fail";
		String build_details=System.getenv("BUILD_NUMBER");
		MongoDbconnectivity connection=new MongoDbconnectivity();
		connection.updateStatus(TestCaseId,module,build_details,status,timeStamp);		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

}
