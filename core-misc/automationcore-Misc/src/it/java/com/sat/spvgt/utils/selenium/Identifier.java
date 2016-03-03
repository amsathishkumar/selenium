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
package com.sat.spvgt.utils.selenium;

// TODO: Auto-generated Javadoc
/**
 * The Enum Identifier.
 */
public enum Identifier {

	/** The class name. */
	CLASS_NAME("className"), 

	/** The css. */
	CSS("cssSelector"), 

	/** The id. */
	ID("id"), 

	/** The link text. */
	LINK_TEXT("linkText"), 

	/** The name. */
	NAME("name"), 

	/** The partial link text. */
	PARTIAL_LINK_TEXT("partialLinkText"), 

	/** The tag name. */
	TAG_NAME("tagName"), 

	/** The xpath. */
	XPATH("xpath");

	/**
	 * Instantiates a new identifier.
	 *
	 * @param name the name
	 */
	Identifier(String name) {
		this.setName(name);
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name            the name to set
	 */
	private void setName(String name) {
		this.name = name;
	}

	/** The name. */
	private String name;

}
