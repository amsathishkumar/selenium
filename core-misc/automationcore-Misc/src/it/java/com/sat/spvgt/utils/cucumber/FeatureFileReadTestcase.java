/**
 * Copyright (c) 2015 by sat, Inc.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of sat,  ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with sat.
 *
 *
 * @Project: Utils
 * @Author: amsathishkumar
 * @Version: 
 * @Description:  
 * @Date created: Apr 9, 2015
 */
package com.sat.spvgt.utils.cucumber;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.SocketException;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.sat.spvgt.utils.configfilehandler.ConfigFileHandlerManager;
import com.sat.spvgt.utils.logging.LogHandler;
import com.sat.spvgt.utils.validation.Validate;
// TODO: Auto-generated Javadoc
/**
 * The Class FeatureFileRead_Testcase.
 */
public class FeatureFileReadTestcase {

	/** The rfile. */
	private File rfile;

	/** The fw. */
	private FileWriter fw ;

	/** The bw. */
	private BufferedWriter bw;
	private String[] extensions ;
	private Validate miscValidate=new  Validate();
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws SocketException the socket exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void main(String args[]) throws SocketException, IOException {

		
		//createTestcaseDetailFile("C:\\Users\\amsathishkumar\\Desktop\\vector\\src\\it\\resources", "feature");
		ConfigFileHandlerManager cfh = new ConfigFileHandlerManager();
		cfh.loadConfigFileBasedOnPath("C:\\LMS\\System\\automationcore-Misc\\src\\it\\resources\\com\\cisco\\dbds\\ccap\\pcb\\");
		FeatureFileReadTestcase ff = new FeatureFileReadTestcase();
		ff.createTestcaseDetailFile("C:\\LMS\\LMS\\LMS\\src\\it\\resources\\", "feature");
		
		
		

	}


	private  File filterSet(String codecdir,String codec) {
		File dir = new File(codecdir);
	     extensions = new String[] { codec};
		return dir;
	}


	public  void createTestcaseDetailFile(String codecdir,String codec ){
		try{
		File dir = filterSet(codecdir, codec);
		LogHandler.info(System.getProperty("user.dir")+System.getProperty("file.separator")+"scenario.txt");
		rfile = new File(System.getProperty("user.dir")+System.getProperty("file.separator")+"scenario.txt");
		if (!rfile.exists()) {
			rfile.createNewFile();
		}
		fw = new FileWriter(rfile.getAbsoluteFile());
		bw = new BufferedWriter(fw);
		LogHandler.info("Getting all .feature files in " + dir.getCanonicalPath()
				+ " including those in subdirectories");
		List<File> files = (List<File>) FileUtils.listFiles(dir, extensions, true);
		for (File file : files) {
			LogHandler.info("file: " + file.getCanonicalPath());
			readFeature(file.getCanonicalPath());
		}
		bw.close();
		}catch(Exception e){
			
		}

	}


	/**
	 * Read feature.
	 *
	 * @param argsFileName the args file name
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private  void readFeature(String argsFileName) throws IOException
	{
		LogHandler.info("Reading File:" + argsFileName);
		BufferedReader br = new BufferedReader(new FileReader(argsFileName));
		String sCurrentLine;
		int k = 0;
		while ((sCurrentLine = br.readLine()) != null) {
			if (k == 1 && !sCurrentLine.trim().equals("")) {
				LogHandler.info("Title " + sCurrentLine);
				String[] title = sCurrentLine.split(":");
				if (title.length > 1)
					bw.write(title[1].trim());

				bw.newLine();
				k = 0;
			}
			if (sCurrentLine.contains("@"+miscValidate.readsystemvar("feature.annotation") )) {
				LogHandler.info("id " + sCurrentLine);
				String[] id = sCurrentLine.split("@");
				int o = 0,idp=0;
				for (int k1 = 0; k1 < id.length-1; k1++) {
					String o1 = id[k1].trim();
					if (o1.equalsIgnoreCase(miscValidate.readsystemvar("feature.annotation")) || o1.equalsIgnoreCase("regression,sanity"))
						o = k1;
					if (o1.startsWith(miscValidate.readsystemvar("feature.annotation")))
						idp=k1;
				}
				String t = argsFileName.replace("\\", "_");
				String fname[] =t.split("_");
				bw.write(fname[fname.length-1]+ ":" + id[o] + ":"
						+ id[idp] + ":");
				k = 1;
			}
			
			if ( (miscValidate.readsystemvar("feature.annotation").trim().length()==0) && (sCurrentLine.contains("@"))) {
				LogHandler.info("id " + sCurrentLine);
				String t = argsFileName.replace("\\", "_");
				String fname[] =t.split("_");
				bw.write(fname[fname.length-1]+ ":" + "  " + ":"
						+ " "  + ":");
				k = 1;
			}

		}
		br.close();
	}


}


