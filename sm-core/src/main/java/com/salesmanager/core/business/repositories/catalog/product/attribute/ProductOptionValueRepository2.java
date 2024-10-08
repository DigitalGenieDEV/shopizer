package com.salesmanager.core.business.repositories.catalog.product.attribute;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.salesmanager.core.model.catalog.product.attribute.ProductOption;
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
	
	
	@Modifying
	@Query(value ="DELETE FROM PRODUCT_OPT_SET_OPT_VALUE WHERE ProductOptionSet_PRODUCT_OPTION_SET_ID = ?1 AND values_PRODUCT_OPTION_VALUE_ID = ?2"
				, nativeQuery = true)
	public void deleteProductOptSet0ptValue(int setId, int valueId);
	
	@Modifying
	@Query(value ="DELETE FROM PRODUCT_OPTION_VALUE_DESCRIPTION WHERE PRODUCT_OPTION_VALUE_ID = ?1"
				, nativeQuery = true)
	public void deleteProductOptionValueDescription(int valueId);
	
	@Modifying
	@Query(value ="DELETE FROM PRODUCT_OPTION_VALUE WHERE PRODUCT_OPTION_VALUE_ID = ?1"
				, nativeQuery = true)
	public void deleteProductOptionValue(int valueId);
	
	
	@Query( value ="SELECT  values_PRODUCT_OPTION_VALUE_ID \r\n"
			+ " FROM PRODUCT_OPT_SET_OPT_VALUE A"
			+ " WHERE  A.ProductOptionSet_PRODUCT_OPTION_SET_ID = ?1 ", nativeQuery=true)
	public List<Integer> getOptSet0ptValueList(int setId);
	
	@Modifying
	@Query(value ="DELETE FROM PRODUCT_OPTION_DESC WHERE PRODUCT_OPTION_ID = ?1"
				, nativeQuery = true)
	public void deleteProductOptionDescription(int optionId);
	
	@Modifying
	@Query(value ="DELETE FROM PRODUCT_OPTION WHERE PRODUCT_OPTION_ID = ?1"
				, nativeQuery = true)
	public void deleteProductOption(int optionId);
	
	@Modifying
	@Query(value ="DELETE FROM PRODUCT_OPTION_SET WHERE PRODUCT_OPTION_SET_ID = ?1 AND PRODUCT_OPTION_ID = ?2"
				, nativeQuery = true)
	public void deleteProductOptSet(int setId, int optionId);

	
	@Query( value ="SELECT  \r\n"
			+ "	A.PRODUCT_OPTION_SET_ID AS ID, A.PRODUCT_OPTION_ID AS OPTIONID, CATEGORY_ID AS CATEGORYID , C.DESCRIPTION, A.PRODUCT_OPTION_SET_CODE AS CODE, C.DESCRIPTION_ID AS DESCID  \r\n"
			+ "FROM PRODUCT_OPTION_SET A INNER JOIN PRODUCT_OPTION B \r\n"
			+ "ON A.PRODUCT_OPTION_ID = B.PRODUCT_OPTION_ID \r\n"
			+ "INNER JOIN PRODUCT_OPTION_DESC C\r\n"
			+ "ON A.PRODUCT_OPTION_ID = C.PRODUCT_OPTION_ID\r\n"
			+ "WHERE A.OPTION_SET_FOR_SALE_TYPE = ?4 \r\n"
			+ "AND A.CATEGORY_ID = ?3 \r\n"
			+ "AND C.LANGUAGE_ID = ?2 \r\n"
			+ "AND ( B.MERCHANT_ID = ?1 OR  B.MERCHANT_ID = 1) \r\n"
			+ "ORDER BY B.PRODUCT_OPTION_ID ASC\r\n"
			+ " ", nativeQuery=true)
	public List<ReadProductOption2> getProductListOption(int code, int lagnageId, int categoryId, String division);
	
	@Query( value ="SELECT  \r\n"
			+ "	A.PRODUCT_OPTION_SET_ID AS ID, A.PRODUCT_OPTION_ID AS OPTIONID, C.PRODUCT_OPTION_VALUE_ID AS VALUEID, C.PRODUCT_OPTION_VAL_CODE AS CODE, D.DESCRIPTION_ID AS DESCID, D.DESCRIPTION  \r\n"
			+ "FROM PRODUCT_OPTION_SET A INNER JOIN PRODUCT_OPT_SET_OPT_VALUE B \r\n"
			+ "ON A.PRODUCT_OPTION_SET_ID = B.ProductOptionSet_PRODUCT_OPTION_SET_ID \r\n"
			+ "INNER JOIN PRODUCT_OPTION_VALUE C\r\n"
			+ "ON B.values_PRODUCT_OPTION_VALUE_ID = C.PRODUCT_OPTION_VALUE_ID\r\n"
			+ "INNER JOIN PRODUCT_OPTION_VALUE_DESCRIPTION D ON \r\n"
			+ "C.PRODUCT_OPTION_VALUE_ID = D.PRODUCT_OPTION_VALUE_ID\r\n"
			+ "WHERE A.OPTION_SET_FOR_SALE_TYPE = ?4 \r\n"
			+ "AND A.CATEGORY_ID = ?3 \r\n"
			+ "AND D.LANGUAGE_ID = ?2 \r\n"
			+ "AND ( C.MERCHANT_ID = ?1 OR  C.MERCHANT_ID = 1) \r\n"
			+ "ORDER BY A.PRODUCT_OPTION_ID ASC \r\n"
			+ " ", nativeQuery=true)
	public List<ReadProductOptionValue2> getProductListOptionValue(int code, int lagnageId, int categoryId, String division);

}
