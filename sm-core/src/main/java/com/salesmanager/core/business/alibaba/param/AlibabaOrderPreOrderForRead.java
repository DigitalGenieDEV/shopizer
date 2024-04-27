package com.salesmanager.core.business.alibaba.param;

public class AlibabaOrderPreOrderForRead {

    private String appkey;

    /**
     * @return 创建预订单的appkey
     */
    public String getAppkey() {
        return appkey;
    }

    /**
     *     创建预订单的appkey     *
     * 参数示例：<pre>12345</pre>     
     *
     */
    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    private String marketName;

    /**
     * @return 创建预订单时传入的市场名
     */
    public String getMarketName() {
        return marketName;
    }

    /**
     *     创建预订单时传入的市场名     *
     * 参数示例：<pre>dxc</pre>     
     *
     */
    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    private Boolean createPreOrderApp;

    /**
     * @return 预订单是否为当前查询的通过当前查询的ERP创建
     */
    public Boolean getCreatePreOrderApp() {
        return createPreOrderApp;
    }

    /**
     *     预订单是否为当前查询的通过当前查询的ERP创建     *
     * 参数示例：<pre>false</pre>     
     *
     */
    public void setCreatePreOrderApp(Boolean createPreOrderApp) {
        this.createPreOrderApp = createPreOrderApp;
    }

}
