package com.salesmanager.shop.model.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileEntity {
	private int fileId = 0;
	private int dataId = 0;
	private String delYn = "";
	private int downloadCnt=0;
	private String fileName="";
	private long fileSize;
	private String fileType="";
	private String fileUrl="";
	private int ord=0;
	private String prgCode="";
	
}
