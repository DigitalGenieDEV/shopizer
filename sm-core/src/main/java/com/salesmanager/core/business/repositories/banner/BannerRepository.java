package com.salesmanager.core.business.repositories.banner;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.salesmanager.core.model.banner.Banner;
import com.salesmanager.core.model.banner.ReadUserBanner;

public interface BannerRepository extends JpaRepository<Banner, Integer> {

	@Query(value = "SELECT ID, POSITION, NAME, URL, CASE WHEN TARGET = 'N' THEN '_self' ELSE  '_blank' END LINKTARGET, IMAGE FROM BANNER \r\n"
			+ "WHERE 1=1\r\n"
			+ "	AND (SITE = 0 OR SITE = ?1  )\r\n"
			+ "	AND NOW() BETWEEN STR_TO_DATE(SDATE, '%Y-%m-%d %H:%i:%s') AND STR_TO_DATE(EDATE, '%Y-%m-%d %H:%i:%s')\r\n"
			+ "ORDER BY ORD ASC", nativeQuery = true)
	List<ReadUserBanner> getBannerUserList(String site);
}
