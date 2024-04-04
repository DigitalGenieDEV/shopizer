package com.salesmanager.core.business.alibaba.param;

public class PushCursorMessageListResult {

    private PushMessage[] pushMessageList;

    /**
     * @return 推送消息列表
     */
    public PushMessage[] getPushMessageList() {
        return pushMessageList;
    }

    /**
     * 设置推送消息列表     *
          
     * 此参数必填
     */
    public void setPushMessageList(PushMessage[] pushMessageList) {
        this.pushMessageList = pushMessageList;
    }

}
