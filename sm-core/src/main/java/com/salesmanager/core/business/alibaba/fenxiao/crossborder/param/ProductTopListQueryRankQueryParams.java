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
     *     榜单ID，可传入类目ID，目前支持类目榜单     *
     * 参数示例：<pre>1111</pre>     
     *
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
     *     榜单类型，complex综合榜，hot热卖榜，goodPrice好价榜     *
     * 参数示例：<pre>complex</pre>     
     *
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
     *     榜单商品个数，最多20     *
     * 参数示例：<pre>10</pre>     
     *
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
     *     榜单商品语言     *
     * 参数示例：<pre>en</pre>     
     *
     */
    public void setLanguage(String language) {
        this.language = language;
    }

}
