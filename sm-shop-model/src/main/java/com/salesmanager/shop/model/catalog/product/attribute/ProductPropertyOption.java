package com.salesmanager.shop.model.catalog.product.attribute;

import java.io.Serializable;
import java.util.List;

import com.salesmanager.shop.model.entity.Entity;


public class ProductPropertyOption extends Entity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private String code;
  private String type;
  private boolean readOnly;

  private List<ProductOptionDescription> descriptions ;

  public List<ProductOptionDescription> getDescriptions() {
    return descriptions;
  }

  public void setDescriptions(List<ProductOptionDescription> descriptions) {
    this.descriptions = descriptions;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public boolean isReadOnly() {
    return readOnly;
  }

  public void setReadOnly(boolean readOnly) {
    this.readOnly = readOnly;
  }

}