package com.kandoka.mybatis.session;

import com.kandoka.mybatis.builder.xml.XMLConfigBuilder;
import com.kandoka.mybatis.session.defaults.DefaultSqlSessionFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.Reader;

/**
 * @Description TODO
 * @Author kandoka
 * @Date 2023/2/6 17:58
 */
@Slf4j
public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(Reader reader) {
        log.info("start building a sql session factory, by config builder");
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder(reader);
        return build(xmlConfigBuilder.parse());
    }

    public SqlSessionFactory build(Configuration config) {
        return new DefaultSqlSessionFactory(config);
    }
}
