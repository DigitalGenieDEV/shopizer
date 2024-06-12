package com.salesmanager.shop.store.facade.product;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.jsoup.helper.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.salesmanager.core.business.services.catalog.product.attribute.ProductOptionService2;
import com.salesmanager.core.model.catalog.product.attribute.ReadProductOption;
import com.salesmanager.core.model.catalog.product.attribute.ReadProductOptionValue;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.product.attribute.DeleteProductValue;
import com.salesmanager.shop.model.catalog.product.attribute.ReadableProductOption2;
import com.salesmanager.shop.model.catalog.product.attribute.ReadableProductOptionValue2;
import com.salesmanager.shop.model.catalog.product.attribute.api.ReadableProductOptionList2;
import com.salesmanager.shop.model.catalog.product.attribute.api.ReadableProductOptionValueList2;
import com.salesmanager.shop.store.controller.product.facade.ProductOptionFacade2;

@Service
public class ProductOptionFacadeImpl2 implements ProductOptionFacade2 {

	@Autowired
	private ProductOptionService2 productOptionService2;

	@Inject
	private ObjectMapper objectMapper;

	public ReadableProductOptionList2 getListOption(MerchantStore store, Language language, String name, int categoryId,
			int page, int count) throws Exception {
		Validate.notNull(store, "MerchantStore should not be null");

		Page<ReadProductOption> options = productOptionService2.getListOption(store, language, name, categoryId, page,
				count);
		List<ReadableProductOption2> dataList = new ArrayList<ReadableProductOption2>();
		ReadableProductOptionList2 valueList = new ReadableProductOptionList2();
		valueList.setTotalPages(options.getTotalPages());
		valueList.setRecordsTotal(options.getTotalElements());
		valueList.setNumber(options.getNumber());

		if (options.getSize() > 0) {
			for (ReadProductOption data : options) {
				objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
				ReadableProductOption2 targetData = objectMapper.convertValue(data, ReadableProductOption2.class);
				dataList.add(targetData);
			}
		}

		valueList.setOptions(dataList);

		return valueList;
	}

	public ReadableProductOptionValueList2 getListOptionValues(MerchantStore store, Language language, int setId,
			int categoryId) throws Exception {
		
		ReadableProductOptionValueList2 targetList = new ReadableProductOptionValueList2();
		List<ReadableProductOptionValue2>  dataList =  new ArrayList<ReadableProductOptionValue2> ();
		List<ReadProductOptionValue> valueList = productOptionService2.getListOptionValues(store, language, setId, categoryId);
		if (valueList.size() > 0) {
			for (ReadProductOptionValue data : valueList) {
				objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
				ReadableProductOptionValue2 targetData = objectMapper.convertValue(data, ReadableProductOptionValue2.class);
				dataList.add(targetData);
			}
		}
		targetList.setData(dataList);
		
		
		return targetList;
		
	}
	
	
	public void deleteValues(DeleteProductValue delValue)  throws Exception{
		productOptionService2.deleteValues(delValue.getSetId(), delValue.getValueId());
	}
	
	public void deleteOption(DeleteProductValue delOption)  throws Exception{
		productOptionService2.deleteOption(delOption.getSetId(), delOption.getOptionId());
	}

}
