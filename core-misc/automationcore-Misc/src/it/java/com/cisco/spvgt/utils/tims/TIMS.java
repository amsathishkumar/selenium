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
package com.cisco.spvgt.utils.tims;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.Scanner;

import org.apache.http.HttpResponse;

import com.cisco.spvgt.utils.configfilehandler.ConfigFileHandlerManager;

// TODO: Auto-generated Javadoc
/**
 * The Class TIMS.
 */
public class TIMS {

	/** The url. */
	private static String url = "";

	/** The xml. */
	private static String xml = "";

	/** The tr id. */
	private static String trID = "";

	/** The credentials. */
	private static LinkedHashMap<String, String> credentials = new LinkedHashMap<String, String>();

	/** The xml file name. */
	private static String xmlFileName = "c:\\sattims.xml"; // Don't changes
	// Log file details
	/** The fname. */
	private static String fname = "c:\\htmltoTIMS.txt";

	/** The outlog. */
	private static PrintWriter outlog;

	/** The rs. */
	private static RestfulIFCommonFunctions rs = new RestfulIFCommonFunctions();

	/** The xmlf. */
	private static XMLFunctions miscXMLFunctions = new XMLFunctions();

	// Input Parameter
	/** The file name. */
	private static String fFileName = "c:\\customreport.html";

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 * @throws Exception
	 *             the exception
	 */
	public static void main(String args[]) throws Exception {

		ConfigFileHandlerManager miscConfigFileHandler = new ConfigFileHandlerManager();
		miscConfigFileHandler.loadConfigFileBasedOnPath("src/it/resources");
		new FileOutputStream(fname).close();
		outlog = new PrintWriter(
				new BufferedWriter(new FileWriter(fname, true)));
		// updatetimsresult("Ttv9629533c", "passed");

		// initializeTIMSParameters(
		// System.getProperty("tims.userID").trim(),
		// System.getProperty("tims.projectID").trim(),
		// System.getProperty("tims.configID").trim(),
		// System.getProperty("tims.token").trim(),
		// System.getProperty("tims.sw").trim(),
		// System.getProperty("tims.platform").trim(),
		// System.getProperty("tims.browsertype").trim(),
		// System.getProperty("tims.browserversion").trim()
		// );
		//
		// postTIMSsearch("Ttv9629533c");

		updateHTMLtoTIMSresults();
		// ArrayList<String> re1 = new ArrayList<String>();
		// re1 = readhtmlfilePass();
		// System.out.println(re1.size()+" bvvv "+re1);

		// createtimsresult("Tl4352381c", "failed","sample111555");
	}

	/**
	 * Initialize tims parameters.
	 * 
	 * @param userID
	 *            the user id
	 * @param projectID
	 *            the project id
	 * @param configID
	 *            the config id
	 * @param token
	 *            the token
	 * @param sw
	 *            the sw
	 * @param platform
	 *            the platform
	 * @param browsertype
	 *            the browsertype
	 * @param browserversion
	 *            the browserversion
	 * @return true, if successful
	 */
	public static boolean initializeTIMSParameters(String userID,
			String projectID, String configID, String token, String sw,
			String platform, String browsertype, String browserversion) {
		try {
			boolean status = true;
			credentials.put("userID", userID);
			credentials.put("token", token);
			credentials.put("projectID", projectID);
			credentials.put("configID", configID);
			credentials.put("sw", sw);
			credentials.put("platform", platform);
			credentials.put("brwtype", browsertype);
			credentials.put("brwversion", browserversion);
			if (userID == null || userID == "") {
				status = false;
				System.out.println("UserID shouldn't be null or blank.");
				return status;
			}
			if (token == null || token == "") {
				status = false;
				System.out
						.println("Automation Token shouldn't be null or blank.");
				return status;
			}

			if (projectID == null || projectID == "") {
				status = false;
				System.out.println("ProjectID shouldn't be null or blank.");
				return status;
			}
			if (configID == null || configID == "") {
				status = false;
				System.out.println("ConfigID shouldn't be null or blank.");
				return status;
			}
			return status;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Generate tims xml file.
	 */
	public static void generateTimsXMLFile() {
		try {
			String fileName = xmlFileName;
			File outFile = new File(fileName);
			FileWriter out = new FileWriter(outFile);
			out.write(xml);
			out.close();
			xml = "";
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load properties.
	 * 
	 * @param FileName
	 *            the file name
	 * @return the properties
	 */
	public static Properties loadProperties(String FileName) {
		try {
			Properties property = new Properties();
			FileInputStream in = new FileInputStream(FileName);
			property.load(in);
			in.close();
			return property;
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Buildsearchxml.
	 * 
	 * @param cred
	 *            the cred
	 * @param testCaseID
	 *            the test case id
	 * @return true, if successful
	 */
	public static boolean buildsearchxml(LinkedHashMap<String, String> cred, String testCaseID) {
		boolean status = true;
		String configID = cred.get("configID").toString();
		try {
			if (configID == null || configID == "") {
				status = false;
				System.out.println("configID shouldn't be null or blank.");
			}
			if (testCaseID == null || testCaseID == "") {
				status = false;
				System.out.println("Test Case ID shouldn't be null or blank");
			}
			xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n";
			xml = xml
					+ "<Tims xsi:schemaLocation=\"http://tims.cisco.com/namespace http://tims.cisco.com/xsd/Tims.xsd\"\n";
			xml = xml + "xmlns=\"http://tims.cisco.com/namespace\"\n";
			xml = xml
					+ "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n";
			xml = xml + "xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n";
			xml = xml + "<ResultList>\n";
			xml = xml + "<ResultRange>last</ResultRange>\n";
			xml = xml + "<CaseID>" + testCaseID + "</CaseID>\n";
			xml = xml + "<ConfigID>" + configID + "</ConfigID>\n";
			xml = xml + "</ResultList>\n";
			xml = xml + "</Tims>";
			return status;
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
			return status;
		}
	}

	/**
	 * Buildupdatexml.
	 * 
	 * @param cred
	 *            the cred
	 * @param result
	 *            the result
	 * @return true, if successful
	 */
	public static boolean buildupdatexml(LinkedHashMap<String, String> cred, String result) {

		boolean status = true;

		String configID = cred.get("configID").toString();
		String usrID = cred.get("userID").toString();
		String tokenv = cred.get("token").toString();
	//	String sw = cred.get("sw").toString();
		String platform = cred.get("platform").toString();
		String brwtype = cred.get("brwtype").toString();
		String brwversion = cred.get("brwversion").toString();
		System.out.println("browser details:" + brwtype + brwversion);
		try {

			if (configID == null || configID == "") {
				status = false;
				System.out.println("configID shouldn't be null or blank.");
			}
			if (trID == null || trID == "") {
				status = false;
				System.out.println("Test Result ID shouldn't be null or blank");
			}
			if (result == null || result == "") {
				status = false;
				System.out.println("Result shouldn't be null or blank");
			}
			if (!(result.equals("passed") | result.equals("failed")

			| result.equals("blocked") | result.equals("dropped")

			| result.equals("passx") | result.equals("pending"))) {

				System.out.println("Invalid Status value");
				return false;
			}

			xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n";
			xml = xml
					+ "<Tims xsi:schemaLocation=\"http://tims.cisco.com/namespace http://tims.cisco.com/xsd/Tims.xsd\"\n";
			xml = xml + "xmlns=\"http://tims.cisco.com/namespace\"\n";
			xml = xml
					+ "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n";
			xml = xml + "xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n";
			xml = xml + "<Credential user=\"" + usrID + "\" token=\"" + tokenv
					+ "\"/>\n";
			xml = xml + "<ID>" + trID + "</ID>\n";

			xml = xml + "<ListFieldValue editoperator=\"set\">\n";
			xml = xml + "<FieldName>Status</FieldName>\n";
			xml = xml + "<Value>" + result + "</Value>\n";
			xml = xml + "</ListFieldValue>\n";

			xml = xml + "<ListFieldValue editoperator=\"set\">\n";
			xml = xml + "<FieldName>Tested by</FieldName>\n";
			xml = xml + "<Value>" + usrID + "</Value>\n";
			xml = xml + "</ListFieldValue>\n";

			/*
			 * xml=xml+"<ListFieldValue editoperator=\"set\">\n";
			 * xml=xml+"<FieldName>Software Version</FieldName>\n";
			 * xml=xml+"<Value>"+sw+"</Value>\n"; xml=xml+"</ListFieldValue>\n";
			 */

			xml = xml + "<ListFieldValue editoperator=\"set\">\n";
			xml = xml + "<FieldName>Platform</FieldName>\n";
			xml = xml + "<Value>" + platform + "</Value>\n";
			xml = xml + "</ListFieldValue>\n";

			xml = xml + "<TextFieldValue editoperator=\"set\">\n";
			xml = xml + "<FieldName>Browser Type</FieldName>\n";
			xml = xml + "<Value>" + brwtype + "</Value>\n";
			xml = xml + "</TextFieldValue>\n";

			xml = xml + "<TextFieldValue editoperator=\"set\">\n";
			xml = xml + "<FieldName>Browser Version</FieldName>\n";
			xml = xml + "<Value>" + brwversion + "</Value>\n";
			xml = xml + "</TextFieldValue>\n";

			xml = xml + "</Tims>\n";
			return status;

		} catch (Exception e) {

			e.printStackTrace();

			status = false;

			return status;

		}

	}

	/**
	 * Buildcreatexml.
	 * 
	 * @param cred
	 *            the cred
	 * @param testCaseID
	 *            the test case id
	 * @param result
	 *            the result
	 * @param title
	 *            the title
	 * @return true, if successful
	 */
	public static boolean buildcreatexml(LinkedHashMap<String, String> cred, String testCaseID,
			String result, String title) {

		boolean status = true;
		String userID = cred.get("userID").toString();
		String automationToken = cred.get("token").toString();
	//	String projectID = cred.get("projectID").toString();
		String configID = cred.get("configID").toString();

		try {
			if (userID == null || userID == "") {
				status = false;
				System.out.println("userID shouldn't be null or blank.");
			}
			if (automationToken == null || automationToken == "") {
				status = false;
				System.out
						.println("Automation Token shouldn't be null or blank.");
			}
			if (testCaseID == null || testCaseID == "") {
				status = false;
				System.out.println("Test Case ID shouldn't be null or blank");
			}
			if (configID == null || configID == "") {
				status = false;
				System.out.println("configID shouldn't be null or blank.");
			}

			xml = xml + "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n";
			xml = xml
					+ "<Tims xsi:schemaLocation=\"http://tims.cisco.com/namespace http://tims.cisco.com/xsd/Tims.xsd\"\n";
			xml = xml + "xmlns=\"http://tims.cisco.com/namespace\"\n";
			xml = xml
					+ "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n";
			xml = xml + "xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n";
			xml = xml + "<Credential user=\"" + userID + "\" token=\""
					+ automationToken + "\"/>\n";
			xml = xml + "<Result xlink:href=\"http://tims.cisco.com/xml/"
					+ testCaseID + "/entity.svc\">\n";

			xml = xml + "<Title><![CDATA[" + title + "]]></Title>\n";
			xml = xml + "<ListFieldValue multi-value=\"false\">\n";
			xml = xml + "<FieldName><![CDATA[Tested by]]>\n";
			xml = xml + "</FieldName>\n";
			xml = xml + "<Value><![CDATA[" + userID + "]]>\n";
			xml = xml + "</Value>\n";
			xml = xml + "<Value><![CDATA[shisuren]]>\n";
			xml = xml + "</Value>\n";
			xml = xml + "</ListFieldValue>\n";

			xml = xml + "<Owner>\n";
			xml = xml + "<UserID>\n" + userID + "\n</UserID>\n";
			xml = xml + "<Email>\n" + userID + "@cisco.com" + "\n</Email>\n";
			xml = xml + "</Owner>\n";
			xml = xml + "<Status>" + result + "</Status>\n";
			xml = xml + "<ConfigID xlink:href=\"http://tims.cisco.com/xml/"
					+ configID + "/entity.svc\">" + configID + "</ConfigID>\n";
			xml = xml + "<CaseID xlink:href=\"http://tims.cisco.com/xml/"
					+ testCaseID + "/entity.svc\">" + testCaseID
					+ "</CaseID>\n";
			xml = xml + "</Result>\n";
			xml = xml + "</Tims>";

			return status;

		} catch (Exception e) {

			e.printStackTrace();

			status = false;

			return status;

		}

	}

	/**
	 * Updatetimsresult.
	 * 
	 * @param tcid
	 *            the tcid
	 * @param status
	 *            the status
	 */
	public static void updatetimsresult(String tcid, String status) {
		boolean result;
		result = initializeTIMSParameters(System.getProperty("tims.userID")
				.trim(), System.getProperty("tims.projectID").trim(), System
				.getProperty("tims.configID").trim(),
				System.getProperty("tims.token").trim(),
				System.getProperty("tims.sw").trim(),
				System.getProperty("tims.platform").trim(),
				System.getProperty("tims.browsertype").trim(), System
						.getProperty("tims.browserversion").trim());

		System.out.println(System.getProperty("tims.browsertype").trim()
				+ System.getProperty("tims.browserversion").trim());
		try {
			if (!postTIMSsearch(tcid)) {
				System.out.println("Test ResultID:" + trID + "\n Result:"
						+ result + "\n");
			} else {
				System.out.println("Test ResultID:" + trID + "\n Result:"
						+ result + "\n");
				System.out.println("update result" + postTIMSupdate(status));
				outlog.print(trID);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Createtimsresult.
	 * 
	 * @param tcid
	 *            the tcid
	 * @param status
	 *            the status
	 * @param title
	 *            the title
	 */
	public static void createtimsresult(String tcid, String status, String title) {
		boolean result;
		result = initializeTIMSParameters(System.getProperty("tims.userID")
				.trim(), System.getProperty("tims.projectID").trim(), System
				.getProperty("tims.configID").trim(),
				System.getProperty("tims.token").trim(),
				System.getProperty("tims.sw").trim(),
				System.getProperty("tims.platform").trim(),
				System.getProperty("tims.browsertype").trim(), System
						.getProperty("tims.browserversion").trim());

		try {
			if (!postTIMScreate(tcid, status, title)) {
				System.out.println("TResult:" + result + "\n");
			} else {
				System.out.println("Result:" + result + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Post tim ssearch.
	 * 
	 * @param testCaseID
	 *            the test case id
	 * @return true, if successful
	 * @throws Exception
	 *             the exception
	 */
	public static boolean postTIMSsearch(String testCaseID) throws Exception {
		try {
			boolean status = true;
			String tc = null;
			String sw = credentials.get("sw").toString();
			if (credentials.size() == 0) {
				status = false;
				System.out
						.println("Sorry!! , Please initialize TIMS parameters before posting the result.");
				return status;
			}
			String projectID = credentials.get("projectID").toString();
			if (projectID == null || projectID == "") {
				status = false;
				System.out.println("projectID shouldn't be null or blank");
				return status;
			}

			if (testCaseID == null || testCaseID == "") {
				status = false;
				System.out.println("Test Case ID shouldn't be null or blank");
				return status;
			}

			url = "http://tims.cisco.com/xml/" + projectID + "/result-list.svc";
			if (!buildsearchxml(credentials, testCaseID)) {
				return false;
			}
			generateTimsXMLFile();

			String strXMLFilename = miscXMLFunctions.convertXMLFileToString(xmlFileName);

			HashMap<String, String> contentType = new HashMap<String, String>();
			contentType.put("Content-type", "text/xml; charset=ISO-8859-1");

			HttpResponse res = rs.getRESTAPIResponse(url, "POST",
					strXMLFilename, "http", contentType);

			int statusCode = RestfulIFCommonFunctions.readStatusCode(res);
			if (statusCode == 200) {
				String a = rs.readContent(res);
				System.out.println("Upload complete, response=" + a);
				String b[] = a.split("\n");
				int found = 0;
				if (b.length > 7 && a.contains("<LogicalID>")) {
					tc = b[8].trim();
					System.out.println("sathis" + tc);
					tc = tc.substring("<LogicalID>".length(), tc.length()
							- "</LogicalID>".length());
					System.out.println("test Result id" + tc);
					// System.out.println("software version" +b[93]);
					for (int sat = 0; sat <= b.length - 1; sat++)
					// for (int sat=0 ;sat<=100;sat++)
					{
						String sw1 = "<Value><![CDATA[" + sw + "]]></Value>";
						if ((b[sat].trim()).equalsIgnoreCase(sw1))
						// if (b[sat].contains(sw))
						{
							found = 1;
							System.out.println("software version" + b[sat]);
						}
					}

					if (found == 1) {
						trID = tc;
						outlog.print(trID);
					} else {

						outlog.println(sw + "are not mtached");
						return false;

					}
				} else {
					outlog.println("no response string matched");
					return false;
				}

			} else {
				System.out.println("Upload failed, response=" + statusCode);
				return false;
			}
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * Post tim supdate.
	 * 
	 * @param result
	 *            the result
	 * @return true, if successful
	 * @throws Exception
	 *             the exception
	 */
	public static boolean postTIMSupdate(String result) throws Exception {
		try {
			boolean status = true;
			if (credentials.size() == 0) {
				status = false;
				System.out
						.println("Sorry!! , Please initialize TIMS parameters before posting the result.");
				return status;
			}

			if (result == null || result == "") {
				status = false;
				System.out.println("Result shouldn't be null or blank.");
				return status;
			}

			String projectID = credentials.get("projectID").toString();
			if (projectID == null || projectID == "") {
				status = false;
				System.out.println("projectID shouldn't be null or blank");
				return status;
			}

			url = "http://tims.cisco.com/xml/" + projectID
					+ "/entity-list/edit.svc";
			if (!buildupdatexml(credentials, result)) {
				return false;
			}
			generateTimsXMLFile();
			String strXMLFilename = miscXMLFunctions.convertXMLFileToString(xmlFileName);

			HashMap<String, String> contentType = new HashMap<String, String>();
			contentType.put("Content-type", "text/xml; charset=ISO-8859-1");

			HttpResponse res = rs.getRESTAPIResponse(url, "POST",
					strXMLFilename, "http", contentType);

			int statusCode = RestfulIFCommonFunctions.readStatusCode(res);
			if (statusCode == 200) {
				String a = rs.readContent(res);
				System.out.println("Upload complete, response=" + a);
				if (a.contains("Error")) {
					System.out.println("Staus: Fail: " + a);
					outlog.println(", Staus: Fail: " + a);
				} else {
					System.out.println("Staus: Pass");
					outlog.println(", Status: Pass ");
				}
			} else {
				System.out.println("Upload failed, response=" + statusCode);
				return false;
			}
			return true;

		} catch (Exception e) {

			e.printStackTrace();

			return false;
		}

	}

	/**
	 * Post tim screate.
	 * 
	 * @param testCaseID
	 *            the test case id
	 * @param result
	 *            the result
	 * @param title
	 *            the title
	 * @return true, if successful
	 * @throws Exception
	 *             the exception
	 */
	public static boolean postTIMScreate(String testCaseID, String result,
			String title) throws Exception {
		try {
			boolean status = true;
			if (credentials.size() == 0) {
				status = false;
				System.out
						.println("Sorry!! , Please initialize TIMS parameters before posting the result.");
				return status;
			}

			if (result == null || result == "") {
				status = false;
				System.out.println("Result shouldn't be null or blank.");
				return status;
			}

			String projectID = credentials.get("projectID").toString();
			if (projectID == null || projectID == "") {
				status = false;
				System.out.println("projectID shouldn't be null or blank");
				return status;
			}

			url = "http://tims.cisco.com/xml/" + projectID + "/entity.svc";

			if (!buildcreatexml(credentials, testCaseID, result, title)) {
				return false;
			}
			generateTimsXMLFile();

			// if (statusCode1 == HttpStatus.SC_OK) {
			String strXMLFilename = miscXMLFunctions.convertXMLFileToString(xmlFileName);

			HashMap<String, String> contentType = new HashMap<String, String>();
			contentType.put("Content-type", "text/xml; charset=ISO-8859-1");

			HttpResponse res = rs.getRESTAPIResponse(url, "POST",
					strXMLFilename, "http", contentType);

			int statusCode = RestfulIFCommonFunctions.readStatusCode(res);
			if (statusCode == 200) {
				String a = rs.readContent(res);
				System.out.println("Upload complete, response=" + a);
			} else {
				System.out.println("Upload failed, response=" + statusCode);
				return false;
			}

			return true;

		} catch (Exception e) {

			e.printStackTrace();

			return false;
		}

	}

	/**
	 * Update htm lto tim sresults.
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void updateHTMLtoTIMSresults() throws IOException {
		ArrayList<String> re = new ArrayList<String>();
		re = readhtmlfilePass();
		new FileOutputStream(fname).close();
		outlog = new PrintWriter(
				new BufferedWriter(new FileWriter(fname, true)));
		System.out.println("Tims Update started: " + re.size());
		outlog.println("Tims Update started");
		outlog.println("Total TIMS test case for update" + re.size());
		outlog.println(" S.no, Desc, TCID, TIMS report id, Update status");
		for (int i = 0; i < re.size(); i++) {
			String res = re.get(i);
			String[] tc = res.split("~");
			if (tc[0].startsWith("Ttv")) {
				if (tc.length > 1) {
					outlog.print(i + ", " + tc[0] + ", " + tc[1] + ", " + tc[2]
							+ ", ");
					updatetimsresult(tc[0], tc[2].toLowerCase());
				} else {
					outlog.print(res + "not updated");
				}
			}
		}
		System.out.println("Tims Update Ended");
		outlog.println("Tims Update Ended");
		outlog.close();
	}

	/**
	 * Readhtmlfile pass.
	 * 
	 * @return the array list
	 * @throws FileNotFoundException
	 *             the file not found exception
	 */
	public static ArrayList<String> readhtmlfilePass()
			throws FileNotFoundException {
		ArrayList<String> re = new ArrayList<String>();

		String fEncoding = "UTF-8";
		String tt = "";
		int fcount = 0;
		Scanner scanner = new Scanner(new FileInputStream(fFileName), fEncoding);
		try {
			while (scanner.hasNextLine()) {

				tt = scanner.nextLine();
				tt = tt.trim();

				if (tt.contains("Failed Test Case ID"))
					fcount = 1;

				if (fcount == 0 && tt.contains("Ttv")
						&& !(tt.contains("Precheck"))
						&& !(tt.contains("Precondition"))) {
					System.out.println("LINE read: " + tt);

					String a[] = tt.split("<td>");
					System.out.println("tcid: "
							+ a[3].substring(a[3].indexOf('T'), 19));
					System.out.println("Desc: "
							+ a[5].substring(0, a[5].indexOf("<")));
					System.out.println("status: "
							+ a[6].substring(a[6].indexOf(">") + 1,
									a[6].indexOf("d") + 1));

					String aa = a[3].substring(a[3].indexOf('T'), 19)
							+ "~"
							+ a[5].substring(0, a[5].indexOf("<"))
							+ "~"
							+ a[6].substring(a[6].indexOf(">") + 1,
									a[6].indexOf("d") + 1);
					re.add(aa);

				}

			}

		} finally {
			scanner.close();
		}
		return re;

	}

	/**
	 * Readhtmlfile fail.
	 * 
	 * @return the array list
	 * @throws FileNotFoundException
	 *             the file not found exception
	 */
	public static ArrayList<String> readhtmlfileFail()
			throws FileNotFoundException {
		ArrayList<String> re = new ArrayList<String>();
		String fEncoding = "UTF-8";
		String tt = "";
		int cc = 0;
		Scanner scanner = new Scanner(new FileInputStream(fFileName), fEncoding);
		try {
			while (scanner.hasNextLine()) {

				tt = scanner.nextLine();
				tt = tt.trim();

				if (tt.contains("FAILED TIMS TEST CASES")) {
					cc = 1;
				}
				if (tt.contains("FAILED NON-TIMS TEST CASE ERROR LIST")) {
					return re;
				}
				if (cc == 1 && tt.contains("Ttv") && !(tt.contains("Precheck"))
						&& !(tt.contains("Precondition"))) {
					int charCount = 0;
					int pos = 0;
					if (!tt.startsWith("Ttv")) {
						for (int i = 0; i < tt.length(); i++) {
							if (tt.charAt(i) == '_') {
								charCount++;
								if (charCount == 2) {
									pos = i;
									break;
								}
							}
						}
					}

					String tcid = null;
					if (charCount == 2 && (!tt.startsWith("Ttv"))) {
						int k = nthOccurrence(tt, '_', 0);
						tcid = tt.substring(k + 1, tt.length() - 5);
						if (!tcid.startsWith("Ttv")) {
							tcid = tt.substring(16, tt.length() - 5);
						}
					} else if (charCount == 2) {
						tcid = tt.substring(pos - 11, tt.length() - 5);
					} else {
						tcid = tt.substring(pos + 16, tt.length() - 5);
					}
					// System.out.println(tt);
					System.out.println(tcid);
					re.add(tcid);

				}

			}

		} finally {
			scanner.close();
		}
		return re;
	}

	/**
	 * Nth occurrence.
	 * 
	 * @param str
	 *            the str
	 * @param c
	 *            the c
	 * @param n
	 *            the n
	 * @return the int
	 */
	public static int nthOccurrence(String str, char c, int n) {
		int pos = str.indexOf(c, 0);
		while (n-- > 0 && pos != -1)
			pos = str.indexOf(c, pos + 1);
		return pos;
	}
}