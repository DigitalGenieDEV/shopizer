package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

import com.salesmanager.core.business.alibaba.rawsdk.client.APIId;
import com.salesmanager.core.business.alibaba.rawsdk.common.AbstractAPIRequest;


public class OrderRelationWriteParam extends AbstractAPIRequest<OrderRelationWriteResult> {

    public OrderRelationWriteParam() {
        super();
        oceanApiId = new APIId("com.alibaba.fenxiao.crossborder", "order.relation.write", 1);
    }

    private AlibabaCbuOrderParamOrderRelationParam orderRelationParam;

    /**
     * @return 订单关系参数
     */
    public AlibabaCbuOrderParamOrderRelationParam getOrderRelationParam() {
        return orderRelationParam;
    }

    /**
     * 设置订单关系参数     *
     * 参数示例：<pre>如下</pre>     
     * 此参数必填
     */
    public void setOrderRelationParam(AlibabaCbuOrderParamOrderRelationParam orderRelationParam) {
        this.orderRelationParam = orderRelationParam;
    }

}
