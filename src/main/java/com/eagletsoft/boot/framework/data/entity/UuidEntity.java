package com.eagletsoft.boot.framework.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

public class UuidEntity implements Entity<String> {
    @TableId(type = IdType.UUID)
    protected String id;

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }
}
