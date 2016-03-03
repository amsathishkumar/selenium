/**
 * Copyright (c) 2015 by SAT Systems, Inc.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of SAT Systems,  ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with SAT Systems.
 *
 *
 * @Project: Utils
 * @Author: amsathishkumar
 * @Version: 
 * @Description:  
 * @Date created: Apr 9, 2015
 */
package com.cisco.dbds.utils.git;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

import org.apache.commons.io.FileUtils;

// TODO: Auto-generated Javadoc
/**
 * This class will look into the feature files to check for syntax of the
 * gherkin...
 * 
 * @author kaseshad
 * 
 */
public class FileUtilities {

	/* All global go in here */
	/** The separator. */
	String separator = File.separator;

	/** The path. */
	String path = null;

	/** The suffix. */
	String[] SUFFIX = null;

	/**
	 * The default Constructor.
	 */
	public FileUtilities() {
		SUFFIX = new String[1];
		SUFFIX[0] = "properties";

	}

	/**
	 * Constructor to accept additional file suffixes.
	 *
	 * @param suffix the suffix
	 */
	public FileUtilities(String[] suffix) {
		SUFFIX = new String[suffix.length];
		System.arraycopy(suffix, 0, SUFFIX, 0, suffix.length);

	}

	/**
	 * Constructor to accept Directory name and file suffixes.
	 *
	 * @param DirName the dir name
	 * @param suffix the suffix
	 */
	public FileUtilities(String DirName, String[] suffix) {
		SUFFIX = new String[suffix.length];
		System.arraycopy(suffix, 0, SUFFIX, 0, suffix.length);
		path = DirName;

	}

	/**
	 * Recurse and get the files...
	 *
	 * @param path the path
	 * @return the files
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public Collection<File> getFiles(String path) throws IOException {
		// path = "src" + "\\" + "it" + "\\" + "resources";

		Collection<File> files = FileUtils.listFiles(new File(path), SUFFIX,
				true);

		return files;
	}

	/**
	 * Get the git root to create an abs path.
	 *
	 * @return the git root dir
	 */
	public String getGitRootDir() {
		String rootDir = "";
		try {
			Process proc = Runtime.getRuntime().exec(
					"git rev-parse --show-toplevel");
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(
					proc.getInputStream()));

			// BufferedReader stdError = new BufferedReader(new
			// InputStreamReader(
			// proc.getErrorStream()));

			String s = null;
			while ((s = stdInput.readLine()) != null) {
				rootDir = s;
			}

			// read any errors from the attempted command
			// System.out.println("Here is the standard error of the command (if any):\n");
			// while ((s = stdError.readLine()) != null) {
			// System.out.println(s);
			// }
		} catch (Exception e) {
			e.printStackTrace();;
		}
		rootDir = rootDir.replaceAll("/", "\\\\");
		rootDir = rootDir.replaceFirst("c", "C");
		return rootDir;
	}


	/**
	 * Get the git root to create an abs path.
	 *
	 * @return the git prefix dir
	 */
	public String getGitPrefixDir() {
		String rootDir = "";
		try {
			Process proc = Runtime.getRuntime().exec(
					"git ls-files --full-name");
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(
					proc.getInputStream()));

			// BufferedReader stdError = new BufferedReader(new
			// InputStreamReader(
			// proc.getErrorStream()));

			String s = null;
			while ((s = stdInput.readLine()) != null) {
				if(s.contains("pom.xml")) {
					s = s.replace("/pom.xml","");
					rootDir = s;
					break;
				}

			}

			// read any errors from the attempted command
			// System.out.println("Here is the standard error of the command (if any):\n");
			// while ((s = stdError.readLine()) != null) {
			// System.out.println(s);
			// }
		} catch (Exception e) {
			e.printStackTrace();;
		}
		rootDir = rootDir.replaceAll("/", "\\\\");
		rootDir = rootDir.replaceFirst("c", "C");
		return rootDir;
	}


	/**
	 * local main.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		FileUtilities fileChecker = new FileUtilities();
		try {

			System.out.println(fileChecker.getGitRootDir() + "/src" + "/" + "it" + "/"
					+ "resources");
			System.out.println(fileChecker.getFiles( fileChecker.getGitRootDir() + "/src" + "/" + "it" + "/"
					+ "resources"));
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

}
