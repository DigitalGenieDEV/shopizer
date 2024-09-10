package com.salesmanager.core.model.fulfillment;

import com.salesmanager.core.model.common.Criteria;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ShippingOrderProductQuery extends Criteria {

	private String productName;

	private Long orderId;


	private Long createStartTime;

	private Long createEndTime;


}
