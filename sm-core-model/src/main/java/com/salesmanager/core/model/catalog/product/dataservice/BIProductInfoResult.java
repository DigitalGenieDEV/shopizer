package com.salesmanager.core.model.catalog.product.dataservice;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BIProductInfoResult {

    @JsonProperty("category_level_1_count")
    private Integer categoryLevel1Count;

    @JsonProperty("category_level_2_count")
    private Integer categoryLevel2Count;

    @JsonProperty("category_level_3_count")
    private Integer categoryLevel3Count;

    @JsonProperty("leaf_category_count")
    private Integer leafCategoryCount;

    @JsonProperty("product_num_for_sale")
    private Integer productNumForSale;

    @JsonProperty("product_sku_num_for_sale")
    private Integer productSkuNumForSale;

    @JsonProperty("low_stock_product_sku_num")
    private Integer lowStockProductSkuNum;

    @JsonProperty("down_shelf_product_sku_num")
    private Integer downShelfProductSkuNum;


    public Integer getCategoryLevel1Count() {
        return categoryLevel1Count;
    }
    public void setCategoryLevel1Count(Integer categoryLevel1Count) {
        this.categoryLevel1Count = categoryLevel1Count;
    }

    public Integer getCategoryLevel2Count() {
        return categoryLevel2Count;
    }
    public void setCategoryLevel2Count(Integer categoryLevel2Count) {
        this.categoryLevel2Count = categoryLevel2Count;
    }

    public Integer getCategoryLevel3Count() {
        return categoryLevel3Count;
    }
    public void setCategoryLevel3Count(Integer categoryLevel3Count) {
        this.categoryLevel3Count = categoryLevel3Count;
    }

    public Integer getLeafCategoryCount() {
        return leafCategoryCount;
    }
    public void setLeafCategoryCount(Integer leafCategoryCount) {
        this.leafCategoryCount = leafCategoryCount;
    }

    public Integer getProductNumForSale() {
        return productNumForSale;
    }
    public void setProductNumForSale(Integer productNumForSale) {
        this.productNumForSale = productNumForSale;
    }

    public Integer getProductSkuNumForSale() {
        return productSkuNumForSale;
    }
    public void setProductSkuNumForSale(Integer productSkuNumForSale) { this.productSkuNumForSale = productSkuNumForSale; }

    public Integer getLowStockProductSkuNum() {
        return lowStockProductSkuNum;
    }
    public void setLowStockProductSkuNum(Integer lowStockProductSkuNum) { this.lowStockProductSkuNum = lowStockProductSkuNum; }

    public Integer getDownShelfProductSkuNum() {
        return downShelfProductSkuNum;
    }
    public void setDownShelfProductSkuNum(Integer downShelfProductSkuNum) { this.downShelfProductSkuNum = downShelfProductSkuNum; }
}
