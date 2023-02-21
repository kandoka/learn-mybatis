package com.kandoka.mybatis.executor.statement;

import com.kandoka.mybatis.executor.Executor;
import com.kandoka.mybatis.executor.parameter.ParameterHandler;
import com.kandoka.mybatis.executor.resultset.ResultSetHandler;
import com.kandoka.mybatis.log.Mark;
import com.kandoka.mybatis.log.MarkableLogger;
import com.kandoka.mybatis.log.MarkableLoggerFactory;
import com.kandoka.mybatis.mapping.BoundSql;
import com.kandoka.mybatis.mapping.MappedStatement;
import com.kandoka.mybatis.session.Configuration;
import com.kandoka.mybatis.session.ResultHandler;
import com.kandoka.mybatis.session.RowBounds;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @Description TODO
 * @Author kandoka
 * @Date 2023/2/9 15:21
 */
public abstract class BaseStatementHandler implements StatementHandler {

    private final static MarkableLogger log = MarkableLoggerFactory.getLogger(Mark.STATEMENT, BaseStatementHandler.class);

    protected final Configuration configuration;

    protected final Executor executor;

    protected final MappedStatement mappedStatement;

    protected final Object parameterObject;

    protected final ResultSetHandler resultSetHandler;
    protected final ParameterHandler parameterHandler;

    protected final RowBounds rowBounds;
    protected BoundSql boundSql;

    public BaseStatementHandler(Executor executor, MappedStatement mappedStatement, Object parameterObject, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
        this.configuration = mappedStatement.getConfiguration();
        this.executor = executor;
        this.mappedStatement = mappedStatement;
        this.rowBounds = rowBounds;
        this.boundSql = boundSql;

        this.parameterObject = parameterObject;
        this.parameterHandler = configuration.newParameterHandler(mappedStatement, parameterObject, boundSql);
        this.resultSetHandler = configuration.newResultSetHandler(executor, mappedStatement, rowBounds, resultHandler, boundSql);
    }


    @Override
    public Statement prepare(Connection connection) throws SQLException {
        Statement statement = null;
        try {
            // instantiate Statement
            statement = instantiateStatement(connection);
            // set config
            statement.setQueryTimeout(350);
            statement.setFetchSize(10000);
            log.info("Create a statement via the connection");
            return statement;
        } catch (Exception e) {
            throw new RuntimeException("Error preparing statement.  Cause: " + e, e);
        }
    }

    protected abstract Statement instantiateStatement(Connection connection) throws SQLException;
}
