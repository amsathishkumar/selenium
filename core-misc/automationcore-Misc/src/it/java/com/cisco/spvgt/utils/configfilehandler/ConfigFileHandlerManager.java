
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import com.cisco.spvgt.utils.logging.LogHandler;
import com.cisco.spvgt.utils.validation.MiscExpception;
import com.cisco.spvgt.utils.validation.Validate;

/**
 * The Class ConfigFileHandler.
 */
public class ConfigFileHandlerManager {

	private Properties prop;
    Validate miscValidate = new Validate();
	public ConfigFileHandlerManager() {
		prop = new Properties();
	}

	public ConfigFileHandlerManager(String pfilename) {
		prop = new Properties();
		FileInputStream fn = null;
		String separator = File.separator;
		String filename = "." + separator + "src" + separator + "it" + separator + "resources" + separator + pfilename + ".properties";
		try {
			fn = new FileInputStream(filename);
			setSystemvariable(fn);
		} catch (FileNotFoundException e) {
			throw new MiscExpception("property file named:" + pfilename + " is missing under /src/it/resources");
		} finally {
			try {
				if (fn != null)
					fn.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	public void loadPropertiesBasedonClassPath(String ClassNameWithPackage) {
		
		Class<?> myClass ;
		String path = null;
		try {
			 myClass = Class.forName(ClassNameWithPackage);
			 path = myClass.getResource("").getPath();
			System.out.println("Reading File path"+path);
			if (path.contains("!")) {
				path = path.substring(6, path.length() - 1);

				path = path.split("!")[0];
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
							LogHandler.info("Jar file" + line);
						}
						reader.close();
					}
				}
				jarFile.close();
			}
			else
			{
			
				loadConfigFileBasedOnPath(path);
				
			
			}
		} catch (Exception e) {
			LogHandler.info("Jar file reading Error");
			throw new MiscExpception("Invalid path:"+path);
		}
	}

	/**
	 * Sets the systemvariable.
	 * 
	 * @param input
	 *            the new systemvariable
	 */
	public void setSystemvariable(InputStream input) {

		try {
			prop.load(input);

			for (Object element : prop.keySet()) {
				String key=element.toString().trim();
				String value= prop.getProperty(element.toString().trim()).trim();
//				if (value.contains("<<") && value.contains(">>")) {
//					value = value.replace("<<", "");
//					value = value.replace(">>", "");
//					value=System.getProperty(value);
//				}
				value=miscValidate.replaceSystemVar(value);
				System.setProperty(key,value);
			}
		} catch (IOException e) {
			LogHandler.info("setSystemvariable method failure");
		}
	}

//	private String replaceSystemVar(String INPUT) {
//		final String REGEX = "<<(.*?)>>";
//	   // String INPUT = "http://<<10.78.203.118>>/dncs/<<iiiii>>/<<iii>>/ddsd/<<rrr>>";
//       Pattern p = Pattern.compile(REGEX);
//       Matcher m = p.matcher(INPUT); // get a matcher object
//     //  int count = 0;
//
//       while(m.find()) {
////         count++;
////         System.out.println("Match number "+count);
////         System.out.println("start(): "+m.start());
////         System.out.println("end(): "+m.end());
//         
//         String q = INPUT.substring(m.start() , m.end());
//       //  System.out.println(q);
//         String value =q;
//         value = value.replace("<<", "");
//		 value = value.replace(">>", "");
//		 value=System.getProperty(value);
//		 
//         INPUT=INPUT.replace(q, value);
//       //  System.out.println(INPUT);
//         m = p.matcher(INPUT);
//      }
//	return INPUT;
//	}

	/**
	 * Load config file.
	 * 
	 * @param filepath
	 *            the filepath
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void loadConfigFileBasedOnPath(String filepath) {

		LogHandler.info("loadConfigFile inisde");
		File dir = new File(filepath);
		String[] extensions = new String[] { "properties" };
		try {
			LogHandler.info("Getting all .properties files in " + dir.getCanonicalPath() + " including those in subdirectories");
			List<File> files = (List<File>) FileUtils.listFiles(dir, extensions, true);
			for (File file : files) {
				LogHandler.info("file: " + file.getCanonicalPath());
				FileInputStream fn = new FileInputStream(file.getCanonicalPath());
				setSystemvariable(fn);
				fn.close();
			}
		} catch (Exception e) {
			System.out.print("confile is invalid");
		}
	}


	public String getPropertieValues(String key) {
		return this.prop.getProperty(key);

	}

	public Set<Object> getAllPropertiekeys() {
		Set<Object> keys = prop.keySet();
		return keys;
	}
	
	public void loadPropertiesBasedonPropertyFileName(String PropertyFileNamewithpackagestructure) {
		LogHandler.info("inside: "
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		ResourceBundle rb = ResourceBundle.getBundle(PropertyFileNamewithpackagestructure);
		Enumeration<String> keys = rb.getKeys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			String value = rb.getString(key);
			value=miscValidate.replaceSystemVar(value);
		//	System.out.println("Modified property value"+value);
			System.setProperty(key, value);
		}
	}
}
