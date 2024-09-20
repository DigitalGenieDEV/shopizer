package com.salesmanager.shop.store.controller.dataservice.facade;

import java.util.Map;

import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.*;

/**
 * Different functions for data service
 * @author sword
 *
 */

public interface DataServiceFacade {

	/**
	 * BI: data statistics for product domain
	 * @param biProductInfoRequest
	 * @param language
	 * @return
	 * @throws Exception
	 */
	Map<String, Integer> BIProductInfoV2(BIProductInfoRequestV2 biProductInfoRequest, Language language);

	/**
	 * BI: data statistics for member domain
	 * @param biMemberInfoRequest
	 * @param language
	 * @return
	 * @throws Exception
	 */
	Map<String, Integer> BIMemberInfoV2(BIMemberInfoRequestV2 biMemberInfoRequest, Language language);

	/**
	 * BI: data statistics for buyer domain
	 * @param biBuyerInfoRequest
	 * @param language
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> BIBuyerInfoV2(BIBuyerInfoRequestV2 biBuyerInfoRequest, Language language);

	/**
	 * BI: data statistics for sale domain
	 * @param biSaleInfoRequest
	 * @param language
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> BISaleInfoV2(BISaleInfoRequestV2 biSaleInfoRequest, Language language);

	/**
	 * BI: data statistics for traffic domain
	 * @param biTrafficInfoRequest
	 * @param language
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> BITrafficInfoV2(BITrafficInfoRequestV2 biTrafficInfoRequest, Language language);
}
