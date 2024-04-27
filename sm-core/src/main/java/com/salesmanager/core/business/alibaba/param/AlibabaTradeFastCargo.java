package com.salesmanager.core.business.alibaba.param;

public class AlibabaTradeFastCargo {

    private Long offerId;

    /**
     * @return 商品对应的offer id
     */
    public Long getOfferId() {
        return offerId;
    }

    /**
     *     商品对应的offer id     *
     * 参数示例：<pre>554456348334</pre>     
     *
     */
    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    private String specId;

    /**
     * @return 商品规格id
     */
    public String getSpecId() {
        return specId;
    }

    /**
     *     商品规格id     *
     * 参数示例：<pre>b266e0726506185beaf205cbae88530d</pre>     
     *
     */
    public void setSpecId(String specId) {
        this.specId = specId;
    }

    private Double quantity;

    /**
     * @return 商品数量(计算金额用)
     */
    public Double getQuantity() {
        return quantity;
    }

    /**
     *     商品数量(计算金额用)     *
     * 参数示例：<pre>5</pre>     
     *
     */
    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

}
