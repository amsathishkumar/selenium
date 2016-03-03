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
package com.cisco.dbds.utils.git;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;

// TODO: Auto-generated Javadoc
/**
 * The Class CucumberFileChecker.
 *
 * @author kaseshad
 */
public class CucumberFileChecker {

	/* All globals go here */
	/** The file util. */
	private FileUtilities fileUtil = null;

	/** The suffix. */
	private String[] suffix = {"feature"};

	/**
	 * Instantiates a new cucumber file checker.
	 */
	public CucumberFileChecker() {
		fileUtil = new FileUtilities(suffix);
	}

	/**
	 * Check cuke file syntax.
	 *
	 * @throws Exception the exception
	 */
	public void checkCukeFileSyntax() throws Exception {
		String Pattern = "^.*\\s*Given\\s+.*|.*\\s*When\\s+.*|.*\\s*Then\\s+.*|.*\\s*And\\s+.*|.*\\s*But\\s+.*|^\\s*Feature:.*|^\\s*Background:.*|^\\s*Scenario:.*|^\\s*Scenario Outline:.*|^\\s*@.*|^\\s*\\|.*|^\\s*#.*|^\\s*Examples:.*";
		String gPattern = ".*\\s*Given\\s+.*|.*\\s*When\\s+.*|.*\\s*Then\\s+.*|.*\\s*And\\s+.*|.*\\s*But\\s+.*";
		ArrayList<String> errors = new ArrayList<String>();
		try {
			Collection<File> fileList = fileUtil.getFiles(fileUtil
					.getGitRootDir()
					+ "\\"
					+ fileUtil.getGitPrefixDir()
					+ "\\"
					+ "src"
					+ "\\" + "it" + "\\" + "resources");


			String msgHeader = "Please correct the cucumber keyword for the files:";
			/**
			 * For each file, check the cucumber syntax:
			 * 
			 * 1) Feature is a mandatory keyword 2) @<TAG> is mandatory 3)
			 * Scenario or Scenario Outline is mandatory. 4) Either of the words
			 * Given,When,Then,And,But needs to be on the start of each line.
			 * 
			 */
			for (File file : fileList) {
				FileReader fileReader = new FileReader(file);
				BufferedReader bufferedReader = new BufferedReader(fileReader);

				boolean isThereError = false;
				boolean isdocString = false;

				int lineCount = 1;
				String line;
				String fileHeader = "\t" + file + ":";

				while ((line = bufferedReader.readLine()) != null) {
					// Skip the blank lines.
					if (line.matches("^\\s*$")) {
						lineCount++;
						continue;
					}

					// Capture all the lines that are docstring and ignore them.
					// If the docString has started, continue till you find the
					// stop string.
					if (isdocString == true) {
						if (line.matches("\"\"\"")) {
							isdocString = false;
						}
						continue;
					}

					if (line.matches("^\\s*\"\"\"\\s*$")) {
						isdocString = true;
						continue;
					}

					if (!(line.matches(Pattern))) {
						fileHeader += "\n\t\t" + lineCount + ":" + line;
						isThereError = true;
					}

					// Skip the comments.
					if (line.matches("^\\s*#.*")) {
						lineCount++;
						continue;
					}

					line = line.replaceAll("^\\W+", "");
					String firstWord = line.split("\\s")[0];
					line = line.replaceAll("^\\s*\\w+", "");

//					if (line.matches(gPattern)) {
//						line = firstWord + " " + line;
//						fileHeader += "\n\t\t" + lineCount + ":" + line;
//						isThereError = true;
//					}
					lineCount++;
				}
				fileReader.close();
				if (isThereError == true) {
					if (errors.size() > 0) {
						errors.add(fileHeader);
					} else {
						fileHeader = msgHeader + fileHeader;
						errors.add(fileHeader);
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
		if(errors.size() > 0) {
			for (String string : errors) {
				System.out.println(string);
			}
		}
	}


	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		CucumberFileChecker cuke = new CucumberFileChecker();
		try {
			cuke.checkCukeFileSyntax();
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}
