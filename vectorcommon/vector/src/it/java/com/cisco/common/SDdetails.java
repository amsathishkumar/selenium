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
 * @Project: LMS
 * @Author: smuniapp
 * @Version:
 * @Description:
 * @Date created: Oct 8, 2015
 */
package com.cisco.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SDdetails {

	/**
	 * @param args
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	static String htmlfname = "./sathish.txt";
	static PrintWriter htmlfile;
	static String sathish="<tr>";
	
	public static void main(String[] args) throws ClientProtocolException, IOException {


	}

	public void printSD(String s)
	{
    	try {
			sdStatus(s);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String pp[] = sathish.split("<tr>");
		for (String oo : pp) {
			String ps=oo.replaceAll("<td>", "");
			ps=ps.replaceAll("</td>", "");
			ps=ps.replaceAll("</tr>", "");
//ps=ps.replaceAll("serviceName :", "serviceName:");
ps=ps.replaceAll("address :", "      address:");
//ps=ps.replaceAll("id :", "id:");
		   System.out.println("Process "+ps);	 
		}
		
	}
	public static void sdStatus(String SDip) throws IOException, FileNotFoundException, ClientProtocolException {
		new FileOutputStream(htmlfname).close();
		htmlfile = new PrintWriter(new BufferedWriter(new FileWriter(htmlfname, true)));

		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet("http://"+SDip+ ":2013/service");

		HttpResponse response = httpClient.execute(getRequest);

		BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

		String output;
		String q = null;
		System.out.println("SD details .... \n");
		while ((output = br.readLine()) != null) {
			q = output;
		//	System.out.println(output);
		}
		httpClient.getConnectionManager().shutdown();
		parserGson(q);
		//htmlfile.close();
	}

	private static void parserGson(String q) {
		JsonElement jelement = new JsonParser().parse(q);
		if (jelement.isJsonArray()) {
			prArray(jelement);
		} else
			prObject(jelement);
	}

	private static void prArray(JsonElement jelement) {
	//	System.out.println("------------------------Array---------------------------");

		JsonArray jarray = jelement.getAsJsonArray();
		for (JsonElement jobjecte : jarray) {
			if (jobjecte.isJsonObject()) {
				prObject(jobjecte);
			} else if (jobjecte.isJsonArray()) {
				prArray(jobjecte);
			} else {
				System.out.println("sathish");
			}

		}
	}

	private static void prObject(JsonElement jelement) {
	//	System.out.println("------------------------Start Object---------------------------");
		if (jelement.isJsonObject()) {
			JsonObject jj = jelement.getAsJsonObject();
			for (Entry<String, JsonElement> a : jj.entrySet()) {
				if (a.getValue().isJsonObject()) {
					JsonElement jelement1 = a.getValue();
					String s = "  sat  " + a.getKey() + " :" + a.getValue().toString();
					// System.out.println(s);
					// htmlfile.print(s);
					prObject(jelement1);
				} else if (a.getValue().isJsonArray()) {
					prArray(a.getValue());
				} else {
					String s = a.getKey() + " : " + a.getValue().toString();

					if(s.startsWith("status") )
						sathish=sathish+"</tr> <tr>";
					if (s.startsWith("id") || s.startsWith("status")  || s.startsWith("address")) {
						//System.out.println(s);
						sathish=sathish+"                 <td>"+s.replaceAll(" ","")+"</td>";
						//htmlfile.println(s);
					}
				}

			}
		}
		//System.out.println("------------------------End Object---------------------------");
	}
}
