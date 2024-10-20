package com.salesmanager.shop.store.controller.product.facade;


import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.model.catalog.product.PublishWayEnums;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.shop.model.catalog.product.product.alibaba.AlibabaProductSearchKeywordQueryParam;
import com.salesmanager.shop.model.catalog.product.product.alibaba.ReadableProductPageInfo;

import java.util.List;

public interface AlibabaProductFacade {

    List<Long> importProduct(List<Long> productIds, String language, MerchantStore merchantStore,
                             List<Long> categoryIds, PublishWayEnums importType) throws ServiceException;


    void adminBatchImportProduct(Long productId, String language, MerchantStore merchantStore,
                             List<Long> categoryIds, PublishWayEnums importType) throws Exception;


    ReadableProductPageInfo searchProductByKeywords(AlibabaProductSearchKeywordQueryParam queryParam) throws ServiceException;

    void update1688Product() throws ServiceException;

}
