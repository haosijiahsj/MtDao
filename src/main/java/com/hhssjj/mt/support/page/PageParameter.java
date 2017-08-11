package com.hhssjj.mt.support.page;

/**
 * Created by 胡胜钧 on 8/8 0008.
 */
public class PageParameter {
    private int pageSize = 20;
    private int pageNumber = 1;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
}
