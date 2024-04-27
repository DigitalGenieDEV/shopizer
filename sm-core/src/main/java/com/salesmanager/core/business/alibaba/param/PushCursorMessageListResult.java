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
     *     推送消息列表     *
          
     *    
     */
    public void setPushMessageList(PushMessage[] pushMessageList) {
        this.pushMessageList = pushMessageList;
    }

}
