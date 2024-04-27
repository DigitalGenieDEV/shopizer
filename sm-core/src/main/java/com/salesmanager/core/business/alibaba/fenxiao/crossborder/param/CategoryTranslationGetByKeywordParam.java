package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

import com.salesmanager.core.business.alibaba.rawsdk.client.APIId;
import com.salesmanager.core.business.alibaba.rawsdk.common.AbstractAPIRequest;


public class CategoryTranslationGetByKeywordParam extends AbstractAPIRequest<CategoryTranslationGetByKeywordResult> {

    public CategoryTranslationGetByKeywordParam() {
        super();
        oceanApiId = new APIId("com.alibaba.fenxiao.crossborder", "category.translation.getByKeyword", 1);
    }

    private String outMemberId;

    /**
     * @return 用户在机构的唯一ID。不超过64位，由数字和字母组成。
     */
    public String getOutMemberId() {
        return outMemberId;
    }

    /**
     *     用户在机构的唯一ID。不超过64位，由数字和字母组成。     *
     * 参数示例：<pre>2423523f13tr12412f</pre>     
     *
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
     *     语种。见常见问题中的枚举。     *
     * 参数示例：<pre>ja</pre>     
     *
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    private String cateName;

    /**
     * @return 类目名。可模糊搜索。
     */
    public String getCateName() {
        return cateName;
    }

    /**
     *     类目名。可模糊搜索。     *
     * 参数示例：<pre>裙子</pre>     
     *
     */
    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

}
