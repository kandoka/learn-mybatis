package com.kandoka.mybatis.session;

import com.kandoka.mybatis.binding.MapperRegistry;
import com.kandoka.mybatis.datasource.druid.DruidDataSourceFactory;
import com.kandoka.mybatis.mapping.Environment;
import com.kandoka.mybatis.mapping.MappedStatement;
import com.kandoka.mybatis.transaction.jdbc.JdbcTransactionFactory;
import com.kandoka.mybatis.type.TypeAliasRegistry;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description a wrapper for registry and sql statements
 * @Author kandoka
 * @Date 2023/2/6 17:58
 */
@Slf4j
public class Configuration {

    protected Environment environment;

    protected MapperRegistry mapperRegistry = new MapperRegistry(this);

    protected final Map<String, MappedStatement> mappedStatements = new HashMap<>();

    // 类型别名注册机
    protected final TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();

    public Configuration() {
        typeAliasRegistry.registerAlias("JDBC", JdbcTransactionFactory.class);
        typeAliasRegistry.registerAlias("DRUID", DruidDataSourceFactory.class);
    }

    public <T> void addMapper(Class<T> type) {
        mapperRegistry.addMapper(type);
    }

    public void addMappedStatement(MappedStatement ms) {
        mappedStatements.put(ms.getId(), ms);
    }


    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        return mapperRegistry.getMapper(type, sqlSession);
    }

    public MappedStatement getMappedStatement(String id) {
        return mappedStatements.get(id);
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public TypeAliasRegistry getTypeAliasRegistry() {
        return typeAliasRegistry;
    }
}
