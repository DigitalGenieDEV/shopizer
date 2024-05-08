package com.salesmanager.core.business.repositories.dept;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.salesmanager.core.model.dept.Dept;
import com.salesmanager.core.model.dept.ReadDept;

public interface DeptRepository extends JpaRepository<Dept, Integer> {
	
	@Query( value ="WITH RECURSIVE DEPT_CTE AS (     \r\n"
			+ "			SELECT      \r\n"
			+ "				  ID, PARENT_ID, DEPT_CODE, DEPT_NAME, TEL, CONTENT, ORD, VISIBLE \r\n"
			+ "				, CAST(IFNULL(REPLACE(@level,'3', ''),1) AS SIGNED INTEGER) AS DEPTH     \r\n"
			+ "				, CAST(CONCAT(LPAD(CAST(IFNULL(REPLACE(@level,'3', ''),1) AS CHAR), 5, '0'), '', LPAD(CAST(A.ORD AS CHAR), 5, '0')) AS CHAR(255)) AS AMENU_SORT     \r\n"
			+ "				, A.DEPT_NAME AS DEPT_NAME_PATH     \r\n"
			+ "			FROM DEPT AS A     \r\n"
			+ "			WHERE A.PARENT_ID = '0'     \r\n"
			+ "		 AND  ((?1 = 0 AND A.VISIBLE = 0) OR (?1 = 1 AND A.VISIBLE IN (0, 1)))         \r\n"
			+ "			UNION ALL     \r\n"
			+ "			SELECT     \r\n"
			+ "				  B.ID, B.PARENT_ID, B.DEPT_CODE, B.DEPT_NAME, B.TEL, B.CONTENT, B.ORD, B.VISIBLE \r\n"
			+ "				, C.DEPTH + 1 AS DEPTH     \r\n"
			+ "				, CAST(CONCAT(CONCAT (C.AMENU_SORT, LPAD(CAST(C.DEPTH + 1 AS CHAR), 5, '0')), '', LPAD(CAST(B.ORD AS CHAR), 5, '0')) AS CHAR(255)) AS AMENU_SORT     \r\n"
			+ "				, CONCAT(C.DEPT_NAME_PATH, '&gt;', B.DEPT_NAME) AS DEPT_NAME_PATH     \r\n"
			+ "			FROM     \r\n"
			+ "				DEPT AS B     \r\n"
			+ "				, DEPT_CTE AS C     \r\n"
			+ "			WHERE B.PARENT_ID = C.ID     \r\n"
			+ " )     \r\n"
			+ "			 \r\n"
			+ " SELECT  ID, PARENT_ID, DEPT_CODE, DEPT_NAME, TEL, CONTENT, ORD, VISIBLE, DEPTH, DEPT_NAME_PATH FROM DEPT_CTE    \r\n"
			+ " ORDER BY  AMENU_SORT ASC", nativeQuery=true)
	public List<ReadDept> getListDept(int visible);

	@Query( value ="SELECT CONCAT('DEPT_', LPAD(IFNULL(CAST(MAX(REPLACE(DEPT_CODE, 'DEPT_', '')) AS SIGNED INTEGER)+1, 1), 3, '0')) AS CODE FROM DEPT AS A", nativeQuery=true)
	public String getDeptCode();
	
	@Query( value ="SELECT IFNULL(MAX(ORD) + 1,1) FROM DEPT B WHERE B.PARENT_ID = ?1", nativeQuery=true)
	public int getOrder(int parentId);
	
	@Query( value ="WITH RECURSIVE DEPT_CTE AS (\r\n"
			+ "		SELECT \r\n"
			+ "			A.*\r\n"
			+ "            , CAST( A.DEPT_NAME AS CHAR(255) ) AS DEPT_NAME_PATH\r\n"
			+ "		FROM\r\n"
			+ "			DEPT AS A\r\n"
			+ "		WHERE A.ID = ?1\r\n"
			+ "			\r\n"
			+ "		UNION ALL\r\n"
			+ "		\r\n"
			+ "		SELECT\r\n"
			+ "			B.*\r\n"
			+ "            , CONCAT( C.DEPT_NAME_PATH , '&gt;' , B.DEPT_NAME ) AS DEPT_NAME_PATH\r\n"
			+ "		FROM\r\n"
			+ "			DEPT AS B\r\n"
			+ "			, DEPT_CTE AS C\r\n"
			+ "		WHERE B.ID = C.PARENT_ID\r\n"
			+ "		\r\n"
			+ "	)\r\n"
			+ "	SELECT \r\n"
			+ "		 MAX( DEPT_NAME_PATH ) AS DEPT_NAME_PATH\r\n"
			+ "	FROM DEPT_CTE" , nativeQuery=true)
	String  getNamePath(int id);
	
	@Query( value =" SELECT \r\n"
			+ "		A.ID, A.PARENT_ID, A.DEPT_CODE, A.DEPT_NAME, A.TEL, A.CONTENT, A.ORD,  A.VISIBLE \r\n"
			+ "	FROM DEPT AS A \r\n"
			+ "	WHERE A.ID = ?1" , nativeQuery=true)
	ReadDept getById(int id);
	
	@Modifying
	@Query(value ="DELETE FROM DEPT WHERE ID IN \r\n"
			+ "	(\r\n"
			+ "		SELECT 	A.ID FROM\r\n"
			+ "		(\r\n"
			+ "			SELECT \r\n"
			+ "				D.ID,\r\n"
			+ "				D.PARENT_ID\r\n"
			+ "			FROM DEPT  D \r\n"
			+ "			WHERE D.ID = ?1 \r\n"
			+ "			UNION ALL\r\n"
			+ "			SELECT\r\n"
			+ "				 A.ID,\r\n"
			+ "				 A.PARENT_ID\r\n"
			+ "			FROM DEPT A\r\n"
			+ "			INNER JOIN DEPT D ON A.PARENT_ID = D.ID\r\n"
			+ "			WHERE D.ID = ?1 \r\n"
			+ "			\r\n"
			+ "		)A\r\n"
			+ "	)", nativeQuery = true)
	public void deleteDept(int deptId);
	
	
	@Modifying
	@Query(value ="UPDATE DEPT SET "
				+ "ORD = :#{#dept.ord}, "
				+ "PARENT_ID = :#{#dept.parentId}, "
				+ "MOD_ID = :#{#dept.mod_id}, "
				+ "MOD_IP = :#{#dept.mod_ip}, "
				+ "MOD_DATE = NOW() "
				+ "WHERE ID = :#{#dept.id}"
				, nativeQuery = true)
	void updateChangeOrd(@Param("dept") Dept dept);


}
