package com.salesmanager.shop.model.references;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class EmailVerificationDTO {
	private String email;
	private String code;
}
