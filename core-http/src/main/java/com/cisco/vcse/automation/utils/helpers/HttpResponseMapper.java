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
 * HttpResponseMapper maps HttpResponse to custom response i.e CoreResponse
 *
 *
 * @Project: core-HTTP
 * @Author: kosk
 * @Version: 1.0.0.0
 * @Description:  HttpResponseMapper maps HttpResponse to custom response i.e CoreResponse
 * @Date updated: 6/16/2015
 */

package com.cisco.vcse.automation.utils.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;

import com.cisco.vcse.automation.utils.datatype.CoreResponse;
import com.cisco.vcse.automation.utils.datatype.CoreRuntimeException;



public class HttpResponseMapper {

	private final static String CLASS_NAME = "HttpResponseMapper: ";	

	/**
	 * Maps the Http Response fields into custom core response
	 * @param response : CloseableHttpResponse
	 * @return CoreResponse
	 * @throws IOException : response close failure
	 */
	public static CoreResponse mapToCoreResponse(final CloseableHttpResponse  response) throws IOException {
		final String METHOD_NAME = "parseResponse(): ";
		
		final CoreResponse coreResponse = new CoreResponse();
		try{
			// Load the Response Status Code from the Response
			coreResponse.setRespStatus(response.getStatusLine().getStatusCode());
			
			// Load the Response Headers from the Response
			final Header[] headers = response.getAllHeaders();
			for(Header header : headers){
				coreResponse.getResponseHeaders().put(header.getName(), header.getValue());
			}
			// Load the Message Body contents from the Response
			if (response.getEntity() != null ) {
				
				try(BufferedReader reader = new BufferedReader(
						new InputStreamReader(response.getEntity().getContent()));) {				
					StringBuilder sb = new StringBuilder();
				    String line;
				    boolean isFirstLineReadOver = false;
				    while ((line = reader.readLine()) != null) {
				        if(isFirstLineReadOver){
				        	sb.append(System.getProperty("line.separator"));
				        	isFirstLineReadOver=true;
				        }
				        sb.append(line);
				    }
				    coreResponse.setRespMsgBody(sb.toString());
				} catch (IOException exp) {				
					throw new CoreRuntimeException(exp,CLASS_NAME + METHOD_NAME + exp.getMessage());
				}
			} else {
				coreResponse.setRespMsgBody(null);
			}
		}finally{
			response.close();
		}
		
		return coreResponse;
	}
}
