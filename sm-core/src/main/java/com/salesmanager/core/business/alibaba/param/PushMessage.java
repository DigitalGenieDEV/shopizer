package com.salesmanager.core.business.alibaba.param;

import java.util.Map;

public class PushMessage {

    private Long msgId;

    /**
     * @return 消息唯一id
     */
    public Long getMsgId() {
        return msgId;
    }

    /**
     *     消息唯一id     *
     *        
     *
     */
    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    private String type;

    /**
     * @return 消息类型
     */
    public String getType() {
        return type;
    }

    /**
     *     消息类型     *
     *        
     *
     */
    public void setType(String type) {
        this.type = type;
    }

    private String userInfo;

    /**
     * @return 消息关联的用户memberId
     */
    public String getUserInfo() {
        return userInfo;
    }

    /**
     *     消息关联的用户memberId     *
     *        
     *
     */
    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    private Map data;

    /**
     * @return 消息内容
     */
    public Map getData() {
        return data;
    }

    /**
     *     消息内容     *
     *        
     *
     */
    public void setData(Map data) {
        this.data = data;
    }

    private Long gmtBorn;

    /**
     * @return 消息创建的时间戳，单位毫秒
     */
    public Long getGmtBorn() {
        return gmtBorn;
    }

    /**
     *     消息创建的时间戳，单位毫秒     *
     *        
     *
     */
    public void setGmtBorn(Long gmtBorn) {
        this.gmtBorn = gmtBorn;
    }

}
