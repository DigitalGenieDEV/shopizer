package com.salesmanager.core.business.alibaba.rawsdk.client.exception;

/**
 * Ocean Exception
 * 
 * @author xiaoning.qxn
 */
public class OceanException extends Exception {

    /**
	 * 
	 */
    private static final long serialVersionUID = -1828858210195741131L;

    protected String          errorCode;

    protected String          errorMessage;

    protected String          traceId;

    protected String          requestId;

    /**
     * 获取返回的error_code错误码
     * 
     * @return
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * 获取返回的error_message错误信息
     * 
     * @return
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    public OceanException(){
    }

    /**
     * @param message
     */
    public OceanException(String message){
        super(message);
        this.errorMessage = message;
    }

    /**
     * @param message
     */
    public OceanException(String errorCode, String message){
        super(message);
        this.errorCode = errorCode;
        this.errorMessage = message;
    }

    /**
     * @param message
     */
    public OceanException(String errorCode, String message,Throwable e){
        super(e);
        this.errorCode = errorCode;
        this.errorMessage = message;
    }

    /**
     * @param cause
     */
    public OceanException(Throwable cause){
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public OceanException(String message, Throwable cause){
        super(message, cause);
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
