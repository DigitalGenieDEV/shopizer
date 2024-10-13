package com.salesmanager.core.business.modules.integration.payment.impl.combine;

import com.salesmanager.core.business.services.payments.combine.CombineTransactionService;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.customer.order.CustomerOrder;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.payments.CombineTransaction;
import com.salesmanager.core.model.payments.Payment;
import com.salesmanager.core.model.payments.PaymentType;
import com.salesmanager.core.model.payments.TransactionType;
import com.salesmanager.core.model.system.IntegrationConfiguration;
import com.salesmanager.core.model.system.IntegrationModule;
import com.salesmanager.core.modules.integration.IntegrationException;
import com.salesmanager.core.modules.integration.payment.model.CombinePaymentModule;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public class KcpPayment implements CombinePaymentModule {

    @Inject
    private CombineTransactionService combineTransactionService;

    @Override
    public void validateModuleConfiguration(IntegrationConfiguration integrationConfiguration, MerchantStore store) throws IntegrationException {

    }

    @Override
    public CombineTransaction initTransaction(MerchantStore store, Customer customer, CustomerOrder customerOrder, BigDecimal amount, Payment payment, IntegrationConfiguration configuration, IntegrationModule module) throws IntegrationException {
        CombineTransaction combineTransaction = new CombineTransaction();
        combineTransaction.setAmount(amount);
        combineTransaction.setCustomerOrder(customerOrder);
        combineTransaction.setTransactionDate(new Date());
        combineTransaction.setTransactionType(TransactionType.AUTHORIZE);
        combineTransaction.setPaymentType(PaymentType.KCP);
        combineTransaction.getTransactionDetails().put("KCP_SITE_CD", "T0000");
        combineTransaction.getTransactionDetails().put("KCP_SITE_NAME", "TEST SITE");
        combineTransaction.getTransactionDetails().put("KCP_PAY_METHOD", "001000000000");
        combineTransaction.getTransactionDetails().put("KCP_AMOUNT", "" + amount);
        return combineTransaction;
    }

    @Override
    public CombineTransaction authorize(MerchantStore store, Customer customer, CustomerOrder order, BigDecimal amount, Payment payment, IntegrationConfiguration configuration, IntegrationModule module) throws IntegrationException {
        return null;
    }

    @Override
    public CombineTransaction capture(MerchantStore store, Customer customer, CustomerOrder order, BigDecimal amount, CombineTransaction capturableTransaction, IntegrationConfiguration configuration, IntegrationModule module) throws IntegrationException {

        return null;
    }

    @Override
    public CombineTransaction authorizeAndCapture(MerchantStore store, Customer customer, CustomerOrder customerOrder, BigDecimal amount, Payment payment, IntegrationConfiguration configuration, IntegrationModule module) throws IntegrationException {
        CombineTransaction combineTransaction = new CombineTransaction();
        combineTransaction.setAmount(amount);
        combineTransaction.setCustomerOrder(customerOrder);
        combineTransaction.setTransactionDate(new Date());
        combineTransaction.setTransactionType(TransactionType.AUTHORIZECAPTURE);
        combineTransaction.setPaymentType(PaymentType.KCP);

        Map<String, String> metaData = payment.getPaymentMetaData();

        combineTransaction.getTransactionDetails().put("KCP_ENC_DATA", metaData.get("enc_data"));
        combineTransaction.getTransactionDetails().put("KCP_ENC_INFO", metaData.get("enc_info"));
        combineTransaction.getTransactionDetails().put("KCP_ORDR_IDXX", metaData.get("ordr_idxx"));
        combineTransaction.getTransactionDetails().put("KCP_TRAN_CD", metaData.get("tran_cd"));
        combineTransaction.getTransactionDetails().put("KCP_RES_MSG", metaData.get("res_msg"));
        combineTransaction.getTransactionDetails().put("KCP_RES_CD", metaData.get("res_cd"));

        return combineTransaction;
    }

    @Override
    public CombineTransaction refund(boolean partial, MerchantStore store, CombineTransaction transaction, CustomerOrder order, BigDecimal amount, IntegrationConfiguration configuration, IntegrationModule module) throws IntegrationException {
        return null;
    }
}
