package com.sat.report;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
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

public class CustomReport {

	public static void main(String[] args) throws FileNotFoundException,
			IOException, ParseException, AddressException {

		System.out.println(reportparsejson());

		//sendmail(reportparsejson());

	}
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
				"C:\\Users\\smuniapp\\Desktop\\dmm\\cucumber-report.json"));
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
					System.out.println("satize"+msg2.size());
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
	public static String reportparsejson1() throws FileNotFoundException,
			IOException, ParseException, AddressException {

		HashMap<String, String> infoHash = new HashMap<String, String>();
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

		String headDetailInfo = "";
//		headDetailInfo += "    for<i><font color=\"silver\">  DTACS</br></i></font>"
//				+ "running<i><font color=\"silver\"> DTACS version XXXXX"
//				+ "  </i></font><br>         on headend <i><font color=\"silver\">"
//				+ "HEADEND" + "</i></font>";


        headDetailInfo += "    for<i><font color=\"silver\">  DTACS XXXXX</br></i></font>"
                + "with<i><font color=\"silver\"> DNCS YYYYY"
                + "  </i></font><br>         on <i><font color=\"silver\">"
                + "HEADEND" + "</i></font>   HeadEnd";


        String headdetails = "<table><br><th style='background-color: rgb(25, 112, 193);'><center><h3>SIT Automation Suite Output Report</h3>"
				+ headDetailInfo + "</center></th><br></table><br><br>";
		String version = "";
		String bodydetailS = "<table> <tr style='background-color: rgb(70,116,209);'><th>#</th><th>Feature Name</th><th>TIMS ID</th><th>Test Type</th><th>Test Case Title</th><th>Status</th></tr>";
		String Total = "";
		Object obj = parser.parse(new FileReader(
				"C:\\Users\\smuniapp\\Desktop\\dmm\\cucumber-report.json"));
		
		JSONArray msg = (JSONArray) obj;

		for (int i = 0; i < msg.size(); i++) {
			JSONObject jo = (JSONObject) msg.get(i);
			JSONArray msg1 = (JSONArray) jo.get("elements");
			String uniid = "";
			String nodeinfo = "";

			for (int j = 0; j < msg1.size(); j++) {

				JSONObject jo1 = (JSONObject) msg1.get(j);
				if (jo1.get("tags") != null) {
					String featureFile = null;
					String timsId = null;
					String serial = null;
					String testType = null;
					String testTitle = null;
					JSONArray msg2 = (JSONArray) jo1.get("tags");
					String uniidstatus = "N";
					for (int j2 = 0; j2 < msg2.size(); j2++) {
						// Version
						JSONObject jo2 = (JSONObject) msg2.get(j2);
						if ((jo2.get("name").toString().contains("NodeInfo"))) {
							nodeinfo = "found";
						} else {
							nodeinfo = "found";
						}
//						// Test case details
//						if ((jo2.get("name").toString().contains("Ttv"))
//								|| (jo2.get("name").toString().contains("TBD"))) {
//							String stype = "";
//							for (int typec = 0; typec < msg2.size(); typec++) {
//								JSONObject jotype = (JSONObject) msg2
//										.get(typec);
//								if (jotype.get("name").toString()
//										.contains("Regression"))
//									stype = "Regression";
//								else if (jotype.get("name").toString()
//										.contains("Sanity"))
//									stype = "Sanity";
//
//							}
//							if (!uniid.trim().equals(
//									jo2.get("name").toString().trim())) {
//
//								String feanmae[] = jo.get("uri").toString()
//										.split("/");
//								featureFile = feanmae[feanmae.length - 1];
//								serial = "" + (++sno);
//								timsId = jo2.get("name").toString();
//								testType = stype;
//								testTitle = jo1.get("name").toString();
//								uniidstatus = "Y";
//							}
//							uniid = jo2.get("name").toString();
//						}
					}


					// Begin Version details
					if (nodeinfo.equals("found")) {
						version += "<table border='2%' style='background-color: rgb(170,221,204);'>";
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

										if (msg6.get(j6).toString()
												.contains("Firefox version")) {
											infoHash.put("Firefox version",
													msg6.get(j6).toString()
															.split(";")[1]
															.trim());
										}

										if (msg6.get(j6).toString()
												.contains("DTACS headend name is")) {
											infoHash.put("DTACS headend name is",
													msg6.get(j6).toString()
															.split(";")[1]
															.trim());
										}

										if (msg6.get(j6).toString()
												.contains(
														"DTACS version")) {
											infoHash.put(
													"DTACS version",
													msg6.get(j6).toString()
															.split(";")[1]
															.trim());
										}

                                        if (msg6.get(j6).toString()
                                                .contains(
                                                        "EC DNCS package Version")) {
                                            infoHash.put(
                                                    "EC DNCS package Version",
                                                    msg6.get(j6).toString()
                                                            .split(";")[1]
                                                            .trim());
                                        }

										if (msg6.get(j6).toString()
												.contains("Client OS Version")) {
											infoHash.put("Client OS Version",
													msg6.get(j6).toString()
															.split(";")[1]
															.trim());
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
					int pf = -1;
					for (int j3 = 0; j3 < msg3.size(); j3++) {
						JSONObject jo3 = (JSONObject) msg3.get(j3);
						JSONObject jo4 = (JSONObject) jo3.get("result");
						if (jo4.get("status").equals("passed")) {
							pf = 0;
						} else {
							pf = 1;
							break;
						}
					}
					if (pf == 0 && uniidstatus.equals("Y")) {
						bodydetailS += "<tr style='background-color: rgb(107,144,70);'><td><center>"
								+ serial
								+ "</center></td><td>"
								+ featureFile
								+ "</td><td><center>"
								+ timsId.substring(1)
								+ "</center></td><td><center>"
								+ testType
								+ "</center></td><td>"
								+ testTitle
								+ "</td><td ><center>Passed</center></td></tr>";
						pass++;
					} else if (uniidstatus.equals("Y")) {
						bodydetailS += "<tr style='background-color: rgb(216, 138, 138);'><center><td>"
								+ serial
								+ "</center></td><td>"
								+ featureFile
								+ "</td><td><center>"
								+ timsId.substring(1)
								+ "</center></td><td><center>"
								+ testType
								+ "</center></td><td>"
								+ testTitle
								+ "</td><td><center>Failed</center></td></tr>";
						failureReasonTable += "<tr><td><center>"
								+ timsId.substring(1) + "</center></td><td>"
								+ testTitle + "</td><td></td><td></td></tr>";
						fail++;
					}
				}
			}
		}

		// Overriding the version logic, to include the newer info.
		version = "<table border='1%' style='background-color: rgb(170,221,204);'>";

		// FF version
		version += "<tr style='background-color: rgb(70,116,209);'><center><td colspan=4><font style='color:black'>Browser Version";
		version += "<td colspan=4><font style='color:black'>Firefox "
				+ infoHash.get("Firefox version") + "</center></td></tr>";

		// Client OS
		version += "<tr style='background-color: rgb(70,116,209);'><center><td colspan=4><font style='color:black'>Client OS Version";
		version += "<td colspan=4><font style='color:black'>"
				+ infoHash.get("Client OS Version") + "</center></td></tr>";

		version += "</table>";


		Total = "<table><tr style='background-color: rgb(70,116,209);'><th>Total</th><td>Passed</th><th>Failed</th></tr>";
		Total += "<center><tr style='background-color: rgb(170,204,204);'><td>"
				+ sno + "</td><td>" + pass + "("
				+ String.format("%.2f", (pass * 100.0F / sno)) + "%)</td><td>"
				+ fail + "(" + String.format("%.2f", (fail * 100.0F / sno))
				+ "%)</td></tr></center></table><br>";
		bodydetailS += "</center></table><br><br><br>";

		System.out.println(version);

		headdetails = headdetails.replace("XXXXX",
				infoHash.get("DTACS version"));

		headdetails = headdetails.replace("HEADEND",
				infoHash.get("DTACS headend name is"));

        headdetails = headdetails.replace("YYYYY",
                infoHash.get("EC DNCS package Version"));



		head += headdetails + version + Total + bodydetailS;
		if (fail > 0) {
			failureReasonTable += "</table>";
			// System.out.println(failureReasonTable);
			head += failureReasonTable;
		}
		head += "</td></tr></table></body></html>";

		return head;

	}

	public static void sendmail(String msg) throws AddressException,
			IOException {
		Properties CustomReport_CONFIG = new Properties();
		System.out.println(System.getProperty("user.dir"));
		FileInputStream fn = new FileInputStream(
				"./src/it/resources/ApplicationConfig.properties");
		CustomReport_CONFIG.load(fn);
		String from = "automationreportmailer@cisco.com";
		//String from = "sitaut@cisco.com";
		String host = CustomReport_CONFIG.getProperty("MAIL.SMTP.HOST");
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
			message.addRecipients(Message.RecipientType.TO,
					CustomReport_CONFIG.getProperty("MAIL.TO"));
			message.setSubject("Run Report for Automated Test Execution for DTACS");

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
