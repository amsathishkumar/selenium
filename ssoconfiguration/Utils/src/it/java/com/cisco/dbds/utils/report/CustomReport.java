/**
 * Copyright (c) 2015 by SAT Systems, Inc.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of SAT Systems,  ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with SAT Systems.
 *
 *
 * @Project: Utils
 * @Author: amsathishkumar
 * @Version: 
 * @Description:  
 * @Date created: Apr 9, 2015
 */
package com.cisco.dbds.utils.report;

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

// TODO: Auto-generated Javadoc
/**
 * The Class CustomReport.
 */
public class CustomReport {

	/** The sub. */
	public String sub = null;
	
	/** The htmlfname. */
	private static String htmlfname="c:\\customreport.html";
	
	/** The htmlfile. */
	private static PrintWriter htmlfile;
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ParseException the parse exception
	 * @throws AddressException the address exception
	 */
	public static void main(String[] args) throws FileNotFoundException,
	IOException, ParseException, AddressException {

		// reportparsejson();
		String msg=reportparsejson();
		sendmail(msg);		
		String eol = System.getProperty("line.separator");		
		//msg=msg.replaceAll("</tr>", eol+"</tr>");
		msg=msg.replaceAll("<tr", eol+"<tr");
		msg=msg.replaceAll("</table>",eol+ "</table>");
		msg=msg.replaceAll("<table", eol+"<table");
		new FileOutputStream(htmlfname).close();
		htmlfile = new PrintWriter(new BufferedWriter(new FileWriter(htmlfname, true)));

		htmlfile.print(msg);
		htmlfile.close();

		// String mailContent = "Hi All<br>"+
		// "Build Tag id: jenkins-CANEAS-Nightly-Completed-190 has been completed and the run reports  found in the link: http://10.78.216.52:8080/job/CANEAS-Nightly-Completed/190/cucumber-html-reports/"
		// + "<br>"
		// +
		//
		// "EDCS link for failure analysis Reasons: http://wwwin-eng.cisco.com/cgi-bin/edcs/edcs_info?3677074"
		// + "<br>"
		// +
		// "(will be updated soon for the Build: jenkins-CANEAS-Nightly-Completed-190 )"
		// + "<br><br>" + "Regards" + "<br>" + "SIT Automation TEAM"
		// + "<br>";
		// sendmail(mailContent);
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
	public static String reportparsejson() throws FileNotFoundException,
	IOException, ParseException, AddressException {
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
		String bodydetailS = "<table> <tr style='background-color: rgb(70,116,209);'><th>#</th><th>Feature Name</th><th>TIMS ID</th><th>Test Type</th><th>Test Case Title</th><th>Status</th></tr>";
		String Total = "";
		Object obj = parser.parse(new FileReader(
				"./target/reports/cucumber-report.json"));
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
			String mid=null,mid1=null;
			for (int j = 0; j < msg1.size(); j++) {

				JSONObject jo1 = (JSONObject) msg1.get(j);
				System.out.println("Id"+jo1.get("id"));
				if (jo1.get("id") != null) {

					JSONArray msg2 = (JSONArray) jo1.get("tags");




					String uniidstatus = "N";
					int pf = -1;
					System.out.println("satsize"+msg2.size());
					for (int j2 = 0; j2 < msg2.size(); j2++) {
						// Version
						JSONObject jo2 = (JSONObject) msg2.get(j2);
						if ((jo2.get("name").toString().contains("NodeInfo"))) {
							nodeinfo = "found";
						}
						// Test case details
						if ((jo2.get("name").toString().contains("Ttv"))
								|| (jo2.get("name").toString().contains("TBD"))) {
							String stype = "";
							for (int typec = 0; typec < msg2.size(); typec++) {
								JSONObject jotype = (JSONObject) msg2
										.get(typec);
								if (jotype.get("name").toString()
										.contains("Regression"))
									stype = "Regression";
								else if (jotype.get("name").toString()
										.contains("Sanity"))
									stype = "Sanity";

							}
							if (!uniid.trim().equals(					
									jo2.get("name").toString().trim())) {

								String feanmae[] = jo.get("uri").toString()
										.split("/");
								featureFile = feanmae[feanmae.length - 1];

								serial = "" + (++sno);
								timsId = jo2.get("name").toString();
								testType = stype;
								testTitle = jo1.get("name").toString();
								mid="<td><center>"
										+ serial
										+ "</center></td><td>"
										+ featureFile
										+ "</td><td><center>"
										+ timsId.substring(1)
										+ "</center></td><td><center>"
										+ testType
										+ "</center></td><td>"
										+ testTitle
										+ "</td>";
								uniidstatus = "Y";

								mid1="<td><center>"
										+ timsId.substring(1)
										+"</center></td><td>"
										+ testTitle
										+ "</td>";

							}

							uniid = jo2.get("name").toString();

						}
					}

					// Begin Version details
					if (nodeinfo.equals("found")) {
						version = "<table border='2%' style='background-color: rgb(170,221,204);'>";
						JSONArray msg5 = (JSONArray) jo1.get("steps");
						System.out.println("Steps:" + msg5);
						for (int j5 = 0; j5 < msg5.size(); j5++) {
							JSONObject jo5 = (JSONObject) msg5.get(j5);
							System.out.println(jo5.keySet());
							JSONArray msg6 = (JSONArray) jo5.get("output");
							System.out.println(msg6);
							if (msg6 != null) {

								for (int j6 = 0; j6 < msg6.size(); j6++) {
									if (msg6.get(j6).toString()
											.contains("cisco.conductor")) {
										System.out.println(msg6.get(j6));
										version += "<tr><td colspan=4><font style='color:rgb(0,102,0);'>"
												+ msg6.get(j6).toString()
												+ "</font></td></tr>";
									} else {
										String rr[] = msg6.get(j6).toString()
												.split(";");
										if (rr.length == 1) {
											version += "<tr><td colspan ='4'><center><b><font size=3 style='color:rgb(0,102,0);'>"
													+ rr[0]
															+ "</font></b></center></td></tr>";
										} else {
											version += "<tr border=''><td colspan=4 style='height:20px' /></tr><tr style='background-color: rgb(70,116,209);'><center>";

											for (int rr1 = 0; rr1 < rr.length; rr1++) {
												version += "<td colspan='"
														+ (4 / rr.length)
														+ "'>" + rr[rr1]
																+ "</td>";
											}
											version += "</center></tr>";
										}
									}
								}
							}
						}
						version += "</table><br>";
						System.out.println(version);
					}
					// End Version details

					JSONArray msg3 = (JSONArray) jo1.get("steps");
					//int pf = -1;
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

					if(uniidstatus.equals("Y"))
					{
						if (pf == 0 ) {
							bodydetailS += "<tr style='background-color: rgb(107,144,70);'>"+mid+"<td><center>Passed</center></td></tr>";
							pass++;
						} else if (pf == 1) {
							bodydetailS += "<tr style='background-color: rgb(216, 138, 138);'><center>"+mid+ "<td><center>Failed</center></td></tr>";
							if (!failureReasonTable.contains("<tr><td><center>"+mid1+ "</td><td></td><td></td></tr>"))								
								failureReasonTable += "<tr>"
										+mid1 + "<td></td><td></td></tr>";
							fail++;
						}
					}
					else if (mid != null)
					{
						if (bodydetailS.contains(mid))
						{
							if (pf == 0 ) {/*
							if (bodydetailS.contains("<tr style='background-color: rgb(107,144,70);'>"+mid+"<td><center>Passed</center></td></tr>"))	
							{	
								bodydetailS=bodydetailS.replace( "<tr style='background-color: rgb(107,144,70);'>"+mid+"<td><center>Passed</center></td></tr>","<tr style='background-color: rgb(107,144,70);'>"+mid+"<td><center>Passed</center></td></tr>");
								//passed1--;
							}
							if (bodydetailS.contains("<tr style='background-color: rgb(216, 138, 138);'><center>"+mid+ "<td><center>Failed</center></td></tr>"))
							{
								bodydetailS=bodydetailS.replace( "<tr style='background-color: rgb(216, 138, 138);'><center>"+mid+ "<td><center>Failed</center></td></tr>","<tr style='background-color: rgb(107,144,70);'>"+mid+"<td><center>Passed</center></td></tr>");
								passed1++;
								failed1--;

							}*/
							} else if (pf == 1) {
								if (bodydetailS.contains("<tr style='background-color: rgb(107,144,70);'>"+mid+"<td><center>Passed</center></td></tr>"))
								{
									failed1++;
									passed1--;
									bodydetailS=bodydetailS.replace( "<tr style='background-color: rgb(107,144,70);'>"+mid+"<td><center>Passed</center></td></tr>","<tr style='background-color: rgb(216, 138, 138);'><center>"+mid+ "<td><center>Failed</center></td></tr>");
								}
								if (bodydetailS.contains("<tr style='background-color: rgb(216, 138, 138);'><center>"+mid+ "<td><center>Failed</center></td></tr>"))
								{
									bodydetailS=bodydetailS.replace( "<tr style='background-color: rgb(216, 138, 138);'><center>"+mid+ "<td><center>Failed</center></td></tr>","<tr style='background-color: rgb(216, 138, 138);'><center>"+mid+ "<td><center>Failed</center></td></tr>");

								}

								if (!failureReasonTable.contains("<tr><td><center>"+mid1+ "</td><td></td><td></td></tr>"))								
									failureReasonTable += "<tr>"
											+mid1+ "<<td></td><td></td></tr>";
							}

						}
						/*if (pf == 0 && uniidstatus.equals("Y")) {
						bodydetailS += "<tr style='background-color: rgb(107,144,70);'>"+mid+"<td><center>Passed</center></td></tr>";
						pass++;
					} else if (pf == 1) {
						bodydetailS += "<tr style='background-color: rgb(216, 138, 138);'><center>"+mid+ "<td><center>Failed</center></td></tr>";
						failureReasonTable += "<tr><td><center>"
							//	+ timsId.substring(1) + "</center></td><td>"
							+ timsId
								+ testTitle + "</td><td></td><td></td></tr>";
						fail++;
					}*/
					}
				}
			}
		}

		Total = "<table><tr style='background-color: rgb(70,116,209);'><th>Total</th><td>Passed</th><th>Failed</th></tr>";
		Total += "<center><tr style='background-color: rgb(170,204,204);'><td>"
				+ sno + "</td><td>" + (pass+passed1) + "("
				+ String.format("%.2f", ((pass+passed1) * 100.0F / sno)) + "%)</td><td>"
				+ (fail+failed1) + "(" + String.format("%.2f", ((fail+failed1) * 100.0F / sno))
				+ "%)</td></tr></center></table><br>";
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
	 * @param msg the msg
	 * @throws AddressException the address exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void sendmail(String msg) throws AddressException,
	IOException {
		//Properties CustomReport_CONFIG = new Properties();
		//FileInputStream fn = new FileInputStream(System.getProperty("user.dir")+ "/src/it/resources/config.properties");
		//CustomReport_CONFIG.load(fn);
		String from = "automationreportmailer@cisco.com";
		// String from = "sitaut@cisco.com";
		//String host = System.getProperty("MAIL.SMTP.HOST");
		String host = "outbound.cisco.com";
		// Mail to details
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		// String ecsip = CustomReport_CONFIG.getProperty("ecsip");
		javax.mail.Session session = javax.mail.Session
				.getDefaultInstance(properties);

		// compose the message
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from
					// ));
					, "Automation Report Mailer"));
			//message.addRecipients(Message.RecipientType.TO, System.getProperty("MAIL.TO"));
			//message.setSubject(System.getProperty("mail.subject"));

			message.addRecipients(Message.RecipientType.TO, "maparame@cisco.com");
			message.setSubject("VCS consle report");
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
			System.out
			.println("\tException in sending mail to configured mailers list");
		}
	}
}
