/* 
 *Defines all methods to perform all database related operations with respect
 * to Oracle and Informix.
 */
package com.cisco.dbds.utils.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * Defines all methods to perform all database related operations with respect
 * to Oracle and Informix.
 * 
 */
public class DatabaseManager {

	/** {@link Connection} object to interact with EC / ECS database. */
	private Connection connection = null;
	
	/** {@link ResultSet} object to handle database results. */
	private ResultSet resultSet = null;
	
	/** {@link Statement} object to perform execution of queries. */
	private Statement statement = null;

	/** Oracle JDBC driver class. */
	private final String JDBC_DRIVER = System.getProperty("oracle.jdbc.driver");
	
	/** Oracle thin driver name. */
	private final String JDBC_DRIVER_TYPE = System
			.getProperty("oracle.jdbc.driver.type");
	
	/** Oracle JDBC port. */
	private final int JDBC_PORT = Integer.parseInt(System
			.getProperty("oracle.jdbc.driver.port"));

	/** Informix database access class. */
	private final String INFORMIX_DRIVER = System
			.getProperty("informix.driver");
	
	/** Informix driver type. */
	private final String INFORMIX_DRIVER_TYPE = System
			.getProperty("informix.driver.type");
	
	/** Informix database access port. */
	private final int INFORMIX_PORT = Integer.parseInt(System
			.getProperty("informix.driver.port"));

	/**
	 * Check if required class available under class path.
	 *
	 * @param driver the driver
	 * @throws ClassNotFoundException the class not found exception
	 */
	private void checkDriver(String driver) throws ClassNotFoundException {
		if ((Class.forName(driver)) == null)
			throw new ClassNotFoundException(
					"Library for database driver is not found under build path. Please fix this issue and retry.");
	}

	/**
	 * Establishes connection to Oracle DB.
	 *
	 * @param hostIp the host ip
	 * @param dbSID the db sid
	 * @param dbUserName the db user name
	 * @param dbUserPassword the db user password
	 * @param resultSetAttr the result set attr
	 * @return the statement
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public Statement establishConnectionToOracleDb(String hostIp,
			String dbSID, String dbUserName, String dbUserPassword,
			int... resultSetAttr) throws SQLException, ClassNotFoundException {

		checkDriver(JDBC_DRIVER);

		/*
		 * Connection String Syntax
		 * DRIVER_TYPE:USER_NAME/PASSWORD@HOST_IP:PORT:DBNAME
		 */

		String url = this.JDBC_DRIVER_TYPE + ":" + dbUserName + "/"
				+ dbUserPassword + "@" + hostIp + ":" + this.JDBC_PORT + ":"
				+ dbSID;

		return this.establishConnection(url, resultSetAttr);
	}
	
	public Statement establishConnectionToSQLDb(String hostIp,
			String dbSID, String dbUserName, String dbUserPassword,
			int... resultSetAttr) throws SQLException, ClassNotFoundException {

		String SQL_DRIVER = System.getProperty("mysql.jdbc.driver");
		checkDriver(SQL_DRIVER);
        String SQL_DRIVER_TYPE = System.getProperty("mysql.jdbc.driver.type");
        String SQL_PORT=System.getProperty("mysql.jdbc.driver.port");
		/*
		 * Connection String Syntax
		 * DRIVER_TYPE:USER_NAME/PASSWORD@HOST_IP:PORT:DBNAME
		 */

		String url = SQL_DRIVER_TYPE + ":" + "//" + hostIp + ":" + SQL_PORT + "/"
				+ dbSID;
	     Class.forName("com.mysql.jdbc.Driver");

	      //STEP 3: Open a connection
	      System.out.println("Connecting to database...");
	      this.connection = DriverManager.getConnection(url,dbUserName,dbUserPassword);
	  	  if (this.connection == null)
			throw new SQLException("Could not create connection using URL: "
					+ url);

		  if (resultSetAttr.length == 2) {
			this.statement = this.connection.createStatement(resultSetAttr[0],
					resultSetAttr[1]);
		     } else {
			this.statement = this.connection.createStatement();
		}
		return this.statement;
	}

	/**
	 * Establishes informix database connection.
	 *
	 * @param hostIp the host ip
	 * @param dbName the db name
	 * @param serverAlias the server alias
	 * @param dbUserName the db user name
	 * @param dbUserPassword the db user password
	 * @param resultSetAttr the result set attr
	 * @return the statement
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public Statement establishConnectionToInformixDB(String hostIp,
			String dbName, String serverAlias, String dbUserName,
			String dbUserPassword, int... resultSetAttr) throws SQLException,
			ClassNotFoundException {

		this.checkDriver(INFORMIX_DRIVER);

		/*
		 * Connection String Syntax
		 * DRIVER_TYPE://HOST_IP:PORT/DB_NAME:informixserver
		 * =INFORMIX_SERVER;user=USER_NAME;password=PASSWORD
		 */
		String url = this.INFORMIX_DRIVER_TYPE + "://" + hostIp + ":"
				+ this.INFORMIX_PORT + "/" + dbName + ":INFORMIXSERVER="
				+ serverAlias + ";user=" + dbUserName + ";password="
				+ dbUserPassword;

		System.out.println(url);
		return this.establishConnection(url, resultSetAttr);
	}

	/**
	 * Establishes connection using database given URL.
	 *
	 * @param url            - JDBC/INFORMIX URL
	 * @param resultSetAttr            - Var arg type which can define attributes of
	 *            {@link ResultSet} object tytpe
	 * @return - {@link Statement} object
	 * @throws SQLException the SQL exception
	 */
	public Statement establishConnection(String url, int... resultSetAttr)
			throws SQLException {
		if (url == null) {
			return null;
		}
		this.connection = DriverManager.getConnection(url);

		if (this.connection == null)
			throw new SQLException("Could not create connection using URL: "
					+ url);

		if (resultSetAttr.length == 2) {
			this.statement = this.connection.createStatement(resultSetAttr[0],
					resultSetAttr[1]);
		} else {
			this.statement = this.connection.createStatement();
		}
		return this.statement;
	}

	/**
	 * Execute the sql and return the result set.
	 *
	 * @param sql            - Query String
	 * @return - {@link ResultSet} object which holds all records retrieved from
	 *         database
	 * @throws SQLException the SQL exception
	 */
	public ResultSet execQuery(String sql) throws SQLException {
		this.connection.prepareStatement(sql);
		System.out.println("Executing Query: " + sql);
		this.resultSet = this.statement.executeQuery(sql);

		/*if (this.resultSet == null)
			throw new SQLException("Could not execute query!");*/
		
		if (this.resultSet == null){
			System.out.println("resut set is null");
		}
		else
		{
			System.out.println("resut set is not null");
		}
		return this.resultSet;
	}

	/**
	 * Exec update.
	 *
	 * @param sql the sql
	 * @throws SQLException the SQL exception
	 */
	public void execUpdate(String sql) throws SQLException {
		this.connection.prepareStatement(sql);
		System.out.println("Executing Query: " + sql);
		this.statement.executeUpdate(sql);
	}
	

	/**
	 * Close the connection.
	 */
	public void closeConnection() {
		try {
			if (statement != null)
				statement.close();
			if (resultSet != null)
				resultSet.close();
			if (connection != null)
				connection.close();
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}
	
	/**
	 * Result set to list map.
	 *
	 * @param rs the rs
	 * @return the list
	 */
	public List<Map<String,String>> resultSetToListMap(ResultSet rs) {
	    ResultSetMetaData md = null;
	    List<Map<String,String>> l = new ArrayList<Map<String,String>>();
		try 
		{
			md = rs.getMetaData();// gets the columns name
	        int columns = 0;
			columns = md.getColumnCount();
		     
	    
				while (rs.next()) 
				 {
					 Map<String,String> map = new HashMap<String,String>();
					  for (int i = 1; i <= columns; ++i) 
					 {
						 // String valBool = (((JSONObject)arr.get(i)).get(key) != null ? ((JSONObject)arr.get(i)).get(key).toString() : "null");
						  if (rs.getObject(i) != null)
						map.put(md.getColumnName(i),rs.getObject(i).toString().trim());
						  else
						  {
							  System.out.println("Inside null");
						  	  map.put(md.getColumnName(i),"null");
						  }
						//map.put(md.getColumnName(i),rs.getString(i));
					 }
				     l.add(map);    		
				}
				
		}
		 catch (SQLException e) 
		 {
				e.printStackTrace();
		}
		return l;
		
	    	 
	}
	public List<Map<String,String>> resultSetToListMapformysql(ResultSet rs) {
	    ResultSetMetaData md = null;
	    List<Map<String,String>> l = new ArrayList<Map<String,String>>();
		try 
		{
			md = rs.getMetaData();// gets the columns name
	        int columns = 0;
			columns = md.getColumnCount();
		     
	    
				while (rs.next()) 
				 {
					 Map<String,String> map = new HashMap<String,String>();
					  for (int i = 1; i <= columns; ++i) 
					 {
						//map.put(md.getColumnName(i),rs.getObject(i).toString().trim());
						  map.put(md.getColumnName(i),rs.getString(i));
					 }
				     l.add(map);    		
				}
				
		}
		 catch (SQLException e) 
		 {
				e.printStackTrace();
		}
		return l;
		
	    	 
	}
}
