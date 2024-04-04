package com.salesmanager.core.business.alibaba.param;

import com.salesmanager.core.business.alibaba.rawsdk.client.APIId;
import com.salesmanager.core.business.alibaba.rawsdk.common.AbstractAPIRequest;


public class AlibabaTradeReceiveAddressGetParam extends AbstractAPIRequest<AlibabaTradeReceiveAddressGetResult> {

    public AlibabaTradeReceiveAddressGetParam() {
        super();
        oceanApiId = new APIId("com.alibaba.trade", "alibaba.trade.receiveAddress.get", 1);
    }

}
