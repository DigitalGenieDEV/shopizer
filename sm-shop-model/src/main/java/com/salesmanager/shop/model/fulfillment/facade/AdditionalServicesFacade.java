package com.salesmanager.shop.model.fulfillment.facade;

import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.fulfillment.ReadableAdditionalServices;

import java.util.List;
import java.util.Map;


public interface AdditionalServicesFacade {

    ReadableAdditionalServices queryAdditionalServicesById(Long id, Language language);

    String queryAdditionalServicesPrice(Long id, Integer additionalServicesQuantity, Integer itemQuantity);

    List<ReadableAdditionalServices> queryAdditionalServicesByIds(String additionalServicesMap, Language language);

    List<ReadableAdditionalServices> queryAdditionalServicesByMerchantId(Long merchantId, Language language);

}

