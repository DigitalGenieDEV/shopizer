package com.salesmanager.shop.model.catalog.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PersistableProductQnaReply extends ProductQnaEntity {
	private static final long serialVersionUID = 1L;
	private Long customerId;
}
