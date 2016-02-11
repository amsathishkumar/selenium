/**
 * Copyright (c) $2015 by Cisco Systems, Inc.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Cisco Systems,  ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Cisco Systems.
 * 
 * NoOpTrustManager used when no trust store file is suppilied
 *
 * @Project: core-HTTP
 * @Author: kosk
 * @Version: 1.0.0.0
 * @Description:  Manager used when no trust store file is suppilied
 * @Date updated: 6/3/2015
 */

package com.cisco.vcse.automation.utils.datatype;

import java.net.Socket;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.X509ExtendedTrustManager;

public class NoOpTrustManager extends X509ExtendedTrustManager{

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		return  new X509Certificate[0];
	}

	@Override
	public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		return;
		
	}

	@Override
	public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		return;
		
	}

	@Override
	public void checkClientTrusted(X509Certificate[] arg0, String arg1, Socket arg2) throws CertificateException {
		return;
		
	}

	@Override
	public void checkClientTrusted(X509Certificate[] arg0, String arg1, SSLEngine arg2) throws CertificateException {
		return;
		
	}

	@Override
	public void checkServerTrusted(X509Certificate[] arg0, String arg1, Socket arg2) throws CertificateException {
		return;
		
	}

	@Override
	public void checkServerTrusted(X509Certificate[] arg0, String arg1, SSLEngine arg2) throws CertificateException {
		return;		
	}

}
