package com.salesmanager.core.business.alibaba.param;

public class AlibabaCreditPayUrlGetResult {

    private String success;

    /**
     * @return 是否成功
     */
    public String getSuccess() {
        return success;
    }

    /**
     * 设置是否成功     *
          
     * 此参数必填
     */
    public void setSuccess(String success) {
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
     * 设置错误码     *
          
     * 此参数必填
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    private String errorMsg;

    /**
     * @return 错误描述
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * 设置错误描述     *
          
     * 此参数必填
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    private String payUrl;

    /**
     * @return 收银台支付链接
     */
    public String getPayUrl() {
        return payUrl;
    }

    /**
     * 设置收银台支付链接     *
          
     * 此参数必填
     */
    public void setPayUrl(String payUrl) {
        this.payUrl = payUrl;
    }

    private long[] cantPayOrderList;

    /**
     * @return 由于额度及风控原因不能批量支付的订单列表
     */
    public long[] getCantPayOrderList() {
        return cantPayOrderList;
    }

    /**
     * 设置由于额度及风控原因不能批量支付的订单列表     *
          
     * 此参数必填
     */
    public void setCantPayOrderList(long[] cantPayOrderList) {
        this.cantPayOrderList = cantPayOrderList;
    }

}
