package com.salesmanager.core.business.repositories.catalog.product.attribute;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.salesmanager.core.model.catalog.product.attribute.ProductOption;
import com.salesmanager.core.model.catalog.product.attribute.ReadProductOption;
import com.salesmanager.core.model.catalog.product.attribute.ReadProductOption2;
import com.salesmanager.core.model.catalog.product.attribute.ReadProductOptionValue;
import com.salesmanager.core.model.catalog.product.attribute.ReadProductOptionValue2;

public interface ProductOptionValueRepository2 extends JpaRepository<ProductOption, Long> {
	
	
	@Query( value ="SELECT \r\n"
			+ "	A.PRODUCT_OPTION_VALUE_ID AS ID, \r\n"
			+ "	A.PRODUCT_OPTION_VAL_CODE AS CODE, \r\n"
			+ "	A.PRODUCT_OPT_VAL_IMAGE AS IMAGE,\r\n"
			+ "	B.NAME AS VALUENAME,  \r\n"
			+ " D.OPTION_SET_FOR_SALE_TYPE AS TYPE \r\n"
			+ " FROM PRODUCT_OPTION_VALUE A INNER JOIN PRODUCT_OPTION_VALUE_DESCRIPTION B \r\n"
			+ " ON A.PRODUCT_OPTION_VALUE_ID = B.PRODUCT_OPTION_VALUE_ID \r\n"
			+ " INNER JOIN PRODUCT_OPT_SET_OPT_VALUE C ON A.PRODUCT_OPTION_VALUE_ID = C.VALUES_PRODUCT_OPTION_VALUE_ID\r\n"
			+ " AND C.PRODUCTOPTIONSET_PRODUCT_OPTION_SET_ID = ?1 \r\n"
			+ " INNER JOIN PRODUCT_OPTION_SET D ON C.PRODUCTOPTIONSET_PRODUCT_OPTION_SET_ID = D.PRODUCT_OPTION_SET_ID \r\n"
			+ " AND D.CATEGORY_ID = ?2 "
			+ " AND B.LANGUAGE_ID= ?3 \r\n", nativeQuery=true)
	public List<ReadProductOptionValue> getListOptionValues(int setId, int categoryId, Integer laguaageId);
	
	@Query(value = "SELECT CONCAT('CODE00000_' , IFNULL(MAX(PRODUCT_OPTION_ID) + 1,1)) FROM PRODUCT_OPTION A" , nativeQuery=true)
	public String getOptionCode();
	
	@Query(value = "SELECT CONCAT('CODE00000_1_' , IFNULL(MAX(PRODUCT_OPTION_VALUE_ID) + 1,1)) FROM PRODUCT_OPTION_VALUE A" , nativeQuery=true)
	public String getOptionValueCode();
	
	@Modifying
	@Query(value ="DELETE FROM PRODUCT_OPT_SET_OPT_VALUE WHERE ProductOptionSet_PRODUCT_OPTION_SET_ID = ?1 AND values_PRODUCT_OPTION_VALUE_ID = ?2"
				, nativeQuery = true)
	public void deleteProductOptSet0ptValue(Long setId, Long valueId);
	
	@Modifying
	@Query(value ="DELETE FROM PRODUCT_OPTION_VALUE_DESCRIPTION WHERE PRODUCT_OPTION_VALUE_ID = ?1"
				, nativeQuery = true)
	public void deleteProductOptionValueDescription(Long valueId);
	
	@Modifying
	@Query(value ="DELETE FROM PRODUCT_OPTION_VALUE WHERE PRODUCT_OPTION_VALUE_ID = ?1"
				, nativeQuery = true)
	public void deleteProductOptionValue(Long valueId);
	
	
	@Query( value ="SELECT  values_PRODUCT_OPTION_VALUE_ID \r\n"
			+ " FROM PRODUCT_OPT_SET_OPT_VALUE A"
			+ " WHERE  A.ProductOptionSet_PRODUCT_OPTION_SET_ID = ?1 ", nativeQuery=true)
	public List<Long> getOptSetOptValueList(Long setId);
	
	@Modifying
	@Query(value ="DELETE FROM PRODUCT_OPTION_DESC WHERE PRODUCT_OPTION_ID = ?1"
				, nativeQuery = true)
	public void deleteProductOptionDescription(Long optionId);
	
	@Modifying
	@Query(value ="DELETE FROM PRODUCT_OPTION WHERE PRODUCT_OPTION_ID = ?1"
				, nativeQuery = true)
	public void deleteProductOption(Long optionId);
	
	@Modifying
	@Query(value ="DELETE FROM PRODUCT_OPTION_SET WHERE PRODUCT_OPTION_SET_ID = ?1 AND PRODUCT_OPTION_ID = ?2"
				, nativeQuery = true)
	public void deleteProductOptSet(Long setId, Long optionId);

	
	@Query( value ="SELECT  \r\n"
			+ "	A.PRODUCT_OPTION_SET_ID AS ID, A.PRODUCT_OPTION_ID AS OPTIONID, CATEGORY_ID AS CATEGORYID , C.DESCRIPTION, A.PRODUCT_OPTION_SET_CODE AS CODE, C.DESCRIPTION_ID AS DESCID  \r\n"
			+ "FROM PRODUCT_OPTION_SET A INNER JOIN PRODUCT_OPTION B \r\n"
			+ "ON A.PRODUCT_OPTION_ID = B.PRODUCT_OPTION_ID \r\n"
			+ "INNER JOIN PRODUCT_OPTION_DESC C\r\n"
			+ "ON A.PRODUCT_OPTION_ID = C.PRODUCT_OPTION_ID\r\n"
			+ "WHERE A.OPTION_SET_FOR_SALE_TYPE = ?3 \r\n"
			+ "AND A.CATEGORY_ID = ?2 \r\n"
			+ "AND C.LANGUAGE_ID = ?1 \r\n"
			+ "AND  A.MERCHANT_ID = 1 \r\n"
			+ "ORDER BY B.PRODUCT_OPTION_ID ASC\r\n"
			+ " ", nativeQuery=true)
	public List<ReadProductOption2> getProductListOption(int lagnageId, int categoryId, String division);
	
	@Query( value ="SELECT  \r\n"
			+ "	A.PRODUCT_OPTION_SET_ID AS ID, A.PRODUCT_OPTION_ID AS OPTIONID, C.PRODUCT_OPTION_VALUE_ID AS VALUEID, C.PRODUCT_OPTION_VAL_CODE AS CODE, D.DESCRIPTION_ID AS DESCID, D.DESCRIPTION  \r\n"
			+ "FROM PRODUCT_OPTION_SET A INNER JOIN PRODUCT_OPT_SET_OPT_VALUE B \r\n"
			+ "ON A.PRODUCT_OPTION_SET_ID = B.ProductOptionSet_PRODUCT_OPTION_SET_ID \r\n"
			+ "INNER JOIN PRODUCT_OPTION_VALUE C\r\n"
			+ "ON B.values_PRODUCT_OPTION_VALUE_ID = C.PRODUCT_OPTION_VALUE_ID\r\n"
			+ "INNER JOIN PRODUCT_OPTION_VALUE_DESCRIPTION D ON \r\n"
			+ "C.PRODUCT_OPTION_VALUE_ID = D.PRODUCT_OPTION_VALUE_ID\r\n"
			+ "WHERE A.OPTION_SET_FOR_SALE_TYPE = ?3 \r\n"
			+ "AND A.CATEGORY_ID = ?2 \r\n"
			+ "AND D.LANGUAGE_ID = ?1 \r\n"
			+ "AND  A.MERCHANT_ID = 1 \r\n"
			+ "ORDER BY A.PRODUCT_OPTION_ID ASC \r\n"
			+ " ", nativeQuery=true)
	public List<ReadProductOptionValue2> getProductListOptionValue(int lagnageId, int categoryId, String division);
	
	@Query( value ="SELECT COUNT(*) AS CNT FROM PRODUCT_OPTION_SET WHERE PRODUCT_OPTION_ID = ?1  ", nativeQuery=true)
	public int getOptionSet(Long optionId);
	
	@Query( value ="SELECT COUNT(*) AS CNT FROM PRODUCT_OPT_SET_OPT_VALUE WHERE values_PRODUCT_OPTION_VALUE_ID = ?1  ", nativeQuery=true)
	public int getOptionSetValue(Long valueId);

	
	@Query( value ="SELECT COUNT(*) AS CNT FROM PRODUCT_OPTION_DESC WHERE NAME = ?1  ", nativeQuery=true)
	public int getOptionNameCount(String name);
	
	@Query( value ="SELECT COUNT(*) AS CNT FROM PRODUCT_OPTION_VALUE_DESCRIPTION WHERE NAME = ?1  ", nativeQuery=true)
	public int getOptionValueNameCount(String name);

	
	@Query( value ="SELECT A.PRODUCT_OPTION_ID AS ID, B.NAME, A.PRODUCT_OPTION_CODE AS CODE,  A.PRODUCT_OPTION_TYPE AS TYPE "
			+ "FROM PRODUCT_OPTION A INNER JOIN  PRODUCT_OPTION_DESC B  ON A.PRODUCT_OPTION_ID = B.PRODUCT_OPTION_ID " 
			+ " WHERE 1=1 "
			+ " AND  A.PRODUCT_OPTION_ID NOT IN ( " 
			+ " SELECT  A.PRODUCT_OPTION_ID FROM PRODUCT_OPTION A INNER JOIN PRODUCT_OPTION_DESC B  ON A.PRODUCT_OPTION_ID = B.PRODUCT_OPTION_ID "
			+ " INNER JOIN PRODUCT_OPTION_SET C ON A.PRODUCT_OPTION_ID = C.PRODUCT_OPTION_ID "
			+ " AND C.CATEGORY_ID = ?3 "
			+ " AND B.LANGUAGE_ID = ?1 ) "
			+ " AND B.NAME LIKE %?2% "
			+ " AND A.PRODUCT_OPTION_CODE LIKE 'CODE00000_%'" , nativeQuery=true)
	public List<ReadProductOption> getListOptionKeyword(int lagnageId, String keyword, int categoryId);
	
	@Query( value ="SELECT A.PRODUCT_OPTION_VALUE_ID AS ID, B.NAME AS VALUENAME, A.PRODUCT_OPTION_VAL_CODE AS CODE,  A.PRODUCT_OPT_VAL_IMAGE AS IMAGE, '' AS TYPE "
			+ "FROM PRODUCT_OPTION_VALUE A INNER JOIN  PRODUCT_OPTION_VALUE_DESCRIPTION B  ON A.PRODUCT_OPTION_VALUE_ID = B.PRODUCT_OPTION_VALUE_ID " 
			+ " WHERE 1=1 "
			+ " AND  A.PRODUCT_OPTION_VALUE_ID NOT IN ( " 
			+ " SELECT   A.PRODUCT_OPTION_VALUE_ID FROM PRODUCT_OPTION_VALUE A INNER JOIN PRODUCT_OPTION_VALUE_DESCRIPTION B  ON A.PRODUCT_OPTION_VALUE_ID = B.PRODUCT_OPTION_VALUE_ID "
			+ " INNER JOIN PRODUCT_OPT_SET_OPT_VALUE C ON  A.PRODUCT_OPTION_VALUE_ID = C.VALUES_PRODUCT_OPTION_VALUE_ID INNER JOIN PRODUCT_OPTION_SET D ON C.PRODUCTOPTIONSET_PRODUCT_OPTION_SET_ID = D.PRODUCT_OPTION_SET_ID "
			+ " AND D.CATEGORY_ID = ?3 "
			+ " AND B.LANGUAGE_ID = ?1 "
			+ " AND C.PRODUCTOPTIONSET_PRODUCT_OPTION_SET_ID = ?2) "
			+ " AND B.NAME LIKE %?4% "
			+ " AND A.PRODUCT_OPTION_VAL_CODE LIKE 'CODE00000_1_%'" , nativeQuery=true)
	
	public List<ReadProductOptionValue> getListOptionKeywordValues(int lagnageId, int setId, int categoryId, String keyword);
	
	@Modifying
	@Query(value = "INSERT INTO PRODUCT_OPT_SET_OPT_VALUE(PRODUCTOPTIONSET_PRODUCT_OPTION_SET_ID, VALUES_PRODUCT_OPTION_VALUE_ID) VALUES (?1, ?2)", nativeQuery = true)
	void insertOptionSetValue(Long setID, Long valueId);
	
	@Modifying
	@Query(value = "DELETE FROM PRODUCT_ATTRIBUTE WHERE PRODUCT_ID = ?1", nativeQuery = true)
	void deleteProductAttribute(Long productId);


}
