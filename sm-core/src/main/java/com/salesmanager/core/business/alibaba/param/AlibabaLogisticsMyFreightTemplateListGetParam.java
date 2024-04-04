package com.salesmanager.core.business.alibaba.param;

import com.salesmanager.core.business.alibaba.rawsdk.client.APIId;
import com.salesmanager.core.business.alibaba.rawsdk.common.AbstractAPIRequest;


public class AlibabaLogisticsMyFreightTemplateListGetParam extends AbstractAPIRequest<AlibabaLogisticsMyFreightTemplateListGetResult> {

    public AlibabaLogisticsMyFreightTemplateListGetParam() {
        super();
        oceanApiId = new APIId("com.alibaba.logistics", "alibaba.logistics.myFreightTemplate.list.get", 1);
    }

    private Long templateId;

    /**
     * @return 模版id，用于单条查询的场景
     */
    public Long getTemplateId() {
        return templateId;
    }

    /**
     * 设置模版id，用于单条查询的场景     *
     * 参数示例：<pre>xxx</pre>     
     * 此参数必填
     */
    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    private Boolean querySubTemplate;

    /**
     * @return 是否查询子模板
     */
    public Boolean getQuerySubTemplate() {
        return querySubTemplate;
    }

    /**
     * 设置是否查询子模板     *
     * 参数示例：<pre>false</pre>     
     * 此参数必填
     */
    public void setQuerySubTemplate(Boolean querySubTemplate) {
        this.querySubTemplate = querySubTemplate;
    }

    private Boolean queryRate;

    /**
     * @return 是否查询子模板费率
     */
    public Boolean getQueryRate() {
        return queryRate;
    }

    /**
     * 设置是否查询子模板费率     *
     * 参数示例：<pre>false</pre>     
     * 此参数必填
     */
    public void setQueryRate(Boolean queryRate) {
        this.queryRate = queryRate;
    }

}
