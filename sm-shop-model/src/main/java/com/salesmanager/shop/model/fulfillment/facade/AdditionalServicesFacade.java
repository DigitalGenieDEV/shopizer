package com.salesmanager.shop.model.fulfillment.facade;

import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.fulfillment.ReadableAdditionalServices;

import java.util.List;


public interface AdditionalServicesFacade {

    ReadableAdditionalServices queryAdditionalServicesById(Long id, Language language);

    List<ReadableAdditionalServices> queryAdditionalServicesByIds(String ids, Language language);

    List<ReadableAdditionalServices> queryAdditionalServicesByMerchantId(Long merchantId, Language language);

}

