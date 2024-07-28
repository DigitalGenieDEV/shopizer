package com.salesmanager.core.business.repositories.storepropertyrightsfile;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.salesmanager.core.model.merchant.propertyrights.PropertyRightsFileEntity;

public interface PropertyRightsFileRepository extends JpaRepository<PropertyRightsFileEntity, Long> {
	
	@Query(value = "select *\r\n"
			+ "from PROPERTYRIGHTS_FILE cf \r\n"
			+ "where 1 = 1\r\n"
			+ "and TEMPLETE_ID = ?1", nativeQuery = true)
	List<PropertyRightsFileEntity> listByTempletId(Long templetId);
	
	@Modifying
	@Query(value = "update PROPERTYRIGHTS_FILE "
			+ "set BASE_YN = ?2 "
			+ ", DATE_MODIFIED = ?4 "
			+ ", UPDT_ID = ?3 "
			+ "where 1 = 1 "
			+ "and PROPERTYRIGHTS_FILE_ID  = ?1 "
			+ "", nativeQuery = true)
	void updateFileState(Long id, String baseYn, String modifiedBy, Date dateModified);

}
