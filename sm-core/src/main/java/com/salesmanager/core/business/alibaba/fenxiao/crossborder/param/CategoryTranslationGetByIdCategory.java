package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class CategoryTranslationGetByIdCategory {

    private Long categoryId;

    /**
     * @return 
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     *          *
     *        
     *    
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
     *          *
     *        
     *    
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
     *          *
     *        
     *    
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
     *          *
     *        
     *    
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    private String leaf;

    /**
     * @return 
     */
    public String getLeaf() {
        return leaf;
    }

    /**
     *          *
     *        
     *    
     */
    public void setLeaf(String leaf) {
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
     *          *
     *        
     *    
     */
    public void setLevel(String level) {
        this.level = level;
    }

    private String parentCateId;

    /**
     * @return 
     */
    public String getParentCateId() {
        return parentCateId;
    }

    /**
     *          *
     *        
     *    
     */
    public void setParentCateId(String parentCateId) {
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
     *          *
     *        
     *    
     */
    public void setFromCache(Boolean fromCache) {
        this.fromCache = fromCache;
    }

    private CategoryTranslationGetByIdChildCategory[] children;

    /**
     * @return 
     */
    public CategoryTranslationGetByIdChildCategory[] getChildren() {
        return children;
    }

    /**
     *          *
     *        
     *    
     */
    public void setChildren(CategoryTranslationGetByIdChildCategory[] children) {
        this.children = children;
    }

}
