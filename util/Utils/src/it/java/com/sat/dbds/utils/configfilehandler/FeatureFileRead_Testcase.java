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
package com.sat.dbds.utils.configfilehandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.SocketException;
import java.util.List;

import org.apache.commons.io.FileUtils;
// TODO: Auto-generated Javadoc
/**
 * The Class FeatureFileRead_Testcase.
 */
public class FeatureFileRead_Testcase {

	/** The rfile. */
	static File rfile = new File("C:/scenario.txt");

	/** The fw. */
	static FileWriter fw ;

	/** The bw. */
	static BufferedWriter bw;

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws SocketException the socket exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void main(String args[]) throws SocketException, IOException {

		if (!rfile.exists()) {
			rfile.createNewFile();
		}
		fw = new FileWriter(rfile.getAbsoluteFile());
		bw = new BufferedWriter(fw);



		File dir = new File("C:\\TIMS");
		String[] extensions = new String[] { "feature"};

		System.out.println("Getting all .feature files in " + dir.getCanonicalPath()
				+ " including those in subdirectories");
		List<File> files = (List<File>) FileUtils.listFiles(dir, extensions, true);
		for (File file : files) {
			System.out.println("file: " + file.getCanonicalPath());
			readFeature(file.getCanonicalPath());
		}
		bw.close();


	}


	/**
	 * Read feature.
	 *
	 * @param argsFileName the args file name
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void readFeature(String argsFileName) throws IOException
	{


		System.out.println("file2:" + argsFileName);
		BufferedReader br = new BufferedReader(new FileReader(argsFileName));
		String sCurrentLine;
		int k = 0;
		while ((sCurrentLine = br.readLine()) != null) {
			if (k == 1 && !sCurrentLine.trim().equals("")) {
				System.out.println("Title " + sCurrentLine);
				String[] title = sCurrentLine.split(":");
				if (title.length > 1)
					bw.write(title[1]);

				bw.newLine();
				k = 0;
			}
			if (sCurrentLine.contains("@Ttv")) {
				System.out.println("id " + sCurrentLine);
				String[] id = sCurrentLine.split("@");
				int o = 0,idp=0;
				for (int k1 = 0; k1 < id.length-1; k1++) {
					String o1 = id[k1].trim();
					if (o1.equalsIgnoreCase("sanity") || o1.equalsIgnoreCase("regression,sanity"))
						o = k1;
					if (o1.startsWith("Ttv"))
						idp=k1;
				}
				System.out.println(o); 
				String t = argsFileName.replace("\\", "-");
				String fname[] =t.split("-");
				bw.write(fname[fname.length-1]+ ":" + id[o] + ":"
						+ id[idp] + ":");
				k = 1;
			}

		}
		br.close();
	}


}


