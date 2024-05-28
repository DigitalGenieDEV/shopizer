package com.salesmanager.core.business.repositories.privacy;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.salesmanager.core.model.privacy.Privacy;

public interface PageablePrivacyRepository  extends JpaRepository<Privacy, Integer> {

	 @Query(value = "SELECT  * FROM PRIVACY A WHERE 1=1"
				+ " AND (CASE WHEN  ?1  = 0 THEN VISIBLE = 0 ELSE TRUE END) "
				+ " AND (CASE WHEN  ?2 != '0' THEN  DIVISION  = ?2   ELSE TRUE  END ) "
		  		+ " AND (CASE WHEN  ?3 != '' THEN  TITLE LIKE %?3%   ELSE TRUE  END ) "
		  		+ "ORDER BY A.ID DESC ",
		      countQuery = "SELECT  COUNT(ID)  FROM PRIVACY A WHERE 1=1  AND (CASE WHEN  ?1  = 0 THEN VISIBLE = 0 ELSE TRUE END) AND (CASE WHEN  ?2 != '0' THEN  DIVISION  = ?2   ELSE TRUE  END ) AND (CASE WHEN  ?3 != '' THEN  TITLE LIKE %?3%   ELSE TRUE  END )", nativeQuery=true)
	 Page<Privacy> getListPrivacy(int visible, String devision, String keyword, Pageable pageable);

}
