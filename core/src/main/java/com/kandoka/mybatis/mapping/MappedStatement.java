package com.kandoka.mybatis.mapping;

import com.kandoka.mybatis.session.Configuration;

import java.util.Map;

/**
 * @Description A wrapper to sql statement
 * @Author kandoka
 * @Date 2023/2/6 17:57
 */
public class MappedStatement {
    private Configuration configuration;
    private String id;
    private SqlCommandType sqlCommandType;

    private BoundSql boundSql;

    public static class Builder {

        private MappedStatement mappedStatement = new MappedStatement();

        public Builder(Configuration configuration, String id, SqlCommandType sqlCommandType, BoundSql boundSql) {
            mappedStatement.configuration = configuration;
            mappedStatement.id = id;
            mappedStatement.sqlCommandType = sqlCommandType;
            mappedStatement.boundSql = boundSql;
        }

        public MappedStatement build() {
            assert mappedStatement.configuration != null;
            assert mappedStatement.id != null;
            return mappedStatement;
        }
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public String getId() {
        return id;
    }

    public SqlCommandType getSqlCommandType() {
        return sqlCommandType;
    }

    public BoundSql getBoundSql() {
        return boundSql;
    }
}
