package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class ProductSearchQuerySellerOfferListModelPageInfoV {

    private Integer totalRecords;

    /**
     * @return 总条数
     */
    public Integer getTotalRecords() {
        return totalRecords;
    }

    /**
     * 设置总条数     *
     * 参数示例：<pre>分页</pre>     
     * 此参数必填
     */
    public void setTotalRecords(Integer totalRecords) {
        this.totalRecords = totalRecords;
    }

    private Integer totalPage;

    /**
     * @return 总页码
     */
    public Integer getTotalPage() {
        return totalPage;
    }

    /**
     * 设置总页码     *
     * 参数示例：<pre>分页</pre>     
     * 此参数必填
     */
    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    private Integer pageSize;

    /**
     * @return 分页
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * 设置分页     *
     * 参数示例：<pre>分页</pre>     
     * 此参数必填
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    private Integer currentPage;

    /**
     * @return 分页
     */
    public Integer getCurrentPage() {
        return currentPage;
    }

    /**
     * 设置分页     *
     * 参数示例：<pre>分页</pre>     
     * 此参数必填
     */
    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    private ProductSearchQuerySellerOfferListModelProductInfoModelV[] data;

    /**
     * @return 数据
     */
    public ProductSearchQuerySellerOfferListModelProductInfoModelV[] getData() {
        return data;
    }

    /**
     * 设置数据     *
     * 参数示例：<pre>数据</pre>     
     * 此参数必填
     */
    public void setData(ProductSearchQuerySellerOfferListModelProductInfoModelV[] data) {
        this.data = data;
    }

}
