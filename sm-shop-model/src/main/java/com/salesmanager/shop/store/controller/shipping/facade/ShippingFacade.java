package com.salesmanager.shop.store.controller.shipping.facade;

import java.util.List;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.model.common.Criteria;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.core.model.shipping.PackageDetails;
import com.salesmanager.core.model.shipping.ShippingOrigin;
import com.salesmanager.shop.model.references.PersistableAddress;
import com.salesmanager.shop.model.references.ReadableAddress;
import com.salesmanager.shop.model.references.ReadableCountry;
import com.salesmanager.shop.model.shipping.ExpeditionConfiguration;
import com.salesmanager.shop.model.shipping.PersistableMerchantShippingConfiguration;
import com.salesmanager.shop.model.shipping.ReadableMerchantShippingConfiguration;
import com.salesmanager.shop.model.shipping.ReadableMerchantShippingConfigurationList;

public interface ShippingFacade {
	
	ExpeditionConfiguration getExpeditionConfiguration(MerchantStore store, Language language);
	void saveExpeditionConfiguration(ExpeditionConfiguration expedition, MerchantStore store);
	
	
	ReadableAddress getShippingOrigin(MerchantStore store);
	ShippingOrigin saveShippingOrigin(PersistableAddress address, MerchantStore store);
	

	void createPackage(PackageDetails packaging, MerchantStore store);
	
	PackageDetails getPackage(String code, MerchantStore store);
	
	/**
	 * List of configured ShippingCountry for a given store
	 * @param store
	 * @param lanuage
	 * @return
	 */
	List<ReadableCountry> shipToCountry(MerchantStore store, Language lanuage);
	
	List<PackageDetails> listPackages(MerchantStore store);
	
	void updatePackage(String code, PackageDetails packaging, MerchantStore store);
	
	void deletePackage(String code, MerchantStore store);

	ReadableMerchantShippingConfiguration getById(MerchantStore store, Long id);

	ReadableMerchantShippingConfigurationList list(MerchantStore store, Criteria criteria, String shippingType, String shippingTransportationType) throws ServiceException;

	void save(MerchantStore store, PersistableMerchantShippingConfiguration configuration) throws ServiceException;

	void delete(Long id) throws ServiceException;


}
