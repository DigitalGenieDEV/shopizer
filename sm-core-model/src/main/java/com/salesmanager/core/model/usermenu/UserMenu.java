package com.salesmanager.core.model.usermenu;

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
@Table(name = "UserMenu", indexes = @Index(columnList = "VISIBLE, MENU_NAME"), uniqueConstraints = @UniqueConstraint(columnNames = {
		"ID" }))
public class UserMenu extends SalesManagerEntity<Integer, UserMenu> implements Auditable2 {
	private static final long serialVersionUID = 1L;

	@Id
	@NotEmpty
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "USERMENU_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Integer id;

	@NotEmpty
	@Column(name = "PARENT_ID", nullable = false, length = 30, updatable = false)
	private Integer parentId;

	@NotEmpty
	@Column(name = "MENU_NAME", nullable = false, length = 100)
	private String menuName;

	@Column(name = "TOP", length = 1, updatable = true)
	private String top;

	@Column(name = "SIDE", length = 1, updatable = true)
	private String side;

	@Column(name = "NAVI", length = 1, updatable = true)
	private String navi;

	@Column(name = "TAB", length = 1, updatable = true)
	private String tab;

	@NotEmpty
	@Column(name = "URL", nullable = false, length = 255)
	private String url;

	@Column(name = "MEMBER_TARGET", length = 30, updatable = true)
	private String memberTarget;

	@Column(name = "LINK_TARGET", length = 1, updatable = true)
	private String linkTarget;

	@Column(name = "MENU_DESC", length = 1000, updatable = true)
	private String menuDesc;

	@NotEmpty
	@Column(name = "ORD", nullable = false, updatable = false)
	private Integer ord;

	@NotEmpty
	@Column(name = "VISIBLE", nullable = false)
	private Integer visible;

	@Embedded
	private AuditSection2 auditSection = new AuditSection2();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getTop() {
		return top;
	}

	public void setTop(String top) {
		this.top = top;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public String getNavi() {
		return navi;
	}

	public void setNavi(String navi) {
		this.navi = navi;
	}

	public String getTab() {
		return tab;
	}

	public void setTab(String tab) {
		this.tab = tab;
	}

	public String getMemberTarget() {
		return memberTarget;
	}

	public void setMemberTarget(String memberTarget) {
		this.memberTarget = memberTarget;
	}

	public String getLinkTarget() {
		return linkTarget;
	}

	public void setLinkTarget(String linkTarget) {
		this.linkTarget = linkTarget;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMenuDesc() {
		return menuDesc;
	}

	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}

	public Integer getOrd() {
		return ord;
	}

	public void setOrd(Integer ord) {
		this.ord = ord;
	}

	public Integer getVisible() {
		return visible;
	}

	public void setVisible(Integer visible) {
		this.visible = visible;
	}

	public AuditSection2 getAuditSection() {
		return auditSection;
	}

	public void setAuditSection(AuditSection2 auditSection) {
		this.auditSection = auditSection;
	}

}
