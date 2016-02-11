package com.cisco.spvgt.utils.nosqldb;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.cisco.spvgt.utils.logging.LogHandler;
import com.cisco.spvgt.utils.validation.MiscExpception;

public class NosqlManager {

	final String CLASS_NAME = "NosqlManager: ";
	private NosqlType nosqldbType;
	private String nosqldbURL;
	private MongoDBManager nosqldb;
	public NosqlManager(final NosqlType NosqldbType, final String dbURL) {
		this.nosqldbType = NosqldbType;
		this.nosqldbURL = dbURL;
		registerNosqlDBDriver();
	}

	private void registerNosqlDBDriver() {
		final String METHOD_NAME = "registerNosqlDBDriver: ";
		try{
		if (NosqlType.Mongo.equals(this.nosqldbType)) {
			LogHandler.info("registerNosqlDBDriver():"+nosqldbURL);
			nosqldb = new MongoDBManager(nosqldbURL.split(":")[0], Integer.parseInt((nosqldbURL.split(":")[1])));
			nosqldb.accessMongoDB(nosqldbURL.split(":")[2]);
		} else {
			System.err.println(CLASS_NAME + METHOD_NAME + "Misc Exception: "
					+ nosqldbType + " is not supported yet.");
			throw new MiscExpception("No SQL db: "+nosqldbType + " is not supported yet");
		}
		}
		catch (Exception e)
		{
			System.err.println(CLASS_NAME + METHOD_NAME + "Misc Exception: DB"
					+ nosqldbURL.split(":")[2] + " is not found.");
			throw new MiscExpception(CLASS_NAME + METHOD_NAME + "Misc Exception: DB"
					+ nosqldbURL.split(":")[2] + " is not found.");
		}
	}
	public ArrayList<LinkedHashMap<String, String>> executeNosqlSearch(String strQuery ) {			
		final String METHOD_NAME = "executeNosqlSearch(String strQuery ):";
     try {
         
    	 String strQueryCondition= strQuery.split("`")[2];   
    	 String strQueryTable= strQuery.split("`")[1]; 
    	 String strQueryList= strQuery.split("`")[0]; 
         System.out.println("Table: " + strQueryTable+" Condition: "+strQueryCondition);
    	  	return mongoArraylistToArrayHashMap(nosqldb.mongoSearch(strQueryTable, strQueryCondition,strQueryList));
    	  
	} catch (Exception e) {
		System.err.println(CLASS_NAME + METHOD_NAME + "Misc Exception: "
				+ nosqldb + " Data base not found");
		throw new MiscExpception(CLASS_NAME + METHOD_NAME + "Misc Exception: "
				+ nosqldb + " Data base not found");
	}
	}
   
	
	public ArrayList<LinkedHashMap<String, String>> deleteNosql(String strQuery1 ) {			
 		final String METHOD_NAME = "deleteNosql(String strQuery1 ):";
 		System.out.println("inside deleteNosql function");
 		System.out.println("inside dele " + strQuery1);
      try {
          
     	 String strQueryCondition= strQuery1.split("`")[2];   
     	 String strQueryTable= strQuery1.split("`")[1]; 
     	 String strQueryList= strQuery1.split("`")[0]; 
          System.out.println("Table: " + strQueryTable+" Condition: "+strQueryCondition);
     	  	return mongoArraylistToArrayHashMap(nosqldb.deletemongo(strQueryTable, strQueryCondition,strQueryList));
     	  
 	} catch (Exception e) {
 		System.err.println(CLASS_NAME + METHOD_NAME + "Misc Exception: "
 				+ nosqldb + " Data base not found");
 		throw new MiscExpception(CLASS_NAME + METHOD_NAME + "Misc Exception: "
 				+ nosqldb + " Data base not found");
 	}
  
		
	}
	
	
	
	private static ArrayList<LinkedHashMap<String, String>> mongoArraylistToArrayHashMap(ArrayList<String> al) {
		  ArrayList<LinkedHashMap<String, String>> alm = new ArrayList<LinkedHashMap<String, String>> ();
		for (String al1:al){
		String jsonText = al1;
		//String jsonText = "{\"first\": 123, \"second\": [4, 5, 6], \"third\": 789}";
		
		  JSONParser parser = new JSONParser();
		  ContainerFactory containerFactory = new ContainerFactory(){
		    public List<?> creatArrayContainer() {
		      return new LinkedList();
		    }

		    public Map<?, ?> createObjectContainer() {
		      return new LinkedHashMap();
		    }
		                        
		  };
		                
		  try{
		    Map<String, String> json = (Map<String, String>)parser.parse(jsonText, containerFactory);
		    alm.add((LinkedHashMap<String, String>) json);
//		    Iterator iter = json.entrySet().iterator();
//		    System.out.println("==iterate result==");
//		    while(iter.hasNext()){
//		      Map.Entry entry = (Map.Entry)iter.next();
//		      System.out.println(entry.getKey() + "=>" + entry.getValue());
//		    }
		                        
//		    System.out.println("==toJSONString()==");
//		    System.out.println(JSONValue.toJSONString(json));
		    
		    
		  }
		  catch(ParseException pe){
		    System.out.println(pe);
		  }
		  
		}
		return alm;
	}
	
	
}
