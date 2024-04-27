package com.salesmanager.core.business.alibaba.param;

import java.util.Date;

public class AlibabaOrderRateDetail {

    private Integer starLevel;

    /**
     * @return 评价星级
     */
    public Integer getStarLevel() {
        return starLevel;
    }

    /**
     *     评价星级     *
     *        
     *
     */
    public void setStarLevel(Integer starLevel) {
        this.starLevel = starLevel;
    }

    private String content;

    /**
     * @return 评价详情
     */
    public String getContent() {
        return content;
    }

    /**
     *     评价详情     *
     *        
     *
     */
    public void setContent(String content) {
        this.content = content;
    }

    private String receiverNick;

    /**
     * @return 收到评价的用户昵称
     */
    public String getReceiverNick() {
        return receiverNick;
    }

    /**
     *     收到评价的用户昵称     *
     *        
     *
     */
    public void setReceiverNick(String receiverNick) {
        this.receiverNick = receiverNick;
    }

    private String posterNick;

    /**
     * @return 发送评价的用户昵称
     */
    public String getPosterNick() {
        return posterNick;
    }

    /**
     *     发送评价的用户昵称     *
     *        
     *
     */
    public void setPosterNick(String posterNick) {
        this.posterNick = posterNick;
    }

    private Date publishTime;

    /**
     * @return 评价上线时间
     */
    public Date getPublishTime() {
        return publishTime;
    }

    /**
     *     评价上线时间     *
     *        
     *
     */
    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

}
