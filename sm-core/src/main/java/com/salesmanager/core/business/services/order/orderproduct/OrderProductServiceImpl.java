package com.salesmanager.core.business.services.order.orderproduct;

import com.salesmanager.core.business.repositories.order.orderproduct.OrderProductRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.OrderProductCriteria;
import com.salesmanager.core.model.order.orderproduct.OrderProduct;
import com.salesmanager.core.model.order.orderproduct.OrderProductDownload;
import com.salesmanager.core.model.order.orderproduct.OrderProductList;
import org.apache.commons.lang3.Validate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("orderProductService")
public class OrderProductServiceImpl extends SalesManagerEntityServiceImpl<Long, OrderProduct> implements OrderProductService {

    private final OrderProductRepository orderProductRepository;

    public OrderProductServiceImpl(OrderProductRepository repository) {
        super(repository);
        this.orderProductRepository = repository;
    }

    @Override
    public OrderProductList getOrderProducts(OrderProductCriteria criteria, MerchantStore store) {
        return orderProductRepository.listOrderProducts(store, criteria);
    }


    @Override
    public List<OrderProduct> getOrderProducts(Long orderId) {
        return orderProductRepository.findListByOrderId(orderId);
    }


    @Override
    public OrderProduct getOrderProduct(Long orderProductId, MerchantStore store) {
        Validate.notNull(orderProductId, "OrderProduct id cannot be null");
        Validate.notNull(store, "Store cannot be null");
        return orderProductRepository.findOne(orderProductId, store.getId());
    }

    @Override
    public OrderProduct getOrderProduct(Long id) {
        return orderProductRepository.getById(id);
    }
}
