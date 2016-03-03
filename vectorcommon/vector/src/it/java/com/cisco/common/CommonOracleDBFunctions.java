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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.sat.spvgt.utils.logging.LogHandler;
import com.sat.spvgt.utils.validation.Validate;
import com.sat.vcse.automation.utils.datatype.DBQueryResult;
import com.sat.vcse.automation.utils.datatype.DatabaseType;
import com.sat.vcse.automation.utils.db.DBClient;

import junit.framework.Assert;

// TODO: Auto-generated Javadoc
/**
 * The Class CommonOracleDBFunctions.
 */
public class CommonOracleDBFunctions {
	
	/** The misc validate. */
	private Validate miscValidate = new Validate();

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
	 * Verify oracle db.
	 *
	 * @param Testdata the testdata
	 */
	public void verifyOracleDB(Map<String, String> Testdata) {

		String strQuery = "Select " + Testdata.get("FIELDLIST") + " from " + Testdata.get("TABLENAME") + " where " + Testdata.get("CONDITION");
		List<LinkedHashMap<String, String>> s2 = oracleSearch(strQuery);

		if (Testdata.get("VERIFICATION").contains("txcount")) {
			int txcount = s2.size();
			String[] ver = Testdata.get("VERIFICATION").split("=");
			int vert = Integer.parseInt(ver[1]);
			Assert.assertTrue("Test Data count: " + Testdata.get("VERIFICATION") + " is not matched with DB :" + txcount, txcount == vert);

		} else {
			List<LinkedHashMap<String, String>> s3 = miscValidate.StringtoArrayListLinkedMap(Testdata.get("VERIFICATION"));

			miscValidate.compareListMap(s3, s2);
		}
	}

	/**
	 * Verify oracle db.
	 *
	 * @param Testdata the testdata
	 */
	public void deleteOracleDB(Map<String, String> Testdata) {
		String strQuery = "Delete  from " + Testdata.get("TABLENAME") + " where " + Testdata.get("CONDITION");
		boolean delete = oracleDelete(strQuery);
		Assert.assertTrue("Record is not deleted", delete);

	}
	/**
	 * Store oracle values.
	 *
	 * @param Testdata the testdata
	 * @return the list
	 */
	public List<LinkedHashMap<String, String>> storeOracleValues(Map<String, String> Testdata) {
	
		String strQuery=null;
		if(Testdata.containsKey("CONDITION"))
			strQuery = "Select " + Testdata.get("STORE") + " from " + Testdata.get("TABLENAME") + " where " + Testdata.get("CONDITION");
		else
		 strQuery = "Select " + Testdata.get("STORE") + " from " + Testdata.get("TABLENAME");
		List<LinkedHashMap<String, String>> storeDBMerger = oracleSearch(strQuery);
		return storeDBMerger;
			
	}

	/**
	 * Oracle search.
	 *
	 * @param strQuery the str query
	 * @return the list
	 */
	private List<LinkedHashMap<String, String>> oracleSearch(String strQuery) {

		DBClient dbClient = new DBClient(DatabaseType.Oracle, System.getProperty("DB.URL"), System.getProperty("DB.USER"), System.getProperty("DB.PWD"));

		LogHandler.info("Query: " + strQuery + " with URL:" + System.getProperty("DB.URL") + " User:" + System.getProperty("DB.USER") + " PWD:" + System.getProperty("DB.PWD"));
		DBQueryResult dbQueryResult = dbClient.executeSelect(strQuery);

		List<String> dbHeader = dbQueryResult.getColumnHeaders();
		List<List<String>> dbValues = dbQueryResult.getRowData();

		List<LinkedHashMap<String, String>> s2 = miscValidate.mergerDBvalueHead(dbHeader, dbValues);
		return s2;
	}
	
	/**
	 * Oracle delete.
	 *
	 * @param strQuery the str query
	 * @return the booelan
	 */
	private boolean oracleDelete(String strQuery) {
		boolean delete;
		DBClient dbClient = new DBClient(DatabaseType.Oracle, System.getProperty("DB.URL"), System.getProperty("DB.USER"), System.getProperty("DB.PWD"));
		LogHandler.info("Query: " + strQuery + " with URL:" + System.getProperty("DB.URL") + " User:" + System.getProperty("DB.USER") + " PWD:" + System.getProperty("DB.PWD"));
		int dbQueryResult = dbClient.executeInsertUpdateOrDelete(strQuery);
		if(String.valueOf(dbQueryResult).equalsIgnoreCase("1"))
			delete = true;
		else
			delete = false;
		System.out.println("Delete value is "+ dbQueryResult);
		return delete;

	}

}
