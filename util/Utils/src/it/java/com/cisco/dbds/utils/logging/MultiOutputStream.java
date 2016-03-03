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
package com.cisco.dbds.utils.logging;

import java.io.IOException;
import java.io.OutputStream;

// TODO: Auto-generated Javadoc
/**
 * The Class MultiOutputStream.
 */
public class MultiOutputStream extends OutputStream {

	/** The output streams. */
	OutputStream[] outputStreams;

	/**
	 * Instantiates a new multi output stream.
	 *
	 * @param outputStreams the output streams
	 */
	public MultiOutputStream(OutputStream... outputStreams) {
		this.outputStreams = outputStreams;
	}

	/* (non-Javadoc)
	 * @see java.io.OutputStream#write(int)
	 */
	@Override
	public void write(int b) throws IOException {
		for (OutputStream out : outputStreams)
			out.write(b);
	}

	/* (non-Javadoc)
	 * @see java.io.OutputStream#write(byte[])
	 */
	@Override
	public void write(byte[] b) throws IOException {
		for (OutputStream out : outputStreams)
			out.write(b);
	}

	/* (non-Javadoc)
	 * @see java.io.OutputStream#write(byte[], int, int)
	 */
	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		for (OutputStream out : outputStreams)
			out.write(b, off, len);
	}

	/* (non-Javadoc)
	 * @see java.io.OutputStream#flush()
	 */
	@Override
	public void flush() throws IOException {
		for (OutputStream out : outputStreams)
			out.flush();
	}

	/* (non-Javadoc)
	 * @see java.io.OutputStream#close()
	 */
	@Override
	public void close() throws IOException {
		for (OutputStream out : outputStreams)
			out.close();
	}
}
