package com.eagletsoft.boot.framework.data.entity;

import java.util.Date;

public interface Auditable {

    Date getCreatedTime();

    void setCreatedTime(Date createdTime);

    Object getCreatedBy();

    void setCreatedBy(Object createdBy);

    Date getUpdatedTime();

    void setUpdatedTime(Date updatedTime);

    Object getUpdatedBy();

    void setUpdatedBy(Object updatedBy);
}
