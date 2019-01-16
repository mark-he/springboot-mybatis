package com.eagletsoft.boot.framework.data.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eagletsoft.boot.framework.common.errors.ServiceException;
import com.eagletsoft.boot.framework.common.errors.StandardErrors;
import com.eagletsoft.boot.framework.data.constraint.ConstraintChecker;
import com.eagletsoft.boot.framework.data.entity.Entity;
import com.eagletsoft.boot.framework.data.query.Filter;
import com.eagletsoft.boot.framework.data.query.PageReponse;
import com.eagletsoft.boot.framework.data.query.PageRequest;
import com.eagletsoft.boot.framework.data.utils.HumpLine;
import com.eagletsoft.boot.framework.data.utils.PropertyCopyUtil;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.io.Serializable;

@Transactional(rollbackFor = Exception.class)
public abstract class BaseService<T extends Entity>  {

    protected abstract BaseMapper<T> getInner();

    public void onCreate(T entity) {

    }

    public void afterCreated(T entity, int sucess) {

    }

    public void onMerge(T entity, Object data) {

    }

    public void onUpdate(T entity) {

    }

    public void afterUpdated(T entity, int cnt) {

    }

    public void onRemove(Serializable id) {

    }

    public void afterRemove(Serializable id, int cnt) {

    }

    public T find(Serializable id) {
        return this.getInner().selectById(id);
    }

    public int removeById(Serializable id) {
        this.onRemove(id);
        int ret = this.getInner().deleteById(id);
        this.afterRemove(id, ret);
        return ret;
    }

    public T create(T entity) {
        this.onCreate(entity);
        ConstraintChecker.checkUnique(entity);
        int ret = this.getInner().insert(entity);
        this.afterCreated(entity, ret);
        return entity;
    }

    public T update(Serializable id, Object data) {
        T entity = this.find(id);
        this.onMerge(entity, data);
        this.mergeData(data, entity);
        ConstraintChecker.checkUnique(entity);
        return this.update(entity);
    }

    public T update(T entity) {
        this.onUpdate(entity);
        int ret = this.getInner().updateById(entity);
        this.afterUpdated(entity, ret);
        return entity;
    }

    public T createOrUpdate(T entity) {
        if (null == entity.getId()) {
            return this.create(entity);
        }
        else {
            return this.update(entity);
        }
    }

    public PageReponse<T> query(PageRequest pageRequest) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        searchFilter(pageRequest, queryWrapper);
        Page page = new Page(pageRequest.getPage(), pageRequest.getSize());

        IPage<T> iPage = this.getInner().selectPage(page, queryWrapper);
        PageReponse<T> result = new PageReponse();

        result.setTotal(iPage.getTotal());
        result.setList(iPage.getRecords());
        return result;
    }

    protected void searchFilter(PageRequest pageRequest, QueryWrapper<T> queryWrapper){
        if (!StringUtils.isEmpty(pageRequest.getSort())) {
            String[] sorts = pageRequest.getSort().split(",", -1);
            for (String sort : sorts) {
                String[] sa = sort.trim().split(" ", -1);
                if (sa.length > 1 && sa[1].equalsIgnoreCase("DESC")) {
                    queryWrapper.orderByDesc(sa[0]);
                }
                else {
                    queryWrapper.orderByAsc(sa[0]);
                }
                break; //only support one sorting column
            }
        }
        if(null != pageRequest.getFilters()) {
            for(Filter filter:pageRequest.getFilters()) {
                if(null != filter.getValue() && !StringUtils.isEmpty(filter.getValue().toString())) {
                    switch (filter.getOp()) {
                        case "*":
                            queryWrapper.like(HumpLine.humpToLine(filter.getName()),filter.getValue());
                            break;
                        case "=":
                            queryWrapper.eq(HumpLine.humpToLine(filter.getName()),filter.getValue());
                            break;
                        case "<>":
                            queryWrapper.ne(HumpLine.humpToLine(filter.getName()),filter.getValue());
                            break;
                        case ">":
                            queryWrapper.gt(HumpLine.humpToLine(filter.getName()),filter.getValue());
                            break;
                        case "<":
                            queryWrapper.lt(HumpLine.humpToLine(filter.getName()),filter.getValue());
                            break;
                        case ">=":
                            queryWrapper.ge(HumpLine.humpToLine(filter.getName()),filter.getValue());
                            break;
                        case "<=":
                            queryWrapper.le(HumpLine.humpToLine(filter.getName()),filter.getValue());
                            break;
                    }
                }
            }
        }
    }

    protected void mergeData(Object src, T target) {
        try {
            PropertyCopyUtil.getInstance().copyPropertiesWithIgnores(target, src, "id", "createdTime", "createdBy", "updatedTime", "updatedBy", "deleted");
        }
        catch (Exception ex) {
            throw new ServiceException(StandardErrors.INTERNAL_ERROR.getStatus(), ex.getMessage());
        }
    }
}
