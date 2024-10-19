package com.salesmanager.core.business.repositories.catalog.product.attribute;

import java.util.List;

import com.salesmanager.core.model.catalog.product.attribute.OptionSetForSaleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.salesmanager.core.model.catalog.product.attribute.ProductOptionSet;

public interface ProductOptionSetRepository extends JpaRepository<ProductOptionSet, Long> {

	@Query("select distinct p from ProductOptionSet p join fetch p.store pm left join fetch p.option po left join fetch po.descriptions pod left join fetch p.values pv left join fetch pv.descriptions pvd where pm.id = ?1 and p.id = ?2 ")
	ProductOptionSet findOne(Integer storeId, Long id, Integer language);
	
	@Query("select distinct p from ProductOptionSet p join fetch p.store pm left join fetch p.option po left join fetch po.descriptions pod left join fetch p.values pv left join fetch pv.descriptions pvd where pm.id = ?1 ")
	List<ProductOptionSet> findByStore(Integer storeId, Integer language);
	
	@Query("select distinct p from ProductOptionSet p "
			+ "join fetch p.store pm left join fetch p.productTypes pt "
			+ "left join fetch p.option po "
			+ "left join fetch po.descriptions pod "
			+ "left join fetch p.values pv "
			+ "left join fetch pv.descriptions pvd where pt.id= ?1 and pm.id = ?2 and pod.language.id = ?3")
	List<ProductOptionSet> findByProductType(Long typeId, Integer storeId, Integer language);


	@Query("select distinct p from ProductOptionSet p "
			+ "join fetch p.category pt "
			+ "left join fetch p.option po "
			+ "left join fetch po.descriptions pod "
			+ "left join fetch p.values pv "
			+ "left join fetch pv.descriptions pvd where pt.id= ?1  and pod.language.id = ?2 and p.optionSetForSaleType = ?3" )
	List<ProductOptionSet> findByCategoryId(Long categoryId, Integer language, String optionSetForSaleType);


	@Query("select distinct p from ProductOptionSet p "
			+ "join fetch p.category ca "
			+ "left join fetch ca.descriptions pod "
			+ "where  pod.language.id = ?1 and p.optionSetForSaleType = ?2" )
	List<ProductOptionSet> findByOptionSetForSaleType(Integer language, String optionSetForSaleType);


	@Query("select p from ProductOptionSet p join fetch p.store pm left join fetch p.option po left join fetch po.descriptions pod left join fetch p.values pv left join fetch pv.descriptions pvd where pm.id = ?1 and p.code = ?2")
	ProductOptionSet findByCode(Integer storeId, String code);
	
	
	@Query("select p from ProductOptionSet p join fetch p.store pm left join fetch p.option po left join fetch po.descriptions pod left join fetch p.values pv left join fetch pv.descriptions pvd where pm.id = ?1 and p.code = ?2 and p.category.id= ?3 ")
	ProductOptionSet findByCode2(Integer storeId, String code, Long categoryId);

}
