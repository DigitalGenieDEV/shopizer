package com.salesmanager.core.business.repositories.manager;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.salesmanager.core.model.manager.CategoryAuth;
import com.salesmanager.core.model.manager.ReadCategoryAuth;

public interface ManagerCategoryAuthRepository extends JpaRepository<CategoryAuth, Integer> {
	
	
	@Query( value ="WITH RECURSIVE MENU_CTE AS (  \r\n"
			+ "	SELECT     \r\n"
			+ "		A.CATEGORY_ID, IFNULL(A.PARENT_ID, 0) AS PARENT_ID , A.LINEAGE, B.NAME,\r\n"
			+ "		B.NAME AS CATEGORY_PATH_NAME,\r\n"
			+ " 	CAST(IFNULL(REPLACE(A.DEPTH,'3', ''),1) AS SIGNED INTEGER) + 1 AS DEPTH,"
			+ "   	A.CODE,"
			+ "		CAST(CONCAT(LPAD(CAST(IFNULL(REPLACE(@LEVEL,'3', ''),1) AS CHAR), 5, '0'), '', LPAD(CAST(A.CATEGORY_ID AS CHAR), 5, '0')) AS CHAR(255)) AS AMENU_SORT\r\n"
			+ "	FROM CATEGORY A, CATEGORY_DESCRIPTION B\r\n"
			+ "	WHERE  A.CATEGORY_ID = B.CATEGORY_ID \r\n"
			+ "		AND A.VISIBLE = 1\r\n"
			+ "		AND B.LANGUAGE_ID = 2\r\n"
			+ "		AND A.MERCHANT_ID = 1\r\n"
			+ "	UNION ALL\r\n"
			+ "	SELECT     \r\n"
			+ "		A.CATEGORY_ID, A.PARENT_ID, A.LINEAGE, B.NAME,\r\n"
			+ "		CONCAT(C.CATEGORY_PATH_NAME, '&GT;', B.NAME) AS CATEGORY_PATH_NAME,\r\n"
			+ "  	CAST(IFNULL(REPLACE(A.DEPTH,'3', ''),1) AS SIGNED INTEGER) + 1 AS DEPTH,"
			+ "     A.CODE,"
			+ "		CAST(CONCAT(CONCAT (C.AMENU_SORT, LPAD(CAST(A.DEPTH + 1 AS CHAR), 5, '0')), '', LPAD(CAST(A.CATEGORY_ID AS CHAR), 5, '0')) AS CHAR(255)) AS AMENU_SORT \r\n"
			+ "	FROM CATEGORY A, CATEGORY_DESCRIPTION B, MENU_CTE C\r\n"
			+ "	WHERE   A.CATEGORY_ID = B.CATEGORY_ID \r\n"
			+ "		AND  A.PARENT_ID = C.CATEGORY_ID \r\n"
			+ "		AND  A.VISIBLE = 1\r\n"
			+ "		AND B.LANGUAGE_ID= 2\r\n"
			+ "		AND A.MERCHANT_ID = 1\r\n"
			+ ")\r\n"
			+ "SELECT  \r\n"
			+ "		CATEGORY_ID AS ID, PARENT_ID AS PARENTID,  LINEAGE AS LINEAGE, NAME AS CATEGORYNAME,  MIN(CATEGORY_PATH_NAME)  AS CATEGORYPATHNAME, CODE, DEPTH FROM MENU_CTE \r\n"
			+ "	GROUP BY  CATEGORY_ID, PARENT_ID, LINEAGE,NAME, DEPTH,CODE", nativeQuery=true)
	List<ReadCategoryAuth> getCategoryList();
	
	
	
	@Query( value ="WITH RECURSIVE MENU_CTE AS (  \r\n"
			+ "				SELECT     \r\n"
			+ "					A.CATEGORY_ID, IFNULL(A.PARENT_ID, 0) AS PARENT_ID , A.LINEAGE, B.NAME,\r\n"
			+ "					B.NAME AS CATEGORY_PATH_NAME,\r\n"
			+ "			 	CAST(IFNULL(REPLACE(A.DEPTH,'3', ''),1) AS SIGNED INTEGER) + 1 AS DEPTH,\r\n"
			+ "					CAST(CONCAT(LPAD(CAST(IFNULL(REPLACE(@LEVEL,'3', ''),1) AS CHAR), 5, '0'), '', LPAD(CAST(A.CATEGORY_ID AS CHAR), 5, '0')) AS CHAR(255)) AS AMENU_SORT\r\n"
			+ "				FROM CATEGORY A, CATEGORY_DESCRIPTION B\r\n"
			+ "				WHERE  A.CATEGORY_ID = B.CATEGORY_ID \r\n"
			+ "					AND A.VISIBLE = 1\r\n"
			+ "					AND B.LANGUAGE_ID = 2\r\n"
			+ "					AND A.MERCHANT_ID = 1\r\n"
			+ "				UNION ALL\r\n"
			+ "				SELECT     \r\n"
			+ "					A.CATEGORY_ID, A.PARENT_ID, A.LINEAGE, B.NAME,\r\n"
			+ "					CONCAT(C.CATEGORY_PATH_NAME, '&gt;', B.NAME) AS CATEGORY_PATH_NAME,\r\n"
			+ "				CAST(IFNULL(REPLACE(A.DEPTH,'3', ''),1) AS SIGNED INTEGER) + 1 AS DEPTH, \r\n"
			+ "					CAST(CONCAT(CONCAT (C.AMENU_SORT, LPAD(CAST(A.DEPTH + 1 AS CHAR), 5, '0')), '', LPAD(CAST(A.CATEGORY_ID AS CHAR), 5, '0')) AS CHAR(255)) AS AMENU_SORT \r\n"
			+ "				FROM CATEGORY A, CATEGORY_DESCRIPTION B, MENU_CTE C\r\n"
			+ "				WHERE   A.CATEGORY_ID = B.CATEGORY_ID \r\n"
			+ "					AND  C.CATEGORY_ID  = A.PARENT_ID \r\n"
			+ "					AND  A.VISIBLE = 1 \r\n"
			+ "					AND B.LANGUAGE_ID= 2 \r\n"
			+ "					AND A.MERCHANT_ID = 1 \r\n"
			+ "			)\r\n"
			+ "		\r\n"
			+ "			SELECT \r\n"
			+ "				A.CATEGORY_ID AS ID, PARENT_ID AS PARENTID,  LINEAGE AS LINEAGE, NAME AS CATEGORYNAME,  MIN(CATEGORY_PATH_NAME)  AS CATEGORYPATHNAME, DEPTH \r\n"
			+ "			 FROM MENU_CTE A, category_auth B WHERE A.CATEGORY_ID = B.CATEGORY_ID\r\n"
			+ "			 AND B.GRP_ID = ?1 \r\n"
			+ "			 GROUP BY  A.CATEGORY_ID, PARENT_ID, LINEAGE,NAME, DEPTH\r\n", nativeQuery=true)
	List<ReadCategoryAuth> getCategoryAuthFullList(int grpId);
	
	
	@Query( value ="SELECT * FROM CATEGORY_AUTH WHERE GRP_ID = ?1 ORDER BY CATEGORY_ID ASC", nativeQuery=true)
	List<CategoryAuth> getCategoryAuthList(int grpId);
	
	
	@Modifying
	@Query(value ="DELETE FROM CATEGORY_AUTH WHERE GRP_ID = ?1", nativeQuery=true)
	void deleteCategoryAuth(int grpId);
}
