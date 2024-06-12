package com.salesmanager.core.business.repositories.order.orderproduct;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.OrderProductCriteria;
import com.salesmanager.core.model.order.orderproduct.OrderProductList;

public interface OrderProductRepositoryCustom {

    OrderProductList listOrderProducts(MerchantStore store, OrderProductCriteria criteria);
}
