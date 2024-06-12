package com.salesmanager.shop.model.popup;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersistablePopup extends PopupEntity {
	private String userId="";
	private String userIp="";
}
