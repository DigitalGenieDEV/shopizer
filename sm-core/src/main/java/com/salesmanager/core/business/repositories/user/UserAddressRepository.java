package com.salesmanager.core.business.repositories.user;

import com.salesmanager.core.model.user.User;
import com.salesmanager.core.model.user.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserAddressRepository extends JpaRepository<UserAddress, Long>, UserAddressRepositoryCustom {

//	@Query("select ua from UserAddress ua " +
//			"left join fetch ua.country c " +
//			"left join fetch c.descriptions cd " +
//			"left join fetch ua.city ci " +
//			"left join fetch ci.descriptions cid  " +
//			"left join fetch ua.province pr  " +
//			"left join fetch pr.descriptions prd  " +
//			" where ua.userId = ?1 and prd.language.id = ?2 and cid.language.id = ?2 and cd.language.id = ?2 ")
//	List<UserAddress> findByUserId(Long userId, Integer langId);


	@Query("select ua from UserAddress ua " +
			" where ua.userId = ?1 ")
	List<UserAddress> findByUserId(Long userId);


//	@Query("select ua from UserAddress ua " +
//			"left join fetch ua.country c " +
//			"left join fetch c.descriptions cd " +
//			"left join fetch ua.city ci " +
//			"left join fetch ci.descriptions cid  " +
//			"left join fetch ua.province pr  " +
//			"left join fetch pr.descriptions prd  " +
//			" where ua.userId = ?1 and ua.isDefault = true and prd.language.id = ?2 and cid.language.id = ?2 and cd.language.id = ?2 ")
//	UserAddress findDefaultAddressByUserId( Long userId, Integer langId);
//
//	@Query("select ua from UserAddress ua " +
//			"left join fetch ua.country c " +
//			"left join fetch c.descriptions cd " +
//			"left join fetch ua.city ci " +
//			"left join fetch ci.descriptions cid  " +
//			"left join fetch ua.province pr  " +
//			"left join fetch pr.descriptions prd  " +
//			"where ua.userId = ?1 and prd.language.id = ?2 and cid.language.id = ?2 and cd.language.id = ?2 ")
//	List<UserAddress> findByUserIdWithCountryAndZone(Long userId, Integer langId);


	@Transactional
	@Modifying
	@Query("update UserAddress ua set ua.isDefault = false where ua.userId = ?1")
	void resetDefaultAddress(Long userId);

	@Transactional
	@Modifying
	@Query("update UserAddress ua set ua.isDefault = true where ua.id = ?1 and ua.userId = ?2")
	void setDefaultAddress(Long addressId, Long userId);
}
