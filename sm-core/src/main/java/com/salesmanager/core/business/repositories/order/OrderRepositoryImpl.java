package com.salesmanager.core.business.repositories.order;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.ProductCriteria;
import com.salesmanager.core.model.catalog.product.ProductList;
import com.salesmanager.core.model.catalog.product.PublishWayEnums;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.order.Order;
import com.salesmanager.core.model.order.OrderCustomerCriteria;
import com.salesmanager.core.model.reference.language.Language;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.salesmanager.core.business.utils.RepositoryHelper;
import com.salesmanager.core.model.common.CriteriaOrderBy;
import com.salesmanager.core.model.common.GenericEntityList;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.OrderCriteria;
import com.salesmanager.core.model.order.OrderList;
import com.salesmanager.core.model.order.orderstatus.OrderStatus;

import java.util.Date;
import java.util.List;


public class OrderRepositoryImpl implements OrderRepositoryCustom {

	
    @PersistenceContext
    private EntityManager em;
    
    /**
     * @deprecated
     */
	@SuppressWarnings("unchecked")
	@Override
	public OrderList listByStore(MerchantStore store, OrderCriteria criteria) {
		

		OrderList orderList = new OrderList();
		StringBuilder countBuilderSelect = new StringBuilder();
		StringBuilder objectBuilderSelect = new StringBuilder();
		
		String orderByCriteria = " order by o.id desc";
		
		if(criteria.getOrderBy()!=null) {
			if(CriteriaOrderBy.ASC.name().equals(criteria.getOrderBy().name())) {
				orderByCriteria = " order by o.id asc";
			}
		}
		
		String countBaseQuery = "select count(o) from Order as o";
		String baseQuery = "select o from Order as o left join fetch o.orderTotal ot left join fetch o.orderProducts op left join fetch o.orderAttributes oa left join fetch op.orderAttributes opo left join fetch op.prices opp";
		countBuilderSelect.append(countBaseQuery);
		objectBuilderSelect.append(baseQuery);

		
		
		StringBuilder countBuilderWhere = new StringBuilder();
		StringBuilder objectBuilderWhere = new StringBuilder();
		String whereQuery = " where o.merchant.id=:mId";
		countBuilderWhere.append(whereQuery);
		objectBuilderWhere.append(whereQuery);
		

		if(!StringUtils.isBlank(criteria.getCustomerName())) {
			String nameQuery =" and o.billing.firstName like:nm or o.billing.lastName like:nm";
			countBuilderWhere.append(nameQuery);
			objectBuilderWhere.append(nameQuery);
		}
		
		if(!StringUtils.isBlank(criteria.getPaymentMethod())) {
			String paymentQuery =" and o.paymentModuleCode like:pm";
			countBuilderWhere.append(paymentQuery);
			objectBuilderWhere.append(paymentQuery);
		}
		
		if(criteria.getCustomerId()!=null) {
			String customerQuery =" and o.customerId =:cid";
			countBuilderWhere.append(customerQuery);
			objectBuilderWhere.append(customerQuery);
		}
		
		objectBuilderWhere.append(orderByCriteria);
		

		//count query
		Query countQ = em.createQuery(
				countBuilderSelect.toString() + countBuilderWhere.toString());
		
		//object query
		Query objectQ = em.createQuery(
				objectBuilderSelect.toString() + objectBuilderWhere.toString());

		countQ.setParameter("mId", store.getId());
		objectQ.setParameter("mId", store.getId());
		

		if(!StringUtils.isBlank(criteria.getCustomerName())) {
			String nameParam = new StringBuilder().append("%").append(criteria.getCustomerName()).append("%").toString();
			countQ.setParameter("nm",nameParam);
			objectQ.setParameter("nm",nameParam);
		}
		
		if(!StringUtils.isBlank(criteria.getPaymentMethod())) {
			String payementParam = new StringBuilder().append("%").append(criteria.getPaymentMethod()).append("%").toString();
			countQ.setParameter("pm",payementParam);
			objectQ.setParameter("pm",payementParam);
		}
		
		if(criteria.getCustomerId()!=null) {
			countQ.setParameter("cid", criteria.getCustomerId());
			objectQ.setParameter("cid",criteria.getCustomerId());
		}
		

		Number count = (Number) countQ.getSingleResult();

		orderList.setTotalCount(count.intValue());
		
        if(count.intValue()==0)
        	return orderList;
        
		//TO BE USED
        int max = criteria.getMaxCount();
        int first = criteria.getStartIndex();
        
        objectQ.setFirstResult(first);
        
        
        
    	if(max>0) {
    			int maxCount = first + max;

			objectQ.setMaxResults(Math.min(maxCount, count.intValue()));
    	}
		
    	orderList.setOrders(objectQ.getResultList());

		return orderList;
		
		
	}

	public OrderList listOrders1(MerchantStore store, OrderCriteria criteria) {
		OrderList orderList = new OrderList();
		StringBuilder countBuilderSelect = new StringBuilder();
		StringBuilder objectBuilderSelect = new StringBuilder();

		String orderByCriteria = " order by o.id desc";

		if(criteria.getOrderBy()!=null) {
			if(CriteriaOrderBy.ASC.name().equals(criteria.getOrderBy().name())) {
				orderByCriteria = " order by o.id asc";
			}
		}

		String baseQuery = "select o from Order as o left join fetch o.delivery.country " +
				"left join fetch o.delivery.zone " +
				"left join fetch o.billing.country " +
				"left join fetch o.billing.zone " +
				"left join fetch o.orderTotal ot " +
				"left join fetch o.orderProducts op " +
				"left join fetch o.orderAttributes oa " +
				"left join fetch op.orderAttributes opo " +
				"left join fetch op.prices opp " +
				"left join fetch o.fulfillmentMainOrder fm ";

		String countBaseQuery = "select count(o) from Order as o";
		
		countBuilderSelect.append(countBaseQuery);
		objectBuilderSelect.append(baseQuery);

		StringBuilder objectBuilderWhere = new StringBuilder();

		String storeQuery =" where o.merchant.code=:mCode";
		objectBuilderWhere.append(storeQuery);
		countBuilderSelect.append(storeQuery);
		
		if(!StringUtils.isEmpty(criteria.getCustomerName())) {
			String nameQuery =  " and o.billing.firstName like:name or o.billing.lastName like:name";
			objectBuilderWhere.append(nameQuery);
			countBuilderSelect.append(nameQuery);
		}
		
		if(!StringUtils.isEmpty(criteria.getEmail())) {
			String nameQuery =  " and o.customerEmailAddress like:email";
			objectBuilderWhere.append(nameQuery);
			countBuilderSelect.append(nameQuery);
		}
		
		//id
		if(criteria.getId() != null) {
			String nameQuery =  " and str(o.id) like:id";
			objectBuilderWhere.append(nameQuery);
			countBuilderSelect.append(nameQuery);
		}
		
		//phone
		if(!StringUtils.isEmpty(criteria.getCustomerPhone())) {
			String nameQuery =  " and o.billing.telephone like:phone or o.delivery.telephone like:phone";
			objectBuilderWhere.append(nameQuery);
			countBuilderSelect.append(nameQuery);
		}
		
		//status
		if(!StringUtils.isEmpty(criteria.getStatus())) {
			String nameQuery =  " and o.status =:status";
			objectBuilderWhere.append(nameQuery);
			countBuilderSelect.append(nameQuery);
		}
	
		objectBuilderWhere.append(orderByCriteria);

		//count query
		Query countQ = em.createQuery(
				countBuilderSelect.toString());

		//object query
		Query objectQ = em.createQuery(
				objectBuilderSelect.toString() + objectBuilderWhere.toString());
		
		//customer name
		if(!StringUtils.isEmpty(criteria.getCustomerName())) {
			countQ.setParameter("name", like(criteria.getCustomerName()));
			objectQ.setParameter("name", like(criteria.getCustomerName()));
		}
		
		//email
		if(!StringUtils.isEmpty(criteria.getEmail())) {
			countQ.setParameter("email", like(criteria.getEmail()));
			objectQ.setParameter("email", like(criteria.getEmail()));			
		}
		
		//id
		if(criteria.getId() != null) {
			countQ.setParameter("id", like(String.valueOf(criteria.getId())));
			objectQ.setParameter("id", like(String.valueOf(criteria.getId())));
		}
		
		//phone
		if(!StringUtils.isEmpty(criteria.getCustomerPhone())) {
			countQ.setParameter("phone", like(criteria.getCustomerPhone()));
			objectQ.setParameter("phone", like(criteria.getCustomerPhone()));
		}
		
		//status
		if(!StringUtils.isEmpty(criteria.getStatus())) {
			countQ.setParameter("status", OrderStatus.valueOf(criteria.getStatus().toUpperCase()));
			objectQ.setParameter("status", OrderStatus.valueOf(criteria.getStatus().toUpperCase()));
		}
		

		countQ.setParameter("mCode", store.getCode());
		objectQ.setParameter("mCode", store.getCode());


		Number count = (Number) countQ.getSingleResult();

		if(count.intValue()==0)
			return orderList;

	    @SuppressWarnings("rawtypes")
		GenericEntityList entityList = new GenericEntityList();
	    entityList.setTotalCount(count.intValue());
		
		objectQ = RepositoryHelper.paginateQuery(objectQ, count, entityList, criteria);
		
		//TODO use GenericEntityList

		orderList.setTotalCount(entityList.getTotalCount());
		orderList.setTotalPages(entityList.getTotalPages());

		orderList.setOrders(objectQ.getResultList());

		return orderList;
	}

	@Override
	public OrderList listByCustomer(Customer customer, OrderCustomerCriteria criteria) {
		OrderList orderList = new OrderList();
		StringBuilder countBuilderSelect = new StringBuilder();
		StringBuilder objectBuilderSelect = new StringBuilder();

		String orderByCriteria = " order by o.id desc";

		if(criteria.getOrderBy()!=null) {
			if(CriteriaOrderBy.ASC.name().equals(criteria.getOrderBy().name())) {
				orderByCriteria = " order by o.id asc";
			}
		}

		String baseQuery = "select o from Order as o left join fetch o.delivery.country left join fetch o.delivery.zone left join fetch o.billing.country left join fetch o.billing.zone left join fetch o.orderTotal ot left join fetch o.orderProducts op left join fetch o.orderAttributes oa left join fetch op.orderAttributes opo left join fetch op.prices opp";
		String countBaseQuery = "select count(o) from Order as o";

		countBuilderSelect.append(countBaseQuery);
		objectBuilderSelect.append(baseQuery);

		StringBuilder objectBuilderWhere = new StringBuilder();

		String storeQuery =" where o.customerId =:cid";
		objectBuilderWhere.append(storeQuery);
		countBuilderSelect.append(storeQuery);

		objectBuilderWhere.append(orderByCriteria);

		//count query
		Query countQ = em.createQuery(
				countBuilderSelect.toString());

		//object query
		Query objectQ = em.createQuery(
				objectBuilderSelect.toString() + objectBuilderWhere.toString());


		countQ.setParameter("cid", customer.getId());
		objectQ.setParameter("cid", customer.getId());

		Number count = (Number) countQ.getSingleResult();

		if(count.intValue()==0)
			return orderList;

		@SuppressWarnings("rawtypes")
		GenericEntityList entityList = new GenericEntityList();
		entityList.setTotalCount(count.intValue());

		objectQ = RepositoryHelper.paginateQuery(objectQ, count, entityList, criteria);

		//TODO use GenericEntityList

		orderList.setTotalCount(entityList.getTotalCount());
		orderList.setTotalPages(entityList.getTotalPages());

		orderList.setOrders(objectQ.getResultList());

		return orderList;
	}




	@Override
	public OrderList listOrders(MerchantStore store, OrderCriteria criteria) {
		OrderList orderList = new OrderList();
		// Step 1: Query to get total count
		StringBuilder countBuilderWhere = new StringBuilder();
		countBuilderWhere.append(" where 1 = 1");
		appendSimpleConditions(store, countBuilderWhere, criteria);
		long start = System.currentTimeMillis();
		// Count query to get total count
		Query countQ = this.em.createQuery("SELECT COUNT(p.id) from Order p" + countBuilderWhere.toString());
		if (store !=null){
			countQ.setParameter("mId", store.getId());
		}
		setSimpleParameters(countQ, criteria);

		Long totalCount = (Long) countQ.getSingleResult();
		orderList.setTotalCount(totalCount.intValue());
		long end = System.currentTimeMillis();
		System.out.println("query count time"+ (end - start));
		if (totalCount == 0) {
			return orderList;
		}
		long start1 = System.currentTimeMillis();
		// Step 2: Query to get paginated Product IDs with sorting by modification time
		StringBuilder countBuilderSelect = new StringBuilder();
		countBuilderSelect.append("select p.id from Order as p");
		//  appendComplexConditions(countBuilderWhere, criteria);

		String fullQuery = countBuilderSelect.toString() + countBuilderWhere.toString() + " ORDER BY p.datePurchased DESC";
		Query orderIdsQ = this.em.createQuery(fullQuery);
		if (store !=null){
			orderIdsQ.setParameter("mId", store.getId());
		}
		setComplexParameters(orderIdsQ, criteria);
		int firstResult = ((criteria.getStartPage()==0?0:criteria.getStartPage())) * criteria.getPageSize();
		orderIdsQ.setFirstResult(firstResult);
		orderIdsQ.setMaxResults(criteria.getPageSize());
		List<Long> orderIds = orderIdsQ.getResultList();
		long end1 = System.currentTimeMillis();
		System.out.println("query orders  time"+ (end1 - start1));
		// Step 3: Query to get detailed Product entities with sorting by modification time
		if (!orderIds.isEmpty()) {
			long start2 = System.currentTimeMillis();
			List<Order> productByIds = getProductByIds(orderIds);
			long end2 = System.currentTimeMillis();
			System.out.println("query order info  time"+ (end2 - start2));
			orderList.setOrders(productByIds);
		}
		return orderList;
	}

	private void appendSimpleConditions(MerchantStore store, StringBuilder queryBuilder, OrderCriteria criteria) {
		if (store !=null){
			queryBuilder.append(" and p.merchant.id=:mId");
		}
		if(!StringUtils.isEmpty(criteria.getCustomerName())) {
			String nameQuery =  " and o.billing.firstName like:name or o.billing.lastName like:name";
			queryBuilder.append(nameQuery);
		}

		if(!StringUtils.isEmpty(criteria.getEmail())) {
			String nameQuery =  " and o.customerEmailAddress like:email";
			queryBuilder.append(nameQuery);
		}
		//id
		if(criteria.getId() != null) {
			String nameQuery =  " and str(o.id) like:id";
			queryBuilder.append(nameQuery);
		}
		//phone
		if(!StringUtils.isEmpty(criteria.getCustomerPhone())) {
			String nameQuery =  " and o.billing.telephone like:phone or o.delivery.telephone like:phone";
			queryBuilder.append(nameQuery);
		}

		//status
		if(!StringUtils.isEmpty(criteria.getStatus())) {
			String nameQuery =  " and o.status =:status";
			queryBuilder.append(nameQuery);
		}
	}

	private void setComplexParameters(Query query, OrderCriteria criteria) {
		setSimpleParameters(query, criteria);
	}

	public List<Order> getProductByIds(List<Long> orderIds) {
		final String hql = "select distinct p from Order as p " +
				"join fetch p.merchant merch " +
				"where p.id in :pid order by p.lastModified";
		final Query q = this.em.createQuery(hql);
		q.setParameter("pid", orderIds);
		return q.getResultList();
	}



	private void setSimpleParameters(Query query, OrderCriteria criteria) {
		//customer name
		if(!StringUtils.isEmpty(criteria.getCustomerName())) {
			query.setParameter("name", like(criteria.getCustomerName()));
		}

		//email
		if(!StringUtils.isEmpty(criteria.getEmail())) {
			query.setParameter("email", like(criteria.getEmail()));
		}

		//id
		if(criteria.getId() != null) {
			query.setParameter("id", like(String.valueOf(criteria.getId())));
		}

		//phone
		if(!StringUtils.isEmpty(criteria.getCustomerPhone())) {
			query.setParameter("phone", like(criteria.getCustomerPhone()));
		}

		//status
		if(!StringUtils.isEmpty(criteria.getStatus())) {
			query.setParameter("status", OrderStatus.valueOf(criteria.getStatus().toUpperCase()));
		}

	}

	private String like(String q) {
		return '%' + q + '%';
	}

}
