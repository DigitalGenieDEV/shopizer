package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class ProductSearchQueryProductDetailModelProductDetailModel {

    private Long offerId;

    /**
     * @return 
     */
    public Long getOfferId() {
        return offerId;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    private Long categoryId;

    /**
     * @return 
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    private String categoryName;

    /**
     * @return 
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    private String subject;

    /**
     * @return 
     */
    public String getSubject() {
        return subject;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    private String subjectTrans;

    /**
     * @return 
     */
    public String getSubjectTrans() {
        return subjectTrans;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setSubjectTrans(String subjectTrans) {
        this.subjectTrans = subjectTrans;
    }

    private String description;

    /**
     * @return 
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setDescription(String description) {
        this.description = description;
    }

    private String mainVideo;

    /**
     * @return 
     */
    public String getMainVideo() {
        return mainVideo;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setMainVideo(String mainVideo) {
        this.mainVideo = mainVideo;
    }

    private String detailVideo;

    /**
     * @return 
     */
    public String getDetailVideo() {
        return detailVideo;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setDetailVideo(String detailVideo) {
        this.detailVideo = detailVideo;
    }

    private ProductSearchQueryProductDetailModelProductImage productImage;

    /**
     * @return 
     */
    public ProductSearchQueryProductDetailModelProductImage getProductImage() {
        return productImage;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setProductImage(ProductSearchQueryProductDetailModelProductImage productImage) {
        this.productImage = productImage;
    }

    private ProductSearchQueryProductDetailModelProductAttribute[] productAttribute;

    /**
     * @return 
     */
    public ProductSearchQueryProductDetailModelProductAttribute[] getProductAttribute() {
        return productAttribute;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setProductAttribute(ProductSearchQueryProductDetailModelProductAttribute[] productAttribute) {
        this.productAttribute = productAttribute;
    }

    private ProductSearchQueryProductDetailModelSkuInfo[] productSkuInfos;

    /**
     * @return 
     */
    public ProductSearchQueryProductDetailModelSkuInfo[] getProductSkuInfos() {
        return productSkuInfos;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setProductSkuInfos(ProductSearchQueryProductDetailModelSkuInfo[] productSkuInfos) {
        this.productSkuInfos = productSkuInfos;
    }

    private ProductSearchQueryProductDetailModelProductSaleInfo productSaleInfo;

    /**
     * @return 
     */
    public ProductSearchQueryProductDetailModelProductSaleInfo getProductSaleInfo() {
        return productSaleInfo;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setProductSaleInfo(ProductSearchQueryProductDetailModelProductSaleInfo productSaleInfo) {
        this.productSaleInfo = productSaleInfo;
    }

    private ProductSearchQueryProductDetailModelProductShippingInfo productShippingInfo;

    /**
     * @return 
     */
    public ProductSearchQueryProductDetailModelProductShippingInfo getProductShippingInfo() {
        return productShippingInfo;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setProductShippingInfo(ProductSearchQueryProductDetailModelProductShippingInfo productShippingInfo) {
        this.productShippingInfo = productShippingInfo;
    }

    private Boolean isJxhy;

    /**
     * @return 
     */
    public Boolean getIsJxhy() {
        return isJxhy;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setIsJxhy(Boolean isJxhy) {
        this.isJxhy = isJxhy;
    }

    private String sellerOpenId;

    /**
     * @return 
     */
    public String getSellerOpenId() {
        return sellerOpenId;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setSellerOpenId(String sellerOpenId) {
        this.sellerOpenId = sellerOpenId;
    }

    private Integer minOrderQuantity;

    /**
     * @return 
     */
    public Integer getMinOrderQuantity() {
        return minOrderQuantity;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setMinOrderQuantity(Integer minOrderQuantity) {
        this.minOrderQuantity = minOrderQuantity;
    }

    private Integer batchNumber;

    /**
     * @return 一手数量
     */
    public Integer getBatchNumber() {
        return batchNumber;
    }

    /**
     * 设置一手数量     *
     * 参数示例：<pre>200</pre>     
     * 此参数必填
     */
    public void setBatchNumber(Integer batchNumber) {
        this.batchNumber = batchNumber;
    }

    private String status;

    /**
     * @return 商品状态。published:上网状态;member expired:会员撤销;auto expired:自然过期;expired:过期(包含手动过期与自动过期);member deleted:会员删除;modified:修改;new:新发;deleted:删除;TBD:to be delete;approved:审批通过;auditing:审核中;untread:审核不通过;
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置商品状态。published:上网状态;member expired:会员撤销;auto expired:自然过期;expired:过期(包含手动过期与自动过期);member deleted:会员删除;modified:修改;new:新发;deleted:删除;TBD:to be delete;approved:审批通过;auditing:审核中;untread:审核不通过;     *
     * 参数示例：<pre>published</pre>     
     * 此参数必填
     */
    public void setStatus(String status) {
        this.status = status;
    }

    private ComAlibabaCbuOfferModelOutProductTagInfo[] tagInfoList;

    /**
     * @return 商品服务标签
     */
    public ComAlibabaCbuOfferModelOutProductTagInfo[] getTagInfoList() {
        return tagInfoList;
    }

    /**
     * 设置商品服务标签     *
     * 参数示例：<pre>1</pre>     
     * 此参数必填
     */
    public void setTagInfoList(ComAlibabaCbuOfferModelOutProductTagInfo[] tagInfoList) {
        this.tagInfoList = tagInfoList;
    }

    private String traceInfo;

    /**
     * @return 打点信息，用于向1688上报打点数据
     */
    public String getTraceInfo() {
        return traceInfo;
    }

    /**
     * 设置打点信息，用于向1688上报打点数据     *
     * 参数示例：<pre>object_id@620201390233^object_type@offer</pre>     
     * 此参数必填
     */
    public void setTraceInfo(String traceInfo) {
        this.traceInfo = traceInfo;
    }

    private ProductSearchQueryProductDetailModelSellerMixSetting sellerMixSetting;

    /**
     * @return 卖家混批配置
     */
    public ProductSearchQueryProductDetailModelSellerMixSetting getSellerMixSetting() {
        return sellerMixSetting;
    }

    /**
     * 设置卖家混批配置     *
     * 参数示例：<pre>1</pre>     
     * 此参数必填
     */
    public void setSellerMixSetting(ProductSearchQueryProductDetailModelSellerMixSetting sellerMixSetting) {
        this.sellerMixSetting = sellerMixSetting;
    }

    private String productCargoNumber;

    /**
     * @return 商品货号
     */
    public String getProductCargoNumber() {
        return productCargoNumber;
    }

    /**
     * 设置商品货号     *
     * 参数示例：<pre>1</pre>     
     * 此参数必填
     */
    public void setProductCargoNumber(String productCargoNumber) {
        this.productCargoNumber = productCargoNumber;
    }

    private ProductSearchQueryProductDetailModelSellerDataInfo sellerDataInfo;

    /**
     * @return 商家属性数据
     */
    public ProductSearchQueryProductDetailModelSellerDataInfo getSellerDataInfo() {
        return sellerDataInfo;
    }

    /**
     * 设置商家属性数据     *
     * 参数示例：<pre>{}</pre>     
     * 此参数必填
     */
    public void setSellerDataInfo(ProductSearchQueryProductDetailModelSellerDataInfo sellerDataInfo) {
        this.sellerDataInfo = sellerDataInfo;
    }

    private String soldOut;

    /**
     * @return 商品销量
     */
    public String getSoldOut() {
        return soldOut;
    }

    /**
     * 设置商品销量     *
     * 参数示例：<pre>12342</pre>     
     * 此参数必填
     */
    public void setSoldOut(String soldOut) {
        this.soldOut = soldOut;
    }

    private ProductSearchQueryProductDetailModelChannelPrice channelPrice;

    /**
     * @return 渠道价格数据
     */
    public ProductSearchQueryProductDetailModelChannelPrice getChannelPrice() {
        return channelPrice;
    }

    /**
     * 设置渠道价格数据     *
     * 参数示例：<pre>如下</pre>     
     * 此参数必填
     */
    public void setChannelPrice(ProductSearchQueryProductDetailModelChannelPrice channelPrice) {
        this.channelPrice = channelPrice;
    }

}
