package com.eagletsoft.boot.profile.core.organization.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eagletsoft.boot.profile.core.organization.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao extends BaseMapper<User> {

    @Select("SELECT * FROM user WHERE login_name = #{loginName}")
    User findByLoginName(@Param("loginName") String loginName);
}
