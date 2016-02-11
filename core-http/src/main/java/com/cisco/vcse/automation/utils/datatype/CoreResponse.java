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
 * This class wraps the http response details so that the client code need not depend on Apache Http jars
 *
 *
 * @Project: core-HTTP
 * @Author: kosk
 * @Version: 1.0.0.0
 * @Description:  This class wraps the http response details so that the client code need not depend on Apache Http jars
 * @Date updated: 6/3/2015
 */

package com.cisco.vcse.automation.utils.datatype;

import java.util.HashMap;
import java.util.Map;

public class CoreResponse {

	// HTTP Response Headers
	private Map<String, String> responseHeaders = new HashMap<>();
	// HTTP Response Status
	private int respStatus;
	// HTTP Response Message Body Content
	private String respMsgBody;
	
	public Map<String, String> getResponseHeaders() {
		return responseHeaders;
	}
	public int getRespStatus() {
		return respStatus;
	}
	public void setRespStatus(int respStatus) {
		this.respStatus = respStatus;
	}
	public String getRespMsgBody() {
		return respMsgBody;
	}
	public void setRespMsgBody(String respMsgBody) {
		this.respMsgBody = respMsgBody;
	}
	
	public String toString(){
		
		return new StringBuilder().append("Status = ").append(getRespStatus()).append("\nHeaders :").append(getResponseHeaders()).
		append("\nBody :").append(getRespMsgBody()).toString();
	}
}
