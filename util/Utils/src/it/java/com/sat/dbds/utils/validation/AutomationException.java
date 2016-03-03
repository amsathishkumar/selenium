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
package com.sat.dbds.utils.validation;

/**
 * The Class AutomationException.
 */
public class AutomationException extends Exception {

	/** The message. */
	private String message = null;

	/**
	 * Instantiates a new automation exception.
	 */
	public AutomationException() {
		super();
	}

	/**
	 * Instantiates a new automation exception.
	 *
	 * @param message the message
	 */
	public AutomationException(String message) {
		super(message);
		this.message = message;
	}

	/**
	 * Instantiates a new automation exception.
	 *
	 * @param cause the cause
	 */
	public AutomationException(Throwable cause) {
		super(cause);
	}

	/* (non-Javadoc)
	 * @see java.lang.Throwable#toString()
	 */
	@Override
	public String toString() {
		return message;
	}

	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		return message;
	}
}
