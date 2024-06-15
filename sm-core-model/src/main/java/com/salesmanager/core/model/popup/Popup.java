package com.salesmanager.core.model.popup;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;

import com.salesmanager.core.model.common.audit2.AuditSection2;
import com.salesmanager.core.model.common.audit2.Auditable2;
import com.salesmanager.core.model.generic.SalesManagerEntity;

@Entity
@EntityListeners(value = com.salesmanager.core.model.common.audit2.AuditListener2.class)
@Table(name = "POPUP", indexes = @Index(columnList = "SITE, NAME, SDATE, EDATE"), uniqueConstraints = @UniqueConstraint(columnNames = {
		"ID" }))
public class Popup extends SalesManagerEntity<Integer, Popup> implements Auditable2 {
	private static final long serialVersionUID = 1L;

	@Id
	@NotEmpty
	@Column(name = "ID", unique = true, nullable = false, updatable = false, columnDefinition = "INT(11) not null comment 'ID'")
	@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "POPUP_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Integer id;

	@NotEmpty
	@Column(name = "SITE", length = 1, nullable = false, updatable = true, columnDefinition = "varchar(1) not null comment '사이트'")
	private String site;

	@NotEmpty
	@Column(name = "TYPE", length = 1, nullable = false, updatable = true, columnDefinition = "varchar(1) not null comment '팝업유형'")
	private String type;

	@NotEmpty
	@Column(name = "NAME", length = 50, nullable = false, updatable = true, columnDefinition = "varchar(50) not null comment '팝업명'")
	private String name;

	@Column(name = "SDATE", length = 20, nullable = true, updatable = true, columnDefinition = "varchar(20)  null comment '시작일자'")
	private String sdate;

	@Column(name = "EDATE", length = 20, nullable = true, updatable = true, columnDefinition = "varchar(20)  null comment '종료일자'")
	private String edate;

	@Column(name = "URL", length = 100, nullable = true, updatable = true, columnDefinition = "varchar(100)  null comment 'URL'")
	private String url;

	@NotEmpty
	@Column(name = "TARGET", length = 1, nullable = true, updatable = true, columnDefinition = "varchar(1) not null comment '링크유형'")
	private String target;

	@Column(name = "IMAGE", length = 255, nullable = true, updatable = true, columnDefinition = "varchar(255) not null comment '이미지'")
	private String image;

	@Column(name = "POP_WIDTH", length = 11, nullable = true, updatable = true, columnDefinition = "INT(11) not null comment '팝업 넓이'")
	private int width = 0;

	@Column(name = "POP_HEIGHT", length = 11, nullable = true, updatable = true, columnDefinition = "INT(11) not null comment '팝업 높이'")
	private int height = 0;

	@Column(name = "POP_LEFT", length = 11, nullable = true, updatable = true, columnDefinition = "INT(11) not null comment '팝업 위치 LEFT'")
	private int left = 0;

	@Column(name = "POP_TOP", length = 11, nullable = true, updatable = true, columnDefinition = "INT(11) not null comment '팝업 위치 TOP'")
	private int top = 0;

	@Column(name = "ORD", length = 11, nullable = true, updatable = true, columnDefinition = "INT(11) not null comment '순서'")
	private int ord = 0;

	@Column(name = "VISIBLE", length = 11, nullable = true, updatable = true, columnDefinition = "INT(11) not null comment '사용여부'")
	private int visible = 0;

	@Column(name = "ALT", length = 100, nullable = true, updatable = true, columnDefinition = "varchar(100)  null comment '이미지 대체텍스트'")
	private String alt;

	@Embedded
	private AuditSection2 auditSection = new AuditSection2();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public int getOrd() {
		return ord;
	}

	public void setOrd(int ord) {
		this.ord = ord;
	}

	public int getVisible() {
		return visible;
	}

	public void setVisible(int visible) {
		this.visible = visible;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public AuditSection2 getAuditSection() {
		return auditSection;
	}

	public void setAuditSection(AuditSection2 auditSection) {
		this.auditSection = auditSection;
	}

}
