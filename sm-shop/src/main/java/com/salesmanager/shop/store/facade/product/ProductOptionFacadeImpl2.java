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
import com.salesmanager.shop.model.catalog.product.attribute.PersistableProductOptionSetValueEntity;
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
	
	
	public void deleteSetValues(DeleteProductValue delValue)  throws Exception{
		productOptionService2.deleteSetValues(delValue.getSetId(), delValue.getValueId());
	}
	
	public void deleteSetOption(DeleteProductValue delOption)  throws Exception{
		productOptionService2.deleteSetOption(delOption.getSetId(), delOption.getOptionId());
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
	
	
	public int getOptionSet(MerchantStore store, Language language,  Long optionId) throws Exception {
		return productOptionService2.getOptionSet(store,language, optionId );
	}
	
	public int getOptionSetValue(MerchantStore store, Language language,  Long valueId) throws Exception{
		return productOptionService2.getOptionSet(store,language, valueId );
	}
	
	public int getOptionNameCount(MerchantStore store, Language language,  String name) throws Exception {
		return productOptionService2.getOptionNameCount(store,language, name );
	}
	
	public int getOptionValueNameCount(MerchantStore store, Language language,  String name) throws Exception{
		return productOptionService2.getOptionValueNameCount(store,language, name );
	}
	
	public ReadableProductOptionList2 getListOptionKeyword(MerchantStore store, Language language, String keyword, int categoryId) throws Exception{
		ReadableProductOptionList2 targetList =  new ReadableProductOptionList2();
		List<ReadableProductOption2> dataList = new ArrayList<ReadableProductOption2>();
		List<ReadProductOption> optionList = productOptionService2.getListOptionKeyword(store, language, keyword, categoryId);
		if (optionList.size() > 0) {
			for (ReadProductOption data : optionList) {
				objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
				ReadableProductOption2 targetData = objectMapper.convertValue(data, ReadableProductOption2.class);
				dataList.add(targetData);
			}
		}
		
		targetList.setOptions(dataList);
		
		return targetList;
	}
	
	public ReadableProductOptionValueList2 getListOptionKeywordValues(MerchantStore store, Language language, String keyword, int categoryId, Integer setId) throws Exception{
		ReadableProductOptionValueList2 targetList = new ReadableProductOptionValueList2();
		List<ReadableProductOptionValue2>  dataList =  new ArrayList<ReadableProductOptionValue2> ();
		List<ReadProductOptionValue> valueList = productOptionService2.getListOptionKeywordValues(store, language, setId, categoryId, keyword);
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
	
	
	public void insertOptionSetValue(PersistableProductOptionSetValueEntity data)  throws Exception{
		productOptionService2.insertOptionSetValue(data.getSetId(), data.getValueId());
	}
	

}
