package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class PoolProductPullProductPoolModel {

    private Long offerId;

    /**
     * @return 商品ID
     */
    public Long getOfferId() {
        return offerId;
    }

    /**
     * 设置商品ID     *
     * 参数示例：<pre>111111</pre>     
     * 此参数必填
     */
    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    private String bizCategoryId;

    /**
     * @return 机构类目ID
     */
    public String getBizCategoryId() {
        return bizCategoryId;
    }

    /**
     * 设置机构类目ID     *
     * 参数示例：<pre>11111</pre>     
     * 此参数必填
     */
    public void setBizCategoryId(String bizCategoryId) {
        this.bizCategoryId = bizCategoryId;
    }

}
