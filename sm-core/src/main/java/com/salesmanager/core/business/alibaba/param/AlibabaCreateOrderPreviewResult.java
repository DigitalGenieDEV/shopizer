package com.salesmanager.core.business.alibaba.param;

public class AlibabaCreateOrderPreviewResult {

    private AlibabaCreateOrderPreviewResultModel[] orderPreviewResuslt;

    /**
     * @return 订单预览结果，过自动拆单会返回多个记录
     */
    public AlibabaCreateOrderPreviewResultModel[] getOrderPreviewResuslt() {
        return orderPreviewResuslt;
    }

    /**
     *     订单预览结果，过自动拆单会返回多个记录     *
          
     *
     */
    public void setOrderPreviewResuslt(AlibabaCreateOrderPreviewResultModel[] orderPreviewResuslt) {
        this.orderPreviewResuslt = orderPreviewResuslt;
    }

    private Boolean success;

    /**
     * @return 是否成功
     */
    public Boolean getSuccess() {
        return success;
    }

    /**
     *     是否成功     *
          
     *
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    private String errorCode;

    /**
     * @return 错误码
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     *     错误码     *
          
     *
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    private String errorMsg;

    /**
     * @return 错误信息
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     *     错误信息     *
          
     *
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    private long[] postFeeByDescOfferList;

    /**
     * @return 运费说明的商品列表
     */
    public long[] getPostFeeByDescOfferList() {
        return postFeeByDescOfferList;
    }

    /**
     *     运费说明的商品列表     *
          
     *
     */
    public void setPostFeeByDescOfferList(long[] postFeeByDescOfferList) {
        this.postFeeByDescOfferList = postFeeByDescOfferList;
    }

    private long[] consignOfferList;

    /**
     * @return 代销商品列表
     */
    public long[] getConsignOfferList() {
        return consignOfferList;
    }

    /**
     *     代销商品列表     *
          
     *
     */
    public void setConsignOfferList(long[] consignOfferList) {
        this.consignOfferList = consignOfferList;
    }

    private long[] unsupportedCrossBorderPayOfferList;

    /**
     * @return 不支持跨境宝支付的商品列表
     */
    public long[] getUnsupportedCrossBorderPayOfferList() {
        return unsupportedCrossBorderPayOfferList;
    }

    /**
     *     不支持跨境宝支付的商品列表     *
          
     *
     */
    public void setUnsupportedCrossBorderPayOfferList(long[] unsupportedCrossBorderPayOfferList) {
        this.unsupportedCrossBorderPayOfferList = unsupportedCrossBorderPayOfferList;
    }

}
