package com.salesmanager.core.business.repositories.banner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.salesmanager.core.model.banner.Banner;
import com.salesmanager.core.model.banner.ReadBanner;

public interface PageableBannerRepository extends JpaRepository<Banner, Integer> {


	@Query(value = "SELECT  ID, NAME, SITE, POSITION,  DATE_FORMAT(SDATE, '%Y-%m-%d') AS SDATE, DATE_FORMAT(EDATE, '%Y-%m-%d') AS EDATE,  URL,TARGET,  IMAGE, ORD, VISIBLE FROM BANNER \r\n"
			+ "WHERE 1=1  \r\n"
			+ "	AND (CASE WHEN  ?1 != '' THEN  SITE = ?1 ELSE TRUE END  ) \r\n"
			+ "	AND (CASE WHEN  ?2 != '' THEN  NAME LIKE %?2%  ELSE TRUE  END )\r\n"
			+ "ORDER BY REG_DATE DESC ",
	      countQuery = "SELECT  COUNT(ID)  FROM BANNER  WHERE 1=1  \r\n"
	    		+ "	AND (CASE WHEN  ?1 != '' THEN  SITE = ?1 ELSE TRUE END  ) \r\n"
	  			+ "	AND (CASE WHEN  ?2 != '' THEN  NAME LIKE %?2%  ELSE TRUE  END )\r\n", nativeQuery=true)
	Page<ReadBanner> getBannerList(String site, String keyword, Pageable pageable);

	
}
