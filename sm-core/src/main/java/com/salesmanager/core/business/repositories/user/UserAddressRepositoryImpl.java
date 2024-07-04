package com.salesmanager.core.business.repositories.user;

import com.salesmanager.core.model.common.description.Description;
import com.salesmanager.core.model.reference.country.Country;
import com.salesmanager.core.model.reference.zone.Zone;
import com.salesmanager.core.model.user.Permission;
import com.salesmanager.core.model.user.PermissionCriteria;
import com.salesmanager.core.model.user.PermissionList;
import com.salesmanager.core.model.user.UserAddress;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;


public class UserAddressRepositoryImpl implements UserAddressRepositoryCustom {

	
    @PersistenceContext
    private EntityManager em;


	@Override
	public List<UserAddress> findByUserId(Long userId, Integer langId) {
		// 查询 UserAddress 和关联的 country, city, province
		String jpql = "select ua from UserAddress ua " +
				"left join fetch ua.country c " +
				"left join fetch ua.city ci " +
				"left join fetch ua.province pr " +
				"where ua.userId = :userId";
		TypedQuery<UserAddress> query = em.createQuery(jpql, UserAddress.class);
		query.setParameter("userId", userId);

		return query.getResultList();
	}


	@Override
	public UserAddress findDefaultAddressByUserId(Long userId) {
		String jpql = "select ua from UserAddress ua " +
				"left join fetch ua.country c " +
				"left join fetch ua.city ci " +
				"left join fetch ua.province pr  " +
				"where ua.userId = :userId and ua.isDefault = true ";
		TypedQuery<UserAddress> query = em.createQuery(jpql, UserAddress.class);
		query.setParameter("userId", userId);
		return query.getSingleResult();
	}




}
