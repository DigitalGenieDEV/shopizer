package com.salesmanager.core.business.alibaba.param;

import com.salesmanager.core.business.alibaba.rawsdk.client.APIId;
import com.salesmanager.core.business.alibaba.rawsdk.common.AbstractAPIRequest;


public class AlibabaTradeAddresscodeParseParam extends AbstractAPIRequest<AlibabaTradeAddresscodeParseResult> {

    public AlibabaTradeAddresscodeParseParam() {
        super();
        oceanApiId = new APIId("com.alibaba.trade", "alibaba.trade.addresscode.parse", 1);
    }

    private String addressInfo;

    /**
     * @return 地址信息
     */
    public String getAddressInfo() {
        return addressInfo;
    }

    /**
     *     地址信息     *
     * 参数示例：<pre>浙江省 杭州市 滨江区网商路699号</pre>     
     *    
     */
    public void setAddressInfo(String addressInfo) {
        this.addressInfo = addressInfo;
    }

}
