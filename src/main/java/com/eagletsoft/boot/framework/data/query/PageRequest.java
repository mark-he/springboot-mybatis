package com.eagletsoft.boot.framework.data.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
* 分页参数接收封装类*/
public class PageRequest {
    private final static int MAX_SIZE = 100;
    private int page = 1;
    private int size = 10;
    private String sort;
    private Map<String, Object> extra;

    private List<Filter> filters;

    public PageRequest addFilter(Filter e) {
        if (null == filters) {
            filters = new ArrayList<>();
        }
        filters.add(e);
        return this;
    }

    public int getPage() {
        return Math.max(page, 1);
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Map<String, Object> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, Object> extra) {
        this.extra = extra;
    }

    public int getSize() {
        return Math.min(MAX_SIZE, size);
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<Filter> getFilters() {
        return filters;
    }

    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }
}
