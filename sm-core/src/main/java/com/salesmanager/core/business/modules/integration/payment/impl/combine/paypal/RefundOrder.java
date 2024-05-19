package com.salesmanager.core.business.modules.integration.payment.impl.combine.paypal;

import com.paypal.http.HttpResponse;
import com.paypal.http.serializer.Json;
import com.paypal.payments.*;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;

public class RefundOrder extends PayPalClient{

    /**
     * Creating empty body for Refund request. This request body can be created with
     * correct values as per the need.
     *
     * @return OrderRequest request with empty body
     */
    public RefundRequest buildRequestBody(BigDecimal amount) {
        RefundRequest refundRequest = new RefundRequest();
        Money money = new Money();
        money.currencyCode("USD");
        money.value(String.valueOf(amount));
        refundRequest.amount(money);
        return refundRequest;
    }

    /**
     * Method to Refund the Capture. valid capture Id should be passed.
     *
     * @param captureId Capture ID from authorizeOrder response
     * @param debug     true = print response data
     * @return HttpResponse<Capture> response received from API
     * @throws IOException Exceptions from API if any
     */
    public HttpResponse<Refund> refundOrder(String captureId, BigDecimal amount, boolean debug) throws IOException {
        CapturesRefundRequest request = new CapturesRefundRequest(captureId);
        request.prefer("return=representation");
        request.requestBody(buildRequestBody(amount));
        HttpResponse<Refund> response = client().execute(request);
        if (debug) {
            System.out.println("Status Code: " + response.statusCode());
            System.out.println("Status: " + response.result().status());
            System.out.println("Refund Id: " + response.result().id());
            System.out.println("Links: ");
            for (LinkDescription link : response.result().links()) {
                System.out.println("\t" + link.rel() + ": " + link.href() + "\tCall Type: " + link.method());
            }
            System.out.println("Full response body:");
            System.out.println(new JSONObject(new Json().serialize(response.result())).toString(4));
        }
        return response;
    }

}
