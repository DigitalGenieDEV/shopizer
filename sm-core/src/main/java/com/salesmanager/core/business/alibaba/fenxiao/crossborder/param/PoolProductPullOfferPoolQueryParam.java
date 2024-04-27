package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class PoolProductPullOfferPoolQueryParam {

    private Long offerPoolId;

    /**
     * @return 商品池ID
     */
    public Long getOfferPoolId() {
        return offerPoolId;
    }

    /**
     *     商品池ID     *
     * 参数示例：<pre>23423</pre>     
     *
     */
    public void setOfferPoolId(Long offerPoolId) {
        this.offerPoolId = offerPoolId;
    }

    private Long cateId;

    /**
     * @return 类目ID
     */
    public Long getCateId() {
        return cateId;
    }

    /**
     *     类目ID     *
     * 参数示例：<pre>324234</pre>     
     *
     */
    public void setCateId(Long cateId) {
        this.cateId = cateId;
    }

    private String taskId;

    /**
     * @return 查询任务ID
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     *     查询任务ID     *
     * 参数示例：<pre>1</pre>     
     *
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    private String language;

    /**
     * @return 语言
     */
    public String getLanguage() {
        return language;
    }

    /**
     *     语言     *
     * 参数示例：<pre>en</pre>     
     *
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    private Integer pageNo;

    /**
     * @return 页码
     */
    public Integer getPageNo() {
        return pageNo;
    }

    /**
     *     页码     *
     * 参数示例：<pre>1</pre>     
     *
     */
    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    private Integer pageSize;

    /**
     * @return 每页数量
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     *     每页数量     *
     * 参数示例：<pre>10</pre>     
     *
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    private String appKey;

    /**
     * @return 机构appKey
     */
    public String getAppKey() {
        return appKey;
    }

    /**
     *     机构appKey     *
     * 参数示例：<pre>1</pre>     
     *
     */
    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

}
