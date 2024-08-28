package com.salesmanager.shop.model.catalog.product;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReadableProductQnaReply {
	private Long Id;
	private String title;
	private String content;
	private String date;
}
