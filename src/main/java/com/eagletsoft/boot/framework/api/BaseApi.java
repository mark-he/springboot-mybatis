package com.eagletsoft.boot.framework.api;

import com.eagletsoft.boot.framework.api.utils.ApiResponse;
import com.eagletsoft.boot.framework.common.security.meta.Access;
import com.eagletsoft.boot.framework.data.entity.Entity;
import com.eagletsoft.boot.framework.data.query.PageRequest;
import com.eagletsoft.boot.framework.data.service.BaseService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

public abstract class BaseApi<T extends Entity, DTO extends Serializable> {

    protected abstract BaseService<T> getService();

    @Access(":read")
    @RequestMapping(value = "/read/{id}",method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody Object read(@PathVariable Serializable id){
        return ApiResponse.make(this.getService().find(id));
    }

    @Access(":write")
    @RequestMapping(value = "/create",method = {RequestMethod.POST})
    public @ResponseBody Object create(@RequestBody @Validated T obj){
        return ApiResponse.make(this.getService().create(obj));
    }

    @Access(":write")
    @RequestMapping(value = "/remove/{id}",method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody Object remove(@PathVariable Serializable id){
        return ApiResponse.make(this.getService().removeById(id));
    }

    @Access(":write")
    @RequestMapping(value = "/update/{id}",method = {RequestMethod.POST})
    public @ResponseBody Object update(@PathVariable Serializable id, @Validated @RequestBody DTO obj){
        return ApiResponse.make(this.getService().update(id, obj));
    }

    @Access(":read")
    @RequestMapping(value = "/search",method = {RequestMethod.POST})
    public @ResponseBody Object update(@RequestBody @Validated PageRequest pageRequest){
        return ApiResponse.make(this.getService().query(pageRequest));
    }
}
