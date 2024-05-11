package com.salesmanager.core.business.repositories.manager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.salesmanager.core.model.manager.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
	
	@Query( value ="SELECT 	"
			+ "ACTIVE \r\n"
			+ "	,ADMIN_EMAIL \r\n"
			+ "	,ADMIN_NAME \r\n"
			+ "	,CONTENT \r\n"
			+ "	,DEPT_ID \r\n"
			+ "	,DEPT_NAME \r\n"
			+ "	,EMPL_ID \r\n"
			+ "	,FAX \r\n"
			+ "	,FAX_ID \r\n"
			+ "	,GRP_ID \r\n"
			+ "	,GRP_NAME \r\n"
			+ "	,LOGIN_DATE \r\n"
			+ "	,LOGIN_FAIL_COUNT \r\n"
			+ "	,MOD_DATE \r\n"
			+ "	,POSITION \r\n"
			+ "	,POSITION_ID \r\n"
			+ "	,TEL1 \r\n"
			+ "	,TEL2 \r\n"
			+ "	,TEL_ID1 \r\n"
			+ "	,TEL_ID2 \r\n"
			+ "	,USER_ID FROM MANAGER B WHERE B.USER_ID = ?1", nativeQuery=true)
	Manager getId(Long id);
	
	@Query( value ="SELECT COUNT(USER_ID) FROM MANAGER B WHERE B.EMPL_ID = ?1", nativeQuery=true)
	int getDupEmplIdCount(String userId);
}
