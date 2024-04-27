package com.salesmanager.core.business.alibaba.fenxiao.param;

import com.salesmanager.core.business.alibaba.rawsdk.client.APIId;
import com.salesmanager.core.business.alibaba.rawsdk.common.AbstractAPIRequest;


public class AlibabaFenxiaoRelationaddParam extends AbstractAPIRequest<AlibabaFenxiaoRelationaddResult> {

    public AlibabaFenxiaoRelationaddParam() {
        super();
        oceanApiId = new APIId("com.alibaba.fenxiao", "alibaba.fenxiao.relationadd", 1);
    }

    private Long offerId;

    /**
     * @return 商品id
     */
    public Long getOfferId() {
        return offerId;
    }

    /**
     *     商品id     *
     * 参数示例：<pre>98129931</pre>     
     *    
     */
    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

}
