package com.salesmanager.core.business.alibaba.param;

public class PushMessagePage {

    private PushMessage[] datas;

    /**
     * @return 分页的消息数据列表
     */
    public PushMessage[] getDatas() {
        return datas;
    }

    /**
     *     分页的消息数据列表     *
     *        
     *
     */
    public void setDatas(PushMessage[] datas) {
        this.datas = datas;
    }

    private Integer totalCount;

    /**
     * @return 消息总数
     */
    public Integer getTotalCount() {
        return totalCount;
    }

    /**
     *     消息总数     *
     *        
     *
     */
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

}
