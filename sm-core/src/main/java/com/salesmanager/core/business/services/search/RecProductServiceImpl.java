package com.salesmanager.core.business.services.search;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.salesmanager.core.business.services.amazonaws.LambdaInvokeService;
import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.recommend.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service("searchRecService")
public class RecProductServiceImpl implements RecProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecProductServiceImpl.class);

    @Inject
    private ProductService productService;

    @Inject
    private LambdaInvokeService lambdaInvokeService;

    private String LAMBDA_REC_GUESS_LIKE = "sr_rec_homepage_gul_api";

    private String LAMBDA_REC_DETAILPAGE_RIR = "sr_rec_detailpage_rir_api";

    private String LAMBDA_REC_SELECTION_PROD = "sr_rec_selection_prod_api";

    private String LAMBDA_REC_FOOT_PRINT = "sr_footprint_api";

    private  static  ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    static {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Override
    public GuessULikeResult guessULike(GuessULikeRequest request) {
        GuessULikeResult guessULikeResult = new GuessULikeResult();
        try {
            String response = lambdaInvokeService.invoke(LAMBDA_REC_GUESS_LIKE, objectMapper.writeValueAsString(request));
            GuessULikeInvokeResult result = objectMapper.readValue(response, GuessULikeInvokeResult.class);

            guessULikeResult.setProductList(getProductList(result.getProductList()));
            guessULikeResult.setCacheid(result.getCacheid());
            guessULikeResult.setHitNumber(result.getHitNumber());
            return guessULikeResult;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("guess u like exception", e);
        }

        return guessULikeResult;
    }

    private List<Product> getProductList(List<ProductResult> productResults) {
        List<Product> products = new ArrayList<>();
        for (ProductResult p : productResults) {
            try {
                long start = System.currentTimeMillis();
                //LOGGER.info("get product id [" + p.getProductId() + "]");
                Product product = productService.getById(Long.valueOf(p.getProductId()));
                if(product != null && product.getId() != null && product.getId() > 0) {
                    products.add(product);
                }
                //LOGGER.info("get product id [" + p.getProductId() + "] cost " + (System.currentTimeMillis() - start)+"ms");
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("get product id [" + p.getProductId() + "] exception", e);
            }
        }

//        Product product = productService.getById(350l);
//        products.add(product);
        return products;
    }

    @Override
    public RelateItemResult relateItem(RelateItemRequest request) {
        RelateItemResult relateItemResult = new RelateItemResult();
        try {
            String response = lambdaInvokeService.invoke(LAMBDA_REC_DETAILPAGE_RIR, objectMapper.writeValueAsString(request));
            RelateItemInvokeResult result = objectMapper.readValue(response, RelateItemInvokeResult.class);


            relateItemResult.setCacheid(result.getCacheid());
            relateItemResult.setProductList(getProductList(result.getProductList()));
            relateItemResult.setHitNumber(result.getHitNumber());
            return relateItemResult;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("relate item exception", e);
        }

        return relateItemResult;
    }

    @Override
    public SelectionItemResult selectionItem(SelectionItemRequest request) {
        SelectionItemResult selectionItemResult = new SelectionItemResult();
        try {
            String response = lambdaInvokeService.invoke(LAMBDA_REC_SELECTION_PROD, objectMapper.writeValueAsString(request));
            SelectionItemInvokeResult result = objectMapper.readValue(response, SelectionItemInvokeResult.class);

            selectionItemResult.setCacheid(result.getCacheid());
            selectionItemResult.setProductList(getProductList(result.getProductList()));
            selectionItemResult.setHitNumber(result.getHitNumber());
            return selectionItemResult;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("selection item exception", e);
        }

        return selectionItemResult;
    }

    @Override
    public FootPrintResult footPrint(FootPrintRequest request) {
        FootPrintResult footPrintResult = new FootPrintResult();
        try {
            String response = lambdaInvokeService.invoke(LAMBDA_REC_FOOT_PRINT, objectMapper.writeValueAsString(request));
            FootPrintInvokeResult result = objectMapper.readValue(response, FootPrintInvokeResult.class);

            footPrintResult.setProductList(getProductList(result.getProductList()));
            footPrintResult.setHitNumber(result.getHitNumber());
            return footPrintResult;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("foot print exception", e);
        }

        return footPrintResult;
    }
}
