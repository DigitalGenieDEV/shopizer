package com.salesmanager.core.business.services.catalog.product;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.ProductCriteria;
import com.salesmanager.core.model.catalog.product.ProductList;
import com.salesmanager.core.model.catalog.product.SellerTextInfo;
import com.salesmanager.core.model.catalog.product.description.ProductDescription;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.core.model.tax.taxclass.TaxClass;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Optional;


public interface SellerTextInfoService extends SalesManagerEntityService<Long, SellerTextInfo> {



    void saveOrUpdate(SellerTextInfo sellerTextInfo) throws ServiceException;


    List<SellerTextInfo> querySellerTextInfoList(Long sellerId, String type);
}

