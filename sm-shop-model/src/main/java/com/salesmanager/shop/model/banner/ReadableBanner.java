package com.salesmanager.shop.model.banner;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class ReadableBanner extends BannerEntity {
	private String regId="";
	private String regDate="";
	private String regIp="";
	private String modId="";
	private String modDate="";
	private String modIp="";
}
