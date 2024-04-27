package com.salesmanager.core.business.alibaba.param;

public class AlibabaOpenplatformLogisticsDeliveryRateDetailDTO {

    private String operateType;

    /**
     * @return 费率操作类型：INSERT,UPDATE,DELETE
     */
    public String getOperateType() {
        return operateType;
    }

    /**
     *     费率操作类型：INSERT,UPDATE,DELETE     *
     *        
     *    
     */
    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    private Boolean isSysRate;

    /**
     * @return 是否系统模板
     */
    public Boolean getIsSysRate() {
        return isSysRate;
    }

    /**
     *     是否系统模板     *
     *        
     *    
     */
    public void setIsSysRate(Boolean isSysRate) {
        this.isSysRate = isSysRate;
    }

    private String toAreaCodeText;

    /**
     * @return 地址编码文本，用顿号隔开。例如：上海、福建省、广东省
     */
    public String getToAreaCodeText() {
        return toAreaCodeText;
    }

    /**
     *     地址编码文本，用顿号隔开。例如：上海、福建省、广东省     *
     *        
     *    
     */
    public void setToAreaCodeText(String toAreaCodeText) {
        this.toAreaCodeText = toAreaCodeText;
    }

    private AlibabaOpenplatformLogisticsDeliveryRateDTO rateDTO;

    /**
     * @return 普通子模板费率
     */
    public AlibabaOpenplatformLogisticsDeliveryRateDTO getRateDTO() {
        return rateDTO;
    }

    /**
     *     普通子模板费率     *
     *        
     *    
     */
    public void setRateDTO(AlibabaOpenplatformLogisticsDeliveryRateDTO rateDTO) {
        this.rateDTO = rateDTO;
    }

    private AlibabaOpenplatformLogisticsDeliverySysRateDTO sysRateDTO;

    /**
     * @return 系统子模板费率
     */
    public AlibabaOpenplatformLogisticsDeliverySysRateDTO getSysRateDTO() {
        return sysRateDTO;
    }

    /**
     *     系统子模板费率     *
     *        
     *    
     */
    public void setSysRateDTO(AlibabaOpenplatformLogisticsDeliverySysRateDTO sysRateDTO) {
        this.sysRateDTO = sysRateDTO;
    }

}
