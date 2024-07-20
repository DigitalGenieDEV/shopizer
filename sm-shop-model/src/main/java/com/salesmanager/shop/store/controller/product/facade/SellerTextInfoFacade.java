package com.salesmanager.shop.store.controller.product.facade;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.model.catalog.product.SellerProductShippingTextInfo;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.product.type.PersistableProductType;
import com.salesmanager.shop.model.catalog.product.type.ReadableProductType;
import com.salesmanager.shop.model.catalog.product.type.ReadableProductTypeList;

import java.util.List;

public interface SellerTextInfoFacade {

  List<SellerProductShippingTextInfo> getSellerProductShippingTextInfoListByMerchant(MerchantStore store);

  SellerProductShippingTextInfo  getSellerProductShippingTextById(Long id);

  Long save(SellerProductShippingTextInfo sellerProductShippingTextInfo, MerchantStore store);
  
  void update(SellerProductShippingTextInfo sellerProductShippingTextInfo, MerchantStore store);
  
  void delete(Long id) throws ServiceException;
  
}
