package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class ProductImageUploadParamUploadImageParam {

    private String imageBase64;

    /**
     * @return 
     */
    public String getImageBase64() {
        return imageBase64;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    private Long appKey;

    /**
     * @return 
     */
    public Long getAppKey() {
        return appKey;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setAppKey(Long appKey) {
        this.appKey = appKey;
    }

    private String outMemberId;

    /**
     * @return 
     */
    public String getOutMemberId() {
        return outMemberId;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setOutMemberId(String outMemberId) {
        this.outMemberId = outMemberId;
    }

}
