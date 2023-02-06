package com.kandoka.mybatis.session.defaults;

import cn.hutool.core.util.StrUtil;
import com.kandoka.mybatis.binding.MapperRegistry;
import com.kandoka.mybatis.session.SqlSession;

/**
 * @Description TODO
 * @Author kandoka
 * @Date 2023/2/6 16:38
 */
public class DefaultSqlSession implements SqlSession {

    /**
     * 映射器注册机
     */
    private MapperRegistry mapperRegistry;

    public DefaultSqlSession(MapperRegistry mapperRegistry) {
        this.mapperRegistry = mapperRegistry;
    }

    @Override
    public <T> T selectOne(String statement) {
        return (T) StrUtil.format("You have been proxied! Method: {}", statement);
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {
        return (T) StrUtil.format("You have been proxied! Method: {}, parameter: {}", statement, parameter);
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return mapperRegistry.getMapper(type, this);
    }
}
