package com.sat.vcse.automation.utils.datatype;

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
