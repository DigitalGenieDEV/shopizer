package com.salesmanager.core.business.repositories.manager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.salesmanager.core.model.manager.ManagerGroup;

public interface PageableManagerGroupRepository  extends JpaRepository<ManagerGroup, Integer> {
	
	
	 @Query(value = "SELECT  A.* FROM MANAGER_GROUP A WHERE 1 = 1 "
		  		+ "AND (CASE WHEN ?1 <> '' THEN GRP_NAME LIKE %?1%  ELSE TRUE  END ) "
		  		+ "ORDER BY REG_DATE DESC ",
		      countQuery = "SELECT  COUNT(ID)  FROM MANAGER_GROUP A WHERE 1 = 1	 AND (CASE WHEN   WHEN ?1 <> '' THEN GRP_NAME LIKE %?1%  ELSE TRUE  END )", nativeQuery=true)
		Page<ManagerGroup> getManagerGroupList(String keyword, Pageable pageable);

}
