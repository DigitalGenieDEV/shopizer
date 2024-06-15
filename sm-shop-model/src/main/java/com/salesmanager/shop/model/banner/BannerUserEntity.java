package com.salesmanager.shop.model.banner;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class BannerUserEntity {
	private int id=0;
	private String position="";
	private String name="";
	private String url="";
	private String linkTarget="";
	private String image="";

}
