package com.salesmanager.core.business.alibaba.param;

public class AlibabaTradeCrossPeriod {

    private Integer tapType;

    /**
     * @return 账期的类型,1：一个月指定日期结算一次，3：两个月指定日期结算一次，6：三个月指定日期结算一次，5：按收货时间和账期日期结算
     */
    public Integer getTapType() {
        return tapType;
    }

    /**
     *     账期的类型,1：一个月指定日期结算一次，3：两个月指定日期结算一次，6：三个月指定日期结算一次，5：按收货时间和账期日期结算     *
     * 参数示例：<pre>1</pre>     
     *
     */
    public void setTapType(Integer tapType) {
        this.tapType = tapType;
    }

    private Integer tapDate;

    /**
     * @return 根据账期类型不同而不同，按月结算类型此值代表具体某日，按收货时间结算时此值代表结算时间周期
     */
    public Integer getTapDate() {
        return tapDate;
    }

    /**
     *     根据账期类型不同而不同，按月结算类型此值代表具体某日，按收货时间结算时此值代表结算时间周期     *
     * 参数示例：<pre>12</pre>     
     *
     */
    public void setTapDate(Integer tapDate) {
        this.tapDate = tapDate;
    }

    private Integer tapOverdue;

    /**
     * @return 逾期次数
     */
    public Integer getTapOverdue() {
        return tapOverdue;
    }

    /**
     *     逾期次数     *
     * 参数示例：<pre>0</pre>     
     *
     */
    public void setTapOverdue(Integer tapOverdue) {
        this.tapOverdue = tapOverdue;
    }

}
