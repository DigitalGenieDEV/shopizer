/*
 * Copyright 2012 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.salesmanager.core.business.alibaba.rawsdk.client;


import com.salesmanager.core.business.alibaba.rawsdk.client.exception.OceanException;

/**
 */
public interface ErrorHandler {
    void handle(OceanException targetException);
}
