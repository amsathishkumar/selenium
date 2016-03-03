/**
 * Copyright (c) 2015 by sat, Inc.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of sat,  ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with sat.
 *
 *
 * @Project: LMS
 * @Author: amsathishkumar
 * @Version:
 * @Description:
 * @Date created: Oct 8, 2015
 */
package com.cisco.lms;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * The Class LmsIT.
 */
@RunWith(Cucumber.class)
@CucumberOptions(monochrome = true, strict = true, features = { "src/it/resources/", }, glue = { "com.cisco", }, format = {
		"pretty", "html:target/reports",
		"json:target/reports/cucumber-report.json" }
)
public class LmsIT {

}
