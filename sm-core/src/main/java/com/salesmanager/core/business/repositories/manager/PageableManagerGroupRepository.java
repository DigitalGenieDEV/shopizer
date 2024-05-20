package com.salesmanager.core.business.repositories.manager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.salesmanager.core.model.manager.ManagerGroup;
import com.salesmanager.core.model.manager.ReadManagerGroup;

public interface PageableManagerGroupRepository  extends JpaRepository<ManagerGroup, Integer> {
	
	
	 @Query(value = "SELECT A.ID, A.GRP_NAME AS GRPNAME, COUNT(B.USER_ID) AS CNT FROM MANAGER_GROUP A LEFT OUTER JOIN MANAGER B ON A.ID = B.GRP_ID WHERE 1 = 1  "
			    + "AND (CASE WHEN ?2  = 1 THEN  A.ID > 1 ELSE TRUE  END ) "
		  		+ "AND (CASE WHEN ?1 <> '' THEN A.GRP_NAME LIKE %?1%  ELSE TRUE  END ) "
		  		+ "GROUP BY A.ID  "
		  		+ "ORDER BY A.ID ASC ",
		      countQuery = "SELECT  COUNT(ID)  FROM MANAGER_GROUP A WHERE 1 = 1	 AND (CASE WHEN ?2  = 1 THEN  A.ID > 1 ELSE TRUE  END ) AND (CASE WHEN   WHEN ?1 <> '' THEN GRP_NAME LIKE %?1%  ELSE TRUE  END )", nativeQuery=true)
		Page<ReadManagerGroup> getManagerGroupList(String keyword, int visible, Pageable pageable);

}
