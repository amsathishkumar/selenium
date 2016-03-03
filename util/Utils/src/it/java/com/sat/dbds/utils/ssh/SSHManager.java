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
package com.sat.dbds.utils.ssh;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Session;

import com.sat.dbds.utils.logging.LogHandler;
import com.sat.dbds.utils.selenium.Autowait;
import com.sat.dbds.utils.selenium.SeleniumUtilities;
import com.sat.dbds.utils.validation.Validate;

// TODO: Auto-generated Javadoc
/**
 * The Class SSHManager.
 */
public class SSHManager {

	/** The ssh connection map. */
	private static HashMap<String, SSH> sshConnectionMap = new HashMap<String, SSH>();

	/**
	 * Gets the ssh connection map.
	 * 
	 * @return the ssh connection map
	 */
	public static HashMap<String, SSH> getSshConnectionMap() {
		return sshConnectionMap;
	}

	/**
	 * Creates the ssh connection.
	 * 
	 * @param connectionName
	 *            the connection name
	 * @param hostname
	 *            the hostname
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @param PTY
	 *            the pty
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void createSSHConnection(String connectionName, String hostname,
			String username, String password, String PTY) throws IOException {
		SSH ssh = new SSH();
		String env = Validate.readsystemvariable("server.env");
		String separator = File.separator;
		String keyFilepath = null;
		if (Validate.readsystemvariable("server.keyfile").contains("\\")
				|| Validate.readsystemvariable("server.keyfile").contains("/")) {
			File file = new File(Validate.readsystemvariable("server.keyfile"));
			if (file.exists())
				keyFilepath = Validate.readsystemvariable("server.keyfile");
			else
				throw new IOException(
						"Key file does not exist in specified location: "
								+ Validate.readsystemvariable("server.keyfile"));
		} else
			keyFilepath = Validate.readsystemvariable("user.dir") + separator
					+ "src" + separator + "it" + separator + "resources"
					+ separator + Validate.readsystemvariable("server.keyfile");
		// String keyFilepath = System.getProperty("server.keyfilepath");
		System.out.println("Keyfilepath: " + keyFilepath);
		System.out.println("environment is" + env);
		if (env.equalsIgnoreCase("openstack")) {
			ssh.createConnectionUsingPem(hostname, "admin", "", keyFilepath);
		} else {
			ssh.createConnection(hostname, username, password);
		}
		ssh.createSession(PTY);
		getSshConnectionMap().put(connectionName, ssh);
	}

	/**
	 * This executes the command and returns the console output.
	 * 
	 * @param connectionName
	 *            the connection name
	 * @param command
	 *            the command
	 * @return the command output
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String getCommandOutput(String connectionName, String command)
			throws IOException {

		// command = "sudo -i ; " + command;
		LogHandler.info("Executing '" + command + "'...");
		Session sess = getSshConnectionMap().get(connectionName).getSess();
		if (Validate.readsystemvariable("server.env").equalsIgnoreCase(
				"openstack")) {
			sess.getStdin().write(
					("( sudo -i )|xargs echo 'Result:'" + " \n").getBytes());
		}
		sess.getStdin().write(
				("(" + command + ")|xargs echo 'Result:'" + " \n").getBytes());
		sess.getStdin().flush();
		return getTerminalOutput(connectionName);

	}

	/**
	 * This executes the command and returns the console output.
	 * 
	 * @param connectionName
	 *            the connection name
	 * @param command
	 *            the command
	 * @return the command output ps2
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String getCommandOutputPs2(String connectionName, String command)
			throws IOException {

		// command = "sudo -i ; " + command;
		LogHandler.info("Executing '" + command + "'...");
		Session sess = getSshConnectionMap().get(connectionName).getSess();
		if (Validate.readsystemvariable("server.env").equalsIgnoreCase(
				"openstack")) {
			sess.getStdin().write(("sudo -i\n").getBytes());
		}
		sess.getStdin().write((command + " \n").getBytes());
		sess.getStdin().flush();
		return getTerminalOutput(connectionName);

	}

	/**
	 * This executes the command given.
	 * 
	 * @param connectionName
	 *            the connection name
	 * @param command
	 *            the command
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String executeCommand(String connectionName, String command)
			throws IOException {

		// command = "sudo -i ; " + command;
		LogHandler.info("Executing '" + command + "'...");
		Session sess = getSshConnectionMap().get(connectionName).getSess();
		LogHandler.printToConsole(getSshConnectionMap());
		if (Validate.readsystemvariable("server.env").equalsIgnoreCase(
				"openstack")) {
			sess.getStdin().write(("sudo -i\n").getBytes());
		}
		sess.getStdin().write((command + "\n").getBytes());
		sess.getStdin().flush();
		return getTerminalOutput(connectionName);
		// LogHandler.info(getTerminalOutput(connectionName));
	}

	/**
	 * This executes the command given.
	 * 
	 * @param connectionName
	 *            the connection name
	 * @param command
	 *            the command
	 * @param timeout
	 *            the timeout
	 * @param aw
	 *            the aw
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String executeCommand(String connectionName, String command,
			int timeout, Autowait aw) throws IOException {

		// command = "sudo -i ; " + command;
		LogHandler.info("Executing '" + command + "'...");
		Session sess = getSshConnectionMap().get(connectionName).getSess();
		LogHandler.printToConsole(getSshConnectionMap());
		if (Validate.readsystemvariable("server.env").equalsIgnoreCase(
				"openstack")) {
			sess.getStdin().write(("sudo -i\n").getBytes());
		}
		sess.getStdin().write((command + "\n").getBytes());
		sess.getStdin().flush();
		SeleniumUtilities.wait(timeout, aw);
		return getTerminalOutput(connectionName);
		// LogHandler.info(getTerminalOutput(connectionName));
	}

	/**
	 * Execute command.
	 * 
	 * @param connectionName
	 *            the connection name
	 * @param command
	 *            the command
	 * @param timeout
	 *            the timeout
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String executeCommand(String connectionName, String command,
			int timeout) throws IOException {

		// command = "sudo -i ; " + command;
		LogHandler.info("Executing '" + command + "'...");
		Session sess = getSshConnectionMap().get(connectionName).getSess();
		LogHandler.printToConsole(getSshConnectionMap());
		if (Validate.readsystemvariable("server.env").equalsIgnoreCase(
				"openstack")) {
			sess.getStdin().write(("sudo -i\n").getBytes());
		}
		sess.getStdin().write((command + "\n").getBytes());
		sess.getStdin().flush();
		SeleniumUtilities.wait(timeout);
		return getTerminalOutput(connectionName);
		// LogHandler.info(getTerminalOutput(connectionName));
	}

	/**
	 * This will tail the given file for capturing the changes.
	 * 
	 * @param connectionName
	 *            the connection name
	 * @param filePath
	 *            the file path
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void tailLogContent(String connectionName, String filePath)
			throws IOException {
		LogHandler.info("Tailing the file : " + filePath);
		String[] fileNameAry;
		fileNameAry = filePath.split("/");
		String fileName = fileNameAry[fileNameAry.length - 1];
		filePath = filePath.replace(fileName, fileName.replace(".*", ""));
		executeCommand(connectionName, "rm -rf " + filePath + ".tmp.tailout");
		executeCommand(connectionName,
				"nohup  sh -c 'while true;do tail -f `ls -tr " + filePath
						+ ".* | grep -v tailout | tail -1`;done >" + filePath
						+ ".tmp.tailout' &");
	}

	/**
	 * This will return the collected tailed content.
	 * 
	 * @param connectionName
	 *            the connection name
	 * @param filePath
	 *            the file path
	 * @return the tailed content
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String getTailedContent(String connectionName, String filePath)
			throws IOException {
		LogHandler.info("Tailed content for the file: " + filePath);
		String[] fileNameAry = filePath.split("/");
		String fileName = fileNameAry[fileNameAry.length - 1];
		filePath = filePath.replace(fileName, fileName.replace(".*", ""));
		executeCommand(connectionName, "kill -9 `ps -eaf |ps -eaf |grep "
				+ fileName.replace(".*", "")
				+ " |grep ' tail -f ' |awk '{print $2}'`");
		String tailedContent = getCommandOutput(connectionName, "cat "
				+ filePath + ".tmp.tailout");
		return tailedContent;

	}

	/**
	 * Gets the tail dump.
	 * 
	 * @param connectionName
	 *            the connection name
	 * @param filePath
	 *            the file path
	 * @return the tail dump
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String getTailDump(String connectionName, String filePath)
			throws IOException {
		LogHandler.info("Tailed content for the file: " + filePath);
		String[] fileNameAry = filePath.split("/");
		String fileName = fileNameAry[fileNameAry.length - 1];
		filePath = filePath.replace(fileName, fileName.replace(".*", ""));
		executeCommand(connectionName, "kill -9 `ps -eaf |ps -eaf |grep "
				+ fileName.replace(".*", "")
				+ " |grep ' tail -f ' |awk '{print $2}'`");
		String tailedContent = getCommandOutputPs2(connectionName, "cat "
				+ filePath + ".tmp.tailout");
		return tailedContent;

	}

	/**
	 * Gets the grepped content.
	 * 
	 * @param connectionName
	 *            the connection name
	 * @param filePath
	 *            the file path
	 * @param pattern
	 *            the pattern
	 * @return the grepped content
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String getGreppedContent(String connectionName, String filePath,
			String pattern) throws IOException {
		LogHandler.info("Tailed content for the file: " + filePath);
		String[] fileNameAry = filePath.split("/");
		String fileName = fileNameAry[fileNameAry.length - 1];
		filePath = filePath.replace(fileName, fileName.replace(".*", ""));
		executeCommand(connectionName, "kill -9 `ps -eaf |ps -eaf |grep "
				+ filePath.replace(".*", "")
				+ " |grep ' tail -f ' |awk '{print $2}'`");
		String tailedContent = getCommandOutput(connectionName, "tail -750 "
				+ filePath + ".tmp.tailout | grep " + pattern);
		return tailedContent;

	}

	/**
	 * This will convert a String to UTF-8 format.
	 * 
	 * @param data
	 *            the data
	 * @param off
	 *            the off
	 * @param len
	 *            the len
	 * @return A string converted to UTF-8
	 */
	public String getString(byte[] data, int off, int len) {
		return new String(data, off, len, Charset.forName("UTF-8"));
	}

	/**
	 * Dump all the terminal output.
	 * 
	 * @param connectionName
	 *            the connection name
	 * @return A string that would the content.
	 */
	public String getTerminalOutput(String connectionName) {
		LogHandler.info("Dumping the Terminal output..");
		String dump = "";
		StringBuilder stringBuilder = new StringBuilder();
		try {
			byte[] buffer = new byte[30720];
			Session sess = getSshConnectionMap().get(connectionName).getSess();
			InputStream stdout = sess.getStdout();
			while (true) {
				if (stdout.available() == 0) {

					int conditions = sess.waitForCondition(
							ChannelCondition.STDERR_DATA
									| ChannelCondition.STDOUT_DATA
									| ChannelCondition.EOF, 2000);

					/* Wait no longer than 2 seconds (= 2000 milliseconds) */

					if ((conditions & ChannelCondition.TIMEOUT) != 0) {
						break;
					}

					if ((conditions & (ChannelCondition.STDOUT_DATA | ChannelCondition.STDERR_DATA)) == 0)
						throw new IllegalStateException(
								"Unexpected condition result (" + conditions
										+ ")");
				}

				if (stdout.available() > 0) {
					int len = stdout.read(buffer);

					String output = "";
					for (int i = 0; i < len; i++) {
						output += ((char) buffer[i]);
					}

					output = getString(buffer, 0, len);

					String[] lines = output.split("\n");
					for (int i = 0; i < lines.length; i++) {
						stringBuilder.append(lines[i]);
					}
				}

			}
			dump = stringBuilder.toString();

		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		LogHandler.info(dump);
		return dump;
	}

	/**
	 * Return the match for LITERAL String match...
	 * 
	 * @param sourceContent
	 *            the source content
	 * @param pattern
	 *            the pattern
	 * @return the matched lines
	 */
	public ArrayList<String> getMatchedLines(String sourceContent,
			String pattern) {
		LogHandler.info("Checking the Source content for " + pattern);
		ArrayList<String> matches = new ArrayList<String>();
		matches.clear();

		Pattern pat = Pattern.compile(pattern, Pattern.MULTILINE
				| Pattern.DOTALL | Pattern.LITERAL);
		Matcher matcher = pat.matcher(sourceContent);
		// check all occurance
		while (matcher.find()) {
			if (matcher.groupCount() == 0) {
				matches.add(matcher.group(0));
			} else {
				for (int group = 1; group <= matcher.groupCount(); group++) {
					matches.add(matcher.group(group));
				}
			}
		}
		return matches;
	}

	/**
	 * Matches the incoming source content for the mentioned pattern.
	 * 
	 * @param sourceContent
	 *            the source content
	 * @param pattern
	 *            the pattern
	 * @return the matched lines
	 */
	public ArrayList<String> getMatchingStrings(String sourceContent,
			String pattern) {
		LogHandler.info("Checking the Source content for " + pattern);
		ArrayList<String> matches = new ArrayList<String>();
		matches.clear();

		Pattern pat = Pattern.compile(pattern, Pattern.MULTILINE
				| Pattern.DOTALL);
		Matcher matcher = pat.matcher(sourceContent);
		// check all occurance
		while (matcher.find()) {
			if (matcher.groupCount() == 0) {
				matches.add(matcher.group(0));
			} else {
				for (int group = 1; group <= matcher.groupCount(); group++) {
					matches.add(matcher.group(group));
				}
			}
		}
		LogHandler.info("[DEBUG]> Matching strings are: " + matches);
		return matches;
	}

	/**
	 * This method performs Tear-down operation on the given connection name if
	 * it is active.
	 * 
	 * @param connectionName
	 *            the connection name
	 */
	public static void teardownConnection(String connectionName) {
		LogHandler.info("Tearing down the connection: " + connectionName);
		SSH ssh = getSshConnectionMap().get(connectionName);
		if (ssh != null) {
			ssh.closeSession();
			ssh.closeConnection();

		}

	}

	/**
	 * This method performs Tear-down operation on all the active connections.
	 */
	public static void teardownAllConnections() {
		for (String connectionName : getSshConnectionMap().keySet()) {
			teardownConnection(connectionName);
		}
	}

	/**
	 * Tail log content.
	 * 
	 * @param connectionName
	 *            the connection name
	 * @param filePath
	 *            the file path
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void tailLogContent(String connectionName, String... filePath)
			throws IOException {
		LogHandler.info("Tailing the file sat1: " + filePath[0]);
		executeCommand(connectionName, "rm -rf " + filePath[0] + ".tmp.tailout");
		executeCommand(connectionName, "nohup  tail -0f " + filePath[0] + " > "
				+ filePath[0] + ".tmp.tailout &");
	}

	/**
	 * Start snoop dump.
	 * 
	 * @param connectionName
	 *            the connection name
	 * @param destIP
	 *            the dest ip
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void startSnoopDump(String connectionName, String destIP)
			throws IOException {
		// LogHandler.info("Tailing the file : " + filePath);
		executeCommand(connectionName, "rm -rf /tmp/snoopout");
		executeCommand(connectionName, "nohup  snoop -d e1000g1 -v -x0 host "
				+ destIP + " > /tmp/snoopout &");

	}

	/**
	 * Gets the tailed content.
	 * 
	 * @param connectionName
	 *            the connection name
	 * @param filePath
	 *            the file path
	 * @return the tailed content
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String getTailedContent(String connectionName, String... filePath)
			throws IOException {
		LogHandler.info("Tailed content for the file: sat " + filePath[0]);
		executeCommand(connectionName, "kill -9 `ps -eaf | grep \" tail -0f "
				+ filePath[0] + "\" | awk '{print $2}' | tail -1`");
		String tailedContent = getCommandOutput(connectionName, "cat "
				+ filePath[0] + ".tmp.tailout");
		return tailedContent;

	}

	/**
	 * Gets the tail dump.
	 * 
	 * @param connectionName
	 *            the connection name
	 * @param filePath
	 *            the file path
	 * @return the tail dump
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String getTailDump(String connectionName, String... filePath)
			throws IOException {
		LogHandler.info("Tailed content for the file in getTailDump method"
				+ filePath[0]);
		executeCommand(connectionName, "kill -9 `ps -eaf | grep \" tail -0f "
				+ filePath[0] + "\" | awk '{print $2}' | head -1`");
		String tailedContent = getCommandOutputPs2(connectionName, "cat "
				+ filePath[0] + ".tmp.tailout");
		LogHandler.info("killed the tail");
		return tailedContent;

	}

	public void createSSHConnectionUsingPem(String connectionName,
			String hostname, String username, String password, String pemFile,
			String PTY) throws IOException {
		SSH ssh = new SSH();
		ssh.createConnectionUsingPem(hostname, username, password, pemFile);
		ssh.createSession(PTY);
		getSshConnectionMap().put(connectionName, ssh);
	}

}
