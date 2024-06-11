package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class ProductSearchKeywordQueryModelProductInfoModelV {

    private Long topCategoryId;

    private Long secondCategoryId;

    private Long thirdCategoryId;


    private String imageUrl;

    /**
     * @return 图片地址
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     *     图片地址     *
     * 参数示例：<pre>图片地址</pre>     
     *    
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
     *     中文标题     *
     * 参数示例：<pre>中文标题</pre>     
     *    
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
     *     外文标题     *
     * 参数示例：<pre>外文标题</pre>     
     *    
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
     *     商品id     *
     * 参数示例：<pre>2</pre>     
     *    
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
     *     是否精选货源     *
     * 参数示例：<pre>true</pre>     
     *    
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
     *     价格     *
     * 参数示例：<pre>1</pre>     
     *    
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
     *     复购率     *
     * 参数示例：<pre>10%</pre>     
     *    
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
     *     30天销量     *
     * 参数示例：<pre>1213</pre>     
     *    
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
     *     向1688上报打点数据     *
     * 参数示例：<pre>object_id@620201390233^object_type@offer</pre>     
     *    
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
     *     是否一件代发     *
     * 参数示例：<pre>true</pre>     
     *    
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
     *     商家身份     *
     * 参数示例：<pre>super_factory-超级工厂 powerful_merchants-实力商家 tp_member-诚信通会员</pre>     
     *    
     */
    public void setSellerIdentities(String[] sellerIdentities) {
        this.sellerIdentities = sellerIdentities;
    }

    public Long getTopCategoryId() {
        return topCategoryId;
    }

    public void setTopCategoryId(Long topCategoryId) {
        this.topCategoryId = topCategoryId;
    }

    public Long getSecondCategoryId() {
        return secondCategoryId;
    }

    public void setSecondCategoryId(Long secondCategoryId) {
        this.secondCategoryId = secondCategoryId;
    }

    public Long getThirdCategoryId() {
        return thirdCategoryId;
    }

    public void setThirdCategoryId(Long thirdCategoryId) {
        this.thirdCategoryId = thirdCategoryId;
    }
}
