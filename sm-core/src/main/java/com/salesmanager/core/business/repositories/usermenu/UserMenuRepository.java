package com.salesmanager.core.business.repositories.usermenu;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.salesmanager.core.model.usermenu.ReadUserMenu;
import com.salesmanager.core.model.usermenu.UserMenu;

public interface UserMenuRepository extends JpaRepository<UserMenu, Integer> {
	
	
	@Query(value = "WITH RECURSIVE MENU_CTE AS (    		\r\n"
			+ "	SELECT     \r\n"
			+ "		  ID, PARENT_ID, MENU_NAME, MENU_NAME_EN, MENU_NAME_CN, MENU_NAME_JP, URL, MENU_DESC, ORD, VISIBLE, MEMBER_TARGET, LINK_TARGET\r\n"
			+ "	    , TOP, SIDE, NAVI, TAB \r\n"
			+ "		, CAST(IFNULL(REPLACE(@level,'3', ''),1) AS SIGNED INTEGER) AS DEPTH    \r\n"
			+ "		, CAST(CONCAT(LPAD(CAST(IFNULL(REPLACE(@level,'3', ''),1) AS CHAR), 5, '0'), '', LPAD(CAST(A.ORD AS CHAR), 5, '0')) AS CHAR(255)) AS AMENU_SORT    \r\n"
			+ "		, A.MENU_NAME AS MENU_NAME_PATH    \r\n"
			+ "	FROM USERMENU AS A    			\r\n"
			+ "	WHERE A.PARENT_ID = '0'    \r\n"
			+ " 	AND  ((?1 = 0 AND A.VISIBLE = 0) OR (?1 = 1 AND A.VISIBLE IN (0, 1)))        \r\n"
			+ "   	AND  (CASE WHEN  ?2 > 0 THEN A.ID = ?2 ELSE  TRUE END)        "
			+ "	UNION ALL    			    			SELECT    \r\n"
			+ "		  B.ID, B.PARENT_ID, B.MENU_NAME, B.MENU_NAME_EN, B.MENU_NAME_CN, B.MENU_NAME_JP, B.URL, B.MENU_DESC, B.ORD, B.VISIBLE, B.MEMBER_TARGET, B.LINK_TARGET\r\n"
			+ "		, B.TOP, B.SIDE, B.NAVI, B.TAB    \r\n"
			+ "		, C.DEPTH + 1 AS DEPTH    \r\n"
			+ "		, CAST(CONCAT(CONCAT (C.AMENU_SORT, LPAD(CAST(C.DEPTH + 1 AS CHAR), 5, '0')), '', LPAD(CAST(B.ORD AS CHAR), 5, '0')) AS CHAR(255)) AS AMENU_SORT    \r\n"
			+ "		, CONCAT(C.MENU_NAME_PATH, '&gt;', B.MENU_NAME) AS MENU_NAME_PATH    \r\n"
			+ "	FROM    		USERMENU AS B    				, MENU_CTE AS C    \r\n"
			+ "	WHERE B.PARENT_ID = C.ID     )    		    \r\n"
			+ "SELECT 	ID, PARENT_ID, MENU_NAME, MENU_NAME_EN, MENU_NAME_CN, MENU_NAME_JP, URL, MENU_DESC, ORD, VISIBLE, MEMBER_TARGET, LINK_TARGET, TOP,SIDE, NAVI, TAB, DEPTH, MENU_NAME_PATH FROM MENU_CTE WHERE 1=1    \r\n"
			+ "ORDER BY AMENU_SORT ASC" , nativeQuery = true)
	List<ReadUserMenu> getListUserMenu(int visible, int parentId);
	
	@Query(value = "SELECT IFNULL(MAX(ORD) + 1,1) FROM USERMENU B WHERE B.PARENT_ID = ?1", nativeQuery = true)
	int getOrder(int parentId);
	
	@Query( value ="SELECT MAX(ID) AS ID FROM USERMENU", nativeQuery=true)
	int getMaxId();
	
	@Query(value = " SELECT \r\n"
			+ "		ID, PARENT_ID, MENU_NAME, MENU_NAME_EN, MENU_NAME_CN, MENU_NAME_JP, URL, MENU_DESC, ORD, VISIBLE, MEMBER_TARGET, LINK_TARGET, TOP,SIDE, NAVI, TAB  \r\n"
			+ " FROM USERMENU A \r\n" 
			+ "	WHERE A.ID = ?1 ", nativeQuery = true)
	ReadUserMenu getById(int id);
	
	
	@Query( value ="WITH RECURSIVE MENU_CTE AS (\r\n"
			+ "		SELECT \r\n"
			+ "			A.*\r\n"
			+ "           , CAST( A.MENU_NAME AS CHAR(255) ) AS MENU_NAME_PATH\r\n"
			+ "		FROM\r\n"
			+ "			USERMENU AS A\r\n"
			+ "		WHERE A.ID = ?1 \r\n"
			+ "			\r\n"
			+ "		UNION ALL\r\n"
			+ "		\r\n"
			+ "		SELECT\r\n"
			+ "			B.*\r\n"
			+ "            , CONCAT(C.MENU_NAME_PATH, '&gt;', B.MENU_NAME) AS MENU_NAME_PATH  \r\n"
			+ "		FROM\r\n"
			+ "			USERMENU AS B\r\n"
			+ "			, MENU_CTE AS C\r\n"
			+ "		WHERE B.ID = C.PARENT_ID\r\n"
			+ "		\r\n"
			+ "	)\r\n"
			+ "	SELECT \r\n"
			+ "		 MAX( MENU_NAME_PATH ) AS MENU_NAME_PATH\r\n"
			+ "	FROM MENU_CTE" , nativeQuery=true)
	String  getNamePath(int id);

	
	@Modifying
	@Query(value = "DELETE FROM USERMENU WHERE ID IN  (SELECT 	A.ID FROM\r\n"
			+ "		(		SELECT 			D.ID,			D.PARENT_ID \r\n"
			+ "			FROM USERMENU  D 		WHERE D.ID = ?1 			UNION ALL\r\n"
			+ "			SELECT 			 A.ID,			 A.PARENT_ID\r\n"
			+ "			FROM USERMENU A 			INNER JOIN USERMENU D ON A.PARENT_ID = D.ID\r\n"
			+ "			WHERE D.ID = ?1 			)A)", nativeQuery = true)
	void deleteUserMenu(int id);
	
	
	@Modifying
	@Query(value = "UPDATE USERMENU SET ORD = :#{#userMenu.ord}, PARENT_ID = :#{#userMenu.parentId}, MOD_ID = :#{#userMenu.auditSection.modId}, "
			+ "MOD_IP = :#{#userMenu.auditSection.modIp}, " + "MOD_DATE = NOW() "
			+ "WHERE ID = :#{#userMenu.id}", nativeQuery = true)
	void updateChangeOrd(@Param("userMenu") UserMenu userMenu);
}
