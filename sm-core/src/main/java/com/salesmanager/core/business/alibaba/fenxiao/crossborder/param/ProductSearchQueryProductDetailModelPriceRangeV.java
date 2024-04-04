package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class ProductSearchQueryProductDetailModelPriceRangeV {

    private Integer startQuantity;

    /**
     * @return 起批量
     */
    public Integer getStartQuantity() {
        return startQuantity;
    }

    /**
     * 设置起批量     *
     * 参数示例：<pre>10</pre>     
     * 此参数必填
     */
    public void setStartQuantity(Integer startQuantity) {
        this.startQuantity = startQuantity;
    }

    private String price;

    /**
     * @return 批发价
     */
    public String getPrice() {
        return price;
    }

    /**
     * 设置批发价     *
     * 参数示例：<pre>10</pre>     
     * 此参数必填
     */
    public void setPrice(String price) {
        this.price = price;
    }

}
