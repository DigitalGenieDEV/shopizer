package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class ComAlibabaCbuOfferModelPicRegionInfo {

    private String currentRegion;

    /**
     * @return 当前主体 x0,y0,x1,y1
     */
    public String getCurrentRegion() {
        return currentRegion;
    }

    /**
     *     当前主体 x0,y0,x1,y1     *
     * 参数示例：<pre>265,597,326,764</pre>     
     *    
     */
    public void setCurrentRegion(String currentRegion) {
        this.currentRegion = currentRegion;
    }

    private String yoloCropRegion;

    /**
     * @return 所有主体 x0,y0,x1,y1;x0,y0,x1,y1
     */
    public String getYoloCropRegion() {
        return yoloCropRegion;
    }

    /**
     *     所有主体 x0,y0,x1,y1;x0,y0,x1,y1     *
     * 参数示例：<pre>265,597,326,764;443,783,154,595</pre>     
     *    
     */
    public void setYoloCropRegion(String yoloCropRegion) {
        this.yoloCropRegion = yoloCropRegion;
    }

}
