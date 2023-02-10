package com.kandoka.mybatis.executor.resultset;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @Description parse the result set to java list
 * @Author kandoka
 * @Date 2023/2/9 15:21
 */
public interface ResultSetHandler {

    <E> List<E> handleResultSets(Statement stmt) throws SQLException;
}
