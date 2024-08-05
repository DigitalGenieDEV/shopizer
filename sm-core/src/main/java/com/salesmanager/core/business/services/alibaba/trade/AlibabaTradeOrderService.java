package com.salesmanager.core.business.services.alibaba.trade;

import com.salesmanager.core.business.alibaba.param.AlibabaOpenplatformTradeModelTradeInfo;
import com.salesmanager.core.business.alibaba.param.AlibabaTradeCreateCrossOrderParam;
import com.salesmanager.core.business.alibaba.param.AlibabaTradeCrossResult;
import com.salesmanager.core.business.alibaba.param.AlibabaTradeGetBuyerViewParam;

public interface AlibabaTradeOrderService {

    AlibabaTradeCrossResult createCrossOrder(AlibabaTradeCreateCrossOrderParam param);

    AlibabaOpenplatformTradeModelTradeInfo getCrossOrderBuyerView(AlibabaTradeGetBuyerViewParam param);
}
