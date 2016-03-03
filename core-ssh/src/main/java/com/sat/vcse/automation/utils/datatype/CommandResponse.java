/**
 * Copyright (c) $2015 by sat, Inc.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of sat,  ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with sat.
 * 
 * This class wraps shall response
 *
 *
 * @Project: acutomationCore-ShellClient
 * @Author: amsathishkumar
 * @Version: 1.0.0.0
 * @Description:  This class wraps shell response, contains exit code for command run and standrd and error console outs
 * @Date updated: 6/10/2015
 */

package com.sat.vcse.automation.utils.datatype;

public class CommandResponse {

	// Standard shell sysouts
	private String stdOut;
	// Standard shell errors
	private String stdErr;
	
	// command run status code
	private int exitStatus;
		
	public String getStdOut() {
		return stdOut;
	}


	public void setStdOut(String stdOut) {
		this.stdOut = stdOut;
	}


	public String getStdErr() {
		return stdErr;
	}


	public void setStdErr(String stdErr) {
		this.stdErr = stdErr;
	}


	public int getExitStatus() {
		return exitStatus;
	}


	public void setExitStatus(Integer exitStatus) {
		if(exitStatus !=null){
			this.exitStatus = exitStatus;
		}
	}

	public String toString(){
		StringBuilder responseString= new StringBuilder("Exit code --> ");
		responseString.append(this.exitStatus);
		if(null != this.stdOut && this.stdOut.length()>0){
			responseString.append("\nstdOut -> ").append(this.stdOut);
		}
		if(null != this.stdErr && this.stdErr.length()>0){
			responseString.append("\nstdErr -> ").append(this.stdErr);
		}
		
		return responseString.toString();
	}

}
