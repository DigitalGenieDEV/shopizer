package com.salesmanager.shop.model.catalog.qna;

import java.util.List;

import com.salesmanager.shop.model.entity.ReadableList;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReadableQnaList extends ReadableList {
	private static final long serialVersionUID = 1L;
	private List<ReadableQna> data;
	
}
