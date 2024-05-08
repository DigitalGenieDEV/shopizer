package com.salesmanager.core.business.repositories.code;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.salesmanager.core.model.code.Code;
import com.salesmanager.core.model.code.ReadCode;

public interface CodeRepository  extends JpaRepository<Code, Integer> {

	@Query(value = "WITH RECURSIVE MENU_CTE AS ( SELECT     \r\n"
			+ "				ID, PARENT_ID, CODE, CODE_NAME_KR, CODE_NAME_EN, CODE_NAME_CN, CODE_NAME_JP, CODE_DESC, VALUE, ORD, VISIBLE,   \r\n"
			+ "				 CAST(IFNULL(REPLACE(@level,'3', ''),1) AS SIGNED INTEGER) AS DEPTH ,   \r\n"
			+ "				 CAST(CONCAT(LPAD(CAST(IFNULL(REPLACE(@level,'3', ''),1) AS CHAR), 5, '0'), '', LPAD(CAST(A.ORD AS CHAR), 5, '0')) AS CHAR(255)) AS AMENU_SORT,     \r\n"
			+ "				 A.CODE_NAME_KR AS CODE_NAME_PATH    \r\n"
			+ "			FROM COMMON_CODE AS A  WHERE A.PARENT_ID = '0'    \r\n"
			+ "		 AND  ((?1 = 0 AND A.VISIBLE = 0) OR (?1 = 1 AND A.VISIBLE IN (0,1)))        \r\n"
			+ "			UNION ALL   		SELECT    \r\n"
			+ "				  B.ID, B.PARENT_ID, B.CODE, B.CODE_NAME_KR, B.CODE_NAME_EN, B.CODE_NAME_CN, B.CODE_NAME_JP, B.CODE_DESC, B.VALUE, B.ORD, B.VISIBLE,    \r\n"
			+ "				C.DEPTH + 1 AS DEPTH,     \r\n"
			+ "				CAST(CONCAT(CONCAT (C.AMENU_SORT, LPAD(CAST(C.DEPTH + 1 AS CHAR), 5, '0')), '', LPAD(CAST(B.ORD AS CHAR), 5, '0')) AS CHAR(255)) AS AMENU_SORT ,    \r\n"
			+ "				CONCAT(C.CODE_NAME_KR, '&gt;', C.CODE_NAME_PATH) AS CODE_NAME_PATH    \r\n"
			+ "			FROM  COMMON_CODE AS B , MENU_CTE AS C    \r\n"
			+ "			WHERE B.PARENT_ID = C.ID    \r\n" + " )    \r\n" + "		    \r\n"
			+ " SELECT  ID, PARENT_ID, CODE, CODE_NAME_KR, CODE_NAME_EN, CODE_NAME_CN, CODE_NAME_JP, CODE_DESC, VALUE, ORD, VISIBLE, DEPTH, CODE_NAME_PATH FROM MENU_CTE     \r\n"
			+ " ORDER BY AMENU_SORT ASC", nativeQuery = true)
	List<ReadCode> getListCode(int visible);
	
	
	@Query( value ="SELECT CONCAT('CODE_', LPAD(IFNULL(CAST(MAX(REPLACE(CODE, 'CODE_', '')) AS SIGNED INTEGER)+1, 1), 3, '0')) AS CODE FROM COMMON_CODE AS A", nativeQuery=true)
	String getCode();
	

	@Query( value ="SELECT IFNULL(MAX(ORD) + 1,1) FROM COMMON_CODE B WHERE B.PARENT_ID = ?1", nativeQuery=true)
	int getOrder(int parentId);
	
	
	@Query( value ="WITH RECURSIVE CODE_CTE AS (\r\n"
			+ "		SELECT \r\n"
			+ "			A.*\r\n"
			+ "            , CAST( A.CODE_NAME_KR AS CHAR(255) ) AS CODE_NAME_PATH \r\n"
			+ "		FROM COMMON_CODE AS A \r\n"
			+ "		WHERE A.ID = ?1\r\n"
			+ "		UNION ALL\r\n"
			+ "		SELECT\r\n"
			+ "			B.*\r\n"
			+ "            , CONCAT( C.CODE_NAME_PATH , '&gt;' , B.CODE_NAME_KR ) AS CODE_NAME_PATH\r\n"
			+ "		FROM COMMON_CODE AS B, CODE_CTE AS C \r\n"
			+ "		WHERE B.ID = C.PARENT_ID \r\n"
			+ "	)\r\n"
			+ "	SELECT \r\n"
			+ "		 MAX( CODE_NAME_PATH ) AS CODE_NAME_PATH\r\n"
			+ "	FROM CODE_CTE" , nativeQuery=true)
	String  getNamePath(int id);
	
	
	@Query( value ="SELECT MAX(ID) AS ID FROM COMMON_CODE", nativeQuery=true)
	int getMaxId();
	
	@Query( value =" SELECT \r\n"
			+ "	A.ID, A.PARENT_ID, A.CODE, A.CODE_NAME_KR, A.CODE_NAME_EN, A.CODE_NAME_CN, A.CODE_NAME_JP, A.CODE_DESC, A.VALUE, A.ORD, A.VISIBLE  \r\n"
			+ "	FROM COMMON_CODE AS A \r\n"
			+ "	WHERE A.ID = ?1" , nativeQuery=true)
	ReadCode getById(int id);
	
	
	@Modifying
	@Query(value ="DELETE FROM COMMON_CODE WHERE ID IN \r\n"
			+ "	(\r\n"
			+ "		SELECT 	A.ID FROM\r\n"
			+ "		(\r\n"
			+ "			SELECT \r\n"
			+ "				D.ID,\r\n"
			+ "				D.PARENT_ID\r\n"
			+ "			FROM COMMON_CODE  D \r\n"
			+ "			WHERE D.ID = ?1 \r\n"
			+ "			UNION ALL\r\n"
			+ "			SELECT\r\n"
			+ "				 A.ID,\r\n"
			+ "				 A.PARENT_ID\r\n"
			+ "			FROM COMMON_CODE A\r\n"
			+ "			INNER JOIN COMMON_CODE D ON A.PARENT_ID = D.ID\r\n"
			+ "			WHERE D.ID = ?1 \r\n"
			+ "			\r\n"
			+ "		)A\r\n"
			+ "	)", nativeQuery = true)
	void deleteCode(int deptId);
	
	
	@Modifying
	@Query(value ="UPDATE COMMON_CODE SET "
				+ "ORD = :#{#code.ord}, "
				+ "PARENT_ID = :#{#code.parentId}, "
				+ "MOD_ID = :#{#code.mod_id}, "
				+ "MOD_IP = :#{#code.mod_ip}, "
				+ "MOD_DATE = NOW() "
				+ "WHERE ID = :#{#code.id}"
				, nativeQuery = true)
	void updateChangeOrd(@Param("code") Code code);
}
