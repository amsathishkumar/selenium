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
package com.cisco.dbds.utils.ssh;

import java.io.File;
import java.io.IOException;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;

import com.cisco.dbds.utils.logging.LogHandler;

// TODO: Auto-generated Javadoc
/**
 * The Class SSH.
 */
public class SSH {

	/** The conn. */
	private Connection conn = null;

	/** The sess. */
	private Session sess = null;

	/**
	 * Establishes a SSh Connection.
	 *
	 * @param hostname the hostname
	 * @param username the username
	 * @param password the password
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void createConnection(String hostname, String username,
			String password) throws IOException {
		LogHandler.info("Establishing Connection...");
		conn = new Connection(hostname);
		conn.connect();

		// Authentication with user name and password
		boolean isAuthenticated = conn.authenticateWithPassword(username,
				password);
		LogHandler.printToConsole("Authenticating...");
		if (isAuthenticated == false) {
			LogHandler.error("Authentication failed.");
			throw new IOException("Authentication failed.");
		}
	}

	/**
	 * Closes the SSH Session.
	 */
	public void closeSession() {
		// Close the session
		LogHandler.info("Closing the session...");
		sess.close();

		return;
	}

	/**
	 * Closes the SSH Connection.
	 */
	public void closeConnection() {
		// Close the connection.
		LogHandler.info("Closing the connection...");
		conn.close();
		return;
	}

	/**
	 * Creates a SSH Session.
	 *
	 * @param PTY the pty
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void createSession(String PTY) throws IOException {
		LogHandler.info("Creating  a session...");

		// Create a session
		sess = conn.openSession();

		// PTY type
		sess.requestPTY(PTY);
		sess.startShell();

	}

	/**
	 * Gets the conn.
	 *
	 * @return the conn
	 */
	public Connection getConn() {
		return conn;
	}

	/**
	 * Gets the sess.
	 *
	 * @return the sess
	 */
	public Session getSess() {
		return sess;
	}
	
    public void createConnectionUsingPem(String hostname, String username,
            String password,String pemFile) throws IOException {
     LogHandler.info("Establishing Connection...");
     conn = new Connection(hostname);
     conn.connect();
     File pemKeyFile = new File(pemFile);
     boolean isAuth = conn.authenticateWithPublicKey(username, pemKeyFile,password );
     LogHandler.printToConsole("Authenticating...");
     if (isAuth == false) {
            LogHandler.error("Authentication failed.");
            throw new IOException("Authentication failed.");
     }
}      



}
