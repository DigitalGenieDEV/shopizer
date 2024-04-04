/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */

package com.salesmanager.core.business.alibaba.rawsdk.client.http.mapi;


import com.salesmanager.core.business.alibaba.rawsdk.client.Request;
import com.salesmanager.core.business.alibaba.rawsdk.client.http.InvokeContext;
import com.salesmanager.core.business.alibaba.rawsdk.client.policy.ClientPolicy;
import com.salesmanager.core.business.alibaba.rawsdk.client.serialize.Serializer;
import com.salesmanager.core.business.alibaba.rawsdk.util.SignatureUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 类MapiHttpSupport.java的实现描述：mapi工具类
 *
 * @author yuan.gy on 2020/10/20 3:23 下午
 */
public class MapiHttpSupport {

    public static final String HTTP_PROTOCOL = "HTTP/1.1";
    public static final String METHOD_POST = "POST";

    public static final String PARAM_NAME_TIMESTAMP = "_aop_timestamp";
    public static final String PARAM_NAME_ACCESS_TOKEY = "access_token";

    public static final Charset CHARSET_UTF_8 = Charset.forName("UTF-8");

    /**
     *
     * @param context
     * @param clientPolicy
     * @return
     */
    public static StringBuilder getProtocolRequestPath(InvokeContext context, ClientPolicy clientPolicy) {
        StringBuilder path = new StringBuilder();
        path.append("/mapi/");
        Request request = context.getRequest();
        if (request.getApiId().getVersion() < 0) {
            path.append(context.getPolicy().getDefaultApiVersion());
        } else {
            path.append(request.getApiId().getVersion());
        }
        path.append('/').append(request.getApiId().getNamespace()).append('/').append(request.getApiId().getName());
        if (clientPolicy.getAppKey() != null) {
            path.append('/').append(clientPolicy.getAppKey());
        }
        return path;
    }

    /**
     *
     * @param serializer
     * @param context
     * @return
     */
    public static Map<String, Object> buildParams(Serializer serializer, InvokeContext context) {
        Map<String, Object> serializerMap = serializer.serialize(context.getRequest().getRequestEntity());
        return serializerMap;
    }

    /**
     * @param path
     * @param queryString
     * @param context
     * @param clientPolicy
     */
    public static String getSignature(String path, String queryString, byte[] params,InvokeContext context,ClientPolicy clientPolicy) {
        String httpRequestStartLine = String.format("%s %s?%s %s", METHOD_POST, path, queryString, HTTP_PROTOCOL);
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        try{
            //查询url
            write(httpRequestStartLine, os, "UTF-8");
            //查询body
            os.write(params);
            String accessToken = context.getRequest().getAccessToken();
            String appSecret = clientPolicy.getSigningKey();
            String securityKey = getSecurityKey(appSecret,accessToken);

            String expectSignature = SignatureUtil.hmacSha1ToBase64(os.toByteArray(), securityKey);
            return expectSignature;
        }catch (Exception e){
            return null;
        }
    }


    /**
     * mapi的url里，queryString暂时只支持时间戳和accessToken
     *
     * @param timestamp
     * @param context
     */
    public static String getQueryString(String timestamp,InvokeContext context){
        StringBuilder sb = new StringBuilder();
        //需要时间戳
        boolean needTimeStamp = context.getPolicy().isRequestSendTimestamp();
        String accessToken = context.getRequest().getAccessToken();
        if(needTimeStamp){
            sb.append(PARAM_NAME_TIMESTAMP+"="+timestamp);
            if(accessToken!=null){
                sb.append("&");
            }
        }
        if(accessToken!=null){
            sb.append(PARAM_NAME_ACCESS_TOKEY+"="+context.getRequest().getAccessToken());
        }
        return sb.toString();
    }

    /**
     * mapi的头部，只接受固定值
     *
     * @param context
     * @param clientPolicy
     */
    public final static Map<String, String> buildHttpHeader(InvokeContext context, ClientPolicy clientPolicy) {
        Map<String, String> header = new LinkedHashMap<String, String>();
        header.put("Accept", "application/json");
        header.put("User-Agent", clientPolicy.getAgent());
        header.put("Content-Type","application/json;charset=UTF-8");
        header.put("Accept-Charset","UTF-8");
        header.put("X-SequenceId", UUID.randomUUID().toString());
        if("gzip".equals(context.getPolicy().getRequestContentEncoding())){
            header.put("Content-Encoding","gzip");
        }else if("deflate".equals(context.getPolicy().getRequestContentEncoding())){
            header.put("Content-Encoding","deflate");
        }
        return header;
    }

    public static URL buildFinalRequestUrl(ClientPolicy clientPolicy, InvokeContext context, String requestPath,String queryString)
            throws IOException {
        String protocol = "http";
        String host = clientPolicy.getServerHost();
        int port = clientPolicy.getHttpPort();
        if (context.getPolicy().isUseHttps()) {
            protocol = "https";
            port = clientPolicy.getHttpsPort();
        }
        StringBuilder pathSB = new StringBuilder(requestPath);
        if(queryString!=null){
            pathSB.append("?"+queryString);
        }
        return new URL(protocol, host, port, pathSB.toString());

    }

    public static String getSecurityKey(String appSecret,String accessToken){
        if(null==accessToken||null==appSecret){
            return appSecret;
        }
        try{
            byte[] secretBytes = appSecret.getBytes("UTF-8");
            byte[] data = accessToken.getBytes(CHARSET_UTF_8);
            return SignatureUtil.hmacSha1ToHexStr(data, secretBytes, 0, data.length);
        }catch (Exception e){
            return null;
        }
    }



    public static void write(String data, OutputStream output, String encoding) throws IOException {
        if (data != null) {
            if (encoding == null) {
                write(data, output);
            } else {
                output.write(data.getBytes(encoding));
            }
        }

    }

    public static void write(String data, OutputStream output) throws IOException {
        if (data != null) {
            output.write(data.getBytes());
        }
    }

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        copy((InputStream)input, (OutputStream)output);
        return output.toByteArray();
    }

    public static int copy(InputStream input, OutputStream output) throws IOException {
        long count = copyLarge(input, output);
        return count > 2147483647L ? -1 : (int)count;
    }

    public static long copyLarge(InputStream input, OutputStream output) throws IOException {
        return copyLarge(input, output, new byte[4096]);
    }

    public static long copyLarge(InputStream input, OutputStream output, byte[] buffer) throws IOException {
        long count = 0L;

        int n;
        for(boolean var5 = false; -1 != (n = input.read(buffer)); count += (long)n) {
            output.write(buffer, 0, n);
        }

        return count;
    }
}
