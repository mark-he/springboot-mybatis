package com.eagletsoft.boot.framework.data.query;

import java.util.Collection;

public class PageReponse<T> {
    private long total;
    private Collection<T> list;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Collection<T> getList() {
        return list;
    }

    public void setList(Collection<T> list) {
        this.list = list;
    }
}
