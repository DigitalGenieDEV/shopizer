package com.salesmanager.core.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

public class HttpUtils {
    public static String sendHttpGetRequest(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // 设置请求头，根据需要自行添加
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } else {
            // 读取错误信息
            InputStream errorStream = connection.getErrorStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(errorStream));
            StringBuilder errorMessage = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                errorMessage.append(line);
            }
            reader.close();

            // 输出错误信息
            System.out.println("Error Message: " + errorMessage.toString());

            throw new Exception("HTTP GET request failed with response code: " + responseCode);
        }
    }

    public static String buildHttpGetParams(Object obj) throws IllegalAccessException, UnsupportedEncodingException {
        StringBuilder params = new StringBuilder();

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(obj);
            if (value != null) {
                appendParam(params, field.getName(), value.toString());
            }
        }

        return params.toString();
    }

    public static void appendParam(StringBuilder params, String key, String value) throws UnsupportedEncodingException {
        if (params.length() > 0) {
            params.append("&");
        }
        params.append(URLEncoder.encode(key, "UTF-8"))
                .append("=")
                .append(URLEncoder.encode(value, "UTF-8"));
    }


    public static String generateSignature(String url, Map<String, String> params, String secretKey) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, MalformedURLException, InvalidKeyException {
        // 提取 urlPath
        String urlPath = getUrlPath(url);

        // 提取请求参数，按首字母排序并拼接
        String paramStr = getSortedParams(params);

        // 合并签名因子
        String signatureString = urlPath + paramStr;

        // 使用 HMAC_SHA1 算法计算签名
        Mac hmacSha1 = Mac.getInstance("HmacSHA1");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA1");
        hmacSha1.init(secretKeySpec);
        byte[] signatureBytes = hmacSha1.doFinal(signatureString.getBytes("UTF-8"));

        // 转为十六进制并转换为大写
        StringBuilder signatureBuilder = new StringBuilder();
        for (byte b : signatureBytes) {
            signatureBuilder.append(String.format("%02x", b));
        }
        String signature = signatureBuilder.toString().toUpperCase();

        return signature;
    }

    private static String getUrlPath(String urlString) throws UnsupportedEncodingException, MalformedURLException {
        URL url = new URL(urlString);
        return extractPath(url.getPath().substring(1)); // 去除第一个斜杠
    }

    public static String extractPath(String url) {
        int index = url.indexOf("openapi/");
        if (index != -1) {
            return url.substring(index + "openapi/".length());
        }
        return null; // 如果没有找到/openapi/，返回null或者根据需求进行处理
    }

    private static String getSortedParams(Map<String, String> params) throws UnsupportedEncodingException {
        TreeMap<String, String> sortedParams = new TreeMap<>(params);
        StringBuilder paramStrBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : sortedParams.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            paramStrBuilder.append(key).append(value);
        }
        return paramStrBuilder.toString();
    }

}
