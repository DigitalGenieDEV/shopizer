package com.salesmanager.core.business.services.shipping;

import javax.inject.Inject;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.shipping.MerchantShippingConfigurationRepository;
import com.salesmanager.core.business.repositories.shipping.MerchantShippingConfigurationRepositoryCustom;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.common.Criteria;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.system.MerchantShippingConfiguration;
import com.salesmanager.core.model.system.MerchantShippingConfigurationList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("merchantShippingConfigurationService")
public class MerchantShippingConfigurationServiceImpl extends
        SalesManagerEntityServiceImpl<Long, MerchantShippingConfiguration> implements
        MerchantShippingConfigurationService {

    private MerchantShippingConfigurationRepository merchantShippingConfigurationRepository;


    @Inject
    public MerchantShippingConfigurationServiceImpl(
            MerchantShippingConfigurationRepository merchantShippingConfigurationRepository) {
        super(merchantShippingConfigurationRepository);
        this.merchantShippingConfigurationRepository = merchantShippingConfigurationRepository;
    }

    @Override
    public MerchantShippingConfiguration getById(Long id) {
        return merchantShippingConfigurationRepository.getById(id);
    }

    @Override
    public List<Long> listIdByShippingType(MerchantStore store, String shippingType) throws ServiceException {
        return merchantShippingConfigurationRepository.listIdByShippingType(store, shippingType);
    }

//    @Override
//    public MerchantShippingConfiguration getMerchantShippingConfiguration(String key, MerchantStore store) throws ServiceException {
//        return merchantShippingConfigurationRepository.findByMerchantStoreAndKey(store.getId(), key);
//    }

    @Override
    public MerchantShippingConfigurationList listByStore(MerchantStore store, Criteria criteria, String shippingType, String shippingTransportationType) throws ServiceException {
        return merchantShippingConfigurationRepository.listByStore(store, criteria, shippingType, shippingTransportationType);
    }

    @Override
    public List<MerchantShippingConfiguration> listDefaultShippingByStore(MerchantStore store) throws ServiceException {
        return merchantShippingConfigurationRepository.findDefaultShippingByMerchantStore(store.getId());
    }

    @Override
    public void saveOrUpdate(MerchantShippingConfiguration entity) throws ServiceException {
        if(entity.getId() != null && entity.getId() > 0) {
            super.update(entity);
        } else {
            super.create(entity);
        }
    }

    @Override
    public void delete(MerchantShippingConfiguration merchantShippingConfiguration) throws ServiceException {
        MerchantShippingConfiguration config = merchantShippingConfigurationRepository.getOne(merchantShippingConfiguration.getId());
        if(config != null) {
            super.delete(config);
        }
    }
}