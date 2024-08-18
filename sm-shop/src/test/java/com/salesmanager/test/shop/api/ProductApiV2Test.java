package com.salesmanager.test.shop.api;

import com.salesmanager.shop.application.ShopApplication;
import com.salesmanager.shop.model.catalog.SearchProductAutocompleteRequestV2;
import com.salesmanager.shop.model.catalog.SearchProductRequestV2;
import com.salesmanager.shop.model.recommend.RecGuessULikeRequest;
import com.salesmanager.shop.model.recommend.RecRelateItemRequest;
import com.salesmanager.shop.model.recommend.RecSelectionProductRequest;
import com.salesmanager.test.shop.common.ServicesTestSupport;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(classes = ShopApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class ProductApiV2Test extends ServicesTestSupport {

    @Test
    public void guess_u_like() {
        RecGuessULikeRequest recGuessULikeRequest = new RecGuessULikeRequest();
        HttpEntity<RecGuessULikeRequest> likeEntity = new HttpEntity<>(recGuessULikeRequest, getHeader());
        ResponseEntity<String> response = testRestTemplate.postForEntity(String.format("/api/v1/rec/guess_u_like"), likeEntity, String.class);
        Assert.assertEquals(OK, response.getStatusCode());
    }

    @Test
    public void relate_item() {
        RecRelateItemRequest recGuessULikeRequest = new RecRelateItemRequest();
        recGuessULikeRequest.setProductId(5273);
        HttpEntity<RecRelateItemRequest> likeEntity = new HttpEntity<>(recGuessULikeRequest, getHeader());
        ResponseEntity<String> response = testRestTemplate.postForEntity(String.format("/api/v1/rec/relate_item"), likeEntity, String.class);
        Assert.assertEquals(OK, response.getStatusCode());
    }

    @Test
    public void selection_product() {
        RecSelectionProductRequest recGuessULikeRequest = new RecSelectionProductRequest();
        HttpEntity<RecSelectionProductRequest> likeEntity = new HttpEntity<>(recGuessULikeRequest, getAdminHeader());
        ResponseEntity<String> managerResponse = testRestTemplate.exchange(String.format("/api/v2/private/main/display/search/products?tag=MD_PICK"), HttpMethod.GET, likeEntity, String.class);
        Assert.assertEquals(OK, managerResponse.getStatusCode());
        // {"totalPages":0,"number":0,"recordsTotal":0,"recordsFiltered":0,"products":[]}

        recGuessULikeRequest.setTag("MD_PICK");
        HttpEntity<RecSelectionProductRequest> productEntity = new HttpEntity<>(recGuessULikeRequest, getHeader());
        ResponseEntity<String> response = testRestTemplate.postForEntity(String.format("/api/v1/rec/selection_product"), productEntity, String.class);
        Assert.assertEquals(OK, response.getStatusCode());
    }

    @Test
    public void search_autocomplete() {
        SearchProductAutocompleteRequestV2 recGuessULikeRequest = new SearchProductAutocompleteRequestV2();
        recGuessULikeRequest.setQ("bottle");
        recGuessULikeRequest.setUid(4);// ???
        HttpEntity<SearchProductAutocompleteRequestV2> likeEntity = new HttpEntity<>(recGuessULikeRequest, getHeader());
        ResponseEntity<String> response = testRestTemplate.postForEntity(String.format("/api/v1/search/autocomplete?lang=ko"), likeEntity, String.class);
        Assert.assertEquals(OK, response.getStatusCode());
    }

    @Test
    public void search() {
        SearchProductRequestV2 recGuessULikeRequest = new SearchProductRequestV2();
        recGuessULikeRequest.setQ("bottle");
        recGuessULikeRequest.setUid(4);// ???
        HttpEntity<SearchProductRequestV2> likeEntity = new HttpEntity<>(recGuessULikeRequest, getHeader());
        ResponseEntity<String> response = testRestTemplate.postForEntity(String.format("/api/v1/search?lang=ko"), likeEntity, String.class);
        Assert.assertEquals(OK, response.getStatusCode());
    }
}
