package com.salesmanager.shop.model.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeStateEntity {
	private String  selectedIds = "";
	private String state = "";
	private String userId="";
	private String userIp="";
}
