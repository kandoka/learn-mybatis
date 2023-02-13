package com.kandoka.mybatis.session;

import com.kandoka.mybatis.binding.MapperRegistry;
import com.kandoka.mybatis.datasource.druid.DruidDataSourceFactory;
import com.kandoka.mybatis.datasource.pooled.PooledDataSourceFactory;
import com.kandoka.mybatis.datasource.unpooled.UnpooledDataSourceFactory;
import com.kandoka.mybatis.executor.Executor;
import com.kandoka.mybatis.executor.SimpleExecutor;
import com.kandoka.mybatis.executor.resultset.DefaultResultSetHandler;
import com.kandoka.mybatis.executor.resultset.ResultSetHandler;
import com.kandoka.mybatis.executor.statement.PreparedStatementHandler;
import com.kandoka.mybatis.executor.statement.StatementHandler;
import com.kandoka.mybatis.mapping.BoundSql;
import com.kandoka.mybatis.mapping.Environment;
import com.kandoka.mybatis.mapping.MappedStatement;
import com.kandoka.mybatis.transaction.Transaction;
import com.kandoka.mybatis.transaction.jdbc.JdbcTransactionFactory;
import com.kandoka.mybatis.type.TypeAliasRegistry;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

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
        typeAliasRegistry.registerAlias("UNPOOLED", UnpooledDataSourceFactory.class);
        typeAliasRegistry.registerAlias("POOLED", PooledDataSourceFactory.class);
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

    /**
     * create a result set handler
     * @param executor
     * @param mappedStatement
     * @param boundSql
     * @return
     */
    public ResultSetHandler newResultSetHandler(Executor executor, MappedStatement mappedStatement, BoundSql boundSql) {
        return new DefaultResultSetHandler(executor, mappedStatement, boundSql);
    }

    /**
     * create a executor
     * @param transaction
     * @return
     */
    public Executor newExecutor(Transaction transaction) {
        return new SimpleExecutor(this, transaction);
    }

    /**
     * create a statement handler
     * @param executor
     * @param mappedStatement
     * @param parameter
     * @param resultHandler
     * @param boundSql
     * @return
     */
    public StatementHandler newStatementHandler(Executor executor, MappedStatement mappedStatement,
                                                Object parameter, ResultHandler resultHandler, BoundSql boundSql) {
        return new PreparedStatementHandler(executor, mappedStatement, parameter, resultHandler, boundSql);
    }
}
