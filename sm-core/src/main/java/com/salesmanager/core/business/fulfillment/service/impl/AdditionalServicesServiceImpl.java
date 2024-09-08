package com.salesmanager.core.business.fulfillment.service.impl;

import com.salesmanager.core.business.fulfillment.service.AdditionalServicesService;
import com.salesmanager.core.business.repositories.fulfillment.AdditionalServicesRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.fulfillment.AdditionalServices;
import com.salesmanager.core.model.reference.language.Language;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service("additionalServicesService")
public class AdditionalServicesServiceImpl extends SalesManagerEntityServiceImpl<Long, AdditionalServices> implements AdditionalServicesService {

    private AdditionalServicesRepository additionalServicesRepository;

    @Inject
    public AdditionalServicesServiceImpl(AdditionalServicesRepository additionalServicesRepository) {
        super(additionalServicesRepository);
        this.additionalServicesRepository = additionalServicesRepository;
    }


    @Override
    public Long saveAdditionalServices(AdditionalServices additionalServices) {
        additionalServicesRepository.save(additionalServices);
        return additionalServices.getId();
    }

    @Override
    public AdditionalServices queryAdditionalServicesById(Long id) {
        return additionalServicesRepository.getById(id);
    }

    @Override
    public List<AdditionalServices> queryAdditionalServicesByIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return null;
        }
        List<AdditionalServices> additionalServices = new ArrayList<>();
        for (Long id : ids){
            additionalServices.add(queryAdditionalServicesById(id));
        }
        return additionalServices;
    }

    @Override
    public List<AdditionalServices> queryAdditionalServicesByMerchantId(Long merchantId, Language language) {
        return additionalServicesRepository.queryAdditionalServicesByMerchantId(merchantId);
    }
}

