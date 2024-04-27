/**
 * 
 */
package com.salesmanager.core.business.alibaba.rawsdk.client.serialize;

import java.util.Map;

/**
 *
 */
public interface SerializerListener {
	public void onRequestSerialized(Map<String, Object> serializedRequest);
}
