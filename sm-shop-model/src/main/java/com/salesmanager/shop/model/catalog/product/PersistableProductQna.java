package com.salesmanager.shop.model.catalog.product;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PersistableProductQna extends ProductQnaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long customerId;
	private String fileContentType;
}
