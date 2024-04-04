package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class ProductSearchImageQueryModelPriceInfoV {

    private String price;

    /**
     * @return 批发价
     */
    public String getPrice() {
        return price;
    }

    /**
     * 设置批发价     *
     * 参数示例：<pre>1</pre>     
     * 此参数必填
     */
    public void setPrice(String price) {
        this.price = price;
    }

    private String jxhyPrice;

    /**
     * @return 代发精选货源价
     */
    public String getJxhyPrice() {
        return jxhyPrice;
    }

    /**
     * 设置代发精选货源价     *
     * 参数示例：<pre>1</pre>     
     * 此参数必填
     */
    public void setJxhyPrice(String jxhyPrice) {
        this.jxhyPrice = jxhyPrice;
    }

    private String pfJxhyPrice;

    /**
     * @return 批发精选货源价
     */
    public String getPfJxhyPrice() {
        return pfJxhyPrice;
    }

    /**
     * 设置批发精选货源价     *
     * 参数示例：<pre>1</pre>     
     * 此参数必填
     */
    public void setPfJxhyPrice(String pfJxhyPrice) {
        this.pfJxhyPrice = pfJxhyPrice;
    }

    private String consignPrice;

    /**
     * @return 一件代发价，当isOnePsale=true表示是一件发代发
     */
    public String getConsignPrice() {
        return consignPrice;
    }

    /**
     * 设置一件代发价，当isOnePsale=true表示是一件发代发     *
     * 参数示例：<pre>1</pre>     
     * 此参数必填
     */
    public void setConsignPrice(String consignPrice) {
        this.consignPrice = consignPrice;
    }

}
