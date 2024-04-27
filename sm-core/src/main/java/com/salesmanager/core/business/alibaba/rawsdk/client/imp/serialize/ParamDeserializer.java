/**
 * 
 */
package com.salesmanager.core.business.alibaba.rawsdk.client.imp.serialize;


import com.salesmanager.core.business.alibaba.rawsdk.client.policy.Protocol;

/**
 *
 */
public class ParamDeserializer extends JsonDeserializer {

	@Override
	public String supportedContentType() {
		return Protocol.param.name();
	}

}
