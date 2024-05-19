package com.salesmanager.core.business.modules.integration.payment.impl.combine.paypal;

import com.paypal.http.HttpResponse;
import com.paypal.http.serializer.Json;
import com.paypal.orders.*;
import com.paypal.payments.AuthorizationsCaptureRequest;
import org.json.JSONObject;

import java.io.IOException;

public class CaptureOrder extends PayPalClient {

    /**
     * Creating empty body for capture request. We can set the payment source if
     * required.
     *
     * @return OrderRequest request with empty body
     */
    public OrderRequest buildRequestBody() {
        return new OrderRequest();
    }

    public HttpResponse<Order> captureOrder(String orderId, boolean debug) throws IOException {
        OrdersCaptureRequest request = new OrdersCaptureRequest(orderId);
        request.requestBody(buildRequestBody());
        HttpResponse<Order> response = client().execute(request);
        if (debug) {
            System.out.println("Status Code: " + response.statusCode());
            System.out.println("Status: " + response.result().status());
            System.out.println("Order ID: " + response.result().id());
            System.out.println("Links: ");
            for (LinkDescription link : response.result().links()) {
                System.out.println("\t" + link.rel() + ": " + link.href());
            }
            System.out.println("Capture ids:");
            for (PurchaseUnit purchaseUnit : response.result().purchaseUnits()) {
                for (Capture capture : purchaseUnit.payments().captures()) {
                    System.out.println("\t" + capture.id());
                }
            }
            System.out.println("Buyer: ");
            Payer buyer = response.result().payer();
            System.out.println("\tEmail Address: " + buyer.email());
            System.out.println("\tName: " + buyer.name().givenName() + " " + buyer.name().surname());
            System.out.println("Full response body:");
            System.out.println(new JSONObject(new Json().serialize(response.result())).toString(4));
        }
        return response;
    }


    public HttpResponse<com.paypal.payments.Capture> captureAuthOrder(String authId, boolean debug) throws IOException {
        AuthorizationsCaptureRequest request = new AuthorizationsCaptureRequest(authId);
        request.requestBody(buildRequestBody());
        HttpResponse<com.paypal.payments.Capture> response = client().execute(request);
        if (debug) {
            System.out.println("Status Code: " + response.statusCode());
            System.out.println("Status: " + response.result().status());
            System.out.println("Capture ID: " + response.result().id());
            System.out.println("Links: ");
            for (com.paypal.payments.LinkDescription link : response.result().links()) {
                System.out.println("\t" + link.rel() + ": " + link.href() + "\tCall Type: " + link.method());
            }
            System.out.println("Full response body:");
            System.out.println(new JSONObject(new Json().serialize(response.result())).toString(4));
        }
        return response;
    }

}
