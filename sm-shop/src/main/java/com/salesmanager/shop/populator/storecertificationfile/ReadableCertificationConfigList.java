package com.salesmanager.shop.populator.storecertificationfile;

import java.util.ArrayList;
import java.util.List;

import com.salesmanager.shop.model.entity.ReadableList;
import com.salesmanager.shop.model.storecertificationfile.ReadableCertificationConfig;

public class ReadableCertificationConfigList extends ReadableList {
	private static final long serialVersionUID = 1L;
	private List<ReadableCertificationConfig> data = new ArrayList<ReadableCertificationConfig>();

	public List<ReadableCertificationConfig> getData() {
		return data;
	}

	public void setData(List<ReadableCertificationConfig> data) {
		this.data = data;
	}

}
