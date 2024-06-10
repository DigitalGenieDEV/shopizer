package com.salesmanager.core.business.services.order.orderproduct;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.OrderProductCriteria;
import com.salesmanager.core.model.order.orderproduct.OrderProduct;
import com.salesmanager.core.model.order.orderproduct.OrderProductList;

public interface OrderProductService {


    OrderProductList getOrderProducts(final OrderProductCriteria criteria, MerchantStore store);

    OrderProduct getOrderProduct(final Long id, MerchantStore store);
}
