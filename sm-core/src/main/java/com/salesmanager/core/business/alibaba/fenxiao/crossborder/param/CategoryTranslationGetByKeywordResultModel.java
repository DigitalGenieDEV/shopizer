package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class CategoryTranslationGetByKeywordResultModel {

    private Boolean success;

    /**
     * @return 是否成功
     */
    public Boolean getSuccess() {
        return success;
    }

    /**
     *     是否成功     *
     * 参数示例：<pre>true</pre>     
     *
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    private String code;

    /**
     * @return 错误码
     */
    public String getCode() {
        return code;
    }

    /**
     *     错误码     *
     * 参数示例：<pre>S0000</pre>     
     *
     */
    public void setCode(String code) {
        this.code = code;
    }

    private String message;

    /**
     * @return 错误描述
     */
    public String getMessage() {
        return message;
    }

    /**
     *     错误描述     *
     * 参数示例：<pre>成功</pre>     
     *
     */
    public void setMessage(String message) {
        this.message = message;
    }

    private CategoryTranslationGetByKeywordCategory[] result;

    /**
     * @return 实际结果
     */
    public CategoryTranslationGetByKeywordCategory[] getResult() {
        return result;
    }

    /**
     *     实际结果     *
     * 参数示例：<pre>如下</pre>     
     *
     */
    public void setResult(CategoryTranslationGetByKeywordCategory[] result) {
        this.result = result;
    }

}
