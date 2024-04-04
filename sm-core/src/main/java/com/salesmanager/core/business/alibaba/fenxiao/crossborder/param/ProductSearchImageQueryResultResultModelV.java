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
     * 设置是否成功     *
     * 参数示例：<pre>是否成功</pre>     
     * 此参数必填
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
     * 设置code     *
     * 参数示例：<pre>code</pre>     
     * 此参数必填
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
     * 设置message     *
     * 参数示例：<pre>message</pre>     
     * 此参数必填
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
     * 设置结果     *
     * 参数示例：<pre>结果</pre>     
     * 此参数必填
     */
    public void setResult(ProductSearchImageQueryModelPageInfoV result) {
        this.result = result;
    }

}
