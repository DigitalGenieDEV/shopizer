package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class ComAlibabaCbuOfferModelOutProductTagInfo {

    private String key;

    /**
     * @return 服务名，isOnePsale-一件代发
     */
    public String getKey() {
        return key;
    }

    /**
     * 设置服务名，isOnePsale-一件代发     *
     * 参数示例：<pre>isOnePsale</pre>     
     * 此参数必填
     */
    public void setKey(String key) {
        this.key = key;
    }

    private Boolean value;

    /**
     * @return 是否开通
     */
    public Boolean getValue() {
        return value;
    }

    /**
     * 设置是否开通     *
     * 参数示例：<pre>true</pre>     
     * 此参数必填
     */
    public void setValue(Boolean value) {
        this.value = value;
    }

}
