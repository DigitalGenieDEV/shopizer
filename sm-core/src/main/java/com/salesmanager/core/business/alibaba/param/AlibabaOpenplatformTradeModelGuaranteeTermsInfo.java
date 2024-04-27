package com.salesmanager.core.business.alibaba.param;

public class AlibabaOpenplatformTradeModelGuaranteeTermsInfo {

    private String assuranceInfo;

    /**
     * @return 保障条款
     */
    public String getAssuranceInfo() {
        return assuranceInfo;
    }

    /**
     *     保障条款     *
     *        
     *
     */
    public void setAssuranceInfo(String assuranceInfo) {
        this.assuranceInfo = assuranceInfo;
    }

    private String assuranceType;

    /**
     * @return 保障方式。国际站：TA(信保)
     */
    public String getAssuranceType() {
        return assuranceType;
    }

    /**
     *     保障方式。国际站：TA(信保)     *
     *        
     *
     */
    public void setAssuranceType(String assuranceType) {
        this.assuranceType = assuranceType;
    }

    private String qualityAssuranceType;

    /**
     * @return 质量保证类型。国际站：pre_shipment(发货前),post_delivery(发货后)
     */
    public String getQualityAssuranceType() {
        return qualityAssuranceType;
    }

    /**
     *     质量保证类型。国际站：pre_shipment(发货前),post_delivery(发货后)     *
     *        
     *
     */
    public void setQualityAssuranceType(String qualityAssuranceType) {
        this.qualityAssuranceType = qualityAssuranceType;
    }

}
