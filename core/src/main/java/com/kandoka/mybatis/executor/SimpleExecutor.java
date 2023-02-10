package com.kandoka.mybatis.executor;

import com.kandoka.mybatis.executor.statement.StatementHandler;
import com.kandoka.mybatis.mapping.BoundSql;
import com.kandoka.mybatis.mapping.MappedStatement;
import com.kandoka.mybatis.session.Configuration;
import com.kandoka.mybatis.session.ResultHandler;
import com.kandoka.mybatis.transaction.Transaction;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @Description A simple executor
 * @Author kandoka
 * @Date 2023/2/9 15:21
 */
@Slf4j
public class SimpleExecutor extends BaseExecutor {

    public SimpleExecutor(Configuration configuration, Transaction transaction) {
        super(configuration, transaction);
    }

    /**
     * do query <br/>
     * 1. get global config <br/>
     * 2. get a proper statement handler <br/>
     * 3. get a connection from transaction <br/>
     * 4. transfer parameter using statement handler <br/>
     * 5. execute statement using handler and return the result
     * @param ms
     * @param parameter
     * @param resultHandler
     * @param boundSql
     * @return
     * @param <E>
     */
    @Override
    protected <E> List<E> doQuery(MappedStatement ms, Object parameter, ResultHandler resultHandler, BoundSql boundSql) {
        try {
            Configuration configuration = ms.getConfiguration();
            StatementHandler handler = configuration.newStatementHandler(this, ms, parameter, resultHandler, boundSql);
            Connection connection = transaction.getConnection();
            Statement stmt = handler.prepare(connection);
            handler.parameterize(stmt);
            return handler.query(stmt, resultHandler);
        } catch (SQLException e) {
            log.error("Error executing query sql", e);
            return null;
        }
    }
}
