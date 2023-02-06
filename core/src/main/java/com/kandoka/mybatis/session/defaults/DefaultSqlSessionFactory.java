package com.kandoka.mybatis.session.defaults;

import com.kandoka.mybatis.binding.MapperRegistry;
import com.kandoka.mybatis.session.SqlSession;
import com.kandoka.mybatis.session.SqlSessionFactory;

/**
 * @Description TODO
 * @Author kandoka
 * @Date 2023/2/6 16:38
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private final MapperRegistry mapperRegistry;

    public DefaultSqlSessionFactory(MapperRegistry mapperRegistry) {
        this.mapperRegistry = mapperRegistry;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(mapperRegistry);
    }
}
