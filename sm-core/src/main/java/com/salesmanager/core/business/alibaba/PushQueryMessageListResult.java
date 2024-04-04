package com.salesmanager.core.business.alibaba;


import com.salesmanager.core.business.alibaba.param.PushMessagePage;

public class PushQueryMessageListResult {

    private PushMessagePage pushMessagePage;

    /**
     * @return 分页数据
     */
    public PushMessagePage getPushMessagePage() {
        return pushMessagePage;
    }

    /**
     * 设置分页数据     *
          
     * 此参数必填
     */
    public void setPushMessagePage(PushMessagePage pushMessagePage) {
        this.pushMessagePage = pushMessagePage;
    }

}
