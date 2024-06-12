package com.salesmanager.shop.model.banner;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersistableBanner extends BannerEntity {
	private String userId="";
	private String userIp="";
}
