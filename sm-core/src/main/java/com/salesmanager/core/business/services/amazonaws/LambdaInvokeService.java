package com.salesmanager.core.business.services.amazonaws;

public interface LambdaInvokeService {

    String invoke(String functionName, String payload) throws Exception;
}
