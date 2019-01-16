package com.eagletsoft.boot.auth.core.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eagletsoft.boot.auth.core.model.Client;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ClientDao extends BaseMapper<Client> {

    @Select("SELECT * FROM client WHERE name = #{name}")
    Client findByName(@Param("name") String name);
}
