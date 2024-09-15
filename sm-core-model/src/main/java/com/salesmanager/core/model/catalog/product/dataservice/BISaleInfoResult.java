//tmpzk
package com.salesmanager.core.model.catalog.product.dataservice;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BISaleInfoResult {

    @JsonProperty("TOTAL_AMT")
    private Double totalAmount;

    @JsonProperty("PRODUCT_AMT")
    private Double productPriceAmount;

    @JsonProperty("ADD_SERV_AMT")
    private Double additionalServiceFeeAmount;

    @JsonProperty("SHIPPING_AMT")
    private Double shippingFeeAmount;

    @JsonProperty("HANDLING_AMT")
    private Double handlingFeeAmount;

    @JsonProperty("PRODUCT_QUANTITY")
    private Integer productQuantity;

    @JsonProperty("PAY_ORDER_NUM")
    private Integer paidOrderNum;

    @JsonProperty("REFUND_AMT")
    private Double refundAmount;

    @JsonProperty("CANCEL_ORDER_NUM")
    private Integer cancelOrderNum;

    @JsonProperty("RETURN_GOODS_ORDER_NUM")
    private Integer returnGoodsOrderNum;

    @JsonProperty("REFUND_RETURN_RATIO")
    private Double refundAndReturnRatio;


    public Double getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getProductPriceAmount() {
        return productPriceAmount;
    }
    public void setProductPriceAmount(Double productPriceAmount) {
        this.productPriceAmount = productPriceAmount;
    }

    public Double getAdditionalServiceFeeAmount() {
        return additionalServiceFeeAmount;
    }
    public void setAdditionalServiceFeeAmount(Double additionalServiceFeeAmount) {
        this.additionalServiceFeeAmount = additionalServiceFeeAmount;
    }

    public Double getShippingFeeAmount() {
        return shippingFeeAmount;
    }
    public void setShippingFeeAmount(Double shippingFeeAmount) {
        this.shippingFeeAmount = shippingFeeAmount;
    }

    public Double getHandlingFeeAmount() {
        return handlingFeeAmount;
    }
    public void setHandlingFeeAmount(Double handlingFeeAmount) {
        this.handlingFeeAmount = handlingFeeAmount;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }
    public void setProductQuantity(Integer productQuantity) { this.productQuantity = productQuantity; }

    public Integer getPaidOrderNum() {
        return paidOrderNum;
    }
    public void setPaidOrderNum(Integer paidOrderNum) { this.paidOrderNum = paidOrderNum; }

    public Double getRefundAmount() {
        return refundAmount;
    }
    public void setRefundAmount(Double refundAmount) { this.refundAmount = refundAmount; }

    public Integer getCancelOrderNum() {
        return cancelOrderNum;
    }
    public void setCancelOrderNum(Integer cancelOrderNum) { this.cancelOrderNum = cancelOrderNum; }

    public Integer getReturnGoodsOrderNum() {
        return returnGoodsOrderNum;
    }
    public void setReturnGoodsOrderNum(Integer returnGoodsOrderNum) { this.returnGoodsOrderNum = returnGoodsOrderNum; }

    public Double getRefundAndReturnRatio() {
        return refundAndReturnRatio;
    }
    public void setRefundAndReturnRatio(Double refundAndReturnRatio) { this.refundAndReturnRatio = refundAndReturnRatio; }
}
