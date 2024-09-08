package com.salesmanager.shop.store.controller.fulfillment.faced;

import com.google.common.collect.Lists;
import com.salesmanager.core.business.fulfillment.service.AdditionalServicesService;
import com.salesmanager.core.business.utils.AdditionalServicesUtils;
import com.salesmanager.core.enmus.AdditionalServiceEnums;
import com.salesmanager.core.model.fulfillment.AdditionalServices;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.listener.alibaba.tuna.fastjson.JSON;
import com.salesmanager.shop.model.fulfillment.ReadableAdditionalServices;
import com.salesmanager.shop.model.fulfillment.facade.AdditionalServicesFacade;
import com.salesmanager.shop.store.controller.fulfillment.faced.convert.AdditionalServicesConvert;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    public String queryAdditionalServicesPrice(Long id, Integer additionalServicesQuantity, Integer itemQuantity) {
        AdditionalServices additionalServices = additionalServicesService.queryAdditionalServicesById(id);
        return AdditionalServicesUtils.getPrice(additionalServices, additionalServicesQuantity, itemQuantity);
    }

    @Override
    public List<ReadableAdditionalServices> queryAdditionalServicesByIds(String additionalServicesMap, Language language) {
        if (StringUtils.isEmpty(additionalServicesMap)){
            return null;
        }
        Map<Long, Integer> additionalServicesMapFromJson =  (Map<Long, Integer>) JSON.parse(additionalServicesMap);

        Set<Long> keySet = additionalServicesMapFromJson.keySet();

        List<AdditionalServices> additionalServices = additionalServicesService.queryAdditionalServicesByIds(new ArrayList<>(keySet));
        if (CollectionUtils.isEmpty(additionalServices)){
            return null;
        }

        return additionalServices.stream().map(additionalService->{
            return AdditionalServicesConvert.convertToReadableAdditionalServices(additionalService, language, additionalServicesMapFromJson);
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
