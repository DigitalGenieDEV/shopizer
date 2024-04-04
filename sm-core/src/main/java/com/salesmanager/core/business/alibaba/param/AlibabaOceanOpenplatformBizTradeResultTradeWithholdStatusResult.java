package com.salesmanager.core.business.alibaba.param;

public class AlibabaOceanOpenplatformBizTradeResultTradeWithholdStatusResult {

    private AlibabaOceanOpenplatformBizTradeResultTradePaymentAgreement[] paymentAgreements;

    /**
     * @return 
     */
    public AlibabaOceanOpenplatformBizTradeResultTradePaymentAgreement[] getPaymentAgreements() {
        return paymentAgreements;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setPaymentAgreements(AlibabaOceanOpenplatformBizTradeResultTradePaymentAgreement[] paymentAgreements) {
        this.paymentAgreements = paymentAgreements;
    }

}
