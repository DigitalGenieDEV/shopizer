package com.salesmanager.core.business.services.payments.combine;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.order.OrderService;
import com.salesmanager.core.business.services.payments.TransactionService;
import com.salesmanager.core.business.services.system.MerchantConfigurationService;
import com.salesmanager.core.business.services.system.ModuleConfigurationService;
import com.salesmanager.core.business.utils.CoreConfiguration;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.customer.order.CustomerOrder;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.payments.CombineTransaction;
import com.salesmanager.core.model.payments.Payment;
import com.salesmanager.core.model.payments.PaymentType;
import com.salesmanager.core.model.payments.TransactionType;
import com.salesmanager.core.modules.integration.payment.model.PaymentModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.util.Date;
import java.util.Map;

@Service("combinePaymentService")
public class CombinePaymentServiceImpl implements CombinePaymentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CombinePaymentServiceImpl.class);

    @Inject
    private MerchantConfigurationService merchantConfigurationService;

    @Inject
    private ModuleConfigurationService moduleConfigurationService;

    @Inject
    private TransactionService transactionService;

    @Inject
    private OrderService orderService;

    @Inject
    private CoreConfiguration coreConfiguration;

    @Inject
    @Resource(name="paymentModules")
    private Map<String,PaymentModule> paymentModules;


    @Override
    public CombineTransaction processPayment(Customer customer, Payment payment, CustomerOrder customerOrder, MerchantStore store) throws ServiceException {
        CombineTransaction combineTransaction = new CombineTransaction();
        combineTransaction.setAmount(customerOrder.getTotal());
        combineTransaction.setTransactionDate(new Date());
        combineTransaction.setTransactionType(TransactionType.AUTHORIZECAPTURE);
        combineTransaction.setPaymentType(PaymentType.FREE);

        return combineTransaction;
    }
}
