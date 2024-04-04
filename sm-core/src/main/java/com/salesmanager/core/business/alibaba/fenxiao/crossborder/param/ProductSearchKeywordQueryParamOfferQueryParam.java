package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class ProductSearchKeywordQueryParamOfferQueryParam {

    private String keyword;

    /**
     * @return 
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    private Integer beginPage;

    /**
     * @return 
     */
    public Integer getBeginPage() {
        return beginPage;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setBeginPage(Integer beginPage) {
        this.beginPage = beginPage;
    }

    private Integer pageSize;

    /**
     * @return 
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    private String filter;

    /**
     * @return 
     */
    public String getFilter() {
        return filter;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setFilter(String filter) {
        this.filter = filter;
    }

    private String sort;

    /**
     * @return 
     */
    public String getSort() {
        return sort;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setSort(String sort) {
        this.sort = sort;
    }

    private String outMemberId;

    /**
     * @return 
     */
    public String getOutMemberId() {
        return outMemberId;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setOutMemberId(String outMemberId) {
        this.outMemberId = outMemberId;
    }

    private String priceStart;

    /**
     * @return 
     */
    public String getPriceStart() {
        return priceStart;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setPriceStart(String priceStart) {
        this.priceStart = priceStart;
    }

    private String priceEnd;

    /**
     * @return 
     */
    public String getPriceEnd() {
        return priceEnd;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setPriceEnd(String priceEnd) {
        this.priceEnd = priceEnd;
    }

    private Long categoryId;

    /**
     * @return 
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    private String country;

    /**
     * @return 
     */
    public String getCountry() {
        return country;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setCountry(String country) {
        this.country = country;
    }

    private String regionOpp;

    /**
     * @return 国别
     */
    public String getRegionOpp() {
        return regionOpp;
    }

    /**
     * 设置国别     *
     * 参数示例：<pre>JP-OPP;KR-OPP</pre>     
     * 此参数必填
     */
    public void setRegionOpp(String regionOpp) {
        this.regionOpp = regionOpp;
    }

    private String productCollectionId;

    /**
     * @return 寻源通工作台货盘id
     */
    public String getProductCollectionId() {
        return productCollectionId;
    }

    /**
     * 设置寻源通工作台货盘id     *
     * 参数示例：<pre>174316138</pre>     
     * 此参数必填
     */
    public void setProductCollectionId(String productCollectionId) {
        this.productCollectionId = productCollectionId;
    }

}
