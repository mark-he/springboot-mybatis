package com.eagletsoft.boot.auth.core.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eagletsoft.boot.auth.core.model.AccessToken;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AccessTokenDao extends BaseMapper<AccessToken> {

    @Select("SELECT * FROM access_token WHERE token = #{token}")
    AccessToken findByToken(@Param("token") String token);

    @Select("SELECT * FROM access_token WHERE client_id = #{clientId} AND user_id = #{userId}")
    AccessToken findByClientUser(@Param("clientId") String clientId, @Param("userId") String userId);
}
