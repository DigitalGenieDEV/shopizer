package com.salesmanager.test.shop.integration.customer;

import com.salesmanager.shop.application.ShopApplication;
import com.salesmanager.shop.model.customer.order.PersistableCustomerOrder;
import com.salesmanager.shop.model.customer.shoppingcart.PersistableCustomerShoppingCartItem;
import com.salesmanager.shop.model.customer.shoppingcart.ReadableCustomerShoppingCart;
import com.salesmanager.shop.model.shoppingcart.PersistableShoppingCartItem;
import com.salesmanager.shop.model.shoppingcart.ReadableShoppingCart;
import com.salesmanager.test.shop.common.ServicesTestSupport;
import com.salesmanager.test.shop.integration.cart.CartTestBean;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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
public class CustomerShoppingCartAPIIntegrationTest extends ServicesTestSupport {
    @Autowired
    private TestRestTemplate testRestTemplate;

    private static CustomerCartTestBean data = new CustomerCartTestBean();

    private static String[] skus = new String[] {
            "5d7cab94-1cc2-459d-8493-c4ff53329ec5",
            "09fc6fcd-03ec-42ef-95a2-e51558de0b3a",
            "42808902-c6a4-4ae5-8135-3017916bcf24",
            "f18cf358-4e59-426f-abc1-a97c8e8fd951",

            "92dcce2e-4a35-4384-90b9-253ad6dcaa0f"
    };

    @Test
    @Order(1)
    public void addToCart() {

        PersistableCustomerShoppingCartItem cartItem = new PersistableCustomerShoppingCartItem();
        cartItem.setProduct(skus[0]);
        cartItem.setQuantity(1);
        cartItem.setMerchantId(1);

        final HttpEntity<PersistableCustomerShoppingCartItem> cartEntity = new HttpEntity<>(cartItem, getHeader());
        final ResponseEntity<ReadableCustomerShoppingCart> response = testRestTemplate.postForEntity(String.format("/api/v1/auth/customer_cart"), cartEntity, ReadableCustomerShoppingCart.class);

        data.setCartId(response.getBody().getCode());
        assertNotNull(response);
        assertThat(response.getStatusCode(), is(CREATED));
    }

    @Test
    @Order(2)
    public void modifyCart() {
        PersistableCustomerShoppingCartItem cartItem = new PersistableCustomerShoppingCartItem();
        cartItem.setProduct(skus[1]);
        cartItem.setQuantity(1);
        cartItem.setMerchantId(1);

        final HttpEntity<PersistableCustomerShoppingCartItem> cartEntity = new HttpEntity<>(cartItem, getHeader());
        final ResponseEntity<ReadableCustomerShoppingCart> response = testRestTemplate.exchange(String.format("/api/v1/auth/customer_cart/" + String.valueOf(data.getCartId())), HttpMethod.PUT, cartEntity, ReadableCustomerShoppingCart.class);

        assertNotNull(response);
        assertThat(response.getStatusCode(), is(CREATED));
    }

    @Test
    @Order(3)
    public void modifyCartMulti() {
        PersistableCustomerShoppingCartItem cartItem1 = new PersistableCustomerShoppingCartItem();
        cartItem1.setProduct(skus[2]);
        cartItem1.setQuantity(1);
        cartItem1.setMerchantId(1);

        PersistableCustomerShoppingCartItem cartItem2 = new PersistableCustomerShoppingCartItem();
        cartItem2.setProduct(skus[3]);
        cartItem2.setQuantity(2);
        cartItem2.setMerchantId(1);

        PersistableCustomerShoppingCartItem cartItem3 = new PersistableCustomerShoppingCartItem();
        cartItem3.setProduct(skus[4]);
        cartItem3.setQuantity(2);
        cartItem3.setMerchantId(2);

        PersistableCustomerShoppingCartItem[] cartItems = new PersistableCustomerShoppingCartItem[]{ cartItem1, cartItem2, cartItem3};

        final HttpEntity<PersistableCustomerShoppingCartItem[]> cartEntity = new HttpEntity<>(cartItems, getHeader());
        final ResponseEntity<ReadableCustomerShoppingCart> response = testRestTemplate.exchange(String.format("/api/v1/auth/customer_cart/" + String.valueOf(data.getCartId()) + "/multi"), HttpMethod.POST, cartEntity, ReadableCustomerShoppingCart.class);

        assertNotNull(response);
        assertThat(response.getStatusCode(), is(CREATED));
    }

    @Test
    @Order(4)
    public void deleteCartItem() {
        final HttpEntity cartEntity = new HttpEntity<>(null, getHeader());
        final ResponseEntity<ReadableCustomerShoppingCart> response = testRestTemplate.exchange(String.format("/api/v1/auth/customer_cart/" + String.valueOf(data.getCartId()) + "/product/" + skus[3] + "?body=true"), HttpMethod.DELETE, cartEntity, ReadableCustomerShoppingCart.class);

        assertNotNull(response);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    @Order(5)
    public void getCart() {
        final HttpEntity<PersistableCustomerShoppingCartItem[]> cartEntity = new HttpEntity<>(null, getHeader());
        final ResponseEntity<ReadableCustomerShoppingCart> response = testRestTemplate.exchange(String.format("/api/v1/customer_cart/" + String.valueOf(data.getCartId())), HttpMethod.GET, cartEntity, ReadableCustomerShoppingCart.class);

        assertNotNull(response);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    @Order(6)
    public void checkoutCart() {
        PersistableCustomerOrder persistableCustomerOrder = new PersistableCustomerOrder();

        final HttpEntity<PersistableCustomerOrder> cartEntity = new HttpEntity<>(persistableCustomerOrder, getHeader());
        final ResponseEntity<String> response = testRestTemplate.exchange(String.format("/api/v1/auth/customer_cart/" + String.valueOf(data.getCartId() + "/checkout")), HttpMethod.POST, cartEntity, String.class);

        assertNotNull(response);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }
}