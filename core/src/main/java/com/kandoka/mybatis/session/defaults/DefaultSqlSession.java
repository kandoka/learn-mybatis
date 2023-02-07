package com.kandoka.mybatis.session.defaults;

import cn.hutool.core.util.StrUtil;
import com.kandoka.mybatis.binding.MapperRegistry;
import com.kandoka.mybatis.mapping.MappedStatement;
import com.kandoka.mybatis.session.Configuration;
import com.kandoka.mybatis.session.SqlSession;

/**
 * @Description TODO
 * @Author kandoka
 * @Date 2023/2/6 16:38
 */
public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <T> T selectOne(String statement) {
        return (T) StrUtil.format("You have been proxied! Method: {}", statement);
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {
        MappedStatement mappedStatement = this.configuration.getMappedStatement(statement);
        return (T) StrUtil.format("You have been proxied! Method: {}, parameter: {}, sql: {}",
                statement, parameter, mappedStatement.getSql());
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return this.configuration.getMapper(type, this);
    }
}
