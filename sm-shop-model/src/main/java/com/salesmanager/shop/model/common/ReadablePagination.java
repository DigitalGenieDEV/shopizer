package com.salesmanager.shop.model.common;

import java.io.Serializable;

public class ReadablePagination implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    protected int pageSize = 10;

    protected int pageNum = 1;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
}
