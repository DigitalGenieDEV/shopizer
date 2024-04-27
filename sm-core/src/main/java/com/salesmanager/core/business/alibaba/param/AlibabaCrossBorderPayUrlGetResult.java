package com.salesmanager.core.business.alibaba.param;

public class AlibabaCrossBorderPayUrlGetResult {

    private String success;

    /**
     * @return 是否成功
     */
    public String getSuccess() {
        return success;
    }

    /**
     *     是否成功     *
          
     *    
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
     *     错误码     *
          
     *    
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
     *     错误描述     *
          
     *    
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
     *     收银台支付链接     *
          
     *    
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
     *     由于额度及风控原因不能批量支付的订单列表     *
          
     *    
     */
    public void setCantPayOrderList(long[] cantPayOrderList) {
        this.cantPayOrderList = cantPayOrderList;
    }

}
