package com.salesmanager.core.business.alibaba.param;

import java.util.Date;

public class AlibabaTradeRefundOpLogisticsCompanyModel {

    private String companyName;

    /**
     * @return 快递公司名
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     *     快递公司名     *
     *        
     *
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    private String companyNo;

    /**
     * @return 物流公司编号
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     *     物流公司编号     *
     *        
     *
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    private String companyPhone;

    /**
     * @return 物流公司服务电话
     */
    public String getCompanyPhone() {
        return companyPhone;
    }

    /**
     *     物流公司服务电话     *
     *        
     *
     */
    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
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
     * @return ID
     */
    public Long getId() {
        return id;
    }

    /**
     *     ID     *
     *        
     *
     */
    public void setId(Long id) {
        this.id = id;
    }

    private String spelling;

    /**
     * @return 全拼
     */
    public String getSpelling() {
        return spelling;
    }

    /**
     *     全拼     *
     *        
     *
     */
    public void setSpelling(String spelling) {
        this.spelling = spelling;
    }

    private Boolean supportPrint;

    /**
     * @return 是否支持打印
     */
    public Boolean getSupportPrint() {
        return supportPrint;
    }

    /**
     *     是否支持打印     *
     *        
     *
     */
    public void setSupportPrint(Boolean supportPrint) {
        this.supportPrint = supportPrint;
    }

}
