package com.salesmanager.core.business.services.alibaba.product;

import com.salesmanager.core.business.alibaba.fenxiao.crossborder.param.*;

import java.util.List;

public interface AlibabaProductService {

    ProductSearchKeywordQueryModelPageInfoV searchKeyword(ProductSearchKeywordQueryParamOfferQueryParam param);

    ProductSearchQueryProductDetailModelProductDetailModel queryProductDetail(ProductSearchQueryProductDetailParamOfferDetailParam param);
}
