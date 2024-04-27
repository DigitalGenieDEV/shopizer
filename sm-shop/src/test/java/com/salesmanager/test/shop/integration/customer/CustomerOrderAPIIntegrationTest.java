package com.salesmanager.test.shop.integration.customer;

import com.salesmanager.shop.application.ShopApplication;
import com.salesmanager.shop.model.customer.shoppingcart.PersistableCustomerShoppingCartItem;
import com.salesmanager.shop.model.customer.shoppingcart.ReadableCustomerShoppingCart;
import com.salesmanager.test.shop.common.ServicesTestSupport;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpStatus.CREATED;

@SpringBootTest(classes = ShopApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@ActiveProfiles(value = "local")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerOrderAPIIntegrationTest extends ServicesTestSupport {

    @Test
    public void listOrders() {
        final HttpEntity<PersistableCustomerShoppingCartItem> cartEntity = new HttpEntity<>(null, getHeader());
        final ResponseEntity<String> response = testRestTemplate.exchange(String.format("/api/v1/auth/customer_orders"), HttpMethod.GET, cartEntity, String.class);

        assertNotNull(response);
        assertThat(response.getStatusCode(), is(HttpStatus.SC_OK));
    }

    @Test
    public void getOrder() {
        final HttpEntity<PersistableCustomerShoppingCartItem> cartEntity = new HttpEntity<>(null, getHeader());
        final ResponseEntity<String> response = testRestTemplate.exchange(String.format("/api/v1/auth/customer_orders/900"), HttpMethod.GET, cartEntity, String.class);

        assertNotNull(response);
        assertThat(response.getStatusCode(), is(HttpStatus.SC_OK));
    }
}
