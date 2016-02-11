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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.cisco.spvgt.utils.nosqldb.NosqlManager;
import com.cisco.spvgt.utils.nosqldb.NosqlType;
import com.cisco.spvgt.utils.validation.Validate;
import com.cisco.vcse.automation.utils.datatype.DBQueryResult;
import com.cisco.vcse.automation.utils.datatype.DatabaseType;
import com.cisco.vcse.automation.utils.db.DBClient;
import com.cisco.vcse.automation.utils.shell.SSHClient;

import junit.framework.Assert;

// TODO: Auto-generated Javadoc
/**
 * The Class CommonInformixDBFunctions.
 */
public class CommonInformixDBFunctions {

	/** The misc validate. */
	private Validate miscValidate = new Validate();

	/**
	 * Informix configuration.
	 */
	public void informixConfiguration() {
		String dbconip = miscValidate.readsystemvariable("DB.CON.IP");
		String dbconpwd = miscValidate.readsystemvariable("DB.CON.PWD");
		String dbconuser = miscValidate.readsystemvariable("DB.CON.USER");
		String dbcontype = miscValidate.readsystemvariable("DB.CON.TYPE");
	
		System.out.println(dbconip+dbconpwd+dbconuser);
		if (dbcontype.equalsIgnoreCase("esn")) {
			String conpvtip= miscValidate.readsystemvariable("DB.CON.PRIVATE.IP");
			SSHClient ssh = new SSHClient.Builder().withHostOrIPAddress(dbconip).withUserNameAndPassword(dbconuser, dbconpwd).build();
			ssh.executeAndReturnResponse("perl -pi -e 's/^DBSERVERALIASES.*/$&,auto_tcp/ if ( not /auto_tcp/ )' /opt/cisco/informix/server/etc/onconfig");
			ssh.executeAndReturnResponse("if [ `grep -c auto_tcp /opt/cisco/informix/server/etc/sqlhosts` -eq 1 ] ; then echo sqlhostPASS ; else echo 'auto_tcp onsoctcp " + conpvtip
					+ " sqlexec' >> /opt/cisco/informix/server/etc/sqlhosts; fi");
			ssh.executeAndReturnResponse(". /dvs/dncs/bin/dncsSetup && onmode -P start auto_tcp");
		} else if (dbcontype.equalsIgnoreCase("dtacs")) {
			SSHClient ssh = new SSHClient.Builder().withHostOrIPAddress(dbconip).withUserNameAndPassword(dbconuser, dbconpwd).build();
			ssh.executeAndReturnResponse("perl -pi -e 's/^DBSERVERALIASES.*/$&,auto_tcp/ if ( not /auto_tcp/ )' /export/home/informix/etc/onconfig");
			ssh.executeAndReturnResponse("if [ `grep -c auto_tcp /export/home/informix/etc/sqlhosts` -eq 1 ] ; then echo sqlhostPASS ; else echo 'auto_tcp ontlitcp " + dbconip + " informixOnline' >> /export/home/informix/etc/sqlhosts; fi");
			ssh.executeAndReturnResponse(". /dvs/dtacs/bin/dtacsSetup && onmode -P start auto_tcp");
		}
	}

	/**
	 * Verify informix db.
	 *
	 * @param Testdata the testdata
	 */
	public void verifyInformixDB(Map<String, String> Testdata) {
		String strQuery;
		if(Testdata.containsKey("CONDITION"))
			strQuery = "Select " + Testdata.get("FIELDLIST") + " from " + Testdata.get("TABLENAME") + " where " + Testdata.get("CONDITION");
		else
			strQuery = "Select " + Testdata.get("FIELDLIST") + " from " + Testdata.get("TABLENAME");
			List<LinkedHashMap<String, String>> s2 = informixSearch(strQuery);
			if (Testdata.get("VERIFICATION").contains("txcount")) {
				int txcount = s2.size();
				String[] ver = Testdata.get("VERIFICATION").split("=");
				int vert = Integer.parseInt(ver[1]);
				Assert.assertTrue("Test Data count: " + Testdata.get("VERIFICATION") + " is not matched with DB :" + txcount, txcount == vert);
		}

	}

	/**
	 * Delete informix db.
	 *
	 * @param Testdata the testdata
	 */
	public boolean deleteInformixDB(Map<String, String> Testdata) {
		String strQuery = "Delete  from " + Testdata.get("TABLENAME") + " where " + Testdata.get("CONDITION");
		boolean delete = informixDelete(strQuery);
		//Assert.assertTrue("Record is not deleted", delete);
		return delete;
	}

	/**
	 * Informix search.
	 *
	 * @param strQuery the str query
	 * @return the list
	 */
	private List<LinkedHashMap<String, String>> informixSearch(String strQuery) {
		System.out.println("sathish" + System.getProperty("DB.URL").trim() + System.getProperty("DB.USER").trim() + System.getProperty("DB.PWD").trim());
		DBClient dbClient = new DBClient(DatabaseType.Informix, System.getProperty("DB.URL").trim(), System.getProperty("DB.USER").trim(), System.getProperty("DB.PWD").trim());

		DBQueryResult dbQueryResult = dbClient.executeSelect(strQuery);

		List<String> dbHeader = dbQueryResult.getColumnHeaders();
		List<List<String>> dbValues = dbQueryResult.getRowData();

		List<LinkedHashMap<String, String>> s2 = miscValidate.mergerDBvalueHead(dbHeader, dbValues);
		return s2;
	}

	/**
	 * Informix delete.
	 *
	 * @param strQuery the str query
	 * @return the booelan
	 */
	private boolean informixDelete(String strQuery) {
		boolean delete;
		System.out.println("sathish" + System.getProperty("DB.URL").trim() + System.getProperty("DB.USER").trim() + System.getProperty("DB.PWD").trim());
		DBClient dbClient = new DBClient(DatabaseType.Informix, System.getProperty("DB.URL").trim(), System.getProperty("DB.USER").trim(), System.getProperty("DB.PWD").trim());
		int dbQueryResult = dbClient.executeInsertUpdateOrDelete(strQuery);
		if(String.valueOf(dbQueryResult).equalsIgnoreCase("1"))
			delete = true;
		else
			delete = false;
		System.out.println("Delete value is "+ dbQueryResult);
		return delete;

	}

	/**
	 * Store informix values.
	 *
	 * @param Testdata the testdata
	 * @return the list
	 */
	public List<LinkedHashMap<String, String>> storeInformixValues(Map<String, String> Testdata) {

		String strQuery=null;
		if(Testdata.containsKey("CONDITION"))
			strQuery = "Select " + Testdata.get("STORE") + " from " + Testdata.get("TABLENAME") + " where " + Testdata.get("CONDITION");
		else
		 strQuery = "Select " + Testdata.get("STORE") + " from " + Testdata.get("TABLENAME");
		List<LinkedHashMap<String, String>> storeDBMerger = informixSearch(strQuery);
		return storeDBMerger;
	}

	/**
	 * Store db values.
	 *
	 * @param Testdata the testdata
	 * @param storeDBmerge the store d bmerge
	 */
	public void storeDBValues(Map<String, String> Testdata, List<LinkedHashMap<String, String>> storeDBmerge) {
		System.out.println("Query store values");
		for (LinkedHashMap<String, String> storeva : storeDBmerge) {
			for (Map.Entry<String, String> me : storeva.entrySet()) {
				System.out.println("Storing......." + me.getKey() + me.getValue());
				String storevars[] = Testdata.get("STORE").split(",");

				for (String storevar : storevars) {
					if (me.getKey().trim().equals(storevar)) {

						if (me.getValue() != null) {
							System.setProperty("t" + storevar, me.getValue());

							System.out.println("t" + storevar + " = " + me.getValue() + "....Done");
						} else {
							System.setProperty("t" + storevar, me.getValue() == null ? "null" : "novalues");
							System.out.println("t" + storevar + " = " + me.getValue() == null ? "null" : "novalues" + "....Done");
						}

					}

				}

			}
		}
	}

	/**
	 * Mongo search.
	 *
	 * @param strQuery the str query
	 * @return the list
	 */
	private List<LinkedHashMap<String, String>> mongoSearch(String strQuery) {
		NosqlManager nosqlManager = new NosqlManager(NosqlType.Mongo, System.getProperty("DB.URL"));		
		List<LinkedHashMap<String, String>> mongodbDBvalues = nosqlManager.executeNosqlSearch(strQuery);
		return mongodbDBvalues;
	}





}
