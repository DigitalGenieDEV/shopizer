package com.salesmanager.shop.store.controller.fulfillment.faced;

import com.salesmanager.core.business.fulfillment.service.AdditionalServicesService;
import com.salesmanager.core.model.fulfillment.AdditionalServices;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.fulfillment.ReadableAdditionalServices;
import com.salesmanager.shop.model.fulfillment.facade.AdditionalServicesFacade;
import com.salesmanager.shop.store.controller.fulfillment.faced.convert.AdditionalServicesConvert;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdditionalServicesFacadeImpl implements AdditionalServicesFacade {

    @Autowired
    private AdditionalServicesService additionalServicesService;

    @Override
    public ReadableAdditionalServices queryAdditionalServicesById(Long id, Language language) {
        AdditionalServices additionalServices = additionalServicesService.queryAdditionalServicesById(id);
        return AdditionalServicesConvert.convertToReadableAdditionalServices(additionalServices, language);
    }

    @Override
    public List<ReadableAdditionalServices> queryAdditionalServicesByIds(String ids, Language language) {
        if (StringUtils.isEmpty(ids)){
            return null;
        }
        List<AdditionalServices> additionalServices = additionalServicesService.queryAdditionalServicesByIds(ids);
        if (CollectionUtils.isEmpty(additionalServices)){
            return null;
        }
        return additionalServices.stream().map(additionalService->{
            return AdditionalServicesConvert.convertToReadableAdditionalServices(additionalService, language);
        }).collect(Collectors.toList());
    }

    @Override
    public List<ReadableAdditionalServices> queryAdditionalServicesByMerchantId(Long merchantId, Language language) {
        List<AdditionalServices> additionalServices = additionalServicesService.queryAdditionalServicesByMerchantId(merchantId, language);
        if (CollectionUtils.isEmpty(additionalServices)){
            return null;
        }
        return additionalServices.stream().map(additionalService->{
            return AdditionalServicesConvert.convertToReadableAdditionalServices(additionalService, language);
        }).collect(Collectors.toList());
    }
}
