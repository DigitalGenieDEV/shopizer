package com.salesmanager.core.business.repositories.order.orderproduct;

import com.salesmanager.core.business.utils.RepositoryHelper;
import com.salesmanager.core.model.common.CriteriaOrderBy;
import com.salesmanager.core.model.common.GenericEntityList;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.Order;
import com.salesmanager.core.model.order.OrderProductCriteria;
import com.salesmanager.core.model.order.orderproduct.OrderProductList;
import com.salesmanager.core.model.order.orderstatus.OrderStatus;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Arrays;
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
//            statusList.add(OrderStatus.PROCESSED);

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

    private String like(String q) {
        return '%' + q + '%';
    }

}
