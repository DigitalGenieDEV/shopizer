package com.salesmanager.core.model.adminmenu;

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
@Table(name = "ADMINMENU", indexes = @Index(columnList = "VISIBLE, MENU_NAME"), uniqueConstraints = @UniqueConstraint(columnNames = {
		"ID" }))
public class AdminMenu extends SalesManagerEntity<Integer, AdminMenu> implements Auditable2 {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "ADMINMENU_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Integer id;

	@Column(name = "PARENT_ID", nullable = false, length = 30, updatable = false)
	private Integer parentId;

	@Column(name = "MENU_NAME", nullable = false, length = 30)
	private String menuName;

	@Column(name = "MENU_NAME_EN", nullable = true, length = 100)
	private String menuNameEn;

	@Column(name = "MENU_NAME_CN", nullable = true, length = 100)
	private String menuNameCn;

	@Column(name = "MENU_NAME_JP", nullable = true, length = 100)
	private String menuNameJp;

	@Column(name = "MENU_DESC")
	private String menuDesc;

	@Column(name = "MENU_URL", nullable = false, length = 255)
	private String menuUrl;

	@Column(name = "API_URL", nullable = false, length = 255)
	private String apiUrl;

	@Column(name = "ORD", nullable = false, updatable = false)
	private Integer ord;

	@Column(name = "VISIBLE", nullable = false)
	private int visible;

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

	public String getMenuNameEn() {
		return menuNameEn;
	}

	public void setMenuNameEn(String menuNameEn) {
		this.menuNameEn = menuNameEn;
	}

	public String getMenuNameCn() {
		return menuNameCn;
	}

	public void setMenuNameCn(String menuNameCn) {
		this.menuNameCn = menuNameCn;
	}

	public String getMenuNameJp() {
		return menuNameJp;
	}

	public void setMenuNameJp(String menuNameJp) {
		this.menuNameJp = menuNameJp;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuDesc() {
		return menuDesc;
	}

	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getApiUrl() {
		return apiUrl;
	}

	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	public Integer getOrd() {
		return ord;
	}

	public void setOrd(Integer ord) {
		this.ord = ord;
	}

	public int getVisible() {
		return visible;
	}

	public void setVisible(int visible) {
		this.visible = visible;
	}

	public AuditSection2 getAuditSection() {
		return auditSection;
	}

	public void setAuditSection(AuditSection2 auditSection) {
		this.auditSection = auditSection;
	}

}
