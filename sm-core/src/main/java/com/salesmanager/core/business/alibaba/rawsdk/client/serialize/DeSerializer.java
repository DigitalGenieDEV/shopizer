/**
 * 
 */
package com.salesmanager.core.business.alibaba.rawsdk.client.serialize;


import com.salesmanager.core.business.alibaba.rawsdk.client.entity.ResponseWrapper;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

/**
 *
 */
public interface DeSerializer {

	/**
	 *
	 * @see com.alibaba.openapi.client.policy.Protocol
	 * @return
	 */
	public String supportedContentType();

	/**
	 *
	 * @param istream
	 * @param resultType
	 * @param charSet
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public <T> ResponseWrapper<T> deSerialize(InputStream istream, Class<T> resultType, String charSet)
			throws IOException, ParseException;

	/**
	 *
	 * @param inputStream
	 * @param statusCode
	 * @param charSet
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public Throwable buildException(InputStream inputStream, int statusCode, String charSet) throws IOException,
			ParseException;

	/**
	 * 
	 * @param listner
	 */
	public void registeDeSerializerListener(DeSerializerListener listner);

}
