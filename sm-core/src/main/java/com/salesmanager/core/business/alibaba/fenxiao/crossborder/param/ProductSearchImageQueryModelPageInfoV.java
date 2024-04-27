package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class ProductSearchImageQueryModelPageInfoV {

    private Integer totalRecords;

    /**
     * @return 总数量
     */
    public Integer getTotalRecords() {
        return totalRecords;
    }

    /**
     *     总数量     *
     * 参数示例：<pre>1</pre>     
     *
     */
    public void setTotalRecords(Integer totalRecords) {
        this.totalRecords = totalRecords;
    }

    private Integer totalPage;

    /**
     * @return 总页数
     */
    public Integer getTotalPage() {
        return totalPage;
    }

    /**
     *     总页数     *
     * 参数示例：<pre>1</pre>     
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
     * 参数示例：<pre>1</pre>     
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
     * 参数示例：<pre>1</pre>     
     *
     */
    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    private ProductSearchImageQueryModelProductInfoModelV[] data;

    /**
     * @return 数据
     */
    public ProductSearchImageQueryModelProductInfoModelV[] getData() {
        return data;
    }

    /**
     *     数据     *
     * 参数示例：<pre>数据</pre>     
     *
     */
    public void setData(ProductSearchImageQueryModelProductInfoModelV[] data) {
        this.data = data;
    }

    private ComAlibabaCbuOfferModelPicRegionInfo picRegionInfo;

    /**
     * @return 主体信息
     */
    public ComAlibabaCbuOfferModelPicRegionInfo getPicRegionInfo() {
        return picRegionInfo;
    }

    /**
     *     主体信息     *
     * 参数示例：<pre>{"currentRegion":"265,597,326,764","yoloCropRegion":"265,597,326,764;443,783,154,595"}</pre>     
     *
     */
    public void setPicRegionInfo(ComAlibabaCbuOfferModelPicRegionInfo picRegionInfo) {
        this.picRegionInfo = picRegionInfo;
    }

}
