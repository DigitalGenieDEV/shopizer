package com.salesmanager.shop.model.references;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SendyOrdersRegisterData {
	private String bookingNumber;
	private String name;
	private String regDate;
	private LocalDateTime regDateTime;
}
