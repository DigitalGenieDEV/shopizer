package com.salesmanager.core.business.repositories.storecertificationfile;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.salesmanager.core.model.merchant.certificationfile.CertificationConfigEntity;

public interface CertificationConfigRepository extends JpaRepository<CertificationConfigEntity, Long> {
	
	@Modifying
	@Query(value = "update certification_config "
			+ "set DATE_MODIFIED = now(), "
			+ "	UPDT_ID = ?2, "
			+ "	DELETE_YN = 'Y' "
			+ "where 1 = 1 "
			+ "	and TEMPLETE_ID = ?1 ", nativeQuery = true)
	void deleteTemplete(Long id, String modifiedBy);
	
	@Query(value = "select * "
			+ "from certification_config "
			+ "where 1 = 1 "
			+ "and DELETE_YN = 'N'  "
			+ "and MERCHANT_ID = ?1 ", nativeQuery = true)
	List<CertificationConfigEntity> listByStoreId(Integer storeId);

}
