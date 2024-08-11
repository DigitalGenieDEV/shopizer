package com.salesmanager.core.business.services.alibaba.trade.impl;

import com.alibaba.fastjson.JSON;
import com.salesmanager.core.business.alibaba.ApiExecutorSingleton;
import com.salesmanager.core.business.alibaba.param.*;
import com.salesmanager.core.business.alibaba.rawsdk.ApiExecutor;
import com.salesmanager.core.business.services.alibaba.trade.AlibabaTradeOrderService;
import com.salesmanager.core.constants.ApiFor1688Constants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AlibabaTradeOrderServiceImpl implements AlibabaTradeOrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AlibabaTradeOrderServiceImpl.class);

    @Override
    public AlibabaTradeCrossResult createCrossOrder(AlibabaTradeCreateCrossOrderParam param) {
        ApiExecutor apiExecutor = ApiExecutorSingleton.getInstance();
        AlibabaTradeCreateCrossOrderResult result = apiExecutor.execute(param, ApiFor1688Constants.ACCESS_TOKEN).getResult();

        if (!result.getResult().getSuccess()) {
            throw new RuntimeException("AlibabaProductServiceIApiFor1688Constantsmpl searchKeyword error" + JSON.toJSON(param));
        }

        return result.getResult();
    }

    @Override
    public AlibabaOpenplatformTradeModelTradeInfo getCrossOrderBuyerView(AlibabaTradeGetBuyerViewParam param) {
        ApiExecutor apiExecutor = ApiExecutorSingleton.getInstance();
        AlibabaTradeGetBuyerViewResult result = apiExecutor.execute(param, ApiFor1688Constants.ACCESS_TOKEN).getResult();
        if (!StringUtils.equalsIgnoreCase(result.getSuccess(), "true")) {
            throw new RuntimeException("AlibabaTradeOrderComponentImpl getCrossOrderBuyerView error" + JSON.toJSON(param));
        }
        return result.getResult();
    }
}
