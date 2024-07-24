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
import com.salesmanager.core.business.services.reference.language.LanguageService;
import com.salesmanager.core.model.catalog.product.attribute.ReadProductOption;
import com.salesmanager.core.model.catalog.product.attribute.ReadProductOption2;
import com.salesmanager.core.model.catalog.product.attribute.ReadProductOptionValue;
import com.salesmanager.core.model.catalog.product.attribute.ReadProductOptionValue2;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.product.attribute.DeleteProductValue;
import com.salesmanager.shop.model.catalog.product.attribute.ReadableProductOption2;
import com.salesmanager.shop.model.catalog.product.attribute.ReadableProductOption3;
import com.salesmanager.shop.model.catalog.product.attribute.ReadableProductOptionValue2;
import com.salesmanager.shop.model.catalog.product.attribute.ReadableProductOptionValue4;
import com.salesmanager.shop.model.catalog.product.attribute.api.ReadableProductOptionList2;
import com.salesmanager.shop.model.catalog.product.attribute.api.ReadableProductOptionList3;
import com.salesmanager.shop.model.catalog.product.attribute.api.ReadableProductOptionValueList2;
import com.salesmanager.shop.store.controller.product.facade.ProductOptionFacade2;

@Service
public class ProductOptionFacadeImpl2 implements ProductOptionFacade2 {

	@Autowired
	private ProductOptionService2 productOptionService2;
	
	@Autowired
	private LanguageService languageService;

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
	
	public ReadableProductOptionList3 getProductListOption(MerchantStore store, Language language,  int categoryId, String division) throws Exception{
		
		Language lang = languageService.getByCode(language.getCode());
		
		ReadableProductOptionList3 reiciveData =  new ReadableProductOptionList3();
		List<ReadProductOption2> propertiesList = productOptionService2.getProductListOption(store.getId(), lang.getId(),categoryId,division);
		List<ReadProductOptionValue2> optionValueList = productOptionService2.getProductListOptionValue(store.getId(), lang.getId(),categoryId,  division);
		List<ReadableProductOption3> dataList = new ArrayList<ReadableProductOption3>();
	
		
		if (propertiesList.size() > 0) {
			for (ReadProductOption2 data : propertiesList) {
				List<ReadableProductOptionValue4> dataList2 = new ArrayList<ReadableProductOptionValue4>();
				objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
				ReadableProductOption3 targetData = objectMapper.convertValue(data, ReadableProductOption3.class);
				for (ReadProductOptionValue2  option : optionValueList) {
					if(data.getId().equals(option.getId()) && data.getOptionId().equals(option.getOptionId())) {
					
						objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
						ReadableProductOptionValue4 targetData2 = objectMapper.convertValue(option, ReadableProductOptionValue4.class);
						
						 dataList2.add(targetData2);
						 targetData.setOptionValue(dataList2);
						
						
					}
				}
				 dataList.add(targetData);
				//targetData.setOptionValue(dataList2);
				
			}
		}
		
		reiciveData.setProperties(dataList);
		return reiciveData;
	}
	
	

}
