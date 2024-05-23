package com.salesmanager.core.business.repositories.shipping;

import com.salesmanager.core.model.common.Criteria;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.system.MerchantShippingConfigurationList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


public class MerchantShippingConfigurationRepositoryImpl implements MerchantShippingConfigurationRepositoryCustom {

	
    @PersistenceContext
    private EntityManager em;
    
    /**
     * @deprecated
     */
	@SuppressWarnings("unchecked")
	@Override
	public MerchantShippingConfigurationList listByStore(MerchantStore store, Criteria criteria) {

		MerchantShippingConfigurationList merchantShippingConfigurationList = new MerchantShippingConfigurationList();
		StringBuilder countBuilderSelect = new StringBuilder();
		StringBuilder objectBuilderSelect = new StringBuilder();
		
		String countBaseQuery = "select count(o) from MerchantShippingConfiguration as o";
		String baseQuery = "select o from MerchantShippingConfiguration as o";
		countBuilderSelect.append(countBaseQuery);
		objectBuilderSelect.append(baseQuery);

		StringBuilder countBuilderWhere = new StringBuilder();
		StringBuilder objectBuilderWhere = new StringBuilder();
		String whereQuery = " where o.merchant.id=:mId";
		countBuilderWhere.append(whereQuery);
		objectBuilderWhere.append(whereQuery);

		//count query
		Query countQ = em.createQuery(
				countBuilderSelect.toString() + countBuilderWhere.toString());
		
		//object query
		Query objectQ = em.createQuery(
				objectBuilderSelect.toString() + objectBuilderWhere.toString());

		countQ.setParameter("mId", store.getId());
		objectQ.setParameter("mId", store.getId());


		Number count = (Number) countQ.getSingleResult();

		merchantShippingConfigurationList.setTotalCount(count.intValue());
		
        if(count.intValue()==0)
        	return merchantShippingConfigurationList;
        

		merchantShippingConfigurationList.setMerchantShippingConfigurations(objectQ.getResultList());

		return merchantShippingConfigurationList;
		
		
	}



}
