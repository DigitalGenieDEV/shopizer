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
     *     收货地址列表     *
     *        
     *
     */
    public void setReceiveAddressItems(AlibabaTradeReceiveAddressItem[] receiveAddressItems) {
        this.receiveAddressItems = receiveAddressItems;
    }

}
