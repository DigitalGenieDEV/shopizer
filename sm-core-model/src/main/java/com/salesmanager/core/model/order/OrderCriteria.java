package com.salesmanager.core.model.order;

import com.salesmanager.core.enmus.FulfillmentTypeEnums;
import com.salesmanager.core.model.common.Criteria;
import com.salesmanager.core.model.order.orderstatus.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class OrderCriteria extends Criteria {

	private List<FulfillmentTypeEnums> shippingStatus;

	private Long beginTime;

	private Long endTime;

	private String customerName = null;
	private String deliveryName = null;
	private String customerPhone = null;
	private List<OrderStatus> status = null;
	private Long id = null;
	private String orderNo = null;
	private String paymentMethod;
	private String transportationMethod;
	private Long customerId;
	private String email;
	private String orderType = null;
	private String customerCountryName = null;
	private String customsClearanceNumber = null;
	private String shippingType = null;
	private String productName = null;
	private String hsCode = null;

}
