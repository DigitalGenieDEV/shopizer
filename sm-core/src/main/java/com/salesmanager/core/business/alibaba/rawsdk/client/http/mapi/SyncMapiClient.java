/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */

package com.salesmanager.core.business.alibaba.rawsdk.client.http.mapi;


import com.salesmanager.core.business.alibaba.rawsdk.client.Request;
import com.salesmanager.core.business.alibaba.rawsdk.client.entity.AuthorizationToken;
import com.salesmanager.core.business.alibaba.rawsdk.client.entity.AuthorizationTokenStore;
import com.salesmanager.core.business.alibaba.rawsdk.client.entity.DefaultAuthorizationTokenStore;
import com.salesmanager.core.business.alibaba.rawsdk.client.exception.OceanException;
import com.salesmanager.core.business.alibaba.rawsdk.client.http.HttpResponseBuilder;
import com.salesmanager.core.business.alibaba.rawsdk.client.http.InvokeContext;
import com.salesmanager.core.business.alibaba.rawsdk.client.http.platform.DefaultHttpResponseBuilder;
import com.salesmanager.core.business.alibaba.rawsdk.client.policy.ClientPolicy;
import com.salesmanager.core.business.alibaba.rawsdk.client.policy.RequestPolicy;
import com.salesmanager.core.business.alibaba.rawsdk.client.serialize.DeSerializerListener;
import com.salesmanager.core.business.alibaba.rawsdk.client.serialize.SerializerListener;
import com.salesmanager.core.business.alibaba.rawsdk.client.serialize.SerializerProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 */
public class SyncMapiClient {

    private MapiHttpClient httpClient;
    private ClientPolicy clientPolicy;
    private AuthorizationTokenStore authorizationTokenStore;

    public SyncMapiClient(ClientPolicy clientPolicy, SerializerProvider serializerProvider) {
        this(clientPolicy, serializerProvider, new DefaultAuthorizationTokenStore());
    }

    public SyncMapiClient(ClientPolicy clientPolicy, SerializerProvider serializerProvider,
                          AuthorizationTokenStore authorizationTokenStore) {
        super();
        this.clientPolicy = clientPolicy;
        this.authorizationTokenStore = authorizationTokenStore;
        HttpResponseBuilder httpResponseBuilder = new DefaultHttpResponseBuilder(clientPolicy, serializerProvider);
        httpClient = new MapiHttpClient(serializerProvider, httpResponseBuilder);
    }


    public <T> T send(Request request, Class<T> resultType, RequestPolicy policy,
                      Collection<SerializerListener> serializerListners, Collection<DeSerializerListener> deSerializerListners)
            throws OceanException {
        InvokeContext invokeContext = new InvokeContext();
        invokeContext.setPolicy(policy);
        invokeContext.setRequest(request);
        invokeContext.setResultType(resultType);
        try {
            httpClient.request(invokeContext, clientPolicy, serializerListners, deSerializerListners);
            if (invokeContext.getResponse().getException() != null) {
                Throwable responseException = invokeContext.getResponse().getException();
                if (responseException instanceof OceanException) {
                    throw (OceanException) responseException;
                } else {
                    throw new OceanException(responseException.getMessage(),responseException);
                }
            }
            return (T) invokeContext.getResponse().getResult();
        } catch (IOException e) {
            throw new OceanException("IOException",e);
        }
    }

    public <T> MapiResult<T> mapiSend(Request request, Class<T> resultType, RequestPolicy policy,
                                      Collection<SerializerListener> serializerListners, Collection<DeSerializerListener> deSerializerListners)
            throws OceanException {
        InvokeContext invokeContext = new InvokeContext();
        invokeContext.setPolicy(policy);
        invokeContext.setRequest(request);
        //设定返回类型是MapiResult
        invokeContext.setResultType(new MapiResult<T>().getClass());
        try {
            httpClient.request(invokeContext, clientPolicy, serializerListners, deSerializerListners);
            if (invokeContext.getResponse().getException() != null) {
                Throwable responseException = invokeContext.getResponse().getException();
                if (responseException instanceof OceanException) {
                    throw (OceanException) responseException;
                } else {
                    throw new OceanException(responseException.getMessage(),responseException);
                }
            }
            return (MapiResult<T>) invokeContext.getResponse().getResult();
        } catch (IOException e) {
            throw new OceanException("IOException",e);
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
                new ArrayList<DeSerializerListener>());
    }

    public AuthorizationToken refreshToken(String refreshToken) throws OceanException {
        Request request = new Request("system.oauth2", "getToken");
        request.addAddtionalParams("refreshToken", refreshToken);
        request.addAddtionalParams("grant_type", "refresh_token");
        request.addAddtionalParams("client_id", clientPolicy.getAppKey());
        request.addAddtionalParams("client_secret", clientPolicy.getSigningKey());
        RequestPolicy oauthPolicy = RequestPolicy.getAuthPolicy();
        return this.send(request, AuthorizationToken.class, oauthPolicy, new ArrayList<SerializerListener>(),
                new ArrayList<DeSerializerListener>());
    }
}
