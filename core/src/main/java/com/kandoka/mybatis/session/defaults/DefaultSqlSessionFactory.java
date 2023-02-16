package com.kandoka.mybatis.session.defaults;

import com.kandoka.mybatis.binding.MapperRegistry;
import com.kandoka.mybatis.executor.Executor;
import com.kandoka.mybatis.log.Mark;
import com.kandoka.mybatis.log.MarkableLogger;
import com.kandoka.mybatis.log.MarkableLoggerFactory;
import com.kandoka.mybatis.mapping.Environment;
import com.kandoka.mybatis.session.Configuration;
import com.kandoka.mybatis.session.SqlSession;
import com.kandoka.mybatis.session.SqlSessionFactory;
import com.kandoka.mybatis.transaction.Transaction;
import com.kandoka.mybatis.transaction.TransactionFactory;
import com.kandoka.mybatis.transaction.TransactionIsolationLevel;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;

/**
 * @Description TODO
 * @Author kandoka
 * @Date 2023/2/6 16:38
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private final static MarkableLogger log = MarkableLoggerFactory.getLogger(Mark.SESSION, DefaultSqlSessionFactory.class);

    private final Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        log.info("create a sql session via configuration");
        Transaction tx = null;
        try {
            final Environment environment = configuration.getEnvironment();
            TransactionFactory transactionFactory = environment.getTransactionFactory();
            tx = transactionFactory.newTransaction(environment.getDataSource(), TransactionIsolationLevel.READ_COMMITTED, false);
            final Executor executor = configuration.newExecutor(tx);
            return new DefaultSqlSession(configuration, executor);
        } catch (Exception e) {
            try {
                assert tx != null;
                tx.close();
            } catch (SQLException ignore) {
            }
            throw new RuntimeException("Error opening session.  Cause: " + e);
        }
    }
}
