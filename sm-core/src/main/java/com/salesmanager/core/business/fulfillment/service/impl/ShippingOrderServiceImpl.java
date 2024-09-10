package com.salesmanager.core.business.fulfillment.service.impl;

import com.salesmanager.core.business.fulfillment.service.ShippingOrderService;
import com.salesmanager.core.business.repositories.fulfillment.PageableShippingOrderProductRepository;
import com.salesmanager.core.business.repositories.fulfillment.ShippingDocumentOrderRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.fulfillment.ShippingDocumentOrder;
import com.salesmanager.core.model.order.orderproduct.OrderProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ShippingOrderServiceImpl extends SalesManagerEntityServiceImpl<Long, ShippingDocumentOrder>  implements ShippingOrderService {


    @Inject
    private PageableShippingOrderProductRepository pageableShippingOrderProductRepository;

    @Inject
    public ShippingOrderServiceImpl(ShippingDocumentOrderRepository shippingDocumentOrderRepository) {
        super(shippingDocumentOrderRepository);
        this.pageableShippingOrderProductRepository = pageableShippingOrderProductRepository;
    }


    @Override
    public Page<OrderProduct> queryShippingOrderProductList(String productName, Long orderId, int page, int count) {
        Pageable pageRequest = PageRequest.of(page, count);
        return pageableShippingOrderProductRepository.findByProductNameOrOrderId(productName, orderId, pageRequest);
    }

}

