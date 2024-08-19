package com.salesmanager.shop.store.controller.order.facade;

import com.salesmanager.core.model.crossorder.logistics.SupplierCrossOrderLogistics;
import com.salesmanager.core.model.crossorder.logistics.SupplierCrossOrderLogisticsTrace;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.OrderProductCriteria;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.crossorder.ReadableSupplierCrossOrderLogistics;
import com.salesmanager.shop.model.crossorder.ReadableSupplierCrossOrderLogisticsTrace;
import com.salesmanager.shop.model.order.ReadableOrderProduct;
import com.salesmanager.shop.model.order.v1.ReadableOrderProductList;

import java.util.List;

public interface OrderProductFacade {

    ReadableOrderProductList getReadableOrderProductList(MerchantStore store, Customer customer, Integer page, Integer count, Language language);

    ReadableOrderProductList getReadableOrderProductList(OrderProductCriteria criteria, MerchantStore store, Language language);

    ReadableOrderProduct getReadableOrderProduct(Long id, MerchantStore merchantStore, Language language);

    List<ReadableSupplierCrossOrderLogistics> getLogisticsInfo(Long id);

    List<ReadableSupplierCrossOrderLogisticsTrace> getLogisticsTrace(Long id);
}
