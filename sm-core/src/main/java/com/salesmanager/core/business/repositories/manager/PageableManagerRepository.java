package com.salesmanager.core.business.repositories.manager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.salesmanager.core.model.manager.Manager;

public interface PageableManagerRepository extends PagingAndSortingRepository<Manager, Long> {
	
	
	  @Query(value = "SELECT  A.* FROM MANAGER A WHERE 1 = 1 "
	  		+ "AND (CASE WHEN  ?1 = 'A' THEN  EMPL_ID LIKE %?2% WHEN ?1 = 'B' THEN ADMIN_NAME LIKE %?2%  ELSE TRUE  END ) "
	  		+ "AND (CASE WHEN  ?3  = 0 THEN DEPT_ID IS NOT NULL ELSE DEPT_ID = ?3  END) "
	  		+ "ORDER BY USER_ID DESC ",
	      countQuery = "SELECT  COUNT(USER_ID)  FROM MANAGER A WHERE 1 = 1	 AND ( CASE WHEN  ?1 = 'A' THEN  EMPL_ID LIKE %?2% WHEN ?1 = 'B' THEN ADMIN_NAME LIKE %?2% ELSE TRUE  END ) AND (CASE WHEN ?3  = 0 THEN DEPT_ID IS NOT NULL ELSE DEPT_ID = ?3 END)", nativeQuery=true)
	Page<Manager> getManagerList(String gbn, String keyword, int deptId, Pageable pageable);

}
