/*
 * Copyright 2012 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.salesmanager.core.business.alibaba.rawsdk.client.entity;


/**
 */
public interface AuthorizationTokenStore {
    /**
     * @param authorizationCode
     * @return {@link AuthorizationToken}
     */
    public AuthorizationToken getToken(String authorizationCode);

    /**
     * @param authorizationCode
     * @param token
     */
    public void storeToken(String authorizationCode, AuthorizationToken token);

    /**
     * @param authorizationCode
     */
    public void removeToken(String authorizationCode);
    
    /**
     * @param uid
     * @param token
     */
    public void storeAccessToken(String uid, AuthorizationToken token);
    
    /**
     * @param uid
     * @return
     */
    public AuthorizationToken getAccessToken(String uid);
}
