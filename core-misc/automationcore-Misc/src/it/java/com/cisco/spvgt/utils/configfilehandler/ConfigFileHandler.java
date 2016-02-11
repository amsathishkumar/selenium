/**
 * Copyright (c) 2015 by Cisco Systems, Inc.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Cisco Systems,  ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Cisco Systems.
 *
 *
 * @Project: Utils
 * @Author: smuniapp
 * @Version: 
 * @Description:  
 * @Date created: Apr 9, 2015
 */
package com.cisco.spvgt.utils.configfilehandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.io.FileUtils;

import com.cisco.spvgt.utils.logging.LogHandler;

/**
 * The Class ConfigFileHandler.
 */
public class ConfigFileHandler {


	/**
	 * Load jar cong file.
	 *
	 * @param Utilclass the utilclass
	 */
	public void loadJarCongFile(Class<?> Utilclass )
	{
		try{     	
			String path= Utilclass.getResource("").getPath();
			path=path.substring(6,path.length()-1);
			path=path.split("!")[0];
			LogHandler.info(path);
			JarFile jarFile = new JarFile(path);

			final Enumeration<JarEntry> entries = jarFile.entries();
			while (entries.hasMoreElements()) {
				final JarEntry entry = entries.nextElement();
				if (entry.getName().contains(".properties")) {
					LogHandler.info("Jar File Property File: " + entry.getName());
					JarEntry fileEntry = jarFile.getJarEntry(entry.getName());
					InputStream input = jarFile.getInputStream(fileEntry);
					setSystemvariable(input);	
					InputStreamReader isr = new InputStreamReader(input); 
					BufferedReader reader = new BufferedReader(isr);
					String line;

					while ((line = reader.readLine()) != null) {
						LogHandler.info("Jar file"+line);
					}
					reader.close();
				}
			}
			jarFile.close();
		}
		catch (Exception e)
		{
			LogHandler.info("Jar file reading Error");
    	}
	}

	/**
	 * Sets the systemvariable.
	 *
	 * @param input the new systemvariable
	 */
	public  void setSystemvariable(InputStream input)
	{
		Properties tmp1 = new Properties();
		try {
			tmp1.load(input);

			for (Object element : tmp1.keySet()) {
				System.setProperty(element.toString().trim(),
						tmp1.getProperty(element.toString().trim()).trim());
			}	
		} catch (IOException e) {
			LogHandler.info("setSystemvariable method failure");
		}
	}

	/**
	 * Load config file.
	 *
	 * @param filepath the filepath
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public  void loadConfigFile(String filepath)  {      

		LogHandler.info("loadConfigFile inisde");
		File dir = new File(filepath);
		String[] extensions = new String[] { "properties"};		
		try{
		LogHandler.info("Getting all .properties files in " + dir.getCanonicalPath()
				+ " including those in subdirectories");
		List<File> files = (List<File>) FileUtils.listFiles(dir, extensions, true);
		for (File file : files) {
			LogHandler.info("file: " + file.getCanonicalPath());
			FileInputStream fn = new FileInputStream(file.getCanonicalPath());
			setSystemvariable(fn);		
			fn.close();
		}
		}
		catch (Exception e)
		{
		  System.out.print("confile is invalid");
		}
	}

	/**
	 * Load config file.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public  void loadConfigFile() throws IOException {
		loadConfigFile("src/it/resource");
	}
	/**
	 * Gets the properties values.
	 *
	 * @param pfile the pfile
	 * @param pvariable the pvariable
	 * @return the properties values
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public String getPropertiesValues(String pfile, String pvariable) throws IOException{		
		Properties pr= new Properties();			   
		FileInputStream fn;
		String separator = File.separator;
		String filename = "." + separator + "src" + separator + "it"	+ separator + "resources" + separator + pfile+".properties";
		fn = new FileInputStream(filename);
		pr.load(fn);
		String ret=pr.getProperty(pvariable);
		fn.close();
		pr.clear();
		return  ret;

	}
	




	
}
