package com.salesmanager.shop.model.catalog;

import com.salesmanager.core.model.catalog.product.MaterialType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Material  {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long code;


    private Long subCode;


    private Long price;


    private Integer sort;


    private List<MaterialDescription> descriptions = new ArrayList<>();

    /**
     * @see MaterialType
     */
    private String type;




}