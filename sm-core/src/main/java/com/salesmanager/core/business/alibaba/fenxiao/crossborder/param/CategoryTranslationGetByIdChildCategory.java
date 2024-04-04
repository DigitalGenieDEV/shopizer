package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class CategoryTranslationGetByIdChildCategory {

    private Long categoryId;

    /**
     * @return 
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    private String chineseName;

    /**
     * @return 
     */
    public String getChineseName() {
        return chineseName;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    private String translatedName;

    /**
     * @return 
     */
    public String getTranslatedName() {
        return translatedName;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setTranslatedName(String translatedName) {
        this.translatedName = translatedName;
    }

    private String language;

    /**
     * @return 
     */
    public String getLanguage() {
        return language;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    private Boolean leaf;

    /**
     * @return 
     */
    public Boolean getLeaf() {
        return leaf;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    private String level;

    /**
     * @return 
     */
    public String getLevel() {
        return level;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setLevel(String level) {
        this.level = level;
    }

    private Long parentCateId;

    /**
     * @return 
     */
    public Long getParentCateId() {
        return parentCateId;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setParentCateId(Long parentCateId) {
        this.parentCateId = parentCateId;
    }

    private Boolean fromCache;

    /**
     * @return 
     */
    public Boolean getFromCache() {
        return fromCache;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setFromCache(Boolean fromCache) {
        this.fromCache = fromCache;
    }

}
