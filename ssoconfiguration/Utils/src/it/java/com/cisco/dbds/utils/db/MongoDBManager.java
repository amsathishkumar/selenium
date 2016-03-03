package com.cisco.dbds.utils.db;

import java.io.IOException;

import com.cisco.dbds.utils.logging.LogHandler;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class MongoDBManager {

	MongoClient mongoClient = null;
	DB db = null;
	DBCollection devicecoll = null;
	
	public void openMongoDBConnection(String mongoIP) throws IOException{
		try{
		System.out.println("Connecting to MongoDB at " + mongoIP + "...");
		mongoClient = new MongoClient(mongoIP, 27017);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			LogHandler.info("Problem while open connection with mongoDB....");
		}		
	}
	
	public void accessMongoDB(String DBName) throws IOException {
		
		//this.ccapsimIP = System.getProperty("ccapsim.server.ip");
		try {
			System.out.println("Switching to DB " + DBName + "...");			
			db = mongoClient.getDB(DBName);
			}
		catch(Exception e)
		{
			e.printStackTrace();
			LogHandler.info("Problem while accessing DBName "+DBName);
		}
       }
		
	public void clearMongoDBCollection(String collectionName) throws IOException
		{
		try{
			System.out.println("Removing all documents from " +collectionName +" collection...");
				devicecoll = db.getCollection(collectionName);
				if (devicecoll.getCount() > 0)
				devicecoll.remove(new BasicDBObject());
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogHandler.info("Problem while deleting collection "+collectionName);
		} 
	}
		
	public void closeMongoDBConnection()
		{
			
			System.out.println("Closing connection to MongoDB ...");
			mongoClient.close();
		}
	}

