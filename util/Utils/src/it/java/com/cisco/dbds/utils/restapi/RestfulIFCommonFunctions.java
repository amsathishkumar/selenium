/**
######################################################
 Copyright (c) 2011-2013 by sat, Inc.
######################################################
 */
package com.cisco.dbds.utils.restapi;

import static org.junit.Assert.assertNotEquals;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpMessage;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


// TODO: Auto-generated Javadoc
/**
 * RestfulIFCommonFunctions consist of functions like invoking the REST API
 * through Rest Url, <br>
 * reading the status code and response message for any Rest call made <br>.
 *
 * @author sarrawat
 */
public class RestfulIFCommonFunctions {

	/**
	 * getRESTAPIResponse method executes the requested method of a REST Service.
	 *
	 * @param url            (Rest URI)
	 * @param httpAction            (GET/POST/DELETE/PUT)
	 * @param strXMLFilename the str xml filename
	 * @param httpType the http type
	 * @param contentType the content type
	 * @return method returns the response message of executed action
	 */
	public HttpResponse getRESTAPIResponse(String url, String httpAction,
			String strXMLFilename, String httpType, String contentType) 
			{
		HttpResponse response = null;
		try {
			DefaultHttpClient client = null;
			try {
				client = chooseHttpClient(httpType);
			} catch (KeyManagementException | UnrecoverableKeyException
					| NoSuchAlgorithmException | KeyStoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (httpAction.equals("GET")) {
				HttpGet getRequest = new HttpGet(url);
				getRequest = chooseContentType(getRequest, contentType);
				response = client.execute(getRequest);
			} else if (httpAction.equals("PUT")) {
				HttpPut getRequest = new HttpPut(url);
				getRequest = chooseContentType(getRequest, contentType);
				StringEntity input = new StringEntity(strXMLFilename);
				//System.out.println("Request"+strXMLFilename);
				getRequest.setEntity(input);
				response = client.execute(getRequest);
			} else if (httpAction.equals("DELETE")) {
				HttpDelete getRequest = new HttpDelete(url);
				getRequest = chooseContentType(getRequest, contentType);
				response = client.execute(getRequest);
			} else if (httpAction.equals("POST")) {
				HttpPost getRequest = new HttpPost(url);
				getRequest = chooseContentType(getRequest, contentType);
				StringEntity input = new StringEntity(strXMLFilename);
				//System.out.println("Request"+strXMLFilename);
				getRequest.setEntity(input);
				response = client.execute(getRequest);
			}
			System.out.println(">> Reponse:\n "+response);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
	
		} catch (IOException e) {
		  System.out.print("Server is not responding for the httpAction");
		 // System.exit(1);
		}
		return response;
	}

	
	
	public HttpResponse getRESTAPIResponse(String url, String httpAction,
		String strXMLFilename, String httpType, Map <String, String> contentType) 
		{
	    
	    System.out.println(strXMLFilename);
	HttpResponse response = null;
	try {
		DefaultHttpClient client = null;
		try {
			client = chooseHttpClient(httpType);
		} catch (KeyManagementException | UnrecoverableKeyException
				| NoSuchAlgorithmException | KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (httpAction.equals("GET")) {
			HttpGet getRequest = new HttpGet(url);
			getRequest = chooseContentType(getRequest, contentType);
			response = client.execute(getRequest);
		} else if (httpAction.equals("PUT")) {
			HttpPut getRequest = new HttpPut(url);
			getRequest = chooseContentType(getRequest, contentType);			
			StringEntity input = new StringEntity(strXMLFilename);
			//System.out.println("Request"+strXMLFilename);
			getRequest.setEntity(input);
			response = client.execute(getRequest);
			
		} else if (httpAction.equals("DELETE")) {
			HttpDelete getRequest = new HttpDelete(url);
			getRequest = chooseContentType(getRequest, contentType);
			response = client.execute(getRequest);
		} else if (httpAction.equals("POST")) {
			HttpPost getRequest = new HttpPost(url);
			getRequest = chooseContentType(getRequest, contentType);
			StringEntity input = new StringEntity(strXMLFilename);
			//System.out.println("Request"+strXMLFilename);
			getRequest.setEntity(input);
			response = client.execute(getRequest);
		}
		System.out.println(">> Reponse:\n "+response);
	} catch (ClientProtocolException e) {
		e.printStackTrace();

	} catch (IOException e) {
	  System.out.print("Server is not responding for the httpAction");
	}
	return response;
}
	
	
	 /**
 	 * Gets the RESTAPI response.
 	 *
 	 * @param url the url
 	 * @param httpAction the http action
 	 * @param strXMLFilename the str xml filename
 	 * @param httpType the http type
 	 * @param contentType the content type
 	 * @param authtype the authtype
 	 * @return the RESTAPI response
 	 */
 	public HttpResponse getRESTAPIResponse(String url, String httpAction,
			String strXMLFilename, String httpType,  Map <String, String> contentType, String authtype) 
			{
			HttpResponse response = null;
			CloseableHttpClient client = null;
			System.out.println("This is local REST API");
		 try{

		String A [] = authtype.split("/");
	
			if (httpAction.equals("GET")) {
				UsernamePasswordCredentials creds = new UsernamePasswordCredentials(A[1], A[2]);
				HttpGet getRequest = new HttpGet(url);
//				getRequest.addHeader("Accept",contentType );
//				getRequest.addHeader("Content-type",contentType);	
				getRequest = chooseContentType(getRequest, contentType);
			    System.out.println("content type"+contentType);
			    
				HttpContext context=null;
				org.apache.http.Header bs = new BasicScheme().authenticate(creds, getRequest, context);
				getRequest.addHeader("Authorization", bs.getValue());
				client = chooseHttpClient(httpType);
			    //client = HttpClientBuilder.create().build();
				response = client.execute(getRequest);
			} else if (httpAction.equals("PUT")) {
				HttpPut getRequest = new HttpPut(url);
			   	
			    UsernamePasswordCredentials creds = new UsernamePasswordCredentials(A[1], A[2]);
				getRequest = chooseContentType(getRequest, contentType);
				 System.out.println("content type"+contentType);
					StringEntity input = new StringEntity(strXMLFilename);
					System.out.println("Request"+strXMLFilename);
					getRequest.setEntity(input);
					
					HttpContext context=null;
					org.apache.http.Header bs = new BasicScheme().authenticate(creds, getRequest, context);
					getRequest.addHeader("Authorization", bs.getValue());
					client = chooseHttpClient(httpType);
				   //client = HttpClientBuilder.create().build();
					response = client.execute(getRequest);
					
			} else if (httpAction.equals("DELETE")) {
				System.out.println("Url is "+url);
				
				HttpDelete getRequest = new HttpDelete(url);
				 UsernamePasswordCredentials creds = new UsernamePasswordCredentials(A[1], A[2]);
				 HttpContext context=null;
				 org.apache.http.Header bs = new BasicScheme().authenticate(creds, getRequest, context);
				 getRequest.addHeader("Authorization", bs.getValue());
				client = chooseHttpClient(httpType);
				//getRequest = chooseContentType(getRequest, contentType);
				response = client.execute(getRequest);
			} else if (httpAction.equals("POST")) {
			   	
			    UsernamePasswordCredentials creds = new UsernamePasswordCredentials(A[1], A[2]);
				HttpPost getRequest = new HttpPost(url);
				System.out.println(url);
//				//getRequest.addHeader("Accept",contentType );
//				getRequest.addHeader("Content-type",contentType);
//				//getRequest = chooseContentType(getRequest, contentType);
				
				getRequest = chooseContentType(getRequest, contentType);
			    System.out.println("content type"+contentType);
				StringEntity input = new StringEntity(strXMLFilename);
				System.out.println("Request"+strXMLFilename);
				getRequest.setEntity(input);
				
				HttpContext context=null;
				org.apache.http.Header bs = new BasicScheme().authenticate(creds, getRequest, context);
				getRequest.addHeader("Authorization", bs.getValue());
				client = chooseHttpClient(httpType);
			   //client = HttpClientBuilder.create().build();
				response = client.execute(getRequest);
				
			}
			
			
			
			System.out.println(">> Reponse is this:\n "+response);
			System.out.println("Resonse code is"+response.getStatusLine());
			//response.getEntity().writeTo(System.out);
		 }
		 catch (Exception e){
			 System.out.println("Rest"+e);
		 }
		    return response;
	}
	


//	public HttpResponse getRESTAPIResponseforHttpsAuth(String url, String httpAction,
//			String strXMLFilename, String httpType, String contentType, String authtype) throws AuthenticationException, ClientProtocolException, IOException, KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException 
//			{
//		HttpResponse response = null;
//		CloseableHttpClient client = null;
//		String A [] = authtype.split("/");
//		System.out.println("in the REST API Method");
//	
//			if (httpAction.equals("GET")) {
//				UsernamePasswordCredentials creds = new UsernamePasswordCredentials(A[1], A[2]);
//				HttpGet getRequest = new HttpGet(url);
//				//getRequest = chooseContentType(getRequest, contentType);
//				getRequest.addHeader("Accept",contentType );
//				getRequest.addHeader("Content-type",contentType);
//				//getRequest = chooseContentType(getRequest, contentType);
//				HttpContext context=null;
//				org.apache.http.Header bs = new BasicScheme().authenticate(creds, getRequest, context);
//				getRequest.addHeader("Authorization", bs.getValue());
//			    client = chooseHttpClient(httpType);
//				response = client.execute(getRequest);
//			} else if (httpAction.equals("PUT")) {
//				HttpPut getRequest = new HttpPut(url);
//				getRequest = chooseContentType(getRequest, contentType);
//				response = client.execute(getRequest);
//			} else if (httpAction.equals("DELETE")) {
//				HttpDelete getRequest = new HttpDelete(url);
//				getRequest = chooseContentType(getRequest, contentType);
//				response = client.execute(getRequest);
//			} else if (httpAction.equals("POST")) {
//			   	
//			    UsernamePasswordCredentials creds = new UsernamePasswordCredentials(A[1], A[2]);
//			    System.out.println("username:"+A[1]);
//			    System.out.println("Password:"+A[2]);
//			    HttpPost getRequest = new HttpPost(url);
//				System.out.println(url);
//				getRequest.addHeader("Accept",contentType );
//				getRequest.addHeader("Content-type",contentType);
//				//getRequest = chooseContentType(getRequest, contentType);
//			    System.out.println("content type"+contentType);
//				StringEntity input = new StringEntity(strXMLFilename);
//				System.out.println("Request"+strXMLFilename);
//				getRequest.setEntity(input);
//				HttpContext context=null;
//				org.apache.http.Header bs = new BasicScheme().authenticate(creds, getRequest, context);
//				getRequest.addHeader("Authorization", bs.getValue());
//			    client = chooseHttpClient(httpType);
//				response = client.execute(getRequest);
//				
//			}
//			System.out.println(">> Reponse is this:\n "+response);
//			System.out.println("Resonse code is"+response.getStatusLine());
//			//response.getEntity().writeTo(System.out);
//		    return response;
//	}
	
	/**
 * chooseHttpClient method will return the DefaultHttpClient object based on
 * the http type(http or https) passed in parameter.
 *
 * @param httpType            ( http or https request)
 * @return the HttpCLient object
 * @throws KeyManagementException the key management exception
 * @throws UnrecoverableKeyException the unrecoverable key exception
 * @throws NoSuchAlgorithmException the no such algorithm exception
 * @throws KeyStoreException the key store exception
 */
	public DefaultHttpClient chooseHttpClient(String httpType)
			throws KeyManagementException, UnrecoverableKeyException,
			NoSuchAlgorithmException, KeyStoreException {
		DefaultHttpClient client;
		if (httpType.equalsIgnoreCase("https")) {
			TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
				@Override
				public boolean isTrusted(X509Certificate[] certificate,
						String authType) {
					return true;
				}
			};
			SSLSocketFactory sf = new SSLSocketFactory(acceptingTrustStrategy,
					SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("https", 443, sf));
			ClientConnectionManager ccm = new PoolingClientConnectionManager(
					registry);
			client = new DefaultHttpClient(ccm);
		} else
			client = new DefaultHttpClient();

		return client;
	}

//	public CloseableHttpClient chooseHttpClient1(String httpType,String authtype)
//			throws KeyManagementException, UnrecoverableKeyException,
//			NoSuchAlgorithmException, KeyStoreException {
//		CloseableHttpClient client = null;
//		if (httpType.equalsIgnoreCase("https")) {
//			TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
//				@Override
//				public boolean isTrusted(X509Certificate[] certificate,
//						String authType) {
//					return true;
//				}
//			};
//			SSLSocketFactory sf = new SSLSocketFactory(acceptingTrustStrategy,
//					SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//			SchemeRegistry registry = new SchemeRegistry();
//			registry.register(new Scheme("https", 443, sf));
//			ClientConnectionManager ccm = new PoolingClientConnectionManager(
//					registry);
//			//client = new DefaultHttpClient(ccm);
//		} else
//			// client = new DefaultHttpClient();
//		if (authtype.contains("basicusrpwd"))
//		{
//		String A [] = authtype.split("/");
//		CredentialsProvider provider = new BasicCredentialsProvider();
//		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("admin", "itaas123");
//		provider.setCredentials(AuthScope.ANY, credentials);
//		client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
//		//client.getCredentialsProvider().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT), new UsernamePasswordCredentials(A[1], A[2]));
//		//System.out.println(authtype);
//		}
//		return client;
//	}

	/**
 * chooseContentType method will accept the HttpMessage type like HttpGet
 * etc and based on content Type(json or xml) passed will set the headers.
 *
 * @param <T> the generic type
 * @param getRequest the get request
 * @param contentType the content type
 * @return HttpGet/HttpPost/HttpPut/HttpDelete object with specified headers
 *         added
 */
	public <T> T chooseContentType(T getRequest, String contentType) {
		if (contentType.equalsIgnoreCase("application/json")) {
			((HttpMessage) getRequest).addHeader("Accept", "application/json");
			((HttpMessage) getRequest).addHeader("Content-type",
					"application/json");
		} else if (contentType.equalsIgnoreCase("application/xml")) {
			((HttpMessage) getRequest).addHeader("Accept", "application/xml");
			((HttpMessage) getRequest).addHeader("Content-type",
					"application/xml");
		} else if (contentType.equalsIgnoreCase("text/xml"))
			((HttpMessage) getRequest).addHeader("Accept", "text/xml");
		((HttpMessage) getRequest).addHeader("Content-type", "text/xml");
		return getRequest;
	}

	/**
	 * verifyStatusCode will read the response of HTTP request made and will
	 * read the response Status code.
	 *
	 * @param response            (response message from HTTP methods)
	 * @return response Status code for the HTTP request made
	 */
	public static int readStatusCode(HttpResponse response) {
		System.out.println(response.getStatusLine());
		return response.getStatusLine().getStatusCode();
	}

	/**
	 * readContent method will buffer the response message from HTTP methods <br>
	 * to a BufferedReader object <br>.
	 *
	 * @param response            (response message from HTTP methods)
	 * @return return the BufferedReader object
	 * @throws IllegalStateException the illegal state exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
//	public static BufferedReader readContent(HttpResponse response)
//			throws IllegalStateException, IOException {
//		HttpEntity entity = response.getEntity();
//		if (entity == null) {
//			return null;
//		}
//		System.out.println("Response content length: "
//				+ entity.getContentLength());
//		BufferedReader rd = new BufferedReader(new InputStreamReader(
//				entity.getContent()));
//		return rd;
//	}

	
	public String readContent(HttpResponse response)
			throws IllegalStateException, IOException {
		HttpEntity entity = response.getEntity();
		if (entity == null) {
			return null;
		}
		System.out.println("Response content length: "
				+ entity.getContentLength());
		BufferedReader rd = new BufferedReader(new InputStreamReader(
				entity.getContent()));
		
		StringBuffer buffer = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			buffer.append(line).append("\n");
		}
		String str = buffer.toString().trim();
		System.out.println("Entire content"+str);
		return str;	
		//return rd.readLine();
	}
	
	
	
	
//	/**
//	 * verifyContent method converts the data in BufferedReader to string
//	 * 
//	 * @param readResponse
//	 * @return return the String containing response
//	 * @throws IOException
//	 */
//	public String verifyContent(BufferedReader readResponse) throws IOException {
//		StringBuffer buffer = new StringBuffer();
//
//		String line = "";
//		while ((line = readResponse.readLine()) != null) {
//			buffer.append(line).append("\n");
//		}
//		String str = buffer.toString();
//		System.out.println("verify content"+str);
//		return str;
//	}


	
//	/**
//	 * readXml method will read the XML inputStream(from UTF-8 byte array) and
//	 * will return the Text(String) value <br>
//	 * for the specified Node Name <br>
//	 * 
//	 * @param line
//	 * @param tagName
//	 * @return
//	 * @throws IOException
//	 */
//	public static List<String> readXml(String str, String tagName)
//			throws IOException {
//		DocumentBuilder docBuilder = null;
//		Document doc = null;
//
//		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
//				.newInstance();
//		int indice = 0;
//		char symbole = '<';
//		while (indice < str.length()) {
//			if (str.charAt(indice) != symbole)
//				indice++;
//			else
//				break;
//		}
//		InputStream inpStream = new ByteArrayInputStream(str.substring(indice,
//				str.length()).getBytes("UTF-8"));
//		assertNotEquals("Response input length is 0", inpStream.available(), 0);
//		try {
//			docBuilder = docBuilderFactory.newDocumentBuilder();
//		} catch (ParserConfigurationException e) {
//			e.printStackTrace();
//		}
//		try {
//			doc = docBuilder.parse(inpStream);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		// normalize text representation
//		doc.getDocumentElement().normalize();
//		String tagRoot = doc.getDocumentElement().getNodeName();
//		System.out.println("Root element of the doc is " + tagRoot);
//		NodeList listOfParentNodes = doc.getElementsByTagName(tagRoot);
//		System.out.println("Total no of head : "
//				+ listOfParentNodes.getLength());
//		List<String> nodeTextval = new ArrayList<String>();
//		for (int ind = 0; ind < listOfParentNodes.getLength(); ind++) {
//			Node firstParentNode = listOfParentNodes.item(ind);
//			if (firstParentNode.getNodeType() == Node.ELEMENT_NODE) {
//				Element firstElement = (Element) firstParentNode;
//				NodeList firstNameList = firstElement
//						.getElementsByTagName(tagName);
//				Element firstNameElement = (Element) firstNameList.item(0);
//				NodeList textFNList = firstNameElement.getChildNodes();
//				if (textFNList.getLength() == 0) {
//					return null;
//				}
//				nodeTextval.add(((Node) textFNList.item(0)).getNodeValue()
//						.trim());
//			}
//		}
//		return nodeTextval;
//
//	}
//
//	/**
//	 * extractFieldValues method will read the json Response and generates the
//	 * string array based on Key Field name passed.
//	 * 
//	 * @param String
//	 *            jsonText
//	 * @param String
//	 *            key_field
//	 * @return string array
//	 * @throws IOException
//	 * @throws JSONException
//	 */
//	public List<String> extractFieldValues(String jsonText, String key_field)
//			throws IOException, JSONException {
//		JSONArray json = new JSONArray(jsonText);
//		assertNotEquals("The json response is empty", json.length(), 0);
//		List<String> tagVal = new ArrayList<String>();
//		for (int ind = 0; ind < json.length(); ind++) {
//			JSONObject json_response = (JSONObject) json.get(ind);
//			tagVal.add((String) json_response.get(key_field));
//		}
//		System.out.println(tagVal);
//		return tagVal;
//	}
//
//	/**
//	 * convert xml file to a string string filepath should be passed.
//	 * 
//	 * @param String
//	 *            xmlText
//	 */
//	public String convertXMLFileToString(String fileName) {
//		try {
//			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
//					.newInstance();
//			documentBuilderFactory.setIgnoringElementContentWhitespace(true);
//			InputStream inputStream = new FileInputStream(new File(fileName));
//			org.w3c.dom.Document doc = documentBuilderFactory
//					.newDocumentBuilder().parse(inputStream);
//			StringWriter stw = new StringWriter();
//			Transformer serializer = TransformerFactory.newInstance()
//					.newTransformer();
//			serializer.transform(new DOMSource(doc), new StreamResult(stw));
//			return stw.toString().trim();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	
//	/**
//	 * updateXMLFile method will read xml file and update the tagelement with the value passed
//	 * 
//	 * @param String
//	 *            xmlText
//	 * @param String
//	 *            tagName
//	 * @param String
//	 *            value
//	 */
//	public static void updateXMLFile(String fileName, String tagName,String value) {
//		try {
//			System.out.println("updateXMLFile():");
//			DocumentBuilderFactory docFactory = DocumentBuilderFactory
//					.newInstance();
//			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
//			Document doc = docBuilder.parse(fileName);
//
//			// Get the root element
//			Node company = doc.getFirstChild();
//			String tagRoot = doc.getDocumentElement().getNodeName();
//			NodeList listOfParentNodes = doc.getElementsByTagName(tagName);
//			for (int ind = 0; ind < listOfParentNodes.getLength(); ind++) {
//				Node firstParentNode = listOfParentNodes.item(ind);
//				if (firstParentNode.getNodeType() == Node.ELEMENT_NODE) {
//					System.out.println(tagName);
//					System.out.println(value);
//					firstParentNode.setTextContent(value);
//				}
//
//			}
//			TransformerFactory transformerFactory = TransformerFactory
//					.newInstance();
//			Transformer transformer = transformerFactory.newTransformer();
//			DOMSource source = new DOMSource(doc);
//			StreamResult result = new StreamResult(new File(fileName));
//			transformer.transform(source, result);
//			System.out.println("Done");
//
//		} catch (ParserConfigurationException pce) {
//			pce.printStackTrace();
//		} catch (TransformerException tfe) {
//			tfe.printStackTrace();
//		} catch (IOException ioe) {
//			ioe.printStackTrace();
//		} catch (SAXException sae) {
//			sae.printStackTrace();
//		}
//	}
//
//	
//	/**
//	 * readInputXMLFile method will read input xml file stored in resources and returns the value 
//	 * of tagElement which is passed
//	 * @param String
//	 *            tagName
//	 * @param String
//	 *            value
//	 */
//	public String readInputXMLFile(String fileName, String tagName) {
//		try {
//			System.out.println("readInputXMLFile():");
//			String returnval = "";
//			DocumentBuilderFactory docFactory = DocumentBuilderFactory
//					.newInstance();
//			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
//			Document doc = docBuilder.parse(fileName);
//
//			// Get the root element
//			Node company = doc.getFirstChild();
//			String tagRoot = doc.getDocumentElement().getNodeName();
//			NodeList listOfParentNodes = doc.getElementsByTagName(tagName);
//			for (int ind = 0; ind < listOfParentNodes.getLength(); ind++) {
//				Node firstParentNode = listOfParentNodes.item(ind);
//				if (firstParentNode.getNodeType() == Node.ELEMENT_NODE) {
//					returnval = firstParentNode.getTextContent();
//				}
//
//			}
//			TransformerFactory transformerFactory = TransformerFactory
//					.newInstance();
//			Transformer transformer = transformerFactory.newTransformer();
//			DOMSource source = new DOMSource(doc);
//			StreamResult result = new StreamResult(new File(fileName));
//			transformer.transform(source, result);
//			System.out.println("Done");
//			return returnval;
//		} catch (ParserConfigurationException pce) {
//			pce.printStackTrace();
//		} catch (TransformerException tfe) {
//			tfe.printStackTrace();
//		} catch (IOException ioe) {
//			ioe.printStackTrace();
//		} catch (SAXException sae) {
//			sae.printStackTrace();
//		}
//		return null;
//
//	}
//
//	
//	
//	/**
//	 * removeChildNode method will remove the childNode based on the tagElement which is passed and 
//	 * it will write the changes to the file
//	 * @param String
//	 *            fileName
//	 * @param String
//	 *            tagName
//	 * @throws ParserConfigurationException
//	 * @throws SAXException
//	 * @throws IOException
//	 * @throws TransformerException
//	 * @throws XPathExpressionException
//	 */
//	public static void removeChildNode(String fileName, String tagName)
//			throws ParserConfigurationException, SAXException, IOException,
//			TransformerException, XPathExpressionException {
//
//		System.out.println("removeChildNode():");
//		DocumentBuilderFactory docFactory = DocumentBuilderFactory
//				.newInstance();
//		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
//		Document doc = docBuilder.parse(fileName);
//		Element element = (Element) doc.getElementsByTagName(tagName).item(0);
//		element.getParentNode().removeChild(element);
//		XPath xp = XPathFactory.newInstance().newXPath();
//		NodeList nl = (NodeList) xp.evaluate("//text()[normalize-space(.)='']",
//				doc, XPathConstants.NODESET);
//
//		for (int i = 0; i < nl.getLength(); ++i) {
//			Node node = nl.item(i);
//			node.getParentNode().removeChild(node);
//		}
//		doc.normalize();
//		TransformerFactory transformerFactory = TransformerFactory
//				.newInstance();
//		Transformer transformer = transformerFactory.newTransformer();
//		DOMSource source = new DOMSource(doc);
//		StreamResult result = new StreamResult(new File(fileName));
//		transformer.transform(source, result);
//		System.out.println("Done");
//
//	}
//
//	
//	/**
//	 * addChildNode method will add the childNode based on the tagElement which is passed and 
//	 * it will write the changes to the file
//	 * @param String
//	 *            fileName
//	 * @param String
//	 *            tagName
//	 * @throws ParserConfigurationException
//	 * @throws SAXException
//	 * @throws IOException
//	 * @throws TransformerException
//	 * @throws XPathExpressionException
//	 */
//	public static void addChildNode(String fileName, String tagName)
//			throws ParserConfigurationException, SAXException, IOException,
//			TransformerException, XPathExpressionException {
//
//		System.out.println("addChildNode():");
//		DocumentBuilderFactory docFactory = DocumentBuilderFactory
//				.newInstance();
//		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
//		Document doc = docBuilder.parse(fileName);
//		Node company = doc.getFirstChild();
//		String val = doc.getFirstChild().getNodeName();
//		System.out.println(val);
//		Element blobKey_E = doc.createElement(tagName);
//		blobKey_E.appendChild(doc
//				.createTextNode("4141"));
//		company.appendChild(blobKey_E);
//
//		TransformerFactory transformerFactory = TransformerFactory
//				.newInstance();
//		Transformer transformer = transformerFactory.newTransformer();
//		DOMSource source = new DOMSource(doc);
//		StreamResult result = new StreamResult(new File(fileName));
//		transformer.transform(source, result);
//		System.out.println("Done");
//
//	}
//	
	/**
 * Choose content type.
 *
 * @param <T> the generic type
 * @param getRequest the get request
 * @param contentType the content type
 * @return the t
 */
public <T> T chooseContentType(T getRequest, Map <String, String> contentType) {

		Iterator<String> keySetIterator = contentType.keySet().iterator();
		while(keySetIterator.hasNext()){
		
		  String skey = keySetIterator.next();
      	  String skeyvalue = (String) contentType.get(skey);
          System.out.println("Value of "+skey+" is: "+skeyvalue);      
          ((HttpMessage) getRequest).addHeader(skey,skeyvalue);
		}

	
		
		return getRequest;
	}

}
