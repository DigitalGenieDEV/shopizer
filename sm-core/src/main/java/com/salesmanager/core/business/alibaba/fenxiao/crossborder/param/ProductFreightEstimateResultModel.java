package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class ProductFreightEstimateResultModel {

    private Boolean success;

    /**
     * @return 结果
     */
    public Boolean getSuccess() {
        return success;
    }

    /**
     *     结果     *
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

    private ProductFreightEstimateProductFreightModel result;

    /**
     * @return 内部结果
     */
    public ProductFreightEstimateProductFreightModel getResult() {
        return result;
    }

    /**
     *     内部结果     *
     *        
     *
     */
    public void setResult(ProductFreightEstimateProductFreightModel result) {
        this.result = result;
    }

}
