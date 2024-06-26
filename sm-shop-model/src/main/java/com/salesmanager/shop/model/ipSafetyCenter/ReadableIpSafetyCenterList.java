package com.salesmanager.shop.model.ipSafetyCenter;

import java.util.ArrayList;
import java.util.List;

import com.salesmanager.shop.model.entity.ReadableList;

public class ReadableIpSafetyCenterList extends ReadableList {
	/**
	  * 
	  */

	private static final long serialVersionUID = 1L;

	private List<IpSafetyCenterEntity> data = new ArrayList<IpSafetyCenterEntity>();

	public List<IpSafetyCenterEntity> getData() {
		return data;
	}

	public void setData(List<IpSafetyCenterEntity> data) {
		this.data = data;
	}
}
