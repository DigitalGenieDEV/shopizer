package com.salesmanager.core.business.repositories.storelibrary;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.salesmanager.core.model.merchant.library.StoreLibrary;

public interface StoreLibraryRepository extends JpaRepository<StoreLibrary, Long> {
	
	@Modifying
	@Query(value = "update LIBRARY_FILE set DATE_MODIFIED = now(), UPDT_ID = ?2, DELETE_YN = ?3 where 1 = 1 and LIBRARY_ID = ?1", nativeQuery = true)
	void deleteLibrary(Long id, String modifiedBy, String deleteYn);
	
}
