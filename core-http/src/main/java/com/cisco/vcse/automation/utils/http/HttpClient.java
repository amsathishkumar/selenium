/**
 * Copyright (c) $2015 by Cisco Systems, Inc.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Cisco Systems,  ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Cisco Systems.
 * 
 * The HttpClient allows uniform access to any HTTP or HTTPS Service.  
 * The configuration for the service can be specified via HttpClientBuilder or HttpClientBuilder
 *
 * @Project: core-HTTP
 * @Author: kosk
 * @Version: 1.0.0.0
 * @Description:  An HttpClient for Integration Test that allows uniform access to any HTTP Service.
 * @Date updated: 6/3/2015
 */

package com.cisco.vcse.automation.utils.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.cisco.vcse.automation.utils.datatype.CoreResponse;
import com.cisco.vcse.automation.utils.datatype.CoreRuntimeException;
import com.cisco.vcse.automation.utils.datatype.NoOpTrustManager;
import com.cisco.vcse.automation.utils.helpers.HttpResponseMapper;
import com.cisco.vcse.automation.utils.logging.LogHandler;

public class HttpClient {

	private final static String CLASS_NAME = "HttpClient: ";	
	
	// Target URL
	private String targetURL;
	// Control use of HTTPS
	private boolean isHttps = false;
	// Cryptographic Protocol
	private String cryptoProtocol = "TLSv1";
	// Cipher Suite
	private String[] cipherSuite = null;
	// Resource location of the truststore with server's root CA certificate(s) in JKS format
	private String truststore;
	// Password to access the truststore file
	private String truststorePasswd;
	// Control use of Client Authentication
	private boolean isClientAuthEnabled = false;
	// Resource location of the keystore with the Client key and certificate in JKS format
	private String keystore;
	// Password to access the keystore file
	private String keystorePasswd;
	// Map that stores the HTTP Request headers
	private Map<String, String> requestHeaders;
	
	//UserId for basic authentication
	private String basicAuthId; 
	//Password for basic authentication
	private String basicAuthPwd;
	//placeholder for basicAutheticatin set or no
	private boolean isBasicAuth = false;

	/**
	 * private constructor for http request. This is also internally used to initialize parameters for https request
	 * @param targetUrl
	 * @param headersMap
	 * @param basicAuthId
	 * @param basicAuthPwd
	 */
	private HttpClient(final String targetUrl, final Map<String, String> headersMap,
			final  String basicAuthId, final String basicAuthPwd) {
		if (!targetUrl.startsWith("http://")) {
			throw new CoreRuntimeException("Target URL does not start with http://!");
		}
		this.isHttps=false;
		this.targetURL=targetUrl;
		this.requestHeaders=headersMap;
		
		if(StringUtils.isNotBlank(basicAuthId) && StringUtils.isNotBlank(basicAuthPwd)){
			this.isBasicAuth = true;
		}else{
			this.isBasicAuth = false;
		}
		this.basicAuthId=basicAuthId;
		this.basicAuthPwd=basicAuthPwd;
	}
	
    /**
     * private Constructor used for https
     * @param targetURL
     * @param headersMap
     * @param basicAuthId
     * @param basicAuthPwd
     * @param cryptoProtocol
     * @param cipherSuite
     * @param truststore
     * @param truststorePasswd
     * @param keystore
     * @param keystorePasswd
     * @param isClientAuthEnabled
     */
	private HttpClient(final String targetURL,final  Map<String, String> headersMap,
			final  String basicAuthId, final String basicAuthPwd,
			final String cryptoProtocol,
			final String[] cipherSuite, 
			final String truststore, String truststorePasswd, 
			final String keystore, String keystorePasswd, 
			boolean isClientAuthEnabled) {
		if (!targetURL.startsWith("https://")) {
			throw new CoreRuntimeException("Target URL does not start with https://!");
		}
		
		this.isHttps=true;
		this.targetURL=targetURL;
		this.requestHeaders=headersMap;
		if(StringUtils.isNotBlank(basicAuthId) && StringUtils.isNotBlank(basicAuthPwd)){
			this.isBasicAuth = true;
		}else{
			this.isBasicAuth = false;
		}
			
		this.basicAuthId=basicAuthId;
		this.basicAuthPwd=basicAuthPwd;
				
		setCryptoProtocol(cryptoProtocol);
		this.cipherSuite=cipherSuite;		
		
		this.truststore=truststore;
		this.truststorePasswd=truststorePasswd;
		this.keystore=keystore;
		this.keystorePasswd=keystorePasswd;
		
		this.isClientAuthEnabled=isClientAuthEnabled;

	}
	

	
	/**
	 * Builder to work with http calls;
	 * To work on https please use HttpsClientBuilder
	 * @author kosk
	 *
	 */
	public static class HttpClientBuilder {
		private String targetURL;
		private String basicAuthId;
		private String basicAuthPwd;
		private Map<String, String> requestHeaders = new HashMap<String, String>();

		public HttpClientBuilder withTargetURL(final String targetURL){
			this.targetURL = targetURL;
			return this;
		}
		/**
		 * This goes as "Content-Type" in http request header.
		 * @param contentType : example "application/json", "application/xml" etc
		 * @return HttpClientBuilder
		 */
		public HttpClientBuilder withContentType(final String contentType){
			this.requestHeaders.put("Content-Type",contentType);
			return this;
		}
		/**
		 * This goes as "Accept" in http request header
		 * @param accept : example "application/json", "application/xml" etc
		 * @return HttpClientBuilder
		 */
		public HttpClientBuilder withExpectedOutputFormat(final String accept){
			this.requestHeaders.put("Accept",accept);
			return this;
		}
		/**
		 * Use this one to pass additional request header name value pairs
		 * @param requestHeader : Map of request Header names and values in string format
		 * @return HttpClientBuilder
		 */
		public HttpClientBuilder withRequestHeader(final Map<String, String> requestHeader){
			this.requestHeaders.putAll(requestHeader);
			return this;
		}
		
		/**
		 * Use this one if there is basic name/value authentication required by underlying service
		 * @param userId : HTTP basic authentication username
		 * @param passwd : HTTP basic authentication username's associated password
		 * @return HttpClientBuilder
		 */
		public HttpClientBuilder withBasicAuthentication(final String userId, final String passwd){			
			this.basicAuthId = userId;
			this.basicAuthPwd = passwd;	
			return this;
		}
		public HttpClient build(){
			return new HttpClient(this.targetURL, this.requestHeaders, this.basicAuthId, this.basicAuthPwd);
		}
	}
	
	/**
	 * Builder to work with https calls
	 * To work on http please use HttpClientBuilder
	 * @author kosk
	 *
	 */
	public static class HttpsClientBuilder extends HttpClientBuilder {

		private String cryptoProtocol = "TLSv1";
		// Cipher Suite
		private String[] cipherSuite = null;
		// Resource location of the truststore with server's root CA certificate(s) in JKS format
		private String truststore;
		// Password to access the truststore file
		private String truststorePasswd;
		// Control use of Client Authentication
		private boolean isClientAuthEnabled = false;
		// Resource location of the keystore with the Client key and certificate in JKS format
		private String keystore;
		// Password to access the keystore file
		private String keystorePasswd;
		
		public HttpsClientBuilder withTargetURL(final String targetURL){
			super.targetURL = targetURL;
			return this;
		}
		
		/**
		 * This goes as "Content-Type" in http request header.
		 * @param contentType : example "application/json", "application/xml" etc
		 * @return HttpsClientBuilder
		 */
		public HttpsClientBuilder withContentType(final String contentType){
			super.requestHeaders.put("Content-Type",contentType);
			return this;
		}

		/**
		 * This goes as "Accept" in http request header
		 * @param accept : example "application/json", "application/xml" etc
		 * @return HttpsClientBuilder 
		 */
		public HttpsClientBuilder withExpectedOutputFormat(final String accept){
			super.requestHeaders.put("Accept",accept);
			return this;
		}
		/**
		 * Use this one to pass additional request header name value pairs
		 * @param requestHeader : Map of Request Header Names and Values in string format 
		 * @return HttpsClientBuilder
		 */
		public HttpsClientBuilder withRequestHeader(final Map<String, String> requestHeader){
			super.requestHeaders.putAll(requestHeader);
			return this;
		}
		
		/**
		 * Use this one if there is basic name/value authentication required by underlying service
		 * @param userId : HTTP basic authentication username
		 * @param passwd : HTTP basic authentication username's associated password
		 * @return HttpsClientBuilder
		 */
		public HttpsClientBuilder withBasicAuthentication(final String userId, final String passwd){			
			super.basicAuthId = userId;
			super.basicAuthPwd = passwd;	
			return this;
		}
		
		public HttpsClientBuilder withCryptoProtocol(final String cryptoProtocol){
			this.cryptoProtocol = cryptoProtocol;
			return this;
		}
		
		public HttpsClientBuilder withCipherSuite(final String[] cipherSuite){
			this.cipherSuite = cipherSuite;
			return this;
		}
		
		/**
		 * provide the path of the trustStore file
		 * @param truststoreFilePath : location of the truststore file
		 * @return HttpsClientBuilder
		 */
		public HttpsClientBuilder withTruststore(final String truststoreFilePath){
			this.truststore = truststoreFilePath;
			return this;
		}
		public HttpsClientBuilder withTruststorePasswd(final String truststorePasswd){
			this.truststorePasswd = truststorePasswd;
			return this;
		}
		
		/**
		 * provide the path of the keystore file
		 * @param keystoreFilePath : location of the keystore file
		 * @return HttpsClientBuilder
		 */
		public HttpsClientBuilder withKeystore(final String keystoreFilePath){
			this.keystore = keystoreFilePath;
			return this;
		}
		public HttpsClientBuilder withKeystorePasswd(final String keystorePasswd){
			this.keystorePasswd = keystorePasswd;
			return this;
		}

		public HttpClient build(){			
			 if(StringUtils.isNotBlank(truststore) && StringUtils.isNotBlank(keystore) &&
					 StringUtils.isNotBlank(truststorePasswd) && StringUtils.isNotBlank(keystorePasswd)){
				this.isClientAuthEnabled = true;
			}
			return new HttpClient(super.targetURL, super.requestHeaders, 
					super.basicAuthId, super.basicAuthPwd, 
					this.cryptoProtocol, this.cipherSuite, 
					this.truststore, this.truststorePasswd, 
					this.keystore, this.keystorePasswd, this.isClientAuthEnabled);
		}
	}
	
	private void setCryptoProtocol(String cryptoProtocol) {
		this.cryptoProtocol = this.verifyCryptoProt(cryptoProtocol);
	}	
	/**
	 * DELETE request to a server
	 * @return CoreResponse
	 */
	public CoreResponse delete() {
		final String METHOD_NAME = "delete(): ";
	
		try(CloseableHttpClient client = getClient();) {
			
			// Instantiate a Delete Request and define the Target URL
			HttpDelete httpDelete = new HttpDelete(this.targetURL);
	
			// Build the HTTP request header from the DeleteersMap
			addHeader(httpDelete);
			
		 	// Execute the request and parse the Response
			return executeRequest(client, httpDelete);
		
		} catch (IOException exp ){
			LogHandler.error(CLASS_NAME + METHOD_NAME + "Exception: "+ exp.getMessage());
			throw new CoreRuntimeException(exp,CLASS_NAME + METHOD_NAME + exp.getMessage());
		}
	}
	
	

	/**
	 * GET request to a server
	 * @return CoreResponse
	 */
	public CoreResponse get() {
		final String METHOD_NAME = "get(): ";
	
		try(CloseableHttpClient client = getClient();) {
			
			// Instantiate a Get Request and define the Target URL
			HttpGet httpGet = new HttpGet(this.targetURL);
			addHeader(httpGet);		
		 	return executeRequest(client, httpGet);
		
		} catch (IOException exp ){
			LogHandler.error(CLASS_NAME + METHOD_NAME + "Exception: "+ exp.getMessage());
			throw new CoreRuntimeException(exp,CLASS_NAME + METHOD_NAME + exp.getMessage());
		}
	}
	
	/**
	 * HEAD request to a server
	 * @return CoreResponse
	 */
	public CoreResponse head(){
		final String METHOD_NAME = "head(): ";
	
		try(CloseableHttpClient client = getClient();) {
			// Instantiate a Head Request and define the Target URL
			HttpRequestBase httpRequestBase = new HttpHead(this.targetURL);
			// Build the HTTP request header from the HeadeMap
			addHeader(httpRequestBase);
			return executeRequest(client, httpRequestBase);
			
		} catch (IOException exp ){
			LogHandler.error(CLASS_NAME + METHOD_NAME + "Exception: "+ exp.getMessage());
			throw new CoreRuntimeException(exp,CLASS_NAME + METHOD_NAME + exp.getMessage());
		}
	}

	/**
	 * OPTIONS request to a server
	 * HTTP or HTTPS shall already be initialized
	 * using either the initHttp or initHttps methods.
	 * @return CoreResponse
	 */
	public CoreResponse options() {
		final String METHOD_NAME = "options(): ";
	
		try(CloseableHttpClient client = getClient();) {
			// Instantiate an Options Request and define the Target URL
			HttpRequestBase httpRequestBase = new HttpOptions(this.targetURL);
			// Build the HTTP request header from the OptionsersMap
			addHeader(httpRequestBase);
			return executeRequest(client, httpRequestBase);
		
		} catch (IOException exp ){
			LogHandler.error(CLASS_NAME + METHOD_NAME + "Exception: "+ exp.getMessage());
			throw new CoreRuntimeException(exp,CLASS_NAME + METHOD_NAME + exp.getMessage());
		}
	}	

	/**
	 * PATCH request with payload to a server
	 * HTTP or HTTPS shall already be initialized
	 * using either the initHttp or initHttps methods.
	 * @param payLoad
	 *        String to be sent in the HTTP request payload
	 * @return CoreResponse
	 */
	public CoreResponse patch(String payLoad) {
		final String METHOD_NAME = "patch(String): ";
	
		try(CloseableHttpClient client = getClient();) {
			// Instantiate a PATCH Request and define the Target URL
			HttpPatch httpPatch = new HttpPatch(this.targetURL);
			// Build the HTTP request header from the HeadersMap
			addHeader(httpPatch);
			// Build the HTTP request message content
			StringEntity entity = new StringEntity(payLoad);
			httpPatch.setEntity(entity);			
			
			return executeRequest(client, httpPatch);		
		
		} catch (IOException exp ){
			LogHandler.error(CLASS_NAME + METHOD_NAME + "Exception: "+ exp.getMessage());
			throw new CoreRuntimeException(exp,CLASS_NAME + METHOD_NAME + exp.getMessage());
		}
	}

	/**
	 * POST request to a server
	 * HTTP or HTTPS shall already be initialized
	 * using either the initHttp or initHttps methods.
	 * @param payLoad
	 *        String to be sent in the HTTP request payload
	 * @return CoreResponse
	 */
	public CoreResponse post(String payLoad) {
		final String METHOD_NAME = "post(String): ";
	
		try(CloseableHttpClient client = getClient();) {			
			// Instantiate a POST Request and define the Target URL
			HttpPost httpPost = new HttpPost(this.targetURL);
			// Build the HTTP request header from the HeadersMap
			addHeader(httpPost);
			// Build the HTTP request message content
			StringEntity entity = new StringEntity(payLoad);
			httpPost.setEntity(entity);
			
			return executeRequest(client, httpPost);
		
		} catch (IOException exp ){
			LogHandler.error(CLASS_NAME + METHOD_NAME + "Exception: "+ exp.getMessage());
			throw new CoreRuntimeException(exp,CLASS_NAME + METHOD_NAME + exp.getMessage());
		}
	}
	
	/**
	 * Allow to post content of a file
	 * @param file : file to post
	 * @return CoreResponse
	 */
	public CoreResponse post(final File file) {
		return post(file,null);
	}

	/**
	 * Allow to post content of a file with specific form name. If null form name is supplied then it uses file name for form name
	 * @param file : file to post
	 * @param formName : formname to use in the post request
	 * @return CoreResponse
	 */
	public CoreResponse post(final File file, final String formName) {
		final String METHOD_NAME = "post(httpFileHandler): ";
		if(file == null){
			throw new CoreRuntimeException("Null parameter was supplied for File object");
		}
		try(CloseableHttpClient client = getClient();) {	
	     	// Instantiate a POST Request and define the Target URL
			HttpPost httpPost = new HttpPost(this.targetURL);
			// Build the HTTP request header from the HeadersMap
			//Remove the user defined content type as it will go as multi part
			this.requestHeaders.remove("Content-Type");
			addHeader(httpPost);
			httpPost.setEntity(getMultiPartEntity(file,formName));
			
			return executeRequest(client, httpPost);
		
		} catch (IOException exp ){
			LogHandler.error(CLASS_NAME + METHOD_NAME + "Exception: "+ exp.getMessage());
			throw new CoreRuntimeException(exp,CLASS_NAME + METHOD_NAME + exp.getMessage());
		}
	}
	/**
	 * Allow to post content of a file with specific form name. If null form name is supplied then it uses file name for form name
	 * @param filePath : location of the file to post
	 * @param formName : forname to use in the post request
	 * @return CoreResponse
	 */
	public CoreResponse post(final String filePath, final String formName) {
		return post(new File(filePath),formName);
	}
	
	/**
	 * create HttpEntity for multi part file upload
	 * @param file : file to upload, must be in class path
	 * @param contentType
	 * @return HttpEntity
	 * @throws FileNotFoundException 
	 */
	private HttpEntity getMultiPartEntity(final File file, String formName) throws FileNotFoundException {
		InputStream is=null;
		if(file.exists()){
			is = new FileInputStream(file);
		}else{
			LogHandler.warn("File not found, so trying to read it from class path now");
			is = HttpClient.class.getResourceAsStream(file.getPath());
		}
		if(null == formName){
			formName = file.getName();
		}
		final MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		
		builder.addBinaryBody(formName,is, ContentType.MULTIPART_FORM_DATA,file.getName());		
	
		return builder.build();
	}
	
	/**
	 * PUT request to a server
	 * @param payLoad : String to be sent in the HTTP request payload
	 * @return CoreResponse
	 */
	public CoreResponse put(String payLoad){
		final String METHOD_NAME = "put(String): ";
	
		try(CloseableHttpClient client = getClient();) {	

			// Instantiate a PUT Request and define the Target URL
			HttpPut httpPut = new HttpPut(this.targetURL);
			// Build the HTTP request header from the HeadersMap
			addHeader(httpPut);
			// Build the HTTP request message content
			StringEntity entity = new StringEntity(payLoad);
			httpPut.setEntity(entity);
			
			return executeRequest(client, httpPut);
			
		} catch (IOException exp ){
			LogHandler.error(CLASS_NAME + METHOD_NAME + "Exception: "+ exp.getMessage());
			throw new CoreRuntimeException(exp,CLASS_NAME + METHOD_NAME + exp.getMessage());
		}
	}

	/**
	 * TRACE request to a server
	 * @return CoreResponse
	 */
	public CoreResponse trace() {
		final String METHOD_NAME = "trace(): ";
	
		try {
			// Build the request
			CloseableHttpClient client = getClient();
			// Instantiate a TRACE Request and define the Target URL
			HttpRequestBase httpRequestBase = new HttpTrace(this.targetURL);
			// Build the HTTP request header from the HeadersMap
			addHeader(httpRequestBase);
			
		 	// Execute the request and parse the Response
			return executeRequest(client, httpRequestBase);
					
		} catch (IOException exp ){
			LogHandler.error(CLASS_NAME + METHOD_NAME + "Exception: "+ exp.getMessage());
			throw new CoreRuntimeException(exp,CLASS_NAME + METHOD_NAME + exp.getMessage());
		}
	}
	
	
	/**
	 * Verify the given list of Cipher Suites is supported
	 * @param cipherSuitesToUse
	 *        Array of strings representing the Cipher Suites that will be
	 *        presented to the HTTPS server during the SSL/TLS handshake
	 * @return True if the given cipher suites are supported
	 */
	public static boolean verifyCipherSuite(String[] cipherSuitesToUse) {
		final String METHOD_NAME = "verifyCipherSuite(cipherSuitesToUse): ";
		final List<String> supportedCipherSuites = getSupportedCipherSuites();
		
		if ( cipherSuitesToUse == null || cipherSuitesToUse.length == 0 ) {
			throw new CoreRuntimeException(CLASS_NAME + METHOD_NAME + "Given cipher suite list is empty!");
		}		
		return supportedCipherSuites.containsAll(Arrays.asList(cipherSuitesToUse));

	}
	
	/**
	 * Get list of supported Cipher Suites
	 * @return Supported Cipher Suites
	 */
	public static List<String> getSupportedCipherSuites()  {
		final String METHOD_NAME = "getSupportedCipherSuites(): ";
		
		try {
			final SSLContext sslContext = SSLContext.getDefault();
			final SSLSocketFactory sslsf = sslContext.getSocketFactory();
			return Arrays.asList(sslsf.getSupportedCipherSuites());
									
		} catch (NoSuchAlgorithmException exp) {
			LogHandler.error(CLASS_NAME + METHOD_NAME + "Exception: "+ exp.getMessage());
			throw new CoreRuntimeException(exp,CLASS_NAME + METHOD_NAME + exp.getMessage());
		}

	}
	
	/*
	 * Creates a CloseableHttpClient for HTTP communication.  
	 */
	private CloseableHttpClient getClient() {		
		CloseableHttpClient client = null;
		
		if (this.isHttps) {
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
	                getSSLContext(),
	                new String[] { this.cryptoProtocol },
	                this.cipherSuite,
	                getHostnameVerifier());
			client = HttpClients.custom().setSSLSocketFactory(sslsf).build();

		}  else {
			client = HttpClients.createDefault();
		}
		
		return client;
		
	}
	
	/*
	 * Creates an SSLContext for HTTP communication.  
	 * If the client authentication is enabled, it will initialize the context
	 * for 2-way authentication (client and server) 
	 */
	private SSLContext getSSLContext() {

		final String METHOD_NAME = "getSSLContext(): ";
		SSLContext sslContext = null;

		try {
			//Get the TrustManager based on client truststore file presence or no
			final TrustManager[] trustManager= getTrustManagers();
			// Configure the SSLContext object with the defined cryptoProtocol 
			sslContext = SSLContext.getInstance(this.cryptoProtocol);
				
			if (this.isClientAuthEnabled) {
				// Load the Client Keystore
				final KeyManagerFactory kmf = KeyManagerFactory.
						getInstance(KeyManagerFactory.getDefaultAlgorithm());
				final KeyStore clientKeystore = KeyStore.getInstance(KeyStore.getDefaultType());
				
				InputStream keystoreis= null;
				//see if the file is present otherwise read from class path
				File keStoreFile =new File(this.keystore);
				if(keStoreFile.exists()){
					keystoreis = new FileInputStream(keStoreFile);
				}else{
					LogHandler.warn("File not found, so trying to read it from class path now");
					keystoreis = HttpClient.class.getResourceAsStream(this.keystore);
				}
				
				clientKeystore.load(keystoreis, this.keystorePasswd.toCharArray());
				kmf.init(clientKeystore, this.keystorePasswd.toCharArray());				
				// Configure the SSLContext object with the Keystore, Truststore and random data 
				sslContext.init(kmf.getKeyManagers(), trustManager, new SecureRandom());
				
			} else {
				// Configure the SSLContext object with the only a Truststore and random data 
				sslContext.init(null, trustManager, new SecureRandom());
			}
			
		} catch (Exception exp) {
			LogHandler.error(CLASS_NAME + METHOD_NAME + exp.getMessage());
			throw new CoreRuntimeException(exp,CLASS_NAME + METHOD_NAME + exp.getMessage());
		} 

		return sslContext;
	}

	private TrustManager[] getTrustManagers()
			throws NoSuchAlgorithmException, KeyStoreException, IOException, CertificateException {
		final InputStream truststoreis;
		TrustManager[] trustManager;
		if(StringUtils.isBlank(this.truststore) || 
				StringUtils.isBlank(this.truststorePasswd)){
		//This means we dont want certificate authentication of any type, however we want only encryption during https call
			trustManager= new TrustManager[]{ new NoOpTrustManager()};
		}else{
			// Load the Client Truststore
			final TrustManagerFactory tmf = TrustManagerFactory.
					getInstance(TrustManagerFactory.getDefaultAlgorithm());
			final KeyStore truststore  = KeyStore.getInstance(KeyStore.getDefaultType());
			
			//see if the file is present otherwise read from class path
			File trustStoreFile =new File(this.truststore);
			if(trustStoreFile.exists()){
				truststoreis = new FileInputStream(trustStoreFile);
			}else{
				LogHandler.warn("File not found, so trying to read it from class path now");
				truststoreis = HttpClient.class.getResourceAsStream(this.truststore);
			}			
			truststore.load(truststoreis, this.truststorePasswd.toCharArray());
			tmf.init(truststore);
			trustManager = tmf.getTrustManagers();
			truststoreis.close();
		}
		return trustManager;
	}
	
	/*
	 * Return a host name verifier. 
	 * Currently set to disable host name verification
	 */
	private X509HostnameVerifier getHostnameVerifier() {
				
		return new X509HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
			public void verify(String host, SSLSocket ssl) throws IOException {
			}
			public void verify(String host, X509Certificate cert)
					throws SSLException {
			}
			public void verify(String host, String[] cns, String[] subjectAlts)
					throws SSLException {
			}
		};
	}	
	
	/*
	 * Verify the name of the given cryptoProtocol
	 */
	private String verifyCryptoProt(final String cryptoProtocol) {
		final String METHOD_NAME = "verifyCryptoProt(cryptoProtocol): ";
		
		if (cryptoProtocol.equalsIgnoreCase("TLSv1.2")) {
			return "TLSv1.2";
		} else if (cryptoProtocol.equalsIgnoreCase("TLSv1.1")) {
			return "TLSv1.1";
		} else if (cryptoProtocol.equalsIgnoreCase("TLSv1")) {
			return "TLSv1";
		} else if (cryptoProtocol.equalsIgnoreCase("SSLv3")) {
			return "SSLv3";
		} else if (cryptoProtocol.equalsIgnoreCase("SSLv2")) {
			return "SSLv2";
		} else {
			throw new IllegalArgumentException(CLASS_NAME + METHOD_NAME + "Invalid Crypto Protocol:" + cryptoProtocol);
		}
	}
	/**
	 * executes httpr request based on basicAut set to true or false. Then maps the https reponse
	 * @param client
	 * @param httpMethod
	 * @return
	 * @throws IOException
	 */
	private CoreResponse executeRequest(final CloseableHttpClient client, final HttpRequestBase httpMethod)
			throws IOException {
		// Execute the request and parse the Response
		if(this.isBasicAuth){
			return HttpResponseMapper.mapToCoreResponse(client.execute(httpMethod,getAuthContext()));
		}else{
			return HttpResponseMapper.mapToCoreResponse(client.execute(httpMethod));
		}
	}

	/**
	 * adds header values in http method object
	 * @param httpMethod
	 */
	private void addHeader(final HttpRequestBase httpMethod) {
		// Build the HTTP request header from the HeadersMap
		for (Map.Entry<String, String> entry : this.requestHeaders.entrySet()) {
			httpMethod.addHeader(entry.getKey(), entry.getValue());
		}
	}
	/**
	 * Creates HttpClientContext required for basic authentication
	 * @return
	 */
	private HttpClientContext getAuthContext(){
		
		//Create target host based on targetURL
		HttpHost targetHost = getTargetHost(this.targetURL);
		
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(
		        new AuthScope(targetHost),
		        new UsernamePasswordCredentials(this.basicAuthId, this.basicAuthPwd));

		// Create AuthCache instance
		AuthCache authCache = new BasicAuthCache();
		// Generate BASIC scheme object and add it to the local auth cache
		authCache.put(targetHost, new BasicScheme());

		// Add AuthCache to the execution context
		HttpClientContext context = HttpClientContext.create();
		context.setCredentialsProvider(credsProvider);
		context.setAuthCache(authCache);
		
		return context;
	}
	
	/**
	 * Creates HttpHost from given targetURL. Depending on whether port is explicitly present or no
	 * @param url : the target url
	 * @return HttpHost object representing the service host and port
	 */
	private HttpHost getTargetHost(String url){
		//Sample value of URL -->http://123.456.177.106:2013/service/xyxz
		final String tempURL = url.substring(url.indexOf("//")+2); //tempURL =10.90.177.106:2013/service/xyxz
		int index = tempURL.indexOf("/");// use this to extract host and port
		
		//Get the host and port -->10.90.177.106:2013
		final String onlyHostPort = tempURL.substring(0,index);	
		//split host and port
		final String[] hostPort = onlyHostPort.split(":");		
		HttpHost targetHost = null;
		if(hostPort.length ==1){ //Only host is available, port is  explicitly unavailable
			targetHost = new HttpHost(hostPort[0]);
		}else if(StringUtils.isNumeric(hostPort[1])){ //host, port both available
			targetHost = new HttpHost(hostPort[0],Integer.valueOf(hostPort[1]));
		}else{
			throw new CoreRuntimeException("Please check the url; port number seems to be wrong !");
		}
		
		return targetHost;
		
	}
	

}


