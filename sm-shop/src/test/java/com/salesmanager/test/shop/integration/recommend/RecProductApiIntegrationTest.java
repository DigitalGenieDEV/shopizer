package com.salesmanager.test.shop.integration.recommend;

import com.salesmanager.core.business.constants.Constants;
import com.salesmanager.shop.application.ShopApplication;
import com.salesmanager.shop.model.catalog.SearchRecGuessULikeRequest;
import com.salesmanager.shop.model.catalog.SearchRecRelateItemRequest;
import com.salesmanager.shop.model.catalog.SearchRecSelectionRequest;
import com.salesmanager.shop.model.recommend.RecFootPrintRequest;
import com.salesmanager.test.shop.common.ServicesTestSupport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(classes = ShopApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@ActiveProfiles(value = "local")
public class RecProductApiIntegrationTest extends ServicesTestSupport {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void searchRecGuessULike() {
        SearchRecGuessULikeRequest request = new SearchRecGuessULikeRequest();
        request.setDeviceId("111");
//        request.setUid(123);
        request.setSize(20);
        request.setNumber(0);

        final HttpEntity<SearchRecGuessULikeRequest> searchEntity = new HttpEntity<>(request, getHeader());

        final ResponseEntity<String> searchResponse = testRestTemplate.postForEntity("/api/v1/rec/guess_u_like?store=" + Constants.DEFAULT_STORE, searchEntity, String.class);
        assertNotNull(searchResponse);
        assertThat(searchResponse.getStatusCode(), is(OK));
    }

    @Test
    public void searchRecRelateItem() {
        SearchRecRelateItemRequest request = new SearchRecRelateItemRequest();
        request.setDeviceId("111");
        request.setUid(123);
        request.setSize(20);
        request.setNumber(0);
        request.setProductId(122926);

        final HttpEntity<SearchRecRelateItemRequest> searchEntity = new HttpEntity<>(request, getHeader());

        final ResponseEntity<String> searchResponse = testRestTemplate.postForEntity("/api/v1/rec/relate_item?store=" + Constants.DEFAULT_STORE, searchEntity, String.class);
        assertNotNull(searchResponse);
        assertThat(searchResponse.getStatusCode(), is(OK));
    }

    @Test
    public void searchSelectionProduct() {
        SearchRecSelectionRequest request = new SearchRecSelectionRequest();
        request.setDeviceId("111");
        request.setUid(123);
        request.setSize(20);
        request.setPageIdx(0);
        request.setTag("CURATE_EXHIBITION");

        final HttpEntity<SearchRecSelectionRequest> searchEntity = new HttpEntity<>(request, getHeader());

        final ResponseEntity<String> searchResponse = testRestTemplate.postForEntity("/api/v1/rec/selection_product?store=" + Constants.DEFAULT_STORE, searchEntity, String.class);
        assertNotNull(searchResponse);
        assertThat(searchResponse.getStatusCode(), is(OK));

    }


    @Test
    public void searchFootPrint() {
        RecFootPrintRequest request = new RecFootPrintRequest();
        request.setUid(2);
        request.setSize(20);

        final HttpEntity<RecFootPrintRequest> searchEntity = new HttpEntity<>(request, getHeader());

        final ResponseEntity<String> searchResponse = testRestTemplate.postForEntity("/api/v1/rec/foot_print?store=" + Constants.DEFAULT_STORE, searchEntity, String.class);
        assertNotNull(searchResponse);
        assertThat(searchResponse.getStatusCode(), is(OK));

    }
}
