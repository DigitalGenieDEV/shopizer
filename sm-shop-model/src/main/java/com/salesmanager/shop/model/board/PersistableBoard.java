package com.salesmanager.shop.model.board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersistableBoard {
	private int id=0;
	private String title="";
	private String writer="";
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
	private String userIp="";
	private String userId="";

}
