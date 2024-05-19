package com.salesmanager.shop.model.common;

import java.io.Serializable;
import java.util.List;

public class ReadablePaginationResult<T> extends ReadablePagination implements Serializable {

    private int totalCount;

    private int totalPage;

    private List<T> data;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
