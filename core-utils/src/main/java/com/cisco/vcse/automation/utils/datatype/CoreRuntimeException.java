/**
 * Copyright (c) $2015 by Cisco Systems, Inc.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Cisco Systems,  ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Cisco Systems.
 * 
 * Custom Runtime exception
 *
 *
 * @Project: core-HTTP
 * @Author: kosk
 * @Version: 1.0.0.0
 * @Description:  Custom Runtime exception
 * @Date updated: 6/3/2015
 */

package com.cisco.vcse.automation.utils.datatype;

public class CoreRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -3735698112188287823L;
	private Exception exp;
	
	public CoreRuntimeException(final Exception originalException, final String message){
		super(message);
		this.exp = originalException;
	}
	public CoreRuntimeException(final String message){
		super(message);
	}

	public String getMessage(){
		if(exp == null){
			return super.getMessage();
		}else{
			return  exp.getClass().getName() +", "+exp.getMessage(); 
		}
	}
	public String toString(){
		if(exp == null){
			return super.toString();
		}else{
			return exp.getClass().getName() +", "+exp.toString();
		}
	}	
}
