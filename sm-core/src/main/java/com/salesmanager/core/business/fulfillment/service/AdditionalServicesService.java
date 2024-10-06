package com.salesmanager.core.business.fulfillment.service;

import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.fulfillment.AdditionalServices;
import com.salesmanager.core.model.reference.language.Language;

import java.util.List;
import java.util.Map;


public interface AdditionalServicesService extends SalesManagerEntityService<Long, AdditionalServices> {


    Long saveAdditionalServices(AdditionalServices additionalServices);

    AdditionalServices queryAdditionalServicesById(Long id);

    List<AdditionalServices> queryAdditionalServicesByIds(List<Long> ids);


    Map<Long, AdditionalServices> queryAdditionalServicesByMerchantIds(Long merchantId);


    List<AdditionalServices> queryAdditionalServicesByMerchantId(Long merchantId, Language language);

    List<AdditionalServices> queryAdditionalServicesByMerchantIdAndCode(Long merchantId, String code, Language language);

}

