package com.salesmanager.shop.model.accesscontrol;

import java.util.ArrayList;
import java.util.List;

import com.salesmanager.shop.model.entity.ReadableList;

public class ReadableAccessControlList extends ReadableList {
	 /**
	   * 
	   */
	  private static final long serialVersionUID = 1L;


	  private List<ReadableAccessControl> data = new ArrayList<ReadableAccessControl>();

	  public List<ReadableAccessControl> getData() {
	    return data;
	  }

	  public void setData(List<ReadableAccessControl> data) {
	    this.data = data;
	  }
}
