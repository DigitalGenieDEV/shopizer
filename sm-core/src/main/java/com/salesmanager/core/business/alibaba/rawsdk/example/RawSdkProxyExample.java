package com.salesmanager.core.business.alibaba.rawsdk.example;


import com.alibaba.fastjson.JSON;
import com.salesmanager.core.business.alibaba.fenxiao.crossborder.param.*;
import com.salesmanager.core.business.alibaba.param.AlibabaTradeCreateCrossOrderParam;
import com.salesmanager.core.business.alibaba.param.AlibabaTradeCreateCrossOrderResult;
import com.salesmanager.core.business.alibaba.param.AlibabaTradeFastAddress;
import com.salesmanager.core.business.alibaba.param.AlibabaTradeFastCargo;
import com.salesmanager.core.business.alibaba.rawsdk.ApiExecutor;
import com.salesmanager.core.business.alibaba.rawsdk.client.SyncAPIClient;
import com.salesmanager.core.business.alibaba.rawsdk.client.policy.ClientPolicy;
import com.salesmanager.core.business.alibaba.rawsdk.client.policy.Protocol;
import com.salesmanager.core.business.alibaba.rawsdk.client.policy.RequestPolicy;
import com.salesmanager.core.business.alibaba.rawsdk.common.SDKResult;
import com.salesmanager.core.constants.ApiFor1688Constants;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qiuwu [qiuwu.sjl@alibaba-inc.com].
 * @date Created at 2023/7/31 2:10 PM.
 */
public class RawSdkProxyExample {
    private static final int PROXY_PORT = Integer.MAX_VALUE;
    static ApiExecutor apiExecutor = new ApiExecutor(ApiFor1688Constants.APP_KEY,ApiFor1688Constants.SECRET_KEY);

    public static void main(String[] args) {

        searchKeyword1688Product();

    }

    private static ProductSearchKeywordQueryResult searchKeyword1688Product(){
        ProductSearchKeywordQueryParam keywordQueryParam =  new  ProductSearchKeywordQueryParam();
        ProductSearchKeywordQueryParamOfferQueryParam productSearchKeywordQueryParamOfferQueryParam
                = new ProductSearchKeywordQueryParamOfferQueryParam();
        productSearchKeywordQueryParamOfferQueryParam.setKeyword("");
        productSearchKeywordQueryParamOfferQueryParam.setBeginPage(1);
        productSearchKeywordQueryParamOfferQueryParam.setPageSize(10);
        productSearchKeywordQueryParamOfferQueryParam.setCountry("ko");
        keywordQueryParam.setOfferQueryParam(productSearchKeywordQueryParamOfferQueryParam);
        ProductSearchKeywordQueryResult result1 = apiExecutor.execute(keywordQueryParam, ApiFor1688Constants.ACCESS_TOKEN).getResult();
        System.out.println(JSON.toJSON(result1));
        return result1;
    }

    private static ProductSearchQueryProductDetailResult query1688ProductDetail(){
        RequestPolicy requestPolicy = new RequestPolicy();
        requestPolicy.setHttpMethod(RequestPolicy.HttpMethodPolicy.POST);
        requestPolicy.setUseHttps(true);
        requestPolicy.setRequestProtocol(Protocol.param2);

        ProductSearchQueryProductDetailParam param = new  ProductSearchQueryProductDetailParam();
        ProductSearchQueryProductDetailParamOfferDetailParam productSearchQueryProductDetailParamOfferDetailParam
                = new ProductSearchQueryProductDetailParamOfferDetailParam();
        productSearchQueryProductDetailParamOfferDetailParam.setOfferId(745671827880L);
        productSearchQueryProductDetailParamOfferDetailParam.setCountry("ko");
        param.setOfferDetailParam(productSearchQueryProductDetailParamOfferDetailParam);
        ProductSearchQueryProductDetailResult result = apiExecutor.execute(param,ApiFor1688Constants.ACCESS_TOKEN).getResult();

        System.out.println(JSON.toJSON(result));
        return result;
    }

}
