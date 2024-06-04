package com.salesmanager.core.model.board;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;

import com.salesmanager.core.model.common.audit2.AuditSection2;
import com.salesmanager.core.model.common.audit2.Auditable2;
import com.salesmanager.core.model.dept.Dept;
import com.salesmanager.core.model.generic.SalesManagerEntity;

@Entity
@EntityListeners(value = com.salesmanager.core.model.common.audit2.AuditListener2.class)
@Table(name = "Board", indexes = @Index(columnList = "TITLE, TYPE, STATE, SDATE, EDATE, WRITER"), uniqueConstraints = @UniqueConstraint(columnNames = {
		"ID" }))
public class Board extends SalesManagerEntity<Integer, Board> implements Auditable2 {
	private static final long serialVersionUID = 1L;

	@Id
	@NotEmpty
	@Column(name = "ID", unique = true, nullable = false, updatable = false, columnDefinition = "INT(11) not null comment 'ID'")
	@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "BOARD_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Integer id;

	@Column(name = "BBS_ID", length = 20, nullable = false, updatable = false, columnDefinition = "varchar(20) not null comment '게시판 ID'")
	private String bbsId;

	@Column(name = "TITLE", length = 50, nullable = false, columnDefinition = "varchar(50) not null comment '제목'")
	private String title;

	@NotEmpty
	@Lob
	@Column(name = "CONTENT", nullable = false, columnDefinition = "LONGTEXT not null comment '내용'")
	private String content;

	@Column(name = "WRITER", length = 50, nullable = false, columnDefinition = "varchar(50) not null comment '작성자'")
	private String writer;

	@Column(name = "EMAIL", length = 50, nullable = true, columnDefinition = "varchar(50) not null comment '이메일'")
	private String email;

	@Column(name = "TEL", length = 20, nullable = true, columnDefinition = "varchar(20) not null comment '이메일'")
	private String tel;

	@Column(name = "NOTICE", nullable = true, columnDefinition = "INT(10) not null comment '공지사용여부'")
	private Integer notice;

	@Column(name = "OPEN", nullable = true, columnDefinition = "INT(10) not null comment '공개여부'")
	private Integer open;

	@Column(name = "SDATE", length = 20, nullable = true, columnDefinition = "VARCHAR(20) not null comment '게시기간 시작일자'")
	private String sdate;

	@Column(name = "EDATE", length = 20, nullable = true, columnDefinition = "VARCHAR(20) not null comment '게시기간 종료일자'")
	private String edate;

	@Column(name = "TYPE", length = 5, nullable = true, columnDefinition = "VARCHAR(20) not null comment '유형'")
	private String type;

	@Column(name = "STATE", length = 1, nullable = true, columnDefinition = "VARCHAR(20) not null comment '상태'")
	private String state;

	@NotEmpty
	@Lob
	@Column(name = "REPLY_CONTENT", nullable = true, columnDefinition = "VARCHAR(20) not null comment '답변내용'")
	private String replyContent;

	@Embedded
	private AuditSection2 auditSection = new AuditSection2();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBbsId() {
		return bbsId;
	}

	public void setBbsId(String bbsId) {
		this.bbsId = bbsId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Integer getNotice() {
		return notice;
	}

	public void setNotice(Integer notice) {
		this.notice = notice;
	}

	public Integer getOpen() {
		return open;
	}

	public void setOpen(Integer open) {
		this.open = open;
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public String getEdate() {
		return edate;
	}

	public void setEdate(String edate) {
		this.edate = edate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public AuditSection2 getAuditSection() {
		return auditSection;
	}

	public void setAuditSection(AuditSection2 auditSection) {
		this.auditSection = auditSection;
	}

}
