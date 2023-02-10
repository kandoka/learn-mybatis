package com.kandoka.mybatis.executor;

import com.kandoka.mybatis.mapping.BoundSql;
import com.kandoka.mybatis.mapping.MappedStatement;
import com.kandoka.mybatis.session.ResultHandler;
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

    <E> List<E> query(MappedStatement ms, Object parameter, ResultHandler resultHandler, BoundSql boundSql);

    Transaction getTransaction();

    void commit(boolean required) throws SQLException;

    void rollback(boolean required) throws SQLException;

    void close(boolean forceRollback) throws SQLException;
}
