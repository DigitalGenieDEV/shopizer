package com.salesmanager.core.business.alibaba.param;

public class AlibabaTradeRefundOpQueryOrderRefundListResult {

    private AlibabaTradeRefundOpOrderRefundModel[] opOrderRefundModels;

    /**
     * @return 退款单列表
     */
    public AlibabaTradeRefundOpOrderRefundModel[] getOpOrderRefundModels() {
        return opOrderRefundModels;
    }

    /**
     *     退款单列表     *
     *        
     *
     */
    public void setOpOrderRefundModels(AlibabaTradeRefundOpOrderRefundModel[] opOrderRefundModels) {
        this.opOrderRefundModels = opOrderRefundModels;
    }

    private Integer totalCount;

    /**
     * @return 符合条件总的记录条数
     */
    public Integer getTotalCount() {
        return totalCount;
    }

    /**
     *     符合条件总的记录条数     *
     *        
     *
     */
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    private Integer currentPageNum;

    /**
     * @return 查询的当前页码
     */
    public Integer getCurrentPageNum() {
        return currentPageNum;
    }

    /**
     *     查询的当前页码     *
     *        
     *
     */
    public void setCurrentPageNum(Integer currentPageNum) {
        this.currentPageNum = currentPageNum;
    }

}
