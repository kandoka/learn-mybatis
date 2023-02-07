package com.kandoka.mybatis.session;

import com.kandoka.mybatis.builder.xml.XMLConfigBuilder;
import com.kandoka.mybatis.session.defaults.DefaultSqlSessionFactory;

import java.io.Reader;

/**
 * @Description TODO
 * @Author kandoka
 * @Date 2023/2/6 17:58
 */
public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(Reader reader) {
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder(reader);
        return build(xmlConfigBuilder.parse());
    }

    public SqlSessionFactory build(Configuration config) {
        return new DefaultSqlSessionFactory(config);
    }
}
