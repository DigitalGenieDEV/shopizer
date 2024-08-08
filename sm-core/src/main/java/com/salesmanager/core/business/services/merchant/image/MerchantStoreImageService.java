package com.salesmanager.core.business.services.merchant.image;

import java.util.Optional;

import com.salesmanager.core.model.merchant.image.MerchantStoreImage;

public interface MerchantStoreImageService {
	void save(MerchantStoreImage image);
	void delete(Long id);
	void deleteByStoreId(Integer storeId);
}
