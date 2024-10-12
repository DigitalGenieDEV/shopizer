package com.salesmanager.shop.store.api.v1.payment;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.salesmanager.core.business.services.customer.CustomerService;
import com.salesmanager.core.business.services.customer.order.CustomerOrderService;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.customer.order.transaction.ReadableCombineTransaction;
import com.salesmanager.shop.store.controller.payment.facade.PaymentAuthorizeFacade;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.annotations.ApiIgnore;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1")
@Api(tags = { "Payment Authorize api" })
@SwaggerDefinition(tags = { @Tag(name = "Payment management resources", description = "Payment management resources") })
public class PaymentAuthorizeApi {

    private static final Logger LOG = LoggerFactory.getLogger(PaymentAuthorizeApi.class);

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Inject
    private PaymentAuthorizeFacade paymentAuthorizeFacade;

    @Inject
    private CustomerOrderService customerOrderService;

    @Inject
    private CustomerService customerService;

//    private final String CLIENT_ID = "S2_af4543a0be4d49a98122e01ec2059a56";
    private final String SECRET_KEY = "9eb85607103646da9f9c02b128f2e5ee";
    private final String BASE_PAYMENT_URL = "https://sandbox-api.nicepay.co.kr/v1/payments/";
    private final String REDIRECT_CHECK_IN = "http://www.sourcingroot.com/checkin";
    private final String REDIRECT_CHECK_NG = "http://www.sourcingroot.com/checkng";

    private final String SUCCESS_REDIRECT_URL = "";



//    @GetMapping(value = "/server_auth")
//    public ResponseEntity serverAuth(
//            @RequestParam String tid,
//            @RequestParam String amount,
//                                            @ApiIgnore MerchantStore merchantStore,
//                                          @ApiIgnore Language language,
//            HttpServletResponse response) throws Exception {
//        System.out.println("hahah");
////        HttpHeaders headers = new HttpHeaders();
////        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString((CLIENT_ID + ":" + SECRET_KEY).getBytes()));
////        headers.setContentType(MediaType.APPLICATION_JSON);
////
////        Map<String, Object> AuthenticationMap = new HashMap<>();
////        AuthenticationMap.put("amount", String.valueOf(amount));
////
////        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(AuthenticationMap), headers);
////
////        ResponseEntity<JsonNode> responseEntity = restTemplate.postForEntity(BASE_PAYMENT_URL + tid, request, JsonNode.class);
////
////        JsonNode responseNode = responseEntity.getBody();
////        String resultCode = responseNode.get("resultCode").asText();
////
////        LOG.info("payment authorize capture response:" + responseNode.toPrettyString());
////        System.out.println(responseNode.toPrettyString());
////
////        if (resultCode.equalsIgnoreCase("0000")) {
////            ReadableCombineTransaction readableCombineTransaction = paymentAuthorizeFacade.processNicepayAuthorizeResponse(getAuthorizeResponseMap(responseNode), merchantStore, language);
////
////        } else {
////        }
//
////        response.sendRedirect("https://www.baidu.com");
//
//        return ResponseEntity.ok().build();
//    }

    /**
     * Get available payment modules
     *
     * @param merchantStore
     * @param language
     * @return
     */
    @PostMapping("/payment/nicepay/server_auth")
    @ApiOperation(httpMethod = "POST", value = "List list of payment modules", notes = "Requires administration access", produces = "application/json", response = List.class)
    @ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT") })
    public ReadableCombineTransaction paymentModules2(
            @RequestParam(required = false) String tid,
            @RequestParam(required = false) String amount,
            @RequestParam String clientId,
            @ApiIgnore MerchantStore merchantStore,
            @ApiIgnore Language language,
            HttpServletResponse response) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString((clientId + ":" + SECRET_KEY).getBytes()));
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> AuthenticationMap = new HashMap<>();
        AuthenticationMap.put("amount", String.valueOf(amount));

        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(AuthenticationMap), headers);

        ResponseEntity<JsonNode> responseEntity = restTemplate.postForEntity(BASE_PAYMENT_URL + tid, request, JsonNode.class);

        JsonNode responseNode = responseEntity.getBody();
        String resultCode = responseNode.get("resultCode").asText();

        LOG.info("payment nicepay authorize response:" + responseNode.toPrettyString());
//        System.out.println(responseNode.toPrettyString());

//        String jsonString = "{\n" +
//                "  \"resultCode\": \"0000\",\n" +
//                "  \"resultMsg\": \"success\",\n" +
//                "  \"tid\": \"UT0000113m01012111051714341073\",\n" +
//                "  \"orderId\": \"1700\",\n" +
//                "  \"ediDate\": \"2021-11-05T17:14:35\",\n" +
//                "  \"signature\": \"teststest\",\n" +
//                "  \"status\": \"paid\",\n" +
//                "  \"paidAt\": \"2021-11-05T17:14:35\",\n" +
//                "  \"payMethod\": \"CARD\",\n" +
//                "  \"amount\": \"1004\",\n" +
//                "  \"goodsName\": \"test goods\",\n" +
//                "  \"approveNo\": \"000000\",\n" +
//                "  \"channel\": \"pc\",\n" +
//                "  \"currency\": \"KRM\"\n" +
//                "}";
//
//        // 创建 ObjectMapper 对象
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        // 使用 ObjectMapper 的 readTree 方法将 JSON 字符串转换为 JsonNode 对象
//        JsonNode jsonNode = objectMapper.readTree(jsonString);
//
//        String resultCode = jsonNode.get("resultCode").asText();


        if (resultCode.equalsIgnoreCase("0000")) {
            ReadableCombineTransaction readableCombineTransaction = null;
            try {
                Map<String, String> authorizeResponseMap = getAuthorizeResponseMap(responseNode);
                readableCombineTransaction = paymentAuthorizeFacade.processNicepayAuthorizeResponse(authorizeResponseMap, merchantStore, language);
                response.sendRedirect(String.format("%s?tid=%s", REDIRECT_CHECK_IN, authorizeResponseMap.get("tid")));
                return readableCombineTransaction;
            } catch (Exception e) {
                LOG.error("unexpected exception:{}", e.getMessage(), e);
                response.sendRedirect(REDIRECT_CHECK_NG);
            }
        } else {
            LOG.error("unexpected result code:" + resultCode +", msg:" + responseNode.get("resultMsg").asText());
            response.sendRedirect(REDIRECT_CHECK_NG);
        }

        return null;
//        response.sendRedirect("https://www.baidu.com");
//        return ResponseEntity.ok().build();
    }


    private Map<String, String> getAuthorizeResponseMap(JsonNode responseNode) throws IOException {
        Map<String, String> respMap = new HashMap<>();
        respMap.put("tid", responseNode.get("tid").asText());
        respMap.put("orderId", responseNode.get("orderId").asText());
        respMap.put("signature", responseNode.get("signature").asText());
        respMap.put("status", responseNode.get("status").asText());
        respMap.put("ediDate", responseNode.get("ediDate").asText());
        respMap.put("paidAt", responseNode.get("paidAt").asText());
        respMap.put("amount", responseNode.get("amount").asText());
        respMap.put("channel", responseNode.get("channel").asText());
        respMap.put("currency", responseNode.get("currency").asText());
        respMap.put("raw", responseNode.toString());

        return respMap;
    }

}
