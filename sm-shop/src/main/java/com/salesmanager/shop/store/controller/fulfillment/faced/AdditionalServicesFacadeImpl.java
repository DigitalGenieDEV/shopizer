package com.salesmanager.shop.store.controller.fulfillment.faced;

import com.salesmanager.core.business.fulfillment.service.AdditionalServicesService;
import com.salesmanager.core.business.utils.AdditionalServicesUtils;
import com.salesmanager.core.model.fulfillment.AdditionalServices;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.fulfillment.ReadableAdditionalServices;
import com.salesmanager.shop.model.fulfillment.facade.AdditionalServicesFacade;
import com.salesmanager.shop.store.controller.fulfillment.faced.convert.AdditionalServicesConvert;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdditionalServicesFacadeImpl implements AdditionalServicesFacade {

    @Autowired
    private AdditionalServicesService additionalServicesService;

    @Autowired
    private AdditionalServicesConvert additionalServicesConvert;


    @Override
    public ReadableAdditionalServices queryAdditionalServicesById(Long id, Language language) {
        AdditionalServices additionalServices = additionalServicesService.queryAdditionalServicesById(id);
        return additionalServicesConvert.convertToReadableAdditionalServices(additionalServices, language);
    }

    @Override
    public String queryAdditionalServicesPrice(Long id, Integer additionalServicesQuantity, Integer itemQuantity) {
        AdditionalServices additionalServices = additionalServicesService.queryAdditionalServicesById(id);
        return AdditionalServicesUtils.getPrice(additionalServices, additionalServicesQuantity, itemQuantity);
    }

    @Override
    public List<ReadableAdditionalServices> queryAdditionalServicesByIds(List<Long> additionalServiceIds, Language language) {
        if (CollectionUtils.isEmpty(additionalServiceIds)){
            return null;
        }

        List<AdditionalServices> additionalServices = additionalServicesService.queryAdditionalServicesByIds(additionalServiceIds);
        if (CollectionUtils.isEmpty(additionalServices)){
            return null;
        }

        return additionalServices.stream().map(additionalService->{
            return additionalServicesConvert
                    .convertToReadableAdditionalServices(additionalService, language);
        }).collect(Collectors.toList());
    }

    @Override
    public List<ReadableAdditionalServices> queryAdditionalServicesByMerchantId(Long merchantId, Language language) {
        List<AdditionalServices> additionalServices = additionalServicesService.queryAdditionalServicesByMerchantId(merchantId, language);
        if (CollectionUtils.isEmpty(additionalServices)){
            return null;
        }
        return additionalServices.stream().map(additionalService->{
            return additionalServicesConvert.convertToReadableAdditionalServices(additionalService, language);
        }).collect(Collectors.toList());
    }



}
