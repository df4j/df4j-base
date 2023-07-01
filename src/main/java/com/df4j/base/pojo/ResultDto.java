package com.df4j.base.pojo;

import java.util.List;

public class ResultDto<T> {
    private int errorNo = 0;
    private String errorInfo = "success";

    private PageDto page;

    private List<T> data;

    public int getErrorNo() {
        return errorNo;
    }

    public void setErrorNo(int errorNo) {
        this.errorNo = errorNo;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public PageDto getPage() {
        return page;
    }

    public void setPage(PageDto page) {
        this.page = page;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public T getSingle() {
        if (this.data == null || this.data.size() == 0) {
            return null;
        }
        return this.data.get(0);
    }
}
