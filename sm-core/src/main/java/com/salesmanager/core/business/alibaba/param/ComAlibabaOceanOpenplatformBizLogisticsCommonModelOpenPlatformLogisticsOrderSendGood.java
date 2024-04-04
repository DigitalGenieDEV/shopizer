package com.salesmanager.core.business.alibaba.param;

public class ComAlibabaOceanOpenplatformBizLogisticsCommonModelOpenPlatformLogisticsOrderSendGood {

    private Long logisticsOrderId;

    /**
     * @return 物流订单号
     */
    public Long getLogisticsOrderId() {
        return logisticsOrderId;
    }

    /**
     * 设置物流订单号     *
     * 参数示例：<pre>189435013090051</pre>     
     * 此参数必填
     */
    public void setLogisticsOrderId(Long logisticsOrderId) {
        this.logisticsOrderId = logisticsOrderId;
    }

    private String logisticsId;

    /**
     * @return 物流编号
     */
    public String getLogisticsId() {
        return logisticsId;
    }

    /**
     * 设置物流编号     *
     * 参数示例：<pre>LP00616288919385</pre>     
     * 此参数必填
     */
    public void setLogisticsId(String logisticsId) {
        this.logisticsId = logisticsId;
    }

    private Long tradeOrderId;

    /**
     * @return 交易主订单号
     */
    public Long getTradeOrderId() {
        return tradeOrderId;
    }

    /**
     * 设置交易主订单号     *
     * 参数示例：<pre>3657499272179232333</pre>     
     * 此参数必填
     */
    public void setTradeOrderId(Long tradeOrderId) {
        this.tradeOrderId = tradeOrderId;
    }

    private Long tradeOrderItemId;

    /**
     * @return 交易子订单号
     */
    public Long getTradeOrderItemId() {
        return tradeOrderItemId;
    }

    /**
     * 设置交易子订单号     *
     * 参数示例：<pre>3657499272179232333</pre>     
     * 此参数必填
     */
    public void setTradeOrderItemId(Long tradeOrderItemId) {
        this.tradeOrderItemId = tradeOrderItemId;
    }

    private String description;

    /**
     * @return sku描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置sku描述     *
     * 参数示例：<pre>颜色: 黑色; 尺码: M;</pre>     
     * 此参数必填
     */
    public void setDescription(String description) {
        this.description = description;
    }

    private Double quantity;

    /**
     * @return 数量
     */
    public Double getQuantity() {
        return quantity;
    }

    /**
     * 设置数量     *
     * 参数示例：<pre>1</pre>     
     * 此参数必填
     */
    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    private String unit;

    /**
     * @return 单位
     */
    public String getUnit() {
        return unit;
    }

    /**
     * 设置单位     *
     * 参数示例：<pre>件</pre>     
     * 此参数必填
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    private String name;

    /**
     * @return 商品名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置商品名称     *
     * 参数示例：<pre>短袖t恤女2023夏季新款韩版宽松字母圆领上衣半袖体恤打底衫女潮</pre>     
     * 此参数必填
     */
    public void setName(String name) {
        this.name = name;
    }

    private String productName;

    /**
     * @return 商品名称
     */
    public String getProductName() {
        return productName;
    }

    /**
     * 设置商品名称     *
     * 参数示例：<pre>品名</pre>     
     * 此参数必填
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

}
