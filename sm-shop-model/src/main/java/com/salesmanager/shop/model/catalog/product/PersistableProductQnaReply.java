package com.salesmanager.shop.model.catalog.product;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PersistableProductQnaReply {
	private static final long serialVersionUID = 1L;
	private Long id;
	@NotEmpty
	private String content;
	private Long productId;
//	private String date;
//	private String category;
//	private boolean secret;
}
