package com.salesmanager.core.business.alibaba.param;

public class AlibabaOceanOpenplatformBizTradeResultOrderRefundReasonModel {

    private Long id;

    /**
     * @return 原因id
     */
    public Long getId() {
        return id;
    }

    /**
     *     原因id     *
     *    
     *
     */
    public void setId(Long id) {
        this.id = id;
    }

    private String name;

    /**
     * @return 原因
     */
    public String getName() {
        return name;
    }

    /**
     *     原因     *
     *    
     *
     */
    public void setName(String name) {
        this.name = name;
    }

    private Boolean needVoucher;

    /**
     * @return 凭证是否必须上传
     */
    public Boolean getNeedVoucher() {
        return needVoucher;
    }

    /**
     *     凭证是否必须上传     *
     *    
     *
     */
    public void setNeedVoucher(Boolean needVoucher) {
        this.needVoucher = needVoucher;
    }

    private Boolean noRefundCarriage;

    /**
     * @return 是否支持退运费
     */
    public Boolean getNoRefundCarriage() {
        return noRefundCarriage;
    }

    /**
     *     是否支持退运费     *
     * 参数示例：<pre>“true" 表示不支持退运费</pre>     
     *
     */
    public void setNoRefundCarriage(Boolean noRefundCarriage) {
        this.noRefundCarriage = noRefundCarriage;
    }

    private String tip;

    /**
     * @return 提示
     */
    public String getTip() {
        return tip;
    }

    /**
     *     提示     *
     *    
     *
     */
    public void setTip(String tip) {
        this.tip = tip;
    }

}
