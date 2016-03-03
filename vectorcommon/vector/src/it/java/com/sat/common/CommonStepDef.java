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
package com.sat.common;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import com.cisco.spvtg.automationcore.datatype.CoreResponse;
import com.cisco.spvtg.automationcore.http.HttpClient;
import com.cisco.spvtg.automationcore.http.HttpClient.HttpClientBuilder;
import com.cisco.spvtg.automationcore.http.HttpClient.HttpsClientBuilder;
import com.google.gson.Gson;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.sat.spvgt.utils.configfilehandler.ConfigFileHandlerManager;
import com.sat.spvgt.utils.cucumber.Hooks;
import com.sat.spvgt.utils.validation.Validate;

import cucumber.api.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;

// TODO: Auto-generated Javadoc
/**
 * The Class CommonStepDef.
 */
public class CommonStepDef {
	
	/** The json str. */
	String jsonStr;
	
	/** The json str. */
	String xmlStr;
	
	/** The str url. */
	String strURL = null;
	
	/** The response. */
	CoreResponse response;
	
	/** The cf. */
	CommonFunctions cf = new CommonFunctions();
	
	/** The misc config file handler. */
	ConfigFileHandlerManager miscConfigFileHandler = new ConfigFileHandlerManager();
	
	/** The misc validate. */
	Validate miscValidate = new Validate();
	
	/** The Modulename. */
	ArrayList<String> Modulename;
	SDdetails sddetails = new SDdetails();
	
	/** RabbitMQ configuration. */
	ConnectionFactory factory = new ConnectionFactory();
	public static Connection connection;
	public static Channel channel;
	String msgRcvd = "" ;
	String QUEUE_NAME1 = "";
	
	/**
	 * Instantiates a new common step def.
	 */
	public CommonStepDef (){
		Modulename= new ArrayList();
		Modulename.add("ComponentInvolved ->");
		String Call_Processed=null;
	}
	
	/**
	 * Gets the property values.
	 *
	 * @param ServiceName the service name
	 * @return the property values
	 */
	@Given("^Load settings of \"(.*?)\"$")
	public void getPropertyValues(String ServiceName) {
		cf.readServiceProperty(ServiceName);
		Modulename.add(ServiceName + "->");
		Hooks.scenario.write(Modulename.toString());

	}

	/**
	 * Print_the_ scenario.
	 */
	@Given("^Print the Scenario$")
	public void print_the_Scenario() {
		Modulename.add("End.");
		Hooks.scenario.write(Modulename.toString().replaceAll(", ", " "));
		Modulename = new ArrayList();
		Modulename.add("ComponentInvolved ->");
	}

	/**
	 * Gets the _ store_value.
	 *
	 * @param arg1 the arg1
	 * @return the _ store_value
	 */
	@Given("^Get Store value \"(.*?)\"$")
	public void get_Store_value(String arg1) {

		System.out.println("Get stored " + arg1 + " value..............." + miscValidate.readsystemvar(arg1.trim()));
	}

	/**
	 * Load_property_from.
	 *
	 * @param propertyFileName the property file name
	 */
	@Given("^Load property from \"(.*?)\"$")
	public void load_property_from(String propertyFileName) {
		miscConfigFileHandler.loadPropertiesBasedonPropertyFileName(propertyFileName);
		// cf.readBundle(propertyFileName, System.getProperty("SD.IP"));
	}

	/**
	 * Generate_json_with_below_data.
	 *
	 * @param Testdata the testdata
	 */
	@Given("^Generate json with below data$")
	public void generate_json_with_below_data(DataTable Testdata) {
	//	List<Map<String, String>> newmm = Testdata.asMaps(String.class, String.class);
		
		List<Map<String, String>> newmm = miscValidate.readsystemvariable(Testdata);
		System.out.println("the converted maps" + newmm);
		
		for (String svalu : cf.generateJsonString(newmm)) {
			jsonStr = svalu;
		}
        System.out.println("Generate json with below data:"+jsonStr);
		Hooks.scenario.write(jsonStr);
	}


	@Given("^Generate xml with below data$")
	public void generate_xml_with_below_data(DataTable Testdata) {
	//	List<Map<String, String>> newmm = Testdata.asMaps(String.class, String.class);
		
		List<Map<String, String>> newmm = miscValidate.readsystemvariablePersistOrder(Testdata);
		System.out.println("The Payload after substituting system variables " + newmm);
		
		for (String svalu : cf.generatexmlString(newmm)) {
			xmlStr = svalu;
		}
        System.out.println("Generate  xml  payload : "+xmlStr);
		Hooks.scenario.write(xmlStr);

}




	/**
	 * Frame_the_ url_for.
	 *
	 * @param ServiceName the service name
	 * @param Data the data
	 * @throws Throwable the throwable
	 */
	@Given("^Frame the Url for \"(.*?)\"$")
	public void frame_the_Url_for(String ServiceName, DataTable Data) throws Throwable {
		cf.readServiceProperty(ServiceName);
		List<String> valueList = generateURL(Data);
		for (String s : valueList)
			strURL = s.trim();
		System.out.println("URL:" + strURL);
		Hooks.scenario.write(strURL);
		

	}

	/**
	 * Generate url.
	 *
	 * @param Data the data
	 * @return the list
	 */
	private List<String> generateURL(DataTable Data) {
		List<String> TestDataLHM = new ArrayList<String>();
	//	List<Map<String, String>> Datavalue = Data.asMaps(String.class, String.class);
		List<Map<String, String>> Datavalue = miscValidate.readsystemvariable(Data);
		for (Map<String, String> Testdata : Datavalue) {
			LinkedHashMap<String, String> e = new LinkedHashMap<String, String>();
			for (String s : Data.topCells()) {
				e.put(s, readVariable(Testdata.get(s)));
			}
			String constURL = "";
			for (Map.Entry<String, String> lhmv : e.entrySet()) {
				constURL = constURL + lhmv.getValue();
				//System.out.println("constURL "+constURL);
			}
			TestDataLHM.add(constURL);
		}
		return TestDataLHM;
	}

	/**
	 * Read variable.
	 *
	 * @param varValue the var value
	 * @return the string
	 */
	private String readVariable(String varValue) {
		final String REGEX = "<<(.*?)>>";
		   Pattern p = Pattern.compile(REGEX);
	       Matcher m = p.matcher(varValue);
	       
		if (m.find())
			varValue = miscValidate.readsystemvar(varValue);
		return varValue;
	}

	/**
	 * Make_a_call_with_the_below_headers.
	 *
	 * @param httpmethod the httpmethod
	 * @param Data the data
	 */
	@Given("^Make a \"(.*?)\" call with the below headers$")
	public void make_a_call_with_the_below_headers(String httpmethod, DataTable Data) {

		String strContenttype = null;
		HttpClient httpClient;
		HttpsClientBuilder dd = new HttpsClientBuilder();
		List<Map<String, String>> Datavalue = Data.asMaps(String.class, String.class);
		dd.withTargetURL(strURL);

		for (Map<String, String> Testdata : Datavalue) {
			for (String s : Data.topCells()) {
				switch (s.trim()) {
				case "Content-Type":
					dd = dd.withContentType(Testdata.get("Content-Type"));
					System.out.println("the content-type" + dd);
					break;
				case "Basic":
					String[] basic = Testdata.get("Basic").split("/");
					dd = dd.withBasicAuthentication(basic[0], basic[1]);
					System.out.println("basic[0]" + basic[0] + basic[1]);
					System.out.println("the authentication " + dd);
					break;
				default:
					System.err.print("Invlid header details");
					break;
				}
			}
		}

		httpClient = dd.build();

		System.out.println(httpmethod + " : " + strURL + " : " + jsonStr);

		switch (httpmethod.trim()) {
		case "POST":
			response = httpClient.post(jsonStr.trim());
			break;
		case "GET":
			response = httpClient.get();
			break;
		case "PUT":
			response = httpClient.put(jsonStr.trim());
			break;
		case "DELETE":
			response = httpClient.delete();
			break;
		}
		Hooks.scenario.write("Response"+response.getRespStatus()+ ": "+response.getRespMsgBody());
	}

	/**
	 * Make_a_call_with_the_below_headers.
	 *
	 * @param httpmethod the httpmethod
	 */
	@Given("^Make a \"(.*?)\" call with no headers$")
	public void make_a_call_with_the_below_headers(String httpmethod) {

		HttpClient httpClient;
		HttpsClientBuilder dd = new HttpsClientBuilder();

		dd.withTargetURL(strURL);

		httpClient = dd.build();

		System.out.println(httpmethod + " : " + strURL + " : " + jsonStr);

		switch (httpmethod.trim()) {
		case "POST":
			response = httpClient.post(jsonStr.trim());
			break;
		case "GET":
			response = httpClient.get();
			break;
		case "PUT":
			response = httpClient.put(jsonStr.trim());
			break;
		case "DELETE":
			response = httpClient.delete();
			break;
		}
		Hooks.scenario.write("Response"+response.getRespStatus()+ ": "+response.getRespMsgBody());
	}
	
	@Given("^Make a \"(.*?)\" \"(.*?)\" call with no headers with xml payload$")
	public void make_a_Specifiedcall_with_the_below_headers(String httpPost , String httpmethod ) {

		HttpClient httpClient;
		
			HttpClientBuilder dd = new HttpClientBuilder();
			dd.withTargetURL(strURL);
			httpClient = dd.build();			
			System.out.println(httpmethod + " : " + strURL + " : " + xmlStr);

			switch (httpmethod.trim()) {
			case "POST":
				response = httpClient.post(xmlStr.trim());
				break;
			case "GET":
				response = httpClient.get();
				break;
			case "PUT":
				response = httpClient.put(xmlStr.trim());
				break;
			case "DELETE":
				response = httpClient.delete();
				break;
			}	
		
		Hooks.scenario.write("Response"+response.getRespStatus()+ ": "+response.getRespMsgBody());
	}


	/**
	 * Verify_the_reponse_status_code_has_with_below_response_value.
	 *
	 * @param statuscode the statuscode
	 * @param bodytype the bodytype
	 * @param Data the data
	 */
	@Given("^Verify the reponse status code has \"(.*?)\" with below response \"(.*?)\" value$")
	public void verify_the_reponse_status_code_has_with_below_response_value(String statuscode, String bodytype, DataTable Data) {
		String actualreponsosecode = Integer.toString(response.getRespStatus());
		Assert.assertEquals("Status code is not Ok", statuscode, actualreponsosecode);
		if (bodytype.equals("file json")) {
			List<String> responseoutput = generateJsonList(Data);
			for (String responseoutputvalue : responseoutput) {
				String actual = response.getRespMsgBody().replace("\"", "").replace(" ", "");
				actual = actual.replace("\n", "").trim();
				String expected = responseoutputvalue.replace("\"", "").trim();
				System.out.println(expected + "Actual:" + actual + "Staut:" + expected.equals(actual));
				Assert.assertEquals("Response Body is not ok", expected, actual);
				System.setProperty("tresponse", actual);
			}
			
		}
		
		if (bodytype.equals("xml")) {
			System.out.println(" xml response validater");
			List<Map<String, String>> responseoutput = miscValidate.readsystemvariablePersistOrder(Data);
			
			System.out.println("Response" + response.getRespMsgBody());
			try {
				 DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			        DocumentBuilder docBuilder;
			        docBuilder = docFactory.newDocumentBuilder();
		            ByteArrayInputStream bis = new ByteArrayInputStream(response.getRespMsgBody().getBytes());
		           Document domDoc = docBuilder.parse(bis);  
		           
				    						       			    
				    for(Map<String,String> attribute: responseoutput)
					{
				    	for(Entry<String,String> Header  : attribute.entrySet())
				    	{
				    		NodeList n = domDoc.getElementsByTagName(Header.getKey());
				    		Assert.assertEquals("Validate the Code", ((Text)(n.item(0).getFirstChild())).getNodeValue(),Header.getValue());	
				    		
				    	}
					}
				    
				
			} catch (Exception e) { 
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
			

}
	}
	
	 
		  

	/**
	 * Generate json list.
	 *
	 * @param Data the data
	 * @return the list
	 */
	private List<String> generateJsonList(DataTable Data) {
		Gson gson = new Gson();
		List<String> TestDataLHM = new ArrayList<String>();
		//List<Map<String, String>> Datavalue = Data.asMaps(String.class, String.class);
		List<Map<String, String>> Datavalue = miscValidate.readsystemvariable(Data);
		for (Map<String, String> Testdata : Datavalue) {
			System.out.println(Data.topCells());
			LinkedHashMap<String, String> e = new LinkedHashMap<String, String>();

			for (String s : Data.topCells()) {
				if (s.trim().equals("Store")) {
					System.setProperty("t" + s, "");
				} else if (s.trim().equals("File")) {
					String ee = null;
					ee = cf.fileToString(System.getProperty("user.dir") + "\\src\\it\\resources\\com\\cisco\\lms\\" + Testdata.get("File").trim());
					ee = gson.toJson(ee);
					ee = cf.strToJson(ee);

					TestDataLHM.add(ee);
					System.out.println("File read Json" + ee);
				} else if (s.trim().equals("Replace")) {
					String rrc[] = Testdata.get(s).split(",");
					for (int i = 0; i < TestDataLHM.size(); i++) {
						for (int k = 0; k < rrc.length; k++) {
							String[] rr = rrc[k].split("=");
							TestDataLHM.set(i, TestDataLHM.get(i).replace(rr[0], readVariable(rr[1])));
						}
					}

				}

				else {
					e.put(s, Testdata.get(s));
					TestDataLHM.add(gson.toJson(e));
					System.out.println(gson.toJson(e));
				}

			}

		}
		return TestDataLHM;
	}

	/**
	 * Frame_the_ url.
	 *
	 * @param Data the data
	 */
	@Given("^Frame the Url$")
	public void frame_the_Url(DataTable Data) {
		
		List<String> valueList = generateURL(Data);
		for (String s : valueList)
			strURL = s.trim();
		System.out.println("URL:" + strURL);
		Hooks.scenario.write(strURL);
	}

	
	@Given("^SD details$")
	public void sd_details() {
		sddetails.printSD(miscValidate.readsystemvariable("LMS.SD.IP"));
	}
	
	@Given("^Calculate the acref \"(.*?)\"$")
	public void calculateAcRef(String sourceID)
	{
		String sourcehex = Integer.toHexString(Integer.parseInt(sourceID));
		String caDomainID =miscValidate.readsystemvariable("tCA_DOMAIN_ID");
		String caDomainIDhex = String.format("%04X",Integer.parseInt(Integer.toHexString(Integer.parseInt(caDomainID))));
		String AcRef = "0B04"+caDomainIDhex+sourcehex;
		AcRef=AcRef.toUpperCase();
		System.setProperty("tacref", AcRef.trim());
		
	}
	
	@And("^estabilish a connection to queues (.*)$")
	public void estabilish_a_rabbit_mq_connection(String queName) throws IOException, InterruptedException
	{
		System.out.println("Starting queue connection for:");
		
		factory.setHost(System.getProperty("RMQ_BROKER_HOST"));
	    factory.setUsername(System.getProperty("RMQ_USER_NAME"));
	    factory.setPassword(System.getProperty("RMQ_PASSWORD"));
	    connection = factory.newConnection();
	    channel = connection.createChannel();
	    String exchangeName = System.getProperty("RMQ_PKC_INTERNAL_DEFAULT_DIRECT_EXCHANGE");	    
		channel.exchangeDeclare(exchangeName, System.getProperty("RMQ_DIRECT_EXCHANGE_TYPE"), true); 
    	if(queName.equalsIgnoreCase("HandleAuth"))
    	{
        	QUEUE_NAME1 = System.getProperty("RMQ_PKC_M104_HANDLE_AUTH_CODE");
    		channel.queueBind(QUEUE_NAME1, exchangeName, "");
    	}		
	}
	
	@And("^verify messages are being sent to queue and contains (.*) $")
	public void verify_the_messages_are_being_sent_to_queue(String expectedMsg1, String expectedMsg2) 
			throws IOException, InterruptedException 
	{
		System.out.println("Starting reading messages for:");
	    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
	    Consumer consumer = new DefaultConsumer(channel) {
	        @Override
	        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
	            throws IOException {
	          String message = new String(body, "UTF-8");
	          try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	          System.out.println(" [x] Received '" + envelope.getRoutingKey() + "':'" + message + "'");
	          msgRcvd = message + msgRcvd;
	        }
	      };
	      channel.basicConsume(QUEUE_NAME1, false, consumer);
	      Thread.sleep(180000);
	      channel.close();
	      connection.close();
		  assertTrue("Message consumed does  not contain generateAC queue message as expected",msgRcvd.contains(expectedMsg1));	      
	}
}
