/**
 * 
 */
package com.salesmanager.core.business.alibaba.rawsdk.client.imp.serialize;


import com.salesmanager.core.business.alibaba.rawsdk.client.policy.Protocol;

/**
 *
 */
public class Param2Deserializer extends Json2Deserializer {

	@Override
	public String supportedContentType() {
		return Protocol.param2.name();
	}

}
