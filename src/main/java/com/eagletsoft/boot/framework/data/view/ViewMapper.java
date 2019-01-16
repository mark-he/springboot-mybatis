package com.eagletsoft.boot.framework.data.view;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface ViewMapper {
    static class PureSqlProvider {
        public String sql(String sql) {
            return sql;
        }

        public String count(String from) {
            return "SELECT count(*) FROM " + from;
        }
    }

    @SelectProvider(type = PureSqlProvider.class, method = "sql")
    <T> List<T> select(String sql);

    @SelectProvider(type = PureSqlProvider.class, method = "sql")
    <T> T selectOne(String sql);

    @SelectProvider(type = PureSqlProvider.class, method = "count")
    Integer count(String from);

    @SelectProvider(type = PureSqlProvider.class, method = "sql")
    Integer execute(String query);
}
