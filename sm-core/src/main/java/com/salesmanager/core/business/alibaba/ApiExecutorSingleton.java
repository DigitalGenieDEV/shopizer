package com.salesmanager.core.business.alibaba;

import com.salesmanager.core.business.alibaba.rawsdk.ApiExecutor;
import com.salesmanager.core.constants.ApiFor1688Constants;

public class ApiExecutorSingleton {

    private static final String APP_KEY = ApiFor1688Constants.APP_KEY;
    private static final String SECRET_KEY = ApiFor1688Constants.SECRET_KEY;
    private static volatile ApiExecutor apiExecutorInstance;

    private ApiExecutorSingleton() {}

    public static ApiExecutor getInstance() {
        if (apiExecutorInstance == null) {
            synchronized (ApiExecutorSingleton.class) {
                if (apiExecutorInstance == null) {
                    apiExecutorInstance = new ApiExecutor(APP_KEY, SECRET_KEY);
                }
            }
        }
        return apiExecutorInstance;
    }
}
