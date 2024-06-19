package com.salesmanager.shop.model.references;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class SendyAddressInfo {
	private String address;
	private String roadAddress;
	private String jibunAddress;
	private String additionalAddress;
	private String addressDetail;
	private Integer floor;
	private Boolean useElevator;
	private double latitude;
	private double longitude;
	private SendyContactInfo contact;
	private String addressName;
	private double lat;
	private double lon;
	private Boolean elevator;
	private Boolean isElevator;
}
