package com.salesmanager.core.model.iprsafecenter;

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

import com.salesmanager.core.model.banner.Banner;
import com.salesmanager.core.model.common.audit2.AuditSection2;
import com.salesmanager.core.model.common.audit2.Auditable2;
import com.salesmanager.core.model.generic.SalesManagerEntity;

@Entity
@EntityListeners(value = com.salesmanager.core.model.common.audit2.AuditListener2.class)
@Table(name = "IPR_SAFETY_CENTER", indexes = @Index(columnList = "REG_NAME, REPORT_TYPE, TITLE, REG_DATE"), uniqueConstraints = @UniqueConstraint(columnNames = {
		"ID" }))
public class IpSafetyCenter extends SalesManagerEntity<Integer, IpSafetyCenter> implements Auditable2 {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ID", unique = true, nullable = false, updatable = false, columnDefinition = "INT(11) not null comment 'ID'")
	@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "IPR_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Integer id;

	@NotEmpty
	@Column(name = "REPORT_TYPE", length = 10, nullable = false, updatable = true, columnDefinition = "varchar(20) not null comment '신고구분'")
	private String reportType;

	@NotEmpty
	@Column(name = "TITLE", length = 100, nullable = false, updatable = true, columnDefinition = "varchar(100) not null comment '제목'")
	private String title;

	@NotEmpty
	@Column(name = "REG_NAME", length = 50, nullable = false, updatable = true, columnDefinition = "varchar(50) not null comment '작성자'")
	private String regName;

	@NotEmpty
	@Column(name = "STATE", length = 10, nullable = false, updatable = true, columnDefinition = "varchar(10) not null comment '진행상태'")
	private String state;

	@NotEmpty
	@Column(name = "TEL", length = 20, nullable = false, updatable = false, columnDefinition = "varchar(20) not null comment '연락처'")
	private String tel;

	@NotEmpty
	@Column(name = "EMAIL", length = 50, nullable = false, updatable = false, columnDefinition = "varchar(50) not null comment '이메일'")
	private String email;

	@Column(name = "OPEN", length = 11, nullable = true, updatable = true, columnDefinition = "INT(11) not null comment '공개여부'")
	private int open;

	@Column(name = "IMAGE", length = 255, nullable = true, updatable = true, columnDefinition = "varchar(255) not null comment '이미지'")
	private String image;

	@Column(name = "URL", length = 255, nullable = true, updatable = true, columnDefinition = "varchar(255) not null comment 'URL'")
	private String url;

	@Column(name = "REASON", length = 1000, nullable = true, updatable = true, columnDefinition = "varchar(1000) not null comment '신고이유'")
	private String reason;

	@Lob
	@Column(name = "CONTENT", nullable = true, columnDefinition = "LONGTEXT not null comment '내용'")
	private String content;

	@Column(name = "IPR_NATION", length = 100, nullable = true, updatable = true, columnDefinition = "varchar(100) not null comment '등록국가'")
	private String iprNation;

	@Column(name = "IPR_TYPE", length = 100, nullable = true, updatable = true, columnDefinition = "varchar(100) not null comment '지식재산권 유형'")
	private String iprType;

	@Column(name = "IPR_TITLE", length = 100, nullable = true, updatable = true, columnDefinition = "varchar(100) not null comment '지식재산권 명칭'")
	private String iprTitle;

	@Column(name = "IPR_NO", length = 40, nullable = true, updatable = true, columnDefinition = "varchar(40) not null comment '지식재산권 등록번호'")
	private String iprNo;

	@Column(name = "IPR_OWNER", length = 50, nullable = true, updatable = true, columnDefinition = "varchar(50) not null comment '지식재산권 소유자'")
	private String iprOwner;

	@Column(name = "IPR_APPLY_DATE", length = 10, nullable = true, updatable = true, columnDefinition = "varchar(10) not null comment '지식재산권 출원일자'")
	private String iprApplyDate;

	@Column(name = "IPR_REG_DATE", length = 10, nullable = true, updatable = true, columnDefinition = "varchar(10) not null comment '지식재산권 등록일자'")
	private String iprRegDate;

	@Column(name = "IPR_COMAPNT", length = 50, nullable = true, updatable = true, columnDefinition = "varchar(50) not null comment '지식재산권 권리자'")
	private String iprCompany;
	
	@Lob
	@Column(name = "REPLY_CONTENT", nullable = true, columnDefinition = "LONGTEXT  null comment '답변내용'")
	private String replyContent;

	@Embedded
	private AuditSection2 auditSection = new AuditSection2();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRegName() {
		return regName;
	}

	public void setRegName(String regName) {
		this.regName = regName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getOpen() {
		return open;
	}

	public void setOpen(int open) {
		this.open = open;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getIprNation() {
		return iprNation;
	}

	public void setIprNation(String iprNation) {
		this.iprNation = iprNation;
	}

	public String getIprType() {
		return iprType;
	}

	public void setIprType(String iprType) {
		this.iprType = iprType;
	}

	public String getIprTitle() {
		return iprTitle;
	}

	public void setIprTitle(String iprTitle) {
		this.iprTitle = iprTitle;
	}

	public String getIprNo() {
		return iprNo;
	}

	public void setIprNo(String iprNo) {
		this.iprNo = iprNo;
	}

	public String getIprOwner() {
		return iprOwner;
	}

	public void setIprOwner(String iprOwner) {
		this.iprOwner = iprOwner;
	}

	public String getIprApplyDate() {
		return iprApplyDate;
	}

	public void setIprApplyDate(String iprApplyDate) {
		this.iprApplyDate = iprApplyDate;
	}

	public String getIprRegDate() {
		return iprRegDate;
	}

	public void setIprRegDate(String iprRegDate) {
		this.iprRegDate = iprRegDate;
	}

	public String getIprCompany() {
		return iprCompany;
	}

	public void setIprCompany(String iprCompany) {
		this.iprCompany = iprCompany;
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
