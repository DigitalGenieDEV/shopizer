package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class ProductSearchQueryProductDetailModelProductDetailModel {

    private Long topCategoryId;

    private Long secondCategoryId;

    private Long thirdCategoryId;


    private Long offerId;

    /**
     * @return 
     */
    public Long getOfferId() {
        return offerId;
    }

    /**
     */
    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    /**
     */
    private Integer monthSold;

    public Integer getMonthSold() {
        return monthSold;
    }

    public void setMonthSold(Integer monthSold) {
        this.monthSold = monthSold;
    }

    private Long categoryId;

    /**
     * @return 
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
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
     *          *
     *        
     *
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
     *          *
     *        
     *
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
     *          *
     *        
     *
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
     *          *
     *
     *    
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
     *          *
     *        
     *
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
     *          *
     *        
     *
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
     *          *
     *        
     *
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
     *          *
     *        
     *
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
     *          *
     *        
     *
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
     *          *
     *        
     *
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
     *          *
     *        
     *
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
     *        
     *
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
     *        
     *
     */
    public void setMinOrderQuantity(Integer minOrderQuantity) {
        this.minOrderQuantity = minOrderQuantity;
    }

    private Integer batchNumber;

    /**
     */
    public Integer getBatchNumber() {
        return batchNumber;
    }

    /**
     *
     */
    public void setBatchNumber(Integer batchNumber) {
        this.batchNumber = batchNumber;
    }

    private String status;

    /**
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     */
    public void setStatus(String status) {
        this.status = status;
    }

    private ComAlibabaCbuOfferModelOutProductTagInfo[] tagInfoList;

    /**
     */
    public ComAlibabaCbuOfferModelOutProductTagInfo[] getTagInfoList() {
        return tagInfoList;
    }

    /**
     *
     */
    public void setTagInfoList(ComAlibabaCbuOfferModelOutProductTagInfo[] tagInfoList) {
        this.tagInfoList = tagInfoList;
    }

    private String traceInfo;

    /**
     */
    public String getTraceInfo() {
        return traceInfo;
    }

    /**
     *
     */
    public void setTraceInfo(String traceInfo) {
        this.traceInfo = traceInfo;
    }

    private ProductSearchQueryProductDetailModelSellerMixSetting sellerMixSetting;

    /**
     */
    public ProductSearchQueryProductDetailModelSellerMixSetting getSellerMixSetting() {
        return sellerMixSetting;
    }

    /**
     *
     */
    public void setSellerMixSetting(ProductSearchQueryProductDetailModelSellerMixSetting sellerMixSetting) {
        this.sellerMixSetting = sellerMixSetting;
    }

    private String productCargoNumber;

    /**
     */
    public String getProductCargoNumber() {
        return productCargoNumber;
    }

    /**
     *
     */
    public void setProductCargoNumber(String productCargoNumber) {
        this.productCargoNumber = productCargoNumber;
    }

    private ProductSearchQueryProductDetailModelSellerDataInfo sellerDataInfo;

    /**
     */
    public ProductSearchQueryProductDetailModelSellerDataInfo getSellerDataInfo() {
        return sellerDataInfo;
    }

    /**
     *
     */
    public void setSellerDataInfo(ProductSearchQueryProductDetailModelSellerDataInfo sellerDataInfo) {
        this.sellerDataInfo = sellerDataInfo;
    }

    private String soldOut;

    /**
     */
    public String getSoldOut() {
        return soldOut;
    }

    /**
     *
     */
    public void setSoldOut(String soldOut) {
        this.soldOut = soldOut;
    }

    private ProductSearchQueryProductDetailModelChannelPrice channelPrice;

    /**
     */
    public ProductSearchQueryProductDetailModelChannelPrice getChannelPrice() {
        return channelPrice;
    }

    /**
     *
     */
    public void setChannelPrice(ProductSearchQueryProductDetailModelChannelPrice channelPrice) {
        this.channelPrice = channelPrice;
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
