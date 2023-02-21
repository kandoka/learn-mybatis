package com.kandoka.mybatis.session.defaults;

import cn.hutool.core.util.StrUtil;
import com.kandoka.mybatis.binding.MapperRegistry;
import com.kandoka.mybatis.executor.Executor;
import com.kandoka.mybatis.mapping.BoundSql;
import com.kandoka.mybatis.mapping.Environment;
import com.kandoka.mybatis.mapping.MappedStatement;
import com.kandoka.mybatis.session.Configuration;
import com.kandoka.mybatis.session.RowBounds;
import com.kandoka.mybatis.session.SqlSession;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @Author kandoka
 * @Date 2023/2/6 16:38
 */
@Slf4j
public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    private Executor executor;

    public DefaultSqlSession(Configuration configuration, Executor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    @Override
    public <T> T selectOne(String statement) {
        return (T) StrUtil.format("You have been proxied! Method: {}", statement);
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {
        try {
            MappedStatement ms = configuration.getMappedStatement(statement);
            List<T> list = executor.query(ms, parameter, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER, ms.getSqlSource().getBoundSql(parameter));
            return list.get(0);
        } catch (Exception e) {
            log.error("Error executing sql: {}, parameter: {}", statement, parameter, e);
            return null;
        }
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return this.configuration.getMapper(type, this);
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }
}
