package com.salesmanager.core.business.services.shipping;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.model.common.Criteria;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.system.MerchantShippingConfiguration;
import com.salesmanager.core.model.system.MerchantShippingConfigurationList;

import java.util.List;

public interface MerchantShippingConfigurationService {

    MerchantShippingConfiguration getById(Long id);


    List<Long> listIdByShippingType(MerchantStore store, String shippingType) throws ServiceException;


//    MerchantShippingConfiguration getMerchantShippingConfiguration(String key, MerchantStore store) throws ServiceException;

    MerchantShippingConfigurationList listByStore(MerchantStore store, Criteria criteria, String shippingType, String shippingTransportationType) throws ServiceException;

    List<MerchantShippingConfiguration> listDefaultShippingByStore(MerchantStore store) throws ServiceException;

    void saveOrUpdate(MerchantShippingConfiguration entity) throws ServiceException;

    void delete(MerchantShippingConfiguration merchantShippingConfiguration) throws ServiceException;
}
