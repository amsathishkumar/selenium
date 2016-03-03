
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
package com.sat.spvgt.utils.logging;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class LogHandler.
 */
public class LogHandler {

	/** The log. */
	private static Logger log;

	/** The method info. */
	private static String methodInfo = null;

	/** The is logging enabled. */
	private static boolean isLoggingEnabled;

	static {

		log = Logger.getLogger(LogHandler.class.getName());
	}

	/**
	 * Start test case.
	 *
	 * @param scenarioTags the scenario tags
	 * @param scenarioName the scenario name
	 */
	public static void startTestCase(String scenarioTags, String scenarioName) {
		printScenarioName(scenarioTags, scenarioName, "Started");
	}

	/**
	 * End test case.
	 *
	 * @param scenarioTags the scenario tags
	 * @param scenarioName the scenario name
	 * @param status the status
	 */
	public static void endTestCase(String scenarioTags, String scenarioName,
			String status) {
		printScenarioName(scenarioTags, scenarioName, status);
		printToConsole("\n\n\n");
	}

	/**
	 * Prints the scenario name.
	 *
	 * @param scenarioTags the scenario tags
	 * @param testCaseName the test case name
	 * @param status the status
	 */
	public static void printScenarioName(String scenarioTags,
			String testCaseName, String status) {

		String formattedLine = "";

		String formattedScenarioTags = ">  Scenario tags   : " +
				scenarioTags;
		String formattedScenarioName = ">  Scenario Name   : "
				+ testCaseName.toUpperCase();
		String formattedStatus = ">  Scenario Status : " + status ;

		if(formattedScenarioTags.length() > formattedScenarioName.length()) {
			formattedLine = StringUtils.leftPad("",
					formattedScenarioTags.length() + 2, '=');

			formattedScenarioTags = StringUtils.rightPad(formattedScenarioTags,
					(formattedScenarioTags.length()  + 1) , " ")  + "<";

			formattedScenarioName = StringUtils.rightPad(formattedScenarioName,
					(formattedScenarioTags.length() - 1) , " ")  + "<";

			formattedStatus = StringUtils.rightPad(formattedStatus,
					(formattedScenarioTags.length() - 1) , " ")  + "<";

		} else {
			formattedLine = StringUtils.leftPad("",
					formattedScenarioName.length() + 2, '=');

			formattedScenarioTags = StringUtils.rightPad(formattedScenarioTags,
					(formattedScenarioName.length() + 1) , " ")  + "<";

			formattedScenarioName = StringUtils.rightPad(formattedScenarioName,
					(formattedScenarioName.length() + 1) , " ")  + "<";

			formattedStatus = StringUtils.rightPad(formattedStatus,
					(formattedScenarioName.length() - 1) , " ")  + "<";
		}


		if (isLoggingEnabled) {
			log.info(formattedLine);
			log.info(formattedScenarioTags);
			log.info(formattedScenarioName);
			log.info(formattedStatus);
			log.info(formattedLine);
		}
		printToConsole(formattedLine + "\n" +
				formattedScenarioTags + "\n" +
				formattedScenarioName + "\n" +
				formattedStatus + "\n" +
				formattedLine);
	}

	/**
	 * Info.
	 *
	 * @param message the message
	 */
	public static void info(String message) {
		methodInfo = getCurrentMethodInfo();
		if (isLoggingEnabled) {
			log.log(Level.INFO, methodInfo + message);
		}
		printToConsole(methodInfo + message);
	}

	/**
	 * Warn.
	 *
	 * @param message the message
	 */
	public static void warn(String message) {
		methodInfo = getCurrentMethodInfo();
		if (isLoggingEnabled) {
			log.log(Level.WARNING, methodInfo + message);
		}
		printToConsole(methodInfo + message);
	}

	/**
	 * Error.
	 *
	 * @param message the message
	 */
	public static void error(String message) {
		methodInfo = getCurrentMethodInfo();
		if (isLoggingEnabled) {
			log.log(Level.SEVERE, methodInfo + message);
		}
		printToConsole(methodInfo + message);
	}

	/**
	 * In Stack trace the first element will be the current object invoked(which
	 * is always the logger object) The first element will be the caller object.
	 *
	 * @return the current method info
	 */
	private static String getCurrentMethodInfo() {
		Throwable t = new Throwable();
		StackTraceElement[] elements = t.getStackTrace();
		// String calleeMethod = elements[0].getMethodName();
		String callerMethodName = elements[3].getMethodName();
		String[] classNames = elements[3].getClassName().split("\\.");
		String callerClassName = classNames[classNames.length - 1];
		return callerClassName + ": " + callerMethodName + "(): ";
	}

	/**
	 * Prints the to console.
	 *
	 * @param message the message
	 */
	public static void printToConsole(Object message) {
		System.out.println(message.toString());
	}

	/**
	 * Prints the to console.
	 */
	public static void printToConsole() {
		printToConsole("\n");
	}

	/**
	 * The main method.
	 *
	 * @param srgs the arguments
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void main(String[] srgs) throws IOException {

	}
}
