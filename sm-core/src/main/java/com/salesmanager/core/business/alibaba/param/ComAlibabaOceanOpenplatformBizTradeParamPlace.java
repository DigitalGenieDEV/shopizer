package com.salesmanager.core.business.alibaba.param;

public class ComAlibabaOceanOpenplatformBizTradeParamPlace {

    private String code;

    /**
     * @return 地址code
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置地址code     *
     * 参数示例：<pre>511300</pre>     
     * 此参数必填
     */
    public void setCode(String code) {
        this.code = code;
    }

    private String name;

    /**
     * @return 地址name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置地址name     *
     * 参数示例：<pre>南充</pre>     
     * 此参数必填
     */
    public void setName(String name) {
        this.name = name;
    }

}
