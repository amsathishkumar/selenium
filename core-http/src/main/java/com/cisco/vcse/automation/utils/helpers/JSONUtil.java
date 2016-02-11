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
 * JSONUtil provides a set of functions to add/update/edit/delete node/values in JSON.
 *
 *
 * @Project: core-HTTP
 * @Author: kosk
 * @Version: 1.0.0.0
 * @Description:  Utility providing basic operation on JSON.
 * @Date updated: 6/17/2015
 */
package com.cisco.vcse.automation.utils.helpers;


import java.util.ArrayList;
import java.util.List;

import com.cisco.vcse.automation.utils.datatype.CoreRuntimeException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JSONUtil {

	/**
	 * gets the value of the node, which can be a simple value or it can be text value of a complex json element
	 * @param inputJsonString : JSON as a string
	 * @param nodeName : name of node to obtain value from
	 * @return : modified json String
	 */
	public static String getByNodeName(final String inputJsonString, final String nodeName){
		final JsonElement jelement = new JsonParser().parse(inputJsonString);	
		final JsonElement matchedElement=getByNodeName(jelement,nodeName);
		return getStringValue(matchedElement);
	}
	
	/**
	 * Returns json named element from the jsonElement
	 * @param jelement : JsonElement
	 * @param nodeName : name of the node to obtain
	 * @return : modified json String
	 */
	public static JsonElement getByNodeName(final JsonElement jelement, final String nodeName){
	    return jelement.getAsJsonObject().get(nodeName);   
	}	
	
	/**
	 * returns named child node which exists under parent node
	 * @param inputJsonString : JSON as a string
	 * @param parentNodeName : name of the parent node
	 * @param childNodeName : name of the child node
	 * @return : modified json String
	 */
	public static String getByParentNodeNameAndChildNodeName(final String inputJsonString, final String parentNodeName, final String childNodeName){
		JsonElement jelement = new JsonParser().parse(inputJsonString);
		//Find parent element
		JsonElement parentElement=getByNodeName(jelement,parentNodeName);
		if(parentElement != null){
			final JsonElement childElement=getByNodeName(parentElement,childNodeName);
			return getStringValue(childElement);
		}
	    return null;	   
	}
	
	/**
	 * returns child element from specified index under parent node
	 * @param inputJsonString : JSON as a string
	 * @param parentNodeName : name of the parent node
	 * @param index : determines from which index of parent node the child has to be looked into
	 * @param childNodeName : name of the child node
	 * @return : modified json String
	 */
	public static String getByNodeNameAndIndexAndChildNodeName(final String inputJsonString, final String parentNodeName, final int index, final String childNodeName ){
		JsonElement jelement = new JsonParser().parse(inputJsonString);
		//Find parent element
		JsonElement parentElement=getByNodeName(jelement,parentNodeName);
		if(parentElement != null && parentElement.isJsonArray()){ //If this is not array, we cant extract nth index item
			JsonArray parentNodeArrays = parentElement.getAsJsonArray();
			if(parentNodeArrays.size()>index){	
				JsonElement childElement= getByNodeName(parentNodeArrays.get(index),childNodeName);
				return getStringValue(childElement);
			}else{
				throw new CoreRuntimeException("Json array size is less than "+index); 
			}
			
		}
	    return null;
	}
	
	/**
	 * removes specified node from input json
	 * @param inputJsonString : JSON as a string
	 * @param nodeName : name of the node to remove
	 * @return : modified jsonString
	 */
	public static String removeNode(final String inputJsonString, final String nodeName){
		JsonElement jelement = new JsonParser().parse(inputJsonString);
		return removeNode(jelement, nodeName);
	}
	
	/**
	 * extractFieldByTagName method will read the json String and generate the
	 * string list based on Key Field name passed.	
	 * @param inputJsonString : JSON as a string
	 * @param tagName : name of the tag to obtain
	 * @return : List of String
	 */
	public static List<String> extractFieldByTagName(final String inputJsonString, final String tagName) {
		JsonElement jelement = new JsonParser().parse(inputJsonString);
		return extractTaggedValues(jelement, tagName);
	}
	
	/**
	 * extractFieldByParentAndChildTagName method will read json STring and 
	 * generate the string list based on a parent and child object combination
	 * @param inputJsonString : JSON as a string
	 * @param parentNode : name of the parent object
	 * @param tagName : name of the child object
	 * @return : List of values for matched parent/child object
	 */
	public static List<String> extractFieldByParentAndChildTagName(final String inputJsonString, final String parentNode, final String tagName) {
		
		JsonElement jelement = new JsonParser().parse(inputJsonString);
		JsonElement parentElement = getByNodeName(jelement, parentNode);
		return extractTaggedValues(parentElement, tagName);
	}
	
	/**
	 * Removes child node from specified index under parent node
	 * @param inputJsonString : JSON as a string
	 * @param parentNodeName : name of the parent object
	 * @param index : child index number to remove
	 * @return : modified json String
	 */
	public static String removeChildNodesBasedOnIndex(final String inputJsonString, final String parentNodeName, final int index){
		JsonElement jelement = new JsonParser().parse(inputJsonString);
		//Find the parent first
		JsonElement parentElement = getByNodeName(jelement, parentNodeName) ;	  
		//If parent is an array then only remove the specified indexed element
		if(parentElement != null && parentElement.isJsonArray()){
			JsonArray parentNodeArrays = parentElement.getAsJsonArray();
			if(parentNodeArrays.size()>index){		
				parentNodeArrays.remove(index);
			}else{
				throw new CoreRuntimeException("Json array size is less than "+index); 
			}
		}
		return getStringValue(jelement);
	}
	
	/**
	 * Removes all the child nodes which appear under a given parent node
	 * @param inputJsonString : JSON as a string
	 * @param parentNodeName : name of the parent object
	 * @param childNodeName : child object name to remove 
	 * @return : modified json String
	 */
	public static String removeChildNodeBasedOnParentNode(final String inputJsonString, final String parentNodeName, final String childNodeName){
		JsonElement jelement = new JsonParser().parse(inputJsonString);
	    JsonElement parentElement=getByNodeName(jelement, parentNodeName);
	    if(null != parentElement){
	    	parentElement.getAsJsonObject().remove(childNodeName);
	    }
	    return getStringValue(jelement);
	}

	/**
	 * Removed specific child under the parent node
	 * @param inputJsonString : JSON as a string
	 * @param parentNodeName : parent whose child has to be removed
	 * @param index : index of the named child to be removed
	 * @param childNodeName : name of the child to be removed
	 * @return : modified json String
	 */
	public static String removeChildNodeBasedOnIndexAndChildNodeName(final String inputJsonString, final String parentNodeName, int index, final String childNodeName){
		JsonElement jelement = new JsonParser().parse(inputJsonString);
		//Find the parent first
		JsonElement parentElement = getByNodeName(jelement, parentNodeName) ;	  
		//If parent is an array then only remove the specified indexed element
		if(parentElement != null && parentElement.isJsonArray()){
			JsonArray parentNodeArrays = parentElement.getAsJsonArray();
			if(parentNodeArrays.size()>index){		
				parentNodeArrays.get(index).getAsJsonObject().remove(childNodeName);
			}else{
				throw new CoreRuntimeException("Parent node json array size is less than "+index); 
			}
		}
		return getStringValue(jelement);
	}	

	/**
	 * Update child object value under specified parent object
	 * @param inputJsonString : JSON as a string
	 * @param parentNodeName : Parent node under which the child node exists
	 * @param childNodeName : name of the child node for which the value needs to be updated
	 * @param childNodeVal : new value which is going to replace the old value for child node
	 * @return : modified json String
	 */
	public static String editChildNodeBasedOnParentNode(final String inputJsonString, final String parentNodeName, final String childNodeName, final String childNodeVal){
		JsonElement jelement = new JsonParser().parse(inputJsonString);
		JsonElement parentElement = getByNodeName(jelement, parentNodeName) ;	
		if(parentElement != null){
			removeNode(parentElement, childNodeName);
			addNameValue(parentElement, childNodeName,childNodeVal);	
		}
		return getStringValue(jelement);
	}
	
	/**
	 * returns tagged values from the parentElement
	 * @param parentElement : Parent Element from where tagged values need to be extracted
	 * @param tagName : tag name for which all the values need to be extracted
	 * @return : list of String
	 */
	private static List<String> extractTaggedValues(final JsonElement parentElement, final String tagName) {
		List<String> listTagValues= new ArrayList<>();
		if(parentElement != null){
			if(parentElement.isJsonArray()){
				JsonArray jsonArray = parentElement.getAsJsonArray();
				for (JsonElement jsObject: jsonArray) {
					final String tagValue = getStringValue(((JsonObject)(jsObject)).getAsJsonObject().get(tagName));
					if(null !=tagValue){
						listTagValues.add(tagValue);
					}
				}
			}else{
				final String tagValue = getStringValue(parentElement.getAsJsonObject().get(tagName));
				if(null !=tagValue){
					listTagValues.add(tagValue);
				}
			}			

		}
		return listTagValues;
	}

	private static void addNameValue(JsonElement jelement, String childNodeName, String childNodeVal) {
		jelement.getAsJsonObject().addProperty(childNodeName, childNodeVal);
	}
	
	/**
	 * remove white spaces from json String
	 * This is used only for unit assertion 
	 * @param inputJsonString : JSON as a string
	 * @return modified json with whitespace removed.
	 */
	public static String getWhiteSpaceRemoved(final String inputJsonString){
		final JsonElement jelement = new JsonParser().parse(inputJsonString);
		return getStringValue(jelement);
	}
	
	private static String getStringValue(final JsonElement jelement ){
		return (jelement==null)?null:
			(jelement.isJsonNull())?null:
				(jelement.isJsonPrimitive()?
						jelement.getAsString():jelement.toString());
	}
	private static String removeNode(final JsonElement jelement, final String nodeName){
	    jelement.getAsJsonObject().remove(nodeName);
	    return getStringValue(jelement);
	}	
}
