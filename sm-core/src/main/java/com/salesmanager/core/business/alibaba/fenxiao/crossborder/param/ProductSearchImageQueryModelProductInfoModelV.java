package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class ProductSearchImageQueryModelProductInfoModelV {

    private String imageUrl;

    /**
     * @return 图片url
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * 设置图片url     *
     * 参数示例：<pre>图片url</pre>     
     * 此参数必填
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private String subject;

    /**
     * @return 标题
     */
    public String getSubject() {
        return subject;
    }

    /**
     * 设置标题     *
     * 参数示例：<pre>标题</pre>     
     * 此参数必填
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    private String subjectTrans;

    /**
     * @return 多语言标题
     */
    public String getSubjectTrans() {
        return subjectTrans;
    }

    /**
     * 设置多语言标题     *
     * 参数示例：<pre>多语言标题</pre>     
     * 此参数必填
     */
    public void setSubjectTrans(String subjectTrans) {
        this.subjectTrans = subjectTrans;
    }

    private ProductSearchImageQueryModelPriceInfoV priceInfo;

    /**
     * @return 价格
     */
    public ProductSearchImageQueryModelPriceInfoV getPriceInfo() {
        return priceInfo;
    }

    /**
     * 设置价格     *
     * 参数示例：<pre>价格</pre>     
     * 此参数必填
     */
    public void setPriceInfo(ProductSearchImageQueryModelPriceInfoV priceInfo) {
        this.priceInfo = priceInfo;
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
     * 参数示例：<pre>商品id</pre>     
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
     * 参数示例：<pre>是否精选货源</pre>     
     * 此参数必填
     */
    public void setIsJxhy(Boolean isJxhy) {
        this.isJxhy = isJxhy;
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
     * 参数示例：<pre>13%</pre>     
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
     * 参数示例：<pre>1234</pre>     
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
