/**
 * Project: ocean.client.java.basic
 *
 * File Created at 2011-10-18
 * $Id: ClientPolicy.java 410405 2015-05-07 13:01:22Z hongbang.hb $
 *
 * Copyright 2008 Alibaba.com Croporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Alibaba Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Alibaba.com.
 */
package com.salesmanager.core.business.alibaba.rawsdk.client.policy;

import java.net.Proxy;

/**
 */
public class ClientPolicy implements Cloneable {

	private static Integer DEFAULT_HTTP_PORT = 80;
	private static Integer DEFAULT_HTTPS_PORT = 443;

	private static final ClientPolicy CBU_POLICY = new ClientPolicy("gw.open.1688.com");

	private String serverHost;
	private int httpPort = DEFAULT_HTTP_PORT;
	private int httpsPort = DEFAULT_HTTPS_PORT;
	private String appKey;
	private String signingKey;
	private int defaultTimeout = 5000;
	private String defaultContentCharset = "UTF-8";
	private boolean defaultUseHttps = false;
	private String agent = "Ocean-SDK-Client";

	private Proxy proxy;

	/**
	 *
	 * @return
	 */
	public static ClientPolicy getDefaultChinaAlibabaPolicy() {
		return CBU_POLICY.clone();
	}

	/**
	 */
	public ClientPolicy clone() {
		ClientPolicy newObj = newPolicy();
		newObj.httpPort = httpPort;
		newObj.httpsPort = httpsPort;
		newObj.defaultTimeout = defaultTimeout;
		newObj.defaultContentCharset = defaultContentCharset;
		newObj.defaultUseHttps = defaultUseHttps;

		newObj.appKey = appKey;
		newObj.signingKey = signingKey;
		newObj.agent = agent;
		return newObj;
	}

	protected ClientPolicy newPolicy() {
		return new ClientPolicy(serverHost);
	}

	/**
	 *
	 * @param serverHost
	 */
	public ClientPolicy(String serverHost) {
		if (serverHost == null || serverHost.length() < 1) {
			throw new IllegalArgumentException("serverHost can not be empty");
		}
		this.serverHost = serverHost;
	}

	/**
	 *
	 * @return
	 */
	public int getHttpPort() {
		return httpPort;
	}

	/**
	 *
	 * @return
	 */
	public int getHttpsPort() {
		return httpsPort;
	}

	/**
	 *
	 * @return
	 */
	public String getServerHost() {
		return serverHost;
	}

	/**
	 *
	 * @param httpPort
	 *            端口
	 * @return a reference to this object
	 */
	public ClientPolicy setHttpPort(Integer httpPort) {
		this.httpPort = httpPort;
		return this;
	}

	/**
	 *
	 * @param httpsPort
	 *            端口
	 * @return a reference to this object
	 */
	public ClientPolicy setHttpsPort(Integer httpsPort) {
		this.httpsPort = httpsPort;
		return this;
	}

	public String getAppKey() {
		return appKey;

	}

	/**
	 *
	 * @param appKey
	 * @return a reference to this object
	 */
	public ClientPolicy setAppKey(String appKey) {
		this.appKey = appKey;
		return this;
	}

	public String getSigningKey() {
		return signingKey;
	}

	/**
	 *
	 * @param signingKey
	 * @return a reference to this object
	 */
	public ClientPolicy setSigningKey(String signingKey) {
		this.signingKey = signingKey;
		return this;
	}

	public int getDefaultTimeout() {
		return defaultTimeout;
	}

	public ClientPolicy setDefaultTimeout(int defaultTimeout) {
		this.defaultTimeout = defaultTimeout;
		return this;
	}

	public String getDefaultContentCharset() {
		return defaultContentCharset;
	}

	public ClientPolicy setDefaultContentCharset(String defaultContentCharset) {
		this.defaultContentCharset = defaultContentCharset;
		return this;
	}

	public boolean isDefaultUseHttps() {
		return defaultUseHttps;
	}

	public ClientPolicy setDefaultUseHttps(boolean defaultUseHttps) {
		this.defaultUseHttps = defaultUseHttps;
		return this;
	}

	public String getAgent() {
		return agent;
	}


	public Proxy getProxy() {
		return proxy;
	}

	public ClientPolicy setProxy(Proxy proxy) {
		this.proxy = proxy;
		return this;
	}
}
