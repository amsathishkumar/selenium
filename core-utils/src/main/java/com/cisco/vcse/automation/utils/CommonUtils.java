/**
 * Copyright (c) 2015 by Cisco Systems, Inc.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Cisco Systems,  ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Cisco Systems.
 *
 *
 * @Project: Utils
 * @Author: smuniapp
 * @Version: 
 * @Description:  
 * @Date created: Apr 9, 2015
 */

package com.cisco.vcse.automation.utils;

import com.cisco.vcse.automation.utils.logging.LogHandler;

// TODO: Auto-generated Javadoc
/**
 * This is the main class that deals with initiating WebDriver, opening of a
 * browser.
 * 
 */
public class CommonUtils {
	/**
	 * Wait/Pause Execution.
	 *
	 * @param milliSeconds the milliSeconds
	 * @param autowait the autowait
	 */
	public static void wait(int milliSeconds,Autowait autowait) {
		LogHandler.info("Waiting for " + milliSeconds + " milliseconds");
		try {
			int offset = 0;
			if(autowait !=null){ 
				try {
					offset = Integer.parseInt(System
							.getProperty(autowait.getOffset()));
				} catch (NumberFormatException e) {
					LogHandler
					.warn("Failed to retrieve valid value from property file.."+autowait.getOffset());
				}
			}
			Thread.sleep(offset + milliSeconds );
		} catch (InterruptedException e) {
			LogHandler.warn("Failed to wait for " + milliSeconds + " seconds");
		}
	}
}
