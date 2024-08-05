package com.salesmanager.core.business.fulfillment.service;

import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.fulfillment.AdditionalServices;
import com.salesmanager.core.model.reference.language.Language;

import java.util.List;


public interface AdditionalServicesService extends SalesManagerEntityService<Long, AdditionalServices> {


    Long saveAdditionalServices(AdditionalServices additionalServices);

    AdditionalServices queryAdditionalServicesById(Long id);

    List<AdditionalServices> queryAdditionalServicesByIds(String ids);

    List<AdditionalServices> queryAdditionalServicesByMerchantId(Long merchantId, Language language);

}

