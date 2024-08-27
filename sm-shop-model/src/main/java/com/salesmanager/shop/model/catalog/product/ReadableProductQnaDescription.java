package com.salesmanager.shop.model.catalog.product;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReadableProductQnaDescription {
	private Long Id;
	private String title;
	private String description;
	private Date created;
}
