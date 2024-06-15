package com.salesmanager.shop.model.banner;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class BannerEntity {
	private int id=0;
	private String site="";
	private String position="";
	private String name="";
	private String sdate="";
	private String edate="";
	private String url="";
	private String target="";
	private String image="";
	private String alt="";
	private int ord = 0;
	private int visible =0;

}
