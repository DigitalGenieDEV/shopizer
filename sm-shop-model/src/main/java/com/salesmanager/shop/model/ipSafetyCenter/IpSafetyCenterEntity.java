package com.salesmanager.shop.model.ipSafetyCenter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class IpSafetyCenterEntity {
	private int id= 0;
	private String reportType= "";
	private String title= "";
	private String regName= "";
	private String state= "";
	private String tel= "";
	private String email= "";
	private int open= 0;
	private String image= "";
	private String url= "";
	private String reason= "";
	private String content= "";
	private String iprNation= "";
	private String iprType= "";
	private String iprTitle= "";
	private String iprNo= "";
	private String iprOwner= "";
	private String iprApplyDate= "";
	private String iprRegDate= "";
	private String iprCompany= "";
	private String regDate="";
	private String replyContent;
}
