package com.salesmanager.core.business.repositories.adminmenu;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.salesmanager.core.model.adminmenu.AdminMenu;
import com.salesmanager.core.model.adminmenu.ReadAdminMenu;

public interface AdminMenuRepository extends JpaRepository<AdminMenu, Integer> {

	@Query(value = "WITH RECURSIVE MENU_CTE AS (    \r\n" + "			SELECT     \r\n"
			+ "				ID, PARENT_ID, MENU_NAME, MENU_URL, MENU_DESC, ORD, VISIBLE   \r\n"
			+ "				, CAST(IFNULL(REPLACE(@level,'3', ''),1) AS SIGNED INTEGER) AS DEPTH    \r\n"
			+ "				, CAST(CONCAT(LPAD(CAST(IFNULL(REPLACE(@level,'3', ''),1) AS CHAR), 5, '0'), '', LPAD(CAST(A.ORD AS CHAR), 5, '0')) AS CHAR(255)) AS AMENU_SORT    \r\n"
			+ "				, A.MENU_NAME AS MENU_NAME_PATH    \r\n"
			+ "			FROM ADMINMENU AS A    \r\n" + "			WHERE A.PARENT_ID = '0'    \r\n"
			+ "		 AND  ((?1 = 0 AND A.VISIBLE = 'Y') OR (?1 = 1 AND A.VISIBLE IN ('Y', 'N')))        \r\n"
			+ "			UNION ALL    \r\n" + "			    \r\n" + "			SELECT    \r\n"
			+ "				  B.ID, B.PARENT_ID, B.MENU_NAME, B.MENU_URL, B.MENU_DESC, B.ORD, B.VISIBLE   \r\n"
			+ "				, C.DEPTH + 1 AS DEPTH    \r\n"
			+ "				, CAST(CONCAT(CONCAT (C.AMENU_SORT, LPAD(CAST(C.DEPTH + 1 AS CHAR), 5, '0')), '', LPAD(CAST(B.ORD AS CHAR), 5, '0')) AS CHAR(255)) AS AMENU_SORT    \r\n"
			+ "				, CONCAT(C.MENU_NAME_PATH, '&gt;', B.MENU_NAME) AS MENU_NAME_PATH    \r\n"
			+ "			FROM    \r\n" + "				ADMINMENU AS B    \r\n" + "				, MENU_CTE AS C    \r\n"
			+ "			WHERE B.PARENT_ID = C.ID    \r\n" + " )    \r\n" + "		    \r\n"
			+ " SELECT  ID, PARENT_ID, MENU_NAME, MENU_URL, MENU_DESC, ORD, VISIBLE, DEPTH, MENU_NAME_PATH FROM MENU_CTE WHERE 1=1    \r\n"
			+ "  	AND (CASE WHEN ?2 > 1 THEN  ID IN (SELECT MENU_ID FROM MANAGER_MENU_AUTH WHERE GRP_ID = ?2) ELSE TRUE END )   "
			+ " ORDER BY AMENU_SORT ASC", nativeQuery = true)
	List<ReadAdminMenu> getListAdminMenu(int visible, int grpId);

	@Query(value = "SELECT IFNULL(MAX(ORD) + 1,1) FROM ADMINMENU B WHERE B.PARENT_ID = ?1", nativeQuery = true)
	int getOrder(int parentId);
	
	@Query( value ="WITH RECURSIVE MENU_CTE AS (\r\n"
			+ "		SELECT \r\n"
			+ "			A.*\r\n"
			+ "           , CAST( A.MENU_NAME AS CHAR(255) ) AS MENU_NAME_PATH\r\n"
			+ "		FROM\r\n"
			+ "			ADMINMENU AS A\r\n"
			+ "		WHERE A.ID = ?1 \r\n"
			+ "			\r\n"
			+ "		UNION ALL\r\n"
			+ "		\r\n"
			+ "		SELECT\r\n"
			+ "			B.*\r\n"
			+ "            , CONCAT(C.MENU_NAME_PATH, '&gt;', B.MENU_NAME) AS MENU_NAME_PATH  \r\n"
			+ "		FROM\r\n"
			+ "			ADMINMENU AS B\r\n"
			+ "			, MENU_CTE AS C\r\n"
			+ "		WHERE B.ID = C.PARENT_ID\r\n"
			+ "		\r\n"
			+ "	)\r\n"
			+ "	SELECT \r\n"
			+ "		 MAX( MENU_NAME_PATH ) AS MENU_NAME_PATH\r\n"
			+ "	FROM MENU_CTE" , nativeQuery=true)
	String  getNamePath(int id);

	@Query(value = " SELECT \r\n"
			+ "		A.ID, A.PARENT_ID, A.MENU_NAME, A.MENU_DESC, A.MENU_URL, A.API_URL, A.ORD,A.VISIBLE  \r\n"
			+ " FROM ADMINMENU AS A \r\n" 
			+ "	WHERE A.ID = ?1 ", nativeQuery = true)
	ReadAdminMenu getById(int id);
	
	
	@Query( value ="SELECT MAX(ID) AS ID FROM ADMINMENU", nativeQuery=true)
	int getMaxId();
	
	@Modifying
	@Query(value = "DELETE FROM ADMINMENU WHERE ID IN \r\n" + "	(\r\n" + "		SELECT 	A.ID FROM\r\n"
			+ "		(\r\n" + "			SELECT \r\n" + "				D.ID,\r\n" + "				D.PARENT_ID\r\n"
			+ "			FROM ADMINMENU  D \r\n" + "			WHERE D.ID = ?1 \r\n" + "			UNION ALL\r\n"
			+ "			SELECT\r\n" + "				 A.ID,\r\n" + "				 A.PARENT_ID\r\n"
			+ "			FROM ADMINMENU A\r\n" + "			INNER JOIN ADMINMENU D ON A.PARENT_ID = D.ID\r\n"
			+ "			WHERE D.ID = ?1 \r\n" + "			\r\n" + "		)A\r\n" + "	)", nativeQuery = true)
	void deleteAdminMenu(int id);

	@Modifying
	@Query(value = "UPDATE ADMINMENU SET ORD = :#{#adminMenu.ord}, PARENT_ID = :#{#adminMenu.parentId}, MOD_ID = :#{#adminMenu.auditSection.modId}, "
			+ "MOD_IP = :#{#adminMenu.auditSection.modIp}, " + "MOD_DATE = NOW() "
			+ "WHERE ID = :#{#adminMenu.id}", nativeQuery = true)
	void updateChangeOrd(@Param("adminMenu") AdminMenu adminMenu);
	
	@Query(value = "SELECT ID FROM adminmenu WHERE API_URL = ?1 ", nativeQuery = true)
	int  getApiMenuFindId(String url);
	
}
