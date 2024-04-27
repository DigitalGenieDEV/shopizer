package com.salesmanager.core.business.services.payments.combine;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.customer.order.CustomerOrder;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.payments.CombineTransaction;
import com.salesmanager.core.model.payments.Payment;

/**
 * 合并支付
 */
public interface CombinePaymentService {

    CombineTransaction processPayment(Customer customer, Payment payment, CustomerOrder customerOrder, MerchantStore store) throws ServiceException;

}
