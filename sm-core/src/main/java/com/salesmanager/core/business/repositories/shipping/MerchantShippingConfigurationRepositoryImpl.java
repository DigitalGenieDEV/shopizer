package com.salesmanager.core.business.repositories.shipping;

import com.salesmanager.core.model.common.Criteria;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.system.MerchantShippingConfiguration;
import com.salesmanager.core.model.system.MerchantShippingConfigurationList;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


public class MerchantShippingConfigurationRepositoryImpl implements MerchantShippingConfigurationRepositoryCustom {

	
    @PersistenceContext
    private EntityManager em;
    
    /**
     * @deprecated
     */
	@SuppressWarnings("unchecked")
	@Override
	public MerchantShippingConfigurationList listByStore(MerchantStore store, Criteria criteria
			, String shippingType, String shippingTransportationType) {

		MerchantShippingConfigurationList merchantShippingConfigurationList = new MerchantShippingConfigurationList();
		StringBuilder countBuilderSelect = new StringBuilder();
		StringBuilder objectBuilderSelect = new StringBuilder();
		
		String countBaseQuery = "select count(*) from MerchantShippingConfiguration as o";
		String baseQuery = "select o from MerchantShippingConfiguration as o";
		countBuilderSelect.append(countBaseQuery);
		objectBuilderSelect.append(baseQuery);

		StringBuilder countBuilderWhere = new StringBuilder();
		StringBuilder objectBuilderWhere = new StringBuilder();
		String whereQuery = " where o.merchantStore.id=:mId";
		countBuilderWhere.append(whereQuery);
		objectBuilderWhere.append(whereQuery);

		if (StringUtils.isNotEmpty(shippingType)) {
			countBuilderWhere.append(" and o.shippingType = :shippingType");
			objectBuilderWhere.append(" and o.shippingType = :shippingType");
		}
		if (StringUtils.isNotEmpty(shippingTransportationType)) {
			countBuilderWhere.append(" and o.shippingTransportationType like :shippingTransportationType");
			objectBuilderWhere.append(" and o.shippingTransportationType like :shippingTransportationType");
		}

		//count query
		Query countQ = em.createQuery(
				countBuilderSelect.toString() + countBuilderWhere.toString());
		
		//object query
		Query objectQ = em.createQuery(
				objectBuilderSelect.toString() + objectBuilderWhere.toString());

		countQ.setParameter("mId", store.getId());
		objectQ.setParameter("mId", store.getId());


		if (StringUtils.isNotEmpty(shippingType)) {
			countQ.setParameter("shippingType", shippingType);
			objectQ.setParameter("shippingType", shippingType);
		}
		if (StringUtils.isNotEmpty(shippingTransportationType)) {
			countQ.setParameter("shippingTransportationType", "%" + shippingTransportationType + "%");
			objectQ.setParameter("shippingTransportationType", "%" + shippingTransportationType + "%");
		}

		Number count = (Number) countQ.getSingleResult();

		merchantShippingConfigurationList.setTotalCount(count.intValue());
		
        if(count.intValue()==0){
			return merchantShippingConfigurationList;
		}

		int firstResult = ((criteria.getStartPage()==0?0:criteria.getStartPage())) * criteria.getPageSize();
		objectQ.setFirstResult(firstResult);
		objectQ.setMaxResults(criteria.getPageSize());

		@SuppressWarnings("unchecked")
		List<MerchantShippingConfiguration> results = objectQ.getResultList();
		merchantShippingConfigurationList.setMerchantShippingConfigurations(results);

		return merchantShippingConfigurationList;
		
		
	}



}
