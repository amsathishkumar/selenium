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
 * This class wraps shall response
 *
 *
 * @Project: acutomationCore-ShellClient
 * @Author: kosk
 * @Version: 1.0.0.0
 * @Description:  This class wraps shell response, contains exit code for command run and standrd and error console outs
 * @Date updated: 6/10/2015
 */

package com.cisco.vcse.automation.utils.datatype;

import java.util.ArrayList;
import java.util.List;

public class DBQueryResult {

	//1st row represents column names
	//2nd row onwards represents data
	List<List<String>> rowData= new ArrayList<>();
	List<String> columnHeaders= new ArrayList<>();
	int currentRowIndex=0;
	
	/**
	 * This returns all the data in form of 2D array. Each row in the array represents a row in the db
	 * @return List of List of String 
	 */
	public List<List<String>> getRowData() {
		return this.rowData;
	}
	/**
	 * Gives all the column names returned by the query execution
	 * @return List of String
	 */
	public List<String> getColumnHeaders() {
		return this.columnHeaders;
	}
	public void setColumnHeaders(List<String> columnHeaders) {
		this.columnHeaders = columnHeaders;
	}
	/**
	 * Number of rows of data returned by the query
	 * @return int size of the returned rows
	 */
	public int getRowCount(){
		return this.rowData.size();
	}
	
	/**
	 * Number of columns of data returned by the query
	 * @return :  int for number of columns returned
	 */
	public int getColumnCount(){
		return this.columnHeaders.size();
	}
	
	public void addRow(final List<String> row){
		this.rowData.add(row);
	}
	
	public void addColumnName(final String columnName){
		this.columnHeaders.add(columnName);
	}

	/**
	 * Checks whether a given column name( case insensitive) was returned or no
	 * @param columnName
	 * @return true or false based on presence of 
	 */
	public boolean isColumnNamePresent(final String columnName){
		return this.columnHeaders.size()>0? this.columnHeaders.contains(columnName.toUpperCase()):false;
	}
	
	/**
	 * Checks whether an entire row exists or no in the underlying data.
	 *  
	 * @param row : List of values representing all the columns starting with index 0 to last index.
	 * Size of the list must be the number of columns returned
	 * @return boolean true/false
	 */
	public boolean isRowDataPresent(final List<String> row){			
		return this.rowData.contains(row);		
	}
	
	/**
	 * Checks whether a given column name/value is present or no
	 * Column is case insensitive but value is case sensitive
	 * @param columnName
	 * @param columnValue
	 * @return boolean true/false
	 */
	public boolean isColumnNameValuePresent(final String columnName, final String columnValue){			
		final String capitalizedColumnName=columnName.toUpperCase();
		boolean isPresent = false;
		final int rowCount=this.rowData.size();
		if(rowCount>0){
			int indexOfCoumnName = this.columnHeaders.indexOf(capitalizedColumnName);
			if(indexOfCoumnName>-1){				
				for(int i=0;i<rowCount;i++){
					if(columnValue.equals(this.rowData.get(i).get(indexOfCoumnName))){
						isPresent = true;
						break;
					}
				}
			}
		}
		
		return isPresent;
	}

	/**
	 * Get all the values of all the rows for a given column name(case insensitive )
	 * @param columnName
	 * @return List of Strings with values for given column name
	 */
	public List<String> getAllRowsForGivenColumnName(final String columnName){
		final List<String> matchedRowData = new ArrayList<>();
		
		//Capitalize the name as DB returns capitalized column names !
		final String capitalizedColumnName=columnName.toUpperCase();
		
		final int rowCount=this.rowData.size();
		if(rowCount>0){
			int columnIndex = this.columnHeaders.indexOf(capitalizedColumnName);
			if(columnIndex>-1){
				for(int i=0; i<rowCount;i++){
					matchedRowData.add(this.rowData.get(i).get(columnIndex));
				}
			}
		}
		
		return matchedRowData;
	}

	/**
	 * Get all the values of all the rows for given column names
	 * @param columnNames : Array of columns for which data has to be returned
	 * @return List of List of String 
	 */
	public List<List<String>>  getAllRowsForGivenColumnNames(final String[] columnNames){
		return getAllRowsForGivenColumnIndexes(getIndexOfColumns(columnNames));
	}
	
	/**
	 * Get all the values of all the rows for a given column indexes 
	 * @param columnIndexes : Array of columns indexes for which data has to be returned
	 * @return List of List of String 
	 */
	public List<List<String>>  getAllRowsForGivenColumnIndexes(final int[] columnIndexes){
		final List<List<String>> matchedRowData  = new ArrayList<List<String>> ();
		int columnCount=columnIndexes.length;
		List<String> tempRow=null;
		for(List<String> row: this.rowData){	
			tempRow = new ArrayList<>(columnCount);	
			for(int index: columnIndexes){
				if(index>=row.size()){
					throw new ArrayIndexOutOfBoundsException("Column index("+index +") is more than the size(" +row.size() + ") of the columns.");
				}
				tempRow.add(row.get(index));				
			}
			matchedRowData.add(tempRow);
		}
		return matchedRowData;
	}
	

	private int[] getIndexOfColumns(String[] columnNames) {
		//Capitalize the name as DB returns capitalized column names !
		int[] indexOfColumns = new int[columnNames.length];
		int index=0;
		for(String columnName:columnNames){
			indexOfColumns[index]=this.columnHeaders.indexOf(columnName.toUpperCase());
			index++;
		}
		return indexOfColumns;
	}
	/**
	 * Get all the values of all the rows for a given column index
	 * @param columnIndex : Indexes for which data has to be returned
	 * @return List of String 
	 */
	public List<String> getAllRowsForGivenColumnIndex(final int columnIndex){
		final List<String> matchedRowData = new ArrayList<>();
		final int rowCount=this.rowData.size();
		for(int i=0; i<rowCount;i++){
			if(columnIndex>=this.rowData.get(i).size()){
				throw new ArrayIndexOutOfBoundsException("Column index("+columnIndex +") is more than the size(" + (this.rowData.get(i).size()-1)+ ") of the columns.");
			}
			matchedRowData.add(this.rowData.get(i).get(columnIndex));
		}	
		return matchedRowData;
	}
	/**
	 * This gives the next row if present. If no more row present then will return empty list
	 * @return List of String 
	 */		
	public List<String> getNextRowData(){
		return getNext();
	}
	
	/**
	 * This gives the next row if present. If no more row present then will return empty list
	 * @return List of List of String 
	 */	
	public List<String> getNext(){
		this.currentRowIndex++;
		if(this.rowData.size()>=this.currentRowIndex){
			return this.rowData.get(this.currentRowIndex-1);
		}else{
			return new ArrayList<String>(0);
		}
	}
	public List<String> getFirstRowData(){
		this.currentRowIndex=1;
		return rowData.get(0);
	}
	
	public List<String> getLastRowData(){
		this.currentRowIndex=this.rowData.size();//this ensures no more row is returned
		return this.rowData.get(this.currentRowIndex-1);	
	}
	
	/**
	 * returns row data 
	 * @param indexOfRow
	 * @return List of String 
	 */
	public List<String> getNthRowData(final int indexOfRow){
		if(this.rowData.size()<indexOfRow){
			return this.rowData.get(indexOfRow);
		}else{
			return new ArrayList<String>(0);
		}
	}

	public boolean hasNext(){
		
		if(this.currentRowIndex<0 || this.currentRowIndex>=this.rowData.size()){
			return false;
		}else{
			return true;
		}
		
		
	}
	public String toString(){
		final StringBuilder stringBuilder = new StringBuilder();
		for(int i=0;i<this.columnHeaders.size();i++){
			stringBuilder.append(columnHeaders.get(i)).append("  ");
		}
		stringBuilder.append("\n");
		for(List<String> row: this.rowData){
			for(String value: row){
				stringBuilder.append(value).append("  ");
			}
		stringBuilder.append("\n");
		}
		
		return stringBuilder.toString();
	}
}
