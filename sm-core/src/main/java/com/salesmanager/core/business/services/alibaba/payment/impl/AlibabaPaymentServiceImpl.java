package com.salesmanager.core.business.services.alibaba.payment.impl;

import com.alibaba.fastjson.JSON;
import com.salesmanager.core.business.alibaba.ApiExecutorSingleton;
import com.salesmanager.core.business.alibaba.param.AlibabaAlipayUrlGetParam;
import com.salesmanager.core.business.alibaba.param.AlibabaAlipayUrlGetResult;
import com.salesmanager.core.business.alibaba.rawsdk.ApiExecutor;
import com.salesmanager.core.business.services.alibaba.payment.AlibabaPaymentService;
import com.salesmanager.core.constants.ApiFor1688Constants;
import org.springframework.stereotype.Service;

@Service
public class AlibabaPaymentServiceImpl implements AlibabaPaymentService {
    @Override
    public AlibabaAlipayUrlGetResult getAlipayUrl(AlibabaAlipayUrlGetParam param) {
        ApiExecutor apiExecutor = ApiExecutorSingleton.getInstance();
        AlibabaAlipayUrlGetResult result = apiExecutor.execute(param, ApiFor1688Constants.ACCESS_TOKEN).getResult();
        if (!result.getSuccess()) {
            throw new RuntimeException("AlibabaPaymentComponentImpl getAlipayUrl error" + JSON.toJSON(param));
        }
        return result;
    }
}
