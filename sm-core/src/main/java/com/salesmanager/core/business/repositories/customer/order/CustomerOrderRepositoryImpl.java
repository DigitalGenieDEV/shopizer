package com.salesmanager.core.business.repositories.customer.order;

import com.salesmanager.core.business.utils.RepositoryHelper;
import com.salesmanager.core.model.common.CriteriaOrderBy;
import com.salesmanager.core.model.common.GenericEntityList;
import com.salesmanager.core.model.customer.order.CustomerOrderCriteria;
import com.salesmanager.core.model.customer.order.CustomerOrderList;
import com.salesmanager.core.model.order.orderstatus.OrderStatus;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class CustomerOrderRepositoryImpl implements CustomerOrderRepositoryCustom{

    @PersistenceContext
    private EntityManager em;

    @Override
    public CustomerOrderList listCustomerOrders(CustomerOrderCriteria criteria) {
        CustomerOrderList customerOrderList = new CustomerOrderList();
        StringBuilder countBuilderSelect = new StringBuilder();
        StringBuilder objectBuilderSelect = new StringBuilder();

        String orderByCriteria = " order by co.id desc";

        if(criteria.getOrderBy()!=null) {
            if(CriteriaOrderBy.ASC.name().equals(criteria.getOrderBy().name())) {
                orderByCriteria = " order by co.id asc";
            }
        }

        String baseQuery = "select co from CustomerOrder as co left join fetch co.orders o left join fetch o.delivery.country left join fetch o.delivery.zone left join fetch o.billing.country left join fetch o.billing.zone left join fetch o.orderTotal ot left join fetch o.orderProducts op left join fetch o.orderAttributes oa left join fetch op.orderAttributes opo left join fetch op.prices opp";
        String countBaseQuery = "select count(o) from Order as o";

        countBuilderSelect.append(countBaseQuery);
        objectBuilderSelect.append(baseQuery);

        StringBuilder objectBuilderWhere = new StringBuilder();

        String storeQuery =" where 1 = 1 ";
        objectBuilderWhere.append(storeQuery);
        countBuilderSelect.append(storeQuery);

        if(!StringUtils.isEmpty(criteria.getCustomerName())) {
            String nameQuery =  " and co.billing.firstName like:name or co.billing.lastName like:name";
            objectBuilderWhere.append(nameQuery);
            countBuilderSelect.append(nameQuery);
        }

//        if(!StringUtils.isEmpty(criteria.getEmail())) {
//            String nameQuery =  " and o.customerEmailAddress like:email";
//            objectBuilderWhere.append(nameQuery);
//            countBuilderSelect.append(nameQuery);
//        }

        //id
        if(criteria.getId() != null) {
            String nameQuery =  " and str(co.id) like:id";
            objectBuilderWhere.append(nameQuery);
            countBuilderSelect.append(nameQuery);
        }

        //phone
        if(!StringUtils.isEmpty(criteria.getCustomerPhone())) {
            String nameQuery =  " and co.billing.telephone like:phone or co.delivery.telephone like:phone";
            objectBuilderWhere.append(nameQuery);
            countBuilderSelect.append(nameQuery);
        }

        //status
        if(!StringUtils.isEmpty(criteria.getStatus())) {
            String nameQuery =  " and co.status =:status";
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
//        if(!StringUtils.isEmpty(criteria.getEmail())) {
//            countQ.setParameter("email", like(criteria.getEmail()));
//            objectQ.setParameter("email", like(criteria.getEmail()));
//        }

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

        Number count = (Number) countQ.getSingleResult();

        if(count.intValue()==0)
            return customerOrderList;

        GenericEntityList entityList = new GenericEntityList();
        entityList.setTotalCount(count.intValue());

        objectQ = RepositoryHelper.paginateQuery(objectQ, count, entityList, criteria);

        customerOrderList.setTotalCount(entityList.getTotalCount());
        customerOrderList.setTotalPages(entityList.getTotalPages());

        customerOrderList.setCustomerOrders(objectQ.getResultList());

        return customerOrderList;
    }

    private String like(String q) {
        return '%' + q + '%';
    }

}
