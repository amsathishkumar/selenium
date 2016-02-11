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

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.cisco.spvgt.utils.configfilehandler.ConfigFileHandlerManager;
import com.cisco.spvgt.utils.validation.Validate;

// TODO: Auto-generated Javadoc
/**
 * The Class CustomReport.
 */
public class CustomReport {




	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ParseException the parse exception
	 * @throws AddressException the address exception
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException, AddressException {

		String msg = reportparsejson();
		sendmail(msg);
		generateHtmlFile(msg);

	}

	/**
	 * Generate html file.
	 *
	 * @param msg the msg
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws FileNotFoundException the file not found exception
	 */
	private static void generateHtmlFile(String msg) throws IOException, FileNotFoundException {
	    String htmlfname = "./customreport.html";
		String eol = System.getProperty("line.separator");
		msg = msg.replaceAll("<tr", eol + "<tr");
		msg = msg.replaceAll("</table>", eol + "</table>");
		msg = msg.replaceAll("<table", eol + "<table");
		new FileOutputStream(htmlfname).close();
		PrintWriter htmlfile = new PrintWriter(new BufferedWriter(new FileWriter(htmlfname, true)));
		htmlfile.print(msg);
		htmlfile.close();
	}

	/**
	 * Reportparsejson.
	 *
	 * @return the string
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ParseException the parse exception
	 * @throws AddressException the address exception
	 */
	public static String reportparsejson() throws FileNotFoundException, IOException, ParseException, AddressException {
		String failureReasonTable = "<table border style='width:100%'><tr style='background-color: rgb(70,116,209);'><colgroup><col span='1' style='width: 14%;'><col span='1' style='width: 40%;'><col span='1' style='width: 33%;'><col span='1' style='width: 13%;'></colgroup><th>Failed Test Case ID</th><th>Test Title</th><th>Failure Reason</th><th>Failure Category</th></tr>";
		String head = "<html>";
		head += "<head>";
		head += "<style>";
		head += "body{position:absolute;width:80%;height:100%;margin:0;padding:0}table,tbody{position:relative;width:100%;table-layout: auto}tr td,th{width:.5%;word-break:break-all;border:.5px solid black;} ";
		head += "</style>";
		head += "</head>";
		head += "<body border='2%'><table><tr><td style='background-color: rgb(170,187,204);'>";

		JSONParser parser = new JSONParser();
		int sno = 0;
		int pass = 0, fail = 0;
		int passed1 = 0, failed1 = 0;
		String headdetails = "<table><br><th style='background-color: rgb(25, 112, 193);'><center>Customized Automation Run Report</center></th><br></table><br><br>";
		String version = "";
		String bodydetailS = "<table> <tr style='background-color: rgb(70,116,209);'><th>#</th><th>Feature Name</th><th>Test Case Title</th><th>TIMS ID</th><th>Test Type</th><th>Component Involved</th><th>Status</th></tr>";
		String Total = "";
		Object obj = parser.parse(new FileReader(System.getProperty("user.dir")+"/LMS/target/reports/cucumber-report.json"));
		JSONArray msg = (JSONArray) obj;

		for (int i = 0; i < msg.size(); i++) {
			JSONObject jo = (JSONObject) msg.get(i);
			JSONArray msg1 = (JSONArray) jo.get("elements");
			String uniid = "";
			String nodeinfo = "";
			String featureFile = null;
			String timsId = null;
			String serial = null;
			String testType = null;
			String testTitle = null;
			String mid = null, mid1 = null;
			for (int j = 0; j < msg1.size(); j++) {

				JSONObject jo1 = (JSONObject) msg1.get(j);
				System.out.println("Id" + jo1.get("id"));
				if (jo1.get("id") != null) {

					JSONArray msg2 = (JSONArray) jo1.get("tags");

					String uniidstatus = "N";
					int pf = -1;
					System.out.println("satsize" + msg2.size());
					for (int j2 = 0; j2 < msg2.size(); j2++) {
						// Version
						JSONObject jo2 = (JSONObject) msg2.get(j2);
						if ((jo2.get("name").toString().contains("NodeInfo"))) {
							nodeinfo = "found";
						}
						// Test case details
						if ((jo2.get("name").toString().contains("Stage2")) || (jo2.get("name").toString().contains("TBD"))) {
							String stype = "Functional";
							for (int typec = 0; typec < msg2.size(); typec++) {
								JSONObject jotype = (JSONObject) msg2.get(typec);
								if (jotype.get("name").toString().contains("Functional"))
									stype = "Functional";
								else if (jotype.get("name").toString().contains("Sanity"))
									stype = "Sanity";

							}
							if (!uniid.trim().equals(jo2.get("name").toString().trim())) {

								String fnameuri[] = jo.get("uri").toString().split("/");
								featureFile = fnameuri[fnameuri.length - 1];
								System.out.println("feture file" + featureFile);
								String[] featureFile1=featureFile.split(".feature");
								System.out.println("split feature"+ featureFile1[0]);

								

								serial = "" + (++sno);
								timsId = jo2.get("name").toString();
								testType = stype;
								testTitle = jo1.get("name").toString();
								mid = "<td><center>" + serial + "</center></td><td>" + featureFile1[0] + "</center></td><td>" + testTitle + "</center></td><td><center>" + timsId.substring(1) + "</center></td><td><center>" + testType + "</td>";
								uniidstatus = "Y";

								mid1 = "<td><center>" + timsId.substring(1) + "</center></td><td>" + testTitle + "</td>";

							}

							uniid = jo2.get("name").toString();

						}
					}



					JSONArray msg3 = (JSONArray) jo1.get("steps");
					for (int j3 = 0; j3 < msg3.size(); j3++) {
						JSONObject jo3 = (JSONObject) msg3.get(j3);
						JSONObject jo4 = (JSONObject) jo3.get("result");
						System.out.println(jo4.get("status"));
						if (jo4.get("status").equals("passed")) {
							pf = 0;
						} else {
							pf = 1;
							break;
						}
					}

					String scenarioprint = null;
					JSONArray output = (JSONArray) jo1.get("steps");
					for (int outputj3 = 0; outputj3 < output.size(); outputj3++) {
						JSONObject outputjo3 = (JSONObject) output.get(outputj3);
						JSONArray outputjo4 = (JSONArray) outputjo3.get("output");
						if (outputjo4 != null) {
							for (int outputj33 = 0; outputj33 < outputjo4.size(); outputj33++) {
								String tempscenarioprint= (String) outputjo4.get(outputj33);
								tempscenarioprint=tempscenarioprint.replace("[", " ");
								tempscenarioprint=tempscenarioprint.replace("]", " ").trim();
								System.out.println(tempscenarioprint);
								if (tempscenarioprint.startsWith("ComponentInvolved"))
								{
									tempscenarioprint=tempscenarioprint.replace("ComponentInvolved", "Begin");
									tempscenarioprint=tempscenarioprint.replace(",", " ");
									scenarioprint=tempscenarioprint;
								}
								System.out.println("scenario" + scenarioprint);
							}
						}

					}

					if (uniidstatus.equals("Y")) {
						if (pf == 0) {
							bodydetailS += "<tr style='background-color: rgb(107,144,70);'>" + mid + "<td><center>"+scenarioprint + "</center></td><td><center>Passed</center></td></tr>";
							pass++;
						} else if (pf == 1) {
							bodydetailS += "<tr style='background-color: rgb(216, 138, 138);'><center>" + mid + "<td><center>"+scenarioprint + "</center></td><td><center>Failed</center></td></tr>";
							if (!failureReasonTable.contains("<tr><td><center>" + mid1 + "</td><td></td><td></td></tr>"))
								failureReasonTable += "<tr>" + mid1 + "<td></td><td></td></tr>";
							fail++;
						}
					} else if (mid != null) {
						if (bodydetailS.contains(mid)) {
							if (pf == 0) {

							} else if (pf == 1) {
								if (bodydetailS.contains("<tr style='background-color: rgb(107,144,70);'>" + mid + "<td><center>"+scenarioprint + "</center></td><td><center>Passed</center></td></tr>")) {
									failed1++;
									passed1--;
									bodydetailS = bodydetailS.replace("<tr style='background-color: rgb(107,144,70);'>" + mid +"<td><center>"+scenarioprint + "</center></td><td><center>Passed</center></td></tr>", "<tr style='background-color: rgb(216, 138, 138);'><center>" + mid
											+ "<td><center>Failed</center></td></tr>");
								}
								if (bodydetailS.contains("<tr style='background-color: rgb(216, 138, 138);'><center>" + mid +"<td><center>"+scenarioprint + "</center></td><td><center>Failed</center></td></tr>")) {
									bodydetailS = bodydetailS.replace("<tr style='background-color: rgb(216, 138, 138);'><center>" + mid + "<td><center>"+scenarioprint + "</center></td><td><center>Failed</center></td></tr>",
											"<tr style='background-color: rgb(216, 138, 138);'><center>" + mid + "<td><center>"+scenarioprint + "</center></td><td><center>Failed</center></td></tr>");

								}

								if (!failureReasonTable.contains("<tr><td><center>" + mid1 + "</td><td></td><td></td></tr>"))
									failureReasonTable += "<tr>" + mid1 + "<<td></td><td></td></tr>";
							}

						}

					}
				}
			}
		}

		Total = "<table><tr style='background-color: rgb(70,116,209);'><th>Total</th><td>Passed</th><th>Failed</th></tr>";
		Total += "<center><tr style='background-color: rgb(170,204,204);'><td>" + sno + "</td><td>" + (pass + passed1) + "(" + String.format("%.2f", ((pass + passed1) * 100.0F / sno)) + "%)</td><td>" + (fail + failed1) + "("
				+ String.format("%.2f", ((fail + failed1) * 100.0F / sno)) + "%)</td></tr></center></table><br>";
		bodydetailS += "</center></table><br><br><br>";

		System.out.println(version);
		head += headdetails + Total + version + bodydetailS;
		if (fail > 0) {
			failureReasonTable += "</table>";
			// System.out.println(failureReasonTable);
			head += failureReasonTable;
		}
		head += "</td></tr></table></body></html>";

		return head;

	}

	/**
	 * Sendmail.
	 * 
	 * @param msg
	 *            the msg
	 * @throws AddressException
	 *             the address exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void sendmail(String msg) throws AddressException, IOException {

		ConfigFileHandlerManager miscConfigFileHandlerManager = new ConfigFileHandlerManager(); 
		miscConfigFileHandlerManager.loadPropertiesBasedonPropertyFileName("com.cisco.lms.lms");
		Validate miscValidate = new Validate();
		
		String from = miscValidate.readsystemvariable("MAIL.FROM");
		String host = miscValidate.readsystemvariable("MAIL.SMTP.HOST");
	    String to =  miscValidate.readsystemvariable("MAIL.TO");		
		
		String subject =  miscValidate.readsystemvariable("MAIL.SUBJECT");
		String personal = miscValidate.readsystemvariable("MAIL.FROM.PERSONAL");
		
		
		// Mail to details
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		javax.mail.Session session = javax.mail.Session.getDefaultInstance(properties);

		// compose the message
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from, personal));
            
			message.addRecipients(Message.RecipientType.TO, to);
			message.setSubject(subject);
			Multipart mp = new MimeMultipart();
			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(msg, "text/html");
			mp.addBodyPart(htmlPart);
			message.setContent(mp);
			Transport.send(message);
			System.out.println(msg);
			System.out.println("message sent successfully....");

		} catch (MessagingException mex) {
			mex.printStackTrace();
		} catch (Exception e) {
			System.out.println("\tException in sending mail to configured mailers list");
		}
	}
}
