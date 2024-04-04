package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class ProductSearchKeywordQueryModelProductInfoModelV {

    private String imageUrl;

    /**
     * @return 图片地址
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * 设置图片地址     *
     * 参数示例：<pre>图片地址</pre>     
     * 此参数必填
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private String subject;

    /**
     * @return 中文标题
     */
    public String getSubject() {
        return subject;
    }

    /**
     * 设置中文标题     *
     * 参数示例：<pre>中文标题</pre>     
     * 此参数必填
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    private String subjectTrans;

    /**
     * @return 外文标题
     */
    public String getSubjectTrans() {
        return subjectTrans;
    }

    /**
     * 设置外文标题     *
     * 参数示例：<pre>外文标题</pre>     
     * 此参数必填
     */
    public void setSubjectTrans(String subjectTrans) {
        this.subjectTrans = subjectTrans;
    }

    private Long offerId;

    /**
     * @return 商品id
     */
    public Long getOfferId() {
        return offerId;
    }

    /**
     * 设置商品id     *
     * 参数示例：<pre>2</pre>     
     * 此参数必填
     */
    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    private Boolean isJxhy;

    /**
     * @return 是否精选货源
     */
    public Boolean getIsJxhy() {
        return isJxhy;
    }

    /**
     * 设置是否精选货源     *
     * 参数示例：<pre>true</pre>     
     * 此参数必填
     */
    public void setIsJxhy(Boolean isJxhy) {
        this.isJxhy = isJxhy;
    }

    private ProductSearchKeywordQueryModelPriceInfoV priceInfo;

    /**
     * @return 价格
     */
    public ProductSearchKeywordQueryModelPriceInfoV getPriceInfo() {
        return priceInfo;
    }

    /**
     * 设置价格     *
     * 参数示例：<pre>1</pre>     
     * 此参数必填
     */
    public void setPriceInfo(ProductSearchKeywordQueryModelPriceInfoV priceInfo) {
        this.priceInfo = priceInfo;
    }

    private String repurchaseRate;

    /**
     * @return 复购率
     */
    public String getRepurchaseRate() {
        return repurchaseRate;
    }

    /**
     * 设置复购率     *
     * 参数示例：<pre>10%</pre>     
     * 此参数必填
     */
    public void setRepurchaseRate(String repurchaseRate) {
        this.repurchaseRate = repurchaseRate;
    }

    private Integer monthSold;

    /**
     * @return 30天销量
     */
    public Integer getMonthSold() {
        return monthSold;
    }

    /**
     * 设置30天销量     *
     * 参数示例：<pre>1213</pre>     
     * 此参数必填
     */
    public void setMonthSold(Integer monthSold) {
        this.monthSold = monthSold;
    }

    private String traceInfo;

    /**
     * @return 向1688上报打点数据
     */
    public String getTraceInfo() {
        return traceInfo;
    }

    /**
     * 设置向1688上报打点数据     *
     * 参数示例：<pre>object_id@620201390233^object_type@offer</pre>     
     * 此参数必填
     */
    public void setTraceInfo(String traceInfo) {
        this.traceInfo = traceInfo;
    }

    private Boolean isOnePsale;

    /**
     * @return 是否一件代发
     */
    public Boolean getIsOnePsale() {
        return isOnePsale;
    }

    /**
     * 设置是否一件代发     *
     * 参数示例：<pre>true</pre>     
     * 此参数必填
     */
    public void setIsOnePsale(Boolean isOnePsale) {
        this.isOnePsale = isOnePsale;
    }

    private String[] sellerIdentities;

    /**
     * @return 商家身份
     */
    public String[] getSellerIdentities() {
        return sellerIdentities;
    }

    /**
     * 设置商家身份     *
     * 参数示例：<pre>super_factory-超级工厂 powerful_merchants-实力商家 tp_member-诚信通会员</pre>     
     * 此参数必填
     */
    public void setSellerIdentities(String[] sellerIdentities) {
        this.sellerIdentities = sellerIdentities;
    }

}
