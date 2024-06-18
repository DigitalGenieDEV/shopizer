package com.salesmanager.core.business.services.search;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.amazonaws.LambdaInvokeService;
import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.model.catalog.product.recommend.*;
import com.salesmanager.core.model.catalog.product.search.*;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service("searchProductService")
public class SearchProductServiceImpl implements SearchProductService{


    private static final Logger LOGGER = LoggerFactory.getLogger(SearchProductServiceImpl.class);

    @Inject
    private ProductService productService;

    @Inject
    private LambdaInvokeService lambdaInvokeService;

    private static final String LAMBDA_SR_SEARCH = "sr_search_api";

    private static final String LAMBDA_SR_SEARCH_AUTOCOMPLETE = "sr_search_autocomplete";



    @Override
    public SearchProductResult search(SearchRequest request) throws ServiceException {
        SearchResult searchResult = getProductSearchResult(request);

        List<ProductResult> productResults = searchResult.getProductList();

//        List<ProductResult> productResults = new ArrayList<>();
//        ProductResult productResult = new ProductResult();
//        productResult.setProductId("350");
//        productResults.add(productResult);

//        List<Product> products = new ArrayList<>();
//
////        List<Product> products =
////                productService.getProductsByIds(productResults.stream().map(ProductResult::getProductId).map(Long::valueOf).collect(Collectors.toList()));
//        for (ProductResult p : productResults) {
//            try {
//                Product product = productService.getById(Long.valueOf(p.getProductId()));
//                if(product != null && product.getId() != null && product.getId() > 0) {
//                    products.add(product);
//                }
//            } catch (Exception e) {
//            }
//        }
//        products.add(productResult);
        SearchProductResult searchProductResult = new SearchProductResult();
        searchProductResult.setProductResults(productResults);
        searchProductResult.setHitNumber(searchResult.getHitNumber());

//        Map<String, List<String>> attrForFilt = new HashMap<>();
//        attrForFilt.put("cate", Arrays.asList("700", "650", "600"));
//        attrForFilt.put("po", Arrays.asList("564", "563"));
//        attrForFilt.put("price", Arrays.asList("18.6", "19.8"));
//        attrForFilt.put("prod_type", Arrays.asList("GENERAL"));
//        attrForFilt.put("attr_17", Arrays.asList("505", "506", "507"));
//        attrForFilt.put("attr_16", Arrays.asList("550", "551", "552"));
        searchProductResult.setFilterOptions(searchResult.getFilterOptions());

        return searchProductResult;
    }


    @Override
    public AutocompleteResult autocomplete(AutocompleteRequest request) {
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String response = "";
        try {
            response = lambdaInvokeService.invoke(LAMBDA_SR_SEARCH_AUTOCOMPLETE, objectMapper.writeValueAsString(request));
            return objectMapper.readValue(response, AutocompleteResult.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            LOGGER.error("autocomplete exception", e);
        }

        return null;
    }

    private SearchResult getProductSearchResult(SearchRequest request) {
//        String endpoint = "https://3melad8jk2.execute-api.ap-northeast-2.amazonaws.com/test_sr_search_api";
////        String endpoint = "https://3melad8jk2.execute-api.ap-northeast-2.amazonaws.com/test_sr_search_api?q=bottle&size=20";
//        final HttpClient httpClient = new HttpClient();
//        final GetMethod method = new GetMethod(endpoint);

//        if(request != null){
//            method.setQueryString(request.getParams().toArray(new NameValuePair[0]));
//        }

        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String response = "";
        try {
            response = lambdaInvokeService.invoke(LAMBDA_SR_SEARCH, objectMapper.writeValueAsString(request));
            return objectMapper.readValue(response, SearchResult.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
//        try{
//            int status = httpClient.executeMethod(method);
//            if(status >= 300 || status < 200){
//                throw new RuntimeException("invoke api failed, urlPath:" + endpoint
//                        + " status:" + status + " response:" + method.getResponseBodyAsString());
//            }
//
//            response = getResponse(method);
//
//            ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//            return objectMapper.readValue(response, ProductSearchResult.class);
//        } catch (HttpException e) {
//            System.out.println(e.getMessage());
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }finally{
//            method.releaseConnection();
//        }

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

    private AutocompleteResult getAutocompleteResult(AutocompleteRequest request) {
        String endpoint = "https://yjticrq1y0.execute-api.ap-northeast-2.amazonaws.com/test_search_autocomplete";
//        String endpoint = "https://3melad8jk2.execute-api.ap-northeast-2.amazonaws.com/test_sr_search_api?q=bottle&size=20";
        final HttpClient httpClient = new HttpClient();
        final GetMethod method = new GetMethod(endpoint);

        if(request != null){
//            method.setQueryString(request.getParams().toArray(new NameValuePair[0]));
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
            return objectMapper.readValue(response, AutocompleteResult.class);
        } catch (HttpException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }finally{
            method.releaseConnection();
        }

        return null;
    }

    private String invokeFunc(String functionName, String payload) {
        InvokeRequest invokeRequest = new InvokeRequest()
                .withFunctionName(functionName)
                .withPayload("{\n" +
                        " \"queryStringParameters\" : {\n" +
                        " \"uid\": 123,\n" +
                        " \"cookieid\": \"abc\",\n" +
                        " \"lang\": \"en\",\n" +
                        " \"q\": \"bottle\",\n" +
                        " \"size\": 20,\n" +
                        " \"page_idx\": 0,\n" +
                        " \"sort\": \"match\"\n" +
                        "} }");

        System.out.println("{\n" +
                " \"queryStringParameters\" : {\n" +
                " \"uid\": 123,\n" +
                " \"cookieid\": \"abc\",\n" +
                " \"lang\": \"en\",\n" +
                " \"q\": \"bottle\",\n" +
                " \"size\": 20,\n" +
                " \"page_idx\": 0,\n" +
                " \"sort\": \"match\"\n" +
                "} }");
        InvokeResult invokeResult = null;

        AWSLambda awsLambda = AWSLambdaClientBuilder.standard()
                .withCredentials(new ProfileCredentialsProvider())
                .withRegion(Regions.AP_NORTHEAST_2).build();

        invokeResult = awsLambda.invoke(invokeRequest);

        String ans = new String(invokeResult.getPayload().array(), StandardCharsets.UTF_8);

        return ans;
    }

    public static void main(String[] args) {
        String functionName = "sr_search_api";

        InvokeRequest invokeRequest = new InvokeRequest()
                .withFunctionName(functionName)
                .withPayload("{\n" +
                        " \"uid\": 123,\n" +
                        " \"cookieid\": \"abc\",\n" +
                        " \"lang\": \"en\",\n" +
                        " \"q\": \"bottle\",\n" +
                        " \"size\": 20,\n" +
                        " \"page_idx\": 0,\n" +
                        " \"sort\": \"match\"\n" +
                        "}");

        System.out.println("{\n" +
                " \"queryStringParameters\" : {\n" +
                " \"uid\": 123,\n" +
                " \"cookieid\": \"abc\",\n" +
                " \"lang\": \"en\",\n" +
                " \"q\": \"bottle\",\n" +
                " \"size\": 20,\n" +
                " \"page_idx\": 0,\n" +
                " \"sort\": \"match\"\n" +
                "} }");
        InvokeResult invokeResult = null;

        AWSLambda awsLambda = AWSLambdaClientBuilder.standard()
                .withRegion(Regions.AP_NORTHEAST_2).build();

        invokeResult = awsLambda.invoke(invokeRequest);

        String ans = new String(invokeResult.getPayload().array(), StandardCharsets.UTF_8);

        System.out.println(ans);
    }
}
