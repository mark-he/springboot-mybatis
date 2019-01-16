package com.eagletsoft.boot.profile.core.organization.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eagletsoft.boot.profile.core.organization.model.UserAuth;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserAuthDao extends BaseMapper<UserAuth> {

    @Select("SELECT * FROM user_auth WHERE user_id = #{userId}")
    UserAuth findByUserId(@Param("userId") String userId);
}
