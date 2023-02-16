package com.kandoka.mybatis.executor.resultset;

import com.kandoka.mybatis.executor.Executor;
import com.kandoka.mybatis.log.Mark;
import com.kandoka.mybatis.log.MarkableLogger;
import com.kandoka.mybatis.log.MarkableLoggerFactory;
import com.kandoka.mybatis.mapping.BoundSql;
import com.kandoka.mybatis.mapping.MappedStatement;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @Author kandoka
 * @Date 2023/2/9 15:21
 */
public class DefaultResultSetHandler implements ResultSetHandler {

    private final static MarkableLogger log = MarkableLoggerFactory.getLogger(Mark.RESULT, DefaultResultSetHandler.class);

    private final BoundSql boundSql;
    private final MappedStatement mappedStatement;

    public DefaultResultSetHandler(Executor executor, MappedStatement mappedStatement, BoundSql boundSql) {
        this.boundSql = boundSql;
        this.mappedStatement = mappedStatement;
    }

    @Override
    public <E> List<E> handleResultSets(Statement stmt) throws SQLException {
        ResultSet resultSet = stmt.getResultSet();
        log.info("handle result set: {}", mappedStatement.getResultType());
        return resultSet2Obj(resultSet, mappedStatement.getResultType());
    }

    /**
     * map result set to java object
     * @param resultSet
     * @param clazz
     * @return
     * @param <T>
     */
    private <T> List<T> resultSet2Obj(ResultSet resultSet, Class<?> clazz) {
        List<T> list = new ArrayList<>();
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            // 每次遍历行值
            while (resultSet.next()) {
                T obj = (T) clazz.newInstance();
                for (int i = 1; i <= columnCount; i++) {
                    Object value = resultSet.getObject(i);
                    String columnName = metaData.getColumnLabel(i);
                    String setMethod = "set" + columnName.substring(0, 1).toUpperCase() + columnName.substring(1);
                    Method method;
                    if(value == null) {
                        continue;
                    }
                    if (value instanceof Timestamp) {
                        method = clazz.getMethod(setMethod, Date.class);
                    } else {
                        method = clazz.getMethod(setMethod, value.getClass());
                    }
                    method.invoke(obj, value);
                }
                list.add(obj);
            }
        } catch (Exception e) {
            log.error("Error mapping result set to java object", e);
        }
        return list;
    }
}
