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
     * 设置总数量     *
     * 参数示例：<pre>1</pre>     
     * 此参数必填
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
     * 设置总页数     *
     * 参数示例：<pre>1</pre>     
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
     * 参数示例：<pre>1</pre>     
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
     * 参数示例：<pre>1</pre>     
     * 此参数必填
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
     * 设置数据     *
     * 参数示例：<pre>数据</pre>     
     * 此参数必填
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
     * 设置主体信息     *
     * 参数示例：<pre>{"currentRegion":"265,597,326,764","yoloCropRegion":"265,597,326,764;443,783,154,595"}</pre>     
     * 此参数必填
     */
    public void setPicRegionInfo(ComAlibabaCbuOfferModelPicRegionInfo picRegionInfo) {
        this.picRegionInfo = picRegionInfo;
    }

}
