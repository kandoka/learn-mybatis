package com.kandoka.mybatis.builder;

import com.kandoka.mybatis.mapping.BoundSql;
import com.kandoka.mybatis.mapping.ParameterMapping;
import com.kandoka.mybatis.mapping.SqlSource;
import com.kandoka.mybatis.session.Configuration;

import java.util.List;

/**
 * @Description A wrapper for a static sql code, including sql and its parameter mappings
 * @Author kandoka
 * @Date 2023/2/14 17:13
 */
public class StaticSqlSource implements SqlSource {

    private String sql;
    private List<ParameterMapping> parameterMappings;
    private Configuration configuration;

    public StaticSqlSource(Configuration configuration, String sql) {
        this(configuration, sql, null);
    }

    public StaticSqlSource(Configuration configuration, String sql, List<ParameterMapping> parameterMappings) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;
        this.configuration = configuration;
    }

    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        return new BoundSql(configuration, sql, parameterMappings, parameterObject);
    }
}
