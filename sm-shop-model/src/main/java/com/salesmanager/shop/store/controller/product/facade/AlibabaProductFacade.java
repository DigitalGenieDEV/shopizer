package com.salesmanager.shop.store.controller.product.facade;


import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.shop.model.catalog.product.product.alibaba.AlibabaProductSearchKeywordQueryParam;
import com.salesmanager.shop.model.catalog.product.product.alibaba.ReadableProductPageInfo;

import java.util.List;

public interface AlibabaProductFacade {

    void importProduct(List<Long> productIds, String language, MerchantStore merchantStore, List<Long> categoryIds) throws ServiceException;


    ReadableProductPageInfo searchProductByKeywords(AlibabaProductSearchKeywordQueryParam queryParam);
}
