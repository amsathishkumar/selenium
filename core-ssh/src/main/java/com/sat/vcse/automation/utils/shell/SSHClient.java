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
 * @Project: Automation Core
 * @Author: amsathishkumar
 * @Version: 1.0
 * @Description: Shell Client Functions
 * @Date 6/10-2015
 */

package com.sat.vcse.automation.utils.shell;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.sat.vcse.automation.utils.Autowait;
import com.sat.vcse.automation.utils.CommonUtils;
import com.sat.vcse.automation.utils.datatype.CoreRuntimeException;
import com.sat.vcse.automation.utils.logging.LogHandler;
import com.sat.vcse.automation.utils.datatype.CommandResponse;
import com.sat.vcse.automation.utils.datatype.Server;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class SSHClient extends Server{

	private final static String CLASS_NAME = "SSHClient: ";
	private String lineSeparatopr= "line.separator";
	private String pemFile;
	private String pemFilePassword;
	private int commandTimeoutInSeconds;
	
	private Connection sshConn;
	private boolean isLogInDone = false;
	private boolean explicitUserLogOut = false;
	private Session sshSession;
	private SSHClient(){
	}
	public static class Builder{
		private String hostOrIP;
		private int portNumber=22; //default port
		private String username;
		private String password;
		private String pemFile;
		private String pemFilePassword;
		private int commandTimeoutInSeconds=1;//default 1 second
		private boolean explicitUserLogOut = false;
				
		public Builder withHostOrIPAddress(final String hostOrIP){			
			this.hostOrIP = hostOrIP;
			return this;
		}
		public Builder withPort(final int portNumber){			
			this.portNumber = portNumber;
			return this;
		}
		public Builder withUserNameAndPassword(final String username, final String password){			
			this.username = username;
			this.password = password;
			return this;
		}
		
		/**
		 * 
		 * @param username
		 * @param pemFilePath : Complete path of the pem file, 
		 * or if the file is present in class path then only file name is enough, no need to provide complete path
		 * @return Builder
		 */
		public Builder withPemAuthentication(final String username,final String pemFilePath){			
			this.username = username;
			this.pemFile = pemFilePath;
			return this;
		}
		/**
		 * Supply this parameter only when reading the pemFile itself requires password
		 * @param optionalPasswordIfPemFileEncrypted
		 * @return Builder
		 */
		public Builder withPemAuthentication(final String optionalPasswordIfPemFileEncrypted){			
			this.pemFilePassword=optionalPasswordIfPemFileEncrypted;
			return this;
		}
		
		public Builder withCommandTimeoutInSeconds(final int commandTimeoutInSeconds){			
			this.commandTimeoutInSeconds = commandTimeoutInSeconds;
			return this;
		}
		/**
		 * If this is selected, then client application should explicitly call "logOut" method to close the resource
		 * If this is not selected, then for every function the client application calls, it will open and close resources.
		 * @return Builder
		 */
		public Builder withIwillLogOutExplicitly(){			
			this.explicitUserLogOut = true;
			return this;
		}

		public SSHClient build(){
			if(this.username==null ){				
				throw new CoreRuntimeException("Please provide Username.");				
			}
			if(this.pemFile==null && this.password==null){				
				throw new CoreRuntimeException("Please provide either Password or Authetication file path.");				
			}
			if(pemFile != null && !new File(pemFile).exists()){
				throw new CoreRuntimeException("Authetication file does not exist. "+pemFile);	
			}
			
			SSHClient sshClient = new SSHClient();
			sshClient.setHostName(this.hostOrIP);
			sshClient.setPortNumber(this.portNumber);
			sshClient.setUsername(this.username);
			sshClient.explicitUserLogOut=this.explicitUserLogOut;
			sshClient.pemFilePassword=pemFilePassword;
			if(pemFile!=null){
				sshClient.pemFile=this.pemFile;		
			}else{
				sshClient.setPassword(this.password);
			}
			sshClient.commandTimeoutInSeconds = this.commandTimeoutInSeconds;
			
			return sshClient;
		}
	}


	/**
	 * Connect to the SSH server
	 * 
	 * @return True if the connection succeeded, false otherwise
	 */
	private boolean logIn() // throws IOException 
	{
		final String METHOD_NAME = "logIn(): ";
		boolean isAuthOk = false;
		try {
			// Connect to the SSH server
			sshConn = new Connection(this.getHostName(),this.getPortNumber());
			sshConn.connect();
			if(this.pemFile != null){
				isAuthOk = sshConn.authenticateWithPublicKey(this.getUsername(), new File(this.pemFile), this.pemFilePassword);
			}else{
				isAuthOk = sshConn.authenticateWithPassword(this.getUsername(), this.getPassword());
			}		
			
			if(!isAuthOk){
				LogHandler.error(CLASS_NAME + METHOD_NAME + "Authentication unsuccessful.");
				throw new CoreRuntimeException(CLASS_NAME + METHOD_NAME + "Authentication unsuccessful.");
			}
						
		} catch (IOException exp) {
			LogHandler.error(CLASS_NAME + METHOD_NAME + "Exception: Unable to connect to "+this.getHostName() + " with given credentials. "+ exp.getMessage());
			throw new CoreRuntimeException(CLASS_NAME + METHOD_NAME + "Exception: Unable to connect to "+this.getHostName() + " with given credentials. "+ exp.getMessage());
			
		}
		this.isLogInDone = isAuthOk;
		return isAuthOk;
	}
	
	
	private Session getSession() throws IOException{
		
		if(explicitUserLogOut){ //then reuse session, dont create new session
			if(sshSession == null){//create if this is the first time invocation
				this.sshSession = this.sshConn.openSession();
			}
		}else{
			this.sshSession = this.sshConn.openSession();
		}
		return this.sshSession;
	}

	/**
	 * Executes a passed in command and returns the response from the SSH server
	 * 
	 * @param command
	 *            The command to execute
	 * @return The response that is returned from the server (or null)
	 */
	public CommandResponse executeAndReturnResponse(String command) {
		final String METHOD_NAME = "executeAndReturnResponse(command): ";
		CommandResponse commandResponse=new CommandResponse();
		//Session session = null;
		try {
			if(!this.isLogInDone){
				logIn();
			}
			// Open an SSH Session
			this.sshSession=getSession();
			
						// Execute the command
			this.sshSession.execCommand(command);
			// Wait for the command execution to complete (stdout & stderr reach
			// EOF)
			this.sshSession.waitForCondition(ChannelCondition.EOF, this.commandTimeoutInSeconds*1000);//millisecond

			// Read the Output
			StringBuilder stdOut = readInputStream(this.sshSession.getStdout());
			StringBuilder stdErr = readInputStream(this.sshSession.getStderr());			
			commandResponse.setStdOut(stdOut.toString());
			commandResponse.setStdErr(stdErr.toString());
			commandResponse.setExitStatus(this.sshSession.getExitStatus());
			
			
		} catch (IOException exp) {
			LogHandler.error(CLASS_NAME + METHOD_NAME + "Exception: during execution of command("+ command  +") " +exp.getMessage());
			throw new CoreRuntimeException(exp,CLASS_NAME + METHOD_NAME + "Exception: during execution of command("+ command  +") " +exp.getMessage());
		}
		finally{
			close();	
			tryLogOut();			
		}
		return commandResponse;
	}
	
	/**
	 * Executes multiple commands for a in a single session
	 * @param multiCommands
	 */
	public void executeMultiCommands(final String[] multiCommands) {
		if(multiCommands != null){
			executeMultiCommands(Arrays.asList(multiCommands));
		}
	}
	/**
	 * Executes multiple commands for a in a single session
	 * @param lstCommands
	 */
	public void executeMultiCommands(final List<String> lstCommands) {
		final String METHOD_NAME = "executeMultiCommands(lstCommands): ";
		OutputStream ois=null;
		if(lstCommands !=null){
			try {
				
				if(!this.isLogInDone){
					logIn();
				}
				// Open an SSH Session
				getSession();
				this.sshSession.requestPTY("vt220");
				this.sshSession.startShell();		
				ois = this.sshSession.getStdin();
				 for(String command:lstCommands){			
					sendCommand(ois, command+"\n");		
					try{
						Thread.sleep(500);
					}catch(InterruptedException ex) {
						Thread.currentThread().interrupt();
					}
	
				 }
				 ois.close();
	
			} catch (IOException exp) {
				System.err.println(CLASS_NAME + METHOD_NAME + "Exception: during execution of command("+ lstCommands  +") " +exp.getMessage());
				throw new CoreRuntimeException(exp,CLASS_NAME + METHOD_NAME + "Exception: during execution of command("+ lstCommands  +") " +exp.getMessage());
			}
			finally{
				close();	
				tryLogOut();			
			}
		}
		
	}	

	/**
	 * 
	 * @param is : InputStream from which to read
	 * @return StringBuilder : containing inputs from underlying InputStream
	 * @throws IOException
	 */
	private StringBuilder readInputStream(final InputStream is) throws IOException{
		StreamGobbler stdIsGobbler=null;
		BufferedReader stderrBr =null;
		StringBuilder respSb= new StringBuilder();
		try{
			stdIsGobbler = new StreamGobbler(is);
			stderrBr = new BufferedReader(new InputStreamReader(
					stdIsGobbler));
			String lineRead = stderrBr.readLine();
			
			while (lineRead != null) {
				respSb.append(lineRead + System.getProperty(lineSeparatopr));
				lineRead = stderrBr.readLine();
			}
		}finally{
			closeSafe(is);
			closeSafe(stdIsGobbler);
			closeSafe(stderrBr);
		}
		return respSb;
	}


	/**
	 * SCPs a passed in class resource (file) to the specified filename
	 * and file directory and sets the mode of the file on the SSH server
	 * 
	 * @param localfile
	 *            
	 * @param remoteFileName
	 *            The name that will be given to the SCP'ed file on the SSH
	 *            server
	 * @param remoteTargetDirectory
	 *            The directory where the file will be placed on the SSH server
	 * @param mode
	 *            The file mode that will be assigned to the SCP'ed file, e.g.
	 *            0744
	 */
	public void scpFile(final String localfile,final String remoteFileName,
			final String remoteTargetDirectory,final  String mode) {
		scpFile(new File(localfile), remoteFileName, remoteTargetDirectory, mode);
	}
	
	/**
	 * Stores the local local file into remote home directory of the logged in user.
	 * Remote file name will be same as local file name, and the mode will be default mode ie 0600
	 * @param localfile
	 */
	public void scpFile(final String localfile) {
		scpFile(new File(localfile), null, null, null);
	}
	
	/**
	 * SCPs a passed in class resource (file) to the specified filename
	 * and file directory and sets the mode of the file on the SSH server
	 * 
	 * @param inputFile : local file which needs to be SCP'ed
	 *            
	 * @param remoteFileName
	 *            The name that will be given to the SCP'ed file on the SSH
	 *            server
	 * @param remoteTargetDirectory
	 *            The directory where the file will be placed on the SSH server
	 * @param mode
	 *            The file mode that will be assigned to the SCP'ed file, e.g.
	 *            0744
	 */
	public void scpFile(final File inputFile, String remoteFileName,
			final String remoteTargetDirectory,final String mode){		

		final String METHOD_NAME = "scpFile(File,String,String,String): ";
		if(!this.isLogInDone){
			logIn();
		} 
				
		if(remoteFileName == null){
			remoteFileName=inputFile.getName();
		}
		
		if(!inputFile.exists()){
			//This means the file might be present in subclass path.
			//So try that now
			scpFile(readFromClassPath(inputFile.getPath()), remoteFileName, remoteTargetDirectory, mode);	
			return;
		}
		final SCPClient scpClient = new SCPClient(sshConn);
		try(OutputStream scpOutStream = scpClient.put(remoteFileName, inputFile.length(),remoteTargetDirectory, mode);){		
			scpOutStream.write(FileUtils.readFileToByteArray(inputFile));
			scpOutStream.flush();
		}catch (IOException exp) {
			LogHandler.error(CLASS_NAME + METHOD_NAME + "Exception: scping" +exp.getMessage());
			throw new CoreRuntimeException(exp,CLASS_NAME + METHOD_NAME + "Exception: scping" +exp.getMessage());
		}finally{
			tryLogOut();
		}
	
	}
	
	/**
	 * 
	 * @param inputBytes : local bytes to be stored in remote server
	 * @param remoteFileName
	 *            The name that will be given to the SCP'ed file on the SSH
	 *            server
	 * @param remoteTargetDirectory
	 *            The directory where the file will be placed on the SSH server
	 * @param mode
	 *            The file mode that will be assigned to the SCP'ed file, e.g.
	 *            0744
	 */
	public void scpFile(final byte[] inputBytes, final String remoteFileName,
			final String remoteTargetDirectory,final String mode){	

		final String METHOD_NAME = "scpFile(byte[],String,String,String): ";
		if(remoteFileName == null){
			LogHandler.error("Remote file name is required to SCP with byte[]");
			throw new CoreRuntimeException("Remote file name is required to SCP with byte[]");
		}
		
		if(!this.isLogInDone){
			logIn();
		} 
		final SCPClient scpClient = new SCPClient(sshConn);
		try(OutputStream scpOutStream = scpClient.put(remoteFileName, inputBytes.length,remoteTargetDirectory, mode);){		
			scpOutStream.write(inputBytes);
			scpOutStream.flush();
		} catch (IOException exp) {
			LogHandler.error(CLASS_NAME + METHOD_NAME + "Exception: scping" +exp.getMessage());
			throw new CoreRuntimeException(exp,CLASS_NAME + METHOD_NAME + "Exception: scping" +exp.getMessage());
		}finally{
			tryLogOut();
		}
	
	}
	
	/**
	 * 
	 * @param localfile : local file which needs to be SCP'ed
	 * @param remoteTargetDirectory : The directory where the file will be placed on the SSH server
	 * @param mode : The file mode that will be assigned to the SCP'ed file, e.g. 0744
	 */
	public void scpFile(final String localfile,
			final String remoteTargetDirectory, final String mode) {
		scpFile(new File(localfile), null, remoteTargetDirectory, mode);
	}
	
	/**
	 * This stores the local file in the remote target directory with the same name as in local and default mode ie 0600
	 * @param localfile : local file which needs to be put uploaded into remote
	 * @param remoteTargetDirectory
	 *            The directory where the file will be placed on the SSH server
	 */
	public void scpFile(final String localfile,
			final String remoteTargetDirectory) {
		scpFile(localfile, null, remoteTargetDirectory, null);
	}
	
	/**
	 * downloads the remote file into local machine
	 * @param completeRemoteFilePath : complete remote file path which needs to be downloaded
	 * @param localfileCompletePath  :local file to which the remote file content has to be saved
	 */
	public void downloadFileToLocal(final String completeRemoteFilePath, final String localfileCompletePath) {
		

		final String METHOD_NAME = "donloadFileToLocal(String,String): ";
		if(!this.isLogInDone){
			logIn();
		} 
		final SCPClient scpClient = new SCPClient(sshConn);
		try(InputStream scpInputStream = scpClient.get(completeRemoteFilePath);	
				FileOutputStream fos = new FileOutputStream(new File(localfileCompletePath));){		

			int bytes;
			while((bytes=scpInputStream.read())!=-1){
				fos.write(bytes);				
			}
			fos.flush();

		} catch (IOException exp) {
			LogHandler.error(CLASS_NAME + METHOD_NAME + "Exception: during downloading file to local " +exp.getMessage());
			throw new CoreRuntimeException(exp,CLASS_NAME + METHOD_NAME + "Exception: during downloading file to local" +exp.getMessage());
		}finally{
			tryLogOut();
		}
	
	
	}

	private void tryLogOut() {
		if(!this.explicitUserLogOut){
			logout();
		}		
	}

	private void close() {
		if(this.sshSession!=null){
			this.sshSession.close();
			this.sshSession= null;
		}
	}
	/**
	 * Logs out of the SSH server
	 */
	public void logout() {
			close();
			this.isLogInDone= false;			
			this.sshConn.close();			
	}

	/**
	 * Returns true if the underlying authentication is complete, otherwise
	 * returns false
	 * 
	 * @return True if authentication is complete, else returns false
	 */
	public boolean isAuthenticationComplete() {
		return this.sshConn.isAuthenticationComplete();
	}
	

	 
	/**
	 * Sends a command to the server.
	 * @param command a string representing the command to be sent
	 * @throws IOException
	 */
	private void sendCommand(OutputStream ois,String input)  {
		final String METHOD_NAME = "sendCommand(string): ";
		try{
			ois.write((input).getBytes());
			ois.flush();
		} catch (IOException exp) {
			LogHandler.error(CLASS_NAME + METHOD_NAME + "Exception during executing command" +exp.getMessage());
			throw new CoreRuntimeException(exp,CLASS_NAME + METHOD_NAME + "Exception during executing command" +exp.getMessage());
		}
	}
	/**
	 * Sends a command that prompts to the server then sends an input after some delay.
	 * @param command a string representing the command to be sent
	 * @param multipleInput a string representing the input for the prompt
	 * @param delayInMilliSecond : an int representing the delay between sending the command and input in milliseconds
	 */	       
	public void sendPromptingCommand(final String command,final String multipleInput[], final int delayInMilliSecond) {
		final String METHOD_NAME = "sendPromptingCommand(string,String[], int): ";
		OutputStream ois = null;
		//Session session = null;
		try {
			if(!this.isLogInDone){
				logIn();
			} 
			this.sshSession = getSession();
			this.sshSession.requestPTY("vt220");
			this.sshSession.startShell();		
			ois = this.sshSession.getStdin();
			sendCommand(ois, command+"\n");			
			//Now send the inputs with wiating for the specified
			for(String input:multipleInput){
				try{
					Thread.sleep(delayInMilliSecond);
				}catch(InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
				sendCommand(ois, input+"\n");
			}
		} catch (IOException exp) {			
			LogHandler.error(CLASS_NAME + METHOD_NAME + "Exception during execution of sendPromptingCommand()" +exp.getMessage());
			throw new CoreRuntimeException(exp,CLASS_NAME + METHOD_NAME + "Exception during execution of sendPromptingCommand()" +exp.getMessage());
		
		}finally{
			closeSafe(ois);
			close();			
			tryLogOut();
		}
	}

	
	/**
	 * Sends a command that prompts to the server then sends an input after some delay.
	 * @param command a string representing the command to be sent
	 * @param promptInput a string representing the input for the prompt
	 * @param delayInMilliSecond an int representing the delay between sending the command and input in milliseconds
	 */
	public void sendPromptingCommand(final String command,final String promptInput, final int delayInMilliSecond) {
		sendPromptingCommand(command, new String[]{promptInput}, delayInMilliSecond);

	}


	private static void closeSafe(final Closeable resource){
		if(resource !=null){
			try {
				resource.close();
			} catch (IOException e) {
			}
		}	
	}
	private byte[] readFromClassPath(final String localfile) {
		final String METHOD_NAME = "readFromClassPath(String): ";
        int readbyte;
		try(InputStream cris = SSHClient.class.getResourceAsStream(localfile);
		    ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
			readbyte = cris.read();
	        while(readbyte != -1) {
                baos.write(readbyte);
                readbyte = cris.read(); 
	        } 
	        return baos.toByteArray();
		} catch (IOException exp) {
			LogHandler.error(CLASS_NAME + METHOD_NAME + "Exception: reading file content from "+localfile +exp.getMessage());
			throw new CoreRuntimeException(exp,CLASS_NAME + METHOD_NAME + "Exception: reading file content from "+localfile +exp.getMessage());
		}

	}	
	
	/**
	 * Sends a command that prompts to the server then sends an input after some delay.
	 * @param command a string representing the command to be sent
	 * @param promptInput a string representing the input for the prompt
	 * @param delayInMilliSecond : an int representing the delay between sending the command and input in milliseconds
	 * @param autowait
	 */
	public void sendPromptingCommand(final String command,final String promptInput, final int delayInMilliSecond, final Autowait autowait) {
		sendPromptingCommand(command, new String[]{promptInput}, delayInMilliSecond,autowait);

	}
	
	/**
	 * Sends a command that prompts to the server then sends an input after some delay.
	 * @param command a string representing the command to be sent
	 * @param multipleInput a string representing the input for the prompt
	 * @param delayInMilliSecond : an int representing the delay between sending the command and input in milliseconds
	 * @param autowait
	 */
	public void sendPromptingCommand(final String command,final String multipleInput[], final int delayInMilliSecond, final Autowait autowait) {
		final String METHOD_NAME = "sendPromptingCommand(string,String[], int, autowait): ";
		OutputStream ois = null;
		//Session session = null;
		try {
			if(!this.isLogInDone){
				logIn();
			} 
			this.sshSession = getSession();
			this.sshSession.requestPTY("vt220");
			this.sshSession.startShell();		
			ois = this.sshSession.getStdin();
			sendCommand(ois, command+"\n");			
			//Now send the inputs with waiting for the specified
			for(String input:multipleInput){
				CommonUtils.wait(delayInMilliSecond, autowait);
				sendCommand(ois, input+"\n");
			}
		} catch (IOException exp) {			
			LogHandler.error(CLASS_NAME + METHOD_NAME + "Exception during execution of sendPromptingCommand()" +exp.getMessage());
			throw new CoreRuntimeException(exp,CLASS_NAME + METHOD_NAME + "Exception during execution of sendPromptingCommand()" +exp.getMessage());
		
		}finally{
			closeSafe(ois);
			close();			
			tryLogOut();
		}
	}
	
	/**
	 * Tail log content.
	 *
	 * @param connectionName the connection name
	 * @param filePath the file path
	 */
	public void tailLogContent(final String connectionName, final String ...filePath) {
		LogHandler.info("Tailing the file sat: " + filePath[0]);
		executeAndReturnResponse("rm -rf " + filePath[0] + ".tmp.tailout");
		executeAndReturnResponse(
				"nohup  tail -0f " + filePath[0] + " > " + filePath[0] + ".tmp.tailout &");
	}

	/**
	 * Gets the tailed content.
	 *
	 * @param connectionName the connection name
	 * @param filePath the file path
	 * @return the tailed content
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public String getTailedContent(final String connectionName, final String ...filePath){
		LogHandler.info("Tailed content for the file: sat " + filePath[0]);
		executeAndReturnResponse("kill -9 `ps -eaf | grep \" tail -0f " + filePath[0] + "\" | awk '{print $2}' | tail -1`");		
		final CommandResponse response = executeAndReturnResponse("cat "+ filePath[0] + ".tmp.tailout");
		return response.getStdOut().toString();

	}

	
//	/**
//	 * Dump all the terminal output.
//	 *
//	 * @param connectionName the connection name
//	 * @return A string that would the content.
//	 */
//	public String getTerminalOutput() {
//		
//		final String METHOD_NAME = "getTerminalOutput(): ";	
//	
//		if(this.sshSession == null){
//			LogHandler.error("Session was already closed");
//			throw new CoreRuntimeException("Session was already closed");
//		}
//		String dump = "";
//		StringBuilder stringBuilder = new StringBuilder();
//		try {
//			byte[] buffer = new byte[30720];
//			InputStream stdout = this.sshSession.getStdout();
//			while (true) {
//				if (stdout.available() == 0) {
//					int conditions = this.sshSession.waitForCondition(
//							ChannelCondition.STDERR_DATA
//							| ChannelCondition.STDOUT_DATA
//							| ChannelCondition.EOF, 2000);
//					/* Wait no longer than 2 seconds (= 2000 milliseconds) */
//
//					if ((conditions & ChannelCondition.TIMEOUT) != 0) {
//						break;
//					}
//
//					if ((conditions & (ChannelCondition.STDOUT_DATA | ChannelCondition.STDERR_DATA)) == 0)
//						throw new IllegalStateException(
//								"Unexpected condition result (" + conditions
//								+ ")");
//				}
//
//				if (stdout.available() > 0) {
//					int len = stdout.read(buffer);
//
//					String output = "";
//					for (int i = 0; i < len; i++) {
//						output += ((char) buffer[i]);
//					}
//
//					output = getString(buffer, 0, len);
//
//					String[] lines = output.split("\n");
//					for (int i = 0; i < lines.length; i++) {
//						stringBuilder.append(lines[i]);
//					}
//				}
//
//			}
//			dump = stringBuilder.toString();
//
//		} catch (IOException exp) {
//			LogHandler.error(CLASS_NAME + METHOD_NAME + "Exception while trying to read terminal output" +exp.getMessage());
//			throw new CoreRuntimeException(exp,CLASS_NAME + METHOD_NAME + "Exception while trying to read terminal output" +exp.getMessage());
//		}
//		return dump;
//	}
	/**
	 * This will convert a String to UTF-8 format.
	 *
	 * @param data the data
	 * @param off the off
	 * @param len the len
	 * @return A string converted to UTF-8
	 */
	public String getString(byte[] data, int off, int len) {
		return new String(data, off, len, Charset.forName("UTF-8"));
	}	
	/**
	 * Gets the tail dump.
	 *
	 * @param filePath the file path
	 * @return the tail dump
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public String getTailDump(final String ...filePath)
			throws IOException {
		LogHandler.info("Tailed content for the file: sat" + filePath[0]);
		executeAndReturnResponse("kill -9 `ps -eaf | grep \" tail -0f " + filePath[0] + "\" | awk '{print $2}' | head -1`");
		final CommandResponse response = executeAndReturnResponse("cat "+ filePath[0] + ".tmp.tailout");
		return response.getStdOut().toString();

	}	
}
