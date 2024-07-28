package com.salesmanager.core.business.repositories.storelibrary;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.salesmanager.core.model.merchant.library.StoreLibrary;

public interface PageableStoreLibraryRepository extends PagingAndSortingRepository<StoreLibrary, Long> {
	
	@Query(value = "select * "
			+ "from library_file lf  "
			+ "where 1 = 1 "
			+ "and lf.MERCHANT_ID = ?1 "
			+ "and lf.FILE_TYPE like %?2% "
			+ "and (case when ?3 = 'name' then READ_FILE_NAME like %?4% when ?3 = 'ext' then FILE_EXT like %?4% else true end) "
			+ "and lf.DELETE_YN = 'N' "
		 , countQuery = "select count(*) "
		 		+ "from library_file "
		 		+ "where 1 = 1 "
		 		+ "and MERCHANT_ID = ?1 "
		 		+ "and FILE_TYPE like %?2% "
		 		+ "and (case when ?3 = 'name' then READ_FILE_NAME like %?4% when ?3 = 'ext' then FILE_EXT like %?4% else true end) "
		 		+ "and DELETE_YN = 'N' ", nativeQuery = true)
	Page<StoreLibrary> listByStoreId(Integer storeId, String fileType, String keywordType, String keyword, Pageable pageRequest);
	
}
