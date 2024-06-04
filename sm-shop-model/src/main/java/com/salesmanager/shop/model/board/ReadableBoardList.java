package com.salesmanager.shop.model.board;

import java.util.ArrayList;
import java.util.List;

import com.salesmanager.shop.model.entity.ReadableList;

public class ReadableBoardList extends ReadableList {
	/**
	  * 
	  */

	private static final long serialVersionUID = 1L;

	private List<ReadableBoard> data = new ArrayList<ReadableBoard>();

	public List<ReadableBoard> getData() {
		return data;
	}

	public void setData(List<ReadableBoard> data) {
		this.data = data;
	}
}
