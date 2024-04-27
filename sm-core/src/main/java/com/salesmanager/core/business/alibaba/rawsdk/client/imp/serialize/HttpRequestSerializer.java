package com.salesmanager.core.business.alibaba.rawsdk.client.imp.serialize;


import com.salesmanager.core.business.alibaba.rawsdk.client.policy.Protocol;

/**
 *
 */
public class HttpRequestSerializer extends AbstractParamRequestSerializer {

	public String supportedContentType() {
		return Protocol.http.name();
	}

	@Override
	protected String processNestedParameter(Object value) {
		throw new RuntimeException(
				"The param protocol does not support Nested parameters.");
	}
	
}