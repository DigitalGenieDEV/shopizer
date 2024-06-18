package com.salesmanager.core.business.repositories.user;

import com.salesmanager.core.model.user.Permission;
import com.salesmanager.core.model.user.PermissionCriteria;
import com.salesmanager.core.model.user.PermissionList;
import com.salesmanager.core.model.user.UserAddress;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;


public class UserAddressRepositoryImpl implements UserAddressRepositoryCustom {

	
    @PersistenceContext
    private EntityManager em;


	@Override
	public List<UserAddress> findByUserId(Long userId, Integer langId) {
		String jpql = "select ua from UserAddress ua " +
				"left join fetch ua.country c " +
				"left join fetch c.descriptions cd " +
				"left join fetch ua.city ci " +
				"left join fetch ci.descriptions cid  " +
				"left join fetch ua.province pr  " +
				"left join fetch pr.descriptions prd  " +
				"where ua.userId = :userId and prd.language.id = :langId and cid.language.id = :langId and cd.language.id = :langId";
		TypedQuery<UserAddress> query = em.createQuery(jpql, UserAddress.class);
		query.setParameter("userId", userId);
		query.setParameter("langId", langId);
		return query.getResultList();
	}

	@Override
	public UserAddress findDefaultAddressByUserId(Long userId, Integer langId) {
		String jpql = "select ua from UserAddress ua " +
				"left join fetch ua.country c " +
				"left join fetch c.descriptions cd " +
				"left join fetch ua.city ci " +
				"left join fetch ci.descriptions cid  " +
				"left join fetch ua.province pr  " +
				"left join fetch pr.descriptions prd  " +
				"where ua.userId = :userId and ua.isDefault = true and prd.language.id = :langId and cid.language.id = :langId and cd.language.id = :langId";
		TypedQuery<UserAddress> query = em.createQuery(jpql, UserAddress.class);
		query.setParameter("userId", userId);
		query.setParameter("langId", langId);
		return query.getSingleResult();
	}




}
