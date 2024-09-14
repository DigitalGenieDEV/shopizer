package com.salesmanager.core.business.services.order.invoice;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.Order;
import com.salesmanager.core.model.order.OrderInvoice;

public interface OrderInvoiceService {
    Boolean saveInvoice(Order order, OrderInvoice orderInvoice, MerchantStore merchantStore);

    Boolean updateInvoice(Order order, OrderInvoice orderInvoice, MerchantStore merchantStore);
}
