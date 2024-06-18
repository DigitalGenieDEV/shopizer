package com.salesmanager.core.business.repositories.user;

import com.salesmanager.core.model.user.User;
import com.salesmanager.core.model.user.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {

	@Query("select ua from UserAddress ua " +
			"left join fetch ua.country c " +
			"left join fetch c.descriptions cd " +
			"left join fetch ua.city ci " +
			"left join fetch ci.descriptions cid  " +
			"left join fetch ua.province pr  " +
			"left join fetch pr.descriptions prd  " +
			" where ua.userId = :userId and prd.language.id = :langId and cid.language.id = :langId and cd.language.id = :langId ")
	List<UserAddress> findByUserId(@Param("userId") Long userId, @Param("langId") Integer langId);


	@Query("select ua from UserAddress ua " +
			" where ua.userId = :userId ")
	List<UserAddress> findByUserId(@Param("userId") Long userId);


	@Query("select ua from UserAddress ua " +
			"left join fetch ua.country c " +
			"left join fetch c.descriptions cd " +
			"left join fetch ua.city ci " +
			"left join fetch ci.descriptions cid  " +
			"left join fetch ua.province pr  " +
			"left join fetch pr.descriptions prd  " +
			" where ua.userId = :userId and ua.isDefault = true and prd.language.id = :langId and cid.language.id = :langId and cd.language.id = :langId ")
	UserAddress findDefaultAddressByUserId(@Param("userId") Long userId, @Param("langId") Integer langId);

	@Query("select ua from UserAddress ua " +
			"left join fetch ua.country c " +
			"left join fetch c.descriptions cd " +
			"left join fetch ua.city ci " +
			"left join fetch ci.descriptions cid  " +
			"left join fetch ua.province pr  " +
			"left join fetch pr.descriptions prd  " +
			"where ua.userId = :userId and prd.language.id = :langId and cid.language.id = :langId and cd.language.id = :langId ")
	List<UserAddress> findByUserIdWithCountryAndZone(@Param("userId") Long userId, @Param("langId") Integer langId);


	@Transactional
	@Modifying
	@Query("update UserAddress ua set ua.isDefault = false where ua.userId = :userId")
	void resetDefaultAddress(@Param("userId") Long userId);

	@Transactional
	@Modifying
	@Query("update UserAddress ua set ua.isDefault = true where ua.id = :addressId and ua.userId = :userId")
	void setDefaultAddress(@Param("addressId") Long addressId, @Param("userId") Long userId);
}
