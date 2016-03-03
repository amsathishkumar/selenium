/**
 * Copyright (c) 2015 by SAT Systems, Inc.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of SAT Systems,  ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with SAT Systems.
 *
 *
 * @Project: Utils
 * @Author: amsathishkumar
 * @Version: 
 * @Description:  
 * @Date created: Apr 9, 2015
 */
package com.cisco.dbds.utils.selenium;

// TODO: Auto-generated Javadoc
/**
 * The Enum Autowait.
 */
public enum Autowait {

	/** The dtacswui. */
	dtacswui("dtacs.delay.offset"),/** The vcswui. */
	vcswui("vcs.delay.wuioffset"),/** The vcswuissh. */
	vcswuissh("vcs.delay.sshoffset");

	/** The offset. */
	private String offset;

	/**
	 * Instantiates a new autowait.
	 *
	 * @param v the v
	 */
	Autowait(String v) {
		offset = v;
	}

	/**
	 * Gets the offset.
	 *
	 * @return the offset
	 */
	String getOffset() {
		return offset;
	} 
}
