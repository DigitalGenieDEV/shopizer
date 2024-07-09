package com.salesmanager.shop.model.privacy;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class PrivacyEntity {
	private int id = 0;
	private String division = "";
	private String title = "";
	private String content;
	private int visible = 0;

	

}
