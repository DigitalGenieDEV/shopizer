package com.salesmanager.test.shop.api;


import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.shop.application.ShopApplication;
import com.salesmanager.shop.model.customer.order.PersistableCustomerOrder;
import com.salesmanager.shop.model.customer.shoppingcart.PersistableCustomerShoppingCartItem;
import com.salesmanager.shop.model.customer.shoppingcart.ReadableCustomerShoppingCart;
import com.salesmanager.shop.model.order.shipping.PersistableDeliveryAddress;
import com.salesmanager.shop.model.order.transaction.PersistablePayment;
import com.salesmanager.test.shop.common.ServicesTestSupport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(classes = ShopApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class CartApiTest extends ServicesTestSupport {


    @Test
    public void additional_services_test() {
        final HttpEntity<PersistableCustomerShoppingCartItem[]> cartEntity = new HttpEntity<>(null, getHeader());
        final ResponseEntity<ReadableCustomerShoppingCart> response = testRestTemplate.exchange(String.format("/api/v1/auth/additional/services"), HttpMethod.GET, cartEntity, ReadableCustomerShoppingCart.class);

        assertNotNull(response);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void get_cart_test() {
        final HttpEntity<PersistableCustomerShoppingCartItem[]> cartEntity = new HttpEntity<>(null, getHeader());
        final ResponseEntity<ReadableCustomerShoppingCart> response = testRestTemplate.exchange(String.format("/api/v1/auth/customer_cart"), HttpMethod.GET, cartEntity, ReadableCustomerShoppingCart.class);

        assertNotNull(response);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void add_cart_test() {
        PersistableCustomerShoppingCartItem cartItem = new PersistableCustomerShoppingCartItem();
        cartItem.setSku("Qkr202408090431460493495");
        cartItem.setQuantity(1);
//        cartItem.setShippingType("NATIONAL");// 不可为空
//        cartItem.setShippingTransportationType("DIRECT_DELIVERY");
//        cartItem.setTruckModel("ONE_TON");
//        cartItem.setInternationalTransportationMethod("SHIPPING");
//        cartItem.setNationalTransportationMethod("SHIPPING");
//        cartItem.setMerchantId(1);
        cartItem.setChecked(false);

        final HttpEntity<PersistableCustomerShoppingCartItem> cartEntity = new HttpEntity<>(cartItem, getHeader());
        final ResponseEntity<ReadableCustomerShoppingCart> response = testRestTemplate.exchange(String.format("/api/v1/auth/customer_cart"), HttpMethod.POST, cartEntity, ReadableCustomerShoppingCart.class);

        assertNotNull(response);
        assertThat(response.getStatusCode(), is(CREATED));
    }


    @Test
    public void modify_cart_test() {
        PersistableCustomerShoppingCartItem cartItem = new PersistableCustomerShoppingCartItem();
        cartItem.setSku("Qkr202408090431460493495");
        cartItem.setQuantity(1);
        cartItem.setChecked(false);

        final HttpEntity<PersistableCustomerShoppingCartItem> cartEntity = new HttpEntity<>(cartItem, getHeader());
        final ResponseEntity<ReadableCustomerShoppingCart> response = testRestTemplate.exchange(String.format("/api/v1/auth/customer_cart"), HttpMethod.PUT, cartEntity, ReadableCustomerShoppingCart.class);

        assertNotNull(response);
        assertThat(response.getStatusCode(), is(CREATED));
    }

    @Test
    public void del_cart_test() {
        PersistableCustomerShoppingCartItem cartItem = new PersistableCustomerShoppingCartItem();
        final HttpEntity<PersistableCustomerShoppingCartItem> cartEntity = new HttpEntity<>(cartItem, getHeader());
        final ResponseEntity<ReadableCustomerShoppingCart> response = testRestTemplate.exchange(String.format("/api/v1/auth/customer_cart/product/Qkr202408090431460493495?body=true"), HttpMethod.DELETE, cartEntity, ReadableCustomerShoppingCart.class);
        assertNotNull(response);
        assertThat(response.getStatusCode(), is(OK));
    }

    @Test
    public void del_multi_test() {
        PersistableCustomerShoppingCartItem cartItem = new PersistableCustomerShoppingCartItem();
        cartItem.setSku("Qkr202408090431460493495");
        PersistableCustomerShoppingCartItem[] customerShoppingCartItems = {cartItem};
        final HttpEntity<PersistableCustomerShoppingCartItem[]> cartEntity = new HttpEntity<>(customerShoppingCartItems, getHeader());
        final ResponseEntity<ReadableCustomerShoppingCart> response = testRestTemplate.exchange(String.format("/api/v1/auth/customer_cart/del_multi?body=true"), HttpMethod.POST, cartEntity, ReadableCustomerShoppingCart.class);
        assertNotNull(response);
        assertThat(response.getStatusCode(), is(OK));
    }

    @Test
    public void checkout_test() {
        PersistableCustomerShoppingCartItem cartItem = new PersistableCustomerShoppingCartItem();
        cartItem.setQuantity(1);
        final HttpEntity<PersistableCustomerShoppingCartItem> cartEntity = new HttpEntity<>(cartItem, getHeader());
        final ResponseEntity<ReadableCustomerShoppingCart> response = testRestTemplate.exchange(String.format("/api/v1/auth/customer_cart/product/Qkr202408090431460493495/checkout"), HttpMethod.POST, cartEntity, ReadableCustomerShoppingCart.class);
        assertNotNull(response);
        assertThat(response.getStatusCode(), is(CREATED));
    }

    @Test
    public void exclusive_select_test() {
        MerchantStore store = new MerchantStore();
        store.setCode("002004001_2_1");
        final HttpEntity<MerchantStore> storeEntity = new HttpEntity<>(store, getHeader());
        final ResponseEntity<ReadableCustomerShoppingCart> response = testRestTemplate.exchange(String.format("/api/v1/auth/customer_cart/product/Qkr202408090431460493495/exclusive_select"), HttpMethod.DELETE, storeEntity, ReadableCustomerShoppingCart.class);
        assertNotNull(response);
        assertThat(response.getStatusCode(), is(OK));
    }

    @Test
    public void cart_checkout_test() {
        PersistablePayment payment = new PersistablePayment();
        payment.setPaymentType("PAYPAL");
        payment.setPaymentModule("PayPal");
        payment.setTransactionType("AUTHORIZE");
        PersistableDeliveryAddress address = new PersistableDeliveryAddress();
        PersistableCustomerOrder persistableCustomerOrder = new PersistableCustomerOrder();
        persistableCustomerOrder.setPayment(payment);
        persistableCustomerOrder.setAddress(address);// ??
        final HttpEntity<PersistableCustomerOrder> orderEntity = new HttpEntity<>(persistableCustomerOrder, getHeader());
        final ResponseEntity<ReadableCustomerShoppingCart> response = testRestTemplate.exchange(String.format("/api/v1/auth/customer_cart/checkout"), HttpMethod.POST, orderEntity, ReadableCustomerShoppingCart.class);
        assertNotNull(response);
        assertThat(response.getStatusCode(), is(OK));
        // Column 'TRUCK_MODEL' cannot be null
    }
}
