package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class PoolProductPullResultModel {

    private String success;

    /**
     * @return 调用结果
     */
    public String getSuccess() {
        return success;
    }

    /**
     * 设置调用结果     *
     * 参数示例：<pre>true</pre>     
     * 此参数必填
     */
    public void setSuccess(String success) {
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
     * 设置错误码     *
     * 参数示例：<pre>S0000</pre>     
     * 此参数必填
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
     * 设置错误描述     *
     * 参数示例：<pre>成功</pre>     
     * 此参数必填
     */
    public void setMessage(String message) {
        this.message = message;
    }

    private PoolProductPullProductPoolModel[] result;

    /**
     * @return 结果
     */
    public PoolProductPullProductPoolModel[] getResult() {
        return result;
    }

    /**
     * 设置结果     *
     * 参数示例：<pre>如下</pre>     
     * 此参数必填
     */
    public void setResult(PoolProductPullProductPoolModel[] result) {
        this.result = result;
    }

}
