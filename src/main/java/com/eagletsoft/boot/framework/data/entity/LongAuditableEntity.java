package com.eagletsoft.boot.framework.data.entity;

import com.eagletsoft.boot.framework.data.entity.meta.CreatedBy;
import com.eagletsoft.boot.framework.data.entity.meta.CreatedTime;
import com.eagletsoft.boot.framework.data.entity.meta.UpdatedBy;
import com.eagletsoft.boot.framework.data.entity.meta.UpdatedTime;

import java.util.Date;

public class LongAuditableEntity extends LongEntity implements Auditable {

    @CreatedTime
    protected Date createdTime;
    @CreatedBy
    protected Object createdBy;
    @UpdatedTime
    protected Date updatedTime;
    @UpdatedBy
    protected Object updatedBy;

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Object getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Object createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Object getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Object updatedBy) {
        this.updatedBy = updatedBy;
    }
}
