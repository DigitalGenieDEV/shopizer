package com.salesmanager.core.business.repositories.ipSafetyCenter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.salesmanager.core.model.iprsafecenter.IpSafetyCenter;
import com.salesmanager.core.model.iprsafecenter.ReadIpSafetyCenter;

public interface PageableIpSafetyCenterRepository extends JpaRepository<IpSafetyCenter, Integer> {

	
	@Query(value = "SELECT  ID, REPORT_TYPE AS REPORTTYPE, TITLE, REG_NAME AS REGNAME, DATE_FORMAT(REG_DATE, '%Y-%m-%d') AS REGDATE, STATE FROM IPR_SAFETY_CENTER\r\n"
			+ "WHERE DATE_FORMAT(REG_DATE, '%Y-%m-%d') BETWEEN ?3 AND ?4  \r\n"
			+ "	AND (CASE WHEN  ?1 != '' THEN  REPORT_TYPE = ?1 ELSE TRUE END  ) \r\n"
			+ "	AND (CASE WHEN  ?2 = 'A' THEN  TITLE LIKE %?5% WHEN ?2 = 'B' THEN REG_NAME LIKE %?5%  ELSE TRUE  END )\r\n"
			+ "ORDER BY REG_DATE DESC ",
	      countQuery = "SELECT  COUNT(ID)  FROM IPR_SAFETY_CENTER  WHERE DATE_FORMAT(REG_DATE, '%Y-%m-%d') BETWEEN ?3 AND ?4   \r\n"
	    	    + "	AND (CASE WHEN  ?1 != '' THEN  REPORT_TYPE = ?1 ELSE TRUE END  )\r\n"
	  			+ "	AND (CASE WHEN  ?2 = 'A' THEN  TITLE LIKE %?5% WHEN ?2 = 'B' THEN REG_NAME LIKE %?5%  ELSE TRUE  END )\r\n", nativeQuery=true)
	Page<ReadIpSafetyCenter> getIpSafetyList(String type, String gbn, String sdate, String edate, String keyword, Pageable pageable);
}
