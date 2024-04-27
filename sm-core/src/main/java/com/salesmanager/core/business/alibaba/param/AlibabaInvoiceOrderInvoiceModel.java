package com.salesmanager.core.business.alibaba.param;

public class AlibabaInvoiceOrderInvoiceModel {

    private String invoiceCompanyName;

    /**
     * @return 发票公司名称(即发票抬头-title)
     */
    public String getInvoiceCompanyName() {
        return invoiceCompanyName;
    }

    /**
     *     发票公司名称(即发票抬头-title)     *
     *        
     *
     */
    public void setInvoiceCompanyName(String invoiceCompanyName) {
        this.invoiceCompanyName = invoiceCompanyName;
    }

    private Integer invoiceType;

    /**
     * @return 发票类型. 0：普通发票，1:增值税发票，9未知类型
     */
    public Integer getInvoiceType() {
        return invoiceType;
    }

    /**
     *     发票类型. 0：普通发票，1:增值税发票，9未知类型     *
     *        
     *
     */
    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    private Long localInvoiceId;

    /**
     * @return 本地发票号
     */
    public Long getLocalInvoiceId() {
        return localInvoiceId;
    }

    /**
     *     本地发票号     *
     *        
     *
     */
    public void setLocalInvoiceId(Long localInvoiceId) {
        this.localInvoiceId = localInvoiceId;
    }

    private Long orderId;

    /**
     * @return 订单Id
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     *     订单Id     *
     *        
     *
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    private String receiveCode;

    /**
     * @return (收件人)址区域编码
     */
    public String getReceiveCode() {
        return receiveCode;
    }

    /**
     *     (收件人)址区域编码     *
     *        
     *
     */
    public void setReceiveCode(String receiveCode) {
        this.receiveCode = receiveCode;
    }

    private String receiveCodeText;

    /**
     * @return (收件人) 省市区编码对应的文案(增值税发票信息)
     */
    public String getReceiveCodeText() {
        return receiveCodeText;
    }

    /**
     *     (收件人) 省市区编码对应的文案(增值税发票信息)     *
     *        
     *
     */
    public void setReceiveCodeText(String receiveCodeText) {
        this.receiveCodeText = receiveCodeText;
    }

    private String receiveMobile;

    /**
     * @return （收件者）发票收货人手机
     */
    public String getReceiveMobile() {
        return receiveMobile;
    }

    /**
     *     （收件者）发票收货人手机     *
     *        
     *
     */
    public void setReceiveMobile(String receiveMobile) {
        this.receiveMobile = receiveMobile;
    }

    private String receiveName;

    /**
     * @return （收件者）发票收货人
     */
    public String getReceiveName() {
        return receiveName;
    }

    /**
     *     （收件者）发票收货人     *
     *        
     *
     */
    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    private String receivePhone;

    /**
     * @return （收件者）发票收货人电话
     */
    public String getReceivePhone() {
        return receivePhone;
    }

    /**
     *     （收件者）发票收货人电话     *
     *        
     *
     */
    public void setReceivePhone(String receivePhone) {
        this.receivePhone = receivePhone;
    }

    private String receivePost;

    /**
     * @return （收件者）发票收货地址邮编
     */
    public String getReceivePost() {
        return receivePost;
    }

    /**
     *     （收件者）发票收货地址邮编     *
     *        
     *
     */
    public void setReceivePost(String receivePost) {
        this.receivePost = receivePost;
    }

    private String receiveStreet;

    /**
     * @return (收件人) 街道地址(增值税发票信息)
     */
    public String getReceiveStreet() {
        return receiveStreet;
    }

    /**
     *     (收件人) 街道地址(增值税发票信息)     *
     *        
     *
     */
    public void setReceiveStreet(String receiveStreet) {
        this.receiveStreet = receiveStreet;
    }

    private String registerAccountId;

    /**
     * @return (公司)银行账号
     */
    public String getRegisterAccountId() {
        return registerAccountId;
    }

    /**
     *     (公司)银行账号     *
     *        
     *
     */
    public void setRegisterAccountId(String registerAccountId) {
        this.registerAccountId = registerAccountId;
    }

    private String registerBank;

    /**
     * @return (公司)开户银行
     */
    public String getRegisterBank() {
        return registerBank;
    }

    /**
     *     (公司)开户银行     *
     *        
     *
     */
    public void setRegisterBank(String registerBank) {
        this.registerBank = registerBank;
    }

    private String registerCode;

    /**
     * @return (注册)省市区编码
     */
    public String getRegisterCode() {
        return registerCode;
    }

    /**
     *     (注册)省市区编码     *
     *        
     *
     */
    public void setRegisterCode(String registerCode) {
        this.registerCode = registerCode;
    }

    private String registerCodeText;

    /**
     * @return (注册)省市区文本
     */
    public String getRegisterCodeText() {
        return registerCodeText;
    }

    /**
     *     (注册)省市区文本     *
     *        
     *
     */
    public void setRegisterCodeText(String registerCodeText) {
        this.registerCodeText = registerCodeText;
    }

    private String registerPhone;

    /**
     * @return （公司）注册电话
     */
    public String getRegisterPhone() {
        return registerPhone;
    }

    /**
     *     （公司）注册电话     *
     *        
     *
     */
    public void setRegisterPhone(String registerPhone) {
        this.registerPhone = registerPhone;
    }

    private String registerStreet;

    /**
     * @return (注册)街道地址
     */
    public String getRegisterStreet() {
        return registerStreet;
    }

    /**
     *     (注册)街道地址     *
     *        
     *
     */
    public void setRegisterStreet(String registerStreet) {
        this.registerStreet = registerStreet;
    }

    private String taxpayerIdentify;

    /**
     * @return 纳税人识别号
     */
    public String getTaxpayerIdentify() {
        return taxpayerIdentify;
    }

    /**
     *     纳税人识别号     *
     *        
     *
     */
    public void setTaxpayerIdentify(String taxpayerIdentify) {
        this.taxpayerIdentify = taxpayerIdentify;
    }

}
