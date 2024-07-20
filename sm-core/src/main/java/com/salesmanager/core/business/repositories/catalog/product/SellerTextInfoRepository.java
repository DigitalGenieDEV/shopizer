package com.salesmanager.core.business.repositories.catalog.product;

import com.salesmanager.core.model.catalog.product.SellerTextInfo;
import com.salesmanager.core.model.catalog.product.SellerTextType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface SellerTextInfoRepository extends JpaRepository<SellerTextInfo, Long> {


	@Query(value="SELECT " +
			"s " +
			"FROM " +
			"SellerTextInfo s " +
			"WHERE s.sellerId = ?1 and s.type = ?2")
	List<SellerTextInfo> querySellerTextInfoList(Long sellerId, SellerTextType type);

}
