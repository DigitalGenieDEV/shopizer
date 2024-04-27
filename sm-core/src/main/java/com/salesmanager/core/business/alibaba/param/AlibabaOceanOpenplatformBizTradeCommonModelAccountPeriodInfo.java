package com.salesmanager.core.business.alibaba.param;

import java.util.Date;

public class AlibabaOceanOpenplatformBizTradeCommonModelAccountPeriodInfo {

    private Long buyerId;

    /**
     * @return 买家userId
     */
    public Long getBuyerId() {
        return buyerId;
    }

    /**
     *     买家userId     *
     * 参数示例：<pre>112312331</pre>     
     *
     */
    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    private String buyerLoginId;

    /**
     * @return 买家loginId
     */
    public String getBuyerLoginId() {
        return buyerLoginId;
    }

    /**
     *     买家loginId     *
     * 参数示例：<pre>alitestforisv01</pre>     
     *
     */
    public void setBuyerLoginId(String buyerLoginId) {
        this.buyerLoginId = buyerLoginId;
    }

    private Date gmtCreate;

    /**
     * @return 创建时间
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     *     创建时间     *
     * 参数示例：<pre>20170913231727000+0800</pre>     
     *
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    private Date gmtModified;

    /**
     * @return 修改时间
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     *     修改时间     *
     * 参数示例：<pre>20170913231727000+0800</pre>     
     *
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    private Date gmtQuota;

    /**
     * @return 授信日期
     */
    public Date getGmtQuota() {
        return gmtQuota;
    }

    /**
     *     授信日期     *
     * 参数示例：<pre>20170913231727000+0800</pre>     
     *
     */
    public void setGmtQuota(Date gmtQuota) {
        this.gmtQuota = gmtQuota;
    }

    private Long quota;

    /**
     * @return 授信额度值,单位为分
     */
    public Long getQuota() {
        return quota;
    }

    /**
     *     授信额度值,单位为分     *
     * 参数示例：<pre>10000</pre>     
     *
     */
    public void setQuota(Long quota) {
        this.quota = quota;
    }

    private Long surplusQuota;

    /**
     * @return 可用授信额度值,单位为分
     */
    public Long getSurplusQuota() {
        return surplusQuota;
    }

    /**
     *     可用授信额度值,单位为分     *
     * 参数示例：<pre>10000</pre>     
     *
     */
    public void setSurplusQuota(Long surplusQuota) {
        this.surplusQuota = surplusQuota;
    }

    private Long sellerId;

    /**
     * @return 卖家userId
     */
    public Long getSellerId() {
        return sellerId;
    }

    /**
     *     卖家userId     *
     * 参数示例：<pre>121312441</pre>     
     *
     */
    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    private String sellerLoginId;

    /**
     * @return 卖家loginId
     */
    public String getSellerLoginId() {
        return sellerLoginId;
    }

    /**
     *     卖家loginId     *
     * 参数示例：<pre>alitestforisv02</pre>     
     *
     */
    public void setSellerLoginId(String sellerLoginId) {
        this.sellerLoginId = sellerLoginId;
    }

    private Integer status;

    /**
     * @return 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     *     状态     *
     * 参数示例：<pre>1</pre>     
     *
     */
    public void setStatus(Integer status) {
        this.status = status;
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
     * 参数示例：<pre>有效</pre>     
     *
     */
    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    private Integer tapDate;

    /**
     * @return 账期日期
     */
    public Integer getTapDate() {
        return tapDate;
    }

    /**
     *     账期日期     *
     * 参数示例：<pre>如每月28日记做：28</pre>     
     *
     */
    public void setTapDate(Integer tapDate) {
        this.tapDate = tapDate;
    }

    private String tapDateStr;

    /**
     * @return 账期日期描述
     */
    public String getTapDateStr() {
        return tapDateStr;
    }

    /**
     *     账期日期描述     *
     * 参数示例：<pre>两个月一结，20号</pre>     
     *
     */
    public void setTapDateStr(String tapDateStr) {
        this.tapDateStr = tapDateStr;
    }

    private Integer tapOverdue;

    /**
     * @return 逾期次数
     */
    public Integer getTapOverdue() {
        return tapOverdue;
    }

    /**
     *     逾期次数     *
     * 参数示例：<pre>0</pre>     
     *
     */
    public void setTapOverdue(Integer tapOverdue) {
        this.tapOverdue = tapOverdue;
    }

    private Integer tapType;

    /**
     * @return 账期类型
     */
    public Integer getTapType() {
        return tapType;
    }

    /**
     *     账期类型     *
     * 参数示例：<pre>1</pre>     
     *
     */
    public void setTapType(Integer tapType) {
        this.tapType = tapType;
    }

}
