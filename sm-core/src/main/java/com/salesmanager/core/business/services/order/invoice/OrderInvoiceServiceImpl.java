package com.salesmanager.core.business.services.order.invoice;

import com.salesmanager.core.business.repositories.order.OrderInvoiceRepository;
import com.salesmanager.core.business.repositories.order.OrderRepository;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.Order;
import com.salesmanager.core.model.order.OrderInvoice;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class OrderInvoiceServiceImpl implements OrderInvoiceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderInvoiceServiceImpl.class);

    private final OrderInvoiceRepository orderInvoiceRepository;

    private final OrderRepository orderRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveInvoice(Order order, OrderInvoice orderInvoice, MerchantStore merchantStore) {
        LOGGER.info("Invoice saved. OrderId:{}, OrderInvoice:{}", order.getId(), orderInvoice);
        if (orderInvoice.getId() == null) {
            orderInvoice.setId(order.getId());
        }
        OrderInvoice invoice = orderInvoiceRepository.saveAndFlush(orderInvoice);
        orderRepository.updateOrderInvoice(order.getId(), invoice.getId());
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateInvoice(Order order, OrderInvoice orderInvoice, MerchantStore merchantStore) {
        LOGGER.info("Invoice updated. OrderId:{}, OrderInvoice:{}", order.getId(), orderInvoice);
        if (orderInvoice.getId() == null) {
            orderInvoice.setId(order.getId());
        }
        orderInvoiceRepository.save(orderInvoice);
        return true;
    }
}
