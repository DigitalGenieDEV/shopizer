package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

import com.salesmanager.core.business.alibaba.rawsdk.client.APIId;
import com.salesmanager.core.business.alibaba.rawsdk.common.AbstractAPIRequest;


public class CategoryTranslationGetByIdParam extends AbstractAPIRequest<CategoryTranslationGetByIdResult> {

    public CategoryTranslationGetByIdParam() {
        super();
        oceanApiId = new APIId("com.alibaba.fenxiao.crossborder", "category.translation.getById", 1);
    }

    private String outMemberId;

    /**
     * @return 用户在机构的唯一ID。不超过64位，由数字和字母组成。
     */
    public String getOutMemberId() {
        return outMemberId;
    }

    /**
     * 设置用户在机构的唯一ID。不超过64位，由数字和字母组成。     *
     * 参数示例：<pre>23423532fwef</pre>     
     * 此参数必填
     */
    public void setOutMemberId(String outMemberId) {
        this.outMemberId = outMemberId;
    }

    private String language;

    /**
     * @return 语种。见常见问题中的枚举。
     */
    public String getLanguage() {
        return language;
    }

    /**
     * 设置语种。见常见问题中的枚举。     *
     * 参数示例：<pre>ja</pre>     
     * 此参数必填
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    private Long categoryId;

    /**
     * @return 类目ID
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * 设置类目ID     *
     * 参数示例：<pre>0</pre>     
     * 此参数必填
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

}
