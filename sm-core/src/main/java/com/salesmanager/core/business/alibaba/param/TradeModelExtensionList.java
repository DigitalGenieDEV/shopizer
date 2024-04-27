package com.salesmanager.core.business.alibaba.param;

public class TradeModelExtensionList {

    private String tradeWay;

    /**
     * @return 交易方式
     */
    public String getTradeWay() {
        return tradeWay;
    }

    /**
     *     交易方式     *
     *    
     *
     */
    public void setTradeWay(String tradeWay) {
        this.tradeWay = tradeWay;
    }

    private String name;

    /**
     * @return 交易方式名称
     */
    public String getName() {
        return name;
    }

    /**
     *     交易方式名称     *
     *    
     *
     */
    public void setName(String name) {
        this.name = name;
    }

    private String tradeType;

    /**
     * @return 开放平台下单时候传入的tradeType
     */
    public String getTradeType() {
        return tradeType;
    }

    /**
     *     开放平台下单时候传入的tradeType     *
     *    
     *
     */
    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    private String description;

    /**
     * @return 交易描述
     */
    public String getDescription() {
        return description;
    }

    /**
     *     交易描述     *
     *    
     *
     */
    public void setDescription(String description) {
        this.description = description;
    }

    private Boolean opSupport;

    /**
     * @return 是否支持
     */
    public Boolean getOpSupport() {
        return opSupport;
    }

    /**
     *     是否支持     *
     *    
     *
     */
    public void setOpSupport(Boolean opSupport) {
        this.opSupport = opSupport;
    }

}
