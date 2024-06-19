package com.salesmanager.shop.model.references;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SendyResponseBody<T> {
	private Boolean result;
	private String message;
	private T data;
}
