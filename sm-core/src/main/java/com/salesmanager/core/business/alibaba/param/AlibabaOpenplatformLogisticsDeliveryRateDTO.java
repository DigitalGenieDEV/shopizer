package com.salesmanager.core.business.alibaba.param;

import java.util.Date;

public class AlibabaOpenplatformLogisticsDeliveryRateDTO {

    private String creator;

    /**
     * @return 创建人
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置创建人     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    private Long firstUnit;

    /**
     * @return 首重（单位：克）或首件（单位：件）
     */
    public Long getFirstUnit() {
        return firstUnit;
    }

    /**
     * 设置首重（单位：克）或首件（单位：件）     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setFirstUnit(Long firstUnit) {
        this.firstUnit = firstUnit;
    }

    private Long firstUnitFee;

    /**
     * @return 首重或首件的价格
     */
    public Long getFirstUnitFee() {
        return firstUnitFee;
    }

    /**
     * 设置首重或首件的价格     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setFirstUnitFee(Long firstUnitFee) {
        this.firstUnitFee = firstUnitFee;
    }

    private Date gmtCreate;

    /**
     * @return 创建时间
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * 设置创建时间     *
     * 参数示例：<pre></pre>     
     * 此参数必填
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
     * 设置修改时间     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    private Long id;

    /**
     * @return 主键id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键id     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setId(Long id) {
        this.id = id;
    }

    private Long leastExpenses;

    /**
     * @return 最低一票
     */
    public Long getLeastExpenses() {
        return leastExpenses;
    }

    /**
     * 设置最低一票     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setLeastExpenses(Long leastExpenses) {
        this.leastExpenses = leastExpenses;
    }

    private String memberId;

    /**
     * @return 会员memberId
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * 设置会员memberId     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    private String modifier;

    /**
     * @return 修改人
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * 设置修改人     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    private Long nextUnit;

    /**
     * @return 续重（单位：克）或续件（单位：件）单位
     */
    public Long getNextUnit() {
        return nextUnit;
    }

    /**
     * 设置续重（单位：克）或续件（单位：件）单位     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setNextUnit(Long nextUnit) {
        this.nextUnit = nextUnit;
    }

    private Long nextUnitFee;

    /**
     * @return 续重件价格
     */
    public Long getNextUnitFee() {
        return nextUnitFee;
    }

    /**
     * 设置续重件价格     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setNextUnitFee(Long nextUnitFee) {
        this.nextUnitFee = nextUnitFee;
    }

    private Long subTemplateId;

    /**
     * @return 子模板id
     */
    public Long getSubTemplateId() {
        return subTemplateId;
    }

    /**
     * 设置子模板id     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setSubTemplateId(Long subTemplateId) {
        this.subTemplateId = subTemplateId;
    }

    private String[] toAreaCodeList;

    /**
     * @return 到达区地址列表（使用行政区编码）
     */
    public String[] getToAreaCodeList() {
        return toAreaCodeList;
    }

    /**
     * 设置到达区地址列表（使用行政区编码）     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setToAreaCodeList(String[] toAreaCodeList) {
        this.toAreaCodeList = toAreaCodeList;
    }

}
