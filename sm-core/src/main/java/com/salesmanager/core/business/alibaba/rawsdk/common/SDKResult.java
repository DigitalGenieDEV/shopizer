/**
 * 
 */
package com.salesmanager.core.business.alibaba.rawsdk.common;

/**
 * @author hongbang.hb
 *
 */
public class SDKResult<T> {

	private String errorCode;
	private String errorMessage;
	private String requestId;
	private String traceId;
	private T result;

	public SDKResult(String code, String message) {
		super();
		this.errorCode = code;
		this.errorMessage = message;
	}

	public SDKResult(String requestId,String code, String message) {
		super();
		this.requestId=requestId;
		this.errorCode = code;
		this.errorMessage = message;
	}

	public SDKResult(T result) {
		super();
		this.result = result;
	}

	public SDKResult(String requestId,T result) {
		super();
		this.requestId=requestId;
		this.result = result;
	}

	public SDKResult() {
		super();
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}
}
