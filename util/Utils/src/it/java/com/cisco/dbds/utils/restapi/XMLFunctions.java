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

package com.cisco.dbds.utils.restapi;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

// TODO: Auto-generated Javadoc
/**
 * The Class XMLFunctions.
 */
public class XMLFunctions {

	// the following method update the attributes of an XML file
	/**
	 * Update attribute xml file.
	 * 
	 * @param fileName
	 *            the file name
	 * @param tagName
	 *            the tag name
	 * @param attrbtName
	 *            the attrbt name
	 * @param attrbtValue
	 *            the attrbt value
	 */
	public static void updateAttributeXMLFile(String fileName, String tagName,
			String attrbtName, String attrbtValue) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(fileName);

			NodeList nodeList = document.getElementsByTagName(tagName);

			// search the node to update the value of an attribute
			for (int x = 0, size = nodeList.getLength(); x < size; x++) {
				System.out.println(nodeList.item(x).getAttributes()
						.getNamedItem(attrbtName).getNodeValue());
				nodeList.item(x).getAttributes().getNamedItem(attrbtName)
				.setTextContent(attrbtValue);
				System.out.println(nodeList.item(x).getAttributes()
						.getNamedItem(attrbtName).getNodeValue());
			}

			// update the XML file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new File(fileName));
			transformer.transform(source, result);
			System.out.println("Done");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (SAXException sae) {
			sae.printStackTrace();
		}
		// return null;
	}

	/**
	 * Read xmlattributevalue.
	 * 
	 * @param str
	 *            the str
	 * @param tagname
	 *            the tagname
	 * @param attribure
	 *            the attribure
	 * @return the string
	 * @throws ParserConfigurationException
	 *             the parser configuration exception
	 * @throws SAXException
	 *             the SAX exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static String readXmlattributevalue(String str, String tagname,
			String attribure) throws ParserConfigurationException,
			SAXException, IOException {

		InputStream is = new ByteArrayInputStream(str.getBytes());
		String attributevalue = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.parse(is);
		NodeList nodeList = document.getElementsByTagName(tagname);
		for (int x = 0, size = nodeList.getLength(); x < size; x++) {
			attributevalue = nodeList.item(x).getAttributes()
					.getNamedItem(attribure).getNodeValue();
			System.out.println("Tagname: " + tagname + " Attribute Value "
					+ attributevalue);
		}
		return attributevalue;
	}

	/**
	 * Read xmlattributevalue.
	 *
	 * @param str            the str
	 * @param tagname            the tagname
	 * @param attribure            the attribure
	 * @param dlimt the dlimt
	 * @return the string
	 * @throws ParserConfigurationException             the parser configuration exception
	 * @throws SAXException             the SAX exception
	 * @throws IOException             Signals that an I/O exception has occurred.
	 */
	public static String readXmlattributevalue(String str, String tagname,
			String attribure, String dlimt)
					throws ParserConfigurationException, SAXException, IOException {

		InputStream is = new ByteArrayInputStream(str.getBytes());
		String attributevalue = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.parse(is);
		NodeList nodeList = document.getElementsByTagName(tagname);
		for (int x = 0, size = nodeList.getLength(); x < size; x++) {
			// attributevalue=nodeList.item(x).getAttributes().getNamedItem(attribure).getNodeValue();
			attributevalue = attributevalue
					+ dlimt
					+ nodeList.item(x).getAttributes().getNamedItem(attribure)
					.getNodeValue();
			System.out.println("Tagname: " + tagname + " Attribute Value "
					+ attributevalue);
		}
		return attributevalue;
	}

	/**
	 * Read xmltag text.
	 * 
	 * @param str
	 *            the str
	 * @param tagname
	 *            the tagname
	 * @return the string
	 * @throws ParserConfigurationException
	 *             the parser configuration exception
	 * @throws SAXException
	 *             the SAX exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static String readXmltagText(String str, String tagname)
			throws ParserConfigurationException, SAXException, IOException {

		InputStream is = new ByteArrayInputStream(str.getBytes());
		String tagText = "";
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.parse(is);
		NodeList nodeList = document.getElementsByTagName(tagname);
		for (int x = 0, size = nodeList.getLength(); x < size; x++) {
			tagText = tagText + nodeList.item(x).getTextContent();
			// System.out.println("\n>>Read Tagname: "+tagname+" Text Value "+tagText
			// );
		}
		return tagText;
	}

	/**
	 * Sets the xmltag text.
	 * 
	 * @param strxml
	 *            the strxml
	 * @param tagname
	 *            the tagname
	 * @param value
	 *            the value
	 * @return the string
	 * @throws ParserConfigurationException
	 *             the parser configuration exception
	 * @throws SAXException
	 *             the SAX exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws TransformerFactoryConfigurationError
	 *             the transformer factory configuration error
	 * @throws TransformerException
	 *             the transformer exception
	 */
	public static String setXmltagText(String strxml, String tagname,
			String value) throws ParserConfigurationException, SAXException,
			IOException, TransformerFactoryConfigurationError,
			TransformerException {
		InputStream is = new ByteArrayInputStream(strxml.getBytes());
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.parse(is);
		NodeList nodeList = document.getElementsByTagName(tagname);
		for (int x = 0, size = nodeList.getLength(); x < size; x++) {
			nodeList.item(x).setTextContent(value);
		}

		Transformer tformer = TransformerFactory.newInstance().newTransformer();
		StreamResult result = new StreamResult(new StringWriter());
		DOMSource source = new DOMSource(document);
		tformer.transform(source, result);
		String xmlString = result.getWriter().toString();
		System.out.println("\n>>Modified xml" + xmlString);
		return xmlString;
	}

	/**
	 * Write xmlattributevalue.
	 * 
	 * @param str
	 *            the str
	 * @param tagname
	 *            the tagname
	 * @param attribure
	 *            the attribure
	 * @param value
	 *            the value
	 * @return the string
	 * @throws ParserConfigurationException
	 *             the parser configuration exception
	 * @throws SAXException
	 *             the SAX exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws TransformerFactoryConfigurationError
	 *             the transformer factory configuration error
	 * @throws TransformerException
	 *             the transformer exception
	 */
	public static String writeXmlattributevalue(String str, String tagname,
			String attribure, String value)
					throws ParserConfigurationException, SAXException, IOException,
					TransformerFactoryConfigurationError, TransformerException {

		InputStream is = new ByteArrayInputStream(str.getBytes());
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.parse(is);
		NodeList nodeList = document.getElementsByTagName(tagname);
		for (int x = 0, size = nodeList.getLength(); x < size; x++) {
			nodeList.item(x).getAttributes().getNamedItem(attribure)
			.setNodeValue(value);
		}
		Transformer tformer = TransformerFactory.newInstance().newTransformer();
		StreamResult result = new StreamResult(new StringWriter());
		DOMSource source = new DOMSource(document);
		tformer.transform(source, result);
		String xmlString = result.getWriter().toString();
		System.out.println("\n>>Modified xml" + xmlString);
		return xmlString;
	}

	/**
	 * Convert xml file to string.
	 * 
	 * @param fileName
	 *            the file name
	 * @return the string
	 */
	public static String convertXMLFileToString(String fileName) {
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
					.newInstance();
			documentBuilderFactory
			.setFeature(
					"http://apache.org/xml/features/nonvalidating/load-external-dtd",
					false);
			documentBuilderFactory.setIgnoringElementContentWhitespace(true);
			InputStream inputStream = new FileInputStream(new File(fileName));
			org.w3c.dom.Document doc = documentBuilderFactory
					.newDocumentBuilder().parse(inputStream);
			StringWriter stw = new StringWriter();
			Transformer serializer = TransformerFactory.newInstance()
					.newTransformer();
			serializer.transform(new DOMSource(doc), new StreamResult(stw));
			return stw.toString().trim();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
