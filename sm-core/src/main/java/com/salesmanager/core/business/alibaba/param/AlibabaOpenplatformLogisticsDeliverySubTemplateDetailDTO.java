package com.salesmanager.core.business.alibaba.param;

public class AlibabaOpenplatformLogisticsDeliverySubTemplateDetailDTO {

    private String operateType;

    /**
     * @return 操作类型
     */
    public String getOperateType() {
        return operateType;
    }

    /**
     *     操作类型     *
     *        
     *
     */
    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    private AlibabaOpenplatformLogisticsDeliverySubTemplateDTO subTemplateDTO;

    /**
     * @return 子模板
     */
    public AlibabaOpenplatformLogisticsDeliverySubTemplateDTO getSubTemplateDTO() {
        return subTemplateDTO;
    }

    /**
     *     子模板     *
     *        
     *
     */
    public void setSubTemplateDTO(AlibabaOpenplatformLogisticsDeliverySubTemplateDTO subTemplateDTO) {
        this.subTemplateDTO = subTemplateDTO;
    }

    private AlibabaOpenplatformLogisticsDeliveryRateDetailDTO[] rateList;

    /**
     * @return 费率
     */
    public AlibabaOpenplatformLogisticsDeliveryRateDetailDTO[] getRateList() {
        return rateList;
    }

    /**
     *     费率     *
     *        
     *
     */
    public void setRateList(AlibabaOpenplatformLogisticsDeliveryRateDetailDTO[] rateList) {
        this.rateList = rateList;
    }

}
