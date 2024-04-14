package com.salesmanager.shop.model.catalog.product.attribute;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Input object used when selecting an item option
 * @author carlsamson
 *
 */
public class ReadableProductVariantPrice implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;


  private Integer quantity;

  private String variantCode;

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public String getVariantCode() {
    return variantCode;
  }

  public void setVariantCode(String variantCode) {
    this.variantCode = variantCode;
  }
}
