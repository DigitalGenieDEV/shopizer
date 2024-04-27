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
     *     商品ID     *
     * 参数示例：<pre>111111</pre>     
     *
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
     *     机构类目ID     *
     * 参数示例：<pre>11111</pre>     
     *
     */
    public void setBizCategoryId(String bizCategoryId) {
        this.bizCategoryId = bizCategoryId;
    }

}
