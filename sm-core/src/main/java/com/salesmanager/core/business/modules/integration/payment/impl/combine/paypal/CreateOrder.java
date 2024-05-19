package com.salesmanager.core.business.modules.integration.payment.impl.combine.paypal;

import com.paypal.http.HttpResponse;
import com.paypal.http.serializer.Json;
import com.paypal.orders.*;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateOrder extends PayPalClient{

    private OrderRequest buildRequestBody(String paymentIntent) {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent(paymentIntent);

        ApplicationContext applicationContext = new ApplicationContext().brandName("EXAMPLE INC").landingPage("BILLING")
                .cancelUrl("https://www.example.com").returnUrl("https://www.example.com").userAction("CONTINUE")
                .shippingPreference("SET_PROVIDED_ADDRESS");
        orderRequest.applicationContext(applicationContext);

        List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<PurchaseUnitRequest>();
        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest().referenceId("PUHF")
                .description("Sporting Goods").customId("CUST-HighFashions").softDescriptor("HighFashions")
                .amountWithBreakdown(new AmountWithBreakdown().currencyCode("USD").value("0.02")
                        .amountBreakdown(new AmountBreakdown().itemTotal(new Money().currencyCode("USD").value("0.02"))
                                .shipping(new Money().currencyCode("USD").value("0.00"))
                                .handling(new Money().currencyCode("USD").value("0.00"))
                                .taxTotal(new Money().currencyCode("USD").value("0.00"))
                                .shippingDiscount(new Money().currencyCode("USD").value("0.00"))))
                .items(new ArrayList<Item>() {
                    {
                        add(new Item().name("T-shirt").description("Green XL").sku("sku01")
                                .unitAmount(new Money().currencyCode("USD").value("0.01"))
                                .tax(new Money().currencyCode("USD").value("0.00")).quantity("1")
                                .category("PHYSICAL_GOODS"));
                        add(new Item().name("Shoes").description("Running, Size 10.5").sku("sku02")
                                .unitAmount(new Money().currencyCode("USD").value("0.01"))
                                .tax(new Money().currencyCode("USD").value("0.00")).quantity("1")
                                .category("PHYSICAL_GOODS"));
                    }
                })
                .shippingDetail(new ShippingDetail().name(new Name().fullName("John Doe"))
                        .addressPortable(new AddressPortable().addressLine1("123 Townsend St").addressLine2("Floor 6")
                                .adminArea2("San Francisco").adminArea1("CA").postalCode("94107").countryCode("US")));
        purchaseUnitRequests.add(purchaseUnitRequest);
        orderRequest.purchaseUnits(purchaseUnitRequests);
        return orderRequest;
    }

    private OrderRequest buildMinimumRequestBody() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("AUTHORIZE");
        ApplicationContext applicationContext = new ApplicationContext()
                .cancelUrl("https://www.example.com").returnUrl("https://www.example.com");
        orderRequest.applicationContext(applicationContext);
        List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<>();
        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
                .amountWithBreakdown(new AmountWithBreakdown().currencyCode("USD").value("220.00"));
        purchaseUnitRequests.add(purchaseUnitRequest);
        orderRequest.purchaseUnits(purchaseUnitRequests);
        return orderRequest;
    }


    /**
     * Method to create order
     *
     * @param debug true = print response data
     * @return HttpResponse<Order> response received from API
     * @throws IOException Exceptions from API if any
     */
    public HttpResponse<Order> createOrder(String paymentIntent, boolean debug) throws IOException {
        OrdersCreateRequest request = new OrdersCreateRequest();
        request.header("prefer","return=representation");
        request.requestBody(buildMinimumRequestBody());
        HttpResponse<Order> response = client().execute(request);
        if (debug) {
            if (response.statusCode() == 201) {
                System.out.println("Status Code: " + response.statusCode());
                System.out.println("Status: " + response.result().status());
                System.out.println("Order ID: " + response.result().id());
                System.out.println("Intent: " + response.result().checkoutPaymentIntent());
                System.out.println("Links: ");
                for (LinkDescription link : response.result().links()) {
                    System.out.println("\t" + link.rel() + ": " + link.href() + "\tCall Type: " + link.method());
                }
                System.out.println("Total Amount: " + response.result().purchaseUnits().get(0).amountWithBreakdown().currencyCode()
                        + " " + response.result().purchaseUnits().get(0).amountWithBreakdown().value());
                System.out.println("Full response body:");
                System.out.println(new JSONObject(new Json().serialize(response.result())).toString(4));
            }
        }
        return response;
    }
}
