package com.salesmanager.core.model.fulfillment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.common.description.Description;

import javax.persistence.*;

@Entity

@Table(name = "ADDITIONAL_SERVICES_DESC",
uniqueConstraints = {@UniqueConstraint(columnNames = { "ADDITIONAL_SERVICES_ID", "LANGUAGE_ID" })}
)

@TableGenerator(name = "description_gen", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME",
		valueColumnName = "SEQ_COUNT", pkColumnValue = "additional_services_description_seq",
		allocationSize = SchemaConstant.DESCRIPTION_ID_ALLOCATION_SIZE,
		initialValue = SchemaConstant.DESCRIPTION_ID_START_VALUE)
public class AddidtionalServicesDescription extends Description {
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@ManyToOne(targetEntity = AdditionalServices.class)
	@JoinColumn(name = "ADDITIONAL_SERVICES_ID", nullable = false)
	private AdditionalServices additionalServices;

	@Column(name="PRODUCT_OPTION_COMMENT", length=4000)
	private String productOptionComment;

	public AddidtionalServicesDescription() {
	}
	
	public String getProductOptionComment() {
		return productOptionComment;
	}
	public void setProductOptionComment(String productOptionComment) {
		this.productOptionComment = productOptionComment;
	}


	public AdditionalServices getAdditionalServices() {
		return additionalServices;
	}

	public void setAdditionalServices(AdditionalServices additionalServices) {
		this.additionalServices = additionalServices;
	}
}
