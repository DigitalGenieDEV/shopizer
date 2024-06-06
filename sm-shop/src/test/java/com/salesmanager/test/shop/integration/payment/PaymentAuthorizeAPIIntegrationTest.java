package com.salesmanager.test.shop.integration.payment;

import com.salesmanager.shop.application.ShopApplication;
import com.salesmanager.shop.model.customer.shoppingcart.PersistableCustomerShoppingCartItem;
import com.salesmanager.shop.model.customer.shoppingcart.ReadableCustomerShoppingCart;
import com.salesmanager.test.shop.common.ServicesTestSupport;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpStatus.CREATED;

@SpringBootTest(classes = ShopApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@ActiveProfiles(value = "local")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PaymentAuthorizeAPIIntegrationTest  extends ServicesTestSupport {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void authorizeNicepay() {
        final HttpEntity<PersistableCustomerShoppingCartItem> cartEntity = new HttpEntity<>(null, getHeader());
        final ResponseEntity<ReadableCustomerShoppingCart> response = testRestTemplate.postForEntity(String.format("/api/v1/payment/nicepay/server_auth"), cartEntity, ReadableCustomerShoppingCart.class);

        assertNotNull(response);
        assertThat(response.getStatusCode(), is(CREATED));
    }
}
