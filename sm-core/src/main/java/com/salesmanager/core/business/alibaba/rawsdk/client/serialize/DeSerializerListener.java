/**
 * 
 */
package com.salesmanager.core.business.alibaba.rawsdk.client.serialize;

/**
 *
 */
public interface DeSerializerListener {

	public void onResponseDeSerialized(String response);

	public void onResponseExceptioned(String response);

}
