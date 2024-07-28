package com.salesmanager.core.business.repositories.storepropertyrightsfile;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.salesmanager.core.model.merchant.propertyrights.PropertyRightsConfigEntity;

public interface PropertyRightsConfigRepository extends JpaRepository<PropertyRightsConfigEntity, Long> {
	
	@Modifying
	@Query(value = "update PROPERTYRIGHTS_CONFIG "
			+ "set DATE_MODIFIED = now(), "
			+ "	UPDT_ID = ?2, "
			+ "	DELETE_YN = 'Y' "
			+ "where 1 = 1 "
			+ "	and TEMPLETE_ID = ?1 ", nativeQuery = true)
	void deleteTemplete(Long id, String modifiedBy);
	
	@Query(value = "select * "
			+ "from PROPERTYRIGHTS_CONFIG "
			+ "where 1 = 1 "
			+ "and DELETE_YN = 'N'  "
			+ "and MERCHANT_ID = ?1 ", nativeQuery = true)
	List<PropertyRightsConfigEntity> listByStoreId(Integer storeId);

}
