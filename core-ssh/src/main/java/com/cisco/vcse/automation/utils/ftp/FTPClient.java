package com.cisco.vcse.automation.utils.ftp;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPReply;

import com.cisco.vcse.automation.utils.datatype.CoreRuntimeException;
import com.cisco.vcse.automation.utils.datatype.Server;

public class FTPClient extends Server {


	final String CLASS_NAME ="FTPClient: ";
	private org.apache.commons.net.ftp.FTPClient apacheFTPClient = new org.apache.commons.net.ftp.FTPClient();
	private final String FORWARD_SLASH="/";

	
	public FTPClient(final String hostOrIPAdress,final int portNumber, final String username, final String password){
		setHostName(hostOrIPAdress);
		setUsername(username);;
		setPassword(password);	
		if(portNumber>0){
			setPortNumber(portNumber);
		}		
		
	}
	
	protected void connect(){
		final String METHOD_NAME ="connect(): ";
		try {
			this.apacheFTPClient.connect(getHostName(), getPortNumber());
			int replyCode = this.apacheFTPClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(replyCode)) {
				System.err.println(CLASS_NAME + METHOD_NAME + "Unable to connect to " + getHostName() +":"+getPortNumber() +", reply code from server="+replyCode);
				throw new CoreRuntimeException(CLASS_NAME + METHOD_NAME + "Unable to connect to " + getHostName() +":"+getPortNumber()+", reply code from server="+replyCode);
			}
			
			boolean logInStatus = this.apacheFTPClient.login(getUsername(), getPassword());
			if(!logInStatus){
				System.err.println(CLASS_NAME + METHOD_NAME + "Unable to login " + getHostName() +":"+getPortNumber() +" with given credentials");
				throw new CoreRuntimeException(CLASS_NAME + METHOD_NAME + "Unable to connect to "+ getHostName() +":"+getPortNumber() +" with given credentials");
			}
			
		} catch (IOException exp) {
			System.err.println(CLASS_NAME + METHOD_NAME + "Unable to connect to " + getHostName() +":"+getPortNumber());
			throw new CoreRuntimeException(exp,CLASS_NAME + METHOD_NAME + "Unable to connect to " + getHostName() +":"+getPortNumber());
		}

	}
	
	/**
	 * uploads the local file into the home directory of the logged in user
	 * The remote file name is same as local file name
	 * @param localFilePath : local file which has to be uploaded.
	 * @return true if file upload was successful, false otherwise
	 */
	public boolean uploadFile(final String localFilePath){
		return uploadFile(new File(localFilePath),null,new File(localFilePath).getName(),  null, null,null);		
		
	}
	
	/**
	 * uploads the local file into the home directory of the logged in user
	 * @param localFilePath : local file which has to be uploaded.
	 * @param remoteFileName : name of the remote file to which the local file content has to be stored
	 * @return boolean : true or false based on upload success or failure
	 */
	public boolean uploadFile(final String localFilePath, final String remoteFileName){
		return uploadFile(new File(localFilePath),null,remoteFileName,  null, null,null);				
		
	}

	/**
	 * uploads the local file into the the specific remote folder with specific name
	 * @param localFilePath : local file which has to be uploaded.
	 * @param remoteDirectory : remote directory where the file has to be uploaded
	 * @param remoteFileName : name of the remote file to which the local file content has to be stored
	 * @return boolean : true or false based on upload success or failure
	 */
	public boolean uploadFile(final String localFilePath, final String remoteDirectory, final String remoteFileName ){
		return uploadFile(new File(localFilePath),remoteDirectory,remoteFileName,  null, null,null);			
	}
	
	/**
	 * 
	 * uploads the local file into the the specific remote folder with specific name and then sets the permission and owner if specified as non null
	 * @param localFilePath : local file which has to be uploaded.
	 * @param remoteDirectory : remote directory where the file has to be uploaded
	 * @param remoteFileName : name of the remote file to which the local file content has to be stored
	 * @param permission : permission like "777" etc. Make sure the current user has permission to change the permission !
	 * @param owner : new owner of the file, make sure the current user has permission to change the owner
	 * @return true if all operation is successful, false otherwise
	 */
	public boolean uploadFile(final String localFilePath, final String remoteDirectory, final String remoteFileName ,final String permission, final String owner){
		return uploadFile(new File(localFilePath),remoteDirectory,remoteFileName,  permission, owner,null);			
	}
	
	
	/**
	 * 
	 * uploads the local file into the the specific remote folder with specific name and then sets the permission and owner if specified as non null
	 * @param fileBytes : file bytes to be uploaded in FTP server
	 * @param remoteDirectory : remote directory where the file has to be uploaded
	 * @param remoteFileName : name of the remote file to which the local file content has to be stored
	 * @param permission : permission like "777" etc. Make sure the current user has permission to change the permission !
	 * @param owner : new owner of the file, make sure the current user has permission to change the owner
	 * @return true if all operation is successful, false otherwise
	 */
	public boolean uploadFile(final byte[] fileBytes, final String remoteDirectory, final String remoteFileName ,final String permission, final String owner){
		final String METHOD_NAME ="uploadFile(byte[],String,String,String,String): ";
		if(remoteFileName==null || remoteFileName.trim().isEmpty()){
			System.err.println(CLASS_NAME + METHOD_NAME + "To upload byte[], remote file name is required");
			throw new CoreRuntimeException(CLASS_NAME + METHOD_NAME + "To upload byte[], remote file name is required");
		
		}
		return uploadFile(null,remoteDirectory,remoteFileName,  permission, owner,fileBytes);			
	}
		
	
	/**
	 * 
	 * @param newOwnerName
	 * @param remotefilePath
	 * @return boolean : true or false based on success or failure
	 */
	public boolean changeOwner(final String newOwnerName, final String remotefilePath){
		return sendSiteCommand("chown "+newOwnerName+ " "+remotefilePath,false, true);			
	}
	
	/**
	 * Changes permission to file, equivalent to chmod command
	 * @param newMode
	 * @param remotefilePath
	 * @return boolean : true or false based on success or failure
	 */
	public boolean changePermission(final String newMode, final String remotefilePath){
		return sendSiteCommand("chmod "+newMode + " "+remotefilePath,false, true);	
	}
	
	/**
	 * Runs given command and returns true/false depending on outcome of the command
	 * @param command
	 * @param commandParameter
	 * @return boolean : true or false based on upload success or failure
	 */
	public boolean executeCommand(final String command, final String commandParameter){
		return executeCommand(command, commandParameter, false, true);
			
	}

	/**
	 * 
	 * @param remotefilePath : remote file to be downloaded
	 * @param localFilePath : Local file to which it has to be saved
	 * @return boolean : true or false based on down success or failure
	 */
	public boolean downloadFileToLocal(final String remotefilePath, final String localFilePath){
		final String METHOD_NAME ="downloadFileToLocal(String,String): ";
		connect();
		try(OutputStream fos = new FileOutputStream(new File(localFilePath));) {			
			return this.apacheFTPClient.retrieveFile(remotefilePath, fos);
		} catch (FileNotFoundException exp) {
			System.err.println(CLASS_NAME + METHOD_NAME + "Unable to create local file. "+exp.getMessage());
			throw new CoreRuntimeException(exp,CLASS_NAME + METHOD_NAME + "Unable to create local file. "+exp.getMessage());
		} catch (IOException exp) {
			System.err.println(CLASS_NAME + METHOD_NAME + "Unable to download remote file "+remotefilePath);
			throw new CoreRuntimeException(exp,CLASS_NAME + METHOD_NAME + "Unable to download remote file "+remotefilePath);
		}finally{
			disconnect(true);
		}
		
	}
	

	/**
	 * 
	 * @param command : FTP command to run
	 * @param commandParameter : parameters for the command
	 * @param reuseConnection : if this is true then it will reuse the existing connection instead of opening a new one
	 * @param closeFTPConn : If true then it will close this connection, otherwise it will not be closed as the connection is re-used later on during the process which will close it.
	 * @return boolean : true or false based on success or failure
	 */
	private boolean executeCommand(final String command, final String commandParameter, boolean reuseConnection,final boolean closeFTPConn){
		final String METHOD_NAME ="executeCommand(String,String,boolean,boolean): ";
		if(!reuseConnection){
			connect();
		}
		try {			
			return this.apacheFTPClient.doCommand(command,commandParameter); 			
		} catch (IOException exp) {
			System.err.println(CLASS_NAME + METHOD_NAME + "Unable to run command "+command +" with parameter "+commandParameter+exp.getMessage());
			throw new CoreRuntimeException(CLASS_NAME + METHOD_NAME + "Unable to run command "+command +" with parameter "+commandParameter+exp.getMessage());
		}finally{
			disconnect(closeFTPConn);
		}
			
	}
	
	/**
	 * 
	/**
	 * 
	 * @param localFile : local file which has to be uploaded.
	 * @param remoteFileName : name of the remote file to which the local file content has to be stored
	 * @param reuseConnection  if this is true then it will reuse the existing connection instead of opening a new one
	 * @param closeFTPConn If true then it will close this connection, otherwise it will not be closed as the connection is re-used later on during the process which will close it.
	 * @param localFileBytes : local file bytes if "localFile" is not mentioned
	 * @return boolean : true or false based on upload success or failure
	 */
	private boolean uploadFile( final File localFile,final String remoteFileName, boolean reuseConnection,final boolean closeFTPConn,final byte[] localFileBytes){
		final String METHOD_NAME ="uploadFile(File, String,boolean,boolean, byte[]): ";
		if(!reuseConnection){
			connect();		
		}
		try(InputStream fis = (null==localFileBytes)?
				new FileInputStream(localFile):
					new ByteArrayInputStream(localFileBytes);) {
			return apacheFTPClient.storeFile(remoteFileName, fis);
		} catch (IOException exp) {
			System.err.println(CLASS_NAME + METHOD_NAME + "Unable to upload file "+exp.getMessage());
			throw new CoreRuntimeException(CLASS_NAME + METHOD_NAME + "Unable to upload file "+exp.getMessage());
		} finally{
			disconnect(closeFTPConn);
		}
		
	}
	
	/**
	 * 
	 * @param localFile : local file which has to be uploaded.
	 * @param remoteFileName : name of the remote file to which the local file content has to be stored
	 * @param remoteFileName:  Name of the remote file to be be stored
	 * @param newPermision : permission like 777, 444 etc
	 * @param newOwner : ownership of file
	 * @param localFileBytes : local file bytes if "localfile" is not mentioned
	 * @return	: true or false based on upload success or failure
	*/
	private boolean uploadFile(final File localfile, final String remoteDirectory, final String remoteFileName,
			final String newPermision, final String newOwner, final byte[] localFileBytes){
		final String METHOD_NAME ="uploadFile(File, String,String,String,String,byte[]): ";
		connect();
		String remoteFileFinalName = remoteFileName;
		try {
			if(remoteFileName==null){ //No specific remote file name was mentioned, hence keeping the same remote file name as of local file name
				remoteFileFinalName = localfile.getName();
			}
			if(remoteDirectory!=null){
				boolean isDirChanged = this.apacheFTPClient.changeWorkingDirectory(remoteDirectory);
				if(!isDirChanged){
					System.err.println(CLASS_NAME + METHOD_NAME + "Failed to change remote directory. Please make sure the directory exists with proper permission");
					throw new CoreRuntimeException(CLASS_NAME + METHOD_NAME + "Failed to change remote directory. Please make sure the directory exists with proper permission");
				}
			}
			//Reuse existing connection, and dont close the connection, hence true,false is passed
			boolean isFileUploadComplete = uploadFile(localfile,remoteFileName, true, false,localFileBytes);
			
			if(!isFileUploadComplete){
				final String workingDir = this.apacheFTPClient.printWorkingDirectory();
				System.err.println(CLASS_NAME + METHOD_NAME + "Unable to upload to "+workingDir + FORWARD_SLASH + remoteFileFinalName);
				throw new CoreRuntimeException(CLASS_NAME + METHOD_NAME + "Unable to upload to "+workingDir +FORWARD_SLASH + remoteFileFinalName);
			}
			final String workingDir = this.apacheFTPClient.printWorkingDirectory();
			if(newPermision != null){
				//Call to change permission, reuse session
				boolean isModeChanged = sendSiteCommand("chmod "+newPermision+ " "+workingDir+ FORWARD_SLASH+remoteFileFinalName,true, false);	
				if(!isModeChanged){
					System.err.println(CLASS_NAME + METHOD_NAME + "Failed to change permision of the remote file");
					throw new CoreRuntimeException(CLASS_NAME + METHOD_NAME + "Failed to change permision of the remote file");
				}
			}
			if(newOwner != null){
				////Call to change owner, reuse session
				boolean isOwnerChanged = sendSiteCommand("chown "+newOwner+ " "+workingDir + FORWARD_SLASH+remoteFileFinalName,true, false);				
				if(!isOwnerChanged){
					System.err.println(CLASS_NAME + METHOD_NAME + "Failed to change owner of the remote file to "+newOwner);
					throw new CoreRuntimeException(CLASS_NAME + METHOD_NAME + "Failed to change owner of the remote file to "+newOwner);
				}
			}	
			
		} catch (IOException exp) {
			System.err.println(CLASS_NAME + METHOD_NAME + "Unable to upload file "+exp.getMessage());
			throw new CoreRuntimeException(CLASS_NAME + METHOD_NAME + "Unable to upload file "+exp.getMessage());
		}finally{
			disconnect(true);
		}
		
		return true;
			
	}
	/**
	 * executes siteCommand example "chmod 777 fileName"
	 * @param fullCommandWithArgIfAny
	 * @return : true or false based on upload success or failure
	 */
	private boolean sendSiteCommand(final String fullCommandWithArgIfAny,  boolean reuseConnection,final boolean closeFTPConn){
		final String METHOD_NAME ="sendSiteCommand(String,boolean,boolean): ";
		if(!reuseConnection){
			connect();
		}
		try {
			return this.apacheFTPClient.sendSiteCommand(fullCommandWithArgIfAny);
		}  catch (IOException exp) {
			System.err.println(CLASS_NAME + METHOD_NAME + "Exception during execution of sendSiteCommand() "+exp.getMessage());
			throw new CoreRuntimeException(CLASS_NAME + METHOD_NAME + "Exception during execution of sendSiteCommand() "+exp.getMessage());
		}finally{
			disconnect(closeFTPConn);
		}		
	}
	
	private void disconnect(final boolean closeConn){
		if(closeConn){
			try {
				this.apacheFTPClient.logout();
				this.apacheFTPClient.disconnect();
			} catch (IOException e) {
			}
		}
	}
}
