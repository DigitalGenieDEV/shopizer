/**
 * 
 */
package com.salesmanager.core.business.alibaba.rawsdk.client.imp.serialize;


import com.salesmanager.core.business.alibaba.rawsdk.client.policy.Protocol;

/**
 * @author hongbang.hb
 *
 */
public class ParamRequestSerializer extends AbstractParamRequestSerializer {

	public String supportedContentType() {
		return Protocol.param.name();
	}

	@Override
	protected String processNestedParameter(Object value) {
		throw new RuntimeException("The param protocol does not support Nested parameters.");
	}

}
