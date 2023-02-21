package com.kandoka.mybatis.executor;

import com.kandoka.mybatis.mapping.BoundSql;
import com.kandoka.mybatis.mapping.MappedStatement;
import com.kandoka.mybatis.session.ResultHandler;
import com.kandoka.mybatis.session.RowBounds;
import com.kandoka.mybatis.transaction.Transaction;

import java.sql.SQLException;
import java.util.List;

/**
 * @Description define operation of sql execution <br/>
 * An executor will wrap and operate a transaction and the connection
 * @Author kandoka
 * @Date 2023/2/9 15:20
 */
public interface Executor {

    ResultHandler NO_RESULT_HANDLER = null;

    /**
     * interface method for insert|update|delete
     * @param ms
     * @param parameter
     * @return
     * @throws SQLException
     */
    int update(MappedStatement ms, Object parameter) throws SQLException;

    /**
     * interface method for query
     * @param ms
     * @param parameter
     * @param rowBounds
     * @param resultHandler
     * @param boundSql
     * @return
     * @param <E>
     */
    <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException;

    Transaction getTransaction();

    void commit(boolean required) throws SQLException;

    void rollback(boolean required) throws SQLException;

    void close(boolean forceRollback) throws SQLException;
}
