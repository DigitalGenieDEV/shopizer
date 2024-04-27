package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class CategoryTranslationGetByKeywordCategory {

    private Long categoryId;

    /**
     * @return 类目ID
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     *     类目ID     *
     * 参数示例：<pre>1031910</pre>     
     *
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    private String chineseName;

    /**
     * @return 类目中文名称
     */
    public String getChineseName() {
        return chineseName;
    }

    /**
     *     类目中文名称     *
     * 参数示例：<pre>连衣裙</pre>     
     *
     */
    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    private String translatedName;

    /**
     * @return 类目翻译名称
     */
    public String getTranslatedName() {
        return translatedName;
    }

    /**
     *     类目翻译名称     *
     * 参数示例：<pre>ワンピース</pre>     
     *
     */
    public void setTranslatedName(String translatedName) {
        this.translatedName = translatedName;
    }

    private String language;

    /**
     * @return 语种
     */
    public String getLanguage() {
        return language;
    }

    /**
     *     语种     *
     * 参数示例：<pre>ja</pre>     
     *
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    private Boolean leaf;

    /**
     * @return 是否叶子类目
     */
    public Boolean getLeaf() {
        return leaf;
    }

    /**
     *     是否叶子类目     *
     * 参数示例：<pre>true</pre>     
     *
     */
    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    private String level;

    /**
     * @return 类目层级
     */
    public String getLevel() {
        return level;
    }

    /**
     *     类目层级     *
     * 参数示例：<pre>2</pre>     
     *
     */
    public void setLevel(String level) {
        this.level = level;
    }

    private Long parentCateId;

    /**
     * @return 上层类目ID
     */
    public Long getParentCateId() {
        return parentCateId;
    }

    /**
     *     上层类目ID     *
     * 参数示例：<pre>10166</pre>     
     *
     */
    public void setParentCateId(Long parentCateId) {
        this.parentCateId = parentCateId;
    }

}
