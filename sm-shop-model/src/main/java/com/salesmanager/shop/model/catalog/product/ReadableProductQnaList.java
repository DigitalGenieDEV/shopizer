package com.salesmanager.shop.model.catalog.product;

import java.util.List;

import org.springframework.data.domain.Page;

import com.salesmanager.shop.model.entity.ReadableList;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReadableProductQnaList extends ReadableList {
	private static final long serialVersionUID = 1L;
	private List<ReadableProductQna> data;
	
}
