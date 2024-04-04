package com.salesmanager.core.business.alibaba.param;

import java.util.Date;

public class AlibabaOpenplatformLogisticsDeliverySubTemplateDTO {

    private Integer chargeType;

    /**
     * @return 计件类型。0:重量 1:件数 2:体积
     */
    public Integer getChargeType() {
        return chargeType;
    }

    /**
     * 设置计件类型。0:重量 1:件数 2:体积     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setChargeType(Integer chargeType) {
        this.chargeType = chargeType;
    }

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
     * @return 主键id，也就是子模板id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键id，也就是子模板id     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setId(Long id) {
        this.id = id;
    }

    private Boolean isSysTemplate;

    /**
     * @return 是否系统模板
     */
    public Boolean getIsSysTemplate() {
        return isSysTemplate;
    }

    /**
     * 设置是否系统模板     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setIsSysTemplate(Boolean isSysTemplate) {
        this.isSysTemplate = isSysTemplate;
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

    private Integer serviceChargeType;

    /**
     * @return 运费承担类型 卖家承担：0；买家承担：1。
     */
    public Integer getServiceChargeType() {
        return serviceChargeType;
    }

    /**
     * 设置运费承担类型 卖家承担：0；买家承担：1。     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setServiceChargeType(Integer serviceChargeType) {
        this.serviceChargeType = serviceChargeType;
    }

    private Integer serviceType;

    /**
     * @return 服务类型。0:快递 1:货运 2:货到付款
     */
    public Integer getServiceType() {
        return serviceType;
    }

    /**
     * 设置服务类型。0:快递 1:货运 2:货到付款     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    private Long templateId;

    /**
     * @return 主模板id
     */
    public Long getTemplateId() {
        return templateId;
    }

    /**
     * 设置主模板id     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    private Integer type;

    /**
     * @return 子模板类型 0基准 1增值。默认0。
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置子模板类型 0基准 1增值。默认0。     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setType(Integer type) {
        this.type = type;
    }

    private String operateType;

    /**
     * @return 操作类型：INSERT，UPDATE，DELETE
     */
    public String getOperateType() {
        return operateType;
    }

    /**
     * 设置操作类型：INSERT，UPDATE，DELETE     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

}
