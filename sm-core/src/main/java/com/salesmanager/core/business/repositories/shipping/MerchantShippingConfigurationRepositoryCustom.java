package com.salesmanager.core.business.repositories.shipping;

import com.salesmanager.core.model.common.Criteria;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.OrderCriteria;
import com.salesmanager.core.model.order.OrderList;
import com.salesmanager.core.model.system.MerchantShippingConfigurationList;


public interface MerchantShippingConfigurationRepositoryCustom {

	MerchantShippingConfigurationList listByStore(MerchantStore store, Criteria criteria);
}
