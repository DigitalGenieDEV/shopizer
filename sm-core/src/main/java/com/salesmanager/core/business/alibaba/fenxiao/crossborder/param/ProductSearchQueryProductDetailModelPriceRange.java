package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class ProductSearchQueryProductDetailModelPriceRange {

    private Integer startQuantity;

    /**
     * @return 
     */
    public Integer getStartQuantity() {
        return startQuantity;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setStartQuantity(Integer startQuantity) {
        this.startQuantity = startQuantity;
    }

    private Long price;

    /**
     * @return 
     */
    public Long getPrice() {
        return price;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setPrice(Long price) {
        this.price = price;
    }

}
