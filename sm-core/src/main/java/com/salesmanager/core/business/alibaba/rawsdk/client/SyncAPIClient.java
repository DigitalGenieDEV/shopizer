/**
 * 
 */
package com.salesmanager.core.business.alibaba.rawsdk.client;


import com.salesmanager.core.business.alibaba.rawsdk.client.entity.AuthorizationToken;
import com.salesmanager.core.business.alibaba.rawsdk.client.entity.AuthorizationTokenStore;
import com.salesmanager.core.business.alibaba.rawsdk.client.entity.DefaultAuthorizationTokenStore;
import com.salesmanager.core.business.alibaba.rawsdk.client.exception.OceanException;
import com.salesmanager.core.business.alibaba.rawsdk.client.http.AbstractHttpClient;
import com.salesmanager.core.business.alibaba.rawsdk.client.http.HttpResponseBuilder;
import com.salesmanager.core.business.alibaba.rawsdk.client.http.InvokeContext;
import com.salesmanager.core.business.alibaba.rawsdk.client.http.platform.DefaultHttpResponseBuilder;
import com.salesmanager.core.business.alibaba.rawsdk.client.http.platform.HttpURLConnectionClient;
import com.salesmanager.core.business.alibaba.rawsdk.client.policy.ClientPolicy;
import com.salesmanager.core.business.alibaba.rawsdk.client.policy.RequestPolicy;
import com.salesmanager.core.business.alibaba.rawsdk.client.serialize.DeSerializerListener;
import com.salesmanager.core.business.alibaba.rawsdk.client.serialize.SerializerListener;
import com.salesmanager.core.business.alibaba.rawsdk.client.serialize.SerializerProvider;
import org.apache.commons.httpclient.HttpException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author hongbang.hb
 *
 */
public class SyncAPIClient {

	private AbstractHttpClient httpClient;
	private ClientPolicy clientPolicy;
	private AuthorizationTokenStore authorizationTokenStore;

	public SyncAPIClient(ClientPolicy clientPolicy, SerializerProvider serializerProvider) {
		this(clientPolicy, serializerProvider, new DefaultAuthorizationTokenStore());
	}

	public SyncAPIClient(ClientPolicy clientPolicy, SerializerProvider serializerProvider,
			AuthorizationTokenStore authorizationTokenStore) {
		super();
		this.clientPolicy = clientPolicy;
		this.authorizationTokenStore = authorizationTokenStore;
		HttpResponseBuilder httpResponseBuilder = new DefaultHttpResponseBuilder(clientPolicy, serializerProvider);
		httpClient = new HttpURLConnectionClient(serializerProvider, httpResponseBuilder);
	}

	public <T> T send(Request request, Class<T> resultType, RequestPolicy policy) throws OceanException {
		return null;
	}

	public <T> T send(Request request, Class<T> resultType, RequestPolicy policy,
					  Collection<SerializerListener> serializerListners, Collection<DeSerializerListener> deSerializerListners, InvokeContext context)
			throws OceanException {
		InvokeContext invokeContext = context == null? new InvokeContext() : context;
		invokeContext.setPolicy(policy);
		invokeContext.setRequest(request);
		invokeContext.setResultType(resultType);
		try {
			httpClient.request(invokeContext, clientPolicy, serializerListners, deSerializerListners);
			if (invokeContext.getResponse().getException() != null) {
				Throwable responseException = invokeContext.getResponse().getException();
				if (responseException instanceof OceanException) {
					 ((OceanException) responseException).setRequestId(invokeContext.getResponse().getRequestId());
					 ((OceanException) responseException).setTraceId(invokeContext.getResponse().getTraceId());
					throw (OceanException) responseException;
				} else {
					throw new OceanException("UNKNOWN_ERROR",responseException.getMessage(),responseException);
				}
			}
			return (T) invokeContext.getResponse().getResult();
		} catch (Exception e) {
			String errorMessage = e.getMessage();
			System.out.println("HTTP 请求发生错误：" + errorMessage);
			throw new OceanException("IOException",e.getMessage(),e);
		}
	}

	public void start() {
	}

	public void shutdown() {
	}

	public AuthorizationToken getToken(String code) throws OceanException {

		Request request = new Request("system.oauth2", "getToken");
		request.addAddtionalParams("code", code);
		request.addAddtionalParams("grant_type", "authorization_code");
		request.addAddtionalParams("need_refresh_token", true);
		request.addAddtionalParams("client_id", clientPolicy.getAppKey());
		request.addAddtionalParams("client_secret", clientPolicy.getSigningKey());
		request.addAddtionalParams("redirect_uri", "default");
		RequestPolicy oauthPolicy = RequestPolicy.getAuthPolicy();

		return this.send(request, AuthorizationToken.class, oauthPolicy, new ArrayList<SerializerListener>(),
				new ArrayList<DeSerializerListener>(),null);
	}

	public AuthorizationToken refreshToken(String refreshToken) throws OceanException {
		Request request = new Request("system.oauth2", "getToken");
		request.addAddtionalParams("refreshToken", refreshToken);
		request.addAddtionalParams("grant_type", "refresh_token");
		request.addAddtionalParams("client_id", clientPolicy.getAppKey());
		request.addAddtionalParams("client_secret", clientPolicy.getSigningKey());
		RequestPolicy oauthPolicy = RequestPolicy.getAuthPolicy();
		return this.send(request, AuthorizationToken.class, oauthPolicy, new ArrayList<SerializerListener>(),
				new ArrayList<DeSerializerListener>(),null);
	}

}
