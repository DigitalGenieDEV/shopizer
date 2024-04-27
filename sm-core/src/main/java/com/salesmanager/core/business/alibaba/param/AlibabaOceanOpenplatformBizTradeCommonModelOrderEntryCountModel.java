package com.salesmanager.core.business.alibaba.param;

public class AlibabaOceanOpenplatformBizTradeCommonModelOrderEntryCountModel {

    private Long id;

    /**
     * @return 子订单id
     */
    public Long getId() {
        return id;
    }

    /**
     *     子订单id     *
     * 参数示例：<pre>1</pre>     
     *    
     */
    public void setId(Long id) {
        this.id = id;
    }

    private Integer count;

    /**
     * @return 子订单购买商品数量
     */
    public Integer getCount() {
        return count;
    }

    /**
     *     子订单购买商品数量     *
     * 参数示例：<pre>1</pre>     
     *    
     */
    public void setCount(Integer count) {
        this.count = count;
    }

}
