package com.salesmanager.core.business.services.alibaba.logistics.impl;

import com.alibaba.fastjson.JSON;
import com.salesmanager.core.business.alibaba.ApiExecutorSingleton;
import com.salesmanager.core.business.alibaba.param.*;
import com.salesmanager.core.business.alibaba.rawsdk.ApiExecutor;
import com.salesmanager.core.business.services.alibaba.logistics.AlibabaLogisticsService;
import com.salesmanager.core.constants.ApiFor1688Constants;
import org.springframework.stereotype.Service;

@Service
public class AlibabaLogisticsServiceImpl implements AlibabaLogisticsService {

    @Override
    public AlibabaLogisticsOpenPlatformLogisticsOrder[] getLogisticsInfosBuyerView(AlibabaTradeGetLogisticsInfosBuyerViewParam param) {
        ApiExecutor apiExecutor = ApiExecutorSingleton.getInstance();
        AlibabaTradeGetLogisticsInfosBuyerViewResult result = apiExecutor.execute(param, ApiFor1688Constants.ACCESS_TOKEN).getResult();
        if (!result.getSuccess()) {
            throw new RuntimeException("AlibabaProductServiceIApiFor1688 getLogisticsInfosBuyerView getLogisticsInfosBuyerView error" + JSON.toJSON(param));
        }
        return result.getResult();
    }

    @Override
    public AlibabaLogisticsOpenPlatformLogisticsTrace[] getLogisticsTraceInfoBuyerView(AlibabaTradeGetLogisticsTraceInfoBuyerViewParam param) {
        ApiExecutor apiExecutor = ApiExecutorSingleton.getInstance();
        AlibabaTradeGetLogisticsTraceInfoBuyerViewResult result = apiExecutor.execute(param, ApiFor1688Constants.ACCESS_TOKEN).getResult();
        if (!result.getSuccess()) {
            throw new RuntimeException("AlibabaProductServiceIApiFor1688 getLogisticsTraceInfoBuyerView error" + JSON.toJSON(param));
        }
        return result.getLogisticsTrace();
    }
}
