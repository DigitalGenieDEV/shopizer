package com.salesmanager.core.business.alibaba.param;

public class AlibabaLogisticsOpenPlatformLogisticsStep {

    private String acceptTime;

    /**
     * @return 物流跟踪单该步骤的时间
     */
    public String getAcceptTime() {
        return acceptTime;
    }

    /**
     *     物流跟踪单该步骤的时间     *
     *        
     *
     */
    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }

    private String remark;

    /**
     * @return 备注，如：“在浙江浦江县公司进行下级地点扫描，即将发往：广东深圳公司”
     */
    public String getRemark() {
        return remark;
    }

    /**
     *     备注，如：“在浙江浦江县公司进行下级地点扫描，即将发往：广东深圳公司”     *
     *        
     *
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

}
