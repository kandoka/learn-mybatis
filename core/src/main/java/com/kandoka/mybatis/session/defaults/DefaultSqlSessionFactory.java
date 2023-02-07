package com.kandoka.mybatis.session.defaults;

import com.kandoka.mybatis.binding.MapperRegistry;
import com.kandoka.mybatis.session.Configuration;
import com.kandoka.mybatis.session.SqlSession;
import com.kandoka.mybatis.session.SqlSessionFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description TODO
 * @Author kandoka
 * @Date 2023/2/6 16:38
 */
@Slf4j
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private final Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        log.info("create a sql session via configuration");
        return new DefaultSqlSession(configuration);
    }
}
