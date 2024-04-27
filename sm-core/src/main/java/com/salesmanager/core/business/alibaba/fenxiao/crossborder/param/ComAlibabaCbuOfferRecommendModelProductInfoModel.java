package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class ComAlibabaCbuOfferRecommendModelProductInfoModel {

    private String imageUrl;

    /**
     * @return 图片地址
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     *     图片地址     *
     * 参数示例：<pre>https://global-img-cdn.1688.com/img/ibank/O1CN01nwtZUD1iKV0m6pNWP_!!3329334394-0-cib.jpg</pre>     
     *    
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private String subject;

    /**
     * @return 中文标题
     */
    public String getSubject() {
        return subject;
    }

    /**
     *     中文标题     *
     * 参数示例：<pre>包邮火龙果色阔腿裤女春秋季2022年新款高腰垂感显瘦提花加长直筒</pre>     
     *    
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    private String subjectTrans;

    /**
     * @return 译文标题
     */
    public String getSubjectTrans() {
        return subjectTrans;
    }

    /**
     *     译文标题     *
     * 参数示例：<pre>Postage Dragon Fruit Color Wide Leg Pants Women's Spring and Autumn 2022 New High Waist Draping Slim Jacquard Lengthened Straight</pre>     
     *    
     */
    public void setSubjectTrans(String subjectTrans) {
        this.subjectTrans = subjectTrans;
    }

    private ComAlibabaCbuOfferModelPriceInfo priceInfo;

    /**
     * @return 价格
     */
    public ComAlibabaCbuOfferModelPriceInfo getPriceInfo() {
        return priceInfo;
    }

    /**
     *     价格     *
     * 参数示例：<pre>{"price":"200","consignPrice":"1002"}</pre>     
     *    
     */
    public void setPriceInfo(ComAlibabaCbuOfferModelPriceInfo priceInfo) {
        this.priceInfo = priceInfo;
    }

    private Long offerId;

    /**
     * @return 商品id
     */
    public Long getOfferId() {
        return offerId;
    }

    /**
     *     商品id     *
     * 参数示例：<pre>691124939009</pre>     
     *    
     */
    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    private Integer monthSold;

    /**
     * @return 月销量
     */
    public Integer getMonthSold() {
        return monthSold;
    }

    /**
     *     月销量     *
     * 参数示例：<pre>200</pre>     
     *    
     */
    public void setMonthSold(Integer monthSold) {
        this.monthSold = monthSold;
    }

    private String repurchaseRate;

    /**
     * @return 复购率
     */
    public String getRepurchaseRate() {
        return repurchaseRate;
    }

    /**
     *     复购率     *
     * 参数示例：<pre>20%</pre>     
     *    
     */
    public void setRepurchaseRate(String repurchaseRate) {
        this.repurchaseRate = repurchaseRate;
    }

    private String traceInfo;

    /**
     * @return 打点信息
     */
    public String getTraceInfo() {
        return traceInfo;
    }

    /**
     *     打点信息     *
     * 参数示例：<pre>12eqweqweqwe1212</pre>     
     *    
     */
    public void setTraceInfo(String traceInfo) {
        this.traceInfo = traceInfo;
    }

}
