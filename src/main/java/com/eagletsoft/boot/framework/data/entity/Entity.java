package com.eagletsoft.boot.framework.data.entity;

import java.io.Serializable;

public interface Entity<T extends Serializable> extends Serializable {
    T getId();
}
