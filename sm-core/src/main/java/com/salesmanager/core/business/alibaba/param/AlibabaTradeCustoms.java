package com.salesmanager.core.business.alibaba.param;

import java.util.Date;

public class AlibabaTradeCustoms {

    private Long id;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     *     id     *
     * 参数示例：<pre>1</pre>     
     *    
     */
    public void setId(Long id) {
        this.id = id;
    }

    private Date gmtCreate;

    /**
     * @return 创建时间
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     *     创建时间     *
     * 参数示例：<pre>20170806114526000+0800</pre>     
     *    
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    private Date gmtModified;

    /**
     * @return 修改时间
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     *     修改时间     *
     * 参数示例：<pre>20170806114526000+0800</pre>     
     *    
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    private Long buyerId;

    /**
     * @return 买家id
     */
    public Long getBuyerId() {
        return buyerId;
    }

    /**
     *     买家id     *
     * 参数示例：<pre>123456</pre>     
     *    
     */
    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    private String orderId;

    /**
     * @return 主订单id
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     *     主订单id     *
     * 参数示例：<pre>12312312312312</pre>     
     *    
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    private Integer type;

    /**
     * @return 业务数据类型,默认1：报关单
     */
    public Integer getType() {
        return type;
    }

    /**
     *     业务数据类型,默认1：报关单     *
     * 参数示例：<pre>1</pre>     
     *    
     */
    public void setType(Integer type) {
        this.type = type;
    }

    private AlibabaTradeCustomsAttributesInfo[] attributes;

    /**
     * @return 报关信息列表
     */
    public AlibabaTradeCustomsAttributesInfo[] getAttributes() {
        return attributes;
    }

    /**
     *     报关信息列表     *
     *        
     *    
     */
    public void setAttributes(AlibabaTradeCustomsAttributesInfo[] attributes) {
        this.attributes = attributes;
    }

}
