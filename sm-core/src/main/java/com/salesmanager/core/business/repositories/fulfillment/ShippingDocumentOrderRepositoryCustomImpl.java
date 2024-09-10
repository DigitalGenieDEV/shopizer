package com.salesmanager.core.business.repositories.fulfillment;

import com.salesmanager.core.enmus.QcStatusEnums;
import com.salesmanager.core.model.fulfillment.ShippingOrderProductQuery;
import com.salesmanager.core.model.fulfillment.ShippingDocumentOrder;
import com.salesmanager.core.model.fulfillment.ShippingDocumentOrderList;
import com.salesmanager.core.model.order.orderproduct.OrderProduct;
import com.salesmanager.core.model.order.orderproduct.OrderProductList;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;


public class ShippingDocumentOrderRepositoryCustomImpl implements ShippingDocumentOrderRepositoryCustom{

    @PersistenceContext
    private EntityManager em;


    @Override
    public ShippingDocumentOrderList queryShippingDocumentOrderList(ShippingOrderProductQuery orderProductShippingQuery){
        ShippingDocumentOrderList shippingDocumentOrderList = new ShippingDocumentOrderList();
        // Step 1: Query to get total count
        StringBuilder countBuilderWhere = new StringBuilder();
        countBuilderWhere.append(" where 1 = 1");
        appendSimpleConditions(countBuilderWhere, orderProductShippingQuery);
        long start = System.currentTimeMillis();
        // Count query to get total count
        Query countQ = this.em.createQuery("SELECT COUNT(p.id) from ShippingDocumentOrder p" + countBuilderWhere.toString());

        setSimpleParameters(countQ, orderProductShippingQuery);

        Long totalCount = (Long) countQ.getSingleResult();
        shippingDocumentOrderList.setTotalCount(totalCount.intValue());
        long end = System.currentTimeMillis();
        System.out.println("query count time"+ (end - start));
        if (totalCount == 0) {
            return shippingDocumentOrderList;
        }
        long start1 = System.currentTimeMillis();
        // Step 2: Query to get paginated Product IDs with sorting by modification time
        StringBuilder countBuilderSelect = new StringBuilder();
        countBuilderSelect.append("select p.id from ShippingDocumentOrder as p");
        //  appendComplexConditions(countBuilderWhere, criteria);

        String fullQuery = countBuilderSelect.toString() + countBuilderWhere.toString() + " ORDER BY p.id DESC";
        Query productIdsQ = this.em.createQuery(fullQuery);
        setComplexParameters(productIdsQ, orderProductShippingQuery);
        int firstResult = ((orderProductShippingQuery.getStartPage()==0?0:orderProductShippingQuery.getStartPage())) * orderProductShippingQuery.getPageSize();
        productIdsQ.setFirstResult(firstResult);
        productIdsQ.setMaxResults(orderProductShippingQuery.getPageSize());
        List<Long> ids = productIdsQ.getResultList();
        long end1 = System.currentTimeMillis();
        System.out.println("query shippingDocumentOrderList  time"+ (end1 - start1));
        // Step 3: Query to get detailed Product entities with sorting by modification time
        if (!ids.isEmpty()) {
            long start2 = System.currentTimeMillis();
            List<ShippingDocumentOrder> productByIds = getShippingDocumentOrderByIds(ids);
            long end2 = System.currentTimeMillis();
            System.out.println("query shippingDocumentOrderList info   time"+ (end2 - start2));
            shippingDocumentOrderList.setShippingDocumentOrders(productByIds);
        }
        return shippingDocumentOrderList;
    }

    private void setComplexParameters(Query query, ShippingOrderProductQuery criteria) {
        setSimpleParameters(query, criteria);
//		if (criteria.getLanguage() != null && !criteria.getLanguage().equals("_all")) {
//			query.setParameter("lang", language.getCode());
//		}
    }


    private void appendSimpleConditions(StringBuilder queryBuilder, ShippingOrderProductQuery orderProductShippingQuery) {
        if (!StringUtils.isBlank(orderProductShippingQuery.getProductName())) {
            queryBuilder.append(" and p.orderProducts.productName like :pname");
        }
        if (orderProductShippingQuery.getCreateStartTime() != null && orderProductShippingQuery.getCreateStartTime() > 0) {
            queryBuilder.append(" and p.auditSection.dateCreated >= :createStartTime");
        }
        if (orderProductShippingQuery.getCreateEndTime() != null && orderProductShippingQuery.getCreateEndTime() > 0) {
            queryBuilder.append(" and p.auditSection.dateCreated <= :createEndTime");
        }
        if (orderProductShippingQuery.getOrderId() !=null) {
            queryBuilder.append(" and p.orderProducts.order.orderId = :ordid");
        }
    }


    private void setSimpleParameters(Query query, ShippingOrderProductQuery orderProductShippingQuery) {
        if (!StringUtils.isBlank(orderProductShippingQuery.getProductName())) {
            query.setParameter("pname", orderProductShippingQuery.getProductName());
        }
        if (orderProductShippingQuery.getOrderId() !=null) {
            query.setParameter("ordid", orderProductShippingQuery.getOrderId());
        }
        if (orderProductShippingQuery.getCreateStartTime() !=null && orderProductShippingQuery.getCreateStartTime() >0) {
            query.setParameter("createStartTime", new Date(orderProductShippingQuery.getCreateStartTime()));
        }

        if (orderProductShippingQuery.getCreateEndTime() !=null && orderProductShippingQuery.getCreateEndTime() >0) {
            query.setParameter("createEndTime", new Date(orderProductShippingQuery.getCreateEndTime()));
        }
    }


    public List<ShippingDocumentOrder> getShippingDocumentOrderByIds(List<Long> ids) {
        final String hql = "select distinct p from ShippingDocumentOrder as p " +
                "where p.id in :pid order by p.id desc";
        final Query q = this.em.createQuery(hql);
        q.setParameter("pid", ids);
        return q.getResultList();
    }

}
