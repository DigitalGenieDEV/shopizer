package com.salesmanager.shop.store.facade.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.catalog.product.information.ProductInformationService;
import com.salesmanager.core.model.catalog.product.information.ProductInformation;
import com.salesmanager.core.model.catalog.product.information.ReadProductInformation;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.product.product.information.PersistableProductInformation;
import com.salesmanager.shop.model.catalog.product.product.information.ProductInformationEntity;
import com.salesmanager.shop.model.catalog.product.product.information.ReadableProductInformation;
import com.salesmanager.shop.populator.catalog.PersistableProductInformationPopulator;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.controller.product.facade.ProductInformationFacade;

@Service
public class ProductInformationFacadeImpl   implements ProductInformationFacade {
	@Inject
	private ProductInformationService productInformationService;
	
	@Inject
	private PersistableProductInformationPopulator persistableProductInformationPopulator;
	
	@Inject
	private ObjectMapper objectMapper;
	
	public ReadableProductInformation getList(MerchantStore merchantStore, Language language,  int page, int count, String division) throws Exception{
		try {
			List<ReadProductInformation> dataList = null;
			List<ProductInformationEntity> targetList = new ArrayList<ProductInformationEntity>();
			ReadableProductInformation returnList = new ReadableProductInformation();
			System.out.println("merchantStore.getId()"+merchantStore.getId());
			System.out.println("merchantStore.getId()"+language.getId());
			Page<ReadProductInformation> pageable = productInformationService.getList(merchantStore.getId(),language.getId(), page, count,division);
			dataList = pageable.getContent();
			returnList.setRecordsTotal(pageable.getTotalElements());
			returnList.setTotalPages(pageable.getTotalPages());
			returnList.setNumber(dataList.size());
			if (dataList.size() > 0) {
				for (ReadProductInformation data : dataList) {
					objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
					ProductInformationEntity targetData = objectMapper.convertValue(data, ProductInformationEntity.class);
					targetList.add(targetData);

				}
			}
			returnList.setData(targetList);
			return returnList;
		} catch (ServiceException e) {
			throw new ServiceRuntimeException(e);
		}
	}
	
	public PersistableProductInformation saveProductInformation(PersistableProductInformation data,MerchantStore merchantStore,  Language language) throws Exception{
		try {

			Long dataId = data.getId();
			ProductInformation target = Optional.ofNullable(dataId)
					.filter(id -> id > 0)
					.map(productInformationService::getById)
					.orElse(new ProductInformation());
			
		
			ProductInformation dbData = populateProductInformation(data, target,merchantStore,language);
			productInformationService.saveOrUpdate(dbData);
			
			return data;
		} catch (ServiceException e) {
			throw new ServiceRuntimeException("Error while updating ProductInformation", e);
		}
	}
	
	private ProductInformation populateProductInformation(PersistableProductInformation data, ProductInformation target,MerchantStore merchantStore,  Language language) {
		try {
			return persistableProductInformationPopulator.populate(data, target,merchantStore,language);
		} catch (ConversionException e) {
			throw new ServiceRuntimeException(e);
		}
	}
	
	public void deleteProductInformation(Long id) throws Exception{
		ProductInformation data = new ProductInformation();
		data.setId(id);
		productInformationService.delete(data);
	}
}
