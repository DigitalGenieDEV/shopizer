package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class ComAlibabaCbuOfferModelPriceInfo {

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

    private String consignPrice;

    /**
     * @return 代发价
     */
    public String getConsignPrice() {
        return consignPrice;
    }

    /**
     * 设置代发价     *
     * 参数示例：<pre>10</pre>     
     * 此参数必填
     */
    public void setConsignPrice(String consignPrice) {
        this.consignPrice = consignPrice;
    }

}
