package com.kandoka.mybatis.executor.statement;

import com.kandoka.mybatis.session.ResultHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @Description A handler for sql statement <br/>
 * for prepare statement, translate parameter, execute sql and wrap the result
 * @Author kandoka
 * @Date 2023/2/9 15:21
 */
public interface StatementHandler {

    /**
     * prepare sql statement
     * @param connection
     * @return
     * @throws SQLException
     */
    Statement prepare(Connection connection) throws SQLException;

    /**
     * parameterize, set parameters in sql
     * @param statement
     * @throws SQLException
     */
    void parameterize(Statement statement) throws SQLException;

    /**
     * execute update
     * @param statement
     * @return
     * @throws SQLException
     */
    int update(Statement statement) throws SQLException;

    /**
     * execute query
     * @param statement
     * @param resultHandler
     * @return
     * @param <E>
     * @throws SQLException
     */
    <E> List<E> query(Statement statement, ResultHandler resultHandler) throws SQLException;
}
