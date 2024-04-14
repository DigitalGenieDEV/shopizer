package com.salesmanager.core.business.services.alibaba.product;

import com.alibaba.fastjson.JSON;
import com.salesmanager.core.business.alibaba.ApiExecutorSingleton;
import com.salesmanager.core.business.alibaba.fenxiao.crossborder.param.*;
import com.salesmanager.core.business.alibaba.rawsdk.ApiExecutor;
import com.salesmanager.core.constants.ApiFor1688Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlibabaProductServiceImpl implements AlibabaProductService{

    private static final Logger LOGGER = LoggerFactory.getLogger(AlibabaProductServiceImpl.class);

    @Override
    public ProductSearchKeywordQueryModelPageInfoV searchKeyword(ProductSearchKeywordQueryParamOfferQueryParam param) {
        ApiExecutor apiExecutor = ApiExecutorSingleton.getInstance();
        ProductSearchKeywordQueryParam keywordQueryParam =  new  ProductSearchKeywordQueryParam();
        keywordQueryParam.setOfferQueryParam(param);
        ProductSearchKeywordQueryResult result = apiExecutor.execute(keywordQueryParam, ApiFor1688Constants.ACCESS_TOKEN).getResult();
        LOGGER.info("AlibabaProductServiceImpl searchKeyword result" + JSON.toJSON(result));
        if (!result.getResult().getSuccess()){
            throw new RuntimeException("AlibabaProductServiceImpl searchKeyword error" + JSON.toJSON(param));
        }
        return result.getResult().getResult();
    }

    @Override
    public ProductSearchQueryProductDetailModelProductDetailModel queryProductDetail(ProductSearchQueryProductDetailParamOfferDetailParam param) {
        ApiExecutor apiExecutor = ApiExecutorSingleton.getInstance();
        ProductSearchQueryProductDetailParam productSearchParam =  new  ProductSearchQueryProductDetailParam();
        productSearchParam.setOfferDetailParam(param);
        ProductSearchQueryProductDetailResult result = apiExecutor.execute(productSearchParam,ApiFor1688Constants.ACCESS_TOKEN).getResult();
        LOGGER.info("AlibabaProductServiceImpl queryProductDetail result" + JSON.toJSON(result));
        ProductSearchQueryProductDetailResutResultModel productSearchQueryProductDetailResutResultModel = result.getResult();
        if (!productSearchQueryProductDetailResutResultModel.getSuccess()){
            throw new RuntimeException("AlibabaProductServiceImpl queryProductDetail error" + JSON.toJSON(param));
        }
        return productSearchQueryProductDetailResutResultModel.getResult();

    }
}
