package com.salesmanager.core.business.alibaba.param;

import com.salesmanager.core.business.alibaba.rawsdk.client.APIId;
import com.salesmanager.core.business.alibaba.rawsdk.common.AbstractAPIRequest;


public class AlibabaTradePayProtocolPayIsopenParam extends AbstractAPIRequest<AlibabaTradePayProtocolPayIsopenResult> {

    public AlibabaTradePayProtocolPayIsopenParam() {
        super();
        oceanApiId = new APIId("com.alibaba.trade", "alibaba.trade.pay.protocolPay.isopen", 1);
    }

}
