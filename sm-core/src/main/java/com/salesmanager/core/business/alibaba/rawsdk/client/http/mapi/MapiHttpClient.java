/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */

package com.salesmanager.core.business.alibaba.rawsdk.client.http.mapi;

import com.alibaba.fastjson.JSON;
import com.salesmanager.core.business.alibaba.rawsdk.client.Response;
import com.salesmanager.core.business.alibaba.rawsdk.client.http.HttpResponseBuilder;
import com.salesmanager.core.business.alibaba.rawsdk.client.http.InvokeContext;
import com.salesmanager.core.business.alibaba.rawsdk.client.policy.ClientPolicy;
import com.salesmanager.core.business.alibaba.rawsdk.client.policy.Protocol;
import com.salesmanager.core.business.alibaba.rawsdk.client.serialize.DeSerializerListener;
import com.salesmanager.core.business.alibaba.rawsdk.client.serialize.Serializer;
import com.salesmanager.core.business.alibaba.rawsdk.client.serialize.SerializerListener;
import com.salesmanager.core.business.alibaba.rawsdk.client.serialize.SerializerProvider;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.Map;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPOutputStream;

/**
 */
public class MapiHttpClient {

    private SerializerProvider serializerProvider;
    private HttpResponseBuilder responseBuilder;

    public MapiHttpClient(SerializerProvider serializerProvider, HttpResponseBuilder responseBuilder) {
        super();
        this.serializerProvider = serializerProvider;
        this.responseBuilder = responseBuilder;
    }

    public Response request(InvokeContext invokeContext, ClientPolicy clientPolicy,
                            Collection<SerializerListener> serializerListners, Collection<DeSerializerListener> deSerializerListners)
            throws IOException {
        // create the path. The pass is used when signature the request.
        StringBuilder requestPath = MapiHttpSupport.getProtocolRequestPath(invokeContext, clientPolicy);
        // generate the http parameters, include the system parameters.
        Protocol protocol = invokeContext.getPolicy().getRequestProtocol();
        Serializer serializer = serializerProvider.getSerializer(protocol.name());
        for (SerializerListener serializerListener : serializerListners) {
            serializer.registeSerializerListener(serializerListener);
        }
        Map<String, Object> parameters = MapiHttpSupport.buildParams(serializer, invokeContext);
        byte[] paramBytes = getParamBytes(parameters,invokeContext);
        String timeStamp = String.valueOf(System.currentTimeMillis());
        String queryString = MapiHttpSupport.getQueryString(timeStamp,invokeContext);
        // signature the request
        String signature = MapiHttpSupport.getSignature(requestPath.toString(), queryString, paramBytes, invokeContext, clientPolicy);
        //mapi 只支持POST
        URL postURL = MapiHttpSupport.buildFinalRequestUrl(clientPolicy, invokeContext, requestPath.toString(), queryString);
        Response response = this.doPost(postURL, paramBytes,signature, invokeContext, clientPolicy, deSerializerListners);
        return response;
    }


    public  Response doPost(URL url, byte[] contentBody, String signature, InvokeContext invokeContext,
                            ClientPolicy clientPolicy, Collection<DeSerializerListener> deSerializerListners) {
        HttpURLConnection conn = null;
        OutputStream out = null;
        Response response = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            //    头部信息
            Map<String, String> clientHttpHeader = MapiHttpSupport.buildHttpHeader(invokeContext, clientPolicy);
            for (Map.Entry<String, String> entry : clientHttpHeader.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
            //    签名
            conn.setRequestProperty("X-Sign-HMAC", signature);

            //查询体body
            out = conn.getOutputStream();
            out.write(contentBody);

            int httpCode = conn.getResponseCode();
            String contentEncoding = conn.getContentEncoding();
            String contentType = conn.getContentType();
            InputStream inputStream = null;
            if (httpCode >= 200 && httpCode <= 299) {
                inputStream = conn.getInputStream();
            } else {
                inputStream = conn.getErrorStream();
            }

            response = this.getResponseBuilder().buildResponse(inputStream, httpCode, contentEncoding, contentType,
                    invokeContext, deSerializerListners);
        }catch (Exception e){
            response.setException(e);
        }finally {
            try{
                if (out != null) {
                    out.close();
                }
            }catch (Exception e){
                //ignore stream close exception
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return response;
    }

    private byte[] getParamBytes( Map<String, Object> parameters,InvokeContext invokeContext){
        String ce = invokeContext.getPolicy().getRequestContentEncoding();
        //默认返回
        byte[] body = JSON.toJSONBytes(parameters);
        if("gzip".equals(ce)){
            return gzipCompressByte(body);
        }else if("deflate".equals(ce)){
            return deflateCompressByte(body);
        }
        return body;
    }

    //压缩
    private byte[] gzipCompressByte(byte[] body){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(body);
            gzip.close();
        } catch (IOException e) {
            return null;
        }
        return out.toByteArray();
    }
    private byte[] deflateCompressByte(byte[] body){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DeflaterOutputStream deflate;
        try {
            deflate = new DeflaterOutputStream(out);
            deflate.write(body);
            deflate.close();
        } catch (IOException e) {
            return null;
        }
        return out.toByteArray();
    }

    public SerializerProvider getSerializerProvider() {
        return serializerProvider;
    }

    public HttpResponseBuilder getResponseBuilder() {
        return responseBuilder;
    }

}
