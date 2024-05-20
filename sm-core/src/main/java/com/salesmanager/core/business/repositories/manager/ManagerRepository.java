package com.salesmanager.core.business.repositories.manager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
	
	
	@Query( value ="SELECT B.* FROM MANAGER B WHERE ACTIVE = 1 AND  B.EMPL_ID = ?1", nativeQuery=true)
	Manager getByUserName(String userName);
	
	@Query( value ="SELECT COUNT(USER_ID) FROM MANAGER B WHERE B.EMPL_ID = ?1", nativeQuery=true)
	int getDupEmplIdCount(String userId);

	@Modifying
	@Query(value ="DELETE FROM MANAGER WHERE GRP_ID = ?1", nativeQuery=true)
	void deleteManager(int grpId);
	
	@Modifying
	@Query(value ="UPDATE MANAGER SET LOGIN_FAIL_COUNT = (LOGIN_FAIL_COUNT+1) WHERE EMPL_ID = ?1", nativeQuery=true)
	void updateLoginFailCount(String emplId);
	
	@Modifying
	@Query(value ="UPDATE MANAGER SET LOGIN_FAIL_COUNT=0, LOGIN_DATE = NOW() WHERE EMPL_ID = ?1", nativeQuery=true)
	void updateLoginDate(String emplId);
}

