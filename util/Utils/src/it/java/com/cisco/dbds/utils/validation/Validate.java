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
package com.cisco.dbds.utils.validation;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;

import com.cisco.dbds.utils.logging.LogHandler;

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
	public static void pingip(String ip){
		LogHandler.info("pingip(String ip)");
		try {
			boolean pingstatus;
			InetAddress inet = InetAddress.getByName(ip);
			pingstatus = inet.isReachable(3000);			    
			if (!pingstatus)
			{
				LogHandler.warn("ip is not reachable");	
				//System.exit(1);
			}
			else
				LogHandler.info(ip+ " is reachable");

		}
		catch (UnknownHostException e) {
			LogHandler.warn("invalid value to "+ip+" ip variable...");
			//System.exit(1);
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
	public static String readsystemvariable(String svar) {	
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
	public static List<Map<String, String>> readsystemvariable(DataTable Dvalue)
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
			     	
			     
        			if (val.startsWith("<<") && val.endsWith(">>") )
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

	public static String readsystemvar(String svar) {	
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
}
