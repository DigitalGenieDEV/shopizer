package com.salesmanager.shop.model.references;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class SendyOrdersData {
	private String orderStatus;
	private LocalDateTime regDateTime;
	private LocalDateTime pickUpDateTime;
	private Integer fare;
	private List<SendyAddressInfo> wayPoints;
	private String workType;
	private String truck;
	private String message;
	private SendyTruckerInfo truckerInfo;
}
