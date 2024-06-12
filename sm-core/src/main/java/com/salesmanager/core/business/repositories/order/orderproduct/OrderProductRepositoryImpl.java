package com.salesmanager.core.business.repositories.order.orderproduct;

import com.salesmanager.core.business.utils.RepositoryHelper;
import com.salesmanager.core.model.common.CriteriaOrderBy;
import com.salesmanager.core.model.common.GenericEntityList;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.OrderProductCriteria;
import com.salesmanager.core.model.order.orderproduct.OrderProductList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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


        objectBuilderWhere.append(orderByCriteria);

        //count query
        Query countQ = em.createQuery(
                countBuilderSelect.toString() + countBuilderWhere.toString());

        //object query
        Query objectQ = em.createQuery(
                objectBuilderSelect.toString() + objectBuilderWhere.toString());

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
}
