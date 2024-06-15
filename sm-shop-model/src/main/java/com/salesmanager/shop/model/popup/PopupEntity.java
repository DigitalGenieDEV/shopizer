package com.salesmanager.shop.model.popup;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class PopupEntity {
	private int id  = 0;
	private String site="";
	private String type="";
	private String name="";
	private String sdate="";
	private String edate="";
	private String url="";
	private String target="";
	private String image="";
	private int width = 0;
	private int height = 0;
	private int left = 0;
	private int top = 0;
	private int ord = 0;
	private int visible = 0;
	private String alt="";
}
