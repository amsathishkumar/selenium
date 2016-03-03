/*
 * 
 */

package com.sat.dbds.vcs.sso;

import org.junit.runner.RunWith;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * The Class ssoconfigIT.
 */
@RunWith(Cucumber.class)
@CucumberOptions(monochrome = true, strict = true, features = { "src/it/resources/"}, glue = { "com.cisco"}, format = {
		"pretty", "html:target/reports",
		"json:target/reports/cucumber-report.json" },tags = "@Login")
public class ssoconfigIT {

}
