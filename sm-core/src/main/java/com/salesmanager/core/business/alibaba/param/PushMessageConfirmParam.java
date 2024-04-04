package com.salesmanager.core.business.alibaba.param;


import com.salesmanager.core.business.alibaba.rawsdk.client.APIId;
import com.salesmanager.core.business.alibaba.rawsdk.common.AbstractAPIRequest;

import java.util.List;

public class PushMessageConfirmParam extends AbstractAPIRequest<PushMessageConfirmResult> {

    public PushMessageConfirmParam() {
        super();
        oceanApiId = new APIId("cn.alibaba.open", "push.message.confirm", 1);
    }

    private List msgIdList;

    /**
     * @return 待确认的消息id列表
     */
    public List getMsgIdList() {
        return msgIdList;
    }

    /**
     * 设置待确认的消息id列表     *
     * 参数示例：<pre>[123,456]</pre>     
     * 此参数必填
     */
    public void setMsgIdList(List msgIdList) {
        this.msgIdList = msgIdList;
    }

}
