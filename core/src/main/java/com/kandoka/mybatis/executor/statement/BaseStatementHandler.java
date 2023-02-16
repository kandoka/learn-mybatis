package com.kandoka.mybatis.executor.statement;

import com.kandoka.mybatis.executor.Executor;
import com.kandoka.mybatis.executor.resultset.ParameterHandler;
import com.kandoka.mybatis.executor.resultset.ResultSetHandler;
import com.kandoka.mybatis.log.Mark;
import com.kandoka.mybatis.log.MarkableLogger;
import com.kandoka.mybatis.log.MarkableLoggerFactory;
import com.kandoka.mybatis.mapping.BoundSql;
import com.kandoka.mybatis.mapping.MappedStatement;
import com.kandoka.mybatis.session.Configuration;
import com.kandoka.mybatis.session.ResultHandler;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

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

    protected BoundSql boundSql;

    public BaseStatementHandler(Executor executor, MappedStatement mappedStatement,
                                Object parameterObject, ResultHandler resultHandler, BoundSql boundSql) {
        this.configuration = mappedStatement.getConfiguration();
        this.executor = executor;
        this.mappedStatement = mappedStatement;
        this.boundSql = boundSql;
        this.parameterObject = parameterObject;
        this.resultSetHandler = configuration.newResultSetHandler(executor, mappedStatement, boundSql);
        this.parameterHandler = configuration.newParameterHandler(mappedStatement, parameterObject, boundSql);
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
