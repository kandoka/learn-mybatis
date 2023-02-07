package com.kandoka.mybatis.transaction;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @Description TODO
 * @Author kandoka
 * @Date 2023/2/7 14:46
 */
public interface TransactionFactory {
    /**
     * 根据 Connection 创建 Transaction
     * @param conn Existing database connection
     * @return Transaction
     */
    Transaction newTransaction(Connection conn);

    /**
     * 根据数据源和事务隔离级别创建 Transaction
     * @param dataSource DataSource to take the connection from
     * @param level Desired isolation level
     * @param autoCommit Desired autocommit
     * @return Transaction
     */
    Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit);

}
