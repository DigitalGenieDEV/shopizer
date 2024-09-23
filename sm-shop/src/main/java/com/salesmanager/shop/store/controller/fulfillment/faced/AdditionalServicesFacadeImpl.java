package com.salesmanager.shop.store.controller.fulfillment.faced;

import com.salesmanager.core.business.fulfillment.service.AdditionalServicesService;
import com.salesmanager.core.business.utils.AdditionalServicesUtils;
import com.salesmanager.core.constants.ProductType;
import com.salesmanager.core.enmus.AdditionalServiceEnums;
import com.salesmanager.core.model.fulfillment.AdditionalServices;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.fulfillment.ReadableAdditionalServices;
import com.salesmanager.shop.model.fulfillment.facade.AdditionalServicesFacade;
import com.salesmanager.shop.store.controller.fulfillment.faced.convert.AdditionalServicesConvert;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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
    public List<ReadableAdditionalServices> queryAdditionalServicesByMerchantIdAndProductType(Long merchantId, String productType, Language language) {
        String code = null;
        try {
            ProductType productTypeEnum = ProductType.valueOf(productType);
            if (ProductType.OEM.equals(productTypeEnum)) {
                code = AdditionalServiceEnums.ORIGIN_MARK.name();
            }
        } catch (Exception ignore) {
        }

        List<AdditionalServices> additionalServices;
        if (code == null) {
            additionalServices = additionalServicesService.queryAdditionalServicesByMerchantId(merchantId, language);
            additionalServices = additionalServices
                    .stream()
                    .filter(p -> !Objects.equals(p.getCode(), AdditionalServiceEnums.ORIGIN_MARK.name()))
                    .collect(Collectors.toList());
        } else {
            additionalServices = additionalServicesService.queryAdditionalServicesByMerchantIdAndCode(merchantId, code, language);
        }
        if (CollectionUtils.isEmpty(additionalServices)){
            return null;
        }
        return additionalServices
                .stream()
                .map(additionalService -> {
                    return additionalServicesConvert.convertToReadableAdditionalServices(additionalService, language);
                }).collect(Collectors.toList());
    }

}
