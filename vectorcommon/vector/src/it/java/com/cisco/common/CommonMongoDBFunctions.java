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

import junit.framework.Assert;

// TODO: Auto-generated Javadoc
/**
 * The Class CommonMongoDBFunctions.
 */
public class CommonMongoDBFunctions {

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
	 * Verify mongo db.
	 *
	 * @param Testdata the testdata
	 */
	public void verifyMongoDB(Map<String, String> Testdata) {
		String strQuery = Testdata.get("FIELDLIST") + "`" + Testdata.get("TABLENAME") + "`" + Testdata.get("CONDITION");
		System.out.println(" the query is " + strQuery);
		List<LinkedHashMap<String, String>> mongodbDBvalues = mongoSearch(strQuery);
	//	boolean mongodbDBvalues1 = mongoDelete(strQuery);
		if (Testdata.get("VERIFICATION").contains("txcount")) {
			int txcount = mongodbDBvalues.size();
			String[] ver = Testdata.get("VERIFICATION").split("=");
			int vert = Integer.parseInt(ver[1]);
			Assert.assertTrue("Test Data count: " + Testdata.get("VERIFICATION") + " is not matched with DB :" + txcount, txcount == vert);
		} else {

			List<LinkedHashMap<String, String>> mongoVerifyvalues = miscValidate.StringtoArrayListLinkedMap(Testdata.get("VERIFICATION"));
			miscValidate.compareListMap(mongoVerifyvalues, mongodbDBvalues);
		}
	}
	

	/**
	 * Store mongo values.
	 *
	 * @param Testdata the testdata
	 * @return the list
	 */
	public List<LinkedHashMap<String, String>> storeMongoValues(Map<String, String> Testdata) {
		String strQuery= Testdata.get("STORE") + "`" + Testdata.get("TABLENAME") + "`" + Testdata.get("CONDITION");
		System.out.println("str" + strQuery);
		List<LinkedHashMap<String, String>> storeDBvalues = mongoSearch(strQuery);
		return storeDBvalues;
	}

	/**
	 * Delete from Mongo db.
	 *
	 * @param Testdata the testdata
	 */
	public void deleteMongoDB(Map<String, String> Testdata) {
		System.out.println("inside mongo delete");
		String strQuery = Testdata.get("FIELDLIST") + "`" + Testdata.get("TABLENAME") + "`" + Testdata.get("CONDITION");
		System.out.println("the strQuery is " + strQuery);
	//	boolean delete = mongoDelete(strQuery);
	 //   Assert.assertTrue("Record is not deleted", delete);

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
		System.out.println(" the queryresult  is " + mongodbDBvalues);
		return mongodbDBvalues;
	}


	/**
	 * Mongo delete.
	 *
	 * @param strQuery the str query
	 * @return the booelan
	 */
/*	private boolean mongoDelete(String strQuery) {
		
		NosqlManager nosqlManager = new NosqlManager(NosqlType.Mongo, System.getProperty("DB.URL"));	
		
		List<LinkedHashMap<String, String>> mongodbDBvalues = nosqlManager.deletemongo(strQuery);LogHandler.info("Query: " + strQuery + " with URL:" + System.getProperty("DB.URL") + " User:" + System.getProperty("DB.USER") + " PWD:" + System.getProperty("DB.PWD"));
		return true;


	}   */


}
