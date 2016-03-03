package com.cisco.common;

import com.sat.spvgt.utils.cucumber.FeatureFileReadTestcase;

public class ReadScenario {
public static void main(String args[]){
	System.setProperty("feature.annotation","Completed");
	System.out.println(System.getProperty("feature.annotation"));
	FeatureFileReadTestcase df = new FeatureFileReadTestcase();
	df.createTestcaseDetailFile("C:\\Users\\amsathishkumar\\git\\linear_management_system\\LMS\\src\\it\\resources", "feature");

}
}
