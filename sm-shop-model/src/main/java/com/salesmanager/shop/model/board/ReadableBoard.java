package com.salesmanager.shop.model.board;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.salesmanager.shop.model.common.FileEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class ReadableBoard {
	private int id=0;
	private String title="";
	private String writer="";
	private String regDate="";
	private String state="";
	private String bbsId="";
	private String email="";
	private String tel="";
	private String sdate="";
	private String edate="";
	private int notice=0;
	private int open=0;
	private String content="";
	private String type="";
	private String replyContent="";
	private String regId="";
	private String regIp="";
	private String modId="";
	private String modIp="";
	private String modDate="";
	
	private List<FileEntity> fileList = new ArrayList<FileEntity>();
	
	
}
