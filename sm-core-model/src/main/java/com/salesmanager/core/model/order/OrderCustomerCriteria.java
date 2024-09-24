package com.salesmanager.core.model.order;

import com.salesmanager.core.model.common.Criteria;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderCustomerCriteria extends Criteria {
    private Long startTime = null;
    private Long endTime = null;
    private String orderStatus = null;
    private String orderType = null;
    private String productName = null;


}
