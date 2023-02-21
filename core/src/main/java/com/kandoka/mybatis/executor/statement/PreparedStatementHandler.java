package com.kandoka.mybatis.executor.statement;

import com.kandoka.mybatis.executor.Executor;
import com.kandoka.mybatis.log.Mark;
import com.kandoka.mybatis.log.MarkableLogger;
import com.kandoka.mybatis.log.MarkableLoggerFactory;
import com.kandoka.mybatis.mapping.BoundSql;
import com.kandoka.mybatis.mapping.MappedStatement;
import com.kandoka.mybatis.session.ResultHandler;
import com.kandoka.mybatis.session.RowBounds;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @Description Handler for prepared statement
 * @Author kandoka
 * @Date 2023/2/9 15:21
 */
public class PreparedStatementHandler extends BaseStatementHandler {

    private final static MarkableLogger log = MarkableLoggerFactory.getLogger(Mark.STATEMENT, PreparedStatementHandler.class);

    public PreparedStatementHandler(Executor executor, MappedStatement mappedStatement, Object parameterObject, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
        super(executor, mappedStatement, parameterObject, rowBounds, resultHandler, boundSql);
    }

    @Override
    protected Statement instantiateStatement(Connection connection) throws SQLException {
        String sql = boundSql.getSql();
        return connection.prepareStatement(sql);
    }

    @Override
    public void parameterize(Statement statement) throws SQLException {
        log.info("Parameterizing for the statement: {}", parameterObject);
        parameterHandler.setParameters((PreparedStatement) statement);
    }

    @Override
    public <E> List<E> query(Statement statement, ResultHandler resultHandler) throws SQLException {
        PreparedStatement ps = (PreparedStatement) statement;
        ps.execute();
        log.info("execute query statement");
        return resultSetHandler.<E> handleResultSets(ps);
    }
}
