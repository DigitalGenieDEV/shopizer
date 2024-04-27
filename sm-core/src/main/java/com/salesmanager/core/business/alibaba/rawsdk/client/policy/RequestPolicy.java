/**
 * Project: ocean.client.java.basic
 *
 * File Created at 2011-10-26
 * $Id: RequestPolicy.java 410052 2015-05-06 08:18:05Z hongbang.hb $
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


import com.salesmanager.core.business.alibaba.rawsdk.client.ErrorHandler;
import com.salesmanager.core.business.alibaba.rawsdk.util.DateUtil;

/**
 *
 * @author jade
 * @author xiaoning.qxn
 */
public class RequestPolicy implements Cloneable {

	private boolean requestSendTimestamp = false;
	private boolean useHttps = false;
	private Protocol requestProtocol = Protocol.param2;
	private Protocol responseProtocol = Protocol.json2;
	private boolean responseCompress = true;
	private int requestCompressThreshold = -1; // 默认不开启
	//请求头header里的content-encoding,    是否压缩，填gzip，deflate，或不填（不填表示不压缩，该字段只在mapi里生效
	private String requestContentEncoding;

	private int timeout = 5000; // 5秒
	private HttpMethodPolicy httpMethod = HttpMethodPolicy.POST;
	private String queryStringCharset = "GB18030"; // Request URL query string
	// encoder
	private String contentCharset = "UTF-8"; // Request body encoder
	private boolean useSignture = true;
	private boolean needAuthorization = false;
	private boolean accessPrivateApi = false;
	private int defaultApiVersion = 1;
	private ErrorHandler errorHandler;
	private String dateFormat = DateUtil.SIMPLE_DATE_FORMAT_STR;

	public static RequestPolicy authPolicy = null;

	/**
	 *
	 * @return
	 */
	public static RequestPolicy getAuthPolicy() {
		if (authPolicy == null) {
			authPolicy = new RequestPolicy().setHttpMethod(HttpMethodPolicy.POST).setRequestProtocol(Protocol.param2).setUseHttps(true)
					.setNeedAuthorization(false);
		}
		return authPolicy;
	}

	public RequestPolicy clone() {
		RequestPolicy newObj = newPolicy();
		newObj.requestSendTimestamp = requestSendTimestamp;
		newObj.useHttps = useHttps;
		newObj.requestProtocol = requestProtocol;
		newObj.responseProtocol = responseProtocol;
		newObj.responseCompress = responseCompress;
		newObj.requestContentEncoding = requestContentEncoding;
		newObj.requestCompressThreshold = requestCompressThreshold;
		newObj.timeout = timeout;
		newObj.httpMethod = httpMethod;
		newObj.queryStringCharset = queryStringCharset;
		newObj.contentCharset = contentCharset;
		newObj.useSignture = useSignture;
		newObj.accessPrivateApi = accessPrivateApi;
		newObj.defaultApiVersion = defaultApiVersion;
		return newObj;
	}

	protected RequestPolicy newPolicy() {
		return new RequestPolicy();
	}

	/**
	 *
	 * @param requestSendTimestamp
	 * @return a reference to this object
	 */
	public RequestPolicy setRequestSendTimestamp(boolean requestSendTimestamp) {
		this.requestSendTimestamp = requestSendTimestamp;
		return this;
	}

	public boolean isRequestSendTimestamp() {
		return requestSendTimestamp;
	}

	public boolean isUseHttps() {
		return useHttps;
	}

	/**
	 *
	 * @param useHttps
	 * @return a reference to this object
	 */
	public RequestPolicy setUseHttps(boolean useHttps) {
		this.useHttps = useHttps;
		return this;
	}

	/**
	 *
	 * @param protocol
	 * @return a reference to this object
	 */
	public RequestPolicy setRequestProtocol(Protocol protocol) {
		if (protocol == null) {
			throw new IllegalArgumentException("protocol can not be null");
		}
		this.requestProtocol = protocol;
		return this;
	}

	public Protocol getRequestProtocol() {
		return requestProtocol;
	}

	public boolean isResponseCompress() {
		return responseCompress;
	}

	/**
	 *
	 * @param responseCompress
	 * @return a reference to this object
	 */
	private RequestPolicy setResponseCompress(boolean responseCompress) {
		this.responseCompress = responseCompress;
		return this;
	}

	public int getRequestCompressThreshold() {
		return requestCompressThreshold;
	}

	/**
	 * @param requestCompressThreshold
	 * @return
	 */
	public RequestPolicy setRequestCompressThreshold(int requestCompressThreshold) {
		this.requestCompressThreshold = requestCompressThreshold;
		return this;
	}

	public String getRequestContentEncoding() {
		return requestContentEncoding;
	}

	public void setRequestContentEncoding(String requestContentEncoding) {
		this.requestContentEncoding = requestContentEncoding;
	}

	public int getTimeout() {
		return timeout;
	}

	/**
	 *
	 * @param timeout
	 * @return a reference to this object
	 */
	public RequestPolicy setTimeout(int timeout) {
		this.timeout = timeout;
		return this;
	}

	/**
	 *
	 * @param httpMethod
	 * @return a reference to this object
	 */
	public RequestPolicy setHttpMethod(HttpMethodPolicy httpMethod) {
		this.httpMethod = httpMethod;
		return this;
	}

	public HttpMethodPolicy getHttpMethod() {
		return httpMethod;
	}

	public String getQueryStringCharset() {
		return queryStringCharset;
	}

	/**
	 *
	 * @param queryStringCharset
	 * @return a reference to this object
	 */
	public RequestPolicy setQueryStringCharset(String queryStringCharset) {
		this.queryStringCharset = queryStringCharset;
		return this;
	}

	public String getContentCharset() {
		return contentCharset;
	}

	/**
	 *
	 * @param contentCharset
	 * @return a reference to this object
	 */
	public RequestPolicy setContentCharset(String contentCharset) {
		this.contentCharset = contentCharset;
		return this;
	}

	/**
	 *
	 * @param responseProtocol
	 * @return a reference to this object
	 */
	public RequestPolicy setResponseProtocol(Protocol responseProtocol) {
		if (requestProtocol == null) {
			throw new IllegalArgumentException("response protocol can not be null");
		}
		this.responseProtocol = responseProtocol;
		return this;
	}

	public Protocol getResponseProtocol() {
		return responseProtocol;
	}

	/**
	 *
	 * @author jade
	 */
	public static enum HttpMethodPolicy {
		/**
		 */
		POST,
		/**
		 */
		GET;
	}

	public ErrorHandler getErrorHandler() {
		return errorHandler;
	}

	/**
	 *
	 * @param errorHandler
	 * @return a reference to this object
	 */
	public RequestPolicy setErrorHandler(ErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
		return this;
	}

	public boolean isUseSignture() {
		return useSignture;
	}

	/**
	 *
	 * @param useSignture
	 * @return a reference to this object
	 */
	public RequestPolicy setUseSignture(boolean useSignture) {
		this.useSignture = useSignture;
		return this;
	}

	public boolean isNeedAuthorization() {
		return needAuthorization;
	}

	/**
	 *
	 * @param needAuthorization
	 * @return a reference to this object
	 */
	public RequestPolicy setNeedAuthorization(boolean needAuthorization) {
		this.needAuthorization = needAuthorization;
		return this;
	}

	public boolean isAccessPrivateApi() {
		return accessPrivateApi;
	}

	/**
	 *
	 * @param accessPrivateApi
	 */
	public RequestPolicy setAccessPrivateApi(boolean accessPrivateApi) {
		this.accessPrivateApi = accessPrivateApi;
		return this;
	}

	public int getDefaultApiVersion() {
		return defaultApiVersion;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public RequestPolicy setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
		return this;
	}

}
