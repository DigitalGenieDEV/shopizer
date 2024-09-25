package com.salesmanager.core.business.repositories.order;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.google.common.collect.Lists;
import com.salesmanager.core.enmus.FulfillmentTypeEnums;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.order.*;
import com.salesmanager.core.model.payments.PaymentType;
import com.salesmanager.core.model.shipping.ShippingType;
import com.salesmanager.core.model.shipping.TransportationMethod;
import org.apache.commons.lang3.StringUtils;

import com.salesmanager.core.business.utils.RepositoryHelper;
import com.salesmanager.core.model.common.CriteriaOrderBy;
import com.salesmanager.core.model.common.GenericEntityList;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.orderstatus.OrderStatus;

import java.util.*;


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
			countQ.setParameter("status", OrderStatus.fromValue(criteria.getStatus().toUpperCase()));
			objectQ.setParameter("status", OrderStatus.fromValue(criteria.getStatus().toUpperCase()));
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

		String orderByCriteria = " order by o.auditSection.dateCreated desc";

		if(criteria.getOrderBy() != null) {
			if(CriteriaOrderBy.ASC.name().equals(criteria.getOrderBy().name())) {
				orderByCriteria = " order by o.auditSection.dateCreated asc";
			}
		}

		String baseQuery = "select o from Order as o left join fetch o.delivery.country left join fetch o.delivery.zone left join fetch o.billing.country left join fetch o.billing.zone left join fetch o.orderTotal ot left join fetch o.orderProducts op left join fetch o.orderAttributes oa left join fetch op.orderAttributes opo left join fetch op.prices opp";
		String countBaseQuery = "select count(o) from Order as o";

		countBuilderSelect.append(countBaseQuery);
		objectBuilderSelect.append(baseQuery);

		StringBuilder objectBuilderWhere = new StringBuilder();

		String storeQuery = " where o.customerId =:cid";

		// append sql
		if (criteria.getStartTime() != null && criteria.getStartTime() > 0) {
			storeQuery += " AND o.auditSection.dateCreated >= :startTime";
		}
		if (criteria.getEndTime() != null && criteria.getEndTime() > 0) {
			storeQuery += " AND o.auditSection.dateCreated <= :endTime";
		}
		if (criteria.getOrderStatus() != null) {
			storeQuery += " and o.status = :status";
		}
		if (StringUtils.isNotBlank(criteria.getProductName())) {
			storeQuery += " and (exists (select oops.id from o.orderProducts oops where oops.productName like :productName) or o.merchant.storename like :productName)";
		}
		if (StringUtils.isNotBlank(criteria.getOrderType())) {
			storeQuery += " and o.orderType in :orderType";
		}


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

		// replace placeholder
		if (criteria.getStartTime() != null && criteria.getStartTime() > 0) {
			countQ.setParameter("startTime", new Date(criteria.getStartTime()));
			objectQ.setParameter("startTime", new Date(criteria.getStartTime()));
		}
		if (criteria.getEndTime() != null && criteria.getEndTime() > 0) {
			countQ.setParameter("endTime", new Date(criteria.getEndTime()));
			objectQ.setParameter("endTime", new Date(criteria.getEndTime()));
		}
		if (criteria.getOrderStatus() != null) {
			OrderStatus orderStatusEnum;
			try {
				orderStatusEnum = OrderStatus.fromValue(criteria.getOrderStatus().toUpperCase());
			} catch (Exception ignore) {
				throw new IllegalArgumentException("Unknown order status:" + criteria.getOrderStatus());
			}

			countQ.setParameter("status", orderStatusEnum);
			objectQ.setParameter("status", orderStatusEnum);
		}
		if (StringUtils.isNotBlank(criteria.getProductName())) {
			countQ.setParameter("productName", like(criteria.getProductName()));
			objectQ.setParameter("productName", like(criteria.getProductName()));
		}
		if (StringUtils.isNotBlank(criteria.getOrderType())) {
			OrderType orderTypeEnum;
			try {
				orderTypeEnum = OrderType.valueOf(criteria.getOrderType().toUpperCase());
			} catch (Exception ignore) {
				throw new IllegalArgumentException("Unknown order type:" + criteria.getOrderType());
			}
			List<OrderType> list;
			if (orderTypeEnum == OrderType.PRODUCT) {
				list = Lists.newArrayList(OrderType.PRODUCT, OrderType.PRODUCT_1688);
			} else {
				list = Lists.newArrayList(orderTypeEnum);
			}

			countQ.setParameter("orderType", list);
			objectQ.setParameter("orderType", list);
		}

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
	public Map<String, Integer> countCustomerOrderByStatus(Customer customer, OrderCustomerCriteria criteria) {

		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("select o.status as orderStatus, count(o) as num from Order as o");
		sqlBuilder.append(" where o.customerId =:cid");
		// append sql
		if (criteria.getStartTime() != null && criteria.getStartTime() > 0) {
			sqlBuilder.append(" AND o.auditSection.dateCreated >= :startTime");
		}
		if (criteria.getEndTime() != null && criteria.getEndTime() > 0) {
			sqlBuilder.append(" AND o.auditSection.dateCreated <= :endTime");
		}
		if (criteria.getOrderStatus() != null) {
			sqlBuilder.append(" and o.status in :orderStatus");
		}
		if (StringUtils.isNotBlank(criteria.getProductName())) {
			sqlBuilder.append("and (exists (select pops.id from o.orderProducts oops where oops.productName like :productName) or o.merchant.storename like :productName)");
		}
		if (StringUtils.isNotBlank(criteria.getOrderType())) {
			sqlBuilder.append(" and o.orderType in :orderType");
		}
		sqlBuilder.append(" group by o.status");

		//count query
		Query countQ = em.createQuery(sqlBuilder.toString());

		countQ.setParameter("cid", customer.getId());
		// replace placeholder
		if (criteria.getStartTime() != null && criteria.getStartTime() > 0) {
			countQ.setParameter("startTime", new Date(criteria.getStartTime()));
		}
		if (criteria.getEndTime() != null && criteria.getEndTime() > 0) {
			countQ.setParameter("endTime", new Date(criteria.getEndTime()));
		}
		if (criteria.getOrderStatus() != null) {
			OrderStatus orderStatusEnum;
			try {
				orderStatusEnum = OrderStatus.fromValue(criteria.getOrderStatus().toUpperCase());
			} catch (Exception ignore) {
				throw new IllegalArgumentException("Unknown order status:" + criteria.getOrderStatus());
			}

			countQ.setParameter("status", orderStatusEnum);
		}
		if (StringUtils.isNotBlank(criteria.getProductName())) {
			countQ.setParameter("productName", like(criteria.getProductName()));
		}
		if (StringUtils.isNotBlank(criteria.getOrderType())) {
			OrderType orderTypeEnum;
			try {
				orderTypeEnum = OrderType.valueOf(criteria.getOrderType().toUpperCase());
			} catch (Exception ignore) {
				throw new IllegalArgumentException("Unknown order type:" + criteria.getOrderType());
			}
			List<OrderType> list;
			if (orderTypeEnum == OrderType.PRODUCT) {
				list = Lists.newArrayList(OrderType.PRODUCT, OrderType.PRODUCT_1688);
			} else {
				list = Lists.newArrayList(orderTypeEnum);
			}

			countQ.setParameter("orderType", list);
		}

		List result = countQ.getResultList();

		int totalCount = 0;
		HashMap<String, Integer> orderStatusCountMap = new HashMap<>();
		for (Object o : result) {
			Object[] objects = (Object[]) o;
			OrderStatus status = (OrderStatus) objects[0];
			Long num = (Long) objects[1];
			totalCount += num.intValue();
			orderStatusCountMap.put(status.toString(), num.intValue());
        }
		for (OrderStatus value : OrderStatus.values()) {
			orderStatusCountMap.merge(value.toString(), 0, Integer::sum);
		}
		orderStatusCountMap.put("TOTAL", totalCount);

		return orderStatusCountMap;
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
		if (StringUtils.isNotEmpty(criteria.getOrderNo())){
			String nameQuery =  " and p.orderNo =:orderNo";
			queryBuilder.append(nameQuery);
		}
		if(!StringUtils.isEmpty(criteria.getCustomerName())) {
			String nameQuery =  " and (p.billing.firstName like:name or p.billing.lastName like:name)";
			queryBuilder.append(nameQuery);
		}
		if(!StringUtils.isEmpty(criteria.getDeliveryName())) {
			String nameQuery =  " and (p.delivery.firstName like:deliveryName or p.delivery.lastName like:deliveryName)";
			queryBuilder.append(nameQuery);
		}

		if(!StringUtils.isEmpty(criteria.getEmail())) {
			String nameQuery =  " and p.customerEmailAddress like:email";
			queryBuilder.append(nameQuery);
		}
		//id
		if(criteria.getId() != null) {
			String nameQuery =  " and str(p.id) like:id";
			queryBuilder.append(nameQuery);
		}
		//phone
		if(!StringUtils.isEmpty(criteria.getCustomerPhone())) {
			String nameQuery =  " and (p.billing.telephone like:phone or p.delivery.telephone like:phone)";
			queryBuilder.append(nameQuery);
		}

		//status
		if(!StringUtils.isEmpty(criteria.getStatus())) {
			String nameQuery =  " and p.status =:status";
			queryBuilder.append(nameQuery);
		}
		if (criteria.getStartTime() != null && criteria.getStartTime() > 0) {
			String nameQuery = " and p.auditSection.dateCreated >= :startTime";
			queryBuilder.append(nameQuery);
		}
		if (criteria.getEndTime() != null && criteria.getEndTime() > 0) {
			String nameQuery = " and p.auditSection.dateCreated <= :endTime";
			queryBuilder.append(nameQuery);
		}
		if (StringUtils.isNotBlank(criteria.getShippingStatus())) {
			String nameQuery =  " and exists (select pff.id from p.fulfillmentMainOrder.fulfillSubOrders pff where pff.fulfillmentMainType = :shippingStatus)";
			queryBuilder.append(nameQuery);
		}
		if (StringUtils.isNotBlank(criteria.getOrderType())) {
			String queryFragment = "and p.orderType = :orderType";
			queryBuilder.append(queryFragment);
		}

		if (StringUtils.isNotBlank(criteria.getCustomerCountryName())) {
			String queryFragment = "and p.delivery.country.id in (select cd.country.id from CountryDescription cd where cd.name = :customerCountryName and cd.language.code = :language  )";
			queryBuilder.append(queryFragment);
		}
		if (StringUtils.isNotBlank(criteria.getCustomsClearanceNumber())) {
			String queryFragment = "and p.customsClearanceNumber = :customsClearanceNumber";
			queryBuilder.append(queryFragment);
		}
		if (StringUtils.isNotBlank(criteria.getPaymentMethod())) {
			String queryFragment = "and p.paymentType = :paymentMethod";
			queryBuilder.append(queryFragment);
		}
		if (StringUtils.isNotBlank(criteria.getTransportationMethod())) {
			if (criteria.getShippingType() == null) {
				String queryFragment = "and exists (select pff.id from p.fulfillmentMainOrder.fulfillSubOrders pff where pff.nationalTransportationMethod = :transportationMethod or pff.internationalTransportationMethod = :transportationMethod)";
				queryBuilder.append(queryFragment);
			} else {
				ShippingType shippingType = ShippingType.valueOf(criteria.getShippingType().toUpperCase());
				if (ShippingType.NATIONAL.equals(shippingType)) {
					String queryFragment = "and exists (select pff.id from p.fulfillmentMainOrder.fulfillSubOrders pff where pff.shippingType = :shippingType and pff.nationalTransportationMethod = :transportationMethod)";
					queryBuilder.append(queryFragment);
				}
				if (ShippingType.INTERNATIONAL.equals(shippingType)) {
					String queryFragment = "and exists (select pff.id from p.fulfillmentMainOrder.fulfillSubOrders pff where pff.shippingType = :shippingType and pff.internationalTransportationMethod = :transportationMethod)";
					queryBuilder.append(queryFragment);
				}
			}
		}
		if (StringUtils.isNotBlank(criteria.getShippingType())) {
			String queryFragment = "and exists (select pops.id from p.orderProducts pops where pops.shippingType = :shippingType)";
			queryBuilder.append(queryFragment);
		}
		if (StringUtils.isNotBlank(criteria.getProductName())) {
			String queryFragment = "and exists (select pops.id from p.orderProducts pops where pops.productName like :productName)";
			queryBuilder.append(queryFragment);
		}
		if (StringUtils.isNotBlank(criteria.getHsCode())) {
			String queryFragment = "and exists (select pops.id from p.orderProducts pops where pops.productId in (select product.id from Product product where product.hsCode = :hsCode))";
			queryBuilder.append(queryFragment);
		}
		if (StringUtils.isNotBlank(criteria.getStoreCode())) {
			String queryFragment = "and p.merchant.code = :storeCode";
			queryBuilder.append(queryFragment);
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
		//delivery name
		if(!StringUtils.isEmpty(criteria.getCustomerName())) {
			query.setParameter("deliveryName", like(criteria.getDeliveryName()));
		}

		if (StringUtils.isNotEmpty(criteria.getOrderNo())){
			query.setParameter("orderNo", criteria.getOrderNo());
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
			query.setParameter("status", OrderStatus.fromValue(criteria.getStatus().toUpperCase()));
		}

		if (criteria.getStartTime() != null && criteria.getStartTime() > 0) {
			query.setParameter("startTime", new Date(criteria.getStartTime()));
		}
		if (criteria.getEndTime() != null && criteria.getEndTime() > 0) {
			query.setParameter("endTime", new Date(criteria.getEndTime()));
		}
		if (StringUtils.isNotBlank(criteria.getShippingStatus())) {
			query.setParameter("shippingStatus", FulfillmentTypeEnums.valueOf(criteria.getShippingStatus().toUpperCase()));
		}
		if (StringUtils.isNotBlank(criteria.getOrderType())) {
			query.setParameter("orderType", OrderType.valueOf(criteria.getOrderType().toUpperCase()));
		}
		if (StringUtils.isNotBlank(criteria.getCustomerCountryName())) {
			query.setParameter("customerCountryName", criteria.getCustomerCountryName());
			query.setParameter("language", criteria.getLanguage());
		}
		if (StringUtils.isNotBlank(criteria.getCustomsClearanceNumber())) {
			query.setParameter("customsClearanceNumber", criteria.getCustomsClearanceNumber());
		}
		if (StringUtils.isNotBlank(criteria.getPaymentMethod())) {
			query.setParameter("paymentMethod", PaymentType.valueOf(criteria.getPaymentMethod().toUpperCase()));
		}
		if (StringUtils.isNotBlank(criteria.getTransportationMethod())) {
			if (criteria.getShippingType() == null) {
				query.setParameter("transportationMethod", TransportationMethod.valueOf(criteria.getTransportationMethod().toUpperCase()));
			} else {
				query.setParameter("shippingType", ShippingType.valueOf(criteria.getShippingType().toUpperCase()));
				query.setParameter("transportationMethod", TransportationMethod.valueOf(criteria.getTransportationMethod().toUpperCase()));
			}
		}
		if (StringUtils.isNotBlank(criteria.getShippingType())) {
			query.setParameter("shippingType", ShippingType.valueOf(criteria.getShippingType().toUpperCase()));
		}
		if (StringUtils.isNotBlank(criteria.getProductName())) {
			query.setParameter("productName", like(criteria.getProductName()));
		}
		if (StringUtils.isNotBlank(criteria.getHsCode())) {
			query.setParameter("hsCode", criteria.getHsCode());
		}
		if (StringUtils.isNotBlank(criteria.getStoreCode())) {
			query.setParameter("storeCode", criteria.getStoreCode());
		}

	}

	private String like(String q) {
		return '%' + q + '%';
	}

}
