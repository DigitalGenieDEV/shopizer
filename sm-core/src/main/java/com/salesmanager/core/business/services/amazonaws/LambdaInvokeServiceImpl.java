package com.salesmanager.core.business.services.amazonaws;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.salesmanager.core.business.services.search.RecProductServiceImpl;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service("lambdaInvokeService")
public class LambdaInvokeServiceImpl implements LambdaInvokeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LambdaInvokeServiceImpl.class);

    @Override
    public String invoke(String functionName, String payload) throws Exception {
        InvokeRequest invokeRequest = new InvokeRequest()
                .withFunctionName(functionName)
                .withPayload(payload);

        InvokeResult invokeResult = null;

        AWSLambda awsLambda = AWSLambdaClientBuilder.standard()
                .withCredentials(new EnvironmentVariableCredentialsProvider())
                .withRegion(Regions.AP_NORTHEAST_2).build();

        invokeResult = awsLambda.invoke(invokeRequest);

        String responseString = new String(invokeResult.getPayload().array(), StandardCharsets.UTF_8);

        return getResponse(responseString);
    }

    private String getResponse(String responseString) throws Exception {
        JSONObject responseJson = new JSONObject(responseString);
        //LOGGER.info("lambda response: " + responseString);
        // 检查 statusCode
        int statusCode = responseJson.getInt("statusCode");
        if (statusCode != 200) {
            throw new Exception("Unexpected statusCode: " + statusCode);
        }

        // 提取 body 字符串
        String body = responseJson.getString("body");
        return body;
    }
}
