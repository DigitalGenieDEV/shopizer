package com.salesmanager.core.business.alibaba.param;

public class AlibabaCreditOrderForDetail {

    private Long payAmount;

    /**
     * @return 订单金额
     */
    public Long getPayAmount() {
        return payAmount;
    }

    /**
     *     订单金额     *
     * 参数示例：<pre>10</pre>     
     *    
     */
    public void setPayAmount(Long payAmount) {
        this.payAmount = payAmount;
    }

    private String createTime;

    /**
     * @return 支付时间
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     *     支付时间     *
     * 参数示例：<pre>2018-01-01 00:00:00</pre>     
     *    
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    private String status;

    /**
     * @return 状态
     */
    public String getStatus() {
        return status;
    }

    /**
     *     状态     *
     * 参数示例：<pre>END</pre>     
     *    
     */
    public void setStatus(String status) {
        this.status = status;
    }

    private String gracePeriodEndTime;

    /**
     * @return 最晚还款时间
     */
    public String getGracePeriodEndTime() {
        return gracePeriodEndTime;
    }

    /**
     *     最晚还款时间     *
     * 参数示例：<pre>2018-01-01 00:00:00</pre>     
     *    
     */
    public void setGracePeriodEndTime(String gracePeriodEndTime) {
        this.gracePeriodEndTime = gracePeriodEndTime;
    }

    private String statusStr;

    /**
     * @return 状态描述
     */
    public String getStatusStr() {
        return statusStr;
    }

    /**
     *     状态描述     *
     * 参数示例：<pre>已完结</pre>     
     *    
     */
    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    private Long restRepayAmount;

    /**
     * @return 应还金额
     */
    public Long getRestRepayAmount() {
        return restRepayAmount;
    }

    /**
     *     应还金额     *
     * 参数示例：<pre>11</pre>     
     *    
     */
    public void setRestRepayAmount(Long restRepayAmount) {
        this.restRepayAmount = restRepayAmount;
    }

}
