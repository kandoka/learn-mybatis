package com.kandoka.mybatis.transaction.jdbc;

import com.kandoka.mybatis.transaction.Transaction;
import com.kandoka.mybatis.transaction.TransactionFactory;
import com.kandoka.mybatis.transaction.TransactionIsolationLevel;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @Description TODO
 * @Author kandoka
 * @Date 2023/2/7 14:46
 */
public class JdbcTransactionFactory implements TransactionFactory {

    @Override
    public Transaction newTransaction(Connection conn) {
        return new JdbcTransaction(conn);
    }

    @Override
    public Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit) {
        return new JdbcTransaction(dataSource, level, autoCommit);
    }
}
