package com.salesmanager.core.model.order;

import com.salesmanager.core.model.common.Criteria;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderCriteria extends Criteria {

	private String shippingStatus;

	private Long startTime;

	private Long endTime;

	private String customerName = null;
	private String customerPhone = null;
	private String status = null;
	private Long id = null;
	private String paymentMethod;
	private Long customerId;
	private String email;
	private String orderType = null;
	private String customerCountryName = null;
	private String customsClearanceNumber = null;
	private String shippingType = null;
	private String productName = null;
	private String hsCode = null;

}
