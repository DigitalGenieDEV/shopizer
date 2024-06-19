package com.salesmanager.shop.model.references;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class SendyDTO {
	private SendyPickup pickUp;
	private List<SendyAddressInfo> wayPoints;
	private String message;
	private String path;
	private String category;
}
