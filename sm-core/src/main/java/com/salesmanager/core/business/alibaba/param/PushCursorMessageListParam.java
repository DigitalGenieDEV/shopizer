package com.salesmanager.core.business.alibaba.param;

import com.salesmanager.core.business.alibaba.rawsdk.client.APIId;
import com.salesmanager.core.business.alibaba.rawsdk.common.AbstractAPIRequest;

import java.util.Date;

public class PushCursorMessageListParam extends AbstractAPIRequest<PushCursorMessageListResult> {

    public PushCursorMessageListParam() {
        super();
        oceanApiId = new APIId("cn.alibaba.open", "push.cursor.messageList", 1);
    }

    private Date createStartTime;

    /**
     * @return 消息创建时间查找范围开始
     */
    public Date getCreateStartTime() {
        return createStartTime;
    }

    /**
     *     消息创建时间查找范围开始     *
     * 参数示例：<pre>20130417000000000+0800</pre>     
     *    
     */
    public void setCreateStartTime(Date createStartTime) {
        this.createStartTime = createStartTime;
    }

    private Date createEndTime;

    /**
     * @return 消息创建时间查找范围结束
     */
    public Date getCreateEndTime() {
        return createEndTime;
    }

    /**
     *     消息创建时间查找范围结束     *
     * 参数示例：<pre>20130417000000000+0800</pre>     
     *    
     */
    public void setCreateEndTime(Date createEndTime) {
        this.createEndTime = createEndTime;
    }

    private Integer quantity;

    /**
     * @return 每次取的数据量，范围20-200，默认20
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     *     每次取的数据量，范围20-200，默认20     *
     * 参数示例：<pre>20</pre>     
     *    
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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
     * 参数示例：<pre>ORDER_BUYER_MAKER</pre>     
     *    
     */
    public void setType(String type) {
        this.type = type;
    }

    private String userInfo;

    /**
     * @return 用户Id
     */
    public String getUserInfo() {
        return userInfo;
    }

    /**
     *     用户Id     *
     * 参数示例：<pre>b2b-4137495171f2513</pre>     
     *    
     */
    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

}
