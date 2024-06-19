package com.salesmanager.shop.model.references;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class SendyPickup {
	private Boolean additionalWorker;
	private Boolean driverLoading;
	private Boolean driverWorking;
	private Boolean hasPassenger;
	private LocalDate date;
	private LocalTime time;
	private SendyTruckInfo truckInfo;
}
