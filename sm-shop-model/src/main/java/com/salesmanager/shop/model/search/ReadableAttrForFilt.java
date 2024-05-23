package com.salesmanager.shop.model.search;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class ReadableAttrForFilt implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private List<ReadableAttrFiltKv> cate;

    @JsonProperty("prod_origin")
    private List<ReadableAttrFiltKv> prodOrigin;

    private List<ReadableAttrFiltKv> price;

    @JsonProperty("prod_type")
    private List<ReadableAttrFiltKv> prodType;

    private List<ReadableAttrFiltAttrKv> attrs;

    public List<ReadableAttrFiltKv> getCate() {
        return cate;
    }

    public void setCate(List<ReadableAttrFiltKv> cate) {
        this.cate = cate;
    }

    public List<ReadableAttrFiltKv> getProdOrigin() {
        return prodOrigin;
    }

    public void setProdOrigin(List<ReadableAttrFiltKv> prodOrigin) {
        this.prodOrigin = prodOrigin;
    }

    public List<ReadableAttrFiltKv> getPrice() {
        return price;
    }

    public void setPrice(List<ReadableAttrFiltKv> price) {
        this.price = price;
    }

    public List<ReadableAttrFiltKv> getProdType() {
        return prodType;
    }

    public void setProdType(List<ReadableAttrFiltKv> prodType) {
        this.prodType = prodType;
    }

    public List<ReadableAttrFiltAttrKv> getAttrs() {
        return attrs;
    }

    public void setAttrs(List<ReadableAttrFiltAttrKv> attrs) {
        this.attrs = attrs;
    }
}
