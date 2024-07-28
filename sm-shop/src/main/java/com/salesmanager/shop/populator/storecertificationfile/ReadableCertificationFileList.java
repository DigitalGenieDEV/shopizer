package com.salesmanager.shop.populator.storecertificationfile;

import java.util.ArrayList;
import java.util.List;

import com.salesmanager.shop.model.entity.ReadableList;
import com.salesmanager.shop.model.storecertificationfile.ReadableCertificationConfig;
import com.salesmanager.shop.model.storecertificationfile.ReadableCertificationFile;

public class ReadableCertificationFileList extends ReadableList {
	private static final long serialVersionUID = 1L;
	private List<ReadableCertificationFile> data = new ArrayList<ReadableCertificationFile>();

	public List<ReadableCertificationFile> getData() {
		return data;
	}

	public void setData(List<ReadableCertificationFile> data) {
		this.data = data;
	}

}
