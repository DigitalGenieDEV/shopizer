package com.salesmanager.core.business.services.alibaba.logistics;

import com.salesmanager.core.business.alibaba.param.AlibabaLogisticsOpenPlatformLogisticsOrder;
import com.salesmanager.core.business.alibaba.param.AlibabaTradeGetLogisticsInfosBuyerViewParam;

public interface AlibabaLogisticsService {

    AlibabaLogisticsOpenPlatformLogisticsOrder[] getLogisticsInfosBuyerView(AlibabaTradeGetLogisticsInfosBuyerViewParam param);
}
