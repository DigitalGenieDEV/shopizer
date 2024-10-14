package com.salesmanager.core.business.repositories.customer;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.customer.CustomerDTO;

public interface CustomerRepository extends JpaRepository<Customer, Long>, CustomerRepositoryCustom {

	
	@Query("select c from Customer c join fetch c.merchantStore cm left join fetch c.defaultLanguage cl left join fetch c.attributes ca left join fetch ca.customerOption cao left join fetch ca.customerOptionValue cav left join fetch cao.descriptions caod left join fetch cav.descriptions left join fetch c.groups where c.id = ?1")
	Customer findOne(Long id);
	
	@Query("select distinct c from Customer c join fetch c.merchantStore cm left join fetch c.defaultLanguage cl left join fetch c.attributes ca left join fetch ca.customerOption cao left join fetch ca.customerOptionValue cav left join fetch cao.descriptions caod left join fetch cav.descriptions left join fetch c.groups  where c.billing.firstName = ?1")
	List<Customer> findByName(String name);
	
	@Query("select c from Customer c join fetch c.merchantStore cm left join fetch c.defaultLanguage cl left join fetch c.attributes ca left join fetch ca.customerOption cao left join fetch ca.customerOptionValue cav left join fetch cao.descriptions caod left join fetch cav.descriptions left join fetch c.groups  where c.nick = ?1")
	Customer findByNick(String nick);
	
	@Query("select c from Customer c "
			+ "join fetch c.merchantStore cm "
			+ "left join fetch c.defaultLanguage cl "
			+ "left join fetch c.attributes ca "
			+ "left join fetch ca.customerOption cao "
			+ "left join fetch ca.customerOptionValue cav "
			+ "left join fetch cao.descriptions caod "
			+ "left join fetch cav.descriptions  "
			+ "left join fetch c.groups  "
			+ "left join fetch c.delivery cd "
			+ "left join fetch c.billing cb "
			+ "left join fetch cd.country "
			+ "left join fetch cd.zone "
			+ "left join fetch cb.country "
			+ "left join fetch cb.zone "
			+ "where c.nick = ?1 and cm.id = ?2")
	Customer findByNick(String nick, int storeId);
	
	@Query("select c from Customer c "
			+ "join fetch c.merchantStore cm "
			+ "left join fetch c.defaultLanguage cl "
			+ "left join fetch c.attributes ca "
			+ "left join fetch ca.customerOption cao "
			+ "left join fetch ca.customerOptionValue cav "
			+ "left join fetch cao.descriptions caod "
			+ "left join fetch cav.descriptions  "
			+ "left join fetch c.groups  "
			+ "left join fetch c.delivery cd "
			+ "left join fetch c.billing cb "
			+ "left join fetch cd.country "
			+ "left join fetch cd.zone "
			+ "left join fetch cb.country "
			+ "left join fetch cb.zone "
			+ "where c.nick = ?1 and cm.code = ?2")
	Customer findByNick(String nick, String store);
	
	@Query("select c from Customer c "
			+ "join fetch c.merchantStore cm "
			+ "left join fetch c.defaultLanguage cl "
			+ "left join fetch c.attributes ca "
			+ "left join fetch ca.customerOption cao "
			+ "left join fetch ca.customerOptionValue cav "
			+ "left join fetch cao.descriptions caod "
			+ "left join fetch cav.descriptions  "
			+ "left join fetch c.groups  "
			+ "left join fetch c.delivery cd "
			+ "left join fetch c.billing cb "
			+ "left join fetch cd.country "
			+ "left join fetch cd.zone "
			+ "left join fetch cb.country "
			+ "left join fetch cb.zone "
			+ "where c.credentialsResetRequest.credentialsRequest = ?1 and cm.code = ?2")
	Customer findByResetPasswordToken(String token, String store);
	
	@Query("select distinct c from Customer c join fetch c.merchantStore cm left join fetch c.defaultLanguage cl left join fetch c.attributes ca left join fetch ca.customerOption cao left join fetch ca.customerOptionValue cav left join fetch cao.descriptions caod left join fetch cav.descriptions left join fetch c.groups  where cm.id = ?1")
	List<Customer> findByStore(int storeId);

	@Query("select distinct c from Customer c join fetch c.merchantStore cm left join fetch c.defaultLanguage cl left join fetch c.attributes ca left join fetch ca.customerOption cao left join fetch ca.customerOptionValue cav left join fetch cao.descriptions caod left join fetch cav.descriptions left join fetch c.groups  where cm.code = ?1")
	List<Customer> findByStoreCode(String storeCode);
	
	@Query(value = "WITH PURCHASE AS ( \r\n"
			+ "	SELECT CUSTOMER_ID \r\n"
			+ "	     , COUNT(*) AS PURCHASE_COUNT \r\n"
			+ "	     , SUM(PURCHASE_PRICE) AS PURCHASE_PRICE \r\n"
			+ "	FROM ( \r\n"
			+ "	       SELECT o.CUSTOMER_ID \r\n"
			+ "	            , op.PRODUCT_QUANTITY * opp.PRODUCT_PRICE AS PURCHASE_PRICE \r\n"
			+ "	       FROM ORDERS o LEFT JOIN ORDER_PRODUCT op ON o.ORDER_ID = op.ORDER_ID \r\n"
			+ "	            LEFT JOIN ORDER_PRODUCT_PRICE opp ON op.ORDER_PRODUCT_ID = opp.ORDER_PRODUCT_ID \r\n"
			+ "	       WHERE 1 = 1 \r\n"
			+ "	             AND o.ORDER_STATUS = 'DELIVERY_COMPLETED' \r\n"
			+ "	     ) A \r\n"
			+ "	GROUP BY CUSTOMER_ID \r\n"
			+ ") \r\n"
			+ "SELECT c.CUSTOMER_ID \r\n"
			+ "     , c.CUSTOMER_EMAIL_ADDRESS \r\n"
			+ "     , c.BILLING_COMPANY AS COMPANY \r\n"
			+ "     , c2.COUNTRY_ISOCODE AS COUNTRY\r\n"
			+ "     , 0 AS GRADE \r\n"
			+ "     , 0 AS TOTAL_GQ_COUNT \r\n"
			+ "     , 0 AS SUBMIT_GQ_COUNT \r\n"
			+ "     , IFNULL(p.PURCHASE_COUNT, 0) AS PURCHASE_COUNT \r\n"
			+ "     , IFNULL(p.PURCHASE_PRICE, 0) AS PURCHASE_PRICE\r\n"
			+ "     , 0 AS AVG_PURCHASE \r\n"
			+ "     , c.DATE_MODIFIED AS JOIN_DATE \r\n"
			+ "FROM CUSTOMER c LEFT JOIN PURCHASE p ON c.CUSTOMER_ID = p.CUSTOMER_ID \r\n"
			+ "     LEFT JOIN COUNTRY c2 ON c.DELIVERY_COUNTRY_ID = c2.COUNTRY_ID \r\n"
			+ "WHERE 1 = 1 \r\n"
			+ "      AND CASE WHEN 'ID' = ?1 THEN c.CUSTOMER_EMAIL_ADDRESS = ?2 ELSE TRUE END \r\n"
			+ "      AND CASE WHEN 'COMPANY_NAME' = ?1 THEN c.BILLING_COMPANY = ?2 ELSE TRUE END \r\n"
			+ "      AND CASE WHEN 'BUSINESS_TYPE' = ?1 THEN '비지니스 유형컬럼' = ?2 ELSE TRUE END \r\n"
			+ "      AND CASE WHEN 'COUNTRY' = ?1 THEN c2.COUNTRY_ISOCODE = ?2 ELSE TRUE END \r\n"
			+ "      AND CASE WHEN ?3 != '' THEN DATE_FORMAT(c.DATE_MODIFIED, '%Y-%m-%d') >= ?3 ELSE TRUE END \r\n"
			+ "      AND CASE WHEN ?4 != '' THEN DATE_FORMAT(c.DATE_MODIFIED, '%Y-%m-%d') <= ?4 ELSE TRUE END \r\n"
		 , countQuery = "SELECT COUNT(*) \r\n"
		 		+ "FROM CUSTOMER c LEFT JOIN COUNTRY c2 ON c.DELIVERY_COUNTRY_ID = c2.COUNTRY_ID \r\n"
		 		+ "WHERE 1 = 1 \r\n"
		 		+ "      AND CASE WHEN 'ID' = ?1 THEN c.CUSTOMER_EMAIL_ADDRESS = ?2 ELSE TRUE END \r\n"
		 		+ "      AND CASE WHEN 'COMPANY_NAME' = ?1 THEN c.BILLING_COMPANY = ?2 ELSE TRUE END \r\n"
		 		+ "      AND CASE WHEN 'BUSINESS_TYPE' = ?1 THEN '비지니스 유형컬럼' = ?2 ELSE TRUE END \r\n"
		 		+ "      AND CASE WHEN 'COUNTRY' = ?1 THEN c2.COUNTRY_ISOCODE = ?2 ELSE TRUE END \r\n"
		 		+ "      AND CASE WHEN ?3 != '' THEN DATE_FORMAT(c.DATE_MODIFIED, '%Y-%m-%d') >= ?3 ELSE TRUE END \r\n"
		 		+ "      AND CASE WHEN ?4 != '' THEN DATE_FORMAT(c.DATE_MODIFIED, '%Y-%m-%d') <= ?4 ELSE TRUE END \r\n", nativeQuery = true)
	Page<CustomerDTO> list(String queryType, String queryValue, String startDate, String endDate, Pageable pageRequest);
	

}
