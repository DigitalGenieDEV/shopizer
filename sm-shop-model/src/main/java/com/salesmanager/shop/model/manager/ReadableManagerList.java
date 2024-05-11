package com.salesmanager.shop.model.manager;

import java.util.ArrayList;
import java.util.List;

import com.salesmanager.shop.model.entity.ReadableList;

public class ReadableManagerList extends ReadableList {
	 /**
	   * 
	   */
	  private static final long serialVersionUID = 1L;

	  private List<ReadableManager> data = new ArrayList<ReadableManager>();

	  public List<ReadableManager> getData() {
	    return data;
	  }

	  public void setData(List<ReadableManager> data) {
	    this.data = data;
	  }
}
