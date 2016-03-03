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
 * @Project: Utils
 * @Author: amsathishkumar
 * @Version: 
 * @Description:  
 * @Date created: Apr 9, 2015
 */
package com.sat.dbds.utils.cucumber;

import java.io.IOException;

import com.sat.dbds.utils.configfilehandler.ConfigFileHandler;
import com.sat.dbds.utils.logging.LogHandler;
import com.sat.dbds.utils.selenium.SeleniumUtilities;
import com.sat.dbds.utils.ssh.SSHManager;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

// TODO: Auto-generated Javadoc
/**
 * The Class Hooks.
 */
public class Hooks {

	/** The scenario. @Before(order = 1)*/
	public static Scenario scenario;
	/* We need this flag, to ensure the browser indeed does not
	 * open when we don't need one.
	 */
	/** The is browser needed. */
	public static boolean isBrowserNeeded = true;
	/*
	 * @Before(order = 1)
	 */
	/**
	 * Start scenario
	 * <pre>
	 * <b> Gherkin </b>
	 *   <code> @Before(order = 1)</code>
	 * </pre>.
	 *
	 * @param scenario the scenario
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Before(order = 1)
	public void startScenario(Scenario scenario) throws IOException {
		// Non-Browser Initializations like SSH,Logger etc
		Hooks.scenario = scenario;
		ConfigFileHandler.loadJarCongFile(Hooks.class);
		ConfigFileHandler.loadConfigFile("src/it/resources");
		LogHandler.startTestCase(scenario.getSourceTagNames().toString(), scenario.getName());
		LogHandler.info("Pre-Hook of non browser Initializations..");
		isBrowserNeeded = false;
	}

	/**
	 * Invoke browser.
	 * <pre>
	 * <b> Gherkin </b>
	 *   <code>@Before(value = "~@BROWSER_NOT_NEEDED", order = 2)</code>
	 * </pre>
	 */
//	@Before(value = "~@BROWSER_NOT_NEEDED", order = 2)
//	public void invokeBrowser(){
//		// Browser Initializations
//		LogHandler.info("Pre-Hook of browser Initializations..");
//		SeleniumUtilities.openBrowser();
//
//	}

	/**
	 * Teardown browser.
	 * <pre>
	 * <b> Gherkin </b>
	 *   <code>@After(value = "~@BROWSER_NOT_NEEDED", order = 2)</code>
	 * </pre>
	 * @param scenario the scenario
	 */
	//@After(value = "~@BROWSER_NOT_NEEDED", order = 2)
	@After(order = 2)
	public void teardownBrowser(Scenario scenario) {
		if (SeleniumUtilities.getDriver() != null)
		{
		// Browser TearDowns
		LogHandler.info("Browser Teardowns in post-hook order 2..");
		if (scenario.isFailed()) {
			try {
				final byte[] screenshot = SeleniumUtilities.takeScreenshot();
				scenario.embed(screenshot, "image/png");
			} catch (Exception e) {
				// TODO: handle exception
				try {
					scenario.embed(SeleniumUtilities.captureScreen(),
							"image/png");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					LogHandler.warn("Unable to Capture the Screeenshot");
				}
			}

		}
		SeleniumUtilities.getDriver().quit();
		}
	}

	/**
	 * End scenario.
	 * <pre>
	 * <b> Gherkin </b>
	 *   <code>@After()</code>
	 * </pre>
	 * @param scenario the scenario
	 */
	@After()
	public void endScenario(Scenario scenario) {
		// Non-Browser TearDowns if any goes here..
		LogHandler.info("Non browser Teardowns in post-hook..");
		SSHManager.teardownAllConnections();
		LogHandler.endTestCase(scenario.getSourceTagNames().toString(), scenario.getName(),
				scenario.getStatus().toUpperCase());

	}
}
