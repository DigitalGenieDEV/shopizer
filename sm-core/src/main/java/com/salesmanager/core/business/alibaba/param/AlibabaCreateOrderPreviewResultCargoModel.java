package com.salesmanager.core.business.alibaba.param;

public class AlibabaCreateOrderPreviewResultCargoModel {

    private Double amount;

    /**
     * @return 产品总金额
     */
    public Double getAmount() {
        return amount;
    }

    /**
     *     产品总金额     *
     *        
     *    
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    private String message;

    /**
     * @return 返回信息
     */
    public String getMessage() {
        return message;
    }

    /**
     *     返回信息     *
     *        
     *    
     */
    public void setMessage(String message) {
        this.message = message;
    }

    private Double finalUnitPrice;

    /**
     * @return 最终单价
     */
    public Double getFinalUnitPrice() {
        return finalUnitPrice;
    }

    /**
     *     最终单价     *
     *        
     *    
     */
    public void setFinalUnitPrice(Double finalUnitPrice) {
        this.finalUnitPrice = finalUnitPrice;
    }

    private String specId;

    /**
     * @return 规格ID，offer内唯一
     */
    public String getSpecId() {
        return specId;
    }

    /**
     *     规格ID，offer内唯一     *
     *        
     *    
     */
    public void setSpecId(String specId) {
        this.specId = specId;
    }

    private Long skuId;

    /**
     * @return 规格ID，全局唯一
     */
    public Long getSkuId() {
        return skuId;
    }

    /**
     *     规格ID，全局唯一     *
     *        
     *    
     */
    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    private String resultCode;

    /**
     * @return 返回码
     */
    public String getResultCode() {
        return resultCode;
    }

    /**
     *     返回码     *
     *        
     *    
     */
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    private Long offerId;

    /**
     * @return 商品ID
     */
    public Long getOfferId() {
        return offerId;
    }

    /**
     *     商品ID     *
     *        
     *    
     */
    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    private AlibabaTradePromotionModel[] cargoPromotionList;

    /**
     * @return 商品优惠列表
     */
    public AlibabaTradePromotionModel[] getCargoPromotionList() {
        return cargoPromotionList;
    }

    /**
     *     商品优惠列表     *
     *        
     *    
     */
    public void setCargoPromotionList(AlibabaTradePromotionModel[] cargoPromotionList) {
        this.cargoPromotionList = cargoPromotionList;
    }

}
