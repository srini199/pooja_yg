package com.yg.mobileTestcase;

import org.testng.annotations.Test;

import com.mobile_automation.solution.Mobile_automation_common_utils;

public class StopAppium  extends Mobile_automation_common_utils {
	   @Test
		public void cleanUp() {
			stopAppiumServer();
		}
}
