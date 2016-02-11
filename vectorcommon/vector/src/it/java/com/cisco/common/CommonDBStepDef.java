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

import com.cisco.spvgt.utils.validation.MiscExpception;
import com.cisco.spvgt.utils.validation.Validate;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;

// TODO: Auto-generated Javadoc
/**
 * The Class CommonDBStepDef.
 */
public class CommonDBStepDef {
	
	/** The cmongodbf. */
	CommonMongoDBFunctions cmongodbf = new CommonMongoDBFunctions();
	
	/** The coracledbf. */
	CommonOracleDBFunctions coracledbf = new CommonOracleDBFunctions();
	
	/** The cinformix. */
	CommonInformixDBFunctions cinformix = new CommonInformixDBFunctions();
	
	/** The csd. */
	CommonStepDef csd = new CommonStepDef();
	
	Validate miscValidate = new Validate();
	/**
	 * Very_the_db_ validation.
      * <pre>
      * <b> Gherkin </b>
      *   <code> And verfiy the "<Service>" db for Validation
      *      |FIELDLIST       |TABLENAME            |CONDITION               | VERIFICATION         |STORE |
|     *       SOURCE_ID,DESCRIPTION,HD_FLAG,IS_DTA_SOURCE,HIGH_VALUE_CONTENT    |SOURCE               |SOURCE_ID = '<SOURCEID>'|  SOURCE_ID=<SOURCEID>,DESCRIPTION=<SOURCENAME>,HD_FLAG=1,IS_DTA_SOURCE=0,HIGH_VALUE_CONTENT=0|AC_REF|
      * </code>
      * </pre>.
	 * @param ServiceName the service name
	 * @param Data the data
	 */
	@Given("^verfiy the \"(.*?)\" db for Validation$")
	public void very_the_db_Validation(String ServiceName, DataTable Data) {
		csd.getPropertyValues(ServiceName);
	//	List<Map<String, String>> Datavalue = Data.asMaps(String.class, String.class);
		List<Map<String, String>> Datavalue = miscValidate.readsystemvariable(Data);
		
		for (Map<String, String> Testdata : Datavalue) {
			switch (System.getProperty("DB.TYPE")) {
			case "INFORMIX":
				cinformix.informixConfiguration();				
				cinformix.verifyInformixDB(Testdata);
				if (Testdata.get("STORE") != null) {
					List<LinkedHashMap<String, String>> storeDBMerger = cinformix.storeInformixValues(Testdata);
					cinformix.storeDBValues(Testdata, storeDBMerger);
				}
				break;
			case "ORACLE":
				coracledbf.verifyOracleDB(Testdata);
				if (Testdata.get("STORE") != null) {
					List<LinkedHashMap<String, String>> storeDBmerge =coracledbf.storeOracleValues(Testdata);
					coracledbf.storeDBValues(Testdata, storeDBmerge);
				}
				break;
			case "MONGODB":
				System.out.println("inside mongodb");
				cmongodbf.verifyMongoDB(Testdata);
				if (Testdata.get("STORE") != null) {
					List<LinkedHashMap<String, String>> storeDBvalues = cmongodbf.storeMongoValues(Testdata);
					cmongodbf.storeDBValues(Testdata, storeDBvalues);
				}
				break;
			default:
				System.err.println("DB.TYPE variable is missing");
				break;
			}
		}

	}

	/**
	 * Delete from DB.
	 *
	 * @param ServiceName the service name
	 * @param Data the data
	 */
	@Given("^Delete below tables in \"(.*?)\" db$")
	public void delete_record(String ServiceName, DataTable Data) {
		csd.getPropertyValues(ServiceName);
		List<Map<String, String>> Datavalue = Data.asMaps(String.class, String.class);
		for (Map<String, String> Testdata : Datavalue) {
			switch (System.getProperty("DB.TYPE")) {
			case "INFORMIX":
				cinformix.informixConfiguration();				
				cinformix.deleteInformixDB(Testdata);
				break;
			case "ORACLE":
				coracledbf.deleteOracleDB(Testdata);
				break;
			case "MONGODB":
				//cmongodbf.verifyMongoDB(Testdata);
				System.out.println("Test data"+ Testdata);
				cmongodbf.deleteMongoDB(Testdata);
				break;
			default:
				System.err.println("DB.TYPE variable is missing");
				break;
			}
		}

	}

	@And("^Store \"(.*?)\" db field values$")
	public void store_db_field_values(String ServiceName, DataTable Data){
		csd.getPropertyValues(ServiceName);		
		List<Map<String, String>> Datavalue = miscValidate.readsystemvariable(Data);
		
		for (Map<String, String> Testdata : Datavalue) {
			switch (System.getProperty("DB.TYPE")) {
			case "INFORMIX":
				cinformix.informixConfiguration();				
				if (Testdata.get("STORE") != null) {
					List<LinkedHashMap<String, String>> storeDBMerger = cinformix.storeInformixValues(Testdata);
					cinformix.storeDBValues(Testdata, storeDBMerger);
				}
				break;
			case "ORACLE":
				if (Testdata.get("STORE") != null) {
					List<LinkedHashMap<String, String>> storeDBmerge =coracledbf.storeOracleValues(Testdata);
					coracledbf.storeDBValues(Testdata, storeDBmerge);
				}
				break;
			case "MONGODB":
				System.out.println("inside mongodb");
				if (Testdata.get("STORE") != null) {
					List<LinkedHashMap<String, String>> storeDBvalues = cmongodbf.storeMongoValues(Testdata);
					cmongodbf.storeDBValues(Testdata, storeDBvalues);
				}
				break;
			default:
				System.err.println("DB.TYPE variable is missing");
				break;
			}
		}
		
	}
}
