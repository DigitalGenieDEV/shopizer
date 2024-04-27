package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class ProductSearchKeywordQueryModelPageInfoV {

    private Integer totalRecords;

    /**
     * @return 总条数
     */
    public Integer getTotalRecords() {
        return totalRecords;
    }

    /**
     *     总条数     *
     * 参数示例：<pre>分页</pre>     
     *
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
     *     总页码     *
     * 参数示例：<pre>分页</pre>     
     *
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
     *     分页     *
     * 参数示例：<pre>分页</pre>     
     *
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
     *     分页     *
     * 参数示例：<pre>分页</pre>     
     *
     */
    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    private ProductSearchKeywordQueryModelProductInfoModelV[] data;

    /**
     * @return 数据
     */
    public ProductSearchKeywordQueryModelProductInfoModelV[] getData() {
        return data;
    }

    /**
     *     数据     *
     * 参数示例：<pre>数据</pre>     
     *
     */
    public void setData(ProductSearchKeywordQueryModelProductInfoModelV[] data) {
        this.data = data;
    }

}
