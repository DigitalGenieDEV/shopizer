package com.salesmanager.shop.model.catalog;

import com.salesmanager.core.model.catalog.product.MaterialType;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class ReadableMaterial extends Material{

    private MaterialDescription description;

}