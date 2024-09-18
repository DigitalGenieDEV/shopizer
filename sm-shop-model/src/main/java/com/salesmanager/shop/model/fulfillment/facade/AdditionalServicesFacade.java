package com.salesmanager.shop.model.fulfillment.facade;

import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.fulfillment.ReadableAdditionalServices;

import java.util.List;


public interface AdditionalServicesFacade {

    ReadableAdditionalServices queryAdditionalServicesById(Long id, Language language);

    String queryAdditionalServicesPrice(Long id, Integer additionalServicesQuantity, Integer itemQuantity);

    List<ReadableAdditionalServices> queryAdditionalServicesByIds(List<Long> additionalServiceIds, Language language);

    List<ReadableAdditionalServices> queryAdditionalServicesByMerchantIdAndProductType(Long merchantId, String productType, Language language);


}

