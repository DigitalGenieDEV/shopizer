package com.salesmanager.shop.model.popup;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PopupUserEntity {
	private int id  = 0;
	private String type="";
	private String name="";
	private String url="";
	private String linkTarget="";
	private String image="";
	private int pop_width = 0;
	private int pop_height = 0;
	private int pop_left = 0;
	private int pop_top = 0;
	private String alt="";

}
