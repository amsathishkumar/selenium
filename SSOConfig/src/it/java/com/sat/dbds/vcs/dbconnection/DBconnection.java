/*
 * 
 */
package com.sat.dbds.vcs.dbconnection;

import com.cisco.dbds.utils.db.DatabaseManager;
import com.cisco.dbds.utils.logging.LogHandler;
import org.junit.Assert;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO: Auto-generated Javadoc

/**
 * The Class DBconnection.
 */
public class DBconnection {

    /**
     * The dbm.
     */
    public DatabaseManager dbm;

    /**
     * Instantiates a new d bconnection.
     */
    public DBconnection() {
        dbm = new DatabaseManager();
        String dbtype=System.getProperty("Dbtype");
        
        boolean data;
        data=dbtype.matches("oracle");
         System.out.println("the boolean data is:" +data);        
         if (data){
       	 

        try {

            String dbip = System.getProperty("oracle.db.ip");
            String dbsid = System.getProperty("oracle.db.scid");
            String dbusr = System.getProperty("oracle.db.username");
            String dbpswd = System.getProperty("oracle.db.password");
            dbm.establishConnectionToOracleDb(dbip, dbsid, dbusr, dbpswd);
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
         }
        else{
        try {

            String dbip = System.getProperty("mysql.db.ip");
            String dbsid = System.getProperty("mysql.db.scid");
            String dbusr = System.getProperty("mysql.db.username");
            String dbpswd = System.getProperty("mysql.db.password");
            dbm.establishConnectionToSQLDb(dbip, dbsid, dbusr, dbpswd);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        }
    }


    /**
     * Dbquery1.
     *
     * @param query the query
     * @return the list
     * @throws ClassNotFoundException the class not found exception
     * @throws SQLException           the SQL exception
     */
//	@SuppressWarnings("finally")
    public List<Map<String, String>> dbselect(String query) throws SQLException {
        List<Map<String, String>> s = null;
        //	try
        //	{
        
        ResultSet rs = dbm.execQuery(query);
        s = dbm.resultSetToListMap(rs);
        dbm.closeConnection();
//			return s;	
        //	}
        //	catch (SQLException e)
        //	{
        LogHandler.info("Check the query passed for dbupdate" + query);
//			return s;	
        //	}
//		return s;	
        //	finally{
        return s;
    }


//	}

    /**
     * Dbquery2.
     *
     * @param query the query
     * @throws ClassNotFoundException the class not found exception
     * @throws SQLException           the SQL exception
     */
    public void dbupdate(String query) {

        try {
            dbm.execUpdate(query);
            dbm.closeConnection();
        } catch (SQLException e) {
            LogHandler.info("Check the query passed for dbupdate" + query);
        }

    }


    /**
     * Testdatacomparedbdata.
     *
     * @param list   the list
     * @param dbdata the dbdata
     */
    public void testdatacomparedbdata(List<Map<String, String>> list, List<Map<String, String>> dbdata) {
        LogHandler.info("testdatacomparedbdata(List<Map<String, String>> testdata, List<Map<String, String>> dbdata)");
        int found = 0;
        LogHandler.info("Test data table" + list.toString());
        LogHandler.info("DB data table" + dbdata.toString());


        if (list.size() != dbdata.size()) {
            LogHandler.info("Test Data value " + list.toString() + "are not equal with DB data value : " + dbdata.toString());
            Assert.assertTrue("Test Data value " + list.toString() + "are not equal with DB data value:" + dbdata.toString(), false);

        }


        for (Map<String, String> testdatavalue : list) {
            for (Map<String, String> dbdatavalue : dbdata) {

                if (!testdatavalue.keySet().equals(dbdatavalue.keySet())) {
                    LogHandler.info("Test Data column " + testdatavalue.keySet().toString() + "are not equal with DB column:" + dbdatavalue.keySet().toString());
                    Assert.assertTrue("Test Data column " + testdatavalue.keySet().toString() + "are not equal with DB column:" + dbdatavalue.keySet().toString(), false);

                }
                LogHandler.info(testdatavalue.values().toString() + dbdatavalue.values().toString() + testdatavalue.values().equals(dbdatavalue.values()));


                if (testdatavalue.equals(dbdatavalue)) {
                    found = 1;
                    LogHandler.info("found");
                }

            }
            if (found == 0 || found > 1) {
                LogHandler.info("" + found);
                LogHandler.info("Test data row value " + testdatavalue.toString() + "are not equal with DB row values:" + dbdata.toString());
                Assert.assertTrue("Test data row value " + testdatavalue.toString() + "are not equal with DB row values:" + dbdata.toString(), false);

            } else
                found = 0;
        }

    }


    /**
     * Testdatacomparedbdata_notpresent.
     *
     * @param testdata the testdata
     * @param dbdata   the dbdata
     */
    public void testdatacomparedbdata_notpresent(List<Map<String, String>> testdata, List<Map<String, String>> dbdata) {

        int found = 0;
        System.out.println("Test data table" + testdata.toString());
        System.out.println("Test data table" + dbdata.toString());
        if (testdata.size() != dbdata.size()) {
            System.out.println("Test Data value " + testdata.toString() + "are not equal with DB data value : " + dbdata.toString());
            //		Assert.assertTrue("Test Data value "+testdata.toString() +"are not equal with DB data value:"+dbdata.toString(),false);

        }

        for (Map<String, String> testdatavalue : testdata) {
            for (Map<String, String> dbdatavalue : dbdata) {
                if (!testdatavalue.keySet().equals(dbdatavalue.keySet())) {
                    System.out.println("Test Data column " + testdatavalue.keySet().toString() + "are equal with DB column:" + dbdatavalue.keySet().toString());
                    //	Assert.assertTrue("Test Data column "+testdatavalue.keySet().toString() +"are equal with DB column:"+dbdatavalue.keySet().toString(),false); Assert.assertTrue("Test Data column "+testdatavalue.keySet().toString() +"are equal with DB column:"+dbdatavalue.keySet().toString(),false);

                }
                System.out.println(testdatavalue.values().toString() + dbdatavalue.values().toString() + testdatavalue.values().equals(dbdatavalue.values()));
                if (!testdatavalue.equals(dbdatavalue)) {
                    found = 1;
                    System.out.println("found");
                }

            }
            if (found == 0 || found > 1) {
                System.out.println(found);
                Assert.assertTrue("Test data row value " + testdatavalue.toString() + "are equal with DB row values:" + dbdata.toString(), false);
                System.out.println("Test data row value " + testdatavalue.toString() + "are equal with DB row values:" + dbdata.toString());
            } else
                found = 0;
        }

    }


    /**
     * Returns the db output
     *
     * @param dbMgr The Database manager
     * @param sql   The query
     * @return Returns an Arraylist
     * @throws Exception
     */
    public ArrayList<HashMap<String, String>> returnTableDump(DatabaseManager dbMgr, String sql)
            throws Exception {

        ArrayList<HashMap<String, String>> dbArray = new ArrayList<>();
        ResultSet resultSet = null;

        System.out.println("\tSQL>" + sql + ";");

        // Execute the query.
        resultSet = dbMgr.execQuery(sql);

        ResultSetMetaData metaData = resultSet.getMetaData();
        int rowCount = metaData.getColumnCount();
        // System.out.println(rowCount);
        while (resultSet.next()) {
            HashMap<String, String> dbValuePair = new HashMap<String, String>();
            for (int i = 1; i <= rowCount; i++) {
                dbValuePair.put(metaData.getColumnName(i),
                        resultSet.getString(i));
            }
            dbArray.add(dbValuePair);
        }

        // Close the DB connection.
        dbMgr.closeConnection();

        return dbArray;
    }


}
