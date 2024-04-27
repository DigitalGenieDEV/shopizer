package com.salesmanager.core.business.alibaba.param;

import java.util.Date;

public class AlibabaOpenplatformLogisticsDeliverySysRateDTO {

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

    private Long firstUnit;

    /**
     * @return 首重（单位：克）或首件（单位：件）
     */
    public Long getFirstUnit() {
        return firstUnit;
    }

    /**
     *     首重（单位：克）或首件（单位：件）     *
     *        
     *
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
     *     首重或首件的价格     *
     *        
     *
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
     * @return 主键id
     */
    public Long getId() {
        return id;
    }

    /**
     *     主键id     *
     *        
     *
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
     *     最低一票     *
     *        
     *
     */
    public void setLeastExpenses(Long leastExpenses) {
        this.leastExpenses = leastExpenses;
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

    private Long nextUnit;

    /**
     * @return 续重（单位：克）或续件（单位：件）单位
     */
    public Long getNextUnit() {
        return nextUnit;
    }

    /**
     *     续重（单位：克）或续件（单位：件）单位     *
     *        
     *
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
     *     续重件价格     *
     *        
     *
     */
    public void setNextUnitFee(Long nextUnitFee) {
        this.nextUnitFee = nextUnitFee;
    }

    private Long sysTemplateId;

    /**
     * @return 系统运费模板id
     */
    public Long getSysTemplateId() {
        return sysTemplateId;
    }

    /**
     *     系统运费模板id     *
     *        
     *
     */
    public void setSysTemplateId(Long sysTemplateId) {
        this.sysTemplateId = sysTemplateId;
    }

    private String[] toAreaCodeList;

    /**
     * @return 到达地区编码（使用行政区编码）
     */
    public String[] getToAreaCodeList() {
        return toAreaCodeList;
    }

    /**
     *     到达地区编码（使用行政区编码）     *
     *        
     *
     */
    public void setToAreaCodeList(String[] toAreaCodeList) {
        this.toAreaCodeList = toAreaCodeList;
    }

}
