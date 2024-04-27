package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class ProductSearchImageQueryResultResultModelV {

    private String success;

    /**
     * @return 是否成功
     */
    public String getSuccess() {
        return success;
    }

    /**
     *     是否成功     *
     * 参数示例：<pre>是否成功</pre>     
     *
     */
    public void setSuccess(String success) {
        this.success = success;
    }

    private String code;

    /**
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     *     code     *
     * 参数示例：<pre>code</pre>     
     *
     */
    public void setCode(String code) {
        this.code = code;
    }

    private String message;

    /**
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     *     message     *
     * 参数示例：<pre>message</pre>     
     *
     */
    public void setMessage(String message) {
        this.message = message;
    }

    private ProductSearchImageQueryModelPageInfoV result;

    /**
     * @return 结果
     */
    public ProductSearchImageQueryModelPageInfoV getResult() {
        return result;
    }

    /**
     *     结果     *
     * 参数示例：<pre>结果</pre>     
     *
     */
    public void setResult(ProductSearchImageQueryModelPageInfoV result) {
        this.result = result;
    }

}
