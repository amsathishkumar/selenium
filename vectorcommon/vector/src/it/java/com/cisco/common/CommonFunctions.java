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
 * @Project: LMS
 * @Author: amsathishkumar
 * @Version:
 * @Description:
 * @Date created: Oct 8, 2015
 */
package com.cisco.common;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.cisco.oss.foundation.directory.LookupManager;
import com.cisco.oss.foundation.directory.ServiceDirectory;
import com.cisco.oss.foundation.directory.entity.ServiceInstance;
import com.cisco.oss.foundation.directory.impl.DirectoryServiceClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import com.sat.spvgt.utils.configfilehandler.ConfigFileHandlerManager;
import com.sat.spvgt.utils.logging.LogHandler;

// TODO: Auto-generated Javadoc
/**
 * The Class CommonFunctions.
 */
public class CommonFunctions {
	
	/** The misc config file handler. */
	ConfigFileHandlerManager miscConfigFileHandler = new ConfigFileHandlerManager();

	/**
	 * Json to linked has map.
	 *
	 * @param strReturn the str return
	 * @return the linked hash map
	 */
	public LinkedHashMap<String, String> jsonToLinkedHasMap(String strReturn) {
		LinkedHashMap <String, String> lhm = new LinkedHashMap<String, String> ();
        Gson gson = new Gson();
		LinkedTreeMap<?, ?> result = gson.fromJson(strReturn , LinkedTreeMap.class);
		  Set<?> keys = result.keySet();
		   for (Iterator<?> i = keys.iterator(); i.hasNext();) {
		     Object key = i.next();
		     Object value = (Object) result.get(key);
		     
		     System.out.println(key + " <=> " + value);
		     String key1=(String)key;
		     String value1;
		     try{
		      value1=(String)value;
		     }
		     catch (ClassCastException e)
		     {
 	        	value1=value.toString();
		     }
		     lhm.put(key1, value1);
		   }
		return lhm;
	}

	/**
	 * File to string.
	 *
	 * @param fileName the file name
	 * @return the string
	 */
	public String fileToString(String fileName) {
		String strReturn = "";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e2) {

		}
		String sCurrentLine = null;

		try {
			while ((sCurrentLine = br.readLine()) != null) {
				strReturn = strReturn + sCurrentLine;

			}
		} catch (IOException e1) {

		}
		return strReturn.replace(" ", "");
	}
	
	/**
	 * Sd details.
	 *
	 * @param ivalue the ivalue
	 * @param sdIP the sd ip
	 * @return the string
	 */
	public String sdDetails(String ivalue, String sdIP) {
		// <<serviceName:cisco.vcs.pk-am:address>>
		LogHandler.info("inside: "
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		LogHandler.info("input:" + ivalue + sdIP);
		String[] isvalue = ivalue.split(":");
		String srvvalue = isvalue[1];
		String srvKey = isvalue[2];
		String srvKeyValue = null;
		System.out.println(srvvalue + srvKey);
		ServiceDirectory.getServiceDirectoryConfig().setProperty(
				DirectoryServiceClient.SD_API_SD_SERVER_PORT_PROPERTY, 2013);

		ServiceDirectory.getServiceDirectoryConfig().setProperty(
				DirectoryServiceClient.SD_API_SD_SERVER_FQDN_PROPERTY, sdIP);
		LookupManager Look1 = ServiceDirectory.getLookupManager();
		List<ServiceInstance> serviceinstancea = Look1.getAllInstances();
		for (ServiceInstance s : serviceinstancea) {

			if (isvalue[0].equals("serviceName")
					&& s.getServiceName().endsWith(srvvalue)) {
				switch (srvKey) {
				case "address":
					srvKeyValue = s.getAddress();
					break;
				case "port":
					srvKeyValue = Integer.toString(s.getPort());
					break;
				}
			}
		}
		return srvKeyValue;
	}

	
	/**
	 * Read service property.
	 *
	 * @param ServiceName the service name
	 */
	public void readServiceProperty(String ServiceName) {
		String srvPropertvalue;
		miscConfigFileHandler.loadPropertiesBasedonPropertyFileName("com.cisco.lms.lms");
		switch (ServiceName.trim()) {
		case "AC Manager":
			srvPropertvalue = "com.cisco.acmanager.acmanager";
			LogHandler.info(srvPropertvalue);
		
			break;
		case "PK_PSM":
			srvPropertvalue = "com.cisco.psm.psm";
			
			break;
			
		case "Service Manager":
			srvPropertvalue = "com.cisco.servivemanager.servicemanager";
			
			break;
		case "PCB":
			srvPropertvalue = "com.cisco.pcb.pcb";
			
			break;
		case "ESN":
			srvPropertvalue = "com.cisco.esn.esn";
			
			break;
			
		case "DTACS":
			srvPropertvalue = "com.cisco.dtacs.dtacs";
			
			break;
		case "SCB":
			srvPropertvalue = "com.cisco.downloadgroup.downloadgroup";
			
			break;
		case "VSRM":
			srvPropertvalue = "com.cisco.vsrm.vsrm";
			
			break;
		default:
			srvPropertvalue = "No Values";
			break;

		}
		
		if (srvPropertvalue.equals("No Values"))
			System.err.print(ServiceName.trim()+" Property file not found ");
		else
			{
			//readBundle(srvPropertvalue, System.getProperty("SD.IP"));			
			miscConfigFileHandler.loadPropertiesBasedonPropertyFileName(srvPropertvalue);
			}
	}
	
	/**
	 * Generate json string.
	 *
	 * @param Data the data
	 * @return the array list
	 */
	public ArrayList<String> generateJsonString(List<Map<String, String>> Data) {
		ArrayList<String> returnjsons = new ArrayList<String>();
		//Gson gson = new Gson();
		Gson gson =new GsonBuilder().disableHtmlEscaping().create();
		for (Map<String, String> Datavalue : Data) {
			String ee = gson.toJson(Datavalue);
			ee = strToJson(ee);
			returnjsons.add(ee);

		}
		return returnjsons;
	}
	
	public ArrayList<String> generatexmlString(List<Map<String, String>> Data) {
		ArrayList<String> returnxml = new ArrayList<String>();
		try
		{
			 DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			 DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			 Document doc = dBuilder.newDocument();
		
			
			int i = 0 ; 
			
			for(Map<String,String> attribute: Data)
			{
				Element First = doc.createElement("m")  ; 
				Element Second = doc.createElement("m");			
				for(Entry<String,String> Header  : attribute.entrySet())
				{
				//String[] arr  = Header.split(",");
					String key = Header.getKey();
					String value = Header.getValue();
					
					if(key.contains("^^"))
					{
						String a = key.replace("^^", "");
						First  = doc.createElement(a) ;	
						doc.appendChild(First);
					    Second = First ; 
					}
					
					
					else if (key.contains("^"))
					{
						String c = key.replace("^", "");
						Element Current = doc.createElement(c);	
						Current.appendChild(doc.createTextNode(value));						
						Second.appendChild(Current);
					}
					else
					{
						Element Current = doc.createElement(key);
						Current.appendChild(doc.createTextNode(value));
						First.appendChild(Current);
						Second = Current;
					}
				
				}
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);		
			StringWriter outWriter = new StringWriter();
			StreamResult result = new StreamResult(outWriter);
			transformer.transform(source, result);
			StringBuffer sb = outWriter.getBuffer(); 
			String finalstring = sb.toString();
			System.out.println("GeneratexmlString: " + finalstring);
			returnxml.add(finalstring);

		}catch(Exception e )
		{
			System.out.println("Exception occured " + e.getMessage());
		}
		return returnxml;
	}

	
	/**
	 * Str to json.
	 *
	 * @param strJson the str json
	 * @return the string
	 */
	public  String strToJson(String strJson) {
		strJson = strJson.replace("\"{", "{");
		strJson = strJson.replace("}\"", "}");
		strJson = strJson.replace("\\", "");
		strJson = strJson.replace("\"[", "[");
		strJson = strJson.replace("]\"", "]");
		return strJson;
	}
}