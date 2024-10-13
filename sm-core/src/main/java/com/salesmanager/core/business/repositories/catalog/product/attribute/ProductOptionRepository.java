package com.salesmanager.core.business.repositories.catalog.product.attribute;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.salesmanager.core.model.catalog.product.attribute.ProductOption;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {

	@Query("select p from ProductOption p join fetch p.merchantStore pm left join fetch p.descriptions pd where p.id = ?1")
	ProductOption findOne(Long id);
	
	@Query("select p from ProductOption p join fetch p.merchantStore pm left join fetch p.descriptions pd where p.id = ?2 and (pm.id = ?1 or pm.code = 'DEFAULT')")
	ProductOption findOne(Integer storeId, Long id);
	
	@Query("select distinct p from ProductOption p join fetch p.merchantStore pm left join fetch p.descriptions pd where (pm.id = ?1 or pm.code = 'DEFAULT') and pd.language.id = ?2")
	List<ProductOption> findByStoreId(Integer storeId, Integer languageId);

	//tmpzk2
	@Query("select p from ProductOption p left join fetch p.descriptions pd where p.id in (?1) and pd.language.id = ?2")
	List<ProductOption> findByIds(List<Long>optionIds, Integer languageId);
	
	@Query("select p from ProductOption p join fetch p.merchantStore pm left join fetch p.descriptions pd where (pm.id = ?1 or pm.code = 'DEFAULT') and pd.name like %?2% and pd.language.id = ?3")
	List<ProductOption> findByName(Integer storeId, String name, Integer languageId);
	
	@Query("select p from ProductOption p join fetch p.merchantStore pm left join fetch p.descriptions pd where (pm.id = ?1 or pm.code = 'DEFAULT') and p.code = ?2")
	ProductOption findByCode(Integer storeId, String optionCode);
	
	@Query("select distinct p from ProductOption p join fetch p.merchantStore pm left join fetch p.descriptions pd where (pm.id = ?1 or pm.code = 'DEFAULT') and p.code = ?2 and p.readOnly = ?3")
	List<ProductOption> findByReadOnly(Integer storeId, Integer languageId, boolean readOnly);


	@Query("select p from ProductOption p join fetch p.merchantStore pm left join fetch p.descriptions pd where (pm.id = ?1 or pm.code = 'DEFAULT') and p.code = ?2 and pd.language.id = ?3")
	ProductOption findByCode(Integer storeId, String optionCode, Integer languageId);

	@Query("select p.id from ProductOption p where p.code = ?1")
	Long findIdByCode(String optionCode);


}
