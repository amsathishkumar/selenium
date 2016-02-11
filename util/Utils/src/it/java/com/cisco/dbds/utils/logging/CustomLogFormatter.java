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
/*
 * Class that defines the Format of the Log being logged in File
 */
package com.cisco.dbds.utils.logging;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

// TODO: Auto-generated Javadoc
/**
 * The Class CustomLogFormatter.
 */
class CustomLogFormatter extends Formatter {
	//
	// Create a DateFormat to format the logger timestamp.
	//
	/** The Constant df. */
	private static final DateFormat df = new SimpleDateFormat(
			"dd-MM-yyyy hh:mm:ss");

	/* (non-Javadoc)
	 * @see java.util.logging.Formatter#format(java.util.logging.LogRecord)
	 */
	public String format(LogRecord record) {
		StringBuilder builder = new StringBuilder(1000);
		builder.append(df.format(new Date(record.getMillis())));
		// builder.append("[").append(record.getSourceClassName()).append(".");
		// builder.append(record.getSourceMethodName()).append("] - ");
		// builder.append("[").append(record.getLevel()).append("] - ");
		builder.append(" " + record.getLevel() + " ");
		builder.append(formatMessage(record));
		builder.append("\n");
		return builder.toString();
	}

	/* (non-Javadoc)
	 * @see java.util.logging.Formatter#getHead(java.util.logging.Handler)
	 */
	public String getHead(Handler h) {
		return super.getHead(h);
	}

	/* (non-Javadoc)
	 * @see java.util.logging.Formatter#getTail(java.util.logging.Handler)
	 */
	public String getTail(Handler h) {
		return super.getTail(h);
	}

}
