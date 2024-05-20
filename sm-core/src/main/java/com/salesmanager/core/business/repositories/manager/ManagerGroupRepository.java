package com.salesmanager.core.business.repositories.manager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.salesmanager.core.model.manager.ManagerGroup;
import com.salesmanager.core.model.manager.ReadManagerGroup;

public interface ManagerGroupRepository  extends JpaRepository<ManagerGroup, Integer> {
	
	@Query( value ="SELECT A.ID, A.GRP_NAME AS GRPNAME, COUNT(*) AS CNT FROM MANAGER_GROUP A LEFT OUTER JOIN MANAGER B ON A.ID = B.GRP_ID WHERE 1 = 1 AND A.ID = ?1 "
			+ "GROUP BY A.ID  ", nativeQuery=true)
	ReadManagerGroup getById(int id);

}
