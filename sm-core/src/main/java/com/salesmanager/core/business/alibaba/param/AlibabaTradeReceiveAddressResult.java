package com.salesmanager.core.business.alibaba.param;

public class AlibabaTradeReceiveAddressResult {

    private AlibabaTradeReceiveAddressItem[] receiveAddressItems;

    /**
     * @return 收货地址列表
     */
    public AlibabaTradeReceiveAddressItem[] getReceiveAddressItems() {
        return receiveAddressItems;
    }

    /**
     * 设置收货地址列表     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setReceiveAddressItems(AlibabaTradeReceiveAddressItem[] receiveAddressItems) {
        this.receiveAddressItems = receiveAddressItems;
    }

}
