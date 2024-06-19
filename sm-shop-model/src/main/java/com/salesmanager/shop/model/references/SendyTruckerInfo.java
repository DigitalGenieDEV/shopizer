package com.salesmanager.shop.model.references;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class SendyTruckerInfo {
	private TruckType truckType;
	private double capacity;
	private Boolean top;
	private Boolean lift;
	private Boolean plus;
	private Boolean freezer;
	private Boolean fridge;
	private Boolean wing;
	private Boolean horu;
	private Boolean wide;
	//private Boolean long; // 변경 필요
}
