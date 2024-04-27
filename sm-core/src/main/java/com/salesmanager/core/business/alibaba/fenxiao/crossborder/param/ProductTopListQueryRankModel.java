package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class ProductTopListQueryRankModel {

    private String rankId;

    /**
     * @return 榜单ID
     */
    public String getRankId() {
        return rankId;
    }

    /**
     *     榜单ID     *
     * 参数示例：<pre>111</pre>     
     *    
     */
    public void setRankId(String rankId) {
        this.rankId = rankId;
    }

    private String rankName;

    /**
     * @return 榜单名称
     */
    public String getRankName() {
        return rankName;
    }

    /**
     *     榜单名称     *
     * 参数示例：<pre>Comprehensive List</pre>     
     *    
     */
    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    private String rankType;

    /**
     * @return 榜单类型
     */
    public String getRankType() {
        return rankType;
    }

    /**
     *     榜单类型     *
     * 参数示例：<pre>complex</pre>     
     *    
     */
    public void setRankType(String rankType) {
        this.rankType = rankType;
    }

    private ProductTopListQueryRankProductModel[] rankProductModels;

    /**
     * @return 榜单结果
     */
    public ProductTopListQueryRankProductModel[] getRankProductModels() {
        return rankProductModels;
    }

    /**
     *     榜单结果     *
     * 参数示例：<pre>如下</pre>     
     *    
     */
    public void setRankProductModels(ProductTopListQueryRankProductModel[] rankProductModels) {
        this.rankProductModels = rankProductModels;
    }

}
