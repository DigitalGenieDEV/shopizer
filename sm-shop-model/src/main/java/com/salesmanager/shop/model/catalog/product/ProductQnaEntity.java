package com.salesmanager.shop.model.catalog.product;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.salesmanager.shop.model.entity.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductQnaEntity extends Entity implements Serializable {
	private static final long serialVersionUID = 1L;
	@NotEmpty
	private String description;
	private Long productId;
	private String date;
	private String questionType;
	private boolean secret;
}
