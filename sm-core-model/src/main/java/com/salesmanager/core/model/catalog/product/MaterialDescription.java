package com.salesmanager.core.model.catalog.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.common.description.Description;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity

@Table(name = "MATERIAL_DESC",
uniqueConstraints = {@UniqueConstraint(columnNames = { "MATERIAL_ID", "LANGUAGE_ID" })}

)

@TableGenerator(name = "description_gen", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "material_description_seq", allocationSize = SchemaConstant.DESCRIPTION_ID_ALLOCATION_SIZE, initialValue = SchemaConstant.DESCRIPTION_ID_START_VALUE)
public class MaterialDescription extends Description {
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@ManyToOne(targetEntity = Material.class)
	@JoinColumn(name = "MATERIAL_ID", nullable = false)
	private Material media;

	@Column(name="SUB_NAME", length=1266)
	private String subName;

	@Column(name="SUB_TITLE", length=1266)
	private String subTitle;

	public MaterialDescription() {
	}

	public Material getMedia() {
		return media;
	}

	public void setMedia(Material media) {
		this.media = media;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
}
