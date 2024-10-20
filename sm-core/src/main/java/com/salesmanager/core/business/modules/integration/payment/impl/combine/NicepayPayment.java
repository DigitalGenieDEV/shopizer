package com.salesmanager.core.business.modules.integration.payment.impl.combine;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.modules.integration.payment.impl.combine.nicepay.NicepayResponse;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.customer.order.CustomerOrder;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.Order;
import com.salesmanager.core.model.payments.CombineTransaction;
import com.salesmanager.core.model.payments.Payment;
import com.salesmanager.core.model.payments.PaymentType;
import com.salesmanager.core.model.payments.TransactionType;
import com.salesmanager.core.model.system.IntegrationConfiguration;
import com.salesmanager.core.model.system.IntegrationModule;
import com.salesmanager.core.modules.integration.IntegrationException;
import com.salesmanager.core.modules.integration.payment.model.CombinePaymentModule;
import org.apache.commons.lang3.StringUtils;
import org.apache.tools.ant.taskdefs.Nice;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NicepayPayment implements CombinePaymentModule {

    private static final String BASE_URL = "https://sandbox-api.nicepay.co.kr/v1/payments/";

    private static final String NP_SUCCESS_RESULT_CODE = "0000";

    public static final String CLIENT_ID = "S2_287d1c8fcf9a4598b28d4360d5758275";

    public static final String CLIENT_SECRET = "13bc186c457445cd816e8494a236429c";

    @Override
    public void validateModuleConfiguration(IntegrationConfiguration integrationConfiguration, MerchantStore store) throws IntegrationException {

    }

    @Override
    public CombineTransaction initTransaction(MerchantStore store, Customer customer, CustomerOrder customerOrder, BigDecimal amount, Payment payment, IntegrationConfiguration configuration, IntegrationModule module) throws IntegrationException {
        CombineTransaction combineTransaction = new CombineTransaction();
        combineTransaction.setAmount(amount);
        combineTransaction.setCustomerOrder(customerOrder);
        combineTransaction.setTransactionDate(new Date());
        combineTransaction.setTransactionType(TransactionType.INIT);
        combineTransaction.setPaymentType(PaymentType.NICEPAY);
        combineTransaction.setPayOrderNo(customerOrder.getOrderNo());
        List<Long> orderList = customerOrder.getOrders().stream().map(Order::getId).collect(Collectors.toList());
        combineTransaction.setRelationOrderIdList(orderList);

        combineTransaction.getTransactionDetails().put("CLIENT_ID", CLIENT_ID);
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
    public CombineTransaction authorizeAndCapture(MerchantStore store, Customer customer, CustomerOrder order, BigDecimal amount, Payment payment, IntegrationConfiguration configuration, IntegrationModule module) throws IntegrationException {
        CombineTransaction combineTransaction = new CombineTransaction();
        combineTransaction.setAmount(amount);
        combineTransaction.setCustomerOrder(order);
        combineTransaction.setTransactionDate(new Date());
        combineTransaction.setTransactionType(TransactionType.AUTHORIZECAPTURE);
        combineTransaction.setPaymentType(PaymentType.NICEPAY);

        Map<String, String> metaData = payment.getPaymentMetaData();

        combineTransaction.getTransactionDetails().put("tid", metaData.get("tid"));
        combineTransaction.getTransactionDetails().put("signature", metaData.get("signature"));
        combineTransaction.getTransactionDetails().put("status", metaData.get("status"));
        combineTransaction.getTransactionDetails().put("ediDate", metaData.get("ediDate"));
        combineTransaction.getTransactionDetails().put("paidAt", metaData.get("paidAt"));
        combineTransaction.getTransactionDetails().put("channel", metaData.get("channel"));
        combineTransaction.getTransactionDetails().put("currency", metaData.get("currency"));
        combineTransaction.getTransactionDetails().put("amount", metaData.get("amount"));
        combineTransaction.getTransactionDetails().put("orderId", metaData.get("orderId"));
        combineTransaction.getTransactionDetails().put("raw", metaData.get("raw"));


        return combineTransaction;
    }

//    public static NicepayResponse makePaymentRequest(String txtid, String clientKey, String secretKey, String amount) throws Exception {
//        String urlString = BASE_URL + txtid;
//        URL url = new URL(urlString);
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestMethod("POST");
//        conn.setRequestProperty("Content-Type", "application/json");
//
//        String authKey = Base64.getEncoder().encodeToString((clientKey + ":" + secretKey).getBytes(StandardCharsets.UTF_8));
//        conn.setRequestProperty("Authorization", "Basic " + authKey);
//        conn.setDoOutput(true);
//
//        String jsonInputString = "{ \"amount\" : " + amount + " }";
//
//        try (OutputStream os = conn.getOutputStream()) {
//            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
//            os.write(input, 0, input.length);
//        }
//
//        int responseCode = conn.getResponseCode();
//        if (responseCode == HttpURLConnection.HTTP_OK) {
//            ObjectMapper mapper = new ObjectMapper();
//            return mapper.readValue(conn.getInputStream(), NicepayResponse.class);
//        } else {
//            throw new RuntimeException("Failed : HTTP error code : " + responseCode);
//        }
//    }

    @Override
    public CombineTransaction refund(boolean partial, MerchantStore store, CombineTransaction transaction, CustomerOrder order, BigDecimal amount, IntegrationConfiguration configuration, IntegrationModule module) throws IntegrationException {
        return null;
    }
}
