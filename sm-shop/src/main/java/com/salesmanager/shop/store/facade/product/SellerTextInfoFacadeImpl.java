package com.salesmanager.shop.store.facade.product;

import com.alibaba.fastjson.JSON;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.catalog.product.SellerTextInfoService;
import com.salesmanager.core.business.services.catalog.product.type.ProductTypeService;
import com.salesmanager.core.model.catalog.product.SellerProductShippingTextInfo;
import com.salesmanager.core.model.catalog.product.SellerTextInfo;
import com.salesmanager.core.model.catalog.product.SellerTextType;
import com.salesmanager.core.model.catalog.product.type.ProductType;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.mapper.catalog.PersistableProductTypeMapper;
import com.salesmanager.shop.mapper.catalog.ReadableProductTypeMapper;
import com.salesmanager.shop.model.catalog.product.type.PersistableProductType;
import com.salesmanager.shop.model.catalog.product.type.ReadableProductType;
import com.salesmanager.shop.model.catalog.product.type.ReadableProductTypeList;
import com.salesmanager.shop.store.api.exception.OperationNotAllowedException;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.controller.product.facade.ProductTypeFacade;
import com.salesmanager.shop.store.controller.product.facade.SellerTextInfoFacade;
import org.jsoup.helper.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service("sellerTextInfoFacade")
public class SellerTextInfoFacadeImpl implements SellerTextInfoFacade {

	@Autowired
	private SellerTextInfoService sellerTextInfoService;


	@Override
	public List<SellerProductShippingTextInfo> getSellerProductShippingTextInfoListByMerchant(MerchantStore store) {
		List<SellerTextInfo> sellerTextInfos = sellerTextInfoService.querySellerTextInfoList(Long.valueOf(store.getId()), SellerTextType.PRODUCT_SHIPPING.name());
		if (CollectionUtils.isEmpty(sellerTextInfos)){
			return null;
		}
		List<SellerProductShippingTextInfo> result = sellerTextInfos.stream().map(sellerTextInfo -> {
			return JSON.parseObject(sellerTextInfo.getText(), SellerProductShippingTextInfo.class);
		}).collect(Collectors.toList());
		return result;
	}

	@Override
	public SellerProductShippingTextInfo getSellerProductShippingTextById(Long id) {
		SellerTextInfo sellerTextInfo = sellerTextInfoService.getById(id);
		if (sellerTextInfo == null){
			return null;
		}
		return JSON.parseObject(sellerTextInfo.getText(), SellerProductShippingTextInfo.class);
	}

	@Override
	public Long save(SellerProductShippingTextInfo sellerProductShippingTextInfo, MerchantStore store) {
		if (sellerProductShippingTextInfo.getId()!= null && sellerProductShippingTextInfo.getId()>0){
			update(sellerProductShippingTextInfo, store);
			return sellerProductShippingTextInfo.getId();
		}
		SellerTextInfo sellerTextInfo = new SellerTextInfo();
		sellerTextInfo.setSellerId(Long.valueOf(store.getId()));
		sellerTextInfo.setText(JSON.toJSONString(sellerProductShippingTextInfo));
		sellerTextInfo.setType(SellerTextType.PRODUCT_SHIPPING);
		try {
			sellerTextInfoService.save(sellerTextInfo);
		} catch (ServiceException e) {
			throw new RuntimeException(e);
		}
		return sellerTextInfo.getId();
	}

	@Override
	public void update(SellerProductShippingTextInfo sellerProductShippingTextInfo, MerchantStore store) {
		SellerTextInfo sellerTextInfo = new SellerTextInfo();
		sellerTextInfo.setId(sellerProductShippingTextInfo.getId());
		sellerTextInfo.setSellerId(Long.valueOf(store.getId()));
		sellerTextInfo.setText(JSON.toJSONString(sellerProductShippingTextInfo));
		sellerTextInfo.setType(SellerTextType.PRODUCT_SHIPPING);
		try {
			sellerTextInfoService.update(sellerTextInfo);
		} catch (ServiceException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delete(Long id) throws ServiceException {
		SellerTextInfo sellerTextInfo = sellerTextInfoService.getById(id);
		sellerTextInfoService.delete(sellerTextInfo);
	}
}
