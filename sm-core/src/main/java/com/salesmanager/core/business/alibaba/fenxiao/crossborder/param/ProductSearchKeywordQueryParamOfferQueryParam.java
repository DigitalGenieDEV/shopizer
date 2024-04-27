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
     *          *
     *        
     *
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
     *          *
     *        
     *
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
     *          *
     *        
     *
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
     *          *
     *        
     *
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
     *          *
     *        
     *
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
     *          *
     *        
     *
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
     *          *
     *        
     *
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
     *          *
     *        
     *
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
     *          *
     *        
     *
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
     *          *
     *        
     *
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
     *     国别     *
     * 参数示例：<pre>JP-OPP;KR-OPP</pre>     
     *
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
     *     寻源通工作台货盘id     *
     * 参数示例：<pre>174316138</pre>     
     *
     */
    public void setProductCollectionId(String productCollectionId) {
        this.productCollectionId = productCollectionId;
    }

}
