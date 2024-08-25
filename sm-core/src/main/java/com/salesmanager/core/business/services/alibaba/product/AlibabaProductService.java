package com.salesmanager.core.business.services.alibaba.product;

import com.salesmanager.core.business.alibaba.fenxiao.crossborder.param.*;
import com.salesmanager.core.business.exception.ServiceException;

import java.util.List;

public interface AlibabaProductService {

    ProductSearchKeywordQueryModelPageInfoV searchKeyword(ProductSearchKeywordQueryParamOfferQueryParam param) throws ServiceException;

    ProductSearchQueryProductDetailModelProductDetailModel queryProductDetail(ProductSearchQueryProductDetailParamOfferDetailParam param);
}
