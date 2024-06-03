package com.salesmanager.shop.model.board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardEntity {
	private int id=0;
	private String title="";
	private String writer="";
	private String state="";
	private String bbsId="";
	private String email="";
	private String tel="";
	private String sDate="";
	private String eDate="";
	private int notice=0;
	private int open=0;
	private String content="";
	private String type="";
	private String replyContent="";
	private String userIp="";
	private String userId="";
}
