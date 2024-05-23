package com.salesmanager.shop.model.search;

import java.io.Serializable;

public class ReadableAttrFiltKv implements Serializable  {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String label;

    private Object value;

    public ReadableAttrFiltKv() {
    }

    public ReadableAttrFiltKv(String label, Object value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
