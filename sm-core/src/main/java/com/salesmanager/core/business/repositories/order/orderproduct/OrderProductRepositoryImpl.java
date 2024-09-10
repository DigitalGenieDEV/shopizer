package com.salesmanager.core.business.repositories.order.orderproduct;

import com.salesmanager.core.business.utils.RepositoryHelper;
import com.salesmanager.core.enmus.QcInfoHistoryCodeEnums;
import com.salesmanager.core.enmus.QcStatusEnums;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.ProductCriteria;
import com.salesmanager.core.model.catalog.product.ProductList;
import com.salesmanager.core.model.catalog.product.PublishWayEnums;
import com.salesmanager.core.model.common.CriteriaOrderBy;
import com.salesmanager.core.model.common.GenericEntityList;
import com.salesmanager.core.model.fulfillment.ShippingOrderProductQuery;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.Order;
import com.salesmanager.core.model.order.OrderProductCriteria;
import com.salesmanager.core.model.order.orderproduct.OrderProduct;
import com.salesmanager.core.model.order.orderproduct.OrderProductList;
import com.salesmanager.core.model.order.orderstatus.OrderStatus;
import com.salesmanager.core.model.reference.language.Language;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class OrderProductRepositoryImpl implements OrderProductRepositoryCustom{

    @PersistenceContext
    private EntityManager em;

    @Override
    public OrderProductList listOrderProducts(MerchantStore store, OrderProductCriteria criteria) {

        OrderProductList orderProductList = new OrderProductList();
        StringBuilder countBuilderSelect = new StringBuilder();
        StringBuilder objectBuilderSelect = new StringBuilder();

        String orderByCriteria = " order by op.id desc";

        if (criteria.getOrderBy() != null) {
            if (CriteriaOrderBy.ASC.name().equals(criteria.getOrderBy().name())) {
                orderByCriteria = " order by op.id asc";
            }
        }

        String baseQuery = "select op from OrderProduct as op left join fetch op.order o";
        String countBaseQuery = "select count(op) from OrderProduct as op";

        countBuilderSelect.append(countBaseQuery);
        objectBuilderSelect.append(baseQuery);

        StringBuilder countBuilderWhere = new StringBuilder();
        StringBuilder objectBuilderWhere = new StringBuilder();

        String storeQuery = " where op.order.merchant.code=:mCode";
        objectBuilderWhere.append(storeQuery);
        countBuilderWhere.append(storeQuery);


        if (!StringUtils.isEmpty(criteria.getReceiver())) {
            String receiverQuery =  " and op.order.delivery.firstName like:name or op.order.delivery.lastName like:name";
            objectBuilderWhere.append(receiverQuery);
            countBuilderWhere.append(receiverQuery);
        }

        if (criteria.getId() != null) {
            String idQuery =  " and str(op.id) like:id";
            objectBuilderWhere.append(idQuery);
            countBuilderWhere.append(idQuery);
        }

        if (!StringUtils.isEmpty(criteria.getStatuses())) {
            String statusesQuery = " and op.order.status in (:statuses)";
            objectBuilderWhere.append(statusesQuery);
            countBuilderWhere.append(statusesQuery);
        }

        if (!StringUtils.isEmpty(criteria.getBeginDate()) && !StringUtils.isEmpty(criteria.getEndDate())) {
            String dateQuery = " and op.order.datePurchased between CAST(:beginDate AS date) and CAST(:endDate AS date)";
            objectBuilderWhere.append(dateQuery);
            countBuilderWhere.append(dateQuery);
        }

        objectBuilderWhere.append(orderByCriteria);

        //count query
        Query countQ = em.createQuery(
                countBuilderSelect.toString() + countBuilderWhere.toString());

        //object query
        Query objectQ = em.createQuery(
                objectBuilderSelect.toString() + objectBuilderWhere.toString());

        if (!StringUtils.isEmpty(criteria.getReceiver())) {
            countQ.setParameter("name", criteria.getReceiver());
            objectQ.setParameter("name", criteria.getReceiver());
        }

        if (criteria.getId() != null) {
            countQ.setParameter("id", like(String.valueOf(criteria.getId())));
            objectQ.setParameter("id", like(String.valueOf(criteria.getId())));
        }

        if (!StringUtils.isEmpty(criteria.getStatuses())) {
//            String[] statuses = criteria.getStatuses().split(",");
//            List<OrderStatus> statusList = new ArrayList<>();
//            statusList.add(OrderStatus.ORDERED);
//            statusList.add(OrderStatus.PAYMENT_COMPLETED);

            List<OrderStatus> statusList =
                    Arrays.asList(criteria.getStatuses().split(",")).stream().map(OrderStatus::valueOf).collect(Collectors.toList());

            countQ.setParameter("statuses", statusList);
            objectQ.setParameter("statuses", statusList);
        }

        if (!StringUtils.isEmpty(criteria.getBeginDate()) && !StringUtils.isEmpty(criteria.getEndDate())) {
            countQ.setParameter("beginDate", criteria.getBeginDate());
            objectQ.setParameter("beginDate", criteria.getBeginDate());

            countQ.setParameter("endDate", criteria.getEndDate());
            objectQ.setParameter("endDate", criteria.getEndDate());
        }

        countQ.setParameter("mCode", store.getCode());
        objectQ.setParameter("mCode", store.getCode());

        Number count = (Number) countQ.getSingleResult();

        orderProductList.setTotalCount(count.intValue());

        if(count.intValue()==0)
            return orderProductList;

        GenericEntityList entityList = new GenericEntityList();
        entityList.setTotalCount(count.intValue());

        objectQ = RepositoryHelper.paginateQuery(objectQ, count, entityList, criteria);

        //TODO use GenericEntityList

        orderProductList.setTotalCount(entityList.getTotalCount());
        orderProductList.setTotalPages(entityList.getTotalPages());

        orderProductList.setOrderProducts(objectQ.getResultList());

        return orderProductList;
    }

    @Override
    public OrderProductList findByProductNameOrOrderId(ShippingOrderProductQuery orderProductShippingQuery) {
        OrderProductList orderProductList = new OrderProductList();
        // Step 1: Query to get total count
        StringBuilder countBuilderWhere = new StringBuilder();
        countBuilderWhere.append(" where 1 = 1");
        appendSimpleConditions(countBuilderWhere, orderProductShippingQuery);
        long start = System.currentTimeMillis();
        // Count query to get total count
        Query countQ = this.em.createQuery("SELECT COUNT(p.id) from OrderProduct p" + countBuilderWhere.toString());

        setSimpleParameters(countQ, orderProductShippingQuery);

        Long totalCount = (Long) countQ.getSingleResult();
        orderProductList.setTotalCount(totalCount.intValue());
        long end = System.currentTimeMillis();
        System.out.println("query count time"+ (end - start));
        if (totalCount == 0) {
            return orderProductList;
        }
        long start1 = System.currentTimeMillis();
        // Step 2: Query to get paginated Product IDs with sorting by modification time
        StringBuilder countBuilderSelect = new StringBuilder();
        countBuilderSelect.append("select p.id from OrderProduct as p");
        //  appendComplexConditions(countBuilderWhere, criteria);

        String fullQuery = countBuilderSelect.toString() + countBuilderWhere.toString() + " ORDER BY p.id DESC";
        Query productIdsQ = this.em.createQuery(fullQuery);
        setComplexParameters(productIdsQ, orderProductShippingQuery);
        int firstResult = ((orderProductShippingQuery.getStartPage()==0?0:orderProductShippingQuery.getStartPage())) * orderProductShippingQuery.getPageSize();
        productIdsQ.setFirstResult(firstResult);
        productIdsQ.setMaxResults(orderProductShippingQuery.getPageSize());
        List<Long> productIds = productIdsQ.getResultList();
        long end1 = System.currentTimeMillis();
        System.out.println("query productIds  time"+ (end1 - start1));
        // Step 3: Query to get detailed Product entities with sorting by modification time
        if (!productIds.isEmpty()) {
            long start2 = System.currentTimeMillis();
            List<OrderProduct> productByIds = getProductByIds(productIds);
            long end2 = System.currentTimeMillis();
            System.out.println("query product info   time"+ (end2 - start2));
            orderProductList.setOrderProducts(productByIds);
        }
        return orderProductList;
    }

    private void setComplexParameters(Query query, ShippingOrderProductQuery criteria) {
        setSimpleParameters(query, criteria);
//		if (criteria.getLanguage() != null && !criteria.getLanguage().equals("_all")) {
//			query.setParameter("lang", language.getCode());
//		}
    }


    private void appendSimpleConditions(StringBuilder queryBuilder, ShippingOrderProductQuery orderProductShippingQuery) {
        queryBuilder.append(" and p.qcInfo.status =  :qcst");
        queryBuilder.append(" and p.isInShippingOrder =  :iiso");

        if (!StringUtils.isBlank(orderProductShippingQuery.getProductName())) {
            queryBuilder.append(" and p.productName like :pname");
        }

        if (orderProductShippingQuery.getOrderId() !=null) {
            queryBuilder.append(" and p.order.orderId = :ordid");
        }
    }


    private void setSimpleParameters(Query query, ShippingOrderProductQuery orderProductShippingQuery) {
        query.setParameter("iiso", false);
        query.setParameter("qcst", QcStatusEnums.APPROVE);

        if (!StringUtils.isBlank(orderProductShippingQuery.getProductName())) {
            query.setParameter("pname", orderProductShippingQuery.getProductName());
        }
        if (orderProductShippingQuery.getOrderId() !=null) {
            query.setParameter("ordid", orderProductShippingQuery.getOrderId());
        }
    }


    public List<OrderProduct> getProductByIds(List<Long> productIds) {
        final String hql = "select distinct p from OrderProduct as p " +
                "where p.id in :pid order by p.id desc";
        final Query q = this.em.createQuery(hql);
        q.setParameter("pid", productIds);
        return q.getResultList();
    }


    private String like(String q) {
        return '%' + q + '%';
    }

}
