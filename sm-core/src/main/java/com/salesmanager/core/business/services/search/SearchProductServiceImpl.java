package com.salesmanager.core.business.services.search;

import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.search.ProductSearchRequest;
import com.salesmanager.core.model.catalog.product.search.ProductSearchResult;
import com.salesmanager.core.utils.CommonUtil;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.client.methods.HttpGet;
import org.hibernate.Hibernate;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("searchProductService")
public class SearchProductServiceImpl implements SearchProductService{

    @Inject
    private ProductService productService;

    @Override
    public List<Product> search(ProductSearchRequest request) {
        ProductSearchResult productSearchResult = getProductSearchResult(request);

        List<ProductSearchResult.ProductResult> productResults = productSearchResult.getProductList();

        List<Product> products = new ArrayList<>();
        for (ProductSearchResult.ProductResult p : productResults) {
            try {
                Product product = productService.getById(Long.valueOf(p.getProductId()));
                if(product != null && product.getId() != null && product.getId() > 0) {
                    products.add(product);
                }
            } catch (Exception e) {
            }
        }

        return products;
    }

    private ProductSearchResult getProductSearchResult(ProductSearchRequest request) {
        String endpoint = "https://3melad8jk2.execute-api.ap-northeast-2.amazonaws.com/test_sr_search_api";
//        String endpoint = "https://3melad8jk2.execute-api.ap-northeast-2.amazonaws.com/test_sr_search_api?q=bottle&size=20";
        final HttpClient httpClient = new HttpClient();
        final GetMethod method = new GetMethod(endpoint);

        if(request != null){
            method.setQueryString(request.getParams().toArray(new NameValuePair[0]));
        }

        String response = "";
        try{
            int status = httpClient.executeMethod(method);
            if(status >= 300 || status < 200){
                throw new RuntimeException("invoke api failed, urlPath:" + endpoint
                        + " status:" + status + " response:" + method.getResponseBodyAsString());
            }

            response = getResponse(method);

            ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.readValue(response, ProductSearchResult.class);
        } catch (HttpException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }finally{
            method.releaseConnection();
        }

        return null;
    }

    private String getResponse(GetMethod method) throws IOException {
        StringBuffer contentBuffer = new StringBuffer();
        InputStream in = method.getResponseBodyAsStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, method.getResponseCharSet()));
        String inputLine = null;
        while((inputLine = reader.readLine()) != null)
        {
            contentBuffer.append(inputLine);
        }
        //去掉结尾的换行符
        in.close();
        return contentBuffer.toString();
    }

}
