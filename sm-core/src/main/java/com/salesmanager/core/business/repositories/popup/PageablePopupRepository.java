package com.salesmanager.core.business.repositories.popup;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.salesmanager.core.model.popup.Popup;
import com.salesmanager.core.model.popup.ReadPopup;

public interface PageablePopupRepository extends JpaRepository<Popup, Integer> {

	@Query(value = "SELECT  ID, NAME, SITE, TYPE, DATE_FORMAT(SDATE, '%Y-%m-%d') AS SDATE, DATE_FORMAT(EDATE, '%Y-%m-%d') AS EDATE,  URL,TARGET,  IMAGE, ORD, VISIBLE FROM POPUP \r\n"
			+ "WHERE 1=1  \r\n"
			+ "	AND (CASE WHEN  ?1 != '' THEN  SITE = ?1 ELSE TRUE END  ) \r\n"
			+ "	AND (CASE WHEN  ?2 != '' THEN  NAME LIKE %?2%  ELSE TRUE  END )\r\n"
			+ "ORDER BY REG_DATE DESC ",
	      countQuery = "SELECT  COUNT(ID)  FROM POPUP  WHERE 1=1  \r\n"
	    		+ "	AND (CASE WHEN  ?1 != '' THEN  SITE = ?1 ELSE TRUE END  ) \r\n"
	  			+ "	AND (CASE WHEN  ?2 != '' THEN  NAME LIKE %?2%  ELSE TRUE  END )\r\n", nativeQuery=true)
	Page<ReadPopup> getPopupList(String site, String keyword, Pageable pageable);
}
