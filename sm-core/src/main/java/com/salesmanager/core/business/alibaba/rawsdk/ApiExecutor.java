package com.salesmanager.core.business.alibaba.rawsdk;

import com.salesmanager.core.business.alibaba.rawsdk.client.*;
import com.salesmanager.core.business.alibaba.rawsdk.client.entity.AuthorizationToken;
import com.salesmanager.core.business.alibaba.rawsdk.client.entity.AuthorizationTokenStore;
import com.salesmanager.core.business.alibaba.rawsdk.client.entity.DefaultAuthorizationTokenStore;
import com.salesmanager.core.business.alibaba.rawsdk.client.entity.DefaultAuthorizationTokenStore;
import com.salesmanager.core.business.alibaba.rawsdk.client.exception.OceanException;
import com.salesmanager.core.business.alibaba.rawsdk.client.http.InvokeContext;
import com.salesmanager.core.business.alibaba.rawsdk.client.http.mapi.MapiResult;
import com.salesmanager.core.business.alibaba.rawsdk.client.http.mapi.SyncMapiClient;
import com.salesmanager.core.business.alibaba.rawsdk.client.policy.ClientPolicy;
import com.salesmanager.core.business.alibaba.rawsdk.client.policy.RequestPolicy;
import com.salesmanager.core.business.alibaba.rawsdk.client.serialize.DeSerializerListener;
import com.salesmanager.core.business.alibaba.rawsdk.client.serialize.SerializerListener;
import com.salesmanager.core.business.alibaba.rawsdk.common.AbstractAPIRequest;
import com.salesmanager.core.business.alibaba.rawsdk.common.SDKResult;

import java.net.Proxy;
import java.util.LinkedHashMap;
import java.util.Map;
/**
 * The API facade.
 */
public final class ApiExecutor implements SDKListener {

	private String serverHost = "gw.open.1688.com";
	private int httpPort = 80;
	private int httpsPort = 443;
	private String appKey;
	private String secKey;

	private Proxy proxy;

	private AuthorizationTokenStore authorizationTokenStore;

	private Map<Class<? extends SerializerListener>, SerializerListener> serializerListeners = new LinkedHashMap<Class<? extends SerializerListener>, SerializerListener>();
	private Map<Class<? extends DeSerializerListener>, DeSerializerListener> deSerializerListeners = new LinkedHashMap<Class<? extends DeSerializerListener>, DeSerializerListener>();
    private volatile SyncAPIClient syncAPIClient;
	private volatile SyncMapiClient syncMAPIClient;

    public ApiExecutor(String appKey, String secKey) {
		super();
		this.appKey = appKey;
		this.secKey = secKey;
	}

	public ApiExecutor(String appKey, String secKey, Proxy proxy) {
		super();
		this.appKey = appKey;
		this.secKey = secKey;
		this.proxy = proxy;
	}

	public ApiExecutor(String serverHost, int httpPort, int httpsPort, String appKey, String secKey) {
		super();
		this.serverHost = serverHost;
		this.httpPort = httpPort;
		this.httpsPort = httpsPort;
		this.appKey = appKey;
		this.secKey = secKey;
	}

	public ApiExecutor(String serverHost, int httpPort, int httpsPort, String appKey, String secKey, Proxy proxy) {
		super();
		this.serverHost = serverHost;
		this.httpPort = httpPort;
		this.httpsPort = httpsPort;
		this.appKey = appKey;
		this.secKey = secKey;
		this.proxy = proxy;
	}

	public void register(SerializerListener serializerListener) {
		serializerListeners.put(serializerListener.getClass(), serializerListener);
	}

	public void register(DeSerializerListener deSerializerListener) {
		deSerializerListeners.put(deSerializerListener.getClass(), deSerializerListener);
	}

	private SyncAPIClient getAPIClient() {
		ClientPolicy clientPolicy = new ClientPolicy(serverHost);
		clientPolicy.setHttpPort(httpPort);
		clientPolicy.setHttpsPort(httpsPort);
		if (appKey != null) {
			clientPolicy.setAppKey(appKey);
		}
		if (secKey != null) {
			clientPolicy.setSigningKey(secKey);
		}
		if (proxy != null) {
			clientPolicy.setProxy(proxy);
		}
		if (authorizationTokenStore == null) {
			authorizationTokenStore = new DefaultAuthorizationTokenStore();
		}

		if (syncAPIClient == null) {
		    synchronized(this) {
		        if (syncAPIClient == null) {
                    syncAPIClient = new AlibabaClientFactory().createAPIClient(clientPolicy, authorizationTokenStore);
		        }
            }
        }

		return syncAPIClient;
	}

	private SyncMapiClient getMAPIClient() {
		ClientPolicy clientPolicy = new ClientPolicy(serverHost);
		clientPolicy.setHttpPort(httpPort);
		clientPolicy.setHttpsPort(httpsPort);
		if (appKey != null) {
			clientPolicy.setAppKey(appKey);
		}
		if (secKey != null) {
			clientPolicy.setSigningKey(secKey);
		}
		if (authorizationTokenStore == null) {
			authorizationTokenStore = new DefaultAuthorizationTokenStore();
		}

		if (syncMAPIClient == null) {
			synchronized(this) {
				if (syncMAPIClient == null) {
					syncMAPIClient = new AlibabaClientFactory().createMAPIClient(clientPolicy, authorizationTokenStore);
				}
			}
		}

		return syncMAPIClient;
	}

	/**
	 * 
	 * 
	 * @param code
	 * 
	 * @return
	 */
	public final AuthorizationToken getToken(String code) {
		try {
			return getAPIClient().getToken(code);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * refresh the access token with refreshToken
	 * 
	 * @param refreshToken
	 * 
	 * @return access token object.
	 */
	public final AuthorizationToken refreshToken(String refreshToken) {
		try {
			return getAPIClient().refreshToken(refreshToken);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * @param apiRequest
	 * @return
	 */
	public final <TResponse> SDKResult<TResponse> execute(AbstractAPIRequest<TResponse> apiRequest) {
		RequestPolicy reqPolicy = apiRequest.getOceanRequestPolicy();
		InvokeContext context=new InvokeContext();
		try {
			APIId apiId = apiRequest.getOceanApiId();
			Request req = new Request(apiId.getNamespace(), apiId.getName(), apiId.getVersion());
			req.setTraceId(apiRequest.getTraceId());
			req.setRpcId(apiRequest.getRpcId());
			req.setRequestEntity(apiRequest);
			TResponse ret = getAPIClient().send(req, apiRequest.getResponseClass(), reqPolicy,
					serializerListeners.values(), deSerializerListeners.values(),context);
			return buildSdkResult( ret,context,null,null,null);
		} catch (OceanException e) {
			if(e.getErrorCode()==null){
				return buildSdkResult(null,context,"UNKNOWN_ERROR",e.getErrorMessage(),e);
			}
			return buildSdkResult(null,context,e.getErrorCode(), e.getErrorMessage(),e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * @param apiRequest
	 * @return
	 */
	public final <TResponse> SDKResult<TResponse> execute(AbstractAPIRequest<TResponse> apiRequest, String accessToken) {
		RequestPolicy reqPolicy = apiRequest.getOceanRequestPolicy();
		InvokeContext context=new InvokeContext();
		try {
			APIId apiId = apiRequest.getOceanApiId();
			Request req = new Request(apiId.getNamespace(), apiId.getName(), apiId.getVersion());
			req.setRequestEntity(apiRequest);
			req.setAccessToken(accessToken);
			req.setTraceId(apiRequest.getTraceId());
			req.setRpcId(apiRequest.getRpcId());
			TResponse ret = getAPIClient().send(req, apiRequest.getResponseClass(), reqPolicy,
					serializerListeners.values(), deSerializerListeners.values(),context);
			return buildSdkResult( ret,context,null,null,null);
		} catch (OceanException e) {
			if(e.getErrorCode()==null){
				return buildSdkResult(null,context,"UNKNOWN_ERROR",e.getErrorMessage(),e);
			}
			return buildSdkResult(null,context,e.getErrorCode(), e.getErrorMessage(),e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private <TResponse> SDKResult<TResponse> buildSdkResult(TResponse ret,InvokeContext context,String errorCode,String errorMessage,OceanException e) {
		SDKResult<TResponse> result = new SDKResult<TResponse>();
		result.setErrorCode(errorCode);
		result.setErrorMessage(errorMessage);
		result.setResult(ret);
		if(context!=null && context.getResponse()!=null) {
			if(context.getResponse().getRequestId()!=null){
				result.setRequestId(context.getResponse().getRequestId());
			}
			if(context.getResponse().getTraceId()!=null){
				result.setTraceId(context.getResponse().getTraceId());
			}
		}
		if(e!=null && e.getRequestId()!=null && e.getTraceId()!=null){
			result.setRequestId(e.getRequestId());
			result.setTraceId(e.getTraceId());
		}
		return result;
	}



	/**
	 *
	 * @param apiRequest
	 * @return
	 */
	public final <TResponse> SDKResult<MapiResult<TResponse>> mapiExecute(AbstractAPIRequest<TResponse> apiRequest, String accessToken) {
		RequestPolicy reqPolicy = apiRequest.getOceanRequestPolicy();
		try {
			APIId apiId = apiRequest.getOceanApiId();
			Request req = new Request(apiId.getNamespace(), apiId.getName(), apiId.getVersion());

			req.setRequestEntity(apiRequest);
			if(accessToken!=null){
				req.setAccessToken(accessToken);
			}

			MapiResult<TResponse> ret = getMAPIClient().mapiSend(req, apiRequest.getResponseClass(), reqPolicy,
					serializerListeners.values(), deSerializerListeners.values());
			return new SDKResult<MapiResult<TResponse>>(ret);
		} catch (OceanException e) {
			return new SDKResult<MapiResult<TResponse>>(e.getErrorCode(), e.getErrorMessage());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
