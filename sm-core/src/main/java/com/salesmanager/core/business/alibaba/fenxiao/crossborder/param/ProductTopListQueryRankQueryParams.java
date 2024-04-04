package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class ProductTopListQueryRankQueryParams {

    private String rankId;

    /**
     * @return 榜单ID，可传入类目ID，目前支持类目榜单
     */
    public String getRankId() {
        return rankId;
    }

    /**
     * 设置榜单ID，可传入类目ID，目前支持类目榜单     *
     * 参数示例：<pre>1111</pre>     
     * 此参数必填
     */
    public void setRankId(String rankId) {
        this.rankId = rankId;
    }

    private String rankType;

    /**
     * @return 榜单类型，complex综合榜，hot热卖榜，goodPrice好价榜
     */
    public String getRankType() {
        return rankType;
    }

    /**
     * 设置榜单类型，complex综合榜，hot热卖榜，goodPrice好价榜     *
     * 参数示例：<pre>complex</pre>     
     * 此参数必填
     */
    public void setRankType(String rankType) {
        this.rankType = rankType;
    }

    private Integer limit;

    /**
     * @return 榜单商品个数，最多20
     */
    public Integer getLimit() {
        return limit;
    }

    /**
     * 设置榜单商品个数，最多20     *
     * 参数示例：<pre>10</pre>     
     * 此参数必填
     */
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    private String language;

    /**
     * @return 榜单商品语言
     */
    public String getLanguage() {
        return language;
    }

    /**
     * 设置榜单商品语言     *
     * 参数示例：<pre>en</pre>     
     * 此参数必填
     */
    public void setLanguage(String language) {
        this.language = language;
    }

}
