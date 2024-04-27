package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class ProductSearchQuerySellerOfferListParamOfferQueryParam {

    private String keyword;

    /**
     * @return 关键词
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     *     关键词     *
     * 参数示例：<pre>饼干</pre>     
     *
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    private Integer beginPage;

    /**
     * @return 分页
     */
    public Integer getBeginPage() {
        return beginPage;
    }

    /**
     *     分页     *
     * 参数示例：<pre>1</pre>     
     *
     */
    public void setBeginPage(Integer beginPage) {
        this.beginPage = beginPage;
    }

    private Integer pageSize;

    /**
     * @return 分页
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     *     分页     *
     * 参数示例：<pre>1</pre>     
     *
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    private String filter;

    /**
     * @return 筛选参数，多个通过英文逗号分隔，枚举参见解决方案介绍
     */
    public String getFilter() {
        return filter;
    }

    /**
     *     筛选参数，多个通过英文逗号分隔，枚举参见解决方案介绍     *
     * 参数示例：<pre>shipInToday,ksCiphertext</pre>     
     *
     */
    public void setFilter(String filter) {
        this.filter = filter;
    }

    private String sort;

    /**
     * @return 排序参数，枚举参见解决方案介绍
     */
    public String getSort() {
        return sort;
    }

    /**
     *     排序参数，枚举参见解决方案介绍     *
     * 参数示例：<pre>{"price":"asc"}</pre>     
     *
     */
    public void setSort(String sort) {
        this.sort = sort;
    }

    private String outMemberId;

    /**
     * @return 外部用户id
     */
    public String getOutMemberId() {
        return outMemberId;
    }

    /**
     *     外部用户id     *
     * 参数示例：<pre>123</pre>     
     *
     */
    public void setOutMemberId(String outMemberId) {
        this.outMemberId = outMemberId;
    }

    private String priceStart;

    /**
     * @return 批发价开始
     */
    public String getPriceStart() {
        return priceStart;
    }

    /**
     *     批发价开始     *
     * 参数示例：<pre>1</pre>     
     *
     */
    public void setPriceStart(String priceStart) {
        this.priceStart = priceStart;
    }

    private String priceEnd;

    /**
     * @return 批发价结束
     */
    public String getPriceEnd() {
        return priceEnd;
    }

    /**
     *     批发价结束     *
     * 参数示例：<pre>1</pre>     
     *
     */
    public void setPriceEnd(String priceEnd) {
        this.priceEnd = priceEnd;
    }

    private Long categoryId;

    /**
     * @return 类目id
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     *     类目id     *
     * 参数示例：<pre>1</pre>     
     *
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    private String country;

    /**
     * @return 城市
     */
    public String getCountry() {
        return country;
    }

    /**
     *     城市     *
     * 参数示例：<pre>japan</pre>     
     *
     */
    public void setCountry(String country) {
        this.country = country;
    }

    private String sellerId;

    /**
     * @return 商家店铺id脱敏
     */
    public String getSellerId() {
        return sellerId;
    }

    /**
     *     商家店铺id脱敏     *
     * 参数示例：<pre>123</pre>     
     *
     */
    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    private String sellerOpenId;

    /**
     * @return 商家店铺id脱敏
     */
    public String getSellerOpenId() {
        return sellerOpenId;
    }

    /**
     *     商家店铺id脱敏     *
     * 参数示例：<pre>123</pre>     
     *
     */
    public void setSellerOpenId(String sellerOpenId) {
        this.sellerOpenId = sellerOpenId;
    }

    private Long offerId;

    /**
     * @return 商品Id
     */
    public Long getOfferId() {
        return offerId;
    }

    /**
     *     商品Id     *
     * 参数示例：<pre>123</pre>     
     *
     */
    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

}
