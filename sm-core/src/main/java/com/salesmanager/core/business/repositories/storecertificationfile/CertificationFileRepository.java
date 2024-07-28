package com.salesmanager.core.business.repositories.storecertificationfile;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.salesmanager.core.model.merchant.certificationfile.CertificationFileEntity;

public interface CertificationFileRepository extends JpaRepository<CertificationFileEntity, Long> {
	
	@Query(value = "select *\r\n"
			+ "from certification_file cf \r\n"
			+ "where 1 = 1\r\n"
			+ "and TEMPLETE_ID = ?1", nativeQuery = true)
	List<CertificationFileEntity> listByTempletId(Long templetId);
	
	@Modifying
	@Query(value = "update certification_file "
			+ "set BASE_YN = ?2 "
			+ ", DATE_MODIFIED = ?4 "
			+ ", UPDT_ID = ?3 "
			+ "where 1 = 1 "
			+ "and CERTIFICATION_FILE_ID  = ?1 "
			+ "", nativeQuery = true)
	void updateFileState(Long id, String baseYn, String modifiedBy, Date dateModified);

}
