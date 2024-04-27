package com.salesmanager.core.business.alibaba.param;

import com.salesmanager.core.business.alibaba.rawsdk.client.APIId;
import com.salesmanager.core.business.alibaba.rawsdk.common.AbstractAPIRequest;


public class AlibabaTradePayProtocolPayPreparePayParam extends AbstractAPIRequest<AlibabaTradePayProtocolPayPreparePayResult> {

    public AlibabaTradePayProtocolPayPreparePayParam() {
        super();
        oceanApiId = new APIId("com.alibaba.trade", "alibaba.trade.pay.protocolPay.preparePay", 1);
    }

    private AlibabaOceanOpenplatformBizTradeParamTradeWithholdPreparePayParam tradeWithholdPreparePayParam;

    /**
     * @return 发起免密支付
     */
    public AlibabaOceanOpenplatformBizTradeParamTradeWithholdPreparePayParam getTradeWithholdPreparePayParam() {
        return tradeWithholdPreparePayParam;
    }

    /**
     *     发起免密支付     *
     *        
     *    
     */
    public void setTradeWithholdPreparePayParam(AlibabaOceanOpenplatformBizTradeParamTradeWithholdPreparePayParam tradeWithholdPreparePayParam) {
        this.tradeWithholdPreparePayParam = tradeWithholdPreparePayParam;
    }

}
