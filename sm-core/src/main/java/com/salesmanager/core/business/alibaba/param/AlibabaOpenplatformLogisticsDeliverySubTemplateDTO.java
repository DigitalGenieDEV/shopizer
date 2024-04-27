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
     *     计件类型。0:重量 1:件数 2:体积     *
     *        
     *
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
     *     创建人     *
     *        
     *
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
     *     创建时间     *
     *        
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
     *        
     *
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
     *     主键id，也就是子模板id     *
     *        
     *
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
     *     是否系统模板     *
     *        
     *
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
     *     会员memberId     *
     *        
     *
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
     *     修改人     *
     *        
     *
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
     *     运费承担类型 卖家承担：0；买家承担：1。     *
     *        
     *
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
     *     服务类型。0:快递 1:货运 2:货到付款     *
     *        
     *
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
     *     主模板id     *
     *        
     *
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
     *     子模板类型 0基准 1增值。默认0。     *
     *        
     *
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
     *     操作类型：INSERT，UPDATE，DELETE     *
     *        
     *
     */
    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

}
