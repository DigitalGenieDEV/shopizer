/**
 * 
 */
package com.salesmanager.core.business.alibaba.rawsdk.common;


import com.salesmanager.core.business.alibaba.rawsdk.client.APIId;
import com.salesmanager.core.business.alibaba.rawsdk.client.policy.RequestPolicy;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author hongbang.hb
 *
 */
public abstract class AbstractAPIRequest<TResponse> {

	protected RequestPolicy oceanRequestPolicy = new RequestPolicy();

	protected APIId oceanApiId;

	protected String traceId;

	protected String rpcId;

	public RequestPolicy getOceanRequestPolicy() {
		return oceanRequestPolicy;
	}

	public void setOceanRequestPolicy(RequestPolicy oceanRequestPolicy) {
		this.oceanRequestPolicy = oceanRequestPolicy;
	}

	public APIId getOceanApiId() {
		return oceanApiId;
	}

	public void setOceanApiId(APIId oceanApiId) {
		this.oceanApiId = oceanApiId;
	}

	public Class<TResponse> getResponseClass() {
		Type type = this.getClass().getGenericSuperclass();

		ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
		return (Class) parameterizedType.getActualTypeArguments()[0];
	}

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}

	public String getRpcId() {
		return rpcId;
	}

	public void setRpcId(String rpcId) {
		this.rpcId = rpcId;
	}
}
