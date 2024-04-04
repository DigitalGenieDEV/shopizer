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
     * 设置商品池ID     *
     * 参数示例：<pre>23423</pre>     
     * 此参数必填
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
     * 设置类目ID     *
     * 参数示例：<pre>324234</pre>     
     * 此参数必填
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
     * 设置查询任务ID     *
     * 参数示例：<pre>1</pre>     
     * 此参数必填
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
     * 设置语言     *
     * 参数示例：<pre>en</pre>     
     * 此参数必填
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
     * 设置页码     *
     * 参数示例：<pre>1</pre>     
     * 此参数必填
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
     * 设置每页数量     *
     * 参数示例：<pre>10</pre>     
     * 此参数必填
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
     * 设置机构appKey     *
     * 参数示例：<pre>1</pre>     
     * 此参数必填
     */
    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

}
