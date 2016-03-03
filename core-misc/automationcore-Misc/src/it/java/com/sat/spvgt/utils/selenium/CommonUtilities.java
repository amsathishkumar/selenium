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

import java.util.Collection;


/**
 * The Class CommonUtilities.
 *
 * @author manogsub
 */
public class CommonUtilities {

	/**
	 * Prints the elements under collection.
	 *
	 * @param collection the collection
	 */
	public static void printElementsUnderCollection(Collection<?> collection) {
		for (Object object : collection) {
			System.out.println(object);
		}
	}
}
