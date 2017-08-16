package com.zzz.mt.support.page;

/**
 * Created by 胡胜钧 on 8/8 0008.
 */
public class PageResult<T> {
    private int pageSize;
    private int pageNumber;
    private long totalPageSize;
    private long totalRowSize;
    private T content;

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

    public long getTotalPageSize() {
        return totalPageSize;
    }

    public void setTotalPageSize(long totalPageSize) {
        this.totalPageSize = totalPageSize;
    }

    public long getTotalRowSize() {
        return totalRowSize;
    }

    public void setTotalRowSize(long totalRowSize) {
        this.totalRowSize = totalRowSize;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
