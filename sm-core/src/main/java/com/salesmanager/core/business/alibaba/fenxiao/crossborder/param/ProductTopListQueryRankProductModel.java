package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class ProductTopListQueryRankProductModel {

    private Long itemId;

    /**
     * @return 商品ID
     */
    public Long getItemId() {
        return itemId;
    }

    /**
     *     商品ID     *
     * 参数示例：<pre>699490252651</pre>     
     *
     */
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    private String title;

    /**
     * @return 商品中文标题
     */
    public String getTitle() {
        return title;
    }

    /**
     *     商品中文标题     *
     * 参数示例：<pre>2023厚底女士凉鞋软底拖鞋夏季现货踩屎感注塑鞋出口家居防滑凉鞋</pre>     
     *
     */
    public void setTitle(String title) {
        this.title = title;
    }

    private String translateTitle;

    /**
     * @return 商品译文标题
     */
    public String getTranslateTitle() {
        return translateTitle;
    }

    /**
     *     商品译文标题     *
     * 参数示例：<pre>2023 thick-soled ladies sandals soft-soled slippers summer spot poop injection shoes export home non-slip sandals</pre>     
     *
     */
    public void setTranslateTitle(String translateTitle) {
        this.translateTitle = translateTitle;
    }

    private String imgUrl;

    /**
     * @return 商品图片
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     *     商品图片     *
     * 参数示例：<pre>http://img.china.alibaba.com/img/ibank/O1CN01p4SIPo1D6Wx4c0xs8_!!2201053890167-0-cib.search.jpg</pre>     
     *
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    private Integer sort;

    /**
     * @return 商品排行
     */
    public Integer getSort() {
        return sort;
    }

    /**
     *     商品排行     *
     * 参数示例：<pre>1</pre>     
     *
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    private String[] serviceList;

    /**
     * @return 商品包含的服务，24小时发货sendGoods24H，48小时发货sendGoods48H
     */
    public String[] getServiceList() {
        return serviceList;
    }

    /**
     *     商品包含的服务，24小时发货sendGoods24H，48小时发货sendGoods48H     *
     * 参数示例：<pre>["sendGoods48H"]</pre>     
     *
     */
    public void setServiceList(String[] serviceList) {
        this.serviceList = serviceList;
    }

    private Integer buyerNum;

    /**
     * @return 最近30天买家数
     */
    public Integer getBuyerNum() {
        return buyerNum;
    }

    /**
     *     最近30天买家数     *
     * 参数示例：<pre>1334</pre>     
     *
     */
    public void setBuyerNum(Integer buyerNum) {
        this.buyerNum = buyerNum;
    }

    private Integer soldOut;

    /**
     * @return 最近30天商品售卖件数
     */
    public Integer getSoldOut() {
        return soldOut;
    }

    /**
     *     最近30天商品售卖件数     *
     * 参数示例：<pre>433454</pre>     
     *
     */
    public void setSoldOut(Integer soldOut) {
        this.soldOut = soldOut;
    }

}
