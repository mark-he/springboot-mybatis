package com.eagletsoft.boot.framework.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

public class LongEntity implements Entity<Long> {
    @TableId(type = IdType.AUTO)
    protected Long id;


    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }
}
