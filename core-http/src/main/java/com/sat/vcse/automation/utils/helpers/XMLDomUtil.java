package com.sat.vcse.automation.utils.helpers;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.cisco.vcse.automation.utils.datatype.CoreRuntimeException;
import com.cisco.vcse.automation.utils.logging.LogHandler;



public class XMLDomUtil {

	private final static String CLASS_NAME = "XMLDomUtil: ";
	
	private Document doc;
	
	public XMLDomUtil(String input) {
		setDoc(getDocument(input));
	}
	
	public XMLDomUtil(File inputFile) {
		setDoc(getDocument(inputFile));
	}
	
	public Document getDoc() {
		return this.doc;
	}

	/**
	 * Get the XML Document's root element name 
	 * @return The XML document root element name
	 */
	public String getRoot() {		
		return this.getDoc().getDocumentElement().getNodeName();
		
	}
	
	/**
	 * Get all values of the given element
	 * @param tagName : name of the tag based on which values need to be retrieved
	 * @return  : List of all values for the given element tag name
	 */
	public List<String> getElementText(final String tagName) {
		
		// Get all elements in the document matching the given element name
		final NodeList nodelist = this.getDoc().getElementsByTagName(tagName);		
		int nodeCount = nodelist.getLength();
		final List<String> elmntTxtValues = new ArrayList<>(nodeCount);	
	
		// Add the text value for all instances of the given element name to the string list
		for (int ind = 0; ind < nodeCount; ind++) {
			Node elementNode = nodelist.item(ind);
			elmntTxtValues.add(elementNode.getTextContent());
		}
		
		return elmntTxtValues;
	}		

	/**
	 * Finds value of specified index item for a given tagName
	 * @param tagName : Name of the tag for which indexed element has to be found
	 * @param indexOfElement : Index of the element for given tag name, index start from 0
	 * @return String : value of the named, indexed tag
	 */
	public String getElementText(final String tagName, final int indexOfElement) {
		
		// Get all elements in the document matching the given element name
		final NodeList nodelist = this.getDoc().getElementsByTagName(tagName);	

		final Element childElements = (Element) nodelist.item(indexOfElement);
		return childElements.getTextContent();
		//return elmntTxtValues;
	}		

	/**
	 * Get all text values of the given element under a given node
	 * @param parentNodeTag
	 * 		The parent node name (tag name of the parent node containing the element)
	 * @param elementTag
	 * 		The element name (tag name of the element)
	 * @return A list of all values for the given element name under a given node
	 */
	public List<String> getElementText(final String parentNodeTag, final String elementTag) {
		
		final List<String> elmntTxtValues = new ArrayList<>();		
		// Get all nodes in the document matching the given node name
		final NodeList nodelist = this.getDoc().getElementsByTagName(parentNodeTag);
		final int nodeCount = nodelist.getLength();
		int innerNodeCount = 0;
		// Add the text value for all instances of the given element name to the string list
		for (int ind = 0; ind < nodeCount; ind++) {
			if (nodelist.item(ind).hasChildNodes()) {
				final Element childElements = (Element) nodelist.item(ind);
				final NodeList elementList = childElements.getElementsByTagName(elementTag);
				innerNodeCount = elementList.getLength();
				for (int i = 0; i < innerNodeCount; i++) {
					elmntTxtValues.add(elementList.item(i).getTextContent());
				}
			}
		}
		
		return elmntTxtValues;
	}
	
	/**
	 * Returns specific tag value from a parent where there is a sibling with that matches the search criteria.
	 * Example below
	 * {@code
	 * <PackageJobInfo>
	 *	<ecName>autoling</ecName>
	 *	<jobId>3503</jobId>
	 *	<operation>Adhoc</operation>
	 *	<status>Failed</status>
	 *	<submittedOn>1441202476121</submittedOn>
	 *	<updatedOn>1441202480798</updatedOn>
	 *	<reportUrl>https://10.90.46.86/cmdexecresults/ls_1441205227654_60-ec.txt</reportUrl>
	 *	<adhocCommand>ls -ltr</adhocCommand>
	 * </PackageJobInfo>
	 * }
	 *
	 *	if you want to retrieve all "jobId" from all "PackageJobInfo" where "status=failed"
	 *	then below is the call you'll make
	 *	getElemmentWithMatchingSibling("PackageJobInfo", "status", "Failed", "jobId");
	 *
	 * @param parentNodeTag : Parent tag name
	 * @param matchingSiblingTagName : matching tag name
	 * @param matchingSiblingTagValue : matching value
	 * @param childTagName : tag name for which the value has to be extracted
	 * @return List of matches
	 */
	public List<String> getElemmentWithMatchingSibling(final String parentNodeTag, 
			final String matchingSiblingTagName,
			final String matchingSiblingTagValue, 
			final String childTagName) {
		
		final List<String> elmntTxtValues = new ArrayList<>();		
		// Get all nodes in the document matching the given node name
		final NodeList nodelist = this.getDoc().getElementsByTagName(parentNodeTag);
		final int nodeCount = nodelist.getLength();
		int siblingCount = 0;
		int childCount = 0;
		// Add the text value for all instances of the given element name to the string list
		for (int ind = 0; ind < nodeCount; ind++) {
			if (nodelist.item(ind).hasChildNodes()) {
				final Element childElements = (Element) nodelist.item(ind);
				
				final NodeList elementSiblingList = childElements.getElementsByTagName(matchingSiblingTagName);
				final NodeList elementChildList = childElements.getElementsByTagName(childTagName);
				siblingCount = elementSiblingList.getLength();
				childCount = elementChildList.getLength();
				for (int i = 0; i < siblingCount; i++) {
					if(matchingSiblingTagValue.equals(elementSiblingList.item(i).getTextContent())){
						//we need to find the value of elementTag for this index
						if(i<childCount){
							elmntTxtValues.add(elementChildList.item(i).getTextContent());
						}else{
							LogHandler.error("Tag count("+childCount+") for <"+childTagName + "> is less than the "
									+ "Tag count("+siblingCount+") for <"+matchingSiblingTagName + ">");
						}
					}
				}
			}
		}
		
		return elmntTxtValues;
	}
	
	/**
	 * Get all text values of a particular indexed element under a given node
	 * @param parentNodeTag : Parent element name
	 * @param indexOfElement : index of the element tag under the parent node
	 * @param elementTag : Child element name
	 * @return : list of String
	 */
	public List<String> getElementText(final String parentNodeTag,final int indexOfElement, final String elementTag) {
	
		final List<String> elmntTxtValues = new ArrayList<>();
		
		// Get all nodes in the document matching the given node name
		final NodeList nodelist = this.getDoc().getElementsByTagName(parentNodeTag);
		final int nodeCount = nodelist.getLength();

		if (nodeCount>indexOfElement) {
			final Element childElements = (Element) nodelist.item(indexOfElement);
			final NodeList elementList = childElements.getElementsByTagName(elementTag);			
			int innerNodeCount = elementList.getLength();
			for (int i = 0; i < innerNodeCount; i++) {
				elmntTxtValues.add(elementList.item(i).getTextContent());
			}
		}		
		return elmntTxtValues;
	
	}
	/**
	 * Get the total count of a given element name appearing 
	 * in all nodes of a given name
	 * @param node
	 * 		The node name (tag name of the node)
	 * @param element
	 * 		The element name (tag name of the element to be counted)
	 * @return A count of all instances of the given node name under all nodes
	 * of a given name.
	 */
	public int getElementCount(final String node, final String element) {
		int elementCount = 0;		
		// Get all nodes in the document matching the given node name
		final NodeList nodelist = this.getDoc().getElementsByTagName(node);
		
		if (nodelist.getLength() > 0 ) {
			// Count the number of elements that match the given name in each given node
			for (int ind = 0; ind < nodelist.getLength(); ind++) {
				NodeList childList = nodelist.item(ind).getChildNodes();
				int childCount = childList.getLength();
				
				for (int i = 0; i < childCount; i++) {
					Node childNode = childList.item(i);
					if (element.equals(childNode.getNodeName())) {
						elementCount++;
					}
				}		
			}
			
		} 
			
		return elementCount;
	}

	/**
	 * Update the text value for a given element.
	 * All instances of the given element in the document are updated.
	 * 
	 * @param element
	 * 		The element name (tag name of the element)
	 * @param elementText
	 * 		The text value to be assigned to the element
	 * @throws Exception : Error while setting an element text value
	 * @return true if the element is updated successfully, false if not
	 */
	public boolean setElementText(final String element, final String elementText) throws Exception {
		// Get all elements in the document matching the given element name
		final NodeList nodelist = this.getDoc().getElementsByTagName(element);
		return setTextElement(nodelist, elementText);
	}
	
	/**
	 * Update text value of only  the Nth matching element. It will not update ALL matching element, 
	 * only the Nth matching element where N is the index specified by indexOfElement argument.
	 * Index of elements starts with 0
	 * @param element : tagName of the element for which the text has to be set
	 * @param indexOfElement : Out of all the matching element tags, only the Nth one will be updated with the elementText
	 * @param elementText : text to be set
	 * @return true if the value is changed, false otherwise meaning the node was not found
	 */
	public boolean setElementText(final String element,final int indexOfElement, final String elementText) {
		// Get all elements in the document matching the given element name
		final NodeList nodelist = this.getDoc().getElementsByTagName(element);
		return setTextElement(nodelist,indexOfElement, elementText);
	}


	/**
	 * Update the text value for a given element under a specified parent element.
	 * All instances of the given element under the specified parent are updated.
	 * @param parentNode : name of the parent node under which the element appears
	 * @param element : name of the tag name for which the value has to be set
	 * @param elementText : the value which needs to be set
	 * @return true if the value is changed, false otherwise meaning the node was not found
	 */
	public boolean setElementText(final String parentNode, final String element, final String elementText) {
		boolean foundElement = false;		
		// Get the specified parent node
		final NodeList parentList = this.getDoc().getElementsByTagName(parentNode);
		final int nodeCount = parentList.getLength();
		
		// Update the text value for all instances of the given element name
		// under each instance of the specified parent
		for (int ind = 0; ind < nodeCount; ind++) {
			if (parentList.item(ind).hasChildNodes()) {
				Element childElements = (Element) parentList.item(ind);
				NodeList elementList = childElements.getElementsByTagName(element);
				foundElement = setTextElement(elementList, elementText);
				
			}
		}
		
		return foundElement;
	}
	
	/**
	 * This allows you to set up the textValue for an element identified by elementTag which is the "indexOfElement"th node of elements under the parentTag
	 * So for below xml, a call to this method with parameters("food",1,"price","$100") will set 
	 * price of the 1st food element as $100.
	 *
	 * {@code
	 * <breakfast_menu>
	 * <food>
	 * 	<name>Belgian Waffles</name>
	 * 	<price>$5.95</price>
	 * 	<description>Two of our famous Belgian Waffles with plenty of real
	 * 		maple syrup
	 * 	</description>
	 * 	<calories>650</calories>
	 * </food>
	 * <food>
	 * 	<name>Strawberry Belgian Waffles</name>
	 * 	<price>$7.95</price>
	 * 	<description>Light Belgian waffles covered with strawberries and
	 * 		whipped cream
	 * 	</description>
	 * 	<calories>900</calories>
	 * </food>
	 * </breakfast_menu>
	 * }	
	 * @param parentNode : Tag name of the parent node
	 * @param indexOfElement : Index number
	 * @param elementTag : Tag name of the child element
	 * @param elementText : Value to apply to the child element
	 * @return true if the value is changed, false otherwise meaning the node was not found
	 */
	public boolean setElementText(final String parentNode, int indexOfElement, final String elementTag,final String elementText )  {

		boolean foundElement = false;
		
		// Get the specified parent node
		final NodeList parentList = this.getDoc().getElementsByTagName(parentNode);
		final int nodeCount = parentList.getLength();
		
		// Update the text value of "element" for index=indexOfElement of parentList
		if(nodeCount>indexOfElement){
			Element childElements = (Element) parentList.item(indexOfElement);
			NodeList elementList = childElements.getElementsByTagName(elementTag);
			setTextElement(elementList, elementText);	
		}else{	
			throw new CoreRuntimeException("There is no more than "+nodeCount +" parent node, hence indexOfElement="+indexOfElement +" is invalid");
		}
		
		return foundElement;
	}
	
	/**
	 * Add a child node with given text to
	 * all instances of the given parent node
	 * @param parentNode : name of the parent node under which the element needs to be added
	 * @param element : name of the tag to be added
	 * @param elementText : value for the tag name
	 * @return Number of child nodes added.
	 */
	public int addNode(final String parentNode, final String element, final String elementText) {
		int nodeCount= 0;
		// Get all elements in the document matching the given parent node
		final NodeList parentList = this.getDoc().getElementsByTagName(parentNode);
		if(parentList != null){
			nodeCount = parentList.getLength();
			// Add the given child node and text to each parent node
			for (int ind = 0; ind < nodeCount; ind++) {
				Element newChild = this.getDoc().createElement(element);
				newChild.appendChild(this.getDoc().createTextNode(elementText));
				parentList.item(ind).appendChild(newChild);
			}
			if(nodeCount==0){
				LogHandler.error("No node found for NodeName="+parentNode);
			}
		}
		
		return nodeCount;
	}
	
	/**
	 * Add a child node with given text to only the "indexOfElement"th instance of the given parent node
	 * @param parentNode : name of the parent node under which the element needs to be added
	 * @param indexOfElement : Index number
	 * @param element : name of the element to be added
	 * @param elementText : value for the element to be added
	 * @return : true if child was added, false if not
	 */
	public boolean addNode(final String parentNode, final int indexOfElement, final String element,  final String elementText) {
		boolean childAdded= false;
		// Get all elements in the document matching the given parent node
		final NodeList parentList = this.getDoc().getElementsByTagName(parentNode);
		if(parentList != null){
			final int nodeCount = parentList.getLength();
			if(indexOfElement<nodeCount){
				//Ad the child node for Nth paraent node
				final Element newChild = this.getDoc().createElement(element);
				newChild.appendChild(this.getDoc().createTextNode(elementText));
				parentList.item(indexOfElement).appendChild(newChild);
				childAdded=true;
			}else{
				LogHandler.error("There is no more than "+nodeCount +" parent node, hence indexOfElement="+indexOfElement +" is invalid");
			}
		}
		return childAdded;
	}	
	
	/**
	 * Remove all instances of a given child node from
	 * all instances of the given parent node.
	 * @param parentNode : name of the parent node under which child node to be removed
	 * @param childNodeName : name of the child node which needs to be removed from specified parent node
	 * @return : Number of child nodes removed
	 */
	public int removeNode(final String parentNode,String childNodeName) {
		int nodeCount= 0;
		int childRemovedCounter=0;
		// Get all elements in the document matching the given parent node
		final NodeList parentList = this.getDoc().getElementsByTagName(parentNode);
		if(parentList != null){
			nodeCount = parentList.getLength();
			// Remove all instances of the child node under each of the found parent nodes
			for (int ind = 0; ind < nodeCount; ind++) {
				//foundParent = true;
				final NodeList childList = parentList.item(ind).getChildNodes();
				int childCount = childList.getLength();
				
				for (int i = 0; i < childCount; i++) {
					final Node childNode = childList.item(i);
					if (childNodeName.equals(childNode.getNodeName())) {
						//foundChild = true;
						parentList.item(ind).removeChild(childNode);
						childCount = childList.getLength();
						childRemovedCounter++;
					}
				}
			}
		}
		return childRemovedCounter;
	}

	private DocumentBuilder getDocBuilder() throws ParserConfigurationException {
		final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		return dBuilder;
	}

	/**
	 * Returns Document representation of given input xml
	 * @param input : input xml as String which needs to be converted to Document
	 * @return Document
	 */
	private Document getDocument(final String input) {
		final String METHOD_NAME = "getDocument(String): ";
		if(input != null){
			try(InputStream  inputSrc = new ByteArrayInputStream(input.getBytes("UTF-8"));){		
				final DocumentBuilder docBuilder = getDocBuilder();
				return docBuilder.parse(inputSrc);
			} catch (SAXException | IOException | ParserConfigurationException exp) {
				LogHandler.error(CLASS_NAME + METHOD_NAME + "Exception: "+ exp.getMessage());
				throw new CoreRuntimeException(exp,CLASS_NAME + METHOD_NAME + exp.getMessage());
			}
		}
		return null;
	}
	/**
	 * Convert a XML text file into a Document
	 * @param xmlFile
	 * 		XML text file name
	 * @return An XML document
	 */
	private Document getDocument(final File xmlFile) {
		final String METHOD_NAME = "getDocument(File): ";

        	try {        	
        		final DocumentBuilder dBuilder = getDocBuilder();        	
				return dBuilder.parse(xmlFile);
			} catch (SAXException | IOException | ParserConfigurationException exp) {
				LogHandler.error(CLASS_NAME + METHOD_NAME + "Exception: "+ exp.getMessage());
				throw new CoreRuntimeException(exp,CLASS_NAME + METHOD_NAME + exp.getMessage());
			}

	}	
	private void setDoc(Document doc) {
		this.doc = doc;
	}	
	private boolean setTextElement(final NodeList nodelist,final int indexOfElement,final String elementText ) {
		boolean foundElement = false;
		if(nodelist.getLength()>=indexOfElement){
			Node elementNode = nodelist.item(indexOfElement);
			elementNode.setTextContent(elementText);
			foundElement = true;
		}
		return foundElement;
	}
	private boolean setTextElement(final NodeList nodelist,final String elementText ) {
		boolean foundElement = false;
		// Update the text value for all instances of the given element name
		final int nodeCount = nodelist.getLength();
		for (int ind = 0; ind < nodeCount; ind++) {
			Node elementNode = nodelist.item(ind);
			elementNode.setTextContent(elementText);
			foundElement = true;
		}
		return foundElement;
	}
	
	/**
	 * Updates attributes values identified by tagName and attribute name and then writes the document into a file
	 * @param outputFileName : output file name this can be complete path of the file
	 * @param tagName : name of the tag based on which attribute values have to updated
	 * @param attrbtName : name of the attribute for which the values have to updated
	 * @param attrbtValue : the updated value which will replace the old value for given tag/attribute pairs
	 */
	public void updateAttributeXMLFile(final String outputFileName, final String tagName,
			final String attrbtName, final String attrbtValue) {
		final String METHOD_NAME = "updateAttributeXMLFile(File): ";		
		try {
			Document document = this.getDoc();
			NodeList nodeList = document.getElementsByTagName(tagName);
			// search the node to update the value of an attribute
			for (int x = 0, size = nodeList.getLength(); x < size; x++) {
				nodeList.item(x).getAttributes().getNamedItem(attrbtName)
				.setTextContent(attrbtValue);
			}
			writeDocIntoFile(outputFileName, document);

		} catch (TransformerException tfe) {
			LogHandler.error(CLASS_NAME + METHOD_NAME + "Exception: "+ tfe.getMessage());
			throw new CoreRuntimeException(tfe,CLASS_NAME + METHOD_NAME + tfe.getMessage());
		} 
	}

	/**
	 * 
	 * @param fileName : output file
	 * @param document :  xml document
	 * @throws TransformerFactoryConfigurationError
	 * @throws TransformerConfigurationException
	 * @throws TransformerException
	 */
	private static void writeDocIntoFile(final String fileName, final Document document)
			throws TransformerFactoryConfigurationError, TransformerConfigurationException, TransformerException {
		// update the XML file
		final TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		final Transformer transformer = transformerFactory.newTransformer();
		final DOMSource source = new DOMSource(document);
		final StreamResult result = new StreamResult(new File(fileName));
		transformer.transform(source, result);
	}
	/**
	 * Reads all the matching attributes identified by tagName and attribute name
	 * @param tagName : name of the tag based on which attribute values have to updated
	 * @param attribureName : name of the attribute for which the values have to updated
	 * @return list of matched tags
	 */
	public  List<String> readXmlattributevalue(final String tagName,
			final String attribureName) {
		final List<String> lstAttValues = new ArrayList<>();
		final Document document = this.getDoc();
		final NodeList nodeList = document.getElementsByTagName(tagName);
		for (int x = 0, size = nodeList.getLength(); x < size; x++) {
			lstAttValues.add(nodeList.item(x).getAttributes()
					.getNamedItem(attribureName).getNodeValue());
		}
		return lstAttValues;
	}


	/**
	 * Writes attributes identified by tagName and attribute name
	 * @param tagName : name of the tag based on which attribute values have to updated
	 * @param attribureName : name of the attribute for which the values have to updated
	 * @param attributeValue : value of the attribute
	 * @return modified xml string
	 */
	public String writeXmlattributevalue(final String tagName,
		final String attribureName, final String  attributeValue) {
		final Document document = this.getDoc();
		final NodeList nodeList = document.getElementsByTagName(tagName);
		for (int x = 0, size = nodeList.getLength(); x < size; x++) {
			nodeList.item(x).getAttributes().getNamedItem(attribureName)
			.setNodeValue(attributeValue);
		}
		return toString();
	}
	
	/**
	 * XML Document in string format 
	 * @return The XML document as a string
	 */
	@Override
	public String toString() {
		final String METHOD_NAME = "toString(): ";
		
		try {
			final DOMSource ds = new DOMSource(getDoc());
			final StringWriter sw = new StringWriter();
			final StreamResult sr = new StreamResult(sw);
			final TransformerFactory tf = TransformerFactory.newInstance();
			final Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(ds, sr);
			return sw.getBuffer().toString();
			
		} catch (Exception exp) {
			LogHandler.error(CLASS_NAME + METHOD_NAME + 
					"Failed to convert XML document to string: " + exp.getMessage());
			throw new CoreRuntimeException(exp,CLASS_NAME + METHOD_NAME + 
					"Failed to convert XML document to string: " + exp.getMessage());
					
		}
	}
	
}
