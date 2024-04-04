/**
 * 
 */
package com.salesmanager.core.business.alibaba.rawsdk.client.serialize;

import java.util.Map;

/**
 * @author hongbang.hb
 *
 */
public interface SerializerListener {
	public void onRequestSerialized(Map<String, Object> serializedRequest);
}
