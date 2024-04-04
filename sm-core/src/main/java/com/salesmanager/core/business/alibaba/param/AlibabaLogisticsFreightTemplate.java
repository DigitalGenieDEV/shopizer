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
     * 设置地址信息     *
     * 参数示例：<pre></pre>     
     * 此参数必填
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
     * 设置发货地址地区码     *
     * 参数示例：<pre></pre>     
     * 此参数必填
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
     * 设置模版ID     *
     * 参数示例：<pre></pre>     
     * 此参数必填
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
     * 设置会员ID     *
     * 参数示例：<pre></pre>     
     * 此参数必填
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
     * 设置名称     *
     * 参数示例：<pre></pre>     
     * 此参数必填
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
     * 设置备注     *
     * 参数示例：<pre></pre>     
     * 此参数必填
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
     * 设置状态     *
     * 参数示例：<pre></pre>     
     * 此参数必填
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
     * 设置快递子模版     *
     * 参数示例：<pre></pre>     
     * 此参数必填
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
     * 设置货运子模版     *
     * 参数示例：<pre></pre>     
     * 此参数必填
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
     * 设置货到付款子模版     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setCodSubTemplate(AlibabaOpenplatformLogisticsDeliverySubTemplateDetailDTO codSubTemplate) {
        this.codSubTemplate = codSubTemplate;
    }

}
