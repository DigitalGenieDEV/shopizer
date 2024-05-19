package com.salesmanager.test.shop.integration.search;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpStatus.CREATED;

import com.salesmanager.shop.model.catalog.SearchProductAutocompleteRequestV2;
import com.salesmanager.shop.model.catalog.SearchProductRequestV2;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.salesmanager.core.business.constants.Constants;
import com.salesmanager.shop.application.ShopApplication;
import com.salesmanager.shop.model.catalog.SearchProductList;
import com.salesmanager.shop.model.catalog.SearchProductRequest;
import com.salesmanager.shop.model.catalog.product.product.PersistableProduct;
import com.salesmanager.test.shop.common.ServicesTestSupport;

@SpringBootTest(classes = ShopApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@ActiveProfiles(value = "local")
//@Ignore
public class SearchApiIntegrationTest extends ServicesTestSupport {

    @Autowired
    private TestRestTemplate testRestTemplate;



    /**
     * Add a product then search for it
     * This tests is disabled since it requires Elastic search server started
     *
     * @throws Exception
     */
    //@Test
    @Ignore
    public void searchItem() throws Exception {
    	
    	PersistableProduct product = super.product("TESTPRODUCT");
    	
        final HttpEntity<PersistableProduct> entity = new HttpEntity<>(product, getHeader());

        final ResponseEntity<PersistableProduct> response = testRestTemplate.postForEntity("/api/v1/private/product?store=" + Constants.DEFAULT_STORE, entity, PersistableProduct.class);
        assertThat(response.getStatusCode(), is(CREATED));
        
        SearchProductRequest searchRequest = new SearchProductRequest();
        searchRequest.setQuery("TEST");
        final HttpEntity<SearchProductRequest> searchEntity = new HttpEntity<>(searchRequest, getHeader());
        
        
        final ResponseEntity<SearchProductList> searchResponse = testRestTemplate.postForEntity("/api/v1/search?store=" + Constants.DEFAULT_STORE, searchEntity, SearchProductList.class);
        assertThat(searchResponse.getStatusCode(), is(CREATED));

    }

    @Test
    public void searchV2() {
        SearchProductRequestV2 searchRequest = new SearchProductRequestV2();
        searchRequest.setQ("bottle");
        searchRequest.setLang("en");
        searchRequest.setSize(20);
        searchRequest.setUid(123);
        searchRequest.setCookieid("111");
        final HttpEntity<SearchProductRequestV2> searchEntity = new HttpEntity<>(searchRequest, getHeader());

        final ResponseEntity<String> searchResponse = testRestTemplate.postForEntity("/api/v1/search?store=" + Constants.DEFAULT_STORE, searchEntity, String.class);
        assertNotNull(searchResponse);
        assertThat(searchResponse.getStatusCode(), is(CREATED));
    }

    @Test
    public void searchAutocompleteV2() {
        SearchProductAutocompleteRequestV2 searchRequest = new SearchProductAutocompleteRequestV2();
        searchRequest.setQ("bottle");
        searchRequest.setLang("en");
        searchRequest.setUid(123);
        searchRequest.setCookieid("111");

        final HttpEntity<SearchProductAutocompleteRequestV2> searchEntity = new HttpEntity<>(searchRequest, getHeader());

        final ResponseEntity<String> searchResponse = testRestTemplate.postForEntity("/api/v1/search/autocomplete?store=" + Constants.DEFAULT_STORE, searchEntity, String.class);
        assertNotNull(searchResponse);
        assertThat(searchResponse.getStatusCode(), is(CREATED));
    }


}