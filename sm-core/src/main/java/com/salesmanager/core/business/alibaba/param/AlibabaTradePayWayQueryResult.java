package com.salesmanager.core.business.alibaba.param;

public class AlibabaTradePayWayQueryResult {

    private String success;

    /**
     * @return 是否成功	
     */
    public String getSuccess() {
        return success;
    }

    /**
     *     是否成功	     *
          
     *    
     */
    public void setSuccess(String success) {
        this.success = success;
    }

    private String errorCode;

    /**
     * @return 错误码	
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     *     错误码	     *
          
     *    
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    private String errorMsg;

    /**
     * @return 错误信息	
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     *     错误信息	     *
          
     *    
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    private AlibabaOceanOpenplatformBizTradeResultTradePayTypeResult resultList;

    /**
     * @return 返回结果
     */
    public AlibabaOceanOpenplatformBizTradeResultTradePayTypeResult getResultList() {
        return resultList;
    }

    /**
     *     返回结果     *
          
     *    
     */
    public void setResultList(AlibabaOceanOpenplatformBizTradeResultTradePayTypeResult resultList) {
        this.resultList = resultList;
    }

}
