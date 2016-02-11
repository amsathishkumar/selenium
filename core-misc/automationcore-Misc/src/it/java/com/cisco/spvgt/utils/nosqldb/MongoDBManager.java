package com.cisco.spvgt.utils.nosqldb;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.cisco.spvgt.utils.logging.LogHandler;
import com.cisco.spvgt.utils.validation.MiscExpception;
import com.cisco.spvgt.utils.validation.Validate;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;

public class MongoDBManager {

	MongoClient mongoClient = null;
	DB db = null;
	DBCollection devicecoll = null;
	Validate miscValidate = new Validate();
	public MongoDBManager(String mongoIP, int port) {
		LogHandler.info("Connecting to MongoDB at " + mongoIP + "...");
		try {
			this.mongoClient = new MongoClient(mongoIP, port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void accessMongoDB(String DBName) throws IOException {
		try {
			LogHandler.info("Switching to DB " + DBName + "...");
			this.db = mongoClient.getDB(DBName);
		} catch (Exception e) {
			e.printStackTrace();
			LogHandler.info("Problem while accessing DBName " + DBName);
		}
	}

	public void clearMongoDBCollection(String collectionName)
			throws IOException {
		try {
			LogHandler.info("Removing all documents from " + collectionName
					+ " collection...");
			this.devicecoll = db.getCollection(collectionName);
			if (devicecoll.getCount() > 0)
				devicecoll.remove(new BasicDBObject());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogHandler.info("Problem while deleting collection "
					+ collectionName);
		}
	}

	public void closeMongoDBConnection() {

		System.out.println("Closing connection to MongoDB ...");
		mongoClient.close();
	}

	public  ArrayList<String>  mongoSearch(String strCollectionName, String strCollectionSearchValue,String strCollectionListValue){
		
		//String values
//		DBCollection tablecollection = db.getCollection(strCollectionName);
//        ArrayList<String> al = new ArrayList<String>(); 
//		BasicDBObject allQuery = new BasicDBObject();
//		BasicDBObject fields = new BasicDBObject();
//		
//		//fields.put("superCasId", "0E110000");
//		String satKey=strCollectionSearchValue.split("=")[0];
//		String satValue=strCollectionSearchValue.split("=")[1];
//		
//		fields.put(satKey,satValue);
//		
//		DBCursor cursor = tablecollection.find(allQuery, fields);
//		String s = "";
//		while (cursor.hasNext()) {
//			s=""+cursor.next();
//			al.add(s.trim());
//		}		  
//		return al;
		
		
		//http://www.mkyong.com/mongodb/java-mongodb-query-document/
		ArrayList<String> al = new ArrayList<String>(); 
		DBCollection tablecollection = db.getCollection(strCollectionName);
		BasicDBObject allQuery = new BasicDBObject();
		List<BasicDBObject> fields = new ArrayList<BasicDBObject>();
//Example		
//		fields.add(new BasicDBObject("superCasId", "0E110000"));
//		fields.add(new BasicDBObject("accessCriteria" , "0E11000000000000090201AF020101D77E9C"));
		for(String s :strCollectionSearchValue.split("AND"))
		{
			String satKey=s.split("=")[0];
			String satValue=s.split("=")[1];
			if (satValue.trim().startsWith("<<") || satValue.trim().endsWith(">>"))
				satValue=miscValidate.readsystemvar(satValue);
			System.out.println("DB Query Condition: "+satKey+satValue);
			fields.add(new BasicDBObject(satKey.trim(), satValue.trim()));
			
		}		
		allQuery.put("$and", fields);
		
	//List columns	
		BasicDBObject Querylist=new BasicDBObject();
 		for(String s1 :strCollectionListValue.split(","))		
 			Querylist.put(s1, 1);
 		  Querylist.put("_id", 0);	
		

		System.out.println(allQuery.toString());
		DBCursor cursor;
		if (strCollectionListValue.trim().startsWith("*"))
		  cursor = tablecollection.find(allQuery);
		else	
		  cursor = tablecollection.find(allQuery,Querylist );
		
		while (cursor.hasNext()) {
			String s=""+cursor.next();
			al.add(s.trim());
			
		}
		System.out.println(" DB Query Result: "+al.toString());
		return al;
	}
	
	
	public  ArrayList<String>  deletemongo(String strCollectionName, String strCollectionSearchValue,String strCollectionListValue){
		
		System.out.println("collection name" + strCollectionName);
		System.out.println("search value" + strCollectionSearchValue);
		DBCollection tablecollection = db.getCollection(strCollectionName);
		
		System.out.println("DBCollection" + tablecollection);
		
		
		return null;
	}
}
