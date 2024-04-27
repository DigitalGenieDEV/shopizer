package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class ProductSearchKeywordQueryResultResultModelV {

    private Boolean success;

    /**
     * @return 正否正常
     */
    public Boolean getSuccess() {
        return success;
    }

    /**
     *     正否正常     *
     * 参数示例：<pre>正否正常</pre>     
     *    
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    private String code;

    /**
     * @return 状态码
     */
    public String getCode() {
        return code;
    }

    /**
     *     状态码     *
     * 参数示例：<pre>状态码</pre>     
     *    
     */
    public void setCode(String code) {
        this.code = code;
    }

    private String message;

    /**
     * @return 提示
     */
    public String getMessage() {
        return message;
    }

    /**
     *     提示     *
     * 参数示例：<pre>提示</pre>     
     *    
     */
    public void setMessage(String message) {
        this.message = message;
    }

    private ProductSearchKeywordQueryModelPageInfoV result;

    /**
     * @return 内容
     */
    public ProductSearchKeywordQueryModelPageInfoV getResult() {
        return result;
    }

    /**
     *     内容     *
     * 参数示例：<pre>内容</pre>     
     *    
     */
    public void setResult(ProductSearchKeywordQueryModelPageInfoV result) {
        this.result = result;
    }

}
