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
package com.cisco.spvgt.utils.validation;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;

import com.cisco.spvgt.utils.logging.LogHandler;

import cucumber.api.DataTable;

/**
 * The Class Validate.
 */
public class Validate {

	/**
	 * Pingip.
	 *
	 * @param ip the ip
	 */
	public void pingip(String ip){
		LogHandler.info("pingip(String ip)");
		try {
			boolean pingstatus;
			InetAddress inet = InetAddress.getByName(ip);
			pingstatus = inet.isReachable(3000);			    
			if (!pingstatus)
			{
				LogHandler.warn("ip is not reachable");	
				System.exit(1);
			}
			else
				LogHandler.info(ip+ " is reachable");

		}
		catch (UnknownHostException e) {
			LogHandler.warn("invalid value to "+ip+" ip variable...");
			System.exit(1);
		}
		catch (Exception e) {
			LogHandler.warn("ping Exception catched");
		}

	}

	/**
	 * Read_system_variable.
	 *
	 * @param svar the svar
	 * @return the string
	 */
	public  String readsystemvariable(String svar) {	
		try 
		{   String tsvar=svar;
		if (svar.startsWith("<<") && svar.endsWith(">>") )
		{	
			svar=svar.substring(2,svar.length()-2);
		}

		svar=System.getProperty(svar);
		if(svar==null)
			throw new IOException(tsvar);
		}
		catch (Exception e)
		{
			LogHandler.warn( "Error in reading System Variable"+e.getMessage());

		}
		return svar;

	}


	/**
	 * Gets the _system_var.
	 *
	 * @param Dvalue the dvalue
	 * @return the _system_var
	 */
	public  List<Map<String, String>> readsystemvariable(DataTable Dvalue)
	{
		LogHandler.info("readsystemvariable(DataTable Dvalue)");
		List<Map<String, String>> stvalue = Dvalue.asMaps(String.class,String.class);
		LogHandler.info("Entering into read properties");
		 List<Map<String, String>> list = new ArrayList<Map<String, String>>();		 
		 System.out.print(Dvalue.getGherkinRows().size());
		 LogHandler.info("Entering into read properties 2");
		 for(int i=0;i<(Dvalue.getGherkinRows().size() -1);i++)
		 {
			 Map<String,String> map = new HashMap<String,String>();
			 for(String svrvalue:Dvalue.topCells())
			 {
				
			     String val=stvalue.get(i).get(svrvalue);
			     	
			     final String REGEX = "<<(.*?)>>";
				   Pattern p = Pattern.compile(REGEX);
			       Matcher m = p.matcher(val);
			       
				    if (m.find())
        			//if (val.startsWith("<<") && val.endsWith(">>") )
        			{
        				val=readsystemvar(val);		
        
        			}
        		
        			map.put(svrvalue,val);
        			LogHandler.warn( "Data Table column:"+svrvalue+ " Value: "+val);
        			
			 }
		
			 list.add(map);
		System.out.println(list.toString());
		 }

		return list;

	}

	public  List<Map<String, String>> readsystemvariablePersistOrder(DataTable Dvalue)
	{
		LogHandler.info("readsystemvariable(DataTable Dvalue)");
		List<Map<String, String>> stvalue = Dvalue.asMaps(String.class,String.class);
		LogHandler.info("Entering into read properties");
		 List<Map<String, String>> list = new ArrayList<Map<String, String>>();		 
		 System.out.print(Dvalue.getGherkinRows().size());
		 LogHandler.info("Entering into read properties 2");
		 for(int i=0;i<(Dvalue.getGherkinRows().size() -1);i++)
		 {
			 Map<String,String> map = new LinkedHashMap<String,String>();
			 for(String svrvalue:Dvalue.topCells())
			 {
				
			     String val=stvalue.get(i).get(svrvalue);
			     	
			     final String REGEX = "<<(.*?)>>";
				   Pattern p = Pattern.compile(REGEX);
			       Matcher m = p.matcher(val);
			       
				    if (m.find())
        			//if (val.startsWith("<<") && val.endsWith(">>") )
        			{
        				val=readsystemvar(val);		
        
        			}
        		
        			map.put(svrvalue,val);
        			LogHandler.warn( "Data Table column:"+svrvalue+ " Value: "+val);
        			
			 }
		
			 list.add(map);
		System.out.println(list.toString());
		 }

		return list;
	}

	
	
	
	public  String readsystemvar(String svar) {	

	//	return replaceSystemVar(svar);
		
		final String REGEX = "<<(.*?)>>";
		// String INPUT =
		// "http://<<10.78.203.118>>/dncs/<<iiiii>>/<<iii>>/ddsd/<<rrr>>";
		Pattern p = Pattern.compile(REGEX);
		Matcher m = p.matcher(svar); // get a matcher object
		String value = null;
	//	System.out.println("count" + m.groupCount() + svar);
		if (m.find())
			return replaceSystemVar(svar);
		else {

			try {
				value = System.getProperty(svar);
				if (value == null)
					throw new IOException(value);

			} catch (Exception e) {
				System.err.append("Error in reading System Variable" + svar
						+ e.getMessage());
			}
		}
		return value;

	}
	
	
	public String replaceSystemVar(String INPUT) {
		final String REGEX = "<<(.*?)>>";
	   // String INPUT = "http://<<10.78.203.118>>/dncs/<<iiiii>>/<<iii>>/ddsd/<<rrr>>";
       Pattern p = Pattern.compile(REGEX);
       Matcher m = p.matcher(INPUT); // get a matcher object
     //  int count = 0;

       while(m.find()) {
//         count++;
//         System.out.println("Match number "+count);
//         System.out.println("start(): "+m.start());
//         System.out.println("end(): "+m.end());
         
         String q = INPUT.substring(m.start() , m.end());
       // System.out.println(q);
         String value =q;
         
         value = value.replace("<<", "");
		 value = value.replace(">>", "");
		 String tmpvalue=value;
		 try{
		     value=System.getProperty(value);
			if(value==null)
			   throw new IOException(value);
		}
		catch (Exception e)
		{
			LogHandler.warn( "Error in reading System Variable"+tmpvalue+e.getMessage());
			value=tmpvalue;
		}
         INPUT=INPUT.replace(q, value);
       // System.out.println(INPUT);
         m = p.matcher(INPUT);
      }
	return INPUT;
	}
	
	
	public void compareListMap(List<LinkedHashMap<String, String>> list,
			List<LinkedHashMap<String, String>> dbdata) {
		LogHandler
				.info("testdatacomparedbdata(List<Map<String, String>> testdata, List<Map<String, String>> dbdata)");
		LogHandler.info("Test data table" + list.toString());
		LogHandler.info("DB data table" + dbdata.toString());

		if (list.size() != dbdata.size()) {
			LogHandler
					.info("Test Data value " + list.toString()
							+ "are not equal with DB data value : "
							+ dbdata.toString());
			Assert.assertTrue("Test Data value " + list.toString()
					+ "are not equal with DB data value:" + dbdata.toString(),
					false);

		}

		Iterator<LinkedHashMap<String, String>> listITR = list.iterator();
		Iterator<LinkedHashMap<String, String>> dbITR = dbdata.iterator();
		while (listITR.hasNext() && dbITR.hasNext()) {
			Object value1 = listITR.next();
			Object value2 = dbITR.next();
			//if (value1.equals(value2)) {
				LogHandler.info("Test Data value " + value1
						+ "is not equal DB data value : " + value2);
				Assert.assertTrue("Test Data value " + value1
						+ "is not equal DB data value : " +value2 + value1.toString().equals(value2.toString()), value1.toString().equals(value2.toString()));
			//}

		}
	}
//	public List<Map<String, String>> StringtoArrayListLinkedMap(
//			String verfiyString) {
//		List<Map<String, String>> s2 = new ArrayList<Map<String, String>>();
//		String[] verf = verfiyString.split("~");
//		for (String sk : verf) {
//			String[] element = sk.split(",");
//			LinkedHashMap<String, String> LM = new LinkedHashMap<String, String>();
//			for (String sk1 : element) {
//				String[] element1 = sk1.split("=");
//				if (element1.length > 0)
//					LM.put(element1[0], element1[1]);
//
//			}
//			s2.add(LM);
//		}
//		return s2;
//	}
	
	public List<LinkedHashMap<String, String>> mergerDBvalueHead(List<String> dbHeader,
			List<List<String>> dbValues) {

		List<LinkedHashMap<String, String>> s2 = new ArrayList<LinkedHashMap<String, String>>();

		for (List<String> sl : dbValues) {
			Iterator<String> vl = sl.iterator();
			Iterator<String> HLV = dbHeader.iterator();
			LinkedHashMap<String, String> LM = new LinkedHashMap<String, String>();
			while (vl.hasNext() && HLV.hasNext()) {
				LM.put(HLV.next(), vl.next());
			}
			s2.add(LM);
		}
		return s2;
	}

	public List<LinkedHashMap<String, String>> StringtoArrayListLinkedMap(
			String verfiyString) {
		List<LinkedHashMap<String, String>> s2 = new ArrayList<LinkedHashMap<String, String>>();
		String[] verf = verfiyString.split("~");
		for (String sk : verf) {
			String[] element = sk.split(",");
			LinkedHashMap<String, String> LM = new LinkedHashMap<String, String>();
			for (String sk1 : element) {
				String[] element1 = sk1.split("=");
				if (element1.length > 0)
				{
					String temVal=element1[1];
					if (temVal.trim().startsWith("<<") || temVal.trim().startsWith(">>"))
						temVal=readsystemvar(temVal);
					LM.put(element1[0], element1[1]);
				}
	
			}
			s2.add(LM);
		}
		return s2;
	}
	

}
