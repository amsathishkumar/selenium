package com.sat.vcse.automation.utils.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cisco.vcse.automation.utils.datatype.CoreRuntimeException;
import com.cisco.vcse.automation.utils.logging.LogHandler;
import com.sat.vcse.automation.utils.datatype.DBQueryResult;
import com.sat.vcse.automation.utils.datatype.DatabaseType;

public class DBClient {

	final String CLASS_NAME = "DBClient: ";
	private DatabaseType dbType;
	private String dbURL;
	private String username;
	private String password;
	private boolean reuseConnection;
	private Connection conn=null;
	/**
	 * 
	 * @param databaseType
	 * @param dbURL
	 * @param username
	 * @param password
	 */
	public DBClient(final DatabaseType databaseType, final String dbURL, final String username, final String password){
		this.dbType=databaseType;
		this.dbURL=dbURL;
		this.username=username;
		this.password=password;
		registerDBDriver();
	}
	
	/**
	 * If this constructor is called with reuseConnection=true, please make sure you make a call to closeConnection() 
	 * as otherwise the library will not close the connection for you.
	 * @param databaseType
	 * @param dbURL
	 * @param username
	 * @param password
	 * @param reuseConnection
	 */
	public DBClient(final DatabaseType databaseType, final String dbURL, final String username, final String password, boolean reuseConnection){
		this(databaseType, dbURL, username, password);
		this.reuseConnection= reuseConnection;
		if(this.reuseConnection){
			LogHandler.info("Please close the connection by calling closeConnection() after you are done.");
		}
	}
	
	/**
	 * This loads the jdbc driver to be used for db connection
	 */
	private void registerDBDriver(){
		final String METHOD_NAME = "registerDBDriver(): ";
		try{
		if(DatabaseType.Oracle.equals(this.dbType)){
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		}else if(DatabaseType.Informix.equals(this.dbType)){
			DriverManager.registerDriver(new com.informix.jdbc.IfxDriver());
		}else if(DatabaseType.PostgreSQL.equals(this.dbType)){
			DriverManager.registerDriver(new org.postgresql.Driver());
		}else if(DatabaseType.MySQL.equals(this.dbType)){
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());			
		}else if(DatabaseType.SQLite.equals(this.dbType)){
			DriverManager.registerDriver(new org.sqlite.JDBC());
		}else{
			
			LogHandler.error(CLASS_NAME + METHOD_NAME + "Exception: "+ dbType +" is not supported yet.");
			throw new CoreRuntimeException(dbType +" is not supported yet");
		}
		}catch(SQLException exp){
			LogHandler.error(CLASS_NAME + METHOD_NAME + "Exception: "+ "Failed to load driver for "+this.dbType);
			throw new CoreRuntimeException(CLASS_NAME + METHOD_NAME + "Exception: "+ "Failed to load driver for "+this.dbType);
		}
	}
	private void openConnection() throws SQLException{
			if((this.reuseConnection && this.conn == null) ||(!this.reuseConnection)){
				this.conn = DriverManager.getConnection(this.dbURL, this.username, this.password);
			}
	}

	/**
	 * Executes insert,update,delete or DDL query
	 * @param sqlQuery : query string to be executed
	 * @return number of rows impacted
	 */
	public int executeInsertUpdateOrDelete(final String sqlQuery){
		final String METHOD_NAME = "executeUpdate(String): ";
		Statement statement=null;
		ResultSet rs=null;
		try {
			openConnection();
			statement = this.conn.createStatement();
			final int rowCount=statement.executeUpdate(sqlQuery);
			final boolean isAutoCommit = this.conn.getAutoCommit();
			//If the DB is NOT already running in autoCommit=true then only we need explicit commit
			if(!isAutoCommit){
				this.conn.commit();
			}
			return rowCount;
		} catch (SQLException exp) {
			LogHandler.error(CLASS_NAME + METHOD_NAME + "Exception: during execution of query("+sqlQuery +")");
			throw new CoreRuntimeException(exp,CLASS_NAME + METHOD_NAME + "Exception: during execution of query("+sqlQuery +")");
		}finally{
			closeSafeDBResources(rs,statement);
		}
	}
	
	/**
	 * Use this to execute select query
	 * @param sqlSelectQuery
	 * @return  DBQueryResult
	 */
	public DBQueryResult executeSelect(final String sqlSelectQuery){
		final String METHOD_NAME = "executeSelect(String): ";
		Statement statement=null;
		ResultSet rs=null;
		try {
			openConnection();
			statement = this.conn.createStatement();
			rs = statement.executeQuery(sqlSelectQuery);
			return mapResultSet(rs);
		} catch (SQLException exp) {
			LogHandler.error(CLASS_NAME + METHOD_NAME + "Exception: during execution of select query("+sqlSelectQuery +")");
			throw new CoreRuntimeException(exp,CLASS_NAME + METHOD_NAME + "Exception: during execution of select query("+sqlSelectQuery +")");
		}finally{
			closeSafeDBResources(rs,statement);
		}
	}
	/**
	 * This method must be explicitly called by the consumer when DBClient was created with reuseConnection=true constructor value
	 */
	public void closeConnection(){
		closeSafe(this.conn);
	}
	/**
	 * Maps the database rows into custom data type DBQueryResult
	 * @param rs
	 * @return DBQueryResult
	 * @throws SQLException
	 */
	private DBQueryResult mapResultSet(final ResultSet rs) throws SQLException {
		
		//custom data type representing the database rows returned
		DBQueryResult dbQueryResult = new DBQueryResult();
		
		final ResultSetMetaData rsMetaData= rs.getMetaData();
		final int columnCount = rsMetaData.getColumnCount();
		dbQueryResult.setColumnHeaders(getColumnNames(rsMetaData));	
		
		List<String> rowData = null;
		while(rs.next()){
			 rowData = new ArrayList<>(columnCount);
			for(int columnIndex=1;columnIndex<=columnCount;columnIndex++){
				rowData.add(rs.getString(columnIndex));
			}
			dbQueryResult.addRow(rowData);
		}
		return dbQueryResult;
	}

	/**
	 * Maps the columns names from resultSetMetaData into a List of String
	 * @param rsMetaData
	 * @return List of String
	 * @throws SQLException
	 */
	private List<String> getColumnNames(final ResultSetMetaData rsMetaData) throws SQLException {		
		final int columnCount = rsMetaData.getColumnCount();
		final List<String> lstColumns = new ArrayList<>(columnCount);
		for (int index=1;index<=columnCount; index++){
			lstColumns.add(rsMetaData.getColumnName(index));
		}
		return lstColumns;
	}
	
	private void closeSafeDBResources(final ResultSet rs, final Statement statement) {
		closeSafe(rs);
		closeSafe(statement);
		if(!this.reuseConnection){
			closeSafe(this.conn);
		}
	}
	
	private void closeSafe(AutoCloseable resource){
		if(resource !=null){
			try {
				resource.close();				
			} catch (Exception e) {
			}
		}	
	}
}
