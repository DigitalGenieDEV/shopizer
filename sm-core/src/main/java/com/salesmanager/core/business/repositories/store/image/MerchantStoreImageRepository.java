package com.salesmanager.core.business.repositories.store.image;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.salesmanager.core.model.merchant.certificationfile.CertificationFileEntity;
import com.salesmanager.core.model.merchant.image.MerchantStoreImage;

public interface MerchantStoreImageRepository extends JpaRepository<MerchantStoreImage, Long> {
	
	@Transactional
	@Modifying
	@Query(value = "delete "
			+ "from MERCHANT_IMAGE "
			+ "where 1 = 1 "
			+ "and MERCHANT_ID = ?1", nativeQuery = true)
	void deleteByStoreId(Integer storeId);
}
