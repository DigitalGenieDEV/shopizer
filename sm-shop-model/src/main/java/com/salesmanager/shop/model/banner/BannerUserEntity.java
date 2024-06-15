package com.salesmanager.shop.model.banner;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BannerUserEntity {
	private int id=0;
	private String position="";
	private String name="";
	private String url="";
	private String linkTarget="";
	private String image="";
	private String alt="";

}
