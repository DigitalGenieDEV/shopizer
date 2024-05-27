package com.salesmanager.core.business.repositories.accesscontrol;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.salesmanager.core.model.accesscontrol.AccessControl;
import com.salesmanager.core.model.accesscontrol.ReadAccessControl;

public interface PageableAccessControlRepository  extends PagingAndSortingRepository<AccessControl, Integer> {
	
	 @Query(value = "SELECT  A.ID, A.TITLE, A.REG_TYPE AS REGTYPE, A.IP_ADDRESS AS IPADDRESS, A.VISIBLE, A.TARGET_SITE AS TARGETSITE FROM ACCESS_CONTROL A WHERE 1=1"
		  		+ " AND (CASE WHEN  ?1 != '' THEN  TITLE LIKE %?1%   ELSE TRUE  END ) "
		  		+ " ORDER BY REG_DATE DESC ",
		      countQuery = "SELECT  COUNT(ID)  FROM ACCESS_CONTROL A  WHERE 1=1  AND (CASE WHEN  ?1 != '' THEN  TITLE LIKE %?1%   ELSE TRUE  END )", nativeQuery=true)
	Page<ReadAccessControl> getAccessControlList(String keyword, Pageable pageable);

}
