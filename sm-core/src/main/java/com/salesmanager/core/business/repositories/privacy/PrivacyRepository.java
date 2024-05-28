package com.salesmanager.core.business.repositories.privacy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.salesmanager.core.model.privacy.Privacy;
import com.salesmanager.core.model.privacy.ReadPrivacy;

public interface PrivacyRepository extends JpaRepository<Privacy, Integer> {

	@Query( value ="SELECT MAX(ID) AS ID, MAX(TITLE) AS TITLE, MAX(CONTENT) AS CONTENT FROM PRIVACY WHERE VISIBLE=0 AND DIVISION = ?1  AND (CASE WHEN  ?2 > 0 THEN ID = ?2 ELSE TRUE END )ORDER BY ID DESC", nativeQuery=true)
	ReadPrivacy getUserPrivacy(String division, int id);
	
	@Query( value ="SELECT * FROM PRIVACY WHERE VISIBLE=0 AND DIVISION = ?1 ORDER BY ID DESC", nativeQuery=true)
	List<Privacy> getListUserPrivacy(String division);
	
}
