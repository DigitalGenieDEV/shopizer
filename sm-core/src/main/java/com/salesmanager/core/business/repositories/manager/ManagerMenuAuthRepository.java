package com.salesmanager.core.business.repositories.manager;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.salesmanager.core.model.manager.MenuAuth;
import com.salesmanager.core.model.manager.ReadMenuAuth;

public interface ManagerMenuAuthRepository   extends JpaRepository<MenuAuth, Integer> {

	
	@Query( value ="SELECT 	ID, MENU_ID AS MENUID, GRP_ID AS GRPID FROM MANAGER_MENU_AUTH WHERE GRP_ID = ?1", nativeQuery=true)
	List<ReadMenuAuth> getManagerAdminMenuAuthList(int grpId);
	
	@Modifying
	@Query(value ="DELETE FROM MANAGER_MENU_AUTH WHERE GRP_ID = ?1", nativeQuery=true)
	void deleteMenuAuth(int grpId);
	
	@Query(value =" SELECT COUNT(*) AS CNT FROM MANAGER_MENU_AUTH WHERE GRP_ID = ?1 AND MENU_ID = ?2 " , nativeQuery=true)
	int getMenuAuthCnt(int grpId, int menuId);
}
