package com.kandoka.mybatis.transaction;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Description define a transaction and operations of an inner connection
 * @Author kandoka
 * @Date 2023/2/7 14:45
 */
public interface Transaction {

    Connection getConnection() throws SQLException;

    void commit() throws SQLException;

    void rollback() throws SQLException;

    void close() throws SQLException;
}
