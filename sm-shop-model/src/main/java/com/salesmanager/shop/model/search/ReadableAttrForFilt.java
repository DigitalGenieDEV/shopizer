package com.salesmanager.shop.model.search;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class ReadableAttrForFilt implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private List<ReadableAttrFiltKv> category;

    @JsonProperty("product_origin")
    private List<ReadableAttrFiltKv> productOrigin;

    private List<ReadableAttrFiltKv> price;

    @JsonProperty("product_type")
    private List<ReadableAttrFiltKv> productType;

    private List<ReadableAttrFiltAttrKv> attrs;

    public List<ReadableAttrFiltKv> getCategory() {
        return category;
    }

    public void setCategory(List<ReadableAttrFiltKv> category) {
        this.category = category;
    }

    public List<ReadableAttrFiltKv> getProductOrigin() {
        return productOrigin;
    }

    public void setProductOrigin(List<ReadableAttrFiltKv> productOrigin) {
        this.productOrigin = productOrigin;
    }

    public List<ReadableAttrFiltKv> getPrice() {
        return price;
    }

    public void setPrice(List<ReadableAttrFiltKv> price) {
        this.price = price;
    }

    public List<ReadableAttrFiltKv> getProductType() {
        return productType;
    }

    public void setProductType(List<ReadableAttrFiltKv> productType) {
        this.productType = productType;
    }

    public List<ReadableAttrFiltAttrKv> getAttrs() {
        return attrs;
    }

    public void setAttrs(List<ReadableAttrFiltAttrKv> attrs) {
        this.attrs = attrs;
    }
}
