package com.mobile_automation.solution;

import org.testng.annotations.Test;

public class StopAppium extends Mobile_automation_common_utils{
	@Test
	 public void cleanUp() {
		   stopAppiumServer();
	  }
}
