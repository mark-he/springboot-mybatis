package com.eagletsoft.boot.auth.core.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eagletsoft.boot.auth.core.model.VerifyCode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AuthCodeDao extends BaseMapper<VerifyCode> {

    @Select("SELECT * FROM VerifyCode WHERE client_id = #{clientId} AND user_id = #{userId}")
    VerifyCode findByClientUser(@Param("clientId") String clientId, @Param("userId") String userId);
}
