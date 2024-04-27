/**
 * 
 */
package com.salesmanager.core.business.alibaba.rawsdk.client;


import com.salesmanager.core.business.alibaba.rawsdk.client.serialize.DeSerializerListener;
import com.salesmanager.core.business.alibaba.rawsdk.client.serialize.SerializerListener;

/**
 *
 */
public interface SDKListener {
	
	public void register(SerializerListener serializerListener);

	public void register(DeSerializerListener deSerializerListener);
	
}
