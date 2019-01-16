package com.eagletsoft.boot.profile.core.organization.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eagletsoft.boot.profile.core.organization.model.Permission;
import com.eagletsoft.boot.profile.core.organization.model.PositionPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Collection;

@Mapper
public interface PositionPermissionDao extends BaseMapper<PositionPermission> {

    @Select("SELECT DISTINCT * FROM permission a WHERE a.enabled = 1 AND EXISTS(SELECT 1 FROM position_permission b WHERE a.id = b.permission_id AND position_id = #{positionId})")
    Collection<Permission> findByPositionId(@Param("positionId") String positionId);
}
