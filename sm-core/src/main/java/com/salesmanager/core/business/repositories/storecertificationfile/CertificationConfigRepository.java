package com.salesmanager.core.business.repositories.storecertificationfile;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.salesmanager.core.model.merchant.certificationfile.CertificationConfigEntity;

public interface CertificationConfigRepository extends JpaRepository<CertificationConfigEntity, Long> {
	
	@Modifying
	@Query(value = "update CERTIFICATION_CONFIG "
			+ "set DATE_MODIFIED = now(), "
			+ "	UPDT_ID = ?2, "
			+ "	DELETE_YN = 'Y' "
			+ "where 1 = 1 "
			+ "	and TEMPLETE_ID = ?1 ", nativeQuery = true)
	void deleteTemplete(Long id, String modifiedBy);
	
	@Query(value = "select * "
			+ "from CERTIFICATION_CONFIG "
			+ "where 1 = 1 "
			+ "and DELETE_YN = 'N'  "
			+ "and MERCHANT_ID = ?1 ", nativeQuery = true)
	List<CertificationConfigEntity> listByStoreId(Integer storeId);

}
