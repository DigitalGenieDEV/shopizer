package com.salesmanager.shop.model.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReadableAttrFiltAttrKv implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private ReadableAttrFiltKv attrName;

    private List<ReadableAttrFiltKv> attrValues = new ArrayList<>();


    public ReadableAttrFiltAttrKv() {

    }

    public ReadableAttrFiltAttrKv(ReadableAttrFiltKv attrName, List<ReadableAttrFiltKv> attrValues) {
        this.attrName = attrName;
        this.attrValues = attrValues;
    }

    public ReadableAttrFiltKv getAttrName() {
        return attrName;
    }

    public void setAttrName(ReadableAttrFiltKv attrName) {
        this.attrName = attrName;
    }

    public List<ReadableAttrFiltKv> getAttrValues() {
        return attrValues;
    }

    public void setAttrValues(List<ReadableAttrFiltKv> attrValues) {
        this.attrValues = attrValues;
    }
}
