package com.salesmanager.core.business.alibaba.param;

public class AlibabaLogisticsFreightTemplate {

    private String addressCodeText;

    /**
     * @return 地址信息
     */
    public String getAddressCodeText() {
        return addressCodeText;
    }

    /**
     *     地址信息     *
     *        
     *    
     */
    public void setAddressCodeText(String addressCodeText) {
        this.addressCodeText = addressCodeText;
    }

    private String fromAreaCode;

    /**
     * @return 发货地址地区码
     */
    public String getFromAreaCode() {
        return fromAreaCode;
    }

    /**
     *     发货地址地区码     *
     *        
     *    
     */
    public void setFromAreaCode(String fromAreaCode) {
        this.fromAreaCode = fromAreaCode;
    }

    private Long id;

    /**
     * @return 模版ID
     */
    public Long getId() {
        return id;
    }

    /**
     *     模版ID     *
     *        
     *    
     */
    public void setId(Long id) {
        this.id = id;
    }

    private String memberId;

    /**
     * @return 会员ID
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     *     会员ID     *
     *        
     *    
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    private String name;

    /**
     * @return 名称
     */
    public String getName() {
        return name;
    }

    /**
     *     名称     *
     *        
     *    
     */
    public void setName(String name) {
        this.name = name;
    }

    private String remark;

    /**
     * @return 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     *     备注     *
     *        
     *    
     */
    public void setRemark(String remark) {
        this.remark = remark;
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
     *        
     *    
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    private AlibabaOpenplatformLogisticsDeliverySubTemplateDetailDTO expressSubTemplate;

    /**
     * @return 快递子模版
     */
    public AlibabaOpenplatformLogisticsDeliverySubTemplateDetailDTO getExpressSubTemplate() {
        return expressSubTemplate;
    }

    /**
     *     快递子模版     *
     *        
     *    
     */
    public void setExpressSubTemplate(AlibabaOpenplatformLogisticsDeliverySubTemplateDetailDTO expressSubTemplate) {
        this.expressSubTemplate = expressSubTemplate;
    }

    private AlibabaOpenplatformLogisticsDeliverySubTemplateDetailDTO logisticsSubTemplate;

    /**
     * @return 货运子模版
     */
    public AlibabaOpenplatformLogisticsDeliverySubTemplateDetailDTO getLogisticsSubTemplate() {
        return logisticsSubTemplate;
    }

    /**
     *     货运子模版     *
     *        
     *    
     */
    public void setLogisticsSubTemplate(AlibabaOpenplatformLogisticsDeliverySubTemplateDetailDTO logisticsSubTemplate) {
        this.logisticsSubTemplate = logisticsSubTemplate;
    }

    private AlibabaOpenplatformLogisticsDeliverySubTemplateDetailDTO codSubTemplate;

    /**
     * @return 货到付款子模版
     */
    public AlibabaOpenplatformLogisticsDeliverySubTemplateDetailDTO getCodSubTemplate() {
        return codSubTemplate;
    }

    /**
     *     货到付款子模版     *
     *        
     *    
     */
    public void setCodSubTemplate(AlibabaOpenplatformLogisticsDeliverySubTemplateDetailDTO codSubTemplate) {
        this.codSubTemplate = codSubTemplate;
    }

}
