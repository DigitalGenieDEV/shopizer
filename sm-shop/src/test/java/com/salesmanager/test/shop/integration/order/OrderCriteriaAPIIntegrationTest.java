package com.salesmanager.test.shop.integration.order;

import com.salesmanager.core.model.order.OrderCriteria;
import com.salesmanager.shop.application.ShopApplication;
import com.salesmanager.shop.model.customer.shoppingcart.PersistableCustomerShoppingCartItem;
import com.salesmanager.test.shop.common.ServicesTestSupport;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@SpringBootTest(classes = ShopApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@ActiveProfiles(value = "local")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderCriteriaAPIIntegrationTest  extends ServicesTestSupport {

    @Test
    @Order(1)
    public void orderCriteria() {
        OrderCriteria orderCriteria = new OrderCriteria();
//        orderCriteria.setBeginDate("2024-04-21");
//        orderCriteria.setEndDate("2024-04-25");

        final HttpEntity<OrderCriteria> cartEntity = new HttpEntity<>(orderCriteria, getHeader());
        final ResponseEntity<String> response = testRestTemplate.exchange(String.format("/api/v1/auth/orders?count=100&" + toQueryParam(orderCriteria)), HttpMethod.GET, cartEntity, String.class);

        assertNotNull(response);
        assertThat(response.getStatusCode(), is(HttpStatus.SC_OK));
    }

    @Test
    public void orderDetail() {
        final HttpEntity<OrderCriteria> cartEntity = new HttpEntity<>(null, getHeader());
        final ResponseEntity<String> response = testRestTemplate.exchange(String.format("/api/v1/auth/orders/1250"), HttpMethod.GET, cartEntity, String.class);

        assertNotNull(response);
        assertThat(response.getStatusCode(), is(HttpStatus.SC_OK));
    }

    public static String toQueryParam(Object obj) {
        if (obj == null) {
            return "";
        }

        List<String> params = new ArrayList<>();
        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(obj);
                if (value != null) {
                    String encodedName = URLEncoder.encode(field.getName(), StandardCharsets.UTF_8.toString());
                    String encodedValue = URLEncoder.encode(value.toString(), StandardCharsets.UTF_8.toString());
                    params.add(encodedName + "=" + encodedValue);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return String.join("&", params);
    }
}
