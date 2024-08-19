package com.salesmanager.shop.model.catalog;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.catalog.product.Material;
import com.salesmanager.core.model.common.description.Description;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


@Data
public class MaterialDescription extends NamedEntity implements
		Serializable {

	private static final long serialVersionUID = 1L;

	private String subName;

	private String subTitle;
}
