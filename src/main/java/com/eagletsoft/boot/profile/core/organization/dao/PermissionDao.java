package com.eagletsoft.boot.profile.core.organization.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eagletsoft.boot.profile.core.organization.model.Permission;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PermissionDao extends BaseMapper<Permission> {

}
