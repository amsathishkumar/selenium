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
package com.cisco.dbds.utils.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTPClient;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SFTPv3Client;
import ch.ethz.ssh2.SFTPv3FileHandle;

import com.cisco.dbds.utils.logging.LogHandler;

// TODO: Auto-generated Javadoc
/**
 * A program that demonstrates how to upload files from local computer to a
 * remote FTP server using Apache Commons Net API.
 * 
 * @author www.codejava.net
 */
public class FTPHandler {

	/** The ftp client. */
	private FTPClient ftpClient = null;

	/** The file name. */
	public static String fileName = "./src/it/resources/com/cisco/inputFiles/modDhctConfig.xml";

	/** The filepl. */
	public static String filepl = "./src/it/resources/com/cisco/inputFiles/bossTest.pl";

	/** The file new. */
	public static String fileNew = "./src/it/resources/com/cisco/inputFiles/ModifyDhctConfiguration.req.xml";

	/** The fileauthcode. */
	public static String fileauthcode ="./src/it/resources/com/cisco/inputFiles/AuthCodeHDEnablDisableModDhCT.xml";

	/** The file vct. */
	public static String fileVCT = "./src/it/resources/com/cisco/inputFiles/Ttv2857798c.req.xml";

	/**
	 * Ftp connect.
	 */
	public void ftpConnect() {
		try {

			String server = System.getProperty("dtacs.db.ip");
			int port = 21;
			String user = System.getProperty("dtacs.ftp.username");
			String pass = System.getProperty("dtacs.ftp.password");

			ftpClient = new FTPClient();
			ftpClient.connect(server, port);
			boolean loginStatus = ftpClient.login(user, pass);

			if (loginStatus) {
				LogHandler.info("FTP connection  sucess");
			}
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTPClient.ASCII_FILE_TYPE);
		} catch (IOException ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/**
	 * Ftp copy.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void ftpCopy() throws IOException {

		File srcfile = new File(fileName);

		String toDestination = "/export/home/dncsop/modDhctConfig.xml";
		InputStream inputStream = new FileInputStream(srcfile);

		System.out.println("Start uploading modDhctConfig file");
		boolean done = ftpClient.storeFile(toDestination, inputStream);

		if (done) {
			LogHandler.info("File copied successfully to server");
		}

		inputStream.close();

		try {
			if (ftpClient.isConnected()) {
				ftpClient.logout();
				ftpClient.disconnect();
			}
		} catch (IOException ex) {
			ex.printStackTrace();

		}
	}
	
	public void ftpCopy(String source, String destination) throws IOException {

		File srcfile = new File(source);

		String toDestination = destination;
		InputStream inputStream = new FileInputStream(srcfile);

		System.out.println("Start uploading modDhctConfig file");
		boolean done = ftpClient.storeFile(toDestination, inputStream);

		if (done) {
			LogHandler.info("File copied successfully to server");
		}

		inputStream.close();

		try {
			if (ftpClient.isConnected()) {
				ftpClient.logout();
				ftpClient.disconnect();
			}
		} catch (IOException ex) {
			ex.printStackTrace();

		}
	}

	/**
	 * Sftp_get.
	 *
	 * @param host the host
	 * @param user the user
	 * @param pass the pass
	 * @param rpath the rpath
	 * @param lpath the lpath
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void sftp_get(String host, String user, String pass,
			String rpath, String lpath) throws IOException {
		File rfile = new File(rpath);
		File lfile = new File(lpath);
		if (lfile.exists() && lfile.isDirectory()) {
			lfile = new File(lfile, rfile.getName());
		}

		Connection ssh = new Connection(host);
		ssh.connect();
		ssh.authenticateWithPassword(user, pass);
		SFTPv3Client sftp = new SFTPv3Client(ssh);
		SFTPv3FileHandle file = sftp.openFileRO(rpath);

		long fileOffset = 0;
		byte[] dst = new byte[32768];
		int i = 0;
		FileOutputStream output = new FileOutputStream(lfile);
		while ((i = sftp.read(file, fileOffset, dst, 0, dst.length)) != -1) {
			fileOffset += i;
			output.write(dst, 0, i);
		}

		output.close();
		sftp.closeFile(file);
		sftp.close();
	}

	/**
	 * Sftp_put.
	 *
	 * @param host the host
	 * @param user the user
	 * @param pass the pass
	 * @param lpath the lpath
	 * @param rpath the rpath
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void sftp_put(String host, String user, String pass,
			String lpath, String rpath) throws IOException {
		File rfile = new File(rpath);
		File lfile = new File(lpath);
		System.out.println("rfile: " + rfile);
		if (!lfile.exists() || lfile.isDirectory()) {
			throw new IOException("Local file must be a regular file: " + lpath);
		}

		Connection ssh = new Connection(host);
		ssh.connect();
		ssh.authenticateWithPassword(user, pass);
		SFTPv3Client sftp = new SFTPv3Client(ssh);
		SFTPv3FileHandle file = sftp.createFileTruncate(rpath);

		long fileOffset = 0;
		byte[] src = new byte[32768];
		int i = 0;
		FileInputStream input = new FileInputStream(lfile);
		while ((i = input.read(src)) != -1) {
			sftp.write(file, fileOffset, src, 0, i);
			fileOffset += i;
		}

		input.close();
		sftp.closeFile(file);
		sftp.close();
	}


	/**
	 * Ftp copy mod dhct transactions pl.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void ftpCopyModDhctTransactionsPl() throws IOException {

		File srcfile = new File(filepl);

		String toDestination = "/dvs/dtacs/doc/boss/html/bossTest.pl";
		InputStream inputStream = new FileInputStream(srcfile);

		System.out.println("Start uploading modDhctConfig file");
		boolean done = ftpClient.storeFile(toDestination, inputStream);

		if (done) {
			LogHandler.info("File copied successfully to server");
		}
		else{
			LogHandler.info("File cpoying failed");
		}

		inputStream.close();

		try {
			if (ftpClient.isConnected()) {
				ftpClient.logout();
				ftpClient.disconnect();
			}
		} catch (IOException ex) {
			ex.printStackTrace();

		}
	}

	/**
	 * Ftp copy mod dhct transactions.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void ftpCopyModDhctTransactions() throws IOException {

		File srcfile = new File(fileNew);

		String toDestination = "/dvs/dtacs/doc/boss/html/ModifyDhctConfiguration.req.xml";
		InputStream inputStream = new FileInputStream(srcfile);
		boolean done = ftpClient.storeFile(toDestination, inputStream);

		if (done) {
			LogHandler.info("File copied successfully to server");
		}
		else{
			LogHandler.info("File cpoying failed");
		}

		inputStream.close();

		try {
			if (ftpClient.isConnected()) {
				ftpClient.logout();
				ftpClient.disconnect();
			}
		} catch (IOException ex) {
			ex.printStackTrace();

		}
	}

	/**
	 * Ftp copy mod dhct transactions hd enable disable auth code assignment.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void ftpCopyModDhctTransactionsHdEnableDisableAuthCodeAssignment() throws IOException {

		File srcfile = new File(fileauthcode);

		String toDestination = "/dvs/dtacs/doc/boss/html/AuthCodeHDEnablDisableModDhCT.xml";
		InputStream inputStream = new FileInputStream(srcfile);

		System.out.println("Start uploading modDhctConfigVCtTest file");
		boolean done = ftpClient.storeFile(toDestination, inputStream);

		if (done) {
			LogHandler.info("File copied successfully to server");
		}
		else{
			LogHandler.info("File cpoying failed");
		}

		inputStream.close();

		try {
			if (ftpClient.isConnected()) {
				ftpClient.logout();
				ftpClient.disconnect();
			}
		} catch (IOException ex) {
			ex.printStackTrace();

		}
	}

	/**
	 * Ftp copy mod dhct transactions test case vct.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void ftpCopyModDhctTransactionsTestCaseVCT() throws IOException {

		File srcfile = new File(fileVCT);

		String toDestination = "/dvs/dtacs/doc/boss/html/Ttv2857798c.req.xml";
		InputStream inputStream = new FileInputStream(srcfile);

		System.out.println("Start uploading modDhctConfigVCtTest file");
		boolean done = ftpClient.storeFile(toDestination, inputStream);

		if (done) {
			LogHandler.info("File copied successfully to server");
		}
		else{
			LogHandler.info("File cpoying failed");
		}

		inputStream.close();

		try {
			if (ftpClient.isConnected()) {
				ftpClient.logout();
				ftpClient.disconnect();
			}
		} catch (IOException ex) {
			ex.printStackTrace();

		}
	}


}
