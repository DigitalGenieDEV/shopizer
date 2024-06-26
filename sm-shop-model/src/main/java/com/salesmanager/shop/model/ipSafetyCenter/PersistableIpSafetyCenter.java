package com.salesmanager.shop.model.ipSafetyCenter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersistableIpSafetyCenter extends IpSafetyCenterEntity   {
	private String userId="";
	private String userIp="";
}
