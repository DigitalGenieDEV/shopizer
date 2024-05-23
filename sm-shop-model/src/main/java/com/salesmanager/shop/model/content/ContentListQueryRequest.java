package com.salesmanager.shop.model.content;

import java.io.Serializable;

public class ContentListQueryRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * name size
     */
    private String sortBy;
    /**
     *
     */
    private Boolean ascending;
    /**
     *
     */
    private String searchQueryName;

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public Boolean getAscending() {
        return ascending;
    }

    public void setAscending(Boolean ascending) {
        this.ascending = ascending;
    }

    public String getSearchQueryName() {
        return searchQueryName;
    }

    public void setSearchQueryName(String searchQueryName) {
        this.searchQueryName = searchQueryName;
    }
}
