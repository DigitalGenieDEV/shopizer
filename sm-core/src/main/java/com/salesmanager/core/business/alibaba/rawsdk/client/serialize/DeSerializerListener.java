/**
 * 
 */
package com.salesmanager.core.business.alibaba.rawsdk.client.serialize;

/**
 * @author hongbang.hb
 *
 */
public interface DeSerializerListener {

	public void onResponseDeSerialized(String response);

	public void onResponseExceptioned(String response);

}
