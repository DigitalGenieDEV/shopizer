package com.salesmanager.core.business.modules.integration.payment.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.salesmanager.core.business.modules.integration.payment.impl.combine.nicepay.NicepayResponse;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.Order;
import com.salesmanager.core.model.payments.Payment;
import com.salesmanager.core.model.payments.PaymentType;
import com.salesmanager.core.model.payments.Transaction;
import com.salesmanager.core.model.payments.TransactionType;
import com.salesmanager.core.model.shoppingcart.ShoppingCartItem;
import com.salesmanager.core.model.system.IntegrationConfiguration;
import com.salesmanager.core.model.system.IntegrationModule;
import com.salesmanager.core.modules.integration.IntegrationException;
import com.salesmanager.core.modules.integration.payment.model.PaymentModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.*;

@Component(value = "nicepay")
public class NicepayPayment implements PaymentModule {

    private static Logger LOGGER = LoggerFactory.getLogger(NicepayPayment.class);

    private final String BASE_PAYMENT_URL = "https://sandbox-api.nicepay.co.kr/v1/payments/";

    @Inject
    private RestTemplate restTemplate;

    @Override
    public void validateModuleConfiguration(IntegrationConfiguration integrationConfiguration, MerchantStore store) throws IntegrationException {

    }

    @Override
    public Transaction initTransaction(MerchantStore store, Customer customer, BigDecimal amount, Payment payment, IntegrationConfiguration configuration, IntegrationModule module) throws IntegrationException {
        return null;
    }

    @Override
    public Transaction authorize(MerchantStore store, Customer customer, List<ShoppingCartItem> items, BigDecimal amount, Payment payment, IntegrationConfiguration configuration, IntegrationModule module) throws IntegrationException {
        return null;
    }

    @Override
    public Transaction capture(MerchantStore store, Customer customer, Order order, Transaction capturableTransaction, IntegrationConfiguration configuration, IntegrationModule module) throws IntegrationException {
        return null;
    }

    @Override
    public Transaction authorizeAndCapture(MerchantStore store, Customer customer, Order order, List<ShoppingCartItem> items, BigDecimal amount, Payment payment, IntegrationConfiguration configuration, IntegrationModule module) throws IntegrationException {
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setOrder(order);
        transaction.setTransactionDate(new Date());
        transaction.setTransactionType(TransactionType.AUTHORIZECAPTURE);
        transaction.setPaymentType(PaymentType.NICEPAY);

        Map<String, String> metaData = payment.getPaymentMetaData();

        transaction.getTransactionDetails().put("tid", metaData.get("tid"));
        transaction.getTransactionDetails().put("signature", metaData.get("signature"));
        transaction.getTransactionDetails().put("status", metaData.get("status"));
        transaction.getTransactionDetails().put("ediDate", metaData.get("ediDate"));
        transaction.getTransactionDetails().put("paidAt", metaData.get("paidAt"));
        transaction.getTransactionDetails().put("channel", metaData.get("channel"));
        transaction.getTransactionDetails().put("currency", metaData.get("currency"));
        transaction.getTransactionDetails().put("amount", metaData.get("amount"));
        transaction.getTransactionDetails().put("orderId", metaData.get("orderId"));
        transaction.getTransactionDetails().put("paymentMode", metaData.get("paymentMode"));

        return transaction;
    }

    @Override
    public Transaction refund(boolean partial, MerchantStore store, Transaction lastTransaction, Order order, BigDecimal amount, IntegrationConfiguration configuration, IntegrationModule module, String reason) throws IntegrationException {

        String tid = lastTransaction.getTransactionDetails().get("tid");
        String orderId = lastTransaction.getTransactionDetails().get("orderId");

        NicepayResponse nicepayResponse = cancelNicepayOrder(com.salesmanager.core.business.modules.integration.payment.impl.combine.NicepayPayment.CLIENT_ID,
                com.salesmanager.core.business.modules.integration.payment.impl.combine.NicepayPayment.CLIENT_SECRET, tid, orderId, reason, String.valueOf(amount.intValue()));

        String resultCode = nicepayResponse.getResultCode();
        if (!resultCode.equals("0000")) {
            throw new RuntimeException("Nicepay cancel order exception, reason:" + nicepayResponse.getResultMsg());
        }

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setOrder(order);
        transaction.setTransactionDate(new Date());
        transaction.setTransactionType(TransactionType.AUTHORIZECAPTURE);
        transaction.setPaymentType(PaymentType.NICEPAY);
        transaction.getTransactionDetails().put("tid", nicepayResponse.getTid());
        transaction.getTransactionDetails().put("orderId", nicepayResponse.getOrderId());
        transaction.getTransactionDetails().put("signature", nicepayResponse.getSignature());
        transaction.getTransactionDetails().put("status", nicepayResponse.getStatus());
        transaction.getTransactionDetails().put("ediDate", nicepayResponse.getEdiDate());
        transaction.getTransactionDetails().put("cancelledAt", nicepayResponse.getCancelledAt());
        transaction.getTransactionDetails().put("amount", String.valueOf(nicepayResponse.getAmount()));
        transaction.getTransactionDetails().put("channel", nicepayResponse.getChannel());
        transaction.getTransactionDetails().put("currency", nicepayResponse.getCurrency());
        transaction.getTransactionDetails().put("raw", nicepayResponse.toString());


        return transaction;
    }

    private NicepayResponse cancelNicepayOrder(String clientId, String secretKey, String tid, String orderId, String reason, String amount) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString((clientId + ":" + secretKey).getBytes()));
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("reason", reason);
        requestBody.put("orderId", orderId);
        requestBody.put("amount", amount);

        ObjectMapper objectMapper = new ObjectMapper();
        HttpEntity<String> request = null;
        try {
            request = new HttpEntity<>(objectMapper.writeValueAsString(requestBody), headers);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        ResponseEntity<NicepayResponse> responseEntity = restTemplate.postForEntity(BASE_PAYMENT_URL + tid + "/cancel", request, NicepayResponse.class);
        LOGGER.info("payment nicepay cancel response:" + responseEntity);

        return responseEntity.getBody();
    }

}
