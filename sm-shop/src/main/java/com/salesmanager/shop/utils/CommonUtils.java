package com.salesmanager.shop.utils;

import javax.servlet.http.HttpServletRequest;

public class CommonUtils {
	
	// Get IP
	public static String getRemoteIp(HttpServletRequest request) throws NullPointerException, SecurityException {
	    String remoteIp = "";

        String[] headersToCheck = { "X-Forwarded-For", "Forwarded", "Proxy-Client-IP", 
                "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR" };
        byte b;
        int i;
        String[] arrayOfString1;
        for (i = (arrayOfString1 = headersToCheck).length, b = 0; b < i; ) {
            String header = arrayOfString1[b];
            remoteIp = request.getHeader(header);
            if (remoteIp != null && remoteIp.length() > 0 
            		&& !"unknown".equalsIgnoreCase(remoteIp)) {
                break; 
            }
            b++;
        } 
        if (remoteIp == null || remoteIp.length() == 0 
        		|| "unknown".equalsIgnoreCase(remoteIp)) {
            remoteIp = request.getRemoteAddr(); 
        }
	   return remoteIp;
	}
}
