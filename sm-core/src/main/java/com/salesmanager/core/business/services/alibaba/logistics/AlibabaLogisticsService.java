package com.salesmanager.core.business.services.alibaba.logistics;

import com.salesmanager.core.business.alibaba.param.AlibabaLogisticsOpenPlatformLogisticsOrder;
import com.salesmanager.core.business.alibaba.param.AlibabaLogisticsOpenPlatformLogisticsTrace;
import com.salesmanager.core.business.alibaba.param.AlibabaTradeGetLogisticsInfosBuyerViewParam;
import com.salesmanager.core.business.alibaba.param.AlibabaTradeGetLogisticsTraceInfoBuyerViewParam;

public interface AlibabaLogisticsService {

    AlibabaLogisticsOpenPlatformLogisticsOrder[] getLogisticsInfosBuyerView(AlibabaTradeGetLogisticsInfosBuyerViewParam param);

    AlibabaLogisticsOpenPlatformLogisticsTrace[] getLogisticsTraceInfoBuyerView(AlibabaTradeGetLogisticsTraceInfoBuyerViewParam param);
}
