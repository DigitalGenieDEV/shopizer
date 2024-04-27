/**
 * 
 */
package com.salesmanager.core.business.alibaba.rawsdk.client.serialize;

import java.util.Map;

/**
 *
 */
public interface Serializer {

	/**
	 *
	 * @see com.alibaba.openapi.client.policy.Protocol
	 * @return
	 */
	public String supportedContentType();

	/**
	 *
	 * @param serializer
	 * @return
	 */
	public Map<String, Object> serialize(Object serializer);

	/**
	 * 
	 * @param listner
	 */
	public void registeSerializerListener(SerializerListener listner);
}
